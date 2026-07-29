<template>
  <div class="min-h-screen pt-24 pb-20 px-6" v-if="result">
    <div class="max-w-[640px] mx-auto">
      <!-- Hero -->
      <section class="text-center mb-12">
        <span class="inline-block px-4 py-1 rounded-full bg-secondary-container text-on-secondary-container text-xs uppercase tracking-widest mb-4">测评结果</span>
        <h1 class="font-display text-3xl md:text-4xl text-on-surface mb-2">您的生肖守护神：<span class="text-primary">{{ result.zodiacName }}</span></h1>
        <p class="text-on-surface-variant text-sm mb-6">潮汕音：{{ result.dialectName || result.zodiacName }}</p>
        <div class="mx-auto max-w-sm aspect-[4/3] rounded-2xl overflow-hidden shadow-xl bg-surface-container mb-6">
          <img v-if="!imgErr" :src="result.zodiacImage" :alt="result.zodiacName" class="w-full h-full object-cover" @error="imgErr=true"/>
          <div v-else class="w-full h-full flex items-center justify-center text-8xl bg-gradient-to-br from-primary/10 to-secondary/10">{{ emoji }}</div>
        </div>
        <div class="flex flex-wrap justify-center gap-3">
          <span v-for="t in result.personalityTags" :key="t" class="px-4 py-1.5 rounded-full bg-primary/10 text-primary text-sm font-semibold">{{ t }}</span>
        </div>
      </section>

      <!-- Five-dimension radar -->
      <section class="mb-12 bg-white rounded-2xl p-6 border border-outline-variant/20" v-if="result.dimensionScores">
        <h2 class="font-display text-lg text-on-surface text-center mb-6">五维性格剖面图</h2>
        <div class="max-w-[380px] mx-auto">
          <div class="relative" style="height:300px">
            <svg viewBox="0 0 200 200" class="w-full h-full">
              <defs><radialGradient id="rg"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.3"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.05"/></radialGradient></defs>
              <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#d9c2ba" stroke-width="0.5"/>
              <line v-for="(a,i) in axes" :key="'ax'+i" :x1="100" :y1="100" :x2="100+95*Math.cos(a.angle)" :y2="100-95*Math.sin(a.angle)" stroke="#d9c2ba" stroke-width="0.5"/>
              <polygon :points="dataPoints" fill="url(#rg)" stroke="#8c4a2f" stroke-width="2" stroke-linejoin="round"/>
              <circle v-for="(p,i) in dataDots" :key="'d'+i" :cx="p.x" :cy="p.y" r="3.5" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
            </svg>
            <div v-for="(a,i) in axes" :key="'l'+i" class="absolute text-xs font-display text-on-surface-variant pointer-events-none"
                 :style="{left:(50+48*Math.cos(a.angle))+'%',top:(50-48*Math.sin(a.angle))+'%',transform:'translate(-50%,-50%)'}">{{ a.label }}</div>
          </div>
          <div class="grid grid-cols-5 gap-1 mt-2 text-center">
            <div v-for="(a,i) in axes" :key="'s'+i" class="text-xs"><div class="font-semibold text-primary">{{ scoresDisplay[i] }}</div><div class="text-on-surface-variant/50">{{ a.short }}</div></div>
          </div>
        </div>
      </section>

      <!-- Attributes -->
      <section class="mb-12 grid grid-cols-2 md:grid-cols-4 gap-3">
        <div v-for="a in attrs" :key="a.label" class="p-4 rounded-xl bg-white border border-outline-variant/20 text-center">
          <p class="text-xs text-outline tracking-wider mb-1">{{ a.label }}</p>
          <p class="text-sm text-primary font-bold">{{ a.value }}</p>
        </div>
      </section>

      <!-- Description + Details -->
      <section class="mb-12 p-6 rounded-2xl bg-white border border-outline-variant/20">
        <h2 class="font-display text-lg text-on-surface mb-4">性格解析</h2>
        <p class="text-on-surface-variant leading-relaxed mb-6">{{ result.description }}</p>
        <div class="grid md:grid-cols-2 gap-4">
          <div><h3 class="font-semibold text-on-surface mb-2">核心力量</h3><p class="text-sm text-on-surface-variant">{{ result.strengths||'独特魅力' }}</p></div>
          <div><h3 class="font-semibold text-on-surface mb-2">社交共鸣</h3><p class="text-sm text-on-surface-variant">{{ result.relationshipAdvice||'散发独特气质' }}</p></div>
        </div>
      </section>

      <details class="mb-12 p-5 rounded-2xl bg-white border border-outline-variant/20 cursor-pointer group">
        <summary class="font-display text-sm text-on-surface list-none flex justify-between items-center">大吴泥塑 · 八百年传承 <span class="text-primary group-open:rotate-180 transition-transform">▼</span></summary>
        <div class="mt-3 text-sm text-on-surface-variant leading-relaxed space-y-2">
          <p>大吴泥塑起源于南宋（1237年），与天津泥人张、无锡惠山泥人并称中国三大泥塑，2008年列入第一批国家级非遗名录。</p>
          <p>核心工艺：<strong>贴塑技法</strong>——"压泥成片，褶片成衣"，像给泥人穿衣服一样层层叠加成型。</p>
          <p class="text-primary">📍 广东省潮州市潮安区浮洋镇大吴村 · 大吴泥塑博物馆</p>
        </div>
      </details>

      <!-- Actions -->
      <section class="text-center space-y-8">
        <div class="flex flex-col sm:flex-row gap-4 justify-center">
          <button @click="showPoster = true" class="btn-primary text-lg px-12 py-4">📋 生成分享长图</button>
          <button @click="restartTest" class="btn-secondary text-lg px-10 py-4">🔄 重新测评</button>
        </div>
        <div class="p-6 rounded-2xl bg-primary/5 border border-primary/10 max-w-sm mx-auto">
          <p class="text-sm text-on-surface-variant mb-3">加入生肖守护者社群 · 领9折定制券</p>
          <div class="w-28 h-28 mx-auto rounded-xl bg-white border border-outline-variant/20 flex items-center justify-center shadow-sm overflow-hidden">
            <img src="/images/team/qrcode.jpg" alt="社群二维码" class="w-full h-full object-cover" @error="e=>e.target.remove()"/>
          </div>
          <p class="text-xs text-on-surface-variant/50 mt-2">扫码入群 · 每周福利 · 传承人直播</p>
        </div>
        <router-link :to="'/zodiac/'+result.zodiacId" class="inline-block text-sm text-primary hover:underline">查看{{ result.zodiacName }}泥塑详情 →</router-link>
      </section>
    </div>

    <!-- ============ SHARE POSTER OVERLAY ============ -->
    <Teleport to="body">
      <div v-if="showPoster" class="fixed inset-0 z-[200] bg-black/70 flex items-start justify-center overflow-y-auto py-6"
           @click.self="showPoster = false">
        <div class="relative">
          <button @click="showPoster=false" class="sticky top-4 float-right w-10 h-10 rounded-full bg-white/10 hover:bg-white/20 text-white text-lg flex items-center justify-center z-10 ml-auto mr-2">✕</button>
          <!-- Poster (long image, 375x scale) -->
          <div ref="posterRef" class="w-[375px] bg-white shadow-2xl overflow-hidden" style="font-family: 'Noto Serif SC', serif;">
            <!-- Header -->
            <div class="bg-gradient-to-br from-[#3a1c10] via-[#5a3020] to-[#2d1a0e] text-white text-center py-8 px-6">
              <p class="text-xs tracking-[0.3em] uppercase opacity-60 mb-2">塑说心语 · 性格测评</p>
              <h2 class="text-2xl font-bold mb-1">我的生肖守护神</h2>
              <p class="text-5xl font-bold text-primary mt-2 mb-1">{{ result.zodiacName }}</p>
              <p class="text-xs opacity-60">潮汕音：{{ result.dialectName || result.zodiacName }}</p>
            </div>

            <!-- Image -->
            <div class="aspect-[4/3] bg-surface-container overflow-hidden">
              <img v-if="!imgErr" :src="result.zodiacImage" :alt="result.zodiacName" class="w-full h-full object-cover"/>
            </div>

            <!-- Tags -->
            <div class="flex flex-wrap justify-center gap-2 py-5 px-4 border-b border-gray-100">
              <span v-for="t in result.personalityTags" :key="t" class="px-3 py-1 rounded-full bg-primary/10 text-primary text-xs font-semibold">{{ t }}</span>
            </div>

            <!-- Mini Radar -->
            <div class="py-4 px-6 border-b border-gray-100" v-if="result.dimensionScores">
              <p class="text-xs text-gray-400 text-center mb-3 tracking-wider">五 维 性 格 剖 面</p>
              <div class="mx-auto" style="width:220px;height:220px">
                <svg viewBox="0 0 200 200" class="w-full h-full">
                  <defs><radialGradient id="rg2"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.25"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.03"/></radialGradient></defs>
                  <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#e5e2dc" stroke-width="0.5"/>
                  <polygon :points="dataPoints" fill="url(#rg2)" stroke="#8c4a2f" stroke-width="1.5" stroke-linejoin="round"/>
                  <circle v-for="(p,i) in dataDots" :key="i" :cx="p.x" :cy="p.y" r="3" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
                </svg>
              </div>
            </div>

            <!-- Attributes -->
            <div class="grid grid-cols-2 gap-px bg-gray-100 py-1">
              <div class="bg-white text-center py-3"><p class="text-[10px] text-gray-400 tracking-wider mb-0.5">五行属性</p><p class="text-sm text-primary font-bold">{{ result.element||'—' }}</p></div>
              <div class="bg-white text-center py-3"><p class="text-[10px] text-gray-400 tracking-wider mb-0.5">幸运色</p><p class="text-sm text-primary font-bold">{{ result.luckyColor||'—' }}</p></div>
              <div class="bg-white text-center py-3"><p class="text-[10px] text-gray-400 tracking-wider mb-0.5">幸运数字</p><p class="text-sm text-primary font-bold">{{ result.luckyNumber||'—' }}</p></div>
              <div class="bg-white text-center py-3"><p class="text-[10px] text-gray-400 tracking-wider mb-0.5">潮汕音译</p><p class="text-sm text-primary font-bold">{{ result.dialectName||'—' }}</p></div>
            </div>

            <!-- Footer -->
            <div class="bg-gray-50 text-center py-5 px-6 space-y-2">
              <p class="text-[11px] text-gray-400">扫码测测你的生肖守护神</p>
              <div class="w-20 h-20 mx-auto bg-white rounded-lg border border-gray-200 flex items-center justify-center overflow-hidden">
                <img :src="qrCodeUrl" alt="扫码测试" class="w-full h-full object-cover" />
              </div>
              <p class="text-[10px] text-gray-300">塑说心语 · 大吴泥塑文化传承平台</p>
            </div>
          </div>

          <!-- Save button -->
          <div class="text-center mt-3">
            <button @click="savePoster" class="bg-white text-on-surface px-8 py-3 rounded-full font-semibold text-sm shadow-lg hover:shadow-xl transition-all">
              💾 长按保存图片（或截图）
            </button>
          </div>

        </div>
      </div>
    </Teleport>
  </div>
  <div v-else class="min-h-screen pt-28 flex items-center justify-center"><p class="text-on-surface-variant">加载中...</p></div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { recordBehavior } from '@/utils/scroll-reveal'

