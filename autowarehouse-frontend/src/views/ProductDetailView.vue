<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <header class="bg-white shadow-sm border-b sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-8">
            <div class="flex items-center space-x-2">
              <i class="fa-solid fa-store text-blue-600 text-xl"></i>
              <span class="text-xl font-bold text-gray-900">Autowarehouse</span>
            </div>
            <nav class="hidden md:flex space-x-6">
              <router-link to="/" class="text-gray-600 hover:text-blue-600 transition-colors">Home</router-link>
              <router-link to="/products" class="text-blue-600 font-medium">Products</router-link>
              <span class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Categories</span>
              <span class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Live Auction</span>
            </nav>
          </div>
          <div class="flex items-center space-x-4">
            <div class="relative">
              <input 
                type="text" 
                placeholder="Search products..." 
                v-model="searchQuery"
                class="w-64 pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
              <i class="fa-solid fa-search absolute left-3 top-3 text-gray-400"></i>
            </div>
            <router-link to="/wishlist" class="relative p-2 text-gray-600 hover:text-blue-600">
              <i class="fa-solid fa-heart text-lg"></i>
              <span class="absolute -top-1 -right-1 bg-orange-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">3</span>
            </router-link>
            <router-link to="/cart" class="relative p-2 text-gray-600 hover:text-blue-600">
              <i class="fa-solid fa-shopping-cart text-lg"></i>
              <span class="absolute -top-1 -right-1 bg-orange-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">2</span>
            </router-link>
            <router-link to="/login" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
              Login
            </router-link>
          </div>
        </div>
      </div>
    </header>

    <!-- Breadcrumb -->
    <section class="bg-gray-100 py-4">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center space-x-2 text-sm">
          <router-link to="/" class="text-blue-600 hover:underline">Home</router-link>
          <i class="fa-solid fa-chevron-right text-gray-400"></i>
          <router-link to="/products" class="text-blue-600 hover:underline">Products</router-link>
          <i class="fa-solid fa-chevron-right text-gray-400"></i>
          <span class="text-gray-600">{{ product.category }}</span>
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
              :alt="product.name"
              class="w-full h-full object-cover"
            >
          </div>
          <div class="grid grid-cols-4 gap-2">
            <div 
              v-for="(image, index) in product.images" 
              :key="index"
              @click="selectedImage = image"
              class="aspect-square bg-white rounded-lg shadow-sm overflow-hidden cursor-pointer border-2 transition-colors"
              :class="selectedImage === image ? 'border-blue-600' : 'border-transparent hover:border-gray-300'"
            >
              <img 
                :src="image" 
                :alt="`${product.name} ${index + 1}`"
                class="w-full h-full object-cover"
              >
            </div>
          </div>
        </div>

        <!-- Product Info -->
        <div class="space-y-6">
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">{{ product.name }}</h1>
            <p class="text-gray-600 mb-4">{{ product.brand }} - {{ product.description }}</p>
            
            <!-- Rating -->
            <div class="flex items-center space-x-2 mb-4">
              <div class="flex text-yellow-400">
                <i 
                  v-for="i in 5" 
                  :key="i"
                  :class="i <= product.rating ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                ></i>
              </div>
              <span class="text-gray-600">({{ product.rating }}) {{ product.reviews.toLocaleString() }} reviews</span>
            </div>

            <!-- Price -->
            <div class="flex items-center space-x-4 mb-6">
              <span class="text-3xl font-bold text-blue-600">Rp {{ formatPrice(product.price) }}</span>
              <span 
                v-if="product.originalPrice"
                class="text-lg text-gray-500 line-through"
              >
                Rp {{ formatPrice(product.originalPrice) }}
              </span>
              <span 
                v-if="product.discount"
                class="bg-green-100 text-green-800 text-sm px-2 py-1 rounded-full"
              >
                {{ product.discount }}% off
              </span>
            </div>

            <!-- Stock Status -->
            <div class="mb-6">
              <span 
                :class="getStockBadgeClass(product.stock)"
                class="text-sm px-3 py-1 rounded-full"
              >
                {{ getStockText(product.stock) }}
              </span>
            </div>
          </div>

          <!-- Variants -->
          <div v-if="product.variants" class="space-y-4">
            <div v-for="variant in product.variants" :key="variant.name">
              <h4 class="font-medium text-gray-900 mb-2">{{ variant.name }}:</h4>
              <div class="flex flex-wrap gap-2">
                <button
                  v-for="option in variant.options"
                  :key="option"
                  @click="selectVariant(variant.name, option)"
                  class="px-4 py-2 border rounded-lg transition-colors"
                  :class="selectedVariants[variant.name] === option 
                    ? 'border-blue-600 bg-blue-50 text-blue-600' 
                    : 'border-gray-300 hover:border-gray-400'"
                >
                  {{ option }}
                </button>
              </div>
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
                :disabled="quantity >= product.maxQuantity"
                class="p-2 hover:bg-gray-100 disabled:opacity-50"
              >
                <i class="fa-solid fa-plus"></i>
              </button>
            </div>
            <span class="text-sm text-gray-500">{{ product.maxQuantity }} available</span>
          </div>

          <!-- Action Buttons -->
          <div class="flex space-x-4">
            <button 
              @click="addToCart"
              :disabled="product.stock === 'out-of-stock'"
              class="flex-1 bg-blue-600 text-white py-3 px-6 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <i class="fa-solid fa-shopping-cart mr-2"></i>
              Add to Cart
            </button>
            <button 
              @click="toggleWishlist"
              class="px-6 py-3 border border-gray-300 rounded-lg hover:bg-gray-50 transition-colors"
              :class="isInWishlist ? 'text-red-500 border-red-300' : 'text-gray-600'"
            >
              <i :class="isInWishlist ? 'fa-solid fa-heart' : 'fa-regular fa-heart'"></i>
            </button>
          </div>

          <!-- Product Features -->
          <div class="border-t pt-6">
            <h3 class="font-semibold text-gray-900 mb-4">Key Features</h3>
            <ul class="space-y-2">
              <li v-for="feature in product.features" :key="feature" class="flex items-center">
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
              Reviews ({{ product.reviews }})
            </button>
          </nav>
        </div>

        <div class="py-8">
          <!-- Description Tab -->
          <div v-if="activeTab === 'description'" class="prose max-w-none">
            <p class="text-gray-700 leading-relaxed">{{ product.fullDescription }}</p>
          </div>

          <!-- Specifications Tab -->
          <div v-if="activeTab === 'specifications'">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div v-for="(value, key) in product.specifications" :key="key" class="flex justify-between py-2 border-b border-gray-200">
                <span class="font-medium text-gray-900">{{ key }}</span>
                <span class="text-gray-600">{{ value }}</span>
              </div>
            </div>
          </div>

          <!-- Reviews Tab -->
          <div v-if="activeTab === 'reviews'" class="space-y-6">
            <div v-for="review in product.reviewsList" :key="review.id" class="border-b border-gray-200 pb-6">
              <div class="flex items-center space-x-4 mb-2">
                <img :src="review.avatar" :alt="review.name" class="w-10 h-10 rounded-full">
                <div>
                  <h4 class="font-medium text-gray-900">{{ review.name }}</h4>
                  <div class="flex items-center space-x-2">
                    <div class="flex text-yellow-400">
                      <i 
                        v-for="i in 5" 
                        :key="i"
                        :class="i <= review.rating ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                        class="text-sm"
                      ></i>
                    </div>
                    <span class="text-sm text-gray-500">{{ review.date }}</span>
                  </div>
                </div>
              </div>
              <p class="text-gray-700">{{ review.comment }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <footer class="bg-gray-900 text-white mt-16">
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
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// State
const searchQuery = ref('')
const quantity = ref(1)
const activeTab = ref('description')
const isInWishlist = ref(false)
const selectedVariants = reactive({})

// Sample product data
const product = ref({
  id: 1,
  name: 'MacBook Pro 14" M3',
  brand: 'Apple',
  category: 'Electronics',
  description: '512GB SSD, 16GB RAM',
  fullDescription: 'The MacBook Pro 14-inch with M3 chip delivers exceptional performance for professionals and creatives. Featuring a stunning Liquid Retina XDR display, advanced camera and audio, and all-day battery life, it\'s built for those who demand the best.',
  price: 32000000,
  originalPrice: 35000000,
  discount: 9,
  rating: 5,
  reviews: 124,
  stock: 'in-stock',
  maxQuantity: 5,
  images: [
    'https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&h=600&fit=crop',
    'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=600&h=600&fit=crop',
    'https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=600&h=600&fit=crop',
    'https://images.unsplash.com/photo-1525547719571-a2d4ac8945e2?w=600&h=600&fit=crop'
  ],
  variants: [
    {
      name: 'Storage',
      options: ['512GB', '1TB', '2TB']
    },
    {
      name: 'Color',
      options: ['Space Gray', 'Silver']
    }
  ],
  features: [
    'Apple M3 chip with 8-core CPU',
    '14-inch Liquid Retina XDR display',
    '16GB unified memory',
    '512GB SSD storage',
    'Up to 18 hours battery life',
    'Three Thunderbolt 4 ports'
  ],
  specifications: {
    'Processor': 'Apple M3 8-core CPU',
    'Memory': '16GB unified memory',
    'Storage': '512GB SSD',
    'Display': '14.2-inch Liquid Retina XDR',
    'Graphics': '10-core GPU',
    'Weight': '1.55 kg',
    'Dimensions': '31.26 × 22.12 × 1.55 cm',
    'Battery': 'Up to 18 hours',
    'Operating System': 'macOS Sonoma'
  },
  reviewsList: [
    {
      id: 1,
      name: 'John Doe',
      rating: 5,
      date: '2024-01-15',
      comment: 'Excellent laptop! The performance is outstanding and the display is beautiful.',
      avatar: 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=40&h=40&fit=crop&crop=face'
    },
    {
      id: 2,
      name: 'Jane Smith',
      rating: 4,
      date: '2024-01-10',
      comment: 'Great build quality and fast performance. Battery life is impressive.',
      avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=40&h=40&fit=crop&crop=face'
    }
  ]
})

const selectedImage = ref(product.value.images[0])

// Initialize selected variants
product.value.variants?.forEach(variant => {
  selectedVariants[variant.name] = variant.options[0]
})

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
  if (quantity.value < product.value.maxQuantity) {
    quantity.value++
  }
}

const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value--
  }
}

const selectVariant = (variantName: string, option: string) => {
  selectedVariants[variantName] = option
}

const addToCart = () => {
  // Implement add to cart logic
  console.log('Adding to cart:', {
    product: product.value,
    quantity: quantity.value,
    variants: selectedVariants
  })
  alert('Product added to cart!')
}

const toggleWishlist = () => {
  isInWishlist.value = !isInWishlist.value
  console.log('Wishlist toggled:', isInWishlist.value)
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
