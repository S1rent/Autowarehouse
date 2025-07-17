package com.autowarehouse.resource;

import com.autowarehouse.entity.Order;
import com.autowarehouse.entity.OrderItem;
import com.autowarehouse.entity.OrderStatusHistory;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.OrderService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Context
    SecurityContext securityContext;

    private User getCurrentUser() {
        // This would typically get the current user from the security context
        // For now, return null - this should be implemented based on your auth system
        return null;
    }

    @POST
    @Path("/create")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response createOrder(@Valid CreateOrderRequest request) {
        try {
            Order order = orderService.createOrderFromCart(request.userId);
            return Response.status(Response.Status.CREATED)
                    .entity(new OrderResponse(order))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/checkout")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response createOrderWithCheckout(@Valid CheckoutOrderRequest request) {
        try {
            Order order = orderService.createOrderFromCartWithDetails(
                request.userId, 
                request.shippingAddress, 
                request.billingAddress, 
                request.paymentMethod
            );
            
            // Simulate payment processing - mark as paid immediately
            orderService.updatePaymentStatus(order.id, Order.PaymentStatus.PAID);
            
            return Response.status(Response.Status.CREATED)
                    .entity(new OrderDetailResponse(order))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getOrder(@PathParam("id") Long id) {
        try {
            Order order = orderService.findById(id);
            if (order == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Order not found"))
                        .build();
            }
            return Response.ok(new OrderDetailResponse(order)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch order"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getUserOrders(@PathParam("userId") Long userId) {
        try {
            List<Order> orders = orderService.findByUserId(userId);
            List<OrderResponse> response = orders.stream()
                    .map(OrderResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch user orders"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/payment")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updatePaymentStatus(@PathParam("id") Long id, @Valid UpdatePaymentRequest request) {
        try {
            Order.PaymentStatus paymentStatus = Order.PaymentStatus.valueOf(request.paymentStatus.toUpperCase());
            orderService.updatePaymentStatus(id, paymentStatus);
            return Response.ok(new SuccessResponse("Payment status updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/cancel")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response cancelOrder(@PathParam("id") Long id, @Valid CancelOrderRequest request) {
        try {
            orderService.cancelOrder(id, request.reason != null ? request.reason : "Cancelled by user");
            return Response.ok(new SuccessResponse("Order cancelled successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // Admin endpoints
    @GET
    @Path("/admin/all")
    @RolesAllowed("ADMIN")
    public Response getAllOrders(@QueryParam("status") String status) {
        try {
            List<Order> orders;
            if (status != null && !status.isEmpty()) {
                Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
                orders = orderService.findByStatus(orderStatus);
            } else {
                orders = orderService.findAllOrders();
            }
            
            List<OrderResponse> response = orders.stream()
                    .map(OrderResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch orders"))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/status")
    @RolesAllowed("ADMIN")
    public Response updateOrderStatus(@PathParam("id") Long id, @Valid UpdateStatusRequest request) {
        try {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(request.status.toUpperCase());
            orderService.updateOrderStatus(id, orderStatus);
            return Response.ok(new SuccessResponse("Order status updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/status")
    @RolesAllowed({"ADMIN", "STAFF"})
    public Response updateOrderStatus(@PathParam("id") Long id, Map<String, String> statusUpdate) {
        try {
            String statusStr = statusUpdate.get("status");
            if (statusStr == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("error", "Status is required"))
                        .build();
            }

            Order.OrderStatus status = Order.OrderStatus.valueOf(statusStr.toUpperCase());
            String changedBy = statusUpdate.get("changedBy");
            String notes = statusUpdate.get("notes");
            
            orderService.updateOrderStatus(id, status, changedBy != null ? changedBy : "ADMIN", notes);

            return Response.ok(Map.of("message", "Order status updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to update order status"))
                    .build();
        }
    }

    @GET
    @Path("/{id}/status-history")
    @RolesAllowed({"USER", "ADMIN", "STAFF"})
    public Response getOrderStatusHistory(@PathParam("id") Long id) {
        try {
            Order order = orderService.findById(id);
            if (order == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Order not found"))
                        .build();
            }

            // Check if user can access this order
            if (!securityContext.isUserInRole("ADMIN") && !securityContext.isUserInRole("STAFF")) {
                User currentUser = getCurrentUser();
                if (currentUser == null || !order.user.id.equals(currentUser.id)) {
                    return Response.status(Response.Status.FORBIDDEN)
                            .entity(Map.of("error", "Access denied"))
                            .build();
                }
            }

            List<OrderStatusHistory> statusHistory = orderService.getOrderStatusHistory(id);
            return Response.ok(statusHistory).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", "Failed to get order status history"))
                    .build();
        }
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getOrderStats() {
        OrderStatsResponse stats = new OrderStatsResponse();
        stats.totalOrders = orderService.getTotalOrdersCount();
        stats.pendingOrders = orderService.getPendingOrdersCount();
        stats.completedOrders = orderService.getCompletedOrdersCount();
        stats.totalRevenue = orderService.getTotalRevenue();
        return Response.ok(stats).build();
    }

    @GET
    @Path("/admin/recent")
    @RolesAllowed("ADMIN")
    public Response getRecentOrders(@QueryParam("limit") @DefaultValue("10") int limit) {
        try {
            List<Order> orders = orderService.findRecentOrders(limit);
            List<OrderResponse> response = orders.stream()
                    .map(OrderResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch recent orders"))
                    .build();
        }
    }

    @GET
    @Path("/admin/search")
    @RolesAllowed("ADMIN")
    public Response searchOrders(
            @QueryParam("query") String query,
            @QueryParam("status") String status,
            @QueryParam("paymentStatus") String paymentStatus,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        try {
            OrderSearchRequest searchRequest = new OrderSearchRequest();
            searchRequest.query = query;
            searchRequest.status = status;
            searchRequest.paymentStatus = paymentStatus;
            searchRequest.startDate = startDate;
            searchRequest.endDate = endDate;
            searchRequest.page = page;
            searchRequest.size = size;

            OrderSearchResponse searchResponse = orderService.searchOrders(searchRequest);
            return Response.ok(searchResponse).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to search orders"))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/ship")
    @RolesAllowed("ADMIN")
    public Response shipOrder(@PathParam("id") Long id, @Valid ShipOrderRequest request) {
        try {
            orderService.shipOrder(id, request.trackingNumber);
            return Response.ok(new SuccessResponse("Order shipped successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/deliver")
    @RolesAllowed("ADMIN")
    public Response deliverOrder(@PathParam("id") Long id) {
        try {
            orderService.deliverOrder(id);
            return Response.ok(new SuccessResponse("Order marked as delivered")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/bulk-status")
    @RolesAllowed("ADMIN")
    public Response bulkUpdateStatus(@Valid BulkStatusUpdateRequest request) {
        try {
            Order.OrderStatus status = Order.OrderStatus.valueOf(request.status.toUpperCase());
            int updatedCount = orderService.bulkUpdateStatus(request.orderIds, status, request.notes);
            return Response.ok(new BulkUpdateResponse(updatedCount, "Orders updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/analytics")
    @RolesAllowed("ADMIN")
    public Response getOrderAnalytics(
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("groupBy") @DefaultValue("day") String groupBy) {
        try {
            OrderAnalyticsResponse analytics = orderService.getOrderAnalytics(startDate, endDate, groupBy);
            return Response.ok(analytics).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch order analytics"))
                    .build();
        }
    }

    @GET
    @Path("/admin/export")
    @RolesAllowed("ADMIN")
    @Produces("text/csv")
    public Response exportOrders(
            @QueryParam("status") String status,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate,
            @QueryParam("format") @DefaultValue("csv") String format) {
        try {
            String exportData = orderService.exportOrders(status, startDate, endDate, format);
            String filename = "orders_export_" + LocalDateTime.now().toString().substring(0, 10) + "." + format;
            
            return Response.ok(exportData)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to export orders"))
                    .build();
        }
    }

    // DTO Classes
    public static class CreateOrderRequest {
        public Long userId;
    }

    public static class CheckoutOrderRequest {
        public Long userId;
        public String shippingAddress;
        public String billingAddress;
        public String paymentMethod;
    }

    public static class UpdatePaymentRequest {
        public String paymentStatus;
        public String paymentReference;
    }

    public static class UpdateStatusRequest {
        public String status;
    }

    public static class ShipOrderRequest {
        public String trackingNumber;
    }

    public static class CancelOrderRequest {
        public String reason;
    }

    public static class OrderResponse {
        public Long id;
        public String orderNumber;
        public String status;
        public String paymentStatus;
        public BigDecimal subtotal;
        public BigDecimal taxAmount;
        public BigDecimal shippingCost;
        public BigDecimal totalAmount;
        public String customerName;
        public String shippingAddress;
        public LocalDateTime createdAt;

        public OrderResponse(Order order) {
            this.id = order.id;
            this.orderNumber = order.orderNumber;
            this.status = order.status.name();
            this.paymentStatus = order.paymentStatus.name();
            this.subtotal = order.subtotal;
            this.taxAmount = order.taxAmount;
            this.shippingCost = order.shippingCost;
            this.totalAmount = order.totalAmount;
            this.customerName = order.user != null ? order.user.getFullName() : null;
            this.shippingAddress = order.shippingAddress;
            this.createdAt = order.createdAt;
        }
    }

    public static class OrderDetailResponse extends OrderResponse {
        public String billingAddress;
        public String paymentMethod;
        public String paymentReference;
        public String notes;
        public LocalDateTime shippedAt;
        public LocalDateTime deliveredAt;
        public List<OrderItemResponse> items;

        public OrderDetailResponse(Order order) {
            super(order);
            this.billingAddress = order.billingAddress;
            this.paymentMethod = order.paymentMethod;
            this.paymentReference = order.paymentReference;
            this.notes = order.notes;
            this.shippedAt = order.shippedAt;
            this.deliveredAt = order.deliveredAt;
            this.items = order.items != null ? 
                        order.items.stream().map(OrderItemResponse::new).toList() : 
                        null;
        }
    }

    public static class OrderItemResponse {
        public Long id;
        public Long productId;
        public String productName;
        public String productSku;
        public BigDecimal productPrice;
        public Integer quantity;
        public BigDecimal subtotal;

        public OrderItemResponse(OrderItem item) {
            this.id = item.id;
            this.productId = item.product != null ? item.product.id : null;
            this.productName = item.productName;
            this.productSku = item.productSku;
            this.productPrice = item.productPrice;
            this.quantity = item.quantity;
            this.subtotal = item.subtotal;
        }
    }

    public static class OrderStatsResponse {
        public long totalOrders;
        public long pendingOrders;
        public long completedOrders;
        public BigDecimal totalRevenue;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }

    public static class OrderSearchRequest {
        public String query;
        public String status;
        public String paymentStatus;
        public String startDate;
        public String endDate;
        public int page;
        public int size;
    }

    public static class OrderSearchResponse {
        public List<OrderResponse> orders;
        public long totalElements;
        public int totalPages;
        public int currentPage;
        public int pageSize;

        public OrderSearchResponse(List<OrderResponse> orders, long totalElements, int totalPages, int currentPage, int pageSize) {
            this.orders = orders;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.currentPage = currentPage;
            this.pageSize = pageSize;
        }
    }

    public static class BulkStatusUpdateRequest {
        public List<Long> orderIds;
        public String status;
        public String notes;
    }

    public static class BulkUpdateResponse {
        public int updatedCount;
        public String message;

        public BulkUpdateResponse(int updatedCount, String message) {
            this.updatedCount = updatedCount;
            this.message = message;
        }
    }

    public static class OrderAnalyticsResponse {
        public List<OrderAnalyticsData> data;
        public OrderSummary summary;

        public static class OrderAnalyticsData {
            public String date;
            public long orderCount;
            public BigDecimal revenue;
            public double averageOrderValue;
        }

        public static class OrderSummary {
            public long totalOrders;
            public BigDecimal totalRevenue;
            public double averageOrderValue;
            public double growthRate;
        }
    }
}
