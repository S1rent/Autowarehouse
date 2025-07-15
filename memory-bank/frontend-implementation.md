# 🎨 Frontend Implementation - Autowarehouse System

## Overview
Comprehensive documentation of the Autowarehouse frontend implementation using Vue 3, TypeScript, and modern web technologies. This document covers the complete frontend architecture, implementation details, and current status as of January 8, 2025.

## 🎯 Implementation Status: 85% Complete

### ✅ Fully Implemented
- **Core Infrastructure**: 100% Complete
- **State Management**: 100% Complete  
- **Authentication System**: 100% Complete
- **Home Page**: 100% Complete
- **Product Catalog**: 100% Complete
- **Live Auction System**: 100% Complete
- **Navigation System**: 100% Complete
- **API Integration**: 90% Complete

### 🟡 Partially Implemented
- **Shopping System**: 70% Complete
- **User Management**: 80% Complete
- **Admin Panel**: 60% Complete
- **Real-time Features**: 30% Complete

### 🔴 Not Implemented
- **Payment Integration**: 0% Complete
- **File Upload System**: 0% Complete
- **PWA Features**: 0% Complete

---

## 🏗️ Project Infrastructure

### Technology Stack
- **Framework**: Vue 3.4.0 with Composition API
- **Language**: TypeScript 5.3.0 with strict mode
- **Build Tool**: Vite 5.0.0 with hot module replacement
- **Styling**: Tailwind CSS 3.4.0 with custom design system
- **State Management**: Pinia 2.1.7 for reactive state
- **Routing**: Vue Router 4.2.5 with lazy loading
- **HTTP Client**: Axios 1.6.0 with interceptors
- **Utilities**: VueUse 10.7.0 for composition utilities

### Build Configuration
```typescript
// vite.config.ts
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  build: {
    target: 'es2020',
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ['vue', 'vue-router', 'pinia'],
          utils: ['axios', '@vueuse/core']
        }
      }
    }
  }
})
```

### TypeScript Configuration
```json
{
  "compilerOptions": {
    "target": "ES2020",
    "useDefineForClassFields": true,
    "lib": ["ES2020", "DOM", "DOM.Iterable"],
    "module": "ESNext",
    "skipLibCheck": true,
    "moduleResolution": "bundler",
    "allowImportingTsExtensions": true,
    "resolveJsonModule": true,
    "isolatedModules": true,
    "noEmit": true,
    "jsx": "preserve",
    "strict": true,
    "noUnusedLocals": true,
    "noUnusedParameters": true,
    "noFallthroughCasesInSwitch": true,
    "baseUrl": ".",
    "paths": {
      "@/*": ["src/*"]
    }
  }
}
```

---

## 🗂️ Project Structure

### Directory Organization
```
src/
├── components/           # Reusable UI components
│   ├── AdminNavbar.vue
│   ├── GuestNavbar.vue
│   └── UserNavbar.vue
├── router/              # Vue Router configuration
│   └── index.ts
├── services/            # API and external services
│   └── api.ts
├── stores/              # Pinia state management
│   ├── auth.ts
│   ├── cart.ts
│   └── products.ts
├── views/               # Page components
│   ├── auth/           # Authentication pages
│   ├── admin/          # Admin panel pages
│   └── *.vue           # User pages
├── App.vue             # Root component
├── main.ts             # Application entry point
└── style.css           # Global styles
```

### Component Architecture
- **Composition API**: All components use Vue 3 Composition API
- **TypeScript**: Strict typing for all components and composables
- **Single File Components**: .vue files with `<script setup lang="ts">`
- **Scoped Styling**: Component-specific styles with Tailwind CSS
- **Props Validation**: Runtime and compile-time prop validation

---

## 🎨 Design System Implementation

