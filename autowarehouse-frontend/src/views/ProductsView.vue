<template>
  <div class="bg-light min-h-screen">
    <UserNavbar />

    <!-- Breadcrumb -->
    <section class="bg-gray-100 py-4">
      <div class="container mx-auto px-4">
        <div class="flex items-center space-x-2 text-sm">
          <router-link to="/" class="text-primary hover:underline">Beranda</router-link>
          <i class="fa-solid fa-chevron-right text-gray-400"></i>
          <span class="text-gray-600">Produk</span>
        </div>
      </div>
    </section>

    <!-- Main Content -->
    <div class="container mx-auto px-4 py-8">
      <div class="flex gap-8 mb-8">
        
        <!-- Sidebar Filter -->
        <aside class="w-80 bg-white rounded-lg shadow-md p-6 h-fit">
          <h3 class="text-xl font-bold text-dark mb-6">Filter Produk</h3>
          
          <!-- Kategori Filter -->
          <div class="mb-6">
            <h4 class="font-semibold text-dark mb-3">Kategori</h4>
            <div v-if="categoriesLoading" class="text-sm text-gray-500">
              Loading categories...
            </div>
            <div v-else class="space-y-2">
              <label 
                v-for="category in availableCategories" 
                :key="category.id"
                class="flex items-center justify-between"
              >
                <div class="flex items-center">
                  <input 
                    type="checkbox" 
                    v-model="filters.categories" 
                    :value="category.id" 
                    class="mr-2 text-primary"
                  >
                  <span class="text-gray-700">{{ category.name }}</span>
                </div>
                <span 
                  v-if="categoryProductCounts[category.id]"
                  class="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-full"
                >
                  {{ categoryProductCounts[category.id] }}
                </span>
              </label>
            </div>
          </div>
          
          <!-- Harga Filter -->
          <div class="mb-6">
            <h4 class="font-semibold text-dark mb-3">Rentang Harga</h4>
            <div class="space-y-3">
              <div class="flex space-x-2">
                <input 
                  type="number" 
                  placeholder="Min" 
                  v-model="filters.priceMin"
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                >
                <input 
                  type="number" 
                  placeholder="Max" 
                  v-model="filters.priceMax"
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                >
              </div>
              <div class="space-y-2">
                <label class="flex items-center">
                  <input type="radio" v-model="filters.priceRange" value="0-5000000" class="mr-2 text-primary">
                  <span class="text-gray-700">&lt; Rp 5.000.000</span>
                </label>
                <label class="flex items-center">
                  <input type="radio" v-model="filters.priceRange" value="5000000-15000000" class="mr-2 text-primary">
                  <span class="text-gray-700">Rp 5.000.000 - Rp 15.000.000</span>
                </label>
                <label class="flex items-center">
                  <input type="radio" v-model="filters.priceRange" value="15000000+" class="mr-2 text-primary">
                  <span class="text-gray-700">&gt; Rp 15.000.000</span>
                </label>
              </div>
            </div>
          </div>
          
          
          <button 
            @click="applyFilters"
            class="w-full bg-primary text-white py-2 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Terapkan Filter
          </button>
        </aside>
        
        <!-- Product Listing -->
        <main class="flex-1">
          
          <!-- Search Bar -->
          <div class="bg-white rounded-lg shadow-md p-6 mb-6">
            <div class="relative">
              <input
                v-model="searchQuery"
                type="text"
                placeholder="Cari produk berdasarkan nama, deskripsi, atau SKU..."
                class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary focus:border-transparent"
                @input="debouncedSearch"
              >
              <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
              <div v-if="productsStore.isLoading" class="absolute right-3 top-1/2 transform -translate-y-1/2">
                <i class="fa-solid fa-spinner fa-spin text-gray-400"></i>
              </div>
            </div>
          </div>

          <!-- Header & Sort -->
          <div class="bg-white rounded-lg shadow-md p-6 mb-6">
            <div class="flex justify-between items-center mb-4">
              <div>
                <h1 class="text-2xl font-bold text-dark">Produk Elektronik</h1>
                <p class="text-gray-600">
                  Menampilkan {{ filteredProducts.length }} produk
                  <span v-if="searchQuery" class="text-primary">untuk "{{ searchQuery }}"</span>
                </p>
              </div>
              <div class="flex items-center space-x-4">
                <span class="text-gray-600">Urutkan:</span>
                <select 
                  v-model="sortBy"
                  class="border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-primary"
                >
                  <option value="name-asc">Nama (A-Z)</option>
                  <option value="name-desc">Nama (Z-A)</option>
                  <option value="price-asc">Harga (Rendah ke Tinggi)</option>
                  <option value="price-desc">Harga (Tinggi ke Rendah)</option>
                </select>
                <div class="flex border border-gray-300 rounded-lg">
                  <button 
                    @click="viewMode = 'grid'"
                    :class="viewMode === 'grid' ? 'bg-primary text-white' : 'hover:bg-gray-100'"
                    class="px-3 py-2 rounded-l-lg"
                  >
                    <i class="fa-solid fa-th-large"></i>
                  </button>
                  <button 
                    @click="viewMode = 'list'"
                    :class="viewMode === 'list' ? 'bg-primary text-white' : 'hover:bg-gray-100'"
                    class="px-3 py-2 rounded-r-lg"
                  >
                    <i class="fa-solid fa-list"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>
          
          <!-- Product Grid/List -->
          <div v-if="viewMode === 'grid'" class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            <ProductCard 
              v-for="product in filteredProducts" 
              :key="product.id"
              :product="product"
            />
          </div>

          <!-- Product List View -->
          <div v-else class="space-y-4">
            <ProductListItem 
              v-for="product in filteredProducts" 
              :key="product.id"
              :product="product"
            />
          </div>

          <!-- Pagination -->
          <div class="mt-8 flex justify-center">
            <div class="flex space-x-2">
              <button 
                v-for="page in totalPages" 
                :key="page"
                @click="currentPage = page"
                :class="currentPage === page ? 'bg-primary text-white' : 'bg-white text-gray-700 hover:bg-gray-100'"
                class="px-4 py-2 border border-gray-300 rounded-lg"
              >
                {{ page }}
              </button>
            </div>
          </div>
          
        </main>
      </div>
    </div>
    <Footer/>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useProductsStore } from '@/stores/products'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useWishlistStore } from '@/stores/wishlist'
