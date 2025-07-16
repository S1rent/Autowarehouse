package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    EmailService emailService;

    @Inject
    JwtService jwtService;

    @Transactional
    public User createUser(@Valid User user) {
        // Check if email already exists
        if (User.findByEmail(user.email) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Hash password
        user.password = BcryptUtil.bcryptHash(user.password);
        
        // Set default values
        user.isActive = true;
        user.isEmailVerified = false;
        user.role = user.role != null ? user.role : "CUSTOMER";

        // Generate simple verification token
        user.emailVerificationToken = "temp-token-" + System.currentTimeMillis();
        
        user.persist();
        
        // Skip email sending for now to avoid errors
        System.out.println("User registered successfully: " + user.email);
        
        return user;
    }

    @Transactional
    public User registerCustomer(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        user.role = "CUSTOMER";
        
        return createUser(user);
    }

    public User authenticate(String email, String password) {
        User user = User.findByEmail(email);
        if (user == null || !user.isActive) {
            return null;
        }

        if (BcryptUtil.matches(password, user.password)) {
            updateLastLogin(user);
            return user;
        }
        
        return null;
    }

    @Transactional
    public void updateLastLogin(User user) {
        // Merge the detached entity first
        User managedUser = User.findById(user.id);
        if (managedUser != null) {
            managedUser.lastLoginAt = LocalDateTime.now();
            managedUser.persist();
        }
    }

    public User findById(Long id) {
        return User.findById(id);
    }

    public User findByEmail(String email) {
        return User.findByEmail(email);
    }

    public List<User> findAllCustomers() {
        return User.findByRole("CUSTOMER");
    }

    public List<User> findAllAdmins() {
        return User.findByRole("ADMIN");
    }

    public List<User> findActiveUsers() {
        return User.findActiveUsers();
    }

    public List<User> findRecentUsers(int days) {
        return User.findRecentUsers(days);
    }

    @Transactional
    public User updateProfile(Long userId, String firstName, String lastName, String phoneNumber, String address) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Debug logging
        System.out.println("Updating profile for user ID: " + userId);
        System.out.println("firstName: " + firstName);
        System.out.println("lastName: " + lastName);
        System.out.println("phoneNumber: " + phoneNumber);
        System.out.println("address: " + address);
        System.out.println("Current phoneNumber in DB: " + user.phoneNumber);

        user.firstName = firstName;
        user.lastName = lastName;
        user.phoneNumber = phoneNumber;
        user.address = address;
        
        System.out.println("After setting phoneNumber: " + user.phoneNumber);
        
        user.persist();
        
        System.out.println("After persist phoneNumber: " + user.phoneNumber);
        
        return user;
    }

    @Transactional
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!BcryptUtil.matches(currentPassword, user.password)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        user.password = BcryptUtil.bcryptHash(newPassword);
        user.persist();
    }

    @Transactional
    public void resetPassword(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        String resetToken = jwtService.generatePasswordResetToken(user);
        user.passwordResetToken = resetToken;
        user.passwordResetTokenExpiry = LocalDateTime.now().plusHours(24);
        user.persist();

        // Send password reset email
        emailService.sendPasswordResetEmail(user, resetToken);
    }

    @Transactional
    public void confirmPasswordReset(String token, String newPassword) {
        User user = User.findByPasswordResetToken(token);
        if (user == null || user.isPasswordResetTokenExpired()) {
            throw new IllegalArgumentException("Invalid or expired reset token");
        }

        user.password = BcryptUtil.bcryptHash(newPassword);
        user.passwordResetToken = null;
        user.passwordResetTokenExpiry = null;
        user.persist();

        // Send password changed notification
        emailService.sendPasswordChangedNotification(user);
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = User.findByEmailVerificationToken(token);
        if (user == null) {
            throw new IllegalArgumentException("Invalid verification token");
        }

        user.isEmailVerified = true;
        user.emailVerificationToken = null;
        user.persist();

        // Send welcome email
        emailService.sendWelcomeEmail(user);
    }

    @Transactional
    public void deactivateUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.isActive = false;
        user.persist();
    }

    @Transactional
    public void activateUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.isActive = true;
        user.persist();
    }

    @Transactional
    public void updateRole(Long userId, String role) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.role = role;
        user.persist();
    }

    public long getTotalUsersCount() {
        return User.count();
    }

    public long getActiveUsersCount() {
        return User.countActiveUsers();
    }

    public long getCustomersCount() {
        return User.countByRole("CUSTOMER");
    }

    public long getAdminsCount() {
        return User.countByRole("ADMIN");
    }

    public boolean emailExists(String email) {
        return User.findByEmail(email) != null;
    }

    public boolean isValidUser(Long userId) {
        User user = User.findById(userId);
        return user != null && user.isActive;
    }
}