// Local zodiac detail data (no API needed)
const ZODIAC_DATA = {
  1:  { name:'鼠', emoji:'🐭', element:'水', luckyColor:'棕色、金色', luckyNumber:'2、3', dialect:'Cêh', personalityTags:['机敏灵活','聪明','善于理财'], strengths:'敏锐的直觉判断、超强的适应能力', weaknesses:'有时过于谨慎多疑', relationshipAdvice:'学会信任身边的人，坦诚沟通' },
  2:  { name:'牛', emoji:'🐮', element:'土', luckyColor:'棕色、黄色', luckyNumber:'1、4', dialect:'Ghu', personalityTags:['踏实','可靠','坚毅'], strengths:'超强的毅力和耐力、稳重可靠', weaknesses:'过于固执己见、不善变通', relationshipAdvice:'偶尔放下计划，给生活一点惊喜' },
  3:  { name:'虎', emoji:'🐯', element:'木', luckyColor:'黄色、红色、黑色', luckyNumber:'1、3、4', dialect:'Hôun', personalityTags:['勇猛','自信','霸气担当'], strengths:'果敢的领导力、无所畏惧', weaknesses:'冲动急躁、不够耐心', relationshipAdvice:'学会倾听他人意见' },
  4:  { name:'兔', emoji:'🐰', element:'木', luckyColor:'白色、粉色', luckyNumber:'3、4、6', dialect:'Tòu', personalityTags:['温柔','优雅','细腻'], strengths:'温暖善解人意、细心体贴', weaknesses:'过于敏感、优柔寡断', relationshipAdvice:'勇敢表达自己的想法' },
  5:  { name:'龙', emoji:'🐲', element:'土', luckyColor:'金色、红色', luckyNumber:'1、6、7', dialect:'Lêng', personalityTags:['卓越','领袖','人间清醒'], strengths:'与生俱来的领袖气质、远大抱负', weaknesses:'过于自负、不善于求助', relationshipAdvice:'学会放权，信任伙伴的能力' },
  6:  { name:'蛇', emoji:'🐍', element:'火', luckyColor:'绿色、金色', luckyNumber:'2、8、9', dialect:'Zuê', personalityTags:['智慧','深邃','洞察'], strengths:'超乎常人的洞察力、冷静沉着', weaknesses:'过于神秘、不善表达情感', relationshipAdvice:'主动敞开心扉' },
  7:  { name:'马', emoji:'🐴', element:'火', luckyColor:'棕色、栗色', luckyNumber:'2、3、7', dialect:'Bhê', personalityTags:['自由','奔放','热情'], strengths:'热情洋溢、行动力强', weaknesses:'缺乏耐心、容易半途而废', relationshipAdvice:'学会坚持和专注' },
  8:  { name:'羊', emoji:'🐏', element:'土', luckyColor:'白色、粉色', luckyNumber:'2、7', dialect:'Iên', personalityTags:['温和','善良','优雅'], strengths:'温和善良、善解人意', weaknesses:'过于被动、缺乏主见', relationshipAdvice:'勇敢追求自己想要的生活' },
  9:  { name:'猴', emoji:'🐵', element:'金', luckyColor:'棕色、黄色', luckyNumber:'1、8', dialect:'Gao', personalityTags:['聪明','灵活','好奇'], strengths:'聪明灵活、适应力强', weaknesses:'不够专注、容易转移兴趣', relationshipAdvice:'学会专注和深入' },
  10: { name:'鸡', emoji:'🐔', element:'金', luckyColor:'黄色、红色', luckyNumber:'5、7、8', dialect:'Goi', personalityTags:['自信','精致','守序'], strengths:'勤奋守时、追求完美', weaknesses:'过于严苛、不够灵活', relationshipAdvice:'学会放松标准，享受过程' },
  11: { name:'狗', emoji:'🐶', element:'土', luckyColor:'棕色、黄色', luckyNumber:'3、4、9', dialect:'Gao', personalityTags:['忠诚','可靠','重情义'], strengths:'忠诚可靠、责任心强', weaknesses:'过于保守、不容易接受新事物', relationshipAdvice:'尝试拥抱变化' },
  12: { name:'猪', emoji:'🐷', element:'水', luckyColor:'粉色、白色', luckyNumber:'2、5、8', dialect:'De', personalityTags:['豁达','乐观','随和'], strengths:'知足常乐、真诚待人', weaknesses:'过于安逸、缺乏进取心', relationshipAdvice:'设定目标并坚持执行' },
}

