<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Navigation -->
    <nav class="bg-white shadow-sm border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex items-center">
            <router-link to="/" class="flex items-center space-x-3">
              <div class="w-8 h-8 bg-blue-600 rounded-lg flex items-center justify-center">
                <i class="fa-solid fa-store text-white text-sm"></i>
              </div>
              <span class="text-xl font-bold text-gray-900">Autowarehouse</span>
            </router-link>
          </div>
          
          <div class="flex items-center space-x-4">
            <router-link to="/cart" class="text-gray-600 hover:text-gray-900">
              <i class="fa-solid fa-shopping-cart text-xl"></i>
            </router-link>
            <router-link to="/profile" class="text-gray-600 hover:text-gray-900">
              <i class="fa-solid fa-user text-xl"></i>
            </router-link>
          </div>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <div class="max-w-4xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="text-center mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">Customer Support Chat</h1>
        <p class="text-gray-600">Get instant help from our support team</p>
        <div class="flex items-center justify-center mt-4 space-x-2">
          <div :class="isConnected ? 'bg-green-500' : 'bg-red-500'" class="w-3 h-3 rounded-full"></div>
          <span class="text-sm text-gray-600">
            {{ isConnected ? 'Connected' : 'Connecting...' }}
          </span>
        </div>
      </div>

      <!-- Chat Container -->
      <div class="bg-white rounded-xl shadow-lg overflow-hidden">
        <!-- Chat Header -->
        <div class="bg-blue-600 text-white p-4">
          <div class="flex items-center justify-between">
            <div class="flex items-center space-x-3">
              <div class="w-10 h-10 bg-blue-500 rounded-full flex items-center justify-center">
                <i class="fa-solid fa-headset"></i>
              </div>
              <div>
                <h3 class="font-semibold">Support Team</h3>
                <p class="text-blue-100 text-sm">
                  <span v-if="agentTyping" class="flex items-center">
                    <i class="fa-solid fa-ellipsis animate-pulse mr-1"></i>
                    Agent is typing...
                  </span>
                  <span v-else>We're here to help!</span>
                </p>
              </div>
            </div>
            <div class="flex items-center space-x-2">
              <button class="p-2 text-blue-100 hover:text-white rounded-lg hover:bg-blue-500">
                <i class="fa-solid fa-phone"></i>
              </button>
              <button class="p-2 text-blue-100 hover:text-white rounded-lg hover:bg-blue-500">
                <i class="fa-solid fa-video"></i>
              </button>
            </div>
          </div>
        </div>

        <!-- Messages Area -->
        <div class="h-96 overflow-y-auto p-4 space-y-4" ref="messagesContainer">
          <!-- Welcome Message -->
          <div v-if="messages.length === 0" class="text-center py-8">
            <i class="fa-solid fa-comments text-4xl text-gray-300 mb-4"></i>
            <h3 class="text-lg font-medium text-gray-900 mb-2">Start a Conversation</h3>
            <p class="text-gray-500">Send a message to connect with our support team</p>
          </div>

          <!-- Messages -->
          <div 
            v-for="message in messages" 
            :key="message.id"
            :class="message.isUser ? 'justify-end' : 'justify-start'"
            class="flex"
          >
            <div class="flex items-start space-x-2 max-w-xs lg:max-w-md">
              <!-- Avatar for agent messages -->
              <!-- <img 
                v-if="!message.isUser"
                src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=32&h=32&fit=crop&crop=face" 
                alt="Support Agent"
                class="w-8 h-8 rounded-full mt-1"
              > -->
              
              <div 
                :class="message.isUser ? 'bg-blue-600 text-white ml-auto' : 'bg-gray-200 text-gray-900'"
                class="px-4 py-2 rounded-lg"
              >
                <p class="text-sm">{{ message.text }}</p>
                <p 
                  :class="message.isUser ? 'text-blue-100' : 'text-gray-500'"
                  class="text-xs mt-1"
                >
                  {{ formatTime(message.timestamp) }}
                </p>
              </div>
              
              <!-- Avatar for user messages -->
              <!-- <img 
                v-if="message.isUser"
                src="https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face" 
                alt="You"
                class="w-8 h-8 rounded-full mt-1"
              > -->
            </div>
          </div>
        </div>

        <!-- Message Input -->
        <div class="border-t border-gray-200 p-4">
          <form @submit.prevent="sendMessage" class="flex items-center space-x-4">
            <button type="button" class="p-2 text-gray-400 hover:text-gray-600">
              <i class="fa-solid fa-paperclip"></i>
            </button>
            <button type="button" class="p-2 text-gray-400 hover:text-gray-600">
              <i class="fa-solid fa-smile"></i>
            </button>
            <div class="flex-1">
              <input 
                v-model="newMessage"
                @input="handleTyping"
                type="text" 
                placeholder="Type your message..."
                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                :disabled="!isConnected"
              >
            </div>
            <button 
              type="submit"
              :disabled="!newMessage.trim() || !isConnected"
              :class="newMessage.trim() && isConnected ? 'bg-blue-600 hover:bg-blue-700' : 'bg-gray-300'"
              class="px-6 py-2 text-white rounded-lg transition-colors"
            >
              <i class="fa-solid fa-paper-plane"></i>
            </button>
          </form>
        </div>
      </div>

      <!-- Quick Actions -->
      <div class="mt-8 grid grid-cols-1 md:grid-cols-3 gap-4">
        <button 
          @click="sendQuickMessage('Hi, I need help with my order')"
          class="p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:border-blue-300 hover:shadow-md transition-all text-left"
        >
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-shopping-bag text-blue-600"></i>
            </div>
            <div>
              <h4 class="font-medium text-gray-900">Order Help</h4>
              <p class="text-sm text-gray-500">Get help with your orders</p>
            </div>
          </div>
        </button>

        <button 
          @click="sendQuickMessage('I have a question about a product')"
          class="p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:border-blue-300 hover:shadow-md transition-all text-left"
        >
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-box text-green-600"></i>
            </div>
            <div>
              <h4 class="font-medium text-gray-900">Product Info</h4>
              <p class="text-sm text-gray-500">Ask about products</p>
            </div>
          </div>
        </button>

        <button 
          @click="sendQuickMessage('I need technical support')"
          class="p-4 bg-white rounded-lg shadow-sm border border-gray-200 hover:border-blue-300 hover:shadow-md transition-all text-left"
        >
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-wrench text-purple-600"></i>
            </div>
            <div>
              <h4 class="font-medium text-gray-900">Technical Support</h4>
              <p class="text-sm text-gray-500">Get technical help</p>
            </div>
          </div>
        </button>
      </div>

      <!-- Support Info -->
      <div class="mt-8 bg-blue-50 rounded-lg p-6">
        <div class="flex items-start space-x-4">
          <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
            <i class="fa-solid fa-info-circle text-blue-600 text-xl"></i>
          </div>
          <div>
            <h3 class="font-semibold text-gray-900 mb-2">Need More Help?</h3>
            <p class="text-gray-600 mb-4">Our support team is available 24/7 to assist you with any questions or concerns.</p>
            <div class="flex items-center space-x-6 text-sm text-gray-600">
              <div class="flex items-center space-x-2">
                <i class="fa-solid fa-clock text-blue-600"></i>
                <span>24/7 Support</span>
              </div>
              <div class="flex items-center space-x-2">
                <i class="fa-solid fa-bolt text-blue-600"></i>
                <span>Instant Response</span>
              </div>
              <div class="flex items-center space-x-2">
                <i class="fa-solid fa-shield-alt text-blue-600"></i>
                <span>Secure Chat</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted, nextTick } from 'vue'
