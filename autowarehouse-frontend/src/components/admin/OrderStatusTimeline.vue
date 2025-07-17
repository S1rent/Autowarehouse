<template>
  <div class="order-status-timeline">
    <div class="timeline-header mb-6">
      <h3 class="text-lg font-semibold text-gray-900">Order Status Timeline</h3>
      <p class="text-sm text-gray-600">Track the progress of this order</p>
    </div>
    
    <div class="timeline-container relative">
      <!-- Timeline Line -->
      <div class="absolute left-6 top-0 bottom-0 w-0.5 bg-gray-200"></div>
      
      <!-- Timeline Items -->
      <div class="space-y-6">
        <div 
          v-for="(status, index) in timelineStatuses" 
          :key="status.key"
          class="relative flex items-start"
        >
          <!-- Timeline Dot -->
          <div class="flex-shrink-0 relative">
            <div 
              :class="[
                'w-12 h-12 rounded-full flex items-center justify-center border-4',
                getStatusDotClass(status, index)
              ]"
            >
              <i :class="getStatusIcon(status.key)" class="text-lg"></i>
            </div>
            
            <!-- Connecting Line for Active Status -->
            <div 
              v-if="status.isActive && !status.isCompleted"
              class="absolute top-12 left-1/2 transform -translate-x-1/2 w-0.5 h-6 bg-blue-500 animate-pulse"
            ></div>
          </div>
          
          <!-- Timeline Content -->
          <div class="ml-6 flex-1 min-w-0">
            <div class="bg-white rounded-lg border border-gray-200 p-4 shadow-sm">
              <div class="flex items-center justify-between mb-2">
                <h4 :class="[
                  'text-sm font-medium',
                  status.isCompleted ? 'text-gray-900' : status.isActive ? 'text-blue-600' : 'text-gray-500'
                ]">
                  {{ getStatusTitle(status.key) }}
                </h4>
                <span 
                  :class="[
                    'inline-flex px-2 py-1 text-xs font-semibold rounded-full',
                    getStatusBadgeClass(status)
                  ]"
                >
                  {{ getStatusBadgeText(status) }}
                </span>
              </div>
              
              <p class="text-sm text-gray-600 mb-3">
                {{ getStatusDescription(status.key) }}
              </p>
              
              <!-- Status Details -->
              <div v-if="status.isCompleted || status.isActive" class="space-y-2">
                <div v-if="status.timestamp" class="flex items-center text-xs text-gray-500">
                  <i class="fa-solid fa-clock mr-2"></i>
                  <span>{{ formatDateTime(status.timestamp) }}</span>
                </div>
                
                <div v-if="status.adminUser" class="flex items-center text-xs text-gray-500">
                  <i class="fa-solid fa-user mr-2"></i>
                  <span>Updated by {{ status.adminUser }}</span>
                </div>
                
                <div v-if="status.notes" class="text-xs text-gray-600 bg-gray-50 rounded p-2">
                  <i class="fa-solid fa-note-sticky mr-2"></i>
                  {{ status.notes }}
                </div>
                
                <div v-if="status.trackingNumber" class="text-xs text-blue-600 bg-blue-50 rounded p-2">
                  <i class="fa-solid fa-truck mr-2"></i>
                  Tracking: {{ status.trackingNumber }}
                </div>
              </div>
              
              <!-- Action Buttons for Current Status -->
              <div v-if="status.isActive && !status.isCompleted && canUpdateStatus" class="mt-4 flex space-x-2">
                <button 
                  v-if="getNextAction(status.key)"
                  @click="$emit('updateStatus', getNextAction(status.key))"
                  class="px-3 py-1 bg-blue-600 text-white text-xs rounded hover:bg-blue-700 transition-colors"
                >
                  {{ getNextActionText(status.key) }}
                </button>
                
                <button 
                  v-if="status.key !== 'cancelled'"
                  @click="$emit('cancelOrder')"
                  class="px-3 py-1 bg-red-600 text-white text-xs rounded hover:bg-red-700 transition-colors"
                >
                  Cancel Order
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface TimelineStatus {
  key: string
  timestamp?: string
  adminUser?: string
  notes?: string
  trackingNumber?: string
  isCompleted: boolean
  isActive: boolean
}

interface Props {
  currentStatus: string
  statusHistory: Array<{
    status: string
    timestamp: string
    adminUser?: string
    notes?: string
    trackingNumber?: string
  }>
  canUpdateStatus?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  canUpdateStatus: true
})

const emit = defineEmits<{
  updateStatus: [status: string]
  cancelOrder: []
}>()

