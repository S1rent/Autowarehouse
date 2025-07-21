<template>
  <div v-if="isVisible" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
    <div class="bg-white rounded-xl shadow-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto">
      <!-- Header -->
      <div class="flex items-center justify-between p-6 border-b border-gray-200">
        <h2 class="text-xl font-bold text-gray-900">Tulis Review Produk</h2>
        <button 
          @click="closeModal"
          class="text-gray-400 hover:text-gray-600 transition-colors"
        >
          <i class="fa-solid fa-times text-xl"></i>
        </button>
      </div>

      <!-- Content -->
      <div class="p-6">
        <!-- Product Info -->
        <div v-if="orderItems && orderItems.length > 0" class="mb-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-3">Produk yang akan direview:</h3>
          <div class="space-y-3">
            <div 
              v-for="item in orderItems" 
              :key="item.productId"
              class="flex items-center space-x-3 p-3 bg-gray-50 rounded-lg"
            >
              <div class="w-16 h-16 bg-gray-200 rounded-lg flex items-center justify-center">
                <img 
                  v-if="item.productImages && item.productImages.length > 0"
                  :src="item.productImages[0]" 
                  :alt="item.productName"
                  class="w-full h-full object-cover rounded-lg"
                >
                <i v-else class="fa-solid fa-box text-gray-400"></i>
              </div>
              <div class="flex-1">
                <h4 class="font-medium text-gray-900">{{ item.productName }}</h4>
                <!-- <p class="text-sm text-gray-600">{{ item.productBrand }}</p> -->
                <p class="text-sm text-gray-500">Qty: {{ item.quantity }}</p>
              </div>
              <button
                @click="selectProduct(item)"
                :class="[
                  'px-4 py-2 rounded-lg text-sm font-medium transition-colors',
                  selectedProduct?.productId === item.productId
                    ? 'bg-blue-600 text-white'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                ]"
              >
                {{ selectedProduct?.productId === item.productId ? 'Dipilih' : 'Pilih' }}
              </button>
            </div>
          </div>
        </div>

        <!-- Review Form -->
        <form v-if="selectedProduct" @submit.prevent="submitReview" class="space-y-6">
          <!-- Rating -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Rating <span class="text-red-500">*</span>
            </label>
            <div class="flex items-center space-x-1">
              <button
                v-for="star in 5"
                :key="star"
                type="button"
                @click="setRating(star)"
                :class="[
                  'text-2xl transition-colors',
                  star <= rating ? 'text-yellow-400' : 'text-gray-300 hover:text-yellow-200'
                ]"
              >
                <i class="fa-solid fa-star"></i>
              </button>
              <span class="ml-3 text-sm text-gray-600">{{ getRatingText(rating) }}</span>
            </div>
          </div>

          <!-- Title -->
          <div>
            <label for="title" class="block text-sm font-medium text-gray-700 mb-2">
              Judul Review
            </label>
            <input
              id="title"
              v-model="title"
              type="text"
              placeholder="Ringkasan singkat tentang produk ini..."
              maxlength="100"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent"
            >
            <p class="text-xs text-gray-500 mt-1">{{ title.length }}/100 karakter</p>
          </div>

          <!-- Description -->
          <div>
            <label for="comment" class="block text-sm font-medium text-gray-700 mb-2">
              Deskripsi Review
            </label>
            <textarea
              id="comment"
              v-model="comment"
              rows="4"
              placeholder="Ceritakan pengalaman Anda dengan produk ini..."
              maxlength="1000"
              class="w-full px-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent resize-none"
            ></textarea>
            <p class="text-xs text-gray-500 mt-1">{{ comment.length }}/1000 karakter</p>
          </div>

          <!-- File Upload -->
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">
              Upload Gambar & Video (Opsional)
            </label>
            
            <!-- Upload Area -->
            <div 
              @drop="handleDrop"
              @dragover.prevent
              @dragenter.prevent
              class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-blue-400 transition-colors"
            >
              <i class="fa-solid fa-cloud-upload text-3xl text-gray-400 mb-3"></i>
              <p class="text-gray-600 mb-2">Drag & drop file atau klik untuk upload</p>
              <p class="text-xs text-gray-500 mb-4">Maksimal 5 file (gambar: JPG, PNG | video: MP4, MOV)</p>
              
              <input
                ref="fileInput"
                type="file"
                multiple
                accept="image/*,video/*"
                @change="handleFileSelect"
                class="hidden"
              >
              
              <button
                type="button"
                @click="$refs.fileInput.click()"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
              >
                Pilih File
              </button>
            </div>

            <!-- File Preview -->
            <div v-if="uploadedFiles.length > 0" class="mt-4 space-y-2">
              <div 
                v-for="(file, index) in uploadedFiles" 
                :key="index"
                class="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
              >
                <div class="flex items-center space-x-3">
                  <div class="w-12 h-12 bg-gray-200 rounded-lg flex items-center justify-center">
                    <img 
                      v-if="file.type.startsWith('image/')"
                      :src="file.preview" 
                      alt="Preview"
                      class="w-full h-full object-cover rounded-lg"
                    >
                    <i v-else class="fa-solid fa-video text-gray-400"></i>
                  </div>
                  <div>
                    <p class="text-sm font-medium text-gray-900">{{ file.name }}</p>
                    <p class="text-xs text-gray-500">{{ formatFileSize(file.size) }}</p>
                  </div>
                </div>
                <div class="flex items-center space-x-2">
                  <!-- <div v-if="file.uploading" class="w-4 h-4 border-2 border-blue-600 border-t-transparent rounded-full animate-spin"></div>
                  <i v-else-if="file.uploaded" class="fa-solid fa-check-circle text-green-500 text-lg"></i> -->
                  <button
                    type="button"
                    @click="removeFile(index)"
                    class="text-red-500 hover:text-red-700 transition-colors"
                  >
                    <i class="fa-solid fa-trash text-sm"></i>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Submit Buttons -->
          <div class="flex items-center justify-end space-x-3 pt-4 border-t border-gray-200">
            <button
              type="button"
              @click="closeModal"
              class="px-6 py-3 border border-gray-200 text-gray-700 rounded-lg hover:bg-gray-50 transition-colors"
            >
              Batal
            </button>
            <button
              type="submit"
              :disabled="!canSubmit || isSubmitting"
              :class="[
                'px-6 py-3 rounded-lg font-medium transition-colors',
                canSubmit && !isSubmitting
                  ? 'bg-blue-600 text-white hover:bg-blue-700'
                  : 'bg-gray-300 text-gray-500 cursor-not-allowed'
              ]"
            >
              <i v-if="isSubmitting" class="fa-solid fa-spinner fa-spin mr-2"></i>
              {{ isSubmitting ? 'Mengirim...' : 'Kirim Review' }}
            </button>
          </div>
        </form>

        <!-- No Product Selected -->
        <div v-else-if="orderItems && orderItems.length > 0" class="text-center py-8">
          <i class="fa-solid fa-hand-pointer text-4xl text-gray-300 mb-4"></i>
          <p class="text-gray-600">Pilih produk yang ingin direview</p>
        </div>

        <!-- No Products to Review -->
        <div v-else class="text-center py-12">
          <i class="fa-solid fa-check-circle text-4xl text-green-500 mb-4"></i>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">Semua Produk Sudah Direview</h3>
          <p class="text-gray-600">Anda telah memberikan review untuk semua produk dalam pesanan ini.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { apiService } from '@/services/api'
