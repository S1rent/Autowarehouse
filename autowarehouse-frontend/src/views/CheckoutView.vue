<template>
  <div class="min-h-screen bg-gray-50 font-inter">
    <UserNavbar />

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Breadcrumb -->
      <nav class="mb-6">
        <div class="flex items-center space-x-2 text-sm text-gray-500">
          <router-link to="/" class="hover:text-blue-600">Home</router-link>
          <i class="fa-solid fa-chevron-right text-xs"></i>
          <router-link to="/cart" class="hover:text-blue-600">Cart</router-link>
          <i class="fa-solid fa-chevron-right text-xs"></i>
          <span class="text-gray-900">Checkout</span>
        </div>
      </nav>

      <!-- Page Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">Checkout</h1>
        <p class="text-gray-600">Complete your purchase securely</p>
      </div>

      <!-- Checkout Progress -->
      <div class="mb-8">
        <div class="flex items-center justify-center space-x-4">
          <div class="flex items-center">
            <div class="w-8 h-8 bg-green-500 text-white rounded-full flex items-center justify-center text-sm">
              <i class="fa-solid fa-check"></i>
            </div>
            <span class="ml-2 text-sm font-medium text-green-600">Cart</span>
          </div>
          <div class="w-12 h-0.5 bg-green-500"></div>
          <div class="flex items-center">
            <div class="w-8 h-8 bg-blue-600 text-white rounded-full flex items-center justify-center text-sm">2</div>
            <span class="ml-2 text-sm font-medium text-blue-600">Checkout</span>
          </div>
          <div class="w-12 h-0.5 bg-gray-300"></div>
          <div class="flex items-center">
            <div class="w-8 h-8 bg-gray-300 text-gray-500 rounded-full flex items-center justify-center text-sm">3</div>
            <span class="ml-2 text-sm font-medium text-gray-500">Payment</span>
          </div>
        </div>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Checkout Form -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Shipping Address -->
          <div class="bg-white rounded-xl shadow-sm border p-6">
            <h3 class="text-xl font-semibold text-gray-900 mb-6 flex items-center">
              <i class="fa-solid fa-truck mr-3 text-blue-600"></i>
              Shipping Address
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">First Name</label>
                <input 
                  v-model="shippingForm.firstName"
                  type="text" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="John"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Last Name</label>
                <input 
                  v-model="shippingForm.lastName"
                  type="text" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="Doe"
                >
              </div>
              <div class="md:col-span-2">
                <label class="block text-sm font-medium text-gray-700 mb-2">Street Address</label>
                <input 
                  v-model="shippingForm.address"
                  type="text" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="123 Main Street"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">City</label>
                <input 
                  v-model="shippingForm.city"
                  type="text" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="Jakarta"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Province</label>
                <select 
                  v-model="shippingForm.province"
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Select Province</option>
                  <option value="jakarta">DKI Jakarta</option>
                  <option value="jabar">Jawa Barat</option>
                  <option value="jateng">Jawa Tengah</option>
                  <option value="jatim">Jawa Timur</option>
                </select>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Postal Code</label>
                <input 
                  v-model="shippingForm.postalCode"
                  type="text" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="12345"
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Phone Number</label>
                <input 
                  v-model="shippingForm.phone"
                  type="tel" 
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500" 
                  placeholder="+62 812-3456-7890"
                >
              </div>
            </div>
          </div>

          <!-- Shipping Options -->
          <div class="bg-white rounded-xl shadow-sm border p-6">
            <h3 class="text-xl font-semibold text-gray-900 mb-6 flex items-center">
              <i class="fa-solid fa-shipping-fast mr-3 text-blue-600"></i>
              Shipping Options
            </h3>
            <div class="space-y-4">
              <label 
                v-for="option in shippingOptions" 
                :key="option.id"
                class="flex items-center p-4 border border-gray-300 rounded-lg cursor-pointer hover:border-blue-500 transition-colors"
                :class="{ 'border-blue-500 bg-blue-50': selectedShipping === option.id }"
              >
                <input 
                  v-model="selectedShipping"
                  :value="option.id"
                  type="radio" 
                  name="shipping" 
                  class="text-blue-600 focus:ring-blue-500"
                >
                <div class="ml-4 flex-1">
                  <div class="flex justify-between items-center">
                    <div>
                      <h4 class="font-medium text-gray-900">{{ option.name }}</h4>
                      <p class="text-sm text-gray-500">{{ option.duration }}</p>
                    </div>
                    <span class="font-medium text-gray-900">Rp {{ option.price.toLocaleString() }}</span>
                  </div>
                </div>
              </label>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-sm border p-6 sticky top-8">
            <h3 class="text-xl font-semibold text-gray-900 mb-6">Order Summary</h3>
            
            <!-- Product Summary -->
            <div class="space-y-4 mb-6">
              <div 
                v-for="item in cartItems" 
                :key="item.id"
                class="flex items-center space-x-3"
              >
                <img 
                  :src="item.image" 
                  :alt="item.name"
                  class="w-12 h-12 rounded-lg object-cover"
                >
                <div class="flex-1 min-w-0">
                  <h4 class="text-sm font-medium text-gray-900 truncate">{{ item.name }}</h4>
                  <p class="text-xs text-gray-500">Qty: {{ item.quantity }}</p>
                </div>
                <span class="text-sm font-medium text-gray-900">Rp {{ (item.price * item.quantity).toLocaleString() }}</span>
              </div>
            </div>
            
            <!-- Price Breakdown -->
            <div class="space-y-3 mb-6 border-t pt-4">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal ({{ totalItems }} items)</span>
                <span class="text-gray-900">Rp {{ subtotal.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Shipping</span>
                <span class="text-gray-900">Rp {{ shippingCost.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Tax</span>
                <span class="text-gray-900">Rp {{ tax.toLocaleString() }}</span>
              </div>
              <div class="border-t pt-3">
                <div class="flex justify-between">
                  <span class="text-lg font-semibold text-gray-900">Total</span>
                  <span class="text-lg font-bold text-gray-900">Rp {{ total.toLocaleString() }}</span>
                </div>
              </div>
            </div>
            
            <!-- Place Order Button -->
            <button 
              @click="placeOrder"
              :disabled="isProcessing"
              class="w-full bg-blue-600 text-white py-3 rounded-lg font-medium hover:bg-blue-700 transition-colors mb-4 disabled:opacity-50"
            >
              <i :class="[isProcessing ? 'fa-solid fa-spinner fa-spin' : 'fa-solid fa-credit-card']" class="mr-2"></i>
              {{ isProcessing ? 'Processing...' : 'Place Order' }}
            </button>
            
            <!-- Back to Cart -->
            <router-link 
              to="/cart"
              class="w-full block text-center text-blue-600 py-2 text-sm hover:text-blue-700 transition-colors"
            >
              <i class="fa-solid fa-arrow-left mr-2"></i>
              Back to Cart
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
            <p class="text-gray-400 text-sm">Your trusted online auction and shopping destination for automotive parts and electronics.</p>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Quick Links</h4>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><router-link to="/about" class="hover:text-white">About Us</router-link></li>
              <li><router-link to="/contact" class="hover:text-white">Contact</router-link></li>
              <li><router-link to="/faq" class="hover:text-white">FAQ</router-link></li>
              <li><router-link to="/shipping" class="hover:text-white">Shipping Info</router-link></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Categories</h4>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><router-link to="/products?category=electronics" class="hover:text-white">Electronics</router-link></li>
              <li><router-link to="/products?category=automotive" class="hover:text-white">Automotive</router-link></li>
              <li><router-link to="/products?category=gaming" class="hover:text-white">Gaming</router-link></li>
              <li><router-link to="/products?category=accessories" class="hover:text-white">Accessories</router-link></li>
            </ul>
          </div>
          <div>
            <h4 class="font-semibold mb-4">Follow Us</h4>
            <div class="flex space-x-4">
              <a href="#" class="text-gray-400 hover:text-white"><i class="fa-brands fa-facebook text-xl"></i></a>
              <a href="#" class="text-gray-400 hover:text-white"><i class="fa-brands fa-twitter text-xl"></i></a>
              <a href="#" class="text-gray-400 hover:text-white"><i class="fa-brands fa-instagram text-xl"></i></a>
              <a href="#" class="text-gray-400 hover:text-white"><i class="fa-brands fa-youtube text-xl"></i></a>
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

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()

// Form data
const shippingForm = ref({
  firstName: '',
  lastName: '',
  address: '',
  city: '',
  province: '',
  postalCode: '',
  phone: ''
})

const selectedShipping = ref('standard')
const isProcessing = ref(false)

// Shipping options
const shippingOptions = ref([
  {
    id: 'standard',
    name: 'Standard Shipping',
    duration: '5-7 business days',
    price: 25000
  },
  {
    id: 'express',
    name: 'Express Shipping',
    duration: '2-3 business days',
    price: 50000
  },
  {
    id: 'overnight',
    name: 'Overnight Shipping',
    duration: 'Next business day',
    price: 100000
  },
  {
    id: 'instant',
    name: 'Same Day Delivery',
    duration: 'Within 4 hours',
    price: 150000
  }
])

// Sample cart items
const cartItems = ref([
  {
    id: 1,
    name: 'Gaming Headset Sony WH-1000XM4',
    image: 'https://images.unsplash.com/photo-1583394838336-acd977736f90?w=400',
    price: 2500000,
    quantity: 1
  },
  {
    id: 2,
    name: 'Gaming Laptop ASUS ROG',
    image: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?w=400',
    price: 15000000,
    quantity: 1
  },
  {
    id: 3,
    name: 'Wireless Gaming Mouse',
    image: 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400',
    price: 750000,
    quantity: 2
  }
])

// Computed properties
const cartItemsCount = computed(() => {
  return cartItems.value.reduce((total, item) => total + item.quantity, 0)
})

const totalItems = computed(() => {
  return cartItems.value.reduce((total, item) => total + item.quantity, 0)
})

const subtotal = computed(() => {
  return cartItems.value.reduce((total, item) => total + (item.price * item.quantity), 0)
})

const shippingCost = computed(() => {
  const option = shippingOptions.value.find(opt => opt.id === selectedShipping.value)
  return option ? option.price : 0
})

const tax = computed(() => {
  return Math.round(subtotal.value * 0.11) // 11% PPN
})

const total = computed(() => {
  return subtotal.value + shippingCost.value + tax.value
})

// Methods
const placeOrder = async () => {
  // Validate form
  if (!shippingForm.value.firstName || !shippingForm.value.lastName || !shippingForm.value.address) {
    alert('Please fill in all required shipping information')
    return
  }

  isProcessing.value = true
  
  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // Navigate to success page or show success message
    alert('Order placed successfully! You will receive a confirmation email shortly.')
    router.push('/order-history')
  } catch (error) {
    alert('Failed to place order. Please try again.')
  } finally {
    isProcessing.value = false
  }
}

onMounted(() => {
  console.log('Checkout page loaded')
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
