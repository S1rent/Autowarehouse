import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type WishlistItem } from '@/services/api'
import { useAuthStore } from '@/stores/auth'

export const useWishlistStore = defineStore('wishlist', () => {
  // State
  const wishlistItems = ref<WishlistItem[]>([])
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const wishlistCount = computed(() => wishlistItems.value.length)
  
  const isInWishlist = computed(() => (productId: number) => {
    return wishlistItems.value.some(item => item.product.id === productId)
  })

  const getWishlistProducts = computed(() => {
    return wishlistItems.value.map(item => item.product)
  })

  // Actions
  const loadWishlist = async () => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      console.warn('User not authenticated, cannot load wishlist')
      return
    }

    try {
      isLoading.value = true
      error.value = null
      
      const items = await apiService.getWishlistItems(authStore.user.id)
      wishlistItems.value = items
      
      console.log('Wishlist loaded successfully:', items.length, 'items')
    } catch (err: any) {
      console.error('Error loading wishlist:', err)
      error.value = err.response?.data?.message || 'Failed to load wishlist'
      wishlistItems.value = []
    } finally {
      isLoading.value = false
    }
  }

  const addToWishlist = async (productId: number) => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      throw new Error('User not authenticated')
    }

    try {
      // Check if already in wishlist
      if (isInWishlist.value(productId)) {
        console.log('Product already in wishlist:', productId)
        return
      }

      isLoading.value = true
      error.value = null

      const newItem = await apiService.addToWishlist({
        userId: authStore.user.id,
        productId: productId
      })
      
      wishlistItems.value.push(newItem)
      console.log('Added to wishlist:', productId)
    } catch (err: any) {
      console.error('Error adding to wishlist:', err)
      error.value = err.response?.data?.message || 'Failed to add to wishlist'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const removeFromWishlist = async (productId: number) => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      throw new Error('User not authenticated')
    }

    try {
      isLoading.value = true
      error.value = null

      await apiService.removeFromWishlist({
        userId: authStore.user.id,
        productId: productId
      })
      
      wishlistItems.value = wishlistItems.value.filter(item => item.product.id !== productId)
      console.log('Removed from wishlist:', productId)
    } catch (err: any) {
      console.error('Error removing from wishlist:', err)
      error.value = err.response?.data?.message || 'Failed to remove from wishlist'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const toggleWishlist = async (productId: number) => {
    if (isInWishlist.value(productId)) {
      await removeFromWishlist(productId)
    } else {
      await addToWishlist(productId)
    }
  }

  const clearWishlist = async () => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      throw new Error('User not authenticated')
    }

    try {
      isLoading.value = true
      error.value = null

      await apiService.clearWishlist(authStore.user.id)
      wishlistItems.value = []
      console.log('Wishlist cleared successfully')
    } catch (err: any) {
      console.error('Error clearing wishlist:', err)
      error.value = err.response?.data?.message || 'Failed to clear wishlist'
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const checkProductInWishlist = async (productId: number): Promise<boolean> => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      return false
    }

    try {
      const result = await apiService.checkProductInWishlist(authStore.user.id, productId)
      return result.exists
    } catch (err) {
      console.error('Error checking product in wishlist:', err)
      return false
    }
  }

  const getWishlistCount = async (): Promise<number> => {
    const authStore = useAuthStore()
    if (!authStore.user?.id) {
      return 0
    }

    try {
      const result = await apiService.getWishlistCount(authStore.user.id)
      return result.count
    } catch (err) {
      console.error('Error getting wishlist count:', err)
      return 0
    }
  }

  // Clear wishlist when user logs out
  const clearLocalWishlist = () => {
    wishlistItems.value = []
    error.value = null
  }

  return {
    // State
    wishlistItems,
    isLoading,
    error,
    
    // Getters
    wishlistCount,
    isInWishlist,
    getWishlistProducts,
    
    // Actions
    loadWishlist,
    addToWishlist,
    removeFromWishlist,
    toggleWishlist,
    clearWishlist,
    checkProductInWishlist,
    getWishlistCount,
    clearLocalWishlist
  }
})
