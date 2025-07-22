<template>
  <div class="min-h-screen bg-gray-50">
    <UserNavbar />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <section class="mb-8">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-2xl font-bold text-gray-900">Riwayat Pesanan</h1>
            <p class="text-gray-600 mt-1">Kelola dan pantau status pesanan Anda</p>
          </div>
          <div class="flex items-center space-x-3">
            <div class="relative">
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Cari pesanan..." 
                class="pl-10 pr-4 py-2 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent"
              >
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
            <select 
              v-model="statusFilter"
              class="px-4 py-2 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="">Semua Status</option>
              <option value="pending">Pending</option>
              <option value="confirmed">Dikonfirmasi</option>
              <option value="shipped">Dikirim</option>
              <option value="delivered">Selesai</option>
              <option value="cancelled">Dibatalkan</option>
            </select>
          </div>
        </div>
      </section>

      <!-- Order Statistics -->
      <section class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white p-6 rounded-xl shadow-sm">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600">Total Pesanan</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
            </div>
            <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-shopping-cart text-blue-600"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white p-6 rounded-xl shadow-sm">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600">Pending</p>
              <p class="text-2xl font-bold text-yellow-600">{{ stats.pending }}</p>
            </div>
            <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-clock text-yellow-600"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white p-6 rounded-xl shadow-sm">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600">Dikirim</p>
              <p class="text-2xl font-bold text-blue-600">{{ stats.shipped }}</p>
            </div>
            <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-truck text-blue-600"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white p-6 rounded-xl shadow-sm">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm text-gray-600">Selesai</p>
              <p class="text-2xl font-bold text-green-600">{{ stats.completed }}</p>
            </div>
            <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-check-circle text-green-600"></i>
            </div>
          </div>
        </div>
      </section>

      <!-- Order Status Legend -->
      <section class="my-8 bg-white rounded-xl shadow-sm p-6">
        <h3 class="text-lg font-semibold text-gray-900 mb-4">Status Pesanan</h3>
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div class="flex items-center space-x-3">
            <div class="w-3 h-3 bg-yellow-400 rounded-full"></div>
            <div>
              <p class="font-medium text-gray-900">Pending</p>
              <p class="text-sm text-gray-600">Pesanan menunggu konfirmasi</p>
            </div>
          </div>
          <div class="flex items-center space-x-3">
            <div class="w-3 h-3 bg-purple-400 rounded-full"></div>
            <div>
              <p class="font-medium text-gray-900">Diproses</p>
              <p class="text-sm text-gray-600">Pesanan sedang disiapkan</p>
            </div>
          </div>
          <div class="flex items-center space-x-3">
            <div class="w-3 h-3 bg-blue-400 rounded-full"></div>
            <div>
              <p class="font-medium text-gray-900">Dikirim</p>
              <p class="text-sm text-gray-600">Pesanan dalam perjalanan</p>
            </div>
          </div>
          <div class="flex items-center space-x-3">
            <div class="w-3 h-3 bg-green-400 rounded-full"></div>
            <div>
              <p class="font-medium text-gray-900">Selesai</p>
              <p class="text-sm text-gray-600">Pesanan telah diterima</p>
            </div>
          </div>
        </div>
      </section>

      <!-- Orders List - Card Layout for Better UX -->
      <section class="space-y-4">
        <!-- Loading state -->
        <div v-if="orderStore.isLoading" class="bg-white rounded-xl shadow-sm p-8 text-center">
          <div class="flex items-center justify-center">
            <i class="fa-solid fa-spinner fa-spin text-blue-600 mr-3 text-xl"></i>
            <span class="text-gray-600 text-lg">Memuat pesanan...</span>
          </div>
        </div>
        
        <!-- Error state -->
        <div v-else-if="orderStore.error" class="bg-white rounded-xl shadow-sm p-8 text-center">
          <div class="text-red-600">
            <i class="fa-solid fa-exclamation-triangle text-3xl mb-4"></i>
            <h3 class="text-lg font-semibold mb-2">Gagal memuat pesanan</h3>
            <p class="mb-4">{{ orderStore.error }}</p>
            <button 
              @click="loadOrders" 
              class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
            >
              <i class="fa-solid fa-refresh mr-2"></i>
              Coba Lagi
            </button>
          </div>
        </div>
        
        <!-- Empty state -->
        <div v-else-if="paginatedOrders.length === 0" class="bg-white rounded-xl shadow-sm p-12 text-center">
          <div class="text-gray-500">
            <i class="fa-solid fa-shopping-cart text-6xl mb-6 text-gray-300"></i>
            <h3 class="text-xl font-semibold mb-2">Belum ada pesanan</h3>
            <p class="text-gray-400 mb-6">Pesanan Anda akan muncul di sini setelah checkout</p>
            <router-link 
              to="/products" 
              class="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors inline-flex items-center"
            >
              <i class="fa-solid fa-shopping-bag mr-2"></i>
              Mulai Belanja
            </router-link>
          </div>
        </div>
        
        <!-- Orders Cards -->
        <div v-else class="space-y-4">
          <div 
            v-for="order in paginatedOrders" 
            :key="order.id"
            class="bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow-md transition-all duration-200 overflow-hidden"
          >
            <!-- Cancel Reason Alert (if order is cancelled and has cancel reason) -->
            <div v-if="order.status === 'CANCELLED' && order.cancelReason" class="bg-red-50 border-l-4 border-red-400 p-4 mb-0">
              <div class="flex">
                <div class="flex-shrink-0">
                  <i class="fa-solid fa-exclamation-triangle text-red-400"></i>
                </div>
                <div class="ml-3">
                  <p class="text-sm text-red-800">
                    <span class="font-medium">Pesanan Dibatalkan:</span>
                    {{ order.cancelReason }}
                  </p>
                </div>
              </div>
            </div>

            <div class="p-6">
              <!-- Order Header -->
              <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between mb-4">
                <div class="mb-3 sm:mb-0">
                  <div class="flex items-center space-x-3 mb-2">
                    <h3 class="text-lg font-semibold text-gray-900">{{ order.orderNumber }}</h3>
                    <span :class="getStatusClass(order.status)" class="inline-flex items-center px-3 py-1 text-xs font-medium rounded-full">
                      <i :class="getStatusIcon(order.status)" class="mr-1.5"></i>
                      {{ getStatusText(order.status) }}
                    </span>
                  </div>
                  <div class="flex items-center space-x-4 text-sm text-gray-500">
                    <span class="flex items-center">
                      <i class="fa-solid fa-calendar mr-1.5"></i>
                      {{ formatDate(order.createdAt) }}
                    </span>
                    <span class="flex items-center">
                      <i class="fa-solid fa-credit-card mr-1.5"></i>
                      {{ order.paymentStatus === 'PAID' ? 'Lunas' : 'Belum Bayar' }}
                    </span>
                  </div>
                </div>
                <div class="flex items-center space-x-3">
                  <div class="text-right">
                    <p class="text-sm text-gray-500">Total Pembayaran</p>
                    <p class="text-xl font-bold text-gray-900">Rp {{ order.totalAmount.toLocaleString('id-ID') }}</p>
                  </div>
                </div>
              </div>

              <!-- Order Items Preview (if available) -->
              

              <!-- Action Buttons -->
              <div class="flex flex-col sm:flex-row gap-3">
                <button 
                  @click="viewOrderDetail(order.id)"
                  class="flex-1 bg-blue-600 text-white px-4 py-2.5 rounded-lg text-sm font-medium hover:bg-blue-700 transition-colors flex items-center justify-center"
                >
                  <i class="fa-solid fa-eye mr-2"></i>
                  Lihat Detail
                </button>
                
                <!-- <button 
                  v-if="order.status === 'DELIVERED'"
                  @click="writeReview(order)"
                  class="flex-1 sm:flex-none border border-gray-200 text-gray-700 px-4 py-2.5 rounded-lg text-sm font-medium hover:bg-gray-50 transition-colors flex items-center justify-center"
                >
                  <i class="fa-solid fa-star mr-2"></i>
                  Tulis Review
                </button> -->
                
                <!-- <button 
                  v-if="['PENDING', 'CONFIRMED'].includes(order.status)"
                  @click="cancelOrder(order.id)"
                  class="flex-1 sm:flex-none border border-red-200 text-red-600 px-4 py-2.5 rounded-lg text-sm font-medium hover:bg-red-50 transition-colors flex items-center justify-center"
                >
                  <i class="fa-solid fa-times mr-2"></i>
                  Batalkan
                </button> -->
                
                <button 
                  @click="downloadInvoice(order.id)"
                  class="flex-1 sm:flex-none border border-gray-200 text-gray-700 px-4 py-2.5 rounded-lg text-sm font-medium hover:bg-gray-50 transition-colors flex items-center justify-center"
                >
                  <i class="fa-solid fa-download mr-2"></i>
                  Invoice
                </button>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Pagination -->
        <div class="px-6 py-4 bg-gray-50 border-t border-gray-100">
          <div class="flex items-center justify-between">
            <div class="text-sm text-gray-600">
              Menampilkan {{ (currentPage - 1) * itemsPerPage + 1 }}-{{ Math.min(currentPage * itemsPerPage, filteredOrders.length) }} dari {{ filteredOrders.length }} pesanan
            </div>
            <div class="flex items-center space-x-2">
              <button 
                @click="currentPage > 1 && (currentPage--)"
                :disabled="currentPage === 1"
                class="px-3 py-2 text-sm text-gray-500 hover:text-blue-600 border border-gray-200 rounded-lg hover:border-blue-600 transition-colors disabled:opacity-50"
              >
                <i class="fa-solid fa-chevron-left"></i>
              </button>
              <button 
                v-for="page in totalPages" 
                :key="page"
                @click="currentPage = page"
                :class="[
                  'px-3 py-2 text-sm rounded-lg transition-colors',
                  currentPage === page 
                    ? 'bg-blue-600 text-white' 
                    : 'text-gray-500 hover:text-blue-600 border border-gray-200 hover:border-blue-600'
                ]"
              >
                {{ page }}
              </button>
              <button 
                @click="currentPage < totalPages && (currentPage++)"
                :disabled="currentPage === totalPages"
                class="px-3 py-2 text-sm text-gray-500 hover:text-blue-600 border border-gray-200 rounded-lg hover:border-blue-600 transition-colors disabled:opacity-50"
              >
                <i class="fa-solid fa-chevron-right"></i>
              </button>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- Footer -->
    <Footer/>

    <!-- Review Modal -->
    <ReviewModal
      :is-visible="showReviewModal"
      :order-id="selectedOrderForReview?.id"
      :order-items="selectedOrderForReview?.items || []"
      @close="closeReviewModal"
      @success="onReviewSuccess"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useOrderStore } from '@/stores/order'
