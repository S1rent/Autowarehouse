<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <UserNavbar />

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center min-h-screen">
      <div class="text-center">
        <i class="fa-solid fa-spinner fa-spin text-4xl text-blue-600 mb-4"></i>
        <p class="text-gray-600">Loading product details...</p>
      </div>
    </div>

    <!-- Error State -->
    <div v-else-if="error" class="flex justify-center items-center min-h-screen">
      <div class="text-center">
        <i class="fa-solid fa-exclamation-triangle text-4xl text-red-600 mb-4"></i>
        <p class="text-red-600 mb-4">{{ error }}</p>
        <button 
          @click="loadProduct" 
          class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
        >
          Try Again
        </button>
      </div>
    </div>

    <!-- Product Content -->
    <div v-else-if="transformedProduct">
      <!-- Breadcrumb -->
      <section class="bg-gray-100 py-4">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div class="flex items-center space-x-2 text-sm">
            <router-link to="/" class="text-blue-600 hover:underline">Home</router-link>
            <i class="fa-solid fa-chevron-right text-gray-400"></i>
            <router-link to="/products" class="text-blue-600 hover:underline">Products</router-link>
            <i class="fa-solid fa-chevron-right text-gray-400"></i>
            <span class="text-gray-600">{{ transformedProduct.category }}</span>
          </div>
        </div>
      </section>

      <!-- Main Content -->
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-12">
        
        <!-- Product Images -->
        <div class="space-y-4">
          <div class="aspect-square bg-white rounded-lg shadow-md overflow-hidden">
            <img 
              :src="selectedImage" 
              :alt="transformedProduct.name"
              class="w-full h-full object-cover"
            >
          </div>
          <div v-if="transformedProduct.images.length > 1" class="grid grid-cols-4 gap-2">
            <div 
              v-for="(image, index) in transformedProduct.images" 
              :key="index"
              @click="selectedImage = image"
              class="aspect-square bg-white rounded-lg shadow-sm overflow-hidden cursor-pointer border-2 transition-colors"
              :class="selectedImage === image ? 'border-blue-600' : 'border-transparent hover:border-gray-300'"
            >
              <img 
                :src="image" 
                :alt="`${transformedProduct.name} ${index + 1}`"
                class="w-full h-full object-cover"
              >
            </div>
          </div>
        </div>

        <!-- Product Info -->
        <div class="space-y-6">
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">{{ transformedProduct.name }}</h1>
            <p class="text-gray-600 mb-4">{{ transformedProduct.brand }} - {{ transformedProduct.description }}</p>
            
            <!-- Rating -->
            <div class="flex items-center space-x-2 mb-4">
              <div class="flex text-yellow-400">
                <i 
                  v-for="i in 5" 
                  :key="i"
                  :class="i <= Math.round(reviewStats.averageRating) ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                ></i>
              </div>
              <span class="text-gray-600">({{ reviewStats.averageRating.toFixed(1) }}) {{ reviewStats.reviewCount.toLocaleString() }} reviews</span>
            </div>

            <!-- Price -->
            <div class="flex items-center space-x-4 mb-6">
              <span class="text-3xl font-bold text-blue-600">Rp {{ formatPrice(transformedProduct.price) }}</span>
              <span 
                v-if="transformedProduct.originalPrice"
                class="text-lg text-gray-500 line-through"
              >
                Rp {{ formatPrice(transformedProduct.originalPrice) }}
              </span>
              <span 
                v-if="transformedProduct.discount"
                class="bg-green-100 text-green-800 text-sm px-2 py-1 rounded-full"
              >
                {{ transformedProduct.discount }}% off
              </span>
            </div>

            <!-- Stock Status -->
            <div class="mb-6">
              <span 
                :class="getStockBadgeClass(transformedProduct.stock)"
                class="text-sm px-3 py-1 rounded-full"
              >
                {{ getStockText(transformedProduct.stock) }}
              </span>
            </div>
          </div>


          <!-- Quantity -->
          <div class="flex items-center space-x-4">
            <span class="font-medium text-gray-900">Quantity:</span>
            <div class="flex items-center border rounded-lg">
              <button 
                @click="decreaseQuantity"
                :disabled="quantity <= 1"
                class="p-2 hover:bg-gray-100 disabled:opacity-50"
              >
                <i class="fa-solid fa-minus"></i>
              </button>
              <span class="px-4 py-2 border-x">{{ quantity }}</span>
              <button 
                @click="increaseQuantity"
                :disabled="quantity >= transformedProduct.maxQuantity"
                class="p-2 hover:bg-gray-100 disabled:opacity-50"
              >
                <i class="fa-solid fa-plus"></i>
              </button>
            </div>
            <span class="text-sm text-gray-500">{{ transformedProduct.maxQuantity }} available</span>
          </div>

          <!-- Action Buttons -->
          <div class="flex space-x-4">
            <button 
              @click="addToCart"
              :disabled="transformedProduct.stock === 'out-of-stock'"
              class="flex-1 bg-blue-600 text-white py-3 px-6 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <i class="fa-solid fa-shopping-cart mr-2"></i>
              Add to Cart
            </button>
            <button 
              @click="toggleWishlist"
              class="px-6 py-3 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
              :class="isInWishlistComputed ? 'text-red-500 border-red-300' : 'text-gray-600'"
            >
              <i :class="isInWishlistComputed ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"></i>
            </button>
          </div>

          <!-- Product Features -->
          <div class="border-t pt-6">
            <h3 class="font-semibold text-gray-900 mb-4">Key Features</h3>
            <ul class="space-y-2">
              <li v-for="feature in transformedProduct.features" :key="feature" class="flex items-center">
                <i class="fa-solid fa-check text-green-500 mr-2"></i>
                <span class="text-gray-700">{{ feature }}</span>
              </li>
            </ul>
          </div>

          <!-- Shipping Info -->
          <div class="border-t pt-6">
            <h3 class="font-semibold text-gray-900 mb-4">Shipping & Returns</h3>
            <div class="space-y-2 text-sm text-gray-600">
              <div class="flex items-center">
                <i class="fa-solid fa-truck text-blue-600 mr-2"></i>
                <span>Free shipping on orders over Rp 1,000,000</span>
              </div>
              <div class="flex items-center">
                <i class="fa-solid fa-undo text-blue-600 mr-2"></i>
                <span>30-day return policy</span>
              </div>
              <div class="flex items-center">
                <i class="fa-solid fa-shield-alt text-blue-600 mr-2"></i>
                <span>1-year warranty included</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Product Description & Reviews -->
      <div class="mt-16">
        <div class="border-b border-gray-200">
          <nav class="-mb-px flex space-x-8">
            <button
              @click="activeTab = 'description'"
              class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
              :class="activeTab === 'description' 
                ? 'border-blue-600 text-blue-600' 
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
            >
              Description
            </button>
            <button
              @click="activeTab = 'specifications'"
              class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
              :class="activeTab === 'specifications' 
                ? 'border-blue-600 text-blue-600' 
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
            >
              Specifications
            </button>
            <button
              @click="activeTab = 'reviews'"
              class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
              :class="activeTab === 'reviews' 
                ? 'border-blue-600 text-blue-600' 
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
            >
              Reviews ({{ reviewStats.reviewCount }})
            </button>
          </nav>
        </div>

        <div class="py-8">
          <!-- Description Tab -->
          <div v-if="activeTab === 'description'" class="prose max-w-none">
            <p class="text-gray-700 leading-relaxed">{{ transformedProduct.fullDescription }}</p>
          </div>

          <!-- Specifications Tab -->
          <div v-if="activeTab === 'specifications'">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div v-for="(value, key) in transformedProduct.specifications" :key="key" class="flex justify-between py-2 border-b border-gray-200">
                <span class="font-medium text-gray-900">{{ key }}</span>
                <span class="text-gray-600">{{ value }}</span>
              </div>
            </div>
          </div>

          <!-- Reviews Tab -->
          <div v-if="activeTab === 'reviews'" class="space-y-6">
            <!-- Reviews Loading -->
            <div v-if="reviewsLoading" class="text-center py-8">
              <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-600 mb-4"></i>
              <p class="text-gray-600">Loading reviews...</p>
            </div>

            <!-- Reviews Content -->
            <div v-else>
              <!-- Reviews Summary -->
              <div class="bg-gray-50 rounded-lg p-6 mb-6">
                <div class="flex items-center justify-between mb-4">
                  <h3 class="text-lg font-semibold text-gray-900">Customer Reviews</h3>
                  <div class="flex items-center space-x-2">
                    <div class="flex text-yellow-400">
                      <i 
                        v-for="i in 5" 
                        :key="i"
                        :class="i <= Math.round(reviewStats.averageRating) ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                      ></i>
                    </div>
                    <span class="text-gray-600 font-medium">{{ reviewStats.averageRating.toFixed(1) }}</span>
                    <span class="text-gray-500">({{ reviewStats.reviewCount }} reviews)</span>
                  </div>
                </div>

                <!-- Rating Breakdown -->
                <div class="grid grid-cols-1 md:grid-cols-5 gap-4">
                  <div v-for="rating in [5, 4, 3, 2, 1]" :key="rating" class="flex items-center space-x-2">
                    <span class="text-sm text-gray-600 w-8">{{ rating }}★</span>
                    <div class="flex-1 bg-gray-200 rounded-full h-2">
                      <div 
                        class="bg-yellow-400 h-2 rounded-full transition-all duration-300"
                        :style="{ width: getRatingPercentage(rating) + '%' }"
                      ></div>
                    </div>
                    <span class="text-sm text-gray-500 w-8">{{ getRatingCount(rating) }}</span>
                  </div>
                </div>
              </div>

              <!-- Reviews List -->
              <div v-if="paginatedReviews.length > 0" class="space-y-6">
                <div 
                  v-for="review in paginatedReviews" 
                  :key="review.id"
                  class="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow"
                >
                  <!-- Review Header -->
                  <div class="flex items-start justify-between mb-4">
                    <div class="flex items-center space-x-3">
                      <div class="w-10 h-10 bg-blue-600 rounded-full flex items-center justify-center">
                        <span class="text-white font-medium text-sm">
                          {{ getInitials(review.userName) }}
                        </span>
                      </div>
                      <div>
                        <h4 class="font-medium text-gray-900">{{ review.userName }}</h4>
                        <div class="flex items-center space-x-2">
                          <div class="flex text-yellow-400">
                            <i 
                              v-for="i in 5" 
                              :key="i"
                              :class="i <= review.rating ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                              class="text-sm"
                            ></i>
                          </div>
                          <span v-if="review.isVerifiedPurchase" class="text-xs bg-green-100 text-green-800 px-2 py-1 rounded-full">
                            Verified Purchase
                          </span>
                        </div>
                      </div>
                    </div>
                    <span class="text-sm text-gray-500">{{ formatDate(review.createdAt) }}</span>
                  </div>

                  <!-- Review Content -->
                  <div class="mb-4">
                    <h5 v-if="review.title" class="font-medium text-gray-900 mb-2">{{ review.title }}</h5>
                    <p class="text-gray-700 leading-relaxed">{{ review.comment }}</p>
                  </div>

                  <!-- Review Images -->
                  <div v-if="review.imageUrls && review.imageUrls.length > 0" class="mb-4">
                    <div class="flex space-x-2 overflow-x-auto">
                      <img 
                        v-for="(imageUrl, index) in review.imageUrls" 
                        :key="index"
                        :src="imageUrl" 
                        :alt="`Review image ${index + 1}`"
                        class="w-20 h-20 object-cover rounded-lg border border-gray-200 cursor-pointer hover:opacity-80"
                        @click="openImageModal(imageUrl)"
                      >
                    </div>
                  </div>

                  <!-- Review Actions -->
                  <div class="flex items-center justify-between pt-4 border-t border-gray-100">
                    <button 
                      @click="markHelpful(review.id)"
                      class="flex items-center space-x-1 text-sm text-gray-600 hover:text-blue-600 transition-colors"
                    >
                      <i class="fa-solid fa-thumbs-up"></i>
                      <span>Helpful ({{ review.helpfulCount }})</span>
                    </button>
                    <div class="text-xs text-gray-400">
                      Review #{{ review.id }}
                    </div>
                  </div>
                </div>

                <!-- Pagination -->
                <div v-if="totalPages > 1" class="flex items-center justify-center space-x-2 mt-8">
                  <button 
                    @click="goToPage(currentPage - 1)"
                    :disabled="currentPage === 1"
                    class="px-3 py-2 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    <i class="fa-solid fa-chevron-left"></i>
                    Previous
                  </button>

                  <div class="flex space-x-1">
                    <button
                      v-for="page in visiblePages"
                      :key="page"
                      @click="goToPage(page)"
                      :class="[
                        'px-3 py-2 text-sm font-medium rounded-md',
                        page === currentPage
                          ? 'bg-blue-600 text-white'
                          : 'text-gray-700 bg-white border border-gray-300 hover:bg-gray-50'
                      ]"
                    >
                      {{ page }}
                    </button>
                  </div>

                  <button 
                    @click="goToPage(currentPage + 1)"
                    :disabled="currentPage === totalPages"
                    class="px-3 py-2 text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-md hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    Next
                    <i class="fa-solid fa-chevron-right"></i>
                  </button>
                </div>

                <!-- Pagination Info -->
                <div v-if="totalPages > 1" class="text-center text-sm text-gray-500 mt-4">
                  Showing {{ startIndex + 1 }}-{{ Math.min(endIndex, allReviews.length) }} of {{ allReviews.length }} reviews
                </div>
              </div>

              <!-- No Reviews -->
              <div v-else class="text-center text-gray-500 py-12">
                <i class="fa-solid fa-comment-dots text-4xl mb-4"></i>
                <p class="text-lg font-medium mb-2">No reviews yet</p>
                <p class="text-sm">Be the first to review this product!</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      </div>
    </div>

    <!-- Footer -->
    <!-- <footer class="bg-gray-900 text-white mt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <div class="flex items-center space-x-2 mb-4">
              <i class="fa-solid fa-store text-blue-400 text-xl"></i>
              <span class="text-xl font-bold">Autowarehouse</span>
            </div>
            <p class="text-gray-400 text-sm">Your trusted online shopping destination with quality products and excellent service.</p>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Quick Links</h4>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><span class="hover:text-white cursor-pointer">About Us</span></li>
              <li><span class="hover:text-white cursor-pointer">Contact</span></li>
              <li><span class="hover:text-white cursor-pointer">FAQ</span></li>
              <li><span class="hover:text-white cursor-pointer">Shipping Info</span></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Categories</h4>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><span class="hover:text-white cursor-pointer">Electronics</span></li>
              <li><span class="hover:text-white cursor-pointer">Hardware</span></li>
              <li><span class="hover:text-white cursor-pointer">Gaming</span></li>
              <li><span class="hover:text-white cursor-pointer">Accessories</span></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Follow Us</h4>
            <div class="flex space-x-4">
              <span class="text-gray-400 hover:text-white cursor-pointer"><i class="fa-brands fa-facebook text-xl"></i></span>
              <span class="text-gray-400 hover:text-white cursor-pointer"><i class="fa-brands fa-twitter text-xl"></i></span>
              <span class="text-gray-400 hover:text-white cursor-pointer"><i class="fa-brands fa-instagram text-xl"></i></span>
              <span class="text-gray-400 hover:text-white cursor-pointer"><i class="fa-brands fa-youtube text-xl"></i></span>
            </div>
          </div>
        </div>
        <div class="border-t border-gray-800 mt-8 pt-8 text-center text-sm text-gray-400">
          <p>© 2024 Autowarehouse. All rights reserved.</p>
        </div>
      </div>
    </footer> -->
    <Footer />
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useWishlistStore } from '@/stores/wishlist'
import { useNotifications } from '@/composables/useNotifications'
import UserNavbar from '../components/UserNavbar.vue'
import { apiService, type Product, type ReviewResponse } from '@/services/api'
import Footer from '../components/Footer.vue'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()
const wishlistStore = useWishlistStore()
const { success, error: showError } = useNotifications()

