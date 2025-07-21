package com.autowarehouse.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PendingOrdersResponse {
    public Long pendingOrdersCount;
    public List<PendingOrder> orders;

    public PendingOrdersResponse() {}

    public PendingOrdersResponse(Long pendingOrdersCount, List<PendingOrder> orders) {
        this.pendingOrdersCount = pendingOrdersCount;
        this.orders = orders;
    }

    public static class PendingOrder {
        public String id;
        public String customerName;
        public BigDecimal totalAmount;
        public String status;
        public LocalDateTime createdAt;

        public PendingOrder() {}

        public PendingOrder(String id, String customerName, BigDecimal totalAmount, 
                           String status, LocalDateTime createdAt) {
            this.id = id;
            this.customerName = customerName;
            this.totalAmount = totalAmount;
            this.status = status;
            this.createdAt = createdAt;
        }
    }
}
