import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type Order, type OrderDetail, type CheckoutOrderRequest } from '@/services/api'
import { useAuthStore } from './auth'
import { useCartStore } from './cart'
import { useNotifications } from '@/composables/useNotifications'

export const useOrderStore = defineStore('order', () => {
  // State
  const orders = ref<Order[]>([])
  const currentOrder = ref<OrderDetail | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Composables
  const authStore = useAuthStore()
  const cartStore = useCartStore()
  const { success, error: showError } = useNotifications()

  // Getters
  const userOrders = computed(() => orders.value)
  const hasOrders = computed(() => orders.value.length > 0)
  const pendingOrders = computed(() => orders.value.filter(order => order.status === 'PENDING'))
  const completedOrders = computed(() => orders.value.filter(order => order.status === 'DELIVERED'))

  // Actions
  const fetchUserOrders = async () => {
    if (!authStore.user?.id) return

    try {
      isLoading.value = true
      error.value = null
      orders.value = await apiService.getUserOrders(authStore.user.id)
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch orders'
      console.error('Error fetching orders:', err)
    } finally {
      isLoading.value = false
    }
  }

  const fetchOrder = async (orderId: number) => {
    try {
      isLoading.value = true
      error.value = null
      const order = await apiService.getOrder(orderId) as OrderDetail
      
      // Fetch product images for each order item
      if (order.items) {
        const itemsWithImages = await Promise.all(
          order.items.map(async (item) => {
            try {
              // Fetch product data to get real images from Firebase Storage
              const product = await apiService.getProduct(item.productId)
              return {
                ...item,
                productImages: product.imageUrls || []
              }
            } catch (productErr) {
              console.warn(`Could not fetch product ${item.productId}:`, productErr)
              // Return item without images if product fetch fails
              return {
                ...item,
                productImages: []
              }
            }
          })
        )
        order.items = itemsWithImages
      }
      
      // Fetch status history if available
      // try {
      //   const statusHistory = await apiService.getOrderStatusHistory(orderId)
      //   order.statusHistory = statusHistory
      // } catch (historyErr) {
      //   console.warn('Could not fetch order status history:', historyErr)
      //   // Continue without status history
      // }
      
      currentOrder.value = order
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch order'
      console.error('Error fetching order:', err)
    } finally {
      isLoading.value = false
    }
  }

  const createOrderWithCheckout = async (checkoutData: Omit<CheckoutOrderRequest, 'userId'>) => {
    if (!authStore.user?.id) {
      throw new Error('User not authenticated')
    }

    try {
      isLoading.value = true
      error.value = null

      const orderData: CheckoutOrderRequest = {
        userId: authStore.user.id,
        ...checkoutData
      }

      const order = await apiService.createOrderWithCheckout(orderData)
      
      // Add to orders list
      orders.value.unshift(order)
      currentOrder.value = order

      // Refresh cart to remove selected items
      await cartStore.fetchCartItems()

      success('Order created successfully!')
      return order
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to create order'
      showError(error.value || 'Failed to create order')
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const calculateShippingCost = (subtotal: number): number => {
    // Free shipping over 10M IDR
    if (subtotal >= 10000000) {
      return 0
    }
    return 50000 // 50K IDR shipping cost
  }

  const calculateTaxAmount = (subtotal: number): number => {
    // 11% tax (PPN in Indonesia)
    return Math.round(subtotal * 0.11)
  }

  const calculateOrderTotal = (subtotal: number): number => {
    const shipping = calculateShippingCost(subtotal)
    const tax = calculateTaxAmount(subtotal)
    return subtotal + shipping + tax
  }

  const getOrderSummary = (selectedAmount: number) => {
    const subtotal = selectedAmount
    const shippingCost = calculateShippingCost(subtotal)
    const taxAmount = calculateTaxAmount(subtotal)
    const totalAmount = subtotal + shippingCost + taxAmount

    return {
      subtotal,
      shippingCost,
      taxAmount,
      totalAmount,
      freeShipping: shippingCost === 0
    }
  }

  const clearError = () => {
    error.value = null
  }

  const clearCurrentOrder = () => {
    currentOrder.value = null
  }

  const reset = () => {
    orders.value = []
    currentOrder.value = null
    isLoading.value = false
    error.value = null
  }

  return {
    // State
    orders,
    currentOrder,
    isLoading,
    error,

    // Getters
    userOrders,
    hasOrders,
    pendingOrders,
    completedOrders,

    // Actions
    fetchUserOrders,
    fetchOrder,
    createOrderWithCheckout,
    calculateShippingCost,
    calculateTaxAmount,
    calculateOrderTotal,
    getOrderSummary,
    clearError,
    clearCurrentOrder,
    reset
  }
})
