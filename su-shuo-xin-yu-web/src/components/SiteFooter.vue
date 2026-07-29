<template>
  <footer class="bg-surface-container-low border-t border-outline-variant/20 mt-24">
    <div class="max-w-[1280px] mx-auto px-6 py-16">
      <div class="grid md:grid-cols-3 gap-12 mb-12">
        <!-- Brand -->
        <div>
          <div class="flex items-center gap-3 mb-4">
            <!-- Team logo -->
            <div class="h-10 w-10 rounded-lg overflow-hidden bg-surface-container border border-outline-variant/20 flex-shrink-0">
              <img
                v-if="!logoImgError"
                :src="'/su-shuo-xin-yu/images/team/logo.jpg'"
                alt="塑说心语"
                class="h-full w-full object-cover"
                @error="logoImgError = true" />
              <div v-else class="h-full w-full flex items-center justify-center bg-primary/20 rounded-lg">
                <span class="font-display text-lg text-primary">塑</span>
              </div>
            </div>
            <div>
              <span class="font-display text-xl text-on-surface">塑说心语</span>
              <span class="block font-body text-xs text-on-surface-variant/60">传统文化的数字守护者</span>
            </div>
          </div>
          <div class="flex gap-3 mt-4 flex-wrap">
            <div class="h-8 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10">
              <img
                v-if="!getImgError('school-name-badge')"
                :src="'/su-shuo-xin-yu/images/team/school-name-badge.png'"
                alt="广东金融学院"
                class="h-full w-auto object-contain opacity-60"
                @error="onImgError('school-name-badge')" />
              <div v-else class="h-full px-3 flex items-center justify-center">
                <span class="text-[10px] text-on-surface-variant/50">广东金融学院</span>
              </div>
            </div>
            <div class="h-8 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10">
              <img
                v-if="!getImgError('contest-badge-footer')"
                :src="'/su-shuo-xin-yu/images/team/contest-badge.png'"
                alt="国创赛"
                class="h-full w-auto object-contain opacity-60"
                @error="onImgError('contest-badge-footer')" />
              <div v-else class="h-full px-3 flex items-center justify-center">
                <span class="text-[10px] text-on-surface-variant/50">国创赛</span>
              </div>
            </div>
            <div class="h-8 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10">
              <img
                v-if="!getImgError('contest-full-footer')"
                :src="'/su-shuo-xin-yu/images/team/contest-full.png'"
                alt="竞赛"
                class="h-full w-auto object-contain opacity-60"
                @error="onImgError('contest-full-footer')" />
              <div v-else class="h-full px-3 flex items-center justify-center">
                <span class="text-[10px] text-on-surface-variant/50">竞赛项目</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Links -->
        <div class="flex gap-12">
          <div class="space-y-3">
            <h4 class="font-body text-xs text-primary uppercase tracking-widest">探索</h4>
            <ul class="space-y-2">
              <li><router-link to="/stories" class="text-sm text-on-surface-variant hover:text-primary transition-colors">文化起源</router-link></li>
              <li><router-link to="/zodiac" class="text-sm text-on-surface-variant hover:text-primary transition-colors">作品赏析</router-link></li>
              <li><router-link to="/gallery" class="text-sm text-on-surface-variant hover:text-primary transition-colors">图库欣赏</router-link></li>
            </ul>
          </div>
          <div class="space-y-3">
            <h4 class="font-body text-xs text-primary uppercase tracking-widest">互动</h4>
            <ul class="space-y-2">
              <li><router-link to="/test" class="text-sm text-on-surface-variant hover:text-primary transition-colors">性格测试</router-link></li>
              <li><router-link to="/products" class="text-sm text-on-surface-variant hover:text-primary transition-colors">文创产品</router-link></li>
              <li><router-link to="/feedback" class="text-sm text-on-surface-variant hover:text-primary transition-colors">意见反馈</router-link></li>
            </ul>
          </div>
        </div>

        <!-- QR Code -->
        <div class="flex justify-end">
          <div class="text-center">
            <div class="w-24 h-24 rounded-xl overflow-hidden mx-auto border border-outline-variant/20 shadow-sm bg-white">
              <img
                v-if="!getImgError('qrcode')"
                :src="'/su-shuo-xin-yu/images/team/qrcode.jpg'"
                alt="公众号"
                class="w-full h-full object-cover"
                @error="onImgError('qrcode')" />
              <div v-else class="w-full h-full flex flex-col items-center justify-center bg-primary/5 gap-1">
                <svg class="w-8 h-8 text-primary/30" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 4v1m6 11h2m-6 0h-2.48a2.5 2.5 0 00-4.9 0H4m16 4H4a2 2 0 01-2-2V6a2 2 0 012-2h16a2 2 0 012 2v11a2 2 0 01-2 2z"/>
                </svg>
                <span class="text-[9px] text-on-surface-variant/40">二维码</span>
              </div>
            </div>
            <p class="text-xs text-on-surface-variant/60 mt-2">关注公众号</p>
          </div>
        </div>
      </div>

      <!-- School Badges Row -->
      <div class="flex flex-wrap justify-center items-center gap-8 py-8 border-t border-outline-variant/20">
        <div class="text-center group">
          <div class="h-14 mx-auto mb-1 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10
                      shadow-sm group-hover:shadow-md transition-all duration-300">
            <img
              v-if="!getImgError('school-name-footer')"
              :src="'/su-shuo-xin-yu/images/team/school-name.png'"
              alt="学校名称"
              class="h-full w-auto object-contain opacity-70 group-hover:opacity-100 transition-opacity px-3"
              loading="lazy"
              @error="onImgError('school-name-footer')" />
            <div v-else class="h-full px-4 flex items-center justify-center">
              <span class="text-[10px] text-on-surface-variant/50">学校名称</span>
            </div>
          </div>
        </div>
        <div class="text-center group">
          <div class="h-14 mx-auto mb-1 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10
                      shadow-sm group-hover:shadow-md transition-all duration-300">
            <img
              v-if="!getImgError('school-badge-footer')"
              :src="'/su-shuo-xin-yu/images/team/school-badge.png'"
              alt="校徽"
              class="h-full w-auto object-contain opacity-70 group-hover:opacity-100 transition-opacity px-3"
              loading="lazy"
              @error="onImgError('school-badge-footer')" />
            <div v-else class="h-full px-4 flex items-center justify-center">
              <span class="text-[10px] text-on-surface-variant/50">校徽</span>
            </div>
          </div>
        </div>
        <div class="text-center group">
          <div class="h-14 mx-auto mb-1 rounded-lg overflow-hidden bg-white/50 border border-outline-variant/10
                      shadow-sm group-hover:shadow-md transition-all duration-300">
            <img
              v-if="!getImgError('school-motto-footer')"
              :src="'/su-shuo-xin-yu/images/team/school-motto.png'"
              alt="校训"
              class="h-full w-auto object-contain opacity-70 group-hover:opacity-100 transition-opacity px-3"
              loading="lazy"
              @error="onImgError('school-motto-footer')" />
            <div v-else class="h-full px-4 flex items-center justify-center">
              <span class="text-[10px] text-on-surface-variant/50">校训</span>
            </div>
          </div>
        </div>
      </div>

      <!-- Copyright -->
      <div class="flex flex-col md:flex-row justify-between items-center pt-8 border-t border-outline-variant/20 gap-8">
        <p class="text-xs text-on-surface-variant/60 text-center md:text-left">
          &copy; 2026 塑说心语. 大吴泥塑文化传承与保护.
        </p>
        <div class="flex gap-6">
          <a href="#" class="text-xs text-on-surface-variant/60 hover:text-primary transition-colors">隐私政策</a>
          <a href="#" class="text-xs text-on-surface-variant/60 hover:text-primary transition-colors">使用条款</a>
          <a href="#" class="text-xs text-on-surface-variant/60 hover:text-primary transition-colors">联系我们</a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script setup>

import { ref, reactive } from 'vue'

const logoImgError = ref(false)

// Track image errors for badge images
const imgErrors = reactive({})

function onImgError(key) {
  imgErrors[key] = true
}

function getImgError(key) {
  return !!imgErrors[key]
}
</script>
