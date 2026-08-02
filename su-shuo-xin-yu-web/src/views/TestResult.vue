<template>
  <div v-if="result" style="min-height:100vh;background:#fcf9f3;padding:6rem 1rem 3rem">
    <div style="max-width:600px;margin:0 auto">

      <!-- ===== HERO ===== -->
      <div style="text-align:center;margin-bottom:1.5rem">
        <span style="display:inline-block;padding:0.2rem 0.8rem;border-radius:999px;background:rgba(57,103,89,0.1);color:#396759;font-size:0.7rem;letter-spacing:0.2em">测评结果 · 塑说心语</span>
      </div>

      <div style="max-width:360px;margin:0 auto 1.5rem;aspect-ratio:4/3;border-radius:1rem;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.1)">
        <img v-if="!imgErr" :src="result.zodiacImage" :alt="result.zodiacName" style="width:100%;height:100%;object-fit:cover" @error="imgErr=true"/>
        <div v-else style="width:100%;height:100%;display:flex;align-items:center;justify-content:center;font-size:5rem;background:linear-gradient(135deg,rgba(140,74,47,0.1),rgba(57,103,89,0.1))">{{ emoji }}</div>
      </div>

      <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;text-align:center;color:#1c1c18;margin-bottom:0.25rem">您的生肖守护神：<span style="color:#8c4a2f">{{ result.zodiacName }}</span></h1>
      <p style="text-align:center;color:#86736c;font-size:0.8rem;margin-bottom:1rem">潮汕音：{{ result.dialectName || result.zodiacName }}</p>

      <div style="display:flex;flex-wrap:wrap;justify-content:center;gap:0.5rem;margin-bottom:2rem">
        <span v-for="t in result.personalityTags" :key="t" style="padding:0.35rem 1rem;border-radius:999px;background:rgba(140,74,47,0.08);color:#8c4a2f;font-size:0.85rem;font-weight:600">{{ t }}</span>
      </div>

      <!-- ===== 工艺解析 ===== -->
      <div style="background:#fff;border-radius:1rem;padding:1.25rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,0.03);font-size:0.85rem;color:#53433d;line-height:1.8">
        <p style="margin:0">{{ craftDesc }}</p>
      </div>

      <!-- ===== 五维雷达图 ===== -->
      <div v-if="result.dimensionScores" style="background:#fff;border-radius:1rem;padding:1.25rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,0.03)">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1rem;color:#1c1c18;text-align:center;margin-bottom:1rem">五维性格剖面图</h2>
        <div style="max-width:300px;margin:0 auto">
          <div style="position:relative;height:250px">
            <svg viewBox="0 0 200 200" style="width:100%;height:100%">
              <defs><radialGradient id="rg"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.3"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.05"/></radialGradient></defs>
              <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#d9c2ba" stroke-width="0.5"/>
              <line v-for="(a,i) in axes" :key="'ax'+i" :x1="100" :y1="100" :x2="100+90*Math.cos(a.angle)" :y2="100-90*Math.sin(a.angle)" stroke="#d9c2ba" stroke-width="0.5"/>
              <polygon :points="dataPoints" fill="url(#rg)" stroke="#8c4a2f" stroke-width="2" stroke-linejoin="round"/>
              <circle v-for="(p,i) in dataDots" :key="'d'+i" :cx="p.x" :cy="p.y" r="3.5" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
            </svg>
            <div v-for="(a,i) in axes" :key="'l'+i" style="position:absolute;font-size:0.65rem;font-family:'Noto Serif SC',serif;color:#53433d;transform:translate(-50%,-50%);pointer-events:none"
                 :style="{left:(50+45*Math.cos(a.angle))+'%',top:(50-45*Math.sin(a.angle))+'%'}">{{ a.label }}</div>
          </div>
          <div style="display:grid;grid-template-columns:repeat(5,1fr);gap:0.25rem;margin-top:0.25rem;text-align:center">
            <div v-for="(a,i) in axes" :key="'s'+i" style="font-size:0.7rem"><div style="font-weight:600;color:#8c4a2f">{{ scoresDisplay[i] }}</div><div style="color:#86736c;font-size:0.6rem">{{ a.short }}</div></div>
          </div>
        </div>
      </div>

      <!-- ===== 性格解析 ===== -->
      <div style="background:#fff;border-radius:1rem;padding:1.25rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,0.03)">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1rem;color:#1c1c18;margin-bottom:1rem">性格解析</h2>
        <p style="font-size:0.85rem;color:#53433d;line-height:1.8;margin-bottom:1rem">{{ result.description }}</p>

        <div style="display:grid;grid-template-columns:1fr 1fr;gap:0.75rem;margin-bottom:1rem">
          <div style="background:rgba(140,74,47,0.03);border-radius:0.75rem;padding:0.75rem">
            <h3 style="font-size:0.8rem;color:#8c4a2f;font-weight:600;margin-bottom:0.5rem">核心力量</h3>
            <p style="font-size:0.8rem;color:#53433d;margin:0">{{ result.strengths||'独特魅力' }}</p>
          </div>
          <div style="background:rgba(57,103,89,0.03);border-radius:0.75rem;padding:0.75rem">
            <h3 style="font-size:0.8rem;color:#396759;font-weight:600;margin-bottom:0.5rem">社交共鸣</h3>
            <p style="font-size:0.8rem;color:#53433d;margin:0">{{ result.relationshipAdvice||'散发独特气质' }}</p>
          </div>
        </div>

        <div style="display:grid;grid-template-columns:1fr 1fr;gap:0.5rem;margin-bottom:1rem">
          <div v-for="a in attrs" :key="a.label" style="background:rgba(140,74,47,0.03);border-radius:0.5rem;padding:0.5rem;text-align:center">
            <p style="font-size:0.65rem;color:#86736c;margin-bottom:0.2rem;letter-spacing:0.1em">{{ a.label }}</p>
            <p style="font-size:0.85rem;color:#8c4a2f;font-weight:700;margin:0">{{ a.value }}</p>
          </div>
        </div>

        <details style="font-size:0.85rem;color:#53433d;line-height:1.8">
          <summary style="cursor:pointer;color:#8c4a2f;font-weight:600">大吴泥塑 · 八百年传承</summary>
          <p style="margin-top:0.5rem">大吴泥塑起源于南宋（1237年），与天津泥人张、无锡惠山泥人并称中国三大泥塑，2008年列入第一批国家级非遗。</p>
          <p>核心工艺：<strong>贴塑技法</strong>——压泥成片，褶片成衣，像给泥人穿衣服一样层层叠加成型。</p>
          <p>📍 广东省潮州市潮安区浮洋镇大吴村 · 大吴泥塑博物馆</p>
        </details>
      </div>

      <!-- ===== 你的守护神文创 ===== -->
      <div style="background:#fff;border-radius:1rem;padding:1.25rem;margin-bottom:1rem;box-shadow:0 2px 8px rgba(0,0,0,0.03)">
        <details open>
          <summary style="cursor:pointer;font-family:'Noto Serif SC',serif;font-size:1rem;color:#1c1c18;font-weight:600">你的守护神文创</summary>
          <div style="margin-top:0.75rem;display:grid;grid-template-columns:1fr 1fr;gap:0.5rem">
            <div v-for="p in relatedProducts" :key="p.id" @click="openTaobao(p.taobao)" style="cursor:pointer;background:rgba(140,74,47,0.03);border-radius:0.75rem;overflow:hidden;transition:all 0.2s">
              <div style="aspect-ratio:1;overflow:hidden;background:#f0eee8">
                <img :src="p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover" loading="lazy"/>
              </div>
              <div style="padding:0.5rem;text-align:center">
                <div style="font-size:0.75rem;color:#1c1c18;margin-bottom:0.15rem;line-height:1.3">{{ p.name }}</div>
                <div style="font-size:0.8rem;color:#8c4a2f;font-weight:600">&yen;{{ p.price }}</div>
                <div style="font-size:0.6rem;color:#86736c;margin-top:0.15rem">去淘宝购买 →</div>
              </div>
            </div>
          </div>
        </details>
      </div>

      <!-- ===== Actions ===== -->
      <div style="text-align:center;margin-bottom:1.5rem">
        <div style="display:flex;flex-wrap:wrap;gap:0.75rem;justify-content:center;margin-bottom:2rem">
          <button @click="showPoster=true" style="background:#8c4a2f;color:#fff;padding:0.65rem 2rem;border-radius:999px;font-weight:600;font-size:1rem;border:none;cursor:pointer;box-shadow:0 6px 16px rgba(140,74,47,0.2)">📋 生成分享长图</button>
          <button @click="$router.push('/test')" style="background:transparent;color:#53433d;padding:0.65rem 2rem;border-radius:999px;border:1px solid #86736c;font-weight:600;font-size:1rem;cursor:pointer">🔄 重新测评</button>
        </div>

        <div style="background:rgba(140,74,47,0.03);border-radius:1rem;padding:1.25rem;border:1px solid rgba(140,74,47,0.06);max-width:18rem;margin:0 auto 1rem">
          <p style="font-size:0.85rem;color:#53433d;margin-bottom:0.75rem">加入生肖守护者社群 · 领9折定制券</p>
          <div style="width:5rem;height:5rem;margin:0 auto 0.5rem;border-radius:0.75rem;overflow:hidden;border:1px solid rgba(0,0,0,0.05)">
            <img :src="'/images/team/qrcode.jpg'" alt="公众号二维码" style="width:100%;height:100%;object-fit:cover" @error="e=>e.target.remove()"/>
          </div>
          <p style="font-size:0.7rem;color:rgba(83,67,61,0.5)">扫码入群 · 每周福利 · 传承人直播</p>
        </div>

        <router-link :to="'/zodiac/'+result.zodiacId" style="font-size:0.85rem;color:#8c4a2f;text-decoration:none">查看{{ result.zodiacName }}泥塑详情 →</router-link>
      </div>

    </div>

    <!-- ===== POSTER ===== -->
    <Teleport to="body">
      <div v-if="showPoster" style="position:fixed;inset:0;z-index:200;background:rgba(0,0,0,0.8);display:flex;align-items:flex-start;justify-content:center;overflow-y:auto;padding:1rem 0" @click.self="showPoster=false">
        <div style="position:relative;width:375px">
          <button @click="showPoster=false" style="position:sticky;top:0.25rem;left:calc(100% - 2rem);width:2rem;height:2rem;border-radius:50%;background:rgba(255,255,255,0.2);color:#fff;border:none;cursor:pointer;display:flex;align-items:center;justify-content:center;z-index:10">✕</button>

          <div ref="posterRef" style="background:#fff;font-family:'Noto Serif SC',serif;overflow:hidden;box-shadow:0 20px 60px rgba(0,0,0,0.3)">
            <div style="background:linear-gradient(135deg,#3a1c10,#5a3020,#2d1a0e);color:#fff;text-align:center;padding:1.5rem 1.25rem">
              <p style="font-size:0.65rem;letter-spacing:0.3em;opacity:0.4;margin:0 0 0.5rem;text-transform:uppercase">塑说心语 · 性格测评</p>
              <h2 style="font-size:1.1rem;margin:0 0 0.25rem;font-weight:700">我的生肖守护神</h2>
              <p style="font-size:2.5rem;font-weight:700;color:#8c4a2f;margin:0.25rem 0;text-shadow:0 2px 10px rgba(140,74,47,0.3)">{{ result.zodiacName }}</p>
              <p style="font-size:0.7rem;opacity:0.4;margin:0">潮汕音：{{ result.dialectName || result.zodiacName }}</p>
            </div>
            <div style="aspect-ratio:4/3;overflow:hidden">
              <img v-if="!imgErr" :src="result.zodiacImage" :alt="result.zodiacName" style="width:100%;height:100%;object-fit:cover"/>
            </div>
            <div style="display:flex;flex-wrap:wrap;justify-content:center;gap:0.4rem;padding:1rem;border-bottom:1px solid #f0eee8">
              <span v-for="t in result.personalityTags" :key="t" style="padding:0.25rem 0.75rem;border-radius:999px;background:rgba(140,74,47,0.08);color:#8c4a2f;font-size:0.7rem;font-weight:600">{{ t }}</span>
            </div>
            <div v-if="result.dimensionScores" style="padding:0.75rem 1rem;border-bottom:1px solid #f0eee8">
              <p style="font-size:0.65rem;color:#86736c;text-align:center;letter-spacing:0.2em;margin-bottom:0.5rem">五维性格剖面</p>
              <div style="width:180px;height:180px;margin:0 auto">
                <svg viewBox="0 0 200 200" style="width:100%;height:100%">
                  <defs><radialGradient id="rg2"><stop offset="0%" stop-color="#8c4a2f" stop-opacity="0.25"/><stop offset="100%" stop-color="#8c4a2f" stop-opacity="0.03"/></radialGradient></defs>
                  <polygon v-for="r in [0.25,0.5,0.75,1]" :key="r" :points="ringPoints(r)" fill="none" stroke="#e5e2dc" stroke-width="0.5"/>
                  <polygon :points="dataPoints" fill="url(#rg2)" stroke="#8c4a2f" stroke-width="1.5" stroke-linejoin="round"/>
                  <circle v-for="(p,i) in dataDots" :key="i" :cx="p.x" :cy="p.y" r="3.5" fill="#8c4a2f" stroke="white" stroke-width="1.5"/>
                </svg>
              </div>
            </div>
            <div style="display:grid;grid-template-columns:1fr 1fr;border-bottom:1px solid #f0eee8">
              <div v-for="a in attrs" :key="a.label" style="text-align:center;padding:0.6rem;border-right:1px solid #f0eee8;border-bottom:1px solid #f0eee8">
                <p style="font-size:0.6rem;color:#86736c;margin:0 0 0.15rem">{{ a.label }}</p>
                <p style="font-size:0.8rem;color:#8c4a2f;font-weight:700;margin:0">{{ a.value }}</p>
              </div>
            </div>
            <div style="background:#fafaf8;text-align:center;padding:1rem">
              <p style="font-size:0.65rem;color:#86736c;margin:0 0 0.5rem">扫码测测你的生肖守护神</p>
              <div style="width:4rem;height:4rem;margin:0 auto 0.25rem;background:#fff;border-radius:0.5rem;border:1px solid #e5e2dc;overflow:hidden">
                <img :src="qrCodeUrl" alt="扫码测试" style="width:100%;height:100%;object-fit:cover"/>
              </div>
              <p style="font-size:0.55rem;color:#c4bfb8;margin:0">塑说心语 · 大吴泥塑文化传承平台</p>
            </div>
          </div>
          <div style="text-align:center;margin-top:0.75rem">
            <button @click="downloadPoster" style="background:#fff;color:#1c1c18;padding:0.65rem 2rem;border-radius:999px;font-weight:600;font-size:0.9rem;border:none;cursor:pointer;box-shadow:0 4px 16px rgba(0,0,0,0.15)">💾 保存长图 / 截图分享</button>
          </div>
        </div>
      </div>
    </Teleport>
  </div>
  <div v-else style="min-height:100vh;display:flex;align-items:center;justify-content:center;background:#fcf9f3"><p style="color:#53433d">加载中...</p></div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const ZODIAC_DATA = {
  1:{name:'鼠',emoji:'🐭',element:'水',luckyColor:'棕色、金色',luckyNumber:'2、3',dialect:'Cêh',personalityTags:['机敏灵活','聪明','善于理财'],strengths:'敏锐的直觉判断、超强的适应能力、出色的社交手腕',weaknesses:'有时过于谨慎多疑、容易想太多',relationshipAdvice:'学会信任身边的人，坦诚沟通是建立深度关系的基石',craft:'生肖鼠泥塑以贴塑技法层层叠加毛发纹理，以捏塑定其灵巧身形，以彩绘赋予机敏神采'},
  2:{name:'牛',emoji:'🐮',element:'土',luckyColor:'棕色、黄色',luckyNumber:'1、4',dialect:'Ghu',personalityTags:['踏实','可靠','坚毅'],strengths:'超强的毅力和耐力、稳重可靠、坚定不移的原则',weaknesses:'过于固执己见、不善变通',relationshipAdvice:'偶尔放下计划，给生活一点惊喜的空间',craft:'生肖牛泥塑以贴塑技法堆叠厚重身躯，捏塑勾勒勤恳神态，彩绘增添朴实温暖的色调'},
  3:{name:'虎',emoji:'🐯',element:'木',luckyColor:'黄色、红色、黑色',luckyNumber:'1、3、4',dialect:'Hôun',personalityTags:['勇猛','自信','霸气担当'],strengths:'果敢的领导力、无所畏惧、气场强大',weaknesses:'冲动急躁、不够耐心',relationshipAdvice:'学会倾听，温柔也是力量',craft:'生肖虎泥塑以贴塑技法层层叠加虎纹毛发，明黄黑色条纹需反复贴片七八层方显威猛'},
  4:{name:'兔',emoji:'🐰',element:'木',luckyColor:'白色、粉色',luckyNumber:'3、4、6',dialect:'Tòu',personalityTags:['温柔','优雅','细腻'],strengths:'温暖善解人意、细心体贴、审美出众',weaknesses:'过于敏感、优柔寡断',relationshipAdvice:'勇敢表达自己的想法，你的声音值得被听见',craft:'生肖兔泥塑以贴塑技法塑造蓬松绒毛，粉白配色经彩绘多次晕染方显柔和温润'},
  5:{name:'龙',emoji:'🐲',element:'土',luckyColor:'金色、红色',luckyNumber:'1、6、7',dialect:'Lêng',personalityTags:['卓越','领袖','人间清醒'],strengths:'与生俱来的领袖气质、远大抱负、冷静沉稳',weaknesses:'过于自负、不善于求助',relationshipAdvice:'学会放权，信任伙伴的能力',craft:'生肖龙泥塑以贴塑技法堆叠金鳞甲片，S型龙身需贴片上百片，红色鳞片点缀彰显尊贵之气'},
  6:{name:'蛇',emoji:'🐍',element:'火',luckyColor:'绿色、金色',luckyNumber:'2、8、9',dialect:'Zuê',personalityTags:['智慧','深邃','洞察'],strengths:'超乎常人的洞察力、冷静沉着、深思熟虑',weaknesses:'过于神秘、不善表达情感',relationshipAdvice:'主动敞开心扉，不是所有人都能读懂你的沉默',craft:'生肖蛇泥塑以贴塑技法叠出蜿蜒曲线，翠绿蛇身配金色纹饰，细腻鳞片刻画展现灵动之美'},
  7:{name:'马',emoji:'🐴',element:'火',luckyColor:'棕色、栗色',luckyNumber:'2、3、7',dialect:'Bhê',personalityTags:['自由','奔放','热情'],strengths:'热情洋溢、行动力强、感染力十足',weaknesses:'缺乏耐心、容易半途而废',relationshipAdvice:'学会坚持和专注，自由不等于无序',craft:'生肖马泥塑以贴塑技法塑造奔腾姿态，鬃毛经多层贴片飞扬生风，栗棕配色彰显活力'},
  8:{name:'羊',emoji:'🐏',element:'土',luckyColor:'白色、粉色',luckyNumber:'2、7',dialect:'Iên',personalityTags:['温和','善良','优雅'],strengths:'温和善良、善解人意、审美出众',weaknesses:'过于被动、缺乏主见',relationshipAdvice:'勇敢追求自己想要的生活',craft:'生肖羊泥塑以贴塑技法层层堆叠柔软卷毛，乳白身躯搭配粉彩晕染，神态温驯安详'},
  9:{name:'猴',emoji:'🐵',element:'金',luckyColor:'棕色、黄色',luckyNumber:'1、8',dialect:'Gao',personalityTags:['聪明','灵活','好奇'],strengths:'聪明灵活、适应力强、创意无限',weaknesses:'不够专注、容易转移兴趣',relationshipAdvice:'学会专注和深入，深度比广度更能成就大事',craft:'生肖猴泥塑以贴塑技法表现灵活姿态，捏塑精准刻画淘气表情'},
  10:{name:'鸡',emoji:'🐔',element:'金',luckyColor:'黄色、红色',luckyNumber:'5、7、8',dialect:'Goi',personalityTags:['自信','精致','守序'],strengths:'勤奋守时、追求完美、一丝不苟',weaknesses:'过于严苛、不够灵活',relationshipAdvice:'学会放松标准，享受过程比完美结果更重要',craft:'生肖鸡泥塑以贴塑技法层层叠加羽毛，金身红冠昂首挺胸，捏塑精准勾勒精气神'},
  11:{name:'狗',emoji:'🐶',element:'土',luckyColor:'棕色、黄色',luckyNumber:'3、4、9',dialect:'Gao',personalityTags:['忠诚','可靠','重情义'],strengths:'忠诚可靠、责任心强、值得信赖',weaknesses:'过于保守、不容易接受新事物',relationshipAdvice:'尝试拥抱变化，世界不会因为你的谨慎而停下脚步',craft:'生肖狗泥塑以贴塑技法塑造憨厚体态，深棕斑点层层贴片方显自然'},
  12:{name:'猪',emoji:'🐷',element:'水',luckyColor:'粉色、白色',luckyNumber:'2、5、8',dialect:'De',personalityTags:['豁达','乐观','随和'],strengths:'知足常乐、真诚待人、人缘极佳',weaknesses:'过于安逸、缺乏进取心',relationshipAdvice:'设定目标并坚持执行',craft:'生肖猪泥塑以贴塑技法堆叠圆润体态，粉白配色经彩绘多次罩染方显憨厚可爱'},
}

