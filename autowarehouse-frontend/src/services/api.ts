import axios, { AxiosInstance, AxiosResponse } from 'axios'

// API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api'

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
    // if (error.response?.status === 401) {
    //   // Token expired or invalid
    //   localStorage.removeItem('auth_token')
    //   localStorage.removeItem('user_data')
    //   window.location.href = '/login'
    // }
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
  address?: string
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

export interface UpdateProfileRequest {
  firstName: string
  lastName: string
  phoneNumber?: string
  address?: string
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
  status?: string
  sortBy?: string
  sortOrder?: string
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

export interface OrderDetail extends Order {
  billingAddress?: string
  paymentMethod?: string
  paymentReference?: string
  notes?: string
  shippedAt?: string
  deliveredAt?: string
  trackingNumber?: string
  items?: OrderItem[]
  statusHistory?: any[]
  user?: {
    id: number
    firstName: string
    lastName: string
    email: string
    phoneNumber?: string
  }
}

export interface OrderItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productPrice: number
  productImages?: string[]
  productBrand?: string
  quantity: number
  subtotal: number
}

export interface CheckoutOrderRequest {
  userId: number
  shippingAddress: string
  billingAddress?: string
  paymentMethod: string
}

// Category Types
export interface Category {
  id: number
  name: string
  description?: string
  slug: string
  parentId?: number
  parent?: Category
  children?: Category[]
  imageUrl?: string
  isActive: boolean
  sortOrder: number
  createdAt: string
  updatedAt?: string
}

