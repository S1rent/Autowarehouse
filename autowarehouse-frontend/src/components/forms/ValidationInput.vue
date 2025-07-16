<template>
  <div class="mb-4">
    <label 
      v-if="label" 
      :for="inputId" 
      class="block text-sm font-medium text-gray-700 mb-2"
    >
      {{ label }}
      <span v-if="required" class="text-red-500">*</span>
    </label>
    
    <div class="relative">
      <input
        :id="inputId"
        :type="type"
        :value="modelValue"
        :placeholder="placeholder"
        :readonly="readonly"
        :disabled="disabled"
        :required="required"
        @input="handleInput"
        @blur="handleBlur"
        @focus="handleFocus"
        :class="inputClasses"
        class="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-transparent transition-colors"
      >
      
      <!-- Icon -->
      <div v-if="icon" class="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
        <i :class="icon" class="text-gray-400"></i>
      </div>
      
      <!-- Loading spinner -->
      <div v-if="loading" class="absolute inset-y-0 right-0 pr-3 flex items-center">
        <i class="fa-solid fa-spinner fa-spin text-gray-400"></i>
      </div>
    </div>
    
    <!-- Error message -->
    <div v-if="errorMessage && showError" class="mt-1 text-sm text-red-600 flex items-center">
      <i class="fa-solid fa-exclamation-circle mr-1"></i>
      {{ errorMessage }}
    </div>
    
    <!-- Success message -->
    <div v-if="successMessage && showSuccess" class="mt-1 text-sm text-green-600 flex items-center">
      <i class="fa-solid fa-check-circle mr-1"></i>
      {{ successMessage }}
    </div>
    
    <!-- Help text -->
    <div v-if="helpText && !errorMessage && !successMessage" class="mt-1 text-sm text-gray-500">
      {{ helpText }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue'

interface Props {
  modelValue: string
  label?: string
  type?: string
  placeholder?: string
  readonly?: boolean
  disabled?: boolean
  required?: boolean
  icon?: string
  loading?: boolean
  errorMessage?: string
  successMessage?: string
  helpText?: string
  validation?: (value: string) => string | null
  validateOnBlur?: boolean
  validateOnInput?: boolean
}

interface Emits {
  (e: 'update:modelValue', value: string): void
  (e: 'blur', event: FocusEvent): void
  (e: 'focus', event: FocusEvent): void
  (e: 'validation', isValid: boolean, message?: string): void
}

const props = withDefaults(defineProps<Props>(), {
  type: 'text',
  validateOnBlur: true,
  validateOnInput: false
})

const emit = defineEmits<Emits>()

// Generate unique ID for input
const inputId = ref(`input-${Math.random().toString(36).substr(2, 9)}`)

// Validation state
const internalError = ref<string | null>(null)
const showError = ref(false)
const showSuccess = ref(false)
const isTouched = ref(false)

// Computed classes for input styling
const inputClasses = computed(() => {
  const baseClasses = []
  
  if (props.readonly) {
    baseClasses.push('bg-gray-50')
  }
  
  if (props.disabled) {
    baseClasses.push('opacity-50', 'cursor-not-allowed')
  }
  
  if (showError.value && (props.errorMessage || internalError.value)) {
    baseClasses.push('border-red-300', 'focus:ring-red-600')
  } else if (showSuccess.value && props.successMessage) {
    baseClasses.push('border-green-300', 'focus:ring-green-600')
  } else {
    baseClasses.push('border-gray-300')
  }
  
  return baseClasses.join(' ')
})

// Get current error message (external or internal)
const errorMessage = computed(() => {
  return props.errorMessage || internalError.value
})

// Handle input events
const handleInput = (event: Event) => {
  const target = event.target as HTMLInputElement
  const value = target.value
  
  emit('update:modelValue', value)
  
  if (props.validateOnInput && isTouched.value) {
    validateInput(value)
  }
}

const handleBlur = (event: FocusEvent) => {
  isTouched.value = true
  
  if (props.validateOnBlur) {
    validateInput(props.modelValue)
  }
  
  emit('blur', event)
}

const handleFocus = (event: FocusEvent) => {
  showError.value = false
  showSuccess.value = false
  emit('focus', event)
}

// Validation logic
const validateInput = (value: string) => {
  if (!props.validation) return
  
  const validationResult = props.validation(value)
  
  if (validationResult) {
    internalError.value = validationResult
    showError.value = true
    showSuccess.value = false
    emit('validation', false, validationResult)
  } else {
    internalError.value = null
    showError.value = false
    showSuccess.value = !!props.successMessage
    emit('validation', true)
  }
}

// Watch for external error changes
watch(() => props.errorMessage, (newError) => {
  if (newError) {
    showError.value = true
    showSuccess.value = false
  }
})

// Watch for success message changes
watch(() => props.successMessage, (newSuccess) => {
  if (newSuccess) {
    showSuccess.value = true
    showError.value = false
  }
})

// Watch model value changes for validation
watch(() => props.modelValue, (newValue) => {
  if (props.validateOnInput && isTouched.value) {
    validateInput(newValue)
  }
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
