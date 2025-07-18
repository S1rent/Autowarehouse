package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ChatSessionService {

    private static final Logger LOG = Logger.getLogger(ChatSessionService.class);

    @Transactional
    public ChatSession createSession(Long ticketId, Long customerId) {
        LOG.infof("Creating chat session for ticket %d and customer %d", ticketId, customerId);
        
        // Check if session already exists
        ChatSession existingSession = ChatSession.findByTicketId(ticketId);
        if (existingSession != null) {
            LOG.infof("Chat session already exists for ticket %d", ticketId);
            return existingSession;
        }

        // Validate ticket exists
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate customer exists
        User customer = User.findById(customerId);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        // Create session
        ChatSession session = new ChatSession(ticketId, customerId);
        session.persist();
        
        LOG.infof("Created chat session %d for ticket %d", session.id, ticketId);
        return session;
    }

    @Transactional
    public ChatSession assignAgent(Long ticketId, Long agentId) {
        LOG.infof("Assigning agent %d to chat session for ticket %d", agentId, ticketId);
        
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session == null) {
            throw new NotFoundException("Chat session not found for ticket");
        }

        // Validate agent if provided
        if (agentId != null) {
            User agent = User.findById(agentId);
            if (agent == null || !agent.isAdmin()) {
                throw new NotFoundException("Agent not found or not authorized");
            }
        }

        session.assignAgent(agentId);
        session.persist();
        
        LOG.infof("Assigned agent %d to chat session %d", agentId, session.id);
        return session;
    }

    @Transactional
    public ChatSession activateSession(Long ticketId) {
        LOG.infof("Activating chat session for ticket %d", ticketId);
        
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session == null) {
            throw new NotFoundException("Chat session not found for ticket");
        }

        session.activate();
        session.persist();
        
        LOG.infof("Activated chat session %d", session.id);
        return session;
    }

    @Transactional
    public ChatSession deactivateSession(Long ticketId) {
        LOG.infof("Deactivating chat session for ticket %d", ticketId);
        
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session == null) {
            throw new NotFoundException("Chat session not found for ticket");
        }

        session.deactivate();
        session.persist();
        
        LOG.infof("Deactivated chat session %d", session.id);
        return session;
    }

    @Transactional
    public ChatSession endSession(Long ticketId) {
        LOG.infof("Ending chat session for ticket %d", ticketId);
        
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session == null) {
            throw new NotFoundException("Chat session not found for ticket");
        }

        session.end();
        session.persist();
        
        LOG.infof("Ended chat session %d", session.id);
        return session;
    }

    @Transactional
    public void updateLastActivity(Long ticketId) {
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session != null) {
            session.updateLastActivity();
            session.persist();
        }
    }

    public ChatSession getSessionByTicketId(Long ticketId) {
        return ChatSession.findByTicketId(ticketId);
    }

    public List<ChatSession> getSessionsByCustomer(Long customerId) {
        return ChatSession.findByCustomerId(customerId);
    }

    public List<ChatSession> getSessionsByAgent(Long agentId) {
        return ChatSession.findByAgentId(agentId);
    }

    public List<ChatSession> getActiveSessions() {
        return ChatSession.findActiveSessions();
    }

    public List<ChatSession> getActiveSessionsByAgent(Long agentId) {
        return ChatSession.findActiveSessionsByAgent(agentId);
    }

    public List<ChatSession> getUnassignedActiveSessions() {
        return ChatSession.findUnassignedActiveSessions();
    }

    public List<ChatSession> getRecentSessions(int hours) {
        return ChatSession.findRecentSessions(hours);
    }

    public long getActiveSessionCount() {
        return ChatSession.countActiveSessions();
    }

    public long getActiveSessionCountByAgent(Long agentId) {
        return ChatSession.countActiveSessionsByAgent(agentId);
    }

    @Transactional
    public void cleanupStaleSessions(int inactiveMinutes) {
        LOG.infof("Cleaning up stale sessions inactive for more than %d minutes", inactiveMinutes);
        
        List<ChatSession> activeSessions = ChatSession.findActiveSessions();
        int cleanedUp = 0;
        
        for (ChatSession session : activeSessions) {
            if (session.isStale(inactiveMinutes)) {
                session.deactivate();
                session.persist();
                cleanedUp++;
                LOG.debugf("Deactivated stale session %d", session.id);
            }
        }
        
        LOG.infof("Cleaned up %d stale sessions", cleanedUp);
    }

    public boolean isSessionActive(Long ticketId) {
        ChatSession session = ChatSession.findByTicketId(ticketId);
        return session != null && session.isActive();
    }

    public boolean isAgentAvailable(Long agentId, int maxConcurrentSessions) {
        long activeSessionCount = ChatSession.countActiveSessionsByAgent(agentId);
        return activeSessionCount < maxConcurrentSessions;
    }

    public ChatSession getSessionById(Long sessionId) {
        ChatSession session = ChatSession.findById(sessionId);
        if (session == null) {
            throw new NotFoundException("Chat session not found");
        }
        return session;
    }

    public long getSessionDuration(Long ticketId) {
        ChatSession session = ChatSession.findByTicketId(ticketId);
        if (session == null) {
            return 0;
        }
        return session.getDurationInMinutes();
    }

    // Statistics methods
    public long getTotalSessionCount() {
        return ChatSession.count();
    }

    public long getSessionCountByStatus(ChatSessionStatus status) {
        return ChatSession.count("status", status);
    }

    public List<ChatSession> getSessionsByStatus(ChatSessionStatus status) {
        return ChatSession.findByStatus(status);
    }
}