import { useAuthStore } from '@/stores/auth'
import { useNotifications } from '@/composables/useNotifications'
import { apiService } from '@/services/api'
import UserNavbar from '../components/UserNavbar.vue'
import ReviewModal from '../components/ReviewModal.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()
const orderStore = useOrderStore()
const authStore = useAuthStore()

// Reactive data
const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 5

// Review modal data
const showReviewModal = ref(false)
const selectedOrderForReview = ref(null)

// Reviews data
const orderReviews = ref({}) // Store reviews by order ID
const productReviews = ref({}) // Store reviews by product ID

// Computed properties
const stats = computed(() => {
  const orders = orderStore.userOrders
  return {
    total: orders.length,
    pending: orders.filter(order => order.status === 'PENDING').length,
    shipped: orders.filter(order => order.status === 'SHIPPED').length,
    completed: orders.filter(order => order.status === 'DELIVERED').length
  }
})

const filteredOrders = computed(() => {
  let filtered = orderStore.userOrders

  // Filter by status
  if (statusFilter.value) {
    const filterStatus = statusFilter.value.toUpperCase()
    filtered = filtered.filter(order => order.status === filterStatus)
  }

  // Filter by search query
  if (searchQuery.value) {
    filtered = filtered.filter(order =>
      order.orderNumber.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  return filtered
})

const paginatedOrders = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filteredOrders.value.slice(start, end)
})

