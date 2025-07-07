package com.autowarehouse.entity;

public enum AuctionStatus {
    UPCOMING("upcoming"),
    LIVE("live"),
    ENDED("ended"),
    CANCELLED("cancelled");

    private final String value;

    AuctionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static AuctionStatus fromString(String value) {
        for (AuctionStatus status : AuctionStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown auction status: " + value);
    }
}
