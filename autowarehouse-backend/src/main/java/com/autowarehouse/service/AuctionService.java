package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class AuctionService {

    @Inject
    NotificationService notificationService;

    @Transactional
    public Auction createAuction(@Valid Auction auction) {
        // Validate auction dates
        if (auction.startTime.isAfter(auction.endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (auction.startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time must be in the future");
        }

        // Set default values
        auction.status = Auction.AuctionStatus.SCHEDULED;
        auction.currentBid = auction.startingBid;
        auction.totalBids = 0;
        auction.watchersCount = 0;

        // Validate reserve price
        if (auction.reservePrice != null && auction.reservePrice.compareTo(auction.startingBid) < 0) {
            throw new IllegalArgumentException("Reserve price cannot be less than starting bid");
        }

        auction.persist();
        return auction;
    }

    @Transactional
    public Auction updateAuction(Long auctionId, @Valid Auction updatedAuction) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        // Only allow updates if auction hasn't started
        if (auction.status != Auction.AuctionStatus.SCHEDULED) {
            throw new IllegalArgumentException("Cannot update auction that has already started");
        }

        auction.title = updatedAuction.title;
        auction.description = updatedAuction.description;
        auction.startingBid = updatedAuction.startingBid;
        auction.reservePrice = updatedAuction.reservePrice;
        auction.bidIncrement = updatedAuction.bidIncrement;
        auction.startTime = updatedAuction.startTime;
        auction.endTime = updatedAuction.endTime;
        auction.product = updatedAuction.product;
        auction.category = updatedAuction.category;

        auction.persist();
        return auction;
    }

    public Auction findById(Long id) {
        return Auction.findById(id);
    }

    public List<Auction> findAll() {
        return Auction.listAll();
    }

    public List<Auction> findByStatus(Auction.AuctionStatus status) {
        return Auction.findByStatus(status);
    }

    public List<Auction> findLiveAuctions() {
        return Auction.findLiveAuctions();
    }

    public List<Auction> findUpcomingAuctions() {
        return Auction.findUpcomingAuctions();
    }

    public List<Auction> findEndingSoon(int minutes) {
        return Auction.findEndingSoon(minutes);
    }

    public List<Auction> findByCategory(Category category) {
        return Auction.findByCategory(category);
    }

    public List<Auction> findByProduct(Product product) {
        return Auction.findByProduct(product);
    }

    public List<Auction> findByWinner(User winner) {
        return Auction.findByWinner(winner);
    }

    public List<Auction> findActiveAuctions() {
        return findLiveAuctions();
    }

    public List<Auction> findEndingSoonAuctions() {
        return findEndingSoon(30); // Default to 30 minutes
    }

    @Transactional
    public AuctionWatcher watchAuction(Long auctionId, Long userId) {
        return addWatcher(auctionId, userId);
    }

    @Transactional
    public void unwatchAuction(Long auctionId, Long userId) {
        removeWatcher(auctionId, userId);
    }

    public List<Bid> getUserBids(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return Bid.findByUser(user);
    }

    public List<Auction> getUserWatchedAuctions(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return Auction.findWatchedByUser(user);
    }

    @Transactional
    public Bid placeBid(Long auctionId, Long userId, BigDecimal bidAmount, boolean isAutoBid, BigDecimal maxAutoBid) {
        Auction auction = Auction.findById(auctionId);
        User user = User.findById(userId);

        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (auction.status != Auction.AuctionStatus.LIVE) {
            throw new IllegalArgumentException("Auction is not live");
        }

        if (auction.endTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Auction has ended");
        }

        // Validate bid amount
        BigDecimal minimumBid = auction.currentBid.add(auction.bidIncrement);
        if (bidAmount.compareTo(minimumBid) < 0) {
            throw new IllegalArgumentException("Bid must be at least " + minimumBid);
        }

        // Check if user is trying to bid on their own auction (if applicable)
        // This would need additional logic if auctions can be created by users

        // Create new bid
        Bid bid = new Bid();
        bid.auction = auction;
        bid.user = user;
        bid.amount = bidAmount;
        bid.isAutoBid = isAutoBid;
        bid.maxAutoBid = maxAutoBid;
        bid.isWinning = true;

        // Mark previous winning bid as not winning
        Bid previousWinningBid = Bid.findWinningBid(auction);
        if (previousWinningBid != null) {
            previousWinningBid.isWinning = false;
            previousWinningBid.persist();

            // Notify previous bidder they've been outbid
            notificationService.createNotification(
                previousWinningBid.user,
                "You've been outbid!",
                "Someone has placed a higher bid on \"" + auction.title + "\"",
                NotificationType.AUCTION_OUTBID,
                auction.id,
                "auction"
            );
        }

        bid.persist();

        // Update auction
        auction.currentBid = bidAmount;
        auction.totalBids++;
        auction.currentWinner = user;

        auction.persist();

        // Notify watchers about new bid
        notifyWatchers(auction, "bid");

        return bid;
    }

    @Transactional
    public void startAuction(Long auctionId) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (auction.status != Auction.AuctionStatus.SCHEDULED) {
            throw new IllegalArgumentException("Auction is not in scheduled status");
        }

        auction.status = Auction.AuctionStatus.LIVE;
        auction.persist();

        // Notify watchers that auction has started
        notifyWatchers(auction, "start");
    }

    @Transactional
    public void endAuction(Long auctionId) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (auction.status != Auction.AuctionStatus.LIVE) {
            throw new IllegalArgumentException("Auction is not live");
        }

        auction.status = Auction.AuctionStatus.ENDED;
        auction.endTime = LocalDateTime.now();

        // Determine winner
        Bid winningBid = Bid.findWinningBid(auction);
        if (winningBid != null) {
            // Check if reserve price is met
            if (auction.reservePrice == null || winningBid.amount.compareTo(auction.reservePrice) >= 0) {
                auction.winner = winningBid.user;
                auction.winningBid = winningBid.amount;

                // Notify winner
                notificationService.createNotification(
                    winningBid.user,
                    "Congratulations! You won the auction!",
                    "You won the auction for \"" + auction.title + "\" with a bid of $" + winningBid.amount,
                    NotificationType.AUCTION_WON,
                    auction.id,
                    "auction"
                );
            }
        }

        auction.persist();

        // Notify all watchers that auction has ended
        notifyWatchers(auction, "end");
    }

    @Transactional
    public void cancelAuction(Long auctionId, String reason) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (auction.status == Auction.AuctionStatus.ENDED) {
            throw new IllegalArgumentException("Cannot cancel ended auction");
        }

        auction.status = Auction.AuctionStatus.CANCELLED;
        auction.cancelReason = reason;
        auction.persist();

        // Notify all watchers about cancellation
        List<AuctionWatcher> watchers = AuctionWatcher.findByAuction(auction);
        for (AuctionWatcher watcher : watchers) {
            notificationService.createNotification(
                watcher.user,
                "Auction Cancelled",
                "The auction \"" + auction.title + "\" has been cancelled. Reason: " + reason,
                NotificationType.GENERAL,
                auction.id,
                "auction"
            );
        }
    }

    @Transactional
    public AuctionWatcher addWatcher(Long auctionId, Long userId) {
        Auction auction = Auction.findById(auctionId);
        User user = User.findById(userId);

        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Check if already watching
        AuctionWatcher existingWatcher = AuctionWatcher.findByUserAndAuction(user, auction);
        if (existingWatcher != null) {
            return existingWatcher;
        }

        AuctionWatcher watcher = new AuctionWatcher(auction, user);
        watcher.persist();

        // Update watcher count
        auction.watchersCount++;
        auction.persist();

        return watcher;
    }

    @Transactional
    public void removeWatcher(Long auctionId, Long userId) {
        Auction auction = Auction.findById(auctionId);
        User user = User.findById(userId);

        if (auction == null || user == null) {
            return;
        }

        AuctionWatcher watcher = AuctionWatcher.findByUserAndAuction(user, auction);
        if (watcher != null) {
            watcher.delete();

            // Update watcher count
            auction.watchersCount = Math.max(0, auction.watchersCount - 1);
            auction.persist();
        }
    }

    public List<Bid> getAuctionBids(Long auctionId) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        return Bid.findByAuction(auction);
    }

    public List<AuctionWatcher> getAuctionWatchers(Long auctionId) {
        Auction auction = Auction.findById(auctionId);
        if (auction == null) {
            throw new IllegalArgumentException("Auction not found");
        }

        return AuctionWatcher.findByAuction(auction);
    }

    public long getTotalAuctionsCount() {
        return Auction.count();
    }

    public long getLiveAuctionsCount() {
        return Auction.countByStatus(Auction.AuctionStatus.LIVE);
    }

    public long getUpcomingAuctionsCount() {
        return Auction.countByStatus(Auction.AuctionStatus.SCHEDULED);
    }

    private void notifyWatchers(Auction auction, String eventType) {
        List<AuctionWatcher> watchers = AuctionWatcher.findWatchersForNotification(auction, eventType);
        
        for (AuctionWatcher watcher : watchers) {
            String title = "";
            String message = "";
            NotificationType notificationType = NotificationType.GENERAL;

            switch (eventType) {
                case "bid":
                    title = "New bid placed";
                    message = "A new bid of $" + auction.currentBid + " was placed on \"" + auction.title + "\"";
                    notificationType = NotificationType.AUCTION_OUTBID;
                    break;
                case "start":
                    title = "Auction started";
                    message = "The auction \"" + auction.title + "\" has started!";
                    notificationType = NotificationType.AUCTION_STARTED;
                    break;
                case "ending_soon":
                    title = "Auction ending soon";
                    message = "The auction \"" + auction.title + "\" is ending soon!";
                    notificationType = NotificationType.AUCTION_ENDING_SOON;
                    break;
                case "end":
                    title = "Auction ended";
                    message = "The auction \"" + auction.title + "\" has ended";
                    notificationType = NotificationType.GENERAL;
                    break;
            }

            notificationService.createNotification(
                watcher.user, title, message, notificationType, auction.id, "auction"
            );
        }
    }
}
