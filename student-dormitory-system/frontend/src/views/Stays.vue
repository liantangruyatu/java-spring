<template>
  <div style="padding:16px">
    <h2>入住记录</h2>
    <div style="margin-bottom:16px">
      <button @click="showAddStay = true" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer">办理入住</button>
    </div>
    <table style="width:100%;border-collapse:collapse">
      <thead style="background:#f5f5f5">
        <tr>
          <th style="border:1px solid #ddd;padding:8px">学号</th>
          <th style="border:1px solid #ddd;padding:8px">姓名</th>
          <th style="border:1px solid #ddd;padding:8px">宿舍</th>
          <th style="border:1px solid #ddd;padding:8px">床号</th>
          <th style="border:1px solid #ddd;padding:8px">入住日期</th>
          <th style="border:1px solid #ddd;padding:8px">状态</th>
          <th style="border:1px solid #ddd;padding:8px">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="r in stays" :key="r.id" style="border-bottom:1px solid #ddd">
          <td style="border:1px solid #ddd;padding:8px">{{ r.student?.studentNo }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ r.student?.name }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ r.dorm?.roomNo }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ r.bedNo }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ r.checkinDate }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ r.active ? '在住' : '已退' }}</td>
          <td style="border:1px solid #ddd;padding:8px">
            <button v-if="r.active" @click="checkoutStay(r.id)" style="padding:4px 8px;background:#ff7f50;color:#fff;border:none;cursor:pointer;margin-right:4px">退宿</button>
            <button @click="deleteStay(r.id)" style="padding:4px 8px;background:#ff9c6e;color:#fff;border:none;cursor:pointer">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showAddStay" style="position:fixed;top:0;left:0;right:0;bottom:0;background:rgba(0,0,0,0.5);display:flex;align-items:center;justify-content:center;z-index:999">
      <div style="background:#fff;padding:24px;border-radius:4px;min-width:400px">
        <h3>办理入住</h3>
        <div style="margin-bottom:12px"><label>学生：<select v-model.number="form.studentId" style="padding:4px;width:100%;margin-top:4px"><option value="">请选择</option><option v-for="s in students" :key="s.id" :value="s.id">{{ s.studentNo }} - {{ s.name }}</option></select></label></div>
        <div style="margin-bottom:12px"><label>宿舍：<select v-model.number="form.dormId" style="padding:4px;width:100%;margin-top:4px"><option value="">请选择</option><option v-for="d in dorms" :key="d.id" :value="d.id">{{ d.roomNo }}</option></select></label></div>
        <div style="margin-bottom:12px"><label>床号：<input v-model.number="form.bedNo" type="number" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="margin-bottom:12px"><label>入住日期：<input v-model="form.checkinDate" type="date" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="display:flex;gap:8px">
          <button @click="saveStay" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer;flex:1">保存</button>
          <button @click="closeForm" style="padding:8px 16px;background:#ccc;border:none;cursor:pointer;flex:1">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const stays = ref([])
const students = ref([])
const dorms = ref([])
const showAddStay = ref(false)
const form = ref({ studentId: null, dormId: null, bedNo: null, checkinDate: '' })

const load = async () => {
  const res = await api.get('/stays')
  stays.value = res.data
}

const loadStudents = async () => {
  const res = await api.get('/students')
  students.value = res.data
}

const loadDorms = async () => {
  const res = await api.get('/dorms')
  dorms.value = res.data
}

const saveStay = async () => {
  const payload = { 
    student: { id: form.value.studentId }, 
    dorm: { id: form.value.dormId }, 
    bedNo: form.value.bedNo, 
    checkinDate: form.value.checkinDate,
    active: true
  }
  await api.post('/stays', payload)
  closeForm()
  load()
}

const checkoutStay = async (id) => {
  if (confirm('确定退宿？')) {
    await api.put(`/stays/${id}`, { ...stays.value.find(s => s.id === id), active: false, checkoutDate: new Date().toISOString().split('T')[0] })
    load()
  }
}

const deleteStay = async (id) => {
  if (confirm('确定删除？')) {
    await api.delete(`/stays/${id}`)
    load()
  }
}

const closeForm = () => {
  showAddStay.value = false
  form.value = { studentId: null, dormId: null, bedNo: null, checkinDate: '' }
}

onMounted(() => {
  load()
  loadStudents()
  loadDorms()
})
</script>
