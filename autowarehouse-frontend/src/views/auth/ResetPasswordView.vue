<template>
  <div class="bg-gradient-to-br from-purple-50 to-indigo-100 min-h-screen">
    <main class="flex items-center justify-center min-h-screen p-4">
      <div class="w-full max-w-md">
        
        <div class="bg-white rounded-2xl shadow-xl p-8 border border-gray-100">
          
          <div class="text-center mb-8">
            <div class="w-16 h-16 bg-gradient-to-r from-purple-600 to-indigo-600 rounded-xl mx-auto mb-4 flex items-center justify-center">
              <i class="fa-solid fa-lock-open text-white text-2xl"></i>
            </div>
            <h1 class="text-2xl font-bold text-gray-900 mb-2">Reset Password</h1>
            <p class="text-gray-600">Enter your new password below</p>
          </div>

          <!-- Error message for invalid/expired token -->
          <div v-if="tokenError" class="mb-6 p-4 bg-red-50 border border-red-200 rounded-lg">
            <div class="flex items-center">
              <i class="fa-solid fa-exclamation-circle text-red-500 mr-2"></i>
              <div>
                <p class="text-sm text-red-700 font-medium">{{ tokenError }}</p>
                <p class="text-xs text-red-600 mt-1">Please request a new password reset link.</p>
              </div>
            </div>
            <div class="mt-3">
              <router-link 
                to="/forgot-password" 
                class="text-sm text-red-600 hover:text-red-800 font-medium underline"
              >
                Request New Reset Link
              </router-link>
            </div>
          </div>

          <form v-if="!tokenError" @submit.prevent="handleResetPassword" class="space-y-6">
            
            <div>
              <label for="password" class="block text-sm font-medium text-gray-700 mb-2">New Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-lock text-gray-400"></i>
                </div>
                <input 
                  :type="showPassword ? 'text' : 'password'" 
                  id="password" 
                  v-model="form.password"
                  required 
                  class="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Enter new password"
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
              <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-2">Confirm New Password</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-lock text-gray-400"></i>
                </div>
                <input 
                  :type="showConfirmPassword ? 'text' : 'password'" 
                  id="confirmPassword" 
                  v-model="form.confirmPassword"
                  required 
                  class="w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Confirm new password"
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
              :disabled="isLoading || !passwordMatch.isMatch"
              class="w-full bg-gradient-to-r from-purple-600 to-indigo-600 text-white py-3 px-4 rounded-lg font-medium hover:from-purple-700 hover:to-indigo-700 focus:ring-4 focus:ring-purple-200 transition-all duration-200 transform hover:scale-[1.02] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span v-if="isLoading" class="flex items-center justify-center">
                <i class="fa-solid fa-spinner fa-spin mr-2"></i>
                Resetting Password...
              </span>
              <span v-else>Reset Password</span>
            </button>

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
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { apiService } from '@/services/api'

const router = useRouter()
const route = useRoute()

// Form data
const form = reactive({
  password: '',
  confirmPassword: ''
})

// Form state
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)
const successMessage = ref('')
const tokenError = ref('')
const resetToken = ref('')
const errors = reactive({
  password: '',
  confirmPassword: '',
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
  errors.password = ''
  errors.confirmPassword = ''
  errors.general = ''

  let isValid = true

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

  return isValid
}

const validateResetToken = async () => {
  const token = route.query.token as string
  
  if (!token) {
    tokenError.value = 'Invalid reset link. No token provided.'
    return false
  }

  try {
    // Validate token with backend
    await apiService.validateResetToken(token)
    resetToken.value = token
    return true
  } catch (error: any) {
    if (error.response?.status === 400) {
      tokenError.value = 'This reset link has expired or is invalid.'
    } else if (error.response?.status === 404) {
      tokenError.value = 'This reset link is not valid.'
    } else {
      tokenError.value = 'Unable to validate reset link. Please try again.'
    }
    return false
  }
}

const handleResetPassword = async () => {
  if (!validateForm()) {
    return
  }

  isLoading.value = true
  errors.general = ''
  successMessage.value = ''

  try {
    // Call backend API to reset password
    await apiService.resetPassword({
      token: resetToken.value,
      newPassword: form.password
    })

    // Show success message
    successMessage.value = 'Password has been reset successfully! Redirecting to login...'
    
    // Clear form
    form.password = ''
    form.confirmPassword = ''

    // Redirect to login page after 3 seconds
    setTimeout(() => {
      router.push('/login')
    }, 3000)

  } catch (error: any) {
    if (error.response?.status === 400) {
      errors.general = 'Reset link has expired. Please request a new one.'
    } else if (error.response?.status === 404) {
      errors.general = 'Invalid reset link. Please request a new one.'
    } else {
      errors.general = 'Failed to reset password. Please try again.'
    }
  } finally {
    isLoading.value = false
  }
}

// Initialize component
onMounted(async () => {
  await validateResetToken()
})
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
