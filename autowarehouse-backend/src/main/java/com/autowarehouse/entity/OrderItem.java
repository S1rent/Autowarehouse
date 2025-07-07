package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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

    @Column(nullable = false)
    @NotNull
    @Min(1)
    public Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal price;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    public BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    public Auction auction; // If item is from won auction

    // Constructors
    public OrderItem() {}

    public OrderItem(Product product, Integer quantity, BigDecimal price, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        calculateTotalPrice();
    }

    // Static finder methods
    public static List<OrderItem> findByOrder(Order order) {
        return find("order", order).list();
    }

    public static List<OrderItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static List<OrderItem> findByAuction(Auction auction) {
        return find("auction", auction).list();
    }

    // Helper methods
    public void calculateTotalPrice() {
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));
        this.totalPrice = subtotal.subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public boolean isFromAuction() {
        return auction != null;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", product=" + (product != null ? product.name : null) +
                '}';
    }
}
