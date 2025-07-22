package com.autowarehouse.service;

import com.autowarehouse.dto.NotificationEvent;
import com.autowarehouse.dto.OrderEvent;
import com.autowarehouse.dto.CustomerServiceEvent;
import com.autowarehouse.entity.Notification;
import com.autowarehouse.entity.User;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
public class NotificationConsumer {

    private static final Logger LOG = Logger.getLogger(NotificationConsumer.class);

    @Inject
    NotificationService notificationService;

    @Inject
    WebSocketNotificationService webSocketService;

    @Incoming("notification-events-consumer")
    @Transactional
    public void processNotificationEvent(NotificationEvent event) {
        try {
            LOG.infof("Processing notification event: %s for user %d", event.getEventType(), event.getUserId());

            // Find the user
            User user = User.findById(event.getUserId());
            if (user == null) {
                LOG.warnf("User not found for notification event: %d", event.getUserId());
                return;
            }

            // Create notification entity
            Notification notification = new Notification();
            notification.user = user;
            notification.title = event.getTitle();
            notification.message = event.getMessage();
            notification.type = event.getNotificationType();
            notification.referenceId = event.getReferenceId();
            notification.referenceType = event.getReferenceType();
            notification.isRead = false;
            notification.createdAt = event.getTimestamp() != null ? event.getTimestamp() : LocalDateTime.now();

            // Save to database
            notification.persist();

            LOG.infof("Notification saved to database with ID: %d", notification.id);

            // Send real-time notification via WebSocket
            try {
                webSocketService.sendNotificationToUser(event.getUserId(), notification);
                LOG.infof("Real-time notification sent to user %d", event.getUserId());
            } catch (Exception e) {
                LOG.errorf(e, "Failed to send real-time notification to user %d", event.getUserId());
            }

            // Note: Push notifications can be added here if FCM is configured
            // For now, we rely on WebSocket for real-time notifications

        } catch (Exception e) {
            LOG.errorf(e, "Error processing notification event: %s", event);
            throw e; // Re-throw to trigger Kafka retry mechanism
        }
    }

    @Incoming("order-events-consumer")
    @Transactional
    public void processOrderEvent(OrderEvent event) {
        try {
            LOG.infof("Processing order event: %s for order %d", event.getEventType(), event.getOrderId());

            // Handle different order event types
            switch (event.getEventType()) {
                case "orderStatusChanged":
                    handleOrderStatusChanged(event);
                    break;
                case "orderCreated":
                    handleOrderCreated(event);
                    break;
                default:
                    LOG.infof("Unhandled order event type: %s", event.getEventType());
            }

        } catch (Exception e) {
            LOG.errorf(e, "Error processing order event: %s", event);
            throw e;
        }
    }

    @Incoming("customer-service-events-consumer")
    @Transactional
    public void processCustomerServiceEvent(CustomerServiceEvent event) {
        try {
            LOG.infof("Processing customer service event: %s for chat %d", event.getEventType(), event.getChatId());

            // Handle different customer service event types
            switch (event.getEventType()) {
                case "messageReceived":
                    handleMessageReceived(event);
                    break;
                case "chatCreated":
                    handleChatCreated(event);
                    break;
                default:
                    LOG.infof("Unhandled customer service event type: %s", event.getEventType());
            }

        } catch (Exception e) {
            LOG.errorf(e, "Error processing customer service event: %s", event);
            throw e;
        }
    }

    private void handleOrderStatusChanged(OrderEvent event) {
        LOG.infof("Handling order status change: Order %d changed from %s to %s", 
                 event.getOrderId(), event.getPreviousStatus(), event.getOrderStatus());
        
        // Additional business logic for order status changes can be added here
        // For example: updating inventory, sending emails, etc.
    }

    private void handleOrderCreated(OrderEvent event) {
        LOG.infof("Handling new order creation: Order %d created by user %d", 
                 event.getOrderId(), event.getUserId());
        
        // Additional business logic for new orders can be added here
        // For example: inventory reservation, payment processing, etc.
    }

    private void handleMessageReceived(CustomerServiceEvent event) {
        LOG.infof("Handling message received: Chat %d, from %s to %s", 
                 event.getChatId(), event.getSenderId(), event.getReceiverId());
        
        // Additional business logic for messages can be added here
        // For example: auto-responses, escalation rules, etc.
    }

    private void handleChatCreated(CustomerServiceEvent event) {
        LOG.infof("Handling chat creation: Chat %d created between %s and %s", 
                 event.getChatId(), event.getSenderId(), event.getReceiverId());
        
        // Additional business logic for new chats can be added here
        // For example: assigning to available agents, setting priorities, etc.
    }
}
