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

    public void notifyOrderCreated(User customer, Order order) {
        // Notify customer
        createNotification(customer, "Order Created", 
            "Your order #" + order.id + " has been created successfully.", 
            NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
        
        // Notify all admins to process the order
        notifyAdminsForOrderProcessing(order, "New Order", 
            "Please process order #" + order.id + " from " + customer.getFullName());
    }

    public void notifyOrderConfirmed(User customer, Order order) {
        notifyOrderConfirmed(customer, order, null);
    }

    public void notifyOrderConfirmed(User customer, Order order, User processedByAdmin) {
        // Notify customer
        createNotification(customer, "Order Confirmed", 
            "Your order #" + order.id + " has been confirmed and is being processed.", 
            NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
        
        // Don't notify admin if they processed it themselves
        if (processedByAdmin == null) {
            notifyAdminsForOrderProcessing(order, "Order Confirmed", 
                "Order #" + order.id + " has been confirmed and needs processing");
        }
    }

    public void notifyOrderShipped(User customer, Order order) {
        notifyOrderShipped(customer, order, null);
    }

    public void notifyOrderShipped(User customer, Order order, User processedByAdmin) {
        // Notify customer
        createNotification(customer, "Order Shipped", 
            "Your order #" + order.id + " has been shipped and is on the way.", 
            NotificationType.ORDER_SHIPPED, order.id, "ORDER");
        
        // Don't notify admin if they processed it themselves
        if (processedByAdmin == null) {
            notifyAdminsForOrderProcessing(order, "Order Shipped", 
                "Order #" + order.id + " has been shipped");
        }
    }

    public void notifyOrderDelivered(User customer, Order order) {
        notifyOrderDelivered(customer, order, null);
    }

    public void notifyOrderDelivered(User customer, Order order, User processedByAdmin) {
        // Notify customer
        createNotification(customer, "Order Delivered", 
            "Your order #" + order.id + " has been delivered successfully.", 
            NotificationType.ORDER_DELIVERED, order.id, "ORDER");
        
        // Don't notify admin if they processed it themselves
        if (processedByAdmin == null) {
            notifyAdminsForOrderProcessing(order, "Order Delivered", 
                "Order #" + order.id + " has been delivered");
        }
    }

    public void notifyOrderCancelled(User customer, Order order) {
        notifyOrderCancelled(customer, order, null);
    }

    public void notifyOrderCancelled(User customer, Order order, User processedByAdmin) {
        // Notify customer
        createNotification(customer, "Order Cancelled", 
            "Your order #" + order.id + " has been cancelled.", 
            NotificationType.ORDER_CANCELLED, order.id, "ORDER");
        
        // Don't notify admin if they processed it themselves
        if (processedByAdmin == null) {
            notifyAdminsForOrderProcessing(order, "Order Cancelled", 
                "Order #" + order.id + " has been cancelled");
        }
    }

    public void notifyOrderStatusChanged(User customer, Order order, String fromStatus, String toStatus, User processedByAdmin) {
        // Notify customer about status change
        createNotification(customer, "Order Status Updated", 
            "Your order #" + order.id + " has been moved from " + fromStatus + " to " + toStatus + ".", 
            NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
        
        // Don't notify admin if they processed it themselves
        if (processedByAdmin == null) {
            notifyAdminsForOrderProcessing(order, "Order Status Changed", 
                "Order #" + order.id + " status changed from " + fromStatus + " to " + toStatus);
        }
    }

    private void notifyAdminsForOrderProcessing(Order order, String title, String message) {
        try {
            // Get all admin users
            List<User> adminUsers = User.find("role = 'ADMIN'").list();
            
            for (User admin : adminUsers) {
                createNotification(admin, title, message, 
                    NotificationType.ORDER_CONFIRMED, order.id, "ORDER");
            }
            
            LOG.infof("Notified %d admins about order %d", adminUsers.size(), order.id);
        } catch (Exception e) {
            LOG.errorf("Error notifying admins about order %d: %s", order.id, e.getMessage());
        }
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
