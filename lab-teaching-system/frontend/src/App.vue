<template>
  <div id="app">
    <header class="app-header">
      <div class="header-left">
        <div class="brand">🔬 实验教学管理系统</div>
        <nav class="nav" v-if="isLoggedIn">
          <router-link to="/" :class="{ active: isActive('/') }">首页</router-link>
          <router-link to="/courses" :class="{ active: isActive('/courses') }">课程</router-link>
          <router-link to="/reservation" :class="{ active: isActive('/reservation') }">预约</router-link>
          <router-link to="/reports" :class="{ active: isActive('/reports') }">报告</router-link>
          <router-link to="/grades" :class="{ active: isActive('/grades') }">成绩</router-link>
          <router-link v-if="isAdmin" to="/admin/courses" :class="{ active: isAdmin && isActive('/admin') }">
            📊 管理
          </router-link>
        </nav>
      </div>
      <div class="user-area">
        <template v-if="user">
          <div class="user-info">
            <span class="user-name">{{ user.fullname || user.username }}</span>
            <span class="user-role">{{ getRoleLabel(user.role) }}</span>
          </div>
          <button class="btn-logout" @click="logout">登出</button>
        </template>
        <template v-else>
          <router-link to="/login" class="btn-login">登录</router-link>
        </template>
      </div>
    </header>
    
    <main class="app-main">
      <router-view />
    </main>

    <footer class="app-footer">
      <p>&copy; 2024 实验教学管理系统 | 版本 1.0</p>
    </footer>
  </div>
</template>

<script>
import { api } from './services/api'

export default {
  name: 'App',
  data() {
    return {
      user: null,
      isLoggedIn: false
    }
  },
  computed: {
    isAdmin() {
      return this.user?.role === 'ADMIN'
    }
  },
  mounted() {
    this.checkAuth()
    this.$router.afterEach(() => {
      this.checkAuth()
    })
  },
  methods: {
    checkAuth() {
      const auth = localStorage.getItem('auth')
      const user = localStorage.getItem('user')
      if (auth && user) {
        this.user = JSON.parse(user)
        this.isLoggedIn = true
      } else {
        this.isLoggedIn = false
        this.user = null
      }
    },
    logout() {
      localStorage.removeItem('auth')
      localStorage.removeItem('user')
      this.user = null
      this.isLoggedIn = false
      this.$router.push('/login')
    },
    getRoleLabel(role) {
      const labels = {
        'ADMIN': '管理员',
        'TEACHER': '教师',
        'STUDENT': '学生'
      }
      return labels[role] || role
    },
    isActive(path) {
      return this.$route.path.startsWith(path)
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', sans-serif;
  background: #f5f5f5;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px 30px;
  background: white;
  border-bottom: 2px solid #f0f0f0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.05);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-left {
  display: flex;
  align-items: center;
  flex: 1;
  gap: 40px;
}

.brand {
  font-weight: 700;
  font-size: 20px;
  color: #667eea;
  white-space: nowrap;
}

.nav {
  display: flex;
  gap: 30px;
}

.nav a {
  color: #666;
  text-decoration: none;
  font-size: 15px;
  padding: 8px 12px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.nav a:hover {
  color: #667eea;
  background: #f5f5f5;
}

.nav a.active {
  color: #667eea;
  font-weight: 600;
  border-bottom: 2px solid #667eea;
}

.user-area {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.user-name {
  font-weight: 600;
  color: #333;
}

.user-role {
  font-size: 12px;
  color: #999;
}

.btn-logout, .btn-login {
  padding: 8px 20px;
  border-radius: 4px;
  cursor: pointer;
  border: none;
  font-size: 14px;
  transition: all 0.3s ease;
  text-decoration: none;
  display: inline-block;
}

.btn-logout {
  background: #f56565;
  color: white;
}

.btn-logout:hover {
  background: #e53e3e;
}

.btn-login {
  background: #667eea;
  color: white;
}

.btn-login:hover {
  background: #5568d3;
}

.app-main {
  flex: 1;
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
}

.app-footer {
  background: #f5f5f5;
  border-top: 1px solid #e0e0e0;
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 14px;
}

@media (max-width: 768px) {
  .app-header {
    flex-direction: column;
    gap: 15px;
    padding: 15px;
  }

  .header-left {
    width: 100%;
    flex-direction: column;
    gap: 15px;
  }

  .nav {
    flex-wrap: wrap;
    gap: 10px;
  }

  .nav a {
    font-size: 14px;
    padding: 6px 10px;
  }

  .user-area {
    width: 100%;
    justify-content: space-between;
  }
}
</style>

