<template>
  <div style="padding:16px">
    <h2>统计信息</h2>
    <div style="display:grid;grid-template-columns:1fr 1fr;gap:16px">
      <div>
        <h3>空闲床位统计</h3>
        <table style="width:100%;border-collapse:collapse">
          <thead style="background:#f5f5f5">
            <tr>
              <th style="border:1px solid #ddd;padding:8px">房号</th>
              <th style="border:1px solid #ddd;padding:8px">楼栋</th>
              <th style="border:1px solid #ddd;padding:8px">容量</th>
              <th style="border:1px solid #ddd;padding:8px">已住</th>
              <th style="border:1px solid #ddd;padding:8px">空闲</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="v in vacantBeds" :key="v.dormId" style="border-bottom:1px solid #ddd">
              <td style="border:1px solid #ddd;padding:8px">{{ v.roomNo }}</td>
              <td style="border:1px solid #ddd;padding:8px">{{ v.building }}</td>
              <td style="border:1px solid #ddd;padding:8px">{{ v.capacity }}</td>
              <td style="border:1px solid #ddd;padding:8px">{{ v.occupied }}</td>
              <td style="border:1px solid #ddd;padding:8px;color:#2d8cf0;font-weight:bold">{{ v.vacant }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div>
        <h3>班级住宿分布</h3>
        <table style="width:100%;border-collapse:collapse">
          <thead style="background:#f5f5f5">
            <tr>
              <th style="border:1px solid #ddd;padding:8px">班级</th>
              <th style="border:1px solid #ddd;padding:8px">人数</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(count, cls) in classDistribution" :key="cls" style="border-bottom:1px solid #ddd">
              <td style="border:1px solid #ddd;padding:8px">{{ cls }}</td>
              <td style="border:1px solid #ddd;padding:8px;font-weight:bold">{{ count }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const vacantBeds = ref([])
const classDistribution = ref({})

const loadStats = async () => {
  try {
    const vacRes = await api.get('/stats/vacant-beds')
    vacantBeds.value = vacRes.data
    const classRes = await api.get('/stats/class-distribution')
    classDistribution.value = classRes.data
  } catch (err) {
    console.error('统计加载失败:', err)
  }
}

onMounted(() => { loadStats() })
</script>
