<template>
  <div v-if="password" class="mt-2">
    <!-- Strength Bar -->
    <div class="flex space-x-1 mb-2">
      <div 
        v-for="i in 4" 
        :key="i"
        class="flex-1 h-2 rounded-full transition-colors duration-300"
        :class="getBarColor(i)"
      ></div>
    </div>
    
    <!-- Strength Text -->
    <div class="flex items-center justify-between mb-2">
      <span class="text-sm font-medium" :class="strengthTextColor">
        {{ strengthText }}
      </span>
      <span class="text-xs text-gray-500">
        {{ password.length }}/{{ minLength }} characters
      </span>
    </div>
    
    <!-- Requirements Checklist -->
    <div class="space-y-1">
      <div 
        v-for="requirement in requirements" 
        :key="requirement.key"
        class="flex items-center text-xs"
        :class="requirement.met ? 'text-green-600' : 'text-gray-500'"
      >
        <i 
          :class="requirement.met ? 'fa-solid fa-check-circle' : 'fa-regular fa-circle'"
          class="mr-2 text-xs"
        ></i>
        {{ requirement.text }}
      </div>
    </div>
    
    <!-- Suggestions -->
    <div v-if="suggestions.length > 0" class="mt-2 p-2 bg-yellow-50 rounded-lg border border-yellow-200">
      <p class="text-xs font-medium text-yellow-800 mb-1">Suggestions:</p>
      <ul class="text-xs text-yellow-700 space-y-1">
        <li v-for="suggestion in suggestions" :key="suggestion" class="flex items-start">
          <i class="fa-solid fa-lightbulb mr-1 mt-0.5 text-yellow-600"></i>
          {{ suggestion }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'

interface Props {
  password: string
  minLength?: number
  showRequirements?: boolean
  showSuggestions?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  minLength: 8,
  showRequirements: true,
  showSuggestions: true
})

// Password strength calculation
const passwordStrength = computed(() => {
  if (!props.password) return 0
  
  let score = 0
  const password = props.password
  
  // Length check
  if (password.length >= props.minLength) score += 1
  if (password.length >= 12) score += 1
  
  // Character variety checks
  if (/[a-z]/.test(password)) score += 1
  if (/[A-Z]/.test(password)) score += 1
  if (/[0-9]/.test(password)) score += 1
  if (/[^A-Za-z0-9]/.test(password)) score += 1
  
  // Bonus for longer passwords
  if (password.length >= 16) score += 1
  
  // Penalty for common patterns
  if (/(.)\1{2,}/.test(password)) score -= 1 // Repeated characters
  if (/123|abc|qwe/i.test(password)) score -= 1 // Sequential patterns
  
  return Math.max(0, Math.min(4, score))
})

// Strength text and color
const strengthText = computed(() => {
  switch (passwordStrength.value) {
    case 0:
    case 1:
      return 'Very Weak'
    case 2:
      return 'Weak'
    case 3:
      return 'Good'
    case 4:
      return 'Strong'
    default:
      return 'Very Weak'
  }
})

const strengthTextColor = computed(() => {
  switch (passwordStrength.value) {
    case 0:
    case 1:
      return 'text-red-600'
    case 2:
      return 'text-orange-600'
    case 3:
      return 'text-yellow-600'
    case 4:
      return 'text-green-600'
    default:
      return 'text-red-600'
  }
})

// Bar colors for strength indicator
const getBarColor = (index: number) => {
  if (index <= passwordStrength.value) {
    switch (passwordStrength.value) {
      case 1:
        return 'bg-red-500'
      case 2:
        return 'bg-orange-500'
      case 3:
        return 'bg-yellow-500'
      case 4:
        return 'bg-green-500'
      default:
        return 'bg-red-500'
    }
  }
  return 'bg-gray-200'
}

// Requirements checklist
const requirements = computed(() => {
  const password = props.password
  
  return [
    {
      key: 'length',
      text: `At least ${props.minLength} characters`,
      met: password.length >= props.minLength
    },
    {
      key: 'lowercase',
      text: 'Contains lowercase letter (a-z)',
      met: /[a-z]/.test(password)
    },
    {
      key: 'uppercase',
      text: 'Contains uppercase letter (A-Z)',
      met: /[A-Z]/.test(password)
    },
    {
      key: 'number',
      text: 'Contains number (0-9)',
      met: /[0-9]/.test(password)
    },
    {
      key: 'special',
      text: 'Contains special character (!@#$%^&*)',
      met: /[^A-Za-z0-9]/.test(password)
    }
  ]
})

// Suggestions for improvement
const suggestions = computed(() => {
  if (!props.showSuggestions || passwordStrength.value >= 4) return []
  
  const password = props.password
  const suggestions: string[] = []
  
  if (password.length < props.minLength) {
    suggestions.push(`Make it at least ${props.minLength} characters long`)
  }
  
  if (!/[a-z]/.test(password)) {
    suggestions.push('Add lowercase letters')
  }
  
  if (!/[A-Z]/.test(password)) {
    suggestions.push('Add uppercase letters')
  }
  
  if (!/[0-9]/.test(password)) {
    suggestions.push('Add numbers')
  }
  
  if (!/[^A-Za-z0-9]/.test(password)) {
    suggestions.push('Add special characters (!@#$%^&*)')
  }
  
  if (password.length < 12) {
    suggestions.push('Consider making it longer for better security')
  }
  
  if (/(.)\1{2,}/.test(password)) {
    suggestions.push('Avoid repeating the same character multiple times')
  }
  
  if (/123|abc|qwe/i.test(password)) {
    suggestions.push('Avoid common sequences like "123" or "abc"')
  }
  
  return suggestions.slice(0, 3) // Limit to 3 suggestions
})

// Expose strength value for parent component
defineExpose({
  strength: passwordStrength,
  isStrong: computed(() => passwordStrength.value >= 3),
  isValid: computed(() => passwordStrength.value >= 2)
})
</script>

<style scoped>
/* Additional custom styles if needed */
</style>
