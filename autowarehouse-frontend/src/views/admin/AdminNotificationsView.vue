<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Admin Sidebar -->
    <div class="flex">
      <!-- Sidebar -->
      <div class="w-64 bg-white shadow-lg min-h-screen">
        <div class="p-6">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-store text-white"></i>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-900">Autowarehouse</h1>
              <p class="text-sm text-gray-500">Admin Panel</p>
            </div>
          </div>
        </div>
        
        <nav class="mt-6">
          <div class="px-6 py-2">
            <p class="text-xs font-semibold text-gray-400 uppercase tracking-wider">Main</p>
          </div>
          <router-link 
            to="/admin/dashboard" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
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
            class="flex items-center px-6 py-3 text-gray-700 bg-blue-50 border-r-4 border-blue-600"
          >
            <i class="fa-solid fa-bell mr-3"></i>
            Notifications
          </router-link>
        </nav>
        
        <div class="absolute bottom-0 w-64 p-6">
          <div class="flex items-center space-x-3">
            <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face" alt="Admin" class="w-10 h-10 rounded-full">
            <div>
              <p class="text-sm font-medium text-gray-900">Admin User</p>
              <p class="text-xs text-gray-500">admin@autowarehouse.com</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex-1">
        <!-- Top Header -->
        <header class="bg-white shadow-sm border-b">
          <div class="px-6 py-4">
            <div class="flex items-center justify-between">
              <div>
                <h1 class="text-2xl font-bold text-gray-900">Notifications</h1>
                <p class="text-gray-600">Manage system notifications and alerts</p>
              </div>
              <div class="flex items-center space-x-4">
                <button 
                  @click="showCreateModal = true"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-plus mr-2"></i>
                  Send Notification
                </button>
                <button class="relative p-2 text-gray-400 hover:text-gray-600">
                  <i class="fa-solid fa-bell text-xl"></i>
                  <span class="absolute -top-1 -right-1 w-3 h-3 bg-red-500 rounded-full"></span>
                </button>
                <div class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center">
                  <i class="fa-solid fa-user text-white text-sm"></i>
                </div>
              </div>
            </div>
          </div>
        </header>

        <!-- Content -->
        <main class="p-6">
          <!-- Stats Cards -->
          <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Sent</p>
                  <p class="text-2xl font-bold text-blue-600">{{ stats.totalSent }}</p>
                </div>
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-paper-plane text-blue-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Delivered</p>
                  <p class="text-2xl font-bold text-green-600">{{ stats.delivered }}</p>
                </div>
                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-check-circle text-green-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Opened</p>
                  <p class="text-2xl font-bold text-purple-600">{{ stats.opened }}</p>
                </div>
                <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-envelope-open text-purple-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Click Rate</p>
                  <p class="text-2xl font-bold text-orange-600">{{ stats.clickRate }}%</p>
                </div>
                <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-mouse-pointer text-orange-600"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Main Content Grid -->
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <!-- Notifications List -->
            <div class="lg:col-span-2">
              <div class="bg-white rounded-xl shadow-sm">
                <div class="px-6 py-4 border-b border-gray-200">
                  <div class="flex items-center justify-between">
                    <h3 class="text-lg font-semibold text-gray-900">Recent Notifications</h3>
                    <div class="flex items-center space-x-2">
                      <select 
                        v-model="typeFilter"
                        class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">All Types</option>
                        <option value="system">System</option>
                        <option value="marketing">Marketing</option>
                        <option value="order">Order</option>
                        <option value="auction">Auction</option>
                      </select>
                      <select 
                        v-model="statusFilter"
                        class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">All Status</option>
                        <option value="sent">Sent</option>
                        <option value="delivered">Delivered</option>
                        <option value="failed">Failed</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="divide-y divide-gray-200">
                  <div 
                    v-for="notification in filteredNotifications" 
                    :key="notification.id"
                    class="p-6 hover:bg-gray-50 transition-colors"
                  >
                    <div class="flex items-start justify-between">
                      <div class="flex items-start space-x-4">
                        <div 
                          :class="getTypeIconClass(notification.type)"
                          class="w-10 h-10 rounded-lg flex items-center justify-center flex-shrink-0"
                        >
                          <i :class="getTypeIcon(notification.type)"></i>
                        </div>
                        <div class="flex-1">
                          <div class="flex items-center space-x-3 mb-2">
                            <h4 class="font-medium text-gray-900">{{ notification.title }}</h4>
                            <span 
                              :class="getTypeClass(notification.type)"
                              class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                            >
                              {{ notification.type }}
                            </span>
                            <span 
                              :class="getStatusClass(notification.status)"
                              class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                            >
                              {{ notification.status }}
                            </span>
                          </div>
                          <p class="text-sm text-gray-600 mb-2">{{ notification.message }}</p>
                          <div class="flex items-center space-x-4 text-sm text-gray-500">
                            <span>{{ notification.recipients }} recipients</span>
                            <span>{{ formatDateTime(notification.sentAt) }}</span>
                            <span>{{ notification.deliveryRate }}% delivered</span>
                          </div>
                        </div>
                      </div>
                      <div class="flex items-center space-x-2">
                        <button 
                          @click="viewNotification(notification)"
                          class="text-blue-600 hover:text-blue-900"
                        >
                          <i class="fa-solid fa-eye"></i>
                        </button>
                        <button 
                          @click="duplicateNotification(notification)"
                          class="text-green-600 hover:text-green-900"
                        >
                          <i class="fa-solid fa-copy"></i>
                        </button>
                        <button 
                          @click="deleteNotification(notification.id)"
                          class="text-red-600 hover:text-red-900"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Quick Actions & Templates -->
            <div class="lg:col-span-1 space-y-6">
              <!-- Quick Actions -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h3>
                <div class="space-y-3">
                  <button 
                    @click="sendSystemAlert"
                    class="w-full flex items-center justify-center px-4 py-3 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
                  >
                    <i class="fa-solid fa-exclamation-triangle mr-2"></i>
                    System Alert
                  </button>
                  <button 
                    @click="sendMaintenanceNotice"
                    class="w-full flex items-center justify-center px-4 py-3 bg-yellow-600 text-white rounded-lg hover:bg-yellow-700 transition-colors"
                  >
                    <i class="fa-solid fa-tools mr-2"></i>
                    Maintenance Notice
                  </button>
                  <button 
                    @click="sendPromotionalEmail"
                    class="w-full flex items-center justify-center px-4 py-3 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
                  >
                    <i class="fa-solid fa-tag mr-2"></i>
                    Promotional Email
                  </button>
                </div>
              </div>

              <!-- Templates -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Templates</h3>
                <div class="space-y-3">
                  <div 
                    v-for="template in templates" 
                    :key="template.id"
                    @click="useTemplate(template)"
                    class="p-3 border border-gray-200 rounded-lg hover:bg-gray-50 cursor-pointer transition-colors"
                  >
                    <div class="flex items-center space-x-3">
                      <div 
                        :class="getTypeIconClass(template.type)"
                        class="w-8 h-8 rounded-lg flex items-center justify-center"
                      >
                        <i :class="getTypeIcon(template.type)" class="text-sm"></i>
                      </div>
                      <div>
                        <p class="font-medium text-gray-900">{{ template.name }}</p>
                        <p class="text-sm text-gray-500">{{ template.description }}</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Analytics -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Analytics</h3>
                <div class="space-y-4">
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Today's Notifications</span>
                    <span class="font-semibold text-gray-900">{{ analytics.today }}</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">This Week</span>
                    <span class="font-semibold text-gray-900">{{ analytics.week }}</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Avg Open Rate</span>
                    <span class="font-semibold text-blue-600">{{ analytics.openRate }}%</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Avg Click Rate</span>
                    <span class="font-semibold text-green-600">{{ analytics.clickRate }}%</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <!-- Create Notification Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-2xl mx-4">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-900">Send Notification</h3>
          <button @click="showCreateModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="sendNotification" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Type</label>
              <select 
                v-model="notificationForm.type"
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
                <option value="">Select Type</option>
                <option value="system">System</option>
                <option value="marketing">Marketing</option>
                <option value="order">Order</option>
                <option value="auction">Auction</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Target Audience</label>
              <select 
                v-model="notificationForm.audience"
                class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
              >
                <option value="">Select Audience</option>
                <option value="all">All Users</option>
                <option value="active">Active Users</option>
                <option value="premium">Premium Users</option>
                <option value="bidders">Active Bidders</option>
              </select>
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Title</label>
            <input 
              v-model="notificationForm.title"
              type="text" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Message</label>
            <textarea 
              v-model="notificationForm.message"
              rows="4" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            ></textarea>
          </div>
          <div class="flex items-center space-x-4">
            <div class="flex items-center">
              <input 
                v-model="notificationForm.sendEmail"
                type="checkbox" 
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              >
              <label class="ml-2 text-sm text-gray-700">Send Email</label>
            </div>
            <div class="flex items-center">
              <input 
                v-model="notificationForm.sendPush"
                type="checkbox" 
                class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              >
              <label class="ml-2 text-sm text-gray-700">Send Push Notification</label>
            </div>
          </div>
          <div class="flex justify-end space-x-4">
            <button 
              type="button"
              @click="showCreateModal = false"
              class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Send Notification
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'

