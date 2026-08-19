<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
    <div style="max-width:1100px;margin:0 auto">
      <div style="text-align:center;margin-bottom:2.5rem">
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">手艺工坊</h1>
        <p style="color:#53433d">大吴泥塑文创与匠心传承</p>
      </div>

      <!-- Tab 切换 -->
      <div style="display:flex;justify-content:center;gap:0.75rem;margin-bottom:2.5rem">
        <button @click="activeTab='products'" style="padding:0.6rem 2rem;border-radius:999px;font-weight:600;font-size:0.95rem;border:none;cursor:pointer;transition:all .3s"
          :style="activeTab==='products' ? 'background:#8c4a2f;color:#fff;box-shadow:0 4px 12px rgba(140,74,47,0.3)' : 'background:#fff;color:#53433d;border:1px solid #d9c2ba'">文创产品</button>
        <button @click="activeTab='workshop'" style="padding:0.6rem 2rem;border-radius:999px;font-weight:600;font-size:0.95rem;border:none;cursor:pointer;transition:all .3s"
          :style="activeTab==='workshop' ? 'background:#8c4a2f;color:#fff;box-shadow:0 4px 12px rgba(140,74,47,0.3)' : 'background:#fff;color:#53433d;border:1px solid #d9c2ba'">手工艺坊</button>
      </div>

      <!-- 文创产品（默认） -->
      <div v-if="activeTab==='products'" style="display:grid;grid-template-columns:repeat(auto-fill,minmax(200px,1fr));gap:1.25rem">
        <div v-for="p in products" :key="p.id" @click="openTaobao(p.link)" style="cursor:pointer;background:#fff;border-radius:1rem;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.06);transition:all .3s">
          <div style="aspect-ratio:1;overflow:hidden;background:#f0eee8">
            <img :src="p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover" loading="lazy"/>
          </div>
          <div style="padding:1rem;text-align:center">
            <h3 style="font-size:0.9rem;color:#1c1c18;margin:0 0 0.4rem">{{ p.name }}</h3>
            <p style="font-size:0.75rem;color:#8c4a2f;font-weight:600;margin:0">去淘宝购买 →</p>
          </div>
        </div>
      </div>

      <!-- 手工艺坊现场图 -->
      <div v-else style="column-count:2;column-gap:1rem">
        <div v-for="img in workshopImages" :key="img.id" style="break-inside:avoid;margin-bottom:1rem;border-radius:0.75rem;overflow:hidden;background:#f0eee8;box-shadow:0 1px 3px rgba(0,0,0,0.06)">
          <img :src="img.url" :alt="img.title" style="width:100%;display:block" loading="lazy"/>
          <div style="padding:0.6rem"><p style="font-size:0.8rem;color:#1c1c18;margin:0;white-space:nowrap;overflow:hidden;text-overflow:ellipsis">{{ img.title }}</p></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeTab = ref('products')

const products = [
  { id:1, name:'十二生肖摆件·揽福生肖', link:'https://e.tb.cn/h.8QkWqn2EMn6nmdU?tk=mm5qTcZ6srO', img:'images/taobao/taobao-1.jpg' },
  { id:2, name:'红桃粿口哨系列·潮州', link:'https://e.tb.cn/h.8iyqOUltCpPrBQb?tk=nUukTcZSYTJ', img:'images/taobao/taobao-2.jpg' },
  { id:3, name:'英歌福蛇·时迁', link:'https://e.tb.cn/h.8jYEWj03ytIrZzN?tk=wyi1TcZR9HL', img:'images/taobao/taobao-3.jpg' },
  { id:4, name:'月老好姻缘·招桃花', link:'https://e.tb.cn/h.8iys426RblGshG1?tk=TughTcZizka', img:'images/taobao/taobao-4.jpg' },
  { id:5, name:'金榜题名·文昌帝君', link:'https://e.tb.cn/h.8iyHpBuSiJMPU6q?tk=uIpyTcZjm0L', img:'images/taobao/taobao-5.jpg' },
]

const workshopImages = [
  { id:'w1',title:'匠人捏塑',url:'images/products/workshop/IMG_0031.JPG'},
  { id:'w2',title:'泥塑半成品',url:'images/products/workshop/IMG_0037.JPG'},
  { id:'w3',title:'精雕细琢',url:'images/products/workshop/IMG_0040.JPG'},
  { id:'w4',title:'传承人创作',url:'images/products/workshop/IMG_0050.JPG'},
  { id:'w5',title:'工坊体验课',url:'images/products/workshop/20260430140858_IMG_2231.JPG'},
  { id:'w6',title:'泥塑教学',url:'images/products/workshop/20260430140907_IMG_2232.JPG'},
  { id:'w7',title:'学员作品',url:'images/products/workshop/20260430140923_IMG_2233.JPG'},
]

function openTaobao(link) { window.open(link, '_blank') }
</script>
