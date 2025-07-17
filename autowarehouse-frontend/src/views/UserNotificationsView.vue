<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <UserNavbar/>

    <!-- Main Content -->
    <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-3 mb-2">
          <button @click="$router.go(-1)" class="text-gray-500 hover:text-gray-700">
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <h1 class="text-2xl font-bold text-gray-900">Notifications</h1>
        </div>
        <p class="text-gray-600">Manage all your notifications and notification settings</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-4 gap-6">
        <!-- Notification Sidebar -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Filter</h3>
            <div class="space-y-3">
              <button 
                @click="activeFilter = 'all'"
                :class="activeFilter === 'all' ? 'bg-blue-600 text-white' : 'hover:bg-gray-50'"
                class="w-full flex items-center justify-between p-3 text-left rounded-lg transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <i class="fa-solid fa-inbox"></i>
                  <span class="text-sm font-medium">All</span>
                </div>
                <span 
                  :class="activeFilter === 'all' ? 'bg-white text-blue-600' : 'bg-gray-100 text-gray-600'"
                  class="text-xs px-2 py-1 rounded-full"
                >
                  {{ notifications.length }}
                </span>
              </button>
              <button 
                @click="activeFilter = 'unread'"
                :class="activeFilter === 'unread' ? 'bg-blue-600 text-white' : 'hover:bg-gray-50'"
                class="w-full flex items-center justify-between p-3 text-left rounded-lg transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <i class="fa-solid fa-circle text-red-500 text-xs"></i>
                  <span class="text-sm font-medium text-gray-700">Unread</span>
                </div>
                <span class="text-xs bg-red-100 text-red-600 px-2 py-1 rounded-full">{{ unreadCount }}</span>
              </button>
              <button 
                @click="activeFilter = 'important'"
                :class="activeFilter === 'important' ? 'bg-blue-600 text-white' : 'hover:bg-gray-50'"
                class="w-full flex items-center justify-between p-3 text-left rounded-lg transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <i class="fa-solid fa-star text-yellow-500"></i>
                  <span class="text-sm font-medium text-gray-700">Important</span>
                </div>
                <span class="text-xs bg-yellow-100 text-yellow-600 px-2 py-1 rounded-full">{{ importantCount }}</span>
              </button>
              <button 
                @click="activeFilter = 'read'"
                :class="activeFilter === 'read' ? 'bg-blue-600 text-white' : 'hover:bg-gray-50'"
                class="w-full flex items-center justify-between p-3 text-left rounded-lg transition-colors"
              >
                <div class="flex items-center space-x-3">
                  <i class="fa-solid fa-check text-green-500"></i>
                  <span class="text-sm font-medium text-gray-700">Read</span>
                </div>
                <span class="text-xs bg-green-100 text-green-600 px-2 py-1 rounded-full">{{ readCount }}</span>
              </button>
            </div>
          </div>

          <!-- Notification Settings -->
          <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mt-6">
            <h4 class="text-lg font-semibold text-gray-900 mb-4">Settings</h4>
            <div class="space-y-4">
              <div class="flex items-center justify-between">
                <span class="text-sm font-medium text-gray-700">Push Notifications</span>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input 
                    type="checkbox" 
                    v-model="settings.pushNotifications"
                    class="sr-only peer"
                  >
                  <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                </label>
              </div>
              <div class="flex items-center justify-between">
                <span class="text-sm font-medium text-gray-700">Email</span>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input 
                    type="checkbox" 
                    v-model="settings.emailNotifications"
                    class="sr-only peer"
                  >
                  <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                </label>
              </div>
              <div class="flex items-center justify-between">
                <span class="text-sm font-medium text-gray-700">SMS</span>
                <label class="relative inline-flex items-center cursor-pointer">
                  <input 
                    type="checkbox" 
                    v-model="settings.smsNotifications"
                    class="sr-only peer"
                  >
                  <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                </label>
              </div>
            </div>
          </div>
        </div> -->

        <!-- Notification Content -->
        <div class="lg:col-span-3">
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Latest Notifications</h3>
                <div class="flex items-center space-x-2">
                  <button 
                    @click="markAllAsRead"
                    class="text-sm text-blue-600 hover:text-blue-700 font-medium"
                  >
                    <i class="fa-solid fa-check mr-1"></i>
                    Mark All as Read
                  </button>
                  <button 
                    @click="deleteAll"
                    class="text-sm text-gray-500 hover:text-gray-700"
                  >
                    <i class="fa-solid fa-trash mr-1"></i>
                    Delete All
                  </button>
                </div>
              </div>
            </div>

            <!-- Notification List -->
            <div class="divide-y divide-gray-200">
              <div 
                v-for="notification in filteredNotifications" 
                :key="notification.id"
                @click="markAsRead(notification.id)"
                class="p-6 hover:bg-gray-50 cursor-pointer transition-colors"
                :class="getNotificationClasses(notification)"
              >
                <div class="flex items-start space-x-4">
                  <div class="flex-shrink-0">
                    <div 
                      :class="getIconClasses(notification.type)"
                      class="w-10 h-10 rounded-full flex items-center justify-center"
                    >
                      <i :class="getIconName(notification.type)"></i>
                    </div>
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="flex items-center justify-between">
                      <p class="text-sm font-semibold text-gray-900">{{ notification.title }}</p>
                      <div class="flex items-center space-x-2">
                        <span 
                          v-if="!notification.read"
                          :class="getUnreadDotClass(notification.type)"
                          class="w-2 h-2 rounded-full"
                        ></span>
                        <span class="text-xs text-gray-500">{{ formatTime(notification.timestamp) }}</span>
                      </div>
                    </div>
                    <p class="text-sm text-gray-600 mt-1">{{ notification.message }}</p>
                    <div v-if="notification.actions" class="flex items-center space-x-3 mt-3">
                      <button 
                        v-for="action in notification.actions" 
                        :key="action.label"
                        @click.stop="handleAction(action, notification.id)"
                        :class="action.primary ? getActionButtonClass(notification.type) : 'bg-gray-200 text-gray-700 hover:bg-gray-300'"
                        class="text-xs px-3 py-1 rounded-full transition-colors"
                      >
                        {{ action.label }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Empty State -->
            <div v-if="filteredNotifications.length === 0" class="p-12 text-center">
              <i class="fa-solid fa-bell-slash text-4xl text-gray-300 mb-4"></i>
              <h3 class="text-lg font-medium text-gray-900 mb-2">No notifications</h3>
              <p class="text-gray-500">You're all caught up! No new notifications to show.</p>
            </div>

            <!-- Pagination -->
            <div v-if="filteredNotifications.length > 0" class="p-6 border-t border-gray-200">
              <div class="flex items-center justify-between">
                <span class="text-sm text-gray-500">
                  Showing {{ Math.min(currentPage * itemsPerPage - itemsPerPage + 1, filteredNotifications.length) }}-{{ Math.min(currentPage * itemsPerPage, filteredNotifications.length) }} of {{ filteredNotifications.length }} notifications
                </span>
                <div class="flex items-center space-x-2">
                  <button 
                    @click="previousPage"
                    :disabled="currentPage === 1"
                    class="px-3 py-1 text-sm text-gray-500 hover:text-gray-700 disabled:opacity-50"
                  >
                    <i class="fa-solid fa-chevron-left"></i>
                  </button>
                  <button 
                    v-for="page in totalPages" 
                    :key="page"
                    @click="currentPage = page"
                    :class="currentPage === page ? 'bg-blue-600 text-white' : 'text-gray-500 hover:text-gray-700'"
                    class="px-3 py-1 text-sm rounded"
                  >
                    {{ page }}
                  </button>
                  <button 
                    @click="nextPage"
                    :disabled="currentPage === totalPages"
                    class="px-3 py-1 text-sm text-gray-500 hover:text-gray-700 disabled:opacity-50"
                  >
                    <i class="fa-solid fa-chevron-right"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
    <Footer/>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, reactive } from 'vue'
