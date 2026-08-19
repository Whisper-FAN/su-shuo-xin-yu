<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:8rem 1.5rem 5rem">
    <div style="max-width:720px;margin:0 auto">

      <!-- Progress -->
      <div v-if="!submitted" style="text-align:center;margin-bottom:3rem">
        <p style="font-family:'Noto Serif SC',serif;color:#8c4a2f;font-size:1.1rem;margin-bottom:0.5rem">
          第 <span style="font-size:2rem;font-weight:700">{{ currentIndex + 1 }}</span> / 24 步
        </p>
        <h1 style="font-family:'Noto Serif SC',serif;font-size:2rem;color:#1c1c18;letter-spacing:0.2em">探 寻 本 我</h1>
        <p style="font-size:0.8rem;color:#86736c;margin-top:0.5rem">已有 <span id="busuanzi_value_site_pv" style="font-weight:700;color:#8c4a2f">0</span> 人次访问</p>
        <div style="max-width:320px;margin:1rem auto 0;height:2px;background:rgba(217,194,186,0.3);border-radius:1px;position:relative">
          <div style="position:absolute;top:0;left:0;height:100%;background:#8c4a2f;border-radius:1px;transition:width 0.6s" :style="{width:progressPercent+'%'}"></div>
        </div>
      </div>

      <!-- Question Card -->
      <div v-if="!submitted" style="background:#fff;border-radius:1.5rem;padding:2.5rem;box-shadow:0 4px 30px rgba(140,74,47,0.06);border:1px solid rgba(217,194,186,0.3);position:relative">
        <div style="position:absolute;top:1.5rem;right:1.5rem;width:3rem;height:3rem;border:2px solid rgba(140,74,47,0.1);border-radius:0.5rem;display:flex;align-items:center;justify-content:center;opacity:0.3">
          <span style="font-family:'Noto Serif SC',serif;color:#8c4a2f;font-size:1.2rem">心</span>
        </div>
        <p style="font-family:'Noto Serif SC',serif;font-size:1.25rem;line-height:1.8;color:#1c1c18;text-align:center;font-style:italic;opacity:0.9;margin:0 0 2.5rem">
          "{{ currentQuestion?.questionText }}"
        </p>
        <div style="max-width:360px;margin:0 auto;display:flex;flex-direction:column;gap:0.75rem">
          <button v-for="opt in currentOptions" :key="opt.id"
            @click="selectOption(opt.score)"
            style="width:100%;padding:0.75rem 1.25rem;border-radius:0.75rem;display:flex;justify-content:space-between;align-items:center;border:1px solid rgba(140,74,47,0.1);background:rgba(255,255,255,0.5);cursor:pointer;transition:all 0.35s;font-family:'Noto Serif SC',serif;font-size:1.05rem;color:#53433d"
            :style="selectedAnswer === opt.score
              ? 'background:#8c4a2f;color:#fff;border-color:#8c4a2f;box-shadow:inset 0 4px 12px rgba(0,0,0,0.2),0 8px 20px rgba(140,74,47,0.2);transform:scale(0.98)'
              : ''">
            <span>{{ opt.optionText }}</span>
            <div style="width:1.5rem;height:1.5rem;border-radius:50%;border:2px solid rgba(140,74,47,0.3);display:flex;align-items:center;justify-content:center"
                 :style="selectedAnswer === opt.score ? 'border-color:rgba(255,255,255,0.5)' : ''">
              <div style="width:0.7rem;height:0.7rem;border-radius:50%;background:transparent"
                   :style="selectedAnswer === opt.score ? 'background:#fff' : ''"></div>
            </div>
          </button>
        </div>
      </div>

      <!-- Navigation -->
      <div v-if="!submitted" style="max-width:720px;margin:2rem auto 0;display:flex;justify-content:space-between;align-items:center">
        <button @click="prevQuestion" :disabled="currentIndex===0"
          style="border:1px solid rgba(140,74,47,0.15);padding:0.6rem 2rem;border-radius:999px;background:transparent;color:#53433d;font-weight:600;cursor:pointer;display:flex;align-items:center;gap:0.5rem;opacity:0.6"
          :style="currentIndex===0 ? 'opacity:0.3;cursor:not-allowed' : ''">← 上一题</button>

        <button v-if="currentIndex < questions.length - 1" @click="nextQuestion" :disabled="selectedAnswer===null"
          style="background:#8c4a2f;color:#fff;padding:0.6rem 2.5rem;border-radius:999px;font-weight:600;cursor:pointer;border:none;font-size:1rem;display:flex;align-items:center;gap:0.5rem;box-shadow:0 8px 20px rgba(140,74,47,0.2)"
          :style="selectedAnswer===null ? 'opacity:0.4;cursor:not-allowed;box-shadow:none' : ''">下一题 →</button>

        <button v-else @click="submit" :disabled="selectedAnswer===null"
          style="background:#8c4a2f;color:#fff;padding:0.6rem 2.5rem;border-radius:999px;font-weight:600;cursor:pointer;border:none;font-size:1rem;box-shadow:0 8px 20px rgba(140,74,47,0.2)"
          :style="selectedAnswer===null ? 'opacity:0.4;cursor:not-allowed;box-shadow:none' : ''">查看结果</button>
      </div>

      <!-- Computing -->
      <div v-if="submitted" style="text-align:center;padding:5rem 0">
        <div style="font-size:4rem;animation:float 2s ease-in-out infinite">🔮</div>
        <h2 style="font-family:'Noto Serif SC',serif;font-size:1.5rem;color:#1c1c18;margin-top:1rem">正在匹配你的生肖守护神...</h2>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

