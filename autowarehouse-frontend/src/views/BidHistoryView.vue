<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow-lg border-b border-gray-200">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-4">
            <i class="fas fa-gavel text-2xl text-blue-600"></i>
            <h1 class="text-xl font-bold text-gray-900">Autowarehouse Auction</h1>
          </div>
          <nav class="hidden md:flex space-x-8">
            <router-link to="/auction" class="text-gray-600 hover:text-blue-600 font-medium">Live Auctions</router-link>
            <router-link to="/bid-history" class="text-blue-600 hover:text-blue-700 font-medium border-b-2 border-blue-600 pb-1">My Bids</router-link>
            <span class="text-gray-600 hover:text-blue-600 font-medium cursor-pointer">Categories</span>
          </nav>
          <div class="flex items-center space-x-4">
            <button class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
              <i class="fas fa-plus mr-2"></i>Create Auction
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
      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">My Bid History</h1>
        <p class="text-gray-600">Track all your auction bids and their current status</p>
      </div>

      <!-- Filters -->
      <div class="bg-white rounded-xl shadow-lg p-6 mb-8">
        <div class="flex flex-wrap gap-4 items-center justify-between">
          <div class="flex flex-wrap gap-4">
            <button 
              v-for="filter in filters" 
              :key="filter.key"
              @click="activeFilter = filter.key"
              :class="[
                'px-4 py-2 rounded-lg font-medium transition-colors',
                activeFilter === filter.key 
                  ? 'bg-blue-600 text-white' 
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              ]"
            >
              {{ filter.label }}
            </button>
          </div>
          <div class="flex items-center space-x-4">
            <select v-model="timeFilter" class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent">
              <option value="30">Last 30 days</option>
              <option value="7">Last 7 days</option>
              <option value="90">Last 3 months</option>
              <option value="all">All time</option>
            </select>
            <div class="relative">
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Search items..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
              >
              <i class="fas fa-search absolute left-3 top-3 text-gray-400"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- Summary Stats -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-green-600 mb-2">{{ stats.won }}</div>
          <div class="text-gray-600">Won Auctions</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-blue-600 mb-2">{{ stats.active }}</div>
          <div class="text-gray-600">Active Bids</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-red-600 mb-2">{{ stats.outbid }}</div>
          <div class="text-gray-600">Outbid</div>
        </div>
        <div class="bg-white rounded-xl shadow-lg p-6 text-center">
          <div class="text-3xl font-bold text-gray-600 mb-2">${{ stats.totalSpent.toLocaleString() }}</div>
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
              <div :class="getStatusClass(bid.status)" class="text-white px-3 py-1 rounded-full text-sm font-medium">
                <i :class="getStatusIcon(bid.status)" class="mr-1"></i>{{ bid.status }}
              </div>
              <span class="text-gray-500 text-sm">{{ bid.timeText }}</span>
            </div>
            
            <div class="flex space-x-4">
              <img :src="bid.image" :alt="bid.title" class="w-20 h-20 object-cover rounded-lg">
              <div class="flex-1">
                <h3 class="font-semibold text-gray-900 mb-1">{{ bid.title }}</h3>
                <p class="text-gray-600 text-sm mb-2">{{ bid.description }}</p>
                <div class="flex items-center justify-between">
                  <div>
                    <span class="text-sm text-gray-500">{{ bid.bidLabel }}:</span>
                    <div :class="getBidAmountClass(bid.status)" class="text-xl font-bold">
                      ${{ bid.bidAmount.toLocaleString() }}
                    </div>
                  </div>
                  <div class="text-right">
                    <span class="text-sm text-gray-500">{{ bid.currentLabel }}:</span>
                    <div :class="getCurrentAmountClass(bid.status)" class="text-lg font-semibold">
                      ${{ bid.currentAmount.toLocaleString() }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="mt-4 flex space-x-3">
              <button 
                v-for="action in bid.actions" 
                :key="action.label"
                :class="action.class"
                class="px-4 py-2 rounded-lg transition-colors"
                @click="handleAction(action.type, bid)"
              >
                <i :class="action.icon" class="mr-2"></i>{{ action.label }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Pagination -->
      <div class="flex justify-center mt-8">
        <div class="flex space-x-2">
          <button 
            @click="currentPage > 1 && (currentPage--)"
            :disabled="currentPage === 1"
            class="px-3 py-2 bg-gray-100 text-gray-600 rounded-lg hover:bg-gray-200 disabled:opacity-50"
          >
            <i class="fas fa-chevron-left"></i>
          </button>
          <button 
            v-for="page in totalPages" 
            :key="page"
            @click="currentPage = page"
            :class="[
              'px-4 py-2 rounded-lg',
              currentPage === page 
                ? 'bg-blue-600 text-white' 
                : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
            ]"
          >
            {{ page }}
          </button>
          <button 
            @click="currentPage < totalPages && (currentPage++)"
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

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Reactive data
const activeFilter = ref('all')
const timeFilter = ref('30')
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 5

const filters = [
  { key: 'all', label: 'All Bids' },
  { key: 'won', label: 'Won' },
  { key: 'active', label: 'Active' },
  { key: 'outbid', label: 'Outbid' },
  { key: 'expired', label: 'Expired' }
]

const stats = ref({
  won: 3,
  active: 7,
  outbid: 12,
  totalSpent: 47250
})

const bids = ref([
  {
    id: 1,
    title: 'NVIDIA RTX 4090 Gaming GPU',
    description: 'Brand new, sealed in box with warranty',
    image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=400',
    status: 'Won',
    bidAmount: 22500,
    currentAmount: 22500,
    bidLabel: 'Your Winning Bid',
    currentLabel: 'Total Bids',
    timeText: 'Ended 2 hours ago',
    actions: [
      { type: 'payment', label: 'Complete Payment', icon: 'fas fa-credit-card', class: 'bg-green-600 text-white hover:bg-green-700' },
      { type: 'details', label: 'View Details', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
    ]
  },
  {
    id: 2,
    title: 'Gaming Mechanical Keyboard RGB',
    description: 'Cherry MX switches, full RGB lighting',
    image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=400',
    status: 'Leading',
    bidAmount: 3200,
    currentAmount: 3200,
    bidLabel: 'Your Current Bid',
    currentLabel: 'Current High',
    timeText: 'Ends in 2h 15m',
    actions: [
      { type: 'increase', label: 'Increase Bid', icon: 'fas fa-plus', class: 'bg-blue-600 text-white hover:bg-blue-700' },
      { type: 'watch', label: 'Watch Auction', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
    ]
  },
  {
    id: 3,
    title: 'Gaming Laptop ASUS ROG',
    description: 'RTX 3070, 32GB RAM, 1TB SSD',
    image: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400',
    status: 'Outbid',
    bidAmount: 18500,
    currentAmount: 19200,
    bidLabel: 'Your Last Bid',
    currentLabel: 'Current High',
    timeText: 'Ends in 1d 8h',
    actions: [
      { type: 'higher', label: 'Place Higher Bid', icon: 'fas fa-arrow-up', class: 'bg-red-600 text-white hover:bg-red-700' },
      { type: 'view', label: 'View Auction', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
    ]
  },
  {
    id: 4,
    title: 'Wireless Gaming Mouse Pro',
    description: 'High precision sensor, RGB lighting',
    image: 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400',
    status: 'Active',
    bidAmount: 850,
    currentAmount: 920,
    bidLabel: 'Your Current Bid',
    currentLabel: 'Current High',
    timeText: 'Ends in 3d 12h',
    actions: [
      { type: 'increase', label: 'Increase Bid', icon: 'fas fa-plus', class: 'bg-blue-600 text-white hover:bg-blue-700' },
      { type: 'details', label: 'View Details', icon: 'fas fa-eye', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' }
    ]
  },
  {
    id: 5,
    title: 'Gaming Headset Wireless',
    description: '7.1 Surround Sound, Noise Cancelling',
    image: 'https://images.unsplash.com/photo-1484704849700-f032a568e944?w=400',
    status: 'Expired',
    bidAmount: 1200,
    currentAmount: 1450,
    bidLabel: 'Your Final Bid',
    currentLabel: 'Winning Bid',
    timeText: 'Ended 3 days ago',
    actions: [
      { type: 'results', label: 'View Results', icon: 'fas fa-chart-bar', class: 'bg-gray-100 text-gray-700 hover:bg-gray-200' },
      { type: 'similar', label: 'Find Similar', icon: 'fas fa-search', class: 'bg-blue-600 text-white hover:bg-blue-700' }
    ]
  }
])

// Computed properties
const filteredBids = computed(() => {
  let filtered = bids.value

  // Filter by status
  if (activeFilter.value !== 'all') {
    filtered = filtered.filter(bid => 
      bid.status.toLowerCase() === activeFilter.value.toLowerCase() ||
      (activeFilter.value === 'active' && bid.status === 'Leading')
    )
  }

  // Filter by search query
  if (searchQuery.value) {
    filtered = filtered.filter(bid =>
      bid.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      bid.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  return filtered
})

const totalPages = computed(() => {
  return Math.ceil(filteredBids.value.length / itemsPerPage)
})

// Methods
const getStatusClass = (status) => {
  const classes = {
    'Won': 'bg-gradient-to-r from-green-500 to-green-600',
    'Leading': 'bg-gradient-to-r from-blue-500 to-blue-600',
    'Active': 'bg-gradient-to-r from-blue-500 to-blue-600',
    'Outbid': 'bg-gradient-to-r from-red-500 to-red-600',
    'Expired': 'bg-gradient-to-r from-gray-500 to-gray-600'
  }
  return classes[status] || 'bg-gray-500'
}

const getStatusIcon = (status) => {
  const icons = {
    'Won': 'fas fa-trophy',
    'Leading': 'fas fa-clock',
    'Active': 'fas fa-clock',
    'Outbid': 'fas fa-exclamation-triangle',
    'Expired': 'fas fa-times'
  }
  return icons[status] || 'fas fa-circle'
}

const getBidAmountClass = (status) => {
  const classes = {
    'Won': 'text-green-600',
    'Leading': 'text-blue-600',
    'Active': 'text-gray-600',
    'Outbid': 'text-gray-600',
    'Expired': 'text-gray-600'
  }
  return classes[status] || 'text-gray-600'
}

const getCurrentAmountClass = (status) => {
  const classes = {
    'Won': 'text-gray-600',
    'Leading': 'text-green-600',
    'Active': 'text-blue-600',
    'Outbid': 'text-red-600',
    'Expired': 'text-green-600'
  }
  return classes[status] || 'text-gray-600'
}

const handleAction = (actionType, bid) => {
  switch (actionType) {
    case 'payment':
      router.push('/auction-won')
      break
    case 'increase':
    case 'higher':
      router.push(`/auction/${bid.id}`)
      break
    case 'details':
    case 'view':
      router.push(`/auction/${bid.id}`)
      break
    case 'watch':
      // Add to watchlist logic
      console.log('Added to watchlist:', bid.title)
      break
    case 'results':
      // Show auction results
      console.log('Showing results for:', bid.title)
      break
    case 'similar':
      // Find similar items
      console.log('Finding similar items to:', bid.title)
      break
  }
}

onMounted(() => {
  console.log('Bid History page loaded')
})
</script>

<style scoped>
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
