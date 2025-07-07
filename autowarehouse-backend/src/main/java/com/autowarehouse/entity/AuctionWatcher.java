package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "auction_watchers")
public class AuctionWatcher extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "notify_on_bid")
    public Boolean notifyOnBid = true;

    @Column(name = "notify_on_ending_soon")
    public Boolean notifyOnEndingSoon = true;

    @Column(name = "notify_on_end")
    public Boolean notifyOnEnd = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    public Auction auction;

    // Constructors
    public AuctionWatcher() {}

    public AuctionWatcher(User user, Auction auction) {
        this.user = user;
        this.auction = auction;
    }

    // Static finder methods
    public static List<AuctionWatcher> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<AuctionWatcher> findByAuction(Auction auction) {
        return find("auction = ?1 order by createdAt desc", auction).list();
    }

    public static AuctionWatcher findByUserAndAuction(User user, Auction auction) {
        return find("user = ?1 and auction = ?2", user, auction).firstResult();
    }

    public static List<AuctionWatcher> findActiveWatchers(User user) {
        return find("user = ?1 and auction.status in (?2, ?3) order by createdAt desc", 
                   user, AuctionStatus.UPCOMING, AuctionStatus.LIVE).list();
    }

    public static List<AuctionWatcher> findWatchersForNotification(Auction auction, String notificationType) {
        String query = "auction = ?1";
        
        switch (notificationType) {
            case "bid":
                query += " and notifyOnBid = true";
                break;
            case "ending_soon":
                query += " and notifyOnEndingSoon = true";
                break;
            case "end":
                query += " and notifyOnEnd = true";
                break;
        }
        
        return find(query, auction).list();
    }

    public static long countByAuction(Auction auction) {
        return count("auction", auction);
    }

    public static long countByUser(User user) {
        return count("user", user);
    }

    public static boolean existsByUserAndAuction(User user, Auction auction) {
        return count("user = ?1 and auction = ?2", user, auction) > 0;
    }

    // Helper methods
    public void updateNotificationPreferences(boolean notifyOnBid, boolean notifyOnEndingSoon, boolean notifyOnEnd) {
        this.notifyOnBid = notifyOnBid;
        this.notifyOnEndingSoon = notifyOnEndingSoon;
        this.notifyOnEnd = notifyOnEnd;
    }

    public boolean shouldNotifyOnBid() {
        return notifyOnBid != null && notifyOnBid;
    }

    public boolean shouldNotifyOnEndingSoon() {
        return notifyOnEndingSoon != null && notifyOnEndingSoon;
    }

    public boolean shouldNotifyOnEnd() {
        return notifyOnEnd != null && notifyOnEnd;
    }

    public boolean isAuctionActive() {
        return auction != null && 
               (auction.status == AuctionStatus.UPCOMING || auction.status == AuctionStatus.LIVE);
    }

    @Override
    public String toString() {
        return "AuctionWatcher{" +
                "id=" + id +
                ", notifyOnBid=" + notifyOnBid +
                ", notifyOnEndingSoon=" + notifyOnEndingSoon +
                ", notifyOnEnd=" + notifyOnEnd +
                ", user=" + (user != null ? user.email : null) +
                ", auction=" + (auction != null ? auction.title : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
