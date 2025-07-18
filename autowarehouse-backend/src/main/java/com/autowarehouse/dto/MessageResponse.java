package com.autowarehouse.dto;

import com.autowarehouse.entity.ChatMessage;
import com.autowarehouse.entity.MessageType;
import com.autowarehouse.entity.SenderType;
import java.time.LocalDateTime;

public class MessageResponse {

    public Long id;
    public Long ticketId;
    public Long senderId;
    public String senderName;
    public SenderType senderType;
    public String message;
    public MessageType messageType;
    public LocalDateTime timestamp;
    public Boolean isRead;

    // Constructors
    public MessageResponse() {}

    public MessageResponse(ChatMessage chatMessage) {
        this.id = chatMessage.id;
        this.ticketId = chatMessage.ticketId;
        this.senderId = chatMessage.senderId;
        this.senderType = chatMessage.senderType;
        this.message = chatMessage.message;
        this.messageType = chatMessage.messageType;
        this.timestamp = chatMessage.timestamp;
        this.isRead = chatMessage.isRead;
        
        // Set sender name if available
        if (chatMessage.sender != null) {
            this.senderName = chatMessage.sender.getFullName();
        }
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", senderType=" + senderType +
                ", messageType=" + messageType +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", message='" + (message.length() > 50 ? message.substring(0, 50) + "..." : message) + '\'' +
                '}';
    }
}
