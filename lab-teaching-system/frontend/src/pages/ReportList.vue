<template>
  <div class="reports-page">
    <h1>实验报告</h1>
    
    <div class="tabs">
      <button 
        :class="{ active: activeTab === 'my' }" 
        @click="activeTab = 'my'"
      >我的报告</button>
      <button 
        v-if="isTeacher" 
        :class="{ active: activeTab === 'pending' }" 
        @click="activeTab = 'pending'"
      >待批改</button>
      <button 
        v-if="isTeacher" 
        :class="{ active: activeTab === 'all' }" 
        @click="activeTab = 'all'"
      >所有报告</button>
    </div>

    <div v-if="activeTab === 'my'" class="my-reports">
      <button @click="showUploadDialog = true" class="btn btn-primary">上传报告</button>
      
      <table class="report-table" v-if="myReports.length > 0">
        <thead>
          <tr>
            <th>课程</th>
            <th>实验项目</th>
            <th>提交时间</th>
            <th>状态</th>
            <th>成绩</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in myReports" :key="report.id">
            <td>{{ report.courseName }}</td>
            <td>{{ report.experimentTitle }}</td>
            <td>{{ formatDate(report.submittedAt) }}</td>
            <td>
              <span :class="'status-' + report.status">{{ report.status }}</span>
            </td>
            <td>{{ report.grade || '-' }}</td>
            <td>
              <button @click="viewReport(report)" class="btn-sm">查看</button>
              <button v-if="report.status === 'SUBMITTED'" @click="deleteReport(report.id)" class="btn-sm btn-danger">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else class="no-data">暂无报告</p>
    </div>

    <div v-if="activeTab === 'pending' && isTeacher" class="pending-reports">
      <table class="report-table" v-if="pendingReports.length > 0">
        <thead>
          <tr>
            <th>学生</th>
            <th>课程</th>
            <th>实验项目</th>
            <th>提交时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in pendingReports" :key="report.id">
            <td>{{ report.studentName }}</td>
            <td>{{ report.courseName }}</td>
            <td>{{ report.experimentTitle }}</td>
            <td>{{ formatDate(report.submittedAt) }}</td>
            <td>
              <button @click="viewReport(report)" class="btn-sm">查看</button>
              <button @click="gradeReport(report)" class="btn-sm btn-success">评分</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else class="no-data">无待批改报告</p>
    </div>

    <div v-if="activeTab === 'all' && isTeacher" class="all-reports">
      <table class="report-table" v-if="allReports.length > 0">
        <thead>
          <tr>
            <th>学生</th>
            <th>课程</th>
            <th>实验项目</th>
            <th>提交时间</th>
            <th>状态</th>
            <th>成绩</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in allReports" :key="report.id">
            <td>{{ report.studentName }}</td>
            <td>{{ report.courseName }}</td>
            <td>{{ report.experimentTitle }}</td>
            <td>{{ formatDate(report.submittedAt) }}</td>
            <td>
              <span :class="'status-' + report.status">{{ report.status }}</span>
            </td>
            <td>{{ report.grade || '-' }}</td>
            <td>
              <button @click="viewReport(report)" class="btn-sm">查看</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else class="no-data">暂无报告</p>
    </div>

    <!-- 上传报告对话框 -->
    <div v-if="showUploadDialog" class="dialog">
      <div class="dialog-content">
        <h2>上传实验报告</h2>
        <form @submit.prevent="submitReport">
          <div class="form-group">
            <label>选择实验</label>
            <select v-model="newReport.experimentId" required>
              <option value="">-- 请选择 --</option>
              <option v-for="exp in myExperiments" :key="exp.id" :value="exp.id">
                {{ exp.courseName }} - {{ exp.title }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>选择文件</label>
            <input type="file" @change="onFileChange" required />
          </div>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">上传</button>
            <button type="button" @click="showUploadDialog = false" class="btn">取消</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 评分对话框 -->
    <div v-if="showGradeDialog" class="dialog">
      <div class="dialog-content">
        <h2>评分</h2>
        <form @submit.prevent="submitGrade">
          <div class="form-group">
            <label>成绩 (0-100)</label>
            <input type="number" v-model.number="gradeForm.score" min="0" max="100" required />
          </div>
          <div class="form-group">
            <label>评语</label>
            <textarea v-model="gradeForm.comment" rows="4"></textarea>
          </div>
          <div class="form-actions">
            <button type="submit" class="btn btn-primary">提交评分</button>
            <button type="button" @click="showGradeDialog = false" class="btn">取消</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'ReportList',
  data() {
    return {
      activeTab: 'my',
      myReports: [],
      pendingReports: [],
      allReports: [],
      myExperiments: [],
      showUploadDialog: false,
      showGradeDialog: false,
      newReport: {
        experimentId: '',
        file: null
      },
      gradeForm: {
        reportId: null,
        score: '',
        comment: ''
      },
      currentUser: null,
      isTeacher: false
    }
  },
  mounted() {
    this.loadUser()
    this.loadReports()
  },
  methods: {
    loadUser() {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      this.currentUser = user
      this.isTeacher = user.role === 'TEACHER'
    },
    async loadReports() {
      try {
        if (!this.isTeacher) {
          const response = await api.get(`/api/student/reports/my?userId=${this.currentUser.id}`)
          this.myReports = response.data
        } else {
          const pending = await api.get('/api/teacher/reports/pending')
          this.pendingReports = pending.data
          const all = await api.get('/api/reports')
          this.allReports = all.data
        }
      } catch (error) {
        console.error('Failed to load reports:', error)
        this.$message?.error('加载报告失败')
      }
    },
    async loadMyExperiments() {
      try {
        const response = await api.get('/api/public/courses')
        const experiments = []
        for (const course of response.data) {
          const expResponse = await api.get(`/api/public/courses/${course.id}/experiments`)
          expResponse.data.forEach(exp => {
            exp.courseName = course.name
            experiments.push(exp)
          })
        }
        this.myExperiments = experiments
      } catch (error) {
        console.error('Failed to load experiments:', error)
      }
    },
    onFileChange(event) {
      this.newReport.file = event.target.files[0]
    },
    async submitReport() {
      try {
        if (!this.newReport.experimentId || !this.newReport.file) {
          alert('请选择实验和文件')
          return
        }
        const formData = new FormData()
        formData.append('userId', this.currentUser.id)
        formData.append('experimentId', this.newReport.experimentId)
        formData.append('file', this.newReport.file)
        
        const response = await api.post('/api/student/reports', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })
        alert('报告上传成功')
        this.showUploadDialog = false
        this.newReport = { experimentId: '', file: null }
        this.loadReports()
      } catch (error) {
        alert('报告上传失败: ' + error.message)
      }
    },
    viewReport(report) {
      alert(`报告详情: ${report.experimentTitle}\n状态: ${report.status}`)
    },
    async deleteReport(reportId) {
      if (confirm('确定删除此报告?')) {
        try {
          await api.delete(`/api/student/reports/${reportId}`)
          alert('报告已删除')
          this.loadReports()
        } catch (error) {
          alert('删除失败')
        }
      }
    },
    gradeReport(report) {
      this.gradeForm.reportId = report.id
      this.gradeForm.score = ''
      this.gradeForm.comment = ''
      this.showGradeDialog = true
    },
    async submitGrade() {
      try {
        const grade = {
          reportId: this.gradeForm.reportId,
          graderId: this.currentUser.id,
          score: this.gradeForm.score,
          comment: this.gradeForm.comment
        }
        await api.post('/api/teacher/grades', grade)
        alert('评分已提交')
        this.showGradeDialog = false
        this.loadReports()
      } catch (error) {
        alert('评分提交失败: ' + error.message)
      }
    },
    formatDate(date) {
      if (!date) return '-'
      return new Date(date).toLocaleString('zh-CN')
    }
  },
  watch: {
    showUploadDialog(newVal) {
      if (newVal) this.loadMyExperiments()
    }
  }
}
</script>

<style scoped>
.reports-page {
  padding: 20px;
}

.reports-page h1 {
  color: #333;
  margin-bottom: 20px;
}

.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
}

.tabs button {
  background: none;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  color: #666;
  font-size: 16px;
  border-bottom: 3px solid transparent;
  transition: all 0.3s ease;
}

.tabs button.active {
  color: #667eea;
  border-bottom-color: #667eea;
}

.tabs button:hover {
  color: #667eea;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
}

.btn:hover {
  background: #f5f5f5;
}

.btn-primary {
  background: #667eea;
  color: white;
  border-color: #667eea;
  margin-bottom: 20px;
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

.btn-success {
  color: #48bb78;
  border-color: #48bb78;
}

.btn-success:hover {
  background: #e0ffe0;
}

.report-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.report-table th {
  background: #f5f5f5;
  padding: 12px;
  text-align: left;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
}

.report-table td {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.report-table tr:hover {
  background: #fafafa;
}

.status-SUBMITTED {
  color: #ed8936;
  background: #fff5e6;
  padding: 4px 8px;
  border-radius: 3px;
}

.status-GRADED {
  color: #48bb78;
  background: #e6ffe6;
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
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  font-family: inherit;
}

.form-group textarea {
  resize: vertical;
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