const route = useRoute()
const result = ref(null)
const imgErr = ref(false)
const emoji = ref('🐲')
const showPoster = ref(false)
const posterRef = ref(null)
const axes = [
  {label:'外向/内向',short:'EI',angle:Math.PI/2,key:'ei'},
  {label:'实感/直觉',short:'SN',angle:Math.PI/2+2*Math.PI/5,key:'sn'},
  {label:'理性/感性',short:'TF',angle:Math.PI/2+4*Math.PI/5,key:'tf'},
  {label:'判断/感知',short:'JP',angle:Math.PI/2+6*Math.PI/5,key:'jp'},
  {label:'价值观',short:'VL',angle:Math.PI/2+8*Math.PI/5,key:'vl'},
]

const craftDesc = computed(() => result.value?.craft || '')

const attrs = computed(() => result.value ? [
  {label:'五行属性',value:result.value.element||'—'},
  {label:'幸运色',value:result.value.luckyColor||'—'},
  {label:'幸运数字',value:result.value.luckyNumber||'—'},
  {label:'潮汕音译',value:result.value.dialectName||'—'},
] : [])

const relatedProducts = [
  { id:1, name:'生肖守护神盲盒', price:'29', taobao:'https://shop.m.taobao.com', img:'images/zodiac/all-zodiac.png' },
  { id:2, name:'基础DIY材料包', price:'29', taobao:'https://shop.m.taobao.com', img:'images/products/creative/IMG_9912.JPG' },
  { id:3, name:'大师手作珍藏定制', price:'699', taobao:'https://shop.m.taobao.com', img:'images/products/premium/premium1.png' },
  { id:4, name:'潮剧脸谱冰箱贴（4枚）', price:'39', taobao:'https://shop.m.taobao.com', img:'images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg' },
]

