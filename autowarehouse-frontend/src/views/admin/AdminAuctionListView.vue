<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Admin Sidebar -->
    <div class="flex">
      <!-- Sidebar -->
      <div class="w-64 bg-white shadow-lg min-h-screen">
        <div class="p-6">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-store text-white"></i>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-900">Autowarehouse</h1>
              <p class="text-sm text-gray-500">Admin Panel</p>
            </div>
          </div>
        </div>
        
        <nav class="mt-6">
          <div class="px-6 py-2">
            <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Main</p>
          </div>
          <router-link 
            to="/admin/dashboard" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
          >
            <i class="fa-solid fa-chart-line mr-3"></i>
            Dashboard
          </router-link>
          
          <div class="px-6 py-2 mt-6">
            <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Management</p>
          </div>
          <router-link 
            to="/admin/auctions" 
            class="flex items-center px-6 py-3 text-gray-700 bg-blue-50 border-r-4 border-blue-600"
          >
            <i class="fa-solid fa-gavel mr-3"></i>
            Auction Management
          </router-link>
          <router-link 
            to="/admin/categories" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
          >
            <i class="fa-solid fa-tags mr-3"></i>
            Category Management
          </router-link>
          <router-link 
            to="/admin/orders" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
          >
            <i class="fa-solid fa-shopping-cart mr-3"></i>
            Order Management
          </router-link>
          <router-link 
            to="/admin/customer-service" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
          >
            <i class="fa-solid fa-headset mr-3"></i>
            Customer Service
          </router-link>
          <router-link 
            to="/admin/notifications" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
          >
            <i class="fa-solid fa-bell mr-3"></i>
            Notifications
          </router-link>
        </nav>
        
        <div class="absolute bottom-0 w-64 p-6">
          <div class="flex items-center space-x-3">
            <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face" alt="Admin" class="w-10 h-10 rounded-full">
            <div>
              <p class="text-sm font-medium text-gray-900">Admin User</p>
              <p class="text-xs text-gray-500">admin@autowarehouse.com</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex-1">
        <!-- Top Header -->
        <header class="bg-white shadow-sm border-b">
          <div class="px-6 py-4">
            <div class="flex items-center justify-between">
              <div>
                <h1 class="text-2xl font-bold text-gray-900">Auction Management</h1>
                <p class="text-gray-600">Manage all auction listings and activities</p>
              </div>
              <div class="flex items-center space-x-4">
                <button 
                  @click="showCreateModal = true"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-plus mr-2"></i>
                  Create Auction
                </button>
                <button class="relative p-2 text-gray-400 hover:text-gray-600">
                  <i class="fa-solid fa-bell text-xl"></i>
                  <span class="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full"></span>
                </button>
                <div class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center">
                  <i class="fa-solid fa-user text-white text-sm"></i>
                </div>
              </div>
            </div>
          </div>
        </header>

        <!-- Content -->
        <main class="p-6">
          <!-- Stats Cards -->
          <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Auctions</p>
                  <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
                </div>
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-gavel text-blue-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Live Auctions</p>
                  <p class="text-2xl font-bold text-red-600">{{ stats.live }}</p>
                </div>
                <div class="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-circle text-red-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Upcoming</p>
                  <p class="text-2xl font-bold text-yellow-600">{{ stats.upcoming }}</p>
                </div>
                <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-clock text-yellow-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Completed</p>
                  <p class="text-2xl font-bold text-green-600">{{ stats.completed }}</p>
                </div>
                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-check-circle text-green-600"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Filters and Search -->
          <div class="bg-white rounded-xl shadow-sm p-6 mb-6">
            <div class="flex flex-col md:flex-row md:items-center md:justify-between space-y-4 md:space-y-0">
              <div class="flex items-center space-x-4">
                <div class="relative">
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Search auctions..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                </div>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">All Status</option>
                  <option value="upcoming">Upcoming</option>
                  <option value="live">Live</option>
                  <option value="ended">Ended</option>
                </select>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">All Categories</option>
                  <option value="electronics">Electronics</option>
                  <option value="automotive">Automotive</option>
                  <option value="gaming">Gaming</option>
                </select>
              </div>
              <div class="flex items-center space-x-2">
                <button class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                  <i class="fa-solid fa-download mr-2"></i>
                  Export
                </button>
                <button class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                  <i class="fa-solid fa-filter mr-2"></i>
                  More Filters
                </button>
              </div>
            </div>
          </div>

          <!-- Auctions Table -->
          <div class="bg-white rounded-xl shadow-sm overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300">
                    </th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Current Bid</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bids</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">End Time</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                  <tr 
                    v-for="auction in filteredAuctions" 
                    :key="auction.id"
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <img :src="auction.image" :alt="auction.title" class="w-12 h-12 rounded-lg object-cover mr-4">
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ auction.title }}</div>
                          <div class="text-sm text-gray-500">{{ auction.category }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(auction.status)"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        <i :class="getStatusIcon(auction.status)" class="mr-1"></i>
                        {{ getStatusText(auction.status) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      Rp {{ auction.currentBid.toLocaleString() }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ auction.totalBids }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ formatDateTime(auction.endTime) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex items-center space-x-2">
                        <button 
                          @click="viewAuction(auction.id)"
                          class="text-blue-600 hover:text-blue-900"
                        >
                          <i class="fa-solid fa-eye"></i>
                        </button>
                        <button 
                          @click="editAuction(auction.id)"
                          class="text-green-600 hover:text-green-900"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteAuction(auction.id)"
                          class="text-red-600 hover:text-red-900"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>

            <!-- Pagination -->
            <div class="px-6 py-4 bg-gray-50 border-t border-gray-200">
              <div class="flex items-center justify-between">
                <div class="text-sm text-gray-700">
                  Showing {{ (currentPage - 1) * itemsPerPage + 1 }} to {{ Math.min(currentPage * itemsPerPage, filteredAuctions.length) }} of {{ filteredAuctions.length }} results
                </div>
                <div class="flex items-center space-x-2">
                  <button 
                    @click="currentPage > 1 && (currentPage--)"
                    :disabled="currentPage === 1"
                    class="px-3 py-2 text-sm text-gray-500 hover:text-blue-600 border border-gray-300 rounded-lg hover:border-blue-600 transition-colors disabled:opacity-50"
                  >
                    Previous
                  </button>
                  <button 
                    v-for="page in totalPages" 
                    :key="page"
                    @click="currentPage = page"
                    :class="[
                      'px-3 py-2 text-sm rounded-lg transition-colors',
                      currentPage === page 
                        ? 'bg-blue-600 text-white' 
                        : 'text-gray-500 hover:text-blue-600 border border-gray-300 hover:border-blue-600'
                    ]"
                  >
                    {{ page }}
                  </button>
                  <button 
                    @click="currentPage < totalPages && (currentPage++)"
                    :disabled="currentPage === totalPages"
                    class="px-3 py-2 text-sm text-gray-500 hover:text-blue-600 border border-gray-300 rounded-lg hover:border-blue-600 transition-colors disabled:opacity-50"
                  >
                    Next
                  </button>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <!-- Create Auction Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-2xl mx-4">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-900">Create New Auction</h3>
          <button @click="showCreateModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="createAuction" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Product Title</label>
              <input 
                v-model="newAuction.title"
                type="text" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Category</label>
              <select 
                v-model="newAuction.category"
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
                <option value="">Select Category</option>
                <option value="electronics">Electronics</option>
                <option value="automotive">Automotive</option>
                <option value="gaming">Gaming</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Starting Bid (Rp)</label>
              <input 
                v-model="newAuction.startingBid"
                type="number" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">End Date & Time</label>
              <input 
                v-model="newAuction.endTime"
                type="datetime-local" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
            <textarea 
              v-model="newAuction.description"
              rows="3" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            ></textarea>
          </div>
          <div class="flex justify-end space-x-4">
            <button 
              type="button"
              @click="showCreateModal = false"
              class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Create Auction
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// State
const searchQuery = ref('')
const statusFilter = ref('')
const categoryFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showCreateModal = ref(false)

// Stats
const stats = ref({
  total: 156,
  live: 24,
  upcoming: 18,
  completed: 114
})

// New auction form
const newAuction = ref({
  title: '',
  category: '',
  startingBid: '',
  endTime: '',
  description: ''
})

// Sample auctions data
const auctions = ref([
  {
    id: 1,
    title: 'NVIDIA RTX 4090 Gaming GPU',
    category: 'Electronics',
    status: 'live',
    currentBid: 18500000,
    totalBids: 47,
    endTime: new Date(Date.now() + 2 * 60 * 60 * 1000), // 2 hours from now
    image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=100&h=100&fit=crop'
  },
  {
    id: 2,
    title: 'MacBook Pro 14" M3',
    category: 'Electronics',
    status: 'live',
    currentBid: 32000000,
    totalBids: 23,
    endTime: new Date(Date.now() + 5 * 60 * 60 * 1000), // 5 hours from now
    image: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=100&h=100&fit=crop'
  },
  {
    id: 3,
    title: 'BMW Engine Parts Set',
    category: 'Automotive',
    status: 'upcoming',
    currentBid: 5000000,
    totalBids: 0,
    endTime: new Date(Date.now() + 24 * 60 * 60 * 1000), // 1 day from now
    image: 'https://images.unsplash.com/photo-1486262715619-67b85e0b08d3?w=100&h=100&fit=crop'
  },
  {
    id: 4,
    title: 'Gaming Chair Pro',
    category: 'Gaming',
    status: 'ended',
    currentBid: 2500000,
    totalBids: 15,
    endTime: new Date(Date.now() - 2 * 60 * 60 * 1000), // 2 hours ago
    image: 'https://images.unsplash.com/photo-1586953208448-b95a79798f07?w=100&h=100&fit=crop'
  },
  {
    id: 5,
    title: 'iPhone 15 Pro Max',
    category: 'Electronics',
    status: 'live',
    currentBid: 20000000,
    totalBids: 34,
    endTime: new Date(Date.now() + 8 * 60 * 60 * 1000), // 8 hours from now
    image: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=100&h=100&fit=crop'
  }
])

// Computed
const filteredAuctions = computed(() => {
  let filtered = auctions.value

  if (searchQuery.value) {
    filtered = filtered.filter(auction =>
      auction.title.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(auction => auction.status === statusFilter.value)
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(auction => auction.category.toLowerCase() === categoryFilter.value)
  }

  return filtered
})

const totalPages = computed(() => {
  return Math.ceil(filteredAuctions.value.length / itemsPerPage)
})

// Methods
const getStatusClass = (status: string) => {
  const classes = {
    'live': 'bg-red-100 text-red-800',
    'upcoming': 'bg-yellow-100 text-yellow-800',
    'ended': 'bg-gray-100 text-gray-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusIcon = (status: string) => {
  const icons = {
    'live': 'fa-solid fa-circle',
    'upcoming': 'fa-solid fa-clock',
    'ended': 'fa-solid fa-check'
  }
  return icons[status] || 'fa-solid fa-circle'
}

const getStatusText = (status: string) => {
  const texts = {
    'live': 'Live',
    'upcoming': 'Upcoming',
    'ended': 'Ended'
  }
  return texts[status] || status
}

const formatDateTime = (date: Date) => {
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const viewAuction = (id: number) => {
  router.push(`/admin/auctions/${id}`)
}

const editAuction = (id: number) => {
  router.push(`/admin/auctions/${id}/edit`)
}

const deleteAuction = (id: number) => {
  if (confirm('Are you sure you want to delete this auction?')) {
    const index = auctions.value.findIndex(a => a.id === id)
    if (index > -1) {
      auctions.value.splice(index, 1)
    }
  }
}

const createAuction = () => {
  // Add new auction logic here
  console.log('Creating auction:', newAuction.value)
  showCreateModal.value = false
  // Reset form
  newAuction.value = {
    title: '',
    category: '',
    startingBid: '',
    endTime: '',
    description: ''
  }
}

onMounted(() => {
  console.log('Admin Auction List loaded')
})
</script>

<style scoped>
/* Custom styles */
</style>
