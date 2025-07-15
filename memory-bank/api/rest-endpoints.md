# REST API Endpoints - Autowarehouse

## 📋 API Overview

Dokumentasi lengkap REST API endpoints untuk Autowarehouse backend. Semua endpoints menggunakan JSON format untuk request dan response.

## 🔐 Authentication

Base URL: `/api`
Authentication: JWT Bearer Token (kecuali untuk public endpoints)

### Headers
```
Content-Type: application/json
Authorization: Bearer <jwt-token>
```

## 👤 User Management API

### Authentication Endpoints

#### POST /api/users/register
Registrasi user baru
```json
Request:
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe"
}

Response (201):
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CUSTOMER",
  "isActive": true,
  "isEmailVerified": false
}
```

#### POST /api/users/login
Login user
```json
Request:
{
  "email": "user@example.com",
  "password": "password123"
}

Response (200):
{
  "user": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"
  },
  "token": "jwt-token-here"
}
```

#### POST /api/users/forgot-password
Reset password request
```json
Request:
{
  "email": "user@example.com"
}

Response (200):
{
  "message": "Password reset email sent"
}
```

### Profile Management

#### GET /api/users/profile/{id}
Get user profile (Auth required)
```json
Response (200):
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "CUSTOMER",
  "isActive": true,
  "isEmailVerified": true
}
```

#### PUT /api/users/profile/{id}
Update user profile (Auth required)
```json
Request:
{
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "+1234567890"
}

Response (200):
{
  "id": 1,
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Smith",
  "phoneNumber": "+1234567890"
}
```

### Admin Endpoints

#### GET /api/users/admin/customers
Get all customers (Admin only)
```json
Response (200):
[
  {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER",
    "isActive": true
  }
]
```

#### GET /api/users/admin/stats
Get user statistics (Admin only)
```json
Response (200):
{
  "totalUsers": 150,
  "activeUsers": 140,
  "totalCustomers": 145,
  "totalAdmins": 5
}
```

## 🛍️ Product Management API

### Public Product Endpoints

#### GET /api/products
Get all products with filtering
```
Query Parameters:
- category: Long (category ID)
- brand: String
- minPrice: BigDecimal
- maxPrice: BigDecimal
- search: String
- onSale: Boolean
- page: Integer (default: 0)
- size: Integer (default: 20)

Response (200):
[
  {
    "id": 1,
    "name": "Product Name",
    "description": "Product description",
    "price": 99.99,
    "originalPrice": 129.99,
    "brand": "Brand Name",
    "model": "Model X",
    "imageUrls": ["url1", "url2"],
    "stockQuantity": 50,
    "isActive": true,
    "isOnSale": true,
    "rating": 4.5,
    "reviewCount": 25
  }
]
```

#### GET /api/products/{id}
Get product details
```json
Response (200):
{
  "id": 1,
  "name": "Product Name",
  "description": "Product description",
  "price": 99.99,
  "originalPrice": 129.99,
  "brand": "Brand Name",
  "model": "Model X",
  "specifications": "Detailed specs",
  "features": "Key features",
  "imageUrls": ["url1", "url2"],
  "stockQuantity": 50,
  "isActive": true,
  "isOnSale": true,
  "rating": 4.5,
  "reviewCount": 25,
  "sku": "SKU123",
  "categoryName": "Electronics",
  "createdAt": "2024-01-01T10:00:00"
}
```

#### GET /api/products/featured/popular
Get popular products
```
Query Parameters:
- limit: Integer (default: 10)

Response (200): Array of ProductResponse
```

### Admin Product Endpoints

#### POST /api/products/admin
Create new product (Admin only)
```json
Request:
{
  "name": "New Product",
  "description": "Product description",
  "price": 99.99,
  "originalPrice": 129.99,
  "stockQuantity": 100,
  "brand": "Brand Name",
  "model": "Model Y",
  "specifications": "Detailed specs",
  "features": "Key features",
  "imageUrls": ["url1", "url2"],
  "categoryId": 1
}

Response (201): ProductDetailResponse
```

#### PUT /api/products/admin/{id}
Update product (Admin only)
```json
Request: Same as create + isActive field
Response (200): ProductDetailResponse
```

