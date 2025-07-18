<template>
  <div class="min-h-screen bg-gray-50">
    <UserNavbar />

    <main class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="mb-8">
        <div class="flex items-center space-x-3 mb-2">
          <button @click="goBack" class="text-gray-500 hover:text-gray-700">
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <h1 class="text-2xl font-bold text-gray-900">Customer Service</h1>
        </div>
        <p class="text-gray-600">Hubungi tim customer service untuk bantuan dan dukungan</p>
      </div>

      <div class="bg-white rounded-lg shadow-sm border border-gray-200 h-[600px] flex flex-col">
        <div class="p-4 border-b border-gray-200 flex items-center justify-between">
          <div class="flex items-center space-x-3">
            <div class="relative">
              <div class="w-10 h-10 bg-blue-600 rounded-full flex items-center justify-center">
                <i class="fas fa-headset text-white text-sm"></i>
              </div>
              <span class="absolute bottom-0 right-0 w-3 h-3 bg-green-500 border-2 border-white rounded-full"></span>
            </div>
            <div>
              <h3 class="text-sm font-semibold text-gray-900">Customer Service</h3>
              <p class="text-xs text-green-600">Online - Biasanya membalas dalam beberapa menit</p>
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <button class="p-2 text-gray-500 hover:text-gray-700 hover:bg-gray-100 rounded-full">
              <i class="fa-solid fa-phone text-sm"></i>
            </button>
            <button class="p-2 text-gray-500 hover:text-gray-700 hover:bg-gray-100 rounded-full">
              <i class="fa-solid fa-ellipsis-vertical text-sm"></i>
            </button>
          </div>
        </div>

        <div ref="chatMessages" class="flex-1 p-4 overflow-y-auto space-y-4">
          <div 
            v-for="message in messages" 
            :key="message.id"
            :class="['flex items-start space-x-3', message.isUser ? 'justify-end' : '']"
          >
            <div v-if="!message.isUser" class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center flex-shrink-0">
              <i class="fas fa-headset text-white text-xs"></i>
            </div>
            <div :class="[
              'rounded-lg p-3 max-w-xs',
              message.isUser ? 'bg-blue-600' : 'bg-gray-100'
            ]">
              <p :class="['text-sm', message.isUser ? 'text-white' : 'text-gray-800']">
                {{ message.text }}
              </p>
              <span :class="['text-xs mt-1 block', message.isUser ? 'text-blue-200' : 'text-gray-500']">
                {{ formatTime(message.timestamp) }}
              </span>
            </div>
            <div v-if="message.isUser" class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center flex-shrink-0">
              <i class="fas fa-user text-white text-xs"></i>
            </div>
          </div>

          <div v-if="isTyping" class="flex items-start space-x-3">
            <div class="w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center flex-shrink-0">
              <i class="fas fa-headset text-white text-xs"></i>
            </div>
            <div class="bg-gray-100 rounded-lg p-3">
              <div class="flex space-x-1">
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce"></div>
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0.1s"></div>
                <div class="w-2 h-2 bg-gray-400 rounded-full animate-bounce" style="animation-delay: 0.2s"></div>
              </div>
            </div>
          </div>
        </div>

        <div class="p-4 border-t border-gray-200">
          <div class="flex items-center space-x-3">
            <button class="p-2 text-gray-500 hover:text-gray-700 hover:bg-gray-100 rounded-full">
              <i class="fa-solid fa-paperclip"></i>
            </button>
            <div class="flex-1 relative">
              <input 
                v-model="newMessage"
                @keypress.enter="sendMessage"
                @input="handleTyping"
                type="text" 
                placeholder="Ketik pesan Anda..." 
                class="w-full px-4 py-2 border border-gray-300 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent"
              >
              <button class="absolute right-2 top-1/2 transform -translate-y-1/2 p-1 text-gray-500 hover:text-gray-700">
                <i class="fa-solid fa-face-smile"></i>
              </button>
            </div>
            <button 
              @click="sendMessage"
              class="p-2 bg-blue-600 text-white rounded-full hover:bg-blue-700 transition-colors"
            >
              <i class="fa-solid fa-paper-plane"></i>
            </button>
          </div>
        </div>
      </div>

      <div class="mt-6 grid grid-cols-1 md:grid-cols-3 gap-4">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-4">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-100 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-question-circle text-blue-600"></i>
            </div>
            <div>
              <h4 class="text-sm font-semibold text-gray-900">FAQ</h4>
              <p class="text-xs text-gray-600">Pertanyaan yang sering ditanyakan</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-4">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-green-100 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-headset text-green-600"></i>
            </div>
            <div>
              <h4 class="text-sm font-semibold text-gray-900">Hubungi Kami</h4>
              <p class="text-xs text-gray-600">+62 21 1234 5678</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-4">
          <div class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-yellow-100 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-clock text-yellow-600"></i>
            </div>
            <div>
              <h4 class="text-sm font-semibold text-gray-900">Jam Operasional</h4>
              <p class="text-xs text-gray-600">24/7 Online</p>
            </div>
          </div>
        </div>
      </div>
    </main>
    <Footer/>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()
