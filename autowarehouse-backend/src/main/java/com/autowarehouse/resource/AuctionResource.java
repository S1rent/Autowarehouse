package com.autowarehouse.resource;

import com.autowarehouse.entity.*;
import com.autowarehouse.service.AuctionService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/auctions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuctionResource {

    @Inject
    AuctionService auctionService;

    @GET
    public Response getAllAuctions(@QueryParam("status") String status) {
        try {
            List<Auction> auctions;
            
            if (status != null) {
                AuctionStatus auctionStatus = AuctionStatus.valueOf(status.toUpperCase());
                auctions = auctionService.findByStatus(auctionStatus);
            } else {
                auctions = auctionService.findAll();
            }

            List<AuctionResponse> response = auctions.stream()
                    .map(AuctionResponse::new)
                    .toList();
            
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch auctions"))
                    .build();
        }
    }

    @GET
    @Path("/live")
    public Response getLiveAuctions() {
        List<Auction> auctions = auctionService.findLiveAuctions();
        List<AuctionResponse> response = auctions.stream()
                .map(AuctionResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/upcoming")
    public Response getUpcomingAuctions() {
        List<Auction> auctions = auctionService.findUpcomingAuctions();
        List<AuctionResponse> response = auctions.stream()
                .map(AuctionResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/ending-soon")
    public Response getEndingSoonAuctions(@QueryParam("minutes") @DefaultValue("60") int minutes) {
        List<Auction> auctions = auctionService.findEndingSoon(minutes);
        List<AuctionResponse> response = auctions.stream()
                .map(AuctionResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuction(@PathParam("id") Long id) {
        try {
            Auction auction = auctionService.findById(id);
            if (auction == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Auction not found"))
                        .build();
            }
            return Response.ok(new AuctionDetailResponse(auction)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch auction"))
                    .build();
        }
    }

    @GET
    @Path("/{id}/bids")
    public Response getAuctionBids(@PathParam("id") Long id) {
        try {
            List<Bid> bids = auctionService.getAuctionBids(id);
            List<BidResponse> response = bids.stream()
                    .map(BidResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}/watchers")
    @RolesAllowed("ADMIN")
    public Response getAuctionWatchers(@PathParam("id") Long id) {
        try {
            List<AuctionWatcher> watchers = auctionService.getAuctionWatchers(id);
            List<WatcherResponse> response = watchers.stream()
                    .map(WatcherResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{id}/bids")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response placeBid(@PathParam("id") Long auctionId, @Valid PlaceBidRequest request) {
        try {
            Bid bid = auctionService.placeBid(
                auctionId, 
                request.userId, 
                request.bidAmount, 
                request.isAutoBid != null ? request.isAutoBid : false,
                request.maxAutoBid
            );
            return Response.status(Response.Status.CREATED)
                    .entity(new BidResponse(bid))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{id}/watch")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response watchAuction(@PathParam("id") Long auctionId, @Valid WatchAuctionRequest request) {
        try {
            AuctionWatcher watcher = auctionService.addWatcher(auctionId, request.userId);
            return Response.status(Response.Status.CREATED)
                    .entity(new WatcherResponse(watcher))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/watch")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response unwatchAuction(@PathParam("id") Long auctionId, @QueryParam("userId") Long userId) {
        try {
            auctionService.removeWatcher(auctionId, userId);
            return Response.ok(new SuccessResponse("Auction unwatched successfully")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to unwatch auction"))
                    .build();
        }
    }

    // Admin endpoints
    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response createAuction(@Valid CreateAuctionRequest request) {
        try {
            Auction auction = new Auction();
            auction.title = request.title;
            auction.description = request.description;
            auction.startingBid = request.startingBid;
            auction.reservePrice = request.reservePrice;
            auction.bidIncrement = request.bidIncrement;
            auction.startTime = request.startTime;
            auction.endTime = request.endTime;
            auction.autoExtendMinutes = request.autoExtendMinutes;
            auction.product = Product.findById(request.productId);
            auction.category = Category.findById(request.categoryId);
            
            Auction createdAuction = auctionService.createAuction(auction);
            return Response.status(Response.Status.CREATED)
                    .entity(new AuctionDetailResponse(createdAuction))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed("ADMIN")
    public Response updateAuction(@PathParam("id") Long id, @Valid UpdateAuctionRequest request) {
        try {
            Auction updatedAuction = new Auction();
            updatedAuction.title = request.title;
            updatedAuction.description = request.description;
            updatedAuction.startingBid = request.startingBid;
            updatedAuction.reservePrice = request.reservePrice;
            updatedAuction.bidIncrement = request.bidIncrement;
            updatedAuction.startTime = request.startTime;
            updatedAuction.endTime = request.endTime;
            updatedAuction.autoExtendMinutes = request.autoExtendMinutes;
            updatedAuction.product = Product.findById(request.productId);
            updatedAuction.category = Category.findById(request.categoryId);
            
            Auction auction = auctionService.updateAuction(id, updatedAuction);
            return Response.ok(new AuctionDetailResponse(auction)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/start")
    @RolesAllowed("ADMIN")
    public Response startAuction(@PathParam("id") Long id) {
        try {
            auctionService.startAuction(id);
            return Response.ok(new SuccessResponse("Auction started successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/end")
    @RolesAllowed("ADMIN")
    public Response endAuction(@PathParam("id") Long id) {
        try {
            auctionService.endAuction(id);
            return Response.ok(new SuccessResponse("Auction ended successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/cancel")
    @RolesAllowed("ADMIN")
    public Response cancelAuction(@PathParam("id") Long id, @Valid CancelAuctionRequest request) {
        try {
            auctionService.cancelAuction(id, request.reason);
            return Response.ok(new SuccessResponse("Auction cancelled successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getAuctionStats() {
        AuctionStatsResponse stats = new AuctionStatsResponse();
        stats.totalAuctions = auctionService.getTotalAuctionsCount();
        stats.liveAuctions = auctionService.getLiveAuctionsCount();
        stats.upcomingAuctions = auctionService.getUpcomingAuctionsCount();
        return Response.ok(stats).build();
    }

    // DTO Classes
    public static class PlaceBidRequest {
        public Long userId;
        public BigDecimal bidAmount;
        public Boolean isAutoBid;
        public BigDecimal maxAutoBid;
    }

    public static class WatchAuctionRequest {
        public Long userId;
    }

    public static class CreateAuctionRequest {
        public String title;
        public String description;
        public BigDecimal startingBid;
        public BigDecimal reservePrice;
        public BigDecimal bidIncrement;
        public LocalDateTime startTime;
        public LocalDateTime endTime;
        public Integer autoExtendMinutes;
        public Long productId;
        public Long categoryId;
    }

    public static class UpdateAuctionRequest extends CreateAuctionRequest {
    }

    public static class CancelAuctionRequest {
        public String reason;
    }

    public static class AuctionResponse {
        public Long id;
        public String title;
        public String description;
        public BigDecimal startingBid;
        public BigDecimal currentBid;
        public BigDecimal reservePrice;
        public BigDecimal bidIncrement;
        public LocalDateTime startTime;
        public LocalDateTime endTime;
        public AuctionStatus status;
        public Integer bidCount;
        public Integer watcherCount;
        public String productName;
        public String categoryName;

        public AuctionResponse(Auction auction) {
            this.id = auction.id;
            this.title = auction.title;
            this.description = auction.description;
            this.startingBid = auction.startingBid;
            this.currentBid = auction.currentBid;
            this.reservePrice = auction.reservePrice;
            this.bidIncrement = auction.bidIncrement;
            this.startTime = auction.startTime;
            this.endTime = auction.endTime;
            this.status = auction.status;
            this.bidCount = auction.bidCount;
            this.watcherCount = auction.watcherCount;
            this.productName = auction.product != null ? auction.product.name : null;
            this.categoryName = auction.category != null ? auction.category.name : null;
        }
    }

    public static class AuctionDetailResponse extends AuctionResponse {
        public Integer autoExtendMinutes;
        public String winnerName;
        public BigDecimal winningBid;
        public String cancelReason;
        public LocalDateTime createdAt;

        public AuctionDetailResponse(Auction auction) {
            super(auction);
            this.autoExtendMinutes = auction.autoExtendMinutes;
            this.winnerName = auction.winner != null ? auction.winner.getFullName() : null;
            this.winningBid = auction.winningBid;
            this.cancelReason = auction.cancelReason;
            this.createdAt = auction.createdAt;
        }
    }

    public static class BidResponse {
        public Long id;
        public BigDecimal bidAmount;
        public Boolean isAutoBid;
        public BigDecimal maxAutoBid;
        public Boolean isWinning;
        public String bidderName;
        public LocalDateTime createdAt;

        public BidResponse(Bid bid) {
            this.id = bid.id;
            this.bidAmount = bid.bidAmount;
            this.isAutoBid = bid.isAutoBid;
            this.maxAutoBid = bid.maxAutoBid;
            this.isWinning = bid.isWinning;
            this.bidderName = bid.user != null ? bid.user.getFullName() : null;
            this.createdAt = bid.createdAt;
        }
    }

    public static class WatcherResponse {
        public Long id;
        public String userName;
        public LocalDateTime createdAt;

        public WatcherResponse(AuctionWatcher watcher) {
            this.id = watcher.id;
            this.userName = watcher.user != null ? watcher.user.getFullName() : null;
            this.createdAt = watcher.createdAt;
        }
    }

    public static class AuctionStatsResponse {
        public long totalAuctions;
        public long liveAuctions;
        public long upcomingAuctions;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}
