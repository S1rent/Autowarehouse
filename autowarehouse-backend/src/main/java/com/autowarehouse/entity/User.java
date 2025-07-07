package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable = false)
    @Username
    @Email
    @NotBlank
    public String email;

    @Column(nullable = false)
    @Password
    @Size(min = 6)
    public String password;

    @Column(name = "first_name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank
    @Size(max = 50)
    public String lastName;

    @Column(name = "phone_number")
    @Size(max = 20)
    public String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Roles
    public UserRole role = UserRole.CUSTOMER;

    @Column(name = "is_active")
    public Boolean isActive = true;

    @Column(name = "is_email_verified")
    public Boolean isEmailVerified = false;

    @Column(name = "google_id")
    public String googleId;

    @Column(name = "profile_image_url")
    public String profileImageUrl;

    @Column(name = "email_verification_token")
    public String emailVerificationToken;

    @Column(name = "password_reset_token")
    public String passwordResetToken;

    @Column(name = "password_reset_expires")
    public LocalDateTime passwordResetExpires;

    @Column(name = "last_login")
    public LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Bid> bids;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<WishlistItem> wishlistItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<CartItem> cartItems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Notification> notifications;

    // Constructors
    public User() {}

    public User(String email, String password, String firstName, String lastName, UserRole role) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Static finder methods
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public static User findByGoogleId(String googleId) {
        return find("googleId", googleId).firstResult();
    }

    public static User findByEmailVerificationToken(String token) {
        return find("emailVerificationToken", token).firstResult();
    }

    public static User findByPasswordResetToken(String token) {
        return find("passwordResetToken = ?1 and passwordResetExpires > ?2", token, LocalDateTime.now()).firstResult();
    }

    public static List<User> findActiveUsers() {
        return find("isActive", true).list();
    }

    public static List<User> findByRole(UserRole role) {
        return find("role", role).list();
    }

    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isCustomer() {
        return role == UserRole.CUSTOMER;
    }

    public boolean isPasswordResetTokenValid() {
        return passwordResetToken != null && 
               passwordResetExpires != null && 
               passwordResetExpires.isAfter(LocalDateTime.now());
    }

    public void clearPasswordResetToken() {
        this.passwordResetToken = null;
        this.passwordResetExpires = null;
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}
