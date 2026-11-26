<template>
  <div class="register-container">
    <el-card class="register-form">
      <template #header>
        <div class="card-header">
          <span class="title">用户注册</span>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="registerForm"
        :rules="rules"
        label-width="120px"
        @submit.prevent="handleRegister"
      >
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="请输入密码"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            show-password
          ></el-input>
        </el-form-item>

        <el-form-item label="真实姓名">
          <el-input v-model="registerForm.realName" placeholder="请输入真实姓名"></el-input>
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <el-radio-group v-model="registerForm.userType">
            <el-radio value="citizen">普通市民</el-radio>
            <el-radio value="worker">维修工</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleRegister" :loading="loading">
            注册
          </el-button>
          <el-button @click="goToLogin">返回登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userAPI } from '../api/index'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)

const registerForm = ref({
  phone: '',
  password: '',
  confirmPassword: '',
  realName: '',
  userType: 'citizen'
})

const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== registerForm.value.password) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  userType: [
    { required: true, message: '请选择用户类型', trigger: 'change' }
  ]
}

const handleRegister = async () => {
  await formRef.value?.validate()
  
  loading.value = true
  try {
    const response = await userAPI.register({
      phone: registerForm.value.phone,
      password: registerForm.value.password,
      realName: registerForm.value.realName,
      userType: registerForm.value.userType
    })

    if (response.code === 200) {
      ElMessage.success('注册成功,请登录')
      router.push('/login')
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('注册失败: ' + error.message)
  } finally {
    loading.value = false
  }
}

const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-form {
  width: 450px;
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
</style>
