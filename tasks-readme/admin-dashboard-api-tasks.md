# Admin Dashboard API Requirements

## Overview
Based on the admin dashboard frontend implementation, we need to create several backend APIs to provide real-time data from the database to replace the current hardcoded values.

## Required APIs

### 1. Dashboard Statistics API
**Endpoint:** `GET /api/admin/dashboard/stats`

**Purpose:** Provide key metrics for the top 4 cards

**Response Structure:**
```json
{
  "totalSalesThisMonth": {
    "amount": 45200000,
    "percentage": 12.5,
    "trend": "up"
  },
  "totalOrdersThisMonth": {
    "count": 1247,
    "percentage": 8.2,
    "trend": "up"
  },
  "productsSold": {
    "count": 3456,
    "percentage": 15.1,
    "trend": "up"
  },
  "lowStockAlert": {
    "count": 12,
    "status": "warning"
  }
}
```

**Database Queries Needed:**
- Calculate total sales amount for current month from `orders` table
- Count total orders for current month from `orders` table
- Count total products sold (sum of quantities) from `order_items` table
- Count products with stock below threshold from `products` table
- Calculate percentage changes compared to previous month

### 2. Pending Orders API
**Endpoint:** `GET /api/admin/dashboard/pending-orders`

**Purpose:** Get count of unprocessed orders for alert card

**Response Structure:**
```json
{
  "pendingOrdersCount": 23,
  "orders": [
    {
      "id": "ORD-2024-001",
      "customerName": "John Doe",
      "totalAmount": 15500000,
      "status": "PENDING",
      "createdAt": "2024-01-15T10:30:00Z"
    }
  ]
}
```

**Database Queries Needed:**
- Count orders with status = 'PENDING' or 'AWAITING_PAYMENT'
- Get recent pending orders with customer details

### 3. Top Products API
**Endpoint:** `GET /api/admin/dashboard/top-products?limit=5`

**Purpose:** Get top selling products for current month

**Response Structure:**
```json
{
  "topProducts": [
    {
      "rank": 1,
      "productId": 101,
      "name": "Wireless Headphones Pro",
      "category": "Electronics",
      "soldCount": 234,
      "totalRevenue": 5800000,
      "imageUrl": "https://example.com/image.jpg"
    }
  ]
}
```

**Database Queries Needed:**
- Join `order_items`, `products`, and `categories` tables
- Group by product and sum quantities sold in current month
- Calculate total revenue per product
- Order by sold count DESC, limit to 5

### 4. Recent Sales API
**Endpoint:** `GET /api/admin/dashboard/recent-sales?limit=5`

**Purpose:** Get last 5 products that were sold

**Response Structure:**
```json
{
  "recentSales": [
    {
      "productName": "MacBook Pro M3 Max",
      "price": 45500000,
      "soldAt": "2024-01-15T14:28:00Z",
      "timeAgo": "2 menit yang lalu",
      "orderId": "ORD-2024-1247"
    }
  ]
}
```

**Database Queries Needed:**
- Join `order_items`, `products`, and `orders` tables
- Filter by completed orders
- Order by order completion time DESC
- Limit to 5 most recent

### 5. Recent Transactions API
**Endpoint:** `GET /api/admin/dashboard/recent-transactions?limit=5`

**Purpose:** Get last 5 transactions/orders

**Response Structure:**
```json
{
  "recentTransactions": [
    {
      "transactionId": "TRX-2024-1247",
      "orderId": "ORD-2024-1247",
      "customerName": "John Doe",
      "itemCount": 3,
      "totalAmount": 15200000,
      "status": "COMPLETED",
      "statusLabel": "Selesai",
      "createdAt": "2024-01-15T14:25:00Z"
    }
  ]
}
```

**Database Queries Needed:**
- Join `orders` and `users` tables
- Count items per order from `order_items`
- Order by created_at DESC
- Map status to Indonesian labels

### 6. Customer Service Stats API
**Endpoint:** `GET /api/admin/dashboard/customer-service-stats`

**Purpose:** Get unread messages count for customer service alert

**Response Structure:**
```json
{
  "unreadMessagesCount": 5,
  "activeChatsCount": 12,
  "averageResponseTime": "2.5 minutes"
}
```

**Database Queries Needed:**
- Count unread messages from `customer_service_messages` table
- Count active chat sessions
- Calculate average response time

## Implementation Priority

### Phase 1 (High Priority)
1. Dashboard Statistics API - Core metrics
2. Pending Orders API - Critical for operations
3. Top Products API - Business insights

### Phase 2 (Medium Priority)
4. Recent Sales API - Real-time activity
5. Recent Transactions API - Transaction monitoring

### Phase 3 (Low Priority)
6. Customer Service Stats API - Support metrics

## Database Schema Requirements

### New Tables (if not exist):
- `customer_service_messages` - For chat functionality
- `customer_service_sessions` - For active chat tracking

### Existing Tables to Use:
- `orders` - Order data and status
- `order_items` - Product quantities and prices
- `products` - Product information and stock
- `categories` - Product categories
- `users` - Customer information

## Additional Considerations

### Caching Strategy:
- Dashboard stats should be cached for 5-10 minutes
- Recent data (sales/transactions) should be cached for 1-2 minutes
- Use Redis for caching if available

### Performance Optimization:
- Create database indexes on frequently queried columns:
  - `orders.created_at`
  - `orders.status`
  - `order_items.created_at`
  - `products.stock_quantity`

### Error Handling:
- Return appropriate HTTP status codes
- Provide meaningful error messages
- Handle database connection failures gracefully

### Security:
- Ensure all endpoints require admin authentication
- Validate admin role permissions
- Sanitize all input parameters

## Testing Requirements
- Unit tests for all API endpoints
- Integration tests with database
- Performance tests for dashboard load times
- Mock data for development environment
