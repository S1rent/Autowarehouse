# Backend Implementation - AutoWarehouse

## Overview
Backend implementation menggunakan Quarkus framework dengan Java, PostgreSQL database, dan JWT authentication. Sistem ini mendukung e-commerce dengan fitur auction, shopping cart, order management, dan user management.

## Technology Stack
- **Framework**: Quarkus 3.x
- **Language**: Java 17+
- **Database**: PostgreSQL
- **Authentication**: JWT with RSA keys
- **ORM**: Hibernate with Panache
- **Migration**: Flyway
- **Build Tool**: Maven

## Project Structure
```
autowarehouse-backend/
├── src/main/java/com/autowarehouse/
│   ├── entity/           # JPA Entities
│   ├── resource/         # REST Resources (Controllers)
│   ├── service/          # Business Logic Services
│   └── dto/              # Data Transfer Objects
├── src/main/resources/
│   ├── application.properties
│   ├── db/migration/     # Flyway migrations
│   └── META-INF/resources/
│       ├── privateKey.pem
│       └── publicKey.pem
└── pom.xml
```

## Entities

### Core Entities

#### User Entity
- **Fields**: id, email, password, firstName, lastName, phoneNumber, address, role, isActive, isEmailVerified, lastLoginAt, createdAt, updatedAt
- **Relationships**: OneToMany with CartItem, WishlistItem, Order, Bid, AuctionWatcher, Review
- **Key Methods**: findByEmail, findActiveUsers, authentication helpers
- **Security**: Password hashing, role-based access

#### Product Entity
- **Fields**: id, name, description, shortDescription, price, originalPrice, stockQuantity, minStockLevel, sku, brand, model, specifications, features, imageUrls, tags, isActive, isFeatured, isOnSale, saleStartDate, saleEndDate, weightKg, dimensions, warrantyMonths, rating, reviewCount, salesCount, viewCount, category, createdAt, updatedAt
- **Relationships**: ManyToOne with Category, OneToMany with CartItem, WishlistItem, Review, OrderItem, Auction
- **Key Methods**: findBySku, findByCategory, findActiveProducts, findFeaturedProducts, findOnSaleProducts, searchProducts, stock management
- **Features**: Sale pricing, stock tracking, rating calculation, view counting

#### Category Entity
- **Fields**: id, name, description, slug, parent, imageUrl, isActive, sortOrder, createdAt, updatedAt
- **Relationships**: ManyToOne with parent Category, OneToMany with children Categories and Products
- **Key Methods**: findBySlug, findRootCategories, findByParent, hierarchical navigation
- **Features**: Hierarchical structure, URL-friendly slugs

#### Order Entity
- **Fields**: id, user, orderNumber, status, paymentStatus, subtotal, taxAmount, shippingCost, discountAmount, totalAmount, shippingAddress, billingAddress, paymentMethod, paymentReference, notes, shippedAt, deliveredAt, createdAt, updatedAt
- **Relationships**: ManyToOne with User, OneToMany with OrderItem
- **Enums**: OrderStatus (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED), PaymentStatus (PENDING, PAID, FAILED, REFUNDED)
- **Key Methods**: findByOrderNumber, findByUser, findByStatus, order lifecycle management
- **Features**: Complete order lifecycle, payment tracking, shipping management

#### OrderItem Entity
- **Fields**: id, order, product, productName, productSku, productPrice, quantity, subtotal, createdAt
- **Relationships**: ManyToOne with Order and Product
- **Key Methods**: findByOrder, findByProduct, quantity management
- **Features**: Product snapshot at time of order

### Shopping & Wishlist

#### CartItem Entity
- **Fields**: id, user, product, quantity, isSelected, createdAt, updatedAt
- **Relationships**: ManyToOne with User and Product
- **Constraints**: Unique constraint on user_id + product_id
- **Key Methods**: findByUser, findSelectedByUser, cart management operations
- **Features**: Item selection, quantity management, stock validation

#### WishlistItem Entity
- **Fields**: id, user, product, createdAt
- **Relationships**: ManyToOne with User and Product
- **Constraints**: Unique constraint on user_id + product_id
- **Key Methods**: findByUser, wishlist management operations
- **Features**: Simple wishlist functionality

### Auction System

#### Auction Entity
- **Fields**: id, product, title, description, startingPrice, currentPrice, buyNowPrice, minimumBidIncrement, status, startTime, endTime, winner, totalBids, watchersCount, createdAt, updatedAt
- **Relationships**: ManyToOne with Product and User (winner), OneToMany with Bid and AuctionWatcher
- **Enums**: AuctionStatus (DRAFT, SCHEDULED, LIVE, ENDED, CANCELLED)
- **Key Methods**: findActiveAuctions, findLiveAuctions, findUpcomingAuctions, auction lifecycle management
- **Features**: Real-time bidding, auto-bidding support, auction scheduling

