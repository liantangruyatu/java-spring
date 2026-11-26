<template>
  <div class="grades-page">
    <h1>成绩查询</h1>

    <div class="filter-section">
      <select v-model="filterCourse">
        <option value="">所有课程</option>
        <option v-for="course in courses" :key="course.id" :value="course.id">
          {{ course.name }}
        </option>
      </select>
    </div>

    <div v-if="!isTeacher" class="student-grades">
      <div class="grade-summary">
        <div class="summary-item">
          <span>平均成绩</span>
          <span class="value">{{ averageGrade || '-' }}</span>
        </div>
        <div class="summary-item">
          <span>已评分数</span>
          <span class="value">{{ gradedCount }}</span>
        </div>
        <div class="summary-item">
          <span>总报告数</span>
          <span class="value">{{ totalReportsCount }}</span>
        </div>
      </div>

      <table class="grades-table" v-if="filteredGrades.length > 0">
        <thead>
          <tr>
            <th>课程</th>
            <th>实验项目</th>
            <th>成绩</th>
            <th>评语</th>
            <th>评分日期</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="grade in filteredGrades" :key="grade.id">
            <td>{{ grade.courseName }}</td>
            <td>{{ grade.experimentTitle }}</td>
            <td>
              <span :class="'grade-badge ' + getGradeClass(grade.score)">
                {{ grade.score }}
              </span>
            </td>
            <td>{{ grade.comment || '-' }}</td>
            <td>{{ formatDate(grade.gradedAt) }}</td>
          </tr>
        </tbody>
      </table>
      <p v-else class="no-data">暂无成绩</p>
    </div>

    <div v-else class="teacher-grades">
      <div class="tabs">
        <button :class="{ active: viewMode === 'list' }" @click="viewMode = 'list'">
          成绩列表
        </button>
        <button :class="{ active: viewMode === 'stats' }" @click="viewMode = 'stats'">
          成绩统计
        </button>
      </div>

      <div v-if="viewMode === 'list'">
        <table class="grades-table" v-if="allGrades.length > 0">
          <thead>
            <tr>
              <th>学生</th>
              <th>课程</th>
              <th>实验项目</th>
              <th>成绩</th>
              <th>评语</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="grade in filteredTeacherGrades" :key="grade.id">
              <td>{{ grade.studentName }}</td>
              <td>{{ grade.courseName }}</td>
              <td>{{ grade.experimentTitle }}</td>
              <td>
                <span :class="'grade-badge ' + getGradeClass(grade.score)">
                  {{ grade.score }}
                </span>
              </td>
              <td>{{ grade.comment || '-' }}</td>
            </tr>
          </tbody>
        </table>
        <p v-else class="no-data">暂无成绩记录</p>
      </div>

      <div v-if="viewMode === 'stats'" class="stats-section">
        <div class="stat-grid">
          <div class="stat-card">
            <h4>平均成绩</h4>
            <p class="stat-value">{{ classAverageGrade }}</p>
          </div>
          <div class="stat-card">
            <h4>最高成绩</h4>
            <p class="stat-value">{{ maxGrade }}</p>
          </div>
          <div class="stat-card">
            <h4>最低成绩</h4>
            <p class="stat-value">{{ minGrade }}</p>
          </div>
          <div class="stat-card">
            <h4>已评分数</h4>
            <p class="stat-value">{{ allGrades.length }}</p>
          </div>
        </div>

        <div class="distribution">
          <h4>成绩分布</h4>
          <div class="grade-dist">
            <div class="dist-item">
              <span>90-100</span>
              <div class="bar">
                <div class="fill" :style="{ width: getPercentage('90-100') + '%' }"></div>
              </div>
              <span>{{ countGradesInRange(90, 100) }}</span>
            </div>
            <div class="dist-item">
              <span>80-89</span>
              <div class="bar">
                <div class="fill" :style="{ width: getPercentage('80-89') + '%' }"></div>
              </div>
              <span>{{ countGradesInRange(80, 89) }}</span>
            </div>
            <div class="dist-item">
              <span>70-79</span>
              <div class="bar">
                <div class="fill" :style="{ width: getPercentage('70-79') + '%' }"></div>
              </div>
              <span>{{ countGradesInRange(70, 79) }}</span>
            </div>
            <div class="dist-item">
              <span>60-69</span>
              <div class="bar">
                <div class="fill" :style="{ width: getPercentage('60-69') + '%' }"></div>
              </div>
              <span>{{ countGradesInRange(60, 69) }}</span>
            </div>
            <div class="dist-item">
              <span>&lt;60</span>
              <div class="bar">
                <div class="fill" :style="{ width: getPercentage('<60') + '%' }"></div>
              </div>
              <span>{{ countGradesInRange(0, 59) }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'GradeList',
  data() {
    return {
      courses: [],
      filterCourse: '',
      myGrades: [],
      allGrades: [],
      currentUser: null,
      isTeacher: false,
      viewMode: 'list'
    }
  },
  computed: {
    filteredGrades() {
      if (!this.filterCourse) return this.myGrades
      return this.myGrades.filter(g => g.courseId == this.filterCourse)
    },
    filteredTeacherGrades() {
      if (!this.filterCourse) return this.allGrades
      return this.allGrades.filter(g => g.courseId == this.filterCourse)
    },
    gradedCount() {
      return this.myGrades.length
    },
    totalReportsCount() {
      // 这需要从报告列表获取
      return this.myGrades.length
    },
    averageGrade() {
      if (this.filteredGrades.length === 0) return '-'
      const sum = this.filteredGrades.reduce((acc, g) => acc + parseFloat(g.score || 0), 0)
      return (sum / this.filteredGrades.length).toFixed(2)
    },
    classAverageGrade() {
      if (this.allGrades.length === 0) return '-'
      const sum = this.allGrades.reduce((acc, g) => acc + parseFloat(g.score || 0), 0)
      return (sum / this.allGrades.length).toFixed(2)
    },
    maxGrade() {
      if (this.allGrades.length === 0) return '-'
      return Math.max(...this.allGrades.map(g => g.score))
    },
    minGrade() {
      if (this.allGrades.length === 0) return '-'
      return Math.min(...this.allGrades.map(g => g.score))
    }
  },
  mounted() {
    this.loadUser()
    this.loadCourses()
    this.loadGrades()
  },
  methods: {
    loadUser() {
      const user = JSON.parse(localStorage.getItem('user') || '{}')
      this.currentUser = user
      this.isTeacher = user.role === 'TEACHER'
    },
    async loadCourses() {
      try {
        const response = await api.get('/api/public/courses')
        this.courses = response.data
      } catch (error) {
        console.error('Failed to load courses:', error)
      }
    },
    async loadGrades() {
      try {
        if (!this.isTeacher) {
          const response = await api.get(`/api/teacher/grades/user/${this.currentUser.id}`)
          this.myGrades = response.data
        } else {
          const response = await api.get('/api/teacher/grades')
          this.allGrades = response.data
        }
      } catch (error) {
        console.error('Failed to load grades:', error)
      }
    },
    getGradeClass(score) {
      if (!score) return 'no-grade'
      if (score >= 90) return 'excellent'
      if (score >= 80) return 'good'
      if (score >= 70) return 'fair'
      if (score >= 60) return 'pass'
      return 'fail'
    },
    formatDate(date) {
      if (!date) return '-'
      return new Date(date).toLocaleString('zh-CN')
    },
    countGradesInRange(min, max) {
      return this.allGrades.filter(g => g.score >= min && g.score <= max).length
    },
    getPercentage(range) {
      if (this.allGrades.length === 0) return 0
      return (this.countGradesInRange(...this.getRangeValues(range)) / this.allGrades.length) * 100
    },
    getRangeValues(range) {
      if (range === '90-100') return [90, 100]
      if (range === '80-89') return [80, 89]
      if (range === '70-79') return [70, 79]
      if (range === '60-69') return [60, 69]
      if (range === '<60') return [0, 59]
      return [0, 100]
    }
  }
}
</script>

