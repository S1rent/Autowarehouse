<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    
  <UserNavbar />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between mb-6">
          <div>
            <h1 class="text-3xl font-bold text-gray-900">Live Auctions</h1>
            <p class="text-gray-600 mt-2">Discover and bid on exclusive hardware and electronics from around the world</p>
          </div>
          <div class="flex items-center space-x-4">
            <div class="relative">
              <input 
                type="text" 
                v-model="searchQuery"
                placeholder="Search auctions..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent w-80"
              >
              <i class="fas fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
            </div>
            <select 
              v-model="selectedCategory"
              class="border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-blue-500 focus:border-transparent"
            >
              <option value="">All Categories</option>
              <option value="gaming">Gaming Hardware</option>
              <option value="components">PC Components</option>
              <option value="peripherals">Peripherals</option>
              <option value="laptops">Laptops</option>
              <option value="accessories">Accessories</option>
            </select>
          </div>
        </div>

        <!-- Filter Tabs -->
        <div class="flex space-x-6 border-b border-gray-200">
          <button 
            v-for="tab in filterTabs" 
            :key="tab.key"
            @click="activeFilter = tab.key"
            :class="activeFilter === tab.key ? 'pb-3 px-1 border-b-2 border-blue-600 text-blue-600 font-medium' : 'pb-3 px-1 text-gray-600 hover:text-blue-600 font-medium'"
          >
            {{ tab.label }} ({{ tab.count }})
          </button>
        </div>
      </div>

      <!-- Auctions Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div 
          v-for="auction in filteredAuctions" 
          :key="auction.id"
          @click="goToAuctionDetail(auction.id)"
          class="auction-card bg-white rounded-xl shadow-lg overflow-hidden cursor-pointer hover:transform hover:-translate-y-1 transition-all duration-300"
        >
          <div class="relative">
            <img 
              :src="auction.image" 
              :alt="auction.title"
              class="w-full h-48 object-cover"
            >
            <div class="absolute top-3 left-3">
              <span 
                :class="getStatusClass(auction.status)"
                class="text-white px-3 py-1 rounded-full text-sm font-medium"
              >
                <i :class="getStatusIcon(auction.status)" class="mr-1"></i>
                {{ getStatusText(auction.status) }}
              </span>
            </div>
            <div class="absolute top-3 right-3 bg-black bg-opacity-50 text-white px-2 py-1 rounded text-sm">
              {{ getTimeText(auction) }}
            </div>
          </div>
          <div class="p-6">
            <h3 class="text-lg font-bold text-gray-900 mb-2">{{ auction.title }}</h3>
            <p class="text-gray-600 text-sm mb-4">{{ auction.description }}</p>
            <div class="flex items-center justify-between mb-4">
              <div>
                <span class="text-sm text-gray-500">{{ getPriceLabel(auction.status) }}</span>
                <div 
                  :class="getPriceClass(auction.status)"
                  class="text-xl font-bold"
                >
                  ${{ auction.currentBid.toLocaleString() }}
                </div>
              </div>
              <div class="text-right">
                <span class="text-sm text-gray-500">{{ auction.status === 'upcoming' ? 'Watchers' : 'Bids' }}</span>
                <div class="text-lg font-semibold">{{ auction.status === 'upcoming' ? auction.watchers : auction.bids }}</div>
              </div>
            </div>
            <div class="flex items-center text-sm text-gray-500 mb-4">
              <i :class="getWatchIcon(auction.status)" class="mr-1"></i>
              <span>{{ getWatchText(auction) }}</span>
            </div>
            <button 
              @click.stop="handleAuctionAction(auction)"
              :class="getButtonClass(auction.status)"
              class="w-full py-2 px-4 rounded-lg font-medium transition-colors"
            >
              {{ getButtonText(auction.status) }}
            </button>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="filteredAuctions.length === 0" class="text-center py-12">
        <i class="fas fa-gavel text-4xl text-gray-300 mb-4"></i>
        <h3 class="text-lg font-medium text-gray-900 mb-2">No auctions found</h3>
        <p class="text-gray-500">Try adjusting your search or filter criteria</p>
      </div>

      <!-- Pagination -->
      <div v-if="filteredAuctions.length > 0" class="flex justify-center mt-12">
        <div class="flex items-center space-x-2">
          <button 
            @click="previousPage"
            :disabled="currentPage === 1"
            class="px-3 py-2 text-gray-500 hover:text-blue-600 border border-gray-300 rounded-lg disabled:opacity-50"
          >
            <i class="fas fa-chevron-left"></i>
          </button>
          <button 
            v-for="page in totalPages" 
            :key="page"
            @click="currentPage = page"
            :class="currentPage === page ? 'px-4 py-2 bg-blue-600 text-white border border-blue-600 rounded-lg' : 'px-4 py-2 text-gray-700 hover:text-blue-600 border border-gray-300 rounded-lg'"
          >
            {{ page }}
          </button>
          <button 
            @click="nextPage"
            :disabled="currentPage === totalPages"
            class="px-3 py-2 text-gray-500 hover:text-blue-600 border border-gray-300 rounded-lg disabled:opacity-50"
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

