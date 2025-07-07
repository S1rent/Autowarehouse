package com.autowarehouse.entity;

public enum NotificationType {
    ORDER_CONFIRMED("order_confirmed"),
    ORDER_SHIPPED("order_shipped"),
    ORDER_DELIVERED("order_delivered"),
    ORDER_CANCELLED("order_cancelled"),
    AUCTION_STARTED("auction_started"),
    AUCTION_ENDING_SOON("auction_ending_soon"),
    AUCTION_WON("auction_won"),
    AUCTION_LOST("auction_lost"),
    AUCTION_OUTBID("auction_outbid"),
    PRODUCT_BACK_IN_STOCK("product_back_in_stock"),
    PRODUCT_PRICE_DROP("product_price_drop"),
    PAYMENT_SUCCESSFUL("payment_successful"),
    PAYMENT_FAILED("payment_failed"),
    REVIEW_APPROVED("review_approved"),
    REVIEW_REJECTED("review_rejected"),
    SYSTEM_MAINTENANCE("system_maintenance"),
    PROMOTIONAL("promotional"),
    GENERAL("general");

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static NotificationType fromString(String value) {
        for (NotificationType type : NotificationType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown notification type: " + value);
    }

    public String getDisplayName() {
        switch (this) {
            case ORDER_CONFIRMED: return "Order Confirmed";
            case ORDER_SHIPPED: return "Order Shipped";
            case ORDER_DELIVERED: return "Order Delivered";
            case ORDER_CANCELLED: return "Order Cancelled";
            case AUCTION_STARTED: return "Auction Started";
            case AUCTION_ENDING_SOON: return "Auction Ending Soon";
            case AUCTION_WON: return "Auction Won";
            case AUCTION_LOST: return "Auction Lost";
            case AUCTION_OUTBID: return "Outbid in Auction";
            case PRODUCT_BACK_IN_STOCK: return "Product Back in Stock";
            case PRODUCT_PRICE_DROP: return "Price Drop Alert";
            case PAYMENT_SUCCESSFUL: return "Payment Successful";
            case PAYMENT_FAILED: return "Payment Failed";
            case REVIEW_APPROVED: return "Review Approved";
            case REVIEW_REJECTED: return "Review Rejected";
            case SYSTEM_MAINTENANCE: return "System Maintenance";
            case PROMOTIONAL: return "Promotional";
            case GENERAL: return "General";
            default: return value;
        }
    }

    public String getDefaultIcon() {
        switch (this) {
            case ORDER_CONFIRMED:
            case ORDER_SHIPPED:
            case ORDER_DELIVERED: return "fas fa-box";
            case ORDER_CANCELLED: return "fas fa-times-circle";
            case AUCTION_STARTED:
            case AUCTION_ENDING_SOON:
            case AUCTION_WON:
            case AUCTION_LOST:
            case AUCTION_OUTBID: return "fas fa-gavel";
            case PRODUCT_BACK_IN_STOCK: return "fas fa-cube";
            case PRODUCT_PRICE_DROP: return "fas fa-tag";
            case PAYMENT_SUCCESSFUL: return "fas fa-check-circle";
            case PAYMENT_FAILED: return "fas fa-exclamation-triangle";
            case REVIEW_APPROVED: return "fas fa-star";
            case REVIEW_REJECTED: return "fas fa-star-half-alt";
            case SYSTEM_MAINTENANCE: return "fas fa-tools";
            case PROMOTIONAL: return "fas fa-gift";
            case GENERAL: return "fas fa-bell";
            default: return "fas fa-info-circle";
        }
    }
}
