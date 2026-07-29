<template>
  <div class="paper-texture min-h-screen pt-24 pb-20 px-6">
    <div class="max-w-[960px] mx-auto" v-if="z">
      <button @click="$router.push('/zodiac')" class="inline-flex items-center gap-2 text-on-surface-variant hover:text-primary mb-8 text-sm">
        ← 返回生肖列表
      </button>

      <div class="grid md:grid-cols-2 gap-10 mb-16">
        <div class="aspect-[3/4] rounded-2xl overflow-hidden shadow-xl bg-surface-container">
          <img :src="'/su-shuo-xin-yu' + z.img" :alt="z.name" class="w-full h-full object-cover"
               @error="e => { e.target.replaceWith(Object.assign(document.createElement('span'),{className:'w-full h-full flex items-center justify-center text-[160px] bg-gradient-to-br from-primary/10 to-secondary/10',textContent:z.emoji})) }" />
        </div>
        <div class="flex flex-col justify-center">
          <span class="text-xs text-primary tracking-[0.2em] uppercase mb-3">{{ z.element }}</span>
          <h1 class="font-display text-4xl md:text-5xl text-on-surface mb-2">{{ z.name }}</h1>
          <p class="font-display text-xl text-on-surface-variant/50 mb-6">{{ z.alias }}</p>
          <p class="text-on-surface-variant leading-relaxed text-body-lg mb-8">{{ z.desc }}</p>

          <div class="grid grid-cols-2 gap-3">
            <div class="p-4 rounded-xl bg-white/60 border border-outline-variant/20 text-center">
              <p class="text-xs text-outline tracking-wider mb-1">幸运色</p>
              <div class="flex items-center justify-center gap-1.5">
                <span v-for="h in z.hexes" :key="h" class="w-4 h-4 rounded-full border border-outline-variant/30" :style="{background:h}"></span>
                <span class="text-sm text-on-surface ml-1">{{ z.color }}</span>
              </div>
            </div>
            <div class="p-4 rounded-xl bg-white/60 border border-outline-variant/20 text-center">
              <p class="text-xs text-outline tracking-wider mb-1">五行</p>
              <p class="text-sm text-primary font-bold">{{ z.element }}</p>
            </div>
            <div class="p-4 rounded-xl bg-white/60 border border-outline-variant/20 text-center">
              <p class="text-xs text-outline tracking-wider mb-1">幸运数字</p>
              <p class="text-sm text-primary font-bold">{{ z.number }}</p>
            </div>
            <div class="p-4 rounded-xl bg-white/60 border border-outline-variant/20 text-center">
              <p class="text-xs text-outline tracking-wider mb-1">性格</p>
              <p class="text-sm text-primary font-bold">{{ z.trait }}</p>
            </div>
          </div>
        </div>
      </div>

      <div class="text-center p-10 rounded-2xl bg-primary/5 border border-primary/10">
        <p class="font-display text-xl text-on-surface mb-2">想知道你是否属于{{ z.name }}吗？</p>
        <router-link to="/test" class="btn-primary inline-block mt-4">开始测试 →</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>

import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const z = ref(null)

