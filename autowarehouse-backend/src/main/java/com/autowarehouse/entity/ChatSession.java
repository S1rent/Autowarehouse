package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chat_sessions")
public class ChatSession extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "ticket_id", nullable = false, unique = true)
    @NotNull
    public Long ticketId;

    @Column(name = "customer_id", nullable = false)
    @NotNull
    public Long customerId;

    @Column(name = "agent_id")
    public Long agentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    public ChatSessionStatus status = ChatSessionStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "started_at", nullable = false, updatable = false)
    public LocalDateTime startedAt;

    @Column(name = "ended_at")
    public LocalDateTime endedAt;

    @Column(name = "last_activity_at", nullable = false)
    public LocalDateTime lastActivityAt = LocalDateTime.now();

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
    public SupportTicket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    public User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", insertable = false, updatable = false)
    public User agent;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ChatMessage> messages;

    // Constructors
    public ChatSession() {}

    public ChatSession(Long ticketId, Long customerId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
    }

    public ChatSession(Long ticketId, Long customerId, Long agentId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.agentId = agentId;
    }

    // Static finder methods
    public static ChatSession findByTicketId(Long ticketId) {
        return find("ticketId", ticketId).firstResult();
    }

    public static List<ChatSession> findByCustomerId(Long customerId) {
        return find("customerId", customerId).list();
    }

    public static List<ChatSession> findByAgentId(Long agentId) {
        return find("agentId", agentId).list();
    }

    public static List<ChatSession> findByStatus(ChatSessionStatus status) {
        return find("status", status).list();
    }

    public static List<ChatSession> findActiveSessions() {
        return find("status", ChatSessionStatus.ACTIVE).list();
    }

    public static List<ChatSession> findActiveSessionsByAgent(Long agentId) {
        return find("agentId = ?1 AND status = ?2", agentId, ChatSessionStatus.ACTIVE).list();
    }

    public static List<ChatSession> findUnassignedActiveSessions() {
        return find("agentId IS NULL AND status = ?1", ChatSessionStatus.ACTIVE).list();
    }

    public static List<ChatSession> findRecentSessions(int hours) {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(hours);
        return find("lastActivityAt >= ?1", cutoffTime).list();
    }

    public static long countActiveSessions() {
        return count("status", ChatSessionStatus.ACTIVE);
    }

    public static long countActiveSessionsByAgent(Long agentId) {
        return count("agentId = ?1 AND status = ?2", agentId, ChatSessionStatus.ACTIVE);
    }

    // Helper methods
    public boolean isActive() {
        return status == ChatSessionStatus.ACTIVE;
    }

    public boolean isInactive() {
        return status == ChatSessionStatus.INACTIVE;
    }

    public boolean isEnded() {
        return status == ChatSessionStatus.ENDED;
    }

    public boolean isAssigned() {
        return agentId != null;
    }

    public void assignAgent(Long agentId) {
        this.agentId = agentId;
        this.status = ChatSessionStatus.ACTIVE;
        updateLastActivity();
    }

    public void activate() {
        this.status = ChatSessionStatus.ACTIVE;
        updateLastActivity();
    }

    public void deactivate() {
        this.status = ChatSessionStatus.INACTIVE;
        updateLastActivity();
    }

    public void end() {
        this.status = ChatSessionStatus.ENDED;
        this.endedAt = LocalDateTime.now();
        updateLastActivity();
    }

    public void updateLastActivity() {
        this.lastActivityAt = LocalDateTime.now();
    }

    public long getDurationInMinutes() {
        LocalDateTime endTime = endedAt != null ? endedAt : LocalDateTime.now();
        return java.time.Duration.between(startedAt, endTime).toMinutes();
    }

    public boolean isStale(int inactiveMinutes) {
        return lastActivityAt.isBefore(LocalDateTime.now().minusMinutes(inactiveMinutes));
    }

    @Override
    public String toString() {
        return "ChatSession{" +
                "id=" + id +
                ", ticketId=" + ticketId +
                ", customerId=" + customerId +
                ", agentId=" + agentId +
                ", status=" + status +
                ", startedAt=" + startedAt +
                ", lastActivityAt=" + lastActivityAt +
                '}';
    }
}