const route = useRoute()
const result = ref(null)
const imgErr = ref(false)
const emoji = ref('🐲')
const showPoster = ref(false)
const posterRef = ref(null)

const axes = [
  { label:'外向/内向', short:'EI', angle:Math.PI/2, key:'ei' },
  { label:'实感/直觉', short:'SN', angle:Math.PI/2+2*Math.PI/5, key:'sn' },
  { label:'理性/感性', short:'TF', angle:Math.PI/2+4*Math.PI/5, key:'tf' },
  { label:'判断/感知', short:'JP', angle:Math.PI/2+6*Math.PI/5, key:'jp' },
  { label:'价值观', short:'VL', angle:Math.PI/2+8*Math.PI/5, key:'vl' },
]

const attrs = computed(() => result.value ? [
  { label:'五行属性', value: result.value.element||'—' },
  { label:'幸运色', value: result.value.luckyColor||'—' },
  { label:'幸运数字', value: result.value.luckyNumber||'—' },
  { label:'潮汕话音译', value: result.value.dialectName||result.value.zodiacName },
] : [])

const scoresDisplay = computed(() => {
  if (!result.value?.dimensionScores) return ['E','S','T','J','—']
  const s = result.value.dimensionScores
  return [
    `${s.ei >= 0 ? 'E' : 'I'} ${Math.abs(s.ei*100).toFixed(0)}%`,
    `${s.sn >= 0 ? 'S' : 'N'} ${Math.abs(s.sn*100).toFixed(0)}%`,
    `${s.tf >= 0 ? 'T' : 'F'} ${Math.abs(s.tf*100).toFixed(0)}%`,
    `${s.jp >= 0 ? 'J' : 'P'} ${Math.abs(s.jp*100).toFixed(0)}%`,
    s.valueLabel||'—'
  ]
})