### Tailwind CSS Configuration
```javascript
// tailwind.config.js
module.exports = {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#eff6ff',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
          900: '#1e3a8a'
        },
        gray: {
          50: '#f9fafb',
          100: '#f3f4f6',
          200: '#e5e7eb',
          300: '#d1d5db',
          400: '#9ca3af',
          500: '#6b7280',
          600: '#4b5563',
          700: '#374151',
          800: '#1f2937',
          900: '#111827'
        }
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif']
      },
      spacing: {
        '18': '4.5rem',
        '88': '22rem'
      }
    }
  },
  plugins: []
}
```

### Design Tokens
- **Colors**: Primary blue palette with gray neutrals
- **Typography**: Inter font family with consistent sizing
- **Spacing**: 8px grid system with custom utilities
- **Shadows**: Consistent elevation system
- **Borders**: Rounded corners and consistent border widths
- **Animations**: Smooth transitions and hover effects

---

## 🛣️ Routing Implementation

### Router Configuration
```typescript
// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  // Public routes
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue')
  },
  
  // Authentication routes
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/LoginView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/auth/RegisterView.vue')
  },
  {
    path: '/forgot-password',
    name: 'ForgotPassword',
    component: () => import('../views/auth/ForgotPasswordView.vue')
  },

  // User routes
  {
    path: '/products',
    name: 'Products',
    component: () => import('../views/ProductsView.vue')
  },
  {
    path: '/product/:id',
    name: 'ProductDetail',
    component: () => import('../views/ProductDetailView.vue')
  },
  {
    path: '/auctions',
    name: 'LiveAuctions',
    component: () => import('../views/LiveAuctionView.vue')
  },
  {
    path: '/auction/:id',
    name: 'AuctionDetail',
    component: () => import('../views/AuctionDetailView.vue')
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/CartView.vue')
  },
  {
    path: '/profile',
    name: 'UserProfile',
    component: () => import('../views/UserProfileView.vue')
  },

  // Admin routes
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: () => import('../views/admin/AdminDashboardView.vue'),
    meta: { requiresAuth: true, requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guards
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next('/login')
  } else if (to.meta.requiresAdmin && !authStore.isAdmin) {
    next('/') 
  } else {
    next()
  }
})

export default router
```

### Route Features
- **Lazy Loading**: All routes use dynamic imports for code splitting
- **Route Guards**: Authentication and authorization checks
- **Meta Fields**: Route metadata for permissions and layout
- **Nested Routes**: Admin panel with nested routing structure
- **Dynamic Routes**: Product and auction detail pages with parameters

---

## 🗄️ State Management Implementation

### Auth Store
```typescript
// src/stores/auth.ts
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type User, type LoginRequest, type RegisterRequest } from '@/services/api'

export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref<User | null>(null)
  const token = ref<string | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const isAuthenticated = computed(() => !!token.value && !!user.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')
  const isCustomer = computed(() => user.value?.role === 'CUSTOMER')
  const fullName = computed(() => {
    if (!user.value) return ''
    return `${user.value.firstName} ${user.value.lastName}`
  })

  // Actions
  const login = async (credentials: LoginRequest) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.login(credentials)
      
      if (response.accessToken && response.userId) {
        user.value = {
          id: response.userId,
          email: response.email || '',
          firstName: response.firstName || '',
          lastName: response.lastName || '',
          role: (response.role as 'ADMIN' | 'CUSTOMER') || 'CUSTOMER',
          isActive: true,
          isEmailVerified: response.isEmailVerified || false
        }
        token.value = response.accessToken
        
        // Store in localStorage
        localStorage.setItem('auth_token', response.accessToken)
        localStorage.setItem('refresh_token', response.refreshToken || '')
        localStorage.setItem('user_data', JSON.stringify(user.value))
      }
      
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Login failed'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const register = async (userData: RegisterRequest) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.register(userData)
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Registration failed'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const logout = () => {
    user.value = null
    token.value = null
    error.value = null
    
    localStorage.removeItem('auth_token')
    localStorage.removeItem('user_data')
  }

  const initializeAuth = () => {
    const storedToken = localStorage.getItem('auth_token')
    const storedUser = localStorage.getItem('user_data')
    
    if (storedToken && storedUser) {
      try {
        token.value = storedToken
        user.value = JSON.parse(storedUser)
      } catch (err) {
        logout()
      }
    }
  }

  return {
    // State
    user,
    token,
    isLoading,
    error,
    
    // Getters
    isAuthenticated,
    isAdmin,
    isCustomer,
    fullName,
    
    // Actions
    login,
    register,
    logout,
    initializeAuth
  }
})
```