import { useAuthStore } from '../stores/auth'
import { apiService, CreateTicketRequest } from '../services/api'

// Types
interface Message {
  id: number
  text: string
  isUser: boolean
  timestamp: string
}

// State
const authStore = useAuthStore()
const websocket = ref<WebSocket | null>(null)
const isConnected = ref(false)
const messages = ref<Message[]>([])
const newMessage = ref('')
const agentTyping = ref(false)
const typingTimeout = ref<NodeJS.Timeout | null>(null)
const messagesContainer = ref<HTMLElement | null>(null)
const currentTicketId = ref<number | null>(null)
const isInitialized = ref(false)

// Computed
const currentUserId = computed(() => authStore.user?.id || 2) // Customer user ID

// WebSocket Methods
const initWebSocket = () => {
  if (!currentUserId.value) {
    console.error('Cannot initialize WebSocket: user not authenticated')
    return
  }

  try {
    websocket.value = new WebSocket(`ws://localhost:8081/ws/customer-service/${currentUserId.value}`)
    
    websocket.value.onopen = () => {
      console.log('Customer WebSocket connected')
      isConnected.value = true
    }
    
    websocket.value.onmessage = (event) => {
      const message = JSON.parse(event.data)
      handleWebSocketMessage(message)
    }
    
    websocket.value.onclose = () => {
      console.log('Customer WebSocket disconnected')
      isConnected.value = false
      
      // Attempt to reconnect after 3 seconds
      setTimeout(() => {
        if (!isConnected.value) {
          initWebSocket()
        }
      }, 3000)
    }
    
    websocket.value.onerror = (error) => {
      console.error('Customer WebSocket error:', error)
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
          isUser: message.data.senderType === 'CUSTOMER',
          timestamp: message.data.timestamp || new Date().toISOString()
        }
        
        messages.value.push(newMsg)
        scrollToBottom()
      }
      break
      
    case 'TYPING_START':
      if (message.userId !== currentUserId.value) {
        agentTyping.value = true
      }
      break
      
    case 'TYPING_STOP':
      if (message.userId !== currentUserId.value) {
        agentTyping.value = false
      }
      break
      
    case 'SUCCESS':
      console.log('Success:', message.message)
      break
      
    case 'ERROR':
      console.error('WebSocket error:', message.message)
      break
      
    case 'NOTIFICATION':
      console.log('Notification:', message.message)
      break
  }
}

