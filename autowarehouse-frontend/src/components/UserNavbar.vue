<template>
  <nav class="bg-white shadow-lg border-b border-gray-200 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/" class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-store text-white text-lg"></i>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-800">AutoWarehouse</h1>
              <p class="text-xs text-gray-500">Your Auto Parts Store</p>
            </div>
          </router-link>
        </div>

        <!-- Desktop Navigation -->
        <div class="hidden md:flex items-center space-x-8">
          <router-link 
            to="/" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Home
          </router-link>
          <router-link 
            to="/products" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Products
          </router-link>
          <router-link 
            to="/order-history" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Order History
          </router-link>
          <!-- <router-link 
            to="/auctions" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Live Auctions
          </router-link>
          <router-link 
            to="/my-bids" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            My Bids
          </router-link> -->
          <router-link 
            to="/customer-service" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Support
          </router-link>
        </div>

        <!-- User Actions -->
        <div class="hidden md:flex items-center space-x-4">
          <!-- Cart -->
          <router-link 
            to="/cart" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
          >
            <i class="fa-solid fa-shopping-cart text-lg"></i>
            <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ cartCount }}
            </span>
          </router-link>

          <!-- Saved Items -->
          <!-- <router-link 
            to="/saved-items" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
            title="Saved Items"
          >
            <i class="fa-solid fa-bookmark text-lg"></i>
            <span v-if="savedItemsCount > 0" class="absolute -top-1 -right-1 bg-blue-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ savedItemsCount }}
            </span>
          </router-link> -->

          <!-- Wishlist -->
          <router-link 
            to="/wishlist" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
          >
            <i class="fa-solid fa-heart text-lg"></i>
            <span v-if="wishlistCount > 0" class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ wishlistCount }}
            </span>
          </router-link>

          <!-- Notifications -->
          <router-link 
            to="/notifications" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
          >
            <i class="fa-solid fa-bell text-lg"></i>
            <span v-if="notificationCount > 0" class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ notificationCount }}
            </span>
            <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              
            </span>
          </router-link>

          <!-- User Menu -->
          <div class="relative">
            <button 
              @click="userMenuOpen = !userMenuOpen"
              class="flex items-center space-x-2 text-gray-700 hover:text-blue-600 p-2 rounded-md transition-colors"
            >
              <div class="w-8 h-8 rounded-full bg-gray-100 flex items-center justify-center">
                <i class="fa-solid fa-user text-gray-500 text-sm"></i>
              </div>
              <span class="text-sm font-medium">{{ userName }}</span>
              <i class="fa-solid fa-chevron-down text-xs"></i>
            </button>

            <!-- User Dropdown -->
            <div v-if="userMenuOpen" class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50 border border-gray-200">
              <router-link 
                to="/profile" 
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                @click="userMenuOpen = false"
              >
                <i class="fa-solid fa-user mr-2"></i>
                Profile
              </router-link>
              <!-- <router-link 
                to="/bid-history" 
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                @click="userMenuOpen = false"
              >
                <i class="fa-solid fa-gavel mr-2"></i>
                Bid History
              </router-link>
              <router-link 
                to="/auction-won" 
                class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                @click="userMenuOpen = false"
              >
                <i class="fa-solid fa-trophy mr-2"></i>
                Won Auctions
              </router-link> -->
              <div class="border-t border-gray-100"></div>
              <button 
                @click="logout"
                class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100"
              >
                <i class="fa-solid fa-sign-out-alt mr-2"></i>
                Logout
              </button>
            </div>
          </div>
        </div>

        <!-- Mobile menu button -->
        <div class="md:hidden">
          <button 
            @click="mobileMenuOpen = !mobileMenuOpen"
            class="text-gray-700 hover:text-blue-600 p-2"
          >
            <i class="fa-solid fa-bars text-lg"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile Navigation -->
    <div v-if="mobileMenuOpen" class="md:hidden bg-white border-t border-gray-200">
      <div class="px-2 pt-2 pb-3 space-y-1">
        <router-link 
          to="/" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Home
        </router-link>
        <router-link 
          to="/products" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Products
        </router-link>
        <!-- <router-link 
          to="/auctions" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Live Auctions
        </router-link>
        <router-link 
          to="/my-bids" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          My Bids
        </router-link> -->
        <router-link 
          to="/customer-service" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Support
        </router-link>
        
        <div class="border-t border-gray-200 pt-4 pb-3">
          <div class="flex items-center px-3 pb-3">
            <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
              <i class="fa-solid fa-user text-gray-500"></i>
            </div>
            <div class="ml-3">
              <div class="text-base font-medium text-gray-800">{{ userName }}</div>
              <div class="text-sm text-gray-500">{{ userEmail }}</div>
            </div>
          </div>
          
          <router-link 
            to="/cart" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-shopping-cart mr-3"></i>
            Cart ({{ cartCount }})
          </router-link>
          <router-link 
            to="/saved-items" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-bookmark mr-3"></i>
            Saved Items ({{ savedItemsCount }})
          </router-link>
          <router-link 
            to="/wishlist" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-heart mr-3"></i>
            Wishlist ({{ wishlistCount }})
          </router-link>
          <router-link 
            to="/notifications" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-bell mr-3"></i>
            Notifications ({{ notificationCount }})
          </router-link>
          <router-link 
            to="/profile" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-user mr-3"></i>
            Profile
          </router-link>
          <router-link 
            to="/order-history" 
            class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            <i class="fa-solid fa-shopping-bag mr-3"></i>
            Order History
          </router-link>
          <button 
            @click="logout"
            class="flex items-center w-full text-left px-3 py-2 text-red-600 hover:bg-gray-50 rounded-md text-base font-medium"
          >
            <i class="fa-solid fa-sign-out-alt mr-3"></i>
            Logout
          </button>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useWishlistStore } from '@/stores/wishlist'
import { useCartStore } from '@/stores/cart'
import { useSavedItemsStore } from '@/stores/savedItems'

const router = useRouter()
const authStore = useAuthStore()
const wishlistStore = useWishlistStore()
const cartStore = useCartStore()
const savedItemsStore = useSavedItemsStore()
const mobileMenuOpen = ref(false)
const userMenuOpen = ref(false)

// Get user data from auth store
const userName = computed(() => authStore.fullName || 'User')
const userEmail = computed(() => authStore.user?.email || '')

// Get counts from stores
const cartCount = computed(() => cartStore.totalQuantity)
const savedItemsCount = computed(() => savedItemsStore.itemCount)
const wishlistCount = computed(() => wishlistStore.wishlistCount)
const notificationCount = ref(0) // TODO: Connect to notification store when implemented

// Load data when component mounts or user changes
const loadData = async () => {
  if (authStore.user?.id) {
    await Promise.all([
      wishlistStore.loadWishlist(),
      cartStore.fetchCartItems(),
      savedItemsStore.fetchSavedItems()
    ])
  }
}

onMounted(loadData)

// Watch for user changes and reload data
watch(() => authStore.user?.id, (newUserId) => {
  if (newUserId) {
    loadData()
  } else {
    // Clear data when user logs out
    wishlistStore.clearLocalWishlist()
    cartStore.resetCart()
    savedItemsStore.resetSavedItems()
  }
})

const logout = () => {
  authStore.logout()
  userMenuOpen.value = false
  mobileMenuOpen.value = false
  router.push('/login')
}
</script>
