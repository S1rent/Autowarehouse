package com.autowarehouse.service;

import com.autowarehouse.dto.MessageResponse;
import com.autowarehouse.dto.SendMessageRequest;
import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ChatService {

    private static final Logger LOG = Logger.getLogger(ChatService.class);

    @Inject
    ChatSessionService chatSessionService;

    @Inject
    NotificationProducer notificationProducer;

    @Transactional
    public MessageResponse sendMessage(SendMessageRequest request, Long senderId) {
        LOG.infof("Sending message from user %d to ticket %d", senderId, request.ticketId);
        
        // Validate ticket exists
        SupportTicket ticket = SupportTicket.findById(request.ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        // Validate sender exists
        User sender = User.findById(senderId);
        if (sender == null) {
            throw new NotFoundException("Sender not found");
        }

        // Determine sender type
        SenderType senderType = sender.isAdmin() ? SenderType.AGENT : SenderType.CUSTOMER;

        // Validate user can send message to this ticket
        boolean canSend = sender.isAdmin() || ticket.customerId.equals(senderId);
        if (!canSend) {
            throw new SecurityException("User not authorized to send message to this ticket");
        }

        // Validate and sanitize message
        String sanitizedMessage = sanitizeMessage(request.message);
        if (sanitizedMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        // Create message
        ChatMessage message = new ChatMessage(
            request.ticketId,
            senderId,
            senderType,
            sanitizedMessage,
            request.messageType != null ? request.messageType : MessageType.TEXT
        );
        
        message.persist();
        LOG.infof("Created message %d for ticket %d", message.id, request.ticketId);

        // Update chat session activity
        chatSessionService.updateLastActivity(request.ticketId);

        // Update ticket status if needed
        updateTicketStatusOnMessage(ticket, senderType);

        // Send Kafka notifications for chat messages
        try {
            if (senderType == SenderType.CUSTOMER) {
                // Customer sent message, notify admins (adminCustomerService)
                List<User> adminUsers = User.find("role = ?1", "ADMIN").list();
                for (User admin : adminUsers) {
                    notificationProducer.sendAdminCustomerServiceNotification(
                        request.ticketId, 
                        admin.id, 
                        sender.getFullName(),
                        truncateMessage(sanitizedMessage, 100)
                    );
                }
                
                // Send customer service event to Kafka
                notificationProducer.sendCustomerServiceEvent(
                    "messageReceived", 
                    request.ticketId, 
                    senderId, 
                    ticket.customerId, 
                    sanitizedMessage,
                    "CUSTOMER"
                );
                
            } else {
                // Admin sent message, notify customer (customerService)
                notificationProducer.sendCustomerServiceMessageNotification(
                    request.ticketId, 
                    ticket.customerId, 
                    truncateMessage(sanitizedMessage, 100)
                );
                
                // Send customer service event to Kafka
                notificationProducer.sendCustomerServiceEvent(
                    "messageReceived", 
                    request.ticketId, 
                    senderId, 
                    ticket.customerId, 
                    sanitizedMessage,
                    "ADMIN"
                );
            }
            
            LOG.infof("Sent Kafka notification for message from %s to ticket %d", 
                     senderType == SenderType.CUSTOMER ? "customer" : "admin", request.ticketId);
                     
        } catch (Exception e) {
            // Log error but don't fail the transaction
            LOG.errorf(e, "Failed to send Kafka notification for chat message in ticket %d: %s", 
                      request.ticketId, e.getMessage());
        }

        return new MessageResponse(message);
    }

    public List<MessageResponse> getMessagesByTicketId(Long ticketId, Long userId) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        List<ChatMessage> messages = ChatMessage.findByTicketId(ticketId);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    public List<MessageResponse> getMessagesByTicketIdPaginated(Long ticketId, Long userId, int page, int size) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        List<ChatMessage> messages = ChatMessage.findByTicketIdPaginated(ticketId, page, size);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    public List<MessageResponse> getUnreadMessages(Long ticketId, Long userId) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        List<ChatMessage> messages = ChatMessage.findUnreadByTicketId(ticketId);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    public List<MessageResponse> getUnreadMessagesBySenderType(Long ticketId, Long userId, SenderType senderType) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        List<ChatMessage> messages = ChatMessage.findUnreadBySenderType(ticketId, senderType);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markMessagesAsRead(Long ticketId, Long userId) {
        LOG.infof("Marking messages as read for ticket %d by user %d", ticketId, userId);
        
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canMarkRead = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canMarkRead) {
            throw new SecurityException("User not authorized to mark messages as read for this ticket");
        }

        // Mark messages as read based on user type
        if (user.isAdmin()) {
            // Agent marking customer messages as read
            ChatMessage.markAllAsReadByTicketIdAndSenderType(ticketId, SenderType.CUSTOMER);
        } else {
            // Customer marking agent messages as read
            ChatMessage.markAllAsReadByTicketIdAndSenderType(ticketId, SenderType.AGENT);
        }

        LOG.infof("Marked messages as read for ticket %d", ticketId);
    }

    @Transactional
    public void markMessageAsRead(Long messageId, Long userId) {
        LOG.infof("Marking message %d as read by user %d", messageId, userId);
        
        ChatMessage message = ChatMessage.findById(messageId);
        if (message == null) {
            throw new NotFoundException("Message not found");
        }

        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(message.ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canMarkRead = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canMarkRead) {
            throw new SecurityException("User not authorized to mark this message as read");
        }

        // Only mark as read if the user is not the sender
        if (!message.senderId.equals(userId)) {
            message.markAsRead();
            message.persist();
            LOG.infof("Marked message %d as read", messageId);
        }
    }

    public MessageResponse getLatestMessage(Long ticketId, Long userId) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        ChatMessage latestMessage = ChatMessage.findLatestByTicketId(ticketId);
        if (latestMessage == null) {
            return null;
        }

        return new MessageResponse(latestMessage);
    }

    public List<MessageResponse> getRecentMessages(Long ticketId, Long userId, int minutes) {
        // Validate ticket exists and user can access it
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Ticket not found");
        }

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        boolean canView = user.isAdmin() || ticket.customerId.equals(userId);
        if (!canView) {
            throw new SecurityException("User not authorized to view messages for this ticket");
        }

        List<ChatMessage> messages = ChatMessage.findRecentMessages(ticketId, minutes);
        return messages.stream()
                .map(MessageResponse::new)
                .collect(Collectors.toList());
    }

    // Statistics methods
    public long getMessageCount(Long ticketId) {
        return ChatMessage.countByTicketId(ticketId);
    }

    public long getUnreadMessageCount(Long ticketId) {
        return ChatMessage.countUnreadByTicketId(ticketId);
    }

    public long getUnreadMessageCountBySenderType(Long ticketId, SenderType senderType) {
        return ChatMessage.countUnreadBySenderType(ticketId, senderType);
    }

    public long getMessageCountBySender(Long senderId) {
        return ChatMessage.countMessagesBySender(senderId);
    }

    // Helper methods
    private String sanitizeMessage(String message) {
        if (message == null) {
            return "";
        }
        
        // Basic sanitization - remove potentially harmful content
        String sanitized = message.trim();
        
        // Remove HTML tags for security
        sanitized = sanitized.replaceAll("<[^>]*>", "");
        
        // Limit message length
        if (sanitized.length() > 2000) {
            sanitized = sanitized.substring(0, 2000);
        }
        
        return sanitized;
    }

    private String truncateMessage(String message, int maxLength) {
        if (message == null) {
            return "";
        }
        
        if (message.length() <= maxLength) {
            return message;
        }
        
        return message.substring(0, maxLength - 3) + "...";
    }

    @Transactional
    public void updateTicketStatusOnMessage(SupportTicket ticket, SenderType senderType) {
        // If customer sends message to resolved ticket, reopen it
        if (senderType == SenderType.CUSTOMER && ticket.status == TicketStatus.RESOLVED) {
            ticket.reopen();
            ticket.persist();
            LOG.infof("Reopened ticket %d due to customer message", ticket.id);
        }
        
        // If ticket is open and agent sends message, mark as in progress
        if (senderType == SenderType.AGENT && ticket.status == TicketStatus.OPEN) {
            ticket.status = TicketStatus.IN_PROGRESS;
            ticket.persist();
            LOG.infof("Updated ticket %d status to IN_PROGRESS", ticket.id);
        }
    }

    public boolean validateMessageAccess(Long ticketId, Long userId) {
        SupportTicket ticket = SupportTicket.findById(ticketId);
        if (ticket == null) {
            return false;
        }

        User user = User.findById(userId);
        if (user == null) {
            return false;
        }

        return user.isAdmin() || ticket.customerId.equals(userId);
    }
}