import UserNavbar from '../components/UserNavbar.vue'
import ProductCard from '../components/ProductCard.vue'
import ProductListItem from '../components/ProductListItem.vue'
import { apiService, type ProductFilters, type Category, type Product } from '@/services/api'
import { debounce } from '@/utils/debounce'
import Footer from '../components/Footer.vue'

const router = useRouter()
const route = useRoute()
const productsStore = useProductsStore()
const cartStore = useCartStore()
const authStore = useAuthStore()
const wishlistStore = useWishlistStore()

// State
const searchQuery = ref('')
const sortBy = ref('name-asc')
const viewMode = ref(localStorage.getItem('products-view-mode') || 'grid')
const itemsPerPage = 12

// Categories
const availableCategories = ref<Category[]>([])
const categoriesLoading = ref(false)
const categoryProductCounts = ref<Record<number, number>>({})

// Filters
const filters = reactive({
  categories: [] as number[],
  priceRange: '',
  priceMin: null as number | null,
  priceMax: null as number | null
})

// Load categories function
const loadCategories = async () => {
  try {
    categoriesLoading.value = true
    const categories = await apiService.getCategories()
    availableCategories.value = categories.filter(cat => cat.isActive)
    
    // Load product counts for each category
    await loadCategoryProductCounts()
  } catch (error) {
    console.error('Error loading categories:', error)
  } finally {
    categoriesLoading.value = false
  }
}

// Load category product counts
const loadCategoryProductCounts = async () => {
  try {
    const counts: Record<number, number> = {}
    
    // Get product count for each category
    for (const category of availableCategories.value) {
      try {
        const products = await apiService.getProducts({
          category: category.id,
          status: 'active',
          size: 1 // We only need the count, not the actual products
        })
        
        // For now, we'll make a separate call to get the actual count
        // In a real implementation, the API should return total count in metadata
        const allProducts = await apiService.getProducts({
          category: category.id,
          status: 'active',
          size: 1000 // Get a large number to count all
        })
        
        counts[category.id] = allProducts.length
      } catch (error) {
        console.error(`Error loading count for category ${category.id}:`, error)
        counts[category.id] = 0
      }
    }
    
    categoryProductCounts.value = counts
  } catch (error) {
    console.error('Error loading category product counts:', error)
  }
}

// Parse price range
const parsePriceRange = () => {
  if (!filters.priceRange) return { min: undefined, max: undefined }
  
  if (filters.priceRange === '0-5000000') {
    return { min: 0, max: 5000000 }
  } else if (filters.priceRange === '5000000-15000000') {
    return { min: 5000000, max: 15000000 }
  } else if (filters.priceRange === '15000000+') {
    return { min: 15000000, max: undefined }
  }
  
  return { min: undefined, max: undefined }
}

// Parse sorting parameter
const parseSortBy = () => {
  switch (sortBy.value) {
    case 'name-asc':
      return { sortBy: 'name', sortOrder: 'asc' }
    case 'name-desc':
      return { sortBy: 'name', sortOrder: 'desc' }
    case 'price-asc':
      return { sortBy: 'price', sortOrder: 'asc' }
    case 'price-desc':
      return { sortBy: 'price', sortOrder: 'desc' }
    default:
      return { sortBy: 'name', sortOrder: 'asc' }
  }
}

// State for combined products when multiple categories are selected
const combinedProducts = ref<Product[]>([])
const isMultiCategoryMode = ref(false)

