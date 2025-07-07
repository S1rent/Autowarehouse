<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Customer Service</h1>
        <p class="text-gray-600 mt-1">Kelola pesan dan komunikasi dengan pelanggan</p>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-blue-100">
              <i class="fa-solid fa-envelope text-blue-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Total Pesan</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-yellow-100">
              <i class="fa-solid fa-clock text-yellow-600 text-xl"></i>
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
              <i class="fa-solid fa-check-circle text-green-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Selesai</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.resolved }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center">
            <div class="p-3 rounded-full bg-purple-100">
              <i class="fa-solid fa-headset text-purple-600 text-xl"></i>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-600">Rata-rata Respon</p>
              <p class="text-2xl font-bold text-gray-900">{{ stats.avgResponse }}h</p>
            </div>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Message List -->
        <div class="lg:col-span-1 bg-white rounded-lg shadow-sm border border-gray-200">
          <div class="p-6 border-b border-gray-200">
            <div class="flex items-center justify-between mb-4">
              <h3 class="text-lg font-semibold text-gray-900">Pesan Masuk</h3>
              <button @click="refreshMessages" class="text-gray-400 hover:text-gray-600">
                <i class="fa-solid fa-arrows-rotate"></i>
              </button>
            </div>
            <div class="relative">
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Cari pesan..." 
                class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
              >
            </div>
          </div>
          
          <div class="max-h-96 overflow-y-auto">
            <div 
              v-for="message in filteredMessages" 
              :key="message.id"
              @click="selectMessage(message)"
              :class="selectedMessage?.id === message.id ? 'bg-blue-50 border-l-4 border-l-blue-600' : 'hover:bg-gray-50'"
              class="p-4 border-b border-gray-200 cursor-pointer"
            >
              <div class="flex items-start space-x-3">
                <img :src="message.customer.avatar" :alt="message.customer.name" class="w-10 h-10 rounded-full">
                <div class="flex-1 min-w-0">
                  <div class="flex items-center justify-between">
                    <p class="text-sm font-medium text-gray-900 truncate">{{ message.customer.name }}</p>
                    <span v-if="!message.isRead" class="w-2 h-2 bg-blue-600 rounded-full"></span>
                  </div>
                  <p class="text-sm text-gray-600 truncate">{{ message.subject }}</p>
                  <p class="text-xs text-gray-500 mt-1">{{ formatTime(message.timestamp) }}</p>
                  <span :class="getStatusClass(message.status)" class="inline-flex px-2 py-1 text-xs font-semibold rounded-full mt-2">
                    {{ getStatusText(message.status) }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Message Detail -->
        <div class="lg:col-span-2 bg-white rounded-lg shadow-sm border border-gray-200">
          <div v-if="selectedMessage" class="h-full flex flex-col">
            <!-- Message Header -->
            <div class="p-6 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <div class="flex items-center space-x-3">
                  <img :src="selectedMessage.customer.avatar" :alt="selectedMessage.customer.name" class="w-12 h-12 rounded-full">
                  <div>
                    <h3 class="text-lg font-semibold text-gray-900">{{ selectedMessage.customer.name }}</h3>
                    <p class="text-sm text-gray-600">{{ selectedMessage.customer.email }}</p>
                  </div>
                </div>
                <div class="flex items-center space-x-2">
                  <select 
                    v-model="selectedMessage.status"
                    @change="updateStatus"
                    class="text-sm border border-gray-300 rounded-lg px-3 py-1"
                  >
                    <option value="new">Baru</option>
                    <option value="in-progress">Dalam Proses</option>
                    <option value="resolved">Selesai</option>
                  </select>
                  <button @click="deleteMessage" class="text-red-600 hover:text-red-700">
                    <i class="fa-solid fa-trash"></i>
                  </button>
                </div>
              </div>
              <div class="mt-4">
                <h4 class="font-medium text-gray-900">{{ selectedMessage.subject }}</h4>
                <p class="text-sm text-gray-500">{{ formatDateTime(selectedMessage.timestamp) }}</p>
              </div>
            </div>

            <!-- Message Content -->
            <div class="flex-1 p-6 overflow-y-auto">
              <div class="space-y-4">
                <div class="bg-gray-50 rounded-lg p-4">
                  <p class="text-gray-800">{{ selectedMessage.content }}</p>
                </div>
                
                <!-- Replies -->
                <div v-for="reply in selectedMessage.replies" :key="reply.id" class="space-y-4">
                  <div :class="reply.isAdmin ? 'ml-8' : 'mr-8'">
                    <div :class="reply.isAdmin ? 'bg-blue-50' : 'bg-gray-50'" class="rounded-lg p-4">
                      <div class="flex items-center space-x-2 mb-2">
                        <span class="text-sm font-medium text-gray-900">{{ reply.sender }}</span>
                        <span class="text-xs text-gray-500">{{ formatDateTime(reply.timestamp) }}</span>
                      </div>
                      <p class="text-gray-800">{{ reply.content }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Reply Form -->
            <div class="p-6 border-t border-gray-200">
              <form @submit.prevent="sendReply" class="space-y-4">
                <textarea 
                  v-model="replyContent"
                  rows="3" 
                  placeholder="Tulis balasan..." 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                  required
                ></textarea>
                <div class="flex justify-end space-x-3">
                  <button 
                    type="button"
                    @click="replyContent = ''"
                    class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
                  >
                    Batal
                  </button>
                  <button 
                    type="submit"
                    class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                  >
                    Kirim Balasan
                  </button>
                </div>
              </form>
            </div>
          </div>

          <!-- Empty State -->
          <div v-else class="h-96 flex items-center justify-center">
            <div class="text-center">
              <i class="fa-solid fa-envelope-open text-4xl text-gray-400 mb-4"></i>
              <p class="text-gray-500">Pilih pesan untuk melihat detail</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface Customer {
  id: string
  name: string
  email: string
  avatar: string
}

interface Reply {
  id: string
  sender: string
  content: string
  timestamp: string
  isAdmin: boolean
}

interface Message {
  id: string
  customer: Customer
  subject: string
  content: string
  timestamp: string
  status: 'new' | 'in-progress' | 'resolved'
  isRead: boolean
  replies: Reply[]
}

const searchQuery = ref('')
const selectedMessage = ref<Message | null>(null)
const replyContent = ref('')

const stats = ref({
  total: 156,
  unread: 23,
  resolved: 98,
  avgResponse: 2.5
})

const messages = ref<Message[]>([
  {
    id: '1',
    customer: {
      id: '1',
      name: 'John Doe',
      email: 'john@example.com',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-3.jpg'
    },
    subject: 'Masalah dengan pesanan #12345',
    content: 'Halo, saya mengalami masalah dengan pesanan saya. Produk yang diterima tidak sesuai dengan yang saya pesan. Mohon bantuannya.',
    timestamp: '2024-12-15T10:30:00',
    status: 'new',
    isRead: false,
    replies: []
  },
  {
    id: '2',
    customer: {
      id: '2',
      name: 'Jane Smith',
      email: 'jane@example.com',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-5.jpg'
    },
    subject: 'Pertanyaan tentang garansi',
    content: 'Selamat siang, saya ingin menanyakan tentang garansi produk yang saya beli minggu lalu. Apakah masih berlaku jika ada kerusakan?',
    timestamp: '2024-12-15T09:15:00',
    status: 'in-progress',
    isRead: true,
    replies: [
      {
        id: '1',
        sender: 'Admin',
        content: 'Selamat siang, terima kasih atas pertanyaannya. Garansi produk berlaku selama 1 tahun dari tanggal pembelian.',
        timestamp: '2024-12-15T09:30:00',
        isAdmin: true
      }
    ]
  },
  {
    id: '3',
    customer: {
      id: '3',
      name: 'Bob Johnson',
      email: 'bob@example.com',
      avatar: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/avatars/avatar-4.jpg'
    },
    subject: 'Request refund',
    content: 'Saya ingin mengajukan refund untuk pesanan #12340 karena produk tidak sesuai ekspektasi.',
    timestamp: '2024-12-14T16:45:00',
    status: 'resolved',
    isRead: true,
    replies: [
      {
        id: '1',
        sender: 'Admin',
        content: 'Baik, kami akan proses refund Anda. Mohon tunggu 3-5 hari kerja.',
        timestamp: '2024-12-14T17:00:00',
        isAdmin: true
      },
      {
        id: '2',
        sender: 'Bob Johnson',
        content: 'Terima kasih atas bantuannya.',
        timestamp: '2024-12-14T17:15:00',
        isAdmin: false
      }
    ]
  }
])

const filteredMessages = computed(() => {
  if (!searchQuery.value) return messages.value
  
  return messages.value.filter(message =>
    message.customer.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    message.subject.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
    message.content.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
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

const formatDateTime = (timestamp: string) => {
  return new Date(timestamp).toLocaleString('id-ID', {
    day: 'numeric',
    month: 'short',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const getStatusClass = (status: string) => {
  const classes = {
    'new': 'bg-blue-100 text-blue-800',
    'in-progress': 'bg-yellow-100 text-yellow-800',
    'resolved': 'bg-green-100 text-green-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    'new': 'Baru',
    'in-progress': 'Dalam Proses',
    'resolved': 'Selesai'
  }
  return texts[status as keyof typeof texts] || status
}

const selectMessage = (message: Message) => {
  selectedMessage.value = message
  if (!message.isRead) {
    message.isRead = true
    stats.value.unread--
  }
}

const updateStatus = () => {
  if (selectedMessage.value) {
    alert(`Status diubah menjadi: ${getStatusText(selectedMessage.value.status)}`)
  }
}

const sendReply = () => {
  if (selectedMessage.value && replyContent.value.trim()) {
    const newReply: Reply = {
      id: Date.now().toString(),
      sender: 'Admin',
      content: replyContent.value.trim(),
      timestamp: new Date().toISOString(),
      isAdmin: true
    }
    
    selectedMessage.value.replies.push(newReply)
    selectedMessage.value.status = 'in-progress'
    replyContent.value = ''
    
    alert('Balasan berhasil dikirim!')
  }
}

const deleteMessage = () => {
  if (selectedMessage.value && confirm('Apakah Anda yakin ingin menghapus pesan ini?')) {
    const index = messages.value.findIndex(m => m.id === selectedMessage.value!.id)
    if (index > -1) {
      messages.value.splice(index, 1)
      selectedMessage.value = null
      stats.value.total--
      alert('Pesan berhasil dihapus!')
    }
  }
}

const refreshMessages = () => {
  alert('Pesan diperbarui!')
}

onMounted(() => {
  console.log('Admin Customer Service loaded')
})
</script>