const chatMessages = ref(null)
const newMessage = ref('')
const isTyping = ref(false)
const typingUser = ref('')
const currentTicket = ref(null)
const websocket = ref(null)
const isConnected = ref(false)

const messages = ref([])

// Mock current user ID - in real app, get from auth store
const currentUserId = 1

const formatTime = (timestamp) => {
  const date = new Date(timestamp)
  return date.toLocaleTimeString('id-ID', { 
    hour: '2-digit', 
    minute: '2-digit' 
  })
}

const scrollToBottom = async () => {
  await nextTick()
  if (chatMessages.value) {
    chatMessages.value.scrollTop = chatMessages.value.scrollHeight
  }
}

const initWebSocket = () => {
  try {
    websocket.value = new WebSocket(`ws://localhost:8080/ws/customer-service/${currentUserId}`)
    
    websocket.value.onopen = () => {
      console.log('WebSocket connected')
      isConnected.value = true
      
      // Create or get existing ticket for this customer
      createOrGetTicket()
    }
    
    websocket.value.onmessage = (event) => {
      const message = JSON.parse(event.data)
      handleWebSocketMessage(message)
    }
    
    websocket.value.onclose = () => {
      console.log('WebSocket disconnected')
      isConnected.value = false
      
      // Attempt to reconnect after 3 seconds
      setTimeout(() => {
        if (!isConnected.value) {
          initWebSocket()
        }
      }, 3000)
    }
    
    websocket.value.onerror = (error) => {
      console.error('WebSocket error:', error)
      isConnected.value = false
    }
  } catch (error) {
    console.error('Failed to initialize WebSocket:', error)
  }
}

