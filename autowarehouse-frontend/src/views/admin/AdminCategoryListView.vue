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
              <div>
                <h1 class="text-2xl font-bold text-gray-900">Category Management</h1>
                <p class="text-gray-600">Organize and manage product categories</p>
              </div>
              <div class="flex items-center space-x-4">
                <button 
                  @click="showCreateModal = true"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-plus mr-2"></i>
                  Add Category
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
          <!-- Stats Cards -->
          <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Categories</p>
                  <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
                </div>
                <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-tags text-blue-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Active Categories</p>
                  <p class="text-2xl font-bold text-green-600">{{ stats.active }}</p>
                </div>
                <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-check-circle text-green-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Total Products</p>
                  <p class="text-2xl font-bold text-purple-600">{{ stats.products }}</p>
                </div>
                <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-box text-purple-600"></i>
                </div>
              </div>
            </div>

            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex items-center justify-between">
                <div>
                  <p class="text-sm text-gray-600">Subcategories</p>
                  <p class="text-2xl font-bold text-orange-600">{{ stats.subcategories }}</p>
                </div>
                <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-sitemap text-orange-600"></i>
                </div>
              </div>
            </div>
          </div>

          <!-- Category Tree View -->
          <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
            <!-- Category Tree -->
            <div class="lg:col-span-2">
              <div class="bg-white rounded-xl shadow-sm">
                <div class="px-6 py-4 border-b border-gray-200">
                  <div class="flex items-center justify-between">
                    <h3 class="text-lg font-semibold text-gray-900">Category Hierarchy</h3>
                    <div class="flex items-center space-x-2">
                      <button 
                        @click="expandAll"
                        class="px-3 py-1 text-sm text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
                      >
                        <i class="fa-solid fa-expand mr-1"></i>
                        Expand All
                      </button>
                      <button 
                        @click="collapseAll"
                        class="px-3 py-1 text-sm text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
                      >
                        <i class="fa-solid fa-compress mr-1"></i>
                        Collapse All
                      </button>
                    </div>
                  </div>
                </div>
                <div class="p-6">
                  <div class="space-y-2">
                    <div 
                      v-for="category in categories" 
                      :key="category.id"
                      class="category-item"
                    >
                      <!-- Parent Category -->
                      <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                        <div class="flex items-center space-x-3">
                          <button 
                            @click="toggleCategory(category.id)"
                            class="text-gray-400 hover:text-gray-600"
                          >
                            <i :class="category.expanded ? 'fa-solid fa-chevron-down' : 'fa-solid fa-chevron-right'"></i>
                          </button>
                          <div 
                            :class="getCategoryIconClass(category.icon)"
                            class="w-8 h-8 rounded-lg flex items-center justify-center"
                          >
                            <i :class="category.icon" class="text-sm"></i>
                          </div>
                          <div>
                            <h4 class="font-medium text-gray-900">{{ category.name }}</h4>
                            <p class="text-sm text-gray-500">{{ category.productCount }} products</p>
                          </div>
                        </div>
                        <div class="flex items-center space-x-2">
                          <span 
                            :class="category.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                            class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                          >
                            {{ category.status }}
                          </span>
                          <button 
                            @click="editCategory(category)"
                            class="text-blue-600 hover:text-blue-900"
                          >
                            <i class="fa-solid fa-edit"></i>
                          </button>
                          <button 
                            @click="deleteCategory(category.id)"
                            class="text-red-600 hover:text-red-900"
                          >
                            <i class="fa-solid fa-trash"></i>
                          </button>
                        </div>
                      </div>

                      <!-- Subcategories -->
                      <div v-if="category.expanded && category.subcategories" class="ml-8 mt-2 space-y-2">
                        <div 
                          v-for="subcategory in category.subcategories" 
                          :key="subcategory.id"
                          class="flex items-center justify-between p-3 bg-white border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
                        >
                          <div class="flex items-center space-x-3">
                            <div class="w-6 h-6 bg-gray-100 rounded flex items-center justify-center">
                              <i class="fa-solid fa-tag text-gray-500 text-xs"></i>
                            </div>
                            <div>
                              <h5 class="font-medium text-gray-900">{{ subcategory.name }}</h5>
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
                            <button 
                              @click="editCategory(subcategory)"
                              class="text-blue-600 hover:text-blue-900"
                            >
                              <i class="fa-solid fa-edit"></i>
                            </button>
                            <button 
                              @click="deleteCategory(subcategory.id)"
                              class="text-red-600 hover:text-red-900"
                            >
                              <i class="fa-solid fa-trash"></i>
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Category Details -->
            <div class="lg:col-span-1">
              <div class="bg-white rounded-xl shadow-sm p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h3>
                <div class="space-y-3">
                  <button 
                    @click="showCreateModal = true"
                    class="w-full flex items-center justify-center px-4 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
                  >
                    <i class="fa-solid fa-plus mr-2"></i>
                    Add New Category
                  </button>
                  <button 
                    @click="bulkImport"
                    class="w-full flex items-center justify-center px-4 py-3 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors"
                  >
                    <i class="fa-solid fa-upload mr-2"></i>
                    Bulk Import
                  </button>
                  <button 
                    @click="exportCategories"
                    class="w-full flex items-center justify-center px-4 py-3 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors"
                  >
                    <i class="fa-solid fa-download mr-2"></i>
                    Export Categories
                  </button>
                </div>
              </div>

              <!-- Category Analytics -->
              <div class="bg-white rounded-xl shadow-sm p-6 mt-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">Top Categories</h3>
                <div class="space-y-4">
                  <div 
                    v-for="top in topCategories" 
                    :key="top.id"
                    class="flex items-center justify-between"
                  >
                    <div class="flex items-center space-x-3">
                      <div 
                        :class="getCategoryIconClass(top.icon)"
                        class="w-8 h-8 rounded-lg flex items-center justify-center"
                      >
                        <i :class="top.icon" class="text-sm"></i>
                      </div>
                      <div>
                        <p class="font-medium text-gray-900">{{ top.name }}</p>
                        <p class="text-sm text-gray-500">{{ top.productCount }} products</p>
                      </div>
                    </div>
                    <div class="text-right">
                      <p class="font-semibold text-gray-900">{{ top.revenue }}%</p>
                      <p class="text-sm text-gray-500">of revenue</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>

    <!-- Create/Edit Category Modal -->
    <div v-if="showCreateModal || editingCategory" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-full max-w-md mx-4">
        <div class="flex items-center justify-between mb-6">
          <h3 class="text-lg font-semibold text-gray-900">
            {{ editingCategory ? 'Edit Category' : 'Create New Category' }}
          </h3>
          <button @click="closeModal" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <form @submit.prevent="saveCategory" class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Category Name</label>
            <input 
              v-model="categoryForm.name"
              type="text" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            >
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Parent Category</label>
            <select 
              v-model="categoryForm.parentId"
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">None (Root Category)</option>
              <option 
                v-for="category in categories" 
                :key="category.id"
                :value="category.id"
              >
                {{ category.name }}
              </option>
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Icon</label>
            <select 
              v-model="categoryForm.icon"
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="fa-solid fa-laptop">Electronics</option>
              <option value="fa-solid fa-car">Automotive</option>
              <option value="fa-solid fa-gamepad">Gaming</option>
              <option value="fa-solid fa-tools">Tools</option>
              <option value="fa-solid fa-home">Home & Garden</option>
              <option value="fa-solid fa-tshirt">Fashion</option>
            </select>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Description</label>
            <textarea 
              v-model="categoryForm.description"
              rows="3" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            ></textarea>
          </div>
          <div class="flex items-center">
            <input 
              v-model="categoryForm.isActive"
              type="checkbox" 
              class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
            >
            <label class="ml-2 text-sm text-gray-700">Active</label>
          </div>
          <div class="flex justify-end space-x-4">
            <button 
              type="button"
              @click="closeModal"
              class="px-4 py-2 text-gray-600 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              {{ editingCategory ? 'Update' : 'Create' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'

