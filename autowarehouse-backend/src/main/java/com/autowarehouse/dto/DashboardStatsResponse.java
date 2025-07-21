package com.autowarehouse.dto;

import java.math.BigDecimal;

public class DashboardStatsResponse {
    public TotalSalesThisMonth totalSalesThisMonth;
    public TotalOrdersThisMonth totalOrdersThisMonth;
    public ProductsSold productsSold;
    public LowStockAlert lowStockAlert;

    public DashboardStatsResponse() {}

    public DashboardStatsResponse(TotalSalesThisMonth totalSalesThisMonth, 
                                TotalOrdersThisMonth totalOrdersThisMonth,
                                ProductsSold productsSold, 
                                LowStockAlert lowStockAlert) {
        this.totalSalesThisMonth = totalSalesThisMonth;
        this.totalOrdersThisMonth = totalOrdersThisMonth;
        this.productsSold = productsSold;
        this.lowStockAlert = lowStockAlert;
    }

    public static class TotalSalesThisMonth {
        public BigDecimal amount;
        public Double percentage;
        public String trend;

        public TotalSalesThisMonth() {}

        public TotalSalesThisMonth(BigDecimal amount, Double percentage, String trend) {
            this.amount = amount;
            this.percentage = percentage;
            this.trend = trend;
        }
    }

    public static class TotalOrdersThisMonth {
        public Long count;
        public Double percentage;
        public String trend;

        public TotalOrdersThisMonth() {}

        public TotalOrdersThisMonth(Long count, Double percentage, String trend) {
            this.count = count;
            this.percentage = percentage;
            this.trend = trend;
        }
    }

    public static class ProductsSold {
        public Long count;
        public Double percentage;
        public String trend;

        public ProductsSold() {}

        public ProductsSold(Long count, Double percentage, String trend) {
            this.count = count;
            this.percentage = percentage;
            this.trend = trend;
        }
    }

    public static class LowStockAlert {
        public Long count;
        public String status;

        public LowStockAlert() {}

        public LowStockAlert(Long count, String status) {
            this.count = count;
            this.status = status;
        }
    }
}
