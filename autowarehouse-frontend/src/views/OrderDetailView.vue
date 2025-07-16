<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow-sm border-b border-gray-100">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-4">
            <div class="flex-shrink-0">
              <div class="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-shopping-bag text-white text-sm"></i>
              </div>
            </div>
            <h1 class="text-xl font-semibold text-gray-900">Autowarehouse</h1>
          </div>
          
          <nav class="hidden md:flex space-x-6">
            <router-link to="/" class="text-gray-600 hover:text-blue-600 transition-colors">Dashboard</router-link>
            <router-link to="/order-history" class="text-blue-600 font-medium">Riwayat Pesanan</router-link>
            <router-link to="/profile" class="text-gray-600 hover:text-blue-600 transition-colors">Profil</router-link>
          </nav>
          
          <div class="flex items-center space-x-4">
            <button class="p-2 text-gray-400 hover:text-gray-600 transition-colors">
              <i class="fa-regular fa-bell"></i>
            </button>
            <div class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center">
              <i class="fas fa-user text-white text-sm"></i>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Loading State -->
      <div v-if="isLoading" class="flex items-center justify-center py-12">
        <div class="text-center">
          <i class="fa-solid fa-spinner fa-spin text-blue-600 text-2xl mb-4"></i>
          <p class="text-gray-600">Memuat detail pesanan...</p>
        </div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-lg p-6 mb-8">
        <div class="flex items-center">
          <i class="fa-solid fa-exclamation-triangle text-red-600 mr-3"></i>
          <div>
            <h3 class="text-red-800 font-medium">Gagal memuat detail pesanan</h3>
            <p class="text-red-600 text-sm mt-1">{{ error }}</p>
            <button 
              @click="loadOrderDetail" 
              class="mt-2 text-blue-600 hover:text-blue-800 underline text-sm"
            >
              Coba lagi
            </button>
          </div>
        </div>
      </div>

      <!-- Order Not Found -->
      <div v-else-if="!order" class="bg-yellow-50 border border-yellow-200 rounded-lg p-6 mb-8">
        <div class="flex items-center">
          <i class="fa-solid fa-exclamation-triangle text-yellow-600 mr-3"></i>
          <div>
            <h3 class="text-yellow-800 font-medium">Pesanan tidak ditemukan</h3>
            <p class="text-yellow-600 text-sm mt-1">Pesanan dengan ID ini tidak ditemukan atau Anda tidak memiliki akses.</p>
            <router-link 
              to="/order-history" 
              class="mt-2 text-blue-600 hover:text-blue-800 underline text-sm"
            >
              Kembali ke Riwayat Pesanan
            </router-link>
          </div>
        </div>
      </div>

      <!-- Order Content -->
      <template v-else>
        <!-- Breadcrumb -->
        <section class="mb-6">
          <div class="flex items-center space-x-2 text-sm text-gray-600">
            <router-link to="/order-history" class="hover:text-blue-600 cursor-pointer">Riwayat Pesanan</router-link>
            <i class="fa-solid fa-chevron-right text-xs"></i>
            <span class="text-gray-900 font-medium">Detail Pesanan {{ order.orderNumber }}</span>
          </div>
        </section>

        <!-- Order Header -->
        <section class="bg-white rounded-xl shadow-sm p-6 mb-8">
          <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between">
            <div>
              <h1 class="text-2xl font-bold text-gray-900 mb-2">Pesanan {{ order.orderNumber }}</h1>
              <div class="flex items-center space-x-4 text-sm text-gray-600">
                <span>Tanggal: {{ formatDate(order.createdAt) }}</span>
                <span>•</span>
                <span>Total: Rp {{ order.totalAmount.toLocaleString() }}</span>
              </div>
            </div>
            <div class="mt-4 lg:mt-0">
              <span :class="getStatusClass(order.status)" class="inline-flex px-4 py-2 text-sm font-medium rounded-full">
                <i :class="getStatusIcon(order.status)" class="mr-2"></i>
                {{ getStatusText(order.status) }}
              </span>
            </div>
          </div>
        </section>
      </template>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Left Column -->
        <div class="lg:col-span-2 space-y-8">
          <!-- Order Items -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Produk yang Dibeli</h2>
            </div>
            <div class="p-6 space-y-4">
              <div 
                v-for="item in order.items" 
                :key="item.id"
                class="flex items-center space-x-4 p-4 border border-gray-100 rounded-lg"
              >
                <div class="w-16 h-16 bg-gray-200 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-box text-gray-400"></i>
                </div>
                <div class="flex-1">
                  <h3 class="font-medium text-gray-900">{{ item.productName }}</h3>
                  <p class="text-sm text-gray-600">SKU: {{ item.productSku }}</p>
                  <div class="flex items-center justify-between mt-2">
                    <span class="text-sm text-gray-600">Qty: {{ item.quantity }}</span>
                    <span class="font-semibold text-gray-900">Rp {{ item.subtotal.toLocaleString() }}</span>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <!-- Order Status Timeline -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Status Pengiriman</h2>
            </div>
            <div class="p-6">
              <OrderStatusTimeline 
                :status-history="order.statusHistory"
                :current-status="order.status"
                :tracking-number="order.trackingNumber"
              />
            </div>
          </section>
        </div>

        <!-- Right Column -->
        <div class="space-y-8">
          <!-- Payment Info -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Informasi Pembayaran</h2>
            </div>
            <div class="p-6 space-y-4" v-if="paymentInfo">
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Subtotal</span>
                <span class="font-medium">Rp {{ paymentInfo.subtotal.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Ongkos Kirim</span>
                <span class="font-medium">Rp {{ paymentInfo.shipping.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Pajak</span>
                <span class="font-medium">Rp {{ order.taxAmount?.toLocaleString() || 0 }}</span>
              </div>
              <hr class="border-gray-200">
              <div class="flex justify-between items-center text-lg font-semibold">
                <span>Total</span>
                <span>Rp {{ paymentInfo.total.toLocaleString() }}</span>
              </div>
              <div class="mt-4">
                <div class="flex items-center space-x-2 text-sm">
                  <i class="fa-solid fa-credit-card text-gray-400"></i>
                  <span class="text-gray-600">Metode: {{ paymentInfo.method }}</span>
                </div>
                <div class="flex items-center space-x-2 text-sm mt-1">
                  <i :class="order.paymentStatus === 'PAID' ? 'fa-solid fa-check-circle text-green-500' : 'fa-solid fa-clock text-yellow-500'"></i>
                  <span :class="order.paymentStatus === 'PAID' ? 'text-green-600' : 'text-yellow-600'">
                    {{ order.paymentStatus === 'PAID' ? 'Pembayaran Berhasil' : 'Menunggu Pembayaran' }}
                  </span>
                </div>
              </div>
            </div>
          </section>

          <!-- Shipping Info -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Alamat Pengiriman</h2>
            </div>
            <div class="p-6" v-if="shippingInfo">
              <div class="space-y-2">
                <p class="font-medium text-gray-900">{{ shippingInfo.name }}</p>
                <p class="text-gray-600">{{ shippingInfo.phone }}</p>
                <p class="text-gray-600">{{ shippingInfo.address }}</p>
              </div>
              <div class="mt-4 pt-4 border-t border-gray-100">
                <div class="flex items-center space-x-2 text-sm">
                  <i class="fa-solid fa-truck text-gray-400"></i>
                  <span class="text-gray-600">{{ shippingInfo.courier }}</span>
                </div>
                <div class="flex items-center space-x-2 text-sm mt-1" v-if="shippingInfo.trackingNumber">
                  <i class="fa-solid fa-barcode text-gray-400"></i>
                  <span class="text-gray-600">Resi: {{ shippingInfo.trackingNumber }}</span>
                </div>
              </div>
            </div>
          </section>

          <!-- Actions -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="p-6 space-y-3">
              <button 
                v-if="order.status === 'completed'"
                @click="writeReview"
                class="w-full bg-blue-600 text-white py-3 px-4 rounded-lg font-medium hover:bg-blue-700 transition-colors"
              >
                <i class="fa-solid fa-star mr-2"></i>
                Tulis Review
              </button>
              <button 
                @click="downloadInvoice"
                class="w-full border border-gray-200 text-gray-700 py-3 px-4 rounded-lg font-medium hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-download mr-2"></i>
                Download Invoice
              </button>
              <button 
                @click="contactCustomerService"
                class="w-full border border-gray-200 text-gray-700 py-3 px-4 rounded-lg font-medium hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-headset mr-2"></i>
                Hubungi Customer Service
              </button>
            </div>
          </section>
        </div>
      </div>
    </main>

    <!-- Footer -->
    <footer class="bg-white border-t border-gray-100 mt-12">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="text-center text-gray-600">
          <p>© 2024 Autowarehouse. Semua hak dilindungi.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useOrderStore } from '@/stores/order'
import { useAuthStore } from '@/stores/auth'
import OrderStatusTimeline from '@/components/OrderStatusTimeline.vue'

const route = useRoute()
const router = useRouter()
const orderStore = useOrderStore()
const authStore = useAuthStore()

// Reactive data
const isLoading = ref(false)
const error = ref(null)

// Computed properties
const order = computed(() => orderStore.currentOrder)

const shippingStatus = computed(() => {
  if (!order.value) return []
  
  const statuses = [
    {
      title: 'Pesanan Dikonfirmasi',
      date: order.value.createdAt ? formatDateTime(order.value.createdAt) : '',
      description: 'Pesanan Anda telah dikonfirmasi dan akan segera diproses',
      completed: ['CONFIRMED', 'SHIPPED', 'DELIVERED'].includes(order.value.status)
    },
    {
      title: 'Sedang Diproses',
      date: order.value.createdAt ? formatDateTime(order.value.createdAt) : '',
      description: 'Pesanan sedang disiapkan oleh penjual',
      completed: ['SHIPPED', 'DELIVERED'].includes(order.value.status)
    },
    {
      title: 'Dikirim',
      date: order.value.shippedAt ? formatDateTime(order.value.shippedAt) : '',
      description: `Paket telah dikirim${order.value.trackingNumber ? ` - Resi: ${order.value.trackingNumber}` : ''}`,
      completed: ['SHIPPED', 'DELIVERED'].includes(order.value.status)
    },
    {
      title: 'Pesanan Selesai',
      date: order.value.deliveredAt ? formatDateTime(order.value.deliveredAt) : '',
      description: 'Paket telah diterima oleh penerima',
      completed: order.value.status === 'DELIVERED'
    }
  ]
  
  return statuses
})

const paymentInfo = computed(() => {
  if (!order.value) return null
  
  return {
    subtotal: order.value.subtotal || 0,
    shipping: order.value.shippingCost || 0,
    discount: 0, // TODO: Add discount field to backend
    total: order.value.totalAmount || 0,
    method: order.value.paymentMethod || 'Transfer Bank'
  }
})

const shippingInfo = computed(() => {
  if (!order.value) return null
  
  return {
    name: authStore.user ? `${authStore.user.firstName} ${authStore.user.lastName}` : '',
    phone: authStore.user?.phoneNumber || '',
    address: order.value.shippingAddress || '',
    courier: 'JNE Regular (2-3 hari)', // TODO: Add courier info to backend
    trackingNumber: order.value.trackingNumber || ''
  }
})

// Methods
const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'long',
    year: 'numeric'
  })
}

