<template>
  <footer style="background:#f6f3ed;border-top:1px solid rgba(217,194,186,0.25);margin-top:6rem;font-family:Inter,sans-serif">
    <div style="max-width:1100px;margin:0 auto;padding:3rem 1.5rem">
      <div style="display:flex;justify-content:space-between;align-items:flex-start;flex-wrap:wrap;gap:2rem;margin-bottom:2rem">
        <!-- Brand -->
        <div style="min-width:160px">
          <div style="font-family:'Noto Serif SC',serif;font-size:1.2rem;font-weight:700;color:#1c1c18;margin-bottom:0.25rem">塑说心语</div>
          <div style="font-size:0.8rem;color:rgba(83,67,61,0.5)">大吴泥塑文化传承平台</div>
        </div>

        <!-- Nav -->
        <div style="display:flex;gap:2.5rem">
          <router-link to="/stories" style="font-size:0.85rem;color:#53433d;text-decoration:none">文化起源</router-link>
          <router-link to="/zodiac" style="font-size:0.85rem;color:#53433d;text-decoration:none">作品赏析</router-link>
          <router-link to="/test" style="font-size:0.85rem;color:#53433d;text-decoration:none">性格测试</router-link>
          <router-link to="/gallery" style="font-size:0.85rem;color:#53433d;text-decoration:none">手艺工坊</router-link>
        </div>

        <!-- Badges -->
        <div style="display:flex;align-items:center;gap:1.5rem;flex-shrink:0">
          <div style="background:#fff;padding:0.3rem 0.5rem;border-radius:0.25rem;border:1px solid rgba(0,0,0,0.04)">
            <img :src="'images/team/school-name.png'" alt="广东金融学院" style="height:2rem;width:auto;object-fit:contain" />
          </div>
          <img :src="'images/team/contest-full.png'" alt="国创赛" style="height:3rem;width:auto;object-fit:contain" />
        </div>
      </div>

      <div style="border-top:1px solid rgba(217,194,186,0.15);padding-top:1.5rem;text-align:center">
        <p style="font-size:0.75rem;color:rgba(83,67,61,0.35);margin:0">
          &copy; 2026 塑说心语 · 大吴泥塑文化传承与保护
        </p>
      </div>
    </div>
  </footer>
</template>
