package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews")
public class Review extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    public Integer rating;

    @Column(columnDefinition = "TEXT")
    @Size(max = 2000)
    public String comment;

    @Column(name = "is_verified_purchase")
    public Boolean isVerifiedPurchase = false;

    @Column(name = "is_approved")
    public Boolean isApproved = true;

    @Column(name = "helpful_count")
    public Integer helpfulCount = 0;

    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    public List<String> imageUrls;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public Order order; // Link to order for verified purchase

    // Constructors
    public Review() {}

    public Review(User user, Product product, Integer rating, String comment) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
    }

    // Static finder methods
    public static List<Review> findByProduct(Product product) {
        return find("product = ?1 and isApproved = true order by createdAt desc", product).list();
    }

    public static List<Review> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Review> findByProductAndRating(Product product, Integer rating) {
        return find("product = ?1 and rating = ?2 and isApproved = true order by createdAt desc", product, rating).list();
    }

    public static List<Review> findVerifiedPurchasesByProduct(Product product) {
        return find("product = ?1 and isVerifiedPurchase = true and isApproved = true order by createdAt desc", product).list();
    }

    public static List<Review> findPendingApproval() {
        return find("isApproved = false order by createdAt").list();
    }

    public static Review findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static long countByProduct(Product product) {
        return count("product = ?1 and isApproved = true", product);
    }

    public static long countByProductAndRating(Product product, Integer rating) {
        return count("product = ?1 and rating = ?2 and isApproved = true", product, rating);
    }

    public static BigDecimal getAverageRatingByProduct(Product product) {
        List<Review> reviews = findByProduct(product);
        if (reviews.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        double average = reviews.stream()
                .mapToInt(review -> review.rating)
                .average()
                .orElse(0.0);
        
        return BigDecimal.valueOf(average).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    // Helper methods
    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void approve() {
        this.isApproved = true;
    }

    public void reject() {
        this.isApproved = false;
    }

    public boolean canBeEditedBy(User user) {
        return this.user.id.equals(user.id) && 
               this.createdAt.isAfter(LocalDateTime.now().minusHours(24)); // Can edit within 24 hours
    }

    public String getRatingStars() {
        return "★".repeat(rating) + "☆".repeat(5 - rating);
    }

    public boolean hasImages() {
        return imageUrls != null && !imageUrls.isEmpty();
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", isVerifiedPurchase=" + isVerifiedPurchase +
                ", isApproved=" + isApproved +
                ", user=" + (user != null ? user.email : null) +
                ", product=" + (product != null ? product.name : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
