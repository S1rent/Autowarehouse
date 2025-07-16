<template>
  <div class="fixed top-4 right-4 z-50 space-y-2">
    <transition-group name="notification" tag="div">
      <div
        v-for="notification in notifications"
        :key="notification.id"
        :class="getNotificationClass(notification.type)"
        class="max-w-sm w-full bg-white shadow-lg rounded-lg pointer-events-auto ring-1 ring-black ring-opacity-5 overflow-hidden"
      >
        <div class="p-4">
          <div class="flex items-start">
            <div class="flex-shrink-0">
              <i :class="getIconClass(notification.type)" class="text-lg"></i>
            </div>
            <div class="ml-3 w-0 flex-1 pt-0.5">
              <p class="text-sm font-medium text-gray-900">
                {{ notification.title }}
              </p>
              <p v-if="notification.message" class="mt-1 text-sm text-gray-500">
                {{ notification.message }}
              </p>
            </div>
            <div class="ml-4 flex-shrink-0 flex">
              <button
                @click="removeNotification(notification.id)"
                class="bg-white rounded-md inline-flex text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
              >
                <span class="sr-only">Close</span>
                <i class="fa-solid fa-times text-sm"></i>
              </button>
            </div>
          </div>
        </div>
        <!-- Progress bar for timed notifications -->
        <div
          v-if="!notification.persistent && notification.duration"
          class="h-1 bg-gray-200"
        >
          <div
            :class="getProgressBarClass(notification.type)"
            class="h-full transition-all ease-linear"
            :style="{ 
              width: '100%',
              animation: `shrink ${notification.duration}ms linear forwards`
            }"
          ></div>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script setup lang="ts">
import { useNotifications } from '@/composables/useNotifications'

const { notifications, removeNotification } = useNotifications()

const getNotificationClass = (type: string) => {
  const baseClass = 'border-l-4'
  switch (type) {
    case 'success':
      return `${baseClass} border-green-400`
    case 'error':
      return `${baseClass} border-red-400`
    case 'warning':
      return `${baseClass} border-yellow-400`
    case 'info':
      return `${baseClass} border-blue-400`
    default:
      return `${baseClass} border-gray-400`
  }
}

const getIconClass = (type: string) => {
  switch (type) {
    case 'success':
      return 'fa-solid fa-check-circle text-green-400'
    case 'error':
      return 'fa-solid fa-exclamation-circle text-red-400'
    case 'warning':
      return 'fa-solid fa-exclamation-triangle text-yellow-400'
    case 'info':
      return 'fa-solid fa-info-circle text-blue-400'
    default:
      return 'fa-solid fa-bell text-gray-400'
  }
}

const getProgressBarClass = (type: string) => {
  switch (type) {
    case 'success':
      return 'bg-green-400'
    case 'error':
      return 'bg-red-400'
    case 'warning':
      return 'bg-yellow-400'
    case 'info':
      return 'bg-blue-400'
    default:
      return 'bg-gray-400'
  }
}
</script>

<style scoped>
.notification-enter-active,
.notification-leave-active {
  transition: all 0.3s ease;
}

.notification-enter-from {
  opacity: 0;
  transform: translateX(100%);
}

.notification-leave-to {
  opacity: 0;
  transform: translateX(100%);
}

.notification-move {
  transition: transform 0.3s ease;
}

@keyframes shrink {
  from {
    width: 100%;
  }
  to {
    width: 0%;
  }
}
</style>
