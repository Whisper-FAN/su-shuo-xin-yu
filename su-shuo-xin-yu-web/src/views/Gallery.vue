<template>
<div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
  <div style="max-width:1100px;margin:0 auto">
    <div style="text-align:center;margin-bottom:3rem">
      <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">手艺工坊</h1>
      <p style="color:#53433d">感受泥土在指尖的温度，记录匠心传承的每一个瞬间</p>
    </div>

    <!-- Workshop Photos -->
    <div style="column-count:2;column-gap:0.75rem;margin-bottom:3rem">
      <div v-for="img in workshopImages" :key="img.id" style="break-inside:avoid;margin-bottom:0.75rem;border-radius:0.75rem;overflow:hidden;background:#f0eee8;box-shadow:0 1px 3px rgba(0,0,0,0.05)">
        <img :src="img" alt="" style="width:100%;display:block" loading="lazy" />
      </div>
    </div>

    <!-- 文创产品 -->
    <div style="border-top:1px solid rgba(140,74,47,0.1);padding-top:2.5rem">
      <h2 style="font-family:'Noto Serif SC',serif;font-size:1.5rem;font-weight:700;color:#1c1c18;text-align:center;margin-bottom:1.5rem">文创产品</h2>
      <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(180px,1fr));gap:1.25rem">
        <div v-for="p in products" :key="p.id" style="background:#fff;border-radius:1rem;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.05)">
          <div style="aspect-ratio:1;overflow:hidden;background:#f0eee8">
            <img :src="p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover" loading="lazy" />
          </div>
          <div style="padding:0.75rem">
            <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:0.5rem">
              <h3 style="font-size:0.85rem;color:#1c1c18;margin:0;flex:1;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ p.name }}</h3>
              <span style="font-size:0.7rem;background:rgba(140,74,47,0.08);color:#8c4a2f;padding:0.15rem 0.4rem;border-radius:999px;white-space:nowrap;flex-shrink:0">{{ p.cat }}</span>
            </div>
            <p style="font-size:0.9rem;color:#8c4a2f;font-weight:700;margin:0.5rem 0 0">&yen;{{ p.price }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script setup>
const workshopImages = [
  'images/products/workshop/IMG_0031.JPG',
  'images/products/workshop/IMG_0037.JPG',
  'images/products/workshop/IMG_0040.JPG',
  'images/products/workshop/IMG_0050.JPG',
  'images/products/workshop/20260430140858_IMG_2231.JPG',
  'images/products/workshop/20260430140907_IMG_2232.JPG',
  'images/products/workshop/20260430140923_IMG_2233.JPG',
  'images/products/creative/hongtaoguo.jpg',
  'images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg',
  'images/products/premium/fulushou.jpg',
  'images/products/premium/premium1.png',
  'images/products/premium/premium2.png',
  'images/products/premium/premium3.jpg',
]

const products = [
  { id:1,  name:'深度人格报告',               price:'9.90',  cat:'数字', img:'images/zodiac/all-zodiac.png' },
  { id:2,  name:'潮剧脸谱冰箱贴（单枚）',     price:'12',    cat:'文创', img:'images/products/creative/903f1b76a74efcdf752ef25dc4f95b8.jpg' },
  { id:3,  name:'生肖守护神盲盒（单盒）',       price:'29',    cat:'盲盒', img:'images/zodiac/all-zodiac.png' },
  { id:4,  name:'基础DIY材料包',               price:'29',    cat:'DIY',  img:'images/products/creative/IMG_9912.JPG' },
  { id:5,  name:'潮剧脸谱冰箱贴（4枚套组）',    price:'39',    cat:'文创', img:'images/products/creative/IMG_9910.JPG' },
  { id:6,  name:'进阶DIY彩绘材料包',           price:'39',    cat:'DIY',  img:'images/products/creative/IMG_9912.JPG' },
  { id:7,  name:'生肖守护神盲盒（端盒6个）',    price:'158',   cat:'盲盒', img:'images/zodiac/all-zodiac.png' },
  { id:8,  name:'《贴塑》专题小课（6节）',      price:'129',   cat:'课程', img:'images/products/workshop/IMG_0031.JPG' },
  { id:9,  name:'泥塑入门系统课（10节）',       price:'199',   cat:'课程', img:'images/products/workshop/IMG_0050.JPG' },
  { id:10, name:'基础生肖定制',                price:'199',   cat:'定制', img:'images/zodiac/all-zodiac.png' },
  { id:11, name:'泥塑里的中国 非遗研学营（1大1小）', price:'298', cat:'研学', img:'images/products/workshop/IMG_0050.JPG' },
  { id:12, name:'大师手作·珍藏定制',            price:'699',   cat:'高端', img:'images/products/premium/premium1.png' },
]
</script>
