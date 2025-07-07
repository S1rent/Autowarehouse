package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan", defaultValue = "86400")
    Long tokenLifespan;

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://autowarehouse.com")
    String issuer;

    public String generateToken(User user) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        return claimsBuilder
                .issuer(issuer)
                .subject(user.email)
                .upn(user.email)
                .preferredUserName(user.email)
                .claim("userId", user.id)
                .claim("email", user.email)
                .claim("firstName", user.firstName)
                .claim("lastName", user.lastName)
                .claim("role", user.role.name())
                .groups(Set.of(user.role.name()))
                .expiresIn(Duration.ofSeconds(tokenLifespan))
                .sign();
    }

    public String generateRefreshToken(User user) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        return claimsBuilder
                .issuer(issuer)
                .subject(user.email)
                .upn(user.email)
                .claim("userId", user.id)
                .claim("type", "refresh")
                .expiresIn(Duration.ofDays(30)) // Refresh token valid for 30 days
                .sign();
    }

    public String generatePasswordResetToken(User user) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        return claimsBuilder
                .issuer(issuer)
                .subject(user.email)
                .upn(user.email)
                .claim("userId", user.id)
                .claim("type", "password_reset")
                .expiresIn(Duration.ofHours(24)) // Password reset token valid for 24 hours
                .sign();
    }

    public String generateEmailVerificationToken(User user) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        return claimsBuilder
                .issuer(issuer)
                .subject(user.email)
                .upn(user.email)
                .claim("userId", user.id)
                .claim("type", "email_verification")
                .expiresIn(Duration.ofDays(7)) // Email verification token valid for 7 days
                .sign();
    }
}
