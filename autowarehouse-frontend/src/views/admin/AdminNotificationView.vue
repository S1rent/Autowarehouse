<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex justify-between items-center">
          <div>
            <h1 class="text-3xl font-bold text-gray-900">Notifications</h1>
            <p class="text-gray-600 mt-1">Kelola notifikasi sistem dan komunikasi</p>
          </div>
          <div class="flex space-x-3">
            <button 
              @click="markAllAsRead"
              class="bg-white border border-gray-300 text-gray-700 px-4 py-2 rounded-lg hover:bg-gray-50 flex items-center space-x-2"
            >
              <i class="fa-solid fa-check-double text-sm"></i>
              <span>Mark All Read</span>
            </button>
            <button 
              @click="showCreateModal = true"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 flex items-center space-x-2"
            >
              <i class="fa-solid fa-plus text-sm"></i>
              <span>Send Notification</span>
            </button>
          </div>
        </div>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-blue-100">
              <i class="fa-solid fa-bell text-blue-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Notifikasi</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-yellow-100">
              <i class="fa-solid fa-exclamation-circle text-yellow-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Belum Dibaca</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.unread }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-green-100">
              <i class="fa-solid fa-paper-plane text-green-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Terkirim Hari Ini</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.sentToday }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-purple-100">
              <i class="fa-solid fa-users text-purple-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Penerima</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.totalRecipients }}</p>
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
                placeholder="Cari notifikasi..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
              >
            </div>
            <select 
              v-model="typeFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">Semua Tipe</option>
              <option value="order">Pesanan</option>
              <option value="system">Sistem</option>
              <option value="promotion">Promosi</option>
              <option value="security">Keamanan</option>
            </select>
            <select 
              v-model="statusFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">Semua Status</option>
              <option value="read">Dibaca</option>
              <option value="unread">Belum Dibaca</option>
            </select>
          </div>
          <button @click="refreshNotifications" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-arrows-rotate"></i>
          </button>
        </div>
      </div>

      <!-- Notifications List -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200">
        <div class="px-6 py-4 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">Daftar Notifikasi</h3>
        </div>
        
        <div class="divide-y divide-gray-200">
          <div 
            v-for="notification in filteredNotifications" 
            :key="notification.id"
            :class="notification.isRead ? 'bg-white' : 'bg-blue-50'"
            class="p-6 hover:bg-gray-50 transition-colors"
          >
            <div class="flex items-start space-x-4">
              <div :class="getTypeIconClass(notification.type)" class="p-2 rounded-full">
                <i :class="getTypeIcon(notification.type)" class="text-lg"></i>
              </div>
              
              <div class="flex-1 min-w-0">
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-2">
                    <h4 class="text-sm font-medium text-gray-900">{{ notification.title }}</h4>
                    <span :class="getTypeClass(notification.type)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full">
                      {{ getTypeText(notification.type) }}
                    </span>
                    <span v-if="!notification.isRead" class="w-2 h-2 bg-blue-600 rounded-full"></span>
                  </div>
                  <div class="flex items-center space-x-2">
                    <span class="text-xs text-gray-500">{{ formatTime(notification.timestamp) }}</span>
                    <button 
                      @click="toggleRead(notification)"
                      :class="notification.isRead ? 'text-gray-400' : 'text-blue-600'"
                      class="hover:text-blue-700"
                      :title="notification.isRead ? 'Mark as unread' : 'Mark as read'"
                    >
                      <i :class="notification.isRead ? 'fa-regular fa-envelope-open' : 'fa-regular fa-envelope'"></i>
                    </button>
                    <button 
                      @click="deleteNotification(notification.id)"
                      class="text-red-600 hover:text-red-700"
                      title="Delete"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <p class="text-sm text-gray-600 mt-1">{{ notification.message }}</p>
                
                <div class="flex items-center justify-between mt-3">
                  <div class="flex items-center space-x-4 text-xs text-gray-500">
                    <span>
                      <i class="fa-solid fa-user mr-1"></i>
                      {{ notification.recipient }}
                    </span>
                    <span v-if="notification.priority">
                      <i class="fa-solid fa-flag mr-1"></i>
                      {{ getPriorityText(notification.priority) }}
                    </span>
                  </div>
                  
                  <div v-if="notification.actions" class="flex space-x-2">
                    <button 
                      v-for="action in notification.actions" 
                      :key="action.label"
                      @click="handleAction(action, notification)"
                      :class="action.type === 'primary' ? 'bg-blue-600 text-white hover:bg-blue-700' : 'bg-gray-200 text-gray-700 hover:bg-gray-300'"
                      class="px-3 py-1 text-xs rounded-lg transition-colors"
                    >
                      {{ action.label }}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div class="px-6 py-4 border-t border-gray-200 flex items-center justify-between">
          <div class="text-sm text-gray-700">
            Showing {{ startIndex }} to {{ endIndex }} of {{ totalNotifications }} notifications
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

    <!-- Create Notification Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-2xl w-full max-h-96 overflow-y-auto">
        <div class="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h3 class="text-lg font-semibold text-gray-900">Send New Notification</h3>
          <button @click="showCreateModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <form @submit.prevent="sendNotification" class="p-6 space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Type</label>
              <select v-model="newNotification.type" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
                <option value="order">Pesanan</option>
                <option value="system">Sistem</option>
                <option value="promotion">Promosi</option>
                <option value="security">Keamanan</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">Priority</label>
              <select v-model="newNotification.priority" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600">
                <option value="low">Low</option>
                <option value="medium">Medium</option>
                <option value="high">High</option>
              </select>
            </div>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Title</label>
            <input v-model="newNotification.title" type="text" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Message</label>
            <textarea v-model="newNotification.message" rows="4" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required></textarea>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Recipient</label>
            <select v-model="newNotification.recipient" class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" required>
              <option value="all">All Users</option>
              <option value="customers">Customers Only</option>
              <option value="admins">Admins Only</option>
            </select>
          </div>
          
          <div class="flex justify-end space-x-3 pt-4">
            <button type="button" @click="showCreateModal = false" class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">
              Cancel
            </button>
            <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
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
import AdminNavbar from '../../components/AdminNavbar.vue'

