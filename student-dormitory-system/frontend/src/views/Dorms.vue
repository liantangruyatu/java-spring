<template>
  <div style="padding:16px">
    <h2>宿舍管理</h2>
    <div style="margin-bottom:16px">
      <button @click="showAddDorm = true" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer">新增宿舍</button>
    </div>
    <table style="width:100%;border-collapse:collapse">
      <thead style="background:#f5f5f5">
        <tr>
          <th style="border:1px solid #ddd;padding:8px">房号</th>
          <th style="border:1px solid #ddd;padding:8px">楼栋</th>
          <th style="border:1px solid #ddd;padding:8px">楼层</th>
          <th style="border:1px solid #ddd;padding:8px">房型</th>
          <th style="border:1px solid #ddd;padding:8px">容量</th>
          <th style="border:1px solid #ddd;padding:8px">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="d in dorms" :key="d.id" style="border-bottom:1px solid #ddd">
          <td style="border:1px solid #ddd;padding:8px">{{ d.roomNo }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ d.building?.name }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ d.floor }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ d.roomType }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ d.capacity }}</td>
          <td style="border:1px solid #ddd;padding:8px">
            <button @click="editDorm(d)" style="padding:4px 8px;background:#2db7f5;color:#fff;border:none;cursor:pointer;margin-right:4px">编辑</button>
            <button @click="deleteDorm(d.id)" style="padding:4px 8px;background:#ff9c6e;color:#fff;border:none;cursor:pointer">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showAddDorm" style="position:fixed;top:0;left:0;right:0;bottom:0;background:rgba(0,0,0,0.5);display:flex;align-items:center;justify-content:center;z-index:999">
      <div style="background:#fff;padding:24px;border-radius:4px;min-width:400px">
        <h3>{{ editingDorm?.id ? '编辑宿舍' : '新增宿舍' }}</h3>
        <div style="margin-bottom:12px">
          <label>房号：<input v-model="form.roomNo" style="padding:4px;width:100%;margin-top:4px"></label>
        </div>
        <div style="margin-bottom:12px">
          <label>楼栋：<select v-model.number="form.buildingId" style="padding:4px;width:100%;margin-top:4px">
            <option value="">请选择</option>
            <option v-for="b in buildings" :key="b.id" :value="b.id">{{ b.name }}</option>
          </select></label>
        </div>
        <div style="margin-bottom:12px">
          <label>楼层：<input v-model.number="form.floor" type="number" style="padding:4px;width:100%;margin-top:4px"></label>
        </div>
        <div style="margin-bottom:12px">
          <label>房型：<input v-model="form.roomType" style="padding:4px;width:100%;margin-top:4px"></label>
        </div>
        <div style="margin-bottom:12px">
          <label>容量：<input v-model.number="form.capacity" type="number" style="padding:4px;width:100%;margin-top:4px"></label>
        </div>
        <div style="display:flex;gap:8px">
          <button @click="saveDorm" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer;flex:1">保存</button>
          <button @click="closeForm" style="padding:8px 16px;background:#ccc;border:none;cursor:pointer;flex:1">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const dorms = ref([])
const buildings = ref([])
const showAddDorm = ref(false)
const editingDorm = ref(null)
const form = ref({ roomNo: '', buildingId: null, floor: null, roomType: '', capacity: null })

const load = async () => {
  const res = await api.get('/dorms')
  dorms.value = res.data
}

const loadBuildings = async () => {
  const res = await api.get('/buildings')
  buildings.value = res.data
}

const editDorm = (d) => {
  editingDorm.value = d
  form.value = { roomNo: d.roomNo, buildingId: d.building.id, floor: d.floor, roomType: d.roomType, capacity: d.capacity }
  showAddDorm.value = true
}

const saveDorm = async () => {
  const payload = { roomNo: form.value.roomNo, floor: form.value.floor, roomType: form.value.roomType, capacity: form.value.capacity, building: { id: form.value.buildingId } }
  if (editingDorm.value?.id) {
    await api.put(`/dorms/${editingDorm.value.id}`, payload)
  } else {
    await api.post('/dorms', payload)
  }
  closeForm()
  load()
}

const deleteDorm = async (id) => {
  if (confirm('确定删除？')) {
    await api.delete(`/dorms/${id}`)
    load()
  }
}

const closeForm = () => {
  showAddDorm.value = false
  editingDorm.value = null
  form.value = { roomNo: '', buildingId: null, floor: null, roomType: '', capacity: null }
}

onMounted(() => {
  load()
  loadBuildings()
})
</script>
