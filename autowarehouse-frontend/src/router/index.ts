import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const routes: RouteRecordRaw[] = [
  // Default route
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue')
  },
  
  // Auth routes
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
    path: '/cart',
    name: 'Cart',
    component: () => import('../views/CartView.vue')
  },
  {
    path: '/saved-items',
    name: 'SavedItems',
    component: () => import('../views/SavedItemsView.vue')
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: () => import('../views/CheckoutView.vue')
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: () => import('../views/WishlistView.vue')
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
    path: '/auction-won',
    name: 'AuctionWon',
    component: () => import('../views/AuctionWonView.vue')
  },
  {
    path: '/my-bids',
    name: 'MyBids',
    component: () => import('../views/MyBidsView.vue')
  },
  {
    path: '/bid-history',
    name: 'BidHistory',
    component: () => import('../views/BidHistoryView.vue')
  },
  {
    path: '/order-history',
    name: 'OrderHistory',
    component: () => import('../views/OrderHistoryView.vue')
  },
  {
    path: '/order/:id',
    name: 'OrderDetail',
    component: () => import('../views/OrderDetailView.vue')
  },
  {
    path: '/profile',
    name: 'UserProfile',
    component: () => import('../views/UserProfileView.vue')
  },
  {
    path: '/notifications',
    name: 'UserNotifications',
    component: () => import('../views/UserNotificationsView.vue')
  },
  {
    path: '/customer-service',
    name: 'CustomerService',
    component: () => import('../views/CustomerServiceView.vue')
  },

  // Admin routes
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    component: () => import('../views/admin/AdminDashboardView.vue')
  },
  {
    path: '/admin/products',
    name: 'AdminProducts',
    component: () => import('../views/admin/AdminProductView.vue')
  },
  {
    path: '/admin/auctions',
    name: 'AdminAuctions',
    component: () => import('../views/admin/AdminAuctionView.vue')
  },
  {
    path: '/admin/categories',
    name: 'AdminCategories',
    component: () => import('../views/admin/AdminCategoryView.vue')
  },
  {
    path: '/admin/customer-service',
    name: 'AdminCustomerService',
    component: () => import('../views/admin/AdminCustomerServiceView.vue')
  },
  {
    path: '/admin/notifications',
    name: 'AdminNotifications',
    component: () => import('../views/admin/AdminNotificationView.vue')
  },
  {
    path: '/admin/orders',
    name: 'AdminOrders',
    component: () => import('../views/admin/AdminOrderView.vue')
  },

  // UI Index for development
  {
    path: '/ui-index',
    name: 'UIIndex',
    component: () => import('../views/UIIndexView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guards
router.beforeEach((to, from, next) => {
  // Check localStorage directly for more reliable auth check
  const token = localStorage.getItem('auth_token')
  const userData = localStorage.getItem('user_data')
  
  let isAuthenticated = false
  let isAdmin = false
  
  if (token && userData) {
    try {
      const user = JSON.parse(userData)
      isAuthenticated = true
      isAdmin = user.role === 'ADMIN'
    } catch (e) {
      // Invalid stored data, clear it
      localStorage.removeItem('auth_token')
      localStorage.removeItem('user_data')
    }
  }
  
  // Routes that should redirect authenticated users away
  const authRoutes = ['Login', 'Register', 'ForgotPassword']
  
  // If user is authenticated and trying to access auth routes, redirect to home
  if (isAuthenticated && authRoutes.includes(to.name as string)) {
    next({ name: 'Home' })
    return
  }
  
  // Routes that require authentication
  const protectedRoutes = [
    'Cart', 'SavedItems', 'Checkout', 'Wishlist', 'MyBids', 'BidHistory', 
    'OrderHistory', 'OrderDetail', 'UserProfile', 'UserNotifications',
    'AuctionWon'
  ]
  
  // If user is not authenticated and trying to access protected routes, redirect to login
  if (!isAuthenticated && protectedRoutes.includes(to.name as string)) {
    next({ name: 'Login' })
    return
  }
  
  // Admin routes protection
  const adminRoutes = [
    'AdminDashboard', 'AdminProducts', 'AdminAuctions', 'AdminCategories',
    'AdminCustomerService', 'AdminNotifications', 'AdminOrders'
  ]
  
  // If user is not admin and trying to access admin routes, redirect to home
  if (adminRoutes.includes(to.name as string) && !isAdmin) {
    next({ name: 'Home' })
    return
  }
  
  next()
})

export default router
