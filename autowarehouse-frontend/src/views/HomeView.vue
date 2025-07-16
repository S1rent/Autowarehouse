<template>
  <div class="bg-light min-h-screen">
    <!-- Dynamic navbar based on authentication status -->
    <UserNavbar v-if="authStore.isAuthenticated" />
    <GuestNavbar v-else />

    <!-- Hero Banner -->
    <section class="bg-gradient-to-br from-primary via-secondary to-indigo-600 text-white h-[600px] flex items-center relative overflow-hidden">
      <div class="absolute inset-0 bg-black opacity-10"></div>
      <div class="container mx-auto px-4 relative z-10">
        <div class="grid md:grid-cols-2 gap-12 items-center">
          <div>
            <div class="inline-block bg-accent/20 text-accent px-4 py-2 rounded-full text-sm font-semibold mb-4">
              🔥 Flash Sale Hari Ini
            </div>
            <h1 class="text-6xl font-bold mb-6 leading-tight">Hardware & Elektronik Terlengkap</h1>
            <p class="text-xl mb-8 opacity-90 leading-relaxed">
              Dapatkan komponen PC, RAM, SSD, dan aksesoris gaming terbaik dengan harga terjangkau. Plus fitur auction untuk deals eksklusif!
            </p>
            <div class="flex flex-col sm:flex-row gap-4">
              <router-link 
                to="/products" 
                class="bg-accent text-white px-8 py-4 rounded-xl font-semibold hover:bg-yellow-600 transition-all transform hover:scale-105 shadow-lg inline-flex items-center justify-center"
              >
                <i class="fa-solid fa-shopping-bag mr-2"></i>Belanja Sekarang
              </router-link>
              <router-link 
                to="/auctions" 
                class="border-2 border-white text-white px-8 py-4 rounded-xl font-semibold hover:bg-white hover:text-primary transition-all inline-flex items-center justify-center"
              >
                <i class="fa-solid fa-gavel mr-2"></i>Cek Auction
              </router-link>
            </div>
          </div>
          <div class="relative">
            <div class="bg-white/10 backdrop-blur-sm rounded-2xl p-8">
              <img 
                class="w-full h-80 object-cover rounded-xl" 
                src="https://images.unsplash.com/photo-1593640408182-31c70c8268f5?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80" 
                alt="Gaming Setup"
              >
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Quick Stats -->
    <section class="py-12 bg-white border-b">
      <div class="container mx-auto px-4">
        <div class="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
          <div>
            <div class="text-3xl font-bold text-primary mb-2">50K+</div>
            <div class="text-gray-600">Produk Tersedia</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">24/7</div>
            <div class="text-gray-600">Customer Support</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">1 Hari</div>
            <div class="text-gray-600">Pengiriman Express</div>
          </div>
          <div>
            <div class="text-3xl font-bold text-primary mb-2">100%</div>
            <div class="text-gray-600">Garansi Resmi</div>
          </div>
        </div>
      </div>
    </section>

    <!-- Kategori Produk -->
    <section class="py-20 bg-gray-50">
      <div class="container mx-auto px-4">
        <div class="text-center mb-16">
          <h2 class="text-4xl font-bold text-dark mb-4">Kategori Produk</h2>
          <p class="text-xl text-gray-600">Temukan komponen dan aksesoris PC terbaik</p>
        </div>
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-6">
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-blue-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-microchip text-2xl text-primary group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">Processor</h3>
            <p class="text-sm text-gray-500 text-center">Intel & AMD</p>
          </div>
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-green-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-memory text-2xl text-green-600 group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">RAM</h3>
            <p class="text-sm text-gray-500 text-center">DDR4 & DDR5</p>
          </div>
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-purple-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-hard-drive text-2xl text-purple-600 group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">Storage</h3>
            <p class="text-sm text-gray-500 text-center">SSD & HDD</p>
          </div>
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-red-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-display text-2xl text-red-600 group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">VGA Card</h3>
            <p class="text-sm text-gray-500 text-center">NVIDIA & AMD</p>
          </div>
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-yellow-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-keyboard text-2xl text-yellow-600 group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">Gaming</h3>
            <p class="text-sm text-gray-500 text-center">Keyboard & Mouse</p>
          </div>
          <div class="category-card bg-white p-8 rounded-2xl shadow-md hover:shadow-xl hover:scale-105 transition-all cursor-pointer group">
            <div class="bg-indigo-100 w-16 h-16 rounded-xl flex items-center justify-center mx-auto mb-4 group-hover:bg-primary transition-colors">
              <i class="fa-solid fa-headset text-2xl text-indigo-600 group-hover:text-white"></i>
            </div>
            <h3 class="font-bold text-dark text-center mb-2">Audio</h3>
            <p class="text-sm text-gray-500 text-center">Headset & Speaker</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Flash Sale -->
    <section class="py-16 bg-gradient-to-r from-red-500 to-pink-500 text-white">
      <div class="container mx-auto px-4">
        <div class="text-center mb-12">
          <h2 class="text-4xl font-bold mb-4">⚡ Flash Sale</h2>
          <p class="text-xl opacity-90">Penawaran terbatas, buruan sebelum kehabisan!</p>
          <div class="inline-flex items-center space-x-4 mt-4 bg-white/20 rounded-xl px-6 py-3">
            <span class="text-lg font-semibold">Berakhir dalam:</span>
            <div class="flex space-x-2">
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.hours }}</div>
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.minutes }}</div>
              <div class="bg-white text-red-500 px-3 py-2 rounded-lg font-bold">{{ countdown.seconds }}</div>
            </div>
          </div>
        </div>
        <div class="grid md:grid-cols-3 gap-6">
          <div v-for="product in flashSaleProducts" :key="product.id" class="bg-white rounded-2xl p-6 text-dark shadow-xl">
            <div class="relative mb-4">
              <img :src="product.image" :alt="product.name" class="w-full h-40 object-cover rounded-xl">
              <div class="absolute top-2 left-2 bg-red-500 text-white px-2 py-1 rounded-lg text-sm font-bold">
                -{{ product.discount }}%
              </div>
            </div>
            <h3 class="font-bold mb-2">{{ product.name }}</h3>
            <div class="flex items-center space-x-2 mb-3">
              <span class="text-2xl font-bold text-primary">{{ formatPrice(product.salePrice) }}</span>
              <span class="text-gray-500 line-through">{{ formatPrice(product.originalPrice) }}</span>
            </div>
            <button class="w-full bg-primary text-white py-2 rounded-xl hover:bg-blue-700 transition-colors">
              Beli Sekarang
            </button>
          </div>
        </div>
        <div class="text-center mt-8">
          <router-link to="/products" class="bg-secondary text-white px-8 py-3 rounded-lg hover:bg-blue-600 transition-colors inline-block">
            Lihat Semua Produk
          </router-link>
        </div>
      </div>
    </section>

    <!-- Cara Kerja -->
    <section class="py-16 bg-white">
      <div class="container mx-auto px-4">
        <div class="text-center mb-12">
          <h2 class="text-3xl font-bold text-dark mb-4">Cara Kerja</h2>
          <p class="text-gray-600">Proses belanja yang mudah dan aman</p>
        </div>
        <div class="grid md:grid-cols-3 gap-8">
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-user-plus text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">1. Daftar</h3>
            <p class="text-gray-600">Buat akun dan verifikasi identitas Anda untuk mulai berbelanja</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-search text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">2. Pilih Produk</h3>
            <p class="text-gray-600">Jelajahi berbagai kategori dan temukan produk yang Anda inginkan</p>
          </div>
          <div class="text-center">
            <div class="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
              <i class="fa-solid fa-shopping-cart text-white text-2xl"></i>
            </div>
            <h3 class="text-xl font-semibold text-dark mb-2">3. Beli & Nikmati</h3>
            <p class="text-gray-600">Checkout dengan mudah dan nikmati produk berkualitas tinggi</p>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="py-16 bg-gradient-to-r from-primary to-secondary text-white">
      <div class="container mx-auto px-4 text-center">
        <h2 class="text-4xl font-bold mb-4">Siap Memulai Belanja?</h2>
        <p class="text-xl mb-8 opacity-90">Bergabunglah dengan ribuan pengguna yang telah merasakan pengalaman belanja terbaik</p>
        <div class="flex justify-center space-x-4">
          <router-link to="/login" class="bg-accent text-white px-8 py-3 rounded-lg font-semibold hover:bg-yellow-600 transition-colors">
            Daftar Gratis
          </router-link>
          <router-link to="/products" class="border-2 border-white text-white px-8 py-3 rounded-lg font-semibold hover:bg-white hover:text-primary transition-colors">
            Mulai Belanja
          </router-link>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <Footer />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import UserNavbar from '../components/UserNavbar.vue'
