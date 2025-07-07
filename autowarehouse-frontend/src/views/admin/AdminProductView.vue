<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option value="elektronik">Elektronik</option>
                  <option value="fashion">Fashion</option>
                  <option value="makanan">Makanan</option>
                  <option value="buku">Buku</option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <img :src="product.image" :alt="product.name" class="w-12 h-12 rounded-lg object-cover mr-4">
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.category }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stock }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(product.status)"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(product.status) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                    >
                      <option value="">Pilih Kategori</option>
                      <option value="elektronik">Elektronik</option>
                      <option value="fashion">Fashion</option>
                      <option value="makanan">Makanan & Minuman</option>
                      <option value="buku">Buku & Media</option>
                      <option value="olahraga">Olahraga</option>
                      <option value="kesehatan">Kesehatan & Kecantikan</option>
                      <option value="otomotif">Otomotif</option>
                      <option value="rumah">Rumah & Taman</option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">Upload Gambar Produk *</label>
                    <div class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100">
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input type="file" class="hidden" accept="image/*" multiple>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors"
                >
                  Simpan sebagai Draft
                </button>
                <button 
                  type="submit" 
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors"
                >
                  Publikasikan Produk
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'

interface Specification {
  name: string
  value: string
}

interface Product {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<string | null>(null)

const products = ref<Product[]>([
  {
    id: 'IPH15PRO001',
    name: 'iPhone 15 Pro',
    sku: 'IPH15PRO001',
    category: 'Elektronik',
    price: 18999000,
    stock: 25,
    status: 'active',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/6687b03202-5912a40fe3b90038b9ed.png'
  },
  {
    id: 'KCS001',
    name: 'Kaos Casual Premium',
    sku: 'KCS001',
    category: 'Fashion',
    price: 149000,
    stock: 0,
    status: 'out_of_stock',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/4d04b8d095-8cfe8cd073db46d980c0.png'
  },
  {
    id: 'KAP500G',
    name: 'Kopi Arabica Premium',
    sku: 'KAP500G',
    category: 'Makanan',
    price: 85000,
    stock: 150,
    status: 'active',
    image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/fef1ee1be2-02b82c0ebfa8dfe6e145.png'
  }
])

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    filtered = filtered.filter(product => 
      product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      product.sku.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(product => 
      product.category.toLowerCase() === categoryFilter.value.toLowerCase()
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(product => product.status === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => products.value.length)
const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = (product: Product) => {
  productForm.value = {
    name: product.name,
    category: product.category,
    sku: product.sku,
    price: product.price,
    stock: product.stock,
    description: product.description || '',
    specifications: product.specifications || [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: product.status === 'out_of_stock' ? 'active' : product.status as any
  }
  editingProductId.value = product.id
  showAddForm.value = true
  isEditing.value = true
}

const deleteProduct = (productId: string) => {
  if (confirm('Apakah Anda yakin ingin menghapus produk ini?')) {
    const index = products.value.findIndex(p => p.id === productId)
    if (index > -1) {
      products.value.splice(index, 1)
    }
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

const saveProduct = () => {
  if (isEditing.value && editingProductId.value) {
    // Update existing product
    const index = products.value.findIndex(p => p.id === editingProductId.value)
    if (index > -1) {
      products.value[index] = {
        ...products.value[index],
        name: productForm.value.name,
        category: productForm.value.category,
        sku: productForm.value.sku,
        price: productForm.value.price,
        stock: productForm.value.stock,
        description: productForm.value.description,
        specifications: productForm.value.specifications.filter(spec => spec.name && spec.value),
        status: productForm.value.stock === 0 ? 'out_of_stock' : productForm.value.status as any
      }
    }
  } else {
    // Add new product
    const newProduct: Product = {
      id: productForm.value.sku || `PRD${Date.now()}`,
      name: productForm.value.name,
      sku: productForm.value.sku || `PRD${Date.now()}`,
      category: productForm.value.category,
      price: productForm.value.price,
      stock: productForm.value.stock,
      status: productForm.value.stock === 0 ? 'out_of_stock' : productForm.value.status as any,
      image: 'https://storage.googleapis.com/uxpilot-auth.appspot.com/6687b03202-5912a40fe3b90038b9ed.png',
      description: productForm.value.description,
      specifications: productForm.value.specifications.filter(spec => spec.name && spec.value)
    }
    products.value.push(newProduct)
  }
  
  goBack()
  alert(isEditing.value ? 'Produk berhasil diupdate!' : 'Produk berhasil ditambahkan!')
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

onMounted(() => {
  console.log('Admin Product Management loaded')
})
</script>