export interface CreateCategoryRequest {
  name: string
  description?: string
  slug: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface UpdateCategoryRequest {
  name?: string
  description?: string
  slug?: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface CreateProductRequest {
  name: string
  description: string
  price: number
  originalPrice?: number
  stockQuantity: number
  brand: string
  model: string
  sku?: string
  specifications?: string
  features?: string
  imageUrls: string[]
  categoryId: number
}

export interface UpdateProductRequest extends CreateProductRequest {
  isActive?: boolean
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

// Saved Items Types
export interface SavedItem {
  id: number
  userId: number
  productId: number
  productName: string
  productBrand: string
  productSku: string
  productPrice: number
  originalPrice?: number
  productImages: string[]
  quantity: number
  subtotal: number
  savings?: number
  savedFromCart: boolean
  isProductActive: boolean
  availableStock: number
  createdAt: string
}

export interface SaveForLaterRequest {
  userId: number
  productId: number
  quantity: number
}

// Wishlist Types
export interface WishlistItem {
  id: number
  product: Product
  createdAt: string
}

export interface AddToWishlistRequest {
  userId: number
  productId: number
}

export interface RemoveFromWishlistRequest {
  userId: number
  productId: number
}

// Customer Service Types
export interface SupportTicket {
  id: number
  subject: string
  description: string
  category: 'GENERAL' | 'TECHNICAL' | 'BILLING' | 'PRODUCT' | 'ORDER'
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'
  status: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED'
  customerId: number
  customerName: string
  assignedAgentId?: number
  assignedAgentName?: string
  createdAt: string
  updatedAt?: string
  resolvedAt?: string
}

export interface CreateTicketRequest {
  subject: string
  description: string
  category: 'GENERAL' | 'TECHNICAL' | 'BILLING' | 'PRODUCT' | 'ORDER'
  priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'
}

export interface UpdateTicketRequest {
  subject?: string
  description?: string
  category?: 'GENERAL' | 'TECHNICAL' | 'BILLING' | 'PRODUCT' | 'ORDER'
  priority?: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'
  status?: 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED'
  assignedAgentId?: number
}

export interface ChatMessage {
  id: number
  ticketId: number
  message: string
  senderType: 'CUSTOMER' | 'AGENT'
  senderId: number
  senderName: string
  timestamp: string
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

// Notification Types
export interface NotificationResponse {
  id: number
  title: string
  message: string
  type: string
  isRead: boolean
  referenceId?: number
  referenceType?: string
  iconClass?: string
  priority: string
  createdAt: string
  user?: {
    id: number
    email: string
    fullName: string
  }
}

export interface NotificationListResponse {
  notifications: NotificationResponse[]
  totalCount: number
  currentPage: number
  pageSize: number
  totalPages: number
}

export interface NotificationStatsResponse {
  totalCount: number
  unreadCount: number
  readCount: number
}

// Review Types
export interface CreateReviewRequest {
  productId: number
  orderId: number
  rating: number
  title: string
  comment: string
  imageUrls?: string[]
  videoUrls?: string[]
}

export interface ReviewResponse {
  id: number
  productId: number
  productName: string
  userId: number
  userName: string
  rating: number
  title: string
  comment: string
  isVerifiedPurchase: boolean
  helpfulCount: number
  isApproved: boolean
  imageUrls: string[]
  videoUrls: string[]
  createdAt: string
  updatedAt: string
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

  async updateUserProfile(userId: number, profileData: UpdateProfileRequest): Promise<User> {
    const response = await api.put<User>(`/users/profile/${userId}`, profileData)
    return response.data
  }

  async changePassword(currentPassword: string, newPassword: string): Promise<{ message: string }> {
    const response = await api.post<{ message: string }>('/users/change-password', {
      currentPassword,
      newPassword
    })
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

  // Admin Product APIs
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

  async getAdminProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getActiveProductCount(): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>('/products/active/count')
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

  // Saved Items APIs
  async getSavedItems(userId: number): Promise<SavedItem[]> {
    const response = await api.get<SavedItem[]>(`/saved-items/user/${userId}`)
    return response.data
  }

  async getSavedItemCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/saved-items/user/${userId}/count`)
    return response.data
  }

  async saveForLater(savedData: SaveForLaterRequest): Promise<SavedItem> {
    const response = await api.post<SavedItem>('/saved-items/save', savedData)
    return response.data
  }

  async moveToSaved(cartItemId: number): Promise<SavedItem> {
    const response = await api.post<SavedItem>(`/saved-items/move-from-cart/${cartItemId}`)
    return response.data
  }

  async moveToCart(savedItemId: number): Promise<CartItem> {
    const response = await api.post<CartItem>(`/saved-items/${savedItemId}/move-to-cart`)
    return response.data
  }

  async moveAllSavedToCart(userId: number): Promise<CartItem[]> {
    const response = await api.post<CartItem[]>(`/saved-items/user/${userId}/move-all-to-cart`)
    return response.data
  }

  async updateSavedItemQuantity(savedItemId: number, quantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/saved-items/${savedItemId}/quantity`, { quantity })
    return response.data
  }

  async removeSavedItem(savedItemId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/saved-items/${savedItemId}`)
    return response.data
  }

  async clearSavedItems(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/saved-items/user/${userId}/clear`)
    return response.data
  }

  async checkProductSaved(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/saved-items/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  // Wishlist APIs
  async getWishlistItems(userId: number): Promise<WishlistItem[]> {
    const response = await api.get<WishlistItem[]>(`/wishlist/user/${userId}`)
    return response.data
  }

  async addToWishlist(wishlistData: AddToWishlistRequest): Promise<WishlistItem> {
    const response = await api.post<WishlistItem>('/wishlist/add', wishlistData)
    return response.data
  }

  async removeFromWishlist(wishlistData: RemoveFromWishlistRequest): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>('/wishlist/remove', { data: wishlistData })
    return response.data
  }

  async getWishlistCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/wishlist/user/${userId}/count`)
    return response.data
  }

  async checkProductInWishlist(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/wishlist/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  async clearWishlist(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/wishlist/user/${userId}/clear`)
    return response.data
  }

// Order APIs
  async createOrder(userId: number): Promise<Order> {
    const response = await api.post<Order>('/orders/create', { userId })
    return response.data
  }

  async createOrderWithCheckout(checkoutData: CheckoutOrderRequest): Promise<OrderDetail> {
    const response = await api.post<OrderDetail>('/orders/checkout', checkoutData)
    return response.data
  }

  async getOrder(orderId: number): Promise<Order> {
    const response = await api.get<Order>(`/orders/${orderId}`)
    return response.data
  }