interface NotificationAction {
  label: string
  type: 'primary' | 'secondary'
  action: string
}

interface Notification {
  id: string
  type: 'order' | 'system' | 'promotion' | 'security'
  title: string
  message: string
  timestamp: string
  isRead: boolean
  recipient: string
  priority?: 'low' | 'medium' | 'high'
  actions?: NotificationAction[]
}

const searchQuery = ref('')
const typeFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showCreateModal = ref(false)

const stats = ref({
  total: 156,
  unread: 23,
  sentToday: 12,
  totalRecipients: 2847
})

const newNotification = ref({
  type: 'system',
  title: '',
  message: '',
  recipient: 'all',
  priority: 'medium'
})

const notifications = ref<Notification[]>([
  {
    id: '1',
    type: 'order',
    title: 'Pesanan Baru #12345',
    message: 'Pesanan baru dari John Doe senilai Rp 1.500.000 perlu diproses.',
    timestamp: '2024-12-15T10:30:00',
    isRead: false,
    recipient: 'Admin Team',
    priority: 'high',
    actions: [
      { label: 'View Order', type: 'primary', action: 'view-order' },
      { label: 'Process', type: 'secondary', action: 'process-order' }
    ]
  },
  {
    id: '2',
    type: 'system',
    title: 'Server Maintenance',
    message: 'Maintenance server dijadwalkan pada 16 Desember 2024 pukul 02:00 WIB.',
    timestamp: '2024-12-15T09:15:00',
    isRead: true,
    recipient: 'All Users',
    priority: 'medium'
  },
  {
    id: '3',
    type: 'promotion',
    title: 'Flash Sale Berakhir',
    message: 'Flash sale elektronik akan berakhir dalam 2 jam. Pastikan stok mencukupi.',
    timestamp: '2024-12-15T08:45:00',
    isRead: false,
    recipient: 'Sales Team',
    priority: 'high'
  },
  {
    id: '4',
    type: 'security',
    title: 'Login Mencurigakan',
    message: 'Terdeteksi login dari IP address yang tidak dikenal pada akun admin.',
    timestamp: '2024-12-14T22:30:00',
    isRead: true,
    recipient: 'Security Team',
    priority: 'high',
    actions: [
      { label: 'Investigate', type: 'primary', action: 'investigate' },
      { label: 'Block IP', type: 'secondary', action: 'block-ip' }
    ]
  }
])

