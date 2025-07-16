package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SavedItemService {

    @Inject
    CartService cartService;

    @Transactional
    public SavedItem saveForLater(Long userId, Long productId, Integer quantity) {
        User user = User.findById(userId);
        Product product = Product.findById(productId);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        if (!product.isActive) {
            throw new IllegalArgumentException("Product is not active");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Check if item already exists in saved items
        SavedItem existingItem = SavedItem.findByUserAndProduct(user, product);
        if (existingItem != null) {
            // Update quantity
            existingItem.quantity = quantity;
            existingItem.persist();
            
            // Force initialization of lazy-loaded collections
            if (existingItem.product != null && existingItem.product.imageUrls != null) {
                existingItem.product.imageUrls.size();
            }
            
            return existingItem;
        } else {
            // Create new saved item
            SavedItem savedItem = new SavedItem(user, product, quantity, false);
            savedItem.persist();
            
            // Force initialization of lazy-loaded collections
            if (savedItem.product != null && savedItem.product.imageUrls != null) {
                savedItem.product.imageUrls.size();
            }
            
            return savedItem;
        }
    }

    @Transactional
    public SavedItem moveToSaved(Long cartItemId) {
        CartItem cartItem = CartItem.findById(cartItemId);
        if (cartItem == null) {
            throw new IllegalArgumentException("Cart item not found");
        }

        User user = cartItem.user;
        Product product = cartItem.product;
        Integer quantity = cartItem.quantity;

        // Check if item already exists in saved items
        SavedItem existingItem = SavedItem.findByUserAndProduct(user, product);
        if (existingItem != null) {
            // Update quantity (add to existing)
            existingItem.quantity += quantity;
            existingItem.savedFromCart = true;
            existingItem.persist();
            
            // Remove from cart
            cartItem.delete();
            
            // Force initialization of lazy-loaded collections
            if (existingItem.product != null && existingItem.product.imageUrls != null) {
                existingItem.product.imageUrls.size();
            }
            
            return existingItem;
        } else {
            // Create new saved item
            SavedItem savedItem = new SavedItem(user, product, quantity, true);
            savedItem.persist();
            
            // Remove from cart
            cartItem.delete();
            
            // Force initialization of lazy-loaded collections
            if (savedItem.product != null && savedItem.product.imageUrls != null) {
                savedItem.product.imageUrls.size();
            }
            
            return savedItem;
        }
    }

    @Transactional
    public CartItem moveToCart(Long savedItemId) {
        SavedItem savedItem = SavedItem.findById(savedItemId);
        if (savedItem == null) {
            throw new IllegalArgumentException("Saved item not found");
        }

        User user = savedItem.user;
        Product product = savedItem.product;
        Integer quantity = savedItem.quantity;

        // Validate product availability
        if (!product.isActive) {
            throw new IllegalArgumentException("Product is no longer active");
        }

        if (product.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.stockQuantity);
        }

        // Check if item already exists in cart
        CartItem existingCartItem = CartItem.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            // Update quantity (add to existing)
            int newQuantity = existingCartItem.quantity + quantity;
            if (product.stockQuantity < newQuantity) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + product.stockQuantity + 
                                                 ", Current in cart: " + existingCartItem.quantity);
            }
            existingCartItem.quantity = newQuantity;
            existingCartItem.persist();
            
            // Remove from saved items
            savedItem.delete();
            
            // Force initialization of lazy-loaded collections
            if (existingCartItem.product != null && existingCartItem.product.imageUrls != null) {
                existingCartItem.product.imageUrls.size();
            }
            
            return existingCartItem;
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem(user, product, quantity);
            cartItem.persist();
            
            // Remove from saved items
            savedItem.delete();
            
            // Force initialization of lazy-loaded collections
            if (cartItem.product != null && cartItem.product.imageUrls != null) {
                cartItem.product.imageUrls.size();
            }
            
            return cartItem;
        }
    }

    @Transactional
    public void updateQuantity(Long savedItemId, Integer newQuantity) {
        SavedItem savedItem = SavedItem.findById(savedItemId);
        if (savedItem == null) {
            throw new IllegalArgumentException("Saved item not found");
        }

        if (newQuantity <= 0) {
            // Remove item if quantity is 0 or negative
            removeSavedItem(savedItemId);
            return;
        }

        savedItem.updateQuantity(newQuantity);
        savedItem.persist();
    }

    @Transactional
    public void removeSavedItem(Long savedItemId) {
        SavedItem savedItem = SavedItem.findById(savedItemId);
        if (savedItem != null) {
            savedItem.delete();
        }
    }

    @Transactional
    public void removeProductFromUserSaved(Long userId, Long productId) {
        User user = User.findById(userId);
        Product product = Product.findById(productId);

        if (user != null && product != null) {
            SavedItem savedItem = SavedItem.findByUserAndProduct(user, product);
            if (savedItem != null) {
                savedItem.delete();
            }
        }
    }

    @Transactional
    public void clearSavedItems(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        SavedItem.deleteByUser(user);
    }

    @Transactional
    public List<SavedItem> getSavedItems(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        
        List<SavedItem> savedItems = SavedItem.findByUser(user);
        
        // Force initialization of lazy-loaded collections
        for (SavedItem savedItem : savedItems) {
            if (savedItem.product != null && savedItem.product.imageUrls != null) {
                savedItem.product.imageUrls.size();
            }
        }
        
        return savedItems;
    }

    public long getSavedItemCount(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            return 0;
        }
        return SavedItem.countByUser(user);
    }

    public boolean isProductSaved(Long userId, Long productId) {
        User user = User.findById(userId);
        Product product = Product.findById(productId);
        
        if (user == null || product == null) {
            return false;
        }
        
        return SavedItem.existsByUserAndProduct(user, product);
    }

    @Transactional
    public void validateSavedItems(Long userId) {
        List<SavedItem> savedItems = getSavedItems(userId);
        
        for (SavedItem savedItem : savedItems) {
            Product product = savedItem.product;
            
            // Remove items for inactive products
            if (!product.isActive) {
                savedItem.delete();
            }
        }
    }

    @Transactional
    public List<CartItem> moveAllToCart(Long userId) {
        List<SavedItem> savedItems = getSavedItems(userId);
        List<CartItem> cartItems = new java.util.ArrayList<>();
        
        for (SavedItem savedItem : savedItems) {
            try {
                CartItem cartItem = moveToCart(savedItem.id);
                cartItems.add(cartItem);
            } catch (Exception e) {
                // Skip items that can't be moved (e.g., out of stock)
                System.err.println("Failed to move saved item to cart: " + e.getMessage());
            }
        }
        
        return cartItems;
    }
}
