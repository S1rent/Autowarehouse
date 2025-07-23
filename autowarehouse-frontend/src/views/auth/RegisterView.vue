<template>
  <div class="bg-gradient-to-br from-green-50 to-emerald-100 min-h-screen">
    <main class="flex items-center justify-center min-h-screen p-4">
      <div class="w-full max-w-md">
        
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-gray-100">
          
          <div class="text-center mb-8">
            <div class="w-16 h-16 bg-gradient-to-r from-green-600 to-emerald-600 rounded-xl mx-auto mb-4 flex items-center justify-center">
              <i class="fa-solid fa-user-plus text-white text-2xl"></i>
            </div>
            <h1 class="text-2xl font-bold text-gray-900 mb-2">Create Account</h1>
            <p class="text-gray-600">Join us and start your journey</p>
          </div>

          <form @submit.prevent="handleRegister" class="space-y-6">
            
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="firstName" class="block text-sm font-medium text-gray-700 mb-2">First Name</label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <i class="fa-solid fa-user text-gray-400"></i>
                  </div>
                  <input 
                    type="text" 
                    id="firstName" 
                    v-model="form.firstName"
                    required 
                    class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                    placeholder="First name"
                    :class="{ 'border-red-500': errors.firstName }"
                  >
                </div>
                <p v-if="errors.firstName" class="mt-1 text-sm text-red-600">{{ errors.firstName }}</p>
              </div>
              
              <div>
                <label for="lastName" class="block text-sm font-medium text-gray-700 mb-2">Last Name</label>
                <div class="relative">
                  <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <i class="fa-solid fa-user text-gray-400"></i>
                  </div>
                  <input 
                    type="text" 
                    id="lastName" 
                    v-model="form.lastName"
                    required 
                    class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                    placeholder="Last name"
                    :class="{ 'border-red-500': errors.lastName }"
                  >
                </div>
                <p v-if="errors.lastName" class="mt-1 text-sm text-red-600">{{ errors.lastName }}</p>
              </div>
            </div>

            <div>
              <label for="email" class="block text-sm font-medium text-gray-700 mb-2">Email Address</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-envelope text-gray-400"></i>
                </div>
                <input 
                  type="email" 
                  id="email" 
                  v-model="form.email"
                  required 
                  class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Enter your email"
                  :class="{ 'border-red-500': errors.email }"
                >
              </div>
              <p v-if="errors.email" class="mt-1 text-sm text-red-600">{{ errors.email }}</p>
            </div>

            <div>
              <label for="password" class="block text-sm font-medium text-gray-700 mb-2">Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-lock text-gray-400"></i>
                </div>
                <input 
                  :type="showPassword ? 'text' : 'password'" 
                  id="password" 
                  v-model="form.password"
                  required 
                  class="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Create a password"
                  :class="{ 'border-red-500': errors.password }"
                  @input="checkPasswordStrength"
                >
                <button 
                  type="button" 
                  class="absolute inset-y-0 right-0 pr-3 flex items-center" 
                  @click="togglePassword"
                >
                  <i :class="showPassword ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'" class="text-gray-400 hover:text-gray-600"></i>
                </button>
              </div>
              <div v-if="form.password && passwordStrength.show" class="mt-2">
                <div class="flex space-x-1">
                  <div 
                    v-for="i in 4" 
                    :key="i"
                    class="h-1 w-full rounded"
                    :class="i <= passwordStrength.level ? passwordStrength.color : 'bg-gray-200'"
                  ></div>
                </div>
                <p class="text-xs text-gray-500 mt-1">{{ passwordStrength.text }}</p>
              </div>
              <p v-if="errors.password" class="mt-1 text-sm text-red-600">{{ errors.password }}</p>
            </div>

            <div>
              <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-2">Confirm Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-lock text-gray-400"></i>
                </div>
                <input 
                  :type="showConfirmPassword ? 'text' : 'password'" 
                  id="confirmPassword" 
                  v-model="form.confirmPassword"
                  required 
                  class="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Confirm your password"
                  :class="{ 'border-red-500': errors.confirmPassword }"
                  @input="checkPasswordMatch"
                >
                <button 
                  type="button" 
                  class="absolute inset-y-0 right-0 pr-3 flex items-center" 
                  @click="toggleConfirmPassword"
                >
                  <i :class="showConfirmPassword ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'" class="text-gray-400 hover:text-gray-600"></i>
                </button>
              </div>
              <div v-if="form.confirmPassword && passwordMatch.show" class="mt-1 text-xs">
                <span v-if="!passwordMatch.isMatch" class="text-red-500">Passwords do not match</span>
                <span v-else class="text-green-500">Passwords match</span>
              </div>
              <p v-if="errors.confirmPassword" class="mt-1 text-sm text-red-600">{{ errors.confirmPassword }}</p>
            </div>

            <div class="flex items-start">
              <div class="flex items-center h-5">
                <input 
                  id="terms" 
                  v-model="form.agreeToTerms"
                  type="checkbox" 
                  required 
                  class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-green-300 text-green-600"
                >
              </div>
              <div class="ml-3 text-sm">
                <label for="terms" class="text-gray-700">
                  I agree to the 
                  <span 
                    @click="showTermsModal = true"
                    class="text-green-600 hover:text-green-800 font-medium cursor-pointer underline"
                  >
                    Terms of Service
                  </span>
                  and 
                  <span 
                    @click="showPrivacyModal = true"
                    class="text-green-600 hover:text-green-800 font-medium cursor-pointer underline"
                  >
                    Privacy Policy
                  </span>
                </label>
              </div>
            </div>
            <p v-if="errors.agreeToTerms" class="mt-1 text-sm text-red-600">{{ errors.agreeToTerms }}</p>

            <!-- Error message -->
            <div v-if="errors.general" class="p-4 bg-red-50 border border-red-200 rounded-lg">
              <div class="flex items-center">
                <i class="fa-solid fa-exclamation-circle text-red-500 mr-2"></i>
                <p class="text-sm text-red-700">{{ errors.general }}</p>
              </div>
            </div>

            <!-- Success message -->
            <div v-if="successMessage" class="p-4 bg-green-50 border border-green-200 rounded-lg">
              <div class="flex items-center">
                <i class="fa-solid fa-check-circle text-green-500 mr-2"></i>
                <p class="text-sm text-green-700">{{ successMessage }}</p>
              </div>
            </div>

            <button 
              type="submit" 
              :disabled="isLoading"
              class="w-full bg-gradient-to-r from-green-600 to-emerald-600 text-white py-3 px-4 rounded-lg font-medium hover:from-green-700 hover:to-emerald-700 focus:ring-4 focus:ring-green-200 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span v-if="isLoading" class="flex items-center justify-center">
                <i class="fa-solid fa-spinner fa-spin mr-2"></i>
                Creating Account...
              </span>
              <span v-else>Create Account</span>
            </button>

            <!-- <div class="relative my-6">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-gray-300"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-2 bg-white text-gray-500">Or sign up with</span>
              </div>
            </div> -->

            <!-- <button 
              type="button" 
              @click="handleGoogleRegister"
              class="w-full flex items-center justify-center px-4 py-3 border border-gray-300 rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-200 transition-all duration-200"
            >
              <i class="fa-brands fa-google text-red-500 mr-3"></i>
              <span class="font-medium">Sign up with Google</span>
            </button> -->

          </form>

          <div class="mt-8 text-center">
            <p class="text-sm text-gray-600">
              Already have an account? 
              <router-link to="/login" class="text-green-600 hover:text-green-800 font-medium">Sign in</router-link>
            </p>
          </div>

        </div>

        <div class="mt-6 text-center">
          <p class="text-xs text-gray-500 flex items-center justify-center">
            <i class="fa-solid fa-shield-check mr-1"></i>
            Your information is secure and encrypted
          </p>
        </div>

      </div>
    </main>

    <!-- Terms of Service Modal -->
    <div v-if="showTermsModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div class="bg-white rounded-lg max-w-2xl w-full max-h-[80vh] overflow-hidden">
        <div class="flex items-center justify-between p-6 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-900">Terms of Service</h2>
          <button 
            @click="showTermsModal = false"
            class="text-gray-400 hover:text-gray-600 text-2xl"
          >
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <div class="p-6 overflow-y-auto max-h-[60vh]">
          <div class="prose prose-sm max-w-none">
            <h3 class="text-lg font-semibold mb-4">AutoWarehouse Terms of Service</h3>
            
            <p class="mb-4"><strong>Effective Date:</strong> January 1, 2025</p>
            
            <h4 class="font-semibold mb-2">1. Acceptance of Terms</h4>
            <p class="mb-4">By creating an account and using AutoWarehouse, you agree to be bound by these Terms of Service. If you do not agree to these terms, please do not use our platform.</p>
            
            <h4 class="font-semibold mb-2">2. Description of Service</h4>
            <p class="mb-4">AutoWarehouse is an online marketplace platform that connects buyers and sellers for automotive parts, accessories, and related products. We provide auction services, direct sales, and customer support features.</p>
            
            <h4 class="font-semibold mb-2">3. User Accounts</h4>
            <p class="mb-4">You must create an account to use certain features of our platform. You are responsible for maintaining the confidentiality of your account credentials and for all activities that occur under your account.</p>
            
            <h4 class="font-semibold mb-2">4. Buying and Selling</h4>
            <p class="mb-4">When you purchase items through AutoWarehouse, you enter into a contract with the seller. We facilitate transactions but are not a party to the sale. All sales are final unless otherwise specified by the seller's return policy.</p>
            
            <h4 class="font-semibold mb-2">5. Auction Services</h4>
            <p class="mb-4">Our auction platform allows users to bid on items. By placing a bid, you commit to purchasing the item if you are the winning bidder. All bids are binding and cannot be retracted.</p>
            
            <h4 class="font-semibold mb-2">6. Payment and Fees</h4>
            <p class="mb-4">Payment processing is handled through secure third-party providers. Sellers may be subject to listing fees and transaction fees as outlined in our fee schedule.</p>
            
            <h4 class="font-semibold mb-2">7. Prohibited Activities</h4>
            <p class="mb-4">Users may not engage in fraudulent activities, sell counterfeit goods, manipulate auctions, or violate any applicable laws while using our platform.</p>
            
            <h4 class="font-semibold mb-2">8. Limitation of Liability</h4>
            <p class="mb-4">AutoWarehouse is not liable for any damages arising from your use of the platform, including but not limited to direct, indirect, incidental, or consequential damages.</p>
            
            <h4 class="font-semibold mb-2">9. Modifications</h4>
            <p class="mb-4">We reserve the right to modify these terms at any time. Changes will be effective immediately upon posting to our website.</p>
            
            <h4 class="font-semibold mb-2">10. Contact Information</h4>
            <p class="mb-4">For questions about these Terms of Service, please contact us at support@autowarehouse.com</p>
          </div>
        </div>
        <div class="flex justify-end p-6 border-t border-gray-200">
          <button 
            @click="showTermsModal = false"
            class="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
          >
            Close
          </button>
        </div>
      </div>
    </div>

    <!-- Privacy Policy Modal -->
    <div v-if="showPrivacyModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <div class="bg-white rounded-lg max-w-2xl w-full max-h-[80vh] overflow-hidden">
        <div class="flex items-center justify-between p-6 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-900">Privacy Policy</h2>
          <button 
            @click="showPrivacyModal = false"
            class="text-gray-400 hover:text-gray-600 text-2xl"
          >
            <i class="fa-solid fa-times"></i>
          </button>
        </div>
        <div class="p-6 overflow-y-auto max-h-[60vh]">
          <div class="prose prose-sm max-w-none">
            <h3 class="text-lg font-semibold mb-4">AutoWarehouse Privacy Policy</h3>
            
            <p class="mb-4"><strong>Effective Date:</strong> January 1, 2025</p>
            
            <h4 class="font-semibold mb-2">1. Information We Collect</h4>
            <p class="mb-4">We collect information you provide directly to us, such as when you create an account, make a purchase, or contact customer support. This includes your name, email address, phone number, and payment information.</p>
            
            <h4 class="font-semibold mb-2">2. How We Use Your Information</h4>
            <p class="mb-4">We use your information to:</p>
            <ul class="list-disc pl-6 mb-4">
              <li>Provide and maintain our services</li>
              <li>Process transactions and send related information</li>
              <li>Send you technical notices and support messages</li>
              <li>Respond to your comments and questions</li>
              <li>Improve our platform and develop new features</li>
            </ul>
            
            <h4 class="font-semibold mb-2">3. Information Sharing</h4>
            <p class="mb-4">We do not sell, trade, or otherwise transfer your personal information to third parties without your consent, except as described in this policy. We may share information with:</p>
            <ul class="list-disc pl-6 mb-4">
              <li>Service providers who assist us in operating our platform</li>
              <li>Law enforcement when required by law</li>
              <li>Other users as necessary to facilitate transactions</li>
            </ul>
            
            <h4 class="font-semibold mb-2">4. Data Security</h4>
            <p class="mb-4">We implement appropriate security measures to protect your personal information against unauthorized access, alteration, disclosure, or destruction. However, no method of transmission over the internet is 100% secure.</p>
            
            <h4 class="font-semibold mb-2">5. Cookies and Tracking</h4>
            <p class="mb-4">We use cookies and similar tracking technologies to enhance your experience on our platform. You can control cookie settings through your browser preferences.</p>
            
            <h4 class="font-semibold mb-2">6. Your Rights</h4>
            <p class="mb-4">You have the right to:</p>
            <ul class="list-disc pl-6 mb-4">
              <li>Access and update your personal information</li>
              <li>Request deletion of your account and data</li>
              <li>Opt out of marketing communications</li>
              <li>Request a copy of your data</li>
            </ul>
            
            <h4 class="font-semibold mb-2">7. Children's Privacy</h4>
            <p class="mb-4">Our platform is not intended for children under 13 years of age. We do not knowingly collect personal information from children under 13.</p>
            
            <h4 class="font-semibold mb-2">8. Changes to This Policy</h4>
            <p class="mb-4">We may update this Privacy Policy from time to time. We will notify you of any changes by posting the new policy on this page and updating the effective date.</p>
            
            <h4 class="font-semibold mb-2">9. Contact Us</h4>
            <p class="mb-4">If you have any questions about this Privacy Policy, please contact us at privacy@autowarehouse.com</p>
          </div>
        </div>
        <div class="flex justify-end p-6 border-t border-gray-200">
          <button 
            @click="showPrivacyModal = false"
            class="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