// State
const quantity = ref(1)
const activeTab = ref('description')
const isInWishlist = ref(false)
const selectedVariants = reactive({})
const product = ref<Product | null>(null)
const isLoading = ref(false)
const error = ref<string | null>(null)

// Reviews state
const reviews = ref<ReviewResponse[]>([])
const allReviews = ref<ReviewResponse[]>([])
const reviewsLoading = ref(false)
const reviewStats = ref({
  averageRating: 0,
  reviewCount: 0
})

// Pagination state
const currentPage = ref(1)
const reviewsPerPage = 10

// Get product ID from route
const productId = computed(() => {
  const id = route.params.id
  return typeof id === 'string' ? parseInt(id) : null
})

// Load product data
const loadProduct = async () => {
  if (!productId.value) {
    error.value = 'Invalid product ID'
    return
  }

  try {
    isLoading.value = true
    error.value = null
    product.value = await apiService.getProduct(productId.value)
    
    // Check if product is active (only show active products to customers)
    if (product.value && !product.value.isActive) {
      error.value = 'Product not available'
      product.value = null
    }
  } catch (err) {
    console.error('Error loading product:', err)
    error.value = 'Failed to load product details'
  } finally {
    isLoading.value = false
  }
}

// Initialize on mount
onMounted(async () => {
  await loadProduct()
  await wishlistStore.loadWishlist()
  // Load reviews immediately when page loads
  await loadReviews()
})

