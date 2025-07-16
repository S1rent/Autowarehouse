<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-3 mb-2">
          <button @click="$router.go(-1)" class="text-gray-500 hover:text-gray-700 transition-colors">
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <h1 class="text-3xl font-bold text-gray-900">Checkout</h1>
        </div>
        <p class="text-gray-600">Complete your order securely</p>
      </div>

      <!-- Loading State -->
      <div v-if="cartStore.isLoading || orderStore.isLoading" class="flex justify-center items-center py-12">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
        <div class="flex">
          <div class="flex-shrink-0">
            <svg class="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
            </svg>
          </div>
          <div class="ml-3">
            <h3 class="text-sm font-medium text-red-800">Error</h3>
            <p class="text-sm text-red-700 mt-1">{{ error }}</p>
          </div>
        </div>
      </div>

      <!-- Empty Cart -->
      <div v-else-if="!selectedItems.length" class="text-center py-12">
        <svg class="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4m0 0L7 13m0 0l-1.5 6M7 13l-1.5-6m0 0h15.5M17 13v6a2 2 0 01-2 2H9a2 2 0 01-2-2v-6" />
        </svg>
        <h3 class="mt-2 text-sm font-medium text-gray-900">No items selected</h3>
        <p class="mt-1 text-sm text-gray-500">Please select items from your cart to proceed with checkout.</p>
        <div class="mt-6">
          <router-link to="/cart" class="inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700">
            Go to Cart
          </router-link>
        </div>
      </div>

      <!-- Checkout Form -->
      <div v-else class="lg:grid lg:grid-cols-12 lg:gap-x-12 lg:items-start xl:gap-x-16">
        <!-- Left Column - Forms -->
        <div class="lg:col-span-7">
          <form @submit.prevent="handleCheckout" class="space-y-8">
            <!-- Shipping Address -->
            <div class="bg-white shadow-sm rounded-xl border border-gray-200 p-6">
              <div class="flex items-center justify-between mb-6">
                <h2 class="text-lg font-semibold text-gray-900 flex items-center">
                  <i class="fa-solid fa-truck text-blue-600 mr-2"></i>
                  Shipping Address
                </h2>
                <button 
                  v-if="authStore.user && !isLoadingProfile"
                  @click="loadProfileData"
                  class="text-sm text-blue-600 hover:text-blue-700 font-medium transition-colors"
                >
                  <i class="fa-solid fa-user mr-1"></i>
                  Use Profile Data
                </button>
              </div>
              
              <!-- Profile Loading State -->
              <div v-if="isLoadingProfile" class="flex items-center justify-center py-4 mb-4 bg-blue-50 rounded-lg">
                <i class="fa-solid fa-spinner fa-spin text-blue-600 mr-2"></i>
                <span class="text-sm text-blue-700">Loading profile data...</span>
              </div>
              
              <div class="space-y-6">
                <div>
                  <label for="fullName" class="block text-sm font-medium text-gray-700 mb-2">
                    Full Name <span class="text-red-500">*</span>
                  </label>
                  <div class="relative">
                    <input
                      type="text"
                      id="fullName"
                      v-model="checkoutForm.fullName"
                      required
                      class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                      placeholder="Enter your full name"
                    />
                    <i class="fa-solid fa-user absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  </div>
                </div>
                
                <div>
                  <label for="phone" class="block text-sm font-medium text-gray-700 mb-2">
                    Phone Number <span class="text-red-500">*</span>
                  </label>
                  <div class="relative">
                    <input
                      type="tel"
                      id="phone"
                      v-model="checkoutForm.phone"
                      required
                      class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                      placeholder="Enter your phone number"
                    />
                    <i class="fa-solid fa-phone absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  </div>
                </div>

                <div>
                  <label for="address" class="block text-sm font-medium text-gray-700 mb-2">
                    Street Address <span class="text-red-500">*</span>
                  </label>
                  <div class="relative">
                    <textarea
                      id="address"
                      v-model="checkoutForm.address"
                      required
                      rows="3"
                      class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors resize-none"
                      placeholder="Enter your complete address"
                    ></textarea>
                    <i class="fa-solid fa-map-marker-alt absolute left-3 top-4 text-gray-400"></i>
                  </div>
                </div>

                <div class="grid grid-cols-1 gap-6 sm:grid-cols-2">
                  <div>
                    <label for="city" class="block text-sm font-medium text-gray-700 mb-2">
                      City <span class="text-red-500">*</span>
                    </label>
                    <input
                      type="text"
                      id="city"
                      v-model="checkoutForm.city"
                      required
                      class="block w-full px-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                      placeholder="City"
                    />
                  </div>
                  
                  <div>
                    <label for="postalCode" class="block text-sm font-medium text-gray-700 mb-2">
                      Postal Code <span class="text-red-500">*</span>
                    </label>
                    <input
                      type="text"
                      id="postalCode"
                      v-model="checkoutForm.postalCode"
                      required
                      class="block w-full px-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors"
                      placeholder="Postal Code"
                    />
                  </div>
                </div>
              </div>
            </div>

            <!-- Payment Method -->
            <div class="bg-white shadow-sm rounded-xl border border-gray-200 p-6">
              <h2 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                <i class="fa-solid fa-credit-card text-blue-600 mr-2"></i>
                Payment Method
              </h2>
              <div class="space-y-4">
                <div class="relative">
                  <input
                    id="bank-transfer"
                    name="payment-method"
                    type="radio"
                    value="bank_transfer"
                    v-model="checkoutForm.paymentMethod"
                    class="sr-only"
                  />
                  <label 
                    for="bank-transfer" 
                    class="flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all hover:bg-gray-50"
                    :class="checkoutForm.paymentMethod === 'bank_transfer' ? 'border-blue-500 bg-blue-50' : 'border-gray-200'"
                  >
                    <div class="flex items-center">
                      <div 
                        class="w-5 h-5 rounded-full border-2 flex items-center justify-center"
                        :class="checkoutForm.paymentMethod === 'bank_transfer' ? 'border-blue-500' : 'border-gray-300'"
                      >
                        <div 
                          v-if="checkoutForm.paymentMethod === 'bank_transfer'"
                          class="w-2.5 h-2.5 rounded-full bg-blue-500"
                        ></div>
                      </div>
                      <div class="ml-3">
                        <div class="flex items-center">
                          <i class="fa-solid fa-university text-gray-600 mr-2"></i>
                          <span class="text-sm font-medium text-gray-900">Bank Transfer</span>
                        </div>
                        <p class="text-xs text-gray-500 mt-1">Transfer via ATM, Internet Banking, or Mobile Banking</p>
                      </div>
                    </div>
                  </label>
                </div>
                
                <div class="relative">
                  <input
                    id="credit-card"
                    name="payment-method"
                    type="radio"
                    value="credit_card"
                    v-model="checkoutForm.paymentMethod"
                    class="sr-only"
                  />
                  <label 
                    for="credit-card" 
                    class="flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all hover:bg-gray-50"
                    :class="checkoutForm.paymentMethod === 'credit_card' ? 'border-blue-500 bg-blue-50' : 'border-gray-200'"
                  >
                    <div class="flex items-center">
                      <div 
                        class="w-5 h-5 rounded-full border-2 flex items-center justify-center"
                        :class="checkoutForm.paymentMethod === 'credit_card' ? 'border-blue-500' : 'border-gray-300'"
                      >
                        <div 
                          v-if="checkoutForm.paymentMethod === 'credit_card'"
                          class="w-2.5 h-2.5 rounded-full bg-blue-500"
                        ></div>
                      </div>
                      <div class="ml-3">
                        <div class="flex items-center">
                          <i class="fa-solid fa-credit-card text-gray-600 mr-2"></i>
                          <span class="text-sm font-medium text-gray-900">Credit Card</span>
                        </div>
                        <p class="text-xs text-gray-500 mt-1">Visa, Mastercard, JCB</p>
                      </div>
                    </div>
                  </label>
                </div>
                
                <div class="relative">
                  <input
                    id="e-wallet"
                    name="payment-method"
                    type="radio"
                    value="e_wallet"
                    v-model="checkoutForm.paymentMethod"
                    class="sr-only"
                  />
                  <label 
                    for="e-wallet" 
                    class="flex items-center p-4 border-2 rounded-lg cursor-pointer transition-all hover:bg-gray-50"
                    :class="checkoutForm.paymentMethod === 'e_wallet' ? 'border-blue-500 bg-blue-50' : 'border-gray-200'"
                  >
                    <div class="flex items-center">
                      <div 
                        class="w-5 h-5 rounded-full border-2 flex items-center justify-center"
                        :class="checkoutForm.paymentMethod === 'e_wallet' ? 'border-blue-500' : 'border-gray-300'"
                      >
                        <div 
                          v-if="checkoutForm.paymentMethod === 'e_wallet'"
                          class="w-2.5 h-2.5 rounded-full bg-blue-500"
                        ></div>
                      </div>
                      <div class="ml-3">
                        <div class="flex items-center">
                          <i class="fa-solid fa-mobile-alt text-gray-600 mr-2"></i>
                          <span class="text-sm font-medium text-gray-900">E-Wallet</span>
                        </div>
                        <p class="text-xs text-gray-500 mt-1">GoPay, OVO, DANA, ShopeePay</p>
                      </div>
                    </div>
                  </label>
                </div>
              </div>
            </div>

            <!-- Order Notes -->
            <div class="bg-white shadow-sm rounded-xl border border-gray-200 p-6">
              <h2 class="text-lg font-semibold text-gray-900 mb-4 flex items-center">
                <i class="fa-solid fa-sticky-note text-blue-600 mr-2"></i>
                Order Notes
                <span class="text-sm font-normal text-gray-500 ml-2">(Optional)</span>
              </h2>
              <div class="relative">
                <textarea
                  v-model="checkoutForm.notes"
                  rows="4"
                  class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg shadow-sm focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition-colors resize-none"
                  placeholder="Any special instructions for your order..."
                ></textarea>
                <i class="fa-solid fa-comment-dots absolute left-3 top-4 text-gray-400"></i>
              </div>
            </div>
          </form>
        </div>

        <!-- Right Column - Order Summary -->
        <div class="mt-10 lg:mt-0 lg:col-span-5">
          <div class="bg-white shadow-sm rounded-xl border border-gray-200 sticky top-4">
            <div class="px-6 py-4 border-b border-gray-200">
              <h2 class="text-lg font-semibold text-gray-900 flex items-center">
                <i class="fa-solid fa-receipt text-blue-600 mr-2"></i>
                Order Summary
              </h2>
            </div>
            
            <div class="px-6 py-4">
              <!-- Selected Items -->
              <div class="space-y-4 mb-6">
                <div v-for="item in selectedItems" :key="item.id" class="flex items-center space-x-4 p-3 bg-gray-50 rounded-lg">
                  <img
                    :src="item.productImages[0] || '/placeholder-product.jpg'"
                    :alt="item.productName"
                    class="w-16 h-16 object-cover rounded-lg border border-gray-200"
                  />
                  <div class="flex-1 min-w-0">
                    <h3 class="text-sm font-medium text-gray-900 truncate">{{ item.productName }}</h3>
                    <p class="text-sm text-gray-500">{{ item.productBrand }}</p>
                    <div class="flex items-center mt-1">
                      <span class="text-xs text-gray-500">Qty:</span>
                      <span class="text-xs font-medium text-gray-700 ml-1 bg-white px-2 py-1 rounded">{{ item.quantity }}</span>
                    </div>
                  </div>
                  <div class="text-right">
                    <p class="text-sm font-semibold text-gray-900">
                      Rp {{ item.subtotal.toLocaleString('id-ID') }}
                    </p>
                    <p v-if="item.originalPrice > item.productPrice" class="text-xs text-gray-500 line-through">
                      Rp {{ (item.originalPrice * item.quantity).toLocaleString('id-ID') }}
                    </p>
                  </div>
                </div>
              </div>

              <!-- Order Totals -->
              <div class="border-t border-gray-200 pt-4 space-y-2">
                <div class="flex justify-between text-sm">
                  <span class="text-gray-600">Subtotal ({{ selectedItems.length }} items)</span>
                  <span class="text-gray-900">Rp {{ orderSummary.subtotal.toLocaleString('id-ID') }}</span>
                </div>
                
                <div class="flex justify-between text-sm">
                  <span class="text-gray-600">Shipping</span>
                  <span class="text-gray-900">
                    <span v-if="orderSummary.freeShipping" class="text-green-600">FREE</span>
                    <span v-else>Rp {{ orderSummary.shippingCost.toLocaleString('id-ID') }}</span>
                  </span>
                </div>
                
                <div class="flex justify-between text-sm">
                  <span class="text-gray-600">Tax (PPN 11%)</span>
                  <span class="text-gray-900">Rp {{ orderSummary.taxAmount.toLocaleString('id-ID') }}</span>
                </div>
                
                <div class="border-t border-gray-200 pt-2">
                  <div class="flex justify-between text-base font-medium">
                    <span class="text-gray-900">Total</span>
                    <span class="text-gray-900">Rp {{ orderSummary.totalAmount.toLocaleString('id-ID') }}</span>
                  </div>
                </div>
              </div>

              <!-- Free Shipping Notice -->
              <div v-if="!orderSummary.freeShipping" class="mt-4 p-4 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-lg border border-blue-200">
                <div class="flex items-center">
                  <i class="fa-solid fa-truck text-blue-600 mr-2"></i>
                  <p class="text-sm text-blue-700">
                    <span class="font-semibold">Free shipping</span> on orders over Rp 10,000,000
                  </p>
                </div>
                <p class="text-xs text-blue-600 mt-2 flex items-center">
                  <i class="fa-solid fa-info-circle mr-1"></i>
                  Add Rp {{ (10000000 - orderSummary.subtotal).toLocaleString('id-ID') }} more to qualify
                </p>
              </div>
            </div>

            <!-- Place Order Button -->
            <div class="px-6 py-4 border-t border-gray-200">
              <button
                type="submit"
                @click="handleCheckout"
                :disabled="!isFormValid || orderStore.isLoading"
                class="w-full bg-gradient-to-r from-blue-600 to-blue-700 border border-transparent rounded-lg shadow-lg py-4 px-4 text-base font-semibold text-white hover:from-blue-700 hover:to-blue-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed transform transition-all duration-200 hover:scale-[1.02] active:scale-[0.98]"
              >
                <span v-if="orderStore.isLoading" class="flex items-center justify-center">
                  <i class="fa-solid fa-spinner fa-spin mr-3"></i>
                  Processing Order...
                </span>
                <span v-else class="flex items-center justify-center">
                  <i class="fa-solid fa-lock mr-2"></i>
                  Place Secure Order - Rp {{ orderSummary.totalAmount.toLocaleString('id-ID') }}
                </span>
              </button>
              
              <!-- Security Notice -->
              <div class="mt-3 flex items-center justify-center text-xs text-gray-500">
                <i class="fa-solid fa-shield-alt text-green-500 mr-1"></i>
                Your payment information is secure and encrypted
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '@/stores/cart'
import { useOrderStore } from '@/stores/order'
import { useAuthStore } from '@/stores/auth'
import { useNotifications } from '@/composables/useNotifications'
import { apiService } from '@/services/api'

