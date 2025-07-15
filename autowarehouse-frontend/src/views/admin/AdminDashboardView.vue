<template>
  <div class="min-h-screen bg-gray-50">
    <AdminNavbar />
    <!-- Admin Sidebar -->
    <div class="flex">
      <!-- Sidebar -->
      <!-- <div class="w-64 bg-white shadow-lg min-h-screen">
        
        <nav class="mt-6">
          <div class="px-6 py-2">
            <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Main</p>
          </div>
          <router-link 
            to="/admin/dashboard" 
            class="flex items-center px-6 py-3 text-gray-700 bg-blue-50 border-r-4 border-blue-600"
          >
            <i class="fa-solid fa-chart-line mr-3"></i>
            Dashboard
          </router-link>
          
          <div class="px-6 py-2 mt-6">
            <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Management</p>
          </div>
          <router-link 
            to="/admin/auctions" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
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
      </div> -->

      <!-- Main Content -->
      <div class="flex-1">

        <!-- Dashboard Content -->
        <main class="p-6">
          <!-- Stats Cards -->
          <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Revenue</p>
                  <p class="text-2xl font-bold text-gray-900">Rp 45.2M</p>
                  <p class="text-sm text-green-600 mt-1">
                    <i class="fa-solid fa-arrow-up mr-1"></i>
                    +12.5% from last month
                  </p>
                </div>
                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-dollar-sign text-green-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Active Auctions</p>
                  <p class="text-2xl font-bold text-gray-900">24</p>
                  <p class="text-sm text-blue-600 mt-1">
                    <i class="fa-solid fa-arrow-up mr-1"></i>
                    +3 new today
                  </p>
                </div>
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-gavel text-blue-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Orders</p>
                  <p class="text-2xl font-bold text-gray-900">1,247</p>
                  <p class="text-sm text-orange-600 mt-1">
                    <i class="fa-solid fa-arrow-up mr-1"></i>
                    +8.2% from last week
                  </p>
                </div>
                <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-shopping-cart text-orange-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Active Users</p>
                  <p class="text-2xl font-bold text-gray-900">8,549</p>
                  <p class="text-sm text-purple-600 mt-1">
                    <i class="fa-solid fa-arrow-up mr-1"></i>
                    +15.3% from last month
                  </p>
                </div>
                <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-users text-purple-600"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Charts Row -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
            <!-- Revenue Chart -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between mb-6">
                <h3 class="text-lg font-semibold text-gray-900">Revenue Overview</h3>
                <select class="text-sm border border-gray-300 rounded-lg px-3 py-1">
                  <option>Last 7 days</option>
                  <option>Last 30 days</option>
                  <option>Last 3 months</option>
                </select>
              </div>
              <div class="h-64 flex items-center justify-center bg-gray-50 rounded-lg">
                <p class="text-gray-500">Revenue Chart Placeholder</p>
              </div>
            </div>

            <!-- Top Categories -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <h3 class="text-lg font-semibold text-gray-900 mb-6">Top Categories</h3>
              <div class="space-y-4">
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-laptop text-blue-600 text-sm"></i>
                    </div>
                    <span class="font-medium text-gray-900">Electronics</span>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 18.5M</p>
                    <p class="text-sm text-gray-500">41% of total</p>
                  </div>
                </div>
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 bg-green-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-car text-green-600 text-sm"></i>
                    </div>
                    <span class="font-medium text-gray-900">Automotive</span>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 12.3M</p>
                    <p class="text-sm text-gray-500">27% of total</p>
                  </div>
                </div>
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 bg-purple-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-gamepad text-purple-600 text-sm"></i>
                    </div>
                    <span class="font-medium text-gray-900">Gaming</span>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 8.7M</p>
                    <p class="text-sm text-gray-500">19% of total</p>
                  </div>
                </div>
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <div class="w-8 h-8 bg-orange-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-tools text-orange-600 text-sm"></i>
                    </div>
                    <span class="font-medium text-gray-900">Tools</span>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 5.7M</p>
                    <p class="text-sm text-gray-500">13% of total</p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Recent Activity -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- Recent Orders -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between mb-6">
                <h3 class="text-lg font-semibold text-gray-900">Recent Orders</h3>
                <router-link to="/admin/orders" class="text-blue-600 hover:text-blue-700 text-sm font-medium">
                  View All
                </router-link>
              </div>
              <div class="space-y-4">
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">#ORD-2024-001</p>
                    <p class="text-sm text-gray-500">John Doe - Gaming Laptop</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 15.5M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-green-100 text-green-800 rounded-full">
                      Completed
                    </span>
                  </div>
                </div>
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">#ORD-2024-002</p>
                    <p class="text-sm text-gray-500">Jane Smith - Headphones</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 2.5M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-blue-100 text-blue-800 rounded-full">
                      Processing
                    </span>
                  </div>
                </div>
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">#ORD-2024-003</p>
                    <p class="text-sm text-gray-500">Mike Johnson - Car Parts</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 8.2M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-yellow-100 text-yellow-800 rounded-full">
                      Pending
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Recent Auctions -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between mb-6">
                <h3 class="text-lg font-semibold text-gray-900">Active Auctions</h3>
                <router-link to="/admin/auctions" class="text-blue-600 hover:text-blue-700 text-sm font-medium">
                  View All
                </router-link>
              </div>
              <div class="space-y-4">
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">NVIDIA RTX 4090</p>
                    <p class="text-sm text-gray-500">47 bids • Ends in 2h 34m</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 18.5M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-red-100 text-red-800 rounded-full">
                      Live
                    </span>
                  </div>
                </div>
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">MacBook Pro M3</p>
                    <p class="text-sm text-gray-500">23 bids • Ends in 5h 12m</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 32.0M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-red-100 text-red-800 rounded-full">
                      Live
                    </span>
                  </div>
                </div>
                <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                  <div>
                    <p class="font-medium text-gray-900">BMW Engine Parts</p>
                    <p class="text-sm text-gray-500">12 bids • Ends in 1d 3h</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp 12.8M</p>
                    <span class="inline-flex px-2 py-1 text-xs font-medium bg-red-100 text-red-800 rounded-full">
                      Live
                    </span>
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
import AdminNavbar from '../../components/AdminNavbar.vue'

// Sample data
const stats = ref({
  revenue: 45200000,
  auctions: 24,
  orders: 1247,
  users: 8549
})

onMounted(() => {
  console.log('Admin Dashboard loaded')
})
</script>

<style scoped>
/* Custom styles for admin dashboard */
</style>
