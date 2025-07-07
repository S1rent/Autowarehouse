<template>
  <div class="bg-gray-50 font-inter min-h-screen">
    <!-- Header -->
    <header class="bg-white shadow-sm border-b border-gray-200">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-8">
            <div class="flex items-center space-x-2">
              <i class="fa-solid fa-store text-blue-600 text-xl"></i>
              <span class="text-xl font-bold text-gray-900">Autowarehouse</span>
            </div>
          </div>
          <div class="flex items-center space-x-4">
            <button class="text-gray-500 hover:text-gray-700">
              <i class="fa-solid fa-bell text-lg"></i>
            </button>
            <div class="flex items-center space-x-2">
              <img :src="user.avatar" alt="Profile" class="w-8 h-8 rounded-full">
              <span class="text-sm font-medium text-gray-700">{{ user.name }}</span>
            </div>
          </div>
        </div>
      </div>
    </header>

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
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mt-6">
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
          </div>
        </div>

        <!-- Profile Content -->
        <div class="lg:col-span-2 space-y-6">
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
                <div class="flex items-center justify-between p-4 bg-yellow-50 rounded-lg border border-yellow-200">
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
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// State
const isEditingPersonal = ref(false)

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
</script>

<style scoped>
.font-inter {
  font-family: 'Inter', sans-serif;
}
</style>
