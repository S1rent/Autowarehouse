# Product Management Integration - Task Breakdown

## Overview
Mengintegrasikan modul Product Management frontend dengan backend API. Frontend sudah lengkap dengan UI/UX yang baik, namun masih menggunakan hardcoded data. Backend API sudah siap dan lengkap.

## Current Status
- ✅ **Backend API**: Fully implemented and tested
- ✅ **Frontend UI**: Complete with all features
- ❌ **Integration**: Frontend still uses hardcoded data

## Task Breakdown

### Phase 1: API Service Setup (Priority: HIGH)

#### Task 1.1: Create Product API Service
**File**: `autowarehouse-frontend/src/services/productApi.ts`
**Estimated Time**: 2 hours

**Requirements**:
- Create comprehensive API service for product operations
- Include all CRUD operations
- Add search and filtering functions
- Handle error responses properly
- Add TypeScript interfaces for type safety

**API Endpoints to Implement**:
```typescript
// Public endpoints
GET /api/products - Get all products with filters
GET /api/products/{id} - Get product by ID
GET /api/products/sku/{sku} - Get product by SKU
GET /api/products/featured/popular - Get popular products
GET /api/products/featured/featured - Get featured products
GET /api/products/featured/on-sale - Get on-sale products
GET /api/products/featured/top-rated - Get top-rated products
GET /api/products/featured/recent - Get recent products

// Admin endpoints
POST /api/products/admin - Create product
PUT /api/products/admin/{id} - Update product
PUT /api/products/admin/{id}/stock - Update stock
PUT /api/products/admin/{id}/sale - Set sale price
DELETE /api/products/admin/{id}/sale - Clear sale
PUT /api/products/admin/{id}/activate - Activate product
PUT /api/products/admin/{id}/deactivate - Deactivate product
DELETE /api/products/admin/{id} - Delete product
GET /api/products/admin/stats - Get product statistics
GET /api/products/admin/low-stock - Get low stock products
GET /api/products/admin/out-of-stock - Get out of stock products
```

**TypeScript Interfaces Needed**:
```typescript
interface Product {
  id: number
  name: string
  description: string
  shortDescription?: string
  price: number
  originalPrice?: number
  stockQuantity: number
  sku: string
  brand: string
  model: string
  specifications?: string
  features?: string
  imageUrls: string[]
  tags?: string[]
  isActive: boolean
  isFeatured: boolean
  isOnSale: boolean
  saleStartDate?: string
  saleEndDate?: string
  rating: number
  reviewCount: number
  salesCount: number
  viewCount: number
  category: {
    id: number
    name: string
  }
  createdAt: string
  updatedAt: string
}

interface CreateProductRequest {
  name: string
  description: string
  price: number
  originalPrice?: number
  stockQuantity: number
  brand: string
  model: string
  specifications?: string
  features?: string
  imageUrls: string[]
  categoryId: number
}

interface ProductFilters {
  search?: string
  categoryId?: number
  brand?: string
  minPrice?: number
  maxPrice?: number
  onSale?: boolean
  page?: number
  size?: number
}

interface ProductStats {
  totalProducts: number
  activeProducts: number
  lowStockProducts: number
  outOfStockProducts: number
}
```

#### Task 1.2: Update API Base Service
**File**: `autowarehouse-frontend/src/services/api.ts`
**Estimated Time**: 30 minutes

**Requirements**:
- Add product-related endpoints to existing API service
- Ensure proper authentication headers for admin endpoints
- Add error handling for product-specific errors

### Phase 2: Frontend Integration (Priority: HIGH)

#### Task 2.1: Integrate Product Listing
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 3 hours

**Requirements**:
- Replace hardcoded products array with API calls
- Implement real-time search with debouncing (300ms)
- Add category filter integration with Category API
- Implement status filtering (active/inactive/out_of_stock)
- Add loading states and error handling
- Implement proper pagination
- Add refresh functionality

