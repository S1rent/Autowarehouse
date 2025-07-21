package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reviews", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "user_id"}))
public class Review extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    public User user;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    @Max(5)
    public Integer rating;

    @Column(length = 100)
    public String title;

    @Column(columnDefinition = "TEXT")
    public String comment;

    @Column(name = "is_verified_purchase")
    public Boolean isVerifiedPurchase = false;

    @Column(name = "helpful_count")
    public Integer helpfulCount = 0;

    @Column(name = "is_approved")
    public Boolean isApproved = true;

    // Temporarily commented out until migration V15 runs
    // @Column(name = "image_urls", columnDefinition = "TEXT")
    // public String imageUrls; // JSON array of image URLs from Firebase Storage

    // @Column(name = "video_urls", columnDefinition = "TEXT")
    // public String videoUrls; // JSON array of video URLs from Firebase Storage

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Constructors
    public Review() {}

    public Review(Product product, User user, Integer rating, String title, String comment) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
    }

    // Static finder methods
    public static Review findByProductAndUser(Product product, User user) {
        return find("product = ?1 and user = ?2", product, user).firstResult();
    }

    public static Review findByProductIdAndUserId(Long productId, Long userId) {
        return find("product.id = ?1 and user.id = ?2", productId, userId).firstResult();
    }

    public static List<Review> findByProduct(Product product) {
        return find("product = ?1 and isApproved = true order by createdAt desc", product).list();
    }

    public static List<Review> findByProductId(Long productId) {
        return find("product.id = ?1 and isApproved = true order by createdAt desc", productId).list();
    }

    public static List<Review> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Review> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<Review> findByRating(Integer rating) {
        return find("rating = ?1 and isApproved = true order by createdAt desc", rating).list();
    }

    public static List<Review> findVerifiedPurchases(Product product) {
        return find("product = ?1 and isVerifiedPurchase = true and isApproved = true order by createdAt desc", product).list();
    }

    public static List<Review> findPendingApproval() {
        return find("isApproved = false order by createdAt desc").list();
    }

    public static List<Review> findMostHelpful(Product product, int limit) {
        return find("product = ?1 and isApproved = true order by helpfulCount desc, createdAt desc", product)
                .page(0, limit).list();
    }

    public static List<Review> findRecent(Product product, int limit) {
        return find("product = ?1 and isApproved = true order by createdAt desc", product)
                .page(0, limit).list();
    }

    public static boolean existsByProductAndUser(Product product, User user) {
        return count("product = ?1 and user = ?2", product, user) > 0;
    }

    public static boolean existsByProductIdAndUserId(Long productId, Long userId) {
        return count("product.id = ?1 and user.id = ?2", productId, userId) > 0;
    }

    public static long countByProduct(Product product) {
        return count("product = ?1 and isApproved = true", product);
    }

    public static long countByProductId(Long productId) {
        return count("product.id = ?1 and isApproved = true", productId);
    }

    public static long countByRating(Product product, Integer rating) {
        return count("product = ?1 and rating = ?2 and isApproved = true", product, rating);
    }

    public static long countByRatingAndProductId(Long productId, Integer rating) {
        return count("product.id = ?1 and rating = ?2 and isApproved = true", productId, rating);
    }

    public static double getAverageRating(Product product) {
        Double avg = find("select avg(rating) from Review where product = ?1 and isApproved = true", product)
                .project(Double.class)
                .firstResult();
        return avg != null ? avg : 0.0;
    }

    public static double getAverageRatingByProductId(Long productId) {
        Double avg = find("select avg(rating) from Review where product.id = ?1 and isApproved = true", productId)
                .project(Double.class)
                .firstResult();
        return avg != null ? avg : 0.0;
    }

    // Helper methods
    public boolean canBeEdited() {
        return LocalDateTime.now().isBefore(createdAt.plusDays(7)); // Can edit within 7 days
    }

    public void incrementHelpfulCount() {
        this.helpfulCount++;
    }

    public void approve() {
        this.isApproved = true;
    }

    public void reject() {
        this.isApproved = false;
    }

    public void markAsVerifiedPurchase() {
        this.isVerifiedPurchase = true;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", productId=" + (product != null ? product.id : null) +
                ", userId=" + (user != null ? user.id : null) +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", isVerifiedPurchase=" + isVerifiedPurchase +
                ", isApproved=" + isApproved +
                ", createdAt=" + createdAt +
                '}';
    }
}
