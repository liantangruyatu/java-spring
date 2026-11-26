<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <div class="card-header">
          <h2>创建账户</h2>
        </div>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" @submit.prevent="handleRegister">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>

        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="M" />
            <el-option label="女" value="F" />
          </el-select>
        </el-form-item>

        <el-form-item label="院系" prop="department">
          <el-input v-model="form.department" placeholder="请输入所在院系" />
        </el-form-item>

        <el-form-item label="电话号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入电话号码" />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" type="email" placeholder="请输入邮箱" />
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址（可选）" />
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="form.userType" placeholder="请选择用户类型">
            <el-option label="卖家" value="卖家" />
            <el-option label="买家" value="买家" />
            <el-option label="都是" value="都是" />
          </el-select>
        </el-form-item>

        <el-button type="primary" @click="handleRegister" class="register-btn" :loading="loading">注册</el-button>

        <div class="login-link">
          已有账户? <router-link to="/login">立即登录</router-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { userApi } from '../api/request'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = ref({
  username: '',
  password: '',
  realName: '',
  gender: 'M',
  department: '',
  phoneNumber: '',
  email: '',
  address: '',
  userType: '卖家',
})

const rules = {
  username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  realName: [{ required: true, message: '真实姓名不能为空', trigger: 'blur' }],
  gender: [{ required: true, message: '性别不能为空', trigger: 'blur' }],
  department: [{ required: true, message: '院系不能为空', trigger: 'blur' }],
  phoneNumber: [{ required: true, message: '电话号码不能为空', trigger: 'blur' }],
  email: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }],
}

const handleRegister = async () => {
  try {
    await formRef.value.validate()
    loading.value = true

    const response = await userApi.register(form.value)
    
    if (response.code === 200) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    } else {
      ElMessage.error(response.message || '注册失败')
    }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 200px);
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.register-card {
  width: 100%;
  max-width: 500px;
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
    margin-bottom: 15px;
  }

  .register-btn {
    width: 100%;
    margin-top: 20px;
  }

  .login-link {
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
