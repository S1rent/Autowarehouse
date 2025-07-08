import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { useAuthStore } from './stores/auth'
import './style.css'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)

// Initialize auth store to restore user session from localStorage
// This must be done after pinia is installed but before router
const authStore = useAuthStore()
authStore.initializeAuth()

app.use(router)
app.mount('#app')
