/**
 * 十二生肖性格匹配算法 - 纯前端实现
 *
 * 商业计划书 表4-1 + 表4-2:
 *   Step 1: 24题 → 四个维度归一化向量 + 价值观类型
 *   Step 2: 欧氏距离计算与12生肖中心点
 *   Step 3: 边界模糊规则 (delta < 0.05 → 价值观优先)
 */

// Table 4-2: 12 zodiac center points [EI, SN, TF, JP]
const CENTERS = [
  [+0.7, +0.6, +0.5, +0.6],  // 鼠
  [-0.8, +0.7, +0.6, +0.8],  // 牛
  [+0.9, -0.5, +0.8, +0.7],  // 虎
  [-0.6, +0.5, -0.7, -0.5],  // 兔
  [+0.8, -0.6, +0.7, +0.8],  // 龙
  [-0.5, -0.7, +0.4, +0.3],  // 蛇
  [+0.9, +0.4, -0.6, -0.7],  // 马
  [-0.4, +0.6, -0.5, +0.2],  // 羊
  [+0.8, -0.4, +0.3, -0.6],  // 猴
  [+0.6, +0.5, +0.7, +0.9],  // 鸡
  [-0.3, +0.5, -0.4, +0.4],  // 狗
  [+0.5, -0.3, -0.6, -0.5],  // 猪
]

// Table 4-1: value-type priority sets (index into CENTERS)
const VALUE_PRIORITY = {
  ACHIEVE: [2, 4, 9],   // 虎(3) 龙(5) 鸡(10)
  HARMONY:  [1, 3, 7],   // 牛(2) 兔(4) 羊(8)
  EXPLORE:  [6, 8, 11],  // 马(7) 猴(9) 猪(12)
  RELATION: [10, 0],     // 狗(11) 鼠(1)
}

const ZODIAC_NAMES = ['鼠', '牛', '虎', '兔', '龙', '蛇', '马', '羊', '猴', '鸡', '狗', '猪']

/**
 * @param {Array} questions - [{id, dimension, positiveScore, negativeScore}]
 * @param {Object} answers  - {questionId: score}
 * @returns {{zodiacId:number, zodiacName:string, vector:{ei,sn,tf,jp}, valueType:string, valueLabel:string}}
 */
export function matchZodiac(questions, answers) {
  // Step 1-2: normalize each dimension to [-1, +1]
  const ei = normalize('E/I', questions, answers)
  const sn = normalize('S/N', questions, answers)
  const tf = normalize('T/F', questions, answers)
  const jp = normalize('J/P', questions, answers)

  // Step 3: value type
  const valueType = getValueType(questions, answers)
  const valueLabels = { ACHIEVE: '成就型', HARMONY: '安稳型', EXPLORE: '自由型', RELATION: '关系型' }

  // Step 4: Euclidean distance to each center
  const distances = CENTERS.map(c =>
    Math.sqrt((ei - c[0]) ** 2 + (sn - c[1]) ** 2 + (tf - c[2]) ** 2 + (jp - c[3]) ** 2)
  )

  // Find nearest + second-nearest
  let best = 0, second = 1
  if (distances[1] < distances[0]) { best = 1; second = 0 }
  for (let i = 2; i < 12; i++) {
    if (distances[i] < distances[best]) { second = best; best = i }
    else if (distances[i] < distances[second]) second = i
  }

  // Boundary blurring (delta < 0.05)
  if (distances[second] - distances[best] < 0.05) {
    const priority = VALUE_PRIORITY[valueType] || []
    const aIn = priority.includes(best), bIn = priority.includes(second)
    if (aIn && !bIn) { /* keep best */ }
    else if (bIn && !aIn) best = second
    else if (distances[second] < distances[best]) best = second
  }

  return {
    zodiacId: best + 1,
    zodiacName: ZODIAC_NAMES[best],
    vector: { ei, sn, tf, jp },
    valueType,
    valueLabel: valueLabels[valueType] || '安稳型',
  }
}

function normalize(dim, questions, answers) {
  let raw = 0, cnt = 0
  for (const q of questions) {
    if (q.dimension !== dim) continue
    const score = answers[q.id]
    if (score == null) continue
    // Direction: positiveScore determines which way counts as positive
    raw += (q.positiveScore && q.positiveScore[0] === dim[0]) ? score : -score
    cnt++
  }
  if (cnt === 0) return 0
  const val = raw / (cnt * 2)
  return Math.max(-1, Math.min(1, val))
}

function getValueType(questions, answers) {
  const scores = { ACHIEVE: 0, HARMONY: 0, EXPLORE: 0, RELATION: 0 }
  for (const q of questions) {
    if (q.dimension !== 'VALUE') continue
    const score = answers[q.id]
    if (score != null && q.positiveScore) {
      scores[q.positiveScore] = (scores[q.positiveScore] || 0) + score
    }
  }
  return Object.entries(scores).sort((a, b) => b[1] - a[1])[0][0]
}
