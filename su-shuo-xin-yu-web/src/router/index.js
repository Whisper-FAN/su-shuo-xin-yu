import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Home', component: () => import('@/views/Home.vue') },
  { path: '/test', name: 'Test', component: () => import('@/views/PersonalityTest.vue') },
  { path: '/result/:recordId?', name: 'Result', component: () => import('@/views/TestResult.vue') },
  { path: '/zodiac', name: 'ZodiacList', component: () => import('@/views/ZodiacList.vue') },
  { path: '/zodiac/:id', name: 'ZodiacDetail', component: () => import('@/views/ZodiacDetail.vue') },
  { path: '/products', name: 'Products', component: () => import('@/views/Products.vue') },
  { path: '/gallery', name: 'Gallery', component: () => import('@/views/Gallery.vue') },
  { path: '/stories', name: 'Stories', component: () => import('@/views/Stories.vue') },
  // Admin (lean: dashboard + login only)
  { path: '/admin/login', name: 'AdminLogin', component: () => import('@/views/admin/Login.vue') },
  { path: '/admin', name: 'AdminDashboard', component: () => import('@/views/admin/Dashboard.vue') },
  { path: '/admin/counter', name: 'AdminCounter', component: () => import('@/views/admin/Counter.vue') },
  // Redirect old/deleted pages to home
  { path: '/articles', redirect: '/stories' },
  { path: '/article/:id', redirect: '/stories' },
  { path: '/product/:id', redirect: '/products' },
  { path: '/team', redirect: '/' },
  { path: '/feedback', redirect: '/' },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior() { return { top: 0 } }
})

export default router
