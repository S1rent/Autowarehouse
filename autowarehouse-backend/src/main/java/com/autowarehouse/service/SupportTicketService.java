package com.autowarehouse.service;

import com.autowarehouse.dto.CreateTicketRequest;
import com.autowarehouse.dto.TicketResponse;
import com.autowarehouse.dto.UpdateTicketRequest;
import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupportTicketService {

    private static final Logger LOG = Logger.getLogger(SupportTicketService.class);

    @Inject
    ChatSessionService chatSessionService;

    @Inject
    NotificationService notificationService;

    @Transactional
    public TicketResponse createTicket(CreateTicketRequest request, Long customerId) {
        LOG.infof("Creating new support ticket for customer %d", customerId);
        
        // Validate customer exists
        User customer = User.findById(customerId);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        // Create ticket
        SupportTicket ticket = new SupportTicket(
            request.subject,
            request.description,
            customerId,
            request.category,
            request.priority
        );
        
        ticket.persist();
        LOG.infof("Created support ticket %d", ticket.id);

        // Create chat session
        chatSessionService.createSession(ticket.id, customerId);

        // Auto-assign if high priority and agents available
        if (ticket.isHighPriority()) {
            autoAssignTicket(ticket);
        }

        // Send notification to admins
        notificationService.notifyNewTicket(ticket);

        return new TicketResponse(ticket);
    }

    @Transactional
    public TicketResponse updateTicket(Long ticketId, UpdateTicketRequest request, Long userId) {
        LOG.infof("Updating ticket %d by user %d", ticketId, userId);
        
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate user can update this ticket
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canUpdate = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canUpdate) {
            throw new SecurityException("User not authorized to update this ticket");
        }

        // Update fields
        boolean statusChanged = false;
        boolean agentChanged = false;

        if (request.status != null && !request.status.equals(ticket.status)) {
            ticket.status = request.status;
            statusChanged = true;
            
            if (request.status == TicketStatus.RESOLVED) {
                ticket.resolvedAt = LocalDateTime.now();
            } else if (request.status == TicketStatus.OPEN) {
                ticket.resolvedAt = null;
            }
        }

        if (request.priority != null) {
            ticket.priority = request.priority;
        }

        if (request.assignedAgentId != null && !request.assignedAgentId.equals(ticket.assignedAgentId)) {
            // Validate agent exists and is admin
            if (request.assignedAgentId > 0) {
                User agent = User.findById(request.assignedAgentId);
                if (agent == null || !agent.isAdmin()) {
                    throw new NotFoundException("Agent not found or not authorized");
                }
            }
            
            ticket.assignedAgentId = request.assignedAgentId;
            agentChanged = true;
            
            if (ticket.status == TicketStatus.OPEN) {
                ticket.status = TicketStatus.IN_PROGRESS;
                statusChanged = true;
            }
        }

        ticket.persist();
        LOG.infof("Updated ticket %d", ticketId);

        // Update chat session if agent changed
        if (agentChanged) {
            chatSessionService.assignAgent(ticketId, request.assignedAgentId);
        }

        // Send notifications
        if (statusChanged || agentChanged) {
            notificationService.notifyTicketUpdated(ticket, statusChanged, agentChanged);
        }

        return new TicketResponse(ticket);
    }

    public TicketResponse getTicket(Long ticketId, Long userId) {
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate user can view this ticket
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view this ticket");
        }

        return new TicketResponse(ticket);
    }

    public List<TicketResponse> getTicketsByCustomer(Long customerId) {
        List<SupportTicket> tickets = SupportTicket.findByCustomerId(customerId);
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getTicketsByAgent(Long agentId) {
        List<SupportTicket> tickets = SupportTicket.findByAgentId(agentId);
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getAllTickets() {
        List<SupportTicket> tickets = SupportTicket.listAll();
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getTicketsByStatus(TicketStatus status) {
        List<SupportTicket> tickets = SupportTicket.findByStatus(status);
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getUnassignedTickets() {
        List<SupportTicket> tickets = SupportTicket.findUnassigned();
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    public List<TicketResponse> getOpenTickets() {
        List<SupportTicket> tickets = SupportTicket.findOpenTickets();
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketResponse assignTicket(Long ticketId, Long agentId) {
        LOG.infof("Assigning ticket %d to agent %d", ticketId, agentId);
        
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate agent
        User agent = User.findById(agentId);
        if (agent == null || !agent.isAdmin()) {
            throw new NotFoundException("Agent not found or not authorized");
        }

        ticket.assignToAgent(agentId);
        ticket.persist();

        // Update chat session
        chatSessionService.assignAgent(ticketId, agentId);

        // Send notification
        notificationService.notifyAgentAssigned(ticket, agent);

        LOG.infof("Assigned ticket %d to agent %d", ticketId, agentId);
        return new TicketResponse(ticket);
    }

    @Transactional
    public void autoAssignTicket(SupportTicket ticket) {
        LOG.infof("Auto-assigning ticket %d", ticket.id);
        
        // Find available agents (simple round-robin for now)
        List<User> availableAgents = User.findOnlineAgents();
        if (availableAgents.isEmpty()) {
            availableAgents = User.findAvailableAgents();
        }

        if (!availableAgents.isEmpty()) {
            // Simple assignment to first available agent
            // TODO: Implement more sophisticated assignment logic
            User selectedAgent = availableAgents.get(0);
            ticket.assignToAgent(selectedAgent.id);
            ticket.persist();

            // Update chat session
            chatSessionService.assignAgent(ticket.id, selectedAgent.id);

            // Send notification
            notificationService.notifyAgentAssigned(ticket, selectedAgent);
            
            LOG.infof("Auto-assigned ticket %d to agent %d", ticket.id, selectedAgent.id);
        } else {
            LOG.warnf("No available agents to auto-assign ticket %d", ticket.id);
        }
    }

    @Transactional
    public TicketResponse resolveTicket(Long ticketId, Long userId) {
        LOG.infof("Resolving ticket %d by user %d", ticketId, userId);
        
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate user can resolve this ticket
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canResolve = user.isAdmin() || 
                           (ticket.assignedAgentId != null && ticket.assignedAgentId.equals(userId));
        if (!canResolve) {
            throw new SecurityException("User not authorized to resolve this ticket");
        }

        ticket.resolve();
        ticket.persist();

        // End chat session
        chatSessionService.endSession(ticketId);

        // Send notification
        notificationService.notifyTicketResolved(ticket);

        LOG.infof("Resolved ticket %d", ticketId);
        return new TicketResponse(ticket);
    }

    @Transactional
    public TicketResponse closeTicket(Long ticketId, Long userId) {
        LOG.infof("Closing ticket %d by user %d", ticketId, userId);
        
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate user can close this ticket
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canClose = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canClose) {
            throw new SecurityException("User not authorized to close this ticket");
        }

        ticket.close();
        ticket.persist();

        // End chat session
        chatSessionService.endSession(ticketId);

        // Send notification
        notificationService.notifyTicketClosed(ticket);

        LOG.infof("Closed ticket %d", ticketId);
        return new TicketResponse(ticket);
    }

    // Statistics methods
    public long getOpenTicketCount() {
        return SupportTicket.countOpenTickets();
    }

    public long getTicketCountByStatus(TicketStatus status) {
        return SupportTicket.countByStatus(status);
    }

    public long getTicketCountByAgent(Long agentId) {
        return SupportTicket.countByAgentId(agentId);
    }

    public List<TicketResponse> getRecentTickets(int days) {
        List<SupportTicket> tickets = SupportTicket.findRecentTickets(days);
        return tickets.stream()
                .map(TicketResponse::new)
                .collect(Collectors.toList());
    }
}
