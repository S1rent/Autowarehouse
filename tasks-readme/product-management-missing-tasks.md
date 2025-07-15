# Product Management - Missing Tasks Only

## Overview
Berdasarkan analisis kode yang sudah ada, ini adalah task yang benar-benar perlu dilakukan untuk menyelesaikan Product Management integration.

## ✅ What Already Exists

### Backend (100% Complete)
- ✅ Product Entity with all fields
- ✅ ProductService with all business logic
- ✅ ProductResource with all REST endpoints
- ✅ All API endpoints tested and working

### Frontend API Service (90% Complete)
- ✅ Basic Product APIs in `api.ts`:
  - `getProducts(filters)` - ✅ Exists
  - `getProduct(productId)` - ✅ Exists  
  - `getPopularProducts()` - ✅ Exists
  - `getFeaturedProducts()` - ✅ Exists
  - `getOnSaleProducts()` - ✅ Exists
- ✅ Product TypeScript interfaces - ✅ Exists
- ✅ Authentication headers - ✅ Exists
- ✅ Error handling interceptors - ✅ Exists

### Frontend UI (100% Complete)
- ✅ AdminProductView.vue with complete UI
- ✅ Product listing with search/filter
- ✅ Add/Edit product forms
- ✅ Pagination UI
- ✅ All form components

## ❌ What's Missing (Only These Need Implementation)

### 1. Missing Admin Product APIs in Frontend
**File**: `autowarehouse-frontend/src/services/api.ts`
**Time**: 30 minutes

**Missing Methods**:
```typescript
// Add these methods to ApiService class:

async createProduct(productData: CreateProductRequest): Promise<Product> {
  const response = await api.post<Product>('/products/admin', productData)
  return response.data
}

async updateProduct(productId: number, productData: UpdateProductRequest): Promise<Product> {
  const response = await api.put<Product>(`/products/admin/${productId}`, productData)
  return response.data
}

async deleteProduct(productId: number): Promise<{ message: string }> {
  const response = await api.delete<{ message: string }>(`/products/admin/${productId}`)
  return response.data
}

async updateProductStock(productId: number, stockQuantity: number): Promise<{ message: string }> {
  const response = await api.put<{ message: string }>(`/products/admin/${productId}/stock`, { stockQuantity })
  return response.data
}

async activateProduct(productId: number): Promise<{ message: string }> {
  const response = await api.put<{ message: string }>(`/products/admin/${productId}/activate`)
  return response.data
}

async deactivateProduct(productId: number): Promise<{ message: string }> {
  const response = await api.put<{ message: string }>(`/products/admin/${productId}/deactivate`)
  return response.data
}

async getProductStats(): Promise<ProductStats> {
  const response = await api.get<ProductStats>('/products/admin/stats')
  return response.data
}

async getLowStockProducts(threshold: number = 10): Promise<Product[]> {
  const response = await api.get<Product[]>(`/products/admin/low-stock?threshold=${threshold}`)
  return response.data
}

async getOutOfStockProducts(): Promise<Product[]> {
  const response = await api.get<Product[]>('/products/admin/out-of-stock')
  return response.data
}
```

**Missing TypeScript Interfaces**:
```typescript
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

interface UpdateProductRequest extends CreateProductRequest {
  isActive?: boolean
}
```

### 2. Missing Debounce Utility
**File**: `autowarehouse-frontend/src/utils/debounce.ts`
**Time**: 5 minutes

```typescript
export function debounce<T extends (...args: any[]) => any>(
  func: T,
  wait: number
): (...args: Parameters<T>) => void {
  let timeout: NodeJS.Timeout | null = null
  
  return (...args: Parameters<T>) => {
    if (timeout) {
      clearTimeout(timeout)
    }
    
    timeout = setTimeout(() => {
      func(...args)
    }, wait)
  }
}
```

### 3. Replace Hardcoded Data in AdminProductView.vue
**File**: `autowarehouse-frontend/src/views/admin/AdminProductView.vue`
**Time**: 2 hours

**Changes Needed**:

#### 3.1 Import Required Services
```typescript
import { apiService } from '@/services/api'
import { debounce } from '@/utils/debounce'
```

#### 3.2 Replace Hardcoded Products Array
```typescript
// REMOVE this hardcoded array:
const products = ref<Product[]>([
  // ... hardcoded data
])

// REPLACE with:
const products = ref<Product[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
```

#### 3.3 Add Categories from API
```typescript
const categories = ref<Category[]>([])
const categoriesLoading = ref(false)

const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}
```

