package com.autowarehouse.resource;

import com.autowarehouse.entity.SavedItem;
import com.autowarehouse.entity.CartItem;
import com.autowarehouse.service.SavedItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/saved-items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SavedItemResource {

    @Inject
    SavedItemService savedItemService;

    @GET
    @Path("/user/{userId}")
    public Response getSavedItems(@PathParam("userId") Long userId) {
        try {
            List<SavedItem> savedItems = savedItemService.getSavedItems(userId);
            List<SavedItemResponse> response = savedItems.stream()
                    .map(SavedItemResponse::new)
                    .collect(Collectors.toList());
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/count")
    public Response getSavedItemCount(@PathParam("userId") Long userId) {
        try {
            long count = savedItemService.getSavedItemCount(userId);
            return Response.ok(new CountResponse(count)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/save")
    public Response saveForLater(SaveForLaterRequest request) {
        try {
            SavedItem savedItem = savedItemService.saveForLater(
                    request.userId, 
                    request.productId, 
                    request.quantity
            );
            return Response.ok(new SavedItemResponse(savedItem)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/move-from-cart/{cartItemId}")
    public Response moveToSaved(@PathParam("cartItemId") Long cartItemId) {
        try {
            SavedItem savedItem = savedItemService.moveToSaved(cartItemId);
            return Response.ok(new SavedItemResponse(savedItem)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{savedItemId}/move-to-cart")
    public Response moveToCart(@PathParam("savedItemId") Long savedItemId) {
        try {
            CartItem cartItem = savedItemService.moveToCart(savedItemId);
            return Response.ok(new CartItemResponse(cartItem)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/user/{userId}/move-all-to-cart")
    public Response moveAllToCart(@PathParam("userId") Long userId) {
        try {
            List<CartItem> cartItems = savedItemService.moveAllToCart(userId);
            List<CartItemResponse> response = cartItems.stream()
                    .map(CartItemResponse::new)
                    .collect(Collectors.toList());
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{savedItemId}/quantity")
    public Response updateQuantity(@PathParam("savedItemId") Long savedItemId, UpdateQuantityRequest request) {
        try {
            savedItemService.updateQuantity(savedItemId, request.quantity);
            return Response.ok(new SuccessResponse("Quantity updated successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{savedItemId}")
    public Response removeSavedItem(@PathParam("savedItemId") Long savedItemId) {
        try {
            savedItemService.removeSavedItem(savedItemId);
            return Response.ok(new SuccessResponse("Item removed from saved items")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/user/{userId}/clear")
    public Response clearSavedItems(@PathParam("userId") Long userId) {
        try {
            savedItemService.clearSavedItems(userId);
            return Response.ok(new SuccessResponse("All saved items cleared")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/product/{productId}/exists")
    public Response checkProductSaved(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        try {
            boolean exists = savedItemService.isProductSaved(userId, productId);
            return Response.ok(new ExistsResponse(exists)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // Request/Response DTOs
    public static class SaveForLaterRequest {
        public Long userId;
        public Long productId;
        public Integer quantity;
    }

    public static class UpdateQuantityRequest {
        public Integer quantity;
    }

    public static class SavedItemResponse {
        public Long id;
        public Long userId;
        public Long productId;
        public String productName;
        public String productBrand;
        public String productSku;
        public BigDecimal productPrice;
        public BigDecimal originalPrice;
        public List<String> productImages;
        public Integer quantity;
        public BigDecimal subtotal;
        public BigDecimal savings;
        public Boolean savedFromCart;
        public Boolean isProductActive;
        public Integer availableStock;
        public LocalDateTime createdAt;

        public SavedItemResponse(SavedItem savedItem) {
            this.id = savedItem.id;
            this.userId = savedItem.user.id;
            this.productId = savedItem.product.id;
            this.productName = savedItem.product.name;
            this.productBrand = savedItem.product.brand;
            this.productSku = savedItem.product.sku;
            this.productPrice = savedItem.product.getCurrentPrice();
            this.originalPrice = savedItem.product.originalPrice;
            this.productImages = savedItem.product.imageUrls;
            this.quantity = savedItem.quantity;
            this.subtotal = savedItem.getSubtotal();
            this.savings = savedItem.getSavings();
            this.savedFromCart = savedItem.savedFromCart;
            this.isProductActive = savedItem.product.isActive;
            this.availableStock = savedItem.product.stockQuantity;
            this.createdAt = savedItem.createdAt;
        }
    }

    public static class CartItemResponse {
        public Long id;
        public Long userId;
        public Long productId;
        public String productName;
        public String productBrand;
        public String productSku;
        public BigDecimal productPrice;
        public BigDecimal originalPrice;
        public List<String> productImages;
        public Integer quantity;
        public BigDecimal subtotal;
        public BigDecimal savings;
        public Boolean isSelected;
        public Boolean isProductActive;
        public Integer availableStock;
        public LocalDateTime createdAt;

        public CartItemResponse(CartItem cartItem) {
            this.id = cartItem.id;
            this.userId = cartItem.user.id;
            this.productId = cartItem.product.id;
            this.productName = cartItem.product.name;
            this.productBrand = cartItem.product.brand;
            this.productSku = cartItem.product.sku;
            this.productPrice = cartItem.product.getCurrentPrice();
            this.originalPrice = cartItem.product.originalPrice;
            this.productImages = cartItem.product.imageUrls;
            this.quantity = cartItem.quantity;
            this.subtotal = cartItem.getSubtotal();
            this.savings = cartItem.getSavings();
            this.isSelected = cartItem.isSelected;
            this.isProductActive = cartItem.product.isActive;
            this.availableStock = cartItem.product.stockQuantity;
            this.createdAt = cartItem.createdAt;
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

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
