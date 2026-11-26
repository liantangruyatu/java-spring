<template>
  <div style="padding:16px">
    <h2>缴费/违纪提示</h2>
    <div style="display:grid;grid-template-columns:1fr 1fr;gap:16px">
      <div style="border:2px solid #ff7f50;padding:16px;border-radius:4px">
        <h3 style="color:#ff7f50">未缴费催缴</h3>
        <div v-if="unpaidPayments.length === 0" style="text-align:center;color:#999">暂无未缴费记录</div>
        <div v-for="p in unpaidPayments" :key="p.id" style="background:#fff8f0;padding:12px;margin-bottom:8px;border-radius:4px">
          <div><strong>{{ p.studentName }}</strong> ({{ p.studentNo }})</div>
          <div style="color:#ff7f50;font-weight:bold">应缴：¥{{ p.amount }}</div>
          <div style="color:#999;font-size:12px">截止日期：{{ p.dueDate }}</div>
          <button @click="markPaid(p.id)" style="padding:4px 8px;background:#ff7f50;color:#fff;border:none;cursor:pointer;margin-top:8px">标记为已缴</button>
        </div>
      </div>
      <div style="border:2px solid #ff4d4f;padding:16px;border-radius:4px">
        <h3 style="color:#ff4d4f">未处理违纪</h3>
        <div v-if="unprocessedViolations.length === 0" style="text-align:center;color:#999">暂无未处理违纪</div>
        <div v-for="v in unprocessedViolations" :key="v.id" style="background:#fff1f0;padding:12px;margin-bottom:8px;border-radius:4px">
          <div><strong>{{ v.studentName }}</strong> ({{ v.studentNo }})</div>
          <div style="color:#ff4d4f;font-weight:bold">违纪：{{ v.description }}</div>
          <div style="color:#999;font-size:12px">发生时间：{{ v.occDate }}</div>
          <button @click="markProcessed(v.id)" style="padding:4px 8px;background:#ff4d4f;color:#fff;border:none;cursor:pointer;margin-top:8px">标记为已处理</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const unpaidPayments = ref([])
const unprocessedViolations = ref([])

const loadAlerts = async () => {
  try {
    const payRes = await api.get('/alerts/unpaid-payments')
    unpaidPayments.value = payRes.data
    const violRes = await api.get('/alerts/unprocessed-violations')
    unprocessedViolations.value = violRes.data
  } catch (err) {
    console.error('提示加载失败:', err)
  }
}

const markPaid = async (id) => {
  if (confirm('确定标记为已缴？')) {
    const payment = unpaidPayments.value.find(p => p.id === id)
    if (payment) {
      await api.put(`/payments/${id}`, { ...payment, paid: true, paidDate: new Date().toISOString().split('T')[0] })
      loadAlerts()
    }
  }
}

const markProcessed = async (id) => {
  if (confirm('确定标记为已处理？')) {
    const violation = unprocessedViolations.value.find(v => v.id === id)
    if (violation) {
      await api.put(`/violations/${id}`, { ...violation, processed: true })
      loadAlerts()
    }
  }
}

onMounted(() => { loadAlerts() })
</script>
