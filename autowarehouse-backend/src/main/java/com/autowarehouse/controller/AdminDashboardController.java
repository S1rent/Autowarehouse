package com.autowarehouse.controller;

import com.autowarehouse.dto.*;
import com.autowarehouse.service.AdminDashboardService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("/api/admin/dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class AdminDashboardController {

    private static final Logger LOG = Logger.getLogger(AdminDashboardController.class);

    @Inject
    AdminDashboardService dashboardService;

    @GET
    @Path("/stats")
    public Response getDashboardStats() {
        LOG.info("Getting dashboard statistics");
        
        try {
            DashboardStatsResponse stats = dashboardService.getDashboardStats();
            return Response.ok(stats).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting dashboard stats: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving dashboard statistics"))
                    .build();
        }
    }

    @GET
    @Path("/pending-orders")
    public Response getPendingOrders() {
        LOG.info("Getting pending orders");
        
        try {
            PendingOrdersResponse pendingOrders = dashboardService.getPendingOrders();
            return Response.ok(pendingOrders).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting pending orders: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving pending orders"))
                    .build();
        }
    }

    @GET
    @Path("/top-products")
    public Response getTopProducts(@QueryParam("limit") @DefaultValue("5") int limit) {
        LOG.infof("Getting top products with limit: %d", limit);
        
        try {
            if (limit <= 0 || limit > 50) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Limit must be between 1 and 50"))
                        .build();
            }
            
            TopProductResponse topProducts = dashboardService.getTopProducts(limit);
            return Response.ok(topProducts).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting top products: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving top products"))
                    .build();
        }
    }

    @GET
    @Path("/recent-sales")
    public Response getRecentSales(@QueryParam("limit") @DefaultValue("5") int limit) {
        LOG.infof("Getting recent sales with limit: %d", limit);
        
        try {
            if (limit <= 0 || limit > 50) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Limit must be between 1 and 50"))
                        .build();
            }
            
            RecentSalesResponse recentSales = dashboardService.getRecentSales(limit);
            return Response.ok(recentSales).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting recent sales: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving recent sales"))
                    .build();
        }
    }

    @GET
    @Path("/recent-transactions")
    public Response getRecentTransactions(@QueryParam("limit") @DefaultValue("5") int limit) {
        LOG.infof("Getting recent transactions with limit: %d", limit);
        
        try {
            if (limit <= 0 || limit > 50) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Limit must be between 1 and 50"))
                        .build();
            }
            
            RecentTransactionsResponse recentTransactions = dashboardService.getRecentTransactions(limit);
            return Response.ok(recentTransactions).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting recent transactions: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error retrieving recent transactions"))
                    .build();
        }
    }

    // Error response DTO
    public static class ErrorResponse {
        public String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
