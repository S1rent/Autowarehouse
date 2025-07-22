package com.autowarehouse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.time.LocalDateTime;

@RegisterForReflection
public class CustomerServiceEvent {
    
    @JsonProperty("eventType")
    private String eventType;
    
    @JsonProperty("chatId")
    private Long chatId;
    
    @JsonProperty("senderId")
    private Long senderId;
    
    @JsonProperty("receiverId")
    private Long receiverId;
    
    @JsonProperty("messageContent")
    private String messageContent;
    
    @JsonProperty("senderType")
    private String senderType; // "CUSTOMER" or "ADMIN"
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("metadata")
    private String metadata;

    // Default constructor
    public CustomerServiceEvent() {
        this.timestamp = LocalDateTime.now();
    }

    // Constructor with required fields
    public CustomerServiceEvent(String eventType, Long chatId, Long senderId, Long receiverId, 
                              String messageContent, String senderType) {
        this();
        this.eventType = eventType;
        this.chatId = chatId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageContent = messageContent;
        this.senderType = senderType;
    }

    // Full constructor
    public CustomerServiceEvent(String eventType, Long chatId, Long senderId, Long receiverId, 
                              String messageContent, String senderType, String metadata) {
        this(eventType, chatId, senderId, receiverId, messageContent, senderType);
        this.metadata = metadata;
    }

    // Getters and Setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
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
        return "CustomerServiceEvent{" +
                "eventType='" + eventType + '\'' +
                ", chatId=" + chatId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", messageContent='" + messageContent + '\'' +
                ", senderType='" + senderType + '\'' +
                ", timestamp=" + timestamp +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
