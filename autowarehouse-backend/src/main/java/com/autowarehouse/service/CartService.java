package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class CartService {

    @Inject
    ProductService productService;

    @Transactional
    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
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

        if (product.stockQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + product.stockQuantity);
        }

        // Check if item already exists in cart
        CartItem existingItem = CartItem.findByUserAndProduct(user, product);
        if (existingItem != null) {
            // Update quantity
            int newQuantity = existingItem.quantity + quantity;
            if (product.stockQuantity < newQuantity) {
                throw new IllegalArgumentException("Insufficient stock. Available: " + product.stockQuantity + 
                                                 ", Current in cart: " + existingItem.quantity);
            }
            existingItem.quantity = newQuantity;
            existingItem.persist();
            return existingItem;
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem(user, product, quantity);
            cartItem.persist();
            return cartItem;
        }
    }

    @Transactional
    public void updateCartItemQuantity(Long cartItemId, Integer newQuantity) {
        CartItem cartItem = CartItem.findById(cartItemId);
        if (cartItem == null) {
            throw new IllegalArgumentException("Cart item not found");
        }

        if (newQuantity <= 0) {
            // Remove item if quantity is 0 or negative
            removeFromCart(cartItemId);
            return;
        }

        if (cartItem.product.stockQuantity < newQuantity) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + cartItem.product.stockQuantity);
        }

        cartItem.updateQuantity(newQuantity);
        cartItem.persist();
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        CartItem cartItem = CartItem.findById(cartItemId);
        if (cartItem != null) {
            cartItem.delete();
        }
    }

    @Transactional
    public void removeProductFromUserCart(Long userId, Long productId) {
        User user = User.findById(userId);
        Product product = Product.findById(productId);

        if (user != null && product != null) {
            CartItem cartItem = CartItem.findByUserAndProduct(user, product);
            if (cartItem != null) {
                cartItem.delete();
            }
        }
    }

    @Transactional
    public void clearCart(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<CartItem> cartItems = CartItem.findByUser(user);
        for (CartItem cartItem : cartItems) {
            cartItem.delete();
        }
    }

    @Transactional
    public void toggleCartItemSelection(Long cartItemId) {
        CartItem cartItem = CartItem.findById(cartItemId);
        if (cartItem == null) {
            throw new IllegalArgumentException("Cart item not found");
        }

        cartItem.toggleSelection();
        cartItem.persist();
    }

    @Transactional
    public void selectAllCartItems(Long userId, boolean selected) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<CartItem> cartItems = CartItem.findByUser(user);
        for (CartItem cartItem : cartItems) {
            cartItem.isSelected = selected;
            cartItem.persist();
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return CartItem.findByUser(user);
    }

    public List<CartItem> getSelectedCartItems(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return CartItem.findSelectedByUser(user);
    }

    public long getCartItemCount(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            return 0;
        }
        return CartItem.countByUser(user);
    }

    public long getSelectedCartItemCount(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            return 0;
        }
        return CartItem.countSelectedByUser(user);
    }

    public BigDecimal getCartTotal(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        return calculateCartTotal(cartItems);
    }

    public BigDecimal getSelectedCartTotal(Long userId) {
        List<CartItem> selectedItems = getSelectedCartItems(userId);
        return calculateCartTotal(selectedItems);
    }

    public CartSummary getCartSummary(Long userId) {
        List<CartItem> allItems = getCartItems(userId);
        List<CartItem> selectedItems = getSelectedCartItems(userId);

        CartSummary summary = new CartSummary();
        summary.totalItems = allItems.size();
        summary.selectedItems = selectedItems.size();
        summary.totalQuantity = allItems.stream().mapToInt(item -> item.quantity).sum();
        summary.selectedQuantity = selectedItems.stream().mapToInt(item -> item.quantity).sum();
        summary.totalAmount = calculateCartTotal(allItems);
        summary.selectedAmount = calculateCartTotal(selectedItems);
        summary.totalSavings = calculateTotalSavings(allItems);
        summary.selectedSavings = calculateTotalSavings(selectedItems);

        return summary;
    }

    @Transactional
    public void validateCartItems(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.product;
            
            // Remove items for inactive products
            if (!product.isActive) {
                cartItem.delete();
                continue;
            }
            
            // Update quantity if stock is insufficient
            if (product.stockQuantity < cartItem.quantity) {
                if (product.stockQuantity > 0) {
                    cartItem.quantity = product.stockQuantity;
                    cartItem.persist();
                } else {
                    cartItem.delete();
                }
            }
        }
    }

    @Transactional
    public void clearCartAfterOrder(Long userId, List<CartItem> orderedItems) {
        for (CartItem cartItem : orderedItems) {
            cartItem.delete();
        }
    }

    public boolean isProductInCart(Long userId, Long productId) {
        User user = User.findById(userId);
        Product product = Product.findById(productId);
        
        if (user == null || product == null) {
            return false;
        }
        
        return CartItem.findByUserAndProduct(user, product) != null;
    }

    @Transactional
    public void updateQuantity(Long cartItemId, Integer newQuantity) {
        updateCartItemQuantity(cartItemId, newQuantity);
    }

    @Transactional
    public void toggleSelection(Long cartItemId) {
        toggleCartItemSelection(cartItemId);
    }

    @Transactional
    public void selectAll(Long userId, Boolean selected) {
        selectAllCartItems(userId, selected);
    }

    private BigDecimal calculateCartTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalSavings(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getTotalSavings)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Inner class for cart summary
    public static class CartSummary {
        public int totalItems;
        public int selectedItems;
        public int totalQuantity;
        public int selectedQuantity;
        public BigDecimal totalAmount;
        public BigDecimal selectedAmount;
        public BigDecimal totalSavings;
        public BigDecimal selectedSavings;
    }
}
