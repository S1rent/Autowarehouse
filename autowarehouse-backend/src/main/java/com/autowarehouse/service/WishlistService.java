package com.autowarehouse.service;

import com.autowarehouse.entity.User;
import com.autowarehouse.entity.Product;
import com.autowarehouse.entity.WishlistItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class WishlistService {

    private static final Logger LOGGER = Logger.getLogger(WishlistService.class.getName());

    @Inject
    EntityManager entityManager;

    /**
     * Get user's wishlist items
     * @param userId The user ID
     * @return List of WishlistItem with product details
     */
    public List<WishlistItem> getUserWishlist(Long userId) {
        LOGGER.info("Getting wishlist for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            // Get wishlist items with product details using JPQL
            List<WishlistItem> wishlistItems = entityManager
                .createQuery("SELECT w FROM WishlistItem w JOIN FETCH w.product p WHERE w.user.id = :userId AND p.isActive = true ORDER BY w.createdAt DESC", WishlistItem.class)
                .setParameter("userId", userId)
                .getResultList();
            
            LOGGER.info("Found " + wishlistItems.size() + " wishlist items for user: " + userId);
            return wishlistItems;
            
        } catch (Exception e) {
            LOGGER.severe("Error getting wishlist for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to get user wishlist", e);
        }
    }

    /**
     * Add product to user's wishlist
     * @param userId The user ID
     * @param productId The product ID
     * @return The created WishlistItem
     */
    @Transactional
    public WishlistItem addToWishlist(Long userId, Long productId) {
        LOGGER.info("Adding product " + productId + " to wishlist for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            // Validate product exists and is active
            Product product = entityManager.find(Product.class, productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found with ID: " + productId);
            }
            if (!product.isActive) {
                throw new IllegalArgumentException("Product is not active: " + productId);
            }
            
            // Check for duplicates
            WishlistItem existingItem = WishlistItem.findByUserIdAndProductId(userId, productId);
            if (existingItem != null) {
                LOGGER.info("Product " + productId + " already in wishlist for user: " + userId);
                return existingItem;
            }
            
            // Create new wishlist item
            WishlistItem wishlistItem = new WishlistItem(user, product);
            wishlistItem.persist();
            
            LOGGER.info("Successfully added product " + productId + " to wishlist for user: " + userId);
            return wishlistItem;
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error adding to wishlist: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error adding product " + productId + " to wishlist for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to add product to wishlist", e);
        }
    }

    /**
     * Remove product from user's wishlist
     * @param userId The user ID
     * @param productId The product ID
     */
    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        LOGGER.info("Removing product " + productId + " from wishlist for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            // Find wishlist item
            WishlistItem wishlistItem = WishlistItem.findByUserIdAndProductId(userId, productId);
            if (wishlistItem == null) {
                LOGGER.warning("Wishlist item not found for user " + userId + " and product " + productId);
                return; // Silently ignore if item doesn't exist
            }
            
            // Delete the wishlist item
            wishlistItem.delete();
            
            LOGGER.info("Successfully removed product " + productId + " from wishlist for user: " + userId);
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error removing from wishlist: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error removing product " + productId + " from wishlist for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to remove product from wishlist", e);
        }
    }

    /**
     * Check if product is in user's wishlist
     * @param userId The user ID
     * @param productId The product ID
     * @return true if product is in wishlist, false otherwise
     */
    public boolean isInWishlist(Long userId, Long productId) {
        LOGGER.info("Checking if product " + productId + " is in wishlist for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            boolean exists = WishlistItem.existsByUserIdAndProductId(userId, productId);
            LOGGER.info("Product " + productId + " in wishlist for user " + userId + ": " + exists);
            return exists;
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error checking wishlist: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error checking wishlist for user " + userId + " and product " + productId + ": " + e.getMessage());
            throw new RuntimeException("Failed to check wishlist", e);
        }
    }

    /**
     * Get wishlist count for user
     * @param userId The user ID
     * @return Number of items in user's wishlist
     */
    public long getWishlistCount(Long userId) {
        LOGGER.info("Getting wishlist count for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            long count = WishlistItem.countByUserId(userId);
            LOGGER.info("Wishlist count for user " + userId + ": " + count);
            return count;
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error getting wishlist count: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error getting wishlist count for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to get wishlist count", e);
        }
    }

    /**
     * Clear entire wishlist for user
     * @param userId The user ID
     */
    @Transactional
    public void clearWishlist(Long userId) {
        LOGGER.info("Clearing wishlist for user: " + userId);
        
        try {
            // Validate user exists
            User user = entityManager.find(User.class, userId);
            if (user == null) {
                throw new IllegalArgumentException("User not found with ID: " + userId);
            }
            
            // Get all wishlist items for user
            List<WishlistItem> wishlistItems = WishlistItem.findByUserId(userId);
            
            // Delete all items
            for (WishlistItem item : wishlistItems) {
                item.delete();
            }
            
            LOGGER.info("Successfully cleared wishlist for user: " + userId + " (" + wishlistItems.size() + " items removed)");
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error clearing wishlist: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error clearing wishlist for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to clear wishlist", e);
        }
    }

    /**
     * Validate user ID parameter
     * @param userId The user ID to validate
     */
    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID: " + userId);
        }
    }

    /**
     * Validate product ID parameter
     * @param productId The product ID to validate
     */
    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID: " + productId);
        }
    }

    /**
     * Get user entity with validation
     * @param userId The user ID
     * @return User entity
     * @throws IllegalArgumentException if user not found
     */
    private User getUserWithValidation(Long userId) {
        validateUserId(userId);
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return user;
    }

    /**
     * Get product entity with validation
     * @param productId The product ID
     * @return Product entity
     * @throws IllegalArgumentException if product not found or inactive
     */
    private Product getProductWithValidation(Long productId) {
        validateProductId(productId);
        Product product = entityManager.find(Product.class, productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        if (!product.isActive) {
            throw new IllegalArgumentException("Product is not active: " + productId);
        }
        return product;
    }

    /**
     * Enhanced method to get user's wishlist with product details for API response
     * @param userId The user ID
     * @return List of WishlistItem with full product details
     */
    public List<WishlistItem> getUserWishlistWithProducts(Long userId) {
        LOGGER.info("Getting wishlist with product details for user: " + userId);
        
        try {
            getUserWithValidation(userId);
            
            // Get wishlist items with eager loading of product details
            List<WishlistItem> wishlistItems = entityManager
                .createQuery("SELECT DISTINCT w FROM WishlistItem w " +
                           "JOIN FETCH w.product p " +
                           "LEFT JOIN FETCH p.category " +
                           "WHERE w.user.id = :userId AND p.isActive = true " +
                           "ORDER BY w.createdAt DESC", WishlistItem.class)
                .setParameter("userId", userId)
                .getResultList();
            
            LOGGER.info("Found " + wishlistItems.size() + " wishlist items with product details for user: " + userId);
            return wishlistItems;
            
        } catch (IllegalArgumentException e) {
            LOGGER.warning("Validation error getting wishlist with products: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.severe("Error getting wishlist with products for user " + userId + ": " + e.getMessage());
            throw new RuntimeException("Failed to get user wishlist with products", e);
        }
    }
    
}
