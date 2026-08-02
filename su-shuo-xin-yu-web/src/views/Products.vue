<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
    <div style="max-width:1100px;margin:0 auto">
      <div style="text-align:center;margin-bottom:3rem">
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">文创产品</h1>
        <p style="color:#53433d">将大吴泥塑的艺术精髓融入现代生活，每一件都是非遗文化的生动表达</p>
      </div>

      <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(200px,1fr));gap:1.5rem">
        <div v-for="p in products" :key="p.id" style="background:#fff;border-radius:1rem;overflow:hidden;box-shadow:0 1px 3px rgba(0,0,0,0.06);cursor:default">
          <div style="aspect-ratio:1;overflow:hidden;background:#f0eee8">
            <img :src="'/su-shuo-xin-yu' + p.img" :alt="p.name" style="width:100%;height:100%;object-fit:cover;transition:transform 0.5s" loading="lazy"
                 @error="e => { e.target.style.display='none'; e.target.parentElement.innerHTML='<div style=width:100%;height:100%;display:flex;align-items:center;justify-content:center;font-size:2rem;background:rgba(140,74,47,0.05)>'+p.cat+'</div>' }" />
          </div>
          <div style="padding:1rem">
            <div style="display:flex;justify-content:space-between;align-items:flex-start">
              <h3 style="font-size:0.9rem;color:#1c1c18;margin:0;flex:1;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ p.name }}</h3>
              <span style="font-size:0.75rem;background:rgba(140,74,47,0.1);color:#8c4a2f;padding:0.15rem 0.5rem;border-radius:999px;white-space:nowrap;margin-left:0.5rem">{{ p.cat }}</span>
            </div>
            <p style="font-size:0.95rem;color:#8c4a2f;font-weight:600;margin:0.5rem 0 0">&yen;{{ p.price }}</p>
          </div>
        </div>
      </div>

      <div style="text-align:center;margin-top:4rem">
        <p style="font-size:0.85rem;color:#53433d;margin-bottom:1rem">以上为展示样品，购买或定制请联系传承人</p>
        <a href="#" style="display:inline-block;border:1px solid #86736c;color:#53433d;padding:0.6rem 2rem;border-radius:999px;font-size:0.9rem;text-decoration:none">联系购买 →</a>
      </div>
    </div>
  </div>
</template>

<script setup>

const products = [
  { id:1, name:'十二生肖泥塑摆件', price:'29', cat:'文创', img:'images/zodiac/all-zodiac.png' },
  { id:2, name:'红桃粿泥塑口哨', price:'12', cat:'文创', img:'images/products/creative/hongtaoguo.jpg' },
  { id:3, name:'潮剧脸谱冰箱贴', price:'15', cat:'文创', img:'images/products/creative/IMG_9910.JPG' },
  { id:4, name:'生肖DIY材料包', price:'39', cat:'DIY', img:'images/products/creative/IMG_9912.JPG' },
  { id:5, name:'福禄寿高端定制', price:'880', cat:'高端定制', img:'images/products/premium/fulushou.jpg' },
  { id:6, name:'祥龙出云摆件', price:'1380', cat:'高端定制', img:'images/products/premium/premium1.png' },
  { id:7, name:'生肖泥塑体验课(单人)', price:'99', cat:'工坊', img:'images/products/workshop/IMG_0031.JPG' },
  { id:8, name:'亲子泥塑体验课(一大一小)', price:'168', cat:'工坊', img:'images/products/workshop/IMG_0050.JPG' },
  { id:9, name:'研学课程包', price:'298', cat:'工坊', img:'images/products/workshop/IMG_0037.JPG' },
]
</script>
