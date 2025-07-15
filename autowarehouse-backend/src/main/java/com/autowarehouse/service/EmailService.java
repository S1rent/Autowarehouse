package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    @ConfigProperty(name = "quarkus.application.name", defaultValue = "Autowarehouse")
    String applicationName;

    @ConfigProperty(name = "app.frontend.url", defaultValue = "http://localhost:3000")
    String frontendUrl;

    public void sendEmailVerification(User user, String verificationToken) {
        String verificationUrl = frontendUrl + "/auth/verify-email?token=" + verificationToken;
        
        String subject = "Verify Your Email - " + applicationName;
        String htmlContent = buildEmailVerificationTemplate(user, verificationUrl);
        
        mailer.send(Mail.withHtml(user.email, subject, htmlContent));
    }

    public void sendPasswordResetEmail(User user, String resetToken) {
        String resetUrl = frontendUrl + "/auth/reset-password?token=" + resetToken;
        
        String subject = "Reset Your Password - " + applicationName;
        String htmlContent = buildPasswordResetTemplate(user, resetUrl);
        
        mailer.send(Mail.withHtml(user.email, subject, htmlContent));
    }

    public void sendWelcomeEmail(User user) {
        String subject = "Welcome to " + applicationName + "!";
        String htmlContent = buildWelcomeTemplate(user);
        
        mailer.send(Mail.withHtml(user.email, subject, htmlContent));
    }

    public void sendPasswordChangedNotification(User user) {
        String subject = "Password Changed - " + applicationName;
        String htmlContent = buildPasswordChangedTemplate(user);
        
        mailer.send(Mail.withHtml(user.email, subject, htmlContent));
    }

    private String buildEmailVerificationTemplate(User user, String verificationUrl) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Email Verification</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .button { display: inline-block; background: #667eea; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Verify Your Email Address</p>
                    </div>
                    <div class="content">
                        <h2>Hello %s!</h2>
                        <p>Thank you for registering with %s. To complete your registration, please verify your email address by clicking the button below:</p>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">Verify Email Address</a>
                        </div>
                        
                        <p>If the button doesn't work, you can copy and paste this link into your browser:</p>
                        <p style="word-break: break-all; color: #667eea;">%s</p>
                        
                        <p><strong>This verification link will expire in 7 days.</strong></p>
                        
                        <p>If you didn't create an account with us, please ignore this email.</p>
                        
                        <p>Best regards,<br>The %s Team</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2025 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                applicationName,
                user.firstName != null ? user.firstName : "User",
                applicationName,
                verificationUrl,
                verificationUrl,
                applicationName,
                applicationName
            );
    }

    private String buildPasswordResetTemplate(User user, String resetUrl) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .button { display: inline-block; background: #e74c3c; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 14px; }
                    .warning { background: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; border-radius: 5px; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>Password Reset Request</p>
                    </div>
                    <div class="content">
                        <h2>Hello %s!</h2>
                        <p>We received a request to reset your password for your %s account.</p>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">Reset Password</a>
                        </div>
                        
                        <p>If the button doesn't work, you can copy and paste this link into your browser:</p>
                        <p style="word-break: break-all; color: #667eea;">%s</p>
                        
                        <div class="warning">
                            <strong>⚠️ Important Security Information:</strong>
                            <ul>
                                <li>This password reset link will expire in 24 hours</li>
                                <li>If you didn't request this reset, please ignore this email</li>
                                <li>For security, this link can only be used once</li>
                            </ul>
                        </div>
                        
                        <p>If you continue to have problems, please contact our support team.</p>
                        
                        <p>Best regards,<br>The %s Team</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2025 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                applicationName,
                user.firstName != null ? user.firstName : "User",
                applicationName,
                resetUrl,
                resetUrl,
                applicationName,
                applicationName
            );
    }

    private String buildWelcomeTemplate(User user) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Welcome</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .button { display: inline-block; background: #27ae60; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 14px; }
                    .features { background: white; padding: 20px; border-radius: 5px; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Welcome to %s!</h1>
                        <p>🎉 Your account has been successfully verified</p>
                    </div>
                    <div class="content">
                        <h2>Hello %s!</h2>
                        <p>Congratulations! Your email has been verified and your account is now active. You can now enjoy all the features of %s.</p>
                        
                        <div class="features">
                            <h3>What you can do now:</h3>
                            <ul>
                                <li>🛍️ Browse and purchase products</li>
                                <li>🏆 Participate in live auctions</li>
                                <li>❤️ Create your wishlist</li>
                                <li>📦 Track your orders</li>
                                <li>🔔 Receive personalized notifications</li>
                            </ul>
                        </div>
                        
                        <div style="text-align: center;">
                            <a href="%s" class="button">Start Shopping</a>
                        </div>
                        
                        <p>If you have any questions or need assistance, our support team is here to help.</p>
                        
                        <p>Happy shopping!<br>The %s Team</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2025 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                applicationName,
                user.firstName != null ? user.firstName : "User",
                applicationName,
                frontendUrl,
                applicationName,
                applicationName
            );
    }

    private String buildPasswordChangedTemplate(User user) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Changed</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 14px; }
                    .security-notice { background: #d4edda; border: 1px solid #c3e6cb; padding: 15px; border-radius: 5px; margin: 20px 0; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                        <p>🔒 Password Successfully Changed</p>
                    </div>
                    <div class="content">
                        <h2>Hello %s!</h2>
                        <p>This is a confirmation that your password has been successfully changed for your %s account.</p>
                        
                        <div class="security-notice">
                            <strong>✅ Security Confirmation:</strong>
                            <ul>
                                <li>Password changed successfully</li>
                                <li>Your account remains secure</li>
                                <li>All active sessions have been logged out</li>
                            </ul>
                        </div>
                        
                        <p><strong>If you didn't make this change:</strong></p>
                        <p>Please contact our support team immediately if you didn't authorize this password change. Your account security is our top priority.</p>
                        
                        <p>For your security, you'll need to log in again with your new password.</p>
                        
                        <p>Best regards,<br>The %s Team</p>
                    </div>
                    <div class="footer">
                        <p>&copy; 2025 %s. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                applicationName,
                user.firstName != null ? user.firstName : "User",
                applicationName,
                applicationName,
                applicationName
            );
    }
}