import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()

// State
const searchQuery = ref('')
const selectedCategory = ref('')
const activeFilter = ref('all')
const currentPage = ref(1)
const itemsPerPage = 6

// Filter tabs
const filterTabs = [
  { key: 'all', label: 'All Auctions', count: 47 },
  { key: 'live', label: 'Live Now', count: 12 },
  { key: 'upcoming', label: 'Starting Soon', count: 8 },
  { key: 'ending', label: 'Ending Soon', count: 5 }
]

// Sample auction data
const auctions = ref([
  {
    id: 1,
    title: 'NVIDIA RTX 4090 Gaming GPU',
    description: 'Brand new, sealed in box with warranty',
    image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=400&h=300&fit=crop',
    status: 'live',
    currentBid: 1850,
    bids: 47,
    watchers: 847,
    timeLeft: '2h 34m',
    category: 'components'
  },
  {
    id: 2,
    title: 'Gaming Mechanical Keyboard RGB',
    description: 'Cherry MX switches, full RGB lighting',
    image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?w=400&h=300&fit=crop',
    status: 'live',
    currentBid: 245,
    bids: 23,
    watchers: 234,
    timeLeft: '1h 12m',
    category: 'peripherals'
  },
  {
    id: 3,
    title: 'Gaming Laptop ASUS ROG',
    description: 'RTX 3070, 32GB RAM, 1TB SSD',
    image: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400&h=300&fit=crop',
    status: 'upcoming',
    currentBid: 1500,
    bids: 0,
    watchers: 156,
    timeLeft: '45m',
    category: 'laptops'
  },
  {
    id: 4,
    title: 'Custom Gaming PC Build',
    description: 'Intel i9, RTX 4080, 64GB RAM',
    image: 'https://images.unsplash.com/photo-1587831990711-23ca6441447b?w=400&h=300&fit=crop',
    status: 'live',
    currentBid: 2890,
    bids: 34,
    watchers: 445,
    timeLeft: '4h 22m',
    category: 'gaming'
  },
  {
    id: 5,
    title: 'Professional Audio Interface',
    description: 'Focusrite Scarlett 18i20, barely used',
    image: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=400&h=300&fit=crop',
    status: 'ended',
    currentBid: 520,
    bids: 67,
    watchers: 123,
    timeLeft: 'Sold',
    category: 'accessories'
  },
  {
    id: 6,
    title: 'Ultra-wide Gaming Monitor 34"',
    description: '144Hz, 1ms response time, G-Sync',
    image: 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=400&h=300&fit=crop',
    status: 'live',
    currentBid: 680,
    bids: 89,
    watchers: 567,
    timeLeft: '6h 45m',
    category: 'peripherals'
  }
])

