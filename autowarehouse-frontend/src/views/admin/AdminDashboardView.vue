<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Dashboard</h1>
        <p class="text-gray-600 mt-1">Selamat datang kembali! Berikut ringkasan aktivitas toko Anda.</p>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-blue-100">
              <i class="fa-solid fa-shopping-cart text-blue-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Pesanan</p>
              <p class="text-2xl font-bold text-gray-900">1,247</p>
              <p class="text-sm text-green-600">+12% dari bulan lalu</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-green-100">
              <i class="fa-solid fa-dollar-sign text-green-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Pendapatan</p>
              <p class="text-2xl font-bold text-gray-900">Rp 45.2M</p>
              <p class="text-sm text-green-600">+8% dari bulan lalu</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-purple-100">
              <i class="fa-solid fa-box text-purple-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Produk</p>
              <p class="text-2xl font-bold text-gray-900">856</p>
              <p class="text-sm text-blue-600">+23 produk baru</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-yellow-100">
              <i class="fa-solid fa-users text-yellow-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Pelanggan</p>
              <p class="text-2xl font-bold text-gray-900">2,847</p>
              <p class="text-sm text-green-600">+156 pelanggan baru</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Charts and Recent Activity -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 mb-8">
        <!-- Sales Chart -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-lg font-semibold text-gray-900">Penjualan Bulanan</h3>
            <select class="text-sm border border-gray-300 rounded-lg px-3 py-1">
              <option>6 Bulan Terakhir</option>
              <option>12 Bulan Terakhir</option>
            </select>
          </div>
          <div class="h-64 flex items-center justify-center bg-gray-50 rounded-lg">
            <div class="text-center">
              <i class="fa-solid fa-chart-line text-4xl text-gray-400 mb-2"></i>
              <p class="text-gray-500">Chart akan ditampilkan di sini</p>
            </div>
          </div>
        </div>

        <!-- Top Products -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-6">Produk Terlaris</h3>
          <div class="space-y-4">
            <div v-for="product in topProducts" :key="product.id" class="flex items-center space-x-4">
              <img :src="product.image" :alt="product.name" class="w-12 h-12 rounded-lg object-cover">
              <div class="flex-1">
                <p class="font-medium text-gray-900">{{ product.name }}</p>
                <p class="text-sm text-gray-500">{{ product.sold }} terjual</p>
              </div>
              <div class="text-right">
                <p class="font-semibold text-gray-900">Rp {{ formatPrice(product.revenue) }}</p>
                <p class="text-sm text-green-600">+{{ product.growth }}%</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Orders and Quick Actions -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Recent Orders -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between mb-6">
            <h3 class="text-lg font-semibold text-gray-900">Pesanan Terbaru</h3>
            <router-link to="/admin/orders" class="text-blue-600 hover:text-blue-700 text-sm font-medium">
              Lihat Semua
            </router-link>
          </div>
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead class="bg-gray-50">
                <tr>
                  <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Order ID</th>
                  <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Pelanggan</th>
                  <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Total</th>
                  <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-200">
                <tr v-for="order in recentOrders" :key="order.id" class="hover:bg-gray-50">
                  <td class="px-4 py-3 text-sm font-medium text-gray-900">#{{ order.id }}</td>
                  <td class="px-4 py-3 text-sm text-gray-900">{{ order.customer }}</td>
                  <td class="px-4 py-3 text-sm font-medium text-gray-900">Rp {{ formatPrice(order.total) }}</td>
                  <td class="px-4 py-3">
                    <span :class="getStatusClass(order.status)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                      {{ order.status }}
                    </span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-6">Aksi Cepat</h3>
          <div class="space-y-4">
            <router-link 
              to="/admin/products/add" 
              class="flex items-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
            >
              <div class="p-2 bg-blue-100 rounded-lg">
                <i class="fa-solid fa-plus text-blue-600"></i>
              </div>
              <div class="ml-3">
                <p class="font-medium text-gray-900">Tambah Produk</p>
                <p class="text-sm text-gray-500">Tambah produk baru</p>
              </div>
            </router-link>

            <router-link 
              to="/admin/orders" 
              class="flex items-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
            >
              <div class="p-2 bg-green-100 rounded-lg">
                <i class="fa-solid fa-clipboard-list text-green-600"></i>
              </div>
              <div class="ml-3">
                <p class="font-medium text-gray-900">Kelola Pesanan</p>
                <p class="text-sm text-gray-500">{{ pendingOrders }} pesanan pending</p>
              </div>
            </router-link>

            <router-link 
              to="/admin/auctions" 
              class="flex items-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
            >
              <div class="p-2 bg-purple-100 rounded-lg">
                <i class="fa-solid fa-gavel text-purple-600"></i>
              </div>
              <div class="ml-3">
                <p class="font-medium text-gray-900">Kelola Auction</p>
                <p class="text-sm text-gray-500">{{ activeAuctions }} auction aktif</p>
              </div>
            </router-link>

            <router-link 
              to="/admin/customer-service" 
              class="flex items-center p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
            >
              <div class="p-2 bg-yellow-100 rounded-lg">
                <i class="fa-solid fa-headset text-yellow-600"></i>
              </div>
              <div class="ml-3">
                <p class="font-medium text-gray-900">Customer Service</p>
                <p class="text-sm text-gray-500">{{ unreadMessages }} pesan baru</p>
              </div>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

// Sample data
const topProducts = ref([
  {
    id: 1,
    name: 'iPhone 15 Pro',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/6687b03202-5912a40fe3b90038b9ed.png',
    sold: 156,
    revenue: 2800000000,
    growth: 15
  },
  {
    id: 2,
    name: 'MacBook Pro M3',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/4d04b8d095-8cfe8cd073db46d980c0.png',
    sold: 89,
    revenue: 2400000000,
    growth: 8
  },
  {
    id: 3,
    name: 'Samsung Galaxy S24',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fef1ee1be2-02b82c0ebfa8dfe6e145.png',
    sold: 134,
    revenue: 1800000000,
    growth: 22
  }
])

const recentOrders = ref([
  { id: 'ORD001', customer: 'John Doe', total: 15000000, status: 'Pending' },
  { id: 'ORD002', customer: 'Jane Smith', total: 8500000, status: 'Processing' },
  { id: 'ORD003', customer: 'Bob Johnson', total: 12000000, status: 'Shipped' },
  { id: 'ORD004', customer: 'Alice Brown', total: 6500000, status: 'Delivered' },
  { id: 'ORD005', customer: 'Charlie Wilson', total: 9800000, status: 'Pending' }
])

const pendingOrders = ref(12)
const activeAuctions = ref(8)
const unreadMessages = ref(5)

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    'Pending': 'bg-yellow-100 text-yellow-800',
    'Processing': 'bg-blue-100 text-blue-800',
    'Shipped': 'bg-purple-100 text-purple-800',
    'Delivered': 'bg-green-100 text-green-800',
    'Cancelled': 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

onMounted(() => {
  console.log('Admin Dashboard loaded')
})
</script>
