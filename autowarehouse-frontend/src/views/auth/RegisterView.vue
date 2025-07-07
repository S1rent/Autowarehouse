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
            
            <div>
              <label for="fullname" class="block text-sm font-medium text-gray-700 mb-2">Full Name</label>
              <div class="relative">
                <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <i class="fa-solid fa-user text-gray-400"></i>
                </div>
                <input 
                  type="text" 
                  id="fullname" 
                  v-model="form.fullname"
                  required 
                  class="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent outline-none transition-all" 
                  placeholder="Enter your full name"
                  :class="{ 'border-red-500': errors.fullname }"
                >
              </div>
              <p v-if="errors.fullname" class="mt-1 text-sm text-red-600">{{ errors.fullname }}</p>
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
                  <span class="text-green-600 hover:text-green-800 font-medium cursor-pointer">Terms of Service</span>
                  and 
                  <span class="text-green-600 hover:text-green-800 font-medium cursor-pointer">Privacy Policy</span>
                </label>
              </div>
            </div>
            <p v-if="errors.agreeToTerms" class="mt-1 text-sm text-red-600">{{ errors.agreeToTerms }}</p>

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

            <div class="relative my-6">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-gray-300"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-2 bg-white text-gray-500">Or sign up with</span>
              </div>
            </div>

            <button 
              type="button" 
              @click="handleGoogleRegister"
              class="w-full flex items-center justify-center px-4 py-3 border border-gray-300 rounded-lg text-gray-700 bg-white hover:bg-gray-50 focus:ring-4 focus:ring-gray-200 transition-all duration-200"
            >
              <i class="fa-brands fa-google text-red-500 mr-3"></i>
              <span class="font-medium">Sign up with Google</span>
            </button>

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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Form data
const form = reactive({
  fullname: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: false
})

// Form state
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)
const errors = reactive({
  fullname: '',
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

  const levels = {
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
  errors.fullname = ''
  errors.email = ''
  errors.password = ''
  errors.confirmPassword = ''
  errors.agreeToTerms = ''
  errors.general = ''

  let isValid = true

  // Full name validation
  if (!form.fullname.trim()) {
    errors.fullname = 'Full name is required'
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
  } else if (form.password.length < 8) {
    errors.password = 'Password must be at least 8 characters'
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

  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    // Mock registration logic
    console.log('Registration attempt:', {
      fullname: form.fullname,
      email: form.email,
      password: form.password
    })

    // Redirect to login page
    router.push('/login')
  } catch (error) {
    errors.general = 'Registration failed. Please try again.'
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
