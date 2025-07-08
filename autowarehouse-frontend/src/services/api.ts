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

// API Response Types
export interface ApiResponse<T = any> {
  data: T
  message?: string
}

export interface ErrorResponse {
  message: string
}

// User Types
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

export interface LoginRequest {
  email: string
  password: string
}

export interface AuthResponse {
  message: string
  accessToken?: string
  refreshToken?: string
  userId?: number
  email?: string
  firstName?: string
  lastName?: string
  role?: string
  isEmailVerified?: boolean
}

export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
}

export interface PasswordResetRequest {
  email: string
}

export interface PasswordResetConfirmRequest {
  token: string
  newPassword: string
}

// Product Types
export interface Product {
  id: number
  name: string
  description: string
  price: number
  originalPrice: number
  brand: string
  model: string
  specifications?: string
  features?: string
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

export interface ProductFilters {
  category?: number
  brand?: string
  minPrice?: number
  maxPrice?: number
  search?: string
  onSale?: boolean
  page?: number
  size?: number
}

// Auction Types
export interface Auction {
  id: number
  title: string
  description: string
  startingBid: number
  currentBid: number
  reservePrice?: number
  bidIncrement: number
  startTime: string
  endTime: string
  status: 'DRAFT' | 'SCHEDULED' | 'LIVE' | 'ENDED' | 'CANCELLED'
  bidCount: number
  watcherCount: number
  productName: string
  categoryName: string
  autoExtendMinutes?: number
  winnerName?: string
  winningBid?: number
  createdAt: string
}

export interface BidRequest {
  userId: number
  bidAmount: number
  isAutoBid: boolean
  maxAutoBid?: number
}

export interface Bid {
  id: number
  bidAmount: number
  isAutoBid: boolean
  isWinning: boolean
  bidderName: string
  createdAt: string
}

// Order Types
export interface Order {
  id: number
  orderNumber: string
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED'
  paymentStatus: 'PENDING' | 'PAID' | 'FAILED' | 'REFUNDED'
  subtotal: number
  taxAmount: number
  shippingCost: number
  totalAmount: number
  customerName: string
  shippingAddress: string
  createdAt: string
}

// Cart Types
export interface CartItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productBrand: string
  productPrice: number
  originalPrice: number
  productImages: string[]
  quantity: number
  isSelected: boolean
  subtotal: number
  savings: number
  availableStock: number
  isProductActive: boolean
}

export interface CartSummary {
  totalItems: number
  selectedItems: number
  totalQuantity: number
  selectedQuantity: number
  totalAmount: number
  selectedAmount: number
  totalSavings: number
  selectedSavings: number
}

export interface AddToCartRequest {
  userId: number
  productId: number
  quantity: number
}

// Statistics Types
export interface UserStats {
  totalUsers: number
  activeUsers: number
  totalCustomers: number
  totalAdmins: number
}

export interface ProductStats {
  totalProducts: number
  activeProducts: number
  lowStockProducts: number
  outOfStockProducts: number
}

export interface AuctionStats {
  totalAuctions: number
  liveAuctions: number
  upcomingAuctions: number
}

export interface OrderStats {
  totalOrders: number
  pendingOrders: number
  completedOrders: number
  totalRevenue: number
}

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

  async forgotPassword(request: PasswordResetRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/forgot-password', request)
    return response.data
  }

  async resetPassword(request: PasswordResetConfirmRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/reset-password', request)
    return response.data
  }

  async resendVerification(email: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/resend-verification?email=${email}`)
    return response.data
  }

  async getCurrentUser(): Promise<AuthResponse> {
    const response = await api.get<AuthResponse>('/auth/me')
    return response.data
  }

  // User Profile APIs
  async getUserProfile(userId: number): Promise<User> {
    const response = await api.get<User>(`/users/profile/${userId}`)
    return response.data
  }

  async updateUserProfile(userId: number, userData: Partial<User>): Promise<User> {
    const response = await api.put<User>(`/users/profile/${userId}`, userData)
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

  async getPopularProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/popular?limit=${limit}`)
    return response.data
  }

  async getFeaturedProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/featured?limit=${limit}`)
    return response.data
  }

  async getOnSaleProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/on-sale?limit=${limit}`)
    return response.data
  }

  // Auction APIs
  async getAuctions(status?: string): Promise<Auction[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Auction[]>(`/auctions${params}`)
    return response.data
  }

  async getLiveAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/live')
    return response.data
  }

  async getUpcomingAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/upcoming')
    return response.data
  }

  async getEndingSoonAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/ending-soon')
    return response.data
  }

  async getAuction(auctionId: number): Promise<Auction> {
    const response = await api.get<Auction>(`/auctions/${auctionId}`)
    return response.data
  }

  async placeBid(auctionId: number, bidData: BidRequest): Promise<Bid> {
    const response = await api.post<Bid>(`/auctions/${auctionId}/bids`, bidData)
    return response.data
  }

  async watchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.post<{ message: string }>(`/auctions/${auctionId}/watch`, { userId })
    return response.data
  }

  async unwatchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/auctions/${auctionId}/watch/${userId}`)
    return response.data
  }

  // Cart APIs
  async getCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}`)
    return response.data
  }

  async getSelectedCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}/selected`)
    return response.data
  }

  async getCartSummary(userId: number): Promise<CartSummary> {
    const response = await api.get<CartSummary>(`/cart/user/${userId}/summary`)
    return response.data
  }

  async getCartItemCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/cart/user/${userId}/count`)
    return response.data
  }

  async addToCart(cartData: AddToCartRequest): Promise<CartItem> {
    const response = await api.post<CartItem>('/cart/add', cartData)
    return response.data
  }

  async updateCartItemQuantity(cartItemId: number, quantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/quantity`, { quantity })
    return response.data
  }

  async toggleCartItemSelection(cartItemId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/toggle-selection`)
    return response.data
  }

  async selectAllCartItems(userId: number, selected: boolean): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/user/${userId}/select-all`, { selected })
    return response.data
  }

  async removeFromCart(cartItemId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/${cartItemId}`)
    return response.data
  }

  async clearCart(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/user/${userId}/clear`)
    return response.data
  }

  async checkProductInCart(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/cart/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  // Order APIs
  async createOrder(userId: number): Promise<Order> {
    const response = await api.post<Order>('/orders/create', { userId })
    return response.data
  }

  async getOrder(orderId: number): Promise<Order> {
    const response = await api.get<Order>(`/orders/${orderId}`)
    return response.data
  }

  async getUserOrders(userId: number): Promise<Order[]> {
    const response = await api.get<Order[]>(`/orders/user/${userId}`)
    return response.data
  }

  // Admin APIs
  async getCustomers(): Promise<User[]> {
    const response = await api.get<User[]>('/users/admin/customers')
    return response.data
  }

  async getUserStats(): Promise<UserStats> {
    const response = await api.get<UserStats>('/users/admin/stats')
    return response.data
  }

  async getProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getAuctionStats(): Promise<AuctionStats> {
    const response = await api.get<AuctionStats>('/auctions/admin/stats')
    return response.data
  }

  async getOrderStats(): Promise<OrderStats> {
    const response = await api.get<OrderStats>('/orders/admin/stats')
    return response.data
  }

  async getAllOrders(status?: string): Promise<Order[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Order[]>(`/orders/admin/all${params}`)
    return response.data
  }

  async updateOrderStatus(orderId: number, status: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/status`, { status })
    return response.data
  }
}

// Export singleton instance
export const apiService = new ApiService()
export default api
