<template>
  <nav class="bg-white shadow-lg border-b border-gray-200 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between items-center h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/" class="flex items-center space-x-3">
            <div class="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center">
              <i class="fa-solid fa-store text-white text-lg"></i>
            </div>
            <div>
              <h1 class="text-xl font-bold text-gray-800">AutoWarehouse</h1>
              <p class="text-xs text-gray-500">Your Auto Parts Store</p>
            </div>
          </router-link>
        </div>

        <!-- Desktop Navigation -->
        <div class="hidden md:flex items-center space-x-8">
          <router-link 
            to="/" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Home
          </router-link>
          <router-link 
            to="/products" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Products
          </router-link>
          <!-- <router-link 
            to="/auctions" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Live Auctions
          </router-link> -->
          <router-link 
            to="/customer-service" 
            class="text-gray-700 hover:text-blue-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Support
          </router-link>
        </div>

        <!-- Auth Buttons -->
        <div class="hidden md:flex items-center space-x-4">
          <router-link 
            to="/login" 
            class="text-gray-700 hover:text-blue-600 px-4 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Login
          </router-link>
          <router-link 
            to="/register" 
            class="bg-blue-600 text-white hover:bg-blue-700 px-4 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Register
          </router-link>
        </div>

        <!-- Mobile menu button -->
        <div class="md:hidden">
          <button 
            @click="mobileMenuOpen = !mobileMenuOpen"
            class="text-gray-700 hover:text-blue-600 p-2"
          >
            <i class="fa-solid fa-bars text-lg"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- Mobile Navigation -->
    <div v-if="mobileMenuOpen" class="md:hidden bg-white border-t border-gray-200">
      <div class="px-2 pt-2 pb-3 space-y-1">
        <router-link 
          to="/" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Home
        </router-link>
        <router-link 
          to="/products" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Products
        </router-link>
        <router-link 
          to="/auctions" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Live Auctions
        </router-link>
        <router-link 
          to="/customer-service" 
          class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
          @click="mobileMenuOpen = false"
        >
          Support
        </router-link>
        <div class="border-t border-gray-200 pt-4 pb-3">
          <router-link 
            to="/login" 
            class="block px-3 py-2 text-gray-700 hover:text-blue-600 hover:bg-gray-50 rounded-md text-base font-medium"
            @click="mobileMenuOpen = false"
          >
            Login
          </router-link>
          <router-link 
            to="/register" 
            class="block px-3 py-2 bg-blue-600 text-white hover:bg-blue-700 rounded-md text-base font-medium mt-2"
            @click="mobileMenuOpen = false"
          >
            Register
          </router-link>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const mobileMenuOpen = ref(false)
</script>
