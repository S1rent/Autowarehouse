<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow-sm border-b">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <div class="flex items-center space-x-2">
            <i class="fa-solid fa-code text-blue-600 text-xl"></i>
            <span class="text-xl font-bold text-gray-900">Autowarehouse UI Components</span>
          </div>
          <div class="text-sm text-gray-500">
            Built with Vue 3 + TypeScript + Tailwind CSS
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-12 text-center">
        <h1 class="text-4xl font-bold text-gray-900 mb-4">UI Component Library</h1>
        <p class="text-xl text-gray-600 mb-6">
          Comprehensive collection of UI components for Autowarehouse e-commerce platform
        </p>
        <div class="inline-flex items-center space-x-4 bg-blue-50 px-6 py-3 rounded-lg">
          <i class="fa-solid fa-info-circle text-blue-600"></i>
          <span class="text-blue-800 font-medium">Click on any component to view it in action</span>
        </div>
      </div>

      <!-- Statistics -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-12">
        <div class="bg-white rounded-xl shadow-sm border p-6 text-center">
          <div class="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center mx-auto mb-3">
            <i class="fa-solid fa-desktop text-blue-600 text-xl"></i>
          </div>
          <div class="text-2xl font-bold text-gray-900 mb-1">{{ stats.totalPages }}</div>
          <div class="text-sm text-gray-600">Total Pages</div>
        </div>
        <div class="bg-white rounded-xl shadow-sm border p-6 text-center">
          <div class="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center mx-auto mb-3">
            <i class="fa-solid fa-puzzle-piece text-green-600 text-xl"></i>
          </div>
          <div class="text-2xl font-bold text-gray-900 mb-1">{{ stats.totalComponents }}</div>
          <div class="text-sm text-gray-600">Components</div>
        </div>
        <div class="bg-white rounded-xl shadow-sm border p-6 text-center">
          <div class="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center mx-auto mb-3">
            <i class="fa-solid fa-mobile-screen text-purple-600 text-xl"></i>
          </div>
          <div class="text-2xl font-bold text-gray-900 mb-1">100%</div>
          <div class="text-sm text-gray-600">Responsive</div>
        </div>
        <div class="bg-white rounded-xl shadow-sm border p-6 text-center">
          <div class="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center mx-auto mb-3">
            <i class="fa-solid fa-shield-check text-yellow-600 text-xl"></i>
          </div>
          <div class="text-2xl font-bold text-gray-900 mb-1">A11Y</div>
          <div class="text-sm text-gray-600">Accessible</div>
        </div>
      </div>

      <!-- Filter Tabs -->
      <div class="mb-8">
        <div class="border-b border-gray-200">
          <nav class="-mb-px flex space-x-8">
            <button
              v-for="category in categories"
              :key="category.id"
              @click="activeCategory = category.id"
              :class="[
                'py-2 px-1 border-b-2 font-medium text-sm transition-colors',
                activeCategory === category.id
                  ? 'border-blue-500 text-blue-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              ]"
            >
              <i :class="category.icon" class="mr-2"></i>
              {{ category.name }}
              <span class="ml-2 bg-gray-100 text-gray-600 py-1 px-2 rounded-full text-xs">
                {{ category.count }}
              </span>
            </button>
          </nav>
        </div>
      </div>

      <!-- UI Components Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
          v-for="component in filteredComponents"
          :key="component.id"
          class="bg-white rounded-xl shadow-sm border hover:shadow-lg transition-all duration-300 overflow-hidden group cursor-pointer"
          @click="navigateToComponent(component.route)"
        >
          <!-- Preview Image/Icon -->
          <div class="h-48 bg-gradient-to-br from-gray-100 to-gray-200 flex items-center justify-center relative overflow-hidden">
            <div class="absolute inset-0 bg-gradient-to-br" :style="{ background: component.gradient }"></div>
            <i :class="component.icon" class="text-4xl text-white relative z-10"></i>
            <div class="absolute top-3 right-3 opacity-0 group-hover:opacity-100 transition-opacity">
              <div class="bg-white/20 backdrop-blur-sm rounded-lg px-3 py-1">
                <i class="fa-solid fa-external-link text-white text-sm"></i>
              </div>
            </div>
          </div>

          <!-- Component Info -->
          <div class="p-6">
            <div class="flex items-center justify-between mb-3">
              <h3 class="text-lg font-semibold text-gray-900">{{ component.name }}</h3>
              <span :class="[
                'px-2 py-1 rounded-full text-xs font-medium',
                component.status === 'completed' ? 'bg-green-100 text-green-800' :
                component.status === 'in-progress' ? 'bg-yellow-100 text-yellow-800' :
                'bg-gray-100 text-gray-800'
              ]">
                {{ component.status }}
              </span>
            </div>
            
            <p class="text-gray-600 text-sm mb-4 line-clamp-2">{{ component.description }}</p>
            
            <!-- Features -->
            <div class="flex flex-wrap gap-2 mb-4">
              <span
                v-for="feature in component.features"
                :key="feature"
                class="bg-blue-50 text-blue-700 px-2 py-1 rounded text-xs font-medium"
              >
                {{ feature }}
              </span>
            </div>

            <!-- Action Button -->
            <div class="flex items-center justify-between">
              <div class="text-xs text-gray-500">
                <i class="fa-solid fa-clock mr-1"></i>
                {{ component.lastUpdated }}
              </div>
              <button class="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700 transition-colors group-hover:scale-105">
                View Component
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Empty State -->
      <div v-if="filteredComponents.length === 0" class="text-center py-16">
        <i class="fa-solid fa-search text-4xl text-gray-300 mb-4"></i>
        <h3 class="text-lg font-semibold text-gray-900 mb-2">No components found</h3>
        <p class="text-gray-600">Try selecting a different category</p>
      </div>
    </main>

    <!-- Footer -->
    <footer class="bg-white border-t mt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="text-center">
          <p class="text-gray-600 text-sm">
            Built with ❤️ using Vue 3, TypeScript, and Tailwind CSS
          </p>
          <div class="mt-4 flex justify-center space-x-6">
            <span class="text-gray-400 hover:text-gray-600 cursor-pointer">
              <i class="fa-brands fa-github text-xl"></i>
            </span>
            <span class="text-gray-400 hover:text-gray-600 cursor-pointer">
              <i class="fa-brands fa-figma text-xl"></i>
            </span>
            <span class="text-gray-400 hover:text-gray-600 cursor-pointer">
              <i class="fa-solid fa-book text-xl"></i>
            </span>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// Active category filter
