<template>
<div style="overflow-x:hidden">
  <!-- ======== HERO ======== -->
  <section style="position:relative;height:100vh;overflow:hidden">
    <div style="position:absolute;inset:0;z-index:0;background:linear-gradient(135deg,#3a1c10,#5a3020,#2d1a0e)">
      <img :src="'/su-shuo-xin-yu/images/zodiac/all-zodiac.png'" alt="十二生肖" style="position:absolute;inset:0;width:100%;height:100%;object-fit:cover;opacity:0.3" />
    </div>
    <div style="position:absolute;inset:0;background:rgba(0,0,0,0.3)"></div>
    <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);z-index:10;text-align:center;color:#fff;padding:0 1.5rem;width:100%;max-width:56rem">
      <h1 style="font-family:'Noto Serif SC',serif;font-size:clamp(2.5rem,8vw,4rem);margin-bottom:1.5rem;letter-spacing:0.15em;text-shadow:0 2px 10px rgba(0,0,0,0.5);line-height:1.15;font-weight:700">塑说心语</h1>
      <p style="font-family:'Noto Serif SC',serif;font-size:clamp(1.1rem,3vw,1.5rem);margin-bottom:1rem;font-weight:300;opacity:0.9">万物有灵，泥韵传情</p>
      <p style="font-size:clamp(0.85rem,2vw,1rem);margin-bottom:3rem;opacity:0.7;max-width:36rem;margin-left:auto;margin-right:auto">在指尖的温度中，开启一场跨越千年的灵魂对话</p>
      <router-link to="/test" style="display:inline-block;background:#8c4a2f;color:#fff;padding:0.75rem 2.5rem;border-radius:999px;font-weight:600;font-size:1.1rem;text-decoration:none;box-shadow:0 10px 30px rgba(140,74,47,0.3);transition:all .3s">开始性格测试</router-link>
    </div>
    <div onclick="document.getElementById('heritage-video').scrollIntoView({behavior:'smooth'})" style="position:absolute;bottom:2rem;left:50%;transform:translateX(-50%);z-index:10;display:flex;flex-direction:column;align-items:center;gap:0.5rem;cursor:pointer;animation:bounce 2s infinite">
      <span style="color:rgba(255,255,255,0.5);font-size:0.8rem">探索文化</span>
      <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M7 13l5 5 5-5M7 6l5 5 5-5"/></svg>
    </div>
  </section>

  <!-- ======== VIDEO INTRO ======== -->
  <section id="heritage-video" style="padding:4rem 1.5rem;background:#f6f3ed">
    <div style="max-width:60rem;margin:0 auto;text-align:center">
      <span style="font-size:0.75rem;color:#8c4a2f;text-transform:uppercase;letter-spacing:0.2em">Video</span>
      <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.5rem,3vw,2rem);color:#1c1c18;margin:1rem 0 1.5rem;font-weight:700">八百年泥韵 · 一分钟初识</h2>
      <div style="aspect-ratio:16/9;border-radius:1rem;overflow:hidden;background:#000;box-shadow:0 10px 40px rgba(0,0,0,0.15);max-width:50rem;margin:0 auto;position:relative;cursor:pointer" onclick="alert('专题短片即将上线！')">
        <img :src="'/su-shuo-xin-yu/images/zodiac/all-zodiac.png'" alt="视频" style="width:100%;height:100%;object-fit:cover;opacity:0.5" />
        <div style="position:absolute;inset:0;display:flex;flex-direction:column;align-items:center;justify-content:center;color:#fff">
          <div style="width:4rem;height:4rem;border-radius:50%;background:rgba(255,255,255,0.2);display:flex;align-items:center;justify-content:center;font-size:1.8rem">▶</div>
          <p style="margin-top:1rem;font-size:0.9rem;opacity:0.8">点击播放</p>
        </div>
      </div>
      <p style="font-size:0.8rem;color:rgba(83,67,61,0.5);margin-top:1rem">"土叽咕，土叽咕，阿公捏泥做戏出" — 潮汕童谣</p>
    </div>
  </section>

  <!-- ======== HERITAGE ======== -->
  <section style="padding:clamp(4rem,10vw,7.5rem) 1.5rem;background:#fcf9f3">
    <div style="max-width:69rem;margin:0 auto;display:grid;grid-template-columns:1fr 1fr;gap:4rem;align-items:center">
      <div>
        <span style="font-size:0.75rem;color:#8c4a2f;text-transform:uppercase;letter-spacing:0.2em;display:block;margin-bottom:1rem">Heritage</span>
        <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.75rem,3vw,2.25rem);color:#1c1c18;margin-bottom:1.5rem;font-weight:700">大吴泥塑：泥土中的文化图腾</h2>
        <div style="width:4rem;height:2px;background:#f5bd58;border-radius:1px;margin-bottom:1.5rem"></div>
        <p style="font-size:1.1rem;color:#53433d;line-height:1.8;margin-bottom:1rem">起源于南宋（1237年），与天津泥人张、无锡惠山泥人并称中国三大泥塑，是<b>国家级非物质文化遗产</b>。</p>
        <p style="color:#53433d;line-height:1.8">近八百年间，这门手艺世代相传，"户户有作坊，人人会泥塑"。我们致力于让这项古老艺术在现代语境下重新绽放。</p>
        <router-link to="/stories" style="display:inline-flex;align-items:center;gap:0.5rem;margin-top:2rem;color:#8c4a2f;font-weight:600;text-decoration:none;font-size:0.95rem">探索传承故事 →</router-link>
      </div>
      <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem">
        <div style="aspect-ratio:1;border-radius:1rem;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);background:#f0eee8"><img :src="'/su-shuo-xin-yu/images/products/premium/premium1.png'" alt="祥龙" style="width:100%;height:100%;object-fit:cover" /></div>
        <div style="aspect-ratio:3/4;border-radius:1rem;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.08);margin-top:2rem;background:#f0eee8"><img :src="'/su-shuo-xin-yu/images/products/premium/fulushou.jpg'" alt="福禄寿" style="width:100%;height:100%;object-fit:cover" /></div>
      </div>
    </div>
  </section>

  <!-- ======== ZODIAC SLIDER ======== -->
  <section style="padding:clamp(4rem,10vw,7.5rem) 1.5rem;background:#f6f3ed;overflow:hidden">
    <div style="max-width:80rem;margin:0 auto 4rem;padding:0 1.5rem;display:flex;justify-content:space-between;align-items:flex-end;flex-wrap:wrap;gap:1.5rem">
      <div>
        <span style="font-size:0.75rem;color:#8c4a2f;text-transform:uppercase;letter-spacing:0.2em;display:block;margin-bottom:0.5rem">Collection</span>
        <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.75rem,3vw,2.25rem);color:#1c1c18;font-weight:700">十二生肖：时间的守护者</h2>
      </div>
      <router-link to="/zodiac" style="display:flex;align-items:center;gap:0.5rem;color:#8c4a2f;font-size:0.9rem;text-decoration:none">查看全部系列 →</router-link>
    </div>
    <div style="display:flex;gap:2rem;overflow-x:auto;padding:0 5% 3rem;-ms-overflow-style:none;scrollbar-width:none">
      <div v-for="(z,i) in allZodiacs" :key="z.id" :style="{flexShrink:0,width:'260px',cursor:'pointer',marginTop:i%2===1?'3rem':'0'}" @click="$router.push(`/zodiac/${z.id}`)">
        <div style="aspect-ratio:3/4;border-radius:1rem;overflow:hidden;background:#fff;margin-bottom:1rem;box-shadow:0 2px 10px rgba(0,0,0,0.06);transition:all .4s">
          <img :src="'/su-shuo-xin-yu' + z.img" :alt="z.name" style="width:100%;height:100%;object-fit:cover;transition:transform .5s" />
        </div>
        <h3 style="font-family:'Noto Serif SC',serif;font-size:1.1rem;color:#1c1c18;margin:0">{{ z.name }} <span style="font-size:0.85rem;color:#53433d;opacity:0.6">· {{ z.alias }}</span></h3>
        <p style="font-size:0.8rem;color:#53433d;margin:0.25rem 0 0">{{ z.element }} · {{ z.color }}</p>
      </div>
    </div>
  </section>

  <!-- ======== TEST CTA ======== -->
  <section style="padding:clamp(4rem,10vw,7.5rem) 1.5rem">
    <div style="max-width:69rem;margin:0 auto;border-radius:2rem;overflow:hidden;background:#8c4a2f;padding:clamp(2.5rem,6vw,5rem);display:flex;align-items:center;gap:3rem;flex-wrap:wrap">
      <div style="flex:1;min-width:280px;color:#fff;text-align:center">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.75rem,3vw,2.25rem);margin-bottom:1rem;font-weight:700">发现你的生肖守护神</h2>
        <p style="font-size:1.1rem;margin-bottom:2.5rem;opacity:0.8">24道趣味测试，基于五维人格模型，匹配专属泥塑生肖。</p>
        <router-link to="/test" style="display:inline-flex;align-items:center;gap:0.5rem;background:#fff;color:#8c4a2f;padding:0.75rem 2.5rem;border-radius:999px;font-weight:600;font-size:1.1rem;text-decoration:none;box-shadow:0 10px 30px rgba(0,0,0,0.2)">开始测试 →</router-link>
      </div>
      <div style="flex:0 0 auto;width:100%;max-width:18rem;margin:0 auto"><img :src="'/su-shuo-xin-yu/images/zodiac/all-zodiac.png'" alt="十二生肖" style="width:100%;object-fit:contain;border-radius:1rem;box-shadow:0 20px 40px rgba(0,0,0,0.3)" /></div>
    </div>
  </section>

  <!-- ======== CRAFTSMANSHIP 5 STEPS ======== -->
  <section style="padding:clamp(4rem,10vw,7.5rem) 1.5rem;background:#fcf9f3">
    <div style="max-width:60rem;margin:0 auto">
      <div style="text-align:center;margin-bottom:4rem">
        <span style="font-size:0.75rem;color:#8c4a2f;text-transform:uppercase;letter-spacing:0.2em;display:block;margin-bottom:0.5rem">Craftsmanship</span>
        <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.75rem,3vw,2.25rem);color:#1c1c18;font-weight:700">五步七技</h2>
        <p style="color:#53433d;margin-top:1rem">大吴泥塑制作分为挖泥、炼泥、捏塑、烧坯和彩绘五个环节，以雕、塑、捏、贴、刻、印和彩为主要技术手段</p>
      </div>
      <div v-for="(s,idx) in craftSteps" :key="s.id" style="display:flex;align-items:center;gap:3rem;margin-bottom:5rem;flex-wrap:wrap;flex-direction:row" :style="{flexDirection:idx%2===1?'row-reverse':'row'}">
        <div style="flex:0 0 auto;display:flex;justify-content:center;min-width:120px">
          <div style="width:8rem;height:8rem;border-radius:50%;display:flex;align-items:center;justify-content:center;background:linear-gradient(135deg,#8c4a2f,#a96245);box-shadow:0 8px 30px rgba(140,74,47,0.25)">
            <span style="color:#fff;font-size:2.5rem;font-weight:700;font-family:'Noto Serif SC',serif">{{ String(idx+1).padStart(2,'0') }}</span>
          </div>
        </div>
        <div style="flex:1;min-width:280px">
          <h3 style="font-family:'Noto Serif SC',serif;font-size:1.5rem;color:#1c1c18;margin:0 0 0.75rem;font-weight:700">{{ s.title }}</h3>
          <p style="color:#53433d;line-height:1.8;font-size:1.1rem;margin:0">{{ s.desc }}</p>
        </div>
      </div>
    </div>
  </section>

  <!-- ======== PRODUCTS ======== -->
  <section style="padding:clamp(4rem,10vw,7.5rem) 1.5rem;background:#f6f3ed">
    <div style="max-width:69rem;margin:0 auto">
      <div style="display:flex;justify-content:space-between;align-items:flex-end;margin-bottom:2.5rem;flex-wrap:wrap;gap:1rem">
        <div>
          <span style="font-size:0.75rem;color:#8c4a2f;text-transform:uppercase;letter-spacing:0.2em;display:block;margin-bottom:0.5rem">Products</span>
          <h2 style="font-family:'Noto Serif SC',serif;font-size:clamp(1.75rem,3vw,2.25rem);color:#1c1c18;font-weight:700;margin:0">精选文创</h2>
        </div>
        <router-link to="/gallery" style="color:#8c4a2f;font-size:0.9rem;text-decoration:none">全部产品 →</router-link>
      </div>
      <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(200px,1fr));gap:1.25rem">
        <div v-for="p in hotProducts" :key="p.id" style="background:#fff;border-radius:1rem;overflow:hidden;box-shadow:0 2px 10px rgba(0,0,0,0.06);transition:all .3s">
          <div style="aspect-ratio:1;background:#f0eee8;overflow:hidden"><img :src="'/su-shuo-xin-yu' + p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover" /></div>
          <div style="padding:1rem"><h3 style="font-size:0.9rem;color:#1c1c18;margin:0 0 0.5rem">{{ p.name }}</h3><p style="font-size:0.95rem;color:#8c4a2f;font-weight:600;margin:0">&yen;{{ p.price }}</p></div>
        </div>
      </div>
    </div>
  </section>

  <!-- ======== PARTNERS ======== -->
  <section style="padding:3rem 1.5rem;background:#f6f3ed;border-top:1px solid rgba(217,194,186,0.3)">
    <div style="max-width:69rem;margin:0 auto;text-align:center">
      <h3 style="font-size:0.75rem;color:rgba(83,67,61,0.5);text-transform:uppercase;letter-spacing:0.2em;margin-bottom:2rem">Partners & Sponsors</h3>
      <div style="display:flex;flex-wrap:wrap;justify-content:center;align-items:center;gap:2rem;opacity:0.7">
        <img :src="'/su-shuo-xin-yu/images/team/school-name-badge.png'" alt="广东金融学院" style="height:3.5rem;width:auto;object-fit:contain" />
        <img :src="'/su-shuo-xin-yu/images/team/contest-full.png'" alt="国创赛" style="height:4rem;width:auto;object-fit:contain" />
        <img :src="'/su-shuo-xin-yu/images/team/school-badge.png'" alt="校徽" style="height:3rem;width:auto;object-fit:contain" />
        <img :src="'/su-shuo-xin-yu/images/team/school-motto.png'" alt="校训" style="height:3rem;width:auto;object-fit:contain" />
      </div>
    </div>
  </section>