const scoresDisplay = computed(() => {
  if (!result.value?.dimensionScores) return ['E','S','T','J','—']
  const s = result.value.dimensionScores
  return [
    `${s.ei>=0?'E':'I'} ${Math.abs(s.ei*100).toFixed(0)}%`,
    `${s.sn>=0?'S':'N'} ${Math.abs(s.sn*100).toFixed(0)}%`,
    `${s.tf>=0?'T':'F'} ${Math.abs(s.tf*100).toFixed(0)}%`,
    `${s.jp>=0?'J':'P'} ${Math.abs(s.jp*100).toFixed(0)}%`,
    s.valueLabel||'—'
  ]
})

function ringPoints(r){return axes.map(a=>`${100+90*r*Math.cos(a.angle)},${100-90*r*Math.sin(a.angle)}`).join(' ')}

function valScore(label){
  if(label==='成就型') return 0.88
  if(label==='安稳型') return 0.28
  if(label==='自由型') return 0.72
  return 0.5
}

const dataPoints = computed(()=>{
  const s=result.value?.dimensionScores
  if(!s) return '100,100 '.repeat(5)
  const vals = [norm(s.ei), norm(s.sn), norm(s.tf), norm(s.jp), valScore(s.valueLabel)]
  return axes.map((a,i) => `${100+82*vals[i]*Math.cos(a.angle)},${100-82*vals[i]*Math.sin(a.angle)}`).join(' ')
})