const statusOrder = ['pending', 'confirmed', 'shipped', 'delivered', 'cancelled']

const timelineStatuses = computed(() => {
  const statuses: TimelineStatus[] = []
  
  // Create timeline items for each status
  statusOrder.forEach(statusKey => {
    if (statusKey === 'cancelled' && props.currentStatus !== 'cancelled') {
      return // Don't show cancelled unless order is actually cancelled
    }
    
    const historyItem = props.statusHistory.find(h => h.status === statusKey)
    const currentIndex = statusOrder.indexOf(props.currentStatus)
    const statusIndex = statusOrder.indexOf(statusKey)
    
    let isCompleted = false
    let isActive = false
    
    if (props.currentStatus === 'cancelled') {
      isCompleted = statusKey === 'cancelled'
      isActive = statusKey === 'cancelled' && !isCompleted
    } else {
      isCompleted = statusIndex < currentIndex
      isActive = statusIndex === currentIndex
    }
    
    statuses.push({
      key: statusKey,
      timestamp: historyItem?.timestamp,
      adminUser: historyItem?.adminUser,
      notes: historyItem?.notes,
      trackingNumber: historyItem?.trackingNumber,
      isCompleted,
      isActive
    })
  })
  
  return statuses
})

const getStatusDotClass = (status: TimelineStatus, index: number) => {
  if (status.key === 'cancelled') {
    return status.isCompleted 
      ? 'bg-red-500 border-red-500 text-white' 
      : 'bg-gray-100 border-gray-300 text-gray-400'
  }
  
  if (status.isCompleted) {
    return 'bg-green-500 border-green-500 text-white'
  } else if (status.isActive) {
    return 'bg-blue-500 border-blue-500 text-white animate-pulse'
  } else {
    return 'bg-gray-100 border-gray-300 text-gray-400'
  }
}

const getStatusIcon = (statusKey: string) => {
  const icons = {
    pending: 'fa-solid fa-clock',
    confirmed: 'fa-solid fa-check-circle',
    shipped: 'fa-solid fa-truck',
    delivered: 'fa-solid fa-box-check',
    cancelled: 'fa-solid fa-times-circle'
  }
  return icons[statusKey as keyof typeof icons] || 'fa-solid fa-circle'
}

const getStatusTitle = (statusKey: string) => {
  const titles = {
    pending: 'Order Pending',
    confirmed: 'Order Confirmed',
    shipped: 'Order Shipped',
    delivered: 'Order Delivered',
    cancelled: 'Order Cancelled'
  }
  return titles[statusKey as keyof typeof titles] || statusKey
}

const getStatusDescription = (statusKey: string) => {
  const descriptions = {
    pending: 'Order has been placed and is awaiting confirmation',
    confirmed: 'Order has been confirmed and is being prepared for shipment',
    shipped: 'Order has been shipped and is on its way to the customer',
    delivered: 'Order has been successfully delivered to the customer',
    cancelled: 'Order has been cancelled and will not be processed'
  }
  return descriptions[statusKey as keyof typeof descriptions] || ''
}

const getStatusBadgeClass = (status: TimelineStatus) => {
  if (status.key === 'cancelled') {
    return status.isCompleted ? 'bg-red-100 text-red-800' : 'bg-gray-100 text-gray-600'
  }
  
  if (status.isCompleted) {
    return 'bg-green-100 text-green-800'
  } else if (status.isActive) {
    return 'bg-blue-100 text-blue-800'
  } else {
    return 'bg-gray-100 text-gray-600'
  }
}

const getStatusBadgeText = (status: TimelineStatus) => {
  if (status.isCompleted) {
    return 'Completed'
  } else if (status.isActive) {
    return 'Current'
  } else {
    return 'Pending'
  }
}

const getNextAction = (currentStatusKey: string) => {
  const nextActions = {
    pending: 'confirmed',
    confirmed: 'shipped',
    shipped: 'delivered'
  }
  return nextActions[currentStatusKey as keyof typeof nextActions]
}

const getNextActionText = (currentStatusKey: string) => {
  const actionTexts = {
    pending: 'Confirm Order',
    confirmed: 'Mark as Shipped',
    shipped: 'Mark as Delivered'
  }
  return actionTexts[currentStatusKey as keyof typeof actionTexts] || ''
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
</script>

<style scoped>
.timeline-container {
  padding-left: 0;
}

.order-status-timeline {
  max-width: 100%;
}

@media (max-width: 640px) {
  .timeline-container {
    padding-left: 0;
  }
  
  .absolute.left-6 {
    left: 1.5rem;
  }
  
  .ml-6 {
    margin-left: 4rem;
  }
}
</style>
