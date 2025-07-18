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
            <button 
              @click="showExportModal = true"
              class="bg-white border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 flex items-center space-x-2"
            >
              <i class="fa-solid fa-download text-sm"></i>
              <span>Export Orders</span>
            </button>
            <button 
              @click="showBulkModal = true"
              :disabled="selectedOrders.length === 0"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
            >
              <i class="fa-solid fa-edit text-sm"></i>
              <span>Bulk Actions ({{ selectedOrders.length }})</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-6 gap-6 mb-8">
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
              <i class="fa-solid fa-money-bill-wave text-yellow-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Pending Refund</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.pendingRefund }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-orange-100">
              <i class="fa-solid fa-cog text-orange-600 text-xl"></i>
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
            <div class="p-3 rounded-full bg-red-100">
              <i class="fa-solid fa-times-circle text-red-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Cancelled</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.cancelled }}</p>
            </div>
          </div>
        </div>

        
      </div>

      <div class="mb-8 bg-white rounded-lg shadow-sm border border-gray-200 p-6">
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
              <option value="PENDING">Pending</option>
              <option value="CONFIRMED">Confirmed</option>
              <option value="SHIPPED">Shipped</option>
              <option value="DELIVERED">Delivered</option>
              <option value="CANCELLED">Cancelled</option>
              <option value="REFUNDED">Refunded</option>
            </select>
            <select 
              v-model="paymentFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">All Payment</option>
              <!-- <option value="PENDING">Pending</option> -->
              <option value="PAID">Paid</option>
              <!-- <option value="FAILED">Failed</option> -->
              <option value="REFUNDED">Refunded</option>
              <!-- <option value="PARTIALLY_REFUNDED">Partially Refunded</option> -->
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

      <!-- Orders Table - Desktop View -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200 hidden lg:block">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Orders List</h3>
        </div>
        
        <div class="overflow-x-auto">
          <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  <input type="checkbox" class="rounded border-gray-300" @change="toggleSelectAll">
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Order ID</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Customer</th>
                <!-- <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Products</th> -->
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Payment</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
              <!-- Loading State -->
              <tr v-if="loading">
                <td colspan="9" class="px-6 py-12 text-center">
                  <div class="flex flex-col items-center">
                    <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mb-4"></div>
                    <p class="text-gray-500">Loading orders...</p>
                  </div>
                </td>
              </tr>
              
              <!-- Error State -->
              <tr v-else-if="error">
                <td colspan="9" class="px-6 py-12 text-center">
                  <div class="flex flex-col items-center">
                    <i class="fa-solid fa-exclamation-triangle text-red-500 text-3xl mb-4"></i>
                    <p class="text-red-600 mb-2">{{ error }}</p>
                    <button @click="loadOrders()" class="text-blue-600 hover:text-blue-700">
                      Try again
                    </button>
                  </div>
                </td>
              </tr>
              
              <!-- Empty State -->
              <tr v-else-if="orders.length === 0">
                <td colspan="9" class="px-6 py-12 text-center">
                  <div class="flex flex-col items-center">
                    <i class="fa-solid fa-shopping-cart text-gray-400 text-3xl mb-4"></i>
                    <p class="text-gray-500 mb-2">No orders found</p>
                    <p class="text-gray-400 text-sm">Orders will appear here once customers place them</p>
                  </div>
                </td>
              </tr>
              
              <!-- Orders Data -->
              <tr v-else v-for="order in filteredOrders" :key="order.id" class="hover:bg-gray-50">
                <td class="px-6 py-4 whitespace-nowrap">
                  <input 
                    type="checkbox" 
                    :value="order.id" 
                    v-model="selectedOrders" 
                    class="rounded border-gray-300"
                  >
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-sm font-medium text-gray-900">#{{ order.id }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="flex items-center">
                    <!-- <img :src="order.customer.avatar" :alt="order.customer.name" class="h-8 w-8 rounded-full"> -->
                    <div class="ml-3">
                      <div class="text-sm font-medium text-gray-900">{{ order.customer.name }}</div>
                      <div class="text-sm text-gray-500">{{ order.customer.email }}</div>
                    </div>
                  </div>
                </td>
                <!-- <td class="px-6 py-4">
                  <div class="text-sm text-gray-900">
                    <div v-for="item in order.items.slice(0, 2)" :key="item.id" class="mb-1">
                      {{ item.name }} ({{ item.quantity }}x)
                    </div>
                    <div v-if="order.items.length > 2" class="text-xs text-gray-500">
                      +{{ order.items.length - 2 }} more items
                    </div>
                  </div>
                </td> -->
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
                      @click="viewOrderDetails(order)"
                      class="text-blue-600 hover:text-blue-700"
                      title="View Details"
                    >
                      <i class="fa-solid fa-eye"></i>
                    </button>
                    <!-- <button 
                      @click="editOrder(order)"
                      class="text-gray-600 hover:text-gray-700"
                      title="Edit Order"
                    >
                      <i class="fa-solid fa-edit"></i>
                    </button> -->
                    <!-- <button 
                      v-if="order.status === 'processing'"
                      @click="markAsShipped(order)"
                      class="text-green-600 hover:text-green-700"
                      title="Mark as Shipped"
                    >
                      <i class="fa-solid fa-truck"></i>
                    </button> -->
                    <button 
                      v-if="order.paymentStatus === 'pending_refund'"
                      @click="processRefund(order)"
                      class="text-yellow-600 hover:text-yellow-700"
                      title="Process Refund"
                    >
                      <i class="fa-solid fa-money-bill-wave"></i>
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

      <!-- Orders Cards - Mobile/Tablet View -->
      <div class="lg:hidden space-y-4">
        <div v-for="order in filteredOrders" :key="order.id" class="bg-white rounded-lg shadow-sm border border-gray-200 p-4">
          <div class="flex items-start justify-between mb-3">
            <div class="flex items-center space-x-3">
              <input 
                type="checkbox" 
                :value="order.id" 
                v-model="selectedOrders" 
                class="rounded border-gray-300 mt-1"
              >
              <div>
                <div class="text-sm font-medium text-gray-900">#{{ order.id }}</div>
                <div class="text-xs text-gray-500">{{ formatDate(order.createdAt) }}</div>
              </div>
            </div>
            <div class="flex space-x-1">
              <span :class="getStatusClass(order.status)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                {{ getStatusText(order.status) }}
              </span>
            </div>
          </div>
          
          <div class="flex items-center space-x-3 mb-3">
            <img :src="order.customer.avatar" :alt="order.customer.name" class="h-10 w-10 rounded-full">
            <div class="flex-1 min-w-0">
              <div class="text-sm font-medium text-gray-900 truncate">{{ order.customer.name }}</div>
              <div class="text-xs text-gray-500 truncate">{{ order.customer.email }}</div>
            </div>
          </div>
          
          <div class="mb-3">
            <div class="text-xs text-gray-500 mb-1">Items:</div>
            <div class="text-sm text-gray-900">
              <div v-for="item in order.items.slice(0, 2)" :key="item.id" class="mb-1">
                {{ item.name }} ({{ item.quantity }}x)
              </div>
              <div v-if="order.items.length > 2" class="text-xs text-gray-500">
                +{{ order.items.length - 2 }} more items
              </div>
            </div>
          </div>
          
          <div class="flex items-center justify-between mb-3">
            <div>
              <div class="text-xs text-gray-500">Total</div>
              <div class="text-sm font-medium text-gray-900">Rp {{ formatPrice(order.total) }}</div>
            </div>
            <div>
              <div class="text-xs text-gray-500">Payment</div>
              <span :class="getPaymentClass(order.paymentStatus)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                {{ getPaymentText(order.paymentStatus) }}
              </span>
            </div>
          </div>
          
          <div class="flex justify-between items-center pt-3 border-t border-gray-200">
            <div class="flex space-x-3">
              <button 
                @click="viewOrderDetails(order)"
                class="text-blue-600 hover:text-blue-700 text-sm"
              >
                <i class="fa-solid fa-eye mr-1"></i>
                <span class="hidden sm:inline">View</span>
              </button>
              <button 
                @click="editOrder(order)"
                class="text-gray-600 hover:text-gray-700 text-sm"
              >
                <i class="fa-solid fa-edit mr-1"></i>
                <span class="hidden sm:inline">Edit</span>
              </button>
              <button 
                @click="printInvoice(order)"
                class="text-purple-600 hover:text-purple-700 text-sm"
              >
                <i class="fa-solid fa-print mr-1"></i>
                <span class="hidden sm:inline">Invoice</span>
              </button>
            </div>
            <button 
              v-if="order.status === 'processing'"
              @click="markAsShipped(order)"
              class="px-3 py-1 bg-green-600 text-white text-xs rounded hover:bg-green-700"
            >
              Ship
            </button>
          </div>
        </div>
        
        <!-- Mobile Pagination -->
        <div class="flex items-center justify-between bg-white rounded-lg border border-gray-200 p-4">
          <div class="text-sm text-gray-700">
            {{ startIndex }}-{{ endIndex }} of {{ totalOrders }}
          </div>
          <div class="flex space-x-2">
            <button 
              @click="previousPage"
              :disabled="currentPage === 1"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              <i class="fa-solid fa-chevron-left"></i>
            </button>
            <span class="px-3 py-1 text-sm text-gray-700">{{ currentPage }}/{{ totalPages }}</span>
            <button 
              @click="nextPage"
              :disabled="currentPage === totalPages"
              class="px-3 py-1 border border-gray-300 rounded text-sm text-gray-600 hover:bg-gray-50 disabled:opacity-50"
            >
              <i class="fa-solid fa-chevron-right"></i>
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

    <!-- Export Modal -->
    <div v-if="showExportModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Export Orders</h3>
          <button @click="showExportModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <div class="p-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Status Filter</label>
              <select v-model="exportFilters.status" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
                <option value="">All Status</option>
                <option value="PENDING">Pending</option>
                <option value="CONFIRMED">Confirmed</option>
                <option value="SHIPPED">Shipped</option>
                <option value="DELIVERED">Delivered</option>
                <option value="CANCELLED">Cancelled</option>
              </select>
            </div>
            
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Start Date</label>
                <input v-model="exportFilters.startDate" type="date" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">End Date</label>
                <input v-model="exportFilters.endDate" type="date" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
              </div>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Format</label>
              <select v-model="exportFilters.format" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
                <option value="csv">CSV</option>
                <option value="excel">Excel</option>
              </select>
            </div>
          </div>
          
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              @click="showExportModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              @click="exportOrders"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Export
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Bulk Action Modal -->
    <div v-if="showBulkModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Bulk Actions</h3>
          <button @click="showBulkModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <div class="p-6">
          <div class="space-y-4">
            <div>
              <p class="text-sm text-gray-600 mb-4">{{ selectedOrders.length }} orders selected</p>
              
              <label class="block text-sm font-medium text-gray-700 mb-2">Action</label>
              <select v-model="bulkAction.action" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
                <option value="">Select Action</option>
                <option value="CONFIRMED">Mark as Confirmed</option>
                <option value="SHIPPED">Mark as Shipped</option>
                <option value="DELIVERED">Mark as Delivered</option>
                <option value="CANCELLED">Mark as Cancelled</option>
              </select>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Notes (Optional)</label>
              <textarea 
                v-model="bulkAction.notes" 
                rows="3" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                placeholder="Add notes for this bulk action..."
              ></textarea>
            </div>
          </div>
          
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              @click="showBulkModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              @click="executeBulkAction"
              :disabled="!bulkAction.action"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Execute
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService } from '../../services/api'
import { useNotifications } from '@/composables/useNotifications'

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
  orderNumber?: string
  customer: Customer
  customerName?: string
  customerEmail?: string
  customerPhone?: string
  items: OrderItem[]
  total: number
  totalAmount?: number
  subtotal?: number
  shippingCost?: number
  taxAmount?: number
  status: 'pending' | 'confirmed' | 'processing' | 'shipped' | 'delivered' | 'cancelled'
  paymentStatus: 'paid' | 'unpaid' | 'refunded' | 'pending_refund'
  paymentMethod: string
  shippingAddress: string
  trackingNumber?: string
  createdAt: string
  cancelReason?: string
}

