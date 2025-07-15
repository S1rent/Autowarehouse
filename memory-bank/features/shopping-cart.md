# 🛒 Shopping Cart & Wishlist Feature

## Overview
Sistem manajemen keranjang belanja dan wishlist yang memungkinkan customer untuk menambah, mengedit, dan mengelola produk sebelum checkout.

## User Stories
- Sebagai customer, saya ingin menambahkan produk ke shopping cart
- Sebagai customer, saya ingin mengubah kuantitas produk di cart
- Sebagai customer, saya ingin menghapus produk dari cart
- Sebagai customer, saya ingin melihat total harga di cart
- Sebagai customer, saya ingin menyimpan produk ke wishlist
- Sebagai customer, saya ingin memindahkan produk dari wishlist ke cart

## Technical Requirements

### Frontend Components
- **ShoppingCartPage**: Halaman utama shopping cart
- **CartItem**: Komponen item dalam cart
- **CartSummary**: Ringkasan total harga dan checkout
- **WishlistPage**: Halaman wishlist
- **WishlistItem**: Komponen item dalam wishlist
- **AddToCartButton**: Tombol add to cart di product pages
- **CartIcon**: Icon cart dengan badge jumlah item

### Backend Endpoints
```
GET /api/cart - Get user's cart items
POST /api/cart/add - Add item to cart
PUT /api/cart/update/{itemId} - Update cart item quantity
DELETE /api/cart/remove/{itemId} - Remove item from cart
DELETE /api/cart/clear - Clear all cart items

GET /api/wishlist - Get user's wishlist
POST /api/wishlist/add - Add item to wishlist
DELETE /api/wishlist/remove/{itemId} - Remove from wishlist
POST /api/wishlist/move-to-cart/{itemId} - Move wishlist item to cart
```

### Database Schema
```sql
-- Shopping cart table
CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    quantity INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);

-- Wishlist table
CREATE TABLE wishlists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);
```

## UI Design Reference
Based on ui-design folder:
- **cart (1).pdf**: Shopping cart page layout
- **cart (2).pdf**: Cart item management interface
- **cart (3).pdf**: Checkout summary and wishlist integration

## Key Features

### 1. Shopping Cart Management
- **Add to Cart**: Tambah produk dengan kuantitas tertentu
- **Update Quantity**: Ubah jumlah item dengan +/- buttons atau input
- **Remove Item**: Hapus item individual dari cart
- **Clear Cart**: Kosongkan seluruh cart
- **Stock Validation**: Validasi ketersediaan stok saat add/update

### 2. Cart Display
- **Item List**: Daftar produk dengan gambar, nama, harga, kuantitas
- **Price Calculation**: Harga per item dan subtotal
- **Total Summary**: Total harga, pajak (jika ada), ongkir estimasi
- **Empty State**: Tampilan ketika cart kosong
- **Loading States**: Loading indicator saat update cart

### 3. Wishlist Management
- **Add to Wishlist**: Simpan produk untuk nanti
- **Remove from Wishlist**: Hapus dari wishlist
- **Move to Cart**: Pindahkan dari wishlist ke cart
- **Wishlist Counter**: Badge jumlah item di wishlist

### 4. Persistence & Sync
- **User Cart**: Cart tersimpan per user account
- **Guest Cart**: Local storage untuk guest users
- **Cart Merge**: Merge guest cart dengan user cart saat login
- **Real-time Sync**: Sinkronisasi cart antar device/tab

## State Management (Pinia)

### Cart Store
```typescript
interface CartState {
  items: CartItem[]
  loading: boolean
  totalItems: number
  totalPrice: number
  lastUpdated: Date | null
}

interface CartItem {
  id: number
  productId: number
  product: Product
  quantity: number
  subtotal: number
}

// Actions
const cartStore = {
  addToCart(productId: number, quantity: number)
  updateQuantity(itemId: number, quantity: number)
  removeItem(itemId: number)
  clearCart()
  calculateTotals()
}
```

### Wishlist Store
```typescript
interface WishlistState {
  items: WishlistItem[]
  loading: boolean
  totalItems: number
}

interface WishlistItem {
  id: number
  productId: number
  product: Product
  addedAt: Date
}
```

## Business Rules

### Cart Validation
- Maximum quantity per item: 99
- Stock availability check sebelum add/update
- Price validation dengan database
- User authentication required untuk persistent cart

### Wishlist Rules
- Maximum 100 items per user
- Duplicate prevention
- Auto-remove jika produk discontinued
- Guest users: local storage only

## Performance Considerations
- **Debounced Updates**: Delay API calls saat user mengubah quantity
- **Optimistic Updates**: Update UI immediately, rollback jika error
- **Batch Operations**: Group multiple cart updates
- **Cache Management**: Cache cart data dengan expiration
- **Lazy Loading**: Load product details on demand

## Integration Points
- **Product Service**: Validasi produk dan stok
- **User Service**: Authentication dan user data
- **Order Service**: Transfer cart data saat checkout
- **Notification Service**: Cart abandonment reminders

## Error Handling
- **Stock Insufficient**: Tampilkan pesan dan adjust quantity
- **Product Unavailable**: Remove dari cart dengan notifikasi
- **Network Errors**: Retry mechanism dan offline support
- **Validation Errors**: Clear error messages dan guidance

## Analytics & Tracking
- Cart abandonment rate
- Most added/removed products
- Average cart value
- Conversion rate dari wishlist ke cart
- Time spent in cart before checkout

## Security Considerations
- User authorization untuk cart operations
- Input validation untuk quantity dan product ID
- Rate limiting untuk cart updates
- CSRF protection untuk state-changing operations
