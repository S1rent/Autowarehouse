package com.autowarehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class OrderEvent {
    
    @JsonProperty("eventType")
    private String eventType;
    
    @JsonProperty("orderId")
    private Long orderId;
    
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("orderStatus")
    private String orderStatus;
    
    @JsonProperty("previousStatus")
    private String previousStatus;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("metadata")
    private String metadata;

    // Default constructor
    public OrderEvent() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with required fields
    public OrderEvent(String eventType, Long orderId, Long userId, String orderStatus) {
        this();
        this.eventType = eventType;
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    // Full constructor
    public OrderEvent(String eventType, Long orderId, Long userId, String orderStatus, 
                     String previousStatus, String metadata) {
        this(eventType, orderId, userId, orderStatus);
        this.previousStatus = previousStatus;
        this.metadata = metadata;
    }

    // Getters and Setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "eventType='" + eventType + '\'' +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", orderStatus='" + orderStatus + '\'' +
                ", previousStatus='" + previousStatus + '\'' +
                ", timestamp=" + timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