// Table 4-2: 12 zodiac center points [EI, SN, TF, JP] - inlined to prevent tree-shaking
const ZC = [[+0.7,+0.6,+0.5,+0.6],[-0.8,+0.7,+0.6,+0.8],[+0.9,-0.5,+0.8,+0.7],[-0.6,+0.5,-0.7,-0.5],[+0.8,-0.6,+0.7,+0.8],[-0.5,-0.7,+0.4,+0.3],[+0.9,+0.4,-0.6,-0.7],[-0.4,+0.6,-0.5,+0.2],[+0.8,-0.4,+0.3,-0.6],[+0.6,+0.5,+0.7,+0.9],[-0.3,+0.5,-0.4,+0.4],[+0.5,-0.3,-0.6,-0.5]]
const ZN = ['鼠','牛','虎','兔','龙','蛇','马','羊','猴','鸡','狗','猪']
const ZP = {ACHIEVE:[2,4,9],HARMONY:[1,3,7],EXPLORE:[6,8,11],RELATION:[10,0]}
function matchZodiac(questions,answers){
  const ei=n('E/I'),sn=n('S/N'),tf=n('T/F'),jp=n('J/P'),vt=g(),vl={ACHIEVE:'成就型',HARMONY:'安稳型',EXPLORE:'自由型',RELATION:'关系型'}
  const D=ZC.map(c=>Math.sqrt((ei-c[0])**2+(sn-c[1])**2+(tf-c[2])**2+(jp-c[3])**2))
  let b=0,s=1;if(D[1]<D[0]){b=1;s=0}
  for(let i=2;i<12;i++){if(D[i]<D[b]){s=b;b=i}else if(D[i]<D[s])s=i}
  if(D[s]-D[b]<.05){const p=ZP[vt]||[];if(p.includes(s)&&!p.includes(b))b=s}
  return{zodiacId:b+1,zodiacName:ZN[b],vector:{ei,sn,tf,jp},valueType:vt,valueLabel:vl[vt]||'安稳型'}
  function n(dim){let r=0,c=0;for(const q of questions){if(q.dimension!==dim)continue;const sc=answers[q.id];if(sc==null)continue;r+=(q.positiveScore&&q.positiveScore[0]===dim[0])?sc:-sc;c++}if(c===0)return 0;return Math.max(-1,Math.min(1,r/(c*2)))}
  function g(){const sc={ACHIEVE:0,HARMONY:0,EXPLORE:0,RELATION:0};for(const q of questions){if(q.dimension!=='VALUE')continue;const s=answers[q.id];if(s!=null&&q.positiveScore)sc[q.positiveScore]=(sc[q.positiveScore]||0)+s}return Object.entries(sc).sort((a,b)=>b[1]-a[1])[0][0]}
}

const router = useRouter()
const currentIndex = ref(0)
const selectedAnswer = ref(null)
const answers = ref({})
const submitted = ref(false)

const currentQuestion = computed(() => questions.value[currentIndex.value])
const currentOptions = computed(() => currentQuestion.value?.options || [])
const progressPercent = computed(() => questions.value.length ? ((currentIndex.value + 1) / questions.value.length) * 100 : 0)

function selectOption(score) { selectedAnswer.value = score }
function nextQuestion() {
  if (selectedAnswer.value === null) return
  answers.value[currentQuestion.value.id] = selectedAnswer.value
  if (currentIndex.value < questions.value.length - 1) {
    currentIndex.value++
    selectedAnswer.value = answers.value[questions.value[currentIndex.value]?.id] ?? null
  }
}
function prevQuestion() {
  if (currentIndex.value > 0) {
    currentIndex.value--
    selectedAnswer.value = answers.value[questions.value[currentIndex.value]?.id] ?? null
  }
}

function submit() {
  if (selectedAnswer.value !== null) answers.value[currentQuestion.value.id] = selectedAnswer.value
  const result = matchZodiac(questions.value, answers.value)
  const record = {
    recordId: Date.now(),
    zodiacId: result.zodiacId,
    zodiacName: result.zodiacName,
    vector: result.vector,
    valueType: result.valueType,
    valueLabel: result.valueLabel,
  }
  sessionStorage.setItem('testResult', JSON.stringify(record))
  router.push('/result/' + record.recordId)
}