**Changes Needed**:
```typescript
// Replace hardcoded data
const products = ref<Product[]>([])
const loading = ref(false)
const error = ref<string | null>(null)

// Add API integration
const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    const filters: ProductFilters = {
      search: searchQuery.value || undefined,
      categoryId: categoryFilter.value ? parseInt(categoryFilter.value) : undefined,
      page: currentPage.value - 1,
      size: itemsPerPage
    }
    
    // Add status filter logic
    if (statusFilter.value === 'active') {
      // Filter active products on frontend or add API parameter
    }
    
    const response = await productApi.getProducts(filters)
    products.value = response.data
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// Add debounced search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Watch for filter changes
watch([searchQuery, categoryFilter, statusFilter, currentPage], () => {
  debouncedFetchProducts()
}, { immediate: true })
```

#### Task 2.2: Integrate Category Dropdown
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 1 hour

**Requirements**:
- Replace hardcoded category options with API data
- Use existing Category API service
- Add loading state for category dropdown
- Handle category selection properly

**Changes Needed**:
```typescript
import { categoryApi } from '@/services/api'

const categories = ref<Category[]>([])
const categoriesLoading = ref(false)

const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await categoryApi.getCategories({ active: true })
    categories.value = response.data
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

onMounted(() => {
  fetchCategories()
})
```

#### Task 2.3: Integrate Product Creation
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Connect create product form to API
- Add proper validation
- Handle success/error responses
- Add loading state during creation
- Reset form after successful creation

**Changes Needed**:
```typescript
const saving = ref(false)

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: productForm.value.brand || 'Unknown',
      model: productForm.value.model || 'Unknown',
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: productForm.value.imageUrls || [],
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await productApi.updateProduct(editingProductId.value, productData)
      showSuccessMessage('Product updated successfully!')
    } else {
      await productApi.createProduct(productData)
      showSuccessMessage('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    showErrorMessage('Failed to save product')
    console.error(err)
  } finally {
    saving.value = false
  }
}
```

#### Task 2.4: Integrate Product Update/Delete
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Connect edit functionality to API
- Connect delete functionality to API
- Add confirmation dialogs
- Handle loading states
- Refresh data after operations

**Changes Needed**:
```typescript
const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const response = await productApi.getProduct(product.id)
    const fullProduct = response.data
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: fullProduct.category.id.toString(),
      // ... map other fields
    }
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    showErrorMessage('Failed to load product details')
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await productApi.deleteProduct(productId)
    showSuccessMessage('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    showErrorMessage('Failed to delete product')
    console.error(err)
  }
}
```

### Phase 3: Enhanced Features (Priority: MEDIUM)

#### Task 3.1: Add Product Statistics Dashboard
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Add statistics cards at the top of the page
- Show total products, active products, low stock, out of stock
- Update statistics in real-time
- Add visual indicators for critical metrics

**Implementation**:
```vue
<template>
  <!-- Statistics Cards -->
  <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
    <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
      <div class="flex items-center">
        <div class="flex-shrink-0">
          <i class="fa-solid fa-box text-blue-600 text-2xl"></i>
        </div>
        <div class="ml-4">
          <p class="text-sm font-medium text-gray-500">Total Products</p>
          <p class="text-2xl font-semibold text-gray-900">{{ stats.totalProducts }}</p>
        </div>
      </div>
    </div>
    <!-- More stat cards... -->
  </div>
</template>
```

#### Task 3.2: Add Bulk Operations
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 3 hours

**Requirements**:
- Add checkbox selection for multiple products
- Implement bulk activate/deactivate
- Implement bulk delete with confirmation
- Add bulk category change
- Show progress during bulk operations

#### Task 3.3: Add Advanced Filtering
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Add price range filter
- Add brand filter
- Add stock level filter (low stock, out of stock)
- Add date range filter (created date)
- Save filter preferences

#### Task 3.4: Add Export Functionality
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Export product list to CSV
- Export with current filters applied
- Include all product details
- Add download progress indicator

### Phase 4: Image Management (Priority: MEDIUM)

#### Task 4.1: Implement Image Upload
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 4 hours

**Requirements**:
- Replace placeholder image upload with real functionality
- Support multiple image upload
- Add image preview
- Add image validation (size, format)
- Implement drag & drop
- Add image reordering
- Add image deletion

