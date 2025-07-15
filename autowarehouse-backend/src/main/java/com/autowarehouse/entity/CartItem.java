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
@Table(name = "cart_items", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "product_id"}))
public class CartItem extends PanacheEntityBase {

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

    @Column(name = "is_selected")
    public Boolean isSelected = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Constructors
    public CartItem() {}

    public CartItem(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    // Static finder methods
    public static CartItem findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static CartItem findByUserIdAndProductId(Long userId, Long productId) {
        return find("user.id = ?1 and product.id = ?2", userId, productId).firstResult();
    }

    public static List<CartItem> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<CartItem> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<CartItem> findSelectedByUser(User user) {
        return find("user = ?1 and isSelected = true order by createdAt desc", user).list();
    }

    public static List<CartItem> findSelectedByUserId(Long userId) {
        return find("user.id = ?1 and isSelected = true order by createdAt desc", userId).list();
    }

    public static List<CartItem> findByUserAndSelected(User user, boolean selected) {
        return find("user = ?1 and isSelected = ?2 order by createdAt desc", user, selected).list();
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

    public static long countSelectedByUser(User user) {
        return count("user = ?1 and isSelected = true", user);
    }

    public static long countSelectedByUserId(Long userId) {
        return count("user.id = ?1 and isSelected = true", userId);
    }

    public static void deleteByUser(User user) {
        delete("user", user);
    }

    public static void deleteByUserId(Long userId) {
        delete("user.id", userId);
    }

    public static void selectAllByUser(User user, boolean selected) {
        update("isSelected = ?1 where user = ?2", selected, user);
    }

    public static void selectAllByUserId(Long userId, boolean selected) {
        update("isSelected = ?1 where user.id = ?2", selected, userId);
    }

    // Helper methods
    public BigDecimal getSubtotal() {
        if (product == null) return BigDecimal.ZERO;
        return product.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getSavings() {
        if (product == null || !product.isOnSaleNow()) return BigDecimal.ZERO;
        BigDecimal savings = product.getSavings();
        return savings.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getTotalSavings() {
        return getSavings();
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
        if (product != null && newQuantity > product.stockQuantity) {
            throw new IllegalArgumentException("Insufficient stock available");
        }
        this.quantity = newQuantity;
    }

    public void increaseQuantity(int amount) {
        updateQuantity(this.quantity + amount);
    }

    public void decreaseQuantity(int amount) {
        updateQuantity(this.quantity - amount);
    }

    public void toggleSelection() {
        this.isSelected = !this.isSelected;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", userId=" + (user != null ? user.id : null) +
                ", productId=" + (product != null ? product.id : null) +
                ", quantity=" + quantity +
                ", isSelected=" + isSelected +
                ", createdAt=" + createdAt +
                '}';
    }
}