// Form data
const form = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: false
})

// Form state
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)
const successMessage = ref('')
const showTermsModal = ref(false)
const showPrivacyModal = ref(false)
const errors = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: '',
  general: ''
})

// Password strength
const passwordStrength = computed(() => {
  const password = form.password
  if (!password) return { show: false, level: 0, color: '', text: '' }

  let strength = 0
  if (password.length >= 8) strength++
  if (/[a-z]/.test(password)) strength++
  if (/[A-Z]/.test(password)) strength++
  if (/[0-9]/.test(password)) strength++

  const levels: Record<number, { color: string; text: string }> = {
    0: { color: 'bg-red-500', text: 'Very Weak' },
    1: { color: 'bg-red-500', text: 'Weak' },
    2: { color: 'bg-yellow-500', text: 'Fair' },
    3: { color: 'bg-blue-500', text: 'Good' },
    4: { color: 'bg-green-500', text: 'Strong' }
  }

  return {
    show: true,
    level: strength,
    color: levels[strength].color,
    text: levels[strength].text
  }
})

// Password match
const passwordMatch = computed(() => {
  if (!form.confirmPassword) return { show: false, isMatch: false }
  return {
    show: true,
    isMatch: form.password === form.confirmPassword
  }
})

// Methods
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const toggleConfirmPassword = () => {
  showConfirmPassword.value = !showConfirmPassword.value
}

