<template>
  <nav class="fixed top-0 w-full z-50 transition-all duration-500"
       :class="navClasses">
    <div class="max-w-[1280px] mx-auto px-6 flex justify-between items-center">
      <!-- Logo -->
      <router-link to="/" class="flex items-center gap-3 hover:scale-105 transition-transform">
        <img
          v-if="!logoError"
          src="/images/team/logo.jpg"
          alt="塑说心语"
          class="h-10 md:h-12 w-auto object-contain rounded-lg shadow-sm"
          @error="logoError = true"
        />
        <div v-else class="h-10 w-10 md:h-12 md:w-12 rounded-full bg-primary flex items-center justify-center text-white font-display text-lg font-bold shadow-sm">
          塑
        </div>
        <span class="font-display text-lg md:text-xl font-bold hidden sm:block"
              :class="isHomeHero ? 'text-white' : 'text-primary'">
          塑说心语
        </span>
      </router-link>

      <!-- Desktop Nav Links -->
      <div class="hidden md:flex items-center gap-8">
        <router-link v-for="item in navItems" :key="item.path"
          :to="item.path"
          class="text-sm transition-all duration-300 pb-1"
          :class="isHomeHero
            ? 'text-white/80 hover:text-white border-white/40'
            : 'text-on-surface-variant hover:text-primary border-transparent',
            isActive(item.path)
              ? (isHomeHero ? 'font-bold border-b-2 !text-white border-white' : 'font-bold border-b-2 !text-primary border-primary')
              : ''">
          {{ item.label }}
        </router-link>
      </div>

      <!-- Right side -->
      <div class="flex items-center gap-3">
        <router-link to="/test"
          class="text-sm px-6 py-2.5 rounded-full font-semibold transition-all duration-300 active:scale-95"
          :class="isHomeHero
            ? 'bg-white/20 text-white border border-white/40 hover:bg-white/30'
            : 'bg-primary text-on-primary hover:shadow-lg hover:shadow-primary/20'">
          开始测试
        </router-link>

        <!-- Mobile menu toggle -->
        <button @click="mobileOpen = !mobileOpen" class="md:hidden p-2">
          <svg class="w-6 h-6" :class="isHomeHero ? 'text-white' : 'text-on-surface'" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path v-if="!mobileOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
            <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
          </svg>
        </button>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div v-if="mobileOpen" class="md:hidden bg-surface/98 backdrop-blur-xl border-t border-outline-variant/20 shadow-lg">
      <div class="px-6 py-4 space-y-1">
        <router-link v-for="item in navItems" :key="item.path"
          :to="item.path"
          @click="mobileOpen = false"
          class="block py-3 px-4 rounded-lg text-sm font-body transition-all"
          :class="isActive(item.path)
            ? 'bg-primary/10 text-primary font-bold'
            : 'text-on-surface-variant hover:bg-surface-container'">
          {{ item.label }}
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const scrolled = ref(false)
const mobileOpen = ref(false)
const logoError = ref(false)

const navItems = [
  { label: '文化起源', path: '/stories' },
  { label: '作品赏析', path: '/zodiac' },
  { label: '性格测试', path: '/test' },
  { label: '手艺工坊', path: '/gallery' },
]

const isHomeHero = computed(() => route.path === '/' && !scrolled.value)
const isActive = (path) => route.path.startsWith(path)

const navClasses = computed(() => {
  if (scrolled.value) return 'bg-surface/90 backdrop-blur-xl shadow-sm py-3'
  if (route.path === '/') return 'bg-transparent py-5'
  return 'bg-surface/50 backdrop-blur-sm py-4'
})

function handleScroll() {
  scrolled.value = window.scrollY > 60
}

onMounted(() => window.addEventListener('scroll', handleScroll, { passive: true }))
onUnmounted(() => window.removeEventListener('scroll', handleScroll))
</script>
