<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Category Management</h1>
        <p class="text-gray-600 mt-1">Kelola kategori produk toko Anda</p>
      </div>

      <!-- Stats Cards -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-gray-600">Total Kategori</p>
              <p class="text-2xl font-bold text-gray-900">
                <span v-if="statsLoading" class="animate-pulse bg-gray-200 rounded h-8 w-12 inline-block"></span>
                <span v-else>{{ stats.total }}</span>
              </p>
            </div>
            <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-tags text-blue-600 text-xl"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-gray-600">Kategori Aktif</p>
              <p class="text-2xl font-bold text-gray-900">
                <span v-if="statsLoading" class="animate-pulse bg-gray-200 rounded h-8 w-12 inline-block"></span>
                <span v-else>{{ stats.active }}</span>
              </p>
            </div>
            <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-check-circle text-green-600 text-xl"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-gray-600">Total Produk</p>
              <p class="text-2xl font-bold text-gray-900">
                <span v-if="statsLoading" class="animate-pulse bg-gray-200 rounded h-8 w-12 inline-block"></span>
                <span v-else>{{ stats.totalProducts }}</span>
              </p>
            </div>
            <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-box text-yellow-600 text-xl"></i>
            </div>
          </div>
        </div>
        
        <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
          <div class="flex items-center justify-between">
            <div>
              <p class="text-sm font-medium text-gray-600">Kategori Terpopuler</p>
              <p class="text-2xl font-bold text-gray-900">
                <span v-if="statsLoading" class="animate-pulse bg-gray-200 rounded h-8 w-16 inline-block"></span>
                <span v-else>{{ stats.mostPopular }}</span>
              </p>
            </div>
            <div class="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-star text-orange-600 text-xl"></i>
            </div>
          </div>
        </div>
      </div>

      <!-- Filters Section -->
      <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
        <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
          <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
            <div class="relative">
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
              <input 
                v-model="searchQuery"
                type="text" 
                placeholder="Cari kategori..." 
                class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
              >
            </div>
            <select 
              v-model="statusFilter"
              class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
            >
              <option value="">Semua Status</option>
              <option value="active">Aktif</option>
              <option value="inactive">Tidak Aktif</option>
            </select>
          </div>
          <button 
            @click="openAddModal"
            class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
          >
            <i class="fa-solid fa-plus"></i>
            <span>Tambah Kategori</span>
          </button>
        </div>
      </div>

      <!-- Categories Grid -->
      <div v-if="loading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <!-- Loading skeleton -->
        <div v-for="n in 8" :key="n" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
          <div class="p-6">
            <div class="flex items-center justify-between mb-4">
              <div class="w-12 h-12 bg-gray-200 rounded-lg animate-pulse"></div>
              <div class="flex space-x-2">
                <div class="w-6 h-6 bg-gray-200 rounded animate-pulse"></div>
                <div class="w-6 h-6 bg-gray-200 rounded animate-pulse"></div>
              </div>
            </div>
            <div class="h-6 bg-gray-200 rounded mb-2 animate-pulse"></div>
            <div class="h-4 bg-gray-200 rounded mb-4 animate-pulse"></div>
            <div class="flex items-center justify-between">
              <div class="h-4 bg-gray-200 rounded w-16 animate-pulse"></div>
              <div class="h-6 bg-gray-200 rounded w-12 animate-pulse"></div>
            </div>
          </div>
        </div>
      </div>
      
      <div v-else-if="filteredCategories.length === 0" class="text-center py-12">
        <i class="fa-solid fa-tags text-gray-400 text-6xl mb-4"></i>
        <h3 class="text-lg font-medium text-gray-900 mb-2">Tidak ada kategori</h3>
        <p class="text-gray-500 mb-6">Belum ada kategori yang dibuat. Mulai dengan menambahkan kategori pertama.</p>
        <button 
          @click="openAddModal"
          class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
        >
          Tambah Kategori Pertama
        </button>
      </div>
      
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <div 
          v-for="category in filteredCategories" 
          :key="category.id"
          class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden hover:shadow-md transition-shadow"
        >
          <div class="p-6">
            <div class="flex items-center justify-between mb-4">
              <div class="w-12 h-12 rounded-lg bg-gray-100 flex items-center justify-center overflow-hidden">
                <img 
                  v-if="category.imageUrl && !category.imageUrl.startsWith('data:')" 
                  :src="category.imageUrl" 
                  :alt="category.name"
                  class="w-full h-full object-cover rounded-lg"
                  @error="(e) => { const target = e.target as HTMLImageElement; target.style.display = 'none'; }"
                >
                <img 
                  v-else-if="category.imageUrl && category.imageUrl.startsWith('data:')" 
                  :src="category.imageUrl" 
                  :alt="category.name"
                  class="w-full h-full object-cover rounded-lg"
                >
                <div v-else class="w-full h-full bg-gradient-to-br from-blue-100 to-blue-200 rounded-lg flex items-center justify-center">
                  <i class="fa-solid fa-image text-blue-400 text-lg"></i>
                </div>
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="editCategory(category)"
                  class="text-blue-600 hover:text-blue-700"
                  title="Edit"
                >
                  <i class="fa-solid fa-edit"></i>
                </button>
                <button 
                  @click="deleteCategory(category.id)"
                  class="text-red-600 hover:text-red-700"
                  title="Delete"
                >
                  <i class="fa-solid fa-trash"></i>
                </button>
              </div>
            </div>
            <h3 class="text-lg font-semibold text-gray-900 mb-2">{{ category.name }}</h3>
            <p class="text-sm text-gray-600 mb-4">{{ category.description }}</p>
            <div class="flex items-center justify-between">
              <span class="text-sm text-gray-500">{{ category.productCount }} produk</span>
              <span 
                :class="getStatusClass(category.status)"
                class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
              >
                {{ getStatusText(category.status) }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Add/Edit Category Modal -->
    <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
      <div class="bg-white rounded-lg max-w-lg w-full">
        <div class="flex items-center justify-between p-6 border-b border-gray-200">
          <h3 class="text-lg font-semibold text-gray-900">
            {{ isEditing ? 'Edit Kategori' : 'Tambah Kategori Baru' }}
          </h3>
          <button @click="closeModal" class="text-gray-400 hover:text-gray-600">
            <i class="fa-solid fa-times text-xl"></i>
          </button>
        </div>
        
        <form @submit.prevent="saveCategory" class="p-6 space-y-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Nama Kategori *</label>
            <input 
              v-model="categoryForm.name"
              type="text" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
              placeholder="Masukkan nama kategori"
              required
            >
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Deskripsi</label>
            <textarea 
              v-model="categoryForm.description"
              rows="3" 
              class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
              placeholder="Masukkan deskripsi kategori"
            ></textarea>
          </div>
          
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Gambar Kategori</label>
            <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center">
              <div v-if="categoryForm.imageUrl" class="mb-4">
                <img :src="categoryForm.imageUrl" alt="Preview" class="w-20 h-20 object-cover rounded-lg mx-auto">
              </div>
              
              <!-- Upload Progress -->
              <div v-if="isUploading" class="mb-4">
                <div class="w-full bg-gray-200 rounded-full h-2 mb-2">
                  <div 
                    class="bg-blue-600 h-2 rounded-full transition-all duration-300" 
                    :style="{ width: uploadProgress + '%' }"
                  ></div>
                </div>
                <p class="text-sm text-blue-600">Mengupload... {{ Math.round(uploadProgress) }}%</p>
              </div>
              
              <div v-else>
                <i class="fa-solid fa-cloud-upload-alt text-gray-400 text-3xl mb-2"></i>
                <p class="text-sm text-gray-600 mb-2">Upload gambar kategori</p>
                <input 
                  type="file" 
                  accept="image/*" 
                  @change="handleImageUpload"
                  class="hidden" 
                  ref="fileInput"
                  :disabled="isUploading"
                >
                <button 
                  type="button" 
                  @click="() => { const input = $refs.fileInput as HTMLInputElement; input?.click(); }"
                  :disabled="isUploading"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 text-sm disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  Pilih Gambar
                </button>
                <p class="text-xs text-gray-500 mt-2">PNG, JPG hingga 2MB</p>
              </div>
            </div>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Status</label>
            <div class="flex space-x-4">
              <label class="flex items-center">
                <input 
                  v-model="categoryForm.status"
                  type="radio" 
                  value="active" 
                  class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                >
                <span class="ml-2 text-sm text-gray-700">Aktif</span>
              </label>
              <label class="flex items-center">
                <input 
                  v-model="categoryForm.status"
                  type="radio" 
                  value="inactive" 
                  class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                >
                <span class="ml-2 text-sm text-gray-700">Tidak Aktif</span>
              </label>
            </div>
          </div>
          
          <div class="flex justify-end space-x-3 pt-4">
            <button 
              type="button" 
              @click="closeModal"
              class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50"
            >
              Batal
            </button>
            <button 
              type="submit"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            >
              {{ isEditing ? 'Update' : 'Simpan' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Category as ApiCategory } from '../../services/api'
import { uploadCategoryImage, validateImageFile, deleteCategoryImage, type UploadProgress } from '../../services/firebase'

interface Category {
  id: string
  name: string
  description: string
  imageUrl: string
  productCount: number
  status: 'active' | 'inactive'
}

interface CategoryForm {
  name: string
  description: string
  imageUrl: string
  status: 'active' | 'inactive'
}

const searchQuery = ref('')
const statusFilter = ref('')
const showModal = ref(false)
const isEditing = ref(false)
const editingId = ref<string | null>(null)

const stats = ref({
  total: 0,
  active: 0,
  totalProducts: 0,
  mostPopular: '-'
})

const loading = ref(false)
const statsLoading = ref(false)

const categoryForm = ref<CategoryForm>({
  name: '',
  description: '',
  imageUrl: '',
  status: 'active'
})

const categories = ref<Category[]>([])

// Helper function to generate slug from name
const generateSlug = (name: string): string => {
  return name
    .toLowerCase()
    .replace(/[^a-z0-9\s-]/g, '') // Remove special characters
    .replace(/\s+/g, '-') // Replace spaces with hyphens
    .replace(/-+/g, '-') // Replace multiple hyphens with single
    .trim()
}

// Helper function to transform API category to frontend category
const transformApiCategory = (apiCategory: ApiCategory): Category => {
  return {
    id: apiCategory.id.toString(),
    name: apiCategory.name,
    description: apiCategory.description || '',
    imageUrl: apiCategory.imageUrl || '',
    productCount: 0, // Will be calculated separately if needed
    status: apiCategory.isActive ? 'active' : 'inactive'
  }
}

// API Functions
const fetchCategories = async (search?: string, status?: string) => {
  try {
    loading.value = true
    
    // Build query parameters
    const params = new URLSearchParams()
    
    if (search && search.trim()) {
      params.append('search', search.trim())
    }
    
    if (status) {
      // Convert frontend status to backend boolean
      if (status === 'active') {
        params.append('active', 'true')
      } else if (status === 'inactive') {
        params.append('active', 'false')
      }
    }
    
    // Use direct fetch since apiService.getCategories doesn't support query params
    const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api'
    const url = `${baseUrl}/categories${params.toString() ? '?' + params.toString() : ''}`
    
    const response = await fetch(url, {
      headers: {
        'Content-Type': 'application/json'
      }
    })
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    
    const apiCategories = await response.json()
    categories.value = apiCategories.map(transformApiCategory)
  } catch (error) {
    console.error('Error fetching categories:', error)
    alert('Gagal memuat kategori. Silakan coba lagi.')
  } finally {
    loading.value = false
  }
}

const fetchStats = async () => {
  try {
    statsLoading.value = true
    
    // Use the direct API call since apiService doesn't have getCategoryStats method
    const response = await fetch(`${import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api'}/categories/admin/stats`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('auth_token')}`,
        'Content-Type': 'application/json'
      }
    })
    
    if (response.ok) {
      const statsData = await response.json()
      stats.value = {
        total: statsData.totalCategories || 0,
        active: statsData.activeCategories || 0,
        totalProducts: 0, // Will be calculated from product stats if needed
        mostPopular: '-' // Will be calculated if needed
      }
    } else {
      console.error('Failed to fetch stats:', response.status, response.statusText)
    }
  } catch (error) {
    console.error('Error fetching stats:', error)
    // Keep default values on error
  } finally {
    statsLoading.value = false
  }
}

// Since we're now filtering on the backend, filteredCategories just returns categories
const filteredCategories = computed(() => categories.value)

const getStatusClass = (status: string) => {
  return status === 'active' 
    ? 'bg-green-100 text-green-800' 
    : 'bg-yellow-100 text-yellow-800'
}

const getStatusText = (status: string) => {
  return status === 'active' ? 'Aktif' : 'Tidak Aktif'
}


const openAddModal = () => {
  isEditing.value = false
  editingId.value = null
  categoryForm.value = {
    name: '',
    description: '',
    imageUrl: '',
    status: 'active'
  }
  showModal.value = true
}

const editCategory = (category: Category) => {
  isEditing.value = true
  editingId.value = category.id
  categoryForm.value = {
    name: category.name,
    description: category.description,
    imageUrl: category.imageUrl,
    status: category.status
  }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  isEditing.value = false
  editingId.value = null
}

// Image upload functionality with Firebase Storage
const uploadProgress = ref(0)
const isUploading = ref(false)

const handleImageUpload = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  
  if (!file) return

  try {
    isUploading.value = true
    uploadProgress.value = 0

    // Validate file using Firebase service
    const validationError = validateImageFile(file)
    if (validationError) {
      alert(validationError)
      return
    }

    // Create local preview while uploading
    const reader = new FileReader()
    reader.onload = (e) => {
      // Show preview immediately
      categoryForm.value.imageUrl = e.target?.result as string
    }
    reader.readAsDataURL(file)

    // Upload to Firebase Storage
    const result = await uploadCategoryImage(file, (progress) => {
      uploadProgress.value = progress.percentage
    })

    // Update form with Firebase URL
    categoryForm.value.imageUrl = result.url
    
    alert('Gambar berhasil diupload!')
  } catch (error) {
    console.error('Error uploading image:', error)
    alert(error instanceof Error ? error.message : 'Gagal mengupload gambar. Silakan coba lagi.')
    
    // Reset image URL on error
    categoryForm.value.imageUrl = ''
  } finally {
    isUploading.value = false
    uploadProgress.value = 0
    
    // Reset file input
    if (target) {
      target.value = ''
    }
  }
}

const saveCategory = async () => {
  try {
    const slug = generateSlug(categoryForm.value.name)
    
    if (isEditing.value && editingId.value) {
      // Get the original category to check if image changed
      const originalCategory = categories.value.find(c => c.id === editingId.value)
      const oldImageUrl = originalCategory?.imageUrl
      const newImageUrl = categoryForm.value.imageUrl
      
      // Update existing category
      const updateData = {
        name: categoryForm.value.name,
        description: categoryForm.value.description,
        slug: slug,
        imageUrl: newImageUrl,
        isActive: categoryForm.value.status === 'active'
      }
      
      await apiService.updateCategory(parseInt(editingId.value), updateData)
      
      // Delete old image if it was changed and it's a Firebase URL
      if (oldImageUrl && oldImageUrl !== newImageUrl && oldImageUrl.includes('firebase')) {
        try {
          await deleteCategoryImage(oldImageUrl)
        } catch (error) {
          console.error('Error deleting old image:', error)
          // Don't fail the update if image deletion fails
        }
      }
      
      alert('Kategori berhasil diupdate!')
      
      // Refresh data
      await fetchCategories()
      await fetchStats()
    } else {
      // Add new category
      const createData = {
        name: categoryForm.value.name,
        description: categoryForm.value.description,
        slug: slug,
        imageUrl: categoryForm.value.imageUrl,
        isActive: categoryForm.value.status === 'active'
      }
      
      await apiService.createCategory(createData)
      alert('Kategori berhasil ditambahkan!')
      
      // Refresh data
      await fetchCategories()
      await fetchStats()
    }
    
    closeModal()
  } catch (error) {
    console.error('Error saving category:', error)
    alert('Gagal menyimpan kategori. Silakan coba lagi.')
  }
}

const deleteCategory = async (categoryId: string) => {
  if (confirm('Apakah Anda yakin ingin menghapus kategori ini?')) {
    try {
      // Get the category to delete its image
      const categoryToDelete = categories.value.find(c => c.id === categoryId)
      const imageUrl = categoryToDelete?.imageUrl
      
      // Delete category from backend
      await apiService.deleteCategory(parseInt(categoryId))
      
      // Delete image from Firebase Storage if it exists
      if (imageUrl && imageUrl.includes('firebase')) {
        try {
          await deleteCategoryImage(imageUrl)
        } catch (error) {
          console.error('Error deleting category image:', error)
          // Don't fail the deletion if image deletion fails
        }
      }
      
      alert('Kategori berhasil dihapus!')
      
      // Refresh data
      await fetchCategories()
      await fetchStats()
    } catch (error) {
      console.error('Error deleting category:', error)
      alert('Gagal menghapus kategori. Silakan coba lagi.')
    }
  }
}

// Debounced function for API calls
let debounceTimer: NodeJS.Timeout | null = null

const debouncedFetchCategories = (search: string, status: string) => {
  if (debounceTimer) {
    clearTimeout(debounceTimer)
  }
  
  debounceTimer = setTimeout(async () => {
    console.log('Filter changed:', { search, status })
    await fetchCategories(search, status)
  }, 300)
}

// Watch for filter changes and refetch data from backend
watch([searchQuery, statusFilter], ([newSearch, newStatus]) => {
  debouncedFetchCategories(newSearch, newStatus)
})

onMounted(async () => {
  console.log('Admin Category Management loaded')
  
  // Fetch initial data
  await Promise.all([
    fetchCategories(),
    fetchStats()
  ])
})
</script>
