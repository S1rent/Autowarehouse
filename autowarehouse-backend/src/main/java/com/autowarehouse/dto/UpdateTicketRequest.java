package com.autowarehouse.dto;

import com.autowarehouse.entity.TicketStatus;
import com.autowarehouse.entity.TicketPriority;

public class UpdateTicketRequest {

    public TicketStatus status;
    public TicketPriority priority;
    public Long assignedAgentId;

    // Constructors
    public UpdateTicketRequest() {}

    public UpdateTicketRequest(TicketStatus status, TicketPriority priority, Long assignedAgentId) {
        this.status = status;
        this.priority = priority;
        this.assignedAgentId = assignedAgentId;
    }

    @Override
    public String toString() {
        return "UpdateTicketRequest{" +
                "status=" + status +
                ", priority=" + priority +
                ", assignedAgentId=" + assignedAgentId +
                '}';
    }
}