const totalPages = computed(() => {
  return Math.ceil(filteredOrders.value.length / itemsPerPage)
})

// Methods
const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric'
  })
}

const getStatusClass = (status) => {
  const classes = {
    'DELIVERED': 'bg-green-100 text-green-800',
    'SHIPPED': 'bg-blue-100 text-blue-800',
    'CONFIRMED': 'bg-purple-100 text-purple-800',
    'PENDING': 'bg-yellow-100 text-yellow-800',
    'CANCELLED': 'bg-red-100 text-red-800',
    'REFUNDED': 'bg-gray-100 text-gray-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusIcon = (status) => {
  const icons = {
    'DELIVERED': 'fa-solid fa-check-circle',
    'SHIPPED': 'fa-solid fa-truck',
    'CONFIRMED': 'fa-solid fa-cog',
    'PENDING': 'fa-solid fa-clock',
    'CANCELLED': 'fa-solid fa-times-circle',
    'REFUNDED': 'fa-solid fa-undo'
  }
  return icons[status] || 'fa-solid fa-circle'
}

const getStatusText = (status) => {
  const texts = {
    'DELIVERED': 'Selesai',
    'SHIPPED': 'Dikirim',
    'CONFIRMED': 'Dikonfirmasi',
    'PENDING': 'Pending',
    'CANCELLED': 'Dibatalkan',
    'REFUNDED': 'Dikembalikan'
  }
  return texts[status] || status
}

const viewOrderDetail = (orderId) => {
  router.push(`/order/${orderId}`)
}

const writeReview = async (order) => {
  try {
    // Fetch complete order details if items are not available
    if (!order.items || order.items.length === 0) {
      await orderStore.fetchOrder(order.id)
      const orderDetail = orderStore.currentOrder
      if (orderDetail && orderDetail.items) {
        order.items = orderDetail.items
      }
    }
    
    selectedOrderForReview.value = order
    showReviewModal.value = true
  } catch (error) {
    console.error('Failed to load order details for review:', error)
    const { error: showError } = useNotifications()
    showError('Error', 'Gagal memuat detail pesanan untuk review')
  }
}

const closeReviewModal = () => {
  showReviewModal.value = false
  selectedOrderForReview.value = null
}

const onReviewSuccess = () => {
  const { success } = useNotifications()
  success('Review Berhasil', 'Terima kasih atas review Anda!')
  // Optionally refresh orders or update UI
}

const cancelOrder = async (orderId) => {
  const { success, error: showError } = useNotifications()
  
  try {
    // TODO: Implement cancel order API call
    console.log('Cancel order:', orderId)
    // await orderStore.cancelOrder(orderId)
    // await loadOrders() // Refresh orders
    success('Order Cancelled', 'Your order has been cancelled successfully')
  } catch (error) {
    console.error('Failed to cancel order:', error)
    showError('Cancel Failed', 'Failed to cancel order. Please try again.')
  }
}

const downloadInvoice = async (orderId) => {
  try {
    // First, fetch the order detail to get complete information
    const orderDetail = await orderStore.fetchOrder(orderId)
    const order = orderStore.currentOrder
    
    if (!order) {
      const { error: showError } = useNotifications()
      showError('Download Failed', 'Failed to get order data')
      return
    }

    // Import jsPDF dynamically
    const { jsPDF } = await import('jspdf')
    
    // Create new PDF document
    const doc = new jsPDF()
    
    // Set font
    doc.setFont('helvetica')
    
    // Header
    doc.setFontSize(20)
    doc.setTextColor(40, 40, 40)
    doc.text('INVOICE', 20, 30)
    
    // Company info
    doc.setFontSize(12)
    doc.setTextColor(100, 100, 100)
    doc.text('Autowarehouse', 20, 45)
    doc.text('Jakarta, Indonesia', 20, 52)
    doc.text('Email: info@autowarehouse.com', 20, 59)
    
    // Invoice details
    doc.setTextColor(40, 40, 40)
    doc.text(`Invoice #: ${order.orderNumber}`, 120, 45)
    doc.text(`Date: ${formatDate(order.createdAt)}`, 120, 52)
    doc.text(`Status: ${getStatusText(order.status)}`, 120, 59)
    
    // Customer info
    doc.setFontSize(14)
    doc.text('Bill To:', 20, 80)
    doc.setFontSize(12)
    const customerName = authStore.user ? `${authStore.user.firstName} ${authStore.user.lastName}` : 'Customer'
    doc.text(customerName, 20, 90)
    if (authStore.user?.phoneNumber) {
      doc.text(authStore.user.phoneNumber, 20, 97)
    }
    if (order.shippingAddress) {
      const addressLines = doc.splitTextToSize(order.shippingAddress, 80)
      doc.text(addressLines, 20, 104)
    }
    
    // Items table header
    let yPos = 130
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('Item', 20, yPos)
    doc.text('Qty', 120, yPos)
    doc.text('Price', 140, yPos)
    doc.text('Total', 170, yPos)
    
    // Draw line under header
    doc.line(20, yPos + 3, 190, yPos + 3)
    yPos += 15
    
    // Items
    if (order.items && order.items.length > 0) {
      order.items.forEach((item) => {
        doc.setFontSize(10)
        const itemName = doc.splitTextToSize(item.productName, 90)
        doc.text(itemName, 20, yPos)
        doc.text(item.quantity.toString(), 120, yPos)
        doc.text(`Rp ${item.productPrice.toLocaleString()}`, 140, yPos)
        doc.text(`Rp ${item.subtotal.toLocaleString()}`, 170, yPos)
        yPos += itemName.length * 5 + 5
      })
    }
    
    // Totals
    yPos += 10
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(11)
    doc.text('Subtotal:', 120, yPos)
    doc.text(`Rp ${order.subtotal.toLocaleString()}`, 170, yPos)
    yPos += 8
    
    doc.text('Shipping:', 120, yPos)
    doc.text(`Rp ${order.shippingCost.toLocaleString()}`, 170, yPos)
    yPos += 8
    
    doc.text('Tax:', 120, yPos)
    doc.text(`Rp ${(order.taxAmount || 0).toLocaleString()}`, 170, yPos)
    yPos += 8
    
    // Total line
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('TOTAL:', 120, yPos)
    doc.text(`Rp ${order.totalAmount.toLocaleString()}`, 170, yPos)
    
    // Payment info
    yPos += 20
    doc.setFontSize(10)
    doc.setTextColor(100, 100, 100)
    doc.text(`Payment Method: ${order.paymentMethod || 'Bank Transfer'}`, 20, yPos)
    doc.text(`Payment Status: ${order.paymentStatus === 'PAID' ? 'Paid' : 'Pending'}`, 20, yPos + 7)
    
    // Footer
    doc.setFontSize(8)
    doc.setTextColor(150, 150, 150)
    doc.text('Thank you for your business!', 20, 280)
    doc.text('This is a computer generated invoice.', 20, 285)
    
    // Save the PDF
    doc.save(`Invoice-${order.orderNumber}.pdf`)
    
  } catch (error) {
    console.error('Error generating PDF:', error)
    const { error: showError } = useNotifications()
    showError('Download Failed', 'Failed to download invoice. Please try again.')
  }
}

const loadProductReviews = async (orders) => {
  try {
    // Get all user reviews
    const userReviews = await apiService.getMyReviews()
    console.log('All user reviews:', userReviews)

    // Create a map of reviews by order ID and product ID combination
    userReviews.forEach(review => {
      if (review.orderId && review.productId) {
        // Store review by order-product combination
        const key = `${review.orderId}-${review.productId}`
        productReviews.value[key] = review
        console.log(`Found review for order ${review.orderId}, product ${review.productId}:`, review)
      }
    })
    
    console.log('Final productReviews:', productReviews.value)
  } catch (error) {
    console.error('Failed to load product reviews:', error)
  }
}

const loadOrders = async () => {
  if (!authStore.user?.id) {
    router.push('/login')
    return
  }
  
  try {
    await orderStore.fetchUserOrders()
    
    // Load order details for orders that don't have items
    const ordersToLoad = orderStore.userOrders.filter(order => !order.items || order.items.length === 0)
    for (const order of ordersToLoad) {
      try {
        await orderStore.fetchOrder(order.id)
        const orderDetail = orderStore.currentOrder
        if (orderDetail && orderDetail.items) {
          order.items = orderDetail.items
        }
      } catch (error) {
        console.error(`Failed to load details for order ${order.id}:`, error)
      }
    }
    
    // Load reviews for all products in orders
    // await loadProductReviews(orderStore.userOrders)
  } catch (error) {
    console.error('Failed to load orders:', error)
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
