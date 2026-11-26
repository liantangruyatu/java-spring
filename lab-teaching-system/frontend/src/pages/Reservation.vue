<template>
  <div class="reservation-page">
    <h1>实验预约</h1>

    <div class="reservation-form-container">
      <form @submit.prevent="submit" class="reservation-form">
        <div class="form-section">
          <h3>第一步：选择实验项目</h3>
          <div class="form-group">
            <label>课程</label>
            <select v-model="selectedCourse" @change="loadExperiments">
              <option value="">-- 请选择课程 --</option>
              <option v-for="course in courses" :key="course.id" :value="course.id">
                {{ course.name }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label>实验项目</label>
            <select v-model="formData.experimentId" required>
              <option value="">-- 请选择 --</option>
              <option v-for="exp in experiments" :key="exp.id" :value="exp.id">
                {{ exp.title }} ({{ exp.durationMinutes }}分钟)
              </option>
            </select>
          </div>
        </div>

        <div class="form-section">
          <h3>第二步：选择实验室和时间</h3>
          <div class="form-group">
            <label>实验室</label>
            <select v-model="formData.labId" required>
              <option value="">-- 请选择 --</option>
              <option v-for="lab in availableLabs" :key="lab.id" :value="lab.id">
                {{ lab.name }} (容量: {{ lab.capacity }}人, 位置: {{ lab.location }})
              </option>
            </select>
          </div>

          <div class="form-group">
            <label>开始时间</label>
            <input type="datetime-local" v-model="formData.startTime" required />
          </div>

          <div class="form-group">
            <label>结束时间</label>
            <input type="datetime-local" v-model="formData.endTime" required />
          </div>
        </div>

        <div class="form-section">
          <h3>预约摘要</h3>
          <div class="summary">
            <div v-if="formData.experimentId" class="summary-item">
              <span>实验项目:</span>
              <strong>{{ selectedExperimentTitle }}</strong>
            </div>
            <div v-if="formData.labId" class="summary-item">
              <span>实验室:</span>
              <strong>{{ selectedLabName }}</strong>
            </div>
            <div v-if="formData.startTime" class="summary-item">
              <span>预约时间:</span>
              <strong>{{ formatDate(formData.startTime) }} - {{ formatDate(formData.endTime) }}</strong>
            </div>
          </div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">提交预约</button>
          <button type="reset" @click="reset" class="btn">重置</button>
        </div>

        <div v-if="reservationMessage" :class="['message', reservationStatus]">
          {{ reservationMessage }}
        </div>
      </form>

      <div class="my-reservations">
        <h2>我的预约</h2>
        <table v-if="myReservations.length > 0" class="reservation-table">
          <thead>
            <tr>
              <th>实验项目</th>
              <th>实验室</th>
              <th>预约时间</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="res in myReservations" :key="res.id">
              <td>{{ res.experimentTitle }}</td>
              <td>{{ res.labName }}</td>
              <td>{{ formatDate(res.startTime) }} - {{ formatDate(res.endTime) }}</td>
              <td>
                <span :class="'status-' + res.status">{{ res.status }}</span>
              </td>
              <td>
                <button 
                  v-if="res.status === 'PENDING'" 
                  @click="cancelReservation(res.id)" 
                  class="btn-sm btn-danger"
                >取消</button>
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else class="no-data">暂无预约</p>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'Reservation',
  data() {
    return {
      courses: [],
      experiments: [],
      labs: [],
      myReservations: [],
      selectedCourse: '',
      formData: {
        experimentId: '',
        labId: '',
        startTime: '',
        endTime: '',
        userId: null
      },
      reservationMessage: '',
      reservationStatus: ''
    }
  },
  computed: {
    availableLabs() {
      return this.labs.filter(l => l.status === 'AVAILABLE')
    },
    selectedExperimentTitle() {
      const exp = this.experiments.find(e => e.id == this.formData.experimentId)
      return exp ? exp.title : ''
    },
    selectedLabName() {
      const lab = this.labs.find(l => l.id == this.formData.labId)
      return lab ? lab.name : ''
    }
  },
  mounted() {
    this.loadUser()
    this.loadCourses()
    this.loadLabs()
    this.loadMyReservations()
  },
  methods: {
    loadUser() {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      this.formData.userId = user.id
    },
    async loadCourses() {
      try {
        const response = await api.get('/api/public/courses')
        this.courses = response.data
      } catch (error) {
        console.error('Failed to load courses:', error)
      }
    },
    async loadExperiments() {
      if (!this.selectedCourse) {
        this.experiments = []
        return
      }
      try {
        const response = await api.get(`/api/public/courses/${this.selectedCourse}/experiments`)
        this.experiments = response.data
      } catch (error) {
        console.error('Failed to load experiments:', error)
      }
    },
    async loadLabs() {
      try {
        const response = await api.get('/api/admin/labs')
        this.labs = response.data
      } catch (error) {
        console.error('Failed to load labs:', error)
      }
    },
    async loadMyReservations() {
      try {
        if (this.formData.userId) {
          const response = await api.get(`/api/student/reservations/my?userId=${this.formData.userId}`)
          this.myReservations = response.data
        }
      } catch (error) {
        console.error('Failed to load reservations:', error)
      }
    },
    async submit() {
      try {
        const reservation = {
          userId: this.formData.userId,
          experimentId: parseInt(this.formData.experimentId),
          labId: parseInt(this.formData.labId),
          startTime: new Date(this.formData.startTime).toISOString(),
          endTime: new Date(this.formData.endTime).toISOString(),
          status: 'PENDING'
        }
        
        const response = await api.post('/api/student/reservations', reservation)
        this.reservationMessage = '预约成功！状态：' + response.data.status
        this.reservationStatus = 'success'
        setTimeout(() => {
          this.reset()
          this.loadMyReservations()
        }, 2000)
      } catch (error) {
        this.reservationMessage = '预约失败：' + (error.response?.data?.message || error.message)
        this.reservationStatus = 'error'
      }
    },
    reset() {
      this.selectedCourse = ''
      this.experiments = []
      this.formData = {
        experimentId: '',
        labId: '',
        startTime: '',
        endTime: '',
        userId: this.formData.userId
      }
      this.reservationMessage = ''
    },
    async cancelReservation(reservationId) {
      if (!confirm('确定取消此预约?')) return
      try {
        await api.post(`/api/student/reservations/${reservationId}/cancel`)
        alert('预约已取消')
        this.loadMyReservations()
      } catch (error) {
        alert('取消失败: ' + error.message)
      }
    },
    formatDate(dateStr) {
      if (!dateStr) return '-'
      return new Date(dateStr).toLocaleString('zh-CN')
    }
  }
}
</script>