**Note**: This requires backend file upload endpoint implementation first.

### Phase 5: Performance Optimization (Priority: LOW)

#### Task 5.1: Add Pagination
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 1 hour

**Requirements**:
- Implement server-side pagination
- Add page size options
- Optimize for large datasets
- Add loading states during page changes

#### Task 5.2: Add Caching
**File**: `autowarehouse-frontend/src/services/productApi.ts`
**Estimated Time**: 2 hours

**Requirements**:
- Cache product list data
- Implement cache invalidation
- Add cache for categories
- Optimize repeated API calls

### Phase 6: Error Handling & UX (Priority: MEDIUM)

#### Task 6.1: Enhanced Error Handling
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 2 hours

**Requirements**:
- Add comprehensive error messages
- Handle network errors gracefully
- Add retry functionality
- Show user-friendly error states

#### Task 6.2: Loading States
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Estimated Time**: 1 hour

**Requirements**:
- Add skeleton loading for product list
- Add loading spinners for actions
- Add progress indicators for uploads
- Optimize perceived performance

## Testing Requirements

### Unit Tests
- Product API service functions
- Form validation logic
- Filter and search functionality
- Error handling scenarios

### Integration Tests
- API integration with backend
- CRUD operations end-to-end
- File upload functionality
- Authentication and authorization

### Manual Testing Checklist
- [ ] Product listing loads correctly
- [ ] Search functionality works with debouncing
- [ ] Category filter works
- [ ] Status filter works
- [ ] Pagination works
- [ ] Create product works
- [ ] Edit product works
- [ ] Delete product works
- [ ] Form validation works
- [ ] Error handling works
- [ ] Loading states work
- [ ] Responsive design works

## Dependencies

### Frontend Dependencies
- Existing API service structure
- Authentication system
- Category API integration
- Debounce utility function

### Backend Dependencies
- Product API endpoints (✅ Already implemented)
- Category API endpoints (✅ Already implemented)
- Authentication middleware (✅ Already implemented)
- File upload endpoint (❌ Needs implementation)

## Risk Assessment

### High Risk
- **Image Upload**: Requires backend file upload implementation
- **Performance**: Large product datasets may cause performance issues

### Medium Risk
- **Data Validation**: Need to ensure frontend validation matches backend
- **Error Handling**: Complex error scenarios need thorough testing

### Low Risk
- **Basic CRUD**: Backend API is already tested and working
- **UI Integration**: Frontend components are already built

## Success Criteria

### Phase 1 Success
- [ ] Product listing shows real data from API
- [ ] Search and filtering work correctly
- [ ] Basic CRUD operations work
- [ ] Error handling is implemented

### Phase 2 Success
- [ ] All advanced features work
- [ ] Performance is acceptable
- [ ] User experience is smooth
- [ ] All tests pass

### Final Success
- [ ] Product management is fully integrated
- [ ] No hardcoded data remains
- [ ] All features work as expected
- [ ] Code is maintainable and documented

## Timeline Estimate

### Phase 1 (Critical): 8.5 hours
- API Service Setup: 2.5 hours
- Basic Integration: 6 hours

### Phase 2 (Important): 9 hours
- Enhanced Features: 9 hours

### Phase 3 (Optional): 11 hours
- Image Management: 4 hours
- Performance & UX: 5 hours
- Testing: 2 hours

**Total Estimated Time: 28.5 hours**

## Notes

1. **Backend API is Ready**: All necessary endpoints are implemented and tested
2. **Frontend UI is Complete**: No UI changes needed, only data integration
3. **Category Integration**: Can reuse existing category API service
4. **Authentication**: Admin endpoints require proper JWT token
5. **Image Upload**: May need to implement backend file upload endpoint first
6. **Testing**: Comprehensive testing is crucial due to complex business logic

## Next Steps

1. Start with Phase 1: API Service Setup
2. Test each integration step thoroughly
3. Implement error handling early
4. Add loading states for better UX
5. Consider performance implications
6. Plan for image upload implementation
7. Document any API changes needed

---

**Created**: July 14, 2025  
**Last Updated**: July 14, 2025  
**Status**: Ready for Implementation
