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
            orderItem.quantity = cartItem.quantity;
            orderItem.price = cartItem.product.getCurrentPrice();
            orderItem.calculateTotalPrice();
            orderItem.persist();

            subtotal = subtotal.add(orderItem.totalPrice);

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

        // Check if order already exists for this auction
        List<OrderItem> existingOrderItems = OrderItem.findByAuction(auction);
        if (!existingOrderItems.isEmpty()) {
            throw new IllegalArgumentException("Order already exists for this auction");
        }

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
        orderItem.auction = auction;
        orderItem.quantity = 1;
        orderItem.price = auction.winningBid;
        orderItem.calculateTotalPrice();
        orderItem.persist();

        // Calculate totals
        order.subtotal = orderItem.totalPrice;
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
        Order order = Order.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        Order.OrderStatus oldStatus = order.status;
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
            // Only restore stock if it's not from an auction
            if (orderItem.auction == null) {
                productService.increaseStock(orderItem.product.id, orderItem.quantity);
                // Decrease sales count
                Product product = orderItem.product;
                product.salesCount = Math.max(0, product.salesCount - orderItem.quantity);
                product.persist();
            }
        }
    }
}
