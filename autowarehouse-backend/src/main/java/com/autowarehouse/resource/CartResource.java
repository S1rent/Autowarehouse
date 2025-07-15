package com.autowarehouse.resource;

import com.autowarehouse.entity.CartItem;
import com.autowarehouse.entity.Product;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.CartService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.List;

@Path("/api/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @Inject
    CartService cartService;

    @GET
    @Path("/user/{userId}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getCartItems(@PathParam("userId") Long userId) {
        try {
            List<CartItem> cartItems = cartService.getCartItems(userId);
            List<CartItemResponse> response = cartItems.stream()
                    .map(CartItemResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch cart items"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/selected")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getSelectedCartItems(@PathParam("userId") Long userId) {
        try {
            List<CartItem> cartItems = cartService.getSelectedCartItems(userId);
            List<CartItemResponse> response = cartItems.stream()
                    .map(CartItemResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch selected cart items"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/summary")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getCartSummary(@PathParam("userId") Long userId) {
        try {
            CartService.CartSummary summary = cartService.getCartSummary(userId);
            CartSummaryResponse response = new CartSummaryResponse();
            response.totalItems = summary.totalItems;
            response.selectedItems = summary.selectedItems;
            response.totalQuantity = summary.totalQuantity;
            response.selectedQuantity = summary.selectedQuantity;
            response.totalAmount = summary.totalAmount;
            response.selectedAmount = summary.selectedAmount;
            response.totalSavings = summary.totalSavings;
            response.selectedSavings = summary.selectedSavings;
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch cart summary"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/count")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getCartItemCount(@PathParam("userId") Long userId) {
        try {
            long count = cartService.getCartItemCount(userId);
            return Response.ok(new CountResponse((int) count)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch cart count"))
                    .build();
        }
    }

    @POST
    @Path("/add")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response addToCart(@Valid AddToCartRequest request) {
        try {
            CartItem cartItem = cartService.addToCart(request.userId, request.productId, request.quantity);
            return Response.status(Response.Status.CREATED)
                    .entity(new CartItemResponse(cartItem))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{cartItemId}/quantity")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updateQuantity(@PathParam("cartItemId") Long cartItemId, @Valid UpdateQuantityRequest request) {
        try {
            cartService.updateQuantity(cartItemId, request.quantity);
            return Response.ok(new SuccessResponse("Quantity updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{cartItemId}/toggle-selection")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response toggleSelection(@PathParam("cartItemId") Long cartItemId) {
        try {
            cartService.toggleSelection(cartItemId);
            return Response.ok(new SuccessResponse("Selection toggled successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/user/{userId}/select-all")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response selectAll(@PathParam("userId") Long userId, @Valid SelectAllRequest request) {
        try {
            cartService.selectAll(userId, request.selected);
            return Response.ok(new SuccessResponse("All items selection updated")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{cartItemId}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response removeFromCart(@PathParam("cartItemId") Long cartItemId) {
        try {
            cartService.removeFromCart(cartItemId);
            return Response.ok(new SuccessResponse("Item removed from cart")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/user/{userId}/clear")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response clearCart(@PathParam("userId") Long userId) {
        try {
            cartService.clearCart(userId);
            return Response.ok(new SuccessResponse("Cart cleared successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/product/{productId}/exists")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response checkProductInCart(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        try {
            boolean exists = cartService.isProductInCart(userId, productId);
            return Response.ok(new ExistsResponse(exists)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to check product in cart"))
                    .build();
        }
    }

    // DTO Classes
    public static class AddToCartRequest {
        public Long userId;
        public Long productId;
        public Integer quantity;
    }

    public static class UpdateQuantityRequest {
        public Integer quantity;
    }

    public static class SelectAllRequest {
        public Boolean selected;
    }

    public static class CartItemResponse {
        public Long id;
        public Long productId;
        public String productName;
        public String productSku;
        public String productBrand;
        public BigDecimal productPrice;
        public BigDecimal originalPrice;
        public List<String> productImages;
        public Integer quantity;
        public Boolean isSelected;
        public BigDecimal subtotal;
        public BigDecimal savings;
        public Integer availableStock;
        public Boolean isProductActive;

        public CartItemResponse(CartItem cartItem) {
            this.id = cartItem.id;
            this.productId = cartItem.product.id;
            this.productName = cartItem.product.name;
            this.productSku = cartItem.product.sku;
            this.productBrand = cartItem.product.brand;
            this.productPrice = cartItem.product.price;
            this.originalPrice = cartItem.product.originalPrice;
            this.productImages = cartItem.product.imageUrls;
            this.quantity = cartItem.quantity;
            this.isSelected = cartItem.isSelected;
            this.subtotal = cartItem.getSubtotal();
            this.savings = cartItem.getSavings();
            this.availableStock = cartItem.product.stockQuantity;
            this.isProductActive = cartItem.product.isActive;
        }
    }

    public static class CartSummaryResponse {
        public Integer totalItems;
        public Integer selectedItems;
        public Integer totalQuantity;
        public Integer selectedQuantity;
        public BigDecimal totalAmount;
        public BigDecimal selectedAmount;
        public BigDecimal totalSavings;
        public BigDecimal selectedSavings;
    }

    public static class CountResponse {
        public Integer count;

        public CountResponse(Integer count) {
            this.count = count;
        }
    }

    public static class ExistsResponse {
        public Boolean exists;

        public ExistsResponse(Boolean exists) {
            this.exists = exists;
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
