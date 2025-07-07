package com.autowarehouse.resource;

import com.autowarehouse.entity.Auction;
import com.autowarehouse.entity.Bid;
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
    public Response getAuctions(@QueryParam("status") String status) {
        try {
            List<Auction> auctions;
            if (status != null && !status.isEmpty()) {
                Auction.AuctionStatus auctionStatus = Auction.AuctionStatus.valueOf(status.toUpperCase());
                auctions = auctionService.findByStatus(auctionStatus);
            } else {
                auctions = auctionService.findActiveAuctions();
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
        try {
            List<Auction> auctions = auctionService.findLiveAuctions();
            List<AuctionResponse> response = auctions.stream()
                    .map(AuctionResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch live auctions"))
                    .build();
        }
    }

    @GET
    @Path("/upcoming")
    public Response getUpcomingAuctions() {
        try {
            List<Auction> auctions = auctionService.findUpcomingAuctions();
            List<AuctionResponse> response = auctions.stream()
                    .map(AuctionResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch upcoming auctions"))
                    .build();
        }
    }

    @GET
    @Path("/ending-soon")
    public Response getEndingSoonAuctions() {
        try {
            List<Auction> auctions = auctionService.findEndingSoonAuctions();
            List<AuctionResponse> response = auctions.stream()
                    .map(AuctionResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch ending soon auctions"))
                    .build();
        }
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

    @POST
    @Path("/{id}/bids")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response placeBid(@PathParam("id") Long auctionId, @Valid BidRequest request) {
        try {
            Bid bid = auctionService.placeBid(auctionId, request.userId, request.bidAmount, 
                                            request.isAutoBid, request.maxAutoBid);
            return Response.status(Response.Status.CREATED)
                    .entity(new BidResponse(bid))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}/bids")
    public Response getAuctionBids(@PathParam("id") Long auctionId) {
        try {
            List<Bid> bids = auctionService.getAuctionBids(auctionId);
            List<BidResponse> response = bids.stream()
                    .map(BidResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch auction bids"))
                    .build();
        }
    }

    @POST
    @Path("/{id}/watch")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response watchAuction(@PathParam("id") Long auctionId, @Valid WatchRequest request) {
        try {
            auctionService.watchAuction(auctionId, request.userId);
            return Response.ok(new SuccessResponse("Auction added to watchlist")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/watch/{userId}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response unwatchAuction(@PathParam("id") Long auctionId, @PathParam("userId") Long userId) {
        try {
            auctionService.unwatchAuction(auctionId, userId);
            return Response.ok(new SuccessResponse("Auction removed from watchlist")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/bids")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getUserBids(@PathParam("userId") Long userId) {
        try {
            List<Bid> bids = auctionService.getUserBids(userId);
            List<BidResponse> response = bids.stream()
                    .map(BidResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch user bids"))
                    .build();
        }
    }

    @GET
    @Path("/user/{userId}/watching")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getUserWatchedAuctions(@PathParam("userId") Long userId) {
        try {
            List<Auction> auctions = auctionService.getUserWatchedAuctions(userId);
            List<AuctionResponse> response = auctions.stream()
                    .map(AuctionResponse::new)
                    .toList();
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch watched auctions"))
                    .build();
        }
    }

    // Admin endpoints
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

    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response createAuction(@Valid CreateAuctionRequest request) {
        try {
            Auction auction = new Auction();
            auction.title = request.title;
            auction.description = request.description;
            auction.startingPrice = request.startingPrice;
            auction.buyNowPrice = request.buyNowPrice;
            auction.minimumBidIncrement = request.minimumBidIncrement;
            auction.startTime = request.startTime;
            auction.endTime = request.endTime;
            
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
            Auction auction = new Auction();
            auction.title = request.title;
            auction.description = request.description;
            auction.startingPrice = request.startingPrice;
            auction.buyNowPrice = request.buyNowPrice;
            auction.minimumBidIncrement = request.minimumBidIncrement;
            auction.startTime = request.startTime;
            auction.endTime = request.endTime;
            
            Auction updatedAuction = auctionService.updateAuction(id, auction);
            return Response.ok(new AuctionDetailResponse(updatedAuction)).build();
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
            auctionService.cancelAuction(id, request.reason != null ? request.reason : "Cancelled by admin");
            return Response.ok(new SuccessResponse("Auction cancelled successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // DTO Classes
    public static class BidRequest {
        public Long userId;
        public BigDecimal bidAmount;
        public Boolean isAutoBid;
        public BigDecimal maxAutoBid;
    }

    public static class WatchRequest {
        public Long userId;
    }

    public static class CreateAuctionRequest {
        public Long productId;
        public String title;
        public String description;
        public BigDecimal startingPrice;
        public BigDecimal buyNowPrice;
        public BigDecimal minimumBidIncrement;
        public LocalDateTime startTime;
        public LocalDateTime endTime;
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
        public String status;
        public Integer bidCount;
        public Integer watcherCount;
        public String productName;
        public String categoryName;
        public Integer autoExtendMinutes;
        public String winnerName;
        public BigDecimal winningBid;
        public LocalDateTime createdAt;

        public AuctionResponse(Auction auction) {
            this.id = auction.id;
            this.title = auction.title;
            this.description = auction.description;
            this.startingBid = auction.startingPrice;
            this.currentBid = auction.currentPrice;
            this.reservePrice = auction.buyNowPrice;
            this.bidIncrement = auction.minimumBidIncrement;
            this.startTime = auction.startTime;
            this.endTime = auction.endTime;
            this.status = auction.status.name();
            this.bidCount = auction.totalBids;
            this.watcherCount = auction.watchersCount;
            this.productName = auction.product != null ? auction.product.name : null;
            this.categoryName = auction.product != null && auction.product.category != null ? 
                               auction.product.category.name : null;
            this.winnerName = auction.winner != null ? auction.winner.getFullName() : null;
            this.winningBid = auction.currentPrice;
            this.createdAt = auction.createdAt;
        }
    }

    public static class AuctionDetailResponse extends AuctionResponse {
        public Long productId;
        public List<String> productImages;
        public String productBrand;
        public String productModel;

        public AuctionDetailResponse(Auction auction) {
            super(auction);
            this.productId = auction.product != null ? auction.product.id : null;
            this.productImages = auction.product != null ? auction.product.imageUrls : null;
            this.productBrand = auction.product != null ? auction.product.brand : null;
            this.productModel = auction.product != null ? auction.product.model : null;
        }
    }

    public static class BidResponse {
        public Long id;
        public BigDecimal bidAmount;
        public Boolean isAutoBid;
        public Boolean isWinning;
        public String bidderName;
        public LocalDateTime createdAt;

        public BidResponse(Bid bid) {
            this.id = bid.id;
            this.bidAmount = bid.amount;
            this.isAutoBid = bid.isAutoBid;
            this.isWinning = bid.isWinning;
            this.bidderName = bid.user != null ? bid.user.getFullName() : null;
            this.createdAt = bid.createdAt;
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
