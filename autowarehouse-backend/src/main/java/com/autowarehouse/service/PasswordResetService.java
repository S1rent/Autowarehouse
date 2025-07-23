package com.autowarehouse.service;

import com.autowarehouse.dto.PasswordResetConfirmRequest;
import com.autowarehouse.dto.PasswordResetRequest;
import com.autowarehouse.entity.PasswordResetToken;
import com.autowarehouse.entity.User;
import com.autowarehouse.repository.PasswordResetTokenRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class PasswordResetService {

    @Inject
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Inject
    EmailService emailService;

    private static final int TOKEN_EXPIRY_HOURS = 24;
    private static final int MAX_REQUESTS_PER_HOUR = 3;

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        User user = User.findByEmail(request.getEmail());
        if (user == null) {
            // Don't reveal if email exists or not for security
            throw new IllegalArgumentException("If this email is registered, you will receive a password reset link.");
        }

        // Check rate limiting - max 3 requests per hour
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        long recentRequests = passwordResetTokenRepository.countByUserAndCreatedAtAfter(user, oneHourAgo);
        if (recentRequests >= MAX_REQUESTS_PER_HOUR) {
            throw new IllegalArgumentException("Too many password reset requests. Please try again later.");
        }

        // Delete any existing tokens for this user
        passwordResetTokenRepository.deleteByUser(user);

        // Generate new token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS);

        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiresAt);
        passwordResetTokenRepository.persist(resetToken);

        // Send email with reset link
        String resetLink = generateResetLink(token);
        emailService.sendPasswordResetEmail(user, token);
    }

    @Transactional
    public void resetPassword(PasswordResetConfirmRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findValidToken(
            request.getToken(), 
            LocalDateTime.now()
        ).orElseThrow(() -> new IllegalArgumentException("Invalid or expired reset token"));

        // Update user password
        User user = resetToken.getUser();
        user.password = BcryptUtil.bcryptHash(request.getNewPassword());
        user.persist();

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.persist(resetToken);

        // Send confirmation email
        emailService.sendPasswordChangedNotification(user);
    }

    public boolean validateResetToken(String token) {
        return passwordResetTokenRepository.findValidToken(token, LocalDateTime.now()).isPresent();
    }

    @Transactional
    public void cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteExpiredAndUsedTokens(LocalDateTime.now());
    }

    private String generateResetLink(String token) {
        // In production, this should be configurable
        String baseUrl = System.getProperty("app.frontend.url", "http://localhost:3000");
        return baseUrl + "/reset-password?token=" + token;
    }
}