// State
const showCreateModal = ref(false)
const editingCategory = ref(null)

// Stats
const stats = ref({
  total: 24,
  active: 20,
  products: 1247,
  subcategories: 48
})

// Category form
const categoryForm = ref({
  name: '',
  parentId: '',
  icon: 'fa-solid fa-laptop',
  description: '',
  isActive: true
})

// Sample categories data
const categories = ref([
  {
    id: 1,
    name: 'Electronics',
    icon: 'fa-solid fa-laptop',
    productCount: 456,
    status: 'active',
    expanded: true,
    subcategories: [
      { id: 11, name: 'Smartphones', productCount: 123, status: 'active' },
      { id: 12, name: 'Laptops', productCount: 89, status: 'active' },
      { id: 13, name: 'Tablets', productCount: 67, status: 'active' },
      { id: 14, name: 'Accessories', productCount: 177, status: 'active' }
    ]
  },
  {
    id: 2,
    name: 'Automotive',
    icon: 'fa-solid fa-car',
    productCount: 234,
    status: 'active',
    expanded: false,
    subcategories: [
      { id: 21, name: 'Engine Parts', productCount: 89, status: 'active' },
      { id: 22, name: 'Body Parts', productCount: 67, status: 'active' },
      { id: 23, name: 'Electronics', productCount: 45, status: 'active' },
      { id: 24, name: 'Accessories', productCount: 33, status: 'active' }
    ]
  },
  {
    id: 3,
    name: 'Gaming',
    icon: 'fa-solid fa-gamepad',
    productCount: 189,
    status: 'active',
    expanded: false,
    subcategories: [
      { id: 31, name: 'Consoles', productCount: 45, status: 'active' },
      { id: 32, name: 'Games', productCount: 78, status: 'active' },
      { id: 33, name: 'Accessories', productCount: 66, status: 'active' }
    ]
  },
  {
    id: 4,
    name: 'Tools',
    icon: 'fa-solid fa-tools',
    productCount: 156,
    status: 'active',
    expanded: false,
    subcategories: [
      { id: 41, name: 'Hand Tools', productCount: 67, status: 'active' },
      { id: 42, name: 'Power Tools', productCount: 89, status: 'active' }
    ]
  }
])

