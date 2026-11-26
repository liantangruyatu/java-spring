<template>
  <div class="upload-page">
    <h1>上传实验报告</h1>

    <div class="upload-container">
      <form @submit.prevent="submit" class="upload-form">
        <div class="form-section">
          <h3>选择要提交的实验</h3>
          
          <div class="form-group">
            <label>课程</label>
            <select v-model="selectedCourse" @change="loadExperiments" required>
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
                {{ exp.title }}
              </option>
            </select>
          </div>
        </div>

        <div class="form-section">
          <h3>选择报告文件</h3>
          
          <div class="form-group">
            <label>选择文件 (PDF, Word, 图片等)</label>
            <div class="file-input-wrapper">
              <input 
                type="file" 
                @change="onFileChange"
                accept=".pdf,.doc,.docx,.xlsx,.jpg,.jpeg,.png,.gif,.txt"
                required
              />
              <span v-if="formData.file" class="file-name">
                ✓ {{ formData.file.name }}
              </span>
              <span v-else class="file-hint">
                点击选择或拖拽文件到此处
              </span>
            </div>
          </div>

          <div class="file-info">
            <p>支持格式: PDF, Word, Excel, 图片 (JPG, PNG, GIF), TXT</p>
            <p>最大文件: 10MB</p>
          </div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary" :disabled="!formData.file || !formData.experimentId">
            上传报告
          </button>
          <button type="reset" @click="reset" class="btn">清除</button>
        </div>

        <div v-if="uploadMessage" :class="['message', uploadStatus]">
          {{ uploadMessage }}
        </div>
      </form>

      <div class="upload-tips">
        <h3>📝 上传须知</h3>
        <ul>
          <li>请确保上传的是完整的实验报告</li>
          <li>报告应包含: 实验目的、原理、步骤、结果、分析</li>
          <li>文件名应该清晰明了，如: 实验1-学号-姓名</li>
          <li>上传后不可修改，请确认无误后再上传</li>
          <li>教师将在规定时间内进行批改和评分</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'ReportUpload',
  data() {
    return {
      courses: [],
      experiments: [],
      selectedCourse: '',
      formData: {
        experimentId: '',
        file: null
      },
      uploadMessage: '',
      uploadStatus: ''
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
    onFileChange(event) {
      const file = event.target.files[0]
      if (file) {
        // 检查文件大小 (10MB)
        if (file.size > 10 * 1024 * 1024) {
          this.uploadMessage = '文件过大，请选择小于10MB的文件'
          this.uploadStatus = 'error'
          return
        }
        this.formData.file = file
      }
    },
    async submit() {
      try {
        if (!this.formData.experimentId || !this.formData.file) {
          this.uploadMessage = '请选择实验项目和文件'
          this.uploadStatus = 'error'
          return
        }

        const user = JSON.parse(localStorage.getItem('user') || '{}')
        const formData = new FormData()
        formData.append('userId', user.id)
        formData.append('experimentId', this.formData.experimentId)
        formData.append('file', this.formData.file)

        const response = await api.post('/api/student/reports', formData, {
          headers: { 'Content-Type': 'multipart/form-data' }
        })

        this.uploadMessage = '报告上传成功！报告ID: ' + response.data.id
        this.uploadStatus = 'success'
        
        setTimeout(() => {
          this.reset()
        }, 2000)
      } catch (error) {
        this.uploadMessage = '上传失败: ' + (error.response?.data?.message || error.message)
        this.uploadStatus = 'error'
      }
    },
    reset() {
      this.selectedCourse = ''
      this.experiments = []
      this.formData = { experimentId: '', file: null }
      this.uploadMessage = ''
      document.querySelectorAll('input[type="file"]').forEach(input => {
        input.value = ''
      })
    }
  }
}
</script>

<style scoped>
.upload-page {
  padding: 20px;
}

.upload-page h1 {
  color: #333;
  margin-bottom: 30px;
}

.upload-container {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: 30px;
}

.upload-form {
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

.form-group select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.file-input-wrapper {
  border: 2px dashed #667eea;
  border-radius: 4px;
  padding: 30px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  background: #f9f9ff;
}

.file-input-wrapper:hover {
  border-color: #5568d3;
  background: #f5f5ff;
}

.file-input-wrapper input[type="file"] {
  display: none;
}

.file-input-wrapper .file-name {
  display: block;
  color: #48bb78;
  font-weight: 600;
}

.file-input-wrapper .file-hint {
  color: #999;
  font-size: 14px;
}

.file-info {
  background: #f5f5f5;
  padding: 15px;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
}

.file-info p {
  margin: 5px 0;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.btn {
  padding: 12px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
  flex: 1;
  font-size: 14px;
}

.btn:hover:not(:disabled) {
  background: #f5f5f5;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-primary {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.btn-primary:hover:not(:disabled) {
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

.upload-tips {
  background: white;
  padding: 25px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.upload-tips h3 {
  margin-top: 0;
  color: #333;
  font-size: 16px;
}

.upload-tips ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.upload-tips li {
  padding: 8px 0;
  border-bottom: 1px solid #f0f0f0;
  color: #666;
  font-size: 14px;
  line-height: 1.6;
}

.upload-tips li:last-child {
  border-bottom: none;
}

.upload-tips li:before {
  content: "✓ ";
  color: #667eea;
  font-weight: 600;
  margin-right: 8px;
}

@media (max-width: 1024px) {
  .upload-container {
    grid-template-columns: 1fr;
  }
}
</style>

