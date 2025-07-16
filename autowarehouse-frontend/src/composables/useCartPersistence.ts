import { ref, watch } from 'vue'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'

export const useCartPersistence = () => {
  const cartStore = useCartStore()
  const authStore = useAuthStore()
  const isOnline = ref(navigator.onLine)
  const pendingOperations = ref<Array<{ type: string, data: any }>>([])

  // Listen for online/offline events
  window.addEventListener('online', () => {
    isOnline.value = true
    syncPendingOperations()
  })
  
  window.addEventListener('offline', () => {
    isOnline.value = false
  })

  // Auto-save cart changes to localStorage
  const saveCartToLocal = () => {
    if (authStore.user?.id) {
      const cartData = {
        items: cartStore.items,
        summary: cartStore.summary,
        timestamp: Date.now(),
        userId: authStore.user.id
      }
      localStorage.setItem(`cart_${authStore.user.id}`, JSON.stringify(cartData))
    }
  }

  // Load cart from localStorage
  const loadCartFromLocal = () => {
    if (authStore.user?.id) {
      const savedCart = localStorage.getItem(`cart_${authStore.user.id}`)
      if (savedCart) {
        try {
          const cartData = JSON.parse(savedCart)
          // Only load if it's recent (within 24 hours) and for the same user
          const isRecent = Date.now() - cartData.timestamp < 24 * 60 * 60 * 1000
          const isSameUser = cartData.userId === authStore.user.id
          
          if (isRecent && isSameUser) {
            return cartData
          }
        } catch (error) {
          console.error('Error loading cart from localStorage:', error)
        }
      }
    }
    return null
  }

  // Clear local cart data
  const clearLocalCart = () => {
    if (authStore.user?.id) {
      localStorage.removeItem(`cart_${authStore.user.id}`)
    }
  }

  // Add operation to pending queue when offline
  const addPendingOperation = (type: string, data: any) => {
    if (!isOnline.value) {
      pendingOperations.value.push({ type, data })
      localStorage.setItem('pendingCartOperations', JSON.stringify(pendingOperations.value))
    }
  }

  // Sync pending operations when back online
  const syncPendingOperations = async () => {
    if (pendingOperations.value.length === 0) return

    try {
      for (const operation of pendingOperations.value) {
        switch (operation.type) {
          case 'addToCart':
            await cartStore.addToCart(operation.data.productId, operation.data.quantity)
            break
          case 'updateQuantity':
            await cartStore.updateQuantity(operation.data.cartItemId, operation.data.quantity)
            break
          case 'removeItem':
            await cartStore.removeItem(operation.data.cartItemId)
            break
          case 'toggleSelection':
            await cartStore.toggleSelection(operation.data.cartItemId)
            break
          case 'selectAll':
            await cartStore.selectAll(operation.data.selected)
            break
        }
      }
      
      // Clear pending operations after successful sync
      pendingOperations.value = []
      localStorage.removeItem('pendingCartOperations')
    } catch (error) {
      console.error('Error syncing pending cart operations:', error)
    }
  }

  // Load pending operations from localStorage
  const loadPendingOperations = () => {
    const saved = localStorage.getItem('pendingCartOperations')
    if (saved) {
      try {
        pendingOperations.value = JSON.parse(saved)
      } catch (error) {
        console.error('Error loading pending operations:', error)
      }
    }
  }

  // Sync cart with server
  const syncCartWithServer = async () => {
    if (!isOnline.value || !authStore.user?.id) return

    try {
      // Fetch latest cart from server
      await cartStore.fetchCartItems()
      
      // Update local storage with latest data
      saveCartToLocal()
    } catch (error) {
      console.error('Error syncing cart with server:', error)
      
      // If sync fails, try to load from local storage
      const localCart = loadCartFromLocal()
      if (localCart) {
        // Restore from local storage (optimistic approach)
        cartStore.items = localCart.items
        cartStore.summary = localCart.summary
      }
    }
  }

  // Auto-sync cart periodically (every 30 seconds)
  const startAutoSync = () => {
    const syncInterval = setInterval(() => {
      if (isOnline.value && authStore.isAuthenticated) {
        syncCartWithServer()
      }
    }, 30000) // 30 seconds

    // Return cleanup function
    return () => clearInterval(syncInterval)
  }

  // Watch for cart changes and auto-save
  watch(
    () => [cartStore.items, cartStore.summary],
    () => {
      saveCartToLocal()
    },
    { deep: true }
  )

  // Watch for auth changes
  watch(
    () => authStore.user,
    (newUser, oldUser) => {
      if (newUser && newUser.id !== oldUser?.id) {
        // User logged in or switched - sync cart
        syncCartWithServer()
        loadPendingOperations()
        syncPendingOperations()
      } else if (!newUser && oldUser) {
        // User logged out - clear local cart
        clearLocalCart()
        pendingOperations.value = []
      }
    }
  )

  return {
    isOnline,
    pendingOperations,
    saveCartToLocal,
    loadCartFromLocal,
    clearLocalCart,
    addPendingOperation,
    syncPendingOperations,
    syncCartWithServer,
    startAutoSync,
    loadPendingOperations
  }
}