// Computed properties
const selectedImage = ref('')

const productImages = computed(() => {
  if (!product.value?.imageUrls || product.value.imageUrls.length === 0) {
    return ['/placeholder-product.jpg']
  }
  return product.value.imageUrls
})

// Set initial image when product loads
const setInitialImage = () => {
  if (productImages.value.length > 0) {
    selectedImage.value = productImages.value[0]
  }
}

// Transform API data to match template expectations
const transformedProduct = computed(() => {
  if (!product.value) return null

  return {
    id: product.value.id,
    name: product.value.name,
    brand: product.value.brand,
    category: product.value.categoryName || 'Electronics',
    description: product.value.description,
    fullDescription: product.value.description, // Use description as full description
    price: product.value.price,
    originalPrice: product.value.originalPrice,
    discount: product.value.originalPrice ? 
      Math.round(((product.value.originalPrice - product.value.price) / product.value.originalPrice) * 100) : 0,
    rating: product.value.rating || 0,
    reviews: product.value.reviewCount || 0,
    stock: getStockStatus(product.value.stockQuantity),
    maxQuantity: Math.min(product.value.stockQuantity, 10), // Max 10 per order
    images: productImages.value,
    features: parseFeatures(product.value.features),
    specifications: parseSpecifications(product.value.specifications),
    reviewsList: [] // TODO: Implement reviews API
  }
})

