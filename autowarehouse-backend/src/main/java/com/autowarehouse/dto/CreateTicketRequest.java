package com.autowarehouse.dto;

import com.autowarehouse.entity.TicketCategory;
import com.autowarehouse.entity.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTicketRequest {

    @NotBlank
    @Size(max = 255)
    public String subject;

    @NotBlank
    public String description;

    @NotNull
    public TicketCategory category;

    @NotNull
    public TicketPriority priority;

    // Constructors
    public CreateTicketRequest() {}

    public CreateTicketRequest(String subject, String description, TicketCategory category, TicketPriority priority) {
        this.subject = subject;
        this.description = description;
        this.category = category;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "CreateTicketRequest{" +
                "subject='" + subject + '\'' +
                ", category=" + category +
                ", priority=" + priority +
                '}';
    }
}
