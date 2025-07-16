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
          <span class="text-gray-900">Shopping Cart</span>
        </div>
      </nav>

      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl font-bold text-gray-900 mb-2">Shopping Cart</h1>
            <div class="flex items-center space-x-4">
              <p class="text-gray-600">Review your items before checkout</p>
              <!-- Online/Offline Status -->
              <div class="flex items-center space-x-2">
                <div 
                  :class="isOnline ? 'bg-green-500' : 'bg-red-500'"
                  class="w-2 h-2 rounded-full"
                ></div>
                <span class="text-xs text-gray-500">
                  {{ isOnline ? 'Online' : 'Offline' }}
                </span>
              </div>
              <!-- Loading Indicator -->
              <div v-if="cartStore.isLoading" class="flex items-center space-x-2">
                <div class="animate-spin rounded-full h-4 w-4 border-b-2 border-blue-600"></div>
                <span class="text-xs text-gray-500">Syncing...</span>
              </div>
            </div>
          </div>
          <div v-if="cartItems.length > 0" class="flex items-center space-x-4">
            <label class="flex items-center space-x-2 cursor-pointer">
              <input 
                type="checkbox" 
                :checked="allItemsSelected"
                @change="toggleSelectAll"
                class="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                :disabled="cartStore.isLoading"
              >
              <span class="text-sm text-gray-700">Select All ({{ cartItems.length }} items)</span>
            </label>
          </div>
        </div>
      </div>

      <!-- Cart Content -->
      <div v-if="cartItems.length > 0" class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Cart Items -->
        <div class="lg:col-span-2 space-y-6">
          <div 
            v-for="item in cartItems" 
            :key="item.id"
            class="bg-white rounded-xl shadow-sm border p-6 transition-all duration-300 cursor-pointer"
            :class="{ 'ring-2 ring-blue-500 bg-blue-50': item.isSelected }"
            @click="navigateToProduct(item.productId, $event)"
          >
            <div class="flex items-center space-x-6">
              <div class="flex-shrink-0">
                <label class="flex items-center cursor-pointer" @click.stop>
                  <input 
                    type="checkbox" 
                    :checked="item.isSelected"
                    @change="toggleItemSelection(item.id)"
                    class="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                  >
                </label>
              </div>
              <div class="flex-shrink-0">
                <img 
                  :src="item.productImages?.[0] || '/placeholder-product.jpg'" 
                  :alt="item.productName"
                  class="w-20 h-20 rounded-lg object-cover"
                >
              </div>
              <div class="flex-1 min-w-0">
                <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ item.productName }}</h3>
                <p class="text-gray-500 text-sm mb-2">{{ item.productBrand }} - {{ item.productSku }}</p>
                <div class="flex items-center space-x-4 mb-2">
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(item.productPrice) }}</span>
                  <span 
                    v-if="item.originalPrice && item.originalPrice > item.productPrice"
                    class="text-sm text-gray-500 line-through"
                  >
                    Rp {{ formatPrice(item.originalPrice) }}
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
                  <span v-if="item.quantity > item.availableStock" class="text-xs text-red-600">
                    <i class="fa-solid fa-exclamation-triangle mr-1"></i>
                    Exceeds available stock
                  </span>
                </div>
              </div>
              <div class="flex flex-col items-end space-y-2">
                <div class="flex items-center space-x-2">
                  <div class="flex items-center border rounded-lg">
                    <button 
                      @click="decreaseQuantity(item.id)"
                      class="p-2 hover:bg-gray-100"
                      :disabled="item.quantity <= 1"
                    >
                      <i class="fa-solid fa-minus text-sm"></i>
                    </button>
                    <span class="px-4 py-2 border-x">{{ item.quantity }}</span>
                    <button 
                      @click="increaseQuantity(item.id)"
                      class="p-2 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-plus text-sm"></i>
                    </button>
                  </div>
                  <button 
                    @click="removeItem(item.id)"
                    class="text-red-500 hover:text-red-700 p-2"
                    title="Remove from cart"
                  >
                    <i class="fa-solid fa-trash"></i>
                  </button>
                </div>
                <!-- <button 
                  @click="saveForLater(item.id)"
                  class="text-blue-600 hover:text-blue-700 text-sm px-2 py-1 rounded transition-colors"
                  title="Save for later"
                  :disabled="savedItemsStore.isLoading"
                >
                  <i class="fa-solid fa-bookmark mr-1"></i>
                  Save for Later
                </button> -->
              </div>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-sm border p-6 sticky top-8">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Order Summary</h3>
            
            <!-- Coupon Section -->
            <!-- <div class="mb-6 p-4 bg-gray-50 rounded-lg">
              <div class="flex items-center justify-between mb-3">
                <span class="text-sm font-medium text-gray-700">Have a coupon?</span>
                <button 
                  @click="toggleCouponInput"
                  class="text-blue-600 text-sm hover:text-blue-700"
                >
                  <i class="fa-solid fa-tag mr-1"></i>Apply
                </button>
              </div>
              <div v-if="showCouponInput && !couponApplied" class="space-y-2">
                <div class="flex space-x-2">
                  <input 
                    type="text" 
                    placeholder="Enter coupon code" 
                    v-model="couponCode"
                    class="flex-1 px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                  <button 
                    @click="applyCoupon"
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700"
                  >
                    Apply
                  </button>
                </div>
                <p v-if="couponError" class="text-red-500 text-xs">{{ couponError }}</p>
              </div>
              <div v-if="couponApplied" class="flex items-center justify-between text-sm">
                <span class="text-green-600">{{ appliedCoupon }} applied</span>
                <button 
                  @click="removeCoupon"
                  class="text-red-500 hover:text-red-700"
                >
                  Remove
                </button>
              </div>
            </div> -->
            
            <!-- Selected Items Info -->
            <div v-if="selectedItems.length > 0" class="mb-4 p-3 bg-blue-50 rounded-lg">
              <div class="flex items-center justify-between text-sm">
                <span class="text-blue-700 font-medium">Selected for checkout:</span>
                <span class="text-blue-900 font-semibold">{{ selectedItems.length }} of {{ cartItems.length }} items</span>
              </div>
            </div>

            <!-- Price Breakdown -->
            <div class="space-y-3 mb-6">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal ({{ selectedItems.length }} selected items)</span>
                <span class="text-gray-900">Rp {{ formatPrice(selectedSubtotal) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Shipping</span>
                <span class="text-gray-900">Rp {{ formatPrice(selectedShippingCost) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Tax</span>
                <span class="text-gray-900">Rp {{ formatPrice(selectedTax) }}</span>
              </div>
              <div v-if="selectedSavings > 0" class="flex justify-between text-sm">
                <span class="text-green-600">You Save</span>
                <span class="text-green-600">-Rp {{ formatPrice(selectedSavings) }}</span>
              </div>
              <div v-if="discount > 0" class="flex justify-between text-sm">
                <span class="text-green-600">Coupon Discount</span>
                <span class="text-green-600">-Rp {{ formatPrice(discount) }}</span>
              </div>
              <div class="border-t pt-3">
                <div class="flex justify-between">
                  <span class="text-lg font-semibold text-gray-900">Total</span>
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(selectedTotal) }}</span>
                </div>
              </div>
            </div>
            
            <!-- Checkout Button -->
            <button 
              @click="proceedToCheckout"
              class="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors mb-4"
            >
              <i class="fa-solid fa-lock mr-2"></i>
              Proceed to Checkout
            </button>
            
            <!-- Continue Shopping -->
            <router-link 
              to="/products"
              class="w-full text-blue-600 py-2 text-sm hover:text-blue-700 transition-colors flex items-center justify-center"
            >
              <i class="fa-solid fa-arrow-left mr-2"></i>
              Continue Shopping
            </router-link>
            
            <!-- Security Badge -->
            <div class="mt-6 text-center">
              <div class="flex items-center justify-center space-x-2 text-xs text-gray-500">
                <i class="fa-solid fa-shield-halved text-green-500"></i>
                <span>Secure checkout with 256-bit SSL encryption</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty Cart State -->
      <div v-else class="text-center py-16">
        <i class="fa-solid fa-shopping-cart text-6xl text-gray-300 mb-4"></i>
        <h3 class="text-xl font-semibold text-gray-900 mb-2">Your cart is empty</h3>
        <p class="text-gray-500 mb-6">Add some products to get started!</p>
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
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useSavedItemsStore } from '@/stores/savedItems'
import { useNotifications } from '@/composables/useNotifications'
import { useCartPersistence } from '@/composables/useCartPersistence'
import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()
const savedItemsStore = useSavedItemsStore()

// Initialize cart persistence
const { isOnline, startAutoSync, syncCartWithServer, loadPendingOperations } = useCartPersistence()

// State
const searchQuery = ref('')
const showCouponInput = ref(false)
const couponCode = ref('')
const couponError = ref('')
const couponApplied = ref(false)
const appliedCoupon = ref('')

// Initialize cart on mount
onMounted(async () => {
  if (authStore.isAuthenticated && authStore.user) {
    // Load pending operations first
    loadPendingOperations()
    
    // Sync cart with server
    await syncCartWithServer()
    
    // Start auto-sync
    const stopAutoSync = startAutoSync()
    
    // Cleanup on unmount
    return stopAutoSync
  }
})

// Get cart items from store
const cartItems = computed(() => cartStore.items)

// Computed properties
const subtotal = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + (item.productPrice * item.quantity), 0)
})