#### PUT /api/products/admin/{id}/stock
Update product stock (Admin only)
```json
Request:
{
  "stockQuantity": 75
}

Response (200):
{
  "message": "Stock updated successfully"
}
```

## 🏷️ Auction Management API

### Public Auction Endpoints

#### GET /api/auctions
Get all auctions
```
Query Parameters:
- status: String (DRAFT, SCHEDULED, LIVE, ENDED, CANCELLED)

Response (200):
[
  {
    "id": 1,
    "title": "Auction Title",
    "description": "Auction description",
    "startingBid": 50.00,
    "currentBid": 75.00,
    "reservePrice": 100.00,
    "bidIncrement": 5.00,
    "startTime": "2024-01-01T10:00:00",
    "endTime": "2024-01-01T18:00:00",
    "status": "LIVE",
    "bidCount": 15,
    "watcherCount": 25,
    "productName": "Product Name",
    "categoryName": "Electronics"
  }
]
```

#### GET /api/auctions/live
Get live auctions
```json
Response (200): Array of AuctionResponse
```

#### GET /api/auctions/{id}
Get auction details
```json
Response (200):
{
  "id": 1,
  "title": "Auction Title",
  "description": "Auction description",
  "startingBid": 50.00,
  "currentBid": 75.00,
  "reservePrice": 100.00,
  "bidIncrement": 5.00,
  "startTime": "2024-01-01T10:00:00",
  "endTime": "2024-01-01T18:00:00",
  "status": "LIVE",
  "bidCount": 15,
  "watcherCount": 25,
  "autoExtendMinutes": 5,
  "winnerName": null,
  "winningBid": null,
  "createdAt": "2024-01-01T08:00:00"
}
```

### Auction Participation

#### POST /api/auctions/{id}/bids
Place a bid (Auth required)
```json
Request:
{
  "userId": 1,
  "bidAmount": 80.00,
  "isAutoBid": false,
  "maxAutoBid": null
}

Response (201):
{
  "id": 1,
  "bidAmount": 80.00,
  "isAutoBid": false,
  "isWinning": true,
  "bidderName": "John Doe",
  "createdAt": "2024-01-01T12:00:00"
}
```

#### POST /api/auctions/{id}/watch
Watch auction (Auth required)
```json
Request:
{
  "userId": 1
}

Response (201):
{
  "id": 1,
  "userName": "John Doe",
  "createdAt": "2024-01-01T12:00:00"
}
```

### Admin Auction Endpoints

#### POST /api/auctions/admin
Create auction (Admin only)
```json
Request:
{
  "title": "New Auction",
  "description": "Auction description",
  "startingBid": 50.00,
  "reservePrice": 100.00,
  "bidIncrement": 5.00,
  "startTime": "2024-01-01T10:00:00",
  "endTime": "2024-01-01T18:00:00",
  "autoExtendMinutes": 5,
  "productId": 1,
  "categoryId": 1
}

Response (201): AuctionDetailResponse
```

## 📦 Order Management API

### Order Operations

#### POST /api/orders/create
Create order from cart (Auth required)
```json
Request:
{
  "userId": 1
}

Response (201):
{
  "id": 1,
  "orderNumber": "ORD-2024-001",
  "status": "PENDING",
  "paymentStatus": "PENDING",
  "subtotal": 199.98,
  "taxAmount": 20.00,
  "shippingCost": 10.00,
  "totalAmount": 229.98,
  "customerName": "John Doe",
  "shippingAddress": "123 Main St",
  "createdAt": "2024-01-01T12:00:00"
}
```

#### GET /api/orders/{id}
Get order details (Auth required)
```json
Response (200): OrderDetailResponse
```

#### GET /api/orders/user/{userId}
Get user orders (Auth required)
```json
Response (200): Array of OrderResponse
```

### Admin Order Endpoints

#### GET /api/orders/admin/all
Get all orders (Admin only)
```
Query Parameters:
- status: String (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)

Response (200): Array of OrderResponse
```

#### PUT /api/orders/admin/{id}/status
Update order status (Admin only)
```json
Request:
{
  "status": "SHIPPED"
}

Response (200):
{
  "message": "Order status updated successfully"
}
```

