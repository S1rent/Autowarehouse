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
                <h1 class="text-2xl font-bold text-gray-900">Customer Service</h1>
                <p class="text-gray-600">Manage customer inquiries and support tickets</p>
              </div>
              <div class="flex items-center space-x-4">
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
                  <p class="text-sm text-gray-600">Open Tickets</p>
                  <p class="text-2xl font-bold text-red-600">{{ stats.openTickets }}</p>
                </div>
                <div class="w-12 h-12 bg-red-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-ticket text-red-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">In Progress</p>
                  <p class="text-2xl font-bold text-yellow-600">{{ stats.inProgress }}</p>
                </div>
                <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-clock text-yellow-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Resolved Today</p>
                  <p class="text-2xl font-bold text-green-600">{{ stats.resolvedToday }}</p>
                </div>
                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-check-circle text-green-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Avg Response Time</p>
                  <p class="text-2xl font-bold text-blue-600">{{ stats.avgResponseTime }}</p>
                </div>
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-stopwatch text-blue-600"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Main Content Grid -->
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <!-- Tickets List -->
            <div class="lg:col-span-2">
              <div class="bg-white rounded-xl shadow-sm">
                <div class="px-6 py-4 border-b border-gray-200">
                  <div class="flex items-center justify-between">
                    <h3 class="text-lg font-semibold text-gray-900">Support Tickets</h3>
                    <div class="flex items-center space-x-2">
                      <select 
                        v-model="statusFilter"
                        class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">All Status</option>
                        <option value="open">Open</option>
                        <option value="in-progress">In Progress</option>
                        <option value="resolved">Resolved</option>
                        <option value="closed">Closed</option>
                      </select>
                      <select 
                        v-model="priorityFilter"
                        class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                      >
                        <option value="">All Priority</option>
                        <option value="high">High</option>
                        <option value="medium">Medium</option>
                        <option value="low">Low</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="divide-y divide-gray-200">
                  <div 
                    v-for="ticket in filteredTickets" 
                    :key="ticket.id"
                    @click="selectTicket(ticket)"
                    :class="selectedTicket?.id === ticket.id ? 'bg-blue-50' : 'hover:bg-gray-50'"
                    class="p-6 cursor-pointer transition-colors"
                  >
                    <div class="flex items-start justify-between">
                      <div class="flex-1">
                        <div class="flex items-center space-x-3 mb-2">
                          <h4 class="font-medium text-gray-900">{{ ticket.subject }}</h4>
                          <span 
                            :class="getPriorityClass(ticket.priority)"
                            class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                          >
                            {{ ticket.priority }}
                          </span>
                          <span 
                            :class="getStatusClass(ticket.status)"
                            class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                          >
                            {{ ticket.status }}
                          </span>
                        </div>
                        <p class="text-sm text-gray-600 mb-2">{{ ticket.message }}</p>
                        <div class="flex items-center space-x-4 text-sm text-gray-500">
                          <span>{{ ticket.customer.name }}</span>
                          <span>{{ ticket.customer.email }}</span>
                          <span>{{ formatDateTime(ticket.createdAt) }}</span>
                        </div>
                      </div>
                      <div class="flex items-center space-x-2 ml-4">
                        <img 
                          :src="ticket.assignedTo?.avatar || 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=32&h=32&fit=crop&crop=face'" 
                          :alt="ticket.assignedTo?.name || 'Unassigned'"
                          class="w-8 h-8 rounded-full"
                        >
                        <i class="fa-solid fa-chevron-right text-gray-400"></i>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Ticket Detail -->
            <div class="lg:col-span-1">
              <div v-if="selectedTicket" class="bg-white rounded-xl shadow-sm p-6">
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-lg font-semibold text-gray-900">Ticket Details</h3>
                  <button 
                    @click="selectedTicket = null"
                    class="text-gray-400 hover:text-gray-600"
                  >
                    <i class="fa-solid fa-times"></i>
                  </button>
                </div>

                <!-- Customer Info -->
                <div class="mb-6">
                  <h4 class="font-medium text-gray-900 mb-3">Customer Information</h4>
                  <div class="space-y-2">
                    <div class="flex items-center space-x-3">
                      <img 
                        :src="selectedTicket.customer.avatar" 
                        :alt="selectedTicket.customer.name"
                        class="w-10 h-10 rounded-full"
                      >
                      <div>
                        <p class="font-medium text-gray-900">{{ selectedTicket.customer.name }}</p>
                        <p class="text-sm text-gray-500">{{ selectedTicket.customer.email }}</p>
                      </div>
                    </div>
                    <div class="text-sm text-gray-600">
                      <p>Phone: {{ selectedTicket.customer.phone }}</p>
                      <p>Member since: {{ formatDate(selectedTicket.customer.memberSince) }}</p>
                    </div>
                  </div>
                </div>

                <!-- Ticket Info -->
                <div class="mb-6">
                  <h4 class="font-medium text-gray-900 mb-3">Ticket Information</h4>
                  <div class="space-y-2 text-sm">
                    <div class="flex justify-between">
                      <span class="text-gray-500">Ticket ID:</span>
                      <span class="font-medium">#{{ selectedTicket.id }}</span>
                    </div>
                    <div class="flex justify-between">
                      <span class="text-gray-500">Category:</span>
                      <span class="font-medium">{{ selectedTicket.category }}</span>
                    </div>
                    <div class="flex justify-between">
                      <span class="text-gray-500">Priority:</span>
                      <span 
                        :class="getPriorityClass(selectedTicket.priority)"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ selectedTicket.priority }}
                      </span>
                    </div>
                    <div class="flex justify-between">
                      <span class="text-gray-500">Status:</span>
                      <span 
                        :class="getStatusClass(selectedTicket.status)"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ selectedTicket.status }}
                      </span>
                    </div>
                    <div class="flex justify-between">
                      <span class="text-gray-500">Created:</span>
                      <span class="font-medium">{{ formatDateTime(selectedTicket.createdAt) }}</span>
                    </div>
                  </div>
                </div>

                <!-- Actions -->
                <div class="space-y-3">
                  <select 
                    v-model="selectedTicket.status"
                    @change="updateTicketStatus"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="open">Open</option>
                    <option value="in-progress">In Progress</option>
                    <option value="resolved">Resolved</option>
                    <option value="closed">Closed</option>
                  </select>
                  
                  <select 
                    v-model="selectedTicket.assignedTo"
                    @change="assignTicket"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option :value="null">Unassigned</option>
                    <option 
                      v-for="agent in agents" 
                      :key="agent.id"
                      :value="agent"
                    >
                      {{ agent.name }}
                    </option>
                  </select>

                  <button 
                    @click="showReplyModal = true"
                    class="w-full bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                  >
                    <i class="fa-solid fa-reply mr-2"></i>
                    Reply to Customer
                  </button>
                </div>
              </div>

              <!-- Quick Stats -->
              <div v-else class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Quick Stats</h3>
                <div class="space-y-4">
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Today's Tickets</span>
                    <span class="font-semibold text-gray-900">{{ stats.todayTickets }}</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">This Week</span>
                    <span class="font-semibold text-gray-900">{{ stats.weekTickets }}</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Resolution Rate</span>
                    <span class="font-semibold text-green-600">{{ stats.resolutionRate }}%</span>
                  </div>
                  <div class="flex items-center justify-between">
                    <span class="text-gray-600">Customer Satisfaction</span>
                    <span class="font-semibold text-blue-600">{{ stats.satisfaction }}/5</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <!-- Reply Modal -->
    <div v-if="showReplyModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-2xl mx-4">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-900">Reply to Customer</h3>
          <button @click="showReplyModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="sendReply" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Message</label>
            <textarea 
              v-model="replyMessage"
              rows="6" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              placeholder="Type your reply here..."
              required
            ></textarea>
          </div>
          <div class="flex items-center">
            <input 
              v-model="markAsResolved"
              type="checkbox" 
              class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
            >
            <label class="ml-2 text-sm text-gray-700">Mark ticket as resolved</label>
          </div>
          <div class="flex justify-end space-x-4">
            <button 
              type="button"
              @click="showReplyModal = false"
              class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Send Reply
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
const selectedTicket = ref(null)
const statusFilter = ref('')
const priorityFilter = ref('')
const showReplyModal = ref(false)
const replyMessage = ref('')
const markAsResolved = ref(false)

