<template>
  <div style="min-height:100vh;padding:6rem 1.25rem 4rem;background:#fcf9f3" v-if="z">
    <div style="max-width:960px;margin:0 auto">
      <button @click="$router.push('/zodiac')" style="display:inline-flex;align-items:center;gap:0.5rem;color:#53433d;background:none;border:none;cursor:pointer;font-size:0.9rem;margin-bottom:2rem;padding:0">
        ← 返回生肖列表
      </button>

      <div style="display:grid;grid-template-columns:1fr 1fr;gap:2.5rem;margin-bottom:4rem">
        <div style="aspect-ratio:3/4;border-radius:1rem;overflow:hidden;box-shadow:0 4px 20px rgba(0,0,0,0.1);background:#f0eee8">
          <img :src="z.img" :alt="z.name" style="width:100%;height:100%;object-fit:cover"
               @error="e => { e.target.style.display='none'; e.target.parentElement.innerHTML='<span style=display:flex;align-items:center;justify-content:center;width:100%;height:100%;font-size:6rem;background:linear-gradient(135deg,rgba(140,74,47,0.1),rgba(57,103,89,0.1))>'+z.emoji+'</span>' }" />
        </div>

        <div style="display:flex;flex-direction:column;justify-content:center">
          <span style="font-size:0.75rem;color:#8c4a2f;letter-spacing:0.2em;text-transform:uppercase;margin-bottom:0.75rem">{{ z.element }}</span>
          <h1 style="font-family:'Noto Serif SC',serif;font-size:2.25rem;color:#1c1c18;margin:0 0 0.5rem;font-weight:700">{{ z.name }}</h1>
          <p style="font-family:'Noto Serif SC',serif;font-size:1.25rem;color:rgba(83,67,61,0.5);margin:0 0 1.5rem">{{ z.alias }}</p>
          <p style="color:#53433d;line-height:1.8;font-size:1.05rem;margin:0 0 2rem">{{ z.desc }}</p>

          <div style="display:grid;grid-template-columns:1fr 1fr;gap:0.75rem">
            <div style="padding:1rem;border-radius:0.75rem;background:rgba(255,255,255,0.6);border:1px solid rgba(217,194,186,0.2);text-align:center">
              <p style="font-size:0.75rem;color:#86736c;letter-spacing:0.1em;margin:0 0 0.5rem">幸运色</p>
              <div style="display:flex;align-items:center;justify-content:center;gap:0.25rem">
                <span v-for="h in z.hexes" :key="h" style="width:1rem;height:1rem;border-radius:50%;border:1px solid rgba(217,194,186,0.3)" :style="{background:h}"></span>
                <span style="font-size:0.85rem;color:#1c1c18;margin-left:0.25rem">{{ z.color }}</span>
              </div>
            </div>
            <div style="padding:1rem;border-radius:0.75rem;background:rgba(255,255,255,0.6);border:1px solid rgba(217,194,186,0.2);text-align:center">
              <p style="font-size:0.75rem;color:#86736c;letter-spacing:0.1em;margin:0 0 0.5rem">五行</p>
              <p style="font-size:0.85rem;color:#8c4a2f;font-weight:700;margin:0">{{ z.element }}</p>
            </div>
            <div style="padding:1rem;border-radius:0.75rem;background:rgba(255,255,255,0.6);border:1px solid rgba(217,194,186,0.2);text-align:center">
              <p style="font-size:0.75rem;color:#86736c;letter-spacing:0.1em;margin:0 0 0.5rem">幸运数字</p>
              <p style="font-size:0.85rem;color:#8c4a2f;font-weight:700;margin:0">{{ z.number }}</p>
            </div>
            <div style="padding:1rem;border-radius:0.75rem;background:rgba(255,255,255,0.6);border:1px solid rgba(217,194,186,0.2);text-align:center">
              <p style="font-size:0.75rem;color:#86736c;letter-spacing:0.1em;margin:0 0 0.5rem">性格</p>
              <p style="font-size:0.85rem;color:#8c4a2f;font-weight:700;margin:0">{{ z.trait }}</p>
            </div>
          </div>
        </div>
      </div>

      <div style="text-align:center;padding:2.5rem;border-radius:1rem;background:rgba(140,74,47,0.04);border:1px solid rgba(140,74,47,0.08)">
        <p style="font-family:'Noto Serif SC',serif;font-size:1.25rem;color:#1c1c18;margin-bottom:1rem">想知道你是否属于{{ z.name }}吗？</p>
        <router-link to="/test" style="display:inline-block;background:#8c4a2f;color:#fff;padding:0.75rem 2.5rem;border-radius:999px;font-weight:600;font-size:1.05rem;text-decoration:none;margin-top:1rem">开始测试 →</router-link>
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
  1:  { name:'鼠', alias:'子鼠', element:'水', color:'棕色、金色', emoji:'🐭', img:'images/zodiac/rat.jpg',      number:'2、3',  trait:'机敏灵活',  hexes:['#92400e','#f59e0b'], desc:'鼠是十二生肖之首，象征机敏与聪慧。大吴泥塑中的生肖鼠造型圆润可爱，深棕色鼠身搭配金色底座装饰，灵动中透着精巧。' },
  2:  { name:'牛', alias:'丑牛', element:'土', color:'棕色、黄色', emoji:'🐮', img:'images/zodiac/ox.jpg',      number:'1、4',  trait:'踏实坚韧',  hexes:['#92400e','#eab308'], desc:'牛是勤恳与坚韧的化身。泥塑生肖牛以棕褐色调为主，身着传统服饰，神情憨厚稳重，体现厚德载物的力量。' },
  3:  { name:'虎', alias:'寅虎', element:'木', color:'黄色、红色、黑色', emoji:'🐯', img:'images/zodiac/tiger.jpg', number:'1、3、4', trait:'勇猛无畏', hexes:['#eab308','#dc2626','#1f2937'], desc:'虎为百兽之王，象征威严与力量。泥塑生肖虎以明黄底黑色条纹为特色，红色头饰增添威猛之气，是驱邪避凶的瑞兽。' },
  4:  { name:'兔', alias:'卯兔', element:'木', color:'白色、粉色', emoji:'🐰', img:'images/zodiac/rabbit.jpg',  number:'3、4、6', trait:'温柔优雅', hexes:['#e5e7eb','#ec4899'], desc:'兔温柔优雅，象征美好与敏捷。泥塑生肖兔以白色兔身搭配粉红服饰，神态安详可爱，体现宁静致远的东方美学。' },
  5:  { name:'龙', alias:'辰龙', element:'土', color:'金色、红色', emoji:'🐲', img:'images/zodiac/dragon.jpg',   number:'1、6、7', trait:'卓越领袖', hexes:['#f59e0b','#dc2626'], desc:'龙是中华民族的图腾，十二生肖中唯一的神话生物。泥塑生肖龙金鳞闪耀、红色鳞片点缀，腾云驾雾的姿态展现龙的威严与神圣。' },
  6:  { name:'蛇', alias:'巳蛇', element:'火', color:'绿色、金色', emoji:'🐍', img:'images/zodiac/snake.jpg',    number:'2、8、9', trait:'智慧深邃', hexes:['#16a34a','#f59e0b'], desc:'蛇是智慧与神秘的象征，被誉为小龙。泥塑生肖蛇以翠绿色蛇身搭配金色花纹，盘旋而上的造型展现灵动与优雅。' },
  7:  { name:'马', alias:'午马', element:'火', color:'棕色、栗色', emoji:'🐴', img:'images/zodiac/horse.jpg',    number:'2、3、7', trait:'自由奔放', hexes:['#92400e','#78350f'], desc:'马象征着自由与活力。泥塑生肖马以栗棕色马身搭配深棕鬃毛，昂首挺胸的姿态展现奔腾不息的生命力。' },
  8:  { name:'羊', alias:'未羊', element:'土', color:'白色、粉色', emoji:'🐏', img:'images/zodiac/goat.jpg',     number:'2、7',   trait:'温和善良', hexes:['#e5e7eb','#ec4899'], desc:'羊温和善良，象征美好与吉祥。泥塑生肖羊乳白色的身体搭配粉色面部点缀，神态温驯安详，传递着宁静致远的意境。' },
  9:  { name:'猴', alias:'申猴', element:'金', color:'棕色、黄色', emoji:'🐵', img:'images/zodiac/monkey.jpg',   number:'1、8',   trait:'聪慧敏捷', hexes:['#92400e','#eab308'], desc:'猴聪明伶俐，象征智慧与活力。泥塑生肖猴以棕色猴身搭配浅黄肤色，灵活的姿态展现猴子淘气可爱的一面。' },
  10: { name:'鸡', alias:'酉鸡', element:'金', color:'黄色、红色', emoji:'🐔', img:'images/zodiac/rooster.jpg',  number:'5、7、8', trait:'自信精致', hexes:['#eab308','#dc2626'], desc:'鸡自信昂扬，象征光明与希望。泥塑生肖鸡金黄色鸡身搭配红色鸡冠，昂首挺胸的姿态充满精气神。' },
  11: { name:'狗', alias:'戌狗', element:'土', color:'棕色、黄色', emoji:'🐶', img:'images/zodiac/dog.jpg',      number:'3、4、9', trait:'忠诚可靠', hexes:['#92400e','#eab308'], desc:'狗忠诚正直，是人类最好的朋友。泥塑生肖狗棕黄色身体搭配深棕斑点，憨态可掬中透出忠诚守护的气质。' },
  12: { name:'猪', alias:'亥猪', element:'水', color:'粉色、白色', emoji:'🐷', img:'images/zodiac/pig.jpg',      number:'2、5、8', trait:'豁达乐观', hexes:['#ec4899','#e5e7eb'], desc:'猪知足常乐，象征福气与圆满。泥塑生肖猪粉红色身体搭配白色点缀，憨厚可爱的造型传达知足者常乐的生活智慧。' },
}

onMounted(() => { z.value = map[route.params.id] })
</script>