// API Methods
const loadChatHistory = async (ticketId: number) => {
  try {
    const chatMessages = await apiService.getTicketMessages(ticketId)
    messages.value = chatMessages.map(msg => ({
      id: msg.id,
      text: msg.message,
      isUser: msg.senderType === 'CUSTOMER',
      timestamp: msg.timestamp
    }))
    scrollToBottom()
  } catch (error) {
    console.error('Error loading chat history:', error)
    throw error
  }
}

const createTicketAndInitialize = async (firstMessage: string) => {
  try {
    // Create a new support ticket
    const ticketData: CreateTicketRequest = {
      subject: 'Customer Support Chat',
      description: firstMessage,
      category: 'GENERAL',
      priority: 'MEDIUM'
    }
    
    const ticket = await apiService.createTicket(ticketData)
    currentTicketId.value = ticket.id
    
    console.log('Created ticket:', ticket.id)
    
    // Join the ticket room via WebSocket
    if (websocket.value && websocket.value.readyState === WebSocket.OPEN) {
      websocket.value.send(JSON.stringify({
        type: 'JOIN_ROOM',
        ticketId: currentTicketId.value
      }))
    }
    
    isInitialized.value = true
    return true
  } catch (error) {
    console.error('Error creating ticket:', error)
    return false
  }
}

// Methods
const sendMessage = async () => {
  const text = newMessage.value.trim()
  if (!text || !websocket.value || !isConnected.value) return

  try {
    // If not initialized, create ticket first
    if (!isInitialized.value || !currentTicketId.value) {
      const success = await createTicketAndInitialize(text)
      if (!success) {
        console.error('Failed to initialize chat')
        return
      }
    }

    // Send via WebSocket with ticket ID
    websocket.value.send(JSON.stringify({
      type: 'SEND_MESSAGE',
      ticketId: currentTicketId.value,
      message: text
    }))
    
    // Don't add to local messages - wait for WebSocket confirmation
    newMessage.value = ''
  } catch (error) {
    console.error('Error sending message:', error)
  }
}

const sendQuickMessage = (text: string) => {
  newMessage.value = text
  sendMessage()
}

const handleTyping = () => {
  if (!websocket.value || !isConnected.value || !currentTicketId.value) return
  
  // Send typing start
  websocket.value.send(JSON.stringify({
    type: 'TYPING_START',
    ticketId: currentTicketId.value
  }))
  
  // Clear existing timeout
  if (typingTimeout.value) {
    clearTimeout(typingTimeout.value)
  }
  
  // Set timeout to send typing stop
  typingTimeout.value = setTimeout(() => {
    if (websocket.value && isConnected.value && currentTicketId.value) {
      websocket.value.send(JSON.stringify({
        type: 'TYPING_STOP',
        ticketId: currentTicketId.value
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
  
  return date.toLocaleTimeString('id-ID', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

onMounted(async () => {
  console.log('Customer Service Chat loaded')
  
  // Initialize WebSocket first
  initWebSocket()
  
  // Wait a bit for WebSocket to connect, then get or create ticket
  setTimeout(async () => {
    if (websocket.value && websocket.value.readyState === WebSocket.OPEN) {
      try {
        // Try to get existing tickets for this customer
        const existingTickets = await apiService.getMyTickets()
        
        if (existingTickets.length > 0) {
          // Use the most recent ticket
          const latestTicket = existingTickets.sort((a, b) => 
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
          )[0]
          
          currentTicketId.value = latestTicket.id
          console.log('Using existing ticket:', currentTicketId.value)
          
          // Load existing chat history
          await loadChatHistory(currentTicketId.value)
          console.log('Loaded existing chat history')
        } else {
          // Create a new ticket for this customer
          const ticketData: CreateTicketRequest = {
            subject: 'Customer Support Chat',
            description: 'Customer support conversation',
            category: 'GENERAL',
            priority: 'MEDIUM'
          }
          
          const newTicket = await apiService.createTicket(ticketData)
          currentTicketId.value = newTicket.id
          console.log('Created new ticket:', currentTicketId.value)
        }
        
        // Join the ticket room
        websocket.value.send(JSON.stringify({
          type: 'JOIN_ROOM',
          ticketId: currentTicketId.value
        }))
        console.log('Customer joined room:', currentTicketId.value)
        
        isInitialized.value = true
        
      } catch (error) {
        console.error('Error initializing chat:', error)
        // Fallback: still set initialized to allow manual ticket creation
        isInitialized.value = true
      }
    }
  }, 1000)
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
