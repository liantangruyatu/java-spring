<template>
  <div class="app-container">
    <el-container>
      <el-header v-if="isLoggedIn" class="app-header">
        <div class="header-content">
          <h1>🏛️ 城市公共设施智能报修系统</h1>
          <div class="header-right">
            <span class="user-info">欢迎, {{ userName }}</span>
            <el-button type="danger" plain @click="handleLogout">退出登录</el-button>
          </div>
        </div>
      </el-header>

      <el-container v-if="isLoggedIn">
        <el-aside class="app-aside">
          <el-menu
            router
            :default-active="activeMenu"
            class="menu"
          >
            <el-menu-item index="/dashboard">
              <span>📊 仪表板</span>
            </el-menu-item>
            <el-menu-item index="/facilities">
              <span>🏗️ 设施管理</span>
            </el-menu-item>
            <el-menu-item index="/repair-orders">
              <span>🔧 报修单</span>
            </el-menu-item>
            <el-menu-item index="/repair-order/create">
              <span>➕ 新建报修</span>
            </el-menu-item>
            <el-menu-item index="/statistics">
              <span>📈 统计分析</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-main class="app-main">
          <router-view />
        </el-main>
      </el-container>

      <div v-else class="auth-container">
        <router-view />
      </div>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from './store/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()

const activeMenu = ref('/dashboard')
const userName = computed(() => authStore.user?.realName || '用户')
const isLoggedIn = computed(() => authStore.isLoggedIn())

onMounted(() => {
  authStore.loadFromStorage()
  activeMenu.value = router.currentRoute.value.path
})

const handleLogout = () => {
  ElMessage.success('已退出登录')
  authStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.app-container {
  height: 100vh;
  background-color: #f5f7fa;
}

.app-header {
  background-color: #ffffff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 20px;
  display: flex;
  align-items: center;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.header-content h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-info {
  color: #606266;
  font-size: 14px;
}

.app-aside {
  width: 200px;
  background-color: #ffffff;
  border-right: 1px solid #e6e6e6;
}

.menu {
  border: none;
}

.app-main {
  padding: 20px;
  background-color: #f5f7fa;
}

.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

:deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
}

:deep(.el-menu-item.is-active) {
  background-color: #e6f7ff !important;
  color: #1890ff !important;
}
</style>
