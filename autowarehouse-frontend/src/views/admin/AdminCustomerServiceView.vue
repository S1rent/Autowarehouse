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
            class="flex items-center px-6 py-3 text-gray-700 bg-blue-50 border-r-4 border-blue-600"
          >
            <i class="fa-solid fa-headset mr-3"></i>
            Customer Service
          </router-link>
          <router-link 
            to="/admin/notifications" 
            class="flex items-center px-6 py-3 text-gray-600 hover:bg-gray-50 hover:text-gray-900"
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
                <h1 class="text-2xl font-bold text-gray-900">Customer Service Chat</h1>
                <p class="text-gray-600">Real-time chat with customers</p>
              </div>
              <div class="flex items-center space-x-4">
                <div class="flex items-center space-x-2">
                  <div class="w-3 h-3 bg-green-500 rounded-full"></div>
                  <span class="text-sm text-gray-600">{{ onlineCustomers.length }} customers online</span>
                </div>
                <button class="relative p-2 text-gray-400 hover:text-gray-600">
                  <i class="fa-solid fa-bell text-xl"></i>
                  <span v-if="unreadCount > 0" class="absolute -top-1 -right-1 w-5 h-5 bg-red-500 text-white text-xs rounded-full flex items-center justify-center">{{ unreadCount }}</span>
                </button>
                <div class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center">
                  <i class="fa-solid fa-user text-white text-sm"></i>
                </div>
              </div>
            </div>
          </div>
        </header>

        <!-- Chat Interface -->
        <main class="flex h-[calc(100vh-120px)]">
          <!-- Customer List -->
          <div class="w-80 bg-white border-r border-gray-200">
            <div class="p-4 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Customer Chats</h3>
              <p class="text-sm text-gray-500">{{ chatSessions.length }} active conversations</p>
            </div>
            
            <div class="overflow-y-auto h-full">
              <div 
                v-for="session in chatSessions" 
                :key="session.customerId"
                @click="selectCustomer(session)"
                :class="selectedCustomer?.customerId === session.customerId ? 'bg-blue-50 border-r-4 border-blue-600' : 'hover:bg-gray-50'"
                class="p-4 border-b border-gray-100 cursor-pointer transition-colors"
              >
                <div class="flex items-start space-x-3">
                  <div class="relative">
                    <img 
                      :src="session.customerAvatar || 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face'" 
                      :alt="session.customerName"
                      class="w-10 h-10 rounded-full"
                    >
                    <div 
                      :class="session.isOnline ? 'bg-green-500' : 'bg-gray-400'"
                      class="absolute -bottom-1 -right-1 w-3 h-3 rounded-full border-2 border-white"
                    ></div>
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="flex items-center justify-between">
                      <h4 class="font-medium text-gray-900 truncate">{{ session.customerName }}</h4>
                      <span class="text-xs text-gray-500">{{ formatTime(session.lastMessageTime) }}</span>
                    </div>
                    <p class="text-sm text-gray-600 truncate">{{ session.lastMessage || 'No messages yet' }}</p>
                    <div class="flex items-center justify-between mt-1">
                      <span class="text-xs text-gray-500">ID: {{ session.customerId }}</span>
                      <span 
                        v-if="session.unreadCount > 0"
                        class="inline-flex items-center justify-center w-5 h-5 bg-red-500 text-white text-xs rounded-full"
                      >
                        {{ session.unreadCount }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- Empty State -->
              <div v-if="chatSessions.length === 0" class="p-8 text-center">
                <i class="fa-solid fa-comments text-4xl text-gray-300 mb-4"></i>
                <h3 class="text-lg font-medium text-gray-900 mb-2">No Active Chats</h3>
                <p class="text-gray-500">Waiting for customers to start conversations...</p>
              </div>
            </div>
          </div>

          <!-- Chat Area -->
          <div class="flex-1 flex flex-col">
            <!-- Chat Header -->
            <div v-if="selectedCustomer" class="bg-white border-b border-gray-200 p-4">
              <div class="flex items-center justify-between">
                <div class="flex items-center space-x-3">
                  <img 
                    :src="selectedCustomer.customerAvatar || 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face'" 
                    :alt="selectedCustomer.customerName"
                    class="w-10 h-10 rounded-full"
                  >
                  <div>
                    <h3 class="font-semibold text-gray-900">{{ selectedCustomer.customerName }}</h3>
                    <p class="text-sm text-gray-500">
                      <span :class="selectedCustomer.isOnline ? 'text-green-600' : 'text-gray-500'">
                        <i class="fa-solid fa-circle text-xs mr-1"></i>
                        {{ selectedCustomer.isOnline ? 'Online' : 'Offline' }}
                      </span>
                      <span v-if="isTyping" class="ml-2 text-blue-600">
                        <i class="fa-solid fa-ellipsis animate-pulse"></i>
                        typing...
                      </span>
                    </p>
                  </div>
                </div>
                <div class="flex items-center space-x-2">
                  <button class="p-2 text-gray-400 hover:text-gray-600 rounded-lg hover:bg-gray-100">
                    <i class="fa-solid fa-phone"></i>
                  </button>
                  <button class="p-2 text-gray-400 hover:text-gray-600 rounded-lg hover:bg-gray-100">
                    <i class="fa-solid fa-video"></i>
                  </button>
                  <button class="p-2 text-gray-400 hover:text-gray-600 rounded-lg hover:bg-gray-100">
                    <i class="fa-solid fa-ellipsis-vertical"></i>
                  </button>
                </div>
              </div>
            </div>

            <!-- Messages -->
            <div class="flex-1 overflow-y-auto p-4 space-y-4" ref="messagesContainer">
              <div v-if="!selectedCustomer" class="flex items-center justify-center h-full">
                <div class="text-center">
                  <i class="fa-solid fa-comments text-6xl text-gray-300 mb-4"></i>
                  <h3 class="text-xl font-medium text-gray-900 mb-2">Select a Customer</h3>
                  <p class="text-gray-500">Choose a customer from the list to start chatting</p>
                </div>
              </div>

              <div v-else>
                <div 
                  v-for="message in messages" 
                  :key="message.id"
                  :class="message.isAdmin ? 'justify-end' : 'justify-start'"
                  class="flex"
                >
                  <div 
                    :class="message.isAdmin ? 'bg-blue-600 text-white' : 'bg-gray-200 text-gray-900'"
                    class="max-w-xs lg:max-w-md px-4 py-2 rounded-lg"
                  >
                    <p class="text-sm">{{ message.text }}</p>
                    <p 
                      :class="message.isAdmin ? 'text-blue-100' : 'text-gray-500'"
                      class="text-xs mt-1"
                    >
                      {{ formatTime(message.timestamp) }}
                    </p>
                  </div>
                </div>
              </div>
            </div>

            <!-- Message Input -->
            <div v-if="selectedCustomer" class="bg-white border-t border-gray-200 p-4">
              <form @submit.prevent="sendMessage" class="flex items-center space-x-4">
                <button type="button" class="p-2 text-gray-400 hover:text-gray-600">
                  <i class="fa-solid fa-paperclip"></i>
                </button>
                <div class="flex-1">
                  <input 
                    v-model="newMessage"
                    @input="handleTyping"
                    type="text" 
                    placeholder="Type your message..."
                    class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  >
                </div>
                <button 
                  type="submit"
                  :disabled="!newMessage.trim()"
                  :class="newMessage.trim() ? 'bg-blue-600 hover:bg-blue-700' : 'bg-gray-300'"
                  class="px-6 py-2 text-white rounded-lg transition-colors"
                >
                  <i class="fa-solid fa-paper-plane"></i>
                </button>
              </form>
            </div>
          </div>
        </main>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import { useAuthStore } from '../../stores/auth'
import { apiService } from '../../services/api'

// Types
interface ChatSession {
  customerId: number
  customerName: string
  customerAvatar?: string
  isOnline: boolean
  lastMessage?: string
  lastMessageTime: string
  unreadCount: number
  ticketId?: number // Add ticket ID to track the active ticket
}

interface Message {
  id: number
  text: string
  isAdmin: boolean
  timestamp: string
  customerId: number
}

// State
const authStore = useAuthStore()
const selectedCustomer = ref<ChatSession | null>(null)
const websocket = ref<WebSocket | null>(null)
const isConnected = ref(false)
const messages = ref<Message[]>([])
const newMessage = ref('')
const isTyping = ref(false)
const typingTimeout = ref<NodeJS.Timeout | null>(null)
const messagesContainer = ref<HTMLElement | null>(null)

// Real data from backend
const chatSessions = ref<ChatSession[]>([])

// Computed
const onlineCustomers = computed(() => chatSessions.value.filter(session => session.isOnline))
const unreadCount = computed(() => chatSessions.value.reduce((total, session) => total + session.unreadCount, 0))
const currentUserId = computed(() => authStore.user?.id || 1) // Admin user ID

// WebSocket Methods
const initWebSocket = () => {
  if (!currentUserId.value) {
    console.error('Cannot initialize WebSocket: user not authenticated')
    return
  }

  try {
    websocket.value = new WebSocket(`ws://localhost:8081/ws/customer-service/${currentUserId.value}`)
    
    websocket.value.onopen = () => {
      console.log('Admin WebSocket connected')
      isConnected.value = true
    }
    
    websocket.value.onmessage = (event) => {
      const message = JSON.parse(event.data)
      handleWebSocketMessage(message)
    }
    
    websocket.value.onclose = () => {
      console.log('Admin WebSocket disconnected')
      isConnected.value = false
      
      // Attempt to reconnect after 3 seconds
      setTimeout(() => {
        if (!isConnected.value) {
          initWebSocket()
        }
      }, 3000)
    }
    
    websocket.value.onerror = (error) => {
      console.error('Admin WebSocket error:', error)
      isConnected.value = false
    }
  } catch (error) {
    console.error('Failed to initialize WebSocket:', error)
  }
}

const handleWebSocketMessage = (message: any) => {
  switch (message.type) {
    case 'RECEIVE_MESSAGE':
      if (message.data) {
        const newMsg: Message = {
          id: message.data.id || Date.now(),
          text: message.data.message,
          isAdmin: message.data.senderType === 'AGENT',
          timestamp: message.data.timestamp || new Date().toISOString(),
          customerId: message.data.senderId
        }
        
        messages.value.push(newMsg)
        
        // Update chat session
        const session = chatSessions.value.find(s => s.customerId === newMsg.customerId)
        if (session) {
          session.lastMessage = newMsg.text
          session.lastMessageTime = newMsg.timestamp
          if (!newMsg.isAdmin && selectedCustomer.value?.customerId !== newMsg.customerId) {
            session.unreadCount++
          }
        }
        
        scrollToBottom()
      }
      break
      
    case 'USER_ONLINE':
      if (message.userId) {
        const session = chatSessions.value.find(s => s.customerId === message.userId)
        if (session) {
          session.isOnline = true
        }
      }
      break
      
    case 'USER_OFFLINE':
      if (message.userId) {
        const session = chatSessions.value.find(s => s.customerId === message.userId)
        if (session) {
          session.isOnline = false
        }
      }
      break
      
    case 'TYPING_START':
      if (selectedCustomer.value && message.userId === selectedCustomer.value.customerId) {
        isTyping.value = true
      }
      break
      
    case 'TYPING_STOP':
      if (selectedCustomer.value && message.userId === selectedCustomer.value.customerId) {
        isTyping.value = false
      }
      break
  }
}

// Methods
const selectCustomer = (session: ChatSession) => {
  selectedCustomer.value = session
  session.unreadCount = 0 // Mark as read
  
  // Load chat history for this customer
  loadChatHistory(session.customerId)
  
  // Join chat room via WebSocket using the actual ticket ID
  if (websocket.value && websocket.value.readyState === WebSocket.OPEN && session.ticketId) {
    websocket.value.send(JSON.stringify({
      type: 'JOIN_ROOM',
      ticketId: session.ticketId // Use the actual ticket ID
    }))
    console.log('Admin joined ticket room:', session.ticketId)
  }
}

const loadChatHistory = (customerId: number) => {
  // Filter messages for selected customer
  messages.value = messages.value.filter(msg => msg.customerId === customerId)
  
  // In a real app, you would load from API
  // For now, we'll just clear and wait for real-time messages
  nextTick(() => {
    scrollToBottom()
  })
}

const sendMessage = () => {
  const text = newMessage.value.trim()
  if (!text || !selectedCustomer.value || !websocket.value || !selectedCustomer.value.ticketId) return

  try {
    // Send via WebSocket using the actual ticket ID
    websocket.value.send(JSON.stringify({
      type: 'SEND_MESSAGE',
      ticketId: selectedCustomer.value.ticketId, // Use the actual ticket ID
      message: text
    }))
    
    // Don't add to local messages - wait for WebSocket confirmation
    // Update chat session
    selectedCustomer.value.lastMessage = text
    selectedCustomer.value.lastMessageTime = new Date().toISOString()
    
    newMessage.value = ''
  } catch (error) {
    console.error('Error sending message:', error)
  }
}

const handleTyping = () => {
  if (!selectedCustomer.value || !websocket.value || !selectedCustomer.value.ticketId) return
  
  // Send typing start using the actual ticket ID
  websocket.value.send(JSON.stringify({
    type: 'TYPING_START',
    ticketId: selectedCustomer.value.ticketId // Use the actual ticket ID
  }))
  
  // Clear existing timeout
  if (typingTimeout.value) {
    clearTimeout(typingTimeout.value)
  }
  
  // Set timeout to send typing stop
  typingTimeout.value = setTimeout(() => {
    if (websocket.value && selectedCustomer.value && selectedCustomer.value.ticketId) {
      websocket.value.send(JSON.stringify({
        type: 'TYPING_STOP',
        ticketId: selectedCustomer.value.ticketId // Use the actual ticket ID
      }))
    }
  }, 1000)
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const formatTime = (dateString: string) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffInMinutes = Math.floor((now.getTime() - date.getTime()) / (1000 * 60))
  
  if (diffInMinutes < 1) return 'now'
  if (diffInMinutes < 60) return `${diffInMinutes}m ago`
  if (diffInMinutes < 1440) return `${Math.floor(diffInMinutes / 60)}h ago`
  
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// API Methods
const loadTickets = async () => {
  try {
    const tickets = await apiService.getAllTickets()
    
    // Group tickets by customer ID to avoid duplicates, but keep the latest ticket ID
    const customerMap = new Map<number, ChatSession>()
    
    tickets.forEach(ticket => {
      const existingSession = customerMap.get(ticket.customerId)
      
      if (!existingSession) {
        // Create new chat session for this customer
        customerMap.set(ticket.customerId, {
          customerId: ticket.customerId,
          customerName: ticket.customerName,
          customerAvatar: undefined, // Will use default avatar
          isOnline: false, // Will be updated via WebSocket
          lastMessage: ticket.subject,
          lastMessageTime: ticket.createdAt,
          unreadCount: 0, // Will be updated via WebSocket
          ticketId: ticket.id // Store the actual ticket ID
        })
      } else {
        // Update with latest ticket info if this ticket is newer
        const ticketDate = new Date(ticket.createdAt)
        const existingDate = new Date(existingSession.lastMessageTime)
        
        if (ticketDate > existingDate) {
          existingSession.lastMessage = ticket.subject
          existingSession.lastMessageTime = ticket.createdAt
          existingSession.ticketId = ticket.id // Update to latest ticket ID
        }
      }
    })
    
    // Convert map to array
    chatSessions.value = Array.from(customerMap.values())
    
    console.log('Loaded unique customers:', chatSessions.value.length)
  } catch (error) {
    console.error('Error loading tickets:', error)
  }
}

onMounted(() => {
  console.log('Admin Customer Service Chat loaded')
  loadTickets()
  initWebSocket()
})

onUnmounted(() => {
  if (websocket.value) {
    websocket.value.close()
  }
  if (typingTimeout.value) {
    clearTimeout(typingTimeout.value)
  }
})
</script>

<style scoped>
/* Custom scrollbar */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
