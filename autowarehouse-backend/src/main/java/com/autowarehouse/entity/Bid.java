package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bids")
public class Bid extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "bid_amount", nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal bidAmount;

    @Column(name = "is_winning")
    public Boolean isWinning = false;

    @Column(name = "is_auto_bid")
    public Boolean isAutoBid = false;

    @Column(name = "max_auto_bid", precision = 12, scale = 2)
    public BigDecimal maxAutoBid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    public Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    // Constructors
    public Bid() {}

    public Bid(BigDecimal bidAmount, Auction auction, User user) {
        this.bidAmount = bidAmount;
        this.auction = auction;
        this.user = user;
    }

    // Static finder methods
    public static List<Bid> findByAuction(Auction auction) {
        return find("auction = ?1 order by bidAmount desc, createdAt desc", auction).list();
    }

    public static List<Bid> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Bid> findWinningBidsByUser(User user) {
        return find("user = ?1 and isWinning = true order by createdAt desc", user).list();
    }

    public static Bid findHighestBidForAuction(Auction auction) {
        return find("auction = ?1 order by bidAmount desc, createdAt desc", auction).firstResult();
    }

    public static List<Bid> findUserBidsForAuction(User user, Auction auction) {
        return find("user = ?1 and auction = ?2 order by bidAmount desc, createdAt desc", user, auction).list();
    }

    // Helper methods
    public boolean isHighestBid() {
        Bid highest = findHighestBidForAuction(auction);
        return highest != null && highest.id.equals(this.id);
    }

    public void setAsWinning() {
        this.isWinning = true;
    }

    public void setAsNotWinning() {
        this.isWinning = false;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", bidAmount=" + bidAmount +
                ", isWinning=" + isWinning +
                ", createdAt=" + createdAt +
                ", auction=" + (auction != null ? auction.id : null) +
                ", user=" + (user != null ? user.email : null) +
                '}';
    }
}
