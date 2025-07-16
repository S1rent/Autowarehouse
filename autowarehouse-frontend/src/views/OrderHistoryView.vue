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

      <!-- Orders Table -->
      <section class="bg-white rounded-xl shadow-sm overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-100">
          <h2 class="text-lg font-semibold text-gray-900">Daftar Pesanan</h2>
        </div>
        
        <div class="overflow-x-auto">
          <table class="w-full">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">No. Pesanan</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Tanggal</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-gray-100">
              <!-- Loading state -->
              <tr v-if="orderStore.isLoading">
                <td colspan="5" class="px-6 py-12 text-center">
                  <div class="flex items-center justify-center">
                    <i class="fa-solid fa-spinner fa-spin text-blue-600 mr-2"></i>
                    <span class="text-gray-600">Memuat pesanan...</span>
                  </div>
                </td>
              </tr>
              
              <!-- Error state -->
              <tr v-else-if="orderStore.error">
                <td colspan="5" class="px-6 py-12 text-center">
                  <div class="text-red-600">
                    <i class="fa-solid fa-exclamation-triangle mb-2"></i>
                    <p>{{ orderStore.error }}</p>
                    <button 
                      @click="loadOrders" 
                      class="mt-2 text-blue-600 hover:text-blue-800 underline"
                    >
                      Coba lagi
                    </button>
                  </div>
                </td>
              </tr>
              
              <!-- Empty state -->
              <tr v-else-if="paginatedOrders.length === 0">
                <td colspan="5" class="px-6 py-12 text-center">
                  <div class="text-gray-500">
                    <i class="fa-solid fa-shopping-cart text-4xl mb-4"></i>
                    <p class="text-lg font-medium">Belum ada pesanan</p>
                    <p class="text-sm">Pesanan Anda akan muncul di sini setelah checkout</p>
                  </div>
                </td>
              </tr>
              
              <!-- Orders data -->
              <tr 
                v-else
                v-for="order in paginatedOrders" 
                :key="order.id"
                class="hover:bg-gray-50 transition-colors"
              >
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="font-medium text-gray-900">{{ order.orderNumber }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-gray-600">
                  {{ formatDate(order.createdAt) }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <span :class="getStatusClass(order.status)" class="inline-flex px-3 py-1 text-xs font-medium rounded-full">
                      <i :class="getStatusIcon(order.status)" class="mr-1"></i>
                      {{ getStatusText(order.status) }}
                    </span>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap font-semibold text-gray-900">
                  Rp {{ order.totalAmount.toLocaleString() }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <button 
                    @click="viewOrderDetail(order.id)"
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700 transition-colors"
                  >
                    Lihat Detail
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
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

      <!-- Order Status Legend -->
      <section class="mt-8 bg-white rounded-xl shadow-sm p-6">
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
import { useRouter } from 'vue-router'
import { useOrderStore } from '@/stores/order'
import { useAuthStore } from '@/stores/auth'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()
const orderStore = useOrderStore()
const authStore = useAuthStore()

// Reactive data
const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 5

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

const loadOrders = async () => {
  if (!authStore.user?.id) {
    router.push('/login')
    return
  }
  
  try {
    await orderStore.fetchUserOrders()
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
