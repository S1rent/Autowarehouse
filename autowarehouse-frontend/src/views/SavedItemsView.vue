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
          <span class="text-gray-900">Saved Items</span>
        </div>
      </nav>

      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">Saved Items</h1>
            <p class="text-gray-600">Items you've saved for later</p>
          </div>
          <div v-if="savedItemsStore.hasItems" class="flex items-center space-x-4">
            <button
              @click="moveAllToCart"
              :disabled="savedItemsStore.isLoading"
              class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
            >
              <i class="fa-solid fa-shopping-cart mr-2"></i>
              Move All to Cart
            </button>
            <button
              @click="clearAllItems"
              :disabled="savedItemsStore.isLoading"
              class="bg-red-600 text-white px-4 py-2 rounded-lg hover:bg-red-700 transition-colors disabled:opacity-50"
            >
              <i class="fa-solid fa-trash mr-2"></i>
              Clear All
            </button>
          </div>
        </div>
      </div>

      <!-- Loading State -->
      <div v-if="savedItemsStore.isLoading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="savedItemsStore.error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
        <div class="flex">
          <div class="flex-shrink-0">
            <i class="fa-solid fa-exclamation-triangle text-red-400"></i>
          </div>
          <div class="ml-3">
            <h3 class="text-sm font-medium text-red-800">Error</h3>
            <p class="text-sm text-red-700 mt-1">{{ savedItemsStore.error }}</p>
          </div>
        </div>
      </div>

      <!-- Saved Items Content -->
      <div v-else-if="savedItemsStore.hasItems" class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Saved Items List -->
        <div class="lg:col-span-2 space-y-6">
          <div 
            v-for="item in savedItemsStore.items" 
            :key="item.id"
            class="bg-white rounded-xl shadow-sm border p-6 transition-all duration-300 hover:shadow-md"
          >
            <div class="flex items-center space-x-6">
              <div class="flex-shrink-0">
                <img 
                  :src="item.productImages?.[0] || '/placeholder-product.jpg'" 
                  :alt="item.productName"
                  class="w-20 h-20 rounded-lg object-cover cursor-pointer"
                  @click="navigateToProduct(item.productId)"
                >
              </div>
              <div class="flex-1 min-w-0">
                <h3 
                  class="text-lg font-semibold text-gray-900 mb-1 cursor-pointer hover:text-blue-600"
                  @click="navigateToProduct(item.productId)"
                >
                  {{ item.productName }}
                </h3>
                <p class="text-gray-500 text-sm mb-2">{{ item.productBrand }} - {{ item.productSku }}</p>
                <div class="flex items-center space-x-4 mb-2">
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(item.productPrice) }}</span>
                  <span 
                    v-if="item.originalPrice && item.originalPrice > item.productPrice"
                    class="text-sm text-gray-500 line-through"
                  >
                    Rp {{ formatPrice(item.originalPrice) }}
                  </span>
                  <span v-if="item.savings && item.savings > 0" class="text-sm text-green-600 font-medium">
                    Save Rp {{ formatPrice(item.savings) }}
                  </span>
                </div>
                <!-- Stock Status -->
                <div class="flex items-center space-x-2">
                  <span 
                    :class="getStockBadgeClass(item)"
                    class="text-xs px-2 py-1 rounded-full"
                  >
                    {{ getStockText(item) }}
                  </span>
                  <span v-if="item.availableStock < 10 && item.availableStock > 0" class="text-xs text-orange-600">
                    Only {{ item.availableStock }} left
                  </span>
                  <span class="text-xs text-gray-500">
                    Saved {{ formatDate(item.createdAt) }}
                  </span>
                </div>
              </div>
              <div class="flex flex-col items-end space-y-2">
                <div class="flex items-center space-x-2">
                  <div class="flex items-center border rounded-lg">
                    <button 
                      @click="decreaseQuantity(item.id, item.quantity)"
                      class="p-2 hover:bg-gray-100"
                      :disabled="item.quantity <= 1 || savedItemsStore.isLoading"
                    >
                      <i class="fa-solid fa-minus text-sm"></i>
                    </button>
                    <span class="px-4 py-2 border-x">{{ item.quantity }}</span>
                    <button 
                      @click="increaseQuantity(item.id, item.quantity)"
                      class="p-2 hover:bg-gray-100"
                      :disabled="savedItemsStore.isLoading"
                    >
                      <i class="fa-solid fa-plus text-sm"></i>
                    </button>
                  </div>
                  <button 
                    @click="removeItem(item.id)"
                    class="text-red-500 hover:text-red-700 p-2"
                    title="Remove from saved items"
                    :disabled="savedItemsStore.isLoading"
                  >
                    <i class="fa-solid fa-trash"></i>
                  </button>
                </div>
                <button 
                  @click="moveToCart(item.id)"
                  :disabled="!item.isProductActive || item.availableStock === 0 || savedItemsStore.isLoading"
                  class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed text-sm"
                  title="Move to cart"
                >
                  <i class="fa-solid fa-shopping-cart mr-1"></i>
                  Move to Cart
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Summary Sidebar -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-sm border p-6 sticky top-8">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Saved Items Summary</h3>
            
            <!-- Stats -->
            <div class="space-y-4 mb-6">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Total Items</span>
                <span class="text-gray-900 font-medium">{{ savedItemsStore.itemCount }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Total Quantity</span>
                <span class="text-gray-900 font-medium">{{ savedItemsStore.totalQuantity }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Total Value</span>
                <span class="text-gray-900 font-medium">Rp {{ formatPrice(savedItemsStore.totalValue) }}</span>
              </div>
              <div v-if="savedItemsStore.totalSavings > 0" class="flex justify-between text-sm">
                <span class="text-green-600">Total Savings</span>
                <span class="text-green-600 font-medium">Rp {{ formatPrice(savedItemsStore.totalSavings) }}</span>
              </div>
            </div>
            
            <!-- Actions -->
            <div class="space-y-3">
              <button 
                @click="moveAllToCart"
                :disabled="savedItemsStore.isLoading || !hasAvailableItems"
                class="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <i class="fa-solid fa-shopping-cart mr-2"></i>
                Move All to Cart
              </button>
              
              <router-link 
                to="/cart"
                class="w-full bg-gray-100 text-gray-700 py-3 rounded-lg font-medium hover:bg-gray-200 transition-colors flex items-center justify-center"
              >
                <i class="fa-solid fa-shopping-cart mr-2"></i>
                View Cart
              </router-link>
              
              <router-link 
                to="/products"
                class="w-full text-blue-600 py-2 text-sm hover:text-blue-700 transition-colors flex items-center justify-center"
              >
                <i class="fa-solid fa-search mr-2"></i>
                Continue Shopping
              </router-link>
            </div>
            
            <!-- Tips -->
            <div class="mt-6 p-4 bg-blue-50 rounded-lg">
              <h4 class="text-sm font-medium text-blue-900 mb-2">💡 Tips</h4>
              <ul class="text-xs text-blue-700 space-y-1">
                <li>• Items stay saved until you remove them</li>
                <li>• Stock levels are checked in real-time</li>
                <li>• Prices may change over time</li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-else class="text-center py-16">
        <i class="fa-solid fa-bookmark text-6xl text-gray-300 mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-900 mb-2">No saved items yet</h3>
        <p class="text-gray-500 mb-6">Save items from your cart or product pages to view them here</p>
        <div class="flex justify-center space-x-4">
          <router-link 
            to="/products"
            class="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors inline-flex items-center"
          >
            <i class="fa-solid fa-search mr-2"></i>
            Browse Products
          </router-link>
          <router-link 
            to="/cart"
            class="bg-gray-100 text-gray-700 px-6 py-3 rounded-lg hover:bg-gray-200 transition-colors inline-flex items-center"
          >
            <i class="fa-solid fa-shopping-cart mr-2"></i>
            View Cart
          </router-link>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useSavedItemsStore } from '@/stores/savedItems'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useNotifications } from '@/composables/useNotifications'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()
