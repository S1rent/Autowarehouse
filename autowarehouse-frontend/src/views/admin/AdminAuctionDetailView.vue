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
              <div class="flex items-center space-x-4">
                <router-link 
                  to="/admin/auctions"
                  class="text-gray-400 hover:text-gray-600"
                >
                  <i class="fa-solid fa-arrow-left text-xl"></i>
                </router-link>
                <div>
                  <h1 class="text-2xl font-bold text-gray-900">Auction Details</h1>
                  <p class="text-gray-600">{{ auction.title }}</p>
                </div>
              </div>
              <div class="flex items-center space-x-4">
                <button 
                  @click="editAuction"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-edit mr-2"></i>
                  Edit Auction
                </button>
                <button 
                  v-if="auction.status === 'live'"
                  @click="endAuction"
                  class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors"
                >
                  <i class="fa-solid fa-stop mr-2"></i>
                  End Auction
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
          <!-- Auction Overview -->
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
            <!-- Product Images -->
            <div class="lg:col-span-1">
              <div class="bg-white rounded-xl shadow-sm p-6">
                <div class="aspect-square bg-gray-100 rounded-lg mb-4 overflow-hidden">
                  <img 
                    :src="auction.image" 
                    :alt="auction.title"
                    class="w-full h-full object-cover"
                  >
                </div>
                <div class="grid grid-cols-4 gap-2">
                  <div 
                    v-for="(thumb, index) in auction.thumbnails" 
                    :key="index"
                    class="aspect-square bg-gray-100 rounded-lg overflow-hidden"
                  >
                    <img 
                      :src="thumb" 
                      :alt="`Thumbnail ${index + 1}`"
                      class="w-full h-full object-cover"
                    >
                  </div>
                </div>
              </div>
            </div>

            <!-- Auction Info -->
            <div class="lg:col-span-2 space-y-6">
              <!-- Basic Info -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <div class="flex items-center justify-between mb-4">
                  <h2 class="text-xl font-semibold text-gray-900">{{ auction.title }}</h2>
                  <span 
                    :class="getStatusClass(auction.status)"
                    class="inline-flex px-3 py-1 text-sm font-semibold rounded-full"
                  >
                    <i :class="getStatusIcon(auction.status)" class="mr-1"></i>
                    {{ getStatusText(auction.status) }}
                  </span>
                </div>
                <p class="text-gray-600 mb-4">{{ auction.description }}</p>
                
                <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
                  <div>
                    <p class="text-sm text-gray-500">Category</p>
                    <p class="font-medium text-gray-900">{{ auction.category }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500">Starting Bid</p>
                    <p class="font-medium text-gray-900">Rp {{ auction.startingBid.toLocaleString() }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500">Current Bid</p>
                    <p class="font-medium text-green-600">Rp {{ auction.currentBid.toLocaleString() }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500">Total Bids</p>
                    <p class="font-medium text-gray-900">{{ auction.totalBids }}</p>
                  </div>
                </div>
              </div>

              <!-- Timing Info -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Timing Information</h3>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div>
                    <p class="text-sm text-gray-500">Start Time</p>
                    <p class="font-medium text-gray-900">{{ formatDateTime(auction.startTime) }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500">End Time</p>
                    <p class="font-medium text-gray-900">{{ formatDateTime(auction.endTime) }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500">Duration</p>
                    <p class="font-medium text-gray-900">{{ auction.duration }}</p>
                  </div>
                </div>
              </div>

              <!-- Stats -->
              <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Watchers</p>
                      <p class="text-2xl font-bold text-blue-600">{{ auction.watchers }}</p>
                    </div>
                    <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-eye text-blue-600"></i>
                    </div>
                  </div>
                </div>

                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Bidders</p>
                      <p class="text-2xl font-bold text-green-600">{{ auction.uniqueBidders }}</p>
                    </div>
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-users text-green-600"></i>
                    </div>
                  </div>
                </div>

                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Views</p>
                      <p class="text-2xl font-bold text-purple-600">{{ auction.views }}</p>
                    </div>
                    <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-chart-line text-purple-600"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Bid History -->
          <div class="bg-white rounded-xl shadow-sm mb-8">
            <div class="px-6 py-4 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Bid History</h3>
                <div class="flex items-center space-x-2">
                  <button class="px-3 py-1 text-sm text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                    <i class="fa-solid fa-download mr-1"></i>
                    Export
                  </button>
                  <button class="px-3 py-1 text-sm text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50">
                    <i class="fa-solid fa-refresh mr-1"></i>
                    Refresh
                  </button>
                </div>
              </div>
            </div>
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bidder</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Bid Amount</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Time</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                  <tr 
                    v-for="(bid, index) in auction.bidHistory" 
                    :key="bid.id"
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <img :src="bid.bidder.avatar" :alt="bid.bidder.name" class="w-8 h-8 rounded-full mr-3">
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ bid.bidder.name }}</div>
                          <div class="text-sm text-gray-500">{{ bid.bidder.email }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="text-sm font-medium text-gray-900">Rp {{ bid.amount.toLocaleString() }}</div>
                      <div v-if="index === 0" class="text-xs text-green-600">Highest Bid</div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ formatDateTime(bid.timestamp) }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="index === 0 ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ index === 0 ? 'Leading' : 'Outbid' }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <button class="text-blue-600 hover:text-blue-900 mr-3">
                        <i class="fa-solid fa-eye"></i>
                      </button>
                      <button class="text-red-600 hover:text-red-900">
                        <i class="fa-solid fa-ban"></i>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Activity Log -->
          <div class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Activity Log</h3>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div 
                  v-for="activity in auction.activityLog" 
                  :key="activity.id"
                  class="flex items-start space-x-3"
                >
                  <div 
                    :class="getActivityIconClass(activity.type)"
                    class="w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0"
                  >
                    <i :class="getActivityIcon(activity.type)" class="text-sm"></i>
                  </div>
                  <div class="flex-1">
                    <p class="text-sm text-gray-900">{{ activity.description }}</p>
                    <p class="text-xs text-gray-500">{{ formatDateTime(activity.timestamp) }}</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// Sample auction data
const auction = ref({
  id: 1,
  title: 'NVIDIA RTX 4090 Gaming GPU',
  description: 'Brand new, sealed in box with full manufacturer warranty. This flagship graphics card delivers unparalleled gaming performance.',
  category: 'Electronics',
  status: 'live',
  startingBid: 15000000,
  currentBid: 18500000,
  totalBids: 47,
  uniqueBidders: 23,
  watchers: 847,
  views: 2341,
  duration: '7 days',
  startTime: new Date(Date.now() - 5 * 24 * 60 * 60 * 1000), // 5 days ago
  endTime: new Date(Date.now() + 2 * 60 * 60 * 1000), // 2 hours from now
  image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=600&h=600&fit=crop',
  thumbnails: [
    'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=200&h=200&fit=crop',
    'https://images.unsplash.com/photo-1518717758536-85ae29035b6d?w=200&h=200&fit=crop',
    'https://images.unsplash.com/photo-1555617981-dac3880eac6e?w=200&h=200&fit=crop',
    'https://images.unsplash.com/photo-1587831990711-23ca6441447b?w=200&h=200&fit=crop'
  ],
  bidHistory: [
    {
      id: 1,
      bidder: {
        name: 'GamerPro_123',
        email: 'gamer@example.com',
        avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face'
      },
      amount: 18500000,
      timestamp: new Date(Date.now() - 5 * 60 * 1000)
    },
    {
      id: 2,
      bidder: {
        name: 'TechCollector',
        email: 'tech@example.com',
        avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=32&h=32&fit=crop&crop=face'
      },
      amount: 18000000,
      timestamp: new Date(Date.now() - 15 * 60 * 1000)
    },
    {
      id: 3,
      bidder: {
        name: 'HardwareHunter',
        email: 'hardware@example.com',
        avatar: 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=32&h=32&fit=crop&crop=face'
      },
      amount: 17500000,
      timestamp: new Date(Date.now() - 30 * 60 * 1000)
    }
  ],
  activityLog: [
    {
      id: 1,
      type: 'bid',
      description: 'New bid placed by GamerPro_123 for Rp 18,500,000',
      timestamp: new Date(Date.now() - 5 * 60 * 1000)
    },
    {
      id: 2,
      type: 'watch',
      description: '5 new users started watching this auction',
      timestamp: new Date(Date.now() - 10 * 60 * 1000)
    },
    {
      id: 3,
      type: 'bid',
      description: 'New bid placed by TechCollector for Rp 18,000,000',
      timestamp: new Date(Date.now() - 15 * 60 * 1000)
    },
    {
      id: 4,
      type: 'system',
      description: 'Auction extended by 2 hours due to last-minute bidding',
      timestamp: new Date(Date.now() - 2 * 60 * 60 * 1000)
    }
  ]
})

// Methods
const getStatusClass = (status: string) => {
  const classes = {
    'live': 'bg-red-100 text-red-800',
    'upcoming': 'bg-yellow-100 text-yellow-800',
    'ended': 'bg-gray-100 text-gray-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusIcon = (status: string) => {
  const icons = {
    'live': 'fa-solid fa-circle',
    'upcoming': 'fa-solid fa-clock',
    'ended': 'fa-solid fa-check'
  }
  return icons[status as keyof typeof icons] || 'fa-solid fa-circle'
}

const getStatusText = (status: string) => {
  const texts = {
    'live': 'Live',
    'upcoming': 'Upcoming',
    'ended': 'Ended'
  }
  return texts[status as keyof typeof texts] || status
}

const getActivityIconClass = (type: string) => {
  const classes = {
    'bid': 'bg-green-100',
    'watch': 'bg-blue-100',
    'system': 'bg-yellow-100'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100'
}

const getActivityIcon = (type: string) => {
  const icons = {
    'bid': 'fa-solid fa-gavel text-green-600',
    'watch': 'fa-solid fa-eye text-blue-600',
    'system': 'fa-solid fa-cog text-yellow-600'
  }
  return icons[type as keyof typeof icons] || 'fa-solid fa-circle text-gray-600'
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

const editAuction = () => {
  router.push(`/admin/auctions/${auction.value.id}/edit`)
}

const endAuction = () => {
  if (confirm('Are you sure you want to end this auction?')) {
    auction.value.status = 'ended'
    // Add API call here
  }
}

onMounted(() => {
  const auctionId = route.params.id
  console.log('Loading auction details for ID:', auctionId)
})
</script>

<style scoped>
/* Custom styles */
</style>
