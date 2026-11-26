<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h1>🔬 实验教学管理系统</h1>
        <p>用户登录</p>
      </div>

      <form @submit.prevent="login" class="login-form">
        <div class="form-group">
          <label>用户名</label>
          <input 
            v-model="username" 
            type="text"
            placeholder="请输入用户名"
            required
          />
        </div>

        <div class="form-group">
          <label>密码</label>
          <input 
            v-model="password" 
            type="password"
            placeholder="请输入密码"
            required
          />
        </div>

        <button type="submit" class="btn-login" :disabled="loading">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>

      <div class="demo-accounts">
        <h4>测试账户</h4>
        <div class="account-item">
          <strong>管理员</strong>
          <span>admin / admin123</span>
        </div>
        <div class="account-item">
          <strong>教师</strong>
          <span>teacher1 / teacher123</span>
        </div>
        <div class="account-item">
          <strong>学生</strong>
          <span>student1 / student123</span>
        </div>
      </div>

      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      loading: false,
      errorMessage: ''
    }
  },
  methods: {
    async login() {
      if (!this.username || !this.password) {
        this.errorMessage = '请输入用户名和密码'
        return
      }

      this.loading = true
      this.errorMessage = ''

      try {
        // 编码用户名和密码为 Base64
        const auth = btoa(this.username + ':' + this.password)
        
        // 设置 Axios 默认头
        api.defaults.headers.common['Authorization'] = 'Basic ' + auth
        
        // 获取当前用户信息
        const response = await api.get('/api/public/me')
        const user = response.data
        
        // 保存认证信息到 localStorage
        localStorage.setItem('auth', auth)
        localStorage.setItem('user', JSON.stringify(user))
        
        this.loading = false
        this.$router.push('/')
      } catch (error) {
      this.loading = false
      this.errorMessage = '登录失败：用户名或密码错误'
      console.error('登录失败:', error.response?.data || error.message)
    }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-box {
  background: white;
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0,0,0,0.2);
  padding: 40px;
  width: 100%;
  max-width: 400px;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h1 {
  color: #667eea;
  margin: 0 0 10px 0;
  font-size: 28px;
}

.login-header p {
  color: #999;
  margin: 0;
  font-size: 16px;
}

.login-form {
  margin-bottom: 30px;
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

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: all 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.btn-login {
  width: 100%;
  padding: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-login:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.btn-login:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.demo-accounts {
  background: #f5f5f5;
  border-radius: 4px;
  padding: 20px;
  margin-bottom: 20px;
}

.demo-accounts h4 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 14px;
}

.account-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #e0e0e0;
  font-size: 13px;
}

.account-item:last-child {
  border-bottom: none;
}

.account-item strong {
  color: #333;
  min-width: 50px;
}

.account-item span {
  color: #667eea;
  font-family: monospace;
}

.error-message {
  background: #ffe6e6;
  color: #d32f2f;
  padding: 12px;
  border-radius: 4px;
  text-align: center;
  font-size: 14px;
  margin-top: 20px;
}

@media (max-width: 600px) {
  .login-box {
    padding: 30px 20px;
  }

  .login-header h1 {
    font-size: 24px;
  }
}
</style>

