package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist_items", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class WishlistItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public WishlistItem() {}

    public WishlistItem(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    // Static finder methods
    public static WishlistItem findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static WishlistItem findByUserIdAndProductId(Long userId, Long productId) {
        return find("user.id = ?1 and product.id = ?2", userId, productId).firstResult();
    }

    public static java.util.List<WishlistItem> findByUser(User user) {
        return find("user", user).list();
    }

    public static java.util.List<WishlistItem> findByUserId(Long userId) {
        return find("user.id", userId).list();
    }

    public static boolean existsByUserAndProduct(User user, Product product) {
        return count("user = ?1 and product = ?2", user, product) > 0;
    }

    public static boolean existsByUserIdAndProductId(Long userId, Long productId) {
        return count("user.id = ?1 and product.id = ?2", userId, productId) > 0;
    }

    public static long countByUser(User user) {
        return count("user", user);
    }

    public static long countByUserId(Long userId) {
        return count("user.id", userId);
    }

    @Override
    public String toString() {
        return "WishlistItem{" +
                "id=" + id +
                ", userId=" + (user != null ? user.id : null) +
                ", productId=" + (product != null ? product.id : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
