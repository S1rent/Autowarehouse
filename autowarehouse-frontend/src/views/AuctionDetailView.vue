<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <UserNavbar />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <!-- Left Column - Product Images -->
        <div class="space-y-4">
          <div class="relative">
            <img 
              :src="auction.image" 
              :alt="auction.title"
              class="w-full h-96 object-cover rounded-xl"
            >
            <div class="absolute top-4 left-4">
              <span 
                :class="getStatusClass(auction.status)"
                class="text-white px-3 py-1 rounded-full text-sm font-medium"
              >
                <i :class="getStatusIcon(auction.status)" class="mr-1"></i>
                {{ getStatusText(auction.status) }}
              </span>
            </div>
            <div class="absolute top-4 right-4 bg-black bg-opacity-50 text-white px-3 py-1 rounded text-sm">
              {{ getTimeText(auction) }}
            </div>
          </div>
          
          <!-- Thumbnail Images -->
          <div class="grid grid-cols-4 gap-2">
            <img 
              v-for="(thumb, index) in auction.thumbnails" 
              :key="index"
              :src="thumb" 
              :alt="`${auction.title} view ${index + 1}`"
              class="w-full h-20 object-cover rounded-lg cursor-pointer hover:opacity-75 transition-opacity"
            >
          </div>
        </div>

        <!-- Right Column - Auction Details -->
        <div class="space-y-6">
          <!-- Title and Description -->
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">{{ auction.title }}</h1>
            <p class="text-gray-600 text-lg mb-4">{{ auction.description }}</p>
            
            <!-- Detailed Product Information -->
            <div class="bg-gray-50 rounded-lg p-4 mb-4">
              <h3 class="font-semibold text-gray-900 mb-3">Product Details</h3>
              <div class="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <span class="text-gray-500">Brand:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.brand }}</span>
                </div>
                <div>
                  <span class="text-gray-500">Model:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.model }}</span>
                </div>
                <div>
                  <span class="text-gray-500">Condition:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.condition }}</span>
                </div>
                <div>
                  <span class="text-gray-500">Warranty:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.warranty }}</span>
                </div>
                <div>
                  <span class="text-gray-500">SKU:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.sku }}</span>
                </div>
                <div>
                  <span class="text-gray-500">Category:</span>
                  <span class="font-medium text-gray-900 ml-2">{{ auction.details.category }}</span>
                </div>
              </div>
            </div>

            <!-- Product Features -->
            <div class="mb-4">
              <h3 class="font-semibold text-gray-900 mb-3">Key Features</h3>
              <ul class="list-disc list-inside space-y-1 text-gray-600">
                <li v-for="feature in auction.features" :key="feature">{{ feature }}</li>
              </ul>
            </div>

            <!-- Additional Description -->
            <div class="text-gray-600 text-sm leading-relaxed">
              {{ auction.fullDescription }}
            </div>
          </div>

          <!-- Current Bid Info -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <div class="flex items-center justify-between mb-4">
              <div>
                <span class="text-sm text-gray-500">{{ getPriceLabel(auction.status) }}</span>
                <div 
                  :class="getPriceClass(auction.status)"
                  class="text-4xl font-bold"
                >
                  ${{ auction.currentBid.toLocaleString() }}
                </div>
              </div>
              <div class="text-right">
                <span class="text-sm text-gray-500">Total Bids</span>
                <div class="text-2xl font-semibold text-gray-900">{{ auction.bids }}</div>
              </div>
            </div>

            <!-- Bid Input -->
            <div v-if="auction.status === 'live'" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Your Bid Amount</label>
                <div class="relative">
                  <span class="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500">$</span>
                  <input 
                    v-model="bidAmount"
                    type="number" 
                    :min="auction.currentBid + auction.minIncrement"
                    class="w-full pl-8 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent text-lg"
                    :placeholder="(auction.currentBid + auction.minIncrement).toString()"
                  >
                </div>
                <p class="text-sm text-gray-500 mt-1">
                  Minimum bid: ${{ (auction.currentBid + auction.minIncrement).toLocaleString() }}
                </p>
              </div>
              <button 
                @click="placeBid"
                :disabled="!isValidBid"
                class="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 text-white py-3 px-6 rounded-lg font-medium text-lg transition-colors"
              >
                Place Bid
              </button>
            </div>

            <!-- Auction Ended State -->
            <div v-else-if="auction.status === 'ended'" class="text-center py-4">
              <i class="fas fa-trophy text-4xl text-yellow-500 mb-2"></i>
              <p class="text-lg font-medium text-gray-900">Auction Ended</p>
              <p class="text-gray-600">Won by {{ auction.winner }}</p>
            </div>

            <!-- Upcoming State -->
            <div v-else class="text-center py-4">
              <i class="fas fa-clock text-4xl text-yellow-500 mb-2"></i>
              <p class="text-lg font-medium text-gray-900">Auction Starting Soon</p>
              <button class="mt-2 bg-gray-600 hover:bg-gray-700 text-white py-2 px-4 rounded-lg font-medium transition-colors">
                Set Reminder
              </button>
            </div>
          </div>

          <!-- Auction Stats -->
          <div class="grid grid-cols-3 gap-4">
            <div class="bg-white rounded-lg p-4 text-center shadow">
              <i class="fas fa-eye text-2xl text-blue-600 mb-2"></i>
              <div class="text-lg font-semibold text-gray-900">{{ auction.watchers }}</div>
              <div class="text-sm text-gray-500">Watching</div>
            </div>
            <div class="bg-white rounded-lg p-4 text-center shadow">
              <i class="fas fa-users text-2xl text-green-600 mb-2"></i>
              <div class="text-lg font-semibold text-gray-900">{{ auction.bidders }}</div>
              <div class="text-sm text-gray-500">Bidders</div>
            </div>
            <div class="bg-white rounded-lg p-4 text-center shadow">
              <i class="fas fa-clock text-2xl text-orange-600 mb-2"></i>
              <div class="text-lg font-semibold text-gray-900">{{ auction.timeLeft }}</div>
              <div class="text-sm text-gray-500">Time Left</div>
            </div>
          </div>

          <!-- Seller Info -->
          <div class="bg-white rounded-xl p-6 shadow-lg">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Seller Information</h3>
            <div class="flex items-center space-x-4">
              <img :src="auction.seller.avatar" :alt="auction.seller.name" class="w-12 h-12 rounded-full">
              <div>
                <div class="font-medium text-gray-900">{{ auction.seller.name }}</div>
                <div class="text-sm text-gray-500">{{ auction.seller.rating }} ⭐ ({{ auction.seller.reviews }} reviews)</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Bid History -->
      <div class="mt-12 bg-white rounded-xl shadow-lg overflow-hidden">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Bid History</h3>
        </div>
        <div class="divide-y divide-gray-200">
          <div 
            v-for="bid in auction.bidHistory" 
            :key="bid.id"
            class="px-6 py-4 flex items-center justify-between"
          >
            <div class="flex items-center space-x-3">
              <img :src="bid.bidder.avatar" :alt="bid.bidder.name" class="w-8 h-8 rounded-full">
              <div>
                <div class="font-medium text-gray-900">{{ bid.bidder.name }}</div>
                <div class="text-sm text-gray-500">{{ formatTime(bid.timestamp) }}</div>
              </div>
            </div>
            <div class="text-lg font-semibold text-green-600">
              ${{ bid.amount.toLocaleString() }}
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'

