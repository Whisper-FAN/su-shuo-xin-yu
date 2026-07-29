<template>
  <!-- ======== RESULT PAGE ======== -->
  <div v-if="result" style="min-height:100vh;padding:6rem 1rem 4rem;background:#fcf9f3">
    <div style="max-width:640px;margin:0 auto">

      <!-- Hero -->
      <div style="text-align:center;margin-bottom:2.5rem">
        <span style="display:inline-block;padding:0.25rem 1rem;border-radius:999px;background:rgba(57,103,89,0.1);color:#396759;font-size:0.75rem;letter-spacing:0.2em;text-transform:uppercase;margin-bottom:1rem">测评结果</span>
        <h1 style="font-family:'Noto Serif SC',serif;font-size:1.75rem;color:#1c1c18;margin-bottom:0.5rem">您的生肖守护神：<span style="color:#8c4a2f">{{ result.zodiacName }}</span></h1>
        <p style="color:#53433d;font-size:0.9rem;margin-bottom:1.5rem">潮汕音：{{ result.dialectName || result.zodiacName }}</p>

        <div style="max-width:320px;margin:0 auto 1.5rem;aspect-ratio:4/3;border-radius:1rem;overflow:hidden;background:#f0eee8;box-shadow:0 4px 20px rgba(0,0,0,0.1)">
          <img v-if="!imgErr" :src="base + result.zodiacImage" :alt="result.zodiacName" style="width:100%;height:100%;object-fit:cover" @error="imgErr=true"/>
          <div v-else style="width:100%;height:100%;display:flex;align-items:center;justify-content:center;font-size:5rem;background:linear-gradient(135deg,rgba(140,74,47,0.1),rgba(57,103,89,0.1))">{{ emoji }}</div>
        </div>

        <div style="display:flex;flex-wrap:wrap;justify-content:center;gap:0.5rem">
          <span v-for="t in result.personalityTags" :key="t" style="padding:0.3rem 1rem;border-radius:999px;background:rgba(140,74,47,0.08);color:#8c4a2f;font-size:0.85rem;font-weight:600">{{ t }}</span>
        </div>
      </div>

      <!-- Five-dimension radar -->
      <div v-if="result.dimensionScores" style="background:#fff;border-radius:1rem;padding:1.5rem;margin-bottom:1.5rem;box-shadow:0 2px 8px rgba(0,0,0,0.04)">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1.1rem;color:#1c1c18;text-align:center;margin-bottom:1.5rem">五维性格剖面图</h2>
        <div style="max-width:340px;margin:0 auto">
          <div style="position:relative;height:280px">
            <svg viewBox="0 0 200 200" style="width:100%;height:100%">
              <defs><radialGradient id="rg"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.3"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.05"/></radialGradient></defs>
              <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#d9c2ba" stroke-width="0.5"/>
              <line v-for="(a,i) in axes" :key="'ax'+i" :x1="100" :y1="100" :x2="100+95*Math.cos(a.angle)" :y2="100-95*Math.sin(a.angle)" stroke="#d9c2ba" stroke-width="0.5"/>
              <polygon :points="dataPoints" fill="url(#rg)" stroke="#8c4a2f" stroke-width="2" stroke-linejoin="round"/>
              <circle v-for="(p,i) in dataDots" :key="'d'+i" :cx="p.x" :cy="p.y" r="3.5" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
            </svg>
            <div v-for="(a,i) in axes" :key="'l'+i" style="position:absolute;font-size:0.7rem;font-family:'Noto Serif SC',serif;color:#53433d;pointer-events:none;transform:translate(-50%,-50%)"
                 :style="{left:(50+48*Math.cos(a.angle))+'%',top:(50-48*Math.sin(a.angle))+'%'}">{{ a.label }}</div>
          </div>
          <div style="display:grid;grid-template-columns:repeat(5,1fr);gap:0.25rem;margin-top:0.5rem;text-align:center">
            <div v-for="(a,i) in axes" :key="'s'+i" style="font-size:0.75rem">
              <div style="font-weight:600;color:#8c4a2f">{{ scoresDisplay[i] }}</div>
              <div style="color:#86736c;font-size:0.65rem">{{ a.short }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- Attributes -->
      <div style="display:grid;grid-template-columns:1fr 1fr;gap:0.75rem;margin-bottom:1.5rem">
        <div v-for="a in attrs" :key="a.label" style="background:#fff;border-radius:0.75rem;padding:1rem;text-align:center;box-shadow:0 2px 6px rgba(0,0,0,0.03)">
          <p style="font-size:0.7rem;color:#86736c;letter-spacing:0.1em;margin-bottom:0.25rem">{{ a.label }}</p>
          <p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0">{{ a.value }}</p>
        </div>
      </div>

      <!-- Description -->
      <div style="background:#fff;border-radius:1rem;padding:1.5rem;margin-bottom:1.5rem;box-shadow:0 2px 6px rgba(0,0,0,0.03)">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1.1rem;color:#1c1c18;margin-bottom:1rem">性格解析</h2>
        <p style="color:#53433d;line-height:1.8;margin-bottom:1rem">{{ result.description }}</p>
        <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem;font-size:0.85rem">
          <div><h3 style="font-weight:600;color:#1c1c18;margin-bottom:0.5rem">核心力量</h3><p style="color:#53433d;margin:0">{{ result.strengths||'独特魅力' }}</p></div>
          <div><h3 style="font-weight:600;color:#1c1c18;margin-bottom:0.5rem">社交共鸣</h3><p style="color:#53433d;margin:0">{{ result.relationshipAdvice||'散发独特气质' }}</p></div>
        </div>
      </div>

      <!-- 八百年传承 -->
      <details style="background:#fff;border-radius:1rem;padding:1.25rem;margin-bottom:1.5rem;box-shadow:0 2px 6px rgba(0,0,0,0.03);cursor:pointer">
        <summary style="font-family:'Noto Serif SC',serif;font-size:0.95rem;color:#1c1c18;display:flex;justify-content:space-between;align-items:center;list-style:none">大吴泥塑 · 八百年传承 <span style="color:#8c4a2f">▼</span></summary>
        <div style="margin-top:0.75rem;font-size:0.85rem;color:#53433d;line-height:1.8">
          <p style="margin-bottom:0.5rem">大吴泥塑起源于南宋（1237年），与天津泥人张、无锡惠山泥人并称中国三大泥塑，2008年列入第一批国家级非遗名录。</p>
          <p style="margin-bottom:0.5rem">核心工艺：<strong>贴塑技法</strong>——压泥成片，褶片成衣，像给泥人穿衣服一样层层叠加成型。</p>
          <p style="color:#8c4a2f">📍 广东省潮州市潮安区浮洋镇大吴村 · 大吴泥塑博物馆</p>
        </div>
      </details>

      <!-- Actions -->
      <div style="text-align:center;margin-bottom:1.5rem">
        <div style="display:flex;flex-wrap:wrap;gap:1rem;justify-content:center;margin-bottom:2rem">
          <button @click="showPoster = true" style="background:#8c4a2f;color:#fff;padding:0.75rem 2.5rem;border-radius:999px;font-weight:600;font-size:1.05rem;border:none;cursor:pointer;box-shadow:0 8px 20px rgba(140,74,47,0.2)">📋 生成分享长图</button>
          <button @click="$router.push('/test')" style="background:transparent;color:#53433d;padding:0.75rem 2.5rem;border-radius:999px;border:1px solid #86736c;font-weight:600;font-size:1.05rem;cursor:pointer">🔄 重新测评</button>
        </div>

        <div style="background:rgba(140,74,47,0.03);border-radius:1rem;padding:1.5rem;border:1px solid rgba(140,74,47,0.08);max-width:20rem;margin:0 auto 1.5rem">
          <p style="font-size:0.9rem;color:#53433d;margin-bottom:1rem">加入生肖守护者社群 · 领9折定制券</p>
          <div style="width:6rem;height:6rem;margin:0 auto 0.75rem;border-radius:0.75rem;overflow:hidden;border:1px solid rgba(0,0,0,0.06)">
            <img :src="base + '/images/team/qrcode.jpg'" alt="社群二维码" style="width:100%;height:100%;object-fit:cover" @error="e=>e.target.remove()"/>
          </div>
          <p style="font-size:0.75rem;color:rgba(83,67,61,0.5)">扫码入群 · 每周福利 · 传承人直播</p>
        </div>

        <router-link :to="'/zodiac/'+result.zodiacId" style="font-size:0.85rem;color:#8c4a2f;text-decoration:none">查看{{ result.zodiacName }}泥塑详情 →</router-link>
      </div>
    </div>

    <!-- ======== POSTER OVERLAY ======== -->
    <Teleport to="body">
      <div v-if="showPoster" style="position:fixed;inset:0;z-index:200;background:rgba(0,0,0,0.75);display:flex;align-items:flex-start;justify-content:center;overflow-y:auto;padding:1.5rem 0" @click.self="showPoster=false">
        <div style="position:relative">
          <button @click="showPoster=false" style="position:sticky;top:0.5rem;float:right;width:2.5rem;height:2.5rem;border-radius:50%;background:rgba(255,255,255,0.15);color:#fff;font-size:1.2rem;border:none;cursor:pointer;display:flex;align-items:center;justify-content:center;z-index:10;margin-right:0.5rem">✕</button>

          <!-- POSTER -->
          <div ref="posterRef" style="width:375px;background:#fff;box-shadow:0 20px 60px rgba(0,0,0,0.3);overflow:hidden;font-family:'Noto Serif SC',serif">

            <!-- Header -->
            <div style="background:linear-gradient(135deg,#3a1c10,#5a3020,#2d1a0e);color:#fff;text-align:center;padding:2rem 1.5rem">
              <p style="font-size:0.7rem;letter-spacing:0.3em;opacity:0.5;margin-bottom:0.5rem;text-transform:uppercase">塑说心语 · 性格测评</p>
              <h2 style="font-size:1.25rem;margin-bottom:0.5rem;font-weight:700">我的生肖守护神</h2>
              <p style="font-size:2.5rem;font-weight:700;color:#8c4a2f;margin:0.5rem 0;text-shadow:0 2px 10px rgba(140,74,47,0.3)">{{ result.zodiacName }}</p>
              <p style="font-size:0.75rem;opacity:0.5">潮汕音：{{ result.dialectName || result.zodiacName }}</p>
            </div>

            <!-- Zodiac image -->
            <div style="aspect-ratio:4/3;background:#f0eee8;overflow:hidden">
              <img v-if="!imgErr" :src="base + result.zodiacImage" :alt="result.zodiacName" style="width:100%;height:100%;object-fit:cover"/>
            </div>

            <!-- Tags -->
            <div style="display:flex;flex-wrap:wrap;justify-content:center;gap:0.5rem;padding:1.25rem 1rem;border-bottom:1px solid #f0eee8">
              <span v-for="t in result.personalityTags" :key="t" style="padding:0.3rem 0.75rem;border-radius:999px;background:rgba(140,74,47,0.08);color:#8c4a2f;font-size:0.75rem;font-weight:600">{{ t }}</span>
            </div>

            <!-- Mini radar -->
            <div v-if="result.dimensionScores" style="padding:1rem 1.5rem;border-bottom:1px solid #f0eee8">
              <p style="font-size:0.7rem;color:#86736c;text-align:center;letter-spacing:0.2em;margin-bottom:0.75rem">五 维 性 格 剖 面</p>
              <div style="width:200px;height:200px;margin:0 auto">
                <svg viewBox="0 0 200 200" style="width:100%;height:100%">
                  <defs><radialGradient id="rg2"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.25"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.03"/></radialGradient></defs>
                  <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#e5e2dc" stroke-width="0.5"/>
                  <polygon :points="dataPoints" fill="url(#rg2)" stroke="#8c4a2f" stroke-width="1.5" stroke-linejoin="round"/>
                  <circle v-for="(p,i) in dataDots" :key="i" :cx="p.x" :cy="p.y" r="3" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
                </svg>
              </div>
            </div>

            <!-- Attributes 2x2 -->
            <div style="display:grid;grid-template-columns:1fr 1fr;border-bottom:1px solid #f0eee8">
              <div style="text-align:center;padding:0.75rem;border-right:1px solid #f0eee8;border-bottom:1px solid #f0eee8"><p style="font-size:0.65rem;color:#86736c;margin-bottom:0.25rem">五行属性</p><p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0">{{ result.element||'—' }}</p></div>
              <div style="text-align:center;padding:0.75rem;border-bottom:1px solid #f0eee8"><p style="font-size:0.65rem;color:#86736c;margin-bottom:0.25rem">幸运色</p><p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0">{{ result.luckyColor||'—' }}</p></div>
              <div style="text-align:center;padding:0.75rem;border-right:1px solid #f0eee8"><p style="font-size:0.65rem;color:#86736c;margin-bottom:0.25rem">幸运数字</p><p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0">{{ result.luckyNumber||'—' }}</p></div>
              <div style="text-align:center;padding:0.75rem"><p style="font-size:0.65rem;color:#86736c;margin-bottom:0.25rem">潮汕音译</p><p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0">{{ result.dialectName||'—' }}</p></div>
            </div>

            <!-- Footer -->
            <div style="background:#fafaf8;text-align:center;padding:1.25rem 1.5rem">
              <p style="font-size:0.7rem;color:#86736c;margin-bottom:0.75rem">扫码测测你的生肖守护神</p>
              <div style="width:5rem;height:5rem;margin:0 auto 0.5rem;background:#fff;border-radius:0.5rem;border:1px solid #e5e2dc;overflow:hidden">
                <img :src="qrCodeUrl" alt="扫码测试" style="width:100%;height:100%;object-fit:cover" />
              </div>
              <p style="font-size:0.6rem;color:#c4bfb8;margin:0">塑说心语 · 大吴泥塑文化传承平台</p>
            </div>
          </div>

          <!-- Download button -->
          <div style="text-align:center;margin-top:1rem">
            <button @click="downloadPoster" style="background:#fff;color:#1c1c18;padding:0.75rem 2rem;border-radius:999px;font-weight:600;font-size:0.9rem;border:none;cursor:pointer;box-shadow:0 4px 16px rgba(0,0,0,0.15)">💾 保存长图到电脑</button>
            <p style="font-size:0.7rem;color:rgba(255,255,255,0.5);margin-top:0.5rem">手机上可截图保存</p>
          </div>
        </div>
      </div>
    </Teleport>
  </div>

  <div v-else style="min-height:100vh;display:flex;align-items:center;justify-content:center;background:#fcf9f3">
    <p style="color:#53433d">加载中...</p>
  </div>
</template>

<script setup>
import { base } from '@/utils/base'
import { ref, computed, onMounted, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { recordBehavior } from '@/utils/scroll-reveal'

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

function ringPoints(r) { return axes.map(a => `${100+95*r*Math.cos(a.angle)},${100-95*r*Math.sin(a.angle)}`).join(' ') }

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
function valScore(label) { return label === '成就型' ? 0.88 : label === '安稳型' ? 0.28 : label === '自由型' ? 0.72 : 0.5 }

function downloadPoster() {
  const w = window.open('', '_blank', 'width=420,height=900')
  if (w) {
    const root = window.location.origin + base
    const posterEl = posterRef.value
    if (!posterEl) return
    const clone = posterEl.cloneNode(true)
    // Fix all relative image src to absolute
    clone.querySelectorAll('img').forEach(img => {
      const src = img.getAttribute('src')
      if (src && src.startsWith('/')) img.setAttribute('src', root + src)
    })
    w.document.write('<!DOCTYPE html><html><head><meta charset="UTF-8"><title>我的生肖守护神 - 分享海报</title>'
      + '<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+SC:wght@400;700&display=swap" rel="stylesheet">'
      + '<style>body{margin:0;display:flex;justify-content:center;background:#e5e2dc;padding:1rem}'
      + '@media print{body{background:#fff;padding:0}}</style></head>'
      + '<body>' + clone.outerHTML + '</body></html>')
    w.document.close()
  }
}

onMounted(() => {
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
      description: `${z.name}是十二生肖中${z.luckyColor.split('、')[0]}系守护神。${z.strengths}。但${z.weaknesses}。`,
      dimensionScores: {
        ei: v.ei, sn: v.sn, tf: v.tf, jp: v.jp,
        valueLabel: record.valueLabel, valueType: record.valueType,
      }
    }
    emoji.value = z.emoji
  }
})
</script>