const handleWebSocketMessage = (message) => {
  switch (message.type) {
    case 'RECEIVE_MESSAGE':
      if (message.data && currentTicket.value && message.data.ticketId === currentTicket.value.id) {
        const newMsg = {
          id: message.data.id,
          text: message.data.message,
          isUser: message.data.senderType === 'CUSTOMER',
          timestamp: message.data.timestamp,
          senderName: message.data.senderName
        }
        messages.value.push(newMsg)
        scrollToBottom()
      }
      break
      
    case 'TYPING_START':
      if (currentTicket.value && message.ticketId === currentTicket.value.id && message.userId !== currentUserId) {
        isTyping.value = true
        typingUser.value = message.userName
      }
      break
      
    case 'TYPING_STOP':
      if (currentTicket.value && message.ticketId === currentTicket.value.id) {
        isTyping.value = false
        typingUser.value = ''
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

const createOrGetTicket = async () => {
  try {
    // First, try to get existing open ticket for this customer
    const response = await fetch('/api/tickets/my-tickets', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || 'mock-token'}`
      }
    })
    
    if (response.ok) {
      const tickets = await response.json()
      const openTicket = tickets.find(t => t.status === 'OPEN' || t.status === 'IN_PROGRESS')
      
      if (openTicket) {
        currentTicket.value = openTicket
        await loadMessages()
        joinTicketRoom()
      } else {
        // Create new ticket
        await createNewTicket()
      }
    } else {
      // Create new ticket if can't get existing ones
      await createNewTicket()
    }
  } catch (error) {
    console.error('Error getting/creating ticket:', error)
    // Create new ticket as fallback
    await createNewTicket()
  }
}

const createNewTicket = async () => {
  try {
    const response = await fetch('/api/tickets', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token') || 'mock-token'}`
      },
      body: JSON.stringify({
        subject: 'Customer Service Chat',
        description: 'Customer service chat session',
        category: 'GENERAL',
        priority: 'MEDIUM'
      })
    })
    
    if (response.ok) {
      currentTicket.value = await response.json()
      joinTicketRoom()
      
      // Send welcome message
      setTimeout(() => {
        const welcomeMessage = {
          id: Date.now(),
          text: 'Halo! Selamat datang di Customer Service Autowarehouse. Ada yang bisa saya bantu hari ini?',
          isUser: false,
          timestamp: new Date(),
          senderName: 'Customer Service'
        }
        messages.value.push(welcomeMessage)
        scrollToBottom()
      }, 1000)
    }
  } catch (error) {
    console.error('Error creating ticket:', error)
  }
}

const loadMessages = async () => {
  if (!currentTicket.value) return
  
  try {
    const response = await fetch(`/api/chat/tickets/${currentTicket.value.id}/messages`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token') || 'mock-token'}`
      }
    })
    
    if (response.ok) {
      const chatMessages = await response.json()
      messages.value = chatMessages.map(msg => ({
        id: msg.id,
        text: msg.message,
        isUser: msg.senderType === 'CUSTOMER',
        timestamp: msg.timestamp,
        senderName: msg.senderName
      }))
      scrollToBottom()
    }
  } catch (error) {
    console.error('Error loading messages:', error)
  }
}

const joinTicketRoom = () => {
  if (websocket.value && websocket.value.readyState === WebSocket.OPEN && currentTicket.value) {
    websocket.value.send(JSON.stringify({
      type: 'JOIN_ROOM',
      ticketId: currentTicket.value.id
    }))
  }
}

const sendMessage = async () => {
  const text = newMessage.value.trim()
  if (!text || !currentTicket.value || !websocket.value) return

  try {
    // Send via WebSocket
    websocket.value.send(JSON.stringify({
      type: 'SEND_MESSAGE',
      ticketId: currentTicket.value.id,
      message: text
    }))
    
    newMessage.value = ''
  } catch (error) {
    console.error('Error sending message:', error)
  }
}

const handleTyping = () => {
  if (!currentTicket.value || !websocket.value) return

  // Send typing start
  websocket.value.send(JSON.stringify({
    type: 'TYPING_START',
    ticketId: currentTicket.value.id
  }))

  // Clear existing timeout
  if (window.typingTimeout) {
    clearTimeout(window.typingTimeout)
  }

  // Set timeout to send typing stop
  window.typingTimeout = setTimeout(() => {
    if (websocket.value && websocket.value.readyState === WebSocket.OPEN) {
      websocket.value.send(JSON.stringify({
        type: 'TYPING_STOP',
        ticketId: currentTicket.value.id
      }))
    }
  }, 1000)
}

const goBack = () => {
  router.go(-1)
}

onMounted(() => {
  initWebSocket()
})

onUnmounted(() => {
  if (websocket.value) {
    websocket.value.close()
  }
  if (window.typingTimeout) {
    clearTimeout(window.typingTimeout)
  }
})
</script>

<style scoped>
@keyframes bounce {
  0%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-6px);
  }
}

.animate-bounce {
  animation: bounce 1s infinite;
}
</style>
