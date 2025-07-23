<template>
  <div class="bg-light min-h-screen">
    <!-- Dynamic navbar based on authentication status -->
    <UserNavbar v-if="authStore.isAuthenticated" />
    <GuestNavbar v-else />

    <!-- Hero Banner -->
    <section class="bg-gradient-to-br from-primary via-secondary to-indigo-600 text-white h-[600px] flex items-center relative overflow-hidden">
      <div class="absolute inset-0 bg-black opacity-10"></div>
      <div class="container mx-auto px-4 relative z-10">
        <div class="grid md:grid-cols-2 gap-12 items-center">
          <div>
            <div class="inline-block bg-accent/20 text-accent px-4 py-2 rounded-full text-sm font-semibold mb-4">
              🔥 Flash Sale Hari Ini
            </div>
            <h1 class="text-6xl font-bold mb-6 leading-tight">Hardware & Elektronik Terlengkap</h1>
            <p class="text-xl mb-8 opacity-90 leading-relaxed">
              Dapatkan komponen PC, RAM, SSD, dan aksesoris gaming terbaik dengan harga terjangkau. Plus fitur auction untuk deals eksklusif!
            </p>
            <div class="flex flex-col sm:flex-row gap-4">
              <router-link 
                to="/products" 
                class="bg-accent text-white px-8 py-4 rounded-xl font-semibold hover:bg-yellow-600 transition-all transform hover:scale-105 shadow-lg inline-flex items-center justify-center"
              >
                <i class="fa-solid fa-shopping-bag mr-2"></i>Belanja Sekarang
              </router-link>
            </div>
          </div>
          <div class="relative">
            <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-8">
              <img 
                class="w-full h-80 object-cover rounded-xl" 
                src="https://images.unsplash.com/photo-1593640408182-31c70c8268f5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80" 
                alt="Gaming Setup"
              >
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Quick Stats -->
    <section class="py-12 bg-white border-b">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
          <div>
            <div class="text-3xl font-bold text-primary mb-2">{{ formatProductCount(activeProductCount) }}+</div>
            <div class="text-gray-600">Produk Tersedia</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">24/7</div>
            <div class="text-gray-600">Customer Support</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">1 Hari</div>
            <div class="text-gray-600">Pengiriman Express</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">100%</div>
            <div class="text-gray-600">Garansi Resmi</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Kategori Produk -->
    <section class="py-20 bg-gray-50">
      <div class="container mx-auto px-4">
        <div class="flex justify-between items-center mb-16">
          <div class="text-center flex-1">
            <h2 class="text-4xl font-bold text-dark mb-4">Kategori Produk</h2>
            <p class="text-xl text-gray-600">Temukan komponen dan aksesoris PC terbaik</p>
          </div>
          
        </div>
        
        <!-- Loading state -->
        <div v-if="isLoadingCategories" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
          <div v-for="i in 6" :key="i" class="bg-white p-8 rounded-2xl shadow-md animate-pulse">
            <div class="bg-gray-200 w-16 h-16 rounded-xl mx-auto mb-4"></div>
            <div class="bg-gray-200 h-4 rounded mx-auto mb-2 w-20"></div>
            <div class="bg-gray-200 h-3 rounded mx-auto w-16"></div>
          </div>
        </div>
        
        <!-- Categories from database -->
        <div v-else-if="categories.length > 0" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
          <router-link 
            v-for="category in categories" 
            :key="category.id"
            :to="`/products?category=${category.id}`"
            class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group block"
          >
            <!-- Use imageUrl if available, otherwise use icon -->
            <div v-if="category.imageUrl" class="w-16 h-16 rounded-xl mx-auto mb-4 overflow-hidden">
              <img 
                :src="category.imageUrl" 
                :alt="category.name" 
                class="w-full h-full object-cover"
                @error="handleCategoryImageError"
              >
            </div>
            <div v-else :class="[getCategoryIcon(category).bgColor, 'w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors']">
              <i :class="['fa-solid', getCategoryIcon(category).icon, 'text-2xl', getCategoryIcon(category).color, 'group-hover:text-white']"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">{{ category.name }}</h3>
            <p class="text-sm text-gray-500 text-center">{{ category.description || 'Berbagai pilihan terbaik' }}</p>
          </router-link>
        </div>
        
        <!-- Fallback if no categories -->
        <div v-else class="text-center py-12">
          <div class="text-gray-500 mb-4">
            <i class="fa-solid fa-box-open text-4xl"></i>
          </div>
          <p class="text-gray-600">Kategori produk akan segera tersedia</p>
        </div>
      </div>
      <div class="text-center mt-8">
        <router-link 
          to="/products" 
          class="bg-primary text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors inline-flex items-center"
        >
          <span class="mr-2">Lihat Semua Kategori</span>
          <i class="fa-solid fa-arrow-right"></i>
        </router-link>
      </div>
    </section>

    <!-- Flash Sale -->
    <section class="py-16 bg-gradient-to-r from-red-500 to-pink-500 text-white">
      <div class="container mx-auto px-4">
        <div class="text-center mb-12">
          <h2 class="text-4xl font-bold mb-4">⚡ Hot Items</h2>
          <p class="text-xl opacity-90">Penawaran terbatas, buruan sebelum kehabisan!</p>
          <div class="inline-flex items-center space-x-4 mt-4 bg-white/20 rounded-xl px-6 py-3">
            <span class="text-lg font-semibold">Produk yang paling banyak dibeli</span>
            <!-- <div class="flex space-x-2">
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.hours }}</div>
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.minutes }}</div>
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.seconds }}</div>
            </div> -->
          </div>
        </div>
        <!-- Loading state for products -->
        <div v-if="isLoadingProducts" class="grid md:grid-cols-3 gap-6">
          <div v-for="i in 3" :key="i" class="bg-white rounded-2xl p-6 shadow-xl animate-pulse">
            <div class="bg-gray-200 w-full h-40 rounded-xl mb-4"></div>
            <div class="bg-gray-200 h-4 rounded mb-2 w-3/4"></div>
            <div class="bg-gray-200 h-6 rounded mb-3 w-1/2"></div>
            <div class="bg-gray-200 h-8 rounded w-full"></div>
          </div>
        </div>
        
        <!-- Products from database -->
        <div v-else-if="popularProducts.length > 0" class="grid md:grid-cols-3 gap-6">
          <router-link 
            v-for="product in popularProducts" 
            :key="product.id"
            :to="`/products/${product.id}`"
            class="bg-white rounded-2xl p-6 text-dark shadow-xl hover:shadow-2xl transition-all transform hover:scale-105 block"
          >
            <div class="relative mb-4">
              <img 
                :src="getProductImage(product)" 
                :alt="product.name" 
                class="w-full h-40 object-cover rounded-xl"
                @error="handleImageError"
              >
              <div v-if="product.isOnSale" class="absolute top-2 left-2 bg-red-500 text-white px-2 py-1 rounded-lg text-sm font-bold">
                Sale
              </div>
            </div>
            <h3 class="font-bold mb-2 line-clamp-2">{{ product.name }}</h3>
            <div class="flex items-center space-x-2 mb-3">
              <span class="text-2xl font-bold text-primary">{{ formatPrice(product.price) }}</span>
              <span v-if="product.originalPrice && product.originalPrice > product.price" class="text-gray-500 line-through text-sm">
                {{ formatPrice(product.originalPrice) }}
              </span>
            </div>
            <div class="flex items-center justify-between">
              <div class="flex items-center text-yellow-500">
                <i class="fa-solid fa-star text-sm"></i>
                <span class="ml-1 text-sm text-gray-600">{{ formatRating(product.rating || 0) }} ({{ product.reviewCount || 0 }})</span>
              </div>
              <span class="text-sm text-gray-500">{{ product.categoryName }}</span>
            </div>
          </router-link>
        </div>
        
        <!-- Fallback products -->
        <!-- <div v-else class="grid md:grid-cols-3 gap-6">
          <div v-for="product in flashSaleProducts" :key="product.id" class="bg-white rounded-2xl p-6 text-dark shadow-xl">
            <div class="relative mb-4">
              <img :src="product.image" :alt="product.name" class="w-full h-40 object-cover rounded-xl">
              <div class="absolute top-2 left-2 bg-red-500 text-white px-2 py-1 rounded-lg text-sm font-bold">
                -{{ product.discount }}%
              </div>
            </div>
            <h3 class="font-bold mb-2">{{ product.name }}</h3>
            <div class="flex items-center space-x-2 mb-3">
              <span class="text-2xl font-bold text-primary">{{ formatPrice(product.salePrice) }}</span>
              <span class="text-gray-500 line-through">{{ formatPrice(product.originalPrice) }}</span>
            </div>
            <button class="w-full bg-primary text-white py-2 rounded-xl hover:bg-blue-700 transition-colors">
              Beli Sekarang
            </button>
          </div>
        </div> -->
        
        <div class="text-center mt-8">
          <router-link to="/products" class="bg-secondary text-white px-8 py-3 rounded-lg hover:bg-blue-600 transition-colors inline-block">
            Lihat Semua Produk
          </router-link>
        </div>
      </div>
    </section>

    <!-- Latest Reviews -->
    <section class="py-16 bg-gray-50">
      <div class="container mx-auto px-4">
        <div class="text-center mb-12">
          <h2 class="text-4xl font-bold text-dark mb-4">💬 Apa Kata Pelanggan</h2>
          <p class="text-xl text-gray-600">Testimoni terbaru dari pelanggan yang puas</p>
        </div>
        
        <!-- Loading state for reviews -->
        <div v-if="isLoadingReviews" class="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
          <div v-for="i in 6" :key="i" class="bg-white rounded-2xl p-6 shadow-md animate-pulse">
            <div class="flex items-center mb-4">
              <div class="bg-gray-200 w-12 h-12 rounded-full mr-3"></div>
              <div class="flex-1">
                <div class="bg-gray-200 h-4 rounded mb-2 w-3/4"></div>
                <div class="bg-gray-200 h-3 rounded w-1/2"></div>
              </div>
            </div>
            <div class="bg-gray-200 h-20 rounded mb-4"></div>
            <div class="bg-gray-200 h-4 rounded mb-2"></div>
            <div class="bg-gray-200 h-3 rounded w-2/3"></div>
          </div>
        </div>
        
        <!-- Reviews from database -->
        <div v-else-if="latestReviews.length > 0" class="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div 
            v-for="review in latestReviews" 
            :key="review.id"
            class="bg-white rounded-2xl p-8 shadow-md hover:shadow-lg transition-shadow min-h-[280px]"
          >
            <!-- User info and product -->
            <div class="flex items-start mb-4">
              <div class="w-12 h-12 bg-primary rounded-full flex items-center justify-center text-white font-bold mr-4 flex-shrink-0">
                {{ getInitials(review.userName) }}
              </div>
              <div class="flex-1 min-w-0">
                <h4 class="font-semibold text-dark mb-1">{{ review.userName }}</h4>
                <p class="text-sm text-gray-500 break-words leading-tight">{{ review.productName }}</p>
              </div>
            </div>
            
            <!-- Rating -->
            <div class="flex items-center mb-4">
              <div class="flex text-yellow-500 mr-2">
                <i v-for="star in 5" :key="star" :class="[
                  'fa-star text-sm',
                  star <= review.rating ? 'fa-solid' : 'fa-regular'
                ]"></i>
              </div>
              <span class="text-sm text-gray-600">{{ formatRating(review.rating) }}</span>
            </div>
            
            <!-- Review title and comment -->
            <div class="mb-4 flex-1">
              <h5 class="font-semibold text-dark mb-2 line-clamp-2 leading-tight">{{ review.title }}</h5>
              <p class="text-gray-600 text-sm line-clamp-4 leading-relaxed">{{ review.comment }}</p>
            </div>
            
            <!-- Verified purchase badge -->
            <div class="flex items-center justify-between mt-auto pt-2">
              <span v-if="review.isVerifiedPurchase" class="inline-flex items-center text-xs text-green-600 bg-green-100 px-2 py-1 rounded-full flex-shrink-0">
                <i class="fa-solid fa-check-circle mr-1"></i>
                Verified
              </span>
              <span class="text-xs text-gray-400 ml-2">{{ formatDate(review.createdAt) }}</span>
            </div>
          </div>
        </div>
        
        <!-- Fallback if no reviews -->
        <div v-else class="text-center py-12">
          <div class="text-gray-500 mb-4">
            <i class="fa-solid fa-comments text-4xl"></i>
          </div>
          <p class="text-gray-600">Belum ada review dari pelanggan</p>
        </div>
      </div>
    </section>

    <!-- Cara Kerja -->
    <section class="py-16 bg-white">
      <div class="container mx-auto px-4">
        <div class="text-center mb-12">
          <h2 class="text-3xl font-bold text-dark mb-4">Cara Kerja</h2>
          <p class="text-gray-600">Proses belanja yang mudah dan aman</p>
        </div>
        <div class="grid md:grid-cols-3 gap-8">
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-user-plus text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">1. Daftar</h3>
            <p class="text-gray-600">Buat akun Anda untuk mulai berbelanja</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-search text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">2. Pilih Produk</h3>
            <p class="text-gray-600">Jelajahi berbagai kategori dan temukan produk yang Anda inginkan</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-shopping-cart text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">3. Beli & Nikmati</h3>
            <p class="text-gray-600">Checkout dengan mudah dan nikmati produk berkualitas tinggi</p>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="py-16 bg-gradient-to-r from-primary to-secondary text-white">
      <div class="container mx-auto px-4 text-center">
        <h2 class="text-4xl font-bold mb-4">Siap Memulai Belanja?</h2>
        <p class="text-xl mb-8 opacity-90">Bergabunglah dengan ribuan pengguna yang telah merasakan pengalaman belanja terbaik</p>
        
        <!-- Show different buttons based on authentication status -->
        <div v-if="!authStore.isAuthenticated" class="flex justify-center space-x-4">
          <router-link to="/login" class="bg-accent text-white px-8 py-3 rounded-lg font-semibold hover:bg-yellow-600 transition-colors">
            Daftar Gratis
          </router-link>
          <router-link to="/products" class="border-2 border-white text-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-primary transition-colors">
            Mulai Belanja
          </router-link>
        </div>
        
        <!-- Show only "Mulai Belanja" button for authenticated users -->
        <div v-else class="flex justify-center">
          <router-link to="/products" class="bg-accent text-white px-8 py-3 rounded-lg font-semibold hover:bg-yellow-600 transition-colors">
            Mulai Belanja
          </router-link>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { apiService, type Category } from '@/services/api'
