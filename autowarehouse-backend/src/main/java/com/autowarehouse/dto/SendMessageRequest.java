package com.autowarehouse.dto;

import com.autowarehouse.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendMessageRequest {

    @NotNull
    public Long ticketId;

    @NotBlank
    public String message;

    public MessageType messageType = MessageType.TEXT;

    // Constructors
    public SendMessageRequest() {}

    public SendMessageRequest(Long ticketId, String message) {
        this.ticketId = ticketId;
        this.message = message;
    }

    public SendMessageRequest(Long ticketId, String message, MessageType messageType) {
        this.ticketId = ticketId;
        this.message = message;
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "ticketId=" + ticketId +
                ", messageType=" + messageType +
                ", message='" + (message.length() > 50 ? message.substring(0, 50) + "..." : message) + '\'' +
                '}';
    }
}
