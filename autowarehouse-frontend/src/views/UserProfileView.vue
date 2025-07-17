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
              <div class="w-24 h-24 rounded-full mx-auto mb-4 border-4 border-blue-600 bg-gray-100 flex items-center justify-center">
                <i class="fa-solid fa-user text-3xl text-gray-500"></i>
              </div>
              <h3 class="text-lg font-semibold text-gray-900 mb-1">{{ user.name }}</h3>
              <p class="text-sm text-gray-500 mb-4">Member</p>
              <!-- <button 
                @click="changePhoto"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-blue-700 transition-colors"
              >
                <i class="fa-solid fa-camera mr-2"></i>
                Change Photo
              </button> -->
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
                  :disabled="isUpdatingProfile"
                  :class="isEditingPersonal ? 'bg-green-600 hover:bg-green-700' : 'bg-blue-600 hover:bg-blue-700'"
                  class="text-white px-4 py-2 rounded-lg text-sm font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <i 
                    :class="isUpdatingProfile ? 'fa-solid fa-spinner fa-spin' : (isEditingPersonal ? 'fa-solid fa-save' : 'fa-solid fa-edit')" 
                    class="mr-2"
                  ></i>
                  {{ isUpdatingProfile ? 'Saving...' : (isEditingPersonal ? 'Save' : 'Edit') }}
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
                <!-- <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">Date of Birth</label>
                  <input 
                    type="date" 
                    v-model="user.birthDate"
                    :readonly="!isEditingPersonal"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent"
                    :class="{ 'bg-gray-50': !isEditingPersonal }"
                  >
                </div> -->
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
                  :disabled="isChangingPassword"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <i 
                    :class="isChangingPassword ? 'fa-solid fa-spinner fa-spin' : 'fa-solid fa-lock'" 
                    class="mr-2"
                  ></i>
                  {{ isChangingPassword ? 'Updating...' : 'Update Password' }}
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
                <div class="space-y-4">
                  <div 
                    v-for="item in wishlistStore.wishlistItems" 
                    :key="item.id"
                    class="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow cursor-pointer"
                    @click="viewProduct(item.product.id)"
                  >
                    <div class="flex space-x-6">
                      <!-- Product Image -->
                      <div class="flex-shrink-0">
                        <img 
                          :src="item.product.imageUrls?.[0] || '/placeholder-product.jpg'" 
                          :alt="item.product.name"
                          class="w-24 h-24 object-cover rounded-lg border border-gray-200"
                        >
                      </div>
                      
                      <!-- Product Info -->
                      <div class="flex-1 min-w-0">
                        <div class="flex items-start justify-between">
                          <div class="flex-1">
                            <h4 class="text-lg font-semibold text-gray-900 mb-1">{{ item.product.name }}</h4>
                            <p class="text-sm text-gray-600 mb-2">{{ item.product.brand }}</p>
                            
                            <!-- Price -->
                            <div class="flex items-center space-x-2 mb-3">
                              <span class="text-xl font-bold text-blue-600">Rp {{ formatPrice(item.product.price) }}</span>
                              <span 
                                v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                                class="text-sm text-gray-500 line-through"
                              >
                                Rp {{ formatPrice(item.product.originalPrice) }}
                              </span>
                              <span 
                                v-if="item.product.originalPrice && item.product.originalPrice > item.product.price"
                                class="text-xs bg-red-100 text-red-800 px-2 py-1 rounded-full"
                              >
                                {{ Math.round(((item.product.originalPrice - item.product.price) / item.product.originalPrice) * 100) }}% OFF
                              </span>
                            </div>
                            
                            <!-- Stock Status -->
                            <div class="mb-4">
                              <span 
                                :class="getStockClass(item.product.stockQuantity)"
                                class="text-xs px-3 py-1 rounded-full font-medium"
                              >
                                {{ getStockText(item.product.stockQuantity) }}
                              </span>
                            </div>
                          </div>
                          
                          <!-- Remove Button -->
                          <button 
                            @click.stop="removeFromWishlist(item.product.id)"
                            class="text-gray-400 hover:text-red-600 transition-colors p-2"
                            title="Remove from wishlist"
                          >
                            <i class="fa-solid fa-heart-broken text-lg"></i>
                          </button>
                        </div>
                        
                        <!-- Action Buttons -->
                        <div class="flex space-x-3">
                          <button 
                            @click.stop="addToCart(item.product.id)"
                            :disabled="item.product.stockQuantity === 0"
                            class="flex-1 bg-blue-600 text-white py-2 px-4 rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                          >
                            <i class="fa-solid fa-shopping-cart mr-2"></i>
                            Add to Cart
                          </button>
                          <button 
                            @click.stop="viewProduct(item.product.id)"
                            class="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-50 transition-colors"
                          >
                            <i class="fa-solid fa-eye mr-2"></i>
                            View Details
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
              
              <!-- Loading State -->
              <div v-if="orderStore.isLoading" class="p-8 text-center">
                <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-600 mb-4"></i>
                <p class="text-gray-600">Loading orders...</p>
              </div>
              
              <!-- Empty State -->
              <div v-else-if="orderStore.orders.length === 0" class="p-8 text-center">
                <i class="fa-solid fa-shopping-bag text-4xl text-gray-400 mb-4"></i>
                <h4 class="text-lg font-medium text-gray-900 mb-2">No orders yet</h4>
                <p class="text-gray-600 mb-4">Start shopping to see your order history here</p>
                <button 
                  @click="$router.push('/products')"
                  class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors"
                >
                  <i class="fa-solid fa-shopping-bag mr-2"></i>
                  Browse Products
                </button>
              </div>
              
              <!-- Orders List -->
              <div v-else class="divide-y divide-gray-200">
                <div 
                  v-for="order in orderStore.orders" 
                  :key="order.id"
                  class="p-6 hover:bg-gray-50 transition-colors cursor-pointer"
                  @click="viewOrderDetail(order.id)"
                >
                  <div class="flex items-center justify-between mb-4">
                    <div>
                      <h4 class="text-lg font-semibold text-gray-900">Order #{{ order.orderNumber }}</h4>
                      <p class="text-sm text-gray-600">{{ formatDate(order.createdAt) }}</p>
                    </div>
                    <div class="text-right">
                      <span 
                        :class="getOrderStatusClass(order.status)"
                        class="px-3 py-1 rounded-full text-xs font-medium"
                      >
                        {{ getOrderStatusText(order.status) }}
                      </span>
                      <p class="text-lg font-bold text-gray-900 mt-1">Rp {{ formatPrice(order.totalAmount) }}</p>
                    </div>
                  </div>
                  
                  <!-- Order Summary -->
                  <div class="bg-gray-50 rounded-lg p-4 mb-4">
                    <div class="grid grid-cols-2 gap-4 text-sm">
                      <div>
                        <span class="text-gray-600">Payment Status:</span>
                        <span 
                          :class="order.paymentStatus === 'PAID' ? 'text-green-600' : 'text-yellow-600'"
                          class="ml-2 font-medium"
                        >
                          {{ order.paymentStatus }}
                        </span>
                      </div>
                      <div>
                        <span class="text-gray-600">Subtotal:</span>
                        <span class="ml-2 font-medium">Rp {{ formatPrice(order.subtotal) }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">Shipping:</span>
                        <span class="ml-2 font-medium">Rp {{ formatPrice(order.shippingCost) }}</span>
                      </div>
                      <div>
                        <span class="text-gray-600">Total:</span>
                        <span class="ml-2 font-medium text-blue-600">Rp {{ formatPrice(order.totalAmount) }}</span>
                      </div>
                    </div>
                  </div>
                  
                  <!-- Order Actions -->
                  <div class="flex items-center justify-between pt-4 border-t border-gray-100">
                    <div class="flex space-x-2">
                      <button 
                        @click.stop="viewOrderDetail(order.id)"
                        class="text-blue-600 hover:text-blue-700 text-sm font-medium"
                      >
                        <i class="fa-solid fa-eye mr-1"></i>
                        View Details
                      </button>
                      <button 
                        v-if="order.status === 'DELIVERED'"
                        @click.stop="reorderItems(order.id)"
                        class="text-green-600 hover:text-green-700 text-sm font-medium"
                      >
                        <i class="fa-solid fa-redo mr-1"></i>
                        Reorder
                      </button>
                    </div>
                    <div class="text-xs text-gray-500">
                      Order placed on {{ formatDate(order.createdAt) }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <Footer/>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useWishlistStore } from '@/stores/wishlist'
import { useCartStore } from '@/stores/cart'
import { useAuthStore } from '@/stores/auth'
import { useOrderStore } from '@/stores/order'
import { useNotifications } from '@/composables/useNotifications'
import { apiService } from '@/services/api'
import UserNavbar from '../components/UserNavbar.vue'
import Footer from '../components/Footer.vue'

const router = useRouter()
const wishlistStore = useWishlistStore()
const cartStore = useCartStore()
const authStore = useAuthStore()
const orderStore = useOrderStore()
const { success, error, warning } = useNotifications()

// State
const isEditingPersonal = ref(false)
const isUpdatingProfile = ref(false)
const isChangingPassword = ref(false)
const activeTab = ref('profile')

// User data - will be populated from auth store
const user = reactive({
  name: '',
  email: '',
  phone: '',
  birthDate: '',
  address: '',
  avatar: '',
  membershipType: 'Member'
})

// Password form
const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// Methods
const toggleEditPersonal = async () => {
  if (isEditingPersonal.value) {
    // Save profile changes
    try {
      isUpdatingProfile.value = true
      
      if (!authStore.user?.id) {
        error('Authentication Error', 'User not found. Please login again.')
        return
      }

      // Parse name into firstName and lastName
      const nameParts = user.name.trim().split(' ')
      const firstName = nameParts[0] || ''
      const lastName = nameParts.slice(1).join(' ') || ''

      const profileData = {
        firstName,
        lastName,
        phoneNumber: user.phone || '',
        address: user.address || ''
      }

      const updatedUser = await apiService.updateUserProfile(authStore.user.id, profileData)
      
      // Update auth store with new data
      authStore.user = {
        ...authStore.user!,
        firstName: updatedUser.firstName,
        lastName: updatedUser.lastName,
        phoneNumber: updatedUser.phoneNumber
      }

      // Reload user data
      loadUserData()
      
      success('Profile Updated', 'Personal information updated successfully!')
      isEditingPersonal.value = false
    } catch (err: any) {
      console.error('Error updating profile:', err)
      error('Update Failed', err.response?.data?.message || 'Failed to update profile. Please try again.')
    } finally {
      isUpdatingProfile.value = false
    }
  } else {
    isEditingPersonal.value = true
  }
}

const changePhoto = () => {
  // Implement photo change logic
  console.log('Change photo clicked')
  warning('Feature Coming Soon', 'Photo change functionality will be available soon')
}

const downloadData = () => {
  console.log('Download data clicked')
  warning('Feature Coming Soon', 'Data download functionality will be available soon')
}

const openSecurity = () => {
  console.log('Security clicked')
  warning('Feature Coming Soon', 'Security settings will be available soon')
}

const openNotifications = () => {
  console.log('Notifications clicked')
  warning('Feature Coming Soon', 'Notification settings will be available soon')
}

const updatePassword = async () => {
  if (!passwordForm.currentPassword || !passwordForm.newPassword || !passwordForm.confirmPassword) {
    warning('Validation Error', 'Please fill in all password fields')
    return
  }
  
  if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    error('Validation Error', 'New passwords do not match')
    return
  }

  if (passwordForm.newPassword.length < 6) {
    warning('Validation Error', 'New password must be at least 6 characters long')
    return
  }
  
  try {
    isChangingPassword.value = true
    
    await apiService.changePassword(passwordForm.currentPassword, passwordForm.newPassword)
    
    success('Password Updated', 'Password updated successfully!')
    
    // Clear form
    passwordForm.currentPassword = ''
    passwordForm.newPassword = ''
    passwordForm.confirmPassword = ''
  } catch (err: any) {
    console.error('Error changing password:', err)
    error('Password Change Failed', err.response?.data?.message || 'Failed to change password. Please try again.')
  } finally {
    isChangingPassword.value = false
  }
}

