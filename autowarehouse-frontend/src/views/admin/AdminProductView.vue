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
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
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

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
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
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
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
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
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
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
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
              </div> -->

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
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
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
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

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

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
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

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
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
  selectedFiles.value = []
  existingImages.value = []
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

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
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

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter], () => {
  currentPage.value = 1 // Reset to first page when search/category filters change
  debouncedFetchProducts()
})

// Watch status filter separately since it's client-side only
watch(statusFilter, () => {
  currentPage.value = 1 // Reset to first page when status filter changes
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>