import { useNotifications } from '@/composables/useNotifications'

// Props
const props = defineProps({
  isVisible: {
    type: Boolean,
    default: false
  },
  orderId: {
    type: Number,
    required: true
  },
  orderItems: {
    type: Array,
    default: () => []
  }
})

// Emits
const emit = defineEmits(['close', 'success'])

// Composables
const { success, error: showError } = useNotifications()

// Reactive data
const selectedProduct = ref(null)
const rating = ref(0)
const title = ref('')
const comment = ref('')
const uploadedFiles = ref([])
const isSubmitting = ref(false)

// Computed
const canSubmit = computed(() => {
  return selectedProduct.value && rating.value > 0 && !isSubmitting.value
})

// Methods
const selectProduct = (item) => {
  selectedProduct.value = item
}

const setRating = (star) => {
  rating.value = star
}

const getRatingText = (rating) => {
  const texts = {
    1: 'Sangat Buruk',
    2: 'Buruk', 
    3: 'Biasa',
    4: 'Bagus',
    5: 'Sangat Bagus'
  }
  return texts[rating] || 'Pilih rating'
}

const handleFileSelect = (event) => {
  const files = Array.from(event.target.files)
  processFiles(files)
}

const handleDrop = (event) => {
  event.preventDefault()
  const files = Array.from(event.dataTransfer.files)
  processFiles(files)
}