const activeCategory = ref('all')

// Statistics
const stats = reactive({
  totalPages: 12,
  totalComponents: 25,
  responsive: '100%',
  accessible: 'A11Y'
})

// Categories
const categories = reactive([
  { id: 'all', name: 'All Components', icon: 'fa-solid fa-grid-2', count: 8 },
  { id: 'auth', name: 'Authentication', icon: 'fa-solid fa-lock', count: 3 },
  { id: 'ecommerce', name: 'E-commerce', icon: 'fa-solid fa-shopping-cart', count: 3 },
  { id: 'admin', name: 'Admin Panel', icon: 'fa-solid fa-shield-halved', count: 2 }
])

// UI Components
const components = reactive([
  {
    id: 1,
    name: 'Home Page',
    description: 'Modern e-commerce homepage with hero section, categories, flash sales, and responsive design',
    route: '/',
    category: 'ecommerce',
    status: 'completed',
    icon: 'fa-solid fa-home',
    gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
    features: ['Hero Banner', 'Categories', 'Flash Sale', 'Responsive'],
    lastUpdated: '2 hours ago'
  },
  {
    id: 2,
    name: 'Login Page',
    description: 'Secure login form with user type selection, validation, and Google OAuth integration',
    route: '/login',
    category: 'auth',
    status: 'completed',
    icon: 'fa-solid fa-sign-in-alt',
    gradient: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
    features: ['User Types', 'Validation', 'OAuth', 'Security'],
    lastUpdated: '1 hour ago'
  },
  {
    id: 3,
    name: 'Product Listing',
    description: 'Advanced product catalog with filtering, sorting, search, and pagination features',
    route: '/products',
    category: 'ecommerce',
    status: 'completed',
    icon: 'fa-solid fa-th-large',
    gradient: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
    features: ['Filters', 'Search', 'Sorting', 'Pagination'],
    lastUpdated: '30 minutes ago'
  },
  {
    id: 4,
    name: 'Shopping Cart',
    description: 'Interactive shopping cart with quantity controls, coupon system, and order summary',
    route: '/cart',
    category: 'ecommerce',
    status: 'completed',
    icon: 'fa-solid fa-shopping-cart',
    gradient: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)',
    features: ['Quantity Control', 'Coupons', 'Summary', 'Checkout'],
    lastUpdated: '15 minutes ago'
  },
  {
    id: 5,
    name: 'Registration',
    description: 'User registration form with validation, password strength, and terms agreement',
    route: '/register',
    category: 'auth',
    status: 'in-progress',
    icon: 'fa-solid fa-user-plus',
    gradient: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
    features: ['Validation', 'Password Strength', 'Terms', 'Multi-step'],
    lastUpdated: 'Coming soon'
  },
  {
    id: 6,
    name: 'Forgot Password',
    description: 'Password reset flow with email verification and secure token validation',
    route: '/forgot-password',
    category: 'auth',
    status: 'in-progress',
    icon: 'fa-solid fa-key',
    gradient: 'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)',
    features: ['Email Reset', 'Token Validation', 'Security', 'Flow'],
    lastUpdated: 'Coming soon'
  },
  {
    id: 7,
    name: 'Admin Dashboard',
    description: 'Comprehensive admin panel with analytics, charts, and management tools',
    route: '/admin/dashboard',
    category: 'admin',
    status: 'planned',
    icon: 'fa-solid fa-chart-line',
    gradient: 'linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%)',
    features: ['Analytics', 'Charts', 'Management', 'Reports'],
    lastUpdated: 'Planned'
  },
  {
    id: 8,
    name: 'User Profile',
    description: 'User profile management with settings, order history, and preferences',
    route: '/profile',
    category: 'ecommerce',
    status: 'planned',
    icon: 'fa-solid fa-user-circle',
    gradient: 'linear-gradient(135deg, #a1c4fd 0%, #c2e9fb 100%)',
    features: ['Settings', 'History', 'Preferences', 'Security'],
    lastUpdated: 'Planned'
  }
])

// Computed filtered components
const filteredComponents = computed(() => {
  if (activeCategory.value === 'all') {
    return components
  }
  return components.filter(component => component.category === activeCategory.value)
})

// Methods
const navigateToComponent = (route: string) => {
  router.push(route)
}
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* Smooth transitions */
.transition-all {
  transition: all 0.3s ease;
}

/* Hover effects */
.group:hover .group-hover\:scale-105 {
  transform: scale(1.05);
}

.group:hover .group-hover\:opacity-100 {
  opacity: 1;
}
</style>
