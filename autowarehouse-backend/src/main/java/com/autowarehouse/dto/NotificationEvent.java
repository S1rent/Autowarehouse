package com.autowarehouse.dto;

import com.autowarehouse.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class NotificationEvent {
    
    @JsonProperty("eventType")
    private String eventType;
    
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("notificationType")
    private NotificationType notificationType;
    
    @JsonProperty("referenceId")
    private Long referenceId;
    
    @JsonProperty("referenceType")
    private String referenceType;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("metadata")
    private String metadata;

    // Default constructor
    public NotificationEvent() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with required fields
    public NotificationEvent(String eventType, Long userId, String title, String message, 
                           NotificationType notificationType) {
        this();
        this.eventType = eventType;
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
    }

    // Full constructor
    public NotificationEvent(String eventType, Long userId, String title, String message, 
                           NotificationType notificationType, Long referenceId, 
                           String referenceType, String metadata) {
        this(eventType, userId, title, message, notificationType);
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.metadata = metadata;
    }

    // Getters and Setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
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
        return "NotificationEvent{" +
                "eventType='" + eventType + '\'' +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", notificationType=" + notificationType +
                ", referenceId=" + referenceId +
                ", referenceType='" + referenceType + '\'' +
                ", timestamp=" + timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