// Computed properties
const filteredAuctions = computed(() => {
  let filtered = auctions.value

  // Filter by search query
  if (searchQuery.value) {
    filtered = filtered.filter(auction => 
      auction.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      auction.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  // Filter by category
  if (selectedCategory.value) {
    filtered = filtered.filter(auction => auction.category === selectedCategory.value)
  }

  // Filter by status
  if (activeFilter.value !== 'all') {
    filtered = filtered.filter(auction => auction.status === activeFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalPages = computed(() => Math.ceil(auctions.value.length / itemsPerPage))

// Methods
const getStatusClass = (status: string) => {
  switch (status) {
    case 'live':
      return 'bg-gradient-to-r from-red-600 to-red-500'
    case 'upcoming':
      return 'bg-gradient-to-r from-yellow-600 to-yellow-500'
    case 'ended':
      return 'bg-gradient-to-r from-gray-600 to-gray-500'
    default:
      return 'bg-gray-500'
  }
}

const getStatusIcon = (status: string) => {
  switch (status) {
    case 'live':
      return 'fas fa-circle animate-pulse'
    case 'upcoming':
      return 'fas fa-clock'
    case 'ended':
      return 'fas fa-check'
    default:
      return 'fas fa-circle'
  }
}

const getStatusText = (status: string) => {
  switch (status) {
    case 'live':
      return 'Live'
    case 'upcoming':
      return 'Starting Soon'
    case 'ended':
      return 'Ended'
    default:
      return 'Unknown'
  }
}

const getTimeText = (auction: any) => {
  if (auction.status === 'upcoming') {
    return `Starts in ${auction.timeLeft}`
  } else if (auction.status === 'ended') {
    return 'Sold'
  } else {
    return `${auction.timeLeft} left`
  }
}

const getPriceLabel = (status: string) => {
  switch (status) {
    case 'upcoming':
      return 'Starting Bid'
    case 'ended':
      return 'Final Price'
    default:
      return 'Current Bid'
  }
}

const getPriceClass = (status: string) => {
  switch (status) {
    case 'upcoming':
      return 'text-blue-600'
    case 'ended':
      return 'text-gray-600'
    default:
      return 'text-green-600'
  }
}

const getWatchIcon = (status: string) => {
  switch (status) {
    case 'ended':
      return 'fas fa-trophy'
    case 'upcoming':
      return 'fas fa-heart'
    default:
      return 'fas fa-eye'
  }
}

const getWatchText = (auction: any) => {
  if (auction.status === 'ended') {
    return 'Won by HighBidder_123'
  } else if (auction.status === 'upcoming') {
    return `${auction.watchers} favorites`
  } else {
    return `${auction.watchers} watching`
  }
}

const getButtonClass = (status: string) => {
  switch (status) {
    case 'upcoming':
      return 'bg-gray-600 hover:bg-gray-700 text-white'
    case 'ended':
      return 'bg-gray-400 text-white cursor-not-allowed'
    default:
      return 'bg-blue-600 hover:bg-blue-700 text-white'
  }
}

const getButtonText = (status: string) => {
  switch (status) {
    case 'upcoming':
      return 'Set Reminder'
    case 'ended':
      return 'Auction Ended'
    default:
      return 'Place Bid'
  }
}

const goToAuctionDetail = (id: number) => {
  router.push(`/auction/${id}`)
}

const handleAuctionAction = (auction: any) => {
  if (auction.status === 'ended') {
    return
  }
  
  if (auction.status === 'upcoming') {
    alert(`Reminder set for ${auction.title}`)
  } else {
    alert(`Placing bid on ${auction.title}`)
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

.auction-card:hover {
  transform: translateY(-4px);
  transition: all 0.3s ease;
}

@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}

.animate-pulse {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}
</style>