const filteredNotifications = computed(() => {
  let filtered = notifications.value

  if (searchQuery.value) {
    filtered = filtered.filter(notification =>
      notification.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      notification.message.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (typeFilter.value) {
    filtered = filtered.filter(notification => notification.type === typeFilter.value)
  }

  if (statusFilter.value) {
    filtered = filtered.filter(notification => 
      statusFilter.value === 'read' ? notification.isRead : !notification.isRead
    )
  }

  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalNotifications = computed(() => notifications.value.length)
const totalPages = computed(() => Math.ceil(notifications.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalNotifications.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatTime = (timestamp: string) => {
  const date = new Date(timestamp)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const hours = Math.floor(diff / (1000 * 60 * 60))
  
  if (hours < 1) return 'Baru saja'
  if (hours < 24) return `${hours} jam lalu`
  
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short'
  })
}

const getTypeIcon = (type: string) => {
  const icons = {
    order: 'fa-solid fa-shopping-cart text-white',
    system: 'fa-solid fa-cog text-white',
    promotion: 'fa-solid fa-tag text-white',
    security: 'fa-solid fa-shield-alt text-white'
  }
  return icons[type as keyof typeof icons] || 'fa-solid fa-bell text-white'
}

const getTypeIconClass = (type: string) => {
  const classes = {
    order: 'bg-blue-600',
    system: 'bg-gray-600',
    promotion: 'bg-green-600',
    security: 'bg-red-600'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-600'
}

const getTypeClass = (type: string) => {
  const classes = {
    order: 'bg-blue-100 text-blue-800',
    system: 'bg-gray-100 text-gray-800',
    promotion: 'bg-green-100 text-green-800',
    security: 'bg-red-100 text-red-800'
  }
  return classes[type as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getTypeText = (type: string) => {
  const texts = {
    order: 'Pesanan',
    system: 'Sistem',
    promotion: 'Promosi',
    security: 'Keamanan'
  }
  return texts[type as keyof typeof texts] || type
}

const getPriorityText = (priority: string) => {
  const texts = {
    low: 'Low',
    medium: 'Medium',
    high: 'High'
  }
  return texts[priority as keyof typeof texts] || priority
}

const toggleRead = (notification: Notification) => {
  notification.isRead = !notification.isRead
  if (notification.isRead) {
    stats.value.unread--
  } else {
    stats.value.unread++
  }
}

const markAllAsRead = () => {
  notifications.value.forEach(notification => {
    notification.isRead = true
  })
  stats.value.unread = 0
  alert('All notifications marked as read!')
}

const deleteNotification = (notificationId: string) => {
  if (confirm('Are you sure you want to delete this notification?')) {
    const index = notifications.value.findIndex(n => n.id === notificationId)
    if (index > -1) {
      const notification = notifications.value[index]
      notifications.value.splice(index, 1)
      stats.value.total--
      if (!notification.isRead) {
        stats.value.unread--
      }
      alert('Notification deleted successfully!')
    }
  }
}

const handleAction = (action: NotificationAction, notification: Notification) => {
  alert(`Action: ${action.action} for notification: ${notification.title}`)
}

const sendNotification = () => {
  const notification: Notification = {
    id: Date.now().toString(),
    type: newNotification.value.type as any,
    title: newNotification.value.title,
    message: newNotification.value.message,
    timestamp: new Date().toISOString(),
    isRead: false,
    recipient: newNotification.value.recipient,
    priority: newNotification.value.priority as any
  }
  
  notifications.value.unshift(notification)
  stats.value.total++
  stats.value.unread++
  stats.value.sentToday++
  
  showCreateModal.value = false
  newNotification.value = {
    type: 'system',
    title: '',
    message: '',
    recipient: 'all',
    priority: 'medium'
  }
  
  alert('Notification sent successfully!')
}

const refreshNotifications = () => {
  alert('Notifications refreshed!')
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
  console.log('Admin Notifications loaded')
})
</script>