import GuestNavbar from '../components/GuestNavbar.vue'
import Footer from '../components/Footer.vue'

const authStore = useAuthStore()

// Initialize auth on component mount
onMounted(() => {
  authStore.initializeAuth()
})

// Countdown timer
const countdown = reactive({
  hours: 23,
  minutes: 45,
  seconds: 12
})

// Flash sale products
const flashSaleProducts = reactive([
  {
    id: 1,
    name: 'Corsair Vengeance RGB Pro 32GB',
    image: 'https://images.unsplash.com/photo-1591799264318-7e6ef8ddb7ea?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 4000000,
    salePrice: 2400000,
    discount: 40
  },
  {
    id: 2,
    name: 'Samsung 980 PRO SSD 1TB',
    image: 'https://images.unsplash.com/photo-1597872200969-2b65d56bd16b?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 3000000,
    salePrice: 1950000,
    discount: 35
  },
  {
    id: 3,
    name: 'Razer BlackWidow V3',
    image: 'https://images.unsplash.com/photo-1541140532154-b024d705b90a?ixlib=rb-4.0.3&auto=format&fit=crop&w=500&q=80',
    originalPrice: 2400000,
    salePrice: 1200000,
    discount: 50
  }
])

// Timer interval
let timerInterval: NodeJS.Timeout

// Format price to Indonesian Rupiah
const formatPrice = (price: number): string => {
  return new Intl.NumberFormat('id-ID', {
    style: 'currency',
    currency: 'IDR',
    minimumFractionDigits: 0
  }).format(price)
}

// Update countdown timer
const updateCountdown = () => {
  countdown.seconds--
  if (countdown.seconds < 0) {
    countdown.seconds = 59
    countdown.minutes--
    if (countdown.minutes < 0) {
      countdown.minutes = 59
      countdown.hours--
      if (countdown.hours < 0) {
        countdown.hours = 23
      }
    }
  }
}

onMounted(() => {
  timerInterval = setInterval(updateCountdown, 1000)
})

onUnmounted(() => {
  if (timerInterval) {
    clearInterval(timerInterval)
  }
})
</script>

<style scoped>
.container {
  max-width: 1200px;
}

.category-card:hover .group-hover\:bg-primary {
  background-color: #1e40af;
}

.category-card:hover .group-hover\:text-white {
  color: white;
}
</style>
