<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="card-header">
          <h2>登录账户</h2>
        </div>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" @submit.prevent="handleLogin">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>

        <el-button type="primary" @click="handleLogin" class="login-btn" :loading="loading">登录</el-button>

        <div class="register-link">
          没有账户? <router-link to="/register">立即注册</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { userApi } from '../api/request'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = ref({
  username: '',
  password: '',
})

const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
}

const handleLogin = async () => {
  try {
    await formRef.value.validate()
    loading.value = true

    const response = await userApi.login(form.value)
    
    if (response.code === 200) {
      userStore.setUser(response.data)
      ElMessage.success('登录成功')
      router.push('/')
    } else {
      ElMessage.error(response.message || '登录失败')
    }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '登录失败，请检查用户名和密码')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 200px);
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 100%;
  max-width: 400px;
  :deep(.el-card__header) {
    border-bottom: 1px solid #ebeef5;
    padding: 20px;
  }

  .card-header {
    h2 {
      margin: 0;
      text-align: center;
    }
  }

  :deep(.el-form-item) {
    margin-bottom: 20px;
  }

  .login-btn {
    width: 100%;
    margin-top: 20px;
  }

  .register-link {
    text-align: center;
    margin-top: 15px;
    font-size: 14px;

    a {
      color: #409eff;
      text-decoration: none;

      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
