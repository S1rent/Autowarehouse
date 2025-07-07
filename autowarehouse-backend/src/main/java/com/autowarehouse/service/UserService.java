package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import com.autowarehouse.entity.UserRole;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

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
        user.emailVerificationToken = UUID.randomUUID().toString();
        user.role = user.role != null ? user.role : "CUSTOMER";

        user.persist();
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
        user.lastLoginAt = LocalDateTime.now();
        user.persist();
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
    public User updateProfile(Long userId, String firstName, String lastName, String phoneNumber) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        user.firstName = firstName;
        user.lastName = lastName;
        user.phoneNumber = phoneNumber;
        user.persist();
        
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

        user.passwordResetToken = UUID.randomUUID().toString();
        user.passwordResetTokenExpiry = LocalDateTime.now().plusHours(24);
        user.persist();

        // TODO: Send password reset email
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
