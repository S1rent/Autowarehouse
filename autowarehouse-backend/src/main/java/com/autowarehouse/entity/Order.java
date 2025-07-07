package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    public User user;

    @Column(name = "order_number", unique = true, nullable = false, length = 50)
    @NotBlank
    public String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull
    public OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @NotNull
    public PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "shipping_cost", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal shippingCost = BigDecimal.ZERO;

    @Column(name = "discount_amount", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal totalAmount;

    @Column(name = "shipping_address", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    public String shippingAddress;

    @Column(name = "billing_address", columnDefinition = "TEXT")
    public String billingAddress;

    @Column(name = "payment_method", length = 50)
    public String paymentMethod;

    @Column(name = "payment_reference")
    public String paymentReference;

    @Column(name = "tracking_number")
    public String trackingNumber;

    @Column(columnDefinition = "TEXT")
    public String notes;

    @Column(name = "shipped_at")
    public LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    public LocalDateTime deliveredAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<OrderItem> items;

    // Constructors
    public Order() {}

    public Order(User user, String orderNumber, BigDecimal subtotal, BigDecimal totalAmount, String shippingAddress) {
        this.user = user;
        this.orderNumber = orderNumber;
        this.subtotal = subtotal;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }

    // Enums
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }

    public enum PaymentStatus {
        PENDING, PAID, FAILED, REFUNDED, PARTIALLY_REFUNDED
    }

    // Static finder methods
    public static Order findByOrderNumber(String orderNumber) {
        return find("orderNumber", orderNumber).firstResult();
    }

    public static List<Order> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Order> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<Order> findByStatus(OrderStatus status) {
        return find("status = ?1 order by createdAt desc", status).list();
    }

    public static List<Order> findByStatus(String status) {
        return find("status = ?1 order by createdAt desc", OrderStatus.valueOf(status.toUpperCase())).list();
    }

    public static List<Order> findByPaymentStatus(PaymentStatus paymentStatus) {
        return find("paymentStatus = ?1 order by createdAt desc", paymentStatus).list();
    }

    public static List<Order> findRecentOrders(int limit) {
        return find("order by createdAt desc").page(0, limit).list();
    }

    public static List<Order> findPendingOrders() {
        return find("status = ?1 order by createdAt desc", OrderStatus.PENDING).list();
    }

    public static List<Order> findCompletedOrders() {
        return find("status = ?1 order by createdAt desc", OrderStatus.DELIVERED).list();
    }

    public static long countByStatus(OrderStatus status) {
        return count("status", status);
    }

    public static long countPendingOrders() {
        return count("status", OrderStatus.PENDING);
    }

    public static long countCompletedOrders() {
        return count("status", OrderStatus.DELIVERED);
    }

    public static BigDecimal getTotalRevenue() {
        return find("select sum(totalAmount) from Order where paymentStatus = ?1", PaymentStatus.PAID)
                .project(BigDecimal.class)
                .firstResult();
    }

    // Helper methods
    public boolean isPending() {
        return status == OrderStatus.PENDING;
    }

    public boolean isConfirmed() {
        return status == OrderStatus.CONFIRMED;
    }

    public boolean isShipped() {
        return status == OrderStatus.SHIPPED;
    }

    public boolean isDelivered() {
        return status == OrderStatus.DELIVERED;
    }

    public boolean isCancelled() {
        return status == OrderStatus.CANCELLED;
    }

    public boolean isPaid() {
        return paymentStatus == PaymentStatus.PAID;
    }

    public boolean canBeCancelled() {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public boolean canBeShipped() {
        return status == OrderStatus.CONFIRMED && paymentStatus == PaymentStatus.PAID;
    }

    public boolean canBeDelivered() {
        return status == OrderStatus.SHIPPED;
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        if (newStatus == OrderStatus.SHIPPED && shippedAt == null) {
            shippedAt = LocalDateTime.now();
        } else if (newStatus == OrderStatus.DELIVERED && deliveredAt == null) {
            deliveredAt = LocalDateTime.now();
        }
    }

    public void updatePaymentStatus(PaymentStatus newPaymentStatus, String reference) {
        this.paymentStatus = newPaymentStatus;
        this.paymentReference = reference;
    }

    public void ship(String trackingNumber) {
        if (!canBeShipped()) {
            throw new IllegalStateException("Order cannot be shipped in current state");
        }
        updateStatus(OrderStatus.SHIPPED);
        this.paymentReference = trackingNumber; // Using payment reference for tracking
    }

    public void deliver() {
        if (!canBeDelivered()) {
            throw new IllegalStateException("Order cannot be delivered in current state");
        }
        updateStatus(OrderStatus.DELIVERED);
    }

    public void cancel() {
        if (!canBeCancelled()) {
            throw new IllegalStateException("Order cannot be cancelled in current state");
        }
        updateStatus(OrderStatus.CANCELLED);
    }

    public int getTotalItems() {
        return items != null ? items.stream().mapToInt(item -> item.quantity).sum() : 0;
    }

    public void calculateTotals() {
        if (items == null || items.isEmpty()) {
            this.subtotal = BigDecimal.ZERO;
            this.totalAmount = BigDecimal.ZERO;
            return;
        }

        this.subtotal = items.stream()
                .map(item -> item.calculateTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = this.subtotal
                .add(this.taxAmount)
                .add(this.shippingCost)
                .subtract(this.discountAmount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", status=" + status +
                ", paymentStatus=" + paymentStatus +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                '}';
    }
}