// ========== 24 questions with options (from 性格测试题目.docx + 商业计划书) ==========
const questions = ref([
  ...buildDimQuestions('E/I', [
    '在大吴泥塑博物馆参观时，我更愿意一个人静静观看每件作品，而不是和同伴边走边聊。',
    '看到泥塑师傅现场演示贴塑技艺，我会先迫不及待地尝试，而不是仔细观看一遍再动手。',
    '周末我更愿意约朋友去潮州古城看泥塑展，而不是宅在家里刷手机。',
    '参加非遗讲座时，我通常是那个主动提问、交流想法的人，而不是默默记笔记。',
    '完成测试后我更愿意立刻分享到朋友圈，找到自己的生肖守护神，并且看看朋友们也是什么。',
  ]),
  ...buildDimQuestions('S/N', [
    '欣赏泥塑作品时，我会首先注意到人物表情、衣纹纹路等精细细节，而不是整体感受。',
    '开始制作一个生肖泥塑前，我会先画一张精确的图纸再动手。',
    '看一个非遗短视频时，我会被具体的工艺步骤和成品效果吸引，而不是其背后的文化隐喻。',
    '我喜欢凭直觉挥洒，而不是按照说明书一步步组装玩具或者材料包。',
    '看到英雄传奇人物泥塑，我会先想到这个英雄背后的历史故事，而不是泥塑本身的制作技法。',
  ]),
  ...buildDimQuestions('T/F', [
    '看到社交媒体上非遗传承困境的新闻，我会先关心经济层面改善的可行性，而不是被深深触动情绪。',
    '如果有人说某个泥塑作品有瑕疵要销毁，我会觉得只要情感寓意在，形态不完美也可以。',
    '评价一个生肖泥塑，我认为技艺精湛比故事感人更重要。',
    '团队制作非遗项目赶进度时，我会直接指出效率问题，而不是顾及成员感受。',
    '看到朋友因捏坏泥塑而沮丧，我会先安慰他，然后再想办法补救。',
  ]),
  ...buildDimQuestions('J/P', [
    '我会提前规划好参观泥塑博物馆的路线和时间，而不是随性逛逛。',
    '开始捏泥时，我喜欢先把所有工具材料准备齐全、摆放整齐，再开始动手。',
    '我更喜欢给人定制一个小泥偶，而不是按照既定图纸来完成作品。',
    '在夜市美食街上，我会先逛完所有摊位再决定吃什么，而不是看到第一个想吃的就买。',
    '发现非遗DIY课程超时后，我会感到焦虑，而不是觉得多出来的都是赚到的。',
  ]),
  ...[
    { id: 21, questionText: '我最愿意成为传承人：技艺精湛被博物馆收藏的大师级匠人，作品被美术馆收藏。', dimension: 'VALUE', questionType: 'personality', positiveScore: 'ACHIEVE', negativeScore: '', options: likertOptions() },
    { id: 22, questionText: '我理想的周末是：和朋友一起逛文创市集，帮摊主出谋划策，享受轻松社交。', dimension: 'VALUE', questionType: 'personality', positiveScore: 'HARMONY', negativeScore: '', options: likertOptions() },
    { id: 23, questionText: '为了去看最地道的泥塑古建，我可以临时改变原定学业安排。', dimension: 'VALUE', questionType: 'personality', positiveScore: 'EXPLORE', negativeScore: '', options: likertOptions() },
    { id: 24, questionText: '收到朋友亲手捏的一个歪歪扭扭的泥偶，比收到精致量产款更让我开心。', dimension: 'VALUE', questionType: 'personality', positiveScore: 'RELATION', negativeScore: '', options: likertOptions() },
  ],
])

function buildDimQuestions(dim, texts) {
  let baseId = dim === 'E/I' ? 1 : dim === 'S/N' ? 6 : dim === 'T/F' ? 11 : 16
  const first = dim[0]
  const second = dim[dim.length - 1]
  return texts.map((text, i) => ({
    id: baseId + i,
    questionText: text,
    dimension: dim,
    questionType: 'scenario',
    // First 3 per dim: first letter is positive; last 2 per dim: second letter is positive (reverse scored)
    positiveScore: i < 3 ? first : second,
    negativeScore: i < 3 ? second : first,
    options: likertOptions(),
  }))
}

function likertOptions() {
  return [
    { id: 0, optionText: '完全不符合', score: -2 },
    { id: 1, optionText: '不符合', score: -1 },
    { id: 2, optionText: '中立', score: 0 },
    { id: 3, optionText: '符合', score: 1 },
    { id: 4, optionText: '完全符合', score: 2 },
  ]
}
</script>

<style scoped>
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-15px); }
}
</style>
