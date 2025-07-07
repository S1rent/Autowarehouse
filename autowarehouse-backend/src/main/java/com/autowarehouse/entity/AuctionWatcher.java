package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "auction_watchers", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"auction_id", "user_id"}))
public class AuctionWatcher extends PanacheEntityBase {

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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public AuctionWatcher() {}

    public AuctionWatcher(Auction auction, User user) {
        this.auction = auction;
        this.user = user;
    }

    // Static finder methods
    public static AuctionWatcher findByAuctionAndUser(Auction auction, User user) {
        return find("auction = ?1 and user = ?2", auction, user).firstResult();
    }

    public static AuctionWatcher findByAuctionIdAndUserId(Long auctionId, Long userId) {
        return find("auction.id = ?1 and user.id = ?2", auctionId, userId).firstResult();
    }

    public static List<AuctionWatcher> findByAuction(Auction auction) {
        return find("auction = ?1 order by createdAt desc", auction).list();
    }

    public static List<AuctionWatcher> findByAuctionId(Long auctionId) {
        return find("auction.id = ?1 order by createdAt desc", auctionId).list();
    }

    public static List<AuctionWatcher> findByUser(User user) {
        return find("user = ?1 order by createdAt desc", user).list();
    }

    public static List<AuctionWatcher> findByUserId(Long userId) {
        return find("user.id = ?1 order by createdAt desc", userId).list();
    }

    public static List<Auction> findWatchedAuctionsByUser(User user) {
        return find("select aw.auction from AuctionWatcher aw where aw.user = ?1 order by aw.createdAt desc", user).list();
    }

    public static List<Auction> findWatchedAuctionsByUserId(Long userId) {
        return find("select aw.auction from AuctionWatcher aw where aw.user.id = ?1 order by aw.createdAt desc", userId).list();
    }

    public static boolean existsByAuctionAndUser(Auction auction, User user) {
        return count("auction = ?1 and user = ?2", auction, user) > 0;
    }

    public static boolean existsByAuctionIdAndUserId(Long auctionId, Long userId) {
        return count("auction.id = ?1 and user.id = ?2", auctionId, userId) > 0;
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

    public static void deleteByAuctionAndUser(Auction auction, User user) {
        delete("auction = ?1 and user = ?2", auction, user);
    }

    public static void deleteByAuctionIdAndUserId(Long auctionId, Long userId) {
        delete("auction.id = ?1 and user.id = ?2", auctionId, userId);
    }

    @Override
    public String toString() {
        return "AuctionWatcher{" +
                "id=" + id +
                ", auctionId=" + (auction != null ? auction.id : null) +
                ", userId=" + (user != null ? user.id : null) +
                ", createdAt=" + createdAt +
                '}';
    }
}
