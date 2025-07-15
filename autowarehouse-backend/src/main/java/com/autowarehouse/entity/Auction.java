package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "auctions")
public class Auction extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(nullable = false, length = 200)
    @NotBlank
    public String title;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "starting_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal startingPrice;

    @Column(name = "current_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal currentPrice;

    @Column(name = "buy_now_price", precision = 12, scale = 2)
    public BigDecimal buyNowPrice;

    @Column(name = "minimum_bid_increment", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal minimumBidIncrement = new BigDecimal("1000.00");


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @NotNull
    public AuctionStatus status = AuctionStatus.SCHEDULED;

    @Column(name = "start_time", nullable = false)
    @NotNull
    public LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull
    public LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    public User winner;

    @Column(name = "total_bids")
    public Integer totalBids = 0;

    @Column(name = "watchers_count")
    public Integer watchersCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Bid> bids;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<AuctionWatcher> watchers;

    // Constructors
    public Auction() {}

    public Auction(Product product, String title, String description, BigDecimal startingPrice, 
                   LocalDateTime startTime, LocalDateTime endTime) {
        this.product = product;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Enums
    public enum AuctionStatus {
        DRAFT, SCHEDULED, LIVE, ENDED, CANCELLED
    }

    // Static finder methods
    public static List<Auction> findActiveAuctions() {
        return find("status in (?1, ?2) order by endTime asc", 
                   AuctionStatus.SCHEDULED, AuctionStatus.LIVE).list();
    }

    public static List<Auction> findLiveAuctions() {
        return find("status = ?1 order by endTime asc", AuctionStatus.LIVE).list();
    }

    public static List<Auction> findUpcomingAuctions() {
        return find("status = ?1 order by startTime asc", AuctionStatus.SCHEDULED).list();
    }

    public static List<Auction> findEndingSoonAuctions() {
        LocalDateTime soon = LocalDateTime.now().plusHours(24);
        return find("status = ?1 and endTime <= ?2 order by endTime asc", 
                   AuctionStatus.LIVE, soon).list();
    }

    public static List<Auction> findEndingSoon(int hours) {
        LocalDateTime soon = LocalDateTime.now().plusHours(hours);
        return find("status = ?1 and endTime <= ?2 order by endTime asc", 
                   AuctionStatus.LIVE, soon).list();
    }


    public static List<Auction> findByStatus(AuctionStatus status) {
        return find("status = ?1 order by createdAt desc", status).list();
    }

    public static List<Auction> findByStatus(String status) {
        return find("status = ?1 order by createdAt desc", 
                   AuctionStatus.valueOf(status.toUpperCase())).list();
    }

    public static List<Auction> findByProduct(Product product) {
        return find("product = ?1 order by createdAt desc", product).list();
    }

    public static List<Auction> findByProductId(Long productId) {
        return find("product.id = ?1 order by createdAt desc", productId).list();
    }

    public static List<Auction> findByWinner(User winner) {
        return find("winner = ?1 order by endTime desc", winner).list();
    }

    public static List<Auction> findWatchedByUser(User user) {
        return find("select aw.auction from AuctionWatcher aw where aw.user = ?1 order by aw.createdAt desc", user).list();
    }

    public static List<Auction> findByWinnerId(Long winnerId) {
        return find("winner.id = ?1 order by endTime desc", winnerId).list();
    }

    public static long countByStatus(AuctionStatus status) {
        return count("status", status);
    }

    public static long countLiveAuctions() {
        return count("status", AuctionStatus.LIVE);
    }

    public static long countUpcomingAuctions() {
        return count("status", AuctionStatus.SCHEDULED);
    }

    // Helper methods
    public boolean isScheduled() {
        return status == AuctionStatus.SCHEDULED;
    }

    public boolean isLive() {
        return status == AuctionStatus.LIVE;
    }

    public boolean isEnded() {
        return status == AuctionStatus.ENDED;
    }

    public boolean isCancelled() {
        return status == AuctionStatus.CANCELLED;
    }

    public boolean canBid() {
        return status == AuctionStatus.LIVE && LocalDateTime.now().isBefore(endTime);
    }

    public boolean canCancel() {
        return status == AuctionStatus.SCHEDULED || status == AuctionStatus.LIVE;
    }

    public boolean shouldStart() {
        return status == AuctionStatus.SCHEDULED && LocalDateTime.now().isAfter(startTime);
    }

    public boolean shouldEnd() {
        return status == AuctionStatus.LIVE && LocalDateTime.now().isAfter(endTime);
    }

    public BigDecimal getMinimumBid() {
        return currentPrice.add(minimumBidIncrement);
    }

    public boolean isValidBid(BigDecimal bidAmount) {
        return bidAmount.compareTo(getMinimumBid()) >= 0;
    }

    public void updateCurrentPrice(BigDecimal newPrice) {
        this.currentPrice = newPrice;
        this.totalBids++;
    }

    public void setWinner(User winner) {
        this.winner = winner;
        this.status = AuctionStatus.ENDED;
    }

    public void start() {
        if (status != AuctionStatus.SCHEDULED) {
            throw new IllegalStateException("Auction must be scheduled to start");
        }
        this.status = AuctionStatus.LIVE;
    }

    public void end() {
        if (status != AuctionStatus.LIVE) {
            throw new IllegalStateException("Auction must be live to end");
        }
        this.status = AuctionStatus.ENDED;
    }

    public void cancel() {
        if (!canCancel()) {
            throw new IllegalStateException("Auction cannot be cancelled in current state");
        }
        this.status = AuctionStatus.CANCELLED;
    }

    public void incrementWatchersCount() {
        this.watchersCount++;
    }

    public void decrementWatchersCount() {
        if (this.watchersCount > 0) {
            this.watchersCount--;
        }
    }

    public long getTimeRemainingMinutes() {
        if (status != AuctionStatus.LIVE) return 0;
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTime)) return 0;
        return java.time.Duration.between(now, endTime).toMinutes();
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", currentPrice=" + currentPrice +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", totalBids=" + totalBids +
                '}';
    }
}