function ringPoints(r) {
  return axes.map(a => `${100+95*r*Math.cos(a.angle)},${100-95*r*Math.sin(a.angle)}`).join(' ')
}

const dataPoints = computed(() => {
  const s = result.value?.dimensionScores
  if (!s) return '100,100 100,100 100,100 100,100 100,100'
  const vals = [norm(s.ei), norm(s.sn), norm(s.tf), norm(s.jp), valScore(s.valueLabel)]
  return axes.map((a,i) => `${100+82*vals[i]*Math.cos(a.angle)},${100-82*vals[i]*Math.sin(a.angle)}`).join(' ')
})

const qrCodeUrl = computed(() => `https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(window.location.origin + '/test')}`)

const dataDots = computed(() => {
  const s = result.value?.dimensionScores
  if (!s) return []
  const vals = [norm(s.ei), norm(s.sn), norm(s.tf), norm(s.jp), valScore(s.valueLabel)]
  return axes.map((a,i) => ({ x: 100+82*vals[i]*Math.cos(a.angle), y: 100-82*vals[i]*Math.sin(a.angle) }))
})

function norm(v) { return (v + 1) / 2 }
function valScore(label) {
  return label === '成就型' ? 0.88 : label === '安稳型' ? 0.28 : label === '自由型' ? 0.72 : 0.5
}

