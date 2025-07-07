import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type CartItem, type CartSummary, type AddToCartRequest } from '@/services/api'
import { useAuthStore } from './auth'

export const useCartStore = defineStore('cart', () => {
  // State
  const items = ref<CartItem[]>([])
  const summary = ref<CartSummary | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const itemCount = computed(() => items.value.length)
  const totalQuantity = computed(() => items.value.reduce((total, item) => total + item.quantity, 0))
  const selectedItems = computed(() => items.value.filter(item => item.isSelected))
  const selectedCount = computed(() => selectedItems.value.length)
  const hasItems = computed(() => items.value.length > 0)
  const hasSelectedItems = computed(() => selectedItems.value.length > 0)

  // Actions
  const fetchCartItems = async () => {
    const authStore = useAuthStore()
    if (!authStore.user) return

    try {
      isLoading.value = true
      error.value = null
      
      const cartItems = await apiService.getCartItems(authStore.user.id)
      items.value = cartItems
      
      // Also fetch summary
      await fetchCartSummary()
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch cart items'
      console.error('Error fetching cart items:', err)
    } finally {
      isLoading.value = false
    }
  }

  const fetchCartSummary = async () => {
    const authStore = useAuthStore()
    if (!authStore.user) return

    try {
      const cartSummary = await apiService.getCartSummary(authStore.user.id)
      summary.value = cartSummary
    } catch (err: any) {
      console.error('Error fetching cart summary:', err)
    }
  }

  const addToCart = async (productId: number, quantity: number = 1) => {
    const authStore = useAuthStore()
    if (!authStore.user) throw new Error('User not authenticated')

    try {
      isLoading.value = true
      error.value = null
      
      const cartData: AddToCartRequest = {
        userId: authStore.user.id,
        productId,
        quantity
      }
      
      const newItem = await apiService.addToCart(cartData)
      
      // Check if item already exists in cart
      const existingItemIndex = items.value.findIndex(item => item.productId === productId)
      
      if (existingItemIndex >= 0) {
        // Update existing item
        items.value[existingItemIndex] = newItem
      } else {
        // Add new item
        items.value.push(newItem)
      }
      
      await fetchCartSummary()
      return newItem
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to add item to cart'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const updateQuantity = async (cartItemId: number, quantity: number) => {
    try {
      isLoading.value = true
      error.value = null
      
      await apiService.updateCartItemQuantity(cartItemId, quantity)
      
      // Update local state
      const itemIndex = items.value.findIndex(item => item.id === cartItemId)
      if (itemIndex >= 0) {
        items.value[itemIndex].quantity = quantity
        items.value[itemIndex].subtotal = items.value[itemIndex].productPrice * quantity
      }
      
      await fetchCartSummary()
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to update quantity'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const toggleSelection = async (cartItemId: number) => {
    try {
      await apiService.toggleCartItemSelection(cartItemId)
      
      // Update local state
      const itemIndex = items.value.findIndex(item => item.id === cartItemId)
      if (itemIndex >= 0) {
        items.value[itemIndex].isSelected = !items.value[itemIndex].isSelected
      }
      
      await fetchCartSummary()
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to toggle selection'
      throw err
    }
  }

  const selectAll = async (selected: boolean) => {
    const authStore = useAuthStore()
    if (!authStore.user) return

    try {
      await apiService.selectAllCartItems(authStore.user.id, selected)
      
      // Update local state
      items.value.forEach(item => {
        item.isSelected = selected
      })
      
      await fetchCartSummary()
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to select all items'
      throw err
    }
  }

  const removeItem = async (cartItemId: number) => {
    try {
      isLoading.value = true
      error.value = null
      
      await apiService.removeFromCart(cartItemId)
      
      // Remove from local state
      items.value = items.value.filter(item => item.id !== cartItemId)
      
      await fetchCartSummary()
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to remove item'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const clearCart = async () => {
    const authStore = useAuthStore()
    if (!authStore.user) return

    try {
      isLoading.value = true
      error.value = null
      
      await apiService.clearCart(authStore.user.id)
      
      // Clear local state
      items.value = []
      summary.value = null
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to clear cart'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const checkProductInCart = async (productId: number): Promise<boolean> => {
    const authStore = useAuthStore()
    if (!authStore.user) return false

    try {
      const response = await apiService.checkProductInCart(authStore.user.id, productId)
      return response.exists
    } catch (err) {
      console.error('Error checking product in cart:', err)
      return false
    }
  }

  const getCartItemCount = async (): Promise<number> => {
    const authStore = useAuthStore()
    if (!authStore.user) return 0

    try {
      const response = await apiService.getCartItemCount(authStore.user.id)
      return response.count
    } catch (err) {
      console.error('Error getting cart item count:', err)
      return 0
    }
  }

  const clearError = () => {
    error.value = null
  }

  const resetCart = () => {
    items.value = []
    summary.value = null
    error.value = null
    isLoading.value = false
  }

  return {
    // State
    items,
    summary,
    isLoading,
    error,
    
    // Getters
    itemCount,
    totalQuantity,
    selectedItems,
    selectedCount,
    hasItems,
    hasSelectedItems,
    
    // Actions
    fetchCartItems,
    fetchCartSummary,
    addToCart,
    updateQuantity,
    toggleSelection,
    selectAll,
    removeItem,
    clearCart,
    checkProductInCart,
    getCartItemCount,
    clearError,
    resetCart
  }
})
