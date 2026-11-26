<template>
  <div class="publish-container">
    <el-card class="publish-card">
      <template #header>
        <h2>发布物品</h2>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="120px">
        <el-form-item label="物品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入物品名称" />
        </el-form-item>

        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类">
            <el-option v-for="cat in categories" :key="cat.categoryId" :label="cat.categoryName" :value="cat.categoryId" />
          </el-select>
        </el-form-item>

        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :min="0" :precision="2" />
        </el-form-item>

        <el-form-item label="成色" prop="condition">
          <el-select v-model="form.condition" placeholder="请选择成色">
            <el-option label="全新" value="全新" />
            <el-option label="九成新" value="九成新" />
            <el-option label="八成新" value="八成新" />
            <el-option label="使用过" value="使用过" />
          </el-select>
        </el-form-item>

        <el-form-item label="物品描述" prop="description">
          <el-input v-model="form.description" type="textarea" rows="4" placeholder="请详细描述物品信息" />
        </el-form-item>

        <el-form-item label="交易地点" prop="location">
          <el-input v-model="form.location" placeholder="请输入交易地点" />
        </el-form-item>

        <el-form-item label="图片URL" prop="imageUrl">
          <el-input v-model="form.imageUrl" placeholder="请输入图片URL" />
        </el-form-item>

        <el-form-item label="开始时间" prop="saleStartTime">
          <el-date-picker v-model="form.saleStartTime" type="datetime" placeholder="选择开始时间" />
        </el-form-item>

        <el-form-item label="结束时间" prop="saleEndTime">
          <el-date-picker v-model="form.saleEndTime" type="datetime" placeholder="选择结束时间" />
        </el-form-item>

        <el-button type="primary" @click="handlePublish" :loading="loading">发布物品</el-button>
        <el-button @click="$router.back()">取消</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { productApi, categoryApi } from '../api/request'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref()
const loading = ref(false)
const categories = ref([])

const form = ref({
  productName: '',
  categoryId: '',
  price: 0,
  condition: '全新',
  description: '',
  location: '',
  imageUrl: '',
  saleStartTime: new Date(),
  saleEndTime: null,
})

const rules = {
  productName: [{ required: true, message: '物品名称不能为空', trigger: 'blur' }],
  categoryId: [{ required: true, message: '分类不能为空', trigger: 'change' }],
  price: [{ required: true, message: '价格不能为空', trigger: 'blur' }],
  condition: [{ required: true, message: '成色不能为空', trigger: 'change' }],
  description: [{ required: true, message: '描述不能为空', trigger: 'blur' }],
}

const loadCategories = async () => {
  try {
    const response = await categoryApi.getAllCategories()
    categories.value = response.data || []
  } catch (error) {
    ElMessage.error('加载分类失败')
  }
}

const handlePublish = async () => {
  try {
    await formRef.value.validate()
    loading.value = true

    const publishData = {
      ...form.value,
      sellerId: userStore.user.userId,
      saleStartTime: form.value.saleStartTime ? new Date(form.value.saleStartTime).toISOString().slice(0, 19) : null,
      saleEndTime: form.value.saleEndTime ? new Date(form.value.saleEndTime).toISOString().slice(0, 19) : null,
    }

    await productApi.createProduct(publishData)
    ElMessage.success('物品发布成功')
    router.push('/my-products')
  } catch (error) {
    ElMessage.error('发布物品失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadCategories()
})
</script>

<style scoped lang="scss">
.publish-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.publish-card {
  h2 {
    margin: 0;
  }
}
</style>