// Stores
const cartStore = useCartStore()
const orderStore = useOrderStore()
const authStore = useAuthStore()
const router = useRouter()
const { success, error: showError, warning } = useNotifications()

// State
const error = ref<string | null>(null)
const isLoadingProfile = ref(false)

// Form data
const checkoutForm = ref({
  fullName: '',
  phone: '',
  address: '',
  city: '',
  postalCode: '',
  paymentMethod: 'bank_transfer',
  notes: ''
})

// Computed
const selectedItems = computed(() => cartStore.selectedItems)

const orderSummary = computed(() => {
  const selectedAmount = cartStore.summary?.selectedAmount || 0
  return orderStore.getOrderSummary(selectedAmount)
})

const isFormValid = computed(() => {
  return checkoutForm.value.fullName &&
         checkoutForm.value.phone &&
         checkoutForm.value.address &&
         checkoutForm.value.city &&
         checkoutForm.value.postalCode &&
         checkoutForm.value.paymentMethod &&
         selectedItems.value.length > 0
})

// Methods
const handleCheckout = async () => {
  if (!isFormValid.value) {
    warning('Validation Error', 'Please fill in all required fields')
    return
  }

  try {
    error.value = null
    
    // Format shipping address
    const shippingAddress = `${checkoutForm.value.fullName}\n${checkoutForm.value.phone}\n${checkoutForm.value.address}\n${checkoutForm.value.city}, ${checkoutForm.value.postalCode}`
    
    // Create order
    const order = await orderStore.createOrderWithCheckout({
      shippingAddress,
      billingAddress: shippingAddress, // Same as shipping for now
      paymentMethod: checkoutForm.value.paymentMethod
    })

    success('Order Placed', 'Your order has been placed successfully!')
    
    // Redirect to order confirmation
    router.push(`/order/${order.id}`)
  } catch (err: any) {
    showError('Checkout Failed', err.message || 'Failed to create order. Please try again.')
  }
}

