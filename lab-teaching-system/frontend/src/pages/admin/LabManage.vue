<template>
  <div class="lab-manage">
    <h1>实验室管理</h1>
    
    <button @click="showAddDialog = true" class="btn btn-primary">新增实验室</button>

    <div class="filter-bar">
      <select v-model="filterStatus">
        <option value="">所有状态</option>
        <option value="AVAILABLE">可用</option>
        <option value="IN_USE">使用中</option>
        <option value="MAINTENANCE">维护中</option>
      </select>
    </div>

    <table class="manage-table" v-if="filteredLabs.length > 0">
      <thead>
        <tr>
          <th>实验室名称</th>
          <th>位置</th>
          <th>容量(人)</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="lab in filteredLabs" :key="lab.id">
          <td>{{ lab.name }}</td>
          <td>{{ lab.location }}</td>
          <td>{{ lab.capacity }}</td>
          <td>
            <span :class="'status-' + lab.status">{{ lab.status }}</span>
          </td>
          <td>
            <button @click="editLab(lab)" class="btn-sm">编辑</button>
            <button @click="deleteLab(lab.id)" class="btn-sm btn-danger">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else class="no-data">暂无实验室</p>

    <!-- 添加/编辑实验室对话框 -->
    <div v-if="showAddDialog" class="dialog">
      <div class="dialog-content">
        <h2>{{ editingLab?.id ? '编辑实验室' : '新增实验室' }}</h2>
        <form @submit.prevent="saveLab">
          <div class="form-group">
            <label>实验室名称</label>
            <input v-model="editingLab.name" required />
          </div>
          <div class="form-group">
            <label>位置</label>
            <input v-model="editingLab.location" required />
          </div>
          <div class="form-group">
            <label>容量(人数)</label>
            <input v-model.number="editingLab.capacity" type="number" required />
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="editingLab.status" required>
              <option value="AVAILABLE">可用</option>
              <option value="IN_USE">使用中</option>
              <option value="MAINTENANCE">维护中</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">保存</button>
            <button type="button" @click="showAddDialog = false" class="btn">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../../services/api'

export default {
  name: 'LabManage',
  data() {
    return {
      labs: [],
      filterStatus: '',
      showAddDialog: false,
      editingLab: {
        name: '',
        location: '',
        capacity: '',
        status: 'AVAILABLE'
      }
    }
  },
  computed: {
    filteredLabs() {
      if (!this.filterStatus) return this.labs
      return this.labs.filter(l => l.status === this.filterStatus)
    }
  },
  mounted() {
    this.loadLabs()
  },
  methods: {
    async loadLabs() {
      try {
        const response = await api.get('/api/admin/labs')
        this.labs = response.data
      } catch (error) {
        console.error('Failed to load labs:', error)
        alert('加载实验室失败')
      }
    },
    editLab(lab) {
      this.editingLab = { ...lab }
      this.showAddDialog = true
    },
    async saveLab() {
      try {
        if (this.editingLab.id) {
          await api.put(`/api/admin/labs/${this.editingLab.id}`, this.editingLab)
          alert('实验室已更新')
        } else {
          await api.post('/api/admin/labs', this.editingLab)
          alert('实验室已创建')
        }
        this.showAddDialog = false
        this.editingLab = { name: '', location: '', capacity: '', status: 'AVAILABLE' }
        this.loadLabs()
      } catch (error) {
        alert('保存失败: ' + error.message)
      }
    },
    async deleteLab(id) {
      if (!confirm('确定删除此实验室?')) return
      try {
        await api.delete(`/api/admin/labs/${id}`)
        alert('实验室已删除')
        this.loadLabs()
      } catch (error) {
        alert('删除失败')
      }
    }
  }
}
</script>

<style scoped>
.lab-manage {
  padding: 20px;
}

.lab-manage h1 {
  color: #333;
  margin-bottom: 20px;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
  margin-bottom: 20px;
}

.btn:hover {
  background: #f5f5f5;
}

.btn-primary {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.btn-primary:hover {
  background: #5568d3;
}

.btn-sm {
  padding: 5px 10px;
  font-size: 14px;
}

.btn-danger {
  color: #f56565;
  border-color: #f56565;
}

.btn-danger:hover {
  background: #ffe0e0;
}

.filter-bar {
  margin-bottom: 20px;
}

.filter-bar select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.manage-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.manage-table th {
  background: #f5f5f5;
  padding: 12px;
  text-align: left;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
}

.manage-table td {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.manage-table tr:hover {
  background: #fafafa;
}

.status-AVAILABLE {
  color: #48bb78;
  background: #e6ffe6;
  padding: 4px 8px;
  border-radius: 3px;
}

.status-IN_USE {
  color: #ed8936;
  background: #fff5e6;
  padding: 4px 8px;
  border-radius: 3px;
}

.status-MAINTENANCE {
  color: #e53e3e;
  background: #ffe6e6;
  padding: 4px 8px;
  border-radius: 3px;
}

.no-data {
  text-align: center;
  color: #999;
  padding: 40px 20px;
}

.dialog {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.dialog-content {
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.2);
  width: 90%;
  max-width: 500px;
}

.dialog-content h2 {
  margin-top: 0;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: #333;
  font-weight: 500;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.form-actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
  margin-top: 20px;
}

.form-actions button {
  padding: 10px 20px;
}
</style>
