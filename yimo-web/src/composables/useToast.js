import { ref } from 'vue'

const toasts = ref([])
let id = 0

export function useToast() {
  function show(message, type = 'success', duration = 2500) {
    const toast = { id: ++id, message, type, leaving: false }
    toasts.value.push(toast)
    setTimeout(() => {
      toast.leaving = true
      setTimeout(() => {
        toasts.value = toasts.value.filter(t => t.id !== toast.id)
      }, 300)
    }, duration)
  }

  function success(msg) { show(msg, 'success') }
  function error(msg) { show(msg, 'error') }
  function info(msg) { show(msg, 'info') }

  return { toasts, show, success, error, info }
}