### Products Store
```typescript
// src/stores/products.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { apiService, type Product, type ProductFilters } from '@/services/api'

export const useProductsStore = defineStore('products', () => {
  // State
  const products = ref<Product[]>([])
  const featuredProducts = ref<Product[]>([])
  const onSaleProducts = ref<Product[]>([])
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Actions
  const fetchProducts = async (filters?: ProductFilters) => {
    try {
      isLoading.value = true
      error.value = null
      
      const data = await apiService.getProducts(filters)
      products.value = data
      
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch products'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const fetchFeaturedProducts = async (limit: number = 10) => {
    try {
      const data = await apiService.getFeaturedProducts(limit)
      featuredProducts.value = data
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch featured products'
      throw err
    }
  }

  const fetchOnSaleProducts = async (limit: number = 10) => {
    try {
      const data = await apiService.getOnSaleProducts(limit)
      onSaleProducts.value = data
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch sale products'
      throw err
    }
  }

  const getProductById = async (id: number) => {
    try {
      return await apiService.getProduct(id)
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch product'
      throw err
    }
  }

  return {
    // State
    products,
    featuredProducts,
    onSaleProducts,
    isLoading,
    error,
    
    // Actions
    fetchProducts,
    fetchFeaturedProducts,
    fetchOnSaleProducts,
    getProductById
  }
})
```

### Cart Store
```typescript
// src/stores/cart.ts
import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type CartItem, type CartSummary } from '@/services/api'

export const useCartStore = defineStore('cart', () => {
  // State
  const items = ref<CartItem[]>([])
  const summary = ref<CartSummary | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const itemCount = computed(() => items.value.reduce((sum, item) => sum + item.quantity, 0))
  const selectedItemCount = computed(() => 
    items.value.filter(item => item.isSelected).reduce((sum, item) => sum + item.quantity, 0)
  )
  const totalAmount = computed(() => 
    items.value.filter(item => item.isSelected).reduce((sum, item) => sum + item.subtotal, 0)
  )

  // Actions
  const fetchCartItems = async (userId: number) => {
    try {
      isLoading.value = true
      error.value = null
      
      const data = await apiService.getCartItems(userId)
      items.value = data
      
      return data
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch cart items'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const addToCart = async (userId: number, productId: number, quantity: number = 1) => {
    try {
      const cartItem = await apiService.addToCart({ userId, productId, quantity })
      
      // Update local state
      const existingIndex = items.value.findIndex(item => item.productId === productId)
      if (existingIndex >= 0) {
        items.value[existingIndex] = cartItem
      } else {
        items.value.push(cartItem)
      }
      
      return cartItem
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to add to cart'
      throw err
    }
  }

  const updateQuantity = async (cartItemId: number, quantity: number) => {
    try {
      await apiService.updateCartItemQuantity(cartItemId, quantity)
      
      // Update local state
      const item = items.value.find(item => item.id === cartItemId)
      if (item) {
        item.quantity = quantity
        item.subtotal = item.productPrice * quantity
      }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update quantity'
      throw err
    }
  }

  const removeFromCart = async (cartItemId: number) => {
    try {
      await apiService.removeFromCart(cartItemId)
      
      // Update local state
      items.value = items.value.filter(item => item.id !== cartItemId)
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to remove from cart'
      throw err
    }
  }

  const toggleSelection = async (cartItemId: number) => {
    try {
      await apiService.toggleCartItemSelection(cartItemId)
      
      // Update local state
      const item = items.value.find(item => item.id === cartItemId)
      if (item) {
        item.isSelected = !item.isSelected
      }
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to toggle selection'
      throw err
    }
  }

  return {
    // State
    items,
    summary,
    isLoading,
    error,
    
    // Getters
    itemCount,
    selectedItemCount,
    totalAmount,
    
    // Actions
    fetchCartItems,
    addToCart,
    updateQuantity,
    removeFromCart,
    toggleSelection
  }
})
```

