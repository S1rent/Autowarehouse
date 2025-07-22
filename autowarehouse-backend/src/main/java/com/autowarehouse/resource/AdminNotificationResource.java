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
            List<Notification> notifications;
            
            if (type != null && !type.trim().isEmpty()) {
                try {
                    NotificationType notificationType = NotificationType.valueOf(type.toUpperCase());
                    notifications = Notification.find("type = ?1 order by createdAt desc", notificationType)
                        .page(page, size).list();
                } catch (IllegalArgumentException e) {
                    notifications = Notification.find("order by createdAt desc")
                        .page(page, size).list();
                }
            } else {
                notifications = Notification.find("order by createdAt desc")
                    .page(page, size).list();
            }

            // Convert to response DTOs
            List<AdminNotificationResponse> notificationResponses = notifications.stream()
                .map(AdminNotificationResponse::new)
                .toList();

            long totalCount = type != null && !type.trim().isEmpty() ? 
                Notification.count("type = ?1", NotificationType.valueOf(type.toUpperCase())) : 
                Notification.count();

            AdminNotificationListResponse response = new AdminNotificationListResponse(
                notificationResponses, 
                totalCount, 
                page, 
                size
            );

            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching all notifications for admin");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching notifications: " + e.getMessage())).build();
        }
    }

    @GET
    @Path("/stats")
    public Response getNotificationStats(@Context SecurityContext ctx) {
        try {
            long totalCount = Notification.count();
            long readCount = Notification.count("isRead = true");
            long unreadCount = totalCount - readCount;
            
            // Get today's notifications
            LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
            long todayCount = Notification.count("createdAt >= ?1", startOfDay);
            
            // Get this week's notifications
            LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
            long weekCount = Notification.count("createdAt >= ?1", startOfWeek);

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