const savedItemsStore = useSavedItemsStore()
const cartStore = useCartStore()
const authStore = useAuthStore()
// const { success, warning, error } = useNotifications()

// Computed
const hasAvailableItems = computed(() => {
  return savedItemsStore.items.some(item => item.isProductActive && item.availableStock > 0)
})

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffTime = Math.abs(now.getTime() - date.getTime())
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24))
  
  if (diffDays === 1) return 'yesterday'
  if (diffDays < 7) return `${diffDays} days ago`
  if (diffDays < 30) return `${Math.ceil(diffDays / 7)} weeks ago`
  return date.toLocaleDateString('id-ID')
}

const navigateToProduct = (productId: number) => {
  router.push(`/product/${productId}`)
}

const increaseQuantity = async (itemId: number, currentQuantity: number) => {
  try {
    await savedItemsStore.updateQuantity(itemId, currentQuantity + 1)
  } catch (err) {
    console.error('Error updating quantity:', err)
  }
}

const decreaseQuantity = async (itemId: number, currentQuantity: number) => {
  if (currentQuantity <= 1) return
  
  try {
    await savedItemsStore.updateQuantity(itemId, currentQuantity - 1)
  } catch (err) {
    console.error('Error updating quantity:', err)
  }
}

const removeItem = async (itemId: number) => {
  const item = savedItemsStore.items.find(item => item.id === itemId)
  const itemName = item?.productName || 'this item'
  
  const confirmed = confirm(`Are you sure you want to remove "${itemName}" from saved items?`)
  if (!confirmed) return
  
  try {
    await savedItemsStore.removeItem(itemId)
  } catch (err) {
    console.error('Error removing item:', err)
  }
}

const moveToCart = async (itemId: number) => {
  try {
    await savedItemsStore.moveToCart(itemId)
    // Refresh cart to show the new item
    await cartStore.fetchCartItems()
  } catch (err) {
    console.error('Error moving item to cart:', err)
  }
}

const moveAllToCart = async () => {
  const confirmed = confirm('Are you sure you want to move all saved items to your cart?')
  if (!confirmed) return
  
  try {
    await savedItemsStore.moveAllToCart()
    // Refresh cart to show the new items
    await cartStore.fetchCartItems()
  } catch (err) {
    console.error('Error moving all items to cart:', err)
  }
}

const clearAllItems = async () => {
  const confirmed = confirm('Are you sure you want to remove all saved items? This action cannot be undone.')
  if (!confirmed) return
  
  try {
    await savedItemsStore.clearSavedItems()
  } catch (err) {
    console.error('Error clearing saved items:', err)
  }
}

const getStockBadgeClass = (item: any) => {
  if (!item.isProductActive) {
    return 'bg-gray-100 text-gray-800'
  }
  if (item.availableStock === 0) {
    return 'bg-red-100 text-red-800'
  }
  if (item.availableStock < 10) {
    return 'bg-orange-100 text-orange-800'
  }
  return 'bg-green-100 text-green-800'
}

const getStockText = (item: any) => {
  if (!item.isProductActive) {
    return 'Inactive'
  }
  if (item.availableStock === 0) {
    return 'Out of Stock'
  }
  if (item.availableStock < 10) {
    return 'Low Stock'
  }
  return 'In Stock'
}

// Initialize on mount
onMounted(async () => {
  if (authStore.isAuthenticated && authStore.user) {
    await savedItemsStore.fetchSavedItems()
  }
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
