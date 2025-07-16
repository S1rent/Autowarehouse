<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <UserNavbar/>

    <!-- Main Content -->
    <main class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-3 mb-2">
          <button @click="$router.go(-1)" class="text-gray-500 hover:text-gray-700">
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <h1 class="text-2xl font-bold text-gray-900">User Profile</h1>
        </div>
        <p class="text-gray-600">Manage your personal information and account settings</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Profile Sidebar -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
            <div class="text-center">
              <img 
                :src="user.avatar" 
                alt="Profile Picture" 
                class="w-24 h-24 rounded-full mx-auto mb-4 border-4 border-blue-600"
              >
              <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ user.name }}</h3>
              <p class="text-sm text-gray-500 mb-4">{{ user.membershipType }}</p>
              <button 
                @click="changePhoto"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-blue-700 transition-colors"
              >
                <i class="fa-solid fa-camera mr-2"></i>
                Change Photo
              </button>
            </div>
          </div>

          <!-- Quick Actions -->
          <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mt-6">
            <h4 class="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h4>
            <div class="space-y-3">
              <button 
                @click="downloadData"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-download text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Download Data</span>
              </button>
              <button 
                @click="openSecurity"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-shield-alt text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Security</span>
              </button>
              <button 
                @click="openNotifications"
                class="w-full flex items-center space-x-3 p-3 text-left rounded-lg hover:bg-gray-50 transition-colors"
              >
                <i class="fa-solid fa-bell text-blue-600"></i>
                <span class="text-sm font-medium text-gray-700">Notifications</span>
              </button>
            </div>
          </div> -->
        </div>

        <!-- Profile Content -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Navigation Tabs -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="border-b border-gray-200">
              <nav class="-mb-px flex space-x-8 px-6">
                <button
                  @click="activeTab = 'profile'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'profile' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-user mr-2"></i>
                  Profile
                </button>
                <button
                  @click="activeTab = 'wishlist'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'wishlist' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-heart mr-2"></i>
                  Wishlist ({{ wishlistStore.wishlistCount }})
                </button>
                <button
                  @click="activeTab = 'orders'"
                  class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
                  :class="activeTab === 'orders' 
                    ? 'border-blue-600 text-blue-600' 
                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Orders
                </button>
              </nav>
            </div>
          </div>

          <!-- Profile Tab Content -->
          <div v-if="activeTab === 'profile'" class="space-y-6">
            <!-- Personal Information Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <div class="flex items-center justify-between">
                <h3 class="text-lg font-semibold text-gray-900">Personal Information</h3>
                <button 
                  @click="toggleEditPersonal"
                  :class="isEditingPersonal ? 'bg-green-600 hover:bg-green-700' : 'bg-blue-600 hover:bg-blue-700'"
                  class="text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors"
                >
                  <i :class="isEditingPersonal ? 'fa-solid fa-save' : 'fa-solid fa-edit'" class="mr-2"></i>
                  {{ isEditingPersonal ? 'Save' : 'Edit' }}
                </button>
              </div>
            </div>
            <div class="p-6">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Full Name</label>
                  <input 
                    type="text" 
                    v-model="user.name"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                  <input 
                    type="email" 
                    v-model="user.email"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Phone Number</label>
                  <input 
                    type="tel" 
                    v-model="user.phone"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Date of Birth</label>
                  <input 
                    type="date" 
                    v-model="user.birthDate"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div>
                <div class="md:col-span-2">
                  <label class="block text-sm font-medium text-gray-700 mb-2">Address</label>
                  <textarea 
                    rows="3" 
                    v-model="user.address"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>

          <!-- Password Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Change Password</h3>
              <p class="text-sm text-gray-600 mt-1">Make sure your new password is secure and memorable</p>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Current Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.currentPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Enter current password"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">New Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.newPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Enter new password"
                  >
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Confirm New Password</label>
                  <input 
                    type="password" 
                    v-model="passwordForm.confirmPassword"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent" 
                    placeholder="Confirm new password"
                  >
                </div>
              </div>
              <div class="mt-6">
                <button 
                  @click="updatePassword"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-lock mr-2"></i>
                  Update Password
                </button>
              </div>
            </div>
          </div>

          <!-- Account Actions -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200">
            <div class="p-6 border-b border-gray-200">
              <h3 class="text-lg font-semibold text-gray-900">Account Settings</h3>
            </div>
            <div class="p-6">
              <div class="space-y-4">
                <div class="flex items-center justify-between p-4 bg-red-50 rounded-lg border border-red-200">
                  <div>
                    <h4 class="text-sm font-medium text-red-900">Logout from Account</h4>
                    <p class="text-sm text-red-700 mt-1">Sign out from current session</p>
                  </div>
                  <button 
                    @click="logout"
                    class="bg-red-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-red-700 transition-colors"
                  >
                    <i class="fa-solid fa-sign-out-alt mr-2"></i>
                    Logout
                  </button>
                </div>
                <!-- <div class="flex items-center justify-between p-4 bg-yellow-50 rounded-lg border border-yellow-200">
                  <div>
                    <h4 class="text-sm font-medium text-yellow-900">Deactivate Account</h4>
                    <p class="text-sm text-yellow-700 mt-1">Temporarily disable your account</p>
                  </div>
                  <button 
                    @click="deactivateAccount"
                    class="bg-yellow-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-yellow-700 transition-colors"
                  >
                    <i class="fa-solid fa-pause mr-2"></i>
                    Deactivate
                  </button>
                </div> -->
              </div>
            </div>
            </div>
          </div>

          <!-- Wishlist Tab Content -->
          <div v-if="activeTab === 'wishlist'" class="space-y-6">
            <div class="bg-white rounded-lg shadow-sm border border-gray-200">
              <div class="p-6 border-b border-gray-200">
                <div class="flex items-center justify-between">
                  <h3 class="text-lg font-semibold text-gray-900">My Wishlist</h3>
                  <button 
                    v-if="wishlistStore.wishlistCount > 0"
                    @click="clearWishlist"
                    class="text-red-600 hover:text-red-700 text-sm font-medium"
                  >
                    <i class="fa-solid fa-trash mr-1"></i>
                    Clear All
                  </button>
                </div>
              </div>
              
              <!-- Loading State -->
              <div v-if="wishlistStore.isLoading" class="p-8 text-center">
                <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-600 mb-4"></i>
                <p class="text-gray-600">Loading wishlist...</p>
              </div>
              
              <!-- Empty State -->
              <div v-else-if="wishlistStore.wishlistCount === 0" class="p-8 text-center">
                <i class="fa-regular fa-heart text-4xl text-gray-400 mb-4"></i>
                <h4 class="text-lg font-medium text-gray-900 mb-2">Your wishlist is empty</h4>
                <p class="text-gray-600 mb-4">Save items you love to your wishlist</p>
                <button 
                  @click="$router.push('/products')"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Browse Products
                </button>
              </div>
              
              <!-- Wishlist Items -->
              <div v-else class="p-6">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div 
                    v-for="item in wishlistStore.wishlistItems" 
                    :key="item.id"
                    class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
                  >
                    <div class="flex space-x-4">
                      <!-- Product Image -->
                      <div class="flex-shrink-0">
                        <img 
                          :src="item.product.imageUrls?.[0] || '/placeholder-product.jpg'" 
                          :alt="item.product.name"
                          class="w-20 h-20 object-cover rounded-lg"
                        >
                      </div>
                      
                      <!-- Product Info -->
                      <div class="flex-1 min-w-0">
                        <h4 class="text-sm font-medium text-gray-900 truncate">{{ item.product.name }}</h4>
                        <p class="text-sm text-gray-600 mt-1">{{ item.product.brand }}</p>
                        <div class="flex items-center mt-2">
                          <span class="text-lg font-bold text-blue-600">Rp {{ formatPrice(item.product.price) }}</span>
                          <span 
                            v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                            class="text-sm text-gray-500 line-through ml-2"
                          >
                            Rp {{ formatPrice(item.product.originalPrice) }}
                          </span>
                        </div>
                        
                        <!-- Stock Status -->
                        <div class="mt-2">
                          <span 
                            :class="getStockClass(item.product.stockQuantity)"
                            class="text-xs px-2 py-1 rounded-full"
                          >
                            {{ getStockText(item.product.stockQuantity) }}
                          </span>
                        </div>
                        
                        <!-- Action Buttons -->
                        <div class="flex space-x-2 mt-3">
                          <button 
                            @click="addToCart(item.product.id)"
                            :disabled="item.product.stockQuantity === 0"
                            class="flex-1 bg-blue-600 text-white py-1 px-3 rounded text-xs hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                          >
                            <i class="fa-solid fa-shopping-cart mr-1"></i>
                            Add to Cart
                          </button>
                          <button 
                            @click="viewProduct(item.product.id)"
                            class="px-3 py-1 border border-gray-300 text-gray-700 rounded text-xs hover:bg-gray-50 transition-colors"
                          >
                            <i class="fa-solid fa-eye"></i>
                          </button>
                          <button 
                            @click="removeFromWishlist(item.product.id)"
                            class="px-3 py-1 text-red-600 hover:bg-red-50 rounded text-xs transition-colors"
                          >
                            <i class="fa-solid fa-trash"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Orders Tab Content -->
          <div v-if="activeTab === 'orders'" class="space-y-6">
            <div class="bg-white rounded-lg shadow-sm border border-gray-200">
              <div class="p-6 border-b border-gray-200">
                <h3 class="text-lg font-semibold text-gray-900">Order History</h3>
              </div>
              <div class="p-8 text-center">
                <i class="fa-solid fa-shopping-bag text-4xl text-gray-400 mb-4"></i>
                <h4 class="text-lg font-medium text-gray-900 mb-2">Order history coming soon</h4>
                <p class="text-gray-600">We're working on implementing the order history feature</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useWishlistStore } from '@/stores/wishlist'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import UserNavbar from '../components/UserNavbar.vue'