// Stats
const stats = ref({
  openTickets: 23,
  inProgress: 8,
  resolvedToday: 15,
  avgResponseTime: '2.5h',
  todayTickets: 12,
  weekTickets: 67,
  resolutionRate: 94,
  satisfaction: 4.8
})

// Agents
const agents = ref([
  { id: 1, name: 'Sarah Johnson', avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face' },
  { id: 2, name: 'Mike Chen', avatar: 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=32&h=32&fit=crop&crop=face' },
  { id: 3, name: 'Emily Davis', avatar: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=32&h=32&fit=crop&crop=face' }
])

// Sample tickets data
const tickets = ref([
  {
    id: 1001,
    subject: 'Payment Issue with Order #12345',
    message: 'I was charged twice for my recent order. Can you please help me resolve this?',
    status: 'open',
    priority: 'high',
    category: 'Payment',
    customer: {
      name: 'John Doe',
      email: 'john.doe@example.com',
      phone: '+62 812-3456-7890',
      avatar: 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=32&h=32&fit=crop&crop=face',
      memberSince: new Date('2023-01-15')
    },
    assignedTo: agents.value[0],
    createdAt: new Date(Date.now() - 2 * 60 * 60 * 1000)
  },
  {
    id: 1002,
    subject: 'Product Not Received',
    message: 'I ordered a laptop 2 weeks ago but haven\'t received it yet. The tracking shows it was delivered but I never got it.',
    status: 'in-progress',
    priority: 'high',
    category: 'Shipping',
    customer: {
      name: 'Jane Smith',
      email: 'jane.smith@example.com',
      phone: '+62 813-4567-8901',
      avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=32&h=32&fit=crop&crop=face',
      memberSince: new Date('2022-08-20')
    },
    assignedTo: agents.value[1],
    createdAt: new Date(Date.now() - 4 * 60 * 60 * 1000)
  },
  {
    id: 1003,
    subject: 'Account Login Problems',
    message: 'I can\'t log into my account. I keep getting an error message saying my credentials are invalid.',
    status: 'open',
    priority: 'medium',
    category: 'Account',
    customer: {
      name: 'Bob Wilson',
      email: 'bob.wilson@example.com',
      phone: '+62 814-5678-9012',
      avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=32&h=32&fit=crop&crop=face',
      memberSince: new Date('2023-03-10')
    },
    assignedTo: null,
    createdAt: new Date(Date.now() - 6 * 60 * 60 * 1000)
  },
  {
    id: 1004,
    subject: 'Refund Request',
    message: 'I would like to return my recent purchase and get a refund. The item doesn\'t match the description.',
    status: 'resolved',
    priority: 'medium',
    category: 'Returns',
    customer: {
      name: 'Alice Brown',
      email: 'alice.brown@example.com',
      phone: '+62 815-6789-0123',
      avatar: 'https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=32&h=32&fit=crop&crop=face',
      memberSince: new Date('2022-11-05')
    },
    assignedTo: agents.value[2],
    createdAt: new Date(Date.now() - 24 * 60 * 60 * 1000)
  }
])

// Computed
const filteredTickets = computed(() => {
  let filtered = tickets.value

  if (statusFilter.value) {
    filtered = filtered.filter(ticket => ticket.status === statusFilter.value)
  }

  if (priorityFilter.value) {
    filtered = filtered.filter(ticket => ticket.priority === priorityFilter.value)
  }

  return filtered.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
})

// Methods
const getPriorityClass = (priority: string) => {
  const classes = {
    'high': 'bg-red-100 text-red-800',
    'medium': 'bg-yellow-100 text-yellow-800',
    'low': 'bg-green-100 text-green-800'
  }
  return classes[priority as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusClass = (status: string) => {
  const classes = {
    'open': 'bg-red-100 text-red-800',
    'in-progress': 'bg-yellow-100 text-yellow-800',
    'resolved': 'bg-green-100 text-green-800',
    'closed': 'bg-gray-100 text-gray-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const formatDateTime = (date: Date) => {
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'short',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatDate = (date: Date) => {
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'long',
    year: 'numeric'
  })
}

const selectTicket = (ticket: any) => {
  selectedTicket.value = ticket
}

const updateTicketStatus = () => {
  console.log('Updating ticket status:', selectedTicket.value?.status)
}

const assignTicket = () => {
  console.log('Assigning ticket to:', selectedTicket.value?.assignedTo)
}

const sendReply = () => {
  console.log('Sending reply:', replyMessage.value)
  if (markAsResolved.value && selectedTicket.value) {
    selectedTicket.value.status = 'resolved'
  }
  showReplyModal.value = false
  replyMessage.value = ''
  markAsResolved.value = false
}

onMounted(() => {
  console.log('Admin Customer Service loaded')
})
</script>

<style scoped>
/* Custom styles */
</style>
