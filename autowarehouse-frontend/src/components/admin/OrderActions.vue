<template>
  <div class="order-actions">
    <!-- Quick Actions Bar -->
    <div class="bg-white rounded-lg border border-gray-200 p-4 mb-6">
      <div class="flex items-center justify-between">
        <div class="flex items-center space-x-4">
          <h3 class="text-sm font-medium text-gray-900">Quick Actions</h3>
          <span :class="[
            'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
            getStatusClass(order.status)
          ]">
            {{ getStatusText(order.status) }}
          </span>
        </div>
        
        <div class="flex items-center space-x-2">
          <!-- Status-specific actions -->
          <template v-if="order.status === 'pending'">
            <button 
              @click="confirmOrder"
              :disabled="loading"
              class="px-3 py-2 bg-green-600 text-white text-sm rounded-lg hover:bg-green-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
            >
              <i class="fa-solid fa-check" :class="{ 'animate-spin fa-spinner': loading }"></i>
              <span>Confirm Order</span>
            </button>
          </template>
          
          <template v-else-if="order.status === 'confirmed'">
            <button 
              @click="showShipModal = true"
              :disabled="loading"
              class="px-3 py-2 bg-blue-600 text-white text-sm rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
            >
              <i class="fa-solid fa-truck"></i>
              <span>Ship Order</span>
            </button>
          </template>
          
          <template v-else-if="order.status === 'shipped'">
            <button 
              @click="deliverOrder"
              :disabled="loading"
              class="px-3 py-2 bg-purple-600 text-white text-sm rounded-lg hover:bg-purple-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
            >
              <i class="fa-solid fa-box-check" :class="{ 'animate-spin fa-spinner': loading }"></i>
              <span>Mark Delivered</span>
            </button>
          </template>
          
          <!-- Common actions -->
          <button 
            v-if="order.status !== 'cancelled' && order.status !== 'delivered'"
            @click="showCancelModal = true"
            :disabled="loading"
            class="px-3 py-2 bg-red-600 text-white text-sm rounded-lg hover:bg-red-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
          >
            <i class="fa-solid fa-times"></i>
            <span>Cancel</span>
          </button>
          
          <button 
            @click="generateInvoice"
            :disabled="loading"
            class="px-3 py-2 bg-gray-600 text-white text-sm rounded-lg hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
          >
            <i class="fa-solid fa-file-pdf" :class="{ 'animate-spin fa-spinner': loading }"></i>
            <span>Invoice</span>
          </button>
          
          <div class="relative">
            <button 
              @click="showMoreActions = !showMoreActions"
              class="px-3 py-2 bg-gray-100 text-gray-700 text-sm rounded-lg hover:bg-gray-200 flex items-center space-x-2"
            >
              <i class="fa-solid fa-ellipsis-vertical"></i>
            </button>
            
            <!-- More Actions Dropdown -->
            <div 
              v-if="showMoreActions"
              class="absolute right-0 top-full mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-200 z-10"
            >
              <div class="py-1">
                <button 
                  @click="viewCustomer"
                  class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
                >
                  <i class="fa-solid fa-user"></i>
                  <span>View Customer</span>
                </button>
                <button 
                  @click="duplicateOrder"
                  class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
                >
                  <i class="fa-solid fa-copy"></i>
                  <span>Duplicate Order</span>
                </button>
                <button 
                  @click="exportOrder"
                  class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
                >
                  <i class="fa-solid fa-download"></i>
                  <span>Export Order</span>
                </button>
                <hr class="my-1">
                <button 
                  @click="showNotesModal = true"
                  class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center space-x-2"
                >
                  <i class="fa-solid fa-note-sticky"></i>
                  <span>Add Notes</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Ship Order Modal -->
    <div v-if="showShipModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Ship Order</h3>
          <button @click="showShipModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <form @submit.prevent="shipOrder" class="p-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Tracking Number</label>
              <input 
                v-model="shipForm.trackingNumber"
                type="text" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                placeholder="Enter tracking number"
              >
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Shipping Carrier</label>
              <select 
                v-model="shipForm.carrier"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              >
                <option value="">Select Carrier</option>
                <option value="jne">JNE</option>
                <option value="jnt">J&T Express</option>
                <option value="pos">Pos Indonesia</option>
                <option value="tiki">TIKI</option>
                <option value="sicepat">SiCepat</option>
                <option value="anteraja">AnterAja</option>
              </select>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Estimated Delivery</label>
              <input 
                v-model="shipForm.estimatedDelivery"
                type="date" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              >
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Notes (Optional)</label>
              <textarea 
                v-model="shipForm.notes"
                rows="3" 
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                placeholder="Add shipping notes..."
              ></textarea>
            </div>
          </div>
          
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              type="button"
              @click="showShipModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              :disabled="loading"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Ship Order
            </button>
          </div>
        </form>
      </div>
    </div>
    
    <!-- Cancel Order Modal -->
    <div v-if="showCancelModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Cancel Order</h3>
          <button @click="showCancelModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <form @submit.prevent="cancelOrder" class="p-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Cancellation Reason</label>
              <select 
                v-model="cancelForm.reason"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              >
                <option value="">Select Reason</option>
                <option value="customer_request">Customer Request</option>
                <option value="out_of_stock">Out of Stock</option>
                <option value="payment_failed">Payment Failed</option>
                <option value="fraud_suspected">Fraud Suspected</option>
                <option value="other">Other</option>
              </select>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Additional Notes</label>
              <textarea 
                v-model="cancelForm.notes"
                rows="3" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                placeholder="Provide details about the cancellation..."
              ></textarea>
            </div>
            
            <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-3">
              <div class="flex">
                <i class="fa-solid fa-exclamation-triangle text-yellow-600 mt-0.5 mr-2"></i>
                <div class="text-sm text-yellow-800">
                  <p class="font-medium">Warning</p>
                  <p>This action cannot be undone. The customer will be notified of the cancellation.</p>
                </div>
              </div>
            </div>
          </div>
          
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              type="button"
              @click="showCancelModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Keep Order
            </button>
            <button 
              type="submit"
              :disabled="loading"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Cancel Order
            </button>
          </div>
        </form>
      </div>
    </div>
    
    <!-- Add Notes Modal -->
    <div v-if="showNotesModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-md w-full">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Add Order Notes</h3>
          <button @click="showNotesModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <form @submit.prevent="addNotes" class="p-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Notes</label>
              <textarea 
                v-model="notesForm.notes"
                rows="4" 
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                placeholder="Add notes about this order..."
              ></textarea>
            </div>
            
            <div class="flex items-center">
              <input 
                v-model="notesForm.isInternal"
                type="checkbox" 
                id="internal-notes"
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              >
              <label for="internal-notes" class="ml-2 block text-sm text-gray-700">
                Internal notes (not visible to customer)
              </label>
            </div>
          </div>
          
          <div class="mt-6 flex justify-end space-x-3">
            <button 
              type="button"
              @click="showNotesModal = false"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              :disabled="loading"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Add Notes
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

