package com.autowarehouse.resource;

import com.autowarehouse.entity.Order;
import com.autowarehouse.entity.OrderItem;
import com.autowarehouse.service.OrderService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

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
            orderService.updatePaymentStatus(id, request.paymentStatus, request.paymentReference);
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
    public Response cancelOrder(@PathParam("id") Long id) {
        try {
            orderService.cancelOrder(id);
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
                orders = orderService.findByStatus(status);
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
            orderService.updateOrderStatus(id, request.status);
            return Response.ok(new SuccessResponse("Order status updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
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
            return Response.ok(new SuccessResponse("Order delivered successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
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

    // DTO Classes
    public static class CreateOrderRequest {
        public Long userId;
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
}
