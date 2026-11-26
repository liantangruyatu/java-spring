<template>
  <div class="my-orders-container">
    <div class="header">
      <h2>我的订单</h2>
      <el-tabs v-model="activeTab" @tab-change="loadOrders(1)">
        <el-tab-pane label="全部订单" name="all" />
        <el-tab-pane label="待支付" name="待支付" />
        <el-tab-pane label="已支付" name="已支付" />
        <el-tab-pane label="已发货" name="已发货" />
        <el-tab-pane label="已收货" name="已收货" />
        <el-tab-pane label="交易完成" name="交易完成" />
      </el-tabs>
    </div>

    <el-skeleton :loading="loading" :rows="3" animated>
      <el-empty v-if="orders.length === 0" description="暂无订单" />

      <el-table :data="orders" stripe border v-else>
        <el-table-column prop="orderNumber" label="订单号" width="150" />
        <el-table-column prop="productName" label="物品" width="150" />
        <el-table-column prop="orderAmount" label="金额" width="100" />
        <el-table-column prop="orderStatus" label="状态" width="100" />
        <el-table-column prop="buyerName" label="买家" width="100" />
        <el-table-column prop="sellerName" label="卖家" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button link type="primary" @click="viewOrder(row)">详情</el-button>
            <el-button link type="success" @click="updateStatus(row)" v-if="canUpdateStatus(row)">更新状态</el-button>
            <el-button link type="warning" @click="rateOrder(row)" v-if="canRate(row)">评价</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-skeleton>

    <el-pagination
      v-if="orders.length > 0"
      @current-change="loadOrders"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      class="pagination"
    />

    <el-dialog v-model="showRatingDialog" title="评价订单">
      <el-form :model="ratingForm">
        <el-form-item label="评分">
          <el-rate v-model="ratingForm.score" />
        </el-form-item>
        <el-form-item label="评价">
          <el-input v-model="ratingForm.comment" type="textarea" rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRatingDialog = false">取消</el-button>
        <el-button type="primary" @click="submitRating">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { orderApi } from '../api/request'

const userStore = useUserStore()
const loading = ref(false)
const activeTab = ref('all')
const orders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const showRatingDialog = ref(false)
const selectedOrder = ref(null)

const ratingForm = ref({
  score: 5,
  comment: '',
})

const loadOrders = async (page = 1) => {
  loading.value = true
  try {
    let response
    if (activeTab.value === 'all') {
      response = await orderApi.getOrdersByBuyer(userStore.user.userId, page - 1, pageSize.value)
    } else {
      response = await orderApi.getOrdersByStatus(activeTab.value, page - 1, pageSize.value)
    }
    orders.value = response.data.content || []
    total.value = response.data.totalElements || 0
  } catch (error) {
    ElMessage.error('加载订单失败')
  } finally {
    loading.value = false
  }
}

const canUpdateStatus = (order) => {
  return ['待支付', '已支付', '已发货'].includes(order.orderStatus)
}

const canRate = (order) => {
  return order.orderStatus === '交易完成'
}

const updateStatus = (order) => {
  let nextStatus = ''
  if (order.orderStatus === '待支付') nextStatus = '已支付'
  else if (order.orderStatus === '已支付') nextStatus = '已发货'
  else if (order.orderStatus === '已发货') nextStatus = '已收货'

  if (nextStatus) {
    orderApi.updateOrderStatus(order.orderId, nextStatus).then(() => {
      ElMessage.success('状态更新成功')
      loadOrders(currentPage.value)
    }).catch(error => {
      ElMessage.error('状态更新失败')
    })
  }
}

const viewOrder = (order) => {
  ElMessage.info('订单详情功能开发中')
}

const rateOrder = (order) => {
  selectedOrder.value = order
  ratingForm.value = { score: 5, comment: '' }
  showRatingDialog.value = true
}

const submitRating = async () => {
  try {
    await orderApi.rateOrder(selectedOrder.value.orderId, ratingForm.value.score, ratingForm.value.comment, 'buyer')
    ElMessage.success('评价成功')
    showRatingDialog.value = false
    loadOrders(currentPage.value)
  } catch (error) {
    ElMessage.error('评价失败')
  }
}

onMounted(() => {
  loadOrders()
})
</script>

<style scoped lang="scss">
.my-orders-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  h2 {
    margin: 0 0 20px 0;
  }
}

.pagination {
  text-align: center;
  margin-top: 20px;
}
</style>