import UserNavbar from '../components/UserNavbar.vue'
import GuestNavbar from '../components/GuestNavbar.vue'
import Footer from '../components/Footer.vue'

const authStore = useAuthStore()

// Product count from database
const activeProductCount = ref(0)

// Categories from database
const categories = ref<Category[]>([])
const isLoadingCategories = ref(true)

// Popular products from database
const popularProducts = ref<any[]>([])
const isLoadingProducts = ref(true)

// Latest reviews from database
const latestReviews = ref<any[]>([])
const isLoadingReviews = ref(true)

// Category icons mapping
const categoryIcons: { [key: string]: { icon: string; color: string; bgColor: string } } = {
  'processor': { icon: 'fa-microchip', color: 'text-blue-600', bgColor: 'bg-blue-100' },
  'cpu': { icon: 'fa-microchip', color: 'text-blue-600', bgColor: 'bg-blue-100' },
  'ram': { icon: 'fa-memory', color: 'text-green-600', bgColor: 'bg-green-100' },
  'memory': { icon: 'fa-memory', color: 'text-green-600', bgColor: 'bg-green-100' },
  'storage': { icon: 'fa-hard-drive', color: 'text-purple-600', bgColor: 'bg-purple-100' },
  'ssd': { icon: 'fa-hard-drive', color: 'text-purple-600', bgColor: 'bg-purple-100' },
  'hdd': { icon: 'fa-hard-drive', color: 'text-purple-600', bgColor: 'bg-purple-100' },
  'vga': { icon: 'fa-display', color: 'text-red-600', bgColor: 'bg-red-100' },
  'gpu': { icon: 'fa-display', color: 'text-red-600', bgColor: 'bg-red-100' },
  'graphics': { icon: 'fa-display', color: 'text-red-600', bgColor: 'bg-red-100' },
  'gaming': { icon: 'fa-gamepad', color: 'text-yellow-600', bgColor: 'bg-yellow-100' },
  'keyboard': { icon: 'fa-keyboard', color: 'text-yellow-600', bgColor: 'bg-yellow-100' },
  'mouse': { icon: 'fa-computer-mouse', color: 'text-yellow-600', bgColor: 'bg-yellow-100' },
  'audio': { icon: 'fa-headset', color: 'text-indigo-600', bgColor: 'bg-indigo-100' },
  'headset': { icon: 'fa-headset', color: 'text-indigo-600', bgColor: 'bg-indigo-100' },
  'speaker': { icon: 'fa-volume-high', color: 'text-indigo-600', bgColor: 'bg-indigo-100' },
  'motherboard': { icon: 'fa-microchip', color: 'text-teal-600', bgColor: 'bg-teal-100' },
  'power': { icon: 'fa-plug', color: 'text-orange-600', bgColor: 'bg-orange-100' },
  'psu': { icon: 'fa-plug', color: 'text-orange-600', bgColor: 'bg-orange-100' },
  'case': { icon: 'fa-computer', color: 'text-gray-600', bgColor: 'bg-gray-100' },
  'cooling': { icon: 'fa-fan', color: 'text-cyan-600', bgColor: 'bg-cyan-100' },
  'monitor': { icon: 'fa-desktop', color: 'text-pink-600', bgColor: 'bg-pink-100' }
}

