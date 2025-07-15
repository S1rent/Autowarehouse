package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 200)
    public String title;

    @Column(columnDefinition = "TEXT")
    @Size(max = 1000)
    public String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public NotificationType type;

    @Column(name = "is_read")
    public Boolean isRead = false;

    @Column(name = "reference_id")
    public Long referenceId; // ID of related entity (order, auction, etc.)

    @Column(name = "reference_type")
    @Size(max = 50)
    public String referenceType; // Type of related entity

    @Column(name = "action_url")
    @Size(max = 500)
    public String actionUrl; // URL to navigate when clicked

    @Column(name = "icon_class")
    @Size(max = 50)
    public String iconClass;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    public NotificationPriority priority = NotificationPriority.NORMAL;

    @Column(name = "expires_at")
    public LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    // Constructors
    public Notification() {}

    public Notification(User user, String title, String message, NotificationType type) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.type = type;
    }

    // Static finder methods
    public static List<Notification> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Notification> findUnreadByUser(User user) {
        return find("user = ?1 and isRead = false order by createdAt desc", user).list();
    }

    public static List<Notification> findByUserAndType(User user, NotificationType type) {
        return find("user = ?1 and type = ?2 order by createdAt desc", user, type).list();
    }

    public static List<Notification> findByType(NotificationType type) {
        return find("type = ?1 order by createdAt desc", type).list();
    }

    public static List<Notification> findExpiredNotifications() {
        return find("expiresAt is not null and expiresAt < ?1", LocalDateTime.now()).list();
    }

    public static long countUnreadByUser(User user) {
        return count("user = ?1 and isRead = false", user);
    }

    public static long countByUserAndType(User user, NotificationType type) {
        return count("user = ?1 and type = ?2", user, type);
    }

    // Helper methods
    public void markAsRead() {
        this.isRead = true;
    }

    public void markAsUnread() {
        this.isRead = false;
    }

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

    public boolean isHighPriority() {
        return priority == NotificationPriority.HIGH || priority == NotificationPriority.URGENT;
    }

    public void setReference(Long referenceId, String referenceType) {
        this.referenceId = referenceId;
        this.referenceType = referenceType;
    }

    public void setExpiration(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setExpiration(int hours) {
        this.expiresAt = LocalDateTime.now().plusHours(hours);
    }

    public String getTimeAgo() {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(createdAt, now).toMinutes();
        
        if (minutes < 1) return "Just now";
        if (minutes < 60) return minutes + " minutes ago";
        
        long hours = minutes / 60;
        if (hours < 24) return hours + " hours ago";
        
        long days = hours / 24;
        if (days < 7) return days + " days ago";
        
        long weeks = days / 7;
        return weeks + " weeks ago";
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", isRead=" + isRead +
                ", priority=" + priority +
                ", user=" + (user != null ? user.email : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
