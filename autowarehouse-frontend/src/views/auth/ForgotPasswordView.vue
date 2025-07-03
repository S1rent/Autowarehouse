<template>
  <div class="forgot-password-view">
    <div class="forgot-password-container">
      <div class="forgot-password-card card">
        <div class="forgot-password-header">
          <div class="logo-section">
            <h1 class="logo-text">Autowarehouse</h1>
            <p class="logo-subtitle">Computer E-commerce & Live Auction</p>
          </div>
          <h2 class="forgot-password-title">Forgot Password?</h2>
          <p class="forgot-password-subtitle">
            Don't worry! Enter your email address and we'll send you a link to reset your password.
          </p>
        </div>

        <form @submit.prevent="handleForgotPassword" class="forgot-password-form">
          <div class="form-group">
            <label for="email" class="form-label">Email Address</label>
            <input
              id="email"
              v-model="forgotPasswordForm.email"
              type="email"
              class="form-input"
              :class="{ error: errors.email }"
              placeholder="Enter your email address"
              required
            />
            <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
          </div>

          <button
            type="submit"
            class="btn btn-primary btn-full forgot-password-button"
            :disabled="isLoading"
          >
            <span v-if="isLoading" class="loading"></span>
            {{ isLoading ? 'Sending Reset Link...' : 'Send Reset Link' }}
          </button>

          <div v-if="successMessage" class="success-message">
            <div class="success-icon">✓</div>
            <p>{{ successMessage }}</p>
          </div>
        </form>

        <div class="forgot-password-footer">
          <p>
            Remember your password?
            <router-link to="/login" class="login-link">Back to Sign In</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Form state
const forgotPasswordForm = reactive({
  email: ''
})

// UI state
const isLoading = ref(false)
const successMessage = ref('')

// Validation errors
const errors = reactive({
  email: ''
})

// Methods
const validateForm = () => {
  errors.email = ''

  if (!forgotPasswordForm.email) {
    errors.email = 'Email is required'
    return false
  }

  if (!forgotPasswordForm.email.includes('@')) {
    errors.email = 'Please enter a valid email address'
    return false
  }

  return true
}

const handleForgotPassword = async () => {
  if (!validateForm()) return

  isLoading.value = true
  successMessage.value = ''

  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // For now, just show success message
    // In real implementation, this would call the forgot password API
    console.log('Forgot password request:', forgotPasswordForm)
    
    // Simulate successful request
    successMessage.value = 'Password reset link has been sent to your email address. Please check your inbox and follow the instructions.'
    
    // Clear form
    forgotPasswordForm.email = ''
    
    // Optionally redirect to login after a delay
    setTimeout(() => {
      router.push('/login')
    }, 3000)
    
  } catch (error) {
    console.error('Forgot password error:', error)
    errors.email = 'Failed to send reset link. Please try again.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.forgot-password-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.forgot-password-container {
  width: 100%;
  max-width: 420px;
}

.forgot-password-card {
  padding: 2.5rem;
  background: white;
  border-radius: 1.5rem;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.forgot-password-header {
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

.forgot-password-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.forgot-password-subtitle {
  color: var(--text-secondary);
  font-size: 0.875rem;
  line-height: 1.5;
}

.forgot-password-form {
  margin-bottom: 1.5rem;
}

.forgot-password-button {
  margin-bottom: 1.5rem;
}

.success-message {
  background: #f0f9ff;
  border: 1px solid #0ea5e9;
  border-radius: 0.75rem;
  padding: 1rem;
  margin-bottom: 1.5rem;
  text-align: center;
}

.success-icon {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 2rem;
  height: 2rem;
  background: #0ea5e9;
  color: white;
  border-radius: 50%;
  font-weight: bold;
  margin-bottom: 0.5rem;
}

.success-message p {
  color: #0369a1;
  font-size: 0.875rem;
  line-height: 1.5;
  margin: 0;
}

.forgot-password-footer {
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

@media (max-width: 480px) {
  .forgot-password-card {
    padding: 2rem 1.5rem;
  }
}
</style>