// State
const showCreateModal = ref(false)
const typeFilter = ref('')
const statusFilter = ref('')

// Stats
const stats = ref({
  totalSent: 1247,
  delivered: 1189,
  opened: 856,
  clickRate: 12.5
})

const analytics = ref({
  today: 23,
  week: 156,
  openRate: 68.7,
  clickRate: 12.5
})

// Notification form
const notificationForm = ref({
  type: '',
  audience: '',
  title: '',
  message: '',
  sendEmail: true,
  sendPush: false
})

// Templates
const templates = ref([
  {
    id: 1,
    name: 'Order Confirmation',
    description: 'Confirm customer orders',
    type: 'order'
  },
  {
    id: 2,
    name: 'Auction Ending Soon',
    description: 'Remind users about ending auctions',
    type: 'auction'
  },
  {
    id: 3,
    name: 'System Maintenance',
    description: 'Notify about system downtime',
    type: 'system'
  },
  {
    id: 4,
    name: 'Weekly Deals',
    description: 'Promotional offers',
    type: 'marketing'
  }
])

// Sample notifications data
const notifications = ref([
  {
    id: 1,
    title: 'System Maintenance Scheduled',
    message: 'We will be performing scheduled maintenance on our servers tonight from 2:00 AM to 4:00 AM.',
    type: 'system',
    status: 'delivered',
    recipients: 8549,
    deliveryRate: 98.5,
    sentAt: new Date(Date.now() - 2 * 60 * 60 * 1000)
  },
  {
    id: 2,
    title: 'Flash Sale: 50% Off Electronics',
    message: 'Don\'t miss our biggest electronics sale of the year! Limited time offer.',
    type: 'marketing',
    status: 'delivered',
    recipients: 5234,
    deliveryRate: 97.2,
    sentAt: new Date(Date.now() - 6 * 60 * 60 * 1000)
  },
  {
    id: 3,
    title: 'Your Auction is Ending Soon',
    message: 'The auction for NVIDIA RTX 4090 will end in 2 hours. Place your final bids now!',
    type: 'auction',
    status: 'delivered',
    recipients: 847,
    deliveryRate: 99.1,
    sentAt: new Date(Date.now() - 12 * 60 * 60 * 1000)
  },
  {
    id: 4,
    title: 'Order Shipped - #ORD-2024-001',
    message: 'Your order has been shipped and is on its way. Track your package using the provided link.',
    type: 'order',
    status: 'delivered',
    recipients: 1,
    deliveryRate: 100,
    sentAt: new Date(Date.now() - 24 * 60 * 60 * 1000)
  }
])

