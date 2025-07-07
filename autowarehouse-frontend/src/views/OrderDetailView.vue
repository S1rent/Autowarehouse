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
              <span>Tanggal: {{ formatDate(order.date) }}</span>
              <span>•</span>
              <span>Total: Rp {{ order.total.toLocaleString() }}</span>
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
                <img 
                  :src="item.image" 
                  :alt="item.name"
                  class="w-16 h-16 rounded-lg object-cover"
                >
                <div class="flex-1">
                  <h3 class="font-medium text-gray-900">{{ item.name }}</h3>
                  <p class="text-sm text-gray-600">{{ item.description }}</p>
                  <div class="flex items-center justify-between mt-2">
                    <span class="text-sm text-gray-600">Qty: {{ item.quantity }}</span>
                    <span class="font-semibold text-gray-900">Rp {{ item.price.toLocaleString() }}</span>
                  </div>
                </div>
              </div>
            </div>
          </section>

          <!-- Shipping Status -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Status Pengiriman</h2>
            </div>
            <div class="p-6">
              <div class="relative">
                <div class="absolute left-4 top-8 bottom-0 w-0.5 bg-gray-200"></div>
                
                <div 
                  v-for="(status, index) in order.shippingStatus" 
                  :key="index"
                  :class="['relative flex items-start space-x-4', index < order.shippingStatus.length - 1 ? 'pb-8' : '']"
                >
                  <div :class="[
                    'w-8 h-8 rounded-full flex items-center justify-center',
                    status.completed ? 'bg-green-500' : 'bg-gray-300'
                  ]">
                    <i :class="[
                      'text-sm',
                      status.completed ? 'fa-solid fa-check text-white' : 'fa-solid fa-clock text-gray-500'
                    ]"></i>
                  </div>
                  <div class="flex-1">
                    <h3 class="font-medium text-gray-900">{{ status.title }}</h3>
                    <p class="text-sm text-gray-600">{{ status.date }}</p>
                    <p class="text-sm text-gray-500 mt-1">{{ status.description }}</p>
                  </div>
                </div>
              </div>
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
            <div class="p-6 space-y-4">
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Subtotal</span>
                <span class="font-medium">Rp {{ order.payment.subtotal.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Ongkos Kirim</span>
                <span class="font-medium">Rp {{ order.payment.shipping.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between items-center">
                <span class="text-gray-600">Diskon</span>
                <span class="font-medium text-green-600">-Rp {{ order.payment.discount.toLocaleString() }}</span>
              </div>
              <hr class="border-gray-200">
              <div class="flex justify-between items-center text-lg font-semibold">
                <span>Total</span>
                <span>Rp {{ order.payment.total.toLocaleString() }}</span>
              </div>
              <div class="mt-4">
                <div class="flex items-center space-x-2 text-sm">
                  <i class="fa-solid fa-credit-card text-gray-400"></i>
                  <span class="text-gray-600">Metode: {{ order.payment.method }}</span>
                </div>
                <div class="flex items-center space-x-2 text-sm mt-1">
                  <i class="fa-solid fa-check-circle text-green-500"></i>
                  <span class="text-green-600">Pembayaran Berhasil</span>
                </div>
              </div>
            </div>
          </section>

          <!-- Shipping Info -->
          <section class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-100">
              <h2 class="text-lg font-semibold text-gray-900">Alamat Pengiriman</h2>
            </div>
            <div class="p-6">
              <div class="space-y-2">
                <p class="font-medium text-gray-900">{{ order.shipping.name }}</p>
                <p class="text-gray-600">{{ order.shipping.phone }}</p>
                <p class="text-gray-600" v-html="order.shipping.address"></p>
              </div>
              <div class="mt-4 pt-4 border-t border-gray-100">
                <div class="flex items-center space-x-2 text-sm">
                  <i class="fa-solid fa-truck text-gray-400"></i>
                  <span class="text-gray-600">{{ order.shipping.courier }}</span>
                </div>
                <div class="flex items-center space-x-2 text-sm mt-1">
                  <i class="fa-solid fa-barcode text-gray-400"></i>
                  <span class="text-gray-600">Resi: {{ order.shipping.trackingNumber }}</span>
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
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// Sample order data
const order = ref({
  id: 1,
  orderNumber: '#ORD-2024-001',
  date: '2024-01-15',
  status: 'completed',
  total: 750000,
  items: [
    {
      id: 1,
      name: 'Laptop Gaming ASUS ROG',
      description: 'Intel Core i7, RTX 3060, 16GB RAM',
      image: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400',
      quantity: 1,
      price: 450000
    },
    {
      id: 2,
      name: 'Mouse Gaming Logitech G Pro',
      description: 'Wireless, RGB, 25K DPI',
      image: 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400',
      quantity: 1,
      price: 150000
    },
    {
      id: 3,
      name: 'Keyboard Mechanical RGB',
      description: 'Cherry MX Blue, Backlit',
      image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=400',
      quantity: 1,
      price: 150000
    }
  ],
  shippingStatus: [
    {
      title: 'Pesanan Dikonfirmasi',
      date: '15 Jan 2024, 10:30',
      description: 'Pesanan Anda telah dikonfirmasi dan akan segera diproses',
      completed: true
    },
    {
      title: 'Sedang Diproses',
      date: '15 Jan 2024, 14:20',
      description: 'Pesanan sedang disiapkan oleh penjual',
      completed: true
    },
    {
      title: 'Dikirim',
      date: '16 Jan 2024, 09:15',
      description: 'Paket telah dikirim dengan kurir JNE - Resi: JNE12345678',
      completed: true
    },
    {
      title: 'Pesanan Selesai',
      date: '18 Jan 2024, 16:45',
      description: 'Paket telah diterima oleh penerima',
      completed: true
    }
  ],
  payment: {
    subtotal: 750000,
    shipping: 15000,
    discount: 15000,
    total: 750000,
    method: 'Transfer Bank BCA'
  },
  shipping: {
    name: 'Sarah Johnson',
    phone: '+62 812-3456-7890',
    address: 'Jl. Sudirman No. 123, RT/RW 01/02<br>Kelurahan Menteng, Jakarta Pusat<br>DKI Jakarta 10310',
    courier: 'JNE Regular (2-3 hari)',
    trackingNumber: 'JNE12345678'
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

const getStatusClass = (status) => {
  const classes = {
    'completed': 'bg-green-100 text-green-800',
    'shipped': 'bg-blue-100 text-blue-800',
    'processing': 'bg-purple-100 text-purple-800',
    'pending': 'bg-yellow-100 text-yellow-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusIcon = (status) => {
  const icons = {
    'completed': 'fa-solid fa-check-circle',
    'shipped': 'fa-solid fa-truck',
    'processing': 'fa-solid fa-cog',
    'pending': 'fa-solid fa-clock'
  }
  return icons[status] || 'fa-solid fa-circle'
}

const getStatusText = (status) => {
  const texts = {
    'completed': 'Selesai',
    'shipped': 'Dikirim',
    'processing': 'Diproses',
    'pending': 'Pending'
  }
  return texts[status] || status
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
  // In real app, fetch order data based on route.params.id
  console.log('Order detail loaded for ID:', route.params.id)
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
