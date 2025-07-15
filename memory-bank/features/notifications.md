# 🔔 Notifications Feature

## Overview
Sistem notifikasi real-time yang menggunakan Firebase Cloud Messaging (FCM) untuk mengirim push notifications dan WebSocket untuk update real-time di aplikasi.

## User Stories
- Sebagai customer, saya ingin mendapat notifikasi saat auction dimulai/selesai
- Sebagai customer, saya ingin mendapat notifikasi saat status pesanan berubah
- Sebagai customer, saya ingin mendapat notifikasi saat ada bid baru di auction
- Sebagai customer, saya ingin mengatur preferensi notifikasi
- Sebagai admin, saya ingin mengirim notifikasi broadcast ke semua user

## Technical Requirements

### Frontend Components
- **NotificationCenter**: Dropdown/panel untuk daftar notifikasi
- **NotificationItem**: Komponen individual notification
- **NotificationSettings**: Halaman pengaturan notifikasi
- **ToastNotification**: Pop-up notification untuk real-time updates
- **NotificationBadge**: Badge counter untuk unread notifications

### Backend Endpoints
```
// User notifications
GET /api/notifications - Get user notifications with pagination
PUT /api/notifications/{id}/read - Mark notification as read
PUT /api/notifications/read-all - Mark all notifications as read
DELETE /api/notifications/{id} - Delete notification

// Notification preferences
GET /api/notifications/preferences - Get user notification preferences
PUT /api/notifications/preferences - Update notification preferences

// Admin notifications
POST /api/admin/notifications/broadcast - Send broadcast notification
GET /api/admin/notifications/stats - Get notification statistics
```

### Database Schema
```sql
-- Notifications table
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    type VARCHAR(50) NOT NULL, -- AUCTION_STARTED, ORDER_STATUS, BID_PLACED, etc.
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    data JSONB, -- Additional data (order_id, auction_id, etc.)
    
    -- Status
    is_read BOOLEAN DEFAULT FALSE,
    is_sent BOOLEAN DEFAULT FALSE, -- FCM sent status
    
    -- Delivery info
    fcm_message_id VARCHAR(255), -- FCM message ID for tracking
    delivery_status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, SENT, DELIVERED, FAILED
    
    -- Timestamps
    scheduled_at TIMESTAMP, -- For scheduled notifications
    sent_at TIMESTAMP,
    read_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User notification preferences
CREATE TABLE notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) UNIQUE,
    
    -- Push notification preferences
    auction_started BOOLEAN DEFAULT TRUE,
    auction_ended BOOLEAN DEFAULT TRUE,
    bid_placed BOOLEAN DEFAULT TRUE,
    order_status_updated BOOLEAN DEFAULT TRUE,
    order_shipped BOOLEAN DEFAULT TRUE,
    order_delivered BOOLEAN DEFAULT TRUE,
    promotional BOOLEAN DEFAULT FALSE,
    
    -- Delivery preferences
    push_enabled BOOLEAN DEFAULT TRUE,
    email_enabled BOOLEAN DEFAULT TRUE,
    
    -- Quiet hours
    quiet_hours_enabled BOOLEAN DEFAULT FALSE,
    quiet_hours_start TIME,
    quiet_hours_end TIME,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- FCM tokens table
CREATE TABLE fcm_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    token VARCHAR(500) NOT NULL,
    device_type VARCHAR(20) NOT NULL, -- WEB, ANDROID, IOS
    device_info JSONB, -- Browser info, device model, etc.
    is_active BOOLEAN DEFAULT TRUE,
    last_used_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, token)
);
```

## UI Design Reference
Based on ui-design folder:
- **notification.pdf**: Customer notification center
- **notification-admin.pdf**: Admin notification management

## Key Features

### 1. Real-time Notifications
- **WebSocket Updates**: Instant in-app notifications
- **Push Notifications**: FCM untuk mobile dan web push
- **Toast Messages**: Non-intrusive pop-up notifications
- **Badge Counter**: Unread notification count

### 2. Notification Types
- **Auction Notifications**:
  - Auction started
  - Auction ending soon (5 minutes warning)
  - Auction ended
  - You won/lost auction
  - New bid placed (for participants)

- **Order Notifications**:
  - Order placed confirmation
  - Order status updated (processing, shipped, delivered)
  - Payment confirmation
  - Delivery confirmation

- **System Notifications**:
  - Account security alerts
  - Promotional offers
  - System maintenance notices

### 3. Notification Center
- **Notification List**: Chronological list dengan pagination
- **Read/Unread Status**: Visual distinction dan mark as read
- **Filtering**: Filter by type, read status, date
- **Actions**: Mark as read, delete, view related content

### 4. Notification Preferences
- **Granular Control**: Enable/disable per notification type
- **Delivery Method**: Push, email, in-app preferences
- **Quiet Hours**: Schedule untuk disable notifications
- **Frequency Control**: Immediate, batched, daily digest