// Helper functions
const getStockStatus = (stockQuantity: number) => {
  if (stockQuantity === 0) return 'out-of-stock'
  if (stockQuantity <= 5) return 'low-stock'
  return 'in-stock'
}

const parseFeatures = (features: string | undefined) => {
  if (!features) return []
  return features.split('\n').filter(f => f.trim().length > 0)
}

const parseSpecifications = (specifications: string | undefined) => {
  if (!specifications) return {}
  
  const specs: Record<string, string> = {}
  const lines = specifications.split('\n')
  
  lines.forEach(line => {
    const [key, ...valueParts] = line.split(':')
    if (key && valueParts.length > 0) {
      specs[key.trim()] = valueParts.join(':').trim()
    }
  })
  
  return specs
}

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStockBadgeClass = (stock: string) => {
  switch (stock) {
    case 'in-stock':
      return 'bg-green-100 text-green-800'
    case 'low-stock':
      return 'bg-yellow-100 text-yellow-800'
    case 'out-of-stock':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-green-100 text-green-800'
  }
}

const getStockText = (stock: string) => {
  switch (stock) {
    case 'in-stock':
      return 'In Stock'
    case 'low-stock':
      return 'Low Stock'
    case 'out-of-stock':
      return 'Out of Stock'
    default:
      return 'In Stock'
  }
}

