package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat_messages")
public class ChatMessage extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "ticket_id", nullable = false)
    @NotNull
    public Long ticketId;

    @Column(name = "sender_id", nullable = false)
    @NotNull
    public Long senderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type", nullable = false)
    @NotNull
    public SenderType senderType;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    public String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    @NotNull
    public MessageType messageType = MessageType.TEXT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    public LocalDateTime timestamp;

    @Column(name = "is_read", nullable = false)
    public Boolean isRead = false;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
    public SupportTicket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", insertable = false, updatable = false)
    public User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
    public ChatSession session;

    // Constructors
    public ChatMessage() {}

    public ChatMessage(Long ticketId, Long senderId, SenderType senderType, String message) {
        this.ticketId = ticketId;
        this.senderId = senderId;
        this.senderType = senderType;
        this.message = message;
    }

    public ChatMessage(Long ticketId, Long senderId, SenderType senderType, String message, MessageType messageType) {
        this.ticketId = ticketId;
        this.senderId = senderId;
        this.senderType = senderType;
        this.message = message;
        this.messageType = messageType;
    }

    // Static finder methods
    public static List<ChatMessage> findByTicketId(Long ticketId) {
        return find("ticketId = ?1 ORDER BY timestamp ASC", ticketId).list();
    }

    public static List<ChatMessage> findByTicketIdPaginated(Long ticketId, int page, int size) {
        return find("ticketId = ?1 ORDER BY timestamp DESC", ticketId).page(page, size).list();
    }

    public static List<ChatMessage> findBySenderId(Long senderId) {
        return find("senderId = ?1 ORDER BY timestamp DESC", senderId).list();
    }

    public static List<ChatMessage> findBySenderType(SenderType senderType) {
        return find("senderType = ?1 ORDER BY timestamp DESC", senderType).list();
    }

    public static List<ChatMessage> findUnreadByTicketId(Long ticketId) {
        return find("ticketId = ?1 AND isRead = false ORDER BY timestamp ASC", ticketId).list();
    }

    public static List<ChatMessage> findUnreadBySenderType(Long ticketId, SenderType senderType) {
        return find("ticketId = ?1 AND senderType = ?2 AND isRead = false ORDER BY timestamp ASC", ticketId, senderType).list();
    }

    public static List<ChatMessage> findRecentMessages(Long ticketId, int minutes) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(minutes);
        return find("ticketId = ?1 AND timestamp >= ?2 ORDER BY timestamp ASC", ticketId, cutoffTime).list();
    }

    public static ChatMessage findLatestByTicketId(Long ticketId) {
        return find("ticketId = ?1 ORDER BY timestamp DESC", ticketId).firstResult();
    }

    public static long countByTicketId(Long ticketId) {
        return count("ticketId = ?1", ticketId);
    }

    public static long countUnreadByTicketId(Long ticketId) {
        return count("ticketId = ?1 AND isRead = false", ticketId);
    }

    public static long countUnreadBySenderType(Long ticketId, SenderType senderType) {
        return count("ticketId = ?1 AND senderType = ?2 AND isRead = false", ticketId, senderType);
    }

    public static long countMessagesBySender(Long senderId) {
        return count("senderId = ?1", senderId);
    }

    // Helper methods
    public boolean isFromCustomer() {
        return senderType == SenderType.CUSTOMER;
    }

    public boolean isFromAgent() {
        return senderType == SenderType.AGENT;
    }

    public boolean isTextMessage() {
        return messageType == MessageType.TEXT;
    }

    public boolean isImageMessage() {
        return messageType == MessageType.IMAGE;
    }

    public boolean isFileMessage() {
        return messageType == MessageType.FILE;
    }

    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }

    public boolean isRecent(int minutes) {
        return timestamp.isAfter(LocalDateTime.now().minusMinutes(minutes));
    }

    public long getAgeInMinutes() {
        return java.time.Duration.between(timestamp, LocalDateTime.now()).toMinutes();
    }

    // Bulk operations
    public static void markAllAsReadByTicketId(Long ticketId) {
        update("isRead = true WHERE ticketId = ?1 AND isRead = false", ticketId);
    }

    public static void markAllAsReadByTicketIdAndSenderType(Long ticketId, SenderType senderType) {
        update("isRead = true WHERE ticketId = ?1 AND senderType = ?2 AND isRead = false", ticketId, senderType);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", senderId=" + senderId +
                ", senderType=" + senderType +
                ", messageType=" + messageType +
                ", timestamp=" + timestamp +
                ", isRead=" + isRead +
                ", message='" + (message.length() > 50 ? message.substring(0, 50) + "..." : message) + '\'' +
                '}';
    }
}
