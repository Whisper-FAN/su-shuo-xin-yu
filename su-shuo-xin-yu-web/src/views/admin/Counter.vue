<template>
  <div style="background:#fcf9f3;min-height:100vh;padding:6rem 1.5rem 3rem;font-family:Inter,sans-serif">
    <div style="max-width:480px;margin:0 auto">
      <h1 style="font-family:'Noto Serif SC',serif;font-size:1.5rem;color:#1c1c18;margin-bottom:2rem">测试计数管理</h1>

      <div style="background:#fff;border-radius:1rem;padding:1.5rem;box-shadow:0 2px 10px rgba(0,0,0,0.04);margin-bottom:1.5rem">
        <p style="font-size:0.85rem;color:#86736c;margin-bottom:0.5rem">当前显示总数</p>
        <p style="font-size:3rem;font-weight:700;color:#8c4a2f;margin:0;font-family:'Noto Serif SC',serif">{{ total }}</p>
        <p style="font-size:0.75rem;color:#86736c;margin-top:0.5rem">基数 {{ base }} + 本地完成 {{ local }}</p>
      </div>

      <div style="background:#fff;border-radius:1rem;padding:1.5rem;box-shadow:0 2px 10px rgba(0,0,0,0.04)">
        <label style="font-size:0.85rem;color:#1c1c18;display:block;margin-bottom:0.5rem">设置基数</label>
        <div style="display:flex;gap:0.75rem">
          <input v-model="input" type="number" min="0" style="flex:1;padding:0.65rem 1rem;border:1px solid #d9c2ba;border-radius:0.75rem;font-size:1rem;color:#1c1c18;outline:none" @keyup.enter="save" />
          <button @click="save" style="background:#8c4a2f;color:#fff;padding:0.65rem 1.5rem;border-radius:0.75rem;font-weight:600;border:none;cursor:pointer;font-size:0.9rem">保存</button>
        </div>
        <p v-if="saved" style="font-size:0.8rem;color:#16a34a;margin:0.75rem 0 0">已更新！刷新测试页面查看</p>
      </div>

      <div style="margin-top:1.5rem;padding:1rem;background:rgba(140,74,47,0.04);border-radius:0.75rem">
        <p style="font-size:0.75rem;color:#86736c;margin:0;line-height:1.6">
          说明：每个浏览器独立计数（localStorage），不会重复计同设备。基数可在此页随时调整。总数 = 基数 + 所有设备本地计数之和的估算值。
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const base = ref(parseInt(localStorage.getItem('test_base') || '3280', 10))
const local = ref(parseInt(localStorage.getItem('test_done') || '0', 10))
const total = ref(base.value + local.value)
const input = ref(String(base.value))
const saved = ref(false)

function save() {
  const v = Math.max(0, parseInt(input.value, 10) || 0)
  localStorage.setItem('test_base', String(v))
  base.value = v
  total.value = base.value + local.value
  saved.value = true
  setTimeout(() => saved.value = false, 3000)
}
</script>
