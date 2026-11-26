<template>
  <div class="login-container">
    <el-card class="login-form">
      <template #header>
        <div class="card-header">
          <span class="title">用户登录</span>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="loginForm"
        :rules="rules"
        label-width="120px"
        @submit.prevent="handleLogin"
      >
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="loginForm.phone" placeholder="请输入手机号" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading">
            登录
          </el-button>
          <el-button @click="goToRegister">返回注册</el-button>
        </el-form-item>
      </el-form>

      <div class="test-accounts">
        <p>测试账号:</p>
        <p>管理员: 18600000001 / root</p>
        <p>维修工: 18600000002 / root</p>
        <p>市民: 18600000010 / root</p>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'
import { userAPI } from '../api/index'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()
const formRef = ref(null)
const loading = ref(false)

const loginForm = ref({
  phone: '',
  password: ''
})

const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  await formRef.value?.validate()
  
  loading.value = true
  try {
    const response = await userAPI.login({
      phone: loginForm.value.phone,
      password: loginForm.value.password
    })

    if (response.code === 200) {
      authStore.setUser(response.data, 'test-token-' + Date.now())
      ElMessage.success('登录成功')
      router.push('/dashboard')
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('登录失败: ' + error.message)
  } finally {
    loading.value = false
  }
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form {
  width: 400px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  text-align: center;
  padding: 20px 0;
}

.title {
  font-size: 20px;
  font-weight: bold;
  color: #333;
}

.test-accounts {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #e6e6e6;
  background-color: #fafafa;
  padding: 15px;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
}

.test-accounts p {
  margin: 5px 0;
}
</style>