const processFiles = (files) => {
  // Limit to 5 files total
  const remainingSlots = 5 - uploadedFiles.value.length
  const filesToProcess = files.slice(0, remainingSlots)
  
  filesToProcess.forEach(file => {
    // Validate file type
    const isImage = file.type.startsWith('image/')
    const isVideo = file.type.startsWith('video/')
    
    if (!isImage && !isVideo) {
      showError('File Error', `File ${file.name} tidak didukung. Hanya gambar dan video yang diperbolehkan.`)
      return
    }
    
    // Validate file size (10MB max)
    if (file.size > 10 * 1024 * 1024) {
      showError('File Error', `File ${file.name} terlalu besar. Maksimal 10MB.`)
      return
    }
    
    const fileObj = {
      file,
      name: file.name,
      size: file.size,
      type: file.type,
      preview: null,
      uploading: false,
      uploaded: false,
      url: null
    }
    
    // Create preview for images
    if (isImage) {
      const reader = new FileReader()
      reader.onload = (e) => {
        fileObj.preview = e.target.result
      }
      reader.readAsDataURL(file)
    }
    
    uploadedFiles.value.push(fileObj)
    
    // Start upload to Firebase
    uploadToFirebase(fileObj)
  })
}

const uploadToFirebase = async (fileObj) => {
  try {
    fileObj.uploading = true
    fileObj.uploaded = false
    
    // Import Firebase upload function
    const { uploadReviewMedia } = await import('@/services/firebase')
    
    // Upload to Firebase Storage
    const result = await uploadReviewMedia(fileObj.file)
    
    // Set success state
    fileObj.uploading = false
    fileObj.uploaded = true
    fileObj.url = result.url
    fileObj.path = result.path
    
    console.log(`Upload completed for ${fileObj.name}:`, result.url)
    
  } catch (error) {
    console.error('Upload error:', error)
    fileObj.uploading = false
    fileObj.uploaded = false
    showError('Upload Error', `Gagal upload ${fileObj.name}: ${error.message}`)
  }
}

const removeFile = (index) => {
  uploadedFiles.value.splice(index, 1)
}

const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 Bytes'
  const k = 1024
  const sizes = ['Bytes', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

const submitReview = async () => {
  if (!canSubmit.value) return
  
  try {
    isSubmitting.value = true
    
    // Prepare image and video URLs
    const imageUrls = uploadedFiles.value
      .filter(file => file.type.startsWith('image/') && file.uploaded)
      .map(file => file.url)
    
    const videoUrls = uploadedFiles.value
      .filter(file => file.type.startsWith('video/') && file.uploaded)
      .map(file => file.url)
    
    const reviewData = {
      productId: selectedProduct.value.productId,
      orderId: props.orderId,
      rating: rating.value,
      title: title.value.trim() || undefined,
      comment: comment.value.trim() || undefined,
      imageUrls: imageUrls.length > 0 ? imageUrls : undefined,
      videoUrls: videoUrls.length > 0 ? videoUrls : undefined
    }
    
    await apiService.createReview(reviewData)
    
    success('Review Berhasil', 'Review Anda telah berhasil dikirim!')
    emit('success')
    closeModal()
    
  } catch (error) {
    console.error('Submit review error:', error)
    showError('Submit Error', error.response?.data?.error || 'Gagal mengirim review')
  } finally {
    isSubmitting.value = false
  }
}

const closeModal = () => {
  // Reset form
  selectedProduct.value = null
  rating.value = 0
  title.value = ''
  comment.value = ''
  uploadedFiles.value = []
  isSubmitting.value = false
  
  emit('close')
}

// Watch for modal visibility changes
watch(() => props.isVisible, (newValue) => {
  if (!newValue) {
    closeModal()
  }
})
</script>

<style scoped>
/* Custom scrollbar for modal */
.overflow-y-auto::-webkit-scrollbar {
  width: 6px;
}

.overflow-y-auto::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

.overflow-y-auto::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
