package com.autowarehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class RecentTransactionsResponse {
    public List<RecentTransaction> recentTransactions;

    public RecentTransactionsResponse() {}

    public RecentTransactionsResponse(List<RecentTransaction> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public static class RecentTransaction {
        public String transactionId;
        public String orderId;
        public String customerName;
        public Integer itemCount;
        public BigDecimal totalAmount;
        public String status;
        public String statusLabel;
        public LocalDateTime createdAt;

        public RecentTransaction() {}

        public RecentTransaction(String transactionId, String orderId, String customerName,
                               Integer itemCount, BigDecimal totalAmount, String status,
                               String statusLabel, LocalDateTime createdAt) {
            this.transactionId = transactionId;
            this.orderId = orderId;
            this.customerName = customerName;
            this.itemCount = itemCount;
            this.totalAmount = totalAmount;
            this.status = status;
            this.statusLabel = statusLabel;
            this.createdAt = createdAt;
        }
    }
}
