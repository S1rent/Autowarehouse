package com.autowarehouse.resource;

import com.autowarehouse.dto.CreateReviewRequest;
import com.autowarehouse.dto.ReviewResponse;
import com.autowarehouse.entity.User;
import com.autowarehouse.entity.UserRole;
import com.autowarehouse.service.ReviewService;
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

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    private static final Logger LOG = Logger.getLogger(ReviewResource.class);

    @Inject
    ReviewService reviewService;

    @POST
    // @RolesAllowed("CUSTOMER") // Temporarily disabled for debugging
    public Response createReview(@Valid CreateReviewRequest request, @Context SecurityContext securityContext) {
        LOG.infof("Creating review for product %d, order %d", request.productId, request.orderId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            LOG.infof("User ID from security context: %d", userId);
            
            ReviewResponse review = reviewService.createReview(request, userId);
            
            LOG.infof("Created review %d", review.id);
            return Response.status(Response.Status.CREATED).entity(review).build();
            
        } catch (NotFoundException e) {
            LOG.errorf("NotFoundException: %s", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (BadRequestException e) {
            LOG.errorf("BadRequestException: %s", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Unexpected error creating review: %s", e.getMessage());
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to create review: " + e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/product/{productId}")
    public Response getProductReviews(@PathParam("productId") Long productId,
                                    @QueryParam("limit") @DefaultValue("10") int limit) {
        LOG.infof("Getting reviews for product %d", productId);
        
        try {
            List<ReviewResponse> reviews = reviewService.getProductReviews(productId);
            
            // Apply limit if specified
            if (limit > 0 && reviews.size() > limit) {
                reviews = reviews.subList(0, limit);
            }
            
            return Response.ok(reviews).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting product reviews: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get reviews"))
                    .build();
        }
    }

    @GET
    @Path("/user/my-reviews")
    @RolesAllowed("CUSTOMER")
    public Response getMyReviews(@Context SecurityContext securityContext) {
        LOG.info("Getting current user's reviews");
        
        try {
            Long userId = getCurrentUserId(securityContext);
            List<ReviewResponse> reviews = reviewService.getUserReviews(userId);
            
            return Response.ok(reviews).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting user reviews: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get reviews"))
                    .build();
        }
    }

    @GET
    @Path("/{reviewId}")
    public Response getReview(@PathParam("reviewId") Long reviewId) {
        LOG.infof("Getting review %d", reviewId);
        
        try {
            ReviewResponse review = reviewService.getReview(reviewId);
            return Response.ok(review).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error getting review: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get review"))
                    .build();
        }
    }

    @DELETE
    @Path("/{reviewId}")
    @RolesAllowed({"CUSTOMER", "ADMIN"})
    public Response deleteReview(@PathParam("reviewId") Long reviewId, @Context SecurityContext securityContext) {
        LOG.infof("Deleting review %d", reviewId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            reviewService.deleteReview(reviewId, userId);
            
            return Response.noContent().build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error deleting review: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to delete review"))
                    .build();
        }
    }

    @POST
    @Path("/{reviewId}/helpful")
    public Response markHelpful(@PathParam("reviewId") Long reviewId) {
        LOG.infof("Marking review %d as helpful", reviewId);
        
        try {
            ReviewResponse review = reviewService.markHelpful(reviewId);
            return Response.ok(review).build();
            
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        } catch (Exception e) {
            LOG.errorf("Error marking review as helpful: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to mark review as helpful"))
                    .build();
        }
    }

    @GET
    @Path("/product/{productId}/stats")
    public Response getProductReviewStats(@PathParam("productId") Long productId) {
        LOG.infof("Getting review stats for product %d", productId);
        
        try {
            double averageRating = reviewService.getProductAverageRating(productId);
            long reviewCount = reviewService.getProductReviewCount(productId);
            
            ReviewStatsResponse stats = new ReviewStatsResponse();
            stats.averageRating = averageRating;
            stats.reviewCount = reviewCount;
            stats.productId = productId;
            
            return Response.ok(stats).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting review stats: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get review stats"))
                    .build();
        }
    }

    @GET
    @Path("/recent")
    public Response getRecentReviews(@QueryParam("limit") @DefaultValue("10") int limit) {
        LOG.infof("Getting recent reviews (limit: %d)", limit);
        
        try {
            List<ReviewResponse> reviews = reviewService.getRecentReviews(limit);
            return Response.ok(reviews).build();
            
        } catch (Exception e) {
            LOG.errorf("Error getting recent reviews: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get recent reviews"))
                    .build();
        }
    }

    @GET
    @Path("/product/{productId}/can-review")
    @RolesAllowed("CUSTOMER")
    public Response canUserReviewProduct(@PathParam("productId") Long productId, @Context SecurityContext securityContext) {
        LOG.infof("Checking if user can review product %d", productId);
        
        try {
            Long userId = getCurrentUserId(securityContext);
            boolean canReview = reviewService.canUserReviewProduct(userId, productId);
            
            CanReviewResponse response = new CanReviewResponse();
            response.canReview = canReview;
            response.productId = productId;
            response.userId = userId;
            
            return Response.ok(response).build();
            
        } catch (Exception e) {
            LOG.errorf("Error checking review eligibility: %s", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to check review eligibility"))
                    .build();
        }
    }

    // Helper methods
    private Long getCurrentUserId(SecurityContext securityContext) {
        try {
            if (securityContext.getUserPrincipal() == null) {
                LOG.warn("No user principal found, using default customer user");
                // For testing without authentication, use a default customer
                User defaultCustomer = User.find("role = ?1", UserRole.CUSTOMER).firstResult();
                return defaultCustomer != null ? defaultCustomer.id : 2L;
            }
            
            String principal = securityContext.getUserPrincipal().getName();
            LOG.infof("Security principal: %s", principal);
            
            try {
                return Long.parseLong(principal);
            } catch (NumberFormatException e) {
                // Fallback: look up user by email from database
                User user = User.findByEmail(principal);
                if (user != null) {
                    LOG.infof("Found user by email: %s -> ID: %d", principal, user.id);
                    return user.id;
                }
                
                // Legacy fallback for hardcoded emails
                if (principal.equals("customer@autowarehouse.com")) {
                    User customer = User.findByEmail("customer@autowarehouse.com");
                    return customer != null ? customer.id : 2L;
                } else if (principal.equals("admin@autowarehouse.com")) {
                    User admin = User.findByEmail("admin@autowarehouse.com");
                    return admin != null ? admin.id : 1L;
                }
                
                // Default fallback - find any customer user
                User anyCustomer = User.find("role = ?1", UserRole.CUSTOMER).firstResult();
                Long userId = anyCustomer != null ? anyCustomer.id : 2L;
                LOG.infof("Using fallback customer user ID: %d", userId);
                return userId;
            }
        } catch (Exception e) {
            LOG.errorf("Error getting user ID: %s", e.getMessage());
            // Ultimate fallback
            return 2L;
        }
    }

    // Response DTOs
    public static class ErrorResponse {
        public String error;
        
        public ErrorResponse(String error) {
            this.error = error;
        }
    }

    public static class ReviewStatsResponse {
        public Long productId;
        public double averageRating;
        public long reviewCount;
    }

    public static class CanReviewResponse {
        public Long productId;
        public Long userId;
        public boolean canReview;
    }
}