const searchQuery = ref('')
const statusFilter = ref('')
const paymentFilter = ref('')
const dateFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const router = useRouter()
const showDetailModal = ref(false)
const selectedOrder = ref<Order | null>(null)
const showExportModal = ref(false)
const showBulkModal = ref(false)
const selectedOrders = ref<string[]>([])

const exportFilters = ref({
  status: '',
  startDate: '',
  endDate: '',
  format: 'csv'
})

const bulkAction = ref({
  action: '',
  notes: ''
})

const stats = ref({
  total: 0,
  pendingRefund: 0,
  processing: 0,
  completed: 0,
  cancelled: 0,
  revenue: 0
})

const orders = ref<Order[]>([])
const loading = ref(false)
const error = ref('')

const { success, error: showError } = useNotifications()

// Load orders from API with search/filter support
const loadOrders = async (useFilters = false) => {
  loading.value = true
  error.value = ''
  try {
    console.log('Loading orders from API...')
    
    let response
    if (useFilters && (searchQuery.value || statusFilter.value || paymentFilter.value || dateFilter.value)) {
      // Use search API with filters
      const searchParams = {
        query: searchQuery.value || undefined,
        status: statusFilter.value ? statusFilter.value.toUpperCase() : undefined,
        paymentStatus: paymentFilter.value ? paymentFilter.value.toUpperCase() : undefined,
        startDate: dateFilter.value || undefined,
        endDate: dateFilter.value || undefined,
        page: 0,
        size: 1000 // Get all for now, implement proper pagination later
      }
      
      console.log('Using search API with params:', searchParams)
      const searchResult = await apiService.searchOrders(searchParams)
      response = searchResult.orders
    } else {
      // Use regular getAllOrders
      response = await apiService.getAllOrders()
    }
    
    console.log('API response:', response)
    console.log('Response length:', response.length)
    
    if (!Array.isArray(response)) {
      console.error('Response is not an array:', response)
      orders.value = []
      return
    }
    
    orders.value = response.map((order: any) => {
      console.log('Processing order:', order)
      
      // Handle different response structures
      const user = order.user || { firstName: order.customerName || 'Unknown', lastName: '', email: '', phoneNumber: '' }
      const orderItems = order.orderItems || order.items || []
      
      return {
        id: order.id.toString(),
        customer: {
          id: user.id?.toString() || '0',
          name: user.firstName && user.lastName ? `${user.firstName} ${user.lastName}` : (order.customerName || 'Unknown Customer'),
          email: user.email || '',
          phone: user.phoneNumber || '',
          avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-3.jpg'
        },
        items: orderItems.map((item: any) => ({
          id: item.id?.toString() || '0',
          name: item.productName || item.product?.name || 'Unknown Product',
          image: item.product?.imageUrl || item.productImages?.[0] || 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fcb55106aa-0ffdc5ff6af0c82fa322.png',
          price: item.productPrice || item.price || 0,
          quantity: item.quantity || 1
        })),
        total: order.totalAmount || 0,
        status: (order.status || 'pending').toLowerCase(),
        paymentStatus: (order.paymentStatus || 'unpaid').toLowerCase(),
        paymentMethod: order.paymentMethod || 'Unknown',
        shippingAddress: order.shippingAddress || 'No address provided',
        createdAt: order.createdAt || new Date().toISOString()
      }
    })
    
    console.log('Mapped orders:', orders.value)
    console.log('Orders count after mapping:', orders.value.length)
    
    // Update stats
    updateStats()
  } catch (err) {
    console.error('Failed to load orders:', err)
    error.value = 'Failed to load orders. Please try again.'
    orders.value = []
  } finally {
    loading.value = false
  }
}

