<template>
    <AdminNavbar/>
  <div class="min-h-screen bg-gray-50 p-6">
    <div class="max-w-7xl mx-auto">
      <!-- Stats Cards Row -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8 pt-5">
        <!-- Total Penjualan Bulan Ini -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <p class="text-xs text-gray-500 mb-1">Total Penjualan Bulan Ini</p>
              <p class="text-2xl font-bold text-gray-900">
                {{ formatCurrency(dashboardStats?.totalSalesThisMonth?.amount || 45200000) }}
              </p>
              <div class="flex items-center mt-2">
                <svg class="w-3 h-3 text-green-500 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 17l9.2-9.2M17 17V7H7"></path>
                </svg>
                <span class="text-xs text-green-500 font-medium">
                  +{{ formatPercentage(dashboardStats?.totalSalesThisMonth?.percentage || 12.5) }}%
                </span>
              </div>
            </div>
            <div class="text-green-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"></path>
              </svg>
            </div>
          </div>
        </div>

        <!-- Jumlah Pesanan Bulan Ini -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <p class="text-xs text-gray-500 mb-1">Jumlah Pesanan Bulan Ini</p>
              <p class="text-2xl font-bold text-gray-900">
                {{ formatNumber(dashboardStats?.totalOrdersThisMonth?.count || 1247) }}
              </p>
              <div class="flex items-center mt-2">
                <svg class="w-3 h-3 text-green-500 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 17l9.2-9.2M17 17V7H7"></path>
                </svg>
                <span class="text-xs text-green-500 font-medium">
                  +{{ formatPercentage(dashboardStats?.totalOrdersThisMonth?.percentage || 8.2) }}%
                </span>
              </div>
            </div>
            <div class="text-blue-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z"></path>
              </svg>
            </div>
          </div>
        </div>

        <!-- Produk Terjual -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <p class="text-xs text-gray-500 mb-1">Produk Terjual</p>
              <p class="text-2xl font-bold text-gray-900">
                {{ formatNumber(dashboardStats?.productsSold?.count || 3456) }}
              </p>
              <div class="flex items-center mt-2">
                <svg class="w-3 h-3 text-green-500 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 17l9.2-9.2M17 17V7H7"></path>
                </svg>
                <span class="text-xs text-green-500 font-medium">
                  +{{ formatPercentage(dashboardStats?.productsSold?.percentage || 15.1) }}%
                </span>
              </div>
            </div>
            <div class="text-purple-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"></path>
              </svg>
            </div>
          </div>
        </div>

        <!-- Stok Hampir Habis -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <p class="text-xs text-gray-500 mb-1">Stok Hampir Habis</p>
              <p class="text-2xl font-bold text-gray-900">
                {{ formatNumber(dashboardStats?.lowStockAlert?.count || 0) }}
              </p>
              <div class="flex items-center mt-2">
                <svg class="w-3 h-3 text-red-500 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z"></path>
                </svg>
                <span class="text-xs text-red-500 font-medium">Perlu Perhatian</span>
              </div>
            </div>
            <div class="text-red-500">
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z"></path>
              </svg>
            </div>
          </div>
        </div>
      </div>

      <!-- Action Cards Row -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
        <!-- Order Baru / Belum Diproses -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center mb-4">
            <div class="p-2 bg-orange-100 rounded-lg mr-3">
              <svg class="w-5 h-5 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <div>
              <h3 class="text-lg font-semibold text-gray-900">Order Baru / Belum Diproses</h3>
              <p class="text-sm text-gray-600">{{ pendingOrders?.pendingOrdersCount || 23 }} pesanan menunggu untuk diproses</p>
            </div>
          </div>
          <button 
            @click="navigateToOrders"
            class="bg-orange-500 hover:bg-orange-600 text-white px-4 py-2 rounded-lg font-medium transition-colors"
          >
            Lihat Pesanan
          </button>
        </div>

        <!-- Customer Live Chat -->
        <div class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex items-center mb-4">
            <div class="p-2 bg-blue-100 rounded-lg mr-3">
              <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-3.582 8-8 8a9.863 9.863 0 01-4.255-.949L5 20l1.395-3.72C7.512 15.042 9.201 14 12 14c4.418 0 8-3.582 8-8s-3.582-8-8-8-8 3.582-8 8c0 1.537.586 2.939 1.545 4.005"></path>
              </svg>
            </div>
            <div>
              <h3 class="text-lg font-semibold text-gray-900">Customer Live Chat</h3>
              <p class="text-sm text-gray-600">Reminder: jangan lupa untuk selalu buka chat dengan menangani keluhan atau pertanyaan customer</p>
            </div>
          </div>
          <button 
            @click="navigateToCustomerService"
            class="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg font-medium transition-colors"
          >
            Buka Chat
          </button>
        </div>
      </div>

      <!-- Bottom Section with 3 columns -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- 5 Produk Terlaris -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200">
          <div class="p-4 border-b border-gray-200">
            <h2 class="text-lg font-semibold text-gray-900">5 Produk Terlaris</h2>
            <p class="text-sm text-gray-600">Berdasarkan penjualan bulan ini</p>
            <button 
              @click="navigateToProducts"
              class="text-blue-600 hover:text-blue-700 font-medium text-sm mt-2"
            >
              Lihat Semua Produk →
            </button>
          </div>
          <div class="p-4">
            <div v-if="loadingTopProducts" class="flex justify-center py-4">
              <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
            </div>
            <div v-else-if="topProducts?.topProducts?.length" class="space-y-3">
              <div 
                v-for="product in topProducts.topProducts.slice(0, 5)" 
                :key="product.productId"
                class="flex items-center space-x-3"
              >
                <div class="flex-shrink-0">
                  <div 
                    :class="getRankBadgeClass(product.rank)"
                    class="w-6 h-6 rounded-full flex items-center justify-center text-xs font-bold text-white"
                  >
                    {{ product.rank }}
                  </div>
                </div>
                <div class="flex-1 min-w-0">
                  <p class="text-sm font-medium text-gray-900 truncate">{{ product.name }}</p>
                  <p class="text-xs text-gray-500">Kategori: {{ product.category }}</p>
                </div>
                <div class="text-right">
                  <p class="text-sm font-semibold text-gray-900">{{ formatNumber(product.soldCount) }} terjual</p>
                  <p class="text-xs text-green-600">{{ formatCurrency(product.totalRevenue) }}</p>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-4">
              <p class="text-gray-500 text-sm">Belum ada data produk terlaris</p>
            </div>
          </div>
        </div>

        <!-- Last 5 Produk Terjual -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200">
          <div class="p-4 border-b border-gray-200">
            <h2 class="text-lg font-semibold text-gray-900">Last 5 Produk Terjual</h2>
            <p class="text-sm text-gray-600">Produk yang baru saja terjual</p>
            <button class="text-blue-600 hover:text-blue-700 font-medium text-sm mt-2">
              Lihat Semua →
            </button>
          </div>
          <div class="p-4">
            <div v-if="loadingRecentSales" class="flex justify-center py-4">
              <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
            </div>
            <div v-else-if="recentSales?.recentSales?.length" class="space-y-4">
              <div 
                v-for="sale in recentSales.recentSales.slice(0, 5)" 
                :key="sale.orderId"
                class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <div class="w-2 h-2 bg-green-500 rounded-full"></div>
                  <div>
                    <p class="text-sm font-medium text-gray-900">{{ sale.productName }}</p>
                    <p class="text-xs text-gray-500">{{ sale.timeAgo }}</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-sm font-semibold text-gray-900">{{ formatCurrency(sale.price) }}</p>
                  <p class="text-xs text-gray-500">Terjual</p>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-4">
              <p class="text-gray-500 text-sm">Belum ada penjualan terbaru</p>
            </div>
          </div>
        </div>

        <!-- Last 5 Transaksi -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200">
          <div class="p-4 border-b border-gray-200">
            <h2 class="text-lg font-semibold text-gray-900">Last 5 Transaksi</h2>
            <p class="text-sm text-gray-600">Transaksi terbaru</p>
            <button class="text-blue-600 hover:text-blue-700 font-medium text-sm mt-2">
              Lihat Semua →
            </button>
          </div>
          <div class="p-4">
            <div v-if="loadingRecentTransactions" class="flex justify-center py-4">
              <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-blue-600"></div>
            </div>
            <div v-else-if="recentTransactions?.recentTransactions?.length" class="space-y-4">
              <div 
                v-for="transaction in recentTransactions.recentTransactions.slice(0, 5)" 
                :key="transaction.transactionId"
                class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <div class="w-8 h-8 bg-blue-100 rounded-full flex items-center justify-center">
                    <span class="text-xs font-medium text-blue-600">#</span>
                  </div>
                  <div>
                    <p class="text-sm font-medium text-gray-900">{{ transaction.transactionId }}</p>
                    <p class="text-xs text-gray-500">{{ transaction.customerName }} • {{ transaction.itemCount }} items</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-sm font-semibold text-gray-900">{{ formatCurrency(transaction.totalAmount) }}</p>
                  <p class="text-xs" :class="getStatusClass(transaction.status)">{{ transaction.statusLabel }}</p>
                </div>
              </div>
            </div>
            <div v-else class="text-center py-4">
              <p class="text-gray-500 text-sm">Belum ada transaksi terbaru</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type DashboardStatsResponse, type TopProductResponse, type PendingOrdersResponse, type RecentSalesResponse, type RecentTransactionsResponse } from '@/services/api'

