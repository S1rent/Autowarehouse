<template>
  <nav class="bg-white shadow-lg border-b border-gray-200 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/admin/dashboard" class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-store text-white text-lg"></i>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-800">AdminPanel</h1>
              <p class="text-xs text-gray-500">AutoWarehouse Management</p>
            </div>
          </router-link>
        </div>

        <!-- Desktop Navigation -->
        <div class="hidden md:flex items-center space-x-8">
          <router-link 
            to="/admin/dashboard" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            <i class="fa-solid fa-chart-line mr-2"></i>
            Dashboard
          </router-link>
          <router-link 
            to="/admin/products" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            <i class="fa-solid fa-box mr-2"></i>
            Products
          </router-link>
          <router-link 
            to="/admin/categories" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            <i class="fa-solid fa-tags mr-2"></i>
            Categories
          </router-link>
          <router-link 
            to="/admin/orders" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            <i class="fa-solid fa-shopping-cart mr-2"></i>
            Orders
          </router-link>
          <router-link 
            to="/admin/auctions" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            <i class="fa-solid fa-gavel mr-2"></i>
            Auctions
          </router-link>
        </div>

        <!-- Admin Actions -->
        <div class="hidden md:flex items-center space-x-4">
          <!-- Notifications -->
          <router-link 
            to="/admin/notifications" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
          >
            <i class="fa-solid fa-bell text-lg"></i>
            <span v-if="notificationCount > 0" class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ notificationCount }}
            </span>
          </router-link>

          <!-- Customer Service -->
          <router-link 
            to="/admin/customer-service" 
            class="relative text-gray-700 hover:text-blue-600 p-2 transition-colors"
          >
            <i class="fa-solid fa-headset text-lg"></i>
            <span v-if="supportTickets > 0" class="absolute -top-1 -right-1 bg-orange-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {{ supportTickets }}
            </span>
          </router-link>

          <!-- Admin Menu -->
          <div class="relative">
            <button 
              @click="adminMenuOpen = !adminMenuOpen"
              class="flex items-center space-x-2 text-gray-700 hover:text-blue-600 p-2 rounded-md transition-colors"
            >
              <img 
                src="https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-2.jpg" 
                alt="Admin Avatar" 
                class="w-8 h-8 rounded-full"
              >
              <span class="text-sm font-medium">{{ adminName }}</span>
              <i class="fa-solid fa-chevron-down text-xs"></i>
            </button>

            <!-- Admin Dropdown -->
            <div v-if="adminMenuOpen" class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-50 border border-gray-200">
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
          to="/admin/dashboard" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-chart-line mr-3"></i>
          Dashboard
        </router-link>
        <router-link 
          to="/admin/products" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-box mr-3"></i>
          Products
        </router-link>
        <router-link 
          to="/admin/categories" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-tags mr-3"></i>
          Categories
        </router-link>
        <router-link 
          to="/admin/orders" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-shopping-cart mr-3"></i>
          Orders
        </router-link>
        <router-link 
          to="/admin/auctions" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-gavel mr-3"></i>
          Auctions
        </router-link>
        <router-link 
          to="/admin/customer-service" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-headset mr-3"></i>
          Customer Service
        </router-link>
        <router-link 
          to="/admin/notifications" 
          class="flex items-center px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          <i class="fa-solid fa-bell mr-3"></i>
          Notifications ({{ notificationCount }})
        </router-link>
        
        <div class="border-t border-gray-200 pt-4 pb-3">
          <div class="flex items-center px-3 pb-3">
            <img 
              src="https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-2.jpg" 
              alt="Admin Avatar" 
              class="w-10 h-10 rounded-full"
            >
            <div class="ml-3">
              <div class="text-base font-medium text-gray-800">{{ adminName }}</div>
              <div class="text-sm text-gray-500">{{ adminEmail }}</div>
            </div>
          </div>
          
          <button 
            @click="viewAsUser"
            class="flex items-center w-full text-left px-3 py-2 text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          >
            <i class="fa-solid fa-eye mr-3"></i>
            View as User
          </button>
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
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const mobileMenuOpen = ref(false)
const adminMenuOpen = ref(false)

// Mock admin data - in real app, this would come from auth store
const adminName = ref('Admin User')
const adminEmail = ref('admin@autowarehouse.com')
const notificationCount = ref(5)
const supportTickets = ref(3)

const logout = () => {
  // In real app, clear auth tokens and admin data
  adminMenuOpen.value = false
  mobileMenuOpen.value = false
  router.push('/login')
}

const viewAsUser = () => {
  // Switch to user view - in real app, this would change the auth context
  adminMenuOpen.value = false
  mobileMenuOpen.value = false
  router.push('/')
}
</script>
