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
              <option value="processing">Diproses</option>
              <option value="shipped">Dikirim</option>
              <option value="completed">Selesai</option>
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
              <tr 
                v-for="order in filteredOrders" 
                :key="order.id"
                class="hover:bg-gray-50 transition-colors"
              >
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="font-medium text-gray-900">{{ order.orderNumber }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-gray-600">
                  {{ formatDate(order.date) }}
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
                  Rp {{ order.total.toLocaleString() }}
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
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()

// Reactive data
const searchQuery = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 5

const stats = ref({
  total: 24,
  pending: 3,
  shipped: 5,
  completed: 16
})

const orders = ref([
  {
    id: 1,
    orderNumber: '#ORD-2024-001',
    date: '2024-01-15',
    status: 'completed',
    total: 750000
  },
  {
    id: 2,
    orderNumber: '#ORD-2024-002',
    date: '2024-01-18',
    status: 'shipped',
    total: 425000
  },
  {
    id: 3,
    orderNumber: '#ORD-2024-003',
    date: '2024-01-20',
    status: 'processing',
    total: 1250000
  },
  {
    id: 4,
    orderNumber: '#ORD-2024-004',
    date: '2024-01-22',
    status: 'pending',
    total: 320000
  },
  {
    id: 5,
    orderNumber: '#ORD-2024-005',
    date: '2024-01-25',
    status: 'completed',
    total: 890000
  },
  {
    id: 6,
    orderNumber: '#ORD-2024-006',
    date: '2024-01-28',
    status: 'shipped',
    total: 675000
  },
  {
    id: 7,
    orderNumber: '#ORD-2024-007',
    date: '2024-02-01',
    status: 'processing',
    total: 540000
  },
  {
    id: 8,
    orderNumber: '#ORD-2024-008',
    date: '2024-02-03',
    status: 'pending',
    total: 280000
  }
])

// Computed properties
const filteredOrders = computed(() => {
  let filtered = orders.value

  // Filter by status
  if (statusFilter.value) {
    filtered = filtered.filter(order => order.status === statusFilter.value)
  }

  // Filter by search query
  if (searchQuery.value) {
    filtered = filtered.filter(order =>
      order.orderNumber.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  return filtered
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

const viewOrderDetail = (orderId) => {
  router.push(`/order/${orderId}`)
}

onMounted(() => {
  console.log('Order History page loaded')
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