// Update statistics
const updateStats = () => {
  const total = orders.value.length
  const pendingRefund = orders.value.filter(o => o.paymentStatus === 'pending_refund').length
  const processing = orders.value.filter(o => o.status === 'confirmed' && o.paymentStatus === 'paid').length
  const completed = orders.value.filter(o => o.status === 'delivered').length
  const cancelled = orders.value.filter(o => o.status === 'cancelled' && o.paymentStatus === 'refunded').length
  const revenue = orders.value
    .filter(o => o.paymentStatus === 'paid')
    .reduce((sum, o) => sum + o.total, 0)
  
  stats.value = { total, pendingRefund, processing, completed, cancelled, revenue }
}

const filteredOrders = computed(() => {
  console.log('Computing paginated orders, total orders:', orders.value.length)
  
  // Since filtering is now done in backend, just paginate the results
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  const paginatedOrders = orders.value.slice(start, end)
  
  console.log('Paginated orders:', paginatedOrders)
  return paginatedOrders
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

const viewOrderDetails = (order: Order) => {
  router.push({ name: 'AdminOrderDetail', params: { id: order.id } })
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

const printInvoice = async (order: Order) => {
  try {
    // Import jsPDF dynamically
    const { jsPDF } = await import('jspdf')
    
    // Create new PDF document
    const doc = new jsPDF()
    
    // Set font
    doc.setFont('helvetica')
    
    // Header
    doc.setFontSize(20)
    doc.setTextColor(40, 40, 40)
    doc.text('INVOICE', 20, 30)
    
    // Company info
    doc.setFontSize(12)
    doc.setTextColor(100, 100, 100)
    doc.text('Autowarehouse', 20, 45)
    doc.text('Jakarta, Indonesia', 20, 52)
    doc.text('Email: info@autowarehouse.com', 20, 59)
    
    // Invoice details
    doc.setTextColor(40, 40, 40)
    doc.text(`Invoice #: ${order.id}`, 120, 45)
    doc.text(`Date: ${formatDateTime(order.createdAt)}`, 120, 52)
    doc.text(`Status: ${getStatusText(order.status)}`, 120, 59)
    
    // Customer info
    doc.setFontSize(14)
    doc.text('Bill To:', 20, 80)
    doc.setFontSize(12)
    const customerName = order.customerName || 'Customer'
    doc.text(customerName, 20, 90)
    if (order.customerEmail) {
      doc.text(order.customerEmail, 20, 97)
    }
    if (order.customerPhone) {
      doc.text(order.customerPhone, 20, 104)
    }
    if (order.shippingAddress) {
      const addressLines = doc.splitTextToSize(order.shippingAddress, 80)
      doc.text(addressLines, 20, 111)
    }
    
    // Items table header
    let yPos = 140
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('Item', 20, yPos)
    doc.text('Qty', 120, yPos)
    doc.text('Price', 140, yPos)
    doc.text('Total', 170, yPos)
    
    // Draw line under header
    doc.line(20, yPos + 3, 190, yPos + 3)
    yPos += 15
    
    // Note: For basic invoice from order list, we'll show summary info
    // For detailed items, user should go to order detail page
    doc.setFontSize(10)
    doc.text('Order Summary', 20, yPos)
    doc.text('1', 120, yPos)
    doc.text(`Rp ${formatPrice(order.totalAmount || order.total)}`, 140, yPos)
    doc.text(`Rp ${formatPrice(order.totalAmount || order.total)}`, 170, yPos)
    yPos += 15
    
    // Totals
    yPos += 10
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(11)
    doc.text('Subtotal:', 120, yPos)
    doc.text(`Rp ${formatPrice(order.subtotal || order.totalAmount || order.total)}`, 170, yPos)
    yPos += 8
    
    if (order.shippingCost && order.shippingCost > 0) {
      doc.text('Shipping:', 120, yPos)
      doc.text(`Rp ${formatPrice(order.shippingCost)}`, 170, yPos)
      yPos += 8
    }
    
    if (order.taxAmount && order.taxAmount > 0) {
      doc.text('Tax:', 120, yPos)
      doc.text(`Rp ${formatPrice(order.taxAmount)}`, 170, yPos)
      yPos += 8
    }
    
    // Total line
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('TOTAL:', 120, yPos)
    doc.text(`Rp ${formatPrice(order.totalAmount || order.total)}`, 170, yPos)
    
    // Payment info
    yPos += 20
    doc.setFontSize(10)
    doc.setTextColor(100, 100, 100)
    doc.text(`Payment Method: ${order.paymentMethod || 'Bank Transfer'}`, 20, yPos)
    doc.text(`Payment Status: ${order.paymentStatus === 'paid' ? 'Paid' : 'Pending'}`, 20, yPos + 7)
    
    if (order.trackingNumber) {
      doc.text(`Tracking Number: ${order.trackingNumber}`, 20, yPos + 14)
    }
    
    // Footer
    doc.setFontSize(8)
    doc.setTextColor(150, 150, 150)
    doc.text('Thank you for your business!', 20, 280)
    doc.text('This is a computer generated invoice.', 20, 285)
    
    // Save the PDF
    doc.save(`Invoice-${order.id}.pdf`)
    
    success('Invoice Downloaded', 'Invoice has been downloaded successfully')
    
  } catch (error) {
    console.error('Error generating PDF:', error)
    showError('Download Failed', 'Failed to generate invoice. Please try again.')
  }
}

const refreshOrders = async () => {
  await loadOrders()
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

const exportOrders = async () => {
  try {
    // Call the API to export orders
    const csvData = await apiService.exportOrders({
      status: exportFilters.value.status,
      startDate: exportFilters.value.startDate,
      endDate: exportFilters.value.endDate,
      format: exportFilters.value.format
    })
    
    // Create and download the file
    const blob = new Blob([csvData], { type: 'text/csv' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `orders_export_${new Date().toISOString().split('T')[0]}.${exportFilters.value.format}`
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
    
    showExportModal.value = false
    alert('Orders exported successfully!')
  } catch (error) {
    console.error('Export failed:', error)
    alert('Failed to export orders. Please try again.')
  }
}

const toggleSelectAll = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.checked) {
    selectedOrders.value = filteredOrders.value.map(order => order.id)
  } else {
    selectedOrders.value = []
  }
}

const processRefund = async (order: Order) => {
  if (!confirm(`Process refund for order #${order.id}?`)) {
    return
  }
  
  try {
    await apiService.processRefund(parseInt(order.id), 'Refund processed by admin')
    
    // Update local order status
    order.paymentStatus = 'refunded'
    
    success('Refund Processed', `Refund for order #${order.id} has been processed successfully`)
  } catch (error) {
    console.error('Process refund failed:', error)
    showError('Process Refund Failed', 'Failed to process refund. Please try again.')
  }
}

const executeBulkAction = async () => {
  if (!bulkAction.value.action || selectedOrders.value.length === 0) {
    return
  }
  
  try {
    const orderIds = selectedOrders.value.map(id => parseInt(id))
    const result = await apiService.bulkUpdateOrderStatus(
      orderIds,
      bulkAction.value.action,
      bulkAction.value.notes
    )
    
    // Update local orders status
    orders.value.forEach(order => {
      if (selectedOrders.value.includes(order.id)) {
        order.status = bulkAction.value.action.toLowerCase() as any
      }
    })
    
    showBulkModal.value = false
    selectedOrders.value = []
    bulkAction.value.action = ''
    bulkAction.value.notes = ''
    
    alert(`${result.updatedCount} orders updated successfully!`)
  } catch (error) {
    console.error('Bulk action failed:', error)
    alert('Failed to execute bulk action. Please try again.')
  }
}

// Watch for filter changes and reload data with debounce
let debounceTimer: NodeJS.Timeout | null = null
watch([searchQuery, statusFilter, paymentFilter, dateFilter], () => {
  console.log('Filter changed, debouncing reload...')
  
  if (debounceTimer) {
    clearTimeout(debounceTimer)
  }
  
  debounceTimer = setTimeout(() => {
    console.log('Executing debounced filter reload...')
    currentPage.value = 1 // Reset to first page
    loadOrders(true) // Use filters
  }, 500)
})

onMounted(() => {
  console.log('Admin Order Management loaded')
  loadOrders()
})
</script>
