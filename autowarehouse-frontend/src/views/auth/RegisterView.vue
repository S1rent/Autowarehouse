<template>
  <div class="register-view">
    <div class="register-container">
      <div class="register-card card">
        <div class="register-header">
          <div class="logo-section">
            <h1 class="logo-text">Autowarehouse</h1>
            <p class="logo-subtitle">Computer E-commerce & Live Auction</p>
          </div>
          <h2 class="register-title">Create Account</h2>
          <p class="register-subtitle">Join us to start shopping and bidding</p>
        </div>

        <form @submit.prevent="handleRegister" class="register-form">
          <div class="form-row">
            <div class="form-group">
              <label for="firstName" class="form-label">First Name</label>
              <input
                id="firstName"
                v-model="registerForm.firstName"
                type="text"
                class="form-input"
                :class="{ error: errors.firstName }"
                placeholder="Enter your first name"
                required
              />
              <span v-if="errors.firstName" class="error-message">{{ errors.firstName }}</span>
            </div>

            <div class="form-group">
              <label for="lastName" class="form-label">Last Name</label>
              <input
                id="lastName"
                v-model="registerForm.lastName"
                type="text"
                class="form-input"
                :class="{ error: errors.lastName }"
                placeholder="Enter your last name"
                required
              />
              <span v-if="errors.lastName" class="error-message">{{ errors.lastName }}</span>
            </div>
          </div>

          <div class="form-group">
            <label for="email" class="form-label">Email Address</label>
            <input
              id="email"
              v-model="registerForm.email"
              type="email"
              class="form-input"
              :class="{ error: errors.email }"
              placeholder="Enter your email address"
              required
            />
            <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
          </div>

          <div class="form-group">
            <label for="phone" class="form-label">Phone Number</label>
            <input
              id="phone"
              v-model="registerForm.phone"
              type="tel"
              class="form-input"
              :class="{ error: errors.phone }"
              placeholder="Enter your phone number"
              required
            />
            <span v-if="errors.phone" class="error-message">{{ errors.phone }}</span>
          </div>

          <div class="form-group">
            <label for="password" class="form-label">Password</label>
            <div class="password-input-container">
              <input
                id="password"
                v-model="registerForm.password"
                :type="showPassword ? 'text' : 'password'"
                class="form-input"
                :class="{ error: errors.password }"
                placeholder="Create a strong password"
                required
              />
              <button
                type="button"
                @click="togglePassword"
                class="password-toggle"
              >
                {{ showPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
            <span v-if="errors.password" class="error-message">{{ errors.password }}</span>
            <div class="password-strength">
              <div class="strength-bar">
                <div 
                  class="strength-fill" 
                  :class="passwordStrength.class"
                  :style="{ width: passwordStrength.width }"
                ></div>
              </div>
              <span class="strength-text">{{ passwordStrength.text }}</span>
            </div>
          </div>

          <div class="form-group">
            <label for="confirmPassword" class="form-label">Confirm Password</label>
            <div class="password-input-container">
              <input
                id="confirmPassword"
                v-model="registerForm.confirmPassword"
                :type="showConfirmPassword ? 'text' : 'password'"
                class="form-input"
                :class="{ error: errors.confirmPassword }"
                placeholder="Confirm your password"
                required
              />
              <button
                type="button"
                @click="toggleConfirmPassword"
                class="password-toggle"
              >
                {{ showConfirmPassword ? '👁️' : '👁️‍🗨️' }}
              </button>
            </div>
            <span v-if="errors.confirmPassword" class="error-message">{{ errors.confirmPassword }}</span>
          </div>

          <div class="form-group">
            <label class="checkbox-container">
              <input
                v-model="registerForm.agreeToTerms"
                type="checkbox"
                class="checkbox-input"
                required
              />
              <span class="checkbox-label">
                I agree to the 
                <a href="#" class="terms-link">Terms of Service</a> 
                and 
                <a href="#" class="terms-link">Privacy Policy</a>
              </span>
            </label>
            <span v-if="errors.agreeToTerms" class="error-message">{{ errors.agreeToTerms }}</span>
          </div>

          <div class="form-group">
            <label class="checkbox-container">
              <input
                v-model="registerForm.subscribeNewsletter"
                type="checkbox"
                class="checkbox-input"
              />
              <span class="checkbox-label">
                Subscribe to our newsletter for updates and promotions
              </span>
            </label>
          </div>

          <button
            type="submit"
            class="btn btn-primary btn-full register-button"
            :disabled="isLoading"
          >
            <span v-if="isLoading" class="loading"></span>
            {{ isLoading ? 'Creating Account...' : 'Create Account' }}
          </button>

          <div class="divider">
            <span>or sign up with</span>
          </div>

          <button
            type="button"
            @click="handleGoogleRegister"
            class="btn btn-secondary btn-full google-button"
          >
            <svg class="google-icon" viewBox="0 0 24 24" width="20" height="20">
              <path fill="#4285F4" d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"/>
              <path fill="#34A853" d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"/>
              <path fill="#FBBC05" d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"/>
              <path fill="#EA4335" d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"/>
            </svg>
            Continue with Google
          </button>
        </form>

        <div class="register-footer">
          <p>
            Already have an account?
            <router-link to="/login" class="login-link">Sign in here</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Form state
const registerForm = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: false,
  subscribeNewsletter: false
})