// Load products function
const loadProducts = async () => {
  try {
    const priceRange = parsePriceRange()
    const sorting = parseSortBy()
    
    // Handle multiple categories by making multiple API calls and combining results
    if (filters.categories.length > 1) {
      isMultiCategoryMode.value = true
      const allProducts: Product[] = []
      const productIds = new Set<number>() // To avoid duplicates
      
      for (const categoryId of filters.categories) {
      const productFilters: ProductFilters = {
        search: searchQuery.value || undefined,
        category: categoryId,
        minPrice: filters.priceMin || priceRange.min,
        maxPrice: filters.priceMax || priceRange.max,
        sortBy: sorting.sortBy,
        sortOrder: sorting.sortOrder,
        status: 'active', // Only show active products to customers
        page: 1, // Always get first page for each category
        size: 100 // Get more items to ensure we have enough
      }
        
        try {
          await productsStore.fetchProducts(productFilters)
          // Add unique products to our combined list
          productsStore.products.forEach(product => {
            if (!productIds.has(product.id)) {
              productIds.add(product.id)
              allProducts.push(product)
            }
          })
        } catch (error) {
          console.error(`Error loading products for category ${categoryId}:`, error)
        }
      }
      
      // Sort the combined results
      if (sorting.sortBy === 'name') {
        allProducts.sort((a, b) => {
          const comparison = a.name.localeCompare(b.name)
          return sorting.sortOrder === 'asc' ? comparison : -comparison
        })
      } else if (sorting.sortBy === 'price') {
        allProducts.sort((a, b) => {
          const comparison = a.price - b.price
          return sorting.sortOrder === 'asc' ? comparison : -comparison
        })
      }
      
      // Store combined results
      combinedProducts.value = allProducts
      
    } else {
      // Single category or no category - use normal API call
      isMultiCategoryMode.value = false
      const productFilters: ProductFilters = {
        search: searchQuery.value || undefined,
        category: filters.categories.length > 0 ? filters.categories[0] : undefined,
        minPrice: filters.priceMin || priceRange.min,
        maxPrice: filters.priceMax || priceRange.max,
        sortBy: sorting.sortBy,
        sortOrder: sorting.sortOrder,
        status: 'active', // Only show active products to customers
        page: productsStore.currentPage,
        size: itemsPerPage
      }
      
      await productsStore.fetchProducts(productFilters)
    }
  } catch (error) {
    console.error('Error loading products:', error)
  }
}

// Debounced search function
const debouncedSearch = debounce(() => {
  loadProducts()
}, 500)

// Initialize on mount
onMounted(async () => {
  await loadCategories()
  
  // Check for category parameter in URL and set filter
  const categoryParam = route.query.category
  if (categoryParam) {
    const categoryId = parseInt(categoryParam as string)
    if (!isNaN(categoryId)) {
      filters.categories = [categoryId]
    }
  }
  
  await loadProducts()
  await wishlistStore.loadWishlist()
})

// Watch for filter changes (excluding searchQuery since it's handled by debounced search)
watch([filters, sortBy], () => {
  loadProducts()
}, { deep: true })

// Watch for search query changes with debounce
watch(searchQuery, () => {
  debouncedSearch()
})

// Watch for view mode changes and save to localStorage
watch(viewMode, (newMode) => {
  localStorage.setItem('products-view-mode', newMode)
})

// Watch for route changes to handle category parameter updates
watch(() => route.query.category, (newCategoryParam) => {
  if (newCategoryParam) {
    const categoryId = parseInt(newCategoryParam as string)
    if (!isNaN(categoryId)) {
      filters.categories = [categoryId]
    }
  } else {
    // Clear category filter if no category parameter
    filters.categories = []
  }
})

// Computed
const filteredProducts = computed(() => {
  return isMultiCategoryMode.value ? combinedProducts.value : productsStore.products
})

const totalPages = computed(() => {
  return productsStore.totalPages
})

const currentPage = computed({
  get: () => productsStore.currentPage,
  set: (value: number) => {
    productsStore.setPage(value)
    loadProducts()
  }
})

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getBadgeClass = (badge: string) => {
  switch (badge) {
    case 'Sale':
      return 'bg-red-500 text-white'
    case 'Populer':
      return 'bg-accent text-white'
    case 'Gaming':
      return 'bg-primary text-white'
    default:
      return 'bg-gray-500 text-white'
  }
}

const getStockClass = (stock: number) => {
  if (stock > 10) return 'bg-green-100 text-green-800'
  if (stock > 5) return 'bg-yellow-100 text-yellow-800'
  return 'bg-red-100 text-red-800'
}


const applyFilters = () => {
  currentPage.value = 1
  // Filters are automatically applied through computed property
}
</script>

<style scoped>
/* Line clamp utility for text truncation */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* Ensure proper spacing and alignment in list view */
.flex-shrink-0 {
  flex-shrink: 0;
}

/* Smooth transitions for view mode toggle */
.transition-shadow {
  transition: box-shadow 0.3s ease;
}

/* Responsive adjustments for list view */
@media (max-width: 768px) {
  .w-48 {
    width: 120px;
  }
  
  .h-32 {
    height: 80px;
  }
}
</style>
