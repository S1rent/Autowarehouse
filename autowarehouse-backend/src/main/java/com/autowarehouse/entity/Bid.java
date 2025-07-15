package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    @NotNull
    public Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    public User user;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal amount;

    @Column(name = "is_auto_bid")
    public Boolean isAutoBid = false;

    @Column(name = "max_auto_bid", precision = 12, scale = 2)
    public BigDecimal maxAutoBid;

    @Column(name = "is_winning")
    public Boolean isWinning = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public Bid() {}

    public Bid(Auction auction, User user, BigDecimal amount) {
        this.auction = auction;
        this.user = user;
        this.amount = amount;
    }

    public Bid(Auction auction, User user, BigDecimal amount, boolean isAutoBid, BigDecimal maxAutoBid) {
        this.auction = auction;
        this.user = user;
        this.amount = amount;
        this.isAutoBid = isAutoBid;
        this.maxAutoBid = maxAutoBid;
    }

    // Static finder methods
    public static List<Bid> findByAuction(Auction auction) {
        return find("auction = ?1 order by createdAt desc", auction).list();
    }

    public static List<Bid> findByAuctionId(Long auctionId) {
        return find("auction.id = ?1 order by createdAt desc", auctionId).list();
    }

    public static List<Bid> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<Bid> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<Bid> findWinningBids(User user) {
        return find("user = ?1 and isWinning = true order by createdAt desc", user).list();
    }

    public static List<Bid> findWinningBidsByUserId(Long userId) {
        return find("user.id = ?1 and isWinning = true order by createdAt desc", userId).list();
    }

    public static Bid findHighestBidForAuction(Auction auction) {
        return find("auction = ?1 order by amount desc", auction).firstResult();
    }

    public static Bid findHighestBidForAuctionId(Long auctionId) {
        return find("auction.id = ?1 order by amount desc", auctionId).firstResult();
    }

    public static Bid findWinningBid(Auction auction) {
        return find("auction = ?1 and isWinning = true", auction).firstResult();
    }

    public static List<Bid> findAutoBidsForAuction(Auction auction, BigDecimal currentPrice) {
        return find("auction = ?1 and isAutoBid = true and maxAutoBid > ?2 order by maxAutoBid desc", 
                   auction, currentPrice).list();
    }

    public static long countByAuction(Auction auction) {
        return count("auction", auction);
    }

    public static long countByAuctionId(Long auctionId) {
        return count("auction.id", auctionId);
    }

    public static long countByUser(User user) {
        return count("user", user);
    }

    public static long countByUserId(Long userId) {
        return count("user.id", userId);
    }

    public static void clearWinningBidsForAuction(Auction auction) {
        update("isWinning = false where auction = ?1", auction);
    }

    public static void clearWinningBidsForAuctionId(Long auctionId) {
        update("isWinning = false where auction.id = ?1", auctionId);
    }

    // Helper methods
    public boolean canAutoBid(BigDecimal currentPrice) {
        return isAutoBid && maxAutoBid != null && maxAutoBid.compareTo(currentPrice) > 0;
    }

    public BigDecimal getNextAutoBidAmount(BigDecimal currentPrice, BigDecimal minimumIncrement) {
        if (!canAutoBid(currentPrice)) {
            return null;
        }
        
        BigDecimal nextBid = currentPrice.add(minimumIncrement);
        return nextBid.compareTo(maxAutoBid) <= 0 ? nextBid : maxAutoBid;
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
                ", auctionId=" + (auction != null ? auction.id : null) +
                ", userId=" + (user != null ? user.id : null) +
                ", amount=" + amount +
                ", isAutoBid=" + isAutoBid +
                ", isWinning=" + isWinning +
                ", createdAt=" + createdAt +
                '}';
    }
}