// UI state
const showPassword = ref(false)
const showConfirmPassword = ref(false)
const isLoading = ref(false)

// Validation errors
const errors = reactive({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  agreeToTerms: ''
})

// Password strength calculation
const passwordStrength = computed(() => {
  const password = registerForm.password
  if (!password) return { width: '0%', class: '', text: '' }

  let score = 0
  let feedback = []

  // Length check
  if (password.length >= 8) score += 1
  else feedback.push('at least 8 characters')

  // Uppercase check
  if (/[A-Z]/.test(password)) score += 1
  else feedback.push('uppercase letter')

  // Lowercase check
  if (/[a-z]/.test(password)) score += 1
  else feedback.push('lowercase letter')

  // Number check
  if (/\d/.test(password)) score += 1
  else feedback.push('number')

  // Special character check
  if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) score += 1
  else feedback.push('special character')

  const strength = {
    0: { width: '20%', class: 'very-weak', text: 'Very Weak' },
    1: { width: '40%', class: 'weak', text: 'Weak' },
    2: { width: '60%', class: 'fair', text: 'Fair' },
    3: { width: '80%', class: 'good', text: 'Good' },
    4: { width: '90%', class: 'strong', text: 'Strong' },
    5: { width: '100%', class: 'very-strong', text: 'Very Strong' }
  }

  return strength[score] || strength[0]
})

// Methods
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const toggleConfirmPassword = () => {
  showConfirmPassword.value = !showConfirmPassword.value
}

const validateForm = () => {
  // Reset errors
  Object.keys(errors).forEach(key => {
    errors[key] = ''
  })

  let isValid = true

  // First name validation
  if (!registerForm.firstName.trim()) {
    errors.firstName = 'First name is required'
    isValid = false
  }

  // Last name validation
  if (!registerForm.lastName.trim()) {
    errors.lastName = 'Last name is required'
    isValid = false
  }

  // Email validation
  if (!registerForm.email) {
    errors.email = 'Email is required'
    isValid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(registerForm.email)) {
    errors.email = 'Please enter a valid email address'
    isValid = false
  }

  // Phone validation
  if (!registerForm.phone) {
    errors.phone = 'Phone number is required'
    isValid = false
  } else if (!/^[\+]?[1-9][\d]{0,15}$/.test(registerForm.phone.replace(/\s/g, ''))) {
    errors.phone = 'Please enter a valid phone number'
    isValid = false
  }

  // Password validation
  if (!registerForm.password) {
    errors.password = 'Password is required'
    isValid = false
  } else if (registerForm.password.length < 8) {
    errors.password = 'Password must be at least 8 characters long'
    isValid = false
  }

  // Confirm password validation
  if (!registerForm.confirmPassword) {
    errors.confirmPassword = 'Please confirm your password'
    isValid = false
  } else if (registerForm.password !== registerForm.confirmPassword) {
    errors.confirmPassword = 'Passwords do not match'
    isValid = false
  }

  // Terms agreement validation
  if (!registerForm.agreeToTerms) {
    errors.agreeToTerms = 'You must agree to the terms and conditions'
    isValid = false
  }

  return isValid
}

