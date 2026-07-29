<template>
  <div class="min-h-screen flex items-center justify-center bg-surface-dim px-6">
    <div class="w-full max-w-md">
      <!-- Logo / Brand -->
      <div class="text-center mb-8">
        <h1 class="font-display text-3xl text-primary mb-2">塑说心语</h1>
        <p class="text-sm text-on-surface-variant">管理后台</p>
      </div>

      <!-- Login Card -->
      <div class="bg-white rounded-2xl shadow-xl p-8 md:p-12">
        <h2 class="font-display text-xl text-on-surface mb-8 text-center">管理员登录</h2>

        <form @submit.prevent="handleLogin" class="space-y-5">
          <div>
            <label for="username" class="block text-sm font-medium text-on-surface mb-1.5">用户名</label>
            <input id="username" v-model="username" type="text" required autocomplete="username"
              class="w-full px-4 py-3 border border-outline-variant rounded-xl focus:border-primary focus:ring-1
                     focus:ring-primary outline-none transition-all text-on-surface"
              placeholder="请输入管理员用户名" />
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-on-surface mb-1.5">密码</label>
            <input id="password" v-model="password" type="password" required autocomplete="current-password"
              class="w-full px-4 py-3 border border-outline-variant rounded-xl focus:border-primary focus:ring-1
                     focus:ring-primary outline-none transition-all text-on-surface"
              placeholder="请输入密码" />
          </div>

          <p v-if="error" class="text-error text-sm bg-error-container/30 px-4 py-3 rounded-xl">
            {{ error }}
          </p>

          <button type="submit" :disabled="loading"
            class="w-full py-3.5 bg-primary text-on-primary rounded-full font-semibold text-lg
                   hover:bg-primary-container shadow-lg shadow-primary/20 hover:shadow-xl
                   transition-all active:scale-95 disabled:opacity-50 disabled:cursor-not-allowed">
            <span v-if="!loading">登录</span>
            <span v-else class="flex items-center justify-center gap-2">
              <svg class="w-5 h-5 animate-spin" viewBox="0 0 24 24" fill="none">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
              </svg>
              登录中...
            </span>
          </button>
        </form>
      </div>

      <!-- Back to Site -->
      <div class="text-center mt-6">
        <router-link to="/" class="text-sm text-on-surface-variant/60 hover:text-primary transition-colors">
          返回前台
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'

const router = useRouter()
const store = useAppStore()

const username = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function handleLogin() {
  if (!username.value.trim() || !password.value.trim()) {
    error.value = '请输入用户名和密码'
    return
  }

  loading.value = true
  error.value = ''

  try {
    const res = await store.login(username.value.trim(), password.value)
    if (res.code === 200) {
      router.push('/admin')
    } else {
      error.value = res.message || '登录失败，请检查用户名和密码'
    }
  } catch (e) {
    console.error('Login failed:', e)
    error.value = '网络错误，请检查网络连接后重试'
  } finally {
    loading.value = false
  }
}
</script>
