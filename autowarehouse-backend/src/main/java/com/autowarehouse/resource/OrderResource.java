package com.autowarehouse.resource;

import com.autowarehouse.entity.*;
import com.autowarehouse.service.OrderService;
import com.autowarehouse.service.CartService;
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

    @Inject
    CartService cartService;

    @POST
    @Path("/create")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response createOrder(@Valid CreateOrderRequest request) {
        try {
            User user = User.findById(request.userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }

            List<CartItem> cartItems = cartService.getSelectedCartItems(request.userId);
            if (cartItems.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("No items selected in cart"))
                        .build();
            }

            Order order = orderService.createOrder(user, cartItems);
            
            // Clear cart after successful order
            cartService.clearCartAfterOrder(request.userId, cartItems);
            
            return Response.status(Response.Status.CREATED)
                    .entity(new OrderDetailResponse(order))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/create-from-auction")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response createOrderFromAuction(@Valid CreateOrderFromAuctionRequest request) {
        try {
            User user = User.findById(request.userId);
            Auction auction = Auction.findById(request.auctionId);
            
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }
            
            if (auction == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Auction not found"))
                        .build();
            }

            Order order = orderService.createOrderFromAuction(user, auction);
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
    @Path("/number/{orderNumber}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getOrderByNumber(@PathParam("orderNumber") String orderNumber) {
        try {
            Order order = orderService.findByOrderNumber(orderNumber);
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
            User user = User.findById(userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }

            List<Order> orders = orderService.findByUser(user);
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

    @GET
    @Path("/{id}/items")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getOrderItems(@PathParam("id") Long id) {
        try {
            List<OrderItem> orderItems = orderService.getOrderItems(id);
            List<OrderItemResponse> response = orderItems.stream()
                    .map(OrderItemResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/shipping")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updateShippingInfo(@PathParam("id") Long id, @Valid UpdateShippingRequest request) {
        try {
            orderService.updateShippingInfo(id, request.shippingAddress, request.trackingNumber);
            return Response.ok(new SuccessResponse("Shipping information updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/billing")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updateBillingAddress(@PathParam("id") Long id, @Valid UpdateBillingRequest request) {
        try {
            orderService.updateBillingAddress(id, request.billingAddress);
            return Response.ok(new SuccessResponse("Billing address updated successfully")).build();
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
            orderService.cancelOrder(id, request.reason);
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
            
            if (status != null) {
                OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
                orders = orderService.findByStatus(orderStatus);
            } else {
                orders = Order.listAll();
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

    @GET
    @Path("/admin/pending")
    @RolesAllowed("ADMIN")
    public Response getPendingOrders() {
        List<Order> orders = orderService.findPendingOrders();
        List<OrderResponse> response = orders.stream()
                .map(OrderResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/admin/recent")
    @RolesAllowed("ADMIN")
    public Response getRecentOrders(@QueryParam("days") @DefaultValue("7") int days) {
        List<Order> orders = orderService.findRecentOrders(days);
        List<OrderResponse> response = orders.stream()
                .map(OrderResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @PUT
    @Path("/admin/{id}/status")
    @RolesAllowed("ADMIN")
    public Response updateOrderStatus(@PathParam("id") Long id, @Valid UpdateOrderStatusRequest request) {
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
    @Path("/admin/{id}/payment-status")
    @RolesAllowed("ADMIN")
    public Response updatePaymentStatus(@PathParam("id") Long id, @Valid UpdatePaymentStatusRequest request) {
        try {
            orderService.updatePaymentStatus(id, request.paymentStatus);
            return Response.ok(new SuccessResponse("Payment status updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/notes")
    @RolesAllowed("ADMIN")
    public Response addOrderNotes(@PathParam("id") Long id, @Valid AddNotesRequest request) {
        try {
            orderService.addOrderNotes(id, request.notes);
            return Response.ok(new SuccessResponse("Order notes added successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/admin/{id}/refund")
    @RolesAllowed("ADMIN")
    public Response processRefund(@PathParam("id") Long id, @Valid ProcessRefundRequest request) {
        try {
            orderService.processRefund(id, request.refundAmount);
            return Response.ok(new SuccessResponse("Refund processed successfully")).build();
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
    @Path("/admin/revenue")
    @RolesAllowed("ADMIN")
    public Response getRevenue(@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
        try {
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            BigDecimal revenue = orderService.getRevenueForPeriod(start, end);
            return Response.ok(new RevenueResponse(revenue)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid date format"))
                    .build();
        }
    }

    // DTO Classes
    public static class CreateOrderRequest {
        public Long userId;
    }

    public static class CreateOrderFromAuctionRequest {
        public Long userId;
        public Long auctionId;
    }

    public static class UpdateShippingRequest {
        public String shippingAddress;
        public String trackingNumber;
    }

    public static class UpdateBillingRequest {
        public String billingAddress;
    }

    public static class CancelOrderRequest {
        public String reason;
    }

    public static class UpdateOrderStatusRequest {
        public OrderStatus status;
    }

    public static class UpdatePaymentStatusRequest {
        public PaymentStatus paymentStatus;
    }

    public static class AddNotesRequest {
        public String notes;
    }

    public static class ProcessRefundRequest {
        public BigDecimal refundAmount;
    }

    public static class OrderResponse {
        public Long id;
        public String orderNumber;
        public OrderStatus status;
        public PaymentStatus paymentStatus;
        public BigDecimal subtotal;
        public BigDecimal taxAmount;
        public BigDecimal shippingCost;
        public BigDecimal totalAmount;
        public String customerName;
        public LocalDateTime createdAt;

        public OrderResponse(Order order) {
            this.id = order.id;
            this.orderNumber = order.orderNumber;
            this.status = order.status;
            this.paymentStatus = order.paymentStatus;
            this.subtotal = order.subtotal;
            this.taxAmount = order.taxAmount;
            this.shippingCost = order.shippingCost;
            this.totalAmount = order.totalAmount;
            this.customerName = order.user != null ? order.user.getFullName() : null;
            this.createdAt = order.createdAt;
        }
    }

    public static class OrderDetailResponse extends OrderResponse {
        public String shippingAddress;
        public String billingAddress;
        public String trackingNumber;
        public String notes;
        public LocalDateTime updatedAt;

        public OrderDetailResponse(Order order) {
            super(order);
            this.shippingAddress = order.shippingAddress;
            this.billingAddress = order.billingAddress;
            this.trackingNumber = order.trackingNumber;
            this.notes = order.notes;
            this.updatedAt = order.updatedAt;
        }
    }

    public static class OrderItemResponse {
        public Long id;
        public String productName;
        public String productSku;
        public Integer quantity;
        public BigDecimal price;
        public BigDecimal totalPrice;
        public String auctionTitle;

        public OrderItemResponse(OrderItem orderItem) {
            this.id = orderItem.id;
            this.productName = orderItem.product != null ? orderItem.product.name : null;
            this.productSku = orderItem.product != null ? orderItem.product.sku : null;
            this.quantity = orderItem.quantity;
            this.price = orderItem.price;
            this.totalPrice = orderItem.totalPrice;
            this.auctionTitle = orderItem.auction != null ? orderItem.auction.title : null;
        }
    }

    public static class OrderStatsResponse {
        public long totalOrders;
        public long pendingOrders;
        public long completedOrders;
        public BigDecimal totalRevenue;
    }

    public static class RevenueResponse {
        public BigDecimal revenue;

        public RevenueResponse(BigDecimal revenue) {
            this.revenue = revenue;
        }
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
