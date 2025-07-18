package com.autowarehouse.controller;

import com.autowarehouse.dto.MessageResponse;
import com.autowarehouse.dto.SendMessageRequest;
import com.autowarehouse.entity.SenderType;
import com.autowarehouse.service.ChatService;
import com.autowarehouse.service.WebSocketService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/api/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatController {

    private static final Logger LOG = Logger.getLogger(ChatController.class);

    @Inject
    ChatService chatService;

    @Inject
    WebSocketService webSocketService;

    @POST
    @Path("/messages")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response sendMessage(@Valid SendMessageRequest request, @Context SecurityContext securityContext) {
        LOG.infof("Sending message to ticket %d", request.ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            MessageResponse message = chatService.sendMessage(request, userId);
            
            // Broadcast message via WebSocket
            webSocketService.broadcastNewMessage(message);
            
            LOG.infof("Sent message %d to ticket %d", message.id, request.ticketId);
            return Response.status(Response.Status.CREATED).entity(message).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to send message to this ticket"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error sending message: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error sending message"))
                    .build();
        }
    }

    @GET
    @Path("/tickets/{ticketId}/messages")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getMessages(@PathParam("ticketId") Long ticketId,
                              @QueryParam("page") @DefaultValue("0") int page,
                              @QueryParam("size") @DefaultValue("50") int size,
                              @Context SecurityContext securityContext) {
        LOG.infof("Getting messages for ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            
            List<MessageResponse> messages;
            if (page > 0 || size != 50) {
                messages = chatService.getMessagesByTicketIdPaginated(ticketId, userId, page, size);
            } else {
                messages = chatService.getMessagesByTicketId(ticketId, userId);
            }
            
            return Response.ok(messages).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to view messages for this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting messages for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving messages"))
                    .build();
        }
    }

    @GET
    @Path("/tickets/{ticketId}/messages/unread")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getUnreadMessages(@PathParam("ticketId") Long ticketId,
                                    @QueryParam("senderType") String senderType,
                                    @Context SecurityContext securityContext) {
        LOG.infof("Getting unread messages for ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            
            List<MessageResponse> messages;
            if (senderType != null && !senderType.isEmpty()) {
                SenderType type = SenderType.valueOf(senderType.toUpperCase());
                messages = chatService.getUnreadMessagesBySenderType(ticketId, userId, type);
            } else {
                messages = chatService.getUnreadMessages(ticketId, userId);
            }
            
            return Response.ok(messages).build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid sender type"))
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to view messages for this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting unread messages for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving unread messages"))
                    .build();
        }
    }

    @POST
    @Path("/tickets/{ticketId}/messages/mark-read")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response markMessagesAsRead(@PathParam("ticketId") Long ticketId,
                                     @Context SecurityContext securityContext) {
        LOG.infof("Marking messages as read for ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            chatService.markMessagesAsRead(ticketId, userId);
            
            return Response.ok(new SuccessResponse("Messages marked as read")).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to mark messages as read for this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error marking messages as read for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error marking messages as read"))
                    .build();
        }
    }

    @POST
    @Path("/messages/{messageId}/mark-read")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response markMessageAsRead(@PathParam("messageId") Long messageId,
                                    @Context SecurityContext securityContext) {
        LOG.infof("Marking message %d as read", messageId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            chatService.markMessageAsRead(messageId, userId);
            
            return Response.ok(new SuccessResponse("Message marked as read")).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Message not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to mark this message as read"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error marking message %d as read: %s", messageId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error marking message as read"))
                    .build();
        }
    }

    @GET
    @Path("/tickets/{ticketId}/messages/latest")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getLatestMessage(@PathParam("ticketId") Long ticketId,
                                   @Context SecurityContext securityContext) {
        LOG.infof("Getting latest message for ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            MessageResponse message = chatService.getLatestMessage(ticketId, userId);
            
            if (message == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("No messages found for this ticket"))
                        .build();
            }
            
            return Response.ok(message).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to view messages for this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting latest message for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving latest message"))
                    .build();
        }
    }

    @GET
    @Path("/tickets/{ticketId}/messages/recent")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getRecentMessages(@PathParam("ticketId") Long ticketId,
                                    @QueryParam("minutes") @DefaultValue("30") int minutes,
                                    @Context SecurityContext securityContext) {
        LOG.infof("Getting recent messages for ticket %d (last %d minutes)", ticketId, minutes);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            List<MessageResponse> messages = chatService.getRecentMessages(ticketId, userId, minutes);
            
            return Response.ok(messages).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to view messages for this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting recent messages for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving recent messages"))
                    .build();
        }
    }

    // Statistics endpoints
    @GET
    @Path("/tickets/{ticketId}/stats/message-count")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getMessageCount(@PathParam("ticketId") Long ticketId) {
        try {
            long count = chatService.getMessageCount(ticketId);
            return Response.ok(new CountResponse(count)).build();
        } catch (Exception e) {
            LOG.errorf("Error getting message count for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving message count"))
                    .build();
        }
    }

    @GET
    @Path("/tickets/{ticketId}/stats/unread-count")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getUnreadMessageCount(@PathParam("ticketId") Long ticketId,
                                        @QueryParam("senderType") String senderType) {
        try {
            long count;
            if (senderType != null && !senderType.isEmpty()) {
                SenderType type = SenderType.valueOf(senderType.toUpperCase());
                count = chatService.getUnreadMessageCountBySenderType(ticketId, type);
            } else {
                count = chatService.getUnreadMessageCount(ticketId);
            }
            
            return Response.ok(new CountResponse(count)).build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid sender type"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting unread message count for ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving unread message count"))
                    .build();
        }
    }

    // Helper methods
    private Long getCurrentUserId(SecurityContext securityContext) {
        // TODO: Extract user ID from JWT token
        // For now, return a mock user ID
        String principal = securityContext.getUserPrincipal().getName();
        // This is a temporary implementation - in real app, extract from JWT
        return 1L; // Mock user ID
    }

    // Response DTOs
    public static class ErrorResponse {
        public String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    public static class SuccessResponse {
        public String message;
        
        public SuccessResponse(String message) {
            this.message = message;
        }
    }

    public static class CountResponse {
        public long count;
        
        public CountResponse(long count) {
            this.count = count;
        }
    }
}