const increaseQuantity = () => {
  if (transformedProduct.value && quantity.value < transformedProduct.value.maxQuantity) {
    quantity.value++
  }
}

const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value--
  }
}

const selectVariant = (variantName: string, option: string) => {
  (selectedVariants as any)[variantName] = option
}

const addToCart = async () => {
  if (!transformedProduct.value) return
  
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    await cartStore.addToCart(transformedProduct.value.id, quantity.value)
    
    // Show success message
    success('Added to Cart', `${transformedProduct.value.name} added to cart!`)
  } catch (error) {
    console.error('Error adding to cart:', error)
    showError('Add to Cart Failed', 'Failed to add product to cart. Please try again.')
  }
}

const toggleWishlist = async () => {
  if (!transformedProduct.value) return
  
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    const isCurrentlyInWishlist = wishlistStore.isInWishlist(transformedProduct.value.id)
    
    await wishlistStore.toggleWishlist(transformedProduct.value.id)
    
    // Show success message
    if (isCurrentlyInWishlist) {
      success('Wishlist Updated', 'Product removed from wishlist!')
    } else {
      success('Wishlist Updated', 'Product added to wishlist!')
    }
    
  } catch (error) {
    console.error('Error toggling wishlist:', error)
    showError('Wishlist Error', 'Failed to update wishlist. Please try again.')
  }
}

