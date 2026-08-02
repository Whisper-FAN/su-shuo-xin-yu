import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig(({ mode }) => ({
  plugins: [
    vue(),
    {
      name: 'inject-base-tag',
      transformIndexHtml(html) {
        return mode === 'production'
          ? html.replace('<title>', '<base href="/su-shuo-xin-yu/"><title>')
          : html
      }
    }
  ],
  base: mode === 'production' ? '/su-shuo-xin-yu/' : '/',
  resolve: { alias: { '@': resolve(__dirname, 'src') } },
  server: { port: 3000 }
}))