// Load profile data method
const loadProfileData = async () => {
  if (!authStore.user) return
  
  try {
    isLoadingProfile.value = true
    const fullProfile = await apiService.getUserProfile(authStore.user.id)
    
    // Update form with profile data
    checkoutForm.value.fullName = `${fullProfile.firstName} ${fullProfile.lastName}`.trim()
    checkoutForm.value.phone = fullProfile.phoneNumber || ''
    checkoutForm.value.address = fullProfile.address || ''
    
    // Try to parse city and postal code from address if they follow a pattern
    if (fullProfile.address) {
      const addressLines = fullProfile.address.split('\n')
      if (addressLines.length > 1) {
        const lastLine = addressLines[addressLines.length - 1]
        const cityPostalMatch = lastLine.match(/^(.+),\s*(\d+)$/)
        if (cityPostalMatch) {
          checkoutForm.value.city = cityPostalMatch[1].trim()
          checkoutForm.value.postalCode = cityPostalMatch[2].trim()
        }
      }
    }
    
    success('Profile Loaded', 'Your profile data has been loaded successfully!')
  } catch (err) {
    console.error('Error loading user profile:', err)
    showError('Load Failed', 'Failed to load profile data. Please try again.')
  } finally {
    isLoadingProfile.value = false
  }
}