#### Bid Entity
- **Fields**: id, auction, user, amount, isAutoBid, maxAutoBid, isWinning, createdAt
- **Relationships**: ManyToOne with Auction and User
- **Key Methods**: findByAuction, findByUser, findHighestBidForAuction, bid management
- **Features**: Manual and automatic bidding, winning bid tracking

#### AuctionWatcher Entity
- **Fields**: id, auction, user, createdAt
- **Relationships**: ManyToOne with Auction and User
- **Constraints**: Unique constraint on auction_id + user_id
- **Key Methods**: findByAuction, findByUser, watcher management
- **Features**: Auction watching/following functionality

### Review System

#### Review Entity
- **Fields**: id, product, user, rating, title, comment, isVerifiedPurchase, helpfulCount, isApproved, createdAt, updatedAt
- **Relationships**: ManyToOne with Product and User
- **Constraints**: Unique constraint on product_id + user_id
- **Key Methods**: findByProduct, findByUser, rating calculations, review management
- **Features**: 5-star rating, verified purchase marking, review approval system

## REST Resources (Controllers)

### UserResource
- **Endpoints**:
  - POST /api/users/register - User registration
  - POST /api/users/login - User authentication
  - GET /api/users/profile - Get user profile
  - PUT /api/users/profile - Update user profile
  - PUT /api/users/password - Change password
  - GET /api/users/admin/all - Admin: Get all users
  - PUT /api/users/admin/{id}/status - Admin: Update user status
- **Security**: JWT authentication, role-based access control
- **Features**: Registration, login, profile management, admin user management

### ProductResource
- **Endpoints**:
  - GET /api/products - Get products with filtering/pagination
  - GET /api/products/{id} - Get product details
  - GET /api/products/search - Search products
  - GET /api/products/category/{categoryId} - Get products by category
  - GET /api/products/featured - Get featured products
  - POST /api/products/admin/create - Admin: Create product
  - PUT /api/products/admin/{id} - Admin: Update product
  - DELETE /api/products/admin/{id} - Admin: Delete product
  - PUT /api/products/admin/{id}/stock - Admin: Update stock
- **Features**: Product catalog, search, filtering, admin management

### CartResource
- **Endpoints**:
  - GET /api/cart - Get user's cart
  - POST /api/cart/add - Add item to cart
  - PUT /api/cart/{id} - Update cart item
  - DELETE /api/cart/{id} - Remove cart item
  - DELETE /api/cart/clear - Clear cart
  - PUT /api/cart/select-all - Select/deselect all items
- **Features**: Shopping cart management, item selection, quantity updates

### OrderResource
- **Endpoints**:
  - POST /api/orders/create - Create order from cart
  - GET /api/orders/{id} - Get order details
  - GET /api/orders/user/{userId} - Get user orders
  - PUT /api/orders/{id}/payment - Update payment status
  - PUT /api/orders/{id}/cancel - Cancel order
  - GET /api/orders/admin/all - Admin: Get all orders
  - PUT /api/orders/admin/{id}/status - Admin: Update order status
  - PUT /api/orders/admin/{id}/ship - Admin: Ship order
  - PUT /api/orders/admin/{id}/deliver - Admin: Deliver order
  - GET /api/orders/admin/stats - Admin: Order statistics
- **Features**: Order creation, payment processing, order lifecycle management

### AuctionResource
- **Endpoints**:
  - GET /api/auctions - Get auctions with filtering
  - GET /api/auctions/{id} - Get auction details
  - GET /api/auctions/live - Get live auctions
  - GET /api/auctions/upcoming - Get upcoming auctions
  - POST /api/auctions/{id}/bid - Place bid
  - POST /api/auctions/{id}/watch - Watch/unwatch auction
  - GET /api/auctions/user/bids - Get user's bids
  - GET /api/auctions/user/watched - Get watched auctions
  - POST /api/auctions/admin/create - Admin: Create auction
  - PUT /api/auctions/admin/{id} - Admin: Update auction
  - PUT /api/auctions/admin/{id}/start - Admin: Start auction
  - PUT /api/auctions/admin/{id}/end - Admin: End auction
- **Features**: Auction management, bidding system, auction watching

## Services

