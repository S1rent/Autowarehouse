<template>
  <footer class="bg-dark text-white py-12">
    <div class="container mx-auto px-4">
      <div class="grid md:grid-cols-4 gap-8">
        <div>
          <div class="text-2xl font-bold mb-4">Autowarehouse</div>
          <p class="text-gray-400 mb-4">Platform e-commerce terpercaya untuk kebutuhan hardware dan elektronik dengan sistem keamanan tinggi.</p>
          <div class="flex space-x-4">
            <i class="fa-brands fa-facebook text-xl hover:text-accent cursor-pointer transition-colors"></i>
            <i class="fa-brands fa-twitter text-xl hover:text-accent cursor-pointer transition-colors"></i>
            <i class="fa-brands fa-instagram text-xl hover:text-accent cursor-pointer transition-colors"></i>
          </div>
        </div>
        <div>
          <h3 class="font-semibold mb-4">Kategori</h3>
          <ul class="space-y-2 text-gray-400">
            <li><router-link to="/products?category=processor" class="hover:text-white cursor-pointer transition-colors">Processor</router-link></li>
            <li><router-link to="/products?category=ram" class="hover:text-white cursor-pointer transition-colors">RAM & Memory</router-link></li>
            <li><router-link to="/products?category=storage" class="hover:text-white cursor-pointer transition-colors">Storage</router-link></li>
            <li><router-link to="/products?category=vga" class="hover:text-white cursor-pointer transition-colors">VGA Card</router-link></li>
          </ul>
        </div>
        <div>
          <h3 class="font-semibold mb-4">Bantuan</h3>
          <ul class="space-y-2 text-gray-400">
            <li><span class="hover:text-white cursor-pointer transition-colors">Cara Belanja</span></li>
            <li><span class="hover:text-white cursor-pointer transition-colors">FAQ</span></li>
            <li><router-link to="/customer-service" class="hover:text-white cursor-pointer transition-colors">Kontak</router-link></li>
            <li><span class="hover:text-white cursor-pointer transition-colors">Syarat & Ketentuan</span></li>
          </ul>
        </div>
        <div>
          <h3 class="font-semibold mb-4">Kontak</h3>
          <div class="space-y-2 text-gray-400">
            <p><i class="fa-solid fa-phone mr-2"></i>+62 21 1234 5678</p>
            <p><i class="fa-solid fa-envelope mr-2"></i>info@autowarehouse.id</p>
            <p><i class="fa-solid fa-location-dot mr-2"></i>Jakarta, Indonesia</p>
          </div>
        </div>
      </div>
      <div class="border-t border-gray-700 mt-8 pt-8 text-center text-gray-400">
        <p>© 2024 Autowarehouse. Semua hak dilindungi undang-undang.</p>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts">
// No props or logic needed for now, just a static footer
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style>
