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

          <!-- Category List -->
          <div class="bg-white rounded-xl shadow-sm">
            <div class="px-6 py-4 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Categories</h3>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div 
                  v-for="category in categories" 
                  :key="category.id"
                  class="flex items-center justify-between p-4 border border-gray-200 rounded-lg hover:bg-gray-50"
                >
                  <div class="flex items-center space-x-4">
                    <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                      <i class="fa-solid fa-tag text-blue-600"></i>
                    </div>
                    <div>
                      <h4 class="font-medium text-gray-900">{{ category.name }}</h4>
                      <p class="text-sm text-gray-500">{{ category.description || 'No description' }}</p>
                    </div>
                  </div>
                  <div class="flex items-center space-x-3">
                    <span 
                      :class="category.isActive ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'"
                      class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                    >
                      {{ category.isActive ? 'Active' : 'Inactive' }}
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
import { ref, onMounted, computed } from 'vue'
import { apiService, type Category } from '@/services/api'

// State
const showCreateModal = ref(false)
const editingCategory = ref<Category | null>(null)
const loading = ref(false)
const error = ref('')

// Categories data
const categories = ref<Category[]>([])

// Category form
const categoryForm = ref({
  name: '',
  description: '',
  isActive: true
})

// Computed properties
const stats = computed(() => {
  const total = categories.value.length
  const active = categories.value.filter(c => c.isActive).length
  const subcategories = categories.value.filter(c => c.parentId).length
  
  return {
    total,
    active,
    products: 0, // This would need to be calculated from products API
    subcategories
  }
})

// Methods
const loadCategories = async () => {
  try {
    loading.value = true
    categories.value = await apiService.getCategories()
  } catch (err) {
    error.value = 'Failed to load categories'
    console.error('Error loading categories:', err)
  } finally {
    loading.value = false
  }
}

const editCategory = (category: Category) => {
  editingCategory.value = category
  categoryForm.value = {
    name: category.name,
    description: category.description || '',
    isActive: category.isActive
  }
}

const deleteCategory = async (categoryId: number) => {
  if (confirm('Are you sure you want to delete this category?')) {
    try {
      await apiService.deleteCategory(categoryId)
      await loadCategories()
    } catch (err) {
      console.error('Error deleting category:', err)
    }
  }
}

const saveCategory = async () => {
  try {
    if (editingCategory.value) {
      await apiService.updateCategory(editingCategory.value.id, categoryForm.value)
    } else {
      await apiService.createCategory({
        ...categoryForm.value,
        slug: categoryForm.value.name.toLowerCase().replace(/\s+/g, '-')
      })
    }
    await loadCategories()
    closeModal()
  } catch (err) {
    console.error('Error saving category:', err)
  }
}

const closeModal = () => {
  showCreateModal.value = false
  editingCategory.value = null
  categoryForm.value = {
    name: '',
    description: '',
    isActive: true
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped>
/* Custom styles */
</style>
