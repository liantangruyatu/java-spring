<template>
  <div class="home-page">
    <div class="banner">
      <h1>实验教学管理系统</h1>
      <p>欢迎使用实验教学管理系统 - 课程 | 预约 | 报告 | 成绩</p>
    </div>
    
    <div class="quick-nav">
      <div class="nav-card" @click="goTo('/courses')">
        <div class="icon">📚</div>
        <h3>课程列表</h3>
        <p>浏览所有课程及实验</p>
      </div>
      <div class="nav-card" @click="goTo('/reservation')">
        <div class="icon">📅</div>
        <h3>实验预约</h3>
        <p>预约实验、选择时间</p>
      </div>
      <div class="nav-card" @click="goTo('/reports')">
        <div class="icon">📝</div>
        <h3>实验报告</h3>
        <p>查看、上传报告</p>
      </div>
      <div class="nav-card" @click="goTo('/grades')">
        <div class="icon">⭐</div>
        <h3>成绩查询</h3>
        <p>查看实验成绩</p>
      </div>
    </div>

    <div class="stats" v-if="stats">
      <div class="stat-item">
        <span class="stat-label">我的课程</span>
        <span class="stat-value">{{ stats.courses }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">待批准预约</span>
        <span class="stat-value">{{ stats.pendingReservations }}</span>
      </div>
      <div class="stat-item">
        <span class="stat-label">未评分报告</span>
        <span class="stat-value">{{ stats.pendingReports }}</span>
      </div>
    </div>

    <div v-if="userRole === 'ADMIN'" class="admin-section">
      <h2>管理员功能</h2>
      <div class="admin-cards">
        <div class="admin-card" @click="goTo('/admin/courses')">
          <h4>课程管理</h4>
          <p>创建、编辑、删除课程</p>
        </div>
        <div class="admin-card" @click="goTo('/admin/experiments')">
          <h4>实验管理</h4>
          <p>管理实验项目内容</p>
        </div>
        <div class="admin-card" @click="goTo('/admin/labs')">
          <h4>实验室管理</h4>
          <p>管理实验室资源</p>
        </div>
        <div class="admin-card" @click="goTo('/dashboard')">
          <h4>系统仪表板</h4>
          <p>查看系统统计信息</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'Home',
  data() {
    return {
      stats: null,
      userRole: ''
    }
  },
  mounted() {
    this.loadStats()
    this.loadUserRole()
  },
  methods: {
    goTo(path) {
      this.$router.push(path)
    },
    async loadStats() {
      try {
        const response = await api.get('/api/public/stats')
        this.stats = response.data
      } catch (error) {
        console.log('Stats unavailable')
      }
    },
    loadUserRole() {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      this.userRole = user.role || ''
    }
  }
}
</script>

<style scoped>
.home-page {
  padding: 20px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 60px 20px;
  text-align: center;
  border-radius: 8px;
  margin-bottom: 40px;
}

.banner h1 {
  font-size: 48px;
  margin: 0 0 20px 0;
}

.banner p {
  font-size: 18px;
  margin: 0;
  opacity: 0.9;
}

.quick-nav {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.nav-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 30px 20px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.nav-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  transform: translateY(-2px);
}

.nav-card .icon {
  font-size: 48px;
  margin-bottom: 15px;
}

.nav-card h3 {
  font-size: 18px;
  margin: 10px 0;
  color: #333;
}

.nav-card p {
  font-size: 14px;
  color: #999;
  margin: 0;
}

.stats {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.stat-item {
  background: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stat-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #667eea;
}

.admin-section {
  background: #f9f9f9;
  padding: 30px;
  border-radius: 8px;
  margin-top: 40px;
}

.admin-section h2 {
  color: #333;
  margin-top: 0;
}

.admin-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.admin-card {
  background: white;
  border: 2px solid #667eea;
  border-radius: 8px;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.admin-card:hover {
  background: #f0f0ff;
  transform: translateY(-2px);
}

.admin-card h4 {
  color: #667eea;
  margin-top: 0;
}

.admin-card p {
  font-size: 14px;
  color: #666;
}
</style>
