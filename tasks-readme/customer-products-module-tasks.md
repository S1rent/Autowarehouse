# Customer Products Module - Task Breakdown

## Overview
Modul Products untuk customer mencakup halaman listing produk (ProductsView.vue) dan detail produk (ProductDetailView.vue). Berdasarkan analisis kode, berikut adalah breakdown task yang perlu dikerjakan.

## Current Status
- ✅ Basic ProductsView.vue structure exists
- ✅ Basic ProductDetailView.vue structure exists  
- ✅ Products store (Pinia) implemented
- ✅ API service methods for products available
- ✅ Basic filtering structure in place

## Tasks to Complete

### 1. Filter System Improvements
**Priority: HIGH**

#### 1.1 Category Filter Integration
- [ ] **Connect category filter to real API data**
  - Currently uses hardcoded categories (smartphone, laptop, tablet, kamera)
  - Need to fetch categories from `/api/categories` endpoint
  - Update filter to use category IDs instead of names
  - Show product count per category

#### 1.2 Price Range Filter Refinement
- [ ] **Implement requested filter options only**
  - Keep: Category, Price Range, Search by keyword
  - Remove: Brand filter (commented out but needs cleanup)
  - Remove: Rating filter (commented out but needs cleanup)
  - Ensure price range works with both manual input and preset ranges

#### 1.3 Search by Keyword
- [ ] **Enhance search functionality**
  - Currently basic search exists but needs improvement
  - Add debounced search (already has debounce utility)
  - Add search suggestions/autocomplete
  - Improve search UX with loading states

### 2. Sorting System Overhaul
**Priority: HIGH**

#### 2.1 Implement Requested Sorting Options
- [ ] **Replace current sorting options with:**
  - Name (A-Z) - ascending
  - Name (Z-A) - descending  
  - Price (Low to High) - ascending
  - Price (High to Low) - descending
- [ ] **Remove existing options:**
  - Terpopuler
  - Terbaru
  - Rating Tertinggi
- [ ] **Update backend API to support name-based sorting**
  - Add sorting parameters to ProductService.findProductsWithFilters()
  - Implement ORDER BY name ASC/DESC and price ASC/DESC

### 3. View Mode Implementation
**Priority: HIGH**

#### 3.1 List View Implementation
- [ ] **Create list view layout**
  - Currently only grid view is implemented
  - Design list view with horizontal product cards
  - Show more product details in list view (specifications, features)
  - Implement responsive design for list view
- [ ] **Toggle functionality**
  - Grid/List toggle buttons exist but list view doesn't change layout
  - Implement proper view switching logic
  - Save user preference in localStorage

### 4. Backend Integration Issues
**Priority: MEDIUM**

#### 4.1 API Integration Fixes
- [ ] **Fix ProductsView API calls**
  - Currently uses mock/hardcoded data in some places
  - Ensure all filters properly call backend API
  - Handle loading states and error states properly
  - Implement proper pagination with backend

#### 4.2 Product Detail Integration
- [ ] **Connect ProductDetailView to real API**
  - Currently uses hardcoded product data
  - Fetch product details from `/api/products/{id}`
  - Handle product not found scenarios
  - Implement proper image gallery with real product images

### 5. Missing Features Implementation
**Priority: MEDIUM**

#### 5.1 Wishlist Functionality
- [ ] **Implement wishlist features**
  - Add/remove from wishlist buttons exist but not functional
  - Create wishlist API endpoints in backend
  - Implement wishlist store in frontend
  - Show wishlist status on product cards

#### 5.2 Cart Integration
- [ ] **Enhance cart functionality**
  - Basic addToCart exists but needs improvement
  - Show "Added to cart" feedback
  - Handle out of stock scenarios
  - Update cart count in navbar

#### 5.3 Product Reviews System
- [ ] **Implement reviews functionality**
  - ProductDetailView shows mock reviews
  - Create review API endpoints
  - Allow customers to write reviews
  - Implement rating system

### 6. UI/UX Improvements
**Priority: LOW**

#### 6.1 Loading States
- [ ] **Improve loading experiences**
  - Add skeleton loaders for product cards
  - Show loading states during filtering
  - Implement progressive image loading

#### 6.2 Error Handling
- [ ] **Better error handling**
  - Show user-friendly error messages
  - Implement retry mechanisms
  - Handle network errors gracefully

#### 6.3 Mobile Responsiveness
- [ ] **Optimize for mobile**
  - Ensure filters work well on mobile
  - Optimize product grid for different screen sizes
  - Implement mobile-friendly navigation

### 7. Performance Optimizations
**Priority: LOW**

#### 7.1 Image Optimization
- [ ] **Optimize product images**
  - Implement lazy loading for product images
  - Add image compression
  - Use appropriate image formats (WebP)

#### 7.2 Pagination Improvements
- [ ] **Better pagination**
  - Currently shows simple page numbers
  - Implement infinite scroll option
  - Add "Load More" functionality

## Technical Notes

### Current File Structure
```
autowarehouse-frontend/src/views/
├── ProductsView.vue          # Main products listing page
├── ProductDetailView.vue     # Individual product detail page
└── WishlistView.vue         # Wishlist page (separate task)

autowarehouse-frontend/src/stores/
└── products.ts              # Products Pinia store

autowarehouse-frontend/src/services/
└── api.ts                   # API service methods
```

### Backend Endpoints Available
- `GET /api/products` - Get products with filters
- `GET /api/products/{id}` - Get single product
- `GET /api/products/featured/popular` - Get popular products
- `GET /api/products/featured/featured` - Get featured products
- `GET /api/products/featured/on-sale` - Get sale products
- `GET /api/categories` - Get categories

### Key Dependencies
- Vue 3 with Composition API
- Pinia for state management
- Vue Router for navigation
- Tailwind CSS for styling
- Axios for API calls

## Immediate Action Items

1. **Start with Filter System** - Fix category integration and clean up unused filters
2. **Implement Sorting** - Replace current options with requested name/price sorting
3. **Create List View** - Implement the missing list view layout
4. **Backend Integration** - Ensure all API calls work properly

## Estimated Timeline
- Filter improvements: 2-3 days
- Sorting system: 1-2 days  
- List view implementation: 2-3 days
- Backend integration fixes: 1-2 days
- Missing features: 3-4 days
- UI/UX improvements: 2-3 days

**Total estimated time: 11-17 days**
