# 🔌 API Endpoints - Autowarehouse System

## Overview
RESTful API endpoints untuk sistem Autowarehouse dengan authentication, authorization, dan comprehensive error handling.

## Base URL
```
Development: http://localhost:8080/api
Production: https://api.autowarehouse.com/api
```

## Authentication
- **Type**: JWT Bearer Token
- **Header**: `Authorization: Bearer <token>`
- **Token Expiry**: 24 hours
- **Refresh Token**: Available for token renewal

## Response Format
```json
{
  "success": true,
  "data": {},
  "message": "Success message",
  "timestamp": "2025-01-07T10:30:00Z"
}

// Error Response
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": []
  },
  "timestamp": "2025-01-07T10:30:00Z"
}
```

## Authentication Endpoints

### POST /auth/register
Register new user account
```json
// Request
{
  "email": "user@example.com",
  "password": "SecurePass123",
  "fullName": "John Doe",
  "phone": "+6281234567890"
}

// Response
{
  "success": true,
  "data": {
    "user": {
      "id": 1,
      "email": "user@example.com",
      "fullName": "John Doe",
      "role": "CUSTOMER"
    },
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

### POST /auth/login
User login
```json
// Request
{
  "email": "user@example.com",
  "password": "SecurePass123"
}

// Response
{
  "success": true,
  "data": {
    "user": {
      "id": 1,
      "email": "user@example.com",
      "fullName": "John Doe",
      "role": "CUSTOMER"
    },
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "refresh_token_here"
  }
}
```

### POST /auth/google
Google OAuth login
```json
// Request
{
  "googleToken": "google_oauth_token"
}

// Response - Same as login
```

### POST /auth/forgot-password
Request password reset
```json
// Request
{
  "email": "user@example.com"
}

// Response
{
  "success": true,
  "message": "Password reset email sent"
}
```

### POST /auth/reset-password
Reset password with token
```json
// Request
{
  "token": "reset_token",
  "newPassword": "NewSecurePass123"
}

// Response
{
  "success": true,
  "message": "Password reset successfully"
}
```

## Product Endpoints

### GET /products
Get products with pagination and filters
```
Query Parameters:
- page: int (default: 1)
- limit: int (default: 20, max: 100)
- category: int (category ID)
- minPrice: decimal
- maxPrice: decimal
- minRating: decimal
- search: string
- sortBy: string (price, rating, created, name)
- sortOrder: string (asc, desc)
- inStock: boolean
```

```json
// Response
{
  "success": true,
  "data": {
    "products": [
      {
        "id": 1,
        "name": "RTX 4090 Gaming GPU",
        "description": "High-end gaming graphics card",
        "price": 25000000,
        "stock": 5,
        "category": {
          "id": 1,
          "name": "Graphics Cards"
        },
        "imageUrls": ["https://firebase.com/image1.jpg"],
        "rating": 4.8,
        "reviewCount": 24,
        "isAuctionEligible": true
      }
    ],
    "pagination": {
      "page": 1,
      "limit": 20,
      "total": 150,
      "totalPages": 8
    }
  }
}
```

### GET /products/{id}
Get product detail
```json
// Response
{
  "success": true,
  "data": {
    "id": 1,
    "name": "RTX 4090 Gaming GPU",
    "description": "Detailed product description...",
    "price": 25000000,
    "stock": 5,
    "category": {
      "id": 1,
      "name": "Graphics Cards"
    },
    "imageUrls": ["https://firebase.com/image1.jpg"],
    "specifications": {
      "memory": "24GB GDDR6X",
      "coreClock": "2520 MHz",
      "memoryInterface": "384-bit"
    },
    "rating": 4.8,
    "reviewCount": 24,
    "reviews": [
      {
        "id": 1,
        "rating": 5,
        "title": "Excellent GPU",
        "comment": "Great performance for gaming",
        "userName": "John D.",
        "createdAt": "2025-01-01T10:00:00Z"
      }
    ]
  }
}
```

### GET /categories
Get product categories
```json
// Response
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Graphics Cards",
      "description": "Gaming and professional GPUs",
      "imageUrl": "https://firebase.com/category1.jpg",
      "productCount": 45,
      "children": []
    }
  ]
}
```

## Shopping Cart Endpoints

### GET /cart
Get user's cart items
```json
// Response
{
  "success": true,
  "data": {
    "items": [
      {
        "id": 1,
        "product": {
          "id": 1,
          "name": "RTX 4090",
          "price": 25000000,
          "imageUrls": ["https://firebase.com/image1.jpg"]
        },
        "quantity": 2,
        "subtotal": 50000000
      }
    ],
    "summary": {
      "totalItems": 2,
      "totalAmount": 50000000,
      "estimatedShipping": 50000
    }
  }
}
```

### POST /cart/add
Add item to cart
```json
// Request
{
  "productId": 1,
  "quantity": 2
}