const router = useRouter()
const wishlistStore = useWishlistStore()
const cartStore = useCartStore()
const authStore = useAuthStore()

// State
const isEditingPersonal = ref(false)
const activeTab = ref('profile')

// User data
const user = reactive({
  name: 'Sarah Johnson',
  email: 'sarah.johnson@email.com',
  phone: '+62 812-3456-7890',
  birthDate: '1990-05-15',
  address: 'Jl. Sudirman No. 123, RT 05/RW 02, Kebayoran Baru, Jakarta Selatan, DKI Jakarta 12190',
  avatar: 'https://images.unsplash.com/photo-1494790108755-2616b612b786?w=100&h=100&fit=crop&crop=face',
  membershipType: 'Premium Member'
})

// Password form
const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// Methods
const toggleEditPersonal = () => {
  if (isEditingPersonal.value) {
    // Save logic here
    console.log('Saving personal information:', user)
    alert('Personal information updated successfully!')
  }
  isEditingPersonal.value = !isEditingPersonal.value
}

const changePhoto = () => {
  // Implement photo change logic
  console.log('Change photo clicked')
  alert('Photo change functionality would be implemented here')
}

const downloadData = () => {
  console.log('Download data clicked')
  alert('Data download would be implemented here')
}

const openSecurity = () => {
  console.log('Security clicked')
  alert('Security settings would be implemented here')
}

