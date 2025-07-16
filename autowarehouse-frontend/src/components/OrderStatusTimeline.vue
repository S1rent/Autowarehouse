<template>
  <div class="order-status-timeline">
    <div class="relative">
      <!-- Timeline line -->
      <div class="absolute left-4 top-8 bottom-0 w-0.5 bg-gray-200"></div>
      
      <!-- Timeline items -->
      <div 
        v-for="(item, index) in timelineItems" 
        :key="index"
        :class="['relative flex items-start space-x-4', index < timelineItems.length - 1 ? 'pb-8' : '']"
      >
        <!-- Status icon -->
        <div :class="[
          'w-8 h-8 rounded-full flex items-center justify-center z-10',
          getStatusIconClass(item.status, item.completed)
        ]">
          <i :class="[
            'text-sm',
            getStatusIcon(item.status, item.completed)
          ]"></i>
        </div>
        
        <!-- Content -->
        <div class="flex-1 min-w-0">
          <div class="flex items-center justify-between">
            <h3 :class="[
              'font-medium',
              item.completed ? 'text-gray-900' : 'text-gray-500'
            ]">
              {{ item.title }}
            </h3>
            <span v-if="item.timestamp" :class="[
              'text-sm',
              item.completed ? 'text-gray-600' : 'text-gray-400'
            ]">
              {{ formatTimestamp(item.timestamp) }}
            </span>
          </div>
          
          <p :class="[
            'text-sm mt-1',
            item.completed ? 'text-gray-600' : 'text-gray-400'
          ]">
            {{ item.description }}
          </p>
          
          <!-- Additional info for shipped status -->
          <div v-if="item.status === 'SHIPPED' && item.trackingNumber" class="mt-2">
            <div class="flex items-center space-x-2 text-sm text-blue-600">
              <i class="fa-solid fa-barcode"></i>
              <span>Tracking: {{ item.trackingNumber }}</span>
            </div>
            <div v-if="item.estimatedDelivery" class="flex items-center space-x-2 text-sm text-gray-600 mt-1">
              <i class="fa-solid fa-calendar"></i>
              <span>Estimated delivery: {{ formatDate(item.estimatedDelivery) }}</span>
            </div>
          </div>
          
          <!-- Additional info for delivered status -->
          <div v-if="item.status === 'DELIVERED' && item.actualDelivery" class="mt-2">
            <div class="flex items-center space-x-2 text-sm text-green-600">
              <i class="fa-solid fa-check-circle"></i>
              <span>Delivered on: {{ formatDate(item.actualDelivery) }}</span>
            </div>
          </div>
          
          <!-- Notes -->
          <div v-if="item.notes" class="mt-2 p-2 bg-gray-50 rounded text-sm text-gray-600">
            <i class="fa-solid fa-sticky-note mr-1"></i>
            {{ item.notes }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface TimelineItem {
  status: string
  title: string
  description: string
  timestamp?: string
  completed: boolean
  trackingNumber?: string
  estimatedDelivery?: string
  actualDelivery?: string
  notes?: string
  changedBy?: string
}

interface Props {
  statusHistory?: any[]
  currentStatus: string
  trackingNumber?: string
}

const props = withDefaults(defineProps<Props>(), {
  statusHistory: () => [],
  trackingNumber: ''
})

// Computed timeline items based on status history or default timeline
const timelineItems = computed<TimelineItem[]>(() => {
  if (props.statusHistory && props.statusHistory.length > 0) {
    // Use actual status history from backend
    return props.statusHistory.map(history => ({
      status: history.status,
      title: getStatusTitle(history.status),
      description: history.notes || getStatusDescription(history.status),
      timestamp: history.changedAt,
      completed: true,
      trackingNumber: history.trackingNumber,
      estimatedDelivery: history.estimatedDelivery,
      actualDelivery: history.actualDelivery,
      notes: history.notes,
      changedBy: history.changedBy
    }))
  } else {
    // Default timeline based on current status
    return getDefaultTimeline()
  }
})

const getDefaultTimeline = (): TimelineItem[] => {
  const statuses = ['PENDING', 'CONFIRMED', 'SHIPPED', 'DELIVERED']
  const currentIndex = statuses.indexOf(props.currentStatus)
  
  return [
    {
      status: 'PENDING',
      title: 'Order Placed',
      description: 'Your order has been placed and is being processed',
      completed: currentIndex >= 0,
      timestamp: currentIndex >= 0 ? new Date().toISOString() : undefined
    },
    {
      status: 'CONFIRMED',
      title: 'Order Confirmed',
      description: 'Your order has been confirmed and payment processed',
      completed: currentIndex >= 1,
      timestamp: currentIndex >= 1 ? new Date().toISOString() : undefined
    },
    {
      status: 'SHIPPED',
      title: 'Order Shipped',
      description: 'Your order has been shipped and is on its way',
      completed: currentIndex >= 2,
      timestamp: currentIndex >= 2 ? new Date().toISOString() : undefined,
      trackingNumber: currentIndex >= 2 ? props.trackingNumber : undefined
    },
    {
      status: 'DELIVERED',
      title: 'Order Delivered',
      description: 'Your order has been successfully delivered',
      completed: currentIndex >= 3,
      timestamp: currentIndex >= 3 ? new Date().toISOString() : undefined
    }
  ]
}

const getStatusTitle = (status: string): string => {
  const titles: Record<string, string> = {
    'PENDING': 'Order Placed',
    'CONFIRMED': 'Order Confirmed',
    'SHIPPED': 'Order Shipped',
    'DELIVERED': 'Order Delivered',
    'CANCELLED': 'Order Cancelled',
    'REFUNDED': 'Order Refunded'
  }
  return titles[status] || status
}

const getStatusDescription = (status: string): string => {
  const descriptions: Record<string, string> = {
    'PENDING': 'Your order has been placed and is being processed',
    'CONFIRMED': 'Your order has been confirmed and payment processed',
    'SHIPPED': 'Your order has been shipped and is on its way',
    'DELIVERED': 'Your order has been successfully delivered',
    'CANCELLED': 'Your order has been cancelled',
    'REFUNDED': 'Your order has been refunded'
  }
  return descriptions[status] || 'Status updated'
}

const getStatusIconClass = (status: string, completed: boolean): string => {
  if (!completed) {
    return 'bg-gray-300'
  }
  
  const classes: Record<string, string> = {
    'PENDING': 'bg-yellow-500',
    'CONFIRMED': 'bg-blue-500',
    'SHIPPED': 'bg-purple-500',
    'DELIVERED': 'bg-green-500',
    'CANCELLED': 'bg-red-500',
    'REFUNDED': 'bg-gray-500'
  }
  return classes[status] || 'bg-gray-500'
}

const getStatusIcon = (status: string, completed: boolean): string => {
  if (!completed) {
    return 'fa-solid fa-clock text-gray-500'
  }
  
  const icons: Record<string, string> = {
    'PENDING': 'fa-solid fa-clock text-white',
    'CONFIRMED': 'fa-solid fa-check text-white',
    'SHIPPED': 'fa-solid fa-truck text-white',
    'DELIVERED': 'fa-solid fa-check-circle text-white',
    'CANCELLED': 'fa-solid fa-times text-white',
    'REFUNDED': 'fa-solid fa-undo text-white'
  }
  return icons[status] || 'fa-solid fa-circle text-white'
}

const formatTimestamp = (timestamp: string): string => {
  const date = new Date(timestamp)
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'long',
    year: 'numeric'
  })
}
</script>

<style scoped>
.order-status-timeline {
  @apply relative;
}

/* Ensure timeline line doesn't show for single items */
.order-status-timeline .relative:only-child .absolute {
  @apply hidden;
}
</style>
