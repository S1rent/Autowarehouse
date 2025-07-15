<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <UserNavbar/>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Winner Announcement -->
      <div class="winner-gradient rounded-2xl p-8 mb-8 text-white text-center relative overflow-hidden">
        <!-- Confetti Animation -->
        <div v-for="i in 9" :key="i" 
             :class="`confetti confetti-${i}`"
             :style="`left: ${i * 10}%; animation-delay: ${(i * 0.5)}s;`">
        </div>
        
        <div class="celebration-animation inline-block mb-4">
          <i class="fas fa-trophy text-6xl text-yellow-300"></i>
        </div>
        <h1 class="text-4xl font-bold mb-2">Congratulations!</h1>
        <p class="text-xl mb-4">You won the auction for</p>
        <h2 class="text-2xl font-semibold mb-4">{{ wonItem.title }}</h2>
        <div class="text-3xl font-bold mb-2">Winning Bid: ${{ wonItem.winningBid.toLocaleString() }}</div>
        <p class="text-green-100">Auction ended {{ wonItem.endedTime }}</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Left Column - Auction Summary -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Payment Required Alert -->
          <div class="bg-red-50 border border-red-200 rounded-xl p-6">
            <div class="flex items-center mb-4">
              <i class="fas fa-exclamation-triangle text-red-600 text-2xl mr-3"></i>
              <h3 class="text-xl font-bold text-red-800">Payment Required</h3>
            </div>
            <p class="text-red-700 mb-4">Complete your payment within <span class="font-bold">48 hours</span> to secure your winning item.</p>
            <div class="bg-white rounded-lg p-4 border border-red-200">
              <div class="flex justify-between items-center">
                <span class="text-gray-700">Payment Deadline:</span>
                <span class="font-bold text-red-600">{{ paymentDeadline }}</span>
              </div>
            </div>
          </div>

          <!-- Item Summary -->
          <div class="bg-white rounded-xl shadow-lg p-6">
            <h3 class="text-xl font-bold text-gray-900 mb-4">Item Summary</h3>
            <div class="flex space-x-4">
              <img 
                :src="wonItem.image" 
                :alt="wonItem.title"
                class="w-24 h-24 object-cover rounded-lg"
              >
              <div class="flex-1">
                <h4 class="font-semibold text-gray-900 mb-2">{{ wonItem.title }}</h4>
                <div class="space-y-1 text-sm text-gray-600">
                  <div class="flex justify-between">
                    <span>SKU:</span>
                    <span>{{ wonItem.sku }}</span>
                  </div>
                  <div class="flex justify-between">
                    <span>Brand:</span>
                    <span>{{ wonItem.brand }}</span>
                  </div>
                  <div class="flex justify-between">
                    <span>Condition:</span>
                    <span>{{ wonItem.condition }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Shipping Information -->
          <div class="bg-white rounded-xl shadow-lg p-6">
            <h3 class="text-xl font-bold text-gray-900 mb-4">Shipping Information</h3>
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Shipping Address</label>
                <textarea 
                  v-model="shippingAddress"
                  class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" 
                  rows="3" 
                  placeholder="Enter your complete shipping address..."
                ></textarea>
              </div>
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Shipping Method</label>
                  <select 
                    v-model="selectedShipping"
                    class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  >
                    <option value="express">Insured Express ($45)</option>
                    <option value="standard">Standard Shipping ($15)</option>
                    <option value="pickup">Pickup ($0)</option>
                  </select>
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Insurance</label>
                  <select 
                    v-model="selectedInsurance"
                    class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                  >
                    <option value="full">Full Coverage ($150)</option>
                    <option value="basic">Basic Coverage ($50)</option>
                    <option value="none">No Insurance ($0)</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Column - Payment Section -->
        <div class="space-y-6">
          <!-- Payment Summary -->
          <div class="bg-white rounded-xl shadow-lg p-6">
            <h3 class="text-xl font-bold text-gray-900 mb-4">Payment Summary</h3>
            <div class="space-y-3">
              <div class="flex justify-between">
                <span class="text-gray-600">Winning Bid:</span>
                <span class="font-semibold">${{ wonItem.winningBid.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Buyer's Premium (10%):</span>
                <span class="font-semibold">${{ buyersPremium.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Shipping:</span>
                <span class="font-semibold">${{ shippingCost.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Insurance:</span>
                <span class="font-semibold">${{ insuranceCost.toLocaleString() }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">Tax:</span>
                <span class="font-semibold">${{ taxAmount.toLocaleString() }}</span>
              </div>
              <hr class="my-3">
              <div class="flex justify-between text-lg font-bold">
                <span>Total Amount:</span>
                <span class="text-green-600">${{ totalAmount.toLocaleString() }}</span>
              </div>
            </div>
          </div>

          <!-- Payment Methods -->
          <div class="bg-white rounded-xl shadow-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Payment Methods</h3>
            <div class="space-y-3">
              <label class="flex items-center p-3 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50">
                <input 
                  type="radio" 
                  v-model="selectedPaymentMethod" 
                  value="card" 
                  class="mr-3"
                >
                <i class="fas fa-credit-card text-blue-600 mr-3"></i>
                <span>Credit/Debit Card</span>
              </label>
              <label class="flex items-center p-3 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50">
                <input 
                  type="radio" 
                  v-model="selectedPaymentMethod" 
                  value="paypal" 
                  class="mr-3"
                >
                <i class="fab fa-paypal text-blue-600 mr-3"></i>
                <span>PayPal</span>
              </label>
              <label class="flex items-center p-3 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50">
                <input 
                  type="radio" 
                  v-model="selectedPaymentMethod" 
                  value="bank" 
                  class="mr-3"
                >
                <i class="fas fa-university text-blue-600 mr-3"></i>
                <span>Bank Transfer</span>
              </label>
            </div>
          </div>

          <!-- Card Details -->
          <div v-if="selectedPaymentMethod === 'card'" class="bg-white rounded-xl shadow-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Card Details</h3>
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Card Number</label>
                <input 
                  type="text" 
                  v-model="cardDetails.number"
                  class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" 
                  placeholder="1234 5678 9012 3456"
                >
              </div>
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Expiry Date</label>
                  <input 
                    type="text" 
                    v-model="cardDetails.expiry"
                    class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" 
                    placeholder="MM/YY"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">CVV</label>
                  <input 
                    type="text" 
                    v-model="cardDetails.cvv"
                    class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" 
                    placeholder="123"
                  >
                </div>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Cardholder Name</label>
                <input 
                  type="text" 
                  v-model="cardDetails.name"
                  class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent" 
                  placeholder="John Doe"
                >
              </div>
            </div>
          </div>

          <!-- Complete Payment Button -->
          <button 
            @click="completePayment"
            :disabled="isProcessing"
            :class="isProcessing ? 'bg-gray-600 cursor-not-allowed' : 'bg-green-600 hover:bg-green-700'"
            class="w-full text-white py-4 px-6 rounded-xl font-bold text-lg transition-colors"
          >
            <i :class="isProcessing ? 'fas fa-spinner fa-spin' : 'fas fa-lock'" class="mr-2"></i>
            {{ isProcessing ? 'Processing Payment...' : `Complete Payment - $${totalAmount.toLocaleString()}` }}
          </button>

          <!-- Security Info -->
          <div class="bg-blue-50 rounded-lg p-4">
            <div class="flex items-center mb-2">
              <i class="fas fa-shield-alt text-blue-600 mr-2"></i>
              <span class="font-medium text-blue-800">Secure Payment</span>
            </div>
            <p class="text-sm text-blue-700">Your payment is protected by 256-bit SSL encryption and our buyer protection guarantee.</p>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()

// State
const isProcessing = ref(false)
const shippingAddress = ref('')
const selectedShipping = ref('express')
const selectedInsurance = ref('full')
const selectedPaymentMethod = ref('card')

const cardDetails = ref({
  number: '',
  expiry: '',
  cvv: '',
  name: ''
})

// Sample won item data
const wonItem = ref({
  title: 'NVIDIA RTX 4090 Gaming GPU',
  image: 'https://images.unsplash.com/photo-1591488320449-011701bb6704?w=200&h=200&fit=crop',
  sku: 'RTX4090-001',
  brand: 'NVIDIA',
  condition: 'Brand New',
  winningBid: 22500,
  endedTime: '2 minutes ago'
})

// Computed properties
const buyersPremium = computed(() => Math.round(wonItem.value.winningBid * 0.1))

const shippingCost = computed(() => {
  switch (selectedShipping.value) {
    case 'express': return 45
    case 'standard': return 15
    case 'pickup': return 0
    default: return 0
  }
})

const insuranceCost = computed(() => {
  switch (selectedInsurance.value) {
    case 'full': return 150
    case 'basic': return 50
    case 'none': return 0
    default: return 0
  }
})

const taxAmount = computed(() => {
  const subtotal = wonItem.value.winningBid + buyersPremium.value + shippingCost.value + insuranceCost.value
  return Math.round(subtotal * 0.08) // 8% tax
})

const totalAmount = computed(() => {
  return wonItem.value.winningBid + buyersPremium.value + shippingCost.value + insuranceCost.value + taxAmount.value
})

const paymentDeadline = computed(() => {
  const deadline = new Date()
  deadline.setHours(deadline.getHours() + 48)
  return deadline.toLocaleDateString('en-US', { 
    year: 'numeric', 
    month: 'long', 
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
})

// Methods
const completePayment = async () => {
  isProcessing.value = true
  
  // Simulate payment processing
  setTimeout(() => {
    isProcessing.value = false
    alert('Payment successful! You will receive a confirmation email shortly.')
    router.push('/auctions')
  }, 3000)
}
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}

.winner-gradient {
  background: linear-gradient(135deg, #10b981, #059669);
  box-shadow: 0 10px 30px rgba(16, 185, 129, 0.3);
}

.celebration-animation {
  animation: celebration 2s ease-in-out infinite;
}

@keyframes celebration {
  0%, 100% { transform: scale(1) rotate(0deg); }
  25% { transform: scale(1.1) rotate(5deg); }
  75% { transform: scale(1.1) rotate(-5deg); }
}

.confetti {
  position: absolute;
  width: 10px;
  height: 10px;
  background: #fbbf24;
  animation: confetti-fall 3s linear infinite;
}

.confetti-1 { background: #fbbf24; }
.confetti-2 { background: #ef4444; }
.confetti-3 { background: #3b82f6; }
.confetti-4 { background: #8b5cf6; }
.confetti-5 { background: #fbbf24; }
.confetti-6 { background: #ef4444; }
.confetti-7 { background: #3b82f6; }
.confetti-8 { background: #8b5cf6; }
.confetti-9 { background: #fbbf24; }

@keyframes confetti-fall {
  0% { transform: translateY(-100vh) rotate(0deg); opacity: 1; }
  100% { transform: translateY(100vh) rotate(720deg); opacity: 0; }
}
</style>
