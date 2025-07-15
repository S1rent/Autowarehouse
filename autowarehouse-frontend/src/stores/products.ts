import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { apiService, type Product, type ProductFilters } from '@/services/api'

export const useProductsStore = defineStore('products', () => {
  // State
  const products = ref<Product[]>([])
  const featuredProducts = ref<Product[]>([])
  const popularProducts = ref<Product[]>([])
  const onSaleProducts = ref<Product[]>([])
  const currentProduct = ref<Product | null>(null)
  const isLoading = ref(false)
  const error = ref<string | null>(null)
  const totalProducts = ref(0)
  const currentPage = ref(1)
  const pageSize = ref(12)

  // Getters
  const hasProducts = computed(() => products.value.length > 0)
  const totalPages = computed(() => Math.ceil(totalProducts.value / pageSize.value))

  // Actions
  const fetchProducts = async (filters?: ProductFilters) => {
    try {
      isLoading.value = true
      error.value = null
      
      const productList = await apiService.getProducts(filters)
      products.value = productList
      totalProducts.value = productList.length // In real API, this would come from response headers
      
      return productList
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch products'
      console.error('Error fetching products:', err)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const fetchProduct = async (productId: number) => {
    try {
      isLoading.value = true
      error.value = null
      
      const product = await apiService.getProduct(productId)
      currentProduct.value = product
      
      return product
    } catch (err: any) {
      error.value = err.response?.data?.message || 'Failed to fetch product'
      console.error('Error fetching product:', err)
      throw err
    } finally {
      isLoading.value = false
    }
  }

  const fetchFeaturedProducts = async (limit: number = 10) => {
    try {
      const products = await apiService.getFeaturedProducts(limit)
      featuredProducts.value = products
      return products
    } catch (err: any) {
      console.error('Error fetching featured products:', err)
      throw err
    }
  }

  const fetchPopularProducts = async (limit: number = 10) => {
    try {
      const products = await apiService.getPopularProducts(limit)
      popularProducts.value = products
      return products
    } catch (err: any) {
      console.error('Error fetching popular products:', err)
      throw err
    }
  }

  const fetchOnSaleProducts = async (limit: number = 10) => {
    try {
      const products = await apiService.getOnSaleProducts(limit)
      onSaleProducts.value = products
      return products
    } catch (err: any) {
      console.error('Error fetching on sale products:', err)
      throw err
    }
  }

  const searchProducts = async (query: string, filters?: Omit<ProductFilters, 'search'>) => {
    const searchFilters: ProductFilters = {
      ...filters,
      search: query
    }
    return await fetchProducts(searchFilters)
  }

  const filterProducts = async (filters: ProductFilters) => {
    return await fetchProducts(filters)
  }

  const clearError = () => {
    error.value = null
  }

  const resetProducts = () => {
    products.value = []
    currentProduct.value = null
    error.value = null
    isLoading.value = false
    currentPage.value = 1
  }

  const setPage = (page: number) => {
    currentPage.value = page
  }

  const setPageSize = (size: number) => {
    pageSize.value = size
  }

  return {
    // State
    products,
    featuredProducts,
    popularProducts,
    onSaleProducts,
    currentProduct,
    isLoading,
    error,
    totalProducts,
    currentPage,
    pageSize,
    
    // Getters
    hasProducts,
    totalPages,
    
    // Actions
    fetchProducts,
    fetchProduct,
    fetchFeaturedProducts,
    fetchPopularProducts,
    fetchOnSaleProducts,
    searchProducts,
    filterProducts,
    clearError,
    resetProducts,
    setPage,
    setPageSize
  }
})