const logout = () => {
  if (confirm('Are you sure you want to logout?')) {
    console.log('Logging out')
    success('Logout Successful', 'You have been logged out successfully!')
    router.push('/login')
  }
}

const deactivateAccount = () => {
  if (confirm('Are you sure you want to deactivate your account? This action can be reversed later.')) {
    console.log('Deactivating account')
    warning('Feature Coming Soon', 'Account deactivation functionality will be available soon')
  }
}

// Wishlist methods
const clearWishlist = async () => {
  if (confirm('Are you sure you want to clear your entire wishlist?')) {
    try {
      await wishlistStore.clearWishlist()
      success('Wishlist Cleared', 'Your wishlist has been cleared successfully!')
    } catch (err) {
      console.error('Error clearing wishlist:', err)
      error('Clear Failed', 'Failed to clear wishlist. Please try again.')
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
    success('Added to Cart', 'Product has been added to your cart!')
  } catch (err) {
    console.error('Error adding to cart:', err)
    error('Add to Cart Failed', 'Failed to add product to cart. Please try again.')
  }
}

const removeFromWishlist = async (productId: number) => {
  try {
    await wishlistStore.removeFromWishlist(productId)
  } catch (err) {
    console.error('Error removing from wishlist:', err)
    error('Remove Failed', 'Failed to remove product from wishlist. Please try again.')
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

// Order methods
const viewOrderDetail = (orderId: number) => {
  router.push(`/order/${orderId}`)
}

const reorderItems = async (orderId: number) => {
  try {
    // Implementation for reordering items
    console.log('Reordering items from order:', orderId)
    warning('Feature Coming Soon', 'Reorder functionality will be available soon')
  } catch (err) {
    console.error('Error reordering items:', err)
    error('Reorder Failed', 'Failed to reorder items. Please try again.')
  }
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('id-ID', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const getOrderStatusClass = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'bg-yellow-100 text-yellow-800'
    case 'CONFIRMED':
      return 'bg-blue-100 text-blue-800'
    case 'SHIPPED':
      return 'bg-purple-100 text-purple-800'
    case 'DELIVERED':
      return 'bg-green-100 text-green-800'
    case 'CANCELLED':
      return 'bg-red-100 text-red-800'
    default:
      return 'bg-gray-100 text-gray-800'
  }
}

const getOrderStatusText = (status: string) => {
  switch (status) {
    case 'PENDING':
      return 'Pending'
    case 'CONFIRMED':
      return 'Confirmed'
    case 'SHIPPED':
      return 'Shipped'
    case 'DELIVERED':
      return 'Delivered'
    case 'CANCELLED':
      return 'Cancelled'
    default:
      return status
  }
}

// Load user data from auth store
const loadUserData = async () => {
  if (authStore.user) {
    user.name = `${authStore.user.firstName} ${authStore.user.lastName}`.trim() || ''
    user.email = authStore.user.email || ''
    user.phone = authStore.user.phoneNumber || ''
    
    // Load full user profile to get address and ensure phone number is loaded
    try {
      const fullProfile = await apiService.getUserProfile(authStore.user.id)
      user.address = fullProfile.address || ''
      // Make sure to use the phoneNumber from the API response
      user.phone = fullProfile.phoneNumber || authStore.user.phoneNumber || ''
      
      console.log('Loaded user profile:', fullProfile)
      console.log('Phone number from API:', fullProfile.phoneNumber)
      console.log('Phone number set to user.phone:', user.phone)
    } catch (err) {
      console.error('Error loading user profile:', err)
      user.address = ''
      // Fallback to auth store phone number if API call fails
      user.phone = authStore.user.phoneNumber || ''
    }
  }
}

// Initialize on mount
onMounted(async () => {
  // Load user data from auth store
  loadUserData()
  
  // Load wishlist and orders
  await Promise.all([
    wishlistStore.loadWishlist(),
    orderStore.fetchUserOrders()
  ])
})
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
