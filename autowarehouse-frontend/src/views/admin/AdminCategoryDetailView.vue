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
            class="flex items-center px-6 py-3 text-gray-700 bg-blue-50 border-r-4 border-blue-600"
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
              <div class="flex items-center space-x-4">
                <router-link 
                  to="/admin/categories"
                  class="text-gray-400 hover:text-gray-600"
                >
                  <i class="fa-solid fa-arrow-left text-xl"></i>
                </router-link>
                <div class="flex items-center space-x-3">
                  <div 
                    :class="getCategoryIconClass(category.icon)"
                    class="w-12 h-12 rounded-lg flex items-center justify-center"
                  >
                    <i :class="category.icon"></i>
                  </div>
                  <div>
                    <h1 class="text-2xl font-bold text-gray-900">{{ category.name }}</h1>
                    <p class="text-gray-600">Category Details & Management</p>
                  </div>
                </div>
              </div>
              <div class="flex items-center space-x-4">
                <button 
                  @click="editCategory"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-edit mr-2"></i>
                  Edit Category
                </button>
                <button 
                  @click="toggleStatus"
                  :class="category.status === 'active' ? 'bg-red-600 hover:bg-red-700' : 'bg-green-600 hover:bg-green-700'"
                  class="text-white px-4 py-2 rounded-lg transition-colors"
                >
                  <i :class="category.status === 'active' ? 'fa-solid fa-pause' : 'fa-solid fa-play'" class="mr-2"></i>
                  {{ category.status === 'active' ? 'Deactivate' : 'Activate' }}
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
          <!-- Category Overview -->
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
            <!-- Category Info -->
            <div class="lg:col-span-2 space-y-6">
              <!-- Basic Information -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Category Information</h3>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div>
                    <p class="text-sm text-gray-500 mb-1">Category Name</p>
                    <p class="font-medium text-gray-900">{{ category.name }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">Status</p>
                    <span 
                      :class="category.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                      class="inline-flex px-2 py-1 text-sm font-semibold rounded-full"
                    >
                      {{ category.status }}
                    </span>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">Parent Category</p>
                    <p class="font-medium text-gray-900">{{ category.parent || 'Root Category' }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">Created Date</p>
                    <p class="font-medium text-gray-900">{{ formatDate(category.createdAt) }}</p>
                  </div>
                  <div class="md:col-span-2">
                    <p class="text-sm text-gray-500 mb-1">Description</p>
                    <p class="text-gray-900">{{ category.description }}</p>
                  </div>
                </div>
              </div>

              <!-- Subcategories -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-lg font-semibold text-gray-900">Subcategories</h3>
                  <button 
                    @click="showAddSubcategoryModal = true"
                    class="bg-blue-600 text-white px-3 py-1 rounded-lg text-sm hover:bg-blue-700 transition-colors"
                  >
                    <i class="fa-solid fa-plus mr-1"></i>
                    Add Subcategory
                  </button>
                </div>
                <div class="space-y-3">
                  <div 
                    v-for="subcategory in category.subcategories" 
                    :key="subcategory.id"
                    class="flex items-center justify-between p-3 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
                  >
                    <div class="flex items-center space-x-3">
                      <div class="w-8 h-8 bg-gray-100 rounded-lg flex items-center justify-center">
                        <i class="fa-solid fa-tag text-gray-500"></i>
                      </div>
                      <div>
                        <h4 class="font-medium text-gray-900">{{ subcategory.name }}</h4>
                        <p class="text-sm text-gray-500">{{ subcategory.productCount }} products</p>
                      </div>
                    </div>
                    <div class="flex items-center space-x-2">
                      <span 
                        :class="subcategory.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ subcategory.status }}
                      </span>
                      <button class="text-blue-600 hover:text-blue-900">
                        <i class="fa-solid fa-edit"></i>
                      </button>
                      <button class="text-red-600 hover:text-red-900">
                        <i class="fa-solid fa-trash"></i>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Stats & Analytics -->
            <div class="lg:col-span-1 space-y-6">
              <!-- Stats Cards -->
              <div class="space-y-4">
                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Total Products</p>
                      <p class="text-2xl font-bold text-blue-600">{{ category.productCount }}</p>
                    </div>
                    <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-box text-blue-600"></i>
                    </div>
                  </div>
                </div>

                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Active Auctions</p>
                      <p class="text-2xl font-bold text-green-600">{{ category.activeAuctions }}</p>
                    </div>
                    <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-gavel text-green-600"></i>
                    </div>
                  </div>
                </div>

                <div class="bg-white rounded-xl shadow-sm p-6">
                  <div class="flex items-center justify-between">
                    <div>
                      <p class="text-sm text-gray-600">Monthly Revenue</p>
                      <p class="text-2xl font-bold text-purple-600">Rp {{ category.monthlyRevenue.toLocaleString() }}</p>
                    </div>
                    <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-chart-line text-purple-600"></i>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Quick Actions -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h3>
                <div class="space-y-3">
                  <button class="w-full flex items-center justify-center px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors">
                    <i class="fa-solid fa-plus mr-2"></i>
                    Add Product
                  </button>
                  <button class="w-full flex items-center justify-center px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors">
                    <i class="fa-solid fa-gavel mr-2"></i>
                    Create Auction
                  </button>
                  <button class="w-full flex items-center justify-center px-4 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors">
                    <i class="fa-solid fa-chart-bar mr-2"></i>
                    View Analytics
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Products in Category -->
          <div class="bg-white rounded-xl shadow-sm mb-8">
            <div class="px-6 py-4 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Products in Category</h3>
                <div class="flex items-center space-x-2">
                  <div class="relative">
                    <input 
                      v-model="productSearchQuery"
                      type="text" 
                      placeholder="Search products..." 
                      class="pl-8 pr-4 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                    <i class="fa-solid fa-search absolute left-2.5 top-1/2 transform -translate-y-1/2 text-gray-400 text-sm"></i>
                  </div>
                  <select 
                    v-model="productStatusFilter"
                    class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                    <option value="">All Status</option>
                    <option value="active">Active</option>
                    <option value="inactive">Inactive</option>
                  </select>
                </div>
              </div>
            </div>
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50">
                  <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Price</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stock</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                  </tr>
                </thead>
                <tbody class="divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id"
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <img :src="product.image" :alt="product.name" class="w-12 h-12 rounded-lg object-cover mr-4">
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">{{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      Rp {{ product.price.toLocaleString() }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ product.stock }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="product.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ product.status }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex items-center space-x-2">
                        <button class="text-blue-600 hover:text-blue-900">
                          <i class="fa-solid fa-eye"></i>
                        </button>
                        <button class="text-green-600 hover:text-green-900">
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button class="text-red-600 hover:text-red-900">
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Category Analytics -->
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <!-- Revenue Chart -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <h3 class="text-lg font-semibold text-gray-900 mb-4">Revenue Trend</h3>
              <div class="h-64 flex items-center justify-center bg-gray-50 rounded-lg">
                <p class="text-gray-500">Revenue Chart Placeholder</p>
              </div>
            </div>

            <!-- Top Products -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <h3 class="text-lg font-semibold text-gray-900 mb-4">Top Products</h3>
              <div class="space-y-4">
                <div 
                  v-for="product in topProducts" 
                  :key="product.id"
                  class="flex items-center justify-between"
                >
                  <div class="flex items-center space-x-3">
                    <img :src="product.image" :alt="product.name" class="w-10 h-10 rounded-lg object-cover">
                    <div>
                      <p class="font-medium text-gray-900">{{ product.name }}</p>
                      <p class="text-sm text-gray-500">{{ product.sales }} sales</p>
                    </div>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">Rp {{ product.revenue.toLocaleString() }}</p>
                    <p class="text-sm text-gray-500">revenue</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <!-- Add Subcategory Modal -->
    <div v-if="showAddSubcategoryModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-md mx-4">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-900">Add Subcategory</h3>
          <button @click="showAddSubcategoryModal = false" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="addSubcategory" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Subcategory Name</label>
            <input 
              v-model="subcategoryForm.name"
              type="text" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
            <textarea 
              v-model="subcategoryForm.description"
              rows="3" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            ></textarea>
          </div>
          <div class="flex justify-end space-x-4">
            <button 
              type="button"
              @click="showAddSubcategoryModal = false"
              class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              Add Subcategory
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// State
const showAddSubcategoryModal = ref(false)
const productSearchQuery = ref('')
const productStatusFilter = ref('')

// Forms
const subcategoryForm = ref({
  name: '',
  description: ''
})

// Sample category data
const category = ref({
  id: 1,
  name: 'Electronics',
  icon: 'fa-solid fa-laptop',
  description: 'Electronic devices, gadgets, and accessories including smartphones, laptops, tablets, and more.',
  status: 'active',
  parent: null,
  productCount: 456,
  activeAuctions: 24,
  monthlyRevenue: 18500000,
  createdAt: new Date('2023-01-15'),
  subcategories: [
    { id: 11, name: 'Smartphones', productCount: 123, status: 'active' },
    { id: 12, name: 'Laptops', productCount: 89, status: 'active' },
    { id: 13, name: 'Tablets', productCount: 67, status: 'active' },
    { id: 14, name: 'Accessories', productCount: 177, status: 'active' }
  ]
})

// Sample products data
const products = ref([
  {
    id: 1,
    name: 'iPhone 15 Pro Max',
    sku: 'IPH-15-PM',
    price: 20000000,
    stock: 25,
    status: 'active',
    image: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=100&h=100&fit=crop'
  },
  {
    id: 2,
    name: 'MacBook Pro 14"',
    sku: 'MBP-14-M3',
    price: 32000000,
    stock: 12,
    status: 'active',
    image: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=100&h=100&fit=crop'
  },
  {
    id: 3,
    name: 'iPad Pro 12.9"',
    sku: 'IPD-PRO-129',
    price: 15000000,
    stock: 18,
    status: 'active',
    image: 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=100&h=100&fit=crop'
  },
  {
    id: 4,
    name: 'AirPods Pro',
    sku: 'APP-2ND',
    price: 3500000,
    stock: 45,
    status: 'inactive',
    image: 'https://images.unsplash.com/photo-1606220945770-b5b6c2c55bf1?w=100&h=100&fit=crop'
  }
])

const topProducts = ref([
  {
    id: 1,
    name: 'iPhone 15 Pro Max',
    sales: 156,
    revenue: 31200000,
    image: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=100&h=100&fit=crop'
  },
  {
    id: 2,
    name: 'MacBook Pro 14"',
    sales: 89,
    revenue: 28480000,
    image: 'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=100&h=100&fit=crop'
  },
  {
    id: 3,
    name: 'iPad Pro 12.9"',
    sales: 67,
    revenue: 10050000,
    image: 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=100&h=100&fit=crop'
  }
])

// Computed
const filteredProducts = computed(() => {
  let filtered = products.value

  if (productSearchQuery.value) {
    filtered = filtered.filter(product =>
      product.name.toLowerCase().includes(productSearchQuery.value.toLowerCase()) ||
      product.sku.toLowerCase().includes(productSearchQuery.value.toLowerCase())
    )
  }

  if (productStatusFilter.value) {
    filtered = filtered.filter(product => product.status === productStatusFilter.value)
  }

  return filtered
})

// Methods
const getCategoryIconClass = (icon: string) => {
  const iconClasses = {
    'fa-solid fa-laptop': 'bg-blue-100 text-blue-600',
    'fa-solid fa-car': 'bg-green-100 text-green-600',
    'fa-solid fa-gamepad': 'bg-purple-100 text-purple-600',
    'fa-solid fa-tools': 'bg-orange-100 text-orange-600',
    'fa-solid fa-home': 'bg-yellow-100 text-yellow-600',
    'fa-solid fa-tshirt': 'bg-pink-100 text-pink-600'
  }
  return iconClasses[icon as keyof typeof iconClasses] || 'bg-gray-100 text-gray-600'
}

const formatDate = (date: Date) => {
  return date.toLocaleDateString('id-ID', {
    day: 'numeric',
    month: 'long',
    year: 'numeric'
  })
}

const editCategory = () => {
  router.push(`/admin/categories/${category.value.id}/edit`)
}

const toggleStatus = () => {
  category.value.status = category.value.status === 'active' ? 'inactive' : 'active'
}

const addSubcategory = () => {
  console.log('Adding subcategory:', subcategoryForm.value)
  showAddSubcategoryModal.value = false
  subcategoryForm.value = {
    name: '',
    description: ''
  }
}

onMounted(() => {
  const categoryId = route.params.id
  console.log('Loading category details for ID:', categoryId)
})
</script>

<style scoped>
/* Custom styles */
</style>
