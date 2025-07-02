# 📋 Order Management Feature

## Overview
Sistem manajemen pesanan yang menangani proses checkout, tracking status pesanan, dan riwayat pembelian untuk customer dan admin.

## User Stories
- Sebagai customer, saya ingin melakukan checkout dari shopping cart
- Sebagai customer, saya ingin melihat status pesanan saya
- Sebagai customer, saya ingin melihat riwayat pesanan
- Sebagai customer, saya ingin melihat detail pesanan
- Sebagai admin, saya ingin memproses dan mengupdate status pesanan
- Sebagai admin, saya ingin melihat semua pesanan yang masuk

## Technical Requirements

### Frontend Components
- **CheckoutPage**: Halaman checkout dengan form alamat dan pembayaran
- **OrderSummary**: Ringkasan pesanan sebelum checkout
- **OrderHistoryPage**: Halaman riwayat pesanan customer
- **OrderDetailPage**: Detail pesanan individual
- **OrderStatusTracker**: Stepper UI untuk tracking status
- **OrderManagementPage**: Admin panel untuk kelola pesanan
- **OrderTable**: Tabel pesanan untuk admin

### Backend Endpoints
```
// Customer endpoints
POST /api/orders/checkout - Create new order from cart
GET /api/orders - Get user's order history
GET /api/orders/{id} - Get order detail

// Admin endpoints
GET /api/admin/orders - Get all orders with filters
PUT /api/admin/orders/{id}/status - Update order status
GET /api/admin/orders/stats - Get order statistics
```

### Database Schema
```sql
-- Orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    total_amount DECIMAL(10,2) NOT NULL,
    shipping_cost DECIMAL(10,2) DEFAULT 0,
    tax_amount DECIMAL(10,2) DEFAULT 0,
    
    -- Shipping information
    shipping_name VARCHAR(255) NOT NULL,
    shipping_phone VARCHAR(20) NOT NULL,
    shipping_address TEXT NOT NULL,
    shipping_city VARCHAR(100) NOT NULL,
    shipping_postal_code VARCHAR(10) NOT NULL,
    shipping_method VARCHAR(50) NOT NULL, -- REGULAR, EXPRESS, INSTANT
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP
);

-- Order items table
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    product_name VARCHAR(255) NOT NULL, -- Snapshot of product name
    product_price DECIMAL(10,2) NOT NULL, -- Snapshot of price at time of order
    quantity INTEGER NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order status history table
CREATE TABLE order_status_history (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    status VARCHAR(20) NOT NULL,
    notes TEXT,
    created_by BIGINT REFERENCES users(id), -- Admin who updated status
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## UI Design Reference
Based on ui-design folder:
- **history (1).pdf**: Order history page layout
- **history (2).pdf**: Order detail and tracking
- **history (3).pdf**: Order status progression
- **order-management-admin (1).pdf**: Admin order management
- **order-management-admin (2).pdf**: Admin order processing

## Key Features

### 1. Checkout Process
- **Cart Validation**: Validasi stok dan harga sebelum checkout
- **Shipping Form**: Input alamat pengiriman lengkap
- **Shipping Options**: Pilihan metode pengiriman (reguler, express, instant)
- **Order Summary**: Review items, harga, ongkir sebelum confirm
- **Order Confirmation**: Konfirmasi pesanan berhasil dibuat

### 2. Order Status Tracking
- **Status Progression**: PENDING → PROCESSING → SHIPPED → DELIVERED
- **Status Timeline**: Visual stepper dengan timestamp
- **Status Notifications**: Real-time update via FCM
- **Estimated Delivery**: Perkiraan waktu sampai

### 3. Order History
- **Order List**: Daftar pesanan dengan filter dan sorting
- **Quick Info**: Order number, tanggal, status, total
- **Search**: Pencarian berdasarkan order number atau produk
- **Filter**: Filter berdasarkan status, tanggal, total amount

### 4. Order Detail
- **Order Information**: Order number, tanggal, status
- **Items List**: Produk yang dibeli dengan harga snapshot
- **Shipping Info**: Alamat dan metode pengiriman
- **Payment Info**: Total, ongkir, pajak
- **Status History**: Log perubahan status dengan timestamp

### 5. Admin Order Management
- **Order Dashboard**: Overview pesanan hari ini, pending, dll
- **Order Processing**: Update status pesanan
- **Bulk Actions**: Update multiple orders sekaligus
- **Order Analytics**: Statistik penjualan dan performa

## State Management (Pinia)

### Order Store
```typescript
interface OrderState {
  orders: Order[]
  currentOrder: Order | null
  checkoutData: CheckoutData
  loading: boolean
  pagination: PaginationInfo
}

