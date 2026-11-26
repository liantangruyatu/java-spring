<template>
  <div class="courses-page">
    <h1>课程列表</h1>
    
    <div class="search-bar">
      <input v-model="searchKeyword" type="text" placeholder="搜索课程代码或名称...">
    </div>

    <div class="courses-grid" v-if="filteredCourses.length > 0">
      <div v-for="course in filteredCourses" :key="course.id" class="course-card">
        <h3>{{ course.code }}</h3>
        <p class="course-name">{{ course.name }}</p>
        <p class="course-info">
          <span>学期: {{ course.term }}</span>
        </p>
        <p class="course-info">
          <span>教师: {{ course.teacherName }}</span>
        </p>
        <button @click="viewExperiments(course.id)" class="btn btn-primary">
          查看实验项目
        </button>
      </div>
    </div>
    <p v-else class="no-data">暂无课程</p>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'CourseList',
  data() {
    return {
      courses: [],
      searchKeyword: ''
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
  },
  methods: {
    async loadCourses() {
      try {
        const response = await api.get('/api/public/courses')
        this.courses = response.data
      } catch (error) {
        console.error('Failed to load courses:', error)
      }
    },
    viewExperiments(courseId) {
      this.$router.push(`/courses/${courseId}/experiments`)
    }
  }
}
</script>

<style scoped>
.courses-page {
  padding: 20px;
}

.courses-page h1 {
  color: #333;
  margin-bottom: 20px;
}

.search-bar {
  margin-bottom: 30px;
}

.search-bar input {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 300px;
  font-size: 14px;
}

.courses-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.course-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
}

.course-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.course-card h3 {
  margin: 0 0 10px 0;
  color: #667eea;
  font-size: 18px;
}

.course-name {
  margin: 0 0 15px 0;
  color: #333;
  font-weight: bold;
  font-size: 16px;
}

.course-info {
  margin: 5px 0;
  color: #666;
  font-size: 14px;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
  margin-top: auto;
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

