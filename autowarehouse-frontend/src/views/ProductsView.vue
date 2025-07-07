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
      <div class="flex gap-8">
        
        <!-- Sidebar Filter -->
        <aside class="w-80 bg-white rounded-lg shadow-md p-6 h-fit">
          <h3 class="text-xl font-bold text-dark mb-6">Filter Produk</h3>
          
          <!-- Kategori Filter -->
          <div class="mb-6">
            <h4 class="font-semibold text-dark mb-3">Kategori</h4>
            <div class="space-y-2">
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.categories" value="smartphone" class="mr-2 text-primary">
                <span class="text-gray-700">Smartphone (45)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.categories" value="laptop" class="mr-2 text-primary">
                <span class="text-gray-700">Laptop (32)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.categories" value="tablet" class="mr-2 text-primary">
                <span class="text-gray-700">Tablet (18)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.categories" value="kamera" class="mr-2 text-primary">
                <span class="text-gray-700">Kamera (24)</span>
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
          
          <!-- Brand Filter -->
          <div class="mb-6">
            <h4 class="font-semibold text-dark mb-3">Brand</h4>
            <div class="space-y-2">
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.brands" value="apple" class="mr-2 text-primary">
                <span class="text-gray-700">Apple (12)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.brands" value="samsung" class="mr-2 text-primary">
                <span class="text-gray-700">Samsung (15)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.brands" value="dell" class="mr-2 text-primary">
                <span class="text-gray-700">Dell (8)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.brands" value="sony" class="mr-2 text-primary">
                <span class="text-gray-700">Sony (6)</span>
              </label>
            </div>
          </div>
          
          <!-- Rating Filter -->
          <div class="mb-6">
            <h4 class="font-semibold text-dark mb-3">Rating</h4>
            <div class="space-y-2">
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.ratings" value="5" class="mr-2 text-primary">
                <div class="flex text-yellow-400 mr-2">
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                </div>
                <span class="text-gray-700">(4.5+)</span>
              </label>
              <label class="flex items-center">
                <input type="checkbox" v-model="filters.ratings" value="4" class="mr-2 text-primary">
                <div class="flex text-yellow-400 mr-2">
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-solid fa-star"></i>
                  <i class="fa-regular fa-star"></i>
                </div>
                <span class="text-gray-700">(4.0+)</span>
              </label>
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
          
          <!-- Header & Sort -->
          <div class="bg-white rounded-lg shadow-md p-6 mb-6">
            <div class="flex justify-between items-center mb-4">
              <div>
                <h1 class="text-2xl font-bold text-dark">Produk Elektronik</h1>
                <p class="text-gray-600">Menampilkan {{ filteredProducts.length }} produk</p>
              </div>
              <div class="flex items-center space-x-4">
                <span class="text-gray-600">Urutkan:</span>
                <select 
                  v-model="sortBy"
                  class="border border-gray-300 rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-primary"
                >
                  <option value="popular">Terpopuler</option>
                  <option value="price-low">Harga Terendah</option>
                  <option value="price-high">Harga Tertinggi</option>
                  <option value="newest">Terbaru</option>
                  <option value="rating">Rating Tertinggi</option>
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
          
          <!-- Product Grid -->
          <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            
            <div 
              v-for="product in filteredProducts" 
              :key="product.id"
              class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-xl transition-shadow"
            >
              <div class="relative">
                <img 
                  :src="product.imageUrls?.[0] || '/placeholder-product.jpg'" 
                  :alt="product.name"
                  class="w-full h-48 object-cover"
                >
                <button 
                  @click="toggleWishlist(product.id)"
                  class="absolute top-3 right-3 w-8 h-8 bg-white rounded-full flex items-center justify-center shadow-md hover:bg-gray-100"
                >
                  <i 
                    class="fa-regular fa-heart text-gray-600"
                  ></i>
                </button>
                <span 
                  v-if="product.isOnSale"
                  class="bg-red-500 text-white absolute top-3 left-3 px-2 py-1 rounded text-xs font-semibold"
                >
                  Sale
                </span>
              </div>
              <div class="p-4">
                <h3 class="font-semibold text-dark mb-2">{{ product.name }}</h3>
                <p class="text-sm text-gray-600 mb-2">{{ product.description }}</p>
                <div class="flex items-center mb-2">
                  <div class="flex text-yellow-400 text-sm mr-2">
                    <i 
                      v-for="i in 5" 
                      :key="i"
                      :class="i <= product.rating ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                    ></i>
                  </div>
                  <span class="text-sm text-gray-600">({{ product.rating }}) ulasan</span>
                </div>
                <div class="flex justify-between items-center mb-3">
                  <div>
                    <span class="text-lg font-bold text-primary">Rp {{ formatPrice(product.price) }}</span>
                    <span 
                      v-if="product.originalPrice"
                      class="text-sm text-gray-500 line-through ml-2"
                    >
                      Rp {{ formatPrice(product.originalPrice) }}
                    </span>
                  </div>
                  <span 
                    :class="getStockClass(product.stockQuantity)"
                    class="text-xs px-2 py-1 rounded"
                  >
                    Stok: {{ product.stockQuantity }}
                  </span>
                </div>
                <div class="flex space-x-2">
                  <button 
                    @click="addToCart(product.id)"
                    class="flex-1 bg-primary text-white py-2 rounded-lg hover:bg-blue-700 transition-colors text-sm"
                  >
                    <i class="fa-solid fa-shopping-cart mr-1"></i>Keranjang
                  </button>
                  <button 
                    @click="viewProduct(product.id)"
                    class="px-3 py-2 border border-primary text-primary rounded-lg hover:bg-primary hover:text-white transition-colors"
                  >
                    <i class="fa-solid fa-eye"></i>
                  </button>
                </div>
              </div>
            </div>
            
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useProductsStore } from '@/stores/products'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import UserNavbar from '../components/UserNavbar.vue'
import type { ProductFilters } from '@/services/api'

