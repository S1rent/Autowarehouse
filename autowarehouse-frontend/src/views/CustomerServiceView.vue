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
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()
const chatMessages = ref(null)
const newMessage = ref('')
const isTyping = ref(false)

const messages = ref([
  {
    id: 1,
    text: 'Halo! Selamat datang di Customer Service Autowarehouse. Ada yang bisa saya bantu hari ini?',
    isUser: false,
    timestamp: new Date(Date.now() - 300000) // 5 minutes ago
  },
  {
    id: 2,
    text: 'Halo, saya mengalami masalah dengan pembayaran auction yang sudah saya menangkan',
    isUser: true,
    timestamp: new Date(Date.now() - 240000) // 4 minutes ago
  },
  {
    id: 3,
    text: 'Baik, saya akan membantu Anda mengatasi masalah pembayaran tersebut. Bisakah Anda memberitahu saya detail masalah yang Anda alami?',
    isUser: false,
    timestamp: new Date(Date.now() - 180000) // 3 minutes ago
  },
  {
    id: 4,
    text: 'Saya sudah melakukan pembayaran tapi status auction masih belum berubah ke "completed"',
    isUser: true,
    timestamp: new Date(Date.now() - 120000) // 2 minutes ago
  },
  {
    id: 5,
    text: 'Saya mengerti masalahnya. Biasanya update status membutuhkan waktu 5-10 menit. Boleh saya minta nomor auction atau email yang digunakan untuk pembayaran?',
    isUser: false,
    timestamp: new Date(Date.now() - 60000) // 1 minute ago
  }
])

const autoResponses = [
  "Terima kasih atas informasinya. Saya akan segera mengecek status pembayaran Anda.",
  "Baik, saya sudah mengecek dan akan memproses update status auction Anda sekarang.",
  "Status auction Anda sudah berhasil diupdate. Silakan refresh halaman untuk melihat perubahannya.",
  "Apakah ada hal lain yang bisa saya bantu?",
  "Saya akan mengirimkan konfirmasi ke email Anda dalam beberapa menit.",
  "Untuk masalah teknis seperti ini, biasanya tim kami membutuhkan waktu 1-2 jam untuk menyelesaikannya."
]

const formatTime = (timestamp) => {
  return timestamp.toLocaleTimeString('id-ID', { 
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

const sendMessage = async () => {
  const text = newMessage.value.trim()
  if (!text) return

  // Add user message
  const userMessage = {
    id: Date.now(),
    text: text,
    isUser: true,
    timestamp: new Date()
  }
  messages.value.push(userMessage)
  newMessage.value = ''
  
  await scrollToBottom()

  // Show typing indicator
  isTyping.value = true
  
  // Simulate CS response after delay
  setTimeout(async () => {
    isTyping.value = false
    
    const randomResponse = autoResponses[Math.floor(Math.random() * autoResponses.length)]
    const csMessage = {
      id: Date.now() + 1,
      text: randomResponse,
      isUser: false,
      timestamp: new Date()
    }
    messages.value.push(csMessage)
    
    await scrollToBottom()
  }, 1000 + Math.random() * 2000)
}

const goBack = () => {
  router.go(-1)
}

onMounted(() => {
  scrollToBottom()
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