const dataDots = computed(()=>{
  const s=result.value?.dimensionScores
  if(!s) return []
  const vals = [norm(s.ei), norm(s.sn), norm(s.tf), norm(s.jp), valScore(s.valueLabel)]
  return axes.map((a,i) => ({x:100+82*vals[i]*Math.cos(a.angle),y:100-82*vals[i]*Math.sin(a.angle)}))
})

const NAME_TO_EN = { '鼠':'rat','牛':'ox','虎':'tiger','兔':'rabbit','龙':'dragon','蛇':'snake','马':'horse','羊':'goat','猴':'monkey','鸡':'rooster','狗':'dog','猪':'pig' }

const qrCodeUrl = computed(()=>`https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=${encodeURIComponent(window.location.origin+'/test')}`)

function norm(v){return (v+1)/2}
function openTaobao(link) { window.open(link || 'https://shop.m.taobao.com', '_blank') }

function downloadPoster(){
  const el=posterRef.value
  if(!el) return
  const clone=el.cloneNode(true)
  clone.querySelectorAll('img').forEach(img=>{
    const s=img.getAttribute('src')
    if(s&&s.startsWith('/')) img.setAttribute('src',window.location.origin+'/su-shuo-xin-yu'+s)
  })
  const w=window.open('','_blank','width=420,height=900')
  if(w){w.document.write('<!DOCTYPE html><html><head><meta charset="UTF-8"><title>我的生肖守护神</title><link href="https://fonts.googleapis.com/css2?family=Noto+Serif+SC:wght@400;700&display=swap" rel="stylesheet"><style>body{margin:0;display:flex;justify-content:center;background:#e5e2dc;padding:1rem}@media print{body{background:#fff;padding:0}}</style></head><body>'+clone.outerHTML+'</body></html>');w.document.close()}
}

