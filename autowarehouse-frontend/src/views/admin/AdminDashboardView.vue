<template>
  <div class="min-h-screen bg-gray-50">
    <AdminNavbar />
    
    <!-- Main Content -->
    <div class="flex-1">
      <!-- Dashboard Content -->
      <main class="p-6">
        <!-- Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <!-- Total Penjualan Bulan Ini -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600">Total Penjualan Bulan Ini</p>
                <p class="text-2xl font-bold text-gray-900">Rp 45.2M</p>
                <p class="text-sm text-green-600 mt-1">
                  <i class="fa-solid fa-arrow-up mr-1"></i>
                  +12.5%
                </p>
              </div>
              <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-dollar-sign text-green-600"></i>
              </div>
            </div>
          </div>

          <!-- Jumlah Pesanan Bulan Ini -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600">Jumlah Pesanan Bulan Ini</p>
                <p class="text-2xl font-bold text-gray-900">1,247</p>
                <p class="text-sm text-blue-600 mt-1">
                  <i class="fa-solid fa-arrow-up mr-1"></i>
                  +8.2%
                </p>
              </div>
              <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-shopping-cart text-blue-600"></i>
              </div>
            </div>
          </div>

          <!-- Produk Terjual -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600">Produk Terjual</p>
                <p class="text-2xl font-bold text-gray-900">3,456</p>
                <p class="text-sm text-purple-600 mt-1">
                  <i class="fa-solid fa-arrow-up mr-1"></i>
                  +15.1%
                </p>
              </div>
              <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-box text-purple-600"></i>
              </div>
            </div>
          </div>

          <!-- Stok Hampir Habis -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-600">Stok Hampir Habis</p>
                <p class="text-2xl font-bold text-gray-900">12</p>
                <p class="text-sm text-red-600 mt-1">
                  <i class="fa-solid fa-exclamation-triangle mr-1"></i>
                  Perlu Perhatian
                </p>
              </div>
              <div class="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-exclamation-triangle text-red-600"></i>
              </div>
            </div>
          </div>
        </div>

        <!-- Alert Cards Row -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-8">
          <!-- Order Baru / Belum Diproses -->
          <div class="bg-orange-50 border border-orange-200 rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-clock text-orange-600"></i>
                </div>
                <div>
                  <h3 class="text-lg font-semibold text-orange-900">Order Baru / Belum Diproses</h3>
                  <p class="text-orange-700">23 pesanan menunggu untuk diproses</p>
                </div>
              </div>
              <button class="bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-lg font-medium transition-colors">
                Lihat Pesanan
              </button>
            </div>
          </div>

          <!-- Customer Live Chat -->
          <div class="bg-blue-50 border border-blue-200 rounded-xl p-6">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-comments text-blue-600"></i>
                </div>
                <div>
                  <h3 class="text-lg font-semibold text-blue-900">Customer Live Chat</h3>
                  <p class="text-blue-700">Reminder: jangan lupa untuk selalu buka live chat untuk menanggapi keluhan atau pertanyaan customer.</p>
                </div>
              </div>
              <button class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg font-medium transition-colors">
                Buka Chat
              </button>
            </div>
          </div>
        </div>

        <!-- Bottom Row with 3 sections -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- 5 Produk Terlaris -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between mb-6">
              <div>
                <h3 class="text-lg font-semibold text-gray-900">5 Produk Terlaris</h3>
                <p class="text-sm text-gray-600">Berdasarkan penjualan bulan ini</p>
              </div>
              <button class="text-blue-600 hover:text-blue-700 text-sm font-medium flex items-center">
                Lihat Semua Produk
                <i class="fa-solid fa-arrow-right ml-1"></i>
              </button>
            </div>
            
            <div class="space-y-3">
              <!-- Product 1 -->
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-yellow-500 text-white rounded-lg flex items-center justify-center font-bold text-xs">
                    1
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Wireless Headphones Pro</p>
                    <p class="text-xs text-gray-500">Kategori: Electronics</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">234 terjual</p>
                  <p class="text-xs text-gray-500">Rp 5.8M</p>
                </div>
              </div>

              <!-- Product 2 -->
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-gray-400 text-white rounded-lg flex items-center justify-center font-bold text-xs">
                    2
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Smart Watch Series 5</p>
                    <p class="text-xs text-gray-500">Kategori: Wearables</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">189 terjual</p>
                  <p class="text-xs text-gray-500">Rp 4.2M</p>
                </div>
              </div>

              <!-- Product 3 -->
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-orange-500 text-white rounded-lg flex items-center justify-center font-bold text-xs">
                    3
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Gaming Mechanical Keyboard</p>
                    <p class="text-xs text-gray-500">Kategori: Gaming</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">156 terjual</p>
                  <p class="text-xs text-gray-500">Rp 2.1M</p>
                </div>
              </div>

              <!-- Product 4 -->
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-gray-500 text-white rounded-lg flex items-center justify-center font-bold text-xs">
                    4
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">USB-C Hub 7-in-1</p>
                    <p class="text-xs text-gray-500">Kategori: Accessories</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">143 terjual</p>
                  <p class="text-xs text-gray-500">Rp 1.8M</p>
                </div>
              </div>

              <!-- Product 5 -->
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-gray-600 text-white rounded-lg flex items-center justify-center font-bold text-xs">
                    5
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Portable Power Bank 20000mAh</p>
                    <p class="text-xs text-gray-500">Kategori: Mobile</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">128 terjual</p>
                  <p class="text-xs text-gray-500">Rp 1.5M</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Last 5 Produk Terjual -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between mb-6">
              <div>
                <h3 class="text-lg font-semibold text-gray-900">Last 5 Produk Terjual</h3>
                <p class="text-sm text-gray-600">Produk yang baru saja terjual</p>
              </div>
              <button class="text-blue-600 hover:text-blue-700 text-sm font-medium flex items-center">
                Lihat Semua
                <i class="fa-solid fa-arrow-right ml-1"></i>
              </button>
            </div>
            
            <div class="space-y-3">
              <!-- Recent Sale 1 -->
              <div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-green-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-check text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">MacBook Pro M3 Max</p>
                    <p class="text-xs text-gray-500">2 menit yang lalu</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 45.5M</p>
                  <p class="text-xs text-green-600">Terjual</p>
                </div>
              </div>

              <!-- Recent Sale 2 -->
              <div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-green-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-check text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">iPhone 15 Pro Max</p>
                    <p class="text-xs text-gray-500">5 menit yang lalu</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 22.9M</p>
                  <p class="text-xs text-green-600">Terjual</p>
                </div>
              </div>

              <!-- Recent Sale 3 -->
              <div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-green-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-check text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Gaming Chair RGB</p>
                    <p class="text-xs text-gray-500">12 menit yang lalu</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 3.2M</p>
                  <p class="text-xs text-green-600">Terjual</p>
                </div>
              </div>

              <!-- Recent Sale 4 -->
              <div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-green-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-check text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">Wireless Earbuds Pro</p>
                    <p class="text-xs text-gray-500">18 menit yang lalu</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 2.8M</p>
                  <p class="text-xs text-green-600">Terjual</p>
                </div>
              </div>

              <!-- Recent Sale 5 -->
              <div class="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-green-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-check text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">4K Monitor 32 inch</p>
                    <p class="text-xs text-gray-500">25 menit yang lalu</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 8.5M</p>
                  <p class="text-xs text-green-600">Terjual</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Last 5 Transaksi -->
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between mb-6">
              <div>
                <h3 class="text-lg font-semibold text-gray-900">Last 5 Transaksi</h3>
                <p class="text-sm text-gray-600">Transaksi terbaru</p>
              </div>
              <button class="text-blue-600 hover:text-blue-700 text-sm font-medium flex items-center">
                Lihat Semua
                <i class="fa-solid fa-arrow-right ml-1"></i>
              </button>
            </div>
            
            <div class="space-y-3">
              <!-- Transaction 1 -->
              <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-credit-card text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">#TRX-2024-1247</p>
                    <p class="text-xs text-gray-500">John Doe - 3 items</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 15.2M</p>
                  <span class="inline-flex px-2 py-1 text-xs font-medium bg-green-100 text-green-800 rounded-full">
                    Selesai
                  </span>
                </div>
              </div>

              <!-- Transaction 2 -->
              <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-credit-card text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">#TRX-2024-1246</p>
                    <p class="text-xs text-gray-500">Jane Smith - 1 item</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 22.9M</p>
                  <span class="inline-flex px-2 py-1 text-xs font-medium bg-yellow-100 text-yellow-800 rounded-full">
                    Proses
                  </span>
                </div>
              </div>

              <!-- Transaction 3 -->
              <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-credit-card text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">#TRX-2024-1245</p>
                    <p class="text-xs text-gray-500">Mike Johnson - 2 items</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 8.7M</p>
                  <span class="inline-flex px-2 py-1 text-xs font-medium bg-green-100 text-green-800 rounded-full">
                    Selesai
                  </span>
                </div>
              </div>

              <!-- Transaction 4 -->
              <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-credit-card text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">#TRX-2024-1244</p>
                    <p class="text-xs text-gray-500">Sarah Wilson - 4 items</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 12.4M</p>
                  <span class="inline-flex px-2 py-1 text-xs font-medium bg-blue-100 text-blue-800 rounded-full">
                    Dikirim
                  </span>
                </div>
              </div>

              <!-- Transaction 5 -->
              <div class="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <div class="flex items-center space-x-3">
                  <div class="w-6 h-6 bg-blue-500 rounded-full flex items-center justify-center">
                    <i class="fa-solid fa-credit-card text-white text-xs"></i>
                  </div>
                  <div>
                    <p class="font-medium text-gray-900 text-sm">#TRX-2024-1243</p>
                    <p class="text-xs text-gray-500">David Brown - 1 item</p>
                  </div>
                </div>
                <div class="text-right">
                  <p class="font-semibold text-gray-900 text-sm">Rp 3.2M</p>
                  <span class="inline-flex px-2 py-1 text-xs font-medium bg-red-100 text-red-800 rounded-full">
                    Pending
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

