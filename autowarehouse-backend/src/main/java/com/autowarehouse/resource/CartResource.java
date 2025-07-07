package com.autowarehouse.resource;

import com.autowarehouse.entity.CartItem;
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
@RolesAllowed({"ADMIN", "CUSTOMER"})
public class CartResource {

    @Inject
    CartService cartService;

    @GET
    @Path("/user/{userId}")
    public Response getCartItems(@PathParam("userId") Long userId) {
        try {
            List<CartItem> cartItems = cartService.getCartItems(userId);
            List<CartItemResponse> response = cartItems.stream()
                    .map(CartItemResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/selected")
    public Response getSelectedCartItems(@PathParam("userId") Long userId) {
        try {
            List<CartItem> cartItems = cartService.getSelectedCartItems(userId);
            List<CartItemResponse> response = cartItems.stream()
                    .map(CartItemResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/summary")
    public Response getCartSummary(@PathParam("userId") Long userId) {
        try {
            CartService.CartSummary summary = cartService.getCartSummary(userId);
            return Response.ok(new CartSummaryResponse(summary)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get cart summary"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/count")
    public Response getCartItemCount(@PathParam("userId") Long userId) {
        long count = cartService.getCartItemCount(userId);
        return Response.ok(new CountResponse(count)).build();
    }

    @POST
    @Path("/add")
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
    public Response updateQuantity(@PathParam("cartItemId") Long cartItemId, @Valid UpdateQuantityRequest request) {
        try {
            cartService.updateCartItemQuantity(cartItemId, request.quantity);
            return Response.ok(new SuccessResponse("Cart item quantity updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{cartItemId}/toggle-selection")
    public Response toggleSelection(@PathParam("cartItemId") Long cartItemId) {
        try {
            cartService.toggleCartItemSelection(cartItemId);
            return Response.ok(new SuccessResponse("Cart item selection toggled successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/user/{userId}/select-all")
    public Response selectAllItems(@PathParam("userId") Long userId, @Valid SelectAllRequest request) {
        try {
            cartService.selectAllCartItems(userId, request.selected);
            return Response.ok(new SuccessResponse("All cart items selection updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{cartItemId}")
    public Response removeFromCart(@PathParam("cartItemId") Long cartItemId) {
        try {
            cartService.removeFromCart(cartItemId);
            return Response.ok(new SuccessResponse("Item removed from cart successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to remove item from cart"))
                    .build();
        }
    }

    @DELETE
    @Path("/user/{userId}/product/{productId}")
    public Response removeProductFromCart(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        try {
            cartService.removeProductFromUserCart(userId, productId);
            return Response.ok(new SuccessResponse("Product removed from cart successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to remove product from cart"))
                    .build();
        }
    }

    @DELETE
    @Path("/user/{userId}/clear")
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

    @POST
    @Path("/user/{userId}/validate")
    public Response validateCartItems(@PathParam("userId") Long userId) {
        try {
            cartService.validateCartItems(userId);
            return Response.ok(new SuccessResponse("Cart items validated successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to validate cart items"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/product/{productId}/exists")
    public Response checkProductInCart(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        boolean exists = cartService.isProductInCart(userId, productId);
        return Response.ok(new ExistsResponse(exists)).build();
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
            this.productPrice = cartItem.product.getCurrentPrice();
            this.originalPrice = cartItem.product.originalPrice;
            this.productImages = cartItem.product.imageUrls;
            this.quantity = cartItem.quantity;
            this.isSelected = cartItem.isSelected;
            this.subtotal = cartItem.getSubtotal();
            this.savings = cartItem.getTotalSavings();
            this.availableStock = cartItem.product.stockQuantity;
            this.isProductActive = cartItem.product.isActive;
        }
    }

    public static class CartSummaryResponse {
        public int totalItems;
        public int selectedItems;
        public int totalQuantity;
        public int selectedQuantity;
        public BigDecimal totalAmount;
        public BigDecimal selectedAmount;
        public BigDecimal totalSavings;
        public BigDecimal selectedSavings;

        public CartSummaryResponse(CartService.CartSummary summary) {
            this.totalItems = summary.totalItems;
            this.selectedItems = summary.selectedItems;
            this.totalQuantity = summary.totalQuantity;
            this.selectedQuantity = summary.selectedQuantity;
            this.totalAmount = summary.totalAmount;
            this.selectedAmount = summary.selectedAmount;
            this.totalSavings = summary.totalSavings;
            this.selectedSavings = summary.selectedSavings;
        }
    }

    public static class CountResponse {
        public long count;

        public CountResponse(long count) {
            this.count = count;
        }
    }

    public static class ExistsResponse {
        public boolean exists;

        public ExistsResponse(boolean exists) {
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
