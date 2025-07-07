package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false)
    @NotBlank
    @Size(max = 200)
    public String title;

    @Column(columnDefinition = "TEXT")
    public String description;

    @Column(name = "starting_bid", nullable = false, precision = 12, scale = 2)
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal startingBid;

    @Column(name = "current_bid", precision = 12, scale = 2)
    public BigDecimal currentBid;

    @Column(name = "reserve_price", precision = 12, scale = 2)
    public BigDecimal reservePrice;

    @Column(name = "bid_increment", precision = 12, scale = 2)
    public BigDecimal bidIncrement = new BigDecimal("10000"); // Default 10k increment

    @Column(name = "start_time", nullable = false)
    @NotNull
    public LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    @NotNull
    public LocalDateTime endTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public AuctionStatus status = AuctionStatus.UPCOMING;

    @Column(name = "total_bids")
    public Integer totalBids = 0;

    @Column(name = "total_watchers")
    public Integer totalWatchers = 0;

    @Column(name = "is_featured")
    public Boolean isFeatured = false;

    @Column(name = "auto_extend_minutes")
    public Integer autoExtendMinutes = 5; // Auto-extend if bid in last 5 minutes

    @Column(name = "condition_description")
    @Size(max = 500)
    public String conditionDescription;

    @ElementCollection
    @CollectionTable(name = "auction_images", joinColumns = @JoinColumn(name = "auction_id"))
    @Column(name = "image_url")
    public List<String> imageUrls;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    public User winner;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("bidAmount DESC, createdAt DESC")
    public List<Bid> bids;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<AuctionWatcher> watchers;

    // Constructors
    public Auction() {}

    public Auction(String title, String description, BigDecimal startingBid, LocalDateTime startTime, LocalDateTime endTime, Category category) {
        this.title = title;
        this.description = description;
        this.startingBid = startingBid;
        this.currentBid = startingBid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
    }

    // Static finder methods
    public static List<Auction> findLiveAuctions() {
        return find("status = ?1 order by endTime", AuctionStatus.LIVE).list();
    }

    public static List<Auction> findUpcomingAuctions() {
        return find("status = ?1 order by startTime", AuctionStatus.UPCOMING).list();
    }

    public static List<Auction> findEndingSoonAuctions() {
        LocalDateTime soon = LocalDateTime.now().plusHours(2);
        return find("status = ?1 and endTime <= ?2 order by endTime", AuctionStatus.LIVE, soon).list();
    }

    public static List<Auction> findByCategory(Category category) {
        return find("category = ?1 and status != ?2 order by startTime desc", category, AuctionStatus.CANCELLED).list();
    }

    public static List<Auction> findByCategoryId(Long categoryId) {
        return find("category.id = ?1 and status != ?2 order by startTime desc", categoryId, AuctionStatus.CANCELLED).list();
    }

    public static List<Auction> findFeaturedAuctions() {
        return find("isFeatured = true and status != ?1 order by startTime desc", AuctionStatus.CANCELLED).list();
    }

    public static List<Auction> findByWinner(User winner) {
        return find("winner = ?1 order by endTime desc", winner).list();
    }

    public static List<Auction> searchByTitle(String searchTerm) {
        return find("lower(title) like ?1 and status != ?2", "%" + searchTerm.toLowerCase() + "%", AuctionStatus.CANCELLED).list();
    }

    // Helper methods
    public boolean isLive() {
        return status == AuctionStatus.LIVE;
    }

    public boolean isUpcoming() {
        return status == AuctionStatus.UPCOMING;
    }

    public boolean isEnded() {
        return status == AuctionStatus.ENDED;
    }

    public boolean isCancelled() {
        return status == AuctionStatus.CANCELLED;
    }

    public boolean hasReservePrice() {
        return reservePrice != null && reservePrice.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isReserveMet() {
        return !hasReservePrice() || (currentBid != null && currentBid.compareTo(reservePrice) >= 0);
    }

    public long getTimeLeftInMinutes() {
        if (isEnded() || isCancelled()) {
            return 0;
        }
        LocalDateTime now = LocalDateTime.now();
        if (isUpcoming()) {
            return java.time.Duration.between(now, startTime).toMinutes();
        } else {
            return java.time.Duration.between(now, endTime).toMinutes();
        }
    }

    public boolean shouldAutoExtend() {
        if (!isLive() || autoExtendMinutes == null || autoExtendMinutes <= 0) {
            return false;
        }
        return getTimeLeftInMinutes() <= autoExtendMinutes;
    }

    public void extendEndTime(int minutes) {
        this.endTime = this.endTime.plusMinutes(minutes);
    }

    public BigDecimal getNextMinimumBid() {
        if (currentBid == null) {
            return startingBid;
        }
        return currentBid.add(bidIncrement);
    }

    public void updateCurrentBid(BigDecimal newBid) {
        this.currentBid = newBid;
        this.totalBids++;
    }

    public void addWatcher() {
        this.totalWatchers++;
    }

    public void removeWatcher() {
        if (this.totalWatchers > 0) {
            this.totalWatchers--;
        }
    }

    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        
        if (status == AuctionStatus.UPCOMING && now.isAfter(startTime)) {
            status = AuctionStatus.LIVE;
        } else if (status == AuctionStatus.LIVE && now.isAfter(endTime)) {
            status = AuctionStatus.ENDED;
            // Set winner if there are bids and reserve is met
            if (totalBids > 0 && isReserveMet() && !bids.isEmpty()) {
                winner = bids.get(0).user; // Highest bidder
            }
        }
    }

    public Bid getHighestBid() {
        return bids != null && !bids.isEmpty() ? bids.get(0) : null;
    }

    public String getPrimaryImageUrl() {
        return imageUrls != null && !imageUrls.isEmpty() ? imageUrls.get(0) : null;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startingBid=" + startingBid +
                ", currentBid=" + currentBid +
                ", status=" + status +
                ", totalBids=" + totalBids +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
