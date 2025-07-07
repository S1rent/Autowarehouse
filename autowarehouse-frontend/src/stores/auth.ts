import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type User, type LoginRequest, type RegisterRequest } from '@/services/api'

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
      
      user.value = response.user
      token.value = response.token
      
      // Store in localStorage
      localStorage.setItem('auth_token', response.token)
      localStorage.setItem('user_data', JSON.stringify(response.user))
      
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
      
      const newUser = await apiService.register(userData)
      
      // Auto login after registration
      await login({
        email: userData.email,
        password: userData.password
      })
      
      return newUser
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
      
      const response = await apiService.forgotPassword(email)
      return response
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to send reset email'
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
    updateProfile,
    initializeAuth,
    clearError
  }
})
