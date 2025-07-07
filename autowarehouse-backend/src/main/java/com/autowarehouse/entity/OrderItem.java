package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(name = "product_name", nullable = false, length = 200)
    @NotBlank
    public String productName;

    @Column(name = "product_sku", nullable = false, length = 100)
    @NotBlank
    public String productSku;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal productPrice;

    @Column(nullable = false)
    @NotNull
    public Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public OrderItem() {}

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.productName = product.name;
        this.productSku = product.sku;
        this.productPrice = product.getCurrentPrice();
        this.quantity = quantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Static finder methods
    public static List<OrderItem> findByOrder(Order order) {
        return find("order", order).list();
    }

    public static List<OrderItem> findByOrderId(Long orderId) {
        return find("order.id", orderId).list();
    }

    public static List<OrderItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static List<OrderItem> findByProductId(Long productId) {
        return find("product.id", productId).list();
    }

    public static long countByProduct(Product product) {
        return count("product", product);
    }

    public static long countByProductId(Long productId) {
        return count("product.id", productId);
    }

    // Helper methods
    public BigDecimal getItemTotal() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(newQuantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.id : null) +
                ", productName='" + productName + '\'' +
                ", productSku='" + productSku + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