import { useRouter } from 'vue-router'

import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()

// State
const activeFilter = ref('all')
const currentPage = ref(1)
const itemsPerPage = 5

// Settings
const settings = reactive({
  pushNotifications: true,
  emailNotifications: true,
  smsNotifications: false
})

// Sample notifications data
const notifications = ref([
  {
    id: 1,
    type: 'security',
    title: 'Security Warning',
    message: 'Login attempt from new device detected. If this wasn\'t you, please change your password immediately.',
    timestamp: new Date(Date.now() - 2 * 60 * 1000), // 2 minutes ago
    read: false,
    important: true,
    actions: [
      { label: 'View Details', primary: true },
      { label: 'Dismiss', primary: false }
    ]
  },
  {
    id: 2,
    type: 'info',
    title: 'System Update',
    message: 'System will be updated tonight at 02:00 WIB. Service may be interrupted for 30 minutes.',
    timestamp: new Date(Date.now() - 60 * 60 * 1000), // 1 hour ago
    read: false,
    important: false,
    actions: [
      { label: 'Learn More', primary: true }
    ]
  },
  {
    id: 3,
    type: 'success',
    title: 'Payment Successful',
    message: 'Your premium subscription payment of Rp 99,000 has been successfully processed.',
    timestamp: new Date(Date.now() - 3 * 60 * 60 * 1000), // 3 hours ago
    read: false,
    important: false,
    actions: [
      { label: 'View Invoice', primary: true }
    ]
  },
  {
    id: 4,
    type: 'social',
    title: 'Friend Request',
    message: 'John Doe sent you a friend request.',
    timestamp: new Date(Date.now() - 24 * 60 * 60 * 1000), // 1 day ago
    read: true,
    important: false,
    actions: [
      { label: 'Accept', primary: true },
      { label: 'Decline', primary: false }
    ]
  },
  {
    id: 5,
    type: 'promotion',
    title: 'Special Promotion',
    message: 'Get 50% discount for premium upgrade. Limited time offer!',
    timestamp: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000), // 2 days ago
    read: true,
    important: false,
    actions: [
      { label: 'Claim Now', primary: true }
    ]
  }
])

