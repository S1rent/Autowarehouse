package com.autowarehouse.repository;

import com.autowarehouse.entity.PasswordResetToken;
import com.autowarehouse.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class PasswordResetTokenRepository implements PanacheRepository<PasswordResetToken> {

    public Optional<PasswordResetToken> findValidToken(String token, LocalDateTime now) {
        return find("token = ?1 AND used = false AND expiresAt > ?2", token, now).firstResultOptional();
    }

    public void deleteByUser(User user) {
        delete("user", user);
    }

    public void deleteExpiredAndUsedTokens(LocalDateTime now) {
        delete("expiresAt < ?1 OR used = true", now);
    }

    public long countByUserAndCreatedAtAfter(User user, LocalDateTime since) {
        return count("user = ?1 AND createdAt > ?2", user, since);
    }
}