// Dashboard data
const dashboardStats = ref({
  totalSales: 45200000,
  totalOrders: 1247,
  productsSold: 3456,
  lowStock: 12,
  pendingOrders: 23,
  unreadMessages: 5
})

const topProducts = ref([
  { rank: 1, name: 'Wireless Headphones Pro', category: 'Electronics', sold: 234, revenue: 5800000 },
  { rank: 2, name: 'Smart Watch Series 5', category: 'Wearables', sold: 189, revenue: 4200000 },
  { rank: 3, name: 'Gaming Mechanical Keyboard', category: 'Gaming', sold: 156, revenue: 2100000 },
  { rank: 4, name: 'USB-C Hub 7-in-1', category: 'Accessories', sold: 143, revenue: 1800000 },
  { rank: 5, name: 'Portable Power Bank 20000mAh', category: 'Mobile', sold: 128, revenue: 1500000 },
  { rank: 6, name: 'Bluetooth Speaker Premium', category: 'Audio', sold: 115, revenue: 1300000 },
  { rank: 7, name: 'Wireless Mouse Gaming', category: 'Gaming', sold: 98, revenue: 1100000 },
  { rank: 8, name: '4K Webcam HD', category: 'Electronics', sold: 87, revenue: 980000 },
  { rank: 9, name: 'Laptop Stand Adjustable', category: 'Accessories', sold: 76, revenue: 850000 },
  { rank: 10, name: 'LED Desk Lamp Smart', category: 'Home & Office', sold: 65, revenue: 720000 }
])

onMounted(() => {
  console.log('Admin Dashboard loaded with new design')
})
</script>

<style scoped>
/* Custom styles for admin dashboard */
.transition-colors {
  transition: background-color 0.2s ease-in-out;
}
</style>
