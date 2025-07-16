package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Order.OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 50)
    private Order.OrderStatus previousStatus;
    
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;
    
    @Column(name = "changed_by")
    private String changedBy; // User ID or system identifier
    
    @Column(name = "notes", length = 500)
    private String notes;
    
    @Column(name = "tracking_number")
    private String trackingNumber;
    
    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;
    
    @Column(name = "actual_delivery")
    private LocalDateTime actualDelivery;
    
    // Constructors
    public OrderStatusHistory() {}
    
    public OrderStatusHistory(Order order, Order.OrderStatus status, Order.OrderStatus previousStatus, 
                            LocalDateTime changedAt, String changedBy) {
        this.order = order;
        this.status = status;
        this.previousStatus = previousStatus;
        this.changedAt = changedAt;
        this.changedBy = changedBy;
    }
    
    public OrderStatusHistory(Order order, Order.OrderStatus status, Order.OrderStatus previousStatus, 
                            LocalDateTime changedAt, String changedBy, String notes) {
        this(order, status, previousStatus, changedAt, changedBy);
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order.OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
    
    public Order.OrderStatus getPreviousStatus() {
        return previousStatus;
    }
    
    public void setPreviousStatus(Order.OrderStatus previousStatus) {
        this.previousStatus = previousStatus;
    }
    
    public LocalDateTime getChangedAt() {
        return changedAt;
    }
    
    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
    
    public String getChangedBy() {
        return changedBy;
    }
    
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    
    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }
    
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }
    
    public LocalDateTime getActualDelivery() {
        return actualDelivery;
    }
    
    public void setActualDelivery(LocalDateTime actualDelivery) {
        this.actualDelivery = actualDelivery;
    }
    
    @Override
    public String toString() {
        return "OrderStatusHistory{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.getId() : null) +
                ", status=" + status +
                ", previousStatus=" + previousStatus +
                ", changedAt=" + changedAt +
                ", changedBy='" + changedBy + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
