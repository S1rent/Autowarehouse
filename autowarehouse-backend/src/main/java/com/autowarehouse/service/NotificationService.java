package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    private static final Logger LOG = Logger.getLogger(NotificationService.class);

    @Inject
    WebSocketService webSocketService;

    // Original methods that other services depend on
    @Transactional
    public void createNotification(User user, String title, String message, NotificationType type, Long relatedId, String relatedType) {
        try {
            Notification notification = new Notification();
            notification.user = user;
            notification.title = title;
            notification.message = message;
            notification.type = type;
            notification.referenceId = relatedId;
            notification.referenceType = relatedType;
            notification.isRead = false;
            notification.createdAt = LocalDateTime.now();
            notification.persist();
            
            LOG.infof("Created notification for user %d: %s", user.id, title);
        } catch (Exception e) {
            LOG.errorf("Error creating notification: %s", e.getMessage());
        }
    }

    public void notifyOrderCreated(User user, Order order) {
        createNotification(user, "Order Created", 
            "Your order #" + order.id + " has been created successfully.", 
            NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
    }

    public void notifyOrderShipped(User user, Order order) {
        createNotification(user, "Order Shipped", 
            "Your order #" + order.id + " has been shipped.", 
            NotificationType.ORDER_SHIPPED, order.id, "ORDER");
    }

    public void notifyOrderDelivered(User user, Order order) {
        createNotification(user, "Order Delivered", 
            "Your order #" + order.id + " has been delivered.", 
            NotificationType.ORDER_DELIVERED, order.id, "ORDER");
    }

    public void notifyOrderConfirmed(User user, Order order) {
        createNotification(user, "Order Confirmed", 
            "Your order #" + order.id + " has been confirmed.", 
            NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
    }

    public void notifyOrderCancelled(User user, Order order) {
        createNotification(user, "Order Cancelled", 
            "Your order #" + order.id + " has been cancelled.", 
            NotificationType.ORDER_CANCELLED, order.id, "ORDER");
    }

    // Customer Service specific methods
    public void notifyNewTicket(SupportTicket ticket) {
        LOG.infof("Sending new ticket notification for ticket %d", ticket.id);
        
        try {
            // Notify all online agents about new ticket
            String message = String.format("New support ticket created: %s (Priority: %s)", 
                    ticket.subject, ticket.priority);
            
            // Find all online agents and send notification
            if (webSocketService != null) {
                webSocketService.getOnlineUsers().forEach(userId -> {
                    User user = User.findById(userId);
                    if (user != null && user.isAdmin()) {
                        webSocketService.sendNotification(userId, message);
                    }
                });
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending new ticket notification: %s", e.getMessage());
        }
    }

    public void notifyTicketUpdated(SupportTicket ticket, boolean statusChanged, boolean agentChanged) {
        LOG.infof("Sending ticket updated notification for ticket %d", ticket.id);
        
        try {
            String message = "Ticket updated: " + ticket.subject;
            
            if (statusChanged) {
                message += " (Status: " + ticket.status + ")";
            }
            
            if (agentChanged) {
                message += " (Agent assigned)";
            }
            
            if (webSocketService != null) {
                // Notify customer
                webSocketService.sendNotification(ticket.customerId, message);
                
                // Notify assigned agent if exists
                if (ticket.assignedAgentId != null) {
                    webSocketService.sendNotification(ticket.assignedAgentId, message);
                }
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending ticket updated notification: %s", e.getMessage());
        }
    }

    public void notifyAgentAssigned(SupportTicket ticket, User agent) {
        LOG.infof("Sending agent assigned notification for ticket %d", ticket.id);
        
        try {
            // Notify customer
            String customerMessage = String.format("Agent %s has been assigned to your ticket: %s", 
                    agent.getFullName(), ticket.subject);
            
            // Notify agent
            String agentMessage = String.format("You have been assigned to ticket: %s", ticket.subject);
            
            if (webSocketService != null) {
                webSocketService.sendNotification(ticket.customerId, customerMessage);
                webSocketService.sendNotification(agent.id, agentMessage);
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending agent assigned notification: %s", e.getMessage());
        }
    }

    public void notifyTicketResolved(SupportTicket ticket) {
        LOG.infof("Sending ticket resolved notification for ticket %d", ticket.id);
        
        try {
            String message = String.format("Your ticket has been resolved: %s", ticket.subject);
            if (webSocketService != null) {
                webSocketService.sendNotification(ticket.customerId, message);
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending ticket resolved notification: %s", e.getMessage());
        }
    }

    public void notifyTicketClosed(SupportTicket ticket) {
        LOG.infof("Sending ticket closed notification for ticket %d", ticket.id);
        
        try {
            String message = String.format("Ticket has been closed: %s", ticket.subject);
            
            if (webSocketService != null) {
                // Notify customer
                webSocketService.sendNotification(ticket.customerId, message);
                
                // Notify assigned agent if exists
                if (ticket.assignedAgentId != null) {
                    webSocketService.sendNotification(ticket.assignedAgentId, message);
                }
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending ticket closed notification: %s", e.getMessage());
        }
    }

    public void notifyNewMessage(Long ticketId, String senderName, String messagePreview) {
        LOG.infof("Sending new message notification for ticket %d", ticketId);
        
        try {
            String message = String.format("New message from %s: %s", senderName, messagePreview);
            
            if (webSocketService != null) {
                // Broadcast to all users in the ticket room
                webSocketService.getUsersInTicketRoom(ticketId).forEach(userId -> {
                    webSocketService.sendNotification(userId, message);
                });
            }
            
        } catch (Exception e) {
            LOG.errorf("Error sending new message notification: %s", e.getMessage());
        }
    }

    // Email notifications (placeholder for future implementation)
    public void sendEmailNotification(String email, String subject, String body) {
        LOG.infof("Email notification would be sent to %s with subject: %s", email, subject);
        // TODO: Implement email sending functionality
    }

    // SMS notifications (placeholder for future implementation)
    public void sendSMSNotification(String phoneNumber, String message) {
        LOG.infof("SMS notification would be sent to %s: %s", phoneNumber, message);
        // TODO: Implement SMS sending functionality
    }
}