const route = useRoute()
const bidAmount = ref<number>(0)

// Sample auction data
const auction = ref({
  id: 1,
  title: 'NVIDIA RTX 4090 Gaming GPU',
  description: 'Brand new, sealed in box with full manufacturer warranty. This flagship graphics card delivers unparalleled gaming performance.',
  image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=800&h=600&fit=crop',
  thumbnails: [
    'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1518717758536-85ae29035b6d?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1555617981-dac3880eac6e?w=200&h=150&fit=crop',
    'https://images.unsplash.com/photo-1587831990711-23ca6441447b?w=200&h=150&fit=crop'
  ],
  status: 'live',
  currentBid: 1850,
  minIncrement: 50,
  bids: 47,
  watchers: 847,
  bidders: 23,
  timeLeft: '2h 34m',
  winner: 'TechEnthusiast_92',
  details: {
    brand: 'NVIDIA',
    model: 'GeForce RTX 4090',
    condition: 'Brand New',
    warranty: '3 Years Manufacturer',
    sku: 'RTX4090-FE-24GB',
    category: 'Graphics Cards'
  },
  features: [
    '24GB GDDR6X Memory',
    'Ada Lovelace Architecture',
    'Ray Tracing 3rd Gen RT Cores',
    '4th Gen Tensor Cores for AI',
    'DLSS 3 with Frame Generation',
    '8K Gaming Ready',
    'AV1 Encode and Decode',
    'PCIe 4.0 Interface'
  ],
  fullDescription: 'This NVIDIA GeForce RTX 4090 represents the pinnacle of gaming performance. Built on the revolutionary Ada Lovelace architecture, it delivers exceptional performance for 4K gaming, content creation, and AI workloads. The card features 24GB of ultra-fast GDDR6X memory, making it perfect for the most demanding games and professional applications. With support for DLSS 3 and Frame Generation, you can achieve incredible frame rates while maintaining stunning visual quality. The card is brand new, factory sealed, and comes with full manufacturer warranty.',
  seller: {
    name: 'ElectronicsDealer',
    avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=48&h=48&fit=crop&crop=face',
    rating: 4.8,
    reviews: 156
  },
  bidHistory: [
    {
      id: 1,
      bidder: {
        name: 'GamerPro_123',
        avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face'
      },
      amount: 1850,
      timestamp: new Date(Date.now() - 5 * 60 * 1000)
    },
    {
      id: 2,
      bidder: {
        name: 'TechCollector',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=32&h=32&fit=crop&crop=face'
      },
      amount: 1800,
      timestamp: new Date(Date.now() - 15 * 60 * 1000)
    },
    {
      id: 3,
      bidder: {
        name: 'HardwareHunter',
        avatar: 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=32&h=32&fit=crop&crop=face'
      },
      amount: 1750,
      timestamp: new Date(Date.now() - 30 * 60 * 1000)
    }
  ]
})

// Computed properties
const isValidBid = computed(() => {
  return bidAmount.value >= auction.value.currentBid + auction.value.minIncrement
})

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

const placeBid = () => {
  if (isValidBid.value) {
    alert(`Bid placed: $${bidAmount.value.toLocaleString()}`)
    // Here you would typically make an API call to place the bid
    auction.value.currentBid = bidAmount.value
    auction.value.bids += 1
    bidAmount.value = 0
  }
}

const formatTime = (timestamp: Date) => {
  const now = new Date()
  const diff = now.getTime() - timestamp.getTime()
  const minutes = Math.floor(diff / (1000 * 60))
  
  if (minutes < 1) {
    return 'Just now'
  } else if (minutes < 60) {
    return `${minutes}m ago`
  } else {
    const hours = Math.floor(minutes / 60)
    return `${hours}h ago`
  }
}

onMounted(() => {
  // Load auction data based on route params
  const auctionId = route.params.id
  console.log('Loading auction:', auctionId)
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
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
