<template>
  <div class="min-h-screen bg-gray-50">
    <UserNavbar />

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

        <!-- Cancel Reason Alert (if order is cancelled and has cancel reason) -->
        <section v-if="order.status === 'CANCELLED' && order.notes" class="bg-red-50 border-l-4 border-red-400 rounded-lg p-6 mb-8">
          <div class="flex">
            <div class="flex-shrink-0">
              <i class="fa-solid fa-exclamation-triangle text-red-400 text-xl"></i>
            </div>
            <div class="ml-4">
              <h3 class="text-lg font-medium text-red-800 mb-2">Pesanan Dibatalkan</h3>
              <p class="text-red-700">
                <span class="font-medium">Alasan pembatalan:</span>
                {{ order.notes }}
              </p>
              <div class="mt-3 text-sm text-red-600">
                <p>Jika Anda memiliki pertanyaan mengenai pembatalan ini, silakan hubungi customer service kami.</p>
              </div>
            </div>
          </div>
        </section>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
          <!-- Left Column -->
          <div class="lg:col-span-2 space-y-8">
            <!-- Order Items -->
            <section class="bg-white rounded-xl shadow-sm">
              <div class="px-6 py-4 border-b border-gray-100">
                <h2 class="text-lg font-semibold text-gray-900">Produk yang Dibeli</h2>
              </div>
              <div class="p-6 space-y-4">
                <div v-if="order.items && order.items.length > 0">
                  <div 
                    v-for="item in order.items" 
                    :key="item.id"
                    class="flex items-center space-x-4 p-4 border border-gray-100 rounded-lg hover:bg-gray-50 cursor-pointer transition-colors"
                    @click="navigateToProduct(item.productId)"
                  >
                    <div class="w-16 h-16 bg-gray-200 rounded-lg flex items-center justify-center overflow-hidden">
                      <img 
                        v-if="item.productImages && item.productImages.length > 0" 
                        :src="item.productImages[0]" 
                        :alt="item.productName"
                        class="w-full h-full object-cover rounded-lg"
                        @error="handleImageError"
                      />
                      <i v-else class="fa-solid fa-box text-gray-400"></i>
                    </div>
                    <div class="flex-1">
                      <h3 class="font-medium text-gray-900 hover:text-blue-600">{{ item.productName }}</h3>
                      <p class="text-sm text-gray-600">SKU: {{ item.productSku }}</p>
                      <div class="flex items-center justify-between mt-2">
                        <span class="text-sm text-gray-600">Qty: {{ item.quantity }}</span>
                        <span class="font-semibold text-gray-900">Rp {{ item.subtotal.toLocaleString() }}</span>
                      </div>
                    </div>
                    <div class="flex-shrink-0">
                      <i class="fa-solid fa-chevron-right text-gray-400"></i>
                    </div>
                  </div>
                </div>
                <div v-else class="text-center py-8 text-gray-500">
                  <i class="fa-solid fa-box-open text-3xl mb-2"></i>
                  <p>Detail produk tidak tersedia</p>
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
                <!-- <div class="flex items-center space-x-2 text-sm mt-1">
                  <i :class="order.paymentStatus === 'PAID' ? 'fa-solid fa-check-circle text-green-500' : 'fa-solid fa-clock text-yellow-500'"></i>
                  <span :class="order.paymentStatus === 'PAID' ? 'text-green-600' : 'text-yellow-600'">
                    {{ order.paymentStatus === 'PAID' ? 'Pembayaran Berhasil' : 'Menunggu Pembayaran' }}
                  </span>
                </div> -->
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
      </template>
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
import UserNavbar from '@/components/UserNavbar.vue'
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

const navigateToProduct = (productId) => {
  if (productId) {
    router.push(`/product/${productId}`)
  }
}

const handleImageError = (event) => {
  // Hide the broken image and show the fallback icon
  event.target.style.display = 'none'
  event.target.nextElementSibling?.classList.remove('hidden')
}

const downloadInvoice = async () => {
  if (!order.value) return
  
  try {
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
    doc.text(`Invoice #: ${order.value.orderNumber}`, 120, 45)
    doc.text(`Date: ${formatDate(order.value.createdAt)}`, 120, 52)
    doc.text(`Status: ${getStatusText(order.value.status)}`, 120, 59)
    
    // Customer info
    doc.setFontSize(14)
    doc.text('Bill To:', 20, 80)
    doc.setFontSize(12)
    const customerName = shippingInfo.value?.name || 'Customer'
    doc.text(customerName, 20, 90)
    if (shippingInfo.value?.phone) {
      doc.text(shippingInfo.value.phone, 20, 97)
    }
    if (shippingInfo.value?.address) {
      const addressLines = doc.splitTextToSize(shippingInfo.value.address, 80)
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
    if (order.value.items && order.value.items.length > 0) {
      order.value.items.forEach((item) => {
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
    doc.text(`Rp ${order.value.subtotal.toLocaleString()}`, 170, yPos)
    yPos += 8
    
    doc.text('Shipping:', 120, yPos)
    doc.text(`Rp ${order.value.shippingCost.toLocaleString()}`, 170, yPos)
    yPos += 8
    
    doc.text('Tax:', 120, yPos)
    doc.text(`Rp ${(order.value.taxAmount || 0).toLocaleString()}`, 170, yPos)
    yPos += 8
    
    // Total line
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('TOTAL:', 120, yPos)
    doc.text(`Rp ${order.value.totalAmount.toLocaleString()}`, 170, yPos)
    
    // Payment info
    yPos += 20
    doc.setFontSize(10)
    doc.setTextColor(100, 100, 100)
    doc.text(`Payment Method: ${order.value.paymentMethod || 'Bank Transfer'}`, 20, yPos)
    doc.text(`Payment Status: ${order.value.paymentStatus === 'PAID' ? 'Paid' : 'Pending'}`, 20, yPos + 7)
    
    // Footer
    doc.setFontSize(8)
    doc.setTextColor(150, 150, 150)
    doc.text('Thank you for your business!', 20, 280)
    doc.text('This is a computer generated invoice.', 20, 285)
    
    // Save the PDF
    doc.save(`Invoice-${order.value.orderNumber}.pdf`)
    
  } catch (error) {
    console.error('Error generating PDF:', error)
    alert('Gagal mengunduh invoice. Silakan coba lagi.')
  }
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
