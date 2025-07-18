package com.autowarehouse.dto;

import com.autowarehouse.entity.*;
import java.time.LocalDateTime;

public class TicketResponse {

    public Long id;
    public String subject;
    public String description;
    public TicketStatus status;
    public TicketPriority priority;
    public TicketCategory category;
    public Long customerId;
    public String customerName;
    public String customerEmail;
    public Long assignedAgentId;
    public String assignedAgentName;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public LocalDateTime resolvedAt;
    public long messageCount;
    public String lastMessage;
    public LocalDateTime lastMessageTime;

    // Constructors
    public TicketResponse() {}

    public TicketResponse(SupportTicket ticket) {
        this.id = ticket.id;
        this.subject = ticket.subject;
        this.description = ticket.description;
        this.status = ticket.status;
        this.priority = ticket.priority;
        this.category = ticket.category;
        this.customerId = ticket.customerId;
        this.assignedAgentId = ticket.assignedAgentId;
        this.createdAt = ticket.createdAt;
        this.updatedAt = ticket.updatedAt;
        this.resolvedAt = ticket.resolvedAt;
        
        // Set customer info if available
        if (ticket.customer != null) {
            this.customerName = ticket.customer.getFullName();
            this.customerEmail = ticket.customer.email;
        }
        
        // Set agent info if available
        if (ticket.assignedAgent != null) {
            this.assignedAgentName = ticket.assignedAgent.getFullName();
        }
        
        // Set message info if available
        if (ticket.messages != null && !ticket.messages.isEmpty()) {
            this.messageCount = ticket.messages.size();
            ChatMessage lastMsg = ticket.messages.get(ticket.messages.size() - 1);
            this.lastMessage = lastMsg.message;
            this.lastMessageTime = lastMsg.timestamp;
        } else {
            this.messageCount = ChatMessage.countByTicketId(ticket.id);
            ChatMessage lastMsg = ChatMessage.findLatestByTicketId(ticket.id);
            if (lastMsg != null) {
                this.lastMessage = lastMsg.message;
                this.lastMessageTime = lastMsg.timestamp;
            }
        }
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", category=" + category +
                ", customerName='" + customerName + '\'' +
                ", assignedAgentName='" + assignedAgentName + '\'' +
                ", messageCount=" + messageCount +
                '}';
    }
}
