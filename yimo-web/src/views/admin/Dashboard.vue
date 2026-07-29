<template>
  <div class="min-h-screen bg-background p-6 md:p-10">
    <div class="max-w-4xl mx-auto">
      <div class="flex justify-between items-center mb-8">
        <h1 class="font-display text-2xl text-on-surface">管理后台</h1>
        <button @click="logout" class="text-sm text-on-surface-variant hover:text-error">退出</button>
      </div>

      <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-10">
        <div v-for="s in stats" :key="s.label" class="bg-white rounded-xl p-5 shadow-sm border border-outline-variant/10 text-center">
          <p class="text-2xl font-display text-primary">{{ s.value }}</p>
          <p class="text-xs text-on-surface-variant mt-1">{{ s.label }}</p>
        </div>
      </div>

      <div class="grid md:grid-cols-2 gap-6">
        <div class="bg-white rounded-xl p-5 shadow-sm border border-outline-variant/10">
          <h3 class="font-semibold mb-3">热门产品 TOP5</h3>
          <div v-if="dashboard.popularProducts?.length" class="space-y-2">
            <div v-for="p in dashboard.popularProducts" :key="p.id" class="flex justify-between text-sm">
              <span>{{ p.name }}</span><span class="text-primary font-semibold">&yen;{{ p.price }} ({{ p.sales }})</span>
            </div>
          </div>
          <p v-else class="text-sm text-on-surface-variant/50">暂无</p>
        </div>
        <div class="bg-white rounded-xl p-5 shadow-sm border border-outline-variant/10">
          <h3 class="font-semibold mb-3">最近测试</h3>
          <div v-if="dashboard.recentTestRecords?.length" class="space-y-2">
            <div v-for="t in dashboard.recentTestRecords" :key="t.id" class="flex justify-between text-sm">
              <span>{{ t.userNickname||'匿名' }} → {{ t.zodiacName||'?' }}</span>
              <span class="text-on-surface-variant/50">{{ formatDate(t.createTime) }}</span>
            </div>
          </div>
          <p v-else class="text-sm text-on-surface-variant/50">暂无</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAppStore } from '@/stores/app'
import { adminAPI } from '@/api'

const router = useRouter()
const store = useAppStore()
const dashboard = ref({})

const stats = computed(() => [
  { label:'用户', value: dashboard.value.userCount||0 },
  { label:'文章', value: dashboard.value.articleCount||0 },
  { label:'产品', value: dashboard.value.productCount||0 },
  { label:'测试', value: dashboard.value.testCount||0 },
])
function formatDate(d) { return d ? new Date(d).toLocaleDateString('zh-CN') : '' }
function logout() { store.logout(); router.push('/admin/login') }
onMounted(async () => {
  try { const r = await adminAPI.dashboard(); if (r.code===200) dashboard.value = r.data } catch(e) {}
})
</script>
