package com.autowarehouse.resource;

import com.autowarehouse.service.WishlistService;
import com.autowarehouse.entity.WishlistItem;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Path("/api/wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WishlistResource {

    private static final Logger LOGGER = Logger.getLogger(WishlistResource.class.getName());

    @Inject
    WishlistService wishlistService;

    // DTO Classes
    public static class AddToWishlistRequest {
        public Long userId;
        public Long productId;

        public AddToWishlistRequest() {}

        public AddToWishlistRequest(Long userId, Long productId) {
            this.userId = userId;
            this.productId = productId;
        }
    }

    public static class RemoveFromWishlistRequest {
        public Long userId;
        public Long productId;

        public RemoveFromWishlistRequest() {}

        public RemoveFromWishlistRequest(Long userId, Long productId) {
            this.userId = userId;
            this.productId = productId;
        }
    }

    public static class WishlistItemResponse {
        public Long id;
        public ProductResponse product;
        public LocalDateTime createdAt;

        public WishlistItemResponse() {}

        public WishlistItemResponse(WishlistItem wishlistItem) {
            this.id = wishlistItem.id;
            this.product = new ProductResponse(wishlistItem.product);
            this.createdAt = wishlistItem.createdAt;
        }
    }

    public static class ProductResponse {
        public Long id;
        public String name;
        public String description;
        public java.math.BigDecimal price;
        public java.math.BigDecimal originalPrice;
        public String brand;
        public String model;
        public String sku;
        public String[] imageUrls;
        public Integer stockQuantity;
        public Boolean isActive;
        public Boolean isOnSale;
        public java.math.BigDecimal rating;
        public Integer reviewCount;
        public String categoryName;

        public ProductResponse() {}

        public ProductResponse(com.autowarehouse.entity.Product product) {
            this.id = product.id;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.originalPrice = product.originalPrice;
            this.brand = product.brand;
            this.model = product.model;
            this.sku = product.sku;
            this.imageUrls = product.imageUrls != null ? product.imageUrls.toArray(new String[0]) : new String[0];
            this.stockQuantity = product.stockQuantity;
            this.isActive = product.isActive;
            this.isOnSale = product.isOnSale;
            this.rating = product.rating;
            this.reviewCount = product.reviewCount;
            this.categoryName = product.category != null ? product.category.name : null;
        }
    }

    public static class SuccessResponse {
        public String message;
        public Object data;

        public SuccessResponse() {}

        public SuccessResponse(String message) {
            this.message = message;
        }

        public SuccessResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }
    }

    public static class ErrorResponse {
        public String message;
        public String error;

        public ErrorResponse() {}

        public ErrorResponse(String message) {
            this.message = message;
        }

        public ErrorResponse(String message, String error) {
            this.message = message;
            this.error = error;
        }
    }

    public static class CountResponse {
        public long count;

        public CountResponse() {}

        public CountResponse(long count) {
            this.count = count;
        }
    }

    public static class ExistsResponse {
        public boolean exists;

        public ExistsResponse() {}

        public ExistsResponse(boolean exists) {
            this.exists = exists;
        }
    }

    /**
     * Get user's wishlist
     * GET /api/wishlist/user/{userId}
     */
    @GET
    @Path("/user/{userId}")
    public Response getUserWishlist(@PathParam("userId") Long userId) {
        LOGGER.info("GET /api/wishlist/user/" + userId);
        
        try {
            var wishlistItems = wishlistService.getUserWishlistWithProducts(userId);
            var response = wishlistItems.stream()
                .map(WishlistItemResponse::new)
                .toList();
            
            LOGGER.info("Successfully retrieved " + response.size() + " wishlist items for user: " + userId);
            return Response.ok(response).build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error getting wishlist: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error getting wishlist for user " + userId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to get wishlist", e.getMessage()))
                .build();
        }
    }

    /**
     * Add product to wishlist
     * POST /api/wishlist/add
     */
    @POST
    @Path("/add")
    public Response addToWishlist(AddToWishlistRequest request) {
        LOGGER.info("POST /api/wishlist/add - User: " + request.userId + ", Product: " + request.productId);
        
        try {
            // Validate request
            if (request.userId == null || request.productId == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid request", "userId and productId are required"))
                    .build();
            }
            
            WishlistItem wishlistItem = wishlistService.addToWishlist(request.userId, request.productId);
            WishlistItemResponse response = new WishlistItemResponse(wishlistItem);
            
            LOGGER.info("Successfully added product " + request.productId + " to wishlist for user: " + request.userId);
            return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error adding to wishlist: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error adding to wishlist: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to add to wishlist", e.getMessage()))
                .build();
        }
    }

    /**
     * Remove product from wishlist
     * DELETE /api/wishlist/remove
     */
    @DELETE
    @Path("/remove")
    public Response removeFromWishlist(RemoveFromWishlistRequest request) {
        LOGGER.info("DELETE /api/wishlist/remove - User: " + request.userId + ", Product: " + request.productId);
        
        try {
            // Validate request
            if (request.userId == null || request.productId == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Invalid request", "userId and productId are required"))
                    .build();
            }
            
            wishlistService.removeFromWishlist(request.userId, request.productId);
            
            LOGGER.info("Successfully removed product " + request.productId + " from wishlist for user: " + request.userId);
            return Response.ok(new SuccessResponse("Product removed from wishlist successfully")).build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error removing from wishlist: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error removing from wishlist: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to remove from wishlist", e.getMessage()))
                .build();
        }
    }

    /**
     * Get wishlist count for user
     * GET /api/wishlist/user/{userId}/count
     */
    @GET
    @Path("/user/{userId}/count")
    public Response getWishlistCount(@PathParam("userId") Long userId) {
        LOGGER.info("GET /api/wishlist/user/" + userId + "/count");
        
        try {
            long count = wishlistService.getWishlistCount(userId);
            
            LOGGER.info("Successfully retrieved wishlist count for user " + userId + ": " + count);
            return Response.ok(new CountResponse(count)).build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error getting wishlist count: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error getting wishlist count for user " + userId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to get wishlist count", e.getMessage()))
                .build();
        }
    }

    /**
     * Check if product is in user's wishlist
     * GET /api/wishlist/user/{userId}/product/{productId}/exists
     */
    @GET
    @Path("/user/{userId}/product/{productId}/exists")
    public Response isInWishlist(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        LOGGER.info("GET /api/wishlist/user/" + userId + "/product/" + productId + "/exists");
        
        try {
            boolean exists = wishlistService.isInWishlist(userId, productId);
            
            LOGGER.info("Product " + productId + " in wishlist for user " + userId + ": " + exists);
            return Response.ok(new ExistsResponse(exists)).build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error checking wishlist: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error checking wishlist for user " + userId + " and product " + productId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to check wishlist", e.getMessage()))
                .build();
        }
    }

    /**
     * Clear entire wishlist for user
     * DELETE /api/wishlist/user/{userId}/clear
     */
    @DELETE
    @Path("/user/{userId}/clear")
    public Response clearWishlist(@PathParam("userId") Long userId) {
        LOGGER.info("DELETE /api/wishlist/user/" + userId + "/clear");
        
        try {
            wishlistService.clearWishlist(userId);
            
            LOGGER.info("Successfully cleared wishlist for user: " + userId);
            return Response.ok(new SuccessResponse("Wishlist cleared successfully")).build();
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error clearing wishlist: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse("Invalid request", e.getMessage()))
                .build();
        } catch (Exception e) {
            LOGGER.severe("Error clearing wishlist for user " + userId + ": " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Failed to clear wishlist", e.getMessage()))
                .build();
        }
    }
    
}
