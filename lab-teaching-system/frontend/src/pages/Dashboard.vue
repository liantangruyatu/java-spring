<template>
  <div class="dashboard">
    <h1>系统仪表板</h1>
    
    <div class="stats-grid">
      <div class="stat-card">
        <h3>总用户数</h3>
        <p class="stat-number">{{ stats.totalUsers }}</p>
      </div>
      <div class="stat-card">
        <h3>课程数</h3>
        <p class="stat-number">{{ stats.totalCourses }}</p>
      </div>
      <div class="stat-card">
        <h3>实验项目</h3>
        <p class="stat-number">{{ stats.totalExperiments }}</p>
      </div>
      <div class="stat-card">
        <h3>实验室</h3>
        <p class="stat-number">{{ stats.totalLabs }}</p>
      </div>
      <div class="stat-card">
        <h3>预约总数</h3>
        <p class="stat-number">{{ stats.totalReservations }}</p>
      </div>
      <div class="stat-card">
        <h3>报告总数</h3>
        <p class="stat-number">{{ stats.totalReports }}</p>
      </div>
    </div>

    <div class="charts">
      <div class="chart-card">
        <h3>预约状态分布</h3>
        <div class="status-list">
          <div><span>已批准:</span> <strong>{{ stats.approvedReservations }}</strong></div>
          <div><span>待批准:</span> <strong>{{ stats.pendingReservations }}</strong></div>
          <div><span>已拒绝:</span> <strong>{{ stats.rejectedReservations }}</strong></div>
          <div><span>已取消:</span> <strong>{{ stats.cancelledReservations }}</strong></div>
        </div>
      </div>

      <div class="chart-card">
        <h3>报告状态分布</h3>
        <div class="status-list">
          <div><span>已提交:</span> <strong>{{ stats.submittedReports }}</strong></div>
          <div><span>已评分:</span> <strong>{{ stats.gradedReports }}</strong></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'Dashboard',
  data() {
    return {
      stats: {
        totalUsers: 0,
        totalCourses: 0,
        totalExperiments: 0,
        totalLabs: 0,
        totalReservations: 0,
        totalReports: 0,
        approvedReservations: 0,
        pendingReservations: 0,
        rejectedReservations: 0,
        cancelledReservations: 0,
        submittedReports: 0,
        gradedReports: 0
      }
    }
  },
  mounted() {
    this.loadStats()
  },
  methods: {
    async loadStats() {
      try {
        // TODO: Create backend endpoint for stats
        // const response = await api.get('/api/admin/stats')
        // this.stats = response.data
        console.log('Stats loading (endpoint not yet created)')
      } catch (error) {
        console.error('Failed to load stats:', error)
      }
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.dashboard h1 {
  color: #333;
  margin-bottom: 30px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  background: white;
  border-left: 4px solid #667eea;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stat-card h3 {
  margin: 0 0 10px 0;
  font-size: 14px;
  color: #666;
}

.stat-number {
  margin: 0;
  font-size: 32px;
  font-weight: bold;
  color: #667eea;
}

.charts {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.chart-card {
  background: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.chart-card h3 {
  margin-top: 0;
  color: #333;
}

.status-list div {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.status-list span {
  color: #666;
}

.status-list strong {
  color: #667eea;
  font-weight: bold;
}
</style>