// Computed property for wishlist status
const isInWishlistComputed = computed(() => {
  return transformedProduct.value ? wishlistStore.isInWishlist(transformedProduct.value.id) : false
})

// Reviews methods
const loadReviews = async () => {
  if (!productId.value) return
  
  try {
    reviewsLoading.value = true
    
    // Load reviews and stats in parallel
    const [reviewsData, statsData] = await Promise.all([
      apiService.getProductReviews(productId.value),
      apiService.getProductReviewStats(productId.value)
    ])
    
    allReviews.value = reviewsData
    reviews.value = reviewsData
    reviewStats.value = statsData
    
  } catch (err) {
    console.error('Error loading reviews:', err)
    // Don't show error for reviews, just keep empty state
  } finally {
    reviewsLoading.value = false
  }
}

// Pagination computed properties
const totalPages = computed(() => {
  return Math.ceil(allReviews.value.length / reviewsPerPage)
})

const startIndex = computed(() => {
  return (currentPage.value - 1) * reviewsPerPage
})

const endIndex = computed(() => {
  return startIndex.value + reviewsPerPage
})

const paginatedReviews = computed(() => {
  return allReviews.value.slice(startIndex.value, endIndex.value)
})

const visiblePages = computed(() => {
  const pages = []
  const total = totalPages.value
  const current = currentPage.value
  
  // Show max 5 pages
  let start = Math.max(1, current - 2)
  let end = Math.min(total, start + 4)
  
  // Adjust start if we're near the end
  if (end - start < 4) {
    start = Math.max(1, end - 4)
  }
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

// Pagination methods
const goToPage = (page: number) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}

// Helper methods for reviews
const getInitials = (name: string) => {
  if (!name) return '?'
  return name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2)
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const getRatingPercentage = (rating: number) => {
  if (reviews.value.length === 0) return 0
  const count = reviews.value.filter(r => r.rating === rating).length
  return (count / reviews.value.length) * 100
}

const getRatingCount = (rating: number) => {
  return reviews.value.filter(r => r.rating === rating).length
}

const markHelpful = async (reviewId: number) => {
  try {
    await apiService.markReviewHelpful(reviewId)
    // Reload reviews to get updated helpful count
    await loadReviews()
    success('Thank you!', 'Your feedback has been recorded.')
  } catch (error) {
    console.error('Error marking review as helpful:', error)
    showError('Error', 'Failed to mark review as helpful.')
  }
}

const openImageModal = (imageUrl: string) => {
  // Simple implementation - open image in new tab
  window.open(imageUrl, '_blank')
}

// Watch for product changes to set initial image
watch(productImages, () => {
  setInitialImage()
}, { immediate: true })

// Watch for product changes to reload reviews when product ID changes
watch(productId, (newProductId) => {
  if (newProductId) {
    loadReviews()
  }
}, { immediate: true })
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
