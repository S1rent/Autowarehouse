<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Checkout</h1>
        <p class="text-gray-600 mt-2">Complete your order</p>
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
            <div class="bg-white shadow rounded-lg p-6">
              <h2 class="text-lg font-medium text-gray-900 mb-4">Shipping Address</h2>
              <div class="space-y-4">
                <div>
                  <label for="fullName" class="block text-sm font-medium text-gray-700">Full Name</label>
                  <input
                    type="text"
                    id="fullName"
                    v-model="checkoutForm.fullName"
                    required
                    class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                    placeholder="Enter your full name"
                  />
                </div>
                
                <div>
                  <label for="phone" class="block text-sm font-medium text-gray-700">Phone Number</label>
                  <input
                    type="tel"
                    id="phone"
                    v-model="checkoutForm.phone"
                    required
                    class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                    placeholder="Enter your phone number"
                  />
                </div>

                <div>
                  <label for="address" class="block text-sm font-medium text-gray-700">Street Address</label>
                  <textarea
                    id="address"
                    v-model="checkoutForm.address"
                    required
                    rows="3"
                    class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                    placeholder="Enter your complete address"
                  ></textarea>
                </div>

                <div class="grid grid-cols-1 gap-4 sm:grid-cols-2">
                  <div>
                    <label for="city" class="block text-sm font-medium text-gray-700">City</label>
                    <input
                      type="text"
                      id="city"
                      v-model="checkoutForm.city"
                      required
                      class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                      placeholder="City"
                    />
                  </div>
                  
                  <div>
                    <label for="postalCode" class="block text-sm font-medium text-gray-700">Postal Code</label>
                    <input
                      type="text"
                      id="postalCode"
                      v-model="checkoutForm.postalCode"
                      required
                      class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                      placeholder="Postal Code"
                    />
                  </div>
                </div>
              </div>
            </div>

            <!-- Payment Method -->
            <div class="bg-white shadow rounded-lg p-6">
              <h2 class="text-lg font-medium text-gray-900 mb-4">Payment Method</h2>
              <div class="space-y-4">
                <div class="flex items-center">
                  <input
                    id="bank-transfer"
                    name="payment-method"
                    type="radio"
                    value="bank_transfer"
                    v-model="checkoutForm.paymentMethod"
                    class="focus:ring-blue-500 h-4 w-4 text-blue-600 border-gray-300"
                  />
                  <label for="bank-transfer" class="ml-3 block text-sm font-medium text-gray-700">
                    Bank Transfer
                  </label>
                </div>
                
                <div class="flex items-center">
                  <input
                    id="credit-card"
                    name="payment-method"
                    type="radio"
                    value="credit_card"
                    v-model="checkoutForm.paymentMethod"
                    class="focus:ring-blue-500 h-4 w-4 text-blue-600 border-gray-300"
                  />
                  <label for="credit-card" class="ml-3 block text-sm font-medium text-gray-700">
                    Credit Card
                  </label>
                </div>
                
                <div class="flex items-center">
                  <input
                    id="e-wallet"
                    name="payment-method"
                    type="radio"
                    value="e_wallet"
                    v-model="checkoutForm.paymentMethod"
                    class="focus:ring-blue-500 h-4 w-4 text-blue-600 border-gray-300"
                  />
                  <label for="e-wallet" class="ml-3 block text-sm font-medium text-gray-700">
                    E-Wallet (GoPay, OVO, DANA)
                  </label>
                </div>
              </div>
            </div>

            <!-- Order Notes -->
            <div class="bg-white shadow rounded-lg p-6">
              <h2 class="text-lg font-medium text-gray-900 mb-4">Order Notes (Optional)</h2>
              <textarea
                v-model="checkoutForm.notes"
                rows="3"
                class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
                placeholder="Any special instructions for your order..."
              ></textarea>
            </div>
          </form>
        </div>

        <!-- Right Column - Order Summary -->
        <div class="mt-10 lg:mt-0 lg:col-span-5">
          <div class="bg-white shadow rounded-lg sticky top-4">
            <div class="px-6 py-4 border-b border-gray-200">
              <h2 class="text-lg font-medium text-gray-900">Order Summary</h2>
            </div>
            
            <div class="px-6 py-4">
              <!-- Selected Items -->
              <div class="space-y-4 mb-6">
                <div v-for="item in selectedItems" :key="item.id" class="flex items-center space-x-4">
                  <img
                    :src="item.productImages[0] || '/placeholder-product.jpg'"
                    :alt="item.productName"
                    class="w-16 h-16 object-cover rounded-lg"
                  />
                  <div class="flex-1 min-w-0">
                    <h3 class="text-sm font-medium text-gray-900 truncate">{{ item.productName }}</h3>
                    <p class="text-sm text-gray-500">{{ item.productBrand }}</p>
                    <p class="text-sm text-gray-500">Qty: {{ item.quantity }}</p>
                  </div>
                  <div class="text-right">
                    <p class="text-sm font-medium text-gray-900">
                      Rp {{ item.subtotal.toLocaleString('id-ID') }}
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
              <div v-if="!orderSummary.freeShipping" class="mt-4 p-3 bg-blue-50 rounded-lg">
                <p class="text-sm text-blue-700">
                  <span class="font-medium">Free shipping</span> on orders over Rp 10,000,000
                </p>
                <p class="text-xs text-blue-600 mt-1">
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
                class="w-full bg-blue-600 border border-transparent rounded-md shadow-sm py-3 px-4 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span v-if="orderStore.isLoading" class="flex items-center justify-center">
                  <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Processing...
                </span>
                <span v-else>Place Order - Rp {{ orderSummary.totalAmount.toLocaleString('id-ID') }}</span>
              </button>
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

// Stores
const cartStore = useCartStore()
const orderStore = useOrderStore()
const authStore = useAuthStore()
const router = useRouter()

// State
const error = ref<string | null>(null)

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
    error.value = 'Please fill in all required fields'
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

    // Redirect to order confirmation
    router.push(`/order/${order.id}`)
  } catch (err: any) {
    error.value = err.message || 'Failed to create order'
  }
}

// Initialize user data
const initializeUserData = () => {
  if (authStore.user) {
    checkoutForm.value.fullName = `${authStore.user.firstName} ${authStore.user.lastName}`
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