// Initialize user data
const initializeUserData = async () => {
  if (authStore.user) {
    checkoutForm.value.fullName = `${authStore.user.firstName} ${authStore.user.lastName}`.trim()
    
    // Load full user profile to get phone and address
    try {
      const fullProfile = await apiService.getUserProfile(authStore.user.id)
      checkoutForm.value.phone = fullProfile.phoneNumber || ''
      
      // Parse address if it exists
      if (fullProfile.address) {
        checkoutForm.value.address = fullProfile.address
        // Try to parse city and postal code from address if they follow a pattern
        const addressLines = fullProfile.address.split('\n')
        if (addressLines.length > 1) {
          const lastLine = addressLines[addressLines.length - 1]
          const cityPostalMatch = lastLine.match(/^(.+),\s*(\d+)$/)
          if (cityPostalMatch) {
            checkoutForm.value.city = cityPostalMatch[1].trim()
            checkoutForm.value.postalCode = cityPostalMatch[2].trim()
          }
        }
      }
    } catch (err) {
      console.error('Error loading user profile:', err)
      // Fallback to auth store data
      checkoutForm.value.phone = authStore.user.phoneNumber || ''
    }
  }
}

// Lifecycle
onMounted(async () => {
  // Fetch cart items
  await cartStore.fetchCartItems()
  
  // Initialize user data
  initializeUserData()
  
  // Redirect if no selected items
  if (!selectedItems.value.length) {
    router.push('/cart')
  }
})
</script>