interface Order {
  id: string
  status: string
  customer: {
    id: string
    name: string
    email: string
  }
}

interface Props {
  order: Order
  canUpdateStatus?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  canUpdateStatus: true
})

const emit = defineEmits<{
  statusUpdated: [status: string, data?: any]
  actionCompleted: [action: string, data?: any]
}>()

const loading = ref(false)
const showMoreActions = ref(false)
const showShipModal = ref(false)
const showCancelModal = ref(false)
const showNotesModal = ref(false)

const shipForm = ref({
  trackingNumber: '',
  carrier: '',
  estimatedDelivery: '',
  notes: ''
})

const cancelForm = ref({
  reason: '',
  notes: ''
})

const notesForm = ref({
  notes: '',
  isInternal: false
})

const getStatusClass = (status: string) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-800',
    confirmed: 'bg-blue-100 text-blue-800',
    shipped: 'bg-orange-100 text-orange-800',
    delivered: 'bg-green-100 text-green-800',
    cancelled: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    pending: 'Pending',
    confirmed: 'Confirmed',
    shipped: 'Shipped',
    delivered: 'Delivered',
    cancelled: 'Cancelled'
  }
  return texts[status as keyof typeof texts] || status
}

const confirmOrder = async () => {
  if (!props.canUpdateStatus) return
  
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('statusUpdated', 'confirmed')
    emit('actionCompleted', 'confirm', { orderId: props.order.id })
  } catch (error) {
    console.error('Failed to confirm order:', error)
    alert('Failed to confirm order. Please try again.')
  } finally {
    loading.value = false
  }
}

