package com.autowarehouse.entity;

public enum NotificationPriority {
    LOW("low"),
    NORMAL("normal"),
    HIGH("high"),
    URGENT("urgent");

    private final String value;

    NotificationPriority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static NotificationPriority fromString(String value) {
        for (NotificationPriority priority : NotificationPriority.values()) {
            if (priority.value.equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown notification priority: " + value);
    }

    public String getDisplayName() {
        switch (this) {
            case LOW: return "Low";
            case NORMAL: return "Normal";
            case HIGH: return "High";
            case URGENT: return "Urgent";
            default: return value;
        }
    }

    public String getCssClass() {
        switch (this) {
            case LOW: return "text-gray-500";
            case NORMAL: return "text-blue-500";
            case HIGH: return "text-orange-500";
            case URGENT: return "text-red-500";
            default: return "text-gray-500";
        }
    }

    public int getNumericValue() {
        switch (this) {
            case LOW: return 1;
            case NORMAL: return 2;
            case HIGH: return 3;
            case URGENT: return 4;
            default: return 2;
        }
    }
}
