package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    // Constructors
    public WishlistItem() {}

    public WishlistItem(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    // Static finder methods
    public static List<WishlistItem> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static WishlistItem findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static List<WishlistItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static long countByUser(User user) {
        return count("user", user);
    }

    public static long countByProduct(Product product) {
        return count("product", product);
    }

    public static boolean existsByUserAndProduct(User user, Product product) {
        return count("user = ?1 and product = ?2", user, product) > 0;
    }

    // Helper methods
    public boolean isProductAvailable() {
        return product != null && product.isActive && product.isInStock();
    }

    public boolean isProductOnSale() {
        return product != null && product.isOnSaleNow();
    }

    @Override
    public String toString() {
        return "WishlistItem{" +
                "id=" + id +
                ", product=" + (product != null ? product.name : null) +
                ", user=" + (user != null ? user.email : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
