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
        <h1 class="text-3xl font-bold text-gray-900 mb-2">Shopping Cart</h1>
        <p class="text-gray-600">Review your items before checkout</p>
      </div>

      <!-- Cart Content -->
      <div v-if="cartItems.length > 0" class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Cart Items -->
        <div class="lg:col-span-2 space-y-6">
          <div 
            v-for="item in cartItems" 
            :key="item.id"
            class="bg-white rounded-xl shadow-sm border p-6 transition-all duration-300"
          >
            <div class="flex items-center space-x-6">
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
                <div class="flex items-center space-x-4">
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(item.productPrice) }}</span>
                  <span 
                    v-if="item.originalPrice && item.originalPrice > item.productPrice"
                    class="text-sm text-gray-500 line-through"
                  >
                    Rp {{ formatPrice(item.originalPrice) }}
                  </span>
                </div>
              </div>
              <div class="flex items-center space-x-4">
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
                >
                  <i class="fa-solid fa-trash"></i>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-sm border p-6 sticky top-8">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Order Summary</h3>
            
            <!-- Coupon Section -->
            <div class="mb-6 p-4 bg-gray-50 rounded-lg">
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
            </div>
            
            <!-- Price Breakdown -->
            <div class="space-y-3 mb-6">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal ({{ totalItems }} items)</span>
                <span class="text-gray-900">Rp {{ formatPrice(subtotal) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Shipping</span>
                <span class="text-gray-900">Rp {{ formatPrice(shippingCost) }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Tax</span>
                <span class="text-gray-900">Rp {{ formatPrice(tax) }}</span>
              </div>
              <div v-if="discount > 0" class="flex justify-between text-sm">
                <span class="text-green-600">Coupon Discount</span>
                <span class="text-green-600">-Rp {{ formatPrice(discount) }}</span>
              </div>
              <div class="border-t pt-3">
                <div class="flex justify-between">
                  <span class="text-lg font-semibold text-gray-900">Total</span>
                  <span class="text-lg font-bold text-gray-900">Rp {{ formatPrice(total) }}</span>
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
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

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
    await cartStore.fetchCartItems()
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

// Methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const increaseQuantity = async (itemId: number) => {
  const item = cartItems.value.find(item => item.id === itemId)
  if (item) {
    try {
      await cartStore.updateQuantity(itemId, item.quantity + 1)
    } catch (error) {
      console.error('Error updating quantity:', error)
    }
  }
}

const decreaseQuantity = async (itemId: number) => {
  const item = cartItems.value.find(item => item.id === itemId)
  if (item && item.quantity > 1) {
    try {
      await cartStore.updateQuantity(itemId, item.quantity - 1)
    } catch (error) {
      console.error('Error updating quantity:', error)
    }
  }
}

const removeItem = async (itemId: number) => {
  try {
    await cartStore.removeItem(itemId)
  } catch (error) {
    console.error('Error removing item:', error)
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

const proceedToCheckout = () => {
  // Implement checkout logic
  console.log('Proceeding to checkout...')
  router.push('/checkout')
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
