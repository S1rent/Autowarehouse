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
              <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
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
              <p class="text-2xl font-bold text-gray-900">{{ stats.active }}</p>
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
              <p class="text-2xl font-bold text-gray-900">{{ stats.totalProducts }}</p>
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
              <p class="text-2xl font-bold text-gray-900">{{ stats.mostPopular }}</p>
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
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <div 
          v-for="category in filteredCategories" 
          :key="category.id"
          class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden hover:shadow-md transition-shadow"
        >
          <div class="p-6">
            <div class="flex items-center justify-between mb-4">
              <div :class="category.iconBg" class="w-12 h-12 rounded-lg flex items-center justify-center">
                <i :class="[category.icon, category.iconColor, 'text-xl']"></i>
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
            <label class="block text-sm font-medium text-gray-700 mb-2">Icon</label>
            <div class="grid grid-cols-6 gap-3">
              <button 
                v-for="icon in availableIcons" 
                :key="icon.name"
                type="button"
                @click="selectIcon(icon)"
                :class="categoryForm.icon === icon.name ? 'border-blue-600 bg-blue-600 text-white' : 'border-gray-300 hover:border-blue-600 hover:bg-blue-600 hover:text-white'"
                class="p-3 border rounded-lg transition-colors"
              >
                <i :class="icon.class"></i>
              </button>
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
import { ref, computed, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface Category {
  id: string
  name: string
  description: string
  icon: string
  iconBg: string
  iconColor: string
  productCount: number
  status: 'active' | 'inactive'
}

interface CategoryForm {
  name: string
  description: string
  icon: string
  status: 'active' | 'inactive'
}

const searchQuery = ref('')
const statusFilter = ref('')
const showModal = ref(false)
const isEditing = ref(false)
const editingId = ref<string | null>(null)

const stats = ref({
  total: 12,
  active: 10,
  totalProducts: 245,
  mostPopular: 'Elektronik'
})

const categoryForm = ref<CategoryForm>({
  name: '',
  description: '',
  icon: 'fa-mobile-alt',
  status: 'active'
})

const availableIcons = [
  { name: 'fa-mobile-alt', class: 'fa-solid fa-mobile-alt' },
  { name: 'fa-tshirt', class: 'fa-solid fa-tshirt' },
  { name: 'fa-utensils', class: 'fa-solid fa-utensils' },
  { name: 'fa-book', class: 'fa-solid fa-book' },
  { name: 'fa-home', class: 'fa-solid fa-home' },
  { name: 'fa-dumbbell', class: 'fa-solid fa-dumbbell' },
  { name: 'fa-car', class: 'fa-solid fa-car' },
  { name: 'fa-gamepad', class: 'fa-solid fa-gamepad' },
  { name: 'fa-music', class: 'fa-solid fa-music' },
  { name: 'fa-camera', class: 'fa-solid fa-camera' },
  { name: 'fa-laptop', class: 'fa-solid fa-laptop' },
  { name: 'fa-heart', class: 'fa-solid fa-heart' }
]

const categories = ref<Category[]>([
  {
    id: '1',
    name: 'Elektronik',
    description: 'Smartphone, laptop, dan perangkat elektronik lainnya',
    icon: 'fa-mobile-alt',
    iconBg: 'bg-blue-100',
    iconColor: 'text-blue-600',
    productCount: 85,
    status: 'active'
  },
  {
    id: '2',
    name: 'Fashion',
    description: 'Pakaian, sepatu, dan aksesoris fashion',
    icon: 'fa-tshirt',
    iconBg: 'bg-pink-100',
    iconColor: 'text-pink-600',
    productCount: 62,
    status: 'active'
  },
  {
    id: '3',
    name: 'Makanan & Minuman',
    description: 'Makanan olahan, minuman, dan camilan',
    icon: 'fa-utensils',
    iconBg: 'bg-green-100',
    iconColor: 'text-green-600',
    productCount: 43,
    status: 'active'
  },
  {
    id: '4',
    name: 'Buku & Edukasi',
    description: 'Buku, majalah, dan materi edukasi',
    icon: 'fa-book',
    iconBg: 'bg-purple-100',
    iconColor: 'text-purple-600',
    productCount: 28,
    status: 'active'
  },
  {
    id: '5',
    name: 'Rumah Tangga',
    description: 'Peralatan dan perlengkapan rumah tangga',
    icon: 'fa-home',
    iconBg: 'bg-yellow-100',
    iconColor: 'text-yellow-600',
    productCount: 15,
    status: 'inactive'
  },
  {
    id: '6',
    name: 'Olahraga',
    description: 'Peralatan olahraga dan fitness',
    icon: 'fa-dumbbell',
    iconBg: 'bg-red-100',
    iconColor: 'text-red-600',
    productCount: 22,
    status: 'active'
  }
])

const filteredCategories = computed(() => {
  let filtered = categories.value

  if (searchQuery.value) {
    filtered = filtered.filter(category =>
      category.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      category.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(category => category.status === statusFilter.value)
  }

  return filtered
})

const getStatusClass = (status: string) => {
  return status === 'active' 
    ? 'bg-green-100 text-green-800' 
    : 'bg-yellow-100 text-yellow-800'
}

const getStatusText = (status: string) => {
  return status === 'active' ? 'Aktif' : 'Tidak Aktif'
}

const getIconConfig = (iconName: string) => {
  const configs = {
    'fa-mobile-alt': { bg: 'bg-blue-100', color: 'text-blue-600' },
    'fa-tshirt': { bg: 'bg-pink-100', color: 'text-pink-600' },
    'fa-utensils': { bg: 'bg-green-100', color: 'text-green-600' },
    'fa-book': { bg: 'bg-purple-100', color: 'text-purple-600' },
    'fa-home': { bg: 'bg-yellow-100', color: 'text-yellow-600' },
    'fa-dumbbell': { bg: 'bg-red-100', color: 'text-red-600' },
    'fa-car': { bg: 'bg-gray-100', color: 'text-gray-600' },
    'fa-gamepad': { bg: 'bg-indigo-100', color: 'text-indigo-600' },
    'fa-music': { bg: 'bg-orange-100', color: 'text-orange-600' },
    'fa-camera': { bg: 'bg-teal-100', color: 'text-teal-600' },
    'fa-laptop': { bg: 'bg-cyan-100', color: 'text-cyan-600' },
    'fa-heart': { bg: 'bg-rose-100', color: 'text-rose-600' }
  }
  return configs[iconName as keyof typeof configs] || { bg: 'bg-gray-100', color: 'text-gray-600' }
}

const openAddModal = () => {
  isEditing.value = false
  editingId.value = null
  categoryForm.value = {
    name: '',
    description: '',
    icon: 'fa-mobile-alt',
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
    icon: category.icon,
    status: category.status
  }
  showModal.value = true
}

const closeModal = () => {
  showModal.value = false
  isEditing.value = false
  editingId.value = null
}

const selectIcon = (icon: { name: string; class: string }) => {
  categoryForm.value.icon = icon.name
}

const saveCategory = () => {
  const iconConfig = getIconConfig(categoryForm.value.icon)
  
  if (isEditing.value && editingId.value) {
    // Update existing category
    const index = categories.value.findIndex(c => c.id === editingId.value)
    if (index > -1) {
      categories.value[index] = {
        ...categories.value[index],
        name: categoryForm.value.name,
        description: categoryForm.value.description,
        icon: categoryForm.value.icon,
        iconBg: iconConfig.bg,
        iconColor: iconConfig.color,
        status: categoryForm.value.status
      }
    }
    alert('Kategori berhasil diupdate!')
  } else {
    // Add new category
    const newCategory: Category = {
      id: Date.now().toString(),
      name: categoryForm.value.name,
      description: categoryForm.value.description,
      icon: categoryForm.value.icon,
      iconBg: iconConfig.bg,
      iconColor: iconConfig.color,
      productCount: 0,
      status: categoryForm.value.status
    }
    categories.value.push(newCategory)
    stats.value.total++
    if (categoryForm.value.status === 'active') {
      stats.value.active++
    }
    alert('Kategori berhasil ditambahkan!')
  }
  
  closeModal()
}

const deleteCategory = (categoryId: string) => {
  if (confirm('Apakah Anda yakin ingin menghapus kategori ini?')) {
    const index = categories.value.findIndex(c => c.id === categoryId)
    if (index > -1) {
      const category = categories.value[index]
      categories.value.splice(index, 1)
      stats.value.total--
      if (category.status === 'active') {
        stats.value.active--
      }
      stats.value.totalProducts -= category.productCount
      alert('Kategori berhasil dihapus!')
    }
  }
}

onMounted(() => {
  console.log('Admin Category Management loaded')
})
</script>
