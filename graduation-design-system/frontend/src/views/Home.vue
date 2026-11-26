<template>
  <div class="home-container">
    <div class="sidebar">
      <div class="logo">
        <h2>毕业设计管理系统</h2>
      </div>
      <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" router>
        <el-menu-item index="/teachers">
          <template #title>
            <span>教师管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/topics">
          <template #title>
            <span>题目管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/students">
          <template #title>
            <span>学生管理</span>
          </template>
        </el-menu-item>
        <el-menu-item index="/selections">
          <template #title>
            <span>选题管理</span>
          </template>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="main-content">
      <div class="header">
        <div class="user-info">
          <span>{{ user?.username }}</span>
          <el-button type="text" @click="logout">退出登录</el-button>
        </div>
      </div>
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      activeIndex: '/teachers',
      user: null
    }
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      const userStr = localStorage.getItem('user')
      if (userStr) {
        this.user = JSON.parse(userStr)
      } else {
        this.$router.push('/login')
      }
    },
    logout() {
      localStorage.removeItem('user')
      this.$router.push('/login')
    }
  },
  watch: {
    $route(to) {
      this.activeIndex = to.path
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  width: 200px;
  background-color: #304156;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #40556f;
}

.logo h2 {
  margin: 0;
  font-size: 18px;
  color: #fff;
}

.el-menu-vertical-demo {
  background-color: transparent;
  border-right: none;
}

.el-menu-vertical-demo .el-menu-item {
  color: #bfcbd9;
}

.el-menu-vertical-demo .el-menu-item:hover {
  background-color: #40556f;
}

.el-menu-vertical-demo .el-menu-item.is-active {
  color: #fff;
  background-color: #1890ff;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: 60px;
  background-color: #fff;
  border-bottom: 1px solid #e6e8eb;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f5f7fa;
}
</style>