// Response
{
  "success": true,
  "message": "Item added to cart"
}
```

### PUT /cart/update/{itemId}
Update cart item quantity
```json
// Request
{
  "quantity": 3
}

// Response
{
  "success": true,
  "message": "Cart updated"
}
```

### DELETE /cart/remove/{itemId}
Remove item from cart
```json
// Response
{
  "success": true,
  "message": "Item removed from cart"
}
```

## Order Endpoints

### POST /orders/checkout
Create order from cart
```json
// Request
{
  "shippingInfo": {
    "name": "John Doe",
    "phone": "+6281234567890",
    "address": "Jl. Sudirman No. 123",
    "city": "Jakarta",
    "postalCode": "12345"
  },
  "shippingMethod": "REGULAR",
  "paymentMethod": "BANK_TRANSFER"
}

// Response
{
  "success": true,
  "data": {
    "order": {
      "id": 1,
      "orderNumber": "ORD-2025-001",
      "status": "PENDING",
      "totalAmount": 50050000,
      "items": [
        {
          "productName": "RTX 4090",
          "quantity": 2,
          "price": 25000000,
          "subtotal": 50000000
        }
      ]
    }
  }
}
```

### GET /orders
Get user's order history
```json
// Response
{
  "success": true,
  "data": {
    "orders": [
      {
        "id": 1,
        "orderNumber": "ORD-2025-001",
        "status": "SHIPPED",
        "totalAmount": 50050000,
        "createdAt": "2025-01-01T10:00:00Z",
        "itemCount": 2
      }
    ],
    "pagination": {
      "page": 1,
      "limit": 20,
      "total": 5,
      "totalPages": 1
    }
  }
}
```

### GET /orders/{id}
Get order detail
```json
// Response
{
  "success": true,
  "data": {
    "id": 1,
    "orderNumber": "ORD-2025-001",
    "status": "SHIPPED",
    "totalAmount": 50050000,
    "shippingCost": 50000,
    "items": [
      {
        "productName": "RTX 4090",
        "quantity": 2,
        "price": 25000000,
        "subtotal": 50000000
      }
    ],
    "shippingInfo": {
      "name": "John Doe",
      "address": "Jl. Sudirman No. 123",
      "city": "Jakarta"
    },
    "statusHistory": [
      {
        "status": "PENDING",
        "createdAt": "2025-01-01T10:00:00Z"
      },
      {
        "status": "PROCESSING",
        "createdAt": "2025-01-01T12:00:00Z"
      }
    ]
  }
}
```

## Auction Endpoints

### GET /auctions
Get active auctions
```json
// Response
{
  "success": true,
  "data": [
    {
      "id": 1,
      "title": "RTX 4090 Auction",
      "product": {
        "id": 1,
        "name": "RTX 4090",
        "imageUrls": ["https://firebase.com/image1.jpg"]
      },
      "currentPrice": 20000000,
      "startingPrice": 15000000,
      "bidIncrement": 500000,
      "endTime": "2025-01-07T20:00:00Z",
      "totalBids": 15,
      "status": "ACTIVE",
      "timeRemaining": 3600
    }
  ]
}
```

### GET /auctions/{id}
Get auction detail
```json
// Response
{
  "success": true,
  "data": {
    "id": 1,
    "title": "RTX 4090 Auction",
    "description": "High-end GPU auction",
    "product": {
      "id": 1,
      "name": "RTX 4090",
      "description": "Gaming GPU",
      "imageUrls": ["https://firebase.com/image1.jpg"]
    },
    "currentPrice": 20000000,
    "startingPrice": 15000000,
    "bidIncrement": 500000,
    "startTime": "2025-01-07T10:00:00Z",
    "endTime": "2025-01-07T20:00:00Z",
    "totalBids": 15,
    "status": "ACTIVE",
    "timeRemaining": 3600,
    "isParticipating": true,
    "userHighestBid": 19500000
  }
}
```

### POST /auctions/{id}/bid
Place a bid
```json
// Request
{
  "amount": 20500000
}

