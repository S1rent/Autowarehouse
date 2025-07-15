# 🗄️ Database Schema - Autowarehouse System

## Overview
PostgreSQL database schema untuk sistem Autowarehouse dengan relasi yang optimal untuk mendukung e-commerce dan auction functionality.

## Database Design Principles
- **Normalization**: 3NF untuk menghindari data redundancy
- **Indexing**: Strategic indexing untuk performance
- **Constraints**: Foreign keys dan check constraints untuk data integrity
- **Scalability**: Design yang mendukung horizontal scaling
- **Audit Trail**: Timestamps dan history tracking

## Core Tables

### Users & Authentication
```sql
-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255), -- NULL for OAuth users
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER', -- CUSTOMER, ADMIN
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE, BLOCKED
    
    -- Profile information
    date_of_birth DATE,
    gender VARCHAR(10),
    profile_image_url VARCHAR(500),
    
    -- OAuth information
    google_id VARCHAR(100),
    oauth_provider VARCHAR(20), -- GOOGLE, FACEBOOK
    
    -- Address information
    address TEXT,
    city VARCHAR(100),
    postal_code VARCHAR(10),
    
    -- Timestamps
    email_verified_at TIMESTAMP,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Password reset tokens
CREATE TABLE password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User sessions (optional)
CREATE TABLE user_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    session_token VARCHAR(255) NOT NULL,
    device_info TEXT,
    ip_address INET,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Product Catalog
```sql
-- Categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES categories(id),
    image_url VARCHAR(500),
    sort_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    image_urls TEXT[], -- Array of Firebase Storage URLs
    specifications JSONB, -- Product specs as JSON
    brand VARCHAR(100),
    model VARCHAR(100),
    sku VARCHAR(50) UNIQUE,
    
    -- Status flags
    is_active BOOLEAN DEFAULT TRUE,
    is_auction_eligible BOOLEAN DEFAULT FALSE,
    is_featured BOOLEAN DEFAULT FALSE,
    
    -- Metrics
    rating DECIMAL(2,1) DEFAULT 0,
    review_count INTEGER DEFAULT 0,
    view_count INTEGER DEFAULT 0,
    sales_count INTEGER DEFAULT 0,
    
    -- SEO
    meta_title VARCHAR(255),
    meta_description TEXT,
    slug VARCHAR(255) UNIQUE,
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Shopping & Orders
```sql
-- Shopping cart
CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);

-- Wishlist
CREATE TABLE wishlists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);

-- Orders
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    total_amount DECIMAL(10,2) NOT NULL,
    shipping_cost DECIMAL(10,2) DEFAULT 0,
    tax_amount DECIMAL(10,2) DEFAULT 0,
    discount_amount DECIMAL(10,2) DEFAULT 0,
    
    -- Shipping information
    shipping_name VARCHAR(255) NOT NULL,
    shipping_phone VARCHAR(20) NOT NULL,
    shipping_address TEXT NOT NULL,
    shipping_city VARCHAR(100) NOT NULL,
    shipping_postal_code VARCHAR(10) NOT NULL,
    shipping_method VARCHAR(50) NOT NULL, -- REGULAR, EXPRESS, INSTANT
    
    -- Payment information
    payment_method VARCHAR(50), -- BANK_TRANSFER, CREDIT_CARD, E_WALLET
    payment_status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, PAID, FAILED, REFUNDED
    
    -- Timestamps
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP
);

-- Order items
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    product_name VARCHAR(255) NOT NULL, -- Snapshot
    product_price DECIMAL(10,2) NOT NULL, -- Snapshot
    quantity INTEGER NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order status history
CREATE TABLE order_status_history (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id),
    status VARCHAR(20) NOT NULL,
    notes TEXT,
    created_by BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Auction System
```sql
-- Auctions
CREATE TABLE auctions (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    title VARCHAR(255) NOT NULL,
    description TEXT,
    starting_price DECIMAL(10,2) NOT NULL,
    current_price DECIMAL(10,2) NOT NULL,
    bid_increment DECIMAL(10,2) NOT NULL DEFAULT 10000,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    winner_id BIGINT REFERENCES users(id),
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED', -- SCHEDULED, ACTIVE, ENDED, CANCELLED
    total_bids INTEGER DEFAULT 0,
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bids
CREATE TABLE bids (
    id BIGSERIAL PRIMARY KEY,
    auction_id BIGINT NOT NULL REFERENCES auctions(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    amount DECIMAL(10,2) NOT NULL,
    is_winning BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Reviews & Ratings
```sql
-- Reviews
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_id BIGINT REFERENCES orders(id), -- Link to purchase
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(255),
    comment TEXT,
    is_verified_purchase BOOLEAN DEFAULT FALSE,
    is_approved BOOLEAN DEFAULT TRUE,
    helpful_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(product_id, user_id, order_id)
);

-- Review helpfulness votes
CREATE TABLE review_votes (
    id BIGSERIAL PRIMARY KEY,
    review_id BIGINT NOT NULL REFERENCES reviews(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    is_helpful BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(review_id, user_id)
);
```

### Notifications
```sql
-- Notifications
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    type VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    data JSONB,
    is_read BOOLEAN DEFAULT FALSE,
    is_sent BOOLEAN DEFAULT FALSE,
    fcm_message_id VARCHAR(255),
    delivery_status VARCHAR(20) DEFAULT 'PENDING',
    scheduled_at TIMESTAMP,
    sent_at TIMESTAMP,
    read_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Notification preferences
CREATE TABLE notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) UNIQUE,
    auction_started BOOLEAN DEFAULT TRUE,
    auction_ended BOOLEAN DEFAULT TRUE,
    bid_placed BOOLEAN DEFAULT TRUE,
    order_status_updated BOOLEAN DEFAULT TRUE,
    order_shipped BOOLEAN DEFAULT TRUE,
    order_delivered BOOLEAN DEFAULT TRUE,
    promotional BOOLEAN DEFAULT FALSE,
    push_enabled BOOLEAN DEFAULT TRUE,
    email_enabled BOOLEAN DEFAULT TRUE,
    quiet_hours_enabled BOOLEAN DEFAULT FALSE,
    quiet_hours_start TIME,
    quiet_hours_end TIME,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- FCM tokens
CREATE TABLE fcm_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    token VARCHAR(500) NOT NULL,
    device_type VARCHAR(20) NOT NULL,
    device_info JSONB,
    is_active BOOLEAN DEFAULT TRUE,
    last_used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, token)
);
```

## Indexes for Performance

```sql
-- User indexes
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_status ON users(status);

-- Product indexes
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_active ON products(is_active);
CREATE INDEX idx_products_price ON products(price);
CREATE INDEX idx_products_rating ON products(rating);
CREATE INDEX idx_products_created ON products(created_at);
CREATE INDEX idx_products_slug ON products(slug);

-- Order indexes
CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created ON orders(created_at);
CREATE INDEX idx_orders_number ON orders(order_number);

-- Auction indexes
CREATE INDEX idx_auctions_product ON auctions(product_id);
CREATE INDEX idx_auctions_status ON auctions(status);
CREATE INDEX idx_auctions_start_time ON auctions(start_time);
CREATE INDEX idx_auctions_end_time ON auctions(end_time);

-- Bid indexes
CREATE INDEX idx_bids_auction ON bids(auction_id);
CREATE INDEX idx_bids_user ON bids(user_id);
CREATE INDEX idx_bids_amount ON bids(auction_id, amount DESC);
CREATE INDEX idx_bids_created ON bids(auction_id, created_at DESC);

-- Notification indexes
CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(user_id, is_read);
CREATE INDEX idx_notifications_created ON notifications(created_at);

-- Cart indexes
CREATE INDEX idx_cart_user ON cart_items(user_id);
CREATE INDEX idx_wishlist_user ON wishlists(user_id);

-- Review indexes
CREATE INDEX idx_reviews_product ON reviews(product_id);
CREATE INDEX idx_reviews_user ON reviews(user_id);
CREATE INDEX idx_reviews_approved ON reviews(is_approved);
```

## Data Relationships

### Entity Relationship Diagram
```
Users (1) -----> (M) Orders
Users (1) -----> (M) Cart Items
Users (1) -----> (M) Wishlists
Users (1) -----> (M) Reviews
Users (1) -----> (M) Bids
Users (1) -----> (M) Notifications

Products (1) -----> (M) Order Items
Products (1) -----> (M) Cart Items
Products (1) -----> (M) Wishlists
Products (1) -----> (M) Reviews
Products (1) -----> (1) Auctions

Categories (1) -----> (M) Products
Categories (1) -----> (M) Categories (self-reference)

Orders (1) -----> (M) Order Items
Auctions (1) -----> (M) Bids
Reviews (1) -----> (M) Review Votes
```

## Data Constraints & Validation

```sql
-- Check constraints
ALTER TABLE products ADD CONSTRAINT chk_price_positive CHECK (price > 0);
ALTER TABLE products ADD CONSTRAINT chk_stock_non_negative CHECK (stock >= 0);
ALTER TABLE products ADD CONSTRAINT chk_rating_range CHECK (rating >= 0 AND rating <= 5);

ALTER TABLE orders ADD CONSTRAINT chk_total_positive CHECK (total_amount > 0);
ALTER TABLE order_items ADD CONSTRAINT chk_quantity_positive CHECK (quantity > 0);

ALTER TABLE bids ADD CONSTRAINT chk_bid_positive CHECK (amount > 0);
ALTER TABLE reviews ADD CONSTRAINT chk_rating_valid CHECK (rating >= 1 AND rating <= 5);

-- Unique constraints
ALTER TABLE products ADD CONSTRAINT uk_products_sku UNIQUE (sku);
ALTER TABLE orders ADD CONSTRAINT uk_orders_number UNIQUE (order_number);
```

## Database Maintenance

### Backup Strategy
- Daily full backup
- Hourly incremental backup
- Point-in-time recovery capability
- Cross-region backup replication

### Archival Strategy
- Archive completed orders older than 2 years
- Archive read notifications older than 30 days
- Archive inactive user sessions older than 90 days

### Performance Monitoring
- Query performance analysis
- Index usage monitoring
- Connection pool monitoring
- Slow query logging
