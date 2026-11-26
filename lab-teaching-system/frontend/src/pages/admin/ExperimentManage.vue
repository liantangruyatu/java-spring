<template>
  <div class="experiment-manage">
    <h1>实验项目管理</h1>
    
    <button @click="showAddDialog = true" class="btn btn-primary">新增实验</button>

    <div class="filter-bar">
      <select v-model="filterCourse">
        <option value="">所有课程</option>
        <option v-for="course in courses" :key="course.id" :value="course.id">
          {{ course.name }}
        </option>
      </select>
    </div>

    <table class="manage-table" v-if="filteredExperiments.length > 0">
      <thead>
        <tr>
          <th>课程</th>
          <th>项目名称</th>
          <th>描述</th>
          <th>时长(分钟)</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="exp in filteredExperiments" :key="exp.id">
          <td>{{ exp.courseName }}</td>
          <td>{{ exp.title }}</td>
          <td>{{ exp.description }}</td>
          <td>{{ exp.durationMinutes }}</td>
          <td>
            <button @click="editExperiment(exp)" class="btn-sm">编辑</button>
            <button @click="deleteExperiment(exp.id)" class="btn-sm btn-danger">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else class="no-data">暂无实验项目</p>

    <!-- 添加/编辑实验对话框 -->
    <div v-if="showAddDialog" class="dialog">
      <div class="dialog-content">
        <h2>{{ editingExp?.id ? '编辑实验' : '新增实验' }}</h2>
        <form @submit.prevent="saveExperiment">
          <div class="form-group">
            <label>所属课程</label>
            <select v-model="editingExp.courseId" required>
              <option value="">-- 请选择 --</option>
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>项目名称</label>
            <input v-model="editingExp.title" required />
          </div>
          <div class="form-group">
            <label>项目描述</label>
            <textarea v-model="editingExp.description" rows="4"></textarea>
          </div>
          <div class="form-group">
            <label>时长 (分钟)</label>
            <input v-model.number="editingExp.durationMinutes" type="number" required />
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
  name: 'ExperimentManage',
  data() {
    return {
      courses: [],
      experiments: [],
      filterCourse: '',
      showAddDialog: false,
      editingExp: {
        courseId: '',
        title: '',
        description: '',
        durationMinutes: ''
      }
    }
  },
  computed: {
    filteredExperiments() {
      if (!this.filterCourse) return this.experiments
      return this.experiments.filter(e => e.courseId == this.filterCourse)
    }
  },
  mounted() {
    this.loadCourses()
    this.loadExperiments()
  },
  methods: {
    async loadCourses() {
      try {
        const response = await api.get('/api/admin/courses')
        this.courses = response.data
      } catch (error) {
        console.error('Failed to load courses:', error)
      }
    },
    async loadExperiments() {
      try {
        const response = await api.get('/api/admin/experiments')
        this.experiments = response.data
      } catch (error) {
        console.error('Failed to load experiments:', error)
        alert('加载实验项目失败')
      }
    },
    editExperiment(exp) {
      this.editingExp = { ...exp }
      this.showAddDialog = true
    },
    async saveExperiment() {
      try {
        if (this.editingExp.id) {
          await api.put(`/api/admin/experiments/${this.editingExp.id}`, this.editingExp)
          alert('实验项目已更新')
        } else {
          await api.post('/api/admin/experiments', this.editingExp)
          alert('实验项目已创建')
        }
        this.showAddDialog = false
        this.editingExp = { courseId: '', title: '', description: '', durationMinutes: '' }
        this.loadExperiments()
      } catch (error) {
        alert('保存失败: ' + error.message)
      }
    },
    async deleteExperiment(id) {
      if (!confirm('确定删除此实验?')) return
      try {
        await api.delete(`/api/admin/experiments/${id}`)
        alert('实验项目已删除')
        this.loadExperiments()
      } catch (error) {
        alert('删除失败')
      }
    }
  }
}
</script>

<style scoped>
.experiment-manage {
  padding: 20px;
}

.experiment-manage h1 {
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
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.form-group textarea {
  resize: vertical;
  font-family: inherit;
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
