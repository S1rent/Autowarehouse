# 🏗️ System Architecture - Autowarehouse System

## Overall Architecture
Sistem Autowarehouse menggunakan arsitektur microservices dengan event-driven processing untuk mendukung scalability dan real-time operations.

## Architecture Diagram
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Vue 3 Client  │    │  Admin Panel    │    │  Mobile App     │
│   (Customer)    │    │   (Admin)       │    │   (Future)      │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
                    ┌─────────────┴─────────────┐
                    │     Load Balancer         │
                    └─────────────┬─────────────┘
                                 │
                    ┌─────────────┴─────────────┐
                    │   Quarkus Backend         │
                    │   - REST API              │
                    │   - WebSocket Server      │
                    │   - Business Logic        │
                    └─────────────┬─────────────┘
                                 │
        ┌────────────────────────┼────────────────────────┐
        │                       │                        │
┌───────┴────────┐    ┌─────────┴─────────┐    ┌─────────┴─────────┐
│  PostgreSQL    │    │  Apache Kafka     │    │  Firebase         │
│  Database      │    │  Message Broker   │    │  - Storage        │
│                │    │                   │    │  - FCM            │
└────────────────┘    └───────────────────┘    └───────────────────┘
```

## Frontend Architecture (Vue 3)

### Component Structure
```
src/
├── components/
│   ├── common/          # Reusable components
│   ├── customer/        # Customer-specific components
│   ├── admin/           # Admin-specific components
│   └── auction/         # Auction-related components
├── views/
│   ├── customer/        # Customer pages
│   ├── admin/           # Admin pages
│   └── shared/          # Shared pages (login, error)
├── stores/              # Pinia stores
├── services/            # API services
├── utils/               # Utility functions
└── locales/             # i18n files
```

### State Management (Pinia)
- **User Store**: Authentication, user profile
- **Product Store**: Product catalog, search, filters
- **Cart Store**: Shopping cart management
- **Auction Store**: Real-time auction data
- **Order Store**: Order history and tracking
- **Notification Store**: Real-time notifications

## Backend Architecture (Quarkus)

### Service Layer Structure
```
src/main/java/
├── controller/          # REST endpoints
├── websocket/           # WebSocket endpoints
├── service/             # Business logic
├── repository/          # Data access layer
├── dto/                 # Data transfer objects
├── entity/              # JPA entities
├── config/              # Configuration
└── event/               # Kafka event handlers
```

### Core Services
- **ProductService**: Product management, catalog
- **OrderService**: Order processing, checkout
- **AuctionService**: Auction management, bidding
- **UserService**: User management, authentication
- **NotificationService**: FCM integration
- **StockService**: Inventory management

## Database Schema (PostgreSQL)

### Core Tables
```sql
-- Users table
users (id, email, password, role, created_at, updated_at)

-- Products table
products (id, name, description, price, stock, category_id, image_urls, is_auction_eligible)

-- Categories table
categories (id, name, description, parent_id)

-- Orders table
orders (id, user_id, total_amount, status, shipping_address, created_at)

-- Order items table
order_items (id, order_id, product_id, quantity, price)

-- Auctions table
auctions (id, product_id, start_time, end_time, starting_price, current_price, winner_id, status)

-- Bids table
bids (id, auction_id, user_id, amount, created_at)

-- Reviews table
reviews (id, product_id, user_id, rating, comment, created_at)

-- Wishlists table
wishlists (id, user_id, product_id, created_at)
```

## Event-Driven Architecture (Kafka)

### Key Events
- **OrderPlacedEvent**: Triggered saat checkout selesai
- **StockUpdatedEvent**: Update stok oleh admin
- **AuctionEndedEvent**: Lelang selesai → kirim hasil
- **ProductUpdatedEvent**: Sinkronisasi data frontend/backend
- **BidPlacedEvent**: Real-time bid updates
- **NotificationEvent**: Push notifications

### Event Flow
```
User Action → Service → Kafka Producer → Event Topic → Kafka Consumer → Action Handler
```

## Real-time Communication

### WebSocket Endpoints
- `/ws/auction/{auctionId}` - Real-time auction updates
- `/ws/notifications/{userId}` - User-specific notifications

### WebSocket Message Types
- `BID_PLACED` - New bid notification
- `AUCTION_STARTED` - Auction start notification
- `AUCTION_ENDED` - Auction end notification
- `TIMER_UPDATE` - Countdown timer updates

## Security Architecture

### Authentication Flow
1. User login → JWT token generation
2. Token validation pada setiap request
3. Role-based access control (RBAC)

### Security Layers
- Input validation dan sanitization
- SQL injection prevention
- XSS protection
- CORS configuration
- Rate limiting untuk API endpoints

## Scalability Considerations

### Horizontal Scaling
- Stateless backend services
- Load balancer untuk distribusi traffic
- Database connection pooling
- Caching layer untuk performance

### Performance Optimization
- Database indexing
- Query optimization
- CDN untuk static assets
- Lazy loading untuk frontend components
