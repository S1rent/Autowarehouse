import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type Product } from '@/services/api'

export const useWishlistStore = defineStore('wishlist', () => {
  // State
  const wishlistItems = ref<Product[]>([])
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Getters
  const wishlistCount = computed(() => wishlistItems.value.length)
  
  const isInWishlist = computed(() => (productId: number) => {
    return wishlistItems.value.some(item => item.id === productId)
  })

  // Actions
  const loadWishlist = async () => {
    try {
      isLoading.value = true
      error.value = null
      
      // TODO: Replace with actual API call when wishlist endpoint is available
      // For now, load from localStorage
      const savedWishlist = localStorage.getItem('wishlist_items')
      if (savedWishlist) {
        const productIds = JSON.parse(savedWishlist) as number[]
        
        // Fetch product details for each ID
        const products = await Promise.all(
          productIds.map(async (id) => {
            try {
              return await apiService.getProduct(id)
            } catch (error) {
              console.error(`Failed to load product ${id}:`, error)
              return null
            }
          })
        )
        
        wishlistItems.value = products.filter(product => product !== null) as Product[]
      }
    } catch (err) {
      console.error('Error loading wishlist:', err)
      error.value = 'Failed to load wishlist'
    } finally {
      isLoading.value = false
    }
  }

  const addToWishlist = async (productId: number) => {
    try {
      // Check if already in wishlist
      if (isInWishlist.value(productId)) {
        return
      }

      // Fetch product details
      const product = await apiService.getProduct(productId)
      wishlistItems.value.push(product)
      
      // Save to localStorage (TODO: Replace with API call)
      const productIds = wishlistItems.value.map(item => item.id)
      localStorage.setItem('wishlist_items', JSON.stringify(productIds))
      
      console.log('Added to wishlist:', productId)
    } catch (err) {
      console.error('Error adding to wishlist:', err)
      error.value = 'Failed to add to wishlist'
      throw err
    }
  }

  const removeFromWishlist = async (productId: number) => {
    try {
      wishlistItems.value = wishlistItems.value.filter(item => item.id !== productId)
      
      // Save to localStorage (TODO: Replace with API call)
      const productIds = wishlistItems.value.map(item => item.id)
      localStorage.setItem('wishlist_items', JSON.stringify(productIds))
      
      console.log('Removed from wishlist:', productId)
    } catch (err) {
      console.error('Error removing from wishlist:', err)
      error.value = 'Failed to remove from wishlist'
      throw err
    }
  }

  const toggleWishlist = async (productId: number) => {
    if (isInWishlist.value(productId)) {
      await removeFromWishlist(productId)
    } else {
      await addToWishlist(productId)
    }
  }

  const clearWishlist = () => {
    wishlistItems.value = []
    localStorage.removeItem('wishlist_items')
  }

  return {
    // State
    wishlistItems,
    isLoading,
    error,
    
    // Getters
    wishlistCount,
    isInWishlist,
    
    // Actions
    loadWishlist,
    addToWishlist,
    removeFromWishlist,
    toggleWishlist,
    clearWishlist
  }
})
