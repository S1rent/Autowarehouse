package com.autowarehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RecentSalesResponse {
    public List<RecentSale> recentSales;

    public RecentSalesResponse() {}

    public RecentSalesResponse(List<RecentSale> recentSales) {
        this.recentSales = recentSales;
    }

    public static class RecentSale {
        public String productName;
        public BigDecimal price;
        public LocalDateTime soldAt;
        public String timeAgo;
        public String orderId;

        public RecentSale() {}

        public RecentSale(String productName, BigDecimal price, LocalDateTime soldAt, 
                         String timeAgo, String orderId) {
            this.productName = productName;
            this.price = price;
            this.soldAt = soldAt;
            this.timeAgo = timeAgo;
            this.orderId = orderId;
        }
    }
}