const shipOrder = async () => {
  if (!props.canUpdateStatus) return
  
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('statusUpdated', 'shipped', shipForm.value)
    emit('actionCompleted', 'ship', { orderId: props.order.id, ...shipForm.value })
    showShipModal.value = false
    
    // Reset form
    shipForm.value = {
      trackingNumber: '',
      carrier: '',
      estimatedDelivery: '',
      notes: ''
    }
  } catch (error) {
    console.error('Failed to ship order:', error)
    alert('Failed to ship order. Please try again.')
  } finally {
    loading.value = false
  }
}

const deliverOrder = async () => {
  if (!props.canUpdateStatus) return
  
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('statusUpdated', 'delivered')
    emit('actionCompleted', 'deliver', { orderId: props.order.id })
  } catch (error) {
    console.error('Failed to mark order as delivered:', error)
    alert('Failed to mark order as delivered. Please try again.')
  } finally {
    loading.value = false
  }
}

const cancelOrder = async () => {
  if (!props.canUpdateStatus) return
  
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('statusUpdated', 'cancelled', cancelForm.value)
    emit('actionCompleted', 'cancel', { orderId: props.order.id, ...cancelForm.value })
    showCancelModal.value = false
    
    // Reset form
    cancelForm.value = {
      reason: '',
      notes: ''
    }
  } catch (error) {
    console.error('Failed to cancel order:', error)
    alert('Failed to cancel order. Please try again.')
  } finally {
    loading.value = false
  }
}

const generateInvoice = async () => {
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('actionCompleted', 'invoice', { orderId: props.order.id })
    alert('Invoice generated successfully!')
  } catch (error) {
    console.error('Failed to generate invoice:', error)
    alert('Failed to generate invoice. Please try again.')
  } finally {
    loading.value = false
  }
}

const addNotes = async () => {
  loading.value = true
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1000))
    emit('actionCompleted', 'notes', { orderId: props.order.id, ...notesForm.value })
    showNotesModal.value = false
    
    // Reset form
    notesForm.value = {
      notes: '',
      isInternal: false
    }
    
    alert('Notes added successfully!')
  } catch (error) {
    console.error('Failed to add notes:', error)
    alert('Failed to add notes. Please try again.')
  } finally {
    loading.value = false
  }
}

const viewCustomer = () => {
  emit('actionCompleted', 'viewCustomer', { customerId: props.order.customer.id })
}

const duplicateOrder = () => {
  emit('actionCompleted', 'duplicate', { orderId: props.order.id })
}

const exportOrder = () => {
  emit('actionCompleted', 'export', { orderId: props.order.id })
}

// Close dropdown when clicking outside
const handleClickOutside = (event: Event) => {
  const target = event.target as HTMLElement
  if (!target.closest('.relative')) {
    showMoreActions.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onUnmounted(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped>
/* Custom styles for better mobile responsiveness */
@media (max-width: 640px) {
  .order-actions .flex.items-center.space-x-2 {
    flex-wrap: wrap;
    gap: 0.5rem;
  }
  
  .order-actions button {
    min-width: auto;
  }
  
  .order-actions button span {
    display: none;
  }
  
  .order-actions button i {
    margin-right: 0;
  }
}
</style>
