package com.autowarehouse.controller;

import com.autowarehouse.dto.CreateTicketRequest;
import com.autowarehouse.dto.TicketResponse;
import com.autowarehouse.dto.UpdateTicketRequest;
import com.autowarehouse.entity.TicketStatus;
import com.autowarehouse.service.SupportTicketService;
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

@Path("/api/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SupportTicketController {

    private static final Logger LOG = Logger.getLogger(SupportTicketController.class);

    @Inject
    SupportTicketService supportTicketService;

    @Inject
    WebSocketService webSocketService;

    @POST
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response createTicket(@Valid CreateTicketRequest request, @Context SecurityContext securityContext) {
        LOG.infof("Creating ticket with subject: %s", request.subject);
        
        try {
            // Get current user ID (this would come from JWT token in real implementation)
            Long userId = getCurrentUserId(securityContext);
            
            TicketResponse ticket = supportTicketService.createTicket(request, userId);
            
            // Broadcast ticket creation via WebSocket
            webSocketService.broadcastTicketCreated(ticket);
            
            LOG.infof("Created ticket %d", ticket.id);
            return Response.status(Response.Status.CREATED).entity(ticket).build();
            
        } catch (Exception e) {
            LOG.errorf("Error creating ticket: %s", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error creating ticket: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{ticketId}")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getTicket(@PathParam("ticketId") Long ticketId, @Context SecurityContext securityContext) {
        LOG.infof("Getting ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            TicketResponse ticket = supportTicketService.getTicket(ticketId, userId);
            
            return Response.ok(ticket).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to view this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving ticket"))
                    .build();
        }
    }

    @PUT
    @Path("/{ticketId}")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response updateTicket(@PathParam("ticketId") Long ticketId, 
                               @Valid UpdateTicketRequest request, 
                               @Context SecurityContext securityContext) {
        LOG.infof("Updating ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            TicketResponse ticket = supportTicketService.updateTicket(ticketId, request, userId);
            
            // Broadcast ticket update via WebSocket
            webSocketService.broadcastTicketUpdated(ticket);
            
            LOG.infof("Updated ticket %d", ticketId);
            return Response.ok(ticket).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to update this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error updating ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error updating ticket: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/my-tickets")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response getMyTickets(@Context SecurityContext securityContext) {
        LOG.info("Getting tickets for current user");
        
        try {
            Long userId = getCurrentUserId(securityContext);
            List<TicketResponse> tickets = supportTicketService.getTicketsByCustomer(userId);
            
            return Response.ok(tickets).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting user tickets: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving tickets"))
                    .build();
        }
    }

    @GET
    @RolesAllowed("ADMIN")
    public Response getAllTickets(@QueryParam("status") String status) {
        LOG.info("Getting all tickets");
        
        try {
            List<TicketResponse> tickets;
            
            if (status != null && !status.isEmpty()) {
                TicketStatus ticketStatus = TicketStatus.valueOf(status.toUpperCase());
                tickets = supportTicketService.getTicketsByStatus(ticketStatus);
            } else {
                tickets = supportTicketService.getAllTickets();
            }
            
            return Response.ok(tickets).build();
            
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid status value"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting all tickets: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving tickets"))
                    .build();
        }
    }

    @GET
    @Path("/assigned-to-me")
    @RolesAllowed("ADMIN")
    public Response getAssignedTickets(@Context SecurityContext securityContext) {
        LOG.info("Getting tickets assigned to current agent");
        
        try {
            Long userId = getCurrentUserId(securityContext);
            List<TicketResponse> tickets = supportTicketService.getTicketsByAgent(userId);
            
            return Response.ok(tickets).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting assigned tickets: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving assigned tickets"))
                    .build();
        }
    }

    @GET
    @Path("/unassigned")
    @RolesAllowed("ADMIN")
    public Response getUnassignedTickets() {
        LOG.info("Getting unassigned tickets");
        
        try {
            List<TicketResponse> tickets = supportTicketService.getUnassignedTickets();
            return Response.ok(tickets).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting unassigned tickets: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving unassigned tickets"))
                    .build();
        }
    }

    @POST
    @Path("/{ticketId}/assign/{agentId}")
    @RolesAllowed("ADMIN")
    public Response assignTicket(@PathParam("ticketId") Long ticketId, 
                               @PathParam("agentId") Long agentId) {
        LOG.infof("Assigning ticket %d to agent %d", ticketId, agentId);
        
        try {
            TicketResponse ticket = supportTicketService.assignTicket(ticketId, agentId);
            
            // Broadcast agent assignment via WebSocket
            webSocketService.broadcastAgentAssigned(ticketId, agentId, ticket.assignedAgentName);
            
            LOG.infof("Assigned ticket %d to agent %d", ticketId, agentId);
            return Response.ok(ticket).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket or agent not found"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error assigning ticket %d to agent %d: %s", ticketId, agentId, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error assigning ticket: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{ticketId}/resolve")
    @RolesAllowed("ADMIN")
    public Response resolveTicket(@PathParam("ticketId") Long ticketId, 
                                @Context SecurityContext securityContext) {
        LOG.infof("Resolving ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            TicketResponse ticket = supportTicketService.resolveTicket(ticketId, userId);
            
            // Broadcast ticket update via WebSocket
            webSocketService.broadcastTicketUpdated(ticket);
            
            LOG.infof("Resolved ticket %d", ticketId);
            return Response.ok(ticket).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to resolve this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error resolving ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error resolving ticket: " + e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{ticketId}/close")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response closeTicket(@PathParam("ticketId") Long ticketId, 
                              @Context SecurityContext securityContext) {
        LOG.infof("Closing ticket %d", ticketId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            TicketResponse ticket = supportTicketService.closeTicket(ticketId, userId);
            
            // Broadcast ticket update via WebSocket
            webSocketService.broadcastTicketUpdated(ticket);
            
            LOG.infof("Closed ticket %d", ticketId);
            return Response.ok(ticket).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Ticket not found"))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("Not authorized to close this ticket"))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error closing ticket %d: %s", ticketId, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Error closing ticket: " + e.getMessage()))
                    .build();
        }
    }

    // Statistics endpoints
    @GET
    @Path("/stats/open-count")
    @RolesAllowed("ADMIN")
    public Response getOpenTicketCount() {
        try {
            long count = supportTicketService.getOpenTicketCount();
            return Response.ok(new CountResponse(count)).build();
        } catch (Exception e) {
            LOG.errorf("Error getting open ticket count: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving ticket count"))
                    .build();
        }
    }

    @GET
    @Path("/stats/recent")
    @RolesAllowed("ADMIN")
    public Response getRecentTickets(@QueryParam("days") @DefaultValue("7") int days) {
        try {
            List<TicketResponse> tickets = supportTicketService.getRecentTickets(days);
            return Response.ok(tickets).build();
        } catch (Exception e) {
            LOG.errorf("Error getting recent tickets: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving recent tickets"))
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

    public static class CountResponse {
        public long count;
        
        public CountResponse(long count) {
            this.count = count;
        }
    }
}
