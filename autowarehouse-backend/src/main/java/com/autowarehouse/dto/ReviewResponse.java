package com.autowarehouse.dto;

import com.autowarehouse.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {

    public Long id;
    public Long productId;
    public String productName;
    public Long userId;
    public String userName;
    public Integer rating;
    public String title;
    public String comment;
    public Boolean isVerifiedPurchase;
    public Integer helpfulCount;
    public Boolean isApproved;
    public List<String> imageUrls;
    public List<String> videoUrls;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updatedAt;

    // Constructors
    public ReviewResponse() {}

    public ReviewResponse(Review review) {
        this.id = review.id;
        this.productId = review.product != null ? review.product.id : null;
        this.productName = review.product != null ? review.product.name : null;
        this.userId = review.user != null ? review.user.id : null;
        this.userName = review.user != null ? 
            (review.user.firstName + " " + review.user.lastName).trim() : null;
        this.rating = review.rating;
        this.title = review.title;
        this.comment = review.comment;
        this.isVerifiedPurchase = review.isVerifiedPurchase;
        this.helpfulCount = review.helpfulCount;
        this.isApproved = review.isApproved;
        this.createdAt = review.createdAt;
        this.updatedAt = review.updatedAt;

        // Temporarily set empty lists until migration V15 runs
        this.imageUrls = new ArrayList<>();
        this.videoUrls = new ArrayList<>();
    }

    private List<String> parseJsonToList(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", isVerifiedPurchase=" + isVerifiedPurchase +
                ", helpfulCount=" + helpfulCount +
                ", isApproved=" + isApproved +
                ", createdAt=" + createdAt +
                '}';
    }
}
