package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "order_number", unique = true, nullable = false)
    @NotBlank
    @Size(max = 50)
    public String orderNumber;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0")
    public BigDecimal subtotal;

    @Column(name = "tax_amount", precision = 12, scale = 2)
    public BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "shipping_cost", precision = 12, scale = 2)
    public BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "discount_amount", precision = 12, scale = 2)
    public BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0")
    public BigDecimal totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus status = OrderStatus.PENDING;

    @Column(name = "payment_method")
    @Size(max = 50)
    public String paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    public PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    public String shippingAddress;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    public String billingAddress;

    @Column(name = "tracking_number")
    @Size(max = 100)
    public String trackingNumber;

    @Column(name = "shipped_at")
    public LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    public LocalDateTime deliveredAt;

    @Column(columnDefinition = "TEXT")
    public String notes;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> orderItems;

    // Constructors
    public Order() {}

    public Order(String orderNumber, User user) {
        this.orderNumber = orderNumber;
        this.user = user;
    }

    // Static finder methods
    public static List<Order> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Order> findByStatus(OrderStatus status) {
        return find("status = ?1 order by createdAt desc", status).list();
    }

    public static Order findByOrderNumber(String orderNumber) {
        return find("orderNumber", orderNumber).firstResult();
    }

    public static List<Order> findPendingOrders() {
        return find("status = ?1 order by createdAt", OrderStatus.PENDING).list();
    }

    public static List<Order> findRecentOrders(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return find("createdAt >= ?1 order by createdAt desc", since).list();
    }

    // Helper methods
    public void calculateTotals() {
        this.subtotal = orderItems.stream()
                .map(item -> item.price.multiply(BigDecimal.valueOf(item.quantity)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.totalAmount = subtotal
                .add(taxAmount != null ? taxAmount : BigDecimal.ZERO)
                .add(shippingCost != null ? shippingCost : BigDecimal.ZERO)
                .subtract(discountAmount != null ? discountAmount : BigDecimal.ZERO);
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        
        if (newStatus == OrderStatus.SHIPPED && shippedAt == null) {
            this.shippedAt = LocalDateTime.now();
        } else if (newStatus == OrderStatus.DELIVERED && deliveredAt == null) {
            this.deliveredAt = LocalDateTime.now();
        }
    }

    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public boolean isCompleted() {
        return status == OrderStatus.DELIVERED;
    }

    public int getTotalItems() {
        return orderItems.stream().mapToInt(item -> item.quantity).sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
