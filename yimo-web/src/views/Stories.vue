<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:7rem 1.5rem 5rem">
    <div style="max-width:960px;margin:0 auto">
      <div style="text-align:center;margin-bottom:4rem">
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;font-weight:700;color:#1c1c18;margin-bottom:0.5rem">文化起源</h1>
        <p style="color:#53433d;font-family:Inter,sans-serif">探寻大吴泥塑近八百年的文化根脉与匠心传承</p>
      </div>
      <div style="display:flex;flex-direction:column;gap:4rem">
        <section v-for="(s, i) in stories" :key="i">
          <img v-if="s.img" :src="s.img" :alt="s.title" style="max-width:480px;height:14rem;object-fit:contain;display:block;margin:0 auto 1.5rem;border-radius:1rem;margin-bottom:1.5rem;box-shadow:0 2px 8px rgba(0,0,0,0.08)" @error="e => e.target.remove()" />
          <h2 style="font-family:'Noto Serif SC',serif;font-size:1.3rem;font-weight:700;color:#1c1c18;margin:0 0 0.75rem">{{ s.title }}</h2>
          <p style="color:#53433d;line-height:1.8;font-size:1.05rem;white-space:pre-line;font-family:Inter,sans-serif;margin:0">{{ s.body }}</p>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { base } from '@/utils/base'
const stories = [
  {
    title: '大吴泥塑：国家级非物质文化遗产',
    body: '大吴泥塑起源于南宋理宗嘉熙元年（1237年），距今近八百年。据史料记载，吴静山带着家眷从福建漳州到潮州大吴村定居，以制作、售卖泥塑为生，从此这门手艺世代相传，达到户户有作坊、人人会泥塑的盛况。全村曾有四百余名艺人投身此业，作品远销东南亚，成为潮汕华侨寄托乡愁的重要文化载体。\n\n2008年，大吴泥塑被列入第一批国家级非物质文化遗产名录，与天津泥人张、无锡惠山泥人并称中国三大泥塑艺术。两者以写实和写意见长，而大吴泥塑则以独创的贴塑技法独树一帜。',
    img: '/images/zodiac/all-zodiac.png'
  },
  {
    title: '制作工艺：五步七技',
    body: '大吴泥塑制作过程分为挖泥、炼泥、捏塑、烧坯和彩绘五个环节，制作时以雕、塑、捏、贴、刻、印和彩为主要技术手段，十分考验制作者的耐心与仔细程度。\n\n其最核心的工艺特色是独创的贴塑技法，即压泥成片，褶片成衣——艺人在塑造时根据人物造型动态先打出筒身基础，将特制陶泥反复捶打醒泥后拍成薄如纸片的泥片，再将这些泥片剪作衣袂佩饰，层层叠加成型，就像给泥人穿衣服一样，甚至连内衣、外衣、鞋帽、裙带都按照由内到外的顺序独立制作。\n\n著名作家冯骥才评价大吴泥塑人物衣服是一层一层地穿上去的，就如同给一个活人穿衣服一样，并亲笔为传承人题写女娲在民间。',
    img: '/images/products/workshop/IMG_0031.JPG'
  },
  {
    title: '题材：立体的潮汕文化百科全书',
    body: '大吴泥塑的题材深深扎根于潮汕文化沃土，内容多取材于地方传统戏剧潮剧的情节、民间传说、章回体小说中大众喜闻乐见的题材，以及民间生活中富有趣味的内容。\n\n包括戏曲人物、脸谱童头、真人肖像、神佛像和儿童玩具（杂锦）等五大类，被誉为立体的潮汕文化百科全书。本项目所依托的十二生肖系列作品——传承人吴漫历年创作的蛇年圆润造型、英歌舞时迁形象、兔帅等——正是大吴泥塑杂锦门类在当代创新的典型代表。',
    img: '/images/products/premium/fulushou.jpg'
  },
  {
    title: '十二生肖与当代传承',
    body: '项目依托的十二生肖泥塑系列主要取材自大吴泥塑维清工艺美术研究所创作的生肖泥塑作品。其历年十二生肖系列已推出含英歌舞蛇、金蛇、兔帅、英歌舞时迁形象以及龙、虎、牛、猴等多个生肖形象，其中英歌舞时迁拿蛇的形象融合了国家级非遗英歌舞与大吴泥塑，妙趣横生地展现了非遗跨界创新的活力，圆润可爱的卡通化造型也充分体现了国潮美学与泥塑技艺的融合趋势。\n\n吴漫每年坚持生肖泥塑的创作——是对来年的一种愿望和期盼。此外，吴维清、吴克煌等传承人已开发的红桃粿口哨、潮剧Q版人物、冰箱贴、小摆件等平价文创产品已初步验证了年轻人对低价非遗文创的消费意愿，基础文创产品价格从12元至39元不等。',
    img: '/images/products/creative/hongtaoguo.jpg'
  }
]
</script>
