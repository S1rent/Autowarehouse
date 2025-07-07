<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <UserNavbar />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Breadcrumb -->
      <nav class="mb-6">
        <div class="flex items-center space-x-2 text-sm text-gray-500">
          <router-link to="/" class="hover:text-blue-600">Home</router-link>
          <i class="fa-solid fa-chevron-right text-xs"></i>
          <span class="text-gray-900">Wishlist</span>
        </div>
      </nav>

      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">My Wishlist</h1>
            <p class="text-gray-600">Save your favorite items for later</p>
          </div>
          <div v-if="wishlistItems.length > 0" class="flex items-center space-x-4">
            <span class="text-sm text-gray-500">{{ wishlistItems.length }} items saved</span>
            <button 
              @click="addAllToCart"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
            >
              <i class="fa-solid fa-cart-plus mr-2"></i>
              Add All to Cart
            </button>
          </div>
        </div>
      </div>

      <!-- Wishlist Items -->
      <div v-if="wishlistItems.length > 0" class="space-y-6">
        <div 
          v-for="item in wishlistItems" 
          :key="item.id"
          class="bg-white rounded-xl shadow-sm border p-6 hover:shadow-md transition-all duration-300"
          :class="{ 'opacity-50 transform -translate-x-4': item.removing }"
        >
          <div class="flex items-center space-x-6">
            <div class="flex-shrink-0">
              <img 
                :src="item.image" 
                :alt="item.name"
                class="w-24 h-24 rounded-lg object-cover"
              >
            </div>
            <div class="flex-1 min-w-0">
              <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ item.name }}</h3>
              <p class="text-gray-500 text-sm mb-2">{{ item.description }}</p>
              <div class="flex items-center space-x-4 mb-3">
                <span class="text-xl font-bold text-gray-900">Rp {{ formatPrice(item.price) }}</span>
                <span 
                  v-if="item.originalPrice"
                  class="text-sm text-gray-500 line-through"
                >
                  Rp {{ formatPrice(item.originalPrice) }}
                </span>
                <span 
                  v-if="item.discount"
                  class="bg-green-100 text-green-800 text-xs px-2 py-1 rounded-full"
                >
                  {{ item.discount }}% off
                </span>
                <span 
                  :class="getStockBadgeClass(item.stock)"
                  class="text-xs px-2 py-1 rounded-full"
                >
                  {{ getStockText(item.stock) }}
                </span>
              </div>
              <div class="flex items-center space-x-2">
                <div class="flex text-yellow-400">
                  <i 
                    v-for="i in 5" 
                    :key="i"
                    :class="i <= item.rating ? 'fa-solid fa-star' : 'fa-regular fa-star'"
                    class="text-sm"
                  ></i>
                </div>
                <span class="text-sm text-gray-500">({{ item.reviews.toLocaleString() }} reviews)</span>
              </div>
            </div>
            <div class="flex flex-col space-y-2">
              <button 
                @click="addToCart(item.id)"
                :disabled="item.addingToCart"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors text-sm disabled:opacity-50"
                :class="{ 'bg-green-600': item.addedToCart }"
              >
                <i :class="item.addedToCart ? 'fa-solid fa-check' : 'fa-solid fa-cart-plus'" class="mr-2"></i>
                {{ item.addedToCart ? 'Added!' : 'Add to Cart' }}
              </button>
              <button 
                @click="removeFromWishlist(item.id)"
                class="text-red-500 hover:text-red-700 px-4 py-2 text-sm transition-colors"
              >
                <i class="fa-solid fa-trash mr-2"></i>
                Remove
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="text-center py-16">
        <i class="fa-regular fa-heart text-6xl text-gray-300 mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-900 mb-2">Your wishlist is empty</h3>
        <p class="text-gray-500 mb-6">Start exploring and save items you love!</p>
        <router-link 
          to="/products"
          class="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors inline-flex items-center"
        >
          <i class="fa-solid fa-search mr-2"></i>
          Browse Products
        </router-link>
      </div>
    </main>

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
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()

// State
const searchQuery = ref('')

// Sample wishlist items
const wishlistItems = ref([
  {
    id: 1,
    name: 'Sony WH-1000XM4 Wireless Headphones',
    description: 'Noise Cancelling, Bluetooth, 30hr Battery',
    price: 5000000,
    originalPrice: 5500000,
    discount: 12,
    rating: 5,
    reviews: 2847,
    stock: 'in-stock',
    image: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&h=300&fit=crop',
    removing: false,
    addingToCart: false,
    addedToCart: false
  },
  {
    id: 2,
    name: 'iPhone 15 Pro Max',
    description: '256GB, Titanium Blue, A17 Pro Chip',
    price: 20000000,
    rating: 4,
    reviews: 1234,
    stock: 'in-stock',
    image: 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400&h=300&fit=crop',
    removing: false,
    addingToCart: false,
    addedToCart: false
  },
  {
    id: 3,
    name: 'ASUS ROG Strix G15 Gaming Laptop',
    description: 'AMD Ryzen 7, RTX 3070, 16GB RAM, 1TB SSD',
    price: 18000000,
    originalPrice: 20000000,
    discount: 13,
    rating: 5,
    reviews: 892,
    stock: 'in-stock',
    image: 'https://images.unsplash.com/photo-1593642632823-8f785ba67e45?w=400&h=300&fit=crop',
    removing: false,
    addingToCart: false,
    addedToCart: false
  },
  {
    id: 4,
    name: 'Apple Watch Series 9',
    description: '45mm, GPS + Cellular, Midnight Aluminum',
    price: 6000000,
    rating: 4,
    reviews: 3421,
    stock: 'low-stock',
    image: 'https://images.unsplash.com/photo-1434493789847-2f02dc6ca35d?w=400&h=300&fit=crop',
    removing: false,
    addingToCart: false,
    addedToCart: false
  },
  {
    id: 5,
    name: 'AirPods Pro (2nd Generation)',
    description: 'Active Noise Cancellation, MagSafe Charging Case',
    price: 3500000,
    originalPrice: 4000000,
    discount: 11,
    rating: 5,
    reviews: 5678,
    stock: 'in-stock',
    image: 'https://images.unsplash.com/photo-1572569511254-d8f925fe2cbb?w=400&h=300&fit=crop',
    removing: false,
    addingToCart: false,
    addedToCart: false
  }
])

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStockBadgeClass = (stock: string) => {
  switch (stock) {
    case 'in-stock':
      return 'bg-blue-100 text-blue-800'
    case 'low-stock':
      return 'bg-red-100 text-red-800'
    case 'out-of-stock':
      return 'bg-gray-100 text-gray-800'
    default:
      return 'bg-blue-100 text-blue-800'
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

const addToCart = (itemId: number) => {
  const item = wishlistItems.value.find(item => item.id === itemId)
  if (item) {
    item.addingToCart = true
    setTimeout(() => {
      item.addingToCart = false
      item.addedToCart = true
      setTimeout(() => {
        item.addedToCart = false
      }, 2000)
    }, 500)
  }
}

const removeFromWishlist = (itemId: number) => {
  const item = wishlistItems.value.find(item => item.id === itemId)
  if (item) {
    item.removing = true
    setTimeout(() => {
      const index = wishlistItems.value.findIndex(item => item.id === itemId)
      if (index > -1) {
        wishlistItems.value.splice(index, 1)
      }
    }, 300)
  }
}

const addAllToCart = () => {
  wishlistItems.value.forEach(item => {
    if (!item.addedToCart) {
      addToCart(item.id)
    }
  })
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
