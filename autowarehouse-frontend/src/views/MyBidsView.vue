<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <header class="bg-white shadow-lg border-b border-gray-200">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-4">
            <i class="fas fa-gavel text-2xl text-blue-600"></i>
            <h1 class="text-xl font-bold text-gray-900">Autowarehouse Auction</h1>
          </div>
          <nav class="hidden md:flex space-x-8">
            <router-link to="/auctions" class="text-gray-600 hover:text-blue-600 font-medium cursor-pointer">Live Auctions</router-link>
            <span class="text-blue-600 hover:text-blue-700 font-medium cursor-pointer border-b-2 border-blue-600 pb-1">My Bids</span>
            <span class="text-gray-600 hover:text-blue-600 font-medium cursor-pointer">Categories</span>
          </nav>
          <div class="flex items-center space-x-4">
            <button class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
              <i class="fas fa-plus mr-2"></i>Create Auction
            </button>
            <img src="https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face" class="w-8 h-8 rounded-full">
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">My Bid History</h1>
        <p class="text-gray-600">Track all your auction bids and their current status</p>
      </div>

      <!-- Bid Filters -->
      <div class="bg-white rounded-xl shadow-lg p-6 mb-8">
        <div class="flex flex-wrap gap-4 items-center justify-between">
          <div class="flex flex-wrap gap-4">
            <button 
              v-for="filter in filterOptions" 
              :key="filter.key"
              @click="activeFilter = filter.key"
              :class="activeFilter === filter.key ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'"
              class="px-4 py-2 rounded-lg font-medium transition-colors"
            >
              {{ filter.label }}
            </button>
          </div>
          <div class="flex items-center space-x-4">
            <select 
              v-model="selectedTimeRange"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="30">Last 30 days</option>
              <option value="7">Last 7 days</option>
              <option value="90">Last 3 months</option>
              <option value="all">All time</option>
            </select>
            <div class="relative">
              <input 
                type="text" 
                v-model="searchQuery"
                placeholder="Search items..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              >
              <i class="fas fa-search absolute left-3 top-3 text-gray-400"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- Bid Summary -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-green-600 mb-2">{{ bidSummary.won }}</div>
          <div class="text-gray-600">Won Auctions</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-blue-600 mb-2">{{ bidSummary.active }}</div>
          <div class="text-gray-600">Active Bids</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-red-600 mb-2">{{ bidSummary.outbid }}</div>
          <div class="text-gray-600">Outbid</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-gray-600 mb-2">${{ bidSummary.totalSpent.toLocaleString() }}</div>
          <div class="text-gray-600">Total Spent</div>
        </div>
      </div>

      <!-- Bid History List -->
      <div class="space-y-4">
        <div 
          v-for="bid in filteredBids" 
          :key="bid.id"
          class="bg-white rounded-xl shadow-lg overflow-hidden"
        >
          <div class="p-6">
            <div class="flex items-center justify-between mb-4">
              <div 
                :class="getStatusClass(bid.status)"
                class="text-white px-3 py-1 rounded-full text-sm font-medium"
              >
                <i :class="getStatusIcon(bid.status)" class="mr-1"></i>
                {{ getStatusText(bid.status) }}
              </div>
              <span class="text-gray-500 text-sm">{{ getTimeText(bid) }}</span>
            </div>
            <div class="flex space-x-4">
              <img 
                :src="bid.item.image" 
                :alt="bid.item.title"
                class="w-20 h-20 object-cover rounded-lg"
              >
              <div class="flex-1">
                <h3 class="font-semibold text-gray-900 mb-1">{{ bid.item.title }}</h3>
                <p class="text-gray-600 text-sm mb-2">{{ bid.item.description }}</p>
                <div class="flex items-center justify-between">
                  <div>
                    <span class="text-sm text-gray-500">{{ getBidLabel(bid.status) }}:</span>
                    <div 
                      :class="getBidPriceClass(bid.status)"
                      class="text-xl font-bold"
                    >
                      ${{ bid.bidAmount.toLocaleString() }}
                    </div>
                  </div>
                  <div class="text-right">
                    <span class="text-sm text-gray-500">{{ getCurrentLabel(bid.status) }}:</span>
                    <div 
                      :class="getCurrentPriceClass(bid.status)"
                      class="text-lg font-semibold"
                    >
                      ${{ bid.currentPrice.toLocaleString() }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="mt-4 flex space-x-3">
              <button 
                v-for="action in getActions(bid.status)" 
                :key="action.key"
                @click="handleAction(action.key, bid)"
                :class="action.class"
                class="px-4 py-2 rounded-lg font-medium transition-colors"
              >
                <i :class="action.icon" class="mr-2"></i>
                {{ action.label }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="filteredBids.length === 0" class="text-center py-12">
        <i class="fas fa-gavel text-4xl text-gray-300 mb-4"></i>
        <h3 class="text-lg font-medium text-gray-900 mb-2">No bids found</h3>
        <p class="text-gray-500">Try adjusting your search or filter criteria</p>
      </div>

      <!-- Pagination -->
      <div v-if="filteredBids.length > 0" class="flex justify-center mt-8">
        <div class="flex space-x-2">
          <button 
            @click="previousPage"
            :disabled="currentPage === 1"
            class="px-3 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200 disabled:opacity-50"
          >
            <i class="fas fa-chevron-left"></i>
          </button>
          <button 
            v-for="page in totalPages" 
            :key="page"
            @click="currentPage = page"
            :class="currentPage === page ? 'px-4 py-2 bg-blue-600 text-white rounded-lg' : 'px-4 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200'"
          >
            {{ page }}
          </button>
          <button 
            @click="nextPage"
            :disabled="currentPage === totalPages"
            class="px-3 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200 disabled:opacity-50"
          >
            <i class="fas fa-chevron-right"></i>
          </button>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// State
const activeFilter = ref('all')
const selectedTimeRange = ref('30')
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 5

// Filter options
const filterOptions = [
  { key: 'all', label: 'All Bids' },
  { key: 'won', label: 'Won' },
  { key: 'active', label: 'Active' },
  { key: 'outbid', label: 'Outbid' },
  { key: 'expired', label: 'Expired' }
]

// Bid summary
const bidSummary = ref({
  won: 3,
  active: 7,
  outbid: 12,
  totalSpent: 47250
})

// Sample bid data
const bids = ref([
  {
    id: 1,
    status: 'won',
    item: {
      title: 'NVIDIA RTX 4090 Gaming GPU',
      description: 'Brand new, sealed in box with warranty',
      image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=200&h=200&fit=crop'
    },
    bidAmount: 22500,
    currentPrice: 22500,
    totalBids: 47,
    endTime: new Date(Date.now() - 2 * 60 * 60 * 1000) // 2 hours ago
  },
  {
    id: 2,
    status: 'leading',
    item: {
      title: 'Gaming Mechanical Keyboard RGB',
      description: 'Cherry MX switches, full RGB lighting',
      image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=200&h=200&fit=crop'
    },
    bidAmount: 320,
    currentPrice: 320,
    totalBids: 23,
    endTime: new Date(Date.now() + 2 * 60 * 60 * 1000) // 2 hours from now
  },
  {
    id: 3,
    status: 'outbid',
    item: {
      title: 'Gaming Laptop ASUS ROG',
      description: 'RTX 3070, 32GB RAM, 1TB SSD',
      image: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=200&h=200&fit=crop'
    },
    bidAmount: 1850,
    currentPrice: 1920,
    totalBids: 34,
    endTime: new Date(Date.now() + 24 * 60 * 60 * 1000) // 1 day from now
  },
  {
    id: 4,
    status: 'active',
    item: {
      title: 'Custom Gaming PC Build',
      description: 'Intel i9, RTX 4080, 64GB RAM',
      image: 'https://images.unsplash.com/photo-1587831990711-23ca6441447b?w=200&h=200&fit=crop'
    },
    bidAmount: 850,
    currentPrice: 920,
    totalBids: 15,
    endTime: new Date(Date.now() + 3 * 24 * 60 * 60 * 1000) // 3 days from now
  },
  {
    id: 5,
    status: 'expired',
    item: {
      title: 'Professional Audio Interface',
      description: 'Focusrite Scarlett 18i20, barely used',
      image: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=200&h=200&fit=crop'
    },
    bidAmount: 1200,
    currentPrice: 1450,
    totalBids: 28,
    endTime: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000) // 3 days ago
  }
])

// Computed properties
const filteredBids = computed(() => {
  let filtered = bids.value

  // Filter by status
  if (activeFilter.value !== 'all') {
    filtered = filtered.filter(bid => bid.status === activeFilter.value)
  }

  // Filter by search query
  if (searchQuery.value) {
    filtered = filtered.filter(bid => 
      bid.item.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      bid.item.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalPages = computed(() => Math.ceil(bids.value.length / itemsPerPage))

// Methods
const getStatusClass = (status: string) => {
  switch (status) {
    case 'won':
      return 'status-won'
    case 'leading':
    case 'active':
      return 'status-active'
    case 'outbid':
      return 'status-outbid'
    case 'expired':
      return 'status-expired'
    default:
      return 'bg-gray-500'
  }
}

const getStatusIcon = (status: string) => {
  switch (status) {
    case 'won':
      return 'fas fa-trophy'
    case 'leading':
    case 'active':
      return 'fas fa-clock'
    case 'outbid':
      return 'fas fa-exclamation-triangle'
    case 'expired':
      return 'fas fa-times'
    default:
      return 'fas fa-circle'
  }
}

const getStatusText = (status: string) => {
  switch (status) {
    case 'won':
      return 'Won'
    case 'leading':
      return 'Leading'
    case 'active':
      return 'Active'
    case 'outbid':
      return 'Outbid'
    case 'expired':
      return 'Expired'
    default:
      return 'Unknown'
  }
}

const getTimeText = (bid: any) => {
  const now = new Date()
  const diff = bid.endTime.getTime() - now.getTime()
  
  if (diff > 0) {
    const days = Math.floor(diff / (1000 * 60 * 60 * 24))
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60))
    
    if (days > 0) {
      return `Ends in ${days}d ${hours}h`
    } else if (hours > 0) {
      return `Ends in ${hours}h ${minutes}m`
    } else {
      return `Ends in ${minutes}m`
    }
  } else {
    const pastDiff = Math.abs(diff)
    const days = Math.floor(pastDiff / (1000 * 60 * 60 * 24))
    const hours = Math.floor((pastDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60))
    
    if (days > 0) {
      return `Ended ${days} days ago`
    } else if (hours > 0) {
      return `Ended ${hours} hours ago`
    } else {
      return `Ended recently`
    }
  }
}

const getBidLabel = (status: string) => {
  switch (status) {
    case 'won':
      return 'Your Winning Bid'
    case 'expired':
      return 'Your Final Bid'
    default:
      return 'Your Current Bid'
  }
}

const getCurrentLabel = (status: string) => {
  switch (status) {
    case 'won':
      return 'Total Bids'
    case 'expired':
      return 'Winning Bid'
    default:
      return 'Current High'
  }
}

const getBidPriceClass = (status: string) => {
  switch (status) {
    case 'won':
      return 'text-green-600'
    case 'outbid':
      return 'text-gray-600'
    default:
      return 'text-blue-600'
  }
}

const getCurrentPriceClass = (status: string) => {
  switch (status) {
    case 'won':
      return 'text-gray-900'
    case 'outbid':
      return 'text-red-600'
    case 'expired':
      return 'text-green-600'
    default:
      return 'text-blue-600'
  }
}

const getActions = (status: string) => {
  switch (status) {
    case 'won':
      return [
        { key: 'payment', label: 'Complete Payment', icon: 'fas fa-credit-card', class: 'bg-green-600 text-white hover:bg-green-700' },
        { key: 'details', label: 'View Details', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
      ]
    case 'leading':
    case 'active':
      return [
        { key: 'increase', label: 'Increase Bid', icon: 'fas fa-plus', class: 'bg-blue-600 text-white hover:bg-blue-700' },
        { key: 'watch', label: 'Watch Auction', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
      ]
    case 'outbid':
      return [
        { key: 'higher', label: 'Place Higher Bid', icon: 'fas fa-arrow-up', class: 'bg-red-600 text-white hover:bg-red-700' },
        { key: 'view', label: 'View Auction', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
      ]
    case 'expired':
      return [
        { key: 'results', label: 'View Results', icon: 'fas fa-chart-bar', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' },
        { key: 'similar', label: 'Find Similar', icon: 'fas fa-search', class: 'bg-blue-600 text-white hover:bg-blue-700' }
      ]
    default:
      return []
  }
}

const handleAction = (action: string, bid: any) => {
  switch (action) {
    case 'payment':
      router.push('/auction-won')
      break
    case 'details':
    case 'view':
    case 'watch':
      router.push(`/auction/${bid.id}`)
      break
    case 'increase':
    case 'higher':
      alert(`Redirecting to place higher bid for ${bid.item.title}`)
      router.push(`/auction/${bid.id}`)
      break
    case 'results':
      alert(`Viewing results for ${bid.item.title}`)
      break
    case 'similar':
      alert(`Finding similar items to ${bid.item.title}`)
      break
  }
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
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}

.status-won {
  background: linear-gradient(135deg, #10b981, #059669);
}

.status-outbid {
  background: linear-gradient(135deg, #ef4444, #dc2626);
}

.status-active {
  background: linear-gradient(135deg, #3b82f6, #2563eb);
}

.status-expired {
  background: linear-gradient(135deg, #6b7280, #4b5563);
}
</style>