// Computed properties
const filteredNotifications = computed(() => {
  let filtered = notifications.value
  
  switch (activeFilter.value) {
    case 'unread':
      filtered = notifications.value.filter(n => !n.read)
      break
    case 'important':
      filtered = notifications.value.filter(n => n.important)
      break
    case 'read':
      filtered = notifications.value.filter(n => n.read)
      break
  }
  
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const unreadCount = computed(() => notifications.value.filter(n => !n.read).length)
const importantCount = computed(() => notifications.value.filter(n => n.important).length)
const readCount = computed(() => notifications.value.filter(n => n.read).length)
const totalPages = computed(() => Math.ceil(notifications.value.length / itemsPerPage))

// Methods
const getNotificationClasses = (notification: any) => {
  const classes = []
  if (!notification.read) {
    switch (notification.type) {
      case 'security':
        classes.push('border-l-4', 'border-l-red-500', 'bg-red-50')
        break
      case 'info':
        classes.push('border-l-4', 'border-l-blue-500', 'bg-blue-50')
        break
      case 'success':
        classes.push('border-l-4', 'border-l-green-500', 'bg-green-50')
        break
    }
  }
  return classes
}

const getIconClasses = (type: string) => {
  switch (type) {
    case 'security':
      return 'bg-red-100 text-red-600'
    case 'info':
      return 'bg-blue-100 text-blue-600'
    case 'success':
      return 'bg-green-100 text-green-600'
    case 'social':
      return 'bg-gray-100 text-gray-600'
    case 'promotion':
      return 'bg-gray-100 text-gray-600'
    default:
      return 'bg-gray-100 text-gray-600'
  }
}

const getIconName = (type: string) => {
  switch (type) {
    case 'security':
      return 'fa-solid fa-exclamation-triangle'
    case 'info':
      return 'fa-solid fa-info-circle'
    case 'success':
      return 'fa-solid fa-check-circle'
    case 'social':
      return 'fa-solid fa-user-plus'
    case 'promotion':
      return 'fa-solid fa-gift'
    default:
      return 'fa-solid fa-bell'
  }
}

const getUnreadDotClass = (type: string) => {
  switch (type) {
    case 'security':
      return 'bg-red-500'
    case 'info':
      return 'bg-blue-500'
    case 'success':
      return 'bg-green-500'
    default:
      return 'bg-gray-500'
  }
}

const getActionButtonClass = (type: string) => {
  switch (type) {
    case 'security':
      return 'bg-red-600 text-white hover:bg-red-700'
    case 'info':
      return 'bg-blue-600 text-white hover:bg-blue-700'
    case 'success':
      return 'bg-green-600 text-white hover:bg-green-700'
    case 'promotion':
      return 'bg-orange-600 text-white hover:bg-orange-700'
    default:
      return 'bg-blue-600 text-white hover:bg-blue-700'
  }
}

const formatTime = (timestamp: Date) => {
  const now = new Date()
  const diff = now.getTime() - timestamp.getTime()
  
  const minutes = Math.floor(diff / (1000 * 60))
  const hours = Math.floor(diff / (1000 * 60 * 60))
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))
  
  if (minutes < 60) {
    return `${minutes} minutes ago`
  } else if (hours < 24) {
    return `${hours} hours ago`
  } else {
    return `${days} days ago`
  }
}

const markAsRead = (id: number) => {
  const notification = notifications.value.find(n => n.id === id)
  if (notification) {
    notification.read = true
  }
}

const markAllAsRead = () => {
  notifications.value.forEach(notification => {
    notification.read = true
  })
}

const deleteAll = () => {
  if (confirm('Are you sure you want to delete all notifications?')) {
    notifications.value.splice(0)
  }
}

const handleAction = (action: any, notificationId: number) => {
  console.log('Action clicked:', action.label, 'for notification:', notificationId)
  alert(`${action.label} clicked for notification ${notificationId}`)
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
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