onMounted(()=>{
  const raw = sessionStorage.getItem('testResult')
  let record = null
  if (raw) record = JSON.parse(raw)

  // If no session data (direct URL access), show Dragon as default
  if (!record) {
    record = { zodiacId: 5, zodiacName: '龙', vector: { ei: 0.8, sn: -0.6, tf: 0.7, jp: 0.8 }, valueType: 'ACHIEVE', valueLabel: '成就型' }
  }

  const z = ZODIAC_DATA[record.zodiacId] || ZODIAC_DATA[5]
  const v = record.vector
  result.value = {
    recordId: record.recordId || Date.now(),
    zodiacId: record.zodiacId,
    zodiacName: record.zodiacName || z.name,
    zodiacImage: 'images/zodiac/' + NAME_TO_EN[record.zodiacName||z.name] + '.jpg',
    dialectName: z.dialect, element: z.element, luckyColor: z.luckyColor,
    luckyNumber: z.luckyNumber, personalityTags: z.personalityTags,
    strengths: z.strengths, weaknesses: z.weaknesses, relationshipAdvice: z.relationshipAdvice,
    craft: z.craft,
    description: z.name + '是十二生肖中' + z.luckyColor.split('、')[0] + '系守护神。' + z.strengths + '。但' + z.weaknesses + '。',
    dimensionScores: { ei: v.ei, sn: v.sn, tf: v.tf, jp: v.jp, valueLabel: record.valueLabel, valueType: record.valueType },
  }
  emoji.value = z.emoji
})
</script>