<style scoped>
.grades-page {
  padding: 20px;
}

.grades-page h1 {
  color: #333;
  margin-bottom: 20px;
}

.filter-section {
  margin-bottom: 20px;
}

.filter-section select {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.grade-summary {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.summary-item {
  background: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  flex-direction: column;
}

.summary-item span:first-child {
  color: #666;
  font-size: 14px;
  margin-bottom: 10px;
}

.summary-item .value {
  font-size: 32px;
  font-weight: bold;
  color: #667eea;
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
}

.tabs button.active {
  color: #667eea;
  border-bottom-color: #667eea;
}

.grades-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.grades-table th {
  background: #f5f5f5;
  padding: 12px;
  text-align: left;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #e0e0e0;
}

.grades-table td {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
}

.grades-table tr:hover {
  background: #fafafa;
}

.grade-badge {
  padding: 4px 8px;
  border-radius: 3px;
  font-weight: bold;
}

.grade-badge.excellent {
  background: #c6f6d5;
  color: #22543d;
}

.grade-badge.good {
  background: #bee3f8;
  color: #2c5282;
}

.grade-badge.fair {
  background: #feebc8;
  color: #7c2d12;
}

.grade-badge.pass {
  background: #fed7d7;
  color: #742a2a;
}

.grade-badge.fail {
  background: #fc8181;
  color: #742a2a;
}

.grade-badge.no-grade {
  background: #e2e8f0;
  color: #2d3748;
}

.no-data {
  text-align: center;
  color: #999;
  padding: 40px 20px;
}

.stat-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stat-card h4 {
  margin: 0 0 10px 0;
  color: #666;
  font-size: 14px;
}

.stat-value {
  margin: 0;
  font-size: 28px;
  font-weight: bold;
  color: #667eea;
}

.distribution {
  background: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.distribution h4 {
  margin-top: 0;
  color: #333;
}

.grade-dist {
  margin-top: 20px;
}

.dist-item {
  display: grid;
  grid-template-columns: 60px 1fr 40px;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.dist-item span {
  font-size: 14px;
  color: #666;
}

.bar {
  height: 24px;
  background: #f0f0f0;
  border-radius: 4px;
  overflow: hidden;
}

.bar .fill {
  height: 100%;
  background: linear-gradient(90deg, #667eea, #764ba2);
  transition: width 0.3s ease;
}
</style>
