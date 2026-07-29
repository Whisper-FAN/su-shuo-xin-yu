<template>
  <div class="min-h-screen flex flex-col">
    <NavBar />
    <main class="flex-grow">
      <router-view v-slot="{ Component, route }">
        <transition :name="route.meta.transition || 'page'" mode="out-in">
          <component :is="Component" :key="route.path" />
        </transition>
      </router-view>
    </main>
    <SiteFooter v-if="!$route.path.startsWith('/admin')" />
    <ToastContainer />
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
import SiteFooter from '@/components/SiteFooter.vue'
import ToastContainer from '@/components/ToastContainer.vue'
</script>

<style>
.page-enter-active { transition: opacity 0.25s ease, transform 0.25s ease; }
.page-leave-active { transition: opacity 0.15s ease, transform 0.15s ease; }
.page-enter-from { opacity: 0; transform: translateY(8px); }
.page-leave-to { opacity: 0; transform: translateY(-8px); }
</style>
