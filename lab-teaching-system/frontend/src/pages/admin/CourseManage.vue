<template>
  <div class="course-manage">
    <h1>课程管理</h1>
    
    <button @click="showAddDialog = true" class="btn btn-primary">新增课程</button>

    <div class="search-bar">
      <input v-model="searchKeyword" type="text" placeholder="搜索课程...">
    </div>

    <table class="manage-table" v-if="filteredCourses.length > 0">
      <thead>
        <tr>
          <th>课程代码</th>
          <th>课程名称</th>
          <th>学期</th>
          <th>授课教师</th>
          <th>实验数</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in filteredCourses" :key="course.id">
          <td>{{ course.code }}</td>
          <td>{{ course.name }}</td>
          <td>{{ course.term }}</td>
          <td>{{ course.teacherName }}</td>
          <td>{{ course.experimentCount || 0 }}</td>
          <td>
            <button @click="editCourse(course)" class="btn-sm">编辑</button>
            <button @click="deleteCourse(course.id)" class="btn-sm btn-danger">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
    <p v-else class="no-data">暂无课程</p>

    <!-- 添加/编辑课程对话框 -->
    <div v-if="showAddDialog" class="dialog">
      <div class="dialog-content">
        <h2>{{ editingCourse?.id ? '编辑课程' : '新增课程' }}</h2>
        <form @submit.prevent="saveCourse">
          <div class="form-group">
            <label>课程代码</label>
            <input v-model="editingCourse.code" required />
          </div>
          <div class="form-group">
            <label>课程名称</label>
            <input v-model="editingCourse.name" required />
          </div>
          <div class="form-group">
            <label>学期</label>
            <input v-model="editingCourse.term" required />
          </div>
          <div class="form-group">
            <label>授课教师</label>
            <select v-model="editingCourse.teacherId" required>
              <option value="">-- 请选择 --</option>
              <option v-for="teacher in teachers" :key="teacher.id" :value="teacher.id">
                {{ teacher.fullname }}
              </option>
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
  name: 'CourseManage',
  data() {
    return {
      courses: [],
      teachers: [],
      searchKeyword: '',
      showAddDialog: false,
      editingCourse: {
        code: '',
        name: '',
        term: '',
        teacherId: ''
      }
    }
  },
  computed: {
    filteredCourses() {
      return this.courses.filter(c => 
        c.code.includes(this.searchKeyword) || 
        c.name.includes(this.searchKeyword)
      )
    }
  },
  mounted() {
    this.loadCourses()
    this.loadTeachers()
  },
  methods: {
    async loadCourses() {
      try {
        const response = await api.get('/api/admin/courses')
        this.courses = response.data
      } catch (error) {
        console.error('Failed to load courses:', error)
        alert('加载课程失败')
      }
    },
    async loadTeachers() {
      try {
        const response = await api.get('/api/admin/users?role=TEACHER')
        this.teachers = response.data
      } catch (error) {
        console.error('Failed to load teachers:', error)
      }
    },
    editCourse(course) {
      this.editingCourse = { ...course }
      this.showAddDialog = true
    },
    async saveCourse() {
      try {
        if (this.editingCourse.id) {
          await api.put(`/api/admin/courses/${this.editingCourse.id}`, this.editingCourse)
          alert('课程已更新')
        } else {
          await api.post('/api/admin/courses', this.editingCourse)
          alert('课程已创建')
        }
        this.showAddDialog = false
        this.editingCourse = { code: '', name: '', term: '', teacherId: '' }
        this.loadCourses()
      } catch (error) {
        alert('保存失败: ' + error.message)
      }
    },
    async deleteCourse(id) {
      if (!confirm('确定删除此课程?')) return
      try {
        await api.delete(`/api/admin/courses/${id}`)
        alert('课程已删除')
        this.loadCourses()
      } catch (error) {
        alert('删除失败')
      }
    }
  }
}
</script>

<style scoped>
.course-manage {
  padding: 20px;
}

.course-manage h1 {
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

.search-bar {
  margin-bottom: 20px;
}

.search-bar input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 250px;
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
