<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between">
          <div class="flex items-center space-x-4">
            <button @click="goBack" class="text-gray-500 hover:text-gray-700">
              <i class="fa-solid fa-arrow-left text-lg"></i>
            </button>
            <div>
              <h1 class="text-3xl font-bold text-gray-900">Order Details</h1>
              <p class="text-gray-600 mt-1">Order #{{ order?.orderNumber }}</p>
            </div>
          </div>
          <div class="flex space-x-3">
            <button 
              @click="printInvoice"
              :disabled="!order"
              class="bg-purple-600 text-white px-4 py-2 rounded-lg hover:bg-purple-700 disabled:opacity-50 flex items-center space-x-2"
            >
              <i class="fa-solid fa-print text-sm"></i>
              <span>Print Invoice</span>
            </button>
            <!-- <button 
              @click="downloadInvoice"
              :disabled="!order"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 disabled:opacity-50 flex items-center space-x-2"
            >
              <i class="fa-solid fa-download text-sm"></i>
              <span>Download PDF</span>
            </button> -->
          </div>
        </div>
      </div>

      <!-- Loading State -->
      <div v-if="isLoading" class="flex justify-center items-center py-12">
        <div class="text-center">
          <i class="fa-solid fa-spinner fa-spin text-3xl text-blue-600 mb-4"></i>
          <p class="text-gray-600">Loading order details...</p>
        </div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-lg p-6 mb-6">
        <div class="flex items-center">
          <i class="fa-solid fa-exclamation-triangle text-red-600 mr-3"></i>
          <div>
            <h3 class="text-red-800 font-medium">Error Loading Order</h3>
            <p class="text-red-700 mt-1">{{ error }}</p>
          </div>
        </div>
        <button @click="loadOrderDetails" class="mt-4 bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700">
          Try Again
        </button>
      </div>

      <!-- Order Content -->
      <div v-else-if="order" class="space-y-6">
        <!-- Order Status and Actions -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between mb-6">
            <div>
              <h3 class="text-lg font-semibold text-gray-900 mb-2">Order Status</h3>
              <div class="flex items-center space-x-4">
                <span :class="getStatusClass(order.status)" class="px-3 py-1 rounded-full text-sm font-medium">
                  {{ getStatusText(order.status) }}
                </span>
                <span :class="getPaymentClass(order.paymentStatus)" class="px-3 py-1 rounded-full text-sm font-medium">
                  {{ getPaymentText(order.paymentStatus) }}
                </span>
              </div>
            </div>
            <div class="flex space-x-3">
              <button 
                v-if="canConfirmOrder"
                @click="updateOrderStatus('CONFIRMED')"
                :disabled="isUpdatingStatus"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 disabled:opacity-50"
              >
                <i class="fa-solid fa-check mr-2"></i>
                Confirm Order
              </button>
              <button 
                v-if="canShipOrder"
                @click="showShippingModal = true"
                :disabled="isUpdatingStatus"
                class="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700 disabled:opacity-50"
              >
                <i class="fa-solid fa-truck mr-2"></i>
                Mark as Shipped
              </button>
              <button 
                v-if="canDeliverOrder"
                @click="updateOrderStatus('DELIVERED')"
                :disabled="isUpdatingStatus"
                class="bg-purple-600 text-white px-4 py-2 rounded-lg hover:bg-purple-700 disabled:opacity-50"
              >
                <i class="fa-solid fa-check-circle mr-2"></i>
                Mark as Delivered
              </button>
              <button 
                v-if="canCancelOrder"
                @click="showCancelModal = true"
                :disabled="isUpdatingStatus"
                class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 disabled:opacity-50"
              >
                <i class="fa-solid fa-times mr-2"></i>
                Cancel Order
              </button>
            </div>
          </div>

          <!-- Order Status Timeline -->
          <div class="border-t pt-6">
            <h4 class="text-sm font-medium text-gray-900 mb-4">Order Timeline</h4>
            <div class="space-y-4">
              <div v-for="(status, index) in orderTimeline" :key="index" class="flex items-start">
                <div class="flex-shrink-0">
                  <div :class="status.completed ? 'bg-green-100 text-green-600' : 'bg-gray-100 text-gray-400'" 
                       class="w-8 h-8 rounded-full flex items-center justify-center">
                    <i :class="status.icon" class="text-sm"></i>
                  </div>
                </div>
                <div class="ml-4 flex-1">
                  <div class="flex items-center justify-between">
                    <p :class="status.completed ? 'text-gray-900 font-medium' : 'text-gray-500'" class="text-sm">
                      {{ status.title }}
                    </p>
                    <p v-if="status.timestamp" class="text-xs text-gray-500">
                      {{ formatDateTime(status.timestamp) }}
                    </p>
                  </div>
                  <p v-if="status.description" class="text-xs text-gray-500 mt-1">
                    {{ status.description }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Information Grid -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <!-- Customer Information -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Customer Information</h3>
            <div class="space-y-3">
              <div class="space-y-3 text-sm">
                <div class="flex justify-between">
                  <span class="font-medium text-gray-700">Customer Name:</span>
                  <span class="text-gray-900">{{ order.customerName || (order.user ? `${order.user.firstName} ${order.user.lastName}` : 'N/A') }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="font-medium text-gray-700">Email:</span>
                  <span class="text-gray-600">{{ order.user?.email || 'N/A' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="font-medium text-gray-700">Phone:</span>
                  <span class="text-gray-600">{{ order.user?.phoneNumber || 'Not provided' }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="font-medium text-gray-700">Customer ID:</span>
                  <span class="text-gray-600">#{{ order.user?.id || 'N/A' }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Order Information -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Order Information</h3>
            <div class="space-y-3 text-sm">
              <div class="flex justify-between">
                <span class="font-medium text-gray-700">Order Date:</span>
                <span class="text-gray-600">{{ formatDateTime(order.createdAt) }}</span>
              </div>
              <div class="flex justify-between">
                <span class="font-medium text-gray-700">Payment Method:</span>
                <span class="text-gray-600">{{ order.paymentMethod || 'Not specified' }}</span>
              </div>
              <div v-if="order.paymentReference" class="flex justify-between">
                <span class="font-medium text-gray-700">Payment Reference:</span>
                <span class="text-gray-600">{{ order.paymentReference }}</span>
              </div>
              <div v-if="order.trackingNumber" class="flex justify-between">
                <span class="font-medium text-gray-700">Tracking Number:</span>
                <span class="text-gray-600">{{ order.trackingNumber }}</span>
              </div>
              <div v-if="order.shippedAt" class="flex justify-between">
                <span class="font-medium text-gray-700">Shipped Date:</span>
                <span class="text-gray-600">{{ formatDateTime(order.shippedAt) }}</span>
              </div>
              <div v-if="order.deliveredAt" class="flex justify-between">
                <span class="font-medium text-gray-700">Delivered Date:</span>
                <span class="text-gray-600">{{ formatDateTime(order.deliveredAt) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Shipping Information -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Shipping Information</h3>
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <div>
              <h4 class="font-medium text-gray-700 mb-2">Shipping Address</h4>
              <p class="text-sm text-gray-600 whitespace-pre-line">{{ order.shippingAddress }}</p>
            </div>
            <!-- <div v-if="order.billingAddress">
              <h4 class="font-medium text-gray-700 mb-2">Billing Address</h4>
              <p class="text-sm text-gray-600 whitespace-pre-line">{{ order.billingAddress }}</p>
            </div> -->
          </div>
        </div>

        <!-- Order Items -->
        <div class="bg-white rounded-lg shadow-sm border border-gray-200">
          <div class="p-6 border-b border-gray-200">
            <h3 class="text-lg font-semibold text-gray-900">Order Items</h3>
          </div>
          <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-white">
                <tr>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">SKU</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Quantity</th>
                  <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Subtotal</th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr v-for="item in order.items" :key="item.id">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <img 
                        :src="item.productImages?.[0] || '/placeholder-product.jpg'" 
                        :alt="item.productName"
                        class="w-12 h-12 rounded-lg object-cover mr-4"
                      >
                      <div>
                        <p class="text-sm font-medium text-gray-900">{{ item.productName }}</p>
                        <p class="text-sm text-gray-500">{{ item.productBrand || 'N/A' }}</p>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                    {{ item.productSku }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    Rp {{ formatPrice(item.productPrice) }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ item.quantity }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                    Rp {{ formatPrice(item.subtotal) }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          
          <!-- Order Summary -->
          <div class="px-6 py-4 border-t border-gray-200 bg-white">
            <div class="flex justify-end">
              <div class="w-64 space-y-2">
                <div class="flex justify-between text-sm">
                  <span class="text-gray-600">Subtotal:</span>
                  <span class="text-gray-900">Rp {{ formatPrice(order.subtotal) }}</span>
                </div>
                <div v-if="order.taxAmount > 0" class="flex justify-between text-sm">
                  <span class="text-gray-600">Tax:</span>
                  <span class="text-gray-900">Rp {{ formatPrice(order.taxAmount) }}</span>
                </div>
                <div v-if="order.shippingCost > 0" class="flex justify-between text-sm">
                  <span class="text-gray-600">Shipping:</span>
                  <span class="text-gray-900">Rp {{ formatPrice(order.shippingCost) }}</span>
                </div>
                <div v-if="order.discountAmount > 0" class="flex justify-between text-sm">
                  <span class="text-gray-600">Discount:</span>
                  <span class="text-red-600">-Rp {{ formatPrice(order.discountAmount) }}</span>
                </div>
                <div class="flex justify-between text-lg font-semibold border-t pt-2">
                  <span class="text-gray-900">Total:</span>
                  <span class="text-gray-900">Rp {{ formatPrice(order.totalAmount) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Notes -->
        <div v-if="order.notes" class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Order Notes</h3>
          <p class="text-sm text-gray-600 whitespace-pre-line">{{ order.notes }}</p>
        </div>
      </div>
    </div>

    <!-- Shipping Modal -->
    <div v-if="showShippingModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Ship Order</h3>
        </div>
        <div class="p-6">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Tracking Number</label>
            <input 
              v-model="trackingNumber"
              type="text" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              placeholder="Enter tracking number"
            >
          </div>
          <div class="flex justify-end space-x-3">
            <button 
              @click="showShippingModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              @click="shipOrder"
              :disabled="!trackingNumber.trim() || isUpdatingStatus"
              class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 disabled:opacity-50"
            >
              Ship Order
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Cancel Modal -->
    <div v-if="showCancelModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Cancel Order</h3>
        </div>
        <div class="p-6">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">Cancellation Reason</label>
            <textarea 
              v-model="cancelReason"
              rows="3"
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              placeholder="Enter reason for cancellation"
            ></textarea>
          </div>
          <div class="flex justify-end space-x-3">
            <button 
              @click="showCancelModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              @click="cancelOrder"
              :disabled="!cancelReason.trim() || isUpdatingStatus"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 disabled:opacity-50"
            >
              Cancel Order
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useNotifications } from '@/composables/useNotifications'
import { apiService, type OrderDetail } from '@/services/api'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface OrderItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productPrice: number
  quantity: number
  subtotal: number
  productBrand?: string
  productImages?: string[]
  product?: {
    id: number
    name: string
    brand: string
    imageUrls: string[]
  }
}

interface Customer {
  id: number
  name: string
  email: string
  phone: string
  avatar: string
}

interface Order {
  id: number
  orderNumber: string
  status: string
  paymentStatus: string
  subtotal: number
  taxAmount: number
  shippingCost: number
  discountAmount: number
  totalAmount: number
  customerName: string
  customer: Customer
  user: {
    id: number
    firstName: string
    lastName: string
    email: string
    phoneNumber: string
  }
  shippingAddress: string
  billingAddress: string
  paymentMethod: string
  paymentReference: string
  trackingNumber: string
  notes: string
  createdAt: string
  shippedAt: string
  deliveredAt: string
  items: OrderItem[]
}

const route = useRoute()
const router = useRouter()
const { success, error: showError } = useNotifications()

const orderId = ref(route.params.id as string)
const order = ref<Order | null>(null)
const isLoading = ref(true)
const error = ref('')
const isUpdatingStatus = ref(false)
const showShippingModal = ref(false)
const showCancelModal = ref(false)
const trackingNumber = ref('')
const cancelReason = ref('')

// Computed properties
const canConfirmOrder = computed(() => {
  return order.value?.status === 'PENDING' && order.value?.paymentStatus === 'PAID'
})

const canShipOrder = computed(() => {
  return order.value?.status === 'CONFIRMED'
})

const canDeliverOrder = computed(() => {
  return order.value?.status === 'SHIPPED'
})

const canCancelOrder = computed(() => {
  return order.value?.status === 'PENDING' || order.value?.status === 'CONFIRMED'
})

const orderTimeline = computed(() => {
  if (!order.value) return []
  
  const timeline = [
    {
      title: 'Order Placed',
      description: 'Order has been placed and is awaiting confirmation',
      icon: 'fa-solid fa-shopping-cart',
      completed: true,
      timestamp: order.value.createdAt
    },
    {
      title: 'Order Confirmed',
      description: 'Order has been confirmed and is being prepared',
      icon: 'fa-solid fa-check',
      completed: ['CONFIRMED', 'SHIPPED', 'DELIVERED'].includes(order.value.status),
      timestamp: order.value.status !== 'PENDING' ? order.value.createdAt : null
    },
    {
      title: 'Order Shipped',
      description: order.value.trackingNumber ? `Tracking: ${order.value.trackingNumber}` : 'Order has been shipped',
      icon: 'fa-solid fa-truck',
      completed: ['SHIPPED', 'DELIVERED'].includes(order.value.status),
      timestamp: order.value.shippedAt
    },
    {
      title: 'Order Delivered',
      description: 'Order has been successfully delivered',
      icon: 'fa-solid fa-check-circle',
      completed: order.value.status === 'DELIVERED',
      timestamp: order.value.deliveredAt
    }
  ]

  if (order.value.status === 'CANCELLED') {
    timeline.push({
      title: 'Order Cancelled',
      description: 'Order has been cancelled',
      icon: 'fa-solid fa-times-circle',
      completed: true,
      timestamp: order.value.createdAt
    })
  }

  return timeline
})

// Methods
const loadOrderDetails = async () => {
  try {
    isLoading.value = true
    error.value = ''
    
    const response = await apiService.getOrderDetail(parseInt(orderId.value))
    order.value = response as any
  } catch (err: any) {
    error.value = err.message || 'Failed to load order details'
  } finally {
    isLoading.value = false
  }
}

const updateOrderStatus = async (newStatus: string) => {
  try {
    isUpdatingStatus.value = true
    
    await apiService.updateOrderStatus(parseInt(orderId.value), newStatus)
    
    // Reload order details to get updated data
    await loadOrderDetails()
    
    success('Status Updated', `Order status updated to ${getStatusText(newStatus)}`)
  } catch (err: any) {
    showError('Update Failed', err.message || 'Failed to update order status')
  } finally {
    isUpdatingStatus.value = false
  }
}

const shipOrder = async () => {
  try {
    isUpdatingStatus.value = true
    
    await apiService.shipOrder(parseInt(orderId.value), trackingNumber.value)
    
    // Reload order details to get updated data
    await loadOrderDetails()
    
    showShippingModal.value = false
    trackingNumber.value = ''
    success('Order Shipped', 'Order has been marked as shipped with tracking number')
  } catch (err: any) {
    showError('Ship Failed', err.message || 'Failed to ship order')
  } finally {
    isUpdatingStatus.value = false
  }
}

const cancelOrder = async () => {
  try {
    isUpdatingStatus.value = true
    
    await apiService.cancelOrder(parseInt(orderId.value), cancelReason.value)
    
    // Reload order details to get updated data
    await loadOrderDetails()
    
    showCancelModal.value = false
    cancelReason.value = ''
    success('Order Cancelled', 'Order has been cancelled successfully')
  } catch (err: any) {
    showError('Cancel Failed', err.message || 'Failed to cancel order')
  } finally {
    isUpdatingStatus.value = false
  }
}

const printInvoice = async () => {
  if (!order.value) return
  
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
    doc.text(`Invoice #: ${order.value.orderNumber}`, 120, 45)
    doc.text(`Date: ${formatDateTime(order.value.createdAt)}`, 120, 52)
    doc.text(`Status: ${getStatusText(order.value.status)}`, 120, 59)
    
    // Customer info
    doc.setFontSize(14)
    doc.text('Bill To:', 20, 80)
    doc.setFontSize(12)
    const customerName = order.value.customerName || (order.value.user ? `${order.value.user.firstName} ${order.value.user.lastName}` : 'Customer')
    doc.text(customerName, 20, 90)
    if (order.value.user?.email) {
      doc.text(order.value.user.email, 20, 97)
    }
    if (order.value.user?.phoneNumber) {
      doc.text(order.value.user.phoneNumber, 20, 104)
    }
    if (order.value.shippingAddress) {
      const addressLines = doc.splitTextToSize(order.value.shippingAddress, 80)
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
    
    // Items
    if (order.value.items && order.value.items.length > 0) {
      order.value.items.forEach((item) => {
        doc.setFontSize(10)
        const itemName = doc.splitTextToSize(item.productName, 90)
        doc.text(itemName, 20, yPos)
        doc.text(item.quantity.toString(), 120, yPos)
        doc.text(`Rp ${formatPrice(item.productPrice)}`, 140, yPos)
        doc.text(`Rp ${formatPrice(item.subtotal)}`, 170, yPos)
        yPos += itemName.length * 5 + 5
      })
    }
    
    // Totals
    yPos += 10
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(11)
    doc.text('Subtotal:', 120, yPos)
    doc.text(`Rp ${formatPrice(order.value.subtotal)}`, 170, yPos)
    yPos += 8
    
    if (order.value.shippingCost > 0) {
      doc.text('Shipping:', 120, yPos)
      doc.text(`Rp ${formatPrice(order.value.shippingCost)}`, 170, yPos)
      yPos += 8
    }
    
    if (order.value.taxAmount > 0) {
      doc.text('Tax:', 120, yPos)
      doc.text(`Rp ${formatPrice(order.value.taxAmount)}`, 170, yPos)
      yPos += 8
    }
    
    // Total line
    doc.line(120, yPos, 190, yPos)
    yPos += 10
    
    doc.setFontSize(12)
    doc.setTextColor(40, 40, 40)
    doc.text('TOTAL:', 120, yPos)
    doc.text(`Rp ${formatPrice(order.value.totalAmount)}`, 170, yPos)
    
    // Payment info
    yPos += 20
    doc.setFontSize(10)
    doc.setTextColor(100, 100, 100)
    doc.text(`Payment Method: ${order.value.paymentMethod || 'Bank Transfer'}`, 20, yPos)
    doc.text(`Payment Status: ${order.value.paymentStatus === 'PAID' ? 'Paid' : 'Pending'}`, 20, yPos + 7)
    
    if (order.value.trackingNumber) {
      doc.text(`Tracking Number: ${order.value.trackingNumber}`, 20, yPos + 14)
    }
    
    // Footer
    doc.setFontSize(8)
    doc.setTextColor(150, 150, 150)
    doc.text('Thank you for your business!', 20, 280)
    doc.text('This is a computer generated invoice.', 20, 285)
    
    // Save the PDF
    doc.save(`Invoice-${order.value.orderNumber}.pdf`)
    
    success('Invoice Downloaded', 'Invoice has been downloaded successfully')
    
  } catch (error) {
    console.error('Error generating PDF:', error)
    showError('Download Failed', 'Failed to generate invoice. Please try again.')
  }
}

const downloadInvoice = () => {
  // TODO: Implement PDF generation and download
  showError('Feature Coming Soon', 'PDF invoice download will be available soon')
}

const goBack = () => {
  router.back()
}

// Utility methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
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
    PENDING: 'bg-yellow-100 text-yellow-800',
    CONFIRMED: 'bg-blue-100 text-blue-800',
    SHIPPED: 'bg-orange-100 text-orange-800',
    DELIVERED: 'bg-green-100 text-green-800',
    CANCELLED: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    PENDING: 'Pending',
    CONFIRMED: 'Confirmed',
    SHIPPED: 'Shipped',
    DELIVERED: 'Delivered',
    CANCELLED: 'Cancelled'
  }
  return texts[status as keyof typeof texts] || status
}

const getPaymentClass = (status: string) => {
  const classes = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    PAID: 'bg-green-100 text-green-800',
    FAILED: 'bg-red-100 text-red-800',
    REFUNDED: 'bg-gray-100 text-gray-800',
    PARTIALLY_REFUNDED: 'bg-orange-100 text-orange-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getPaymentText = (status: string) => {
  const texts = {
    PENDING: 'Pending',
    PAID: 'Paid',
    FAILED: 'Failed',
    REFUNDED: 'Refunded',
    PARTIALLY_REFUNDED: 'Partially Refunded'
  }
  return texts[status as keyof typeof texts] || status
}

// Initialize on mount
onMounted(() => {
  loadOrderDetails()
})
</script>