// Get category icon based on name or slug
const getCategoryIcon = (category: Category) => {
  const slug = category.slug.toLowerCase()
  const name = category.name.toLowerCase()
  
  // Try to match by slug first, then by name
  for (const [key, value] of Object.entries(categoryIcons)) {
    if (slug.includes(key) || name.includes(key)) {
      return value
    }
  }
  
  // Default icon
  return { icon: 'fa-cube', color: 'text-gray-600', bgColor: 'bg-gray-100' }
}

// Initialize auth and load data on component mount
onMounted(async () => {
  authStore.initializeAuth()
  await Promise.all([
    loadActiveProductCount(),
    loadCategories(),
    loadPopularProducts(),
    loadLatestReviews()
  ])
})

// Load active product count from database
const loadActiveProductCount = async () => {
  try {
    const response = await apiService.getActiveProductCount()
    activeProductCount.value = response.count
  } catch (error) {
    console.error('Failed to load active product count:', error)
    // Keep default value if API fails
    activeProductCount.value = 100
  }
}

// Load categories from database
const loadCategories = async () => {
  try {
    isLoadingCategories.value = true
    const response = await apiService.getRootCategories()
    // Limit to 6 categories as requested
    categories.value = response.slice(0, 6)
  } catch (error) {
    console.error('Failed to load categories:', error)
    // Keep empty array if API fails
    categories.value = []
  } finally {
    isLoadingCategories.value = false
  }
}