### JwtService
- **Functions**: JWT token generation, validation, user extraction
- **Security**: RSA key-based signing, token expiration handling
- **Features**: Secure authentication token management

### OrderService
- **Functions**: Order creation from cart, order lifecycle management, payment processing
- **Features**: Cart to order conversion, inventory management, order status updates

### AuctionService
- **Functions**: Auction lifecycle management, bidding logic, auto-bidding
- **Features**: Real-time auction processing, winner determination

## Database Configuration

### Connection Settings
```properties
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=${DB_USERNAME:autowarehouse}
quarkus.datasource.password=${DB_PASSWORD:password}
quarkus.datasource.jdbc.url=${DB_URL:jdbc:postgresql://localhost:5432/autowarehouse}
```

### Migration Management
- **Tool**: Flyway
- **Location**: src/main/resources/db/migration/
- **Naming**: V1__Create_initial_tables.sql
- **Features**: Version-controlled database schema evolution

## Security Implementation

### JWT Authentication
- **Algorithm**: RS256 (RSA with SHA-256)
- **Keys**: RSA public/private key pair
- **Token Expiration**: Configurable (default: 24 hours)
- **Claims**: User ID, email, role, expiration

### Role-Based Access Control
- **Roles**: ADMIN, CUSTOMER
- **Implementation**: @RolesAllowed annotations on endpoints
- **Features**: Method-level security, role hierarchy

### Password Security
- **Hashing**: BCrypt with salt
- **Validation**: Strong password requirements
- **Features**: Secure password storage and verification

## API Features

### Pagination & Filtering
- **Implementation**: Query parameters for page, size, sort
- **Filtering**: Product search, category filtering, status filtering
- **Sorting**: Multiple sort criteria support

### Error Handling
- **Structure**: Consistent error response format
- **Validation**: Bean validation with custom messages
- **Logging**: Comprehensive error logging

### Response Format
- **Success**: Structured response with data
- **Error**: Error code, message, and details
- **Pagination**: Total count, page info in responses

## Performance Optimizations

### Database
- **Lazy Loading**: FetchType.LAZY for relationships
- **Indexing**: Strategic database indexes
- **Connection Pooling**: HikariCP connection pool

### Caching
- **Query Caching**: Hibernate second-level cache
- **Application Caching**: Strategic caching of frequently accessed data

### API Optimization
- **Projection**: DTO pattern for API responses
- **Batch Operations**: Bulk operations where applicable
- **Pagination**: Limit result sets for large collections

## Deployment Configuration

### Environment Variables
- **Database**: DB_URL, DB_USERNAME, DB_PASSWORD
- **JWT**: JWT_ISSUER, JWT_EXPIRATION
- **Application**: QUARKUS_PROFILE, LOG_LEVEL

### Production Settings
- **Logging**: Structured logging with appropriate levels
- **Monitoring**: Health checks and metrics endpoints
- **Security**: HTTPS enforcement, CORS configuration

## Testing Strategy

### Unit Tests
- **Entities**: Entity validation and business logic
- **Services**: Service layer business logic
- **Resources**: REST endpoint testing

### Integration Tests
- **Database**: Repository layer testing
- **API**: End-to-end API testing
- **Security**: Authentication and authorization testing

## Development Workflow

### Code Organization
- **Packages**: Clear separation of concerns
- **Naming**: Consistent naming conventions
- **Documentation**: Comprehensive code documentation

### Database Evolution
- **Migrations**: Version-controlled schema changes
- **Rollback**: Safe rollback procedures
- **Testing**: Migration testing in development

### API Documentation
- **OpenAPI**: Automatic API documentation generation
- **Examples**: Request/response examples
- **Validation**: API contract validation

## Monitoring & Maintenance

### Health Checks
- **Database**: Connection health monitoring
- **Application**: Service health endpoints
- **Dependencies**: External service monitoring

### Logging
- **Levels**: Appropriate log levels for different environments
- **Format**: Structured logging for analysis
- **Retention**: Log retention policies

### Metrics
- **Performance**: Response time monitoring
- **Usage**: API usage statistics
- **Errors**: Error rate monitoring

## Future Enhancements

### Scalability
- **Microservices**: Service decomposition strategy
- **Caching**: Redis integration for distributed caching
- **Load Balancing**: Horizontal scaling preparation

### Features
- **Real-time**: WebSocket integration for live updates
- **Search**: Elasticsearch integration for advanced search
- **Analytics**: Business intelligence and reporting

### Security
- **OAuth2**: Third-party authentication integration
- **Rate Limiting**: API rate limiting implementation
- **Audit**: Comprehensive audit logging
