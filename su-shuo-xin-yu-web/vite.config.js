import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig(({ mode }) => {
  const isProd = mode === 'production'
  return {
    plugins: [
      vue(),
      {
        name: 'inject-base',
        transformIndexHtml(html) {
          return isProd ? html.replace('<title>', '<base href="/su-shuo-xin-yu/"><title>') : html
        }
      }
    ],
    base: isProd ? '/su-shuo-xin-yu/' : '/',
    resolve: { alias: { '@': resolve(__dirname, 'src') } },
    server: { port: 3000 }
  }
})
