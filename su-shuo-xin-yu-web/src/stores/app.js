import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI } from '@/api'

export const useAppStore = defineStore('app', () => {
  const token = ref(localStorage.getItem('token') || '')
  const user = ref(JSON.parse(localStorage.getItem('user') || 'null'))
  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'admin')

  async function login(username, password) {
    const res = await authAPI.login(username, password)
    if (res.code === 200) {
      token.value = res.data.token
      user.value = {
        userId: res.data.userId,
        nickname: res.data.nickname,
        avatar: res.data.avatar,
        role: res.data.role
      }
      localStorage.setItem('token', token.value)
      localStorage.setItem('user', JSON.stringify(user.value))
    }
    return res
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  }

  return { token, user, isLoggedIn, isAdmin, login, logout }
})