const topCategories = ref([
  { id: 1, name: 'Electronics', icon: 'fa-solid fa-laptop', productCount: 456, revenue: 45 },
  { id: 2, name: 'Automotive', icon: 'fa-solid fa-car', productCount: 234, revenue: 28 },
  { id: 3, name: 'Gaming', icon: 'fa-solid fa-gamepad', productCount: 189, revenue: 18 },
  { id: 4, name: 'Tools', icon: 'fa-solid fa-tools', productCount: 156, revenue: 9 }
])

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

const toggleCategory = (categoryId: number) => {
  const category = categories.value.find(c => c.id === categoryId)
  if (category) {
    category.expanded = !category.expanded
  }
}

const expandAll = () => {
  categories.value.forEach(category => {
    category.expanded = true
  })
}

const collapseAll = () => {
  categories.value.forEach(category => {
    category.expanded = false
  })
}

const editCategory = (category: any) => {
  editingCategory.value = category
  categoryForm.value = {
    name: category.name,
    parentId: category.parentId || '',
    icon: category.icon,
    description: category.description || '',
    isActive: category.status === 'active'
  }
}

const deleteCategory = (categoryId: number) => {
  if (confirm('Are you sure you want to delete this category?')) {
    // Remove from main categories
    const mainIndex = categories.value.findIndex(c => c.id === categoryId)
    if (mainIndex > -1) {
      categories.value.splice(mainIndex, 1)
      return
    }
    
    // Remove from subcategories
    categories.value.forEach(category => {
      if (category.subcategories) {
        const subIndex = category.subcategories.findIndex(s => s.id === categoryId)
        if (subIndex > -1) {
          category.subcategories.splice(subIndex, 1)
        }
      }
    })
  }
}

const saveCategory = () => {
  console.log('Saving category:', categoryForm.value)
  closeModal()
}

const closeModal = () => {
  showCreateModal.value = false
  editingCategory.value = null
  categoryForm.value = {
    name: '',
    parentId: '',
    icon: 'fa-solid fa-laptop',
    description: '',
    isActive: true
  }
}

const bulkImport = () => {
  console.log('Bulk import categories')
}

const exportCategories = () => {
  console.log('Export categories')
}

onMounted(() => {
  console.log('Admin Category Management loaded')
})
</script>

<style scoped>
/* Custom styles */
</style>