const totalItems = computed(() => {
  return cartItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

const shippingCost = computed(() => {
  return subtotal.value > 10000000 ? 0 : 50000 // Free shipping over 10M
})

const tax = computed(() => {
  return Math.round(subtotal.value * 0.11) // 11% tax
})

const discount = computed(() => {
  if (couponApplied.value) {
    return Math.round(subtotal.value * 0.2) // 20% discount
  }
  return 0
})

const total = computed(() => {
  return subtotal.value + shippingCost.value + tax.value - discount.value
})

// Selection computed properties
const allItemsSelected = computed(() => {
  return cartItems.value.length > 0 && cartItems.value.every(item => item.isSelected)
})

const selectedItems = computed(() => {
  return cartItems.value.filter(item => item.isSelected)
})

// Selected items calculations
const selectedSubtotal = computed(() => {
  return selectedItems.value.reduce((sum, item) => sum + (item.productPrice * item.quantity), 0)
})

const selectedShippingCost = computed(() => {
  return selectedSubtotal.value > 10000000 ? 0 : 50000 // Free shipping over 10M
})

const selectedTax = computed(() => {
  return Math.round(selectedSubtotal.value * 0.11) // 11% tax
})

const selectedSavings = computed(() => {
  return selectedItems.value.reduce((sum, item) => sum + (item.savings || 0), 0)
})

const selectedTotal = computed(() => {
  return selectedSubtotal.value + selectedShippingCost.value + selectedTax.value - selectedSavings.value - discount.value
})

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const toggleSelectAll = async () => {
  if (!authStore.user?.id) return
  
  try {
    const selectAll = !allItemsSelected.value
    await cartStore.selectAll(selectAll)
  } catch (error) {
    console.error('Error toggling select all:', error)
  }
}

const toggleItemSelection = async (itemId: number) => {
  try {
    await cartStore.toggleSelection(itemId)
  } catch (error) {
    console.error('Error toggling item selection:', error)
  }
}

const navigateToProduct = (productId: number, event: Event) => {
  // Check if the click was on a button or interactive element
  const target = event.target as HTMLElement
  if (target.closest('button') || target.closest('input') || target.closest('label')) {
    // If clicked on a button, input, or label, don't navigate
    event.stopPropagation()
    return
  }
  
  // Navigate to product detail page
  router.push(`/product/${productId}`)
}

const increaseQuantity = async (itemId: number) => {
  const { warning, error } = useNotifications()
  const item = cartItems.value.find(item => item.id === itemId)
  
  if (item) {
    // Check stock availability before increasing
    if (item.quantity >= item.availableStock) {
      warning('Stock Limit Reached', `Cannot add more items. Only ${item.availableStock} available in stock.`)
      return
    }
    
    try {
      await cartStore.updateQuantity(itemId, item.quantity + 1)
    } catch (err) {
      console.error('Error updating quantity:', err)
      error('Update Failed', 'Failed to update quantity. Please try again.')
    }
  }
}

const decreaseQuantity = async (itemId: number) => {
  const { error } = useNotifications()
  const item = cartItems.value.find(item => item.id === itemId)
  
  if (item && item.quantity > 1) {
    try {
      await cartStore.updateQuantity(itemId, item.quantity - 1)
    } catch (err) {
      console.error('Error updating quantity:', err)
      error('Update Failed', 'Failed to update quantity. Please try again.')
    }
  }
}

const removeItem = async (itemId: number) => {
  const { warning, success } = useNotifications()
  
  // Get item name for confirmation
  const item = cartItems.value.find(item => item.id === itemId)
  const itemName = item?.productName || 'this item'
  
  try {
    await cartStore.removeItem(itemId)
    success('Item Removed', `"${itemName}" has been removed from your cart`)
  } catch (error) {
    console.error('Error removing item:', error)
    warning('Remove Failed', 'Failed to remove item from cart. Please try again.')
  }
}

const saveForLater = async (itemId: number) => {
  try {
    // Move cart item to saved items
    await savedItemsStore.moveToSaved(itemId)
    
    // Refresh cart items to reflect the removal
    if (authStore.user) {
      await cartStore.fetchCartItems()
    }
  } catch (error) {
    console.error('Error saving item for later:', error)
  }
}

const toggleCouponInput = () => {
  showCouponInput.value = !showCouponInput.value
}

const applyCoupon = () => {
  couponError.value = ''
  
  if (couponCode.value.toLowerCase() === 'save20') {
    couponApplied.value = true
    appliedCoupon.value = couponCode.value.toUpperCase()
    showCouponInput.value = false
    couponCode.value = ''
  } else {
    couponError.value = 'Invalid coupon code'
  }
}

const removeCoupon = () => {
  couponApplied.value = false
  appliedCoupon.value = ''
  showCouponInput.value = false
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

const proceedToCheckout = () => {
  // Check if any selected items have stock issues
  const hasStockIssues = selectedItems.value.some(item => 
    !item.isProductActive || 
    item.availableStock === 0 || 
    item.quantity > item.availableStock
  )
  
  if (hasStockIssues) {
    const { warning } = useNotifications()
    warning('Stock Issues', 'Some items in your cart have stock issues. Please review and update your cart.')
    return
  }
  
  if (selectedItems.value.length === 0) {
    const { warning } = useNotifications()
    warning('No Items Selected', 'Please select items to checkout.')
    return
  }
  
  // Proceed to checkout
  console.log('Proceeding to checkout...')
  router.push('/checkout')
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
