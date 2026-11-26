<template>
  <div class="repair-order-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>🔧 新建报修单</span>
        </div>
      </template>

      <el-form :model="repairForm" label-width="120px">
        <el-form-item label="选择设施" required>
          <el-select
            v-model="repairForm.facilityId"
            placeholder="请选择要报修的设施"
            filterable
          >
            <el-option
              v-for="facility in facilities"
              :key="facility.id"
              :label="`${facility.location} (${facility.facilityCode})`"
              :value="facility.id"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="问题描述" required>
          <el-input
            v-model="repairForm.problemDescription"
            type="textarea"
            rows="4"
            placeholder="请详细描述设施存在的问题"
          ></el-input>
        </el-form-item>

        <el-form-item label="上传图片">
          <el-upload
            drag
            action="#"
            :auto-upload="false"
            multiple
            @change="handleImageUpload"
          >
            <el-icon class="is-loading">
              <DocumentCopy />
            </el-icon>
            <div class="el-upload__text">
              拖拽或点击上传图片
            </div>
            <template #tip>
              <div class="el-upload__tip">
                支持 JPG、PNG、GIF 格式，单个文件不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">
            提交报修
          </el-button>
          <el-button @click="goBack">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { facilityAPI, repairOrderAPI } from '../api/index'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const facilities = ref([])
const submitting = ref(false)

const repairForm = ref({
  facilityId: null,
  problemDescription: '',
  images: ''
})

onMounted(() => {
  loadFacilities()
  
  // 如果URL中有facilityId，自动填充
  if (route.query.facilityId) {
    repairForm.value.facilityId = parseInt(route.query.facilityId)
  }
})

const loadFacilities = async () => {
  try {
    const response = await facilityAPI.getList(0, 100)
    if (response.code === 200) {
      facilities.value = response.data.content || []
    }
  } catch (error) {
    ElMessage.error('加载设施列表失败')
  }
}

const handleImageUpload = (file) => {
  // 在实际应用中，这里应该上传文件到服务器
  // 这是一个简化的示例
  console.log('上传文件:', file)
}

const handleSubmit = async () => {
  if (!repairForm.value.facilityId) {
    ElMessage.warning('请选择要报修的设施')
    return
  }

  if (!repairForm.value.problemDescription) {
    ElMessage.warning('请描述设施的问题')
    return
  }

  submitting.value = true
  try {
    const response = await repairOrderAPI.create({
      facilityId: repairForm.value.facilityId,
      problemDescription: repairForm.value.problemDescription,
      images: repairForm.value.images
    })

    if (response.code === 200) {
      ElMessage.success('报修单提交成功')
      router.push('/repair-orders')
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('提交报修失败: ' + error.message)
  } finally {
    submitting.value = false
  }
}

const goBack = () => {
  router.back()
}
</script>

<style scoped>
.repair-order-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

:deep(.el-form) {
  max-width: 600px;
}
</style>
