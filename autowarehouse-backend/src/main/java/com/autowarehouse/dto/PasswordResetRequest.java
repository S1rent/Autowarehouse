package com.autowarehouse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequest {
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    public String email;
    
    public PasswordResetRequest() {}
    
    public PasswordResetRequest(String email) {
        this.email = email;
    }
}