// Computed
const filteredNotifications = computed(() => {
  let filtered = notifications.value

  if (typeFilter.value) {
    filtered = filtered.filter(notification => notification.type === typeFilter.value)
  }

  if (statusFilter.value) {
    filtered = filtered.filter(notification => notification.status === statusFilter.value)
  }

  return filtered.sort((a, b) => new Date(b.sentAt).getTime() - new Date(a.sentAt).getTime())
})

// Methods
const getTypeClass = (type: string) => {
  const classes = {
    'system': 'bg-red-100 text-red-800',
    'marketing': 'bg-green-100 text-green-800',
    'order': 'bg-blue-100 text-blue-800',
    'auction': 'bg-purple-100 text-purple-800'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusClass = (status: string) => {
  const classes = {
    'sent': 'bg-yellow-100 text-yellow-800',
    'delivered': 'bg-green-100 text-green-800',
    'failed': 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getTypeIconClass = (type: string) => {
  const classes = {
    'system': 'bg-red-100 text-red-600',
    'marketing': 'bg-green-100 text-green-600',
    'order': 'bg-blue-100 text-blue-600',
    'auction': 'bg-purple-100 text-purple-600'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100 text-gray-600'
}

const getTypeIcon = (type: string) => {
  const icons = {
    'system': 'fa-solid fa-cog',
    'marketing': 'fa-solid fa-bullhorn',
    'order': 'fa-solid fa-shopping-cart',
    'auction': 'fa-solid fa-gavel'
  }
  return icons[type as keyof typeof icons] || 'fa-solid fa-bell'
}

const formatDateTime = (date: Date) => {
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const viewNotification = (notification: any) => {
  console.log('Viewing notification:', notification)
}

const duplicateNotification = (notification: any) => {
  console.log('Duplicating notification:', notification)
}

const deleteNotification = (id: number) => {
  if (confirm('Are you sure you want to delete this notification?')) {
    const index = notifications.value.findIndex(n => n.id === id)
    if (index > -1) {
      notifications.value.splice(index, 1)
    }
  }
}

const sendSystemAlert = () => {
  notificationForm.value = {
    type: 'system',
    audience: 'all',
    title: 'System Alert',
    message: '',
    sendEmail: true,
    sendPush: true
  }
  showCreateModal.value = true
}

const sendMaintenanceNotice = () => {
  notificationForm.value = {
    type: 'system',
    audience: 'all',
    title: 'Scheduled Maintenance',
    message: 'We will be performing scheduled maintenance...',
    sendEmail: true,
    sendPush: false
  }
  showCreateModal.value = true
}

const sendPromotionalEmail = () => {
  notificationForm.value = {
    type: 'marketing',
    audience: 'active',
    title: 'Special Offer',
    message: '',
    sendEmail: true,
    sendPush: false
  }
  showCreateModal.value = true
}

const useTemplate = (template: any) => {
  notificationForm.value = {
    type: template.type,
    audience: 'all',
    title: template.name,
    message: template.description,
    sendEmail: true,
    sendPush: false
  }
  showCreateModal.value = true
}

const sendNotification = () => {
  console.log('Sending notification:', notificationForm.value)
  showCreateModal.value = false
  // Reset form
  notificationForm.value = {
    type: '',
    audience: '',
    title: '',
    message: '',
    sendEmail: true,
    sendPush: false
  }
}

onMounted(() => {
  console.log('Admin Notifications loaded')
})
</script>

<style scoped>
/* Custom styles */
</style>
