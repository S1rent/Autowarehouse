<template>
  <div class="login-view">
    <div class="login-container">
      <div class="login-card card">
        <div class="login-header">
          <div class="logo-section">
            <h1 class="logo-text">Autowarehouse</h1>
            <p class="logo-subtitle">Computer E-commerce & Live Auction</p>
          </div>
          <h2 class="login-title">Welcome Back</h2>
          <p class="login-subtitle">Sign in to your account to continue</p>
        </div>

        <form @submit.prevent="handleLogin" class="login-form">
          <div class="form-group">
            <label for="email" class="form-label">Email Address</label>
            <input
              id="email"
              v-model="loginForm.email"
              type="email"
              class="form-input"
              :class="{ error: errors.email }"
              placeholder="Enter your email address"
              required
            />
            <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
          </div>

          <div class="form-group">
            <label for="password" class="form-label">Password</label>
            <div class="password-input-container">
              <input
                id="password"
                v-model="loginForm.password"
                :type="showPassword ? 'text' : 'password'"
                class="form-input"
                :class="{ error: errors.password }"
                placeholder="Enter your password"
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
          </div>

          <div class="form-options">
            <label class="checkbox-container">
              <input
                v-model="loginForm.rememberMe"
                type="checkbox"
                class="checkbox-input"
              />
              <span class="checkbox-label">Remember me</span>
            </label>
            <a href="#" class="forgot-password">Forgot Password?</a>
          </div>

          <button
            type="submit"
            class="btn btn-primary btn-full login-button"
            :disabled="isLoading"
          >
            <span v-if="isLoading" class="loading"></span>
            {{ isLoading ? 'Signing In...' : 'Sign In' }}
          </button>

          <div class="divider">
            <span>or continue with</span>
          </div>

          <button
            type="button"
            @click="handleGoogleLogin"
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

        <div class="login-footer">
          <p>
            Don't have an account?
            <router-link to="/register" class="register-link">Sign up here</router-link>
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
const loginForm = reactive({
  email: '',
  password: '',
  rememberMe: false
})

// UI state
const showPassword = ref(false)
const isLoading = ref(false)

// Validation errors
const errors = reactive({
  email: '',
  password: ''
})

// Methods
const togglePassword = () => {
  showPassword.value = !showPassword.value
}

const validateForm = () => {
  errors.email = ''
  errors.password = ''

  if (!loginForm.email) {
    errors.email = 'Email is required'
    return false
  }

  if (!loginForm.email.includes('@')) {
    errors.email = 'Please enter a valid email address'
    return false
  }

  if (!loginForm.password) {
    errors.password = 'Password is required'
    return false
  }

  if (loginForm.password.length < 6) {
    errors.password = 'Password must be at least 6 characters'
    return false
  }

  return true
}

const handleLogin = async () => {
  if (!validateForm()) return

  isLoading.value = true

  try {
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    // For now, just redirect to home
    // In real implementation, this would call the authentication API
    console.log('Login attempt:', loginForm)
    
    // Simulate successful login
    router.push('/')
  } catch (error) {
    console.error('Login error:', error)
    errors.email = 'Invalid email or password'
  } finally {
    isLoading.value = false
  }
}

const handleGoogleLogin = async () => {
  try {
    // Simulate Google OAuth
    console.log('Google login initiated')
    // In real implementation, this would integrate with Google OAuth
    router.push('/')
  } catch (error) {
    console.error('Google login error:', error)
  }
}
</script>

<style scoped>
.login-view {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 1rem;
}

.login-container {
  width: 100%;
  max-width: 420px;
}

.login-card {
  padding: 2.5rem;
  background: white;
  border-radius: 1.5rem;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.login-header {
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

.login-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.login-subtitle {
  color: var(--text-secondary);
  font-size: 0.875rem;
}

.login-form {
  margin-bottom: 1.5rem;
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

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  font-size: 0.875rem;
}

.checkbox-container {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.checkbox-input {
  margin-right: 0.5rem;
  accent-color: var(--primary-color);
}

.checkbox-label {
  color: var(--text-secondary);
}

.forgot-password {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
}

.forgot-password:hover {
  text-decoration: underline;
}

.login-button {
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

.login-footer {
  text-align: center;
  font-size: 0.875rem;
  color: var(--text-secondary);
}

.register-link {
  color: var(--primary-color);
  text-decoration: none;
  font-weight: 500;
}

.register-link:hover {
  text-decoration: underline;
}

@media (max-width: 480px) {
  .login-card {
    padding: 2rem 1.5rem;
  }
  
  .form-options {
    flex-direction: column;
    gap: 1rem;
    align-items: flex-start;
  }
}
</style>
