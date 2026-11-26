<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <h2>个人信息</h2>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>

        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-select v-model="form.gender" placeholder="请选择性别">
            <el-option label="男" value="M" />
            <el-option label="女" value="F" />
          </el-select>
        </el-form-item>

        <el-form-item label="院系" prop="department">
          <el-input v-model="form.department" />
        </el-form-item>

        <el-form-item label="电话号码">
          <el-input v-model="form.phoneNumber" disabled />
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="form.email" disabled />
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" />
        </el-form-item>

        <el-form-item label="信用评分">
          <el-rate v-model="form.creditScore" disabled allow-half />
          <span>{{ form.creditScore }}/100</span>
        </el-form-item>

        <el-button type="primary" @click="handleUpdate" :loading="loading">保存修改</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { userApi } from '../api/request'

const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)

const form = ref({
  username: '',
  realName: '',
  gender: 'M',
  department: '',
  phoneNumber: '',
  email: '',
  address: '',
  creditScore: 100,
})

const rules = {
  realName: [{ required: true, message: '真实姓名不能为空', trigger: 'blur' }],
  gender: [{ required: true, message: '性别不能为空', trigger: 'change' }],
  department: [{ required: true, message: '院系不能为空', trigger: 'blur' }],
}

const handleUpdate = async () => {
  try {
    await formRef.value.validate()
    loading.value = true

    const updateData = {
      realName: form.value.realName,
      gender: form.value.gender,
      department: form.value.department,
      address: form.value.address,
    }

    const response = await userApi.updateUser(userStore.user.userId, updateData)
    userStore.setUser(response.data)
    ElMessage.success('更新成功')
  } catch (error) {
    ElMessage.error('更新失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (userStore.user) {
    form.value = { ...userStore.user }
  }
})
</script>

<style scoped lang="scss">
.profile-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.profile-card {
  h2 {
    margin: 0;
  }
}
</style>