### 5. Admin Notification Management
- **Broadcast Messages**: Send notification ke semua users
- **Targeted Notifications**: Send ke specific user groups
- **Scheduled Notifications**: Schedule untuk waktu tertentu
- **Analytics**: Delivery rates, open rates, engagement

## State Management (Pinia)

### Notification Store
```typescript
interface NotificationState {
  notifications: Notification[]
  unreadCount: number
  preferences: NotificationPreferences
  loading: boolean
  wsConnection: WebSocket | null
}

interface Notification {
  id: number
  userId: number
  type: NotificationType
  title: string
  message: string
  data?: any
  isRead: boolean
  createdAt: Date
  readAt?: Date
}

enum NotificationType {
  AUCTION_STARTED = 'AUCTION_STARTED',
  AUCTION_ENDED = 'AUCTION_ENDED',
  BID_PLACED = 'BID_PLACED',
  ORDER_STATUS = 'ORDER_STATUS',
  ORDER_SHIPPED = 'ORDER_SHIPPED',
  ORDER_DELIVERED = 'ORDER_DELIVERED',
  PROMOTIONAL = 'PROMOTIONAL',
  SYSTEM = 'SYSTEM'
}

interface NotificationPreferences {
  auctionStarted: boolean
  auctionEnded: boolean
  bidPlaced: boolean
  orderStatusUpdated: boolean
  orderShipped: boolean
  orderDelivered: boolean
  promotional: boolean
  pushEnabled: boolean
  emailEnabled: boolean
  quietHoursEnabled: boolean
  quietHoursStart?: string
  quietHoursEnd?: string
}
```

## Firebase Cloud Messaging Integration

### FCM Setup
```typescript
// FCM service worker registration
const messaging = getMessaging()

// Request permission
const permission = await Notification.requestPermission()
if (permission === 'granted') {
  const token = await getToken(messaging, { vapidKey: 'your-vapid-key' })
  // Send token to backend
}

// Handle foreground messages
onMessage(messaging, (payload) => {
  // Show toast notification
  showToastNotification(payload)
})
```

### Message Payload Structure
```json
{
  "notification": {
    "title": "Auction Started",
    "body": "RTX 4090 auction has started!",
    "icon": "/icons/auction.png",
    "badge": "/icons/badge.png"
  },
  "data": {
    "type": "AUCTION_STARTED",
    "auctionId": "123",
    "clickAction": "/auction/123"
  },
  "webpush": {
    "fcm_options": {
      "link": "/auction/123"
    }
  }
}
```

## Event-Driven Notifications (Kafka)

### Notification Events
```typescript
interface NotificationEvent {
  userId: number
  type: NotificationType
  title: string
  message: string
  data?: any
  scheduledAt?: Date
}

// Event handlers
class NotificationEventHandler {
  @EventHandler('OrderPlacedEvent')
  async handleOrderPlaced(event: OrderPlacedEvent) {
    await this.sendNotification({
      userId: event.userId,
      type: 'ORDER_STATUS',
      title: 'Order Confirmed',
      message: `Your order #${event.orderNumber} has been placed successfully`,
      data: { orderId: event.orderId }
    })
  }

  @EventHandler('AuctionStartedEvent')
  async handleAuctionStarted(event: AuctionStartedEvent) {
    // Send to all users who wishlisted this product
    const interestedUsers = await this.getInterestedUsers(event.productId)
    for (const userId of interestedUsers) {
      await this.sendNotification({
        userId,
        type: 'AUCTION_STARTED',
        title: 'Auction Started',
        message: `${event.productName} auction has started!`,
        data: { auctionId: event.auctionId }
      })
    }
  }
}
```

## Performance Considerations
- **Batch Processing**: Group notifications untuk efficient delivery
- **Rate Limiting**: Prevent notification spam
- **Token Management**: Clean up expired FCM tokens
- **Database Optimization**: Index pada user_id, created_at, is_read
- **Caching**: Cache notification preferences

## Integration Points
- **Firebase Cloud Messaging**: Push notification delivery
- **Email Service**: Email notification fallback
- **WebSocket Service**: Real-time in-app notifications
- **Kafka Events**: Event-driven notification triggers

## Business Rules

### Notification Delivery Rules
- Respect user preferences dan quiet hours
- No duplicate notifications dalam 5 menit
- Maximum 10 notifications per user per hour
- Promotional notifications max 1 per day

### Retention Policy
- Keep notifications for 30 days
- Archive read notifications after 7 days
- Delete promotional notifications after 3 days

## Error Handling
- **FCM Token Expired**: Remove invalid tokens
- **Delivery Failure**: Retry with exponential backoff
- **Network Issues**: Queue notifications untuk retry
- **User Preferences**: Respect opt-out preferences

## Analytics & Monitoring
- Notification delivery rates
- Open/click rates per notification type
- User engagement metrics
- FCM token health monitoring
- Notification preference trends

## Security Considerations
- **Token Security**: Secure FCM token storage
- **User Privacy**: Respect notification preferences
- **Content Filtering**: Validate notification content
- **Rate Limiting**: Prevent notification abuse
