package com.autowarehouse.dto;

import java.math.BigDecimal;
import java.util.List;

public class TopProductResponse {
    public List<TopProduct> topProducts;

    public TopProductResponse() {}

    public TopProductResponse(List<TopProduct> topProducts) {
        this.topProducts = topProducts;
    }

    public static class TopProduct {
        public Integer rank;
        public Long productId;
        public String name;
        public String category;
        public Long soldCount;
        public BigDecimal totalRevenue;
        public String imageUrl;

        public TopProduct() {}

        public TopProduct(Integer rank, Long productId, String name, String category, 
                         Long soldCount, BigDecimal totalRevenue, String imageUrl) {
            this.rank = rank;
            this.productId = productId;
            this.name = name;
            this.category = category;
            this.soldCount = soldCount;
            this.totalRevenue = totalRevenue;
            this.imageUrl = imageUrl;
        }
    }
}
