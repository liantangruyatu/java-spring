<template>
  <div style="padding:16px">
    <h2>学生管理</h2>
    <div style="margin-bottom:16px">
      <button @click="showAddStudent = true" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer">新增学生</button>
    </div>
    <table style="width:100%;border-collapse:collapse">
      <thead style="background:#f5f5f5">
        <tr>
          <th style="border:1px solid #ddd;padding:8px">学号</th>
          <th style="border:1px solid #ddd;padding:8px">姓名</th>
          <th style="border:1px solid #ddd;padding:8px">班级</th>
          <th style="border:1px solid #ddd;padding:8px">电话</th>
          <th style="border:1px solid #ddd;padding:8px">性别</th>
          <th style="border:1px solid #ddd;padding:8px">操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="s in students" :key="s.id" style="border-bottom:1px solid #ddd">
          <td style="border:1px solid #ddd;padding:8px">{{ s.studentNo }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ s.name }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ s.studentClass }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ s.phone }}</td>
          <td style="border:1px solid #ddd;padding:8px">{{ s.gender }}</td>
          <td style="border:1px solid #ddd;padding:8px">
            <button @click="editStudent(s)" style="padding:4px 8px;background:#2db7f5;color:#fff;border:none;cursor:pointer;margin-right:4px">编辑</button>
            <button @click="deleteStudent(s.id)" style="padding:4px 8px;background:#ff9c6e;color:#fff;border:none;cursor:pointer">删除</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showAddStudent" style="position:fixed;top:0;left:0;right:0;bottom:0;background:rgba(0,0,0,0.5);display:flex;align-items:center;justify-content:center;z-index:999">
      <div style="background:#fff;padding:24px;border-radius:4px;min-width:400px">
        <h3>{{ editingStudent?.id ? '编辑学生' : '新增学生' }}</h3>
        <div style="margin-bottom:12px"><label>学号：<input v-model="form.studentNo" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="margin-bottom:12px"><label>姓名：<input v-model="form.name" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="margin-bottom:12px"><label>班级：<input v-model="form.studentClass" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="margin-bottom:12px"><label>电话：<input v-model="form.phone" style="padding:4px;width:100%;margin-top:4px"></label></div>
        <div style="margin-bottom:12px"><label>性别：<select v-model="form.gender" style="padding:4px;width:100%;margin-top:4px"><option value="">请选择</option><option value="男">男</option><option value="女">女</option></select></label></div>
        <div style="display:flex;gap:8px">
          <button @click="saveStudent" style="padding:8px 16px;background:#2d8cf0;color:#fff;border:none;cursor:pointer;flex:1">保存</button>
          <button @click="closeForm" style="padding:8px 16px;background:#ccc;border:none;cursor:pointer;flex:1">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../api'

const students = ref([])
const showAddStudent = ref(false)
const editingStudent = ref(null)
const form = ref({ studentNo: '', name: '', studentClass: '', phone: '', gender: '' })

const load = async () => {
  const res = await api.get('/students')
  students.value = res.data
}

const editStudent = (s) => {
  editingStudent.value = s
  form.value = { studentNo: s.studentNo, name: s.name, studentClass: s.studentClass, phone: s.phone, gender: s.gender }
  showAddStudent.value = true
}

const saveStudent = async () => {
  if (editingStudent.value?.id) {
    await api.put(`/students/${editingStudent.value.id}`, form.value)
  } else {
    await api.post('/students', form.value)
  }
  closeForm()
  load()
}

const deleteStudent = async (id) => {
  if (confirm('确定删除？')) {
    await api.delete(`/students/${id}`)
    load()
  }
}

const closeForm = () => {
  showAddStudent.value = false
  editingStudent.value = null
  form.value = { studentNo: '', name: '', studentClass: '', phone: '', gender: '' }
}

onMounted(() => { load() })
</script>