const router = useRouter()
const productsStore = useProductsStore()
const cartStore = useCartStore()
const authStore = useAuthStore()

// State
const searchQuery = ref('')
const sortBy = ref('popular')
const viewMode = ref('grid')
const itemsPerPage = 12

// Filters
const filters = reactive({
  categories: [] as string[],
  brands: [] as string[],
  ratings: [] as string[],
  priceRange: '',
  priceMin: null as number | null,
  priceMax: null as number | null
})

// Initialize on mount
onMounted(async () => {
  await loadProducts()
})

// Watch for filter changes
watch([filters, sortBy, searchQuery], () => {
  loadProducts()
}, { deep: true })

// Load products function
const loadProducts = async () => {
  try {
    const productFilters: ProductFilters = {
      search: searchQuery.value || undefined,
      brand: filters.brands.length > 0 ? filters.brands.join(',') : undefined,
      minPrice: filters.priceMin || undefined,
      maxPrice: filters.priceMax || undefined,
      onSale: filters.priceRange === 'sale' ? true : undefined,
      page: productsStore.currentPage,
      size: itemsPerPage
    }
    
    await productsStore.fetchProducts(productFilters)
  } catch (error) {
    console.error('Error loading products:', error)
  }
}

// Computed
const filteredProducts = computed(() => {
  return productsStore.products
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

const toggleWishlist = (productId: number) => {
  console.log('Toggle wishlist:', productId)
  // TODO: Implement wishlist functionality
}

const addToCart = async (productId: number) => {
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    await cartStore.addToCart(productId, 1)
    console.log('Added to cart:', productId)
  } catch (error) {
    console.error('Error adding to cart:', error)
  }
}

const viewProduct = (productId: number) => {
  router.push(`/products/${productId}`)
}

const applyFilters = () => {
  currentPage.value = 1
  // Filters are automatically applied through computed property
}
</script>

<style scoped>
/* Custom styles if needed */
</style>