interface Order {
  id: number
  orderNumber: string
  userId: number
  status: OrderStatus
  totalAmount: number
  shippingCost: number
  taxAmount: number
  shippingInfo: ShippingInfo
  items: OrderItem[]
  statusHistory: OrderStatusHistory[]
  createdAt: Date
  updatedAt: Date
  shippedAt?: Date
  deliveredAt?: Date
}

interface CheckoutData {
  items: CartItem[]
  shippingInfo: ShippingInfo
  shippingMethod: ShippingMethod
  totalAmount: number
  shippingCost: number
}

enum OrderStatus {
  PENDING = 'PENDING',
  PROCESSING = 'PROCESSING', 
  SHIPPED = 'SHIPPED',
  DELIVERED = 'DELIVERED',
  CANCELLED = 'CANCELLED'
}
```

## Business Rules

### Checkout Validation
- Stock availability check untuk semua items
- Minimum order amount (optional)
- Valid shipping address required
- User authentication required

### Order Status Rules
- Status hanya bisa maju, tidak bisa mundur (kecuali cancel)
- CANCELLED hanya bisa dari PENDING atau PROCESSING
- Auto-update delivered setelah X hari dari shipped (optional)
- Stock reduction saat order PROCESSING

### Inventory Management
- Reserve stock saat order PENDING
- Deduct stock saat order PROCESSING
- Return stock saat order CANCELLED

## Event-Driven Processing (Kafka)

### Order Events
```typescript
interface OrderPlacedEvent {
  orderId: number
  userId: number
  items: OrderItem[]
  totalAmount: number
  timestamp: Date
}

interface OrderStatusUpdatedEvent {
  orderId: number
  oldStatus: OrderStatus
  newStatus: OrderStatus
  updatedBy: number
  timestamp: Date
}

interface StockReservationEvent {
  productId: number
  quantity: number
  orderId: number
  action: 'RESERVE' | 'DEDUCT' | 'RETURN'
}
```

## Integration Points
- **Cart Service**: Transfer cart items ke order
- **Product Service**: Stock validation dan update
- **User Service**: Customer information
- **Notification Service**: Order status updates
- **Payment Service**: Payment processing (future)
- **Shipping Service**: Tracking integration (future)

## Performance Considerations
- **Database Indexing**: Index pada user_id, status, created_at
- **Pagination**: Limit order history queries
- **Caching**: Cache order statistics untuk admin dashboard
- **Async Processing**: Background job untuk stock updates
- **Archive Strategy**: Archive old completed orders

## Error Handling
- **Stock Insufficient**: Clear error message dan suggested alternatives
- **Payment Failure**: Rollback order creation
- **Shipping Validation**: Address validation dengan clear feedback
- **Concurrent Orders**: Handle race conditions untuk stock

## Security Considerations
- **User Authorization**: Users hanya bisa akses order mereka sendiri
- **Admin Authorization**: Role-based access untuk admin functions
- **Input Validation**: Validate semua checkout data
- **Audit Trail**: Log semua perubahan status order

## Analytics & Reporting
- Daily/monthly sales reports
- Order conversion rate dari cart
- Average order value
- Popular products dan categories
- Order fulfillment time analysis
- Customer lifetime value

## Notification Strategy
- **Order Placed**: Konfirmasi email dan push notification
- **Status Updates**: Real-time notification untuk setiap perubahan status
- **Delivery Confirmation**: Notification saat pesanan delivered
- **Review Reminder**: Reminder untuk review produk setelah delivered
