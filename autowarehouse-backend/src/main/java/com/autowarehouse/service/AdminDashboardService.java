package com.autowarehouse.service;

import com.autowarehouse.dto.*;
import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AdminDashboardService {

    private static final Logger LOG = Logger.getLogger(AdminDashboardService.class);

    @PersistenceContext
    EntityManager entityManager;

    public DashboardStatsResponse getDashboardStats() {
        LOG.info("Getting dashboard statistics");

        try {
            // Get current month stats
            LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
            
            // Get previous month stats for comparison
            LocalDateTime startOfPrevMonth = startOfMonth.minusMonths(1);
            LocalDateTime endOfPrevMonth = startOfMonth.minusSeconds(1);

            // Total sales this month
            BigDecimal currentMonthSales = getTotalSales(startOfMonth, endOfMonth);
            BigDecimal prevMonthSales = getTotalSales(startOfPrevMonth, endOfPrevMonth);
            Double salesPercentage = calculatePercentageChange(
                prevMonthSales != null ? prevMonthSales.doubleValue() : 0.0, 
                currentMonthSales != null ? currentMonthSales.doubleValue() : 0.0
            );

            // Total orders this month
            Long currentMonthOrders = getTotalOrders(startOfMonth, endOfMonth);
            Long prevMonthOrders = getTotalOrders(startOfPrevMonth, endOfPrevMonth);
            Double ordersPercentage = calculatePercentageChange(prevMonthOrders.doubleValue(), currentMonthOrders.doubleValue());

            // Products sold this month
            Long currentMonthProductsSold = getProductsSold(startOfMonth, endOfMonth);
            Long prevMonthProductsSold = getProductsSold(startOfPrevMonth, endOfPrevMonth);
            Double productsSoldPercentage = calculatePercentageChange(prevMonthProductsSold.doubleValue(), currentMonthProductsSold.doubleValue());

            // Low stock alert
            Long lowStockCount = getLowStockCount();

            return new DashboardStatsResponse(
                new DashboardStatsResponse.TotalSalesThisMonth(currentMonthSales, salesPercentage, getTrend(salesPercentage)),
                new DashboardStatsResponse.TotalOrdersThisMonth(currentMonthOrders, ordersPercentage, getTrend(ordersPercentage)),
                new DashboardStatsResponse.ProductsSold(currentMonthProductsSold, productsSoldPercentage, getTrend(productsSoldPercentage)),
                new DashboardStatsResponse.LowStockAlert(lowStockCount, lowStockCount > 0 ? "warning" : "good")
            );

        } catch (Exception e) {
            LOG.errorf("Error getting dashboard stats: %s", e.getMessage());
            throw new RuntimeException("Failed to get dashboard statistics", e);
        }
    }

    public PendingOrdersResponse getPendingOrders() {
        LOG.info("Getting pending orders");

        try {
            List<Order> pendingOrders = Order.find("status IN ('PENDING', 'CONFIRMED') ORDER BY createdAt DESC").list();
            
            List<PendingOrdersResponse.PendingOrder> orderList = pendingOrders.stream()
                .limit(10) // Limit to 10 recent pending orders
                .map(order -> new PendingOrdersResponse.PendingOrder(
                    order.orderNumber,
                    order.user.firstName + " " + order.user.lastName,
                    order.totalAmount,
                    order.status.toString(),
                    order.createdAt
                ))
                .collect(Collectors.toList());

            return new PendingOrdersResponse((long) pendingOrders.size(), orderList);

        } catch (Exception e) {
            LOG.errorf("Error getting pending orders: %s", e.getMessage());
            throw new RuntimeException("Failed to get pending orders", e);
        }
    }

    public TopProductResponse getTopProducts(int limit) {
        LOG.info("Getting top products with limit: " + limit);

        try {
            LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

            String queryStr = """
                SELECT p.id, p.name, c.name, SUM(oi.quantity), SUM(oi.subtotal)
                FROM OrderItem oi 
                JOIN oi.product p 
                JOIN p.category c 
                JOIN oi.order o 
                WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate 
                AND o.status IN ('DELIVERED', 'SHIPPED', 'CONFIRMED')
                GROUP BY p.id, p.name, c.name
                ORDER BY SUM(oi.quantity) DESC
                """;

            Query query = entityManager.createQuery(queryStr)
                .setParameter("startDate", startOfMonth)
                .setParameter("endDate", endOfMonth)
                .setMaxResults(limit);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            List<TopProductResponse.TopProduct> topProducts = new ArrayList<>();
            int rank = 1;
            
            for (Object[] result : results) {
                Long productId = (Long) result[0];
                String productName = (String) result[1];
                String categoryName = (String) result[2];
                Long soldCount = ((Number) result[3]).longValue();
                BigDecimal totalRevenue = (BigDecimal) result[4];
                String imageUrl = null; // We'll get this separately if needed

                topProducts.add(new TopProductResponse.TopProduct(
                    rank++, productId, productName, categoryName, soldCount, totalRevenue, imageUrl
                ));
            }

            return new TopProductResponse(topProducts);

        } catch (Exception e) {
            LOG.errorf("Error getting top products: %s", e.getMessage());
            throw new RuntimeException("Failed to get top products", e);
        }
    }

    public RecentSalesResponse getRecentSales(int limit) {
        LOG.info("Getting recent sales with limit: " + limit);

        try {
            String queryStr = """
                SELECT p.name, oi.productPrice, o.updatedAt, o.orderNumber
                FROM OrderItem oi 
                JOIN oi.product p 
                JOIN oi.order o 
                WHERE o.status IN ('DELIVERED', 'SHIPPED')
                ORDER BY o.updatedAt DESC
                """;

            Query query = entityManager.createQuery(queryStr)
                .setMaxResults(limit);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            List<RecentSalesResponse.RecentSale> recentSales = results.stream()
                .map(result -> {
                    String productName = (String) result[0];
                    BigDecimal price = (BigDecimal) result[1];
                    LocalDateTime soldAt = (LocalDateTime) result[2];
                    String orderId = (String) result[3];
                    String timeAgo = formatTimeAgo(soldAt);

                    return new RecentSalesResponse.RecentSale(productName, price, soldAt, timeAgo, orderId);
                })
                .collect(Collectors.toList());

            return new RecentSalesResponse(recentSales);

        } catch (Exception e) {
            LOG.errorf("Error getting recent sales: %s", e.getMessage());
            throw new RuntimeException("Failed to get recent sales", e);
        }
    }

    public RecentTransactionsResponse getRecentTransactions(int limit) {
        LOG.info("Getting recent transactions with limit: " + limit);

        try {
            String queryStr = """
                SELECT o.orderNumber, o.orderNumber, u.firstName, u.lastName, 
                       SIZE(o.items), o.totalAmount, o.status, o.createdAt
                FROM Order o 
                JOIN o.user u 
                ORDER BY o.createdAt DESC
                """;

            Query query = entityManager.createQuery(queryStr)
                .setMaxResults(limit);

            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();

            List<RecentTransactionsResponse.RecentTransaction> transactions = results.stream()
                .map(result -> {
                    String transactionId = "TRX-" + result[0];
                    String orderId = (String) result[1];
                    String customerName = result[2] + " " + result[3];
                    Integer itemCount = (Integer) result[4];
                    BigDecimal totalAmount = (BigDecimal) result[5];
                    Order.OrderStatus status = (Order.OrderStatus) result[6];
                    LocalDateTime createdAt = (LocalDateTime) result[7];
                    
                    String statusLabel = getStatusLabel(status);

                    return new RecentTransactionsResponse.RecentTransaction(
                        transactionId, orderId, customerName, itemCount, 
                        totalAmount, status.toString(), statusLabel, createdAt
                    );
                })
                .collect(Collectors.toList());

            return new RecentTransactionsResponse(transactions);

        } catch (Exception e) {
            LOG.errorf("Error getting recent transactions: %s", e.getMessage());
            throw new RuntimeException("Failed to get recent transactions", e);
        }
    }

    // Helper methods
    private BigDecimal getTotalSales(LocalDateTime startDate, LocalDateTime endDate) {
        String queryStr = "SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.paymentStatus = 'PAID'";
        Query query = entityManager.createQuery(queryStr)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate);
        
        Object result = query.getSingleResult();
        return result != null ? (BigDecimal) result : BigDecimal.ZERO;
    }

    private Long getTotalOrders(LocalDateTime startDate, LocalDateTime endDate) {
        String queryStr = "SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate";
        Query query = entityManager.createQuery(queryStr)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate);
        
        return (Long) query.getSingleResult();
    }

    private Long getProductsSold(LocalDateTime startDate, LocalDateTime endDate) {
        String queryStr = "SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi JOIN oi.order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.status IN ('DELIVERED', 'SHIPPED', 'CONFIRMED')";
        Query query = entityManager.createQuery(queryStr)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate);
        
        Object result = query.getSingleResult();
        return result != null ? ((Number) result).longValue() : 0L;
    }

    private Long getLowStockCount() {
        return Product.count("stockQuantity <= minStockLevel AND isActive = true");
    }

    private Double calculatePercentageChange(Double oldValue, Double newValue) {
        if (oldValue == null || oldValue == 0) {
            return newValue != null && newValue > 0 ? 100.0 : 0.0;
        }
        if (newValue == null) {
            return -100.0;
        }
        return ((newValue - oldValue) / oldValue) * 100.0;
    }

    private String getTrend(Double percentage) {
        if (percentage == null || percentage == 0) {
            return "neutral";
        }
        return percentage > 0 ? "up" : "down";
    }

    private String formatTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        
        if (minutes < 1) {
            return "Baru saja";
        } else if (minutes < 60) {
            return minutes + " menit yang lalu";
        } else if (minutes < 1440) { // 24 hours
            long hours = minutes / 60;
            return hours + " jam yang lalu";
        } else {
            long days = minutes / 1440;
            return days + " hari yang lalu";
        }
    }

    private String getStatusLabel(Order.OrderStatus status) {
        return switch (status) {
            case PENDING -> "Pending";
            case CONFIRMED -> "Dikonfirmasi";
            case SHIPPED -> "Dikirim";
            case DELIVERED -> "Selesai";
            case CANCELLED -> "Dibatalkan";
            case REFUNDED -> "Dikembalikan";
        };
    }
}