## 🛒 Shopping Cart API

### Cart Operations

#### GET /api/cart/user/{userId}
Get cart items (Auth required)
```json
Response (200):
[
  {
    "id": 1,
    "productId": 1,
    "productName": "Product Name",
    "productSku": "SKU123",
    "productBrand": "Brand Name",
    "productPrice": 99.99,
    "originalPrice": 129.99,
    "productImages": ["url1", "url2"],
    "quantity": 2,
    "isSelected": true,
    "subtotal": 199.98,
    "savings": 60.00,
    "availableStock": 50,
    "isProductActive": true
  }
]
```

#### POST /api/cart/add
Add item to cart (Auth required)
```json
Request:
{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}

Response (201): CartItemResponse
```

#### PUT /api/cart/{cartItemId}/quantity
Update cart item quantity (Auth required)
```json
Request:
{
  "quantity": 3
}

Response (200):
{
  "message": "Cart item quantity updated successfully"
}
```

#### DELETE /api/cart/{cartItemId}
Remove item from cart (Auth required)
```json
Response (200):
{
  "message": "Item removed from cart successfully"
}
```

#### GET /api/cart/user/{userId}/summary
Get cart summary (Auth required)
```json
Response (200):
{
  "totalItems": 5,
  "selectedItems": 3,
  "totalQuantity": 8,
  "selectedQuantity": 5,
  "totalAmount": 399.95,
  "selectedAmount": 249.97,
  "totalSavings": 120.00,
  "selectedSavings": 75.00
}
```

## 📊 Statistics & Analytics

### Admin Statistics

#### GET /api/users/admin/stats
User statistics (Admin only)

#### GET /api/products/admin/stats
Product statistics (Admin only)
```json
Response (200):
{
  "totalProducts": 500,
  "activeProducts": 480,
  "lowStockProducts": 25,
  "outOfStockProducts": 5
}
```

#### GET /api/auctions/admin/stats
Auction statistics (Admin only)
```json
Response (200):
{
  "totalAuctions": 150,
  "liveAuctions": 5,
  "upcomingAuctions": 10
}
```

#### GET /api/orders/admin/stats
Order statistics (Admin only)
```json
Response (200):
{
  "totalOrders": 1250,
  "pendingOrders": 45,
  "completedOrders": 1150,
  "totalRevenue": 125000.00
}
```

## 🔍 Search & Filtering

### Product Search
- Text search across name, description, brand
- Category filtering
- Price range filtering
- Brand filtering
- Sale status filtering
- Pagination support

### Auction Filtering
- Status filtering (LIVE, UPCOMING, ENDED)
- Category filtering
- Ending soon filtering
- Popular auctions

### Order Filtering
- Status filtering
- Date range filtering
- User filtering (Admin)
- Payment status filtering

## 📝 Error Responses

### Standard Error Format
```json
{
  "message": "Error description"
}
```

### HTTP Status Codes
- `200` - Success
- `201` - Created
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `500` - Internal Server Error

### Common Error Messages
- "User not found"
- "Product not found"
- "Auction not found"
- "Order not found"
- "Invalid credentials"
- "Insufficient stock"
- "Auction has ended"
- "Bid amount too low"

## 🔐 Security Features

### Authentication
- JWT token-based authentication
- Role-based access control (ADMIN, CUSTOMER)
- Token expiration handling
- Password hashing with BCrypt

### Authorization
- Method-level security with `@RolesAllowed`
- Resource ownership validation
- Admin-only endpoints protection
- User data isolation

### Input Validation
- Bean Validation annotations
- Request payload validation
- Parameter validation
- SQL injection prevention
- XSS protection

## 📈 Performance Features

### Pagination
- Page-based pagination for large datasets
- Configurable page sizes
- Total count information

### Caching
- Entity-level caching
- Query result caching
- Static resource caching

### Optimization
- Lazy loading for relationships
- Indexed database queries
- Batch operations
- Connection pooling

---

**API Status**: ✅ **FULLY IMPLEMENTED**  
**Documentation**: Complete with examples  
**Security**: Role-based access control  
**Validation**: Comprehensive input validation  
**Error Handling**: Standardized error responses
