package com.autowarehouse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProfileRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    public String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    public String lastName;
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    public String phoneNumber;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    public String address;
    
    // Default constructor
    public UpdateProfileRequest() {}
    
    // Constructor with parameters
    public UpdateProfileRequest(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