const formatDateTime = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getStatusClass = (status) => {
  const classes = {
    'DELIVERED': 'bg-green-100 text-green-800',
    'SHIPPED': 'bg-blue-100 text-blue-800',
    'CONFIRMED': 'bg-purple-100 text-purple-800',
    'PENDING': 'bg-yellow-100 text-yellow-800',
    'CANCELLED': 'bg-red-100 text-red-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusIcon = (status) => {
  const icons = {
    'DELIVERED': 'fa-solid fa-check-circle',
    'SHIPPED': 'fa-solid fa-truck',
    'CONFIRMED': 'fa-solid fa-cog',
    'PENDING': 'fa-solid fa-clock',
    'CANCELLED': 'fa-solid fa-times-circle'
  }
  return icons[status] || 'fa-solid fa-circle'
}

const getStatusText = (status) => {
  const texts = {
    'DELIVERED': 'Selesai',
    'SHIPPED': 'Dikirim',
    'CONFIRMED': 'Dikonfirmasi',
    'PENDING': 'Pending',
    'CANCELLED': 'Dibatalkan'
  }
  return texts[status] || status
}

const loadOrderDetail = async () => {
  const orderId = parseInt(route.params.id)
  if (!orderId) {
    error.value = 'ID pesanan tidak valid'
    return
  }

  try {
    isLoading.value = true
    error.value = null
    await orderStore.fetchOrder(orderId)
  } catch (err) {
    error.value = err.response?.data?.message || 'Gagal memuat detail pesanan'
    console.error('Error loading order detail:', err)
  } finally {
    isLoading.value = false
  }
}

const writeReview = () => {
  // Navigate to review page or show review modal
  console.log('Write review for order:', order.value.orderNumber)
}

const downloadInvoice = () => {
  // Download invoice logic
  console.log('Download invoice for order:', order.value.orderNumber)
}

const contactCustomerService = () => {
  // Navigate to customer service or open chat
  router.push('/customer-service')
}

onMounted(() => {
  loadOrderDetail()
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
