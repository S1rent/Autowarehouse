package com.autowarehouse.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateReviewRequest {

    @NotNull(message = "Product ID is required")
    public Long productId;

    @NotNull(message = "Order ID is required")
    public Long orderId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    public Integer rating;

    @Size(max = 100, message = "Title must not exceed 100 characters")
    public String title;

    @Size(max = 1000, message = "Comment must not exceed 1000 characters")
    public String comment;

    public List<String> imageUrls;

    public List<String> videoUrls;

    // Constructors
    public CreateReviewRequest() {}

    public CreateReviewRequest(Long productId, Long orderId, Integer rating, String title, String comment) {
        this.productId = productId;
        this.orderId = orderId;
        this.rating = rating;
        this.title = title;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CreateReviewRequest{" +
                "productId=" + productId +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", imageUrls=" + imageUrls +
                ", videoUrls=" + videoUrls +
                '}';
    }
}
