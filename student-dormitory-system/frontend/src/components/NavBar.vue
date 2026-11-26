<template>
  <nav style="padding:12px;background:#2d8cf0;color:#fff;display:flex;gap:12px;align-items:center;flex-wrap:wrap">
    <router-link to="/" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">📊 仪表盘</router-link>
    <router-link to="/dorms" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">🏠 宿舍管理</router-link>
    <router-link to="/students" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">👤 学生管理</router-link>
    <router-link to="/stays" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">📝 入住管理</router-link>
    <router-link to="/statistics" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">📈 统计</router-link>
    <router-link to="/alerts" style="color:#fff;text-decoration:none;padding:8px 12px;border-radius:4px;transition:background 0.2s" @click.prevent="" @mouseenter="e => e.target.style.background='rgba(255,255,255,0.2)'" @mouseleave="e => e.target.style.background='transparent'">⚠️ 提示</router-link>
  </nav>
</template>

<script setup>
</script>

<style scoped>
a{color:#fff;text-decoration:none}
</style>