</div>
</template>

<script setup>

import { onMounted } from 'vue'
import { recordBehavior } from '@/utils/scroll-reveal'

const allZodiacs = [
  { id:1, name:'鼠', alias:'子鼠', element:'水', color:'棕色、金色', img:'/images/zodiac/rat.jpg' },
  { id:2, name:'牛', alias:'丑牛', element:'土', color:'棕色、黄色', img:'/images/zodiac/ox.jpg' },
  { id:3, name:'虎', alias:'寅虎', element:'木', color:'黄色、红色、黑色', img:'/images/zodiac/tiger.jpg' },
  { id:4, name:'兔', alias:'卯兔', element:'木', color:'白色、粉色', img:'/images/zodiac/rabbit.jpg' },
  { id:5, name:'龙', alias:'辰龙', element:'土', color:'金色、红色', img:'/images/zodiac/dragon.jpg' },
  { id:6, name:'蛇', alias:'巳蛇', element:'火', color:'绿色、金色', img:'/images/zodiac/snake.jpg' },
  { id:7, name:'马', alias:'午马', element:'火', color:'棕色、栗色', img:'/images/zodiac/horse.jpg' },
  { id:8, name:'羊', alias:'未羊', element:'土', color:'白色、粉色', img:'/images/zodiac/goat.jpg' },
  { id:9, name:'猴', alias:'申猴', element:'金', color:'棕色、黄色', img:'/images/zodiac/monkey.jpg' },
  { id:10, name:'鸡', alias:'酉鸡', element:'金', color:'黄色、红色', img:'/images/zodiac/rooster.jpg' },
  { id:11, name:'狗', alias:'戌狗', element:'土', color:'棕色、黄色', img:'/images/zodiac/dog.jpg' },
  { id:12, name:'猪', alias:'亥猪', element:'水', color:'粉色、白色', img:'/images/zodiac/pig.jpg' },
]

