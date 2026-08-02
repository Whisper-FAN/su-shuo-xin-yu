<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
    <div style="max-width:1100px;margin:0 auto">
      <div style="text-align:center;margin-bottom:3rem">
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">手艺工坊</h1>
        <p style="color:#53433d">感受泥土在指尖的温度，记录匠心传承的每一个瞬间</p>
      </div>

      <div style="column-count:2;column-gap:1rem;margin-bottom:4rem">
        <div v-for="img in images" :key="img.id" style="break-inside:avoid;margin-bottom:1rem;border-radius:0.75rem;overflow:hidden;background:#f0eee8;box-shadow:0 1px 3px rgba(0,0,0,0.06);cursor:pointer" @click="lightbox = img">
          <img :src="img.url" :alt="img.title" style="width:100%;display:block;transition:transform 0.5s" loading="lazy"
               @error="e => { e.target.src='/images/products/workshop/IMG_0031.JPG' }" />
          <div style="padding:0.75rem"><p style="font-size:0.8rem;color:#1c1c18;margin:0;white-space:nowrap;overflow:hidden;text-overflow:ellipsis">{{ img.title }}</p></div>
        </div>
      </div>

      <!-- 文创产品 with prices -->
      <div style="border-top:1px solid rgba(140,74,47,0.1);padding-top:3rem;margin-top:2rem">
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1.5rem;font-weight:700;color:#1c1c18;text-align:center;margin-bottom:2rem">文创产品</h2>
        <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(200px,1fr));gap:1.5rem">
          <div v-for="p in products" :key="p.id" @click="window.open(p.link||'https://shop.m.taobao.com','_blank')" style="cursor:pointer;background:#fff;border-radius:1rem;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.06)">
            <div style="aspect-ratio:1;overflow:hidden;background:#f0eee8">
              <img :src="p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover;transition:transform 0.5s;transform:scale(1);box-shadow:none" loading="lazy"
                   @mouseenter="e => e.target.style.transform='scale(1.05)'"
                   @mouseleave="e => e.target.style.transform='scale(1)'"
                   @error="e => { e.target.style.display='none'; e.target.parentElement.innerHTML='<div style=width:100%;height:100%;display:flex;align-items:center;justify-content:center;font-size:2rem;background:rgba(140,74,47,0.05)>'+p.cat+'</div>' }" />
            </div>
            <div style="padding:1rem">
              <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:0.5rem">
                <h3 style="font-size:0.9rem;color:#1c1c18;margin:0;flex:1;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ p.name }}</h3>
                <span style="font-size:0.75rem;background:rgba(140,74,47,0.1);color:#8c4a2f;padding:0.15rem 0.5rem;border-radius:999px;white-space:nowrap;flex-shrink:0">{{ p.cat }}</span>
              </div>
              <p style="font-size:0.95rem;color:#8c4a2f;font-weight:700;margin:0.5rem 0 0">&yen;{{ p.price }}</p>
            </div>
          </div>
        </div>
        <p style="font-size:0.8rem;color:#86736c;text-align:center;margin-top:2rem">以上为展示样品，购买或定制请联系传承人</p>
      </div>

      <Teleport to="body">
        <div v-if="lightbox" @click="lightbox = null" style="position:fixed;inset:0;z-index:100;background:rgba(0,0,0,0.9);display:flex;align-items:center;justify-content:center;padding:2rem">
          <button @click.stop="lightbox = null" style="position:absolute;top:1rem;right:1rem;width:2.5rem;height:2.5rem;border-radius:50%;background:rgba(255,255,255,0.1);color:#fff;font-size:1.2rem;border:none;cursor:pointer;display:flex;align-items:center;justify-content:center">✕</button>
          <img :src="lightbox.url" :alt="lightbox.title" style="max-width:100%;max-height:85vh;object-fit:contain;border-radius:0.5rem" @click.stop />
        </div>
      </Teleport>
    </div>
  </div>
</template>

<script setup>

