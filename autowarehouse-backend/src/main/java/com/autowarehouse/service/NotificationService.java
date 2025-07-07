package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NotificationService {

    @Transactional
    public Notification createNotification(User user, String title, String message, NotificationType type) {
        return createNotification(user, title, message, type, null, null);
    }

    @Transactional
    public Notification createNotification(User user, String title, String message, NotificationType type, 
                                         Long referenceId, String referenceType) {
        Notification notification = new Notification(user, title, message, type);
        
        if (referenceId != null && referenceType != null) {
            notification.setReference(referenceId, referenceType);
        }
        
        // Set default icon and action URL based on type
        notification.iconClass = type.getDefaultIcon();
        notification.actionUrl = generateActionUrl(type, referenceId, referenceType);
        
        notification.persist();
        return notification;
    }

    @Transactional
    public Notification createNotificationWithPriority(User user, String title, String message, 
                                                      NotificationType type, NotificationPriority priority,
                                                      Long referenceId, String referenceType) {
        Notification notification = createNotification(user, title, message, type, referenceId, referenceType);
        notification.priority = priority;
        notification.persist();
        return notification;
    }

    @Transactional
    public Notification createExpiringNotification(User user, String title, String message, 
                                                  NotificationType type, int expirationHours) {
        Notification notification = createNotification(user, title, message, type);
        notification.setExpiration(expirationHours);
        notification.persist();
        return notification;
    }

    public List<Notification> getUserNotifications(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return Notification.findByUser(user);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return Notification.findUnreadByUser(user);
    }

    public List<Notification> getNotificationsByType(Long userId, NotificationType type) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return Notification.findByUserAndType(user, type);
    }

    public long getUnreadCount(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            return 0;
        }
        return Notification.countUnreadByUser(user);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = Notification.findById(notificationId);
        if (notification != null) {
            notification.markAsRead();
            notification.persist();
        }
    }

    @Transactional
    public void markAsUnread(Long notificationId) {
        Notification notification = Notification.findById(notificationId);
        if (notification != null) {
            notification.markAsUnread();
            notification.persist();
        }
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Notification> unreadNotifications = Notification.findUnreadByUser(user);
        for (Notification notification : unreadNotifications) {
            notification.markAsRead();
            notification.persist();
        }
    }

    @Transactional
    public void deleteNotification(Long notificationId) {
        Notification notification = Notification.findById(notificationId);
        if (notification != null) {
            notification.delete();
        }
    }

    @Transactional
    public void deleteExpiredNotifications() {
        List<Notification> expiredNotifications = Notification.findExpiredNotifications();
        for (Notification notification : expiredNotifications) {
            notification.delete();
        }
    }

    @Transactional
    public void deleteUserNotifications(Long userId, NotificationType type) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<Notification> notifications = Notification.findByUserAndType(user, type);
        for (Notification notification : notifications) {
            notification.delete();
        }
    }

    // Bulk notification methods for admin
    @Transactional
    public void createBulkNotification(List<User> users, String title, String message, NotificationType type) {
        for (User user : users) {
            createNotification(user, title, message, type);
        }
    }

    @Transactional
    public void createSystemNotification(String title, String message, NotificationPriority priority) {
        List<User> activeUsers = User.findActiveUsers();
        for (User user : activeUsers) {
            createNotificationWithPriority(user, title, message, NotificationType.SYSTEM_MAINTENANCE, 
                                         priority, null, null);
        }
    }

    @Transactional
    public void createPromotionalNotification(String title, String message, Long referenceId, String referenceType) {
        List<User> customers = User.findByRole(UserRole.CUSTOMER);
        for (User customer : customers) {
            createNotification(customer, title, message, NotificationType.PROMOTIONAL, referenceId, referenceType);
        }
    }

    // Order-related notifications
    @Transactional
    public void notifyOrderConfirmed(User user, Order order) {
        createNotification(
            user,
            "Order Confirmed",
            "Your order #" + order.orderNumber + " has been confirmed and is being processed.",
            NotificationType.ORDER_CONFIRMED,
            order.id,
            "order"
        );
    }

    @Transactional
    public void notifyOrderShipped(User user, Order order) {
        String message = "Your order #" + order.orderNumber + " has been shipped";
        if (order.trackingNumber != null) {
            message += " with tracking number: " + order.trackingNumber;
        }
        
        createNotification(
            user,
            "Order Shipped",
            message,
            NotificationType.ORDER_SHIPPED,
            order.id,
            "order"
        );
    }

    @Transactional
    public void notifyOrderDelivered(User user, Order order) {
        createNotification(
            user,
            "Order Delivered",
            "Your order #" + order.orderNumber + " has been delivered successfully!",
            NotificationType.ORDER_DELIVERED,
            order.id,
            "order"
        );
    }

    @Transactional
    public void notifyOrderCancelled(User user, Order order) {
        createNotification(
            user,
            "Order Cancelled",
            "Your order #" + order.orderNumber + " has been cancelled.",
            NotificationType.ORDER_CANCELLED,
            order.id,
            "order"
        );
    }

    // Product-related notifications
    @Transactional
    public void notifyProductBackInStock(User user, Product product) {
        createNotification(
            user,
            "Product Back in Stock",
            "Good news! \"" + product.name + "\" is back in stock.",
            NotificationType.PRODUCT_BACK_IN_STOCK,
            product.id,
            "product"
        );
    }

    @Transactional
    public void notifyPriceDrop(User user, Product product, BigDecimal oldPrice, BigDecimal newPrice) {
        BigDecimal savings = oldPrice.subtract(newPrice);
        createNotification(
            user,
            "Price Drop Alert",
            "\"" + product.name + "\" price dropped by $" + savings + "! Now only $" + newPrice,
            NotificationType.PRODUCT_PRICE_DROP,
            product.id,
            "product"
        );
    }

    // Review-related notifications
    @Transactional
    public void notifyReviewApproved(User user, Review review) {
        createNotification(
            user,
            "Review Approved",
            "Your review for \"" + review.product.name + "\" has been approved and is now visible.",
            NotificationType.REVIEW_APPROVED,
            review.id,
            "review"
        );
    }

    @Transactional
    public void notifyReviewRejected(User user, Review review) {
        createNotification(
            user,
            "Review Rejected",
            "Your review for \"" + review.product.name + "\" was rejected and needs revision.",
            NotificationType.REVIEW_REJECTED,
            review.id,
            "review"
        );
    }

    private String generateActionUrl(NotificationType type, Long referenceId, String referenceType) {
        if (referenceId == null || referenceType == null) {
            return null;
        }

        switch (referenceType) {
            case "order":
                return "/orders/" + referenceId;
            case "auction":
                return "/auctions/" + referenceId;
            case "product":
                return "/products/" + referenceId;
            case "review":
                return "/reviews/" + referenceId;
            default:
                return null;
        }
    }
}
