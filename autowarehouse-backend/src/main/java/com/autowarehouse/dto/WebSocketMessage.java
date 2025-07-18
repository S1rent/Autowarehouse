package com.autowarehouse.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebSocketMessage {

    public enum Type {
        // Connection events
        JOIN_ROOM,
        LEAVE_ROOM,
        USER_ONLINE,
        USER_OFFLINE,
        
        // Chat events
        SEND_MESSAGE,
        RECEIVE_MESSAGE,
        MESSAGE_READ,
        
        // Typing events
        TYPING_START,
        TYPING_STOP,
        
        // Ticket events
        TICKET_CREATED,
        TICKET_UPDATED,
        AGENT_ASSIGNED,
        TICKET_STATUS_CHANGED,
        
        // System events
        ERROR,
        SUCCESS,
        NOTIFICATION
    }

    public Type type;
    public Long ticketId;
    public Long userId;
    public String userName;
    public String message;
    public Object data;
    public LocalDateTime timestamp;
    public Map<String, Object> metadata;

    // Constructors
    public WebSocketMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(Type type) {
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(Type type, Long ticketId) {
        this.type = type;
        this.ticketId = ticketId;
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(Type type, Long ticketId, Long userId, String userName) {
        this.type = type;
        this.ticketId = ticketId;
        this.userId = userId;
        this.userName = userName;
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(Type type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(Type type, Object data) {
        this.type = type;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Static factory methods
    public static WebSocketMessage joinRoom(Long ticketId, Long userId, String userName) {
        return new WebSocketMessage(Type.JOIN_ROOM, ticketId, userId, userName);
    }

    public static WebSocketMessage leaveRoom(Long ticketId, Long userId, String userName) {
        return new WebSocketMessage(Type.LEAVE_ROOM, ticketId, userId, userName);
    }

    public static WebSocketMessage userOnline(Long userId, String userName) {
        WebSocketMessage msg = new WebSocketMessage(Type.USER_ONLINE);
        msg.userId = userId;
        msg.userName = userName;
        return msg;
    }

    public static WebSocketMessage userOffline(Long userId, String userName) {
        WebSocketMessage msg = new WebSocketMessage(Type.USER_OFFLINE);
        msg.userId = userId;
        msg.userName = userName;
        return msg;
    }

    public static WebSocketMessage newMessage(MessageResponse messageResponse) {
        WebSocketMessage msg = new WebSocketMessage(Type.RECEIVE_MESSAGE);
        msg.ticketId = messageResponse.ticketId;
        msg.userId = messageResponse.senderId;
        msg.userName = messageResponse.senderName;
        msg.data = messageResponse;
        return msg;
    }

    public static WebSocketMessage typingStart(Long ticketId, Long userId, String userName) {
        return new WebSocketMessage(Type.TYPING_START, ticketId, userId, userName);
    }

    public static WebSocketMessage typingStop(Long ticketId, Long userId, String userName) {
        return new WebSocketMessage(Type.TYPING_STOP, ticketId, userId, userName);
    }

    public static WebSocketMessage ticketCreated(TicketResponse ticket) {
        WebSocketMessage msg = new WebSocketMessage(Type.TICKET_CREATED);
        msg.ticketId = ticket.id;
        msg.data = ticket;
        return msg;
    }

    public static WebSocketMessage ticketUpdated(TicketResponse ticket) {
        WebSocketMessage msg = new WebSocketMessage(Type.TICKET_UPDATED);
        msg.ticketId = ticket.id;
        msg.data = ticket;
        return msg;
    }

    public static WebSocketMessage agentAssigned(Long ticketId, Long agentId, String agentName) {
        WebSocketMessage msg = new WebSocketMessage(Type.AGENT_ASSIGNED);
        msg.ticketId = ticketId;
        msg.userId = agentId;
        msg.userName = agentName;
        return msg;
    }

    public static WebSocketMessage error(String errorMessage) {
        return new WebSocketMessage(Type.ERROR, errorMessage);
    }

    public static WebSocketMessage success(String successMessage) {
        return new WebSocketMessage(Type.SUCCESS, successMessage);
    }

    public static WebSocketMessage notification(String notificationMessage) {
        return new WebSocketMessage(Type.NOTIFICATION, notificationMessage);
    }

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "type=" + type +
                ", ticketId=" + ticketId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