// Load popular products from database
const loadPopularProducts = async () => {
  try {
    isLoadingProducts.value = true
    const response = await apiService.getPopularProducts(3) // Get 3 popular products
    
    // Load review stats for each product
    const productsWithStats = await Promise.all(
      response.map(async (product: any) => {
        try {
          const reviewStats = await apiService.getProductReviewStats(product.id)
          return {
            ...product,
            rating: reviewStats.averageRating || 0,
            reviewCount: reviewStats.reviewCount || 0
          }
        } catch (error) {
          console.error(`Failed to load review stats for product ${product.id}:`, error)
          return {
            ...product,
            rating: 0,
            reviewCount: 0
          }
        }
      })
    )
    
    popularProducts.value = productsWithStats
  } catch (error) {
    console.error('Failed to load popular products:', error)
    // Keep empty array if API fails
    popularProducts.value = []
  } finally {
    isLoadingProducts.value = false
  }
}

// Format number with K suffix
const formatProductCount = (count: number): string => {
  if (count >= 1000) {
    return Math.floor(count / 1000) + 'K+'
  }
  return count.toString()
}

// Countdown timer
const countdown = reactive({
  hours: 23,
  minutes: 45,
  seconds: 12
})

// Flash sale products
const flashSaleProducts = reactive([
  {
    id: 1,
    name: 'Corsair Vengeance RGB Pro 32GB',
    image: 'https://images.unsplash.com/photo-1591799264318-7e6ef8ddb7ea?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 4000000,
    salePrice: 2400000,
    discount: 40
  },
  {
    id: 2,
    name: 'Samsung 980 PRO SSD 1TB',
    image: 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 3000000,
    salePrice: 1950000,
    discount: 35
  },
  {
    id: 3,
    name: 'Razer BlackWidow V3',
    image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 2400000,
    salePrice: 1200000,
    discount: 50
  }
])

