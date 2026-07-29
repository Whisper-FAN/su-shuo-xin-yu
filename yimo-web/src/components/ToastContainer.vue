<template>
  <div class="fixed bottom-6 left-1/2 z-[9999] flex flex-col items-center gap-2 pointer-events-none">
    <div v-for="toast in toasts" :key="toast.id"
      :class="['pointer-events-auto px-6 py-3 rounded-full text-sm font-semibold shadow-2xl',
               toast.leaving ? 'toast-leave' : 'toast-enter',
               toast.type === 'success' ? 'bg-secondary text-on-secondary' :
               toast.type === 'error' ? 'bg-error text-on-error' :
               'bg-on-surface text-surface']">
      {{ toast.message }}
    </div>
  </div>
</template>

<script setup>
import { useToast } from '@/composables/useToast'
const { toasts } = useToast()
</script>
