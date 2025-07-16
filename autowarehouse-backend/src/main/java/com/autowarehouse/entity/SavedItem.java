package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "saved_items", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class SavedItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(nullable = false)
    @NotNull
    public Integer quantity = 1;

    @Column(name = "saved_from_cart")
    public Boolean savedFromCart = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Constructors
    public SavedItem() {}

    public SavedItem(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public SavedItem(User user, Product product, Integer quantity, Boolean savedFromCart) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.savedFromCart = savedFromCart;
    }

    // Static finder methods
    public static SavedItem findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static SavedItem findByUserIdAndProductId(Long userId, Long productId) {
        return find("user.id = ?1 and product.id = ?2", userId, productId).firstResult();
    }

    public static List<SavedItem> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<SavedItem> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<SavedItem> findByUserAndSavedFromCart(User user, boolean savedFromCart) {
        return find("user = ?1 and savedFromCart = ?2 order by createdAt desc", user, savedFromCart).list();
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

    public static void deleteByUser(User user) {
        delete("user", user);
    }

    public static void deleteByUserId(Long userId) {
        delete("user.id", userId);
    }

    public static void deleteByUserAndProduct(User user, Product product) {
        delete("user = ?1 and product = ?2", user, product);
    }

    public static void deleteByUserIdAndProductId(Long userId, Long productId) {
        delete("user.id = ?1 and product.id = ?2", userId, productId);
    }

    // Helper methods
    public BigDecimal getCurrentPrice() {
        if (product == null) return BigDecimal.ZERO;
        return product.getCurrentPrice();
    }

    public BigDecimal getSubtotal() {
        if (product == null) return BigDecimal.ZERO;
        return product.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getSavings() {
        if (product == null || !product.isOnSaleNow()) return BigDecimal.ZERO;
        BigDecimal savings = product.getSavings();
        return savings.multiply(BigDecimal.valueOf(quantity));
    }

    public boolean isProductAvailable() {
        return product != null && product.isActive && product.isInStock();
    }

    public boolean hasEnoughStock() {
        return product != null && product.stockQuantity >= quantity;
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
    }

    public CartItem toCartItem() {
        return new CartItem(this.user, this.product, this.quantity);
    }

    @Override
    public String toString() {
        return "SavedItem{" +
                "id=" + id +
                ", userId=" + (user != null ? user.id : null) +
                ", productId=" + (product != null ? product.id : null) +
                ", quantity=" + quantity +
                ", savedFromCart=" + savedFromCart +
                ", createdAt=" + createdAt +
                '}';
    }
}
