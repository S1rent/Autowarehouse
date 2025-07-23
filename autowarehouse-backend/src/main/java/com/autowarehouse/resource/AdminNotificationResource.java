package com.autowarehouse.resource;

import com.autowarehouse.entity.Notification;
import com.autowarehouse.entity.NotificationType;
import com.autowarehouse.entity.NotificationPriority;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.NotificationService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.List;

@Path("/api/admin/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class AdminNotificationResource {

    private static final Logger LOG = Logger.getLogger(AdminNotificationResource.class);

    @Inject
    JsonWebToken jwt;

    @Inject
    NotificationService notificationService;

    @GET
    @Path("/all")
    public Response getAllNotifications(@Context SecurityContext ctx,
                                      @QueryParam("page") @DefaultValue("0") int page,
                                      @QueryParam("size") @DefaultValue("50") int size,
                                      @QueryParam("type") String type) {
        try {
            // First, get all admin user IDs
            List<User> adminUsers = User.find("role = 'ADMIN'").list();
            if (adminUsers.isEmpty()) {
                LOG.warn("No admin users found in the system");
                return Response.ok(new AdminNotificationListResponse(List.of(), 0, page, size)).build();
            }

            List<Long> adminUserIds = adminUsers.stream()
                .map(user -> user.id)
                .toList();

            List<Notification> notifications;
            long totalCount;
            
            if (type != null && !type.trim().isEmpty()) {
                String filterType = type.toLowerCase();
                
                if ("order".equals(filterType)) {
                    // Filter for all ORDER_ types
                    notifications = Notification.find(
                        "user.id in ?1 and (type = ?2 or type = ?3 or type = ?4 or type = ?5 or type = ?6) order by createdAt desc", 
                        adminUserIds, 
                        NotificationType.ORDER_CONFIRMED,
                        NotificationType.ORDER_SHIPPED,
                        NotificationType.ORDER_DELIVERED,
                        NotificationType.ORDER_CANCELLED,
                        NotificationType.ORDER_REFUNDED
                    ).page(page, size).list();
                    
                    totalCount = Notification.count(
                        "user.id in ?1 and (type = ?2 or type = ?3 or type = ?4 or type = ?5 or type = ?6)", 
                        adminUserIds,
                        NotificationType.ORDER_CONFIRMED,
                        NotificationType.ORDER_SHIPPED,
                        NotificationType.ORDER_DELIVERED,
                        NotificationType.ORDER_CANCELLED,
                        NotificationType.ORDER_REFUNDED
                    );
                } else if ("customerservice".equals(filterType)) {
                    // Filter for customer service types
                    notifications = Notification.find(
                        "user.id in ?1 and (type = ?2 or type = ?3) order by createdAt desc", 
                        adminUserIds,
                        NotificationType.CUSTOMER_SERVICE_MESSAGE,
                        NotificationType.ADMIN_CUSTOMER_SERVICE_MESSAGE
                    ).page(page, size).list();
                    
                    totalCount = Notification.count(
                        "user.id in ?1 and (type = ?2 or type = ?3)", 
                        adminUserIds,
                        NotificationType.CUSTOMER_SERVICE_MESSAGE,
                        NotificationType.ADMIN_CUSTOMER_SERVICE_MESSAGE
                    );
                } else {
                    // Try to match exact type
                    try {
                        NotificationType notificationType = NotificationType.valueOf(type.toUpperCase());
                        notifications = Notification.find(
                            "user.id in ?1 and type = ?2 order by createdAt desc", 
                            adminUserIds, notificationType
                        ).page(page, size).list();
                        
                        totalCount = Notification.count(
                            "user.id in ?1 and type = ?2", 
                            adminUserIds, notificationType
                        );
                    } catch (IllegalArgumentException e) {
                        LOG.warnf("Invalid notification type: %s, fetching all admin notifications", type);
                        // Query all notifications for admin users
                        notifications = Notification.find(
                            "user.id in ?1 order by createdAt desc", 
                            adminUserIds
                        ).page(page, size).list();
                        
                        totalCount = Notification.count("user.id in ?1", adminUserIds);
                    }
                }
            } else {
                // Query all notifications for admin users
                notifications = Notification.find(
                    "user.id in ?1 order by createdAt desc", 
                    adminUserIds
                ).page(page, size).list();
                
                totalCount = Notification.count("user.id in ?1", adminUserIds);
            }

            // Remove duplicates based on title and message combination
            List<AdminNotificationResponse> notificationResponses = notifications.stream()
                .map(AdminNotificationResponse::new)
                .collect(java.util.stream.Collectors.toMap(
                    // Key: combination of title and message to identify duplicates
                    notif -> notif.title + "|" + notif.message,
                    // Value: the notification response
                    notif -> notif,
                    // Merge function: keep the most recent one (first in the sorted list)
                    (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((a, b) -> b.createdAt.compareTo(a.createdAt)) // Sort by creation time descending
                .toList();

            LOG.infof("Found %d admin notifications (after removing duplicates: %d)", 
                notifications.size(), notificationResponses.size());

            AdminNotificationListResponse response = new AdminNotificationListResponse(
                notificationResponses, 
                totalCount, 
                page, 
                size
            );

            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching admin notifications");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching notifications: " + e.getMessage())).build();
        }
    }

    @GET
    @Path("/stats")
    public Response getNotificationStats(@Context SecurityContext ctx) {
        try {
            // First, get all admin user IDs
            List<User> adminUsers = User.find("role = 'ADMIN'").list();
            if (adminUsers.isEmpty()) {
                LOG.warn("No admin users found in the system");
                AdminNotificationStatsResponse stats = new AdminNotificationStatsResponse(0, 0, 0, 0, 0);
                return Response.ok(stats).build();
            }

            List<Long> adminUserIds = adminUsers.stream()
                .map(user -> user.id)
                .toList();

            // Count only notifications for admin users
            long totalCount = Notification.count("user.id in ?1", adminUserIds);
            long readCount = Notification.count("user.id in ?1 and isRead = true", adminUserIds);
            long unreadCount = totalCount - readCount;
            
            // Get today's notifications for admin users
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            long todayCount = Notification.count("user.id in ?1 and createdAt >= ?2", adminUserIds, startOfDay);
            
            // Get this week's notifications for admin users
            LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
            long weekCount = Notification.count("user.id in ?1 and createdAt >= ?2", adminUserIds, startOfWeek);

            LOG.infof("Admin notification stats - Total: %d, Read: %d, Unread: %d, Today: %d, Week: %d", 
                totalCount, readCount, unreadCount, todayCount, weekCount);

            AdminNotificationStatsResponse stats = new AdminNotificationStatsResponse(
                totalCount, readCount, unreadCount, todayCount, weekCount
            );

            return Response.ok(stats).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching notification stats");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching notification stats: " + e.getMessage())).build();
        }
    }

    @POST
    @Path("/broadcast")
    @Transactional
    public Response broadcastNotification(@Context SecurityContext ctx, BroadcastNotificationRequest request) {
        try {
            // Get admin user
            Object userIdClaim = jwt.getClaim("userId");
            Long adminUserId = userIdClaim instanceof Number ? ((Number) userIdClaim).longValue() : null;
            if (adminUserId == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Invalid admin user ID")).build();
            }

            User adminUser = User.findById(adminUserId);
            if (adminUser == null || !"ADMIN".equals(adminUser.role)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Admin access required")).build();
            }

            // Get target users based on audience
            List<User> targetUsers = getTargetUsers(request.audience);
            
            if (targetUsers.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("No target users found")).build();
            }

            // Create notifications for all target users
            int createdCount = 0;
            for (User user : targetUsers) {
                try {
                    Notification notification = new Notification();
                    notification.user = user;
                    notification.title = request.title;
                    notification.message = request.message;
                    notification.type = NotificationType.valueOf(request.type.toUpperCase());
                    notification.priority = NotificationPriority.valueOf(request.priority != null ? 
                        request.priority.toUpperCase() : "NORMAL");
                    notification.isRead = false;
                    notification.createdAt = LocalDateTime.now();
                    notification.persist();
                    
                    createdCount++;
                } catch (Exception e) {
                    LOG.errorf(e, "Failed to create notification for user %d", user.id);
                }
            }

            BroadcastNotificationResponse response = new BroadcastNotificationResponse(
                "Broadcast notification sent successfully",
                createdCount,
                targetUsers.size()
            );

            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error broadcasting notification");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error broadcasting notification: " + e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteNotification(@PathParam("id") Long notificationId, @Context SecurityContext ctx) {
        try {
            Notification notification = Notification.findById(notificationId);
            if (notification == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Notification not found")).build();
            }

            notification.delete();

            return Response.ok(new MessageResponse("Notification deleted successfully")).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error deleting notification");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error deleting notification: " + e.getMessage())).build();
        }
    }

    private List<User> getTargetUsers(String audience) {
        return switch (audience.toLowerCase()) {
            case "all" -> User.listAll();
            case "active" -> User.find("lastLoginAt >= ?1", LocalDateTime.now().minusDays(30)).list();
            case "premium" -> User.find("role = 'PREMIUM'").list(); // If you have premium users
            case "customers" -> User.find("role = 'CUSTOMER'").list();
            case "admins" -> User.find("role = 'ADMIN'").list();
            default -> User.find("role = 'CUSTOMER'").list(); // Default to customers
        };
    }

    // Response DTOs
    public static class AdminNotificationResponse {
        public Long id;
        public String title;
        public String message;
        public String type;
        public String priority;
        public boolean isRead;
        public String createdAt;
        public UserInfo user;

        public AdminNotificationResponse() {}

        public AdminNotificationResponse(Notification notification) {
            this.id = notification.id;
            this.title = notification.title;
            this.message = notification.message;
            this.type = notification.type != null ? notification.type.getValue() : null;
            this.priority = notification.priority != null ? notification.priority.name() : "NORMAL";
            this.isRead = notification.isRead;
            this.createdAt = notification.createdAt != null ? notification.createdAt.toString() : null;
            this.user = notification.user != null ? new UserInfo(notification.user) : null;
        }

        public static class UserInfo {
            public Long id;
            public String email;
            public String fullName;
            public String role;

            public UserInfo() {}

            public UserInfo(User user) {
                this.id = user.id;
                this.email = user.email;
                this.fullName = user.getFullName();
                this.role = user.role;
            }
        }
    }

    public static class AdminNotificationListResponse {
        public List<AdminNotificationResponse> notifications;
        public long totalCount;
        public int currentPage;
        public int pageSize;
        public int totalPages;

        public AdminNotificationListResponse() {}

        public AdminNotificationListResponse(List<AdminNotificationResponse> notifications, long totalCount, int currentPage, int pageSize) {
            this.notifications = notifications;
            this.totalCount = totalCount;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPages = pageSize > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;
        }
    }

    public static class AdminNotificationStatsResponse {
        public long totalCount;
        public long readCount;
        public long unreadCount;
        public long todayCount;
        public long weekCount;
        public double readPercentage;

        public AdminNotificationStatsResponse() {}

        public AdminNotificationStatsResponse(long totalCount, long readCount, long unreadCount, long todayCount, long weekCount) {
            this.totalCount = totalCount;
            this.readCount = readCount;
            this.unreadCount = unreadCount;
            this.todayCount = todayCount;
            this.weekCount = weekCount;
            this.readPercentage = totalCount > 0 ? Math.round((double) readCount / totalCount * 100.0 * 100.0) / 100.0 : 0.0;
        }
    }

    public static class BroadcastNotificationRequest {
        public String type;
        public String audience;
        public String title;
        public String message;
        public String priority;
    }

    public static class BroadcastNotificationResponse {
        public String message;
        public int successCount;
        public int totalTargets;

        public BroadcastNotificationResponse() {}

        public BroadcastNotificationResponse(String message, int successCount, int totalTargets) {
            this.message = message;
            this.successCount = successCount;
            this.totalTargets = totalTargets;
        }
    }

    public static class MessageResponse {
        public String message;
        
        public MessageResponse() {}
        
        public MessageResponse(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponse {
        public String error;
        
        public ErrorResponse() {}
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