---

## 🌐 API Integration

### API Service Implementation
```typescript
// src/services/api.ts
import axios, { AxiosInstance, AxiosResponse } from 'axios'

// API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

// Create axios instance
const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('auth_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor for error handling
api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('auth_token')
      localStorage.removeItem('user_data')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// API Service Class
class ApiService {
  // Authentication APIs
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/login', credentials)
    return response.data
  }

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/register', userData)
    return response.data
  }

  async verifyEmail(token: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/verify-email?token=${token}`)
    return response.data
  }

  // Product APIs
  async getProducts(filters?: ProductFilters): Promise<Product[]> {
    const params = new URLSearchParams()
    if (filters) {
      Object.entries(filters).forEach(([key, value]) => {
        if (value !== undefined && value !== null) {
          params.append(key, value.toString())
        }
      })
    }
    const response = await api.get<Product[]>(`/products?${params.toString()}`)
    return response.data
  }

  async getProduct(productId: number): Promise<Product> {
    const response = await api.get<Product>(`/products/${productId}`)
    return response.data
  }

  // Auction APIs
  async getLiveAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/live')
    return response.data
  }

  async placeBid(auctionId: number, bidData: BidRequest): Promise<Bid> {
    const response = await api.post<Bid>(`/auctions/${auctionId}/bids`, bidData)
    return response.data
  }

  // Cart APIs
  async getCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}`)
    return response.data
  }

  async addToCart(cartData: AddToCartRequest): Promise<CartItem> {
    const response = await api.post<CartItem>('/cart/add', cartData)
    return response.data
  }
}

// Export singleton instance
export const apiService = new ApiService()
export default api
```

### Type Definitions
```typescript
// API Response Types
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  phoneNumber?: string
  role: 'ADMIN' | 'CUSTOMER'
  isActive: boolean
  isEmailVerified: boolean
  createdAt?: string
}

export interface Product {
  id: number
  name: string
  description: string
  price: number
  originalPrice: number
  brand: string
  model: string
  imageUrls: string[]
  stockQuantity: number
  isActive: boolean
  isOnSale: boolean
  rating: number
  reviewCount: number
  sku: string
  categoryName: string
  createdAt: string
}

export interface Auction {
  id: number
  title: string
  description: string
  startingBid: number
  currentBid: number
  bidIncrement: number
  startTime: string
  endTime: string
  status: 'DRAFT' | 'SCHEDULED' | 'LIVE' | 'ENDED' | 'CANCELLED'
  bidCount: number
  watcherCount: number
  productName: string
  categoryName: string
}

export interface CartItem {
  id: number
  productId: number
  productName: string
  productPrice: number
  quantity: number
  isSelected: boolean
  subtotal: number
  availableStock: number
}
```

---

## 🎯 Component Implementation