const router = useRouter()

// Reactive data
const dashboardStats = ref<DashboardStatsResponse | null>(null)
const topProducts = ref<TopProductResponse | null>(null)
const pendingOrders = ref<PendingOrdersResponse | null>(null)
const recentSales = ref<RecentSalesResponse | null>(null)
const recentTransactions = ref<RecentTransactionsResponse | null>(null)
const loadingStats = ref(true)
const loadingTopProducts = ref(true)
const loadingPendingOrders = ref(true)
const loadingRecentSales = ref(true)
const loadingRecentTransactions = ref(true)

// Load dashboard data
const loadDashboardData = async () => {
  try {
    // Load all data in parallel
    const [statsResponse, topProductsResponse, pendingOrdersResponse, recentSalesResponse, recentTransactionsResponse] = await Promise.all([
      apiService.getDashboardStats().catch(err => {
        console.error('Error loading dashboard stats:', err)
        return null
      }),
      apiService.getTopProducts(10).catch(err => {
        console.error('Error loading top products:', err)
        return null
      }),
      apiService.getPendingOrders().catch(err => {
        console.error('Error loading pending orders:', err)
        return null
      }),
      apiService.getRecentSales(5).catch(err => {
        console.error('Error loading recent sales:', err)
        return null
      }),
      apiService.getRecentTransactions(5).catch(err => {
        console.error('Error loading recent transactions:', err)
        return null
      })
    ])

    dashboardStats.value = statsResponse
    topProducts.value = topProductsResponse
    pendingOrders.value = pendingOrdersResponse
    recentSales.value = recentSalesResponse
    recentTransactions.value = recentTransactionsResponse
  } catch (error) {
    console.error('Error loading dashboard data:', error)
  } finally {
    loadingStats.value = false
    loadingTopProducts.value = false
    loadingPendingOrders.value = false
    loadingRecentSales.value = false
    loadingRecentTransactions.value = false
  }
}

