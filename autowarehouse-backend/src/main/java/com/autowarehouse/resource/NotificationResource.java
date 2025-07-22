package com.autowarehouse.resource;

import com.autowarehouse.entity.Notification;
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

import java.util.List;

@Path("/api/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificationResource {

    private static final Logger LOG = Logger.getLogger(NotificationResource.class);

    @Inject
    JsonWebToken jwt;

    @Inject
    NotificationService notificationService;

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUserNotifications(@Context SecurityContext ctx,
                                       @QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("20") int size,
                                       @QueryParam("type") String type,
                                       @QueryParam("unreadOnly") @DefaultValue("false") boolean unreadOnly) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            List<Notification> notifications;
            
            if (unreadOnly) {
                notifications = Notification.findUnreadByUser(user);
            } else if (type != null && !type.trim().isEmpty()) {
                notifications = Notification.findByUserAndType(user, type);
            } else {
                notifications = Notification.findByUserPaginated(user, page, size);
            }

            // Convert to response DTOs
            List<NotificationResponse> notificationResponses = notifications.stream()
                .map(NotificationResponse::new)
                .toList();

            long totalCount = unreadOnly ? 
                Notification.countUnreadByUser(user) : 
                Notification.countByUser(user);

            NotificationListResponse response = new NotificationListResponse(
                notificationResponses, 
                totalCount, 
                page, 
                size
            );

            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching notifications for user %d", userId);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching notifications: " + e.getMessage())).build();
        }
    }

    @GET
    @Path("/unread")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUnreadNotifications(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            List<Notification> notifications = Notification.findUnreadByUser(user);
            List<NotificationResponse> notificationResponses = notifications.stream()
                .map(NotificationResponse::new)
                .toList();

            return Response.ok(notificationResponses).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching unread notifications");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching unread notifications: " + e.getMessage())).build();
        }
    }

    @GET
    @Path("/count/unread")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getUnreadCount(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            long count = Notification.countUnreadByUser(user);
            return Response.ok(new UnreadCountResponse(count)).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching unread count");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching unread count: " + e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}/read")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response markAsRead(@PathParam("id") Long notificationId, @Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            Notification notification = Notification.findById(notificationId);
            if (notification == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Notification not found")).build();
            }

            if (!notification.user.id.equals(userId)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to modify this notification")).build();
            }

            notification.markAsRead();
            notification.persist();

            return Response.ok(new NotificationResponse(notification)).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error marking notification as read");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error marking notification as read: " + e.getMessage())).build();
        }
    }

    @PUT
    @Path("/read-all")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response markAllAsRead(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            List<Notification> notifications = Notification.findUnreadByUser(user);
            for (Notification notification : notifications) {
                notification.markAsRead();
                notification.persist();
            }

            return Response.ok(new MessageResponse("All notifications marked as read")).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error marking all notifications as read");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error marking all notifications as read: " + e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response deleteNotification(@PathParam("id") Long notificationId, @Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            Notification notification = Notification.findById(notificationId);
            if (notification == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Notification not found")).build();
            }

            if (!notification.user.id.equals(userId)) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to delete this notification")).build();
            }

            notification.delete();

            return Response.ok(new MessageResponse("Notification deleted")).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error deleting notification");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error deleting notification: " + e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/clear-all")
    @RolesAllowed({"USER", "ADMIN"})
    @Transactional
    public Response clearAllNotifications(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            List<Notification> notifications = Notification.findByUser(user);
            for (Notification notification : notifications) {
                notification.delete();
            }

            return Response.ok(new MessageResponse("All notifications cleared")).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error clearing all notifications");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error clearing all notifications: " + e.getMessage())).build();
        }
    }

    @GET
    @Path("/stats")
    @RolesAllowed({"USER", "ADMIN"})
    public Response getNotificationStats(@Context SecurityContext ctx) {
        try {
            Long userId = Long.parseLong(jwt.getSubject());
            User user = User.findById(userId);
            
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found")).build();
            }

            long totalCount = Notification.countByUser(user);
            long unreadCount = Notification.countUnreadByUser(user);
            long readCount = totalCount - unreadCount;

            NotificationStatsResponse stats = new NotificationStatsResponse(
                totalCount, unreadCount, readCount
            );

            return Response.ok(stats).build();
            
        } catch (Exception e) {
            LOG.errorf(e, "Error fetching notification stats");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error fetching notification stats: " + e.getMessage())).build();
        }
    }

    // Response DTOs
    public static class NotificationResponse {
        public Long id;
        public String title;
        public String message;
        public String type;
        public boolean isRead;
        public Long referenceId;
        public String referenceType;
        public String iconClass;
        public String priority;
        public String createdAt;
        public UserInfo user;

        public NotificationResponse() {}

        public NotificationResponse(Notification notification) {
            this.id = notification.id;
            this.title = notification.title;
            this.message = notification.message;
            this.type = notification.type != null ? notification.type.getValue() : null;
            this.isRead = notification.isRead;
            this.referenceId = notification.referenceId;
            this.referenceType = notification.referenceType;
            this.iconClass = notification.type != null ? notification.type.getDefaultIcon() : "fas fa-bell";
            this.priority = notification.priority != null ? notification.priority : "NORMAL";
            this.createdAt = notification.createdAt != null ? notification.createdAt.toString() : null;
            this.user = notification.user != null ? new UserInfo(notification.user) : null;
        }

        public static class UserInfo {
            public Long id;
            public String email;
            public String fullName;

            public UserInfo() {}

            public UserInfo(User user) {
                this.id = user.id;
                this.email = user.email;
                this.fullName = user.getFullName();
            }
        }
    }

    public static class NotificationListResponse {
        public List<NotificationResponse> notifications;
        public long totalCount;
        public int currentPage;
        public int pageSize;
        public int totalPages;

        public NotificationListResponse() {}

        public NotificationListResponse(List<NotificationResponse> notifications, long totalCount, int currentPage, int pageSize) {
            this.notifications = notifications;
            this.totalCount = totalCount;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalPages = pageSize > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;
        }
    }

    public static class UnreadCountResponse {
        public long count;
        
        public UnreadCountResponse() {}
        
        public UnreadCountResponse(long count) {
            this.count = count;
        }
    }

    public static class NotificationStatsResponse {
        public long totalCount;
        public long unreadCount;
        public long readCount;

        public NotificationStatsResponse() {}

        public NotificationStatsResponse(long totalCount, long unreadCount, long readCount) {
            this.totalCount = totalCount;
            this.unreadCount = unreadCount;
            this.readCount = readCount;
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
