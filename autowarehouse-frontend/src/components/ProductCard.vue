<template>
  <div class="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-xl transition-shadow">
    <div class="relative">
      <img 
        :src="product.imageUrls?.[0] || '/placeholder-product.jpg'" 
        :alt="product.name"
        class="w-full h-48 object-cover"
      >
      <button 
        @click="toggleWishlist"
        class="absolute top-3 right-3 w-8 h-8 bg-white rounded-full flex items-center justify-center shadow-md hover:bg-gray-100"
      >
        <i 
          :class="isInWishlist ? 'fa-solid fa-heart text-red-500' : 'fa-regular fa-heart text-gray-600'"
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
          @click="addToCart"
          class="flex-1 bg-primary text-white py-2 rounded-lg hover:bg-blue-700 transition-colors text-sm"
        >
          <i class="fa-solid fa-shopping-cart mr-1"></i>Keranjang
        </button>
        <button 
          @click="viewProduct"
          class="px-3 py-2 border border-primary text-primary rounded-lg hover:bg-primary hover:text-white transition-colors"
        >
          <i class="fa-solid fa-eye"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useWishlistStore } from '@/stores/wishlist'
import type { Product } from '@/services/api'

interface Props {
  product: Product
}

const props = defineProps<Props>()
const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()
const wishlistStore = useWishlistStore()

// Local state for wishlist status
const isInWishlist = ref(false)

// Initialize wishlist status
onMounted(() => {
  isInWishlist.value = wishlistStore.isInWishlist(props.product.id)
})

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStockClass = (stock: number) => {
  if (stock > 10) return 'bg-green-100 text-green-800'
  if (stock > 5) return 'bg-yellow-100 text-yellow-800'
  return 'bg-red-100 text-red-800'
}

const toggleWishlist = async () => {
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    const wasInWishlist = isInWishlist.value
    
    // Optimistically update UI
    isInWishlist.value = !isInWishlist.value
    
    try {
      await wishlistStore.toggleWishlist(props.product.id)
      
      // Show success alert
      if (wasInWishlist) {
        alert('Produk berhasil dihapus dari wishlist!')
      } else {
        alert('Produk berhasil ditambahkan ke wishlist!')
      }
    } catch (error) {
      // Revert optimistic update on error
      isInWishlist.value = wasInWishlist
      console.error('Error toggling wishlist:', error)
      alert('Gagal mengubah wishlist. Silakan coba lagi.')
    }
    
  } catch (error) {
    console.error('Error toggling wishlist:', error)
    alert('Gagal mengubah wishlist. Silakan coba lagi.')
  }
}

const addToCart = async () => {
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    await cartStore.addToCart(props.product.id, 1)
    console.log('Added to cart:', props.product.id)
  } catch (error) {
    console.error('Error adding to cart:', error)
  }
}

const viewProduct = () => {
  router.push(`/product/${props.product.id}`)
}
</script>