function shareResult() { recordBehavior('share_click', result.value?.zodiacId, 'zodiac'); showPoster.value = true }
async function savePoster() {
  try {
    await nextTick()
    if (navigator.share) {
      navigator.share({ title:'我的生肖守护神', text:`我的生肖守护神是${result.value?.zodiacName}！`, url: window.location.origin+'/test' }).catch(()=>{})
    } else {
      await navigator.clipboard.writeText(window.location.origin+'/test')
      alert('链接已复制！分享给朋友来测试吧')
    }
  } catch(e) {}
}
function restartTest() { window.location.href = '/test' }

const zodiacEmoji = {'鼠':'🐭','牛':'🐮','虎':'🐯','兔':'🐰','龙':'🐲','蛇':'🐍','马':'🐴','羊':'🐏','猴':'🐵','鸡':'🐔','狗':'🐶','猪':'🐷'}

onMounted(() => {
  // Read from sessionStorage (set by PersonalityTest.vue)
  const raw = sessionStorage.getItem('testResult')
  if (raw) {
    const record = JSON.parse(raw)
    const z = ZODIAC_DATA[record.zodiacId] || ZODIAC_DATA[5]
    const v = record.vector

    result.value = {
      recordId: record.recordId,
      zodiacId: record.zodiacId,
      zodiacName: record.zodiacName || z.name,
      zodiacImage: `/images/zodiac/${record.zodiacName||z.name}.jpg`,
      dialectName: z.dialect,
      element: z.element,
      luckyColor: z.luckyColor,
      luckyNumber: z.luckyNumber,
      personalityTags: z.personalityTags,
      strengths: z.strengths,
      weaknesses: z.weaknesses,
      relationshipAdvice: z.relationshipAdvice,
      description: `${z.name}是十二生肖中${z.luckyColor.split('、')[0]}系守护神。${z.strengths}，${z.weaknesses}。`,
      dimensionScores: {
        ei: v.ei, sn: v.sn, tf: v.tf, jp: v.jp,
        valueLabel: record.valueLabel, valueType: record.valueType,
        eiLabel: v.ei >= 0 ? '外向型' : '内向型',
        snLabel: v.sn >= 0 ? '实感型' : '直觉型',
        tfLabel: v.tf >= 0 ? '理性型' : '感性型',
        jpLabel: v.jp >= 0 ? '判断型' : '感知型',
        eiType: v.ei >= 0 ? 'E' : 'I',
        snType: v.sn >= 0 ? 'S' : 'N',
        tfType: v.tf >= 0 ? 'T' : 'F',
        jpType: v.jp >= 0 ? 'J' : 'P',
      }
    }
    emoji.value = z.emoji
  }
})
</script>