const hotProducts = [
  { id:1, name:'潮剧脸谱冰箱贴', price:'12', img:'/images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg' },
  { id:2, name:'生肖守护神盲盒', price:'29', img:'/images/zodiac/all-zodiac.png' },
  { id:3, name:'基础DIY材料包', price:'29', img:'/images/products/creative/IMG_9912.JPG' },
  { id:4, name:'大师手作珍藏定制', price:'699', img:'/images/products/premium/premium1.png' },
]

const craftSteps = [
  { id:1, title:'挖泥 · 取材大地', desc:'从潮州本地取澄泥，这是泥塑的物质起点。泥土经反复捶打醒泥后质地均匀细腻，为后续工序做好准备。' },
  { id:2, title:'炼泥 · 千锤百炼', desc:'将泥料反复揉捏摔打，去除气泡和杂质，炼出柔韧适中的泥坯。炼泥的火候决定了泥塑是否经得起窑火烧制。' },
  { id:3, title:'捏塑 · 贴塑绝技', desc:'压泥成片，褶片成衣——先将陶泥打出筒身基础，再将泥片层层叠加，连内衣、外衣、鞋帽、裙带都按由内到外顺序独立制作，像给泥人穿衣服一样。' },
  { id:4, title:'烧坯 · 窑火淬炼', desc:'捏塑完成后入窑烧制，泥坯在高温中发生质变，从此告别凡胎，化为坚硬牢固的陶质作品。' },
  { id:5, title:'彩绘 · 焕发新生', desc:'以天然矿物颜料施彩——红为朱砂，绿为孔雀石，金为云母。每一抹色彩都来自大地，历经窑火不褪，正如这近八百年的技艺。' },
]

onMounted(() => { recordBehavior('page_view', null, 'home') })
</script>
