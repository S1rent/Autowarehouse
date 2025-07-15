package com.autowarehouse.security;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class SecurityConfig {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    public Long getCurrentUserId() {
        if (securityIdentity.isAnonymous()) {
            return null;
        }
        
        try {
            return jwt.getClaim("userId");
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentUserEmail() {
        if (securityIdentity.isAnonymous()) {
            return null;
        }
        
        return securityIdentity.getPrincipal().getName();
    }

    public String getCurrentUserRole() {
        if (securityIdentity.isAnonymous()) {
            return null;
        }
        
        try {
            return jwt.getClaim("role");
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isAuthenticated() {
        return !securityIdentity.isAnonymous();
    }

    public boolean hasRole(String role) {
        return securityIdentity.hasRole(role);
    }

    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public boolean isCustomer() {
        return hasRole("CUSTOMER");
    }
}
