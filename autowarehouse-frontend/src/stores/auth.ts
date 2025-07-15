import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type User, type LoginRequest, type RegisterRequest, type AuthResponse, type PasswordResetRequest, type PasswordResetConfirmRequest } from '@/services/api'

export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref<User | null>(null)
  const token = ref<string | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const isAuthenticated = computed(() => !!token.value && !!user.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')
  const isCustomer = computed(() => user.value?.role === 'CUSTOMER')
  const fullName = computed(() => {
    if (!user.value) return ''
    return `${user.value.firstName} ${user.value.lastName}`
  })

  // Actions
  const login = async (credentials: LoginRequest) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.login(credentials)
      
      if (response.accessToken && response.userId) {
        // Create user object from response
        user.value = {
          id: response.userId,
          email: response.email || '',
          firstName: response.firstName || '',
          lastName: response.lastName || '',
          role: (response.role as 'ADMIN' | 'CUSTOMER') || 'CUSTOMER',
          isActive: true,
          isEmailVerified: response.isEmailVerified || false
        }
        token.value = response.accessToken
        
        // Store in localStorage
        localStorage.setItem('auth_token', response.accessToken)
        localStorage.setItem('refresh_token', response.refreshToken || '')
        localStorage.setItem('user_data', JSON.stringify(user.value))
      }
      
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Login failed'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const register = async (userData: RegisterRequest) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.register(userData)
      
      // Registration successful, but user needs to verify email
      // Don't auto-login, just return the response
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Registration failed'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const logout = () => {
    user.value = null
    token.value = null
    error.value = null
    
    // Clear localStorage
    localStorage.removeItem('auth_token')
    localStorage.removeItem('user_data')
  }

  const forgotPassword = async (email: string) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.forgotPassword({ email })
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to send reset email'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const resetPassword = async (token: string, newPassword: string) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.resetPassword({ token, newPassword })
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to reset password'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const verifyEmail = async (token: string) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.verifyEmail(token)
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to verify email'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const resendVerification = async (email: string) => {
    try {
      isLoading.value = true
      error.value = null
      
      const response = await apiService.resendVerification(email)
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to resend verification'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const updateProfile = async (userData: Partial<User>) => {
    if (!user.value) throw new Error('User not authenticated')
    
    try {
      isLoading.value = true
      error.value = null
      
      const updatedUser = await apiService.updateUserProfile(user.value.id, userData)
      user.value = updatedUser
      
      // Update localStorage
      localStorage.setItem('user_data', JSON.stringify(updatedUser))
      
      return updatedUser
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update profile'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const initializeAuth = () => {
    // Check if user is already logged in
    const storedToken = localStorage.getItem('auth_token')
    const storedUser = localStorage.getItem('user_data')
    
    if (storedToken && storedUser) {
      try {
        token.value = storedToken
        user.value = JSON.parse(storedUser)
      } catch (err) {
        // Invalid stored data, clear it
        logout()
      }
    }
  }

  const clearError = () => {
    error.value = null
  }

  return {
    // State
    user,
    token,
    isLoading,
    error,
    
    // Getters
    isAuthenticated,
    isAdmin,
    isCustomer,
    fullName,
    
    // Actions
    login,
    register,
    logout,
    forgotPassword,
    resetPassword,
    verifyEmail,
    resendVerification,
    updateProfile,
    initializeAuth,
    clearError
  }
})
