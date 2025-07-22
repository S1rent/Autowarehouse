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
                  {{ totalCount }}
                </span>
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
          </div> -->
        </div>

        <!-- Notification Content -->
        <div class="lg:col-span-3">
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Latest Notifications</h3>
                <!-- <div class="flex items-center space-x-2">
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
                </div> -->
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
                          v-if="!notification.isRead"
                          :class="getUnreadDotClass(notification.type)"
                          class="w-2 h-2 rounded-full"
                        ></span>
                        <span class="text-xs text-gray-500">{{ formatTime(notification.createdAt) }}</span>
                      </div>
                    </div>
                    <p class="text-sm text-gray-600 mt-1">{{ notification.message }}</p>
                  </div>
                </div>
              </div>
            </div>

            <!-- Error State -->
            <div v-if="error" class="p-12 text-center">
              <i class="fa-solid fa-exclamation-triangle text-4xl text-red-300 mb-4"></i>
              <h3 class="text-lg font-medium text-gray-900 mb-2">Unable to load notifications</h3>
              <p class="text-gray-500 mb-4">{{ error }}</p>
              <div class="flex justify-center space-x-3">
                <button 
                  v-if="error.includes('login')"
                  @click="$router.push('/login')"
                  class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
                >
                  Go to Login
                </button>
                <button 
                  @click="loadNotifications"
                  class="px-4 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors"
                >
                  Try Again
                </button>
              </div>
            </div>

            <!-- Loading State -->
            <div v-else-if="loading" class="p-12 text-center">
              <i class="fa-solid fa-spinner fa-spin text-4xl text-gray-300 mb-4"></i>
              <h3 class="text-lg font-medium text-gray-900 mb-2">Loading notifications...</h3>
              <p class="text-gray-500">Please wait while we fetch your notifications.</p>
            </div>

            <!-- Empty State -->
            <div v-else-if="filteredNotifications.length === 0" class="p-12 text-center">
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
                    v-for="page in totalPagesComputed" 
                    :key="page"
                    @click="currentPage = page; loadNotifications()"
                    :class="currentPage === page ? 'bg-blue-600 text-white' : 'text-gray-500 hover:text-gray-700'"
                    class="px-3 py-1 text-sm rounded"
                  >
                    {{ page }}
                  </button>
                  <button 
                    @click="nextPage"
                    :disabled="currentPage === totalPagesComputed"
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
import { ref, computed, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { apiService, type NotificationResponse, type NotificationListResponse } from '../services/api'

import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()

// State
const activeFilter = ref('all')
const currentPage = ref(1)
const itemsPerPage = 5
const loading = ref(false)
const error = ref('')

// Settings
const settings = reactive({
  pushNotifications: true,
  emailNotifications: true,
  smsNotifications: false
})

// Notifications data
const notifications = ref<NotificationResponse[]>([])
const totalCount = ref(0)

// Get user data from localStorage
const getUserData = () => {
  const userData = localStorage.getItem('user_data')
  return userData ? JSON.parse(userData) : null
}

// Load notifications from API
const loadNotifications = async () => {
  try {
    loading.value = true
    error.value = ''
    
    // Check if user is logged in
    const token = localStorage.getItem('auth_token')
    if (!token) {
      // If no token, show empty state but don't show error
      notifications.value = []
      totalCount.value = 0
      return
    }
    
    const response = await apiService.getNotifications({
      page: currentPage.value - 1, // API uses 0-based pagination
      size: itemsPerPage,
      type: activeFilter.value === 'all' ? undefined : activeFilter.value,
      unreadOnly: activeFilter.value === 'customerService'
    })
    
    notifications.value = response.notifications
    totalCount.value = response.totalCount
  } catch (err: any) {
    console.error('Error loading notifications:', err)
    
    // Handle different error types
    // if (err.response?.status === 401 || err.response?.status === 403) {
    //   // Authentication/authorization error - redirect to login or show login prompt
    //   error.value = 'Please login to view notifications'
    //   notifications.value = []
    //   totalCount.value = 0
    // } else {
    //   error.value = err.message || 'Failed to load notifications'
    // }
  } finally {
    loading.value = false
  }
}

// Computed properties
const filteredNotifications = computed(() => {
  let filtered = notifications.value
  
  switch (activeFilter.value) {
    case 'customerService':
      filtered = notifications.value.filter(n => !n.isRead)
      break
    case 'order':
      filtered = notifications.value.filter(n => n.type === 'ORDER' || n.priority === 'HIGH')
      break
    case 'read':
      filtered = notifications.value.filter(n => n.isRead)
      break
  }
  
  return filtered
})

const unreadCount = computed(() => notifications.value.filter(n => !n.isRead).length)
const importantCount = computed(() => notifications.value.filter(n => n.type === 'ORDER' || n.priority === 'HIGH').length)
const readCount = computed(() => notifications.value.filter(n => n.isRead).length)
const totalPagesComputed = computed(() => Math.ceil(totalCount.value / itemsPerPage))

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

const formatTime = (timestamp: string) => {
  const date = new Date(timestamp)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  
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

const markAsRead = async (id: number) => {
  try {
    await apiService.markNotificationAsRead(id)
    const notification = notifications.value.find(n => n.id === id)
    if (notification) {
      notification.isRead = true
    }
  } catch (err) {
    console.error('Error marking notification as read:', err)
  }
}

const markAllAsRead = async () => {
  try {
    await apiService.markAllNotificationsAsRead()
    notifications.value.forEach(notification => {
      notification.isRead = true
    })
  } catch (err) {
    console.error('Error marking all notifications as read:', err)
  }
}

const deleteAll = async () => {
  if (confirm('Are you sure you want to delete all notifications?')) {
    try {
      await apiService.clearAllNotifications()
      notifications.value.splice(0)
    } catch (err) {
      console.error('Error clearing all notifications:', err)
    }
  }
}

const handleAction = (action: any, notificationId: number) => {
  console.log('Action clicked:', action.label, 'for notification:', notificationId)
  alert(`${action.label} clicked for notification ${notificationId}`)
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
    loadNotifications()
  }
}

const nextPage = () => {
  if (currentPage.value < totalPagesComputed.value) {
    currentPage.value++
    loadNotifications()
  }
}

// Watch for filter changes
const changeFilter = (filter: string) => {
  activeFilter.value = filter
  currentPage.value = 1
  loadNotifications()
}

// Initialize
onMounted(() => {
  loadNotifications()
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
