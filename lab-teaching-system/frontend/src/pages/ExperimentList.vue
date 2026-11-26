<template>
  <div class="experiment-list-page">
    <div class="breadcrumb">
      <router-link to="/courses">课程列表</router-link>
      <span>&gt;</span>
      <span>{{ courseName }}</span>
    </div>

    <h1>{{ courseName }} - 实验项目</h1>

    <div v-if="experiments.length > 0" class="experiments-grid">
      <div v-for="exp in experiments" :key="exp.id" class="experiment-card">
        <div class="exp-header">
          <h3>{{ exp.title }}</h3>
          <span class="duration">{{ exp.durationMinutes }}分钟</span>
        </div>
        
        <p class="exp-description">{{ exp.description }}</p>

        <div class="exp-footer">
          <button @click="goToReservation(exp.id)" class="btn btn-primary">
            📅 预约实验
          </button>
        </div>
      </div>
    </div>
    <p v-else class="no-data">暂无实验项目</p>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'ExperimentList',
  props: {
    courseId: [String, Number]
  },
  data() {
    return {
      experiments: [],
      course: null,
      courseName: ''
    }
  },
  mounted() {
    this.loadExperiments()
  },
  methods: {
    async loadExperiments() {
      try {
        const id = this.courseId || this.$route.params.courseId
        if (!id) return
        
        const response = await api.get(`/api/public/courses/${id}/experiments`)
        this.experiments = response.data
        
        const coursesResponse = await api.get('/api/public/courses')
        const course = coursesResponse.data.find(c => c.id == id)
        if (course) {
          this.courseName = course.name
        }
      } catch (error) {
        console.error('Failed to load experiments:', error)
        this.courseName = '实验项目'
      }
    },
    goToReservation(experimentId) {
      this.$router.push('/reservation?experimentId=' + experimentId)
    }
  }
}
</script>

<style scoped>
.experiment-list-page {
  padding: 20px;
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
  font-size: 14px;
  color: #666;
}

.breadcrumb a {
  color: #667eea;
  text-decoration: none;
  cursor: pointer;
}

.breadcrumb a:hover {
  text-decoration: underline;
}

.breadcrumb span {
  color: #999;
}

.experiment-list-page h1 {
  color: #333;
  margin-bottom: 30px;
}

.experiments-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.experiment-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
}

.experiment-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.exp-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.exp-header h3 {
  margin: 0;
  color: #333;
  font-size: 18px;
  flex: 1;
}

.duration {
  background: #f0f0f0;
  color: #666;
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  white-space: nowrap;
  margin-left: 10px;
}

.exp-description {
  color: #666;
  font-size: 14px;
  line-height: 1.6;
  margin: 0 0 15px 0;
  flex: 1;
}

.exp-footer {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
  flex: 1;
  text-align: center;
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

.no-data {
  text-align: center;
  color: #999;
  padding: 40px 20px;
}
</style>

