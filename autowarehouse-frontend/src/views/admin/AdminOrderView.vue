<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex justify-between items-center">
          <div>
            <h1 class="text-3xl font-bold text-gray-900">Order Management</h1>
            <p class="text-gray-600 mt-1">Kelola pesanan dan status pengiriman</p>
          </div>
          <div class="flex space-x-3">
            <button class="bg-white border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 flex items-center space-x-2">
              <i class="fa-solid fa-download text-sm"></i>
              <span>Export Orders</span>
            </button>
            <button class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center space-x-2">
              <i class="fa-solid fa-plus text-sm"></i>
              <span>Manual Order</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-5 gap-6 mb-8">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-blue-100">
              <i class="fa-solid fa-shopping-cart text-blue-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Orders</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-yellow-100">
              <i class="fa-solid fa-clock text-yellow-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Pending</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.pending }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-orange-100">
              <i class="fa-solid fa-truck text-orange-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Processing</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.processing }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-green-100">
              <i class="fa-solid fa-check-circle text-green-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Completed</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.completed }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-purple-100">
              <i class="fa-solid fa-dollar-sign text-purple-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Revenue</p>
              <p class="text-2xl font-bold text-gray-900">Rp {{ formatPrice(stats.revenue) }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Filters -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between space-y-4 md:space-y-0">
          <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
            <div class="relative">
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Cari order ID atau customer..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
              >
            </div>
            <select 
              v-model="statusFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">All Status</option>
              <option value="pending">Pending</option>
              <option value="processing">Processing</option>
              <option value="shipped">Shipped</option>
              <option value="delivered">Delivered</option>
              <option value="cancelled">Cancelled</option>
            </select>
            <select 
              v-model="paymentFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">All Payment</option>
              <option value="paid">Paid</option>
              <option value="unpaid">Unpaid</option>
              <option value="refunded">Refunded</option>
            </select>
            <input 
              v-model="dateFilter"
              type="date" 
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
          </div>
          <button @click="refreshOrders" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-arrows-rotate"></i>
          </button>
        </div>
      </div>

      <!-- Orders Table -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Orders List</h3>
        </div>
        
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order ID</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Customer</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Products</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Payment</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="order in filteredOrders" :key="order.id" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm font-medium text-gray-900">#{{ order.id }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <img :src="order.customer.avatar" :alt="order.customer.name" class="h-8 w-8 rounded-full">
                    <div class="ml-3">
                      <div class="text-sm font-medium text-gray-900">{{ order.customer.name }}</div>
                      <div class="text-sm text-gray-500">{{ order.customer.email }}</div>
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4">
                  <div class="text-sm text-gray-900">
                    <div v-for="item in order.items.slice(0, 2)" :key="item.id" class="mb-1">
                      {{ item.name }} ({{ item.quantity }}x)
                    </div>
                    <div v-if="order.items.length > 2" class="text-xs text-gray-500">
                      +{{ order.items.length - 2 }} more items
                    </div>
                  </div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm font-medium text-gray-900">Rp {{ formatPrice(order.total) }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span :class="getStatusClass(order.status)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                    {{ getStatusText(order.status) }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <span :class="getPaymentClass(order.paymentStatus)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                    {{ getPaymentText(order.paymentStatus) }}
                  </span>
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                  {{ formatDate(order.createdAt) }}
                </td>
                <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                  <div class="flex space-x-2">
                    <button 
                      @click="viewOrder(order)"
                      class="text-blue-600 hover:text-blue-700"
                      title="View Details"
                    >
                      <i class="fa-solid fa-eye"></i>
                    </button>
                    <button 
                      @click="editOrder(order)"
                      class="text-gray-600 hover:text-gray-700"
                      title="Edit Order"
                    >
                      <i class="fa-solid fa-edit"></i>
                    </button>
                    <button 
                      v-if="order.status === 'processing'"
                      @click="markAsShipped(order)"
                      class="text-green-600 hover:text-green-700"
                      title="Mark as Shipped"
                    >
                      <i class="fa-solid fa-truck"></i>
                    </button>
                    <button 
                      @click="printInvoice(order)"
                      class="text-purple-600 hover:text-purple-700"
                      title="Print Invoice"
                    >
                      <i class="fa-solid fa-print"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Pagination -->
        <div class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
          <div class="text-sm text-gray-700">
            Showing {{ startIndex }} to {{ endIndex }} of {{ totalOrders }} orders
          </div>
          <div class="flex space-x-2">
            <button 
              @click="previousPage"
              :disabled="currentPage === 1"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              Previous
            </button>
            <button 
              v-for="page in visiblePages" 
              :key="page"
              @click="goToPage(page)"
              :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 text-gray-600 hover:bg-gray-50'"
              class="px-3 py-1 rounded text-sm"
            >
              {{ page }}
            </button>
            <button 
              @click="nextPage"
              :disabled="currentPage === totalPages"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              Next
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Order Detail Modal -->
    <div v-if="showDetailModal && selectedOrder" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-4xl w-full max-h-96 overflow-y-auto">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Order Details - #{{ selectedOrder.id }}</h3>
          <button @click="showDetailModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <div class="p-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Customer Info -->
            <div>
              <h4 class="text-sm font-medium text-gray-900 mb-3">Customer Information</h4>
              <div class="bg-gray-50 rounded-lg p-4">
                <div class="flex items-center space-x-3 mb-3">
                  <img :src="selectedOrder.customer.avatar" :alt="selectedOrder.customer.name" class="h-10 w-10 rounded-full">
                  <div>
                    <p class="font-medium text-gray-900">{{ selectedOrder.customer.name }}</p>
                    <p class="text-sm text-gray-600">{{ selectedOrder.customer.email }}</p>
                  </div>
                </div>
                <div class="space-y-2 text-sm">
                  <p><span class="font-medium">Phone:</span> {{ selectedOrder.customer.phone }}</p>
                  <p><span class="font-medium">Address:</span> {{ selectedOrder.shippingAddress }}</p>
                </div>
              </div>
            </div>

            <!-- Order Info -->
            <div>
              <h4 class="text-sm font-medium text-gray-900 mb-3">Order Information</h4>
              <div class="bg-gray-50 rounded-lg p-4 space-y-2 text-sm">
                <p><span class="font-medium">Order Date:</span> {{ formatDateTime(selectedOrder.createdAt) }}</p>
                <p><span class="font-medium">Status:</span> 
                  <span :class="getStatusClass(selectedOrder.status)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full ml-2">
                    {{ getStatusText(selectedOrder.status) }}
                  </span>
                </p>
                <p><span class="font-medium">Payment:</span> 
                  <span :class="getPaymentClass(selectedOrder.paymentStatus)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full ml-2">
                    {{ getPaymentText(selectedOrder.paymentStatus) }}
                  </span>
                </p>
                <p><span class="font-medium">Payment Method:</span> {{ selectedOrder.paymentMethod }}</p>
              </div>
            </div>
          </div>

          <!-- Order Items -->
          <div class="mt-6">
            <h4 class="text-sm font-medium text-gray-900 mb-3">Order Items</h4>
            <div class="bg-gray-50 rounded-lg p-4">
              <div class="space-y-3">
                <div v-for="item in selectedOrder.items" :key="item.id" class="flex items-center justify-between">
                  <div class="flex items-center space-x-3">
                    <img :src="item.image" :alt="item.name" class="h-12 w-12 rounded-lg object-cover">
                    <div>
                      <p class="font-medium text-gray-900">{{ item.name }}</p>
                      <p class="text-sm text-gray-600">Qty: {{ item.quantity }}</p>
                    </div>
                  </div>
                  <div class="text-right">
                    <p class="font-medium text-gray-900">Rp {{ formatPrice(item.price * item.quantity) }}</p>
                    <p class="text-sm text-gray-600">@ Rp {{ formatPrice(item.price) }}</p>
                  </div>
                </div>
              </div>
              
              <div class="border-t border-gray-200 mt-4 pt-4">
                <div class="flex justify-between items-center">
                  <span class="text-lg font-semibold text-gray-900">Total:</span>
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(selectedOrder.total) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Actions -->
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              @click="printInvoice(selectedOrder)"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Print Invoice
            </button>
            <button 
              v-if="selectedOrder.status === 'processing'"
              @click="markAsShipped(selectedOrder)"
              class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700"
            >
              Mark as Shipped
            </button>
            <button 
              @click="showDetailModal = false"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface Customer {
  id: string
  name: string
  email: string
  phone: string
  avatar: string
}

interface OrderItem {
  id: string
  name: string
  image: string
  price: number
  quantity: number
}

interface Order {
  id: string
  customer: Customer
  items: OrderItem[]
  total: number
  status: 'pending' | 'processing' | 'shipped' | 'delivered' | 'cancelled'
  paymentStatus: 'paid' | 'unpaid' | 'refunded'
  paymentMethod: string
  shippingAddress: string
  createdAt: string
}

const searchQuery = ref('')
const statusFilter = ref('')
const paymentFilter = ref('')
const dateFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showDetailModal = ref(false)
const selectedOrder = ref<Order | null>(null)

const stats = ref({
  total: 1247,
  pending: 23,
  processing: 45,
  completed: 1156,
  revenue: 125000000
})

const orders = ref<Order[]>([
  {
    id: 'ORD001',
    customer: {
      id: '1',
      name: 'John Doe',
      email: 'john@example.com',
      phone: '+62812345678',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-3.jpg'
    },
    items: [
      {
        id: '1',
        name: 'iPhone 15 Pro',
        image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fcb55106aa-0ffdc5ff6af0c82fa322.png',
        price: 15000000,
        quantity: 1
      },
      {
        id: '2',
        name: 'MacBook Pro M3',
        image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/ce91227571-81a0fc642b74d0c29cf3.png',
        price: 25000000,
        quantity: 1
      }
    ],
    total: 40000000,
    status: 'processing',
    paymentStatus: 'paid',
    paymentMethod: 'Credit Card',
    shippingAddress: 'Jl. Sudirman No. 123, Jakarta Pusat',
    createdAt: '2024-12-15T10:30:00'
  },
  {
    id: 'ORD002',
    customer: {
      id: '2',
      name: 'Jane Smith',
      email: 'jane@example.com',
      phone: '+62812345679',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-5.jpg'
    },
    items: [
      {
        id: '3',
        name: 'Samsung Galaxy S24',
        image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/bcd852fa37-ddb00ee9183bf59763f5.png',
        price: 12000000,
        quantity: 2
      }
    ],
    total: 24000000,
    status: 'shipped',
    paymentStatus: 'paid',
    paymentMethod: 'Bank Transfer',
    shippingAddress: 'Jl. Thamrin No. 456, Jakarta Pusat',
    createdAt: '2024-12-14T15:20:00'
  },
  {
    id: 'ORD003',
    customer: {
      id: '3',
      name: 'Bob Johnson',
      email: 'bob@example.com',
      phone: '+62812345680',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-4.jpg'
    },
    items: [
      {
        id: '4',
        name: 'iPad Pro',
        image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fcb55106aa-0ffdc5ff6af0c82fa322.png',
        price: 18000000,
        quantity: 1
      }
    ],
    total: 18000000,
    status: 'pending',
    paymentStatus: 'unpaid',
    paymentMethod: 'COD',
    shippingAddress: 'Jl. Gatot Subroto No. 789, Jakarta Selatan',
    createdAt: '2024-12-13T09:45:00'
  }
])

const filteredOrders = computed(() => {
  let filtered = orders.value

  if (searchQuery.value) {
    filtered = filtered.filter(order =>
      order.id.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      order.customer.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      order.customer.email.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(order => order.status === statusFilter.value)
  }

  if (paymentFilter.value) {
    filtered = filtered.filter(order => order.paymentStatus === paymentFilter.value)
  }

  if (dateFilter.value) {
    filtered = filtered.filter(order => 
      order.createdAt.startsWith(dateFilter.value)
    )
  }

  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalOrders = computed(() => orders.value.length)
const totalPages = computed(() => Math.ceil(orders.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalOrders.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric'
  })
}

const formatDateTime = (dateString: string) => {
  return new Date(dateString).toLocaleString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getStatusClass = (status: string) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-800',
    processing: 'bg-blue-100 text-blue-800',
    shipped: 'bg-orange-100 text-orange-800',
    delivered: 'bg-green-100 text-green-800',
    cancelled: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    pending: 'Pending',
    processing: 'Processing',
    shipped: 'Shipped',
    delivered: 'Delivered',
    cancelled: 'Cancelled'
  }
  return texts[status as keyof typeof texts] || status
}

const getPaymentClass = (status: string) => {
  const classes = {
    paid: 'bg-green-100 text-green-800',
    unpaid: 'bg-red-100 text-red-800',
    refunded: 'bg-gray-100 text-gray-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getPaymentText = (status: string) => {
  const texts = {
    paid: 'Paid',
    unpaid: 'Unpaid',
    refunded: 'Refunded'
  }
  return texts[status as keyof typeof texts] || status
}

const viewOrder = (order: Order) => {
  selectedOrder.value = order
  showDetailModal.value = true
}

const editOrder = (order: Order) => {
  alert(`Edit order ${order.id}`)
}

const markAsShipped = (order: Order) => {
  if (confirm(`Mark order ${order.id} as shipped?`)) {
    order.status = 'shipped'
    alert(`Order ${order.id} marked as shipped!`)
    if (showDetailModal.value) {
      showDetailModal.value = false
    }
  }
}

const printInvoice = (order: Order) => {
  alert(`Print invoice for order ${order.id}`)
}

const refreshOrders = () => {
  alert('Orders refreshed!')
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

const goToPage = (page: number) => {
  currentPage.value = page
}

onMounted(() => {
  console.log('Admin Order Management loaded')
})
</script>
