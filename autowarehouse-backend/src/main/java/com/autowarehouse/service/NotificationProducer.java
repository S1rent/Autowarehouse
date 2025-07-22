package com.autowarehouse.service;

import com.autowarehouse.dto.NotificationEvent;
import com.autowarehouse.dto.OrderEvent;
import com.autowarehouse.dto.CustomerServiceEvent;
import com.autowarehouse.entity.NotificationType;
import io.smallrye.reactive.messaging.annotations.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class NotificationProducer {

    private static final Logger LOG = Logger.getLogger(NotificationProducer.class);

    @Inject
    @Channel("notification-events")
    Emitter<NotificationEvent> notificationEmitter;

    @Inject
    @Channel("order-events")
    Emitter<OrderEvent> orderEmitter;

    @Inject
    @Channel("customer-service-events")
    Emitter<CustomerServiceEvent> customerServiceEmitter;

    // Order-related notification methods
    public CompletionStage<Void> sendOrderConfirmedNotification(Long orderId, Long customerId) {
        NotificationEvent event = new NotificationEvent(
            "orderConfirmed",
            customerId,
            "Order Confirmed",
            "Your order #" + orderId + " has been confirmed and is being processed.",
            NotificationType.ORDER_CONFIRMED,
            orderId,
            "ORDER",
            null
        );
        
        LOG.infof("Sending order confirmed notification for order %d to customer %d", orderId, customerId);
        return notificationEmitter.send(event);
    }

    public CompletionStage<Void> sendOrderShippedNotification(Long orderId, Long customerId) {
        NotificationEvent event = new NotificationEvent(
            "orderShipped",
            customerId,
            "Order Shipped",
            "Your order #" + orderId + " has been shipped and is on its way to you.",
            NotificationType.ORDER_SHIPPED,
            orderId,
            "ORDER",
            null
        );
        
        LOG.infof("Sending order shipped notification for order %d to customer %d", orderId, customerId);
        return notificationEmitter.send(event);
    }

    public CompletionStage<Void> sendOrderDeliveredNotification(Long orderId, Long customerId) {
        NotificationEvent event = new NotificationEvent(
            "orderDelivered",
            customerId,
            "Order Delivered",
            "Your order #" + orderId + " has been delivered successfully.",
            NotificationType.ORDER_DELIVERED,
            orderId,
            "ORDER",
            null
        );
        
        LOG.infof("Sending order delivered notification for order %d to customer %d", orderId, customerId);
        return notificationEmitter.send(event);
    }

    public CompletionStage<Void> sendOrderCancelledNotification(Long orderId, Long customerId) {
        NotificationEvent event = new NotificationEvent(
            "orderCancelled",
            customerId,
            "Order Cancelled",
            "Your order #" + orderId + " has been cancelled.",
            NotificationType.ORDER_CANCELLED,
            orderId,
            "ORDER",
            null
        );
        
        LOG.infof("Sending order cancelled notification for order %d to customer %d", orderId, customerId);
        return notificationEmitter.send(event);
    }

    public CompletionStage<Void> sendOrderRefundedNotification(Long orderId, Long customerId) {
        NotificationEvent event = new NotificationEvent(
            "orderRefunded",
            customerId,
            "Order Refunded",
            "Your order #" + orderId + " has been refunded. The amount will be credited to your account.",
            NotificationType.ORDER_REFUNDED,
            orderId,
            "ORDER",
            null
        );
        
        LOG.infof("Sending order refunded notification for order %d to customer %d", orderId, customerId);
        return notificationEmitter.send(event);
    }

    // Customer Service notification methods
    public CompletionStage<Void> sendCustomerServiceMessageNotification(Long chatId, Long customerId, String message) {
        NotificationEvent event = new NotificationEvent(
            "customerService",
            customerId,
            "New Message from Support",
            "You have received a new message from customer service: " + 
            (message.length() > 50 ? message.substring(0, 50) + "..." : message),
            NotificationType.CUSTOMER_SERVICE_MESSAGE,
            chatId,
            "CHAT",
            null
        );
        
        LOG.infof("Sending customer service message notification for chat %d to customer %d", chatId, customerId);
        return notificationEmitter.send(event);
    }

    public CompletionStage<Void> sendAdminCustomerServiceNotification(Long chatId, Long adminId, String customerName, String message) {
        NotificationEvent event = new NotificationEvent(
            "adminCustomerService",
            adminId,
            "New Customer Message",
            "New message from " + customerName + ": " + 
            (message.length() > 50 ? message.substring(0, 50) + "..." : message),
            NotificationType.ADMIN_CUSTOMER_SERVICE_MESSAGE,
            chatId,
            "CHAT",
            null
        );
        
        LOG.infof("Sending admin customer service notification for chat %d to admin %d", chatId, adminId);
        return notificationEmitter.send(event);
    }

    // Order event methods
    public CompletionStage<Void> sendOrderEvent(String eventType, Long orderId, Long userId, String orderStatus, String previousStatus) {
        OrderEvent event = new OrderEvent(eventType, orderId, userId, orderStatus, previousStatus, null);
        
        LOG.infof("Sending order event %s for order %d", eventType, orderId);
        return orderEmitter.send(event);
    }

    // Customer Service event methods
    public CompletionStage<Void> sendCustomerServiceEvent(String eventType, Long chatId, Long senderId, Long receiverId, 
                                                         String messageContent, String senderType) {
        CustomerServiceEvent event = new CustomerServiceEvent(eventType, chatId, senderId, receiverId, messageContent, senderType);
        
        LOG.infof("Sending customer service event %s for chat %d", eventType, chatId);
        return customerServiceEmitter.send(event);
    }

    // Generic notification method
    public CompletionStage<Void> sendNotification(NotificationEvent event) {
        LOG.infof("Sending generic notification event %s to user %d", event.getEventType(), event.getUserId());
        return notificationEmitter.send(event);
    }
}