#### 3.4 Replace fetchProducts Function
```typescript
const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    const filters: ProductFilters = {}
    
    if (searchQuery.value) {
      filters.search = searchQuery.value
    }
    
    if (categoryFilter.value) {
      filters.category = parseInt(categoryFilter.value)
    }
    
    const allProducts = await apiService.getProducts(filters)
    
    // Apply status filter on frontend (since backend doesn't have this filter)
    let filteredProducts = allProducts
    if (statusFilter.value === 'active') {
      filteredProducts = allProducts.filter(p => p.isActive)
    } else if (statusFilter.value === 'inactive') {
      filteredProducts = allProducts.filter(p => !p.isActive)
    } else if (statusFilter.value === 'out_of_stock') {
      filteredProducts = allProducts.filter(p => p.stockQuantity === 0)
    }
    
    products.value = filteredProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

// Add debounced version
const debouncedFetchProducts = debounce(fetchProducts, 300)
```

#### 3.5 Add Watchers for Real-time Filtering
```typescript
// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
}, { immediate: true })
```

#### 3.6 Replace saveProduct Function
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
      await apiService.updateProduct(parseInt(editingProductId.value), {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to save product')
    console.error(err)
  } finally {
    saving.value = false
  }
}
```

#### 3.7 Replace editProduct Function
```typescript
const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: fullProduct.categoryName, // Need to map to category ID
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    editingProductId.value = product.id.toString()
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}
```

#### 3.8 Replace deleteProduct Function
```typescript
const deleteProduct = async (productId: string) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(parseInt(productId))
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}
```

#### 3.9 Add Form Validation
```typescript
const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}
```

#### 3.10 Update onMounted
```typescript
onMounted(() => {
  fetchCategories()
  fetchProducts()
})
```

#### 3.11 Update Category Options in Template
```vue
<select 
  v-model="productForm.category"
  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
  required
>
  <option value="">Pilih Kategori</option>
  <option 
    v-for="category in categories" 
    :key="category.id" 
    :value="category.id"
  >
    {{ category.name }}
  </option>
</select>
```

#### 3.12 Update Category Filter in Template
```vue
<select 
  v-model="categoryFilter"
  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
>
  <option value="">Semua Kategori</option>
  <option 
    v-for="category in categories" 
    :key="category.id" 
    :value="category.id"
  >
    {{ category.name }}
  </option>
</select>
```

#### 3.13 Add Loading States in Template
```vue
<!-- Add loading spinner for product list -->
<div v-if="loading" class="flex justify-center items-center py-8">
  <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
  <span class="ml-2 text-gray-600">Loading products...</span>
</div>

<!-- Add error state -->
<div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
  <div class="flex">
    <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
    <span class="text-red-800">{{ error }}</span>
    <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
      <i class="fa-solid fa-refresh"></i> Retry
    </button>
  </div>
</div>

<!-- Update save button to show loading -->
<button 
  type="submit" 
  :disabled="saving"
  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
>
  <span v-if="saving">
    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
    Saving...
  </span>
  <span v-else>Publikasikan Produk</span>
</button>
```

## Summary

### Total Work Required: ~3 hours

1. **Add Missing APIs** (30 min) - Add 9 missing admin endpoints to api.ts
2. **Create Debounce Utility** (5 min) - Simple utility function
3. **Update AdminProductView.vue** (2 hours) - Replace hardcoded data with API calls

### Key Changes:
- ✅ Remove hardcoded products array
- ✅ Add real API integration with error handling
- ✅ Add debounced search (300ms)
- ✅ Connect to Category API for dropdowns
- ✅ Add loading states and error handling
- ✅ Connect CRUD operations to backend
- ✅ Add form validation

### What Won't Change:
- ✅ UI/UX stays exactly the same
- ✅ All existing functionality preserved
- ✅ No new components needed
- ✅ No design changes required

### Testing Checklist:
- [ ] Product listing loads from API
- [ ] Search works with 300ms debounce
- [ ] Category filter works
- [ ] Status filter works
- [ ] Create product works
- [ ] Edit product works
- [ ] Delete product works
- [ ] Loading states work
- [ ] Error handling works

### Dependencies:
- ✅ Backend APIs (already working)
- ✅ Category APIs (already integrated)
- ✅ Authentication (already working)
- ❌ Debounce utility (need to create)
- ❌ Missing admin APIs (need to add)

---

**Estimated Time**: 3 hours total
**Complexity**: Low (mostly replacing hardcoded data)
**Risk**: Very Low (backend already tested)
**Priority**: High (needed for admin functionality)
