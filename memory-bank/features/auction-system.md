# 🏆 Live Auction System Feature

## Overview
Sistem lelang real-time yang memungkinkan customer untuk berpartisipasi dalam bidding produk dengan timer countdown dan update real-time menggunakan WebSocket.

## User Stories
- Sebagai customer, saya ingin melihat produk yang sedang dilelang
- Sebagai customer, saya ingin memasang bid pada auction
- Sebagai customer, saya ingin melihat bid history secara real-time
- Sebagai customer, saya ingin mendapat notifikasi jika saya menang/kalah
- Sebagai admin, saya ingin memulai dan mengakhiri auction
- Sebagai admin, saya ingin melihat semua auction yang aktif

## Technical Requirements

### Frontend Components
- **LiveAuctionPage**: Halaman utama auction dengan timer dan bidding
- **AuctionCard**: Komponen card untuk auction di listing
- **BidForm**: Form untuk memasang bid
- **BidHistory**: Daftar bid history real-time
- **AuctionTimer**: Countdown timer component
- **AuctionWinner**: Tampilan pemenang auction
- **AuctionManagement**: Admin panel untuk kelola auction

### Backend Endpoints
```
GET /api/auctions - Get active auctions
GET /api/auctions/{id} - Get auction detail
POST /api/auctions/{id}/bid - Place a bid
GET /api/auctions/{id}/bids - Get bid history

// Admin endpoints
POST /api/admin/auctions - Create new auction
PUT /api/admin/auctions/{id}/start - Start auction
PUT /api/admin/auctions/{id}/end - End auction
DELETE /api/admin/auctions/{id} - Cancel auction
```

### WebSocket Endpoints
```
/ws/auction/{auctionId} - Real-time auction updates
```

### Database Schema
```sql
-- Auctions table
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bids table
CREATE TABLE bids (
    id BIGSERIAL PRIMARY KEY,
    auction_id BIGINT NOT NULL REFERENCES auctions(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_auction_amount (auction_id, amount DESC),
    INDEX idx_auction_time (auction_id, created_at DESC)
);
```

## UI Design Reference
Based on ui-design folder:
- **auction (1).pdf**: Live auction page layout
- **auction (2).pdf**: Bidding interface and timer
- **auction (3).pdf**: Bid history and winner announcement
- **auction (4).pdf**: Auction listing and management
- **auction-management-admin (1).pdf**: Admin auction management
- **auction-management-admin (2).pdf**: Admin auction controls

## Key Features

### 1. Real-time Auction Display
- **Countdown Timer**: Real-time countdown dengan format HH:MM:SS
- **Current Price**: Harga tertinggi saat ini
- **Bid Increment**: Minimum kenaikan bid
- **Total Bidders**: Jumlah peserta yang sudah bid
- **Product Info**: Detail produk yang dilelang

### 2. Bidding System
- **Bid Validation**: Validasi minimum bid amount
- **Auto-increment**: Tombol quick bid dengan increment
- **Custom Bid**: Input manual untuk bid amount
- **Bid Confirmation**: Konfirmasi sebelum submit bid
- **Balance Check**: Validasi saldo user (future feature)

### 3. Real-time Updates (WebSocket)
- **New Bid Notifications**: Update harga dan bidder terbaru
- **Timer Sync**: Sinkronisasi countdown timer
- **Auction Status**: Update status auction (started/ended)
- **Winner Announcement**: Notifikasi pemenang

### 4. Bid History
- **Live Updates**: Daftar bid terbaru secara real-time
- **Bidder Info**: Username (anonymized) dan bid amount
- **Timestamp**: Waktu bid ditempatkan
- **Your Bids**: Highlight bid dari user yang login

### 5. Auction Management (Admin)
- **Create Auction**: Setup produk, harga awal, durasi
- **Start/Stop**: Kontrol manual start dan end auction
- **Monitor**: Dashboard untuk monitor auction aktif
- **History**: Riwayat auction yang sudah selesai

## State Management (Pinia)

### Auction Store
```typescript
interface AuctionState {
  activeAuctions: Auction[]
  currentAuction: Auction | null
  bidHistory: Bid[]
  userBids: Bid[]
  wsConnection: WebSocket | null
  loading: boolean
  bidding: boolean
}

interface Auction {
  id: number
  productId: number
  product: Product
  title: string
  startingPrice: number
  currentPrice: number
  bidIncrement: number
  startTime: Date
  endTime: Date
  winnerId?: number
  status: AuctionStatus
  totalBids: number
  timeRemaining?: number
}

interface Bid {
  id: number
  auctionId: number
  userId: number
  username: string
  amount: number
  createdAt: Date
  isYourBid: boolean
}
```

## WebSocket Message Types
```typescript
// Incoming messages
interface BidPlacedMessage {
  type: 'BID_PLACED'
  auctionId: number
  bid: Bid
  newCurrentPrice: number
}

interface AuctionEndedMessage {
  type: 'AUCTION_ENDED'
  auctionId: number
  winnerId: number
  finalPrice: number
}

interface TimerUpdateMessage {
  type: 'TIMER_UPDATE'
  auctionId: number
  timeRemaining: number
}

// Outgoing messages
interface PlaceBidMessage {
  type: 'PLACE_BID'
  auctionId: number
  amount: number
  userId: number
}
```

## Business Rules

### Auction Rules
- Minimum bid = current price + bid increment
- Auction tidak bisa di-pause setelah dimulai
- Produk hanya bisa dijual melalui auction ATAU normal sale
- Winner otomatis masuk ke checkout process
- Auction extension jika ada bid di detik terakhir (optional)

### Bidding Rules
- User harus login untuk bid
- Tidak bisa bid pada auction sendiri (jika admin)
- Maximum bid per user per auction (optional)
- Cooldown period antar bid (anti-spam)

## Performance Considerations
- **WebSocket Connection Pooling**: Efficient connection management
- **Message Throttling**: Limit message frequency untuk prevent spam
- **Database Optimization**: Index pada auction_id dan timestamp
- **Caching**: Cache auction data untuk reduce DB load
- **Horizontal Scaling**: WebSocket server clustering

## Integration Points
- **Kafka Events**: AuctionEndedEvent, BidPlacedEvent
- **FCM Notifications**: Push notifications untuk bid updates
- **Order Service**: Auto-create order untuk auction winner
- **Payment Service**: Hold/reserve funds untuk bidding

## Error Handling
- **Connection Loss**: Auto-reconnect WebSocket
- **Bid Conflicts**: Handle simultaneous bids
- **Timer Desync**: Periodic timer synchronization
- **Invalid Bids**: Clear error messages dan validation

## Security Considerations
- **Authentication**: JWT validation untuk WebSocket
- **Rate Limiting**: Prevent bid spam
- **Input Validation**: Validate bid amounts
- **Anti-cheating**: Detect suspicious bidding patterns

## Analytics & Monitoring
- Auction participation rate
- Average bid per auction
- Peak concurrent users
- WebSocket connection metrics
- Bid frequency analysis
