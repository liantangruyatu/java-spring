<template>
  <div class="login-container">
    <el-card class="login-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <h2>毕业设计管理系统</h2>
          <p>请登录您的账号</p>
        </div>
      </template>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    login() {
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
          axios.post('/user/login', {
            username: this.loginForm.username,
            password: this.loginForm.password
          }, {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            transformRequest: [(data) => {
              let params = ''
              for (let key in data) {
                params += key + '=' + encodeURIComponent(data[key]) + '&'
              }
              return params.slice(0, -1)
            }]
          })
          .then(response => {
            if (response.data.code === 1) {
              this.$message.success('登录成功')
              localStorage.setItem('user', JSON.stringify(response.data.data))
              this.$router.push('/home')
            } else {
              this.$message.error(response.data.message)
            }
          })
          .catch(error => {
            this.$message.error('登录失败，请检查网络或服务器')
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-card {
  width: 400px;
}

.card-header {
  text-align: center;
}

.card-header h2 {
  margin-bottom: 10px;
  color: #303133;
}

.card-header p {
  margin: 0;
  color: #909399;
  font-size: 14px;
}
</style>
