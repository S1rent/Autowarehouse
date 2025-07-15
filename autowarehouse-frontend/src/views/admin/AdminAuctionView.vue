<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex justify-between items-center">
          <div>
            <h1 class="text-3xl font-bold text-gray-900">Auction Management</h1>
            <p class="text-gray-600 mt-1">Kelola sesi lelang dan pantau aktivitas bidding</p>
          </div>
          <div class="flex space-x-3">
            <button class="bg-white border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 flex items-center space-x-2">
              <i class="fa-solid fa-download text-sm"></i>
              <span>Export</span>
            </button>
            <button 
              @click="showCreateModal = true"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center space-x-2"
            >
              <i class="fa-solid fa-plus text-sm"></i>
              <span>Create Auction</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-lg p-6 shadow-sm border border-gray-200">
          <div class="flex items-center">
            <div class="p-2 bg-blue-100 rounded-lg">
              <i class="fa-solid fa-gavel text-blue-600 text-lg"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Active Auctions</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.active }}</p>
            </div>
          </div>
        </div>
        <div class="bg-white rounded-lg p-6 shadow-sm border border-gray-200">
          <div class="flex items-center">
            <div class="p-2 bg-green-100 rounded-lg">
              <i class="fa-solid fa-clock text-green-600 text-lg"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Scheduled</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.scheduled }}</p>
            </div>
          </div>
        </div>
        <div class="bg-white rounded-lg p-6 shadow-sm border border-gray-200">
          <div class="flex items-center">
            <div class="p-2 bg-yellow-100 rounded-lg">
              <i class="fa-solid fa-check-circle text-yellow-600 text-lg"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Completed</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.completed }}</p>
            </div>
          </div>
        </div>
        <div class="bg-white rounded-lg p-6 shadow-sm border border-gray-200">
          <div class="flex items-center">
            <div class="p-2 bg-purple-100 rounded-lg">
              <i class="fa-solid fa-dollar-sign text-purple-600 text-lg"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Revenue</p>
              <p class="text-2xl font-bold text-gray-900">Rp {{ formatPrice(stats.revenue) }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Filters and Search -->
      <div class="bg-white rounded-lg p-6 shadow-sm border border-gray-200 mb-6">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between space-y-4 md:space-y-0">
          <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
            <div class="relative">
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Search auctions..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
              >
            </div>
            <select 
              v-model="statusFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">All Status</option>
              <option value="active">Active</option>
              <option value="scheduled">Scheduled</option>
              <option value="completed">Completed</option>
              <option value="cancelled">Cancelled</option>
            </select>
            <select 
              v-model="categoryFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">All Categories</option>
              <option value="electronics">Electronics</option>
              <option value="art">Art</option>
              <option value="collectibles">Collectibles</option>
              <option value="jewelry">Jewelry</option>
            </select>
          </div>
          <div class="flex items-center space-x-2">
            <button class="p-2 text-gray-400 hover:text-gray-600">
              <i class="fa-solid fa-filter"></i>
            </button>
            <button @click="refreshData" class="p-2 text-gray-400 hover:text-gray-600">
              <i class="fa-solid fa-arrows-rotate"></i>
            </button>
          </div>
        </div>
      </div>

      <!-- Auction Table -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Auction Sessions</h3>
        </div>
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Start Time</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">End Time</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Current Bid</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bidders</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="auction in filteredAuctions" :key="auction.id" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <img :src="auction.product.image" :alt="auction.product.name" class="h-12 w-12 rounded-lg object-cover">
                    <div class="ml-4">
                      <div class="text-sm font-medium text-gray-900">{{ auction.product.name }}</div>
                      <div class="text-sm text-gray-500">ID: #{{ auction.id }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span :class="getStatusClass(auction.status)" class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium">
                    <span :class="getStatusDotClass(auction.status)" class="w-1.5 h-1.5 rounded-full mr-1.5"></span>
                    {{ getStatusText(auction.status) }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {{ formatDate(auction.startTime) }}<br>
                  <span class="text-gray-500">{{ formatTime(auction.startTime) }}</span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                  {{ formatDate(auction.endTime) }}<br>
                  <span class="text-gray-500">{{ formatTime(auction.endTime) }}</span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                  Rp {{ formatPrice(auction.currentBid) }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ auction.bidders }}</td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <div class="flex space-x-2">
                    <button 
                      v-if="auction.status === 'scheduled'"
                      @click="startAuction(auction.id)"
                      class="bg-blue-600 text-white px-3 py-1 rounded text-xs hover:bg-blue-700"
                    >
                      Start
                    </button>
                    <button 
                      @click="viewBiddingHistory(auction.id)"
                      class="text-blue-600 hover:text-blue-700"
                      title="View Bidding History"
                    >
                      <i class="fa-solid fa-history"></i>
                    </button>
                    <button 
                      @click="viewAuction(auction.id)"
                      class="text-gray-600 hover:text-gray-800"
                      title="View Details"
                    >
                      <i class="fa-solid fa-eye"></i>
                    </button>
                    <button 
                      v-if="auction.status === 'active'"
                      @click="stopAuction(auction.id)"
                      class="text-red-600 hover:text-red-800"
                      title="Stop Auction"
                    >
                      <i class="fa-solid fa-stop"></i>
                    </button>
                    <button 
                      v-if="auction.status === 'scheduled'"
                      @click="editAuction(auction.id)"
                      class="text-gray-600 hover:text-gray-800"
                      title="Edit"
                    >
                      <i class="fa-solid fa-edit"></i>
                    </button>
                    <button 
                      v-if="auction.status === 'scheduled'"
                      @click="deleteAuction(auction.id)"
                      class="text-red-600 hover:text-red-800"
                      title="Delete"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                    <button 
                      v-if="auction.status === 'completed'"
                      @click="downloadReport(auction.id)"
                      class="text-gray-600 hover:text-gray-800"
                      title="Download Report"
                    >
                      <i class="fa-solid fa-download"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
          <div class="text-sm text-gray-700">
            Showing {{ startIndex }} to {{ endIndex }} of {{ totalAuctions }} results
          </div>
          <div class="flex space-x-2">
            <button 
              @click="previousPage"
              :disabled="currentPage === 1"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              Previous
            </button>
            <button 
              v-for="page in visiblePages" 
              :key="page"
              @click="goToPage(page)"
              :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 text-gray-600 hover:bg-gray-50'"
              class="px-3 py-1 rounded text-sm"
            >
              {{ page }}
            </button>
            <button 
              @click="nextPage"
              :disabled="currentPage === totalPages"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              Next
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Bidding History Modal -->
    <div v-if="showBiddingModal" class="fixed inset-0 bg-gray-600 bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-4xl w-full max-h-96 overflow-y-auto">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Bidding History</h3>
          <button @click="showBiddingModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <div class="p-6">
          <div class="space-y-4">
            <div v-for="bid in selectedAuctionBids" :key="bid.id" class="flex justify-between items-center p-4 bg-gray-50 rounded-lg">
              <div class="flex items-center space-x-3">
                <img :src="bid.bidder.avatar" :alt="bid.bidder.name" class="h-8 w-8 rounded-full">
                <div>
                  <p class="font-medium text-gray-900">{{ bid.bidder.name }}</p>
                  <p class="text-sm text-gray-500">{{ formatDateTime(bid.timestamp) }}</p>
                </div>
              </div>
              <div class="text-right">
                <p class="font-bold text-lg text-gray-900">Rp {{ formatPrice(bid.amount) }}</p>
                <p v-if="bid.isHighest" class="text-sm text-green-600">Highest Bid</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Create Auction Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-gray-600 bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-2xl w-full max-h-96 overflow-y-auto">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Create New Auction</h3>
          <button @click="showCreateModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <div class="p-6">
          <form @submit.prevent="createAuction" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Product</label>
              <select v-model="newAuction.productId" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
                <option value="">Select Product</option>
                <option v-for="product in availableProducts" :key="product.id" :value="product.id">
                  {{ product.name }}
                </option>
              </select>
            </div>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Start Time</label>
                <input v-model="newAuction.startTime" type="datetime-local" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">End Time</label>
                <input v-model="newAuction.endTime" type="datetime-local" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
              </div>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Starting Bid (Rp)</label>
              <input v-model="newAuction.startingBid" type="number" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
            </div>
            <div class="flex justify-end space-x-3 pt-4">
              <button type="button" @click="showCreateModal = false" class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">
                Cancel
              </button>
              <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                Create Auction
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface Product {
  id: string
  name: string
  image: string
}

interface Bidder {
  id: string
  name: string
  avatar: string
}

interface Bid {
  id: string
  bidder: Bidder
  amount: number
  timestamp: string
  isHighest: boolean
}

interface Auction {
  id: string
  product: Product
  status: 'active' | 'scheduled' | 'completed' | 'cancelled'
  startTime: string
  endTime: string
  currentBid: number
  bidders: number
  category: string
}

const searchQuery = ref('')
const statusFilter = ref('')
const categoryFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showBiddingModal = ref(false)
const showCreateModal = ref(false)
const selectedAuctionBids = ref<Bid[]>([])

const stats = ref({
  active: 24,
  scheduled: 12,
  completed: 156,
  revenue: 245000000
})

const newAuction = ref({
  productId: '',
  startTime: '',
  endTime: '',
  startingBid: 0
})

const availableProducts = ref<Product[]>([
  { id: '1', name: 'iPhone 15 Pro', image: '' },
  { id: '2', name: 'MacBook Pro M3', image: '' },
  { id: '3', name: 'Samsung Galaxy S24', image: '' }
])

const auctions = ref<Auction[]>([
  {
    id: 'AUC001',
    product: {
      id: '1',
      name: 'Vintage Rolex Submariner',
      image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fcb55106aa-0ffdc5ff6af0c82fa322.png'
    },
    status: 'active',
    startTime: '2024-12-15T10:00:00',
    endTime: '2024-12-15T18:00:00',
    currentBid: 15420000,
    bidders: 23,
    category: 'jewelry'
  },
  {
    id: 'AUC002',
    product: {
      id: '2',
      name: 'Abstract Modern Art',
      image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/ce91227571-81a0fc642b74d0c29cf3.png'
    },
    status: 'scheduled',
    startTime: '2024-12-16T14:00:00',
    endTime: '2024-12-16T20:00:00',
    currentBid: 2500000,
    bidders: 0,
    category: 'art'
  },
  {
    id: 'AUC003',
    product: {
      id: '3',
      name: 'Diamond Necklace',
      image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/bcd852fa37-ddb00ee9183bf59763f5.png'
    },
    status: 'completed',
    startTime: '2024-12-14T13:00:00',
    endTime: '2024-12-14T19:00:00',
    currentBid: 8750000,
    bidders: 15,
    category: 'jewelry'
  }
])

const filteredAuctions = computed(() => {
  let filtered = auctions.value

  if (searchQuery.value) {
    filtered = filtered.filter(auction =>
      auction.product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      auction.id.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(auction => auction.status === statusFilter.value)
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(auction => auction.category === categoryFilter.value)
  }

  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalAuctions = computed(() => auctions.value.length)
const totalPages = computed(() => Math.ceil(auctions.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalAuctions.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric'
  })
}

const formatTime = (dateString: string) => {
  return new Date(dateString).toLocaleTimeString('id-ID', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatDateTime = (dateString: string) => {
  return new Date(dateString).toLocaleString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    scheduled: 'bg-yellow-100 text-yellow-800',
    completed: 'bg-gray-100 text-gray-800',
    cancelled: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusDotClass = (status: string) => {
  const classes = {
    active: 'bg-green-400',
    scheduled: 'bg-yellow-400',
    completed: 'bg-gray-400',
    cancelled: 'bg-red-400'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-400'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Active',
    scheduled: 'Scheduled',
    completed: 'Completed',
    cancelled: 'Cancelled'
  }
  return texts[status as keyof typeof texts] || status
}

const viewBiddingHistory = (auctionId: string) => {
  // Mock bidding history data
  selectedAuctionBids.value = [
    {
      id: '1',
      bidder: {
        id: '1',
        name: 'John Smith',
        avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-3.jpg'
      },
      amount: 15420000,
      timestamp: '2024-12-15T15:45:00',
      isHighest: true
    },
    {
      id: '2',
      bidder: {
        id: '2',
        name: 'Sarah Johnson',
        avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-5.jpg'
      },
      amount: 15000000,
      timestamp: '2024-12-15T15:30:00',
      isHighest: false
    }
  ]
  showBiddingModal.value = true
}

const startAuction = (auctionId: string) => {
  const auction = auctions.value.find(a => a.id === auctionId)
  if (auction) {
    auction.status = 'active'
    alert(`Auction ${auctionId} started successfully!`)
  }
}

const stopAuction = (auctionId: string) => {
  if (confirm('Are you sure you want to stop this auction?')) {
    const auction = auctions.value.find(a => a.id === auctionId)
    if (auction) {
      auction.status = 'completed'
      alert(`Auction ${auctionId} stopped successfully!`)
    }
  }
}

const editAuction = (auctionId: string) => {
  alert(`Edit auction ${auctionId}`)
}

const deleteAuction = (auctionId: string) => {
  if (confirm('Are you sure you want to delete this auction?')) {
    const index = auctions.value.findIndex(a => a.id === auctionId)
    if (index > -1) {
      auctions.value.splice(index, 1)
      alert(`Auction ${auctionId} deleted successfully!`)
    }
  }
}

const viewAuction = (auctionId: string) => {
  alert(`View auction details for ${auctionId}`)
}

const downloadReport = (auctionId: string) => {
  alert(`Download report for auction ${auctionId}`)
}

const createAuction = () => {
  // Add new auction logic here
  console.log('Creating auction:', newAuction.value)
  showCreateModal.value = false
  alert('Auction created successfully!')
}

const refreshData = () => {
  alert('Data refreshed!')
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

onMounted(() => {
  console.log('Admin Auction Management loaded')
})
</script>
