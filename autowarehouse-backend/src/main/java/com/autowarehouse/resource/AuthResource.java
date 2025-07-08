package com.autowarehouse.resource;

import com.autowarehouse.dto.LoginRequest;
import com.autowarehouse.dto.RegisterRequest;
import com.autowarehouse.dto.PasswordResetRequest;
import com.autowarehouse.dto.PasswordResetConfirmRequest;
import com.autowarehouse.dto.AuthResponse;
import com.autowarehouse.entity.User;
import com.autowarehouse.service.UserService;
import com.autowarehouse.service.JwtService;
import com.autowarehouse.service.EmailService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication", description = "Authentication and user management endpoints")
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @Inject
    EmailService emailService;

    @POST
    @Path("/register")
    @Operation(summary = "Register new user", description = "Register a new user account and send email verification")
    public Response register(@Valid RegisterRequest request) {
        try {
            User user = userService.registerCustomer(
                request.email,
                request.password,
                request.firstName,
                request.lastName
            );

            return Response.status(Response.Status.CREATED)
                .entity(new AuthResponse(
                    "Registration successful. Please check your email to verify your account.",
                    null,
                    null,
                    user.id,
                    user.email,
                    user.firstName,
                    user.lastName,
                    user.role,
                    false // not verified yet
                ))
                .build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse(e.getMessage(), null, null, null, null, null, null, null, false))
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Registration failed. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @POST
    @Path("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT tokens")
    public Response login(@Valid LoginRequest request) {
        try {
            User user = userService.authenticate(request.email, request.password);
            
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new AuthResponse("Invalid email or password", null, null, null, null, null, null, null, false))
                    .build();
            }

            if (!user.isEmailVerified) {
                return Response.status(Response.Status.FORBIDDEN)
                    .entity(new AuthResponse("Please verify your email before logging in", null, null, null, null, null, null, null, false))
                    .build();
            }

            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            return Response.ok(new AuthResponse(
                "Login successful",
                accessToken,
                refreshToken,
                user.id,
                user.email,
                user.firstName,
                user.lastName,
                user.role,
                user.isEmailVerified
            )).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Login failed. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @POST
    @Path("/verify-email")
    @Operation(summary = "Verify email", description = "Verify user email with verification token")
    public Response verifyEmail(@QueryParam("token") String token) {
        if (token == null || token.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse("Verification token is required", null, null, null, null, null, null, null, false))
                .build();
        }

        try {
            userService.verifyEmail(token);
            
            return Response.ok(new AuthResponse(
                "Email verified successfully. Welcome to Autowarehouse!",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
            )).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse(e.getMessage(), null, null, null, null, null, null, null, false))
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Email verification failed. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @POST
    @Path("/forgot-password")
    @Operation(summary = "Request password reset", description = "Send password reset email to user")
    public Response forgotPassword(@Valid PasswordResetRequest request) {
        try {
            userService.resetPassword(request.email);
            
            return Response.ok(new AuthResponse(
                "If an account with that email exists, we've sent password reset instructions.",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false
            )).build();

        } catch (Exception e) {
            // Always return success message for security reasons
            return Response.ok(new AuthResponse(
                "If an account with that email exists, we've sent password reset instructions.",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false
            )).build();
        }
    }

    @POST
    @Path("/reset-password")
    @Operation(summary = "Reset password", description = "Reset user password with reset token")
    public Response resetPassword(@Valid PasswordResetConfirmRequest request) {
        try {
            userService.confirmPasswordReset(request.token, request.newPassword);
            
            return Response.ok(new AuthResponse(
                "Password reset successful. You can now login with your new password.",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false
            )).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse(e.getMessage(), null, null, null, null, null, null, null, false))
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Password reset failed. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @POST
    @Path("/resend-verification")
    @Operation(summary = "Resend email verification", description = "Resend email verification to user")
    public Response resendVerification(@QueryParam("email") String email) {
        if (email == null || email.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(new AuthResponse("Email is required", null, null, null, null, null, null, null, false))
                .build();
        }

        try {
            User user = userService.findByEmail(email);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(new AuthResponse("User not found", null, null, null, null, null, null, null, false))
                    .build();
            }

            if (user.isEmailVerified) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new AuthResponse("Email is already verified", null, null, null, null, null, null, null, true))
                    .build();
            }

            // Generate new verification token and send email
            String newToken = jwtService.generateEmailVerificationToken(user);
            user.emailVerificationToken = newToken;
            user.persist();
            
            // Send verification email
            emailService.sendEmailVerification(user, newToken);

            return Response.ok(new AuthResponse(
                "Verification email sent. Please check your inbox.",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false
            )).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new AuthResponse("Failed to resend verification email. Please try again.", null, null, null, null, null, null, null, false))
                .build();
        }
    }

    @GET
    @Path("/me")
    @Operation(summary = "Get current user", description = "Get current authenticated user information")
    public Response getCurrentUser() {
        // This would require JWT authentication context
        // For now, return a placeholder response
        return Response.status(Response.Status.NOT_IMPLEMENTED)
            .entity(new AuthResponse("Endpoint not implemented yet", null, null, null, null, null, null, null, false))
            .build();
    }
}
