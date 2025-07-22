package com.autowarehouse.service;

import com.autowarehouse.dto.CreateReviewRequest;
import com.autowarehouse.dto.ReviewResponse;
import com.autowarehouse.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReviewService {

    private static final Logger LOG = Logger.getLogger(ReviewService.class);

    @Inject
    ObjectMapper objectMapper;

    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, Long userId) {
        LOG.infof("Creating review for product %d by user %d, order %d", request.productId, userId, request.orderId);

        // Validate user exists
        User user = User.findById(userId);
        if (user == null) {
            LOG.errorf("User not found: %d", userId);
            throw new NotFoundException("User not found");
        }
        LOG.infof("User found: %s", user.email);

        // Validate product exists
        Product product = Product.findById(request.productId);
        if (product == null) {
            LOG.errorf("Product not found: %d", request.productId);
            throw new NotFoundException("Product not found");
        }
        LOG.infof("Product found: %s", product.name);

        // Validate order exists and belongs to user
        Order order = Order.findById(request.orderId);
        if (order == null) {
            LOG.errorf("Order not found: %d", request.orderId);
            throw new NotFoundException("Order not found");
        }
        LOG.infof("Order found: %s, status: %s, user: %d", order.orderNumber, order.status, order.user.id);

        if (!order.user.id.equals(userId)) {
            LOG.errorf("Order %d belongs to user %d, not %d", request.orderId, order.user.id, userId);
            throw new BadRequestException("Order does not belong to user");
        }

        // Check if order is delivered
        if (order.status != Order.OrderStatus.DELIVERED) {
            LOG.errorf("Order %d status is %s, not DELIVERED", request.orderId, order.status);
            throw new BadRequestException("Can only review delivered orders");
        }

        // Check if user has already reviewed this product for this specific order
        Review existingReview = Review.findByProductIdUserIdAndOrderId(request.productId, userId, request.orderId);
        if (existingReview != null) {
            LOG.errorf("User %d already reviewed product %d for order %d", userId, request.productId, request.orderId);
            throw new BadRequestException("You have already reviewed this product for this order");
        }

        // Verify that the product was actually in the order
        LOG.infof("Checking if product %d is in order %d items", request.productId, request.orderId);
        if (order.items == null || order.items.isEmpty()) {
            LOG.errorf("Order %d has no items", request.orderId);
            throw new BadRequestException("Order has no items");
        }
        
        boolean productInOrder = order.items.stream()
                .anyMatch(item -> item.product.id.equals(request.productId));
        
        if (!productInOrder) {
            LOG.errorf("Product %d was not found in order %d", request.productId, request.orderId);
            throw new BadRequestException("Product was not in the specified order");
        }
        LOG.infof("Product %d confirmed in order %d", request.productId, request.orderId);

        // Create review
        Review review = new Review();
        review.product = product;
        review.user = user;
        review.order = order;
        review.rating = request.rating;
        review.title = request.title;
        review.comment = request.comment;
        review.isVerifiedPurchase = true; // Since it's from a delivered order
        review.isApproved = true; // Auto-approve for now

        // Handle image URLs
        if (request.imageUrls != null && !request.imageUrls.isEmpty()) {
            try {
                review.imageUrls = objectMapper.writeValueAsString(request.imageUrls);
                LOG.infof("Saved %d image URLs for review", request.imageUrls.size());
            } catch (JsonProcessingException e) {
                LOG.warnf("Failed to serialize image URLs: %s", e.getMessage());
                review.imageUrls = "[]";
            }
        } else {
            review.imageUrls = "[]";
        }

        // Handle video URLs
        if (request.videoUrls != null && !request.videoUrls.isEmpty()) {
            try {
                review.videoUrls = objectMapper.writeValueAsString(request.videoUrls);
                LOG.infof("Saved %d video URLs for review", request.videoUrls.size());
            } catch (JsonProcessingException e) {
                LOG.warnf("Failed to serialize video URLs: %s", e.getMessage());
                review.videoUrls = "[]";
            }
        } else {
            review.videoUrls = "[]";
        }

        review.persist();
        LOG.infof("Created review %d for product %d", review.id, request.productId);

        return new ReviewResponse(review);
    }

    public List<ReviewResponse> getProductReviews(Long productId) {
        LOG.infof("Getting reviews for product %d", productId);

        Product product = Product.findById(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        List<Review> reviews = Review.findByProductId(productId);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getUserReviews(Long userId) {
        LOG.infof("Getting reviews by user %d", userId);

        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        List<Review> reviews = Review.findByUserId(userId);
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    public ReviewResponse getReview(Long reviewId) {
        LOG.infof("Getting review %d", reviewId);

        Review review = Review.findById(reviewId);
        if (review == null) {
            throw new NotFoundException("Review not found");
        }

        return new ReviewResponse(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        LOG.infof("Deleting review %d by user %d", reviewId, userId);

        Review review = Review.findById(reviewId);
        if (review == null) {
            throw new NotFoundException("Review not found");
        }

        // Only allow user to delete their own review or admin
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }

        if (!review.user.id.equals(userId) && !user.role.equals(UserRole.ADMIN)) {
            throw new BadRequestException("You can only delete your own reviews");
        }

        review.delete();
        LOG.infof("Deleted review %d", reviewId);
    }

    @Transactional
    public ReviewResponse markHelpful(Long reviewId) {
        LOG.infof("Marking review %d as helpful", reviewId);

        Review review = Review.findById(reviewId);
        if (review == null) {
            throw new NotFoundException("Review not found");
        }

        review.incrementHelpfulCount();
        review.persist();

        return new ReviewResponse(review);
    }

    public double getProductAverageRating(Long productId) {
        return Review.getAverageRatingByProductId(productId);
    }

    public long getProductReviewCount(Long productId) {
        return Review.countByProductId(productId);
    }

    public List<ReviewResponse> getRecentReviews(int limit) {
        List<Review> reviews = Review.find("isApproved = true order by createdAt desc")
                .page(0, limit)
                .list();
        
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    public List<ReviewResponse> getTopRatedReviews(Long productId, int limit) {
        List<Review> reviews = Review.find("product.id = ?1 and isApproved = true order by rating desc, helpfulCount desc, createdAt desc", productId)
                .page(0, limit)
                .list();
        
        return reviews.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());
    }

    public boolean canUserReviewProduct(Long userId, Long productId) {
        // Check if user has purchased and received this product
        List<Order> userOrders = Order.find("user.id = ?1 and status = ?2", userId, Order.OrderStatus.DELIVERED).list();
        
        return userOrders.stream()
                .flatMap(order -> order.items.stream())
                .anyMatch(item -> item.product.id.equals(productId));
    }

    public boolean canUserReviewProductForOrder(Long userId, Long productId, Long orderId) {
        // Check if user has already reviewed this product for this specific order
        if (Review.findByProductIdUserIdAndOrderId(productId, userId, orderId) != null) {
            return false;
        }

        // Check if the order exists, belongs to user, is delivered, and contains the product
        Order order = Order.findById(orderId);
        if (order == null || !order.user.id.equals(userId) || order.status != Order.OrderStatus.DELIVERED) {
            return false;
        }

        return order.items.stream()
                .anyMatch(item -> item.product.id.equals(productId));
    }
}