import { ref } from 'vue'
const lightbox = ref(null)
const images = [
  { id:'w1',title:'匠人捏塑',cat:'workshop',url:'images/products/workshop/IMG_0031.JPG'},
  { id:'w2',title:'泥塑半成品',cat:'workshop',url:'images/products/workshop/IMG_0037.JPG'},
  { id:'w3',title:'精雕细琢',cat:'workshop',url:'images/products/workshop/IMG_0040.JPG'},
  { id:'w4',title:'传承人创作',cat:'workshop',url:'images/products/workshop/IMG_0050.JPG'},
  { id:'w5',title:'工坊体验课',cat:'workshop',url:'images/products/workshop/20260430140858_IMG_2231.JPG'},
  { id:'w6',title:'泥塑教学',cat:'workshop',url:'images/products/workshop/20260430140907_IMG_2232.JPG'},
  { id:'w7',title:'学员作品',cat:'workshop',url:'images/products/workshop/20260430140923_IMG_2233.JPG'},
  { id:'c1',title:'红桃粿泥塑',cat:'creative',url:'images/products/creative/hongtaoguo.jpg'},
  { id:'c2',title:'文创笔记本',cat:'creative',url:'images/products/creative/IMG_9910.JPG'},
  { id:'c3',title:'DIY材料包',cat:'creative',url:'images/products/creative/IMG_9912.JPG'},
  { id:'c4',title:'泥塑摆件',cat:'creative',url:'images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg'},
  { id:'p1',title:'福禄寿泥塑',cat:'premium',url:'images/products/premium/fulushou.jpg'},
  { id:'p2',title:'高端定制祥龙',cat:'premium',url:'images/products/premium/premium1.png'},
  { id:'p3',title:'高端定制精品',cat:'premium',url:'images/products/premium/premium2.png'},
  { id:'p4',title:'精品泥塑',cat:'premium',url:'images/products/premium/premium3.jpg'},
]

const TAOBAO = 'https://shop.m.taobao.com'
const products = [
  { id:1,  name:'深度人格报告',               price:'9.90',  cat:'数字', img:'images/zodiac/all-zodiac.png', link: TAOBAO },
  { id:2,  name:'潮剧脸谱冰箱贴（单枚）',     price:'12',    cat:'文创', img:'images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg', link: TAOBAO },
  { id:3,  name:'生肖守护神盲盒（单盒）',       price:'29',    cat:'盲盒', img:'images/zodiac/all-zodiac.png', link: TAOBAO },
  { id:4,  name:'基础DIY材料包',               price:'29',    cat:'DIY',  img:'images/products/creative/IMG_9912.JPG', link: TAOBAO },
  { id:5,  name:'潮剧脸谱冰箱贴（4枚套组）',    price:'39',    cat:'文创', img:'images/products/creative/IMG_9910.JPG', link: TAOBAO },
  { id:6,  name:'进阶DIY彩绘材料包',           price:'39',    cat:'DIY',  img:'images/products/creative/IMG_9912.JPG', link: TAOBAO },
  { id:7,  name:'生肖守护神盲盒（端盒6个）',    price:'158',   cat:'盲盒', img:'images/zodiac/all-zodiac.png', link: TAOBAO },
  { id:8,  name:'《贴塑》专题小课（6节）',      price:'129',   cat:'课程', img:'images/products/workshop/IMG_0031.JPG', link: TAOBAO },
  { id:9,  name:'泥塑入门系统课（10节）',       price:'199',   cat:'课程', img:'images/products/workshop/IMG_0050.JPG', link: TAOBAO },
  { id:10, name:'基础生肖定制',                price:'199',   cat:'定制', img:'images/zodiac/all-zodiac.png', link: TAOBAO },
  { id:11, name:'泥塑里的中国 非遗研学营',     price:'298',   cat:'研学', img:'images/products/workshop/IMG_0050.JPG', link: TAOBAO },
  { id:12, name:'大师手作·珍藏定制',            price:'699',   cat:'高端', img:'images/products/premium/premium1.png', link: TAOBAO },
]
</script>