// Navigation methods
const navigateToOrders = () => {
  router.push('/admin/orders')
}

const navigateToCustomerService = () => {
  router.push('/admin/customer-service')
}

const navigateToProducts = () => {
  router.push('/admin/products')
}

// Utility methods
const formatCurrency = (amount: number): string => {
  return new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR',
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  }).format(amount)
}

const formatNumber = (num: number): string => {
  return new Intl.NumberFormat('id-ID').format(num)
}

const formatPercentage = (percentage: number): string => {
  return Math.abs(percentage).toFixed(1)
}

const getRankBadgeClass = (rank: number): string => {
  if (rank === 1) return 'bg-yellow-500'
  if (rank === 2) return 'bg-gray-400'
  if (rank === 3) return 'bg-orange-500'
  return 'bg-blue-500'
}

const getStatusClass = (status: string): string => {
  switch (status) {
    case 'DELIVERED':
      return 'text-green-600'
    case 'SHIPPED':
      return 'text-blue-600'
    case 'PENDING':
      return 'text-yellow-600'
    case 'CANCELLED':
      return 'text-red-600'
    default:
      return 'text-gray-600'
  }
}

// Lifecycle
onMounted(() => {
  loadDashboardData()
})
</script>

<style scoped>
/* Custom styles if needed */
</style>
