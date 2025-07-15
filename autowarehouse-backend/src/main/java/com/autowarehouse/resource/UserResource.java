package com.autowarehouse.resource;

import com.autowarehouse.entity.User;
import com.autowarehouse.entity.UserRole;
import com.autowarehouse.service.UserService;
import com.autowarehouse.service.JwtService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/register")
    public Response register(@Valid RegisterRequest request) {
        try {
            User user = userService.registerCustomer(
                request.email, 
                request.password, 
                request.firstName, 
                request.lastName
            );
            return Response.status(Response.Status.CREATED)
                    .entity(new UserResponse(user))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest request) {
        try {
            User user = userService.authenticate(request.email, request.password);
            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Invalid credentials"))
                        .build();
            }
            
            String token = jwtService.generateToken(user);
            return Response.ok(new LoginResponse(user, token))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Login failed"))
                    .build();
        }
    }

    @GET
    @Path("/profile/{id}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response getUserProfile(@PathParam("id") Long id) {
        try {
            User user = userService.findById(id);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("User not found"))
                        .build();
            }
            return Response.ok(new UserResponse(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to get user profile"))
                    .build();
        }
    }

    @PUT
    @Path("/profile/{id}")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response updateProfile(@PathParam("id") Long id, @Valid UpdateProfileRequest request) {
        try {
            User user = userService.updateProfile(id, request.firstName, request.lastName, request.phoneNumber);
            return Response.ok(new UserResponse(user)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/change-password")
    @RolesAllowed({"ADMIN", "CUSTOMER"})
    public Response changePassword(@Valid ChangePasswordRequest request) {
        try {
            userService.changePassword(request.userId, request.currentPassword, request.newPassword);
            return Response.ok(new SuccessResponse("Password changed successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/forgot-password")
    public Response forgotPassword(@Valid ForgotPasswordRequest request) {
        try {
            userService.resetPassword(request.email);
            return Response.ok(new SuccessResponse("Password reset email sent")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/reset-password")
    public Response resetPassword(@Valid ResetPasswordRequest request) {
        try {
            userService.confirmPasswordReset(request.token, request.newPassword);
            return Response.ok(new SuccessResponse("Password reset successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/verify-email")
    public Response verifyEmail(@Valid EmailVerificationRequest request) {
        try {
            userService.verifyEmail(request.token);
            return Response.ok(new SuccessResponse("Email verified successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // Admin endpoints
    @GET
    @Path("/admin/customers")
    @RolesAllowed("ADMIN")
    public Response getAllCustomers() {
        List<User> customers = userService.findAllCustomers();
        List<UserResponse> response = customers.stream()
                .map(UserResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getUserStats() {
        UserStatsResponse stats = new UserStatsResponse();
        stats.totalUsers = userService.getTotalUsersCount();
        stats.activeUsers = userService.getActiveUsersCount();
        stats.totalCustomers = userService.getCustomersCount();
        stats.totalAdmins = userService.getAdminsCount();
        return Response.ok(stats).build();
    }

    @PUT
    @Path("/admin/{id}/role")
    @RolesAllowed("ADMIN")
    public Response updateUserRole(@PathParam("id") Long id, @Valid UpdateRoleRequest request) {
        try {
            userService.updateRole(id, request.role);
            return Response.ok(new SuccessResponse("User role updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/deactivate")
    @RolesAllowed("ADMIN")
    public Response deactivateUser(@PathParam("id") Long id) {
        try {
            userService.deactivateUser(id);
            return Response.ok(new SuccessResponse("User deactivated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/activate")
    @RolesAllowed("ADMIN")
    public Response activateUser(@PathParam("id") Long id) {
        try {
            userService.activateUser(id);
            return Response.ok(new SuccessResponse("User activated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    // DTO Classes
    public static class RegisterRequest {
        public String email;
        public String password;
        public String firstName;
        public String lastName;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class UpdateProfileRequest {
        public String firstName;
        public String lastName;
        public String phoneNumber;
    }

    public static class ChangePasswordRequest {
        public Long userId;
        public String currentPassword;
        public String newPassword;
    }

    public static class ForgotPasswordRequest {
        public String email;
    }

    public static class ResetPasswordRequest {
        public String token;
        public String newPassword;
    }

    public static class EmailVerificationRequest {
        public String token;
    }

    public static class UpdateRoleRequest {
        public String role;
    }

    public static class UserResponse {
        public Long id;
        public String email;
        public String firstName;
        public String lastName;
        public String phoneNumber;
        public String role;
        public Boolean isActive;
        public Boolean isEmailVerified;

        public UserResponse(User user) {
            this.id = user.id;
            this.email = user.email;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
            this.phoneNumber = user.phoneNumber;
            this.role = user.role;
            this.isActive = user.isActive;
            this.isEmailVerified = user.isEmailVerified;
        }
    }

    public static class LoginResponse {
        public UserResponse user;
        public String token;

        public LoginResponse(User user, String token) {
            this.user = new UserResponse(user);
            this.token = token;
        }
    }

    public static class UserStatsResponse {
        public long totalUsers;
        public long activeUsers;
        public long totalCustomers;
        public long totalAdmins;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}
