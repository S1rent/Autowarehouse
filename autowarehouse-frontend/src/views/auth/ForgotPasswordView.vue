<template>
  <div class="bg-gradient-to-br from-purple-50 to-indigo-100 min-h-screen">
    <main class="flex items-center justify-center min-h-screen p-4">
      <div class="w-full max-w-md">
        
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-gray-100">
          
          <div class="text-center mb-8">
            <div class="w-16 h-16 bg-gradient-to-r from-purple-600 to-indigo-600 rounded-xl mx-auto mb-4 flex items-center justify-center">
              <i class="fa-solid fa-key text-white text-2xl"></i>
            </div>
            <h1 class="text-2xl font-bold text-gray-900 mb-2">Forgot Password?</h1>
            <p class="text-gray-600">Don't worry! Enter your email address and we'll send you a link to reset your password.</p>
          </div>

          <form @submit.prevent="handleForgotPassword" class="space-y-6">
            
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
                  class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Enter your email address"
                  :class="{ 'border-red-500': errors.email }"
                >
              </div>
              <p v-if="errors.email" class="mt-1 text-sm text-red-600">{{ errors.email }}</p>
            </div>

            <button 
              type="submit" 
              :disabled="isLoading"
              class="w-full bg-gradient-to-r from-purple-600 to-indigo-600 text-white py-3 px-4 rounded-lg font-medium hover:from-purple-700 hover:to-indigo-700 focus:ring-4 focus:ring-purple-200 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span v-if="isLoading" class="flex items-center justify-center">
                <i class="fa-solid fa-spinner fa-spin mr-2"></i>
                Sending Reset Link...
              </span>
              <span v-else>Send Reset Link</span>
            </button>

            <!-- Error message -->
            <div v-if="errors.general" class="p-4 bg-red-50 border border-red-200 rounded-lg">
              <div class="flex items-center">
                <i class="fa-solid fa-exclamation-circle text-red-500 mr-2"></i>
                <p class="text-sm text-red-700">{{ errors.general }}</p>
              </div>
            </div>

            <!-- Success message -->
            <div v-if="successMessage" class="bg-green-50 border border-green-200 rounded-lg p-4">
              <div class="flex items-center">
                <div class="flex-shrink-0">
                  <i class="fa-solid fa-check-circle text-green-400 text-xl"></i>
                </div>
                <div class="ml-3">
                  <p class="text-sm text-green-800">{{ successMessage }}</p>
                </div>
              </div>
            </div>

          </form>

          <div class="mt-8 text-center">
            <p class="text-sm text-gray-600">
              Remember your password? 
              <router-link to="/login" class="text-purple-600 hover:text-purple-800 font-medium">Back to Sign In</router-link>
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { apiService } from '@/services/api'

const router = useRouter()

// Form data
const form = reactive({
  email: ''
})

// Form state
const isLoading = ref(false)
const successMessage = ref('')
const errors = reactive({
  email: '',
  general: ''
})

// Methods
const validateForm = () => {
  // Reset errors
  errors.email = ''
  errors.general = ''

  let isValid = true

  // Email validation
  if (!form.email) {
    errors.email = 'Email is required'
    isValid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = 'Please enter a valid email address'
    isValid = false
  }

  return isValid
}

const handleForgotPassword = async () => {
  if (!validateForm()) {
    return
  }

  isLoading.value = true
  successMessage.value = ''
  errors.general = ''

  try {
    // Call backend API to send reset password email
    await apiService.forgotPassword({ email: form.email })

    // Show success message
    successMessage.value = 'Password reset link has been sent to your email address. Please check your inbox and follow the instructions.'
    
    // Clear form
    form.email = ''
    
    // Optionally redirect to login after a delay
    setTimeout(() => {
      router.push('/login')
    }, 4000)
    
  } catch (error: any) {
    if (error.response?.status === 404) {
      errors.general = 'No account found with this email address.'
    } else if (error.response?.status === 400) {
      errors.general = 'Invalid email address format.'
    } else {
      errors.general = 'Failed to send reset link. Please try again.'
    }
  } finally {
    isLoading.value = false
  }
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
