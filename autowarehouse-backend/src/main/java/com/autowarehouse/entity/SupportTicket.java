package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "support_tickets")
public class SupportTicket extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    public String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    public TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    public TicketPriority priority = TicketPriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    public TicketCategory category = TicketCategory.OTHER;

    @Column(name = "customer_id", nullable = false)
    @NotNull
    public Long customerId;

    @Column(name = "assigned_agent_id")
    public Long assignedAgentId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @Column(name = "resolved_at")
    public LocalDateTime resolvedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    public User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_agent_id", insertable = false, updatable = false)
    public User assignedAgent;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ChatMessage> messages;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public ChatSession chatSession;

    // Constructors
    public SupportTicket() {}

    public SupportTicket(String subject, String description, Long customerId, TicketCategory category, TicketPriority priority) {
        this.subject = subject;
        this.description = description;
        this.customerId = customerId;
        this.category = category;
        this.priority = priority;
    }

    // Static finder methods
    public static List<SupportTicket> findByCustomerId(Long customerId) {
        return find("customerId", customerId).list();
    }

    public static List<SupportTicket> findByAgentId(Long agentId) {
        return find("assignedAgentId", agentId).list();
    }

    public static List<SupportTicket> findByStatus(TicketStatus status) {
        return find("status", status).list();
    }

    public static List<SupportTicket> findByPriority(TicketPriority priority) {
        return find("priority", priority).list();
    }

    public static List<SupportTicket> findByCategory(TicketCategory category) {
        return find("category", category).list();
    }

    public static List<SupportTicket> findUnassigned() {
        return find("assignedAgentId IS NULL").list();
    }

    public static List<SupportTicket> findOpenTickets() {
        return find("status IN (?1, ?2)", TicketStatus.OPEN, TicketStatus.IN_PROGRESS).list();
    }

    public static List<SupportTicket> findRecentTickets(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return find("createdAt >= ?1", cutoffDate).list();
    }

    public static long countByStatus(TicketStatus status) {
        return count("status", status);
    }

    public static long countByAgentId(Long agentId) {
        return count("assignedAgentId", agentId);
    }

    public static long countOpenTickets() {
        return count("status IN (?1, ?2)", TicketStatus.OPEN, TicketStatus.IN_PROGRESS);
    }

    // Helper methods
    public boolean isOpen() {
        return status == TicketStatus.OPEN;
    }

    public boolean isInProgress() {
        return status == TicketStatus.IN_PROGRESS;
    }

    public boolean isResolved() {
        return status == TicketStatus.RESOLVED;
    }

    public boolean isClosed() {
        return status == TicketStatus.CLOSED;
    }

    public boolean isAssigned() {
        return assignedAgentId != null;
    }

    public boolean isHighPriority() {
        return priority == TicketPriority.HIGH || priority == TicketPriority.URGENT;
    }

    public void assignToAgent(Long agentId) {
        this.assignedAgentId = agentId;
        if (this.status == TicketStatus.OPEN) {
            this.status = TicketStatus.IN_PROGRESS;
        }
    }

    public void resolve() {
        this.status = TicketStatus.RESOLVED;
        this.resolvedAt = LocalDateTime.now();
    }

    public void close() {
        this.status = TicketStatus.CLOSED;
        if (this.resolvedAt == null) {
            this.resolvedAt = LocalDateTime.now();
        }
    }

    public void reopen() {
        this.status = TicketStatus.OPEN;
        this.resolvedAt = null;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", category=" + category +
                ", customerId=" + customerId +
                ", assignedAgentId=" + assignedAgentId +
                ", createdAt=" + createdAt +
                '}';
    }
}