// Response
{
  "success": true,
  "data": {
    "bid": {
      "id": 1,
      "amount": 20500000,
      "isWinning": true,
      "createdAt": "2025-01-07T15:30:00Z"
    },
    "auction": {
      "currentPrice": 20500000,
      "totalBids": 16
    }
  }
}
```

### GET /auctions/{id}/bids
Get bid history
```json
// Response
{
  "success": true,
  "data": [
    {
      "id": 1,
      "amount": 20500000,
      "userName": "John D.",
      "createdAt": "2025-01-07T15:30:00Z",
      "isYourBid": true
    }
  ]
}
```

## Admin Endpoints

### GET /admin/products
Get all products (admin)
```
Query Parameters: Same as /products plus:
- status: string (active, inactive, all)
```

### POST /admin/products
Create new product
```json
// Request
{
  "name": "New GPU",
  "description": "Latest GPU model",
  "price": 30000000,
  "stock": 10,
  "categoryId": 1,
  "specifications": {
    "memory": "16GB GDDR6"
  },
  "isAuctionEligible": true
}

// Response
{
  "success": true,
  "data": {
    "id": 2,
    "name": "New GPU",
    "price": 30000000,
    "stock": 10
  }
}
```

### PUT /admin/products/{id}
Update product
```json
// Request - Same as create
// Response - Updated product data
```

### DELETE /admin/products/{id}
Delete product
```json
// Response
{
  "success": true,
  "message": "Product deleted successfully"
}
```

### GET /admin/orders
Get all orders (admin)
```
Query Parameters:
- status: string
- dateFrom: date
- dateTo: date
- page: int
- limit: int
```

### PUT /admin/orders/{id}/status
Update order status
```json
// Request
{
  "status": "SHIPPED",
  "notes": "Shipped via JNE"
}

// Response
{
  "success": true,
  "message": "Order status updated"
}
```

## Error Codes

| Code | HTTP Status | Description |
|------|-------------|-------------|
| VALIDATION_ERROR | 400 | Invalid input data |
| UNAUTHORIZED | 401 | Authentication required |
| FORBIDDEN | 403 | Insufficient permissions |
| NOT_FOUND | 404 | Resource not found |
| CONFLICT | 409 | Resource conflict |
| STOCK_INSUFFICIENT | 409 | Not enough stock |
| BID_TOO_LOW | 409 | Bid amount too low |
| AUCTION_ENDED | 409 | Auction has ended |
| RATE_LIMIT_EXCEEDED | 429 | Too many requests |
| INTERNAL_ERROR | 500 | Server error |

## Rate Limiting
- **General API**: 1000 requests per hour per user
- **Authentication**: 10 requests per minute per IP
- **Bidding**: 60 requests per minute per user
- **File Upload**: 10 requests per minute per user

## Pagination
All list endpoints support pagination:
```json
{
  "pagination": {
    "page": 1,
    "limit": 20,
    "total": 150,
    "totalPages": 8,
    "hasNext": true,
    "hasPrev": false
  }
}
```

## File Upload
Product images upload via Firebase Storage:
```json
// POST /admin/products/{id}/images
// Content-Type: multipart/form-data

// Response
{
  "success": true,
  "data": {
    "imageUrl": "https://firebase.com/products/image123.jpg"
  }
}