const map = {
  1:  { name:'鼠', alias:'子鼠', element:'水', color:'棕色、金色', emoji:'🐭', img:'/images/zodiac/rat.jpg',      number:'2、3',  trait:'机敏灵活',  hexes:['#92400e','#f59e0b'], desc:'鼠是十二生肖之首，象征机敏与聪慧。大吴泥塑中的生肖鼠造型圆润可爱，深棕色鼠身搭配金色底座装饰，灵动中透着精巧。' },
  2:  { name:'牛', alias:'丑牛', element:'土', color:'棕色、黄色', emoji:'🐮', img:'/images/zodiac/ox.jpg',      number:'1、4',  trait:'踏实坚韧',  hexes:['#92400e','#eab308'], desc:'牛是勤恳与坚韧的化身。泥塑生肖牛以棕褐色调为主，身着传统服饰，神情憨厚稳重，体现"厚德载物"的力量。' },
  3:  { name:'虎', alias:'寅虎', element:'木', color:'黄色、红色、黑色', emoji:'🐯', img:'/images/zodiac/tiger.jpg', number:'1、3、4', trait:'勇猛无畏', hexes:['#eab308','#dc2626','#1f2937'], desc:'虎为百兽之王，象征威严与力量。泥塑生肖虎以明黄底黑色条纹为特色，红色头饰增添威猛之气，是驱邪避凶的瑞兽。' },
  4:  { name:'兔', alias:'卯兔', element:'木', color:'白色、粉色', emoji:'🐰', img:'/images/zodiac/rabbit.jpg',  number:'3、4、6', trait:'温柔优雅', hexes:['#e5e7eb','#ec4899'], desc:'兔温柔优雅，象征美好与敏捷。泥塑生肖兔以白色兔身搭配粉红服饰，神态安详可爱，体现宁静致远的东方美学。' },
  5:  { name:'龙', alias:'辰龙', element:'土', color:'金色、红色', emoji:'🐲', img:'/images/zodiac/dragon.jpg',   number:'1、6、7', trait:'卓越领袖', hexes:['#f59e0b','#dc2626'], desc:'龙是中华民族的图腾，十二生肖中唯一的神话生物。泥塑生肖龙金鳞闪耀、红色鳞片点缀，腾云驾雾的姿态展现龙的威严与神圣。' },
  6:  { name:'蛇', alias:'巳蛇', element:'火', color:'绿色、金色', emoji:'🐍', img:'/images/zodiac/snake.jpg',    number:'2、8、9', trait:'智慧深邃', hexes:['#16a34a','#f59e0b'], desc:'蛇是智慧与神秘的象征，被誉为"小龙"。泥塑生肖蛇以翠绿色蛇身搭配金色花纹，盘旋而上的造型展现灵动与优雅。' },
  7:  { name:'马', alias:'午马', element:'火', color:'棕色、栗色', emoji:'🐴', img:'/images/zodiac/horse.jpg',    number:'2、3、7', trait:'自由奔放', hexes:['#92400e','#78350f'], desc:'马象征着自由与活力。泥塑生肖马以栗棕色马身搭配深棕鬃毛，昂首挺胸的姿态展现奔腾不息的生命力。' },
  8:  { name:'羊', alias:'未羊', element:'土', color:'白色、粉色', emoji:'🐏', img:'/images/zodiac/goat.jpg',     number:'2、7',   trait:'温和善良', hexes:['#e5e7eb','#ec4899'], desc:'羊温和善良，象征美好与吉祥。泥塑生肖羊乳白色的身体搭配粉色面部点缀，神态温驯安详，传递着宁静致远的意境。' },
  9:  { name:'猴', alias:'申猴', element:'金', color:'棕色、黄色', emoji:'🐵', img:'/images/zodiac/monkey.jpg',   number:'1、8',   trait:'聪慧敏捷', hexes:['#92400e','#eab308'], desc:'猴聪明伶俐，象征智慧与活力。泥塑生肖猴以棕色猴身搭配浅黄肤色，灵活的姿态展现猴子淘气可爱的一面。' },
  10: { name:'鸡', alias:'酉鸡', element:'金', color:'黄色、红色', emoji:'🐔', img:'/images/zodiac/rooster.jpg',  number:'5、7、8', trait:'自信精致', hexes:['#eab308','#dc2626'], desc:'鸡自信昂扬，象征光明与希望。泥塑生肖鸡金黄色鸡身搭配红色鸡冠，昂首挺胸的姿态充满精气神。' },
  11: { name:'狗', alias:'戌狗', element:'土', color:'棕色、黄色', emoji:'🐶', img:'/images/zodiac/dog.jpg',      number:'3、4、9', trait:'忠诚可靠', hexes:['#92400e','#eab308'], desc:'狗忠诚正直，是人类最好的朋友。泥塑生肖狗棕黄色身体搭配深棕斑点，憨态可掬中透出忠诚守护的气质。' },
  12: { name:'猪', alias:'亥猪', element:'水', color:'粉色、白色', emoji:'🐷', img:'/images/zodiac/pig.jpg',      number:'2、5、8', trait:'豁达乐观', hexes:['#ec4899','#e5e7eb'], desc:'猪知足常乐，象征福气与圆满。泥塑生肖猪粉红色身体搭配白色点缀，憨厚可爱的造型传达"知足者常乐"的生活智慧。' },
}

onMounted(() => { z.value = map[route.params.id] })
</script>