const checkPasswordStrength = () => {
  // This will trigger the computed property
}

const checkPasswordMatch = () => {
  // This will trigger the computed property
}

const validateForm = () => {
  // Reset errors
  errors.firstName = ''
  errors.lastName = ''
  errors.email = ''
  errors.password = ''
  errors.confirmPassword = ''
  errors.agreeToTerms = ''
  errors.general = ''

  let isValid = true

  // First name validation
  if (!form.firstName.trim()) {
    errors.firstName = 'First name is required'
    isValid = false
  }

  // Last name validation
  if (!form.lastName.trim()) {
    errors.lastName = 'Last name is required'
    isValid = false
  }

  // Email validation
  if (!form.email) {
    errors.email = 'Email is required'
    isValid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = 'Please enter a valid email address'
    isValid = false
  }

  // Password validation
  if (!form.password) {
    errors.password = 'Password is required'
    isValid = false
  } else if (form.password.length < 6) {
    errors.password = 'Password must be at least 6 characters'
    isValid = false
  }

  // Confirm password validation
  if (!form.confirmPassword) {
    errors.confirmPassword = 'Please confirm your password'
    isValid = false
  } else if (form.password !== form.confirmPassword) {
    errors.confirmPassword = 'Passwords do not match'
    isValid = false
  }

  // Terms validation
  if (!form.agreeToTerms) {
    errors.agreeToTerms = 'You must agree to the terms and conditions'
    isValid = false
  }

  return isValid
}

const handleRegister = async () => {
  if (!validateForm()) {
    return
  }

  isLoading.value = true
  authStore.clearError()
  successMessage.value = ''

  try {
    // Use auth store to register with firstName and lastName directly
    const response = await authStore.register({
      email: form.email,
      password: form.password,
      firstName: form.firstName,
      lastName: form.lastName
    })

    // Show success message
    successMessage.value = 'Registration successful! Please log in.'
    
    // Clear form
    form.firstName = ''
    form.lastName = ''
    form.email = ''
    form.password = ''
    form.confirmPassword = ''
    form.agreeToTerms = false

    // Redirect to login page after 3 seconds
    setTimeout(() => {
      router.push('/login')
    }, 3000)

  } catch (error: any) {
    errors.general = authStore.error || 'Registration failed. Please try again.'
  } finally {
    isLoading.value = false
  }
}

const handleGoogleRegister = () => {
  console.log('Google registration')
  // Implement Google OAuth logic here
  alert('Google registration initiated')
}
</script>

<style scoped>
/* Loading animation */
@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.fa-spin {
  animation: spin 1s linear infinite;
}
</style>