### Home Page Component
```vue
<!-- src/views/HomeView.vue -->
<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Section -->
    <section class="relative bg-gradient-to-r from-blue-600 to-purple-700 text-white">
      <div class="container mx-auto px-4 py-20">
        <div class="max-w-4xl mx-auto text-center">
          <h1 class="text-5xl font-bold mb-6">
            Welcome to Autowarehouse
          </h1>
          <p class="text-xl mb-8 opacity-90">
            Your ultimate destination for computer hardware with live auctions
          </p>
          <div class="flex gap-4 justify-center">
            <router-link 
              to="/products" 
              class="bg-white text-blue-600 px-8 py-3 rounded-lg font-semibold hover:bg-gray-100 transition-colors"
            >
              Browse Products
            </router-link>
            <router-link 
              to="/auctions" 
              class="border-2 border-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-blue-600 transition-colors"
            >
              Live Auctions
            </router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- Statistics Section -->
    <section class="py-16 bg-white">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div class="text-center">
            <div class="text-3xl font-bold text-blue-600 mb-2">10,000+</div>
            <div class="text-gray-600">Products Available</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-green-600 mb-2">5,000+</div>
            <div class="text-gray-600">Happy Customers</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-purple-600 mb-2">500+</div>
            <div class="text-gray-600">Live Auctions</div>
          </div>
          <div class="text-center">
            <div class="text-3xl font-bold text-orange-600 mb-2">24/7</div>
            <div class="text-gray-600">Customer Support</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Categories Section -->
    <section class="py-16 bg-gray-50">
      <div class="container mx-auto px-4">
        <h2 class="text-3xl font-bold text-center mb-12">Shop by Category</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div 
            v-for="category in categories" 
            :key="category.id"
            class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow cursor-pointer"
            @click="navigateToCategory(category.slug)"
          >
            <div class="h-48 bg-gradient-to-br from-blue-500 to-purple-600"></div>
            <div class="p-6">
              <h3 class="text-xl font-semibold mb-2">{{ category.name }}</h3>
              <p class="text-gray-600 mb-4">{{ category.description }}</p>
              <div class="text-blue-600 font-medium">
                {{ category.productCount }} Products
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Featured Products -->
    <section class="py-16 bg-white">
      <div class="container mx-auto px-4">
        <h2 class="text-3xl font-bold text-center mb-12">Featured Products</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
          <ProductCard 
            v-for="product in featuredProducts" 
            :key="product.id"
            :product="product"
          />
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useProductsStore } from '@/stores/products'
import ProductCard from '@/components/ProductCard.vue'

const router = useRouter()
const productsStore = useProductsStore()

const categories = ref([
  { id: 1, name: 'Graphics Cards', slug: 'graphics-cards', description: 'High-performance GPUs', productCount: 150 },
  { id: 2, name: 'Processors', slug: 'processors', description: 'Latest CPUs', productCount: 80 },
  { id: 3, name: 'Motherboards', slug: 'motherboards', description: 'Quality mainboards', productCount: 120 },
  { id: 4, name: 'Memory', slug: 'memory', description: 'RAM modules', productCount: 200 },
  { id: 5, name: 'Storage', slug: 'storage', description: 'SSDs and HDDs', productCount: 180 },
  { id: 6, name: 'Cooling', slug: 'cooling', description: 'Cooling solutions', productCount: 90 }
])

const featuredProducts = ref([])

const navigateToCategory = (slug: string) => {
  router.push(`/products?category=${slug}`)
}

onMounted(async () => {
  try {
    featuredProducts.value = await productsStore.fetchFeaturedProducts(8)
  } catch (error) {
    console.error('Failed to load featured products:', error)
  }
})
</script>
```

### Product Listing Component
```vue
<!-- src/views/ProductsView.vue -->
<template>
  <div class="min-h-screen bg-gray-50">
    <div class="container mx-auto px-4 py-8">
      <!-- Breadcrumb -->
      <nav class="mb-8">
        <ol class="flex items-center space-x-2 text-sm">
          <li><router-link to="/" class="text-blue-600 hover:underline">Home</router-link></li>
          <li class="text-gray-500">/</li>
          <li class="text-gray-900">Products</li>
        </ol>
      </nav>

      <div class="flex gap-8">
        <!-- Sidebar Filters -->
        <aside class="w-64 flex-shrink-0">
          <div class="bg-white rounded-lg shadow-sm p-6">
            <h3 class="font-semibold mb-4">Filters</h3>
            
            <!-- Category Filter -->
            <div class="mb-6">
              <h4 class="font-medium mb-3">Category</h4>
              <div class="space-y-2">
                <label v-for="category in categories" :key="category.id" class="flex items-center">
                  <input 
                    type="checkbox" 
                    :value="category.id"
                    v-model="filters.categories"
                    class="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
                  >
                  <span class="ml-2 text-sm">{{ category.name }}</span>
                </label>
              </div>
            </div>

            <!-- Price Range -->
            <div class="mb-6">
              <h4 class="font-medium mb-3">Price Range</h4>
              <div class="space-