const openNotifications = () => {
  console.log('Notifications clicked')
  alert('Notification settings would be implemented here')
}

const updatePassword = () => {
  if (!passwordForm.currentPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    alert('Please fill in all password fields')
    return
  }
  
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    alert('New passwords do not match')
    return
  }
  
  console.log('Updating password')
  alert('Password updated successfully!')
  
  // Clear form
  passwordForm.currentPassword = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
}

const logout = () => {
  if (confirm('Are you sure you want to logout?')) {
    console.log('Logging out')
    alert('Logout successful!')
    router.push('/login')
  }
}

const deactivateAccount = () => {
  if (confirm('Are you sure you want to deactivate your account? This action can be reversed later.')) {
    console.log('Deactivating account')
    alert('Account deactivation would be implemented here')
  }
}

// Wishlist methods
const clearWishlist = async () => {
  if (confirm('Are you sure you want to clear your entire wishlist?')) {
    try {
      await wishlistStore.clearWishlist()
      alert('Wishlist cleared successfully!')
    } catch (error) {
      console.error('Error clearing wishlist:', error)
      alert('Failed to clear wishlist. Please try again.')
    }
  }
}

const addToCart = async (productId: number) => {
  try {
    if (!authStore.isAuthenticated) {
      router.push('/login')
      return
    }
    
    await cartStore.addToCart(productId, 1)
    alert('Product added to cart!')
  } catch (error) {
    console.error('Error adding to cart:', error)
    alert('Failed to add product to cart. Please try again.')
  }
}

const removeFromWishlist = async (productId: number) => {
  try {
    await wishlistStore.removeFromWishlist(productId)
  } catch (error) {
    console.error('Error removing from wishlist:', error)
    alert('Failed to remove product from wishlist. Please try again.')
  }
}

const viewProduct = (productId: number) => {
  router.push(`/product/${productId}`)
}

// Utility methods
const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStockClass = (stock: number) => {
  if (stock > 10) return 'bg-green-100 text-green-800'
  if (stock > 5) return 'bg-yellow-100 text-yellow-800'
  return 'bg-red-100 text-red-800'
}

const getStockText = (stock: number) => {
  if (stock > 10) return `Stock: ${stock}`
  if (stock > 5) return `Low Stock: ${stock}`
  if (stock > 0) return `Only ${stock} left`
  return 'Out of Stock'
}

// Initialize on mount
onMounted(async () => {
  await wishlistStore.loadWishlist()
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