// Timer interval
let timerInterval: NodeJS.Timeout

// Format price to Indonesian Rupiah
const formatPrice = (price: number): string => {
  return new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR',
    minimumFractionDigits: 0
  }).format(price)
}

// Format rating to 1 decimal place
const formatRating = (rating: number): string => {
  return rating.toFixed(1)
}

// Get product image with fallback
const getProductImage = (product: any): string => {
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0].url || product.imageUrls[0]
  }
  if (product.imageUrl) {
    return product.imageUrl
  }
  // Fallback to a default product image
  return 'https://images.unsplash.com/photo-1593640408182-31c70c8268f5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80'
}

// Handle image error
const handleImageError = (event: Event) => {
  const img = event.target as HTMLImageElement
  img.src = 'https://images.unsplash.com/photo-1593640408182-31c70c8268f5?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80'
}

// Handle category image error - fallback to icon
const handleCategoryImageError = (event: Event) => {
  const img = event.target as HTMLImageElement
  const categoryCard = img.closest('.category-card')
  if (categoryCard) {
    // Hide the image container and show icon fallback
    const imageContainer = img.closest('div')
    if (imageContainer) {
      imageContainer.style.display = 'none'
    }
  }
}

// Load latest reviews from database
const loadLatestReviews = async () => {
  try {
    isLoadingReviews.value = true
    const response = await apiService.getRecentReviews(6) // Get 6 latest reviews
    latestReviews.value = response
  } catch (error) {
    console.error('Failed to load latest reviews:', error)
    // Keep empty array if API fails
    latestReviews.value = []
  } finally {
    isLoadingReviews.value = false
  }
}

