package com.autowarehouse.service;

import com.autowarehouse.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    @Inject
    ProductService productService;

    @Inject
    NotificationService notificationService;

    @Transactional
    public Order createOrder(User user, List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        // Validate all cart items
        for (CartItem cartItem : cartItems) {
            if (!productService.isProductAvailable(cartItem.product.id, cartItem.quantity)) {
                throw new IllegalArgumentException("Product " + cartItem.product.name + " is not available in requested quantity");
            }
        }

        // Create order
        Order order = new Order();
        order.orderNumber = generateOrderNumber();
        order.user = user;
        order.status = Order.OrderStatus.PENDING;
        order.paymentStatus = Order.PaymentStatus.PENDING;
        order.persist();

        // Create order items
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.product = cartItem.product;
            orderItem.productName = cartItem.product.name;
            orderItem.productSku = cartItem.product.sku;
            orderItem.productPrice = cartItem.product.getCurrentPrice();
            orderItem.quantity = cartItem.quantity;
            orderItem.subtotal = orderItem.productPrice.multiply(BigDecimal.valueOf(orderItem.quantity));
            orderItem.persist();

            subtotal = subtotal.add(orderItem.subtotal);

            // Decrease product stock
            productService.decreaseStock(cartItem.product.id, cartItem.quantity);
            productService.incrementSalesCount(cartItem.product.id, cartItem.quantity);
        }

        // Calculate totals
        order.subtotal = subtotal;
        order.calculateTotals();
        order.persist();

        return order;
    }

    @Transactional
    public Order createOrderFromAuction(User user, Auction auction) {
        if (auction.winner == null || !auction.winner.id.equals(user.id)) {
            throw new IllegalArgumentException("User is not the winner of this auction");
        }

        if (auction.status != Auction.AuctionStatus.ENDED) {
            throw new IllegalArgumentException("Auction has not ended yet");
        }

        // Note: Since we don't track auction orders separately, we'll proceed with creating the order

        // Create order
        Order order = new Order();
        order.orderNumber = generateOrderNumber();
        order.user = user;
        order.status = Order.OrderStatus.PENDING;
        order.paymentStatus = Order.PaymentStatus.PENDING;
        order.persist();

        // Create order item from auction
        OrderItem orderItem = new OrderItem();
        orderItem.order = order;
        orderItem.product = auction.product;
        orderItem.productName = auction.product.name;
        orderItem.productSku = auction.product.sku;
        orderItem.quantity = 1;
        // Get the winning bid amount from the highest bid
        Bid winningBid = Bid.findWinningBid(auction);
        orderItem.productPrice = winningBid != null ? winningBid.amount : auction.currentPrice;
        orderItem.subtotal = orderItem.productPrice.multiply(BigDecimal.valueOf(orderItem.quantity));
        orderItem.persist();

        // Calculate totals
        order.subtotal = orderItem.subtotal;
        order.calculateTotals();
        order.persist();

        return order;
    }

    public Order findById(Long id) {
        return Order.findById(id);
    }

    public Order findByOrderNumber(String orderNumber) {
        return Order.findByOrderNumber(orderNumber);
    }

    public List<Order> findByUser(User user) {
        return Order.findByUser(user);
    }

    public List<Order> findByStatus(Order.OrderStatus status) {
        return Order.findByStatus(status);
    }

    public List<Order> findPendingOrders() {
        return Order.findPendingOrders();
    }

    public List<Order> findRecentOrders(int days) {
        return Order.findRecentOrders(days);
    }

    public Order createOrderFromCart(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<CartItem> cartItems = CartItem.findByUserAndSelected(user, true);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No selected items in cart");
        }

        return createOrder(user, cartItems);
    }

    @Transactional
    public Order createOrderFromCartWithDetails(Long userId, String shippingAddress, String billingAddress, String paymentMethod) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        List<CartItem> cartItems = CartItem.findByUserAndSelected(user, true);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No selected items in cart");
        }

        // Validate all cart items
        for (CartItem cartItem : cartItems) {
            if (!productService.isProductAvailable(cartItem.product.id, cartItem.quantity)) {
                throw new IllegalArgumentException("Product " + cartItem.product.name + " is not available in requested quantity");
            }
        }

        // Create order
        Order order = new Order();
        order.orderNumber = generateOrderNumber();
        order.user = user;
        order.status = Order.OrderStatus.PENDING;
        order.paymentStatus = Order.PaymentStatus.PENDING;
        order.shippingAddress = shippingAddress;
        order.billingAddress = billingAddress;
        order.paymentMethod = paymentMethod;
        
        // Calculate shipping and tax
        BigDecimal subtotal = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.product.getCurrentPrice().multiply(BigDecimal.valueOf(cartItem.quantity));
            subtotal = subtotal.add(itemTotal);
        }
        
        order.subtotal = subtotal;
        order.shippingCost = calculateShippingCost(subtotal);
        order.taxAmount = calculateTaxAmount(subtotal);
        order.totalAmount = subtotal.add(order.shippingCost).add(order.taxAmount);
        
        order.persist();

        // Create order items
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.product = cartItem.product;
            orderItem.productName = cartItem.product.name;
            orderItem.productSku = cartItem.product.sku;
            orderItem.productPrice = cartItem.product.getCurrentPrice();
            orderItem.quantity = cartItem.quantity;
            orderItem.subtotal = orderItem.productPrice.multiply(BigDecimal.valueOf(orderItem.quantity));
            orderItem.persist();

            // Decrease product stock
            productService.decreaseStock(cartItem.product.id, cartItem.quantity);
            productService.incrementSalesCount(cartItem.product.id, cartItem.quantity);
        }

        // Clear selected cart items after successful order creation
        clearSelectedCartItems(user);

        // Send order confirmation notification
        notificationService.notifyOrderCreated(user, order);

        return order;
    }

    @Transactional
    public void clearSelectedCartItems(User user) {
        List<CartItem> selectedItems = CartItem.findByUserAndSelected(user, true);
        for (CartItem item : selectedItems) {
            item.delete();
        }
    }

    private BigDecimal calculateShippingCost(BigDecimal subtotal) {
        // Free shipping over 10M IDR
        if (subtotal.compareTo(new BigDecimal("10000000")) >= 0) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal("50000"); // 50K IDR shipping cost
    }

    private BigDecimal calculateTaxAmount(BigDecimal subtotal) {
        // 11% tax (PPN in Indonesia)
        return subtotal.multiply(new BigDecimal("0.11"));
    }

    public List<Order> findByUserId(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return findByUser(user);
    }

    public List<Order> findAllOrders() {
        return Order.listAll();
    }

    @Transactional
    public void shipOrder(Long orderId, String trackingNumber) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.trackingNumber = trackingNumber;
        order.updateStatus(Order.OrderStatus.SHIPPED);
        order.persist();

        notificationService.notifyOrderShipped(order.user, order);
    }

    @Transactional
    public void deliverOrder(Long orderId) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.updateStatus(Order.OrderStatus.DELIVERED);
        order.persist();

        notificationService.notifyOrderDelivered(order.user, order);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        updateOrderStatus(orderId, newStatus, "SYSTEM", null);
    }

    @Transactional
    public void updateOrderStatus(Long orderId, Order.OrderStatus newStatus, String changedBy, String notes) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        Order.OrderStatus oldStatus = order.status;
        
        // Create status history entry
        createStatusHistory(order, newStatus, oldStatus, changedBy, notes);
        
        order.updateStatus(newStatus);
        order.persist();

        // Send notifications based on status change
        switch (newStatus) {
            case CONFIRMED:
                notificationService.notifyOrderConfirmed(order.user, order);
                break;
            case SHIPPED:
                notificationService.notifyOrderShipped(order.user, order);
                break;
            case DELIVERED:
                notificationService.notifyOrderDelivered(order.user, order);
                break;
            case CANCELLED:
                notificationService.notifyOrderCancelled(order.user, order);
                // Restore product stock if order was cancelled
                if (oldStatus == Order.OrderStatus.PENDING || oldStatus == Order.OrderStatus.CONFIRMED) {
                    restoreProductStock(order);
                }
                break;
        }
    }

    @Transactional
    public void updatePaymentStatus(Long orderId, Order.PaymentStatus paymentStatus) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.paymentStatus = paymentStatus;
        
        if (paymentStatus == Order.PaymentStatus.PAID && order.status == Order.OrderStatus.PENDING) {
            order.updateStatus(Order.OrderStatus.CONFIRMED);
        }
        
        order.persist();
    }

    @Transactional
    public void updateShippingInfo(Long orderId, String shippingAddress, String trackingNumber) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.shippingAddress = shippingAddress;
        order.trackingNumber = trackingNumber;
        order.persist();
    }

    @Transactional
    public void updateBillingAddress(Long orderId, String billingAddress) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.billingAddress = billingAddress;
        order.persist();
    }

    @Transactional
    public void addOrderNotes(Long orderId, String notes) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        order.notes = notes;
        order.persist();
    }

    @Transactional
    public void cancelOrder(Long orderId, String reason) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (!order.canBeCancelled()) {
            throw new IllegalArgumentException("Order cannot be cancelled in current status");
        }

        order.updateStatus(Order.OrderStatus.CANCELLED);
        order.notes = (order.notes != null ? order.notes + "\n" : "") + "Cancelled: " + reason;
        order.persist();

        // Restore product stock
        restoreProductStock(order);

        // Notify user
        notificationService.notifyOrderCancelled(order.user, order);
    }

    @Transactional
    public void processRefund(Long orderId, BigDecimal refundAmount) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.paymentStatus != Order.PaymentStatus.PAID) {
            throw new IllegalArgumentException("Order payment is not in paid status");
        }

        // Update payment status
        if (refundAmount.compareTo(order.totalAmount) >= 0) {
            order.paymentStatus = Order.PaymentStatus.REFUNDED;
            order.updateStatus(Order.OrderStatus.REFUNDED);
        } else {
            order.paymentStatus = Order.PaymentStatus.PARTIALLY_REFUNDED;
        }

        order.persist();

        // Create notification
        notificationService.createNotification(
            order.user,
            "Refund Processed",
            "A refund of $" + refundAmount + " has been processed for order #" + order.orderNumber,
            NotificationType.PAYMENT_SUCCESSFUL,
            order.id,
            "order"
        );
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        return OrderItem.findByOrder(order);
    }

    public BigDecimal calculateOrderTotal(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            BigDecimal itemTotal = cartItem.product.getCurrentPrice().multiply(BigDecimal.valueOf(cartItem.quantity));
            total = total.add(itemTotal);
        }
        return total;
    }

    public long getTotalOrdersCount() {
        return Order.count();
    }

    public long getPendingOrdersCount() {
        return Order.countByStatus(Order.OrderStatus.PENDING);
    }

    public long getCompletedOrdersCount() {
        return Order.countByStatus(Order.OrderStatus.DELIVERED);
    }

    public BigDecimal getTotalRevenue() {
        List<Order> completedOrders = Order.findByStatus(Order.OrderStatus.DELIVERED);
        return completedOrders.stream()
                .map(order -> order.totalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRevenueForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = Order.find("status = ?1 and createdAt >= ?2 and createdAt <= ?3", 
                                       Order.OrderStatus.DELIVERED, startDate, endDate).list();
        return orders.stream()
                .map(order -> order.totalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateOrderNumber() {
        String prefix = "ORD";
        String timestamp = String.valueOf(System.currentTimeMillis());
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return prefix + "-" + timestamp.substring(timestamp.length() - 8) + "-" + random;
    }

    @Transactional
    public void restoreProductStock(Order order) {
        List<OrderItem> orderItems = OrderItem.findByOrder(order);
        for (OrderItem orderItem : orderItems) {
            // Restore stock for all order items
            productService.increaseStock(orderItem.product.id, orderItem.quantity);
            // Decrease sales count
            Product product = orderItem.product;
            product.salesCount = Math.max(0, product.salesCount - orderItem.quantity);
            product.persist();
        }
    }

    // Order Status History Methods
    @Transactional
    public void createStatusHistory(Order order, Order.OrderStatus newStatus, Order.OrderStatus previousStatus, String changedBy, String notes) {
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setOrder(order);
        statusHistory.setStatus(newStatus);
        statusHistory.setPreviousStatus(previousStatus);
        statusHistory.setChangedAt(LocalDateTime.now());
        statusHistory.setChangedBy(changedBy);
        statusHistory.setNotes(notes);
        
        // Set tracking number if status is shipped
        if (newStatus == Order.OrderStatus.SHIPPED && order.trackingNumber != null) {
            statusHistory.setTrackingNumber(order.trackingNumber);
            statusHistory.setEstimatedDelivery(calculateEstimatedDelivery());
        }
        
        // Set actual delivery time if status is delivered
        if (newStatus == Order.OrderStatus.DELIVERED) {
            statusHistory.setActualDelivery(LocalDateTime.now());
        }
        
        statusHistory.persist();
    }

    public List<OrderStatusHistory> getOrderStatusHistory(Long orderId) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }
        
        return OrderStatusHistory.find("order = ?1 order by changedAt asc", order).list();
    }

    public List<OrderStatusHistory> getOrderStatusHistory(Order order) {
        return OrderStatusHistory.find("order = ?1 order by changedAt asc", order).list();
    }

    private LocalDateTime calculateEstimatedDelivery() {
        // Default estimation: 3-5 business days from now
        return LocalDateTime.now().plusDays(4);
    }

    // Order Actions Methods
    @Transactional
    public void cancelOrderWithReason(Long orderId, String reason, String cancelledBy) {
        updateOrderStatus(orderId, Order.OrderStatus.CANCELLED, cancelledBy, "Order cancelled: " + reason);
    }

    @Transactional
    public void shipOrderWithTracking(Long orderId, String trackingNumber, String shippedBy) {
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (!order.canBeShipped()) {
            throw new IllegalArgumentException("Order cannot be shipped in current state");
        }

        order.trackingNumber = trackingNumber;
        order.persist();

        updateOrderStatus(orderId, Order.OrderStatus.SHIPPED, shippedBy, "Order shipped with tracking: " + trackingNumber);
    }

    @Transactional
    public void confirmOrder(Long orderId, String confirmedBy) {
        updateOrderStatus(orderId, Order.OrderStatus.CONFIRMED, confirmedBy, "Order confirmed and ready for processing");
    }

    @Transactional
    public void deliverOrderWithConfirmation(Long orderId, String deliveredBy) {
        updateOrderStatus(orderId, Order.OrderStatus.DELIVERED, deliveredBy, "Order successfully delivered");
    }
}
