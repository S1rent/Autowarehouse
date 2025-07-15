<template>
  <div class="bg-gradient-to-br from-blue-50 to-indigo-100 min-h-screen">
    <main class="flex items-center justify-center min-h-screen p-4">
      <div class="w-full max-w-md">
        
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-gray-100">
          
          <div class="text-center mb-8">
            <div class="w-16 h-16 bg-gradient-to-r from-blue-600 to-indigo-600 rounded-xl mx-auto mb-4 flex items-center justify-center">
              <i class="fa-solid fa-lock text-white text-2xl"></i>
            </div>
            <h1 class="text-2xl font-bold text-gray-900 mb-2">Welcome Back</h1>
            <p class="text-gray-600">Sign in to your account</p>
          </div>

          <form @submit.prevent="handleLogin" class="space-y-6">

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
                  class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" 
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
                  class="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Enter your password"
                  :class="{ 'border-red-500': errors.password }"
                >
                <button 
                  type="button" 
                  class="absolute inset-y-0 right-0 pr-3 flex items-center" 
                  @click="togglePassword"
                >
                  <i :class="showPassword ? 'fa-solid fa-eye-slash' : 'fa-solid fa-eye'" class="text-gray-400 hover:text-gray-600"></i>
                </button>
              </div>
              <p v-if="errors.password" class="mt-1 text-sm text-red-600">{{ errors.password }}</p>
            </div>

            <div class="flex items-center justify-between">
              <label class="flex items-center">
                <input 
                  type="checkbox" 
                  v-model="form.rememberMe"
                  class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
                >
                <span class="ml-2 text-sm text-gray-600">Remember me</span>
              </label>
              <router-link to="/forgot-password" class="text-sm text-blue-600 hover:text-blue-800 font-medium">
                Forgot Password?
              </router-link>
            </div>

            <!-- Error message -->
            <div v-if="errors.general" class="p-4 bg-red-50 border border-red-200 rounded-lg">
              <div class="flex items-center">
                <i class="fa-solid fa-exclamation-circle text-red-500 mr-2"></i>
                <p class="text-sm text-red-700">{{ errors.general }}</p>
              </div>
              <div v-if="showResendVerification" class="mt-2">
                <button 
                  @click="handleResendVerification"
                  :disabled="resendingVerification"
                  class="text-sm text-blue-600 hover:text-blue-800 font-medium disabled:opacity-50"
                >
                  <span v-if="resendingVerification">
                    <i class="fa-solid fa-spinner fa-spin mr-1"></i>
                    Sending...
                  </span>
                  <span v-else>Resend verification email</span>
                </button>
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
              class="w-full bg-gradient-to-r from-blue-600 to-indigo-600 text-white py-3 px-4 rounded-lg font-medium hover:from-blue-700 hover:to-indigo-700 focus:ring-4 focus:ring-blue-200 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span v-if="isLoading" class="flex items-center justify-center">
                <i class="fa-solid fa-spinner fa-spin mr-2"></i>
                Signing In...
              </span>
              <span v-else>Sign In</span>
            </button>

            <div class="relative my-6">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-gray-300"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-2 bg-white text-gray-500">Or continue with</span>
              </div>
            </div>

            <button 
              type="button" 
              @click="handleGoogleLogin"
              class="w-full flex items-center justify-center px-4 py-3 border border-gray-300 rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-200 transition-all duration-200"
            >
              <i class="fa-brands fa-google text-red-500 mr-3"></i>
              <span class="font-medium">Sign in with Google</span>
            </button>

          </form>

          <div class="mt-8 text-center">
            <p class="text-sm text-gray-600">
              Don't have an account? 
              <router-link to="/register" class="text-blue-600 hover:text-blue-800 font-medium">Sign up</router-link>
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
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

// Form data
const form = reactive({
  userType: 'customer',
  email: '',
  password: '',
  rememberMe: false
})

// Form state
const showPassword = ref(false)
const isLoading = ref(false)
const showResendVerification = ref(false)
const resendingVerification = ref(false)
const successMessage = ref('')
const errors = reactive({
  email: '',
  password: '',
  general: ''
})

// Methods
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const validateForm = () => {
  // Reset errors
  errors.email = ''
  errors.password = ''
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

  // Password validation
  if (!form.password) {
    errors.password = 'Password is required'
    isValid = false
  } else if (form.password.length < 6) {
    errors.password = 'Password must be at least 6 characters'
    isValid = false
  }

  return isValid
}

const handleLogin = async () => {
  if (!validateForm()) {
    return
  }

  isLoading.value = true
  authStore.clearError()
  showResendVerification.value = false
  successMessage.value = ''

  try {
    // Use auth store to login
    await authStore.login({
      email: form.email,
      password: form.password
    })
    
    // Redirect based on user role from backend
    if (authStore.isAdmin) {
      router.push('/admin/dashboard')
    } else {
      router.push('/')
    }
  } catch (error: any) {
    const errorMessage = authStore.error || 'Login failed. Please check your credentials and try again.'
    errors.general = errorMessage
    
    // Check if error is related to email verification
    if (errorMessage.toLowerCase().includes('verify') || errorMessage.toLowerCase().includes('verification')) {
      showResendVerification.value = true
    }
  } finally {
    isLoading.value = false
  }
}

const handleResendVerification = async () => {
  if (!form.email) {
    errors.general = 'Please enter your email address'
    return
  }

  resendingVerification.value = true
  successMessage.value = ''

  try {
    await authStore.resendVerification(form.email)
    successMessage.value = 'Verification email sent! Please check your inbox.'
    showResendVerification.value = false
    errors.general = ''
  } catch (error: any) {
    errors.general = authStore.error || 'Failed to resend verification email'
  } finally {
    resendingVerification.value = false
  }
}

const handleGoogleLogin = () => {
  console.log('Google login as:', form.userType)
  // Implement Google OAuth logic here
  alert(`Google login as ${form.userType}`)
}
</script>

<style scoped>
/* Custom radio button styling */
input[type="radio"]:checked + div {
  border-color: #3b82f6;
  background-color: #eff6ff;
}

input[type="radio"]:checked + div i,
input[type="radio"]:checked + div span {
  color: #3b82f6;
}

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