// Get user initials from name
const getInitials = (name: string): string => {
  if (!name) return 'U'
  const names = name.split(' ')
  if (names.length >= 2) {
    return (names[0][0] + names[1][0]).toUpperCase()
  }
  return name[0].toUpperCase()
}

// Format date to relative time
const formatDate = (dateString: string): string => {
  const date = new Date(dateString)
  const now = new Date()
  const diffInMs = now.getTime() - date.getTime()
  const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24))
  
  if (diffInDays === 0) {
    return 'Hari ini'
  } else if (diffInDays === 1) {
    return 'Kemarin'
  } else if (diffInDays < 7) {
    return `${diffInDays} hari lalu`
  } else if (diffInDays < 30) {
    const weeks = Math.floor(diffInDays / 7)
    return `${weeks} minggu lalu`
  } else {
    const months = Math.floor(diffInDays / 30)
    return `${months} bulan lalu`
  }
}

// Update countdown timer
const updateCountdown = () => {
  countdown.seconds--
  if (countdown.seconds < 0) {
    countdown.seconds = 59
    countdown.minutes--
    if (countdown.minutes < 0) {
      countdown.minutes = 59
      countdown.hours--
      if (countdown.hours < 0) {
        countdown.hours = 23
      }
    }
  }
}

onMounted(() => {
  timerInterval = setInterval(updateCountdown, 1000)
})

onUnmounted(() => {
  if (timerInterval) {
    clearInterval(timerInterval)
  }
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.category-card:hover .group-hover\:bg-primary {
  background-color: #1e40af;
}

.category-card:hover .group-hover\:text-white {
  color: white;
}
</style>
