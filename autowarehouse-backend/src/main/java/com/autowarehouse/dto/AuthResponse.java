package com.autowarehouse.dto;

public class AuthResponse {
    
    public String message;
    public String accessToken;
    public String refreshToken;
    public Long userId;
    public String email;
    public String firstName;
    public String lastName;
    public String role;
    public Boolean isEmailVerified;
    
    public AuthResponse() {}
    
    public AuthResponse(String message, String accessToken, String refreshToken, 
                       Long userId, String email, String firstName, String lastName, 
                       String role, Boolean isEmailVerified) {
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isEmailVerified = isEmailVerified;
    }
    
    // Constructor for error responses
    public AuthResponse(String message) {
        this.message = message;
        this.accessToken = null;
        this.refreshToken = null;
        this.userId = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.role = null;
        this.isEmailVerified = false;
    }
    
    // Constructor for successful login
    public static AuthResponse success(String message, String accessToken, String refreshToken, 
                                     Long userId, String email, String firstName, String lastName, 
                                     String role, Boolean isEmailVerified) {
        return new AuthResponse(message, accessToken, refreshToken, userId, email, 
                              firstName, lastName, role, isEmailVerified);
    }
    
    // Constructor for error responses
    public static AuthResponse error(String message) {
        return new AuthResponse(message);
    }
}