const handleRegister = async () => {
  if (!validateForm()) return

  isLoading.value = true

  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // For now, just redirect to login
    // In real implementation, this would call the registration API
    console.log('Registration attempt:', registerForm)
    
    // Simulate successful registration
    router.push('/login')
  } catch (error) {
    console.error('Registration error:', error)
    errors.email = 'Registration failed. Please try again.'
  } finally {
    isLoading.value = false
  }
}

const handleGoogleRegister = async () => {
  try {
    // Simulate Google OAuth
    console.log('Google registration initiated')
    // In real implementation, this would integrate with Google OAuth
    router.push('/')
  } catch (error) {
    console.error('Google registration error:', error)
  }
}

// Watch for password changes to clear confirm password error
watch(() => registerForm.password, () => {
  if (errors.confirmPassword && registerForm.password === registerForm.confirmPassword) {
    errors.confirmPassword = ''
  }
})

watch(() => registerForm.confirmPassword, () => {
  if (errors.confirmPassword && registerForm.password === registerForm.confirmPassword) {
    errors.confirmPassword = ''
  }
})
</script>

<style scoped>
.register-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.register-container {
  width: 100%;
  max-width: 480px;
}

.register-card {
  padding: 2.5rem;
  background: white;
  border-radius: 1.5rem;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.register-header {
  text-align: center;
  margin-bottom: 2rem;
}

.logo-section {
  margin-bottom: 1.5rem;
}

.logo-text {
  font-size: 1.75rem;
  font-weight: 700;
  color: var(--primary-color);
  margin-bottom: 0.25rem;
}

.logo-subtitle {
  font-size: 0.875rem;
  color: var(--text-secondary);
}

.register-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.register-subtitle {
  color: var(--text-secondary);
  font-size: 0.875rem;
}

.register-form {
  margin-bottom: 1.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

.password-input-container {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 1rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.25rem;
  color: var(--text-secondary);
  padding: 0;
}

.password-strength {
  margin-top: 0.5rem;
}

.strength-bar {
  height: 4px;
  background: var(--border-color);
  border-radius: 2px;
  overflow: hidden;
  margin-bottom: 0.25rem;
}

.strength-fill {
  height: 100%;
  transition: width 0.3s ease;
  border-radius: 2px;
}

.strength-fill.very-weak { background: #ef4444; }
.strength-fill.weak { background: #f97316; }
.strength-fill.fair { background: #eab308; }
.strength-fill.good { background: #22c55e; }
.strength-fill.strong { background: #16a34a; }
.strength-fill.very-strong { background: #15803d; }

.strength-text {
  font-size: 0.75rem;
  color: var(--text-secondary);
}

.checkbox-container {
  display: flex;
  align-items: flex-start;
  cursor: pointer;
  line-height: 1.5;
}

.checkbox-input {
  margin-right: 0.5rem;
  margin-top: 0.125rem;
  accent-color: var(--primary-color);
  flex-shrink: 0;
}

.checkbox-label {
  color: var(--text-secondary);
  font-size: 0.875rem;
}

.terms-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
}

.terms-link:hover {
  text-decoration: underline;
}

.register-button {
  margin-bottom: 1.5rem;
}

.divider {
  text-align: center;
  margin: 1.5rem 0;
  position: relative;
  color: var(--text-secondary);
  font-size: 0.875rem;
}

.divider::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background: var(--border-color);
  z-index: 1;
}

.divider span {
  background: white;
  padding: 0 1rem;
  position: relative;
  z-index: 2;
}

.google-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  margin-bottom: 1.5rem;
}

.google-icon {
  flex-shrink: 0;
}

.register-footer {
  text-align: center;
  font-size: 0.875rem;
  color: var(--text-secondary);
}

.login-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
}

.login-link:hover {
  text-decoration: underline;
}

@media (max-width: 640px) {
  .register-card {
    padding: 2rem 1.5rem;
  }
  
  .form-row {
    grid-template-columns: 1fr;
    gap: 0;
  }
}
</style>
