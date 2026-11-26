<template>
  <div class="my-products-container">
    <div class="header">
      <h2>我的物品</h2>
      <el-button type="primary" @click="$router.push('/publish-product')">发布新物品</el-button>
    </div>

    <el-skeleton :loading="loading" :rows="3" animated>
      <el-empty v-if="products.length === 0" description="暂无物品" />

      <el-table :data="products" stripe border v-else>
        <el-table-column prop="productName" label="物品名称" width="180" />
        <el-table-column prop="price" label="价格" width="100" />
        <el-table-column prop="condition" label="成色" width="100" />
        <el-table-column prop="productStatus" label="状态" width="100" />
        <el-table-column prop="publishTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="editProduct(row)">编辑</el-button>
            <el-button link type="danger" @click="deleteProduct(row.productId)">删除</el-button>
            <el-dropdown @command="handleStatusChange(row, $event)">
              <el-button link type="info">更改状态</el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="在售">在售</el-dropdown-item>
                  <el-dropdown-item command="已下架">已下架</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </el-skeleton>

    <el-pagination
      v-if="products.length > 0"
      @current-change="handlePageChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '../store/user'
import { productApi } from '../api/request'

const userStore = useUserStore()
const loading = ref(false)
const products = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadProducts = async (page = 1) => {
  loading.value = true
  try {
    const response = await productApi.getProductsBySeller(userStore.user.userId, page - 1, pageSize.value)
    products.value = response.data.content || []
    total.value = response.data.totalElements || 0
  } catch (error) {
    ElMessage.error('加载物品失败')
  } finally {
    loading.value = false
  }
}

const editProduct = (product) => {
  // 可以实现编辑功能
  ElMessage.info('编辑功能开发中')
}

const deleteProduct = (productId) => {
  ElMessageBox.confirm('确定删除此物品?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    try {
      await productApi.deleteProduct(productId)
      ElMessage.success('删除成功')
      loadProducts(currentPage.value)
    } catch (error) {
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

const handleStatusChange = async (product, status) => {
  try {
    await productApi.changeProductStatus(product.productId, status)
    ElMessage.success('状态更新成功')
    loadProducts(currentPage.value)
  } catch (error) {
    ElMessage.error('状态更新失败')
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadProducts(page)
}

onMounted(() => {
  loadProducts()
})
</script>

<style scoped lang="scss">
.my-products-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  h2 {
    margin: 0;
  }
}

.pagination {
  text-align: center;
  margin-top: 20px;
}
</style>
