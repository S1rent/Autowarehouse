import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type SavedItem, type SaveForLaterRequest } from '@/services/api'
import { useAuthStore } from './auth'
import { useNotifications } from '@/composables/useNotifications'

export const useSavedItemsStore = defineStore('savedItems', () => {
  // State
  const items = ref<SavedItem[]>([])
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const itemCount = computed(() => items.value.length)
  const totalQuantity = computed(() => items.value.reduce((total, item) => total + item.quantity, 0))
  const hasItems = computed(() => items.value.length > 0)
  const totalValue = computed(() => items.value.reduce((total, item) => total + item.subtotal, 0))
  const totalSavings = computed(() => items.value.reduce((total, item) => total + (item.savings || 0), 0))

  // Actions
  const fetchSavedItems = async () => {
    const authStore = useAuthStore()
    if (!authStore.user) return

    try {
      isLoading.value = true
      error.value = null
      
      const savedItems = await apiService.getSavedItems(authStore.user.id)
      items.value = savedItems
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch saved items'
      console.error('Error fetching saved items:', err)
    } finally {
      isLoading.value = false
    }
  }

  const saveForLater = async (productId: number, quantity: number = 1) => {
    const authStore = useAuthStore()
    const { success, error: notifyError } = useNotifications()
    
    if (!authStore.user) throw new Error('User not authenticated')

    try {
      isLoading.value = true
      error.value = null
      
      const savedData: SaveForLaterRequest = {
        userId: authStore.user.id,
        productId,
        quantity
      }
      
      const savedItem = await apiService.saveForLater(savedData)
      
      // Check if item already exists in saved items
      const existingItemIndex = items.value.findIndex(item => item.productId === productId)
      
      if (existingItemIndex >= 0) {
        // Update existing item
        items.value[existingItemIndex] = savedItem
        success('Updated Saved Item', `Quantity updated to ${savedItem.quantity}`)
      } else {
        // Add new item
        items.value.push(savedItem)
        success('Saved for Later', `${savedItem.productName} has been saved for later`)
      }
      
      return savedItem
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to save item for later'
      error.value = errorMessage
      notifyError('Save Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const moveToSaved = async (cartItemId: number) => {
    const { success, error: notifyError } = useNotifications()
    
    try {
      isLoading.value = true
      error.value = null
      
      const savedItem = await apiService.moveToSaved(cartItemId)
      
      // Check if item already exists in saved items
      const existingItemIndex = items.value.findIndex(item => item.productId === savedItem.productId)
      
      if (existingItemIndex >= 0) {
        // Update existing item
        items.value[existingItemIndex] = savedItem
      } else {
        // Add new item
        items.value.push(savedItem)
      }
      
      success('Moved to Saved', `${savedItem.productName} has been moved to saved items`)
      return savedItem
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to move item to saved'
      error.value = errorMessage
      notifyError('Move Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const moveToCart = async (savedItemId: number) => {
    const { success, error: notifyError } = useNotifications()
    
    try {
      isLoading.value = true
      error.value = null
      
      // Get item name before moving for notification
      const item = items.value.find(item => item.id === savedItemId)
      const itemName = item?.productName || 'Item'
      
      const cartItem = await apiService.moveToCart(savedItemId)
      
      // Remove from saved items
      items.value = items.value.filter(item => item.id !== savedItemId)
      
      success('Moved to Cart', `${itemName} has been moved to your cart`)
      return cartItem
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to move item to cart'
      error.value = errorMessage
      notifyError('Move Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const moveAllToCart = async () => {
    const authStore = useAuthStore()
    const { success, error: notifyError, info } = useNotifications()
    
    if (!authStore.user) return

    try {
      isLoading.value = true
      error.value = null
      
      const cartItems = await apiService.moveAllSavedToCart(authStore.user.id)
      
      if (cartItems.length > 0) {
        // Clear saved items since they were moved
        items.value = []
        success('Moved to Cart', `${cartItems.length} items have been moved to your cart`)
      } else {
        info('No Items Moved', 'No items could be moved to cart (possibly out of stock)')
      }
      
      return cartItems
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to move items to cart'
      error.value = errorMessage
      notifyError('Move Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const updateQuantity = async (savedItemId: number, quantity: number) => {
    const { success, error: notifyError } = useNotifications()
    
    try {
      isLoading.value = true
      error.value = null
      
      await apiService.updateSavedItemQuantity(savedItemId, quantity)
      
      // Update local state
      const itemIndex = items.value.findIndex(item => item.id === savedItemId)
      if (itemIndex >= 0) {
        items.value[itemIndex].quantity = quantity
        items.value[itemIndex].subtotal = items.value[itemIndex].productPrice * quantity
        success('Quantity Updated', `Updated to ${quantity} items`)
      }
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to update quantity'
      error.value = errorMessage
      notifyError('Update Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const removeItem = async (savedItemId: number) => {
    const { success, error: notifyError } = useNotifications()
    
    try {
      isLoading.value = true
      error.value = null
      
      // Get item name before removing for notification
      const item = items.value.find(item => item.id === savedItemId)
      const itemName = item?.productName || 'Item'
      
      await apiService.removeSavedItem(savedItemId)
      
      // Remove from local state
      items.value = items.value.filter(item => item.id !== savedItemId)
      
      success('Item Removed', `${itemName} has been removed from saved items`)
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to remove item'
      error.value = errorMessage
      notifyError('Remove Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const clearSavedItems = async () => {
    const authStore = useAuthStore()
    const { success, error: notifyError } = useNotifications()
    
    if (!authStore.user) return

    try {
      isLoading.value = true
      error.value = null
      
      await apiService.clearSavedItems(authStore.user.id)
      
      // Clear local state
      items.value = []
      
      success('Saved Items Cleared', 'All saved items have been removed')
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || 'Failed to clear saved items'
      error.value = errorMessage
      notifyError('Clear Failed', errorMessage)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const checkProductSaved = async (productId: number): Promise<boolean> => {
    const authStore = useAuthStore()
    if (!authStore.user) return false

    try {
      const response = await apiService.checkProductSaved(authStore.user.id, productId)
      return response.exists
    } catch (err) {
      console.error('Error checking product in saved items:', err)
      return false
    }
  }

  const getSavedItemCount = async (): Promise<number> => {
    const authStore = useAuthStore()
    if (!authStore.user) return 0

    try {
      const response = await apiService.getSavedItemCount(authStore.user.id)
      return response.count
    } catch (err) {
      console.error('Error getting saved item count:', err)
      return 0
    }
  }

  const clearError = () => {
    error.value = null
  }

  const resetSavedItems = () => {
    items.value = []
    error.value = null
    isLoading.value = false
  }

  return {
    // State
    items,
    isLoading,
    error,
    
    // Getters
    itemCount,
    totalQuantity,
    hasItems,
    totalValue,
    totalSavings,
    
    // Actions
    fetchSavedItems,
    saveForLater,
    moveToSaved,
    moveToCart,
    moveAllToCart,
    updateQuantity,
    removeItem,
    clearSavedItems,
    checkProductSaved,
    getSavedItemCount,
    clearError,
    resetSavedItems
  }
})
