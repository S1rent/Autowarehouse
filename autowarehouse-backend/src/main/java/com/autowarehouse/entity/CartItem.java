package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart_items")
public class CartItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    public Integer quantity;

    @Column(name = "is_selected")
    public Boolean isSelected = true;

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

    // Constructors
    public CartItem() {}

    public CartItem(User user, Product product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    // Static finder methods
    public static List<CartItem> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<CartItem> findSelectedByUser(User user) {
        return find("user = ?1 and isSelected = true order by createdAt desc", user).list();
    }

    public static CartItem findByUserAndProduct(User user, Product product) {
        return find("user = ?1 and product = ?2", user, product).firstResult();
    }

    public static List<CartItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static long countByUser(User user) {
        return count("user", user);
    }

    public static long countSelectedByUser(User user) {
        return count("user = ?1 and isSelected = true", user);
    }

    // Helper methods
    public BigDecimal getSubtotal() {
        if (product == null) return BigDecimal.ZERO;
        return product.getCurrentPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal getTotalSavings() {
        if (product == null || !product.isOnSaleNow()) return BigDecimal.ZERO;
        return product.getDiscountAmount().multiply(BigDecimal.valueOf(quantity));
    }

    public boolean isProductAvailable() {
        return product != null && product.isActive && product.isInStock();
    }

    public boolean canIncreaseQuantity() {
        return product != null && quantity < product.stockQuantity;
    }

    public void increaseQuantity() {
        if (canIncreaseQuantity()) {
            this.quantity++;
        }
    }

    public void decreaseQuantity() {
        if (this.quantity > 1) {
            this.quantity--;
        }
    }

    public void updateQuantity(Integer newQuantity) {
        if (newQuantity > 0 && product != null && newQuantity <= product.stockQuantity) {
            this.quantity = newQuantity;
        }
    }

    public void toggleSelection() {
        this.isSelected = !this.isSelected;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", isSelected=" + isSelected +
                ", product=" + (product != null ? product.name : null) +
                ", user=" + (user != null ? user.email : null) +
                '}';
    }
}
