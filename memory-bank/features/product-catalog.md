# 📦 Product Catalog Feature

## Overview
Sistem katalog produk yang memungkinkan customer untuk browse, search, dan filter produk komputer dan aksesoris.

## User Stories
- Sebagai customer, saya ingin melihat daftar produk berdasarkan kategori
- Sebagai customer, saya ingin mencari produk dengan keyword tertentu
- Sebagai customer, saya ingin memfilter produk berdasarkan harga, rating, brand
- Sebagai customer, saya ingin melihat detail lengkap produk
- Sebagai customer, saya ingin melihat gambar produk dalam carousel

## Technical Requirements

### Frontend Components
- **ProductListPage**: Halaman daftar produk dengan filter dan sorting
- **ProductCard**: Komponen card untuk menampilkan produk
- **ProductDetailPage**: Halaman detail produk lengkap
- **ProductFilter**: Komponen filter (kategori, harga, rating, brand)
- **ProductSearch**: Komponen pencarian produk
- **ImageCarousel**: Carousel untuk gambar produk

### Backend Endpoints
```
GET /api/products - Get all products with pagination
GET /api/products/{id} - Get product detail
GET /api/products/search?q={query} - Search products
GET /api/products/category/{categoryId} - Get products by category
GET /api/categories - Get all categories
```

### Database Schema
```sql
-- Products table
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    category_id BIGINT REFERENCES categories(id),
    image_urls TEXT[], -- Array of Firebase Storage URLs
    specifications JSONB, -- Product specs as JSON
    is_auction_eligible BOOLEAN DEFAULT false,
    rating DECIMAL(2,1) DEFAULT 0,
    review_count INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    parent_id BIGINT REFERENCES categories(id),
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## UI Design Reference
Based on ui-design folder:
- **product (1).pdf**: Product listing page layout
- **product (2).pdf**: Product detail page layout
- **product (3).pdf**: Product search and filter interface

## Key Features

### 1. Product Browsing
- Grid/list view toggle
- Pagination atau infinite scroll
- Category navigation
- Breadcrumb navigation

### 2. Search & Filter
- **Search**: Full-text search pada nama dan deskripsi
- **Category Filter**: Filter berdasarkan kategori produk
- **Price Filter**: Range slider untuk harga
- **Rating Filter**: Filter berdasarkan rating minimum
- **Brand Filter**: Checkbox untuk brand tertentu
- **Stock Filter**: Hanya tampilkan produk yang tersedia

### 3. Product Sorting
- Termurah ke termahal
- Termahal ke termurah
- Terbaru
- Terpopuler (berdasarkan penjualan)
- Rating tertinggi

### 4. Product Detail
- Multiple product images dengan zoom
- Spesifikasi teknis lengkap
- Harga dan informasi stok
- Rating dan review dari customer
- Tombol "Add to Cart" dan "Add to Wishlist"
- Tombol "Join Auction" jika produk sedang dilelang

## State Management (Pinia)

### Product Store
```typescript
interface ProductState {
  products: Product[]
  currentProduct: Product | null
  categories: Category[]
  filters: ProductFilters
  searchQuery: string
  loading: boolean
  pagination: PaginationInfo
}

interface ProductFilters {
  categoryId?: number
  minPrice?: number
  maxPrice?: number
  minRating?: number
  brands?: string[]
  inStock?: boolean
}
```

## Performance Considerations
- Lazy loading untuk gambar produk
- Pagination untuk menghindari load data berlebihan
- Caching untuk kategori dan filter options
- Image optimization dan CDN
- Search debouncing untuk mengurangi API calls

## Integration Points
- **Firebase Storage**: Untuk menyimpan dan serve gambar produk
- **Search Service**: Elasticsearch atau PostgreSQL full-text search
- **Cache Layer**: Redis untuk caching data produk populer
- **CDN**: Untuk optimasi loading gambar

## Validation Rules
- Nama produk: Required, max 255 karakter
- Harga: Required, positive number
- Stok: Required, non-negative integer
- Kategori: Required, must exist in categories table
- Gambar: Max 10 images per product, max 5MB per image
