<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <header class="bg-white shadow-sm border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-8">
            <div class="flex items-center space-x-2">
              <i class="fa-solid fa-store text-blue-600 text-xl"></i>
              <span class="text-xl font-bold text-gray-900">Autowarehouse</span>
            </div>
            <nav class="hidden md:flex space-x-6">
              <router-link to="/" class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Home</router-link>
              <router-link to="/products" class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Categories</router-link>
              <span class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Deals</span>
              <span class="text-gray-600 hover:text-blue-600 transition-colors cursor-pointer">Wishlist</span>
            </nav>
          </div>
          <div class="flex items-center space-x-4">
            <div class="relative">
              <input 
                type="text" 
                placeholder="Search products..." 
                class="w-64 pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                v-model="searchQuery"
              >
              <i class="fa-solid fa-search absolute left-3 top-3 text-gray-400"></i>
            </div>
            <button class="relative p-2 text-blue-600 hover:text-blue-700">
              <i class="fa-solid fa-shopping-cart text-lg"></i>
              <span class="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">{{ cartItems.length }}</span>
            </button>
            <div class="w-8 h-8 bg-blue-500 rounded-full flex items-center justify-center">
              <i class="fa-solid fa-user text-white text-sm"></i>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Breadcrumb -->
      <nav class="mb-6">
        <div class="flex items-center space-x-2 text-sm text-gray-500">
          <router-link to="/" class="hover:text-blue-600 cursor-pointer">Home</router-link>
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
            :class="{ 'opacity-50 transform translate-x-[-10px]': item.removing }"
          >
            <div class="flex items-center space-x-6">
              <div class="flex-shrink-0">
                <div class="w-20 h-20 bg-gray-100 rounded-lg flex items-center justify-center">
                  <i :class="item.icon" class="text-2xl text-gray-400"></i>
                </div>
              </div>
              <div class="flex-1 min-w-0">
                <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ item.name }}</h3>
                <p class="text-gray-500 text-sm mb-2">{{ item.description }}</p>
                <div class="flex items-center space-x-4">
                  <span class="text-lg font-bold text-gray-900">Rp {{ item.price.toLocaleString() }}</span>
                  <span v-if="item.originalPrice" class="text-sm text-gray-500 line-through">Rp {{ item.originalPrice.toLocaleString() }}</span>
                </div>
              </div>
              <div class="flex items-center space-x-4">
                <div class="flex items-center border rounded-lg">
                  <button 
                    class="p-2 hover:bg-gray-100 transition-colors" 
                    @click="updateQuantity(item.id, item.quantity - 1)"
                    :disabled="item.quantity <= 1"
                  >
                    <i class="fa-solid fa-minus text-sm"></i>
                  </button>
                  <span class="px-4 py-2 border-x min-w-[3rem] text-center">{{ item.quantity }}</span>
                  <button 
                    class="p-2 hover:bg-gray-100 transition-colors" 
                    @click="updateQuantity(item.id, item.quantity + 1)"
                  >
                    <i class="fa-solid fa-plus text-sm"></i>
                  </button>
                </div>
                <button 
                  class="text-red-500 hover:text-red-700 p-2 transition-colors" 
                  @click="removeItem(item.id)"
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
                  class="text-blue-600 text-sm hover:text-blue-700 transition-colors"
                  @click="toggleCoupon"
                >
                  <i class="fa-solid fa-tag mr-1"></i>Apply
                </button>
              </div>
              <div v-if="showCouponInput" class="space-y-3">
                <div class="flex space-x-2">
                  <input 
                    type="text" 
                    placeholder="Enter coupon code" 
                    v-model="couponCode"
                    class="flex-1 px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                  >
                  <button 
                    class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700 transition-colors"
                    @click="applyCoupon"
                  >
                    Apply
                  </button>
                </div>
              </div>
              <div v-if="appliedCoupon" class="flex items-center justify-between text-sm">
                <span class="text-green-600">{{ appliedCoupon }} applied</span>
                <button 
                  class="text-red-500 hover:text-red-700 transition-colors"
                  @click="removeCoupon"
                >
                  Remove
                </button>
              </div>
            </div>
            
            <!-- Price Breakdown -->
            <div class="space-y-3 mb-6">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal ({{ totalItems }} items)</span>
                <span class="text-gray-900">Rp {{ subtotal.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Shipping</span>
                <span class="text-gray-900">Rp {{ shipping.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Tax</span>
                <span class="text-gray-900">Rp {{ tax.toLocaleString() }}</span>
              </div>
              <div v-if="discount > 0" class="flex justify-between text-sm">
                <span class="text-green-600">Coupon Discount</span>
                <span class="text-green-600">-Rp {{ discount.toLocaleString() }}</span>
              </div>
              <div class="border-t pt-3">
                <div class="flex justify-between">
                  <span class="text-lg font-semibold text-gray-900">Total</span>
                  <span class="text-lg font-bold text-gray-900">Rp {{ total.toLocaleString() }}</span>
                </div>
              </div>
            </div>
            
            <!-- Checkout Button -->
            <button 
              class="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors mb-4"
              @click="proceedToCheckout"
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
              <li><span class="hover:text-white cursor-pointer">Gaming</span></li>
              <li><span class="hover:text-white cursor-pointer">Components</span></li>
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
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Reactive data
const searchQuery = ref('')
const showCouponInput = ref(false)
const couponCode = ref('')
const appliedCoupon = ref('')

// Cart items
const cartItems = reactive([
  {
    id: 1,
    name: 'Sony WH-1000XM4 Wireless Headphones',
    description: 'Noise Cancelling, Bluetooth',
    price: 5250000,
    originalPrice: 6000000,
    quantity: 1,
    icon: 'fa-solid fa-headphones',
    removing: false
  },
  {
    id: 2,
    name: 'iPhone 15 Pro Max',
    description: '256GB, Titanium Blue',
    price: 18000000,
    originalPrice: null,
    quantity: 1,
    icon: 'fa-solid fa-mobile-screen',
    removing: false
  },
  {
    id: 3,
    name: 'AirPods Pro (2nd Generation)',
    description: 'Active Noise Cancellation',
    price: 3750000,
    originalPrice: 4200000,
    quantity: 2,
    icon: 'fa-solid fa-headphones-simple',
    removing: false
  }
])

// Computed properties
const totalItems = computed(() => {
  return cartItems.reduce((total, item) => total + item.quantity, 0)
})

const subtotal = computed(() => {
  return cartItems.reduce((total, item) => total + (item.price * item.quantity), 0)
})

const shipping = computed(() => {
  return subtotal.value > 10000000 ? 0 : 150000 // Free shipping over 10M
})

const tax = computed(() => {
  return Math.round(subtotal.value * 0.11) // 11% tax
})

const discount = computed(() => {
  if (appliedCoupon.value === 'SAVE20') {
    return Math.round(subtotal.value * 0.2) // 20% discount
  }
  return 0
})

const total = computed(() => {
  return subtotal.value + shipping.value + tax.value - discount.value
})

// Methods
const updateQuantity = (itemId: number, newQuantity: number) => {
  if (newQuantity < 1) return
  
  const item = cartItems.find(item => item.id === itemId)
  if (item) {
    item.quantity = newQuantity
  }
}

const removeItem = (itemId: number) => {
  const item = cartItems.find(item => item.id === itemId)
  if (item) {
    item.removing = true
    setTimeout(() => {
      const index = cartItems.findIndex(item => item.id === itemId)
      if (index > -1) {
        cartItems.splice(index, 1)
      }
    }, 300)
  }
}

const toggleCoupon = () => {
  showCouponInput.value = !showCouponInput.value
}

const applyCoupon = () => {
  if (couponCode.value.toUpperCase() === 'SAVE20') {
    appliedCoupon.value = 'SAVE20'
    showCouponInput.value = false
    couponCode.value = ''
  } else {
    alert('Invalid coupon code')
  }
}

const removeCoupon = () => {
  appliedCoupon.value = ''
  showCouponInput.value = false
}

const proceedToCheckout = () => {
  router.push('/checkout')
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}

/* Smooth transitions for cart items */
.transition-all {
  transition: all 0.3s ease;
}

/* Hover effects */
button:hover {
  transform: translateY(-1px);
}

button:active {
  transform: translateY(0);
}
</style>