  async getOrderDetail(orderId: number): Promise<OrderDetail> {
    const response = await api.get<OrderDetail>(`/orders/${orderId}`)
    return response.data
  }

  async getUserOrders(userId: number): Promise<Order[]> {
    const response = await api.get<Order[]>(`/orders/user/${userId}`)
    return response.data
  }

  async getOrderStatusHistory(orderId: number): Promise<any[]> {
    const response = await api.get<any[]>(`/orders/${orderId}/status-history`)
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

  // Enhanced Admin Order APIs
  async searchOrders(searchParams: {
    query?: string
    status?: string
    paymentStatus?: string
    startDate?: string
    endDate?: string
    page?: number
    size?: number
  }): Promise<{
    orders: Order[]
    totalElements: number
    totalPages: number
    currentPage: number
    pageSize: number
  }> {
    const params = new URLSearchParams()
    Object.entries(searchParams).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        params.append(key, value.toString())
      }
    })
    const response = await api.get(`/orders/admin/search?${params.toString()}`)
    return response.data
  }

  async shipOrder(orderId: number, trackingNumber: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/ship`, { trackingNumber })
    return response.data
  }

  async deliverOrder(orderId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/deliver`)
    return response.data
  }

  async cancelOrder(orderId: number, reason?: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/${orderId}/cancel`, { reason })
    return response.data
  }

  async processRefund(orderId: number, notes?: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/process-refund`, { notes })
    return response.data
  }

  async bulkUpdateOrderStatus(orderIds: number[], status: string, notes?: string): Promise<{
    updatedCount: number
    message: string
  }> {
    const response = await api.put(`/orders/admin/bulk-status`, {
      orderIds,
      status,
      notes
    })
    return response.data
  }

  async getOrderAnalytics(params: {
    startDate?: string
    endDate?: string
    groupBy?: string
  }): Promise<{
    data: Array<{
      date: string
      orderCount: number
      revenue: number
      averageOrderValue: number
    }>
    summary: {
      totalOrders: number
      totalRevenue: number
      averageOrderValue: number
      growthRate: number
    }
  }> {
    const queryParams = new URLSearchParams()
    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        queryParams.append(key, value.toString())
      }
    })
    const response = await api.get(`/orders/admin/analytics?${queryParams.toString()}`)
    return response.data
  }

  async exportOrders(params: {
    status?: string
    startDate?: string
    endDate?: string
    format?: string
  }): Promise<string> {
    const queryParams = new URLSearchParams()
    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        queryParams.append(key, value.toString())
      }
    })
    const response = await api.get(`/orders/admin/export?${queryParams.toString()}`, {
      responseType: 'text'
    })
    return response.data
  }

  async getRecentOrders(limit: number = 10): Promise<Order[]> {
    const response = await api.get<Order[]>(`/orders/admin/recent?limit=${limit}`)
    return response.data
  }

  // Invoice APIs
  async generateInvoicePDF(orderId: number): Promise<Blob> {
    const response = await api.get(`/invoices/${orderId}/pdf`, {
      responseType: 'blob'
    })
    return response.data
  }

  async generateInvoiceHTML(orderId: number): Promise<string> {
    const response = await api.get(`/invoices/${orderId}/html`, {
      responseType: 'text'
    })
    return response.data
  }

  async downloadInvoice(orderId: number): Promise<Blob> {
    const response = await api.get(`/invoices/${orderId}/download`, {
      responseType: 'blob'
    })
    return response.data
  }

  // Category APIs
  async getCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories')
    return response.data
  }

  async getCategory(categoryId: number): Promise<Category> {
    const response = await api.get<Category>(`/categories/${categoryId}`)
    return response.data
  }

  async getCategoryBySlug(slug: string): Promise<Category> {
    const response = await api.get<Category>(`/categories/slug/${slug}`)
    return response.data
  }

  async getRootCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories/root')
    return response.data
  }

  async createCategory(categoryData: CreateCategoryRequest): Promise<Category> {
    const response = await api.post<Category>('/categories', categoryData)
    return response.data
  }

  async updateCategory(categoryId: number, categoryData: UpdateCategoryRequest): Promise<Category> {
    const response = await api.put<Category>(`/categories/${categoryId}`, categoryData)
    return response.data
  }

  async deleteCategory(categoryId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/categories/${categoryId}`)
    return response.data
  }

  // Customer Service APIs
  async getMyTickets(): Promise<SupportTicket[]> {
    const response = await api.get<SupportTicket[]>('/tickets/my-tickets')
    return response.data
  }

  async createTicket(ticketData: CreateTicketRequest): Promise<SupportTicket> {
    const response = await api.post<SupportTicket>('/tickets', ticketData)
    return response.data
  }

  async getTicket(ticketId: number): Promise<SupportTicket> {
    const response = await api.get<SupportTicket>(`/tickets/${ticketId}`)
    return response.data
  }

  async updateTicket(ticketId: number, ticketData: UpdateTicketRequest): Promise<SupportTicket> {
    const response = await api.put<SupportTicket>(`/tickets/${ticketId}`, ticketData)
    return response.data
  }

  async getTicketMessages(ticketId: number): Promise<ChatMessage[]> {
    const response = await api.get<ChatMessage[]>(`/chat/tickets/${ticketId}/messages`)
    return response.data
  }

  // Admin Customer Service APIs
  async getAllTickets(status?: string): Promise<SupportTicket[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<SupportTicket[]>(`/tickets${params}`)
    return response.data
  }

  async assignTicket(ticketId: number, agentId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/tickets/admin/${ticketId}/assign`, { agentId })
    return response.data
  }

  async updateTicketStatus(ticketId: number, status: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/tickets/admin/${ticketId}/status`, { status })
    return response.data
  }

  // Review APIs
  async createReview(reviewData: CreateReviewRequest): Promise<ReviewResponse> {
    const response = await api.post<ReviewResponse>('/reviews', reviewData)
    return response.data
  }

  async getProductReviews(productId: number, limit?: number): Promise<ReviewResponse[]> {
    const params = limit ? `?limit=${limit}` : ''
    const response = await api.get<ReviewResponse[]>(`/reviews/product/${productId}${params}`)
    return response.data
  }

  async getMyReviews(): Promise<ReviewResponse[]> {
    const response = await api.get<ReviewResponse[]>('/reviews/user/my-reviews')
    return response.data
  }

  async getOrderReviews(orderId: number): Promise<ReviewResponse[]> {
    const response = await api.get<ReviewResponse[]>(`/reviews/order/${orderId}`)
    return response.data
  }

  async getUserProductReview(productId: number): Promise<ReviewResponse | null> {
    try {
      const response = await api.get<ReviewResponse>(`/reviews/user/product/${productId}`)
      return response.data
    } catch (error: any) {
      // Return null if no review found (404)
      if (error.response?.status === 404) {
        return null
      }
      throw error
    }
  }

  async getReview(reviewId: number): Promise<ReviewResponse> {
    const response = await api.get<ReviewResponse>(`/reviews/${reviewId}`)
    return response.data
  }

  async deleteReview(reviewId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/reviews/${reviewId}`)
    return response.data
  }

  async markReviewHelpful(reviewId: number): Promise<ReviewResponse> {
    const response = await api.post<ReviewResponse>(`/reviews/${reviewId}/helpful`)
    return response.data
  }

  async getProductReviewStats(productId: number): Promise<{
    productId: number
    averageRating: number
    reviewCount: number
  }> {
    const response = await api.get(`/reviews/product/${productId}/stats`)
    return response.data
  }

  async canUserReviewProduct(productId: number): Promise<{
    productId: number
    userId: number
    canReview: boolean
  }> {
    const response = await api.get(`/reviews/product/${productId}/can-review`)
    return response.data
  }

  async getRecentReviews(limit: number = 10): Promise<ReviewResponse[]> {
    const response = await api.get<ReviewResponse[]>(`/reviews/recent?limit=${limit}`)
    return response.data
  }

  // Notification APIs
  async getNotifications(params?: {
    page?: number
    size?: number
    type?: string
    unreadOnly?: boolean
  }): Promise<NotificationListResponse> {
    // Check if user is authenticated
    const token = localStorage.getItem('auth_token')
    if (!token) {
      throw new Error('Authentication required')
    }

    const queryParams = new URLSearchParams()
    if (params) {
      Object.entries(params).forEach(([key, value]) => {
        if (value !== undefined && value !== null) {
          queryParams.append(key, value.toString())
        }
      })
    }
    
    try {
      const response = await api.get<NotificationListResponse>(`/notifications?${queryParams.toString()}`)
      return response.data
    } catch (error: any) {
      // if (error.response?.status === 401 || error.response?.status === 403) {
      //   // Clear invalid token
      //   localStorage.removeItem('auth_token')
      //   localStorage.removeItem('user_data')
      //   throw new Error('Please login to view notifications')
      // }
      throw error
    }
  }

  async getUnreadNotifications(): Promise<NotificationResponse[]> {
    const response = await api.get<NotificationResponse[]>('/notifications/unread')
    return response.data
  }

  async getUnreadNotificationCount(): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>('/notifications/count/unread')
    return response.data
  }

  async markNotificationAsRead(notificationId: number): Promise<NotificationResponse> {
    const response = await api.put<NotificationResponse>(`/notifications/${notificationId}/read`)
    return response.data
  }

  async markAllNotificationsAsRead(): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>('/notifications/read-all')
    return response.data
  }

  async deleteNotification(notificationId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/notifications/${notificationId}`)
    return response.data
  }

  async clearAllNotifications(): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>('/notifications/clear-all')
    return response.data
  }

  async getNotificationStats(): Promise<NotificationStatsResponse> {
    const response = await api.get<NotificationStatsResponse>('/notifications/stats')
    return response.data
  }

  // Admin Dashboard APIs
  async getDashboardStats(): Promise<DashboardStatsResponse> {
    const response = await api.get<DashboardStatsResponse>('/admin/dashboard/stats')
    return response.data
  }

  async getPendingOrders(): Promise<PendingOrdersResponse> {
    const response = await api.get<PendingOrdersResponse>('/admin/dashboard/pending-orders')
    return response.data
  }

  async getTopProducts(limit: number = 5): Promise<TopProductResponse> {
    const response = await api.get<TopProductResponse>(`/admin/dashboard/top-products?limit=${limit}`)
    return response.data
  }

  async getRecentSales(limit: number = 5): Promise<RecentSalesResponse> {
    const response = await api.get<RecentSalesResponse>(`/admin/dashboard/recent-sales?limit=${limit}`)
    return response.data
  }

  async getRecentTransactions(limit: number = 5): Promise<RecentTransactionsResponse> {
    const response = await api.get<RecentTransactionsResponse>(`/admin/dashboard/recent-transactions?limit=${limit}`)
    return response.data
  }
}

// Dashboard Types
export interface DashboardStatsResponse {
  totalSalesThisMonth: {
    amount: number
    percentage: number
    trend: string
  }
  totalOrdersThisMonth: {
    count: number
    percentage: number
    trend: string
  }
  productsSold: {
    count: number
    percentage: number
    trend: string
  }
  lowStockAlert: {
    count: number
    status: string
  }
}

export interface TopProductResponse {
  topProducts: Array<{
    rank: number
    productId: number
    name: string
    category: string
    soldCount: number
    totalRevenue: number
    imageUrl?: string
  }>
}

export interface RecentSalesResponse {
  recentSales: Array<{
    productName: string
    price: number
    soldAt: string
    timeAgo: string
    orderId: string
  }>
}

export interface RecentTransactionsResponse {
  recentTransactions: Array<{
    transactionId: string
    orderId: string
    customerName: string
    itemCount: number
    totalAmount: number
    status: string
    statusLabel: string
    createdAt: string
  }>
}

export interface PendingOrdersResponse {
  pendingOrdersCount: number
  orders: Array<{
    id: string
    customerName: string
    totalAmount: number
    status: string
    createdAt: string
  }>
}

// Export singleton instance
export const apiService = new ApiService()
export default api
