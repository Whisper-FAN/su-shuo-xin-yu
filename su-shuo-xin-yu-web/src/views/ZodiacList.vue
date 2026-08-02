<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
    <div style="max-width:720px;margin:0 auto">
      <div style="text-align:center;margin-bottom:3rem">
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">作品赏析</h1>
        <p style="color:#53433d">十二生肖泥塑 - 传承人吴维清、吴漫历年创作实物作品</p>
      </div>

      <!-- Row 1 -->
      <div style="display:flex;gap:10px;margin-bottom:10px;justify-content:center">
        <div v-for="z in zodiacs.slice(0,6)" :key="z.id"
             style="width:calc((100% - 50px) / 6);cursor:pointer;text-align:center"
             @click="$router.push('/zodiac/'+z.id)">
          <div style="aspect-ratio:3/4;border-radius:10px;overflow:hidden;background:#f0eee8;margin-bottom:6px">
            <img :src="'/su-shuo-xin-yu' + z.img" :alt="z.name"
                 style="width:100%;height:100%;object-fit:cover"
                 loading="lazy"
                 @error="e => e.target.parentElement.innerHTML='<span style=display:flex;align-items:center;justify-content:center;width:100%;height:100%;font-size:2rem;background:rgba(140,74,47,0.05)>'+z.emoji+'</span>'" />
          </div>
          <div style="font-family:'Noto Serif SC',serif;font-size:14px;color:#1c1c18;line-height:1.4">{{ z.name }}</div>
          <div style="font-size:11px;color:#86736c">{{ z.alias }}</div>
        </div>
      </div>

      <!-- Row 2 -->
      <div style="display:flex;gap:10px;justify-content:center">
        <div v-for="z in zodiacs.slice(6,12)" :key="z.id"
             style="width:calc((100% - 50px) / 6);cursor:pointer;text-align:center"
             @click="$router.push('/zodiac/'+z.id)">
          <div style="aspect-ratio:3/4;border-radius:10px;overflow:hidden;background:#f0eee8;margin-bottom:6px">
            <img :src="'/su-shuo-xin-yu' + z.img" :alt="z.name"
                 style="width:100%;height:100%;object-fit:cover"
                 loading="lazy"
                 @error="e => e.target.parentElement.innerHTML='<span style=display:flex;align-items:center;justify-content:center;width:100%;height:100%;font-size:2rem;background:rgba(140,74,47,0.05)>'+z.emoji+'</span>'" />
          </div>
          <div style="font-family:'Noto Serif SC',serif;font-size:14px;color:#1c1c18;line-height:1.4">{{ z.name }}</div>
          <div style="font-size:11px;color:#86736c">{{ z.alias }}</div>
        </div>
      </div>

      <div style="text-align:center;margin-top:3rem">
        <router-link to="/test" style="display:inline-block;background:#8c4a2f;color:#fff;padding:0.75rem 2.5rem;border-radius:999px;font-weight:600;text-decoration:none;font-size:1.1rem">测测你是哪个生肖 →</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>

const zodiacs = [
  { id:1, name:'鼠', alias:'子鼠', emoji:'🐭', img:'images/zodiac/rat.jpg' },
  { id:2, name:'牛', alias:'丑牛', emoji:'🐮', img:'images/zodiac/ox.jpg' },
  { id:3, name:'虎', alias:'寅虎', emoji:'🐯', img:'images/zodiac/tiger.jpg' },
  { id:4, name:'兔', alias:'卯兔', emoji:'🐰', img:'images/zodiac/rabbit.jpg' },
  { id:5, name:'龙', alias:'辰龙', emoji:'🐲', img:'images/zodiac/dragon.jpg' },
  { id:6, name:'蛇', alias:'巳蛇', emoji:'🐍', img:'images/zodiac/snake.jpg' },
  { id:7, name:'马', alias:'午马', emoji:'🐴', img:'images/zodiac/horse.jpg' },
  { id:8, name:'羊', alias:'未羊', emoji:'🐏', img:'images/zodiac/goat.jpg' },
  { id:9, name:'猴', alias:'申猴', emoji:'🐵', img:'images/zodiac/monkey.jpg' },
  { id:10, name:'鸡', alias:'酉鸡', emoji:'🐔', img:'images/zodiac/rooster.jpg' },
  { id:11, name:'狗', alias:'戌狗', emoji:'🐶', img:'images/zodiac/dog.jpg' },
  { id:12, name:'猪', alias:'亥猪', emoji:'🐷', img:'images/zodiac/pig.jpg' },
]
</script>