<style scoped>
.reservation-page {
  padding: 20px;
}

.reservation-page h1 {
  color: #333;
  margin-bottom: 30px;
}

.reservation-form-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.reservation-form {
  background: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.form-section {
  margin-bottom: 25px;
  padding-bottom: 25px;
  border-bottom: 1px solid #f0f0f0;
}

.form-section h3 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 16px;
}

.form-section:last-of-type {
  border-bottom: none;
}

.form-group {
  margin-bottom: 15px;
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

.summary {
  background: #f5f5f5;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 15px;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  color: #666;
}

.summary-item strong {
  color: #333;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
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
  flex: 1;
}

.btn-primary:hover {
  background: #5568d3;
}

.message {
  margin-top: 15px;
  padding: 12px;
  border-radius: 4px;
  text-align: center;
}

.message.success {
  background: #e6ffe6;
  color: #22543d;
  border: 1px solid #9ae6a6;
}

.message.error {
  background: #ffe6e6;
  color: #742a2a;
  border: 1px solid #fc8181;
}

.my-reservations {
  background: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.my-reservations h2 {
  margin-top: 0;
  color: #333;
  font-size: 18px;
  margin-bottom: 20px;
}

.reservation-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.reservation-table th {
  background: #f5f5f5;
  padding: 12px;
  text-align: left;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
}

.reservation-table td {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.reservation-table tr:hover {
  background: #fafafa;
}

.status-PENDING {
  color: #ed8936;
  background: #fff5e6;
  padding: 4px 8px;
  border-radius: 3px;
}

.status-APPROVED {
  color: #48bb78;
  background: #e6ffe6;
  padding: 4px 8px;
  border-radius: 3px;
}

.status-REJECTED {
  color: #f56565;
  background: #ffe6e6;
  padding: 4px 8px;
  border-radius: 3px;
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

.no-data {
  text-align: center;
  color: #999;
  padding: 20px;
}

@media (max-width: 1024px) {
  .reservation-form-container {
    grid-template-columns: 1fr;
  }
}
</style>

