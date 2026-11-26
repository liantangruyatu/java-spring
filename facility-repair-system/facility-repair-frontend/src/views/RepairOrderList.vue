<template>
  <div class="repair-order-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>🔧 报修单管理</span>
          <el-button type="primary" @click="goToCreate">+ 新建报修</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item>
          <el-select
            v-model="searchForm.status"
            placeholder="选择状态"
            clearable
          >
            <el-option label="已提交" value="SUBMITTED"></el-option>
            <el-option label="已接受" value="ACCEPTED"></el-option>
            <el-option label="维修中" value="PROCESSING"></el-option>
            <el-option label="已完成" value="COMPLETED"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadRepairOrders">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="repairOrders" stripe style="width: 100%; margin-top: 20px">
        <el-table-column prop="repairCode" label="报修编号" width="120"></el-table-column>
        <el-table-column prop="facilityCode" label="设施编号" width="100"></el-table-column>
        <el-table-column prop="problemDescription" label="问题描述" min-width="150"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="submitTime" label="提交时间" width="180"></el-table-column>
        <el-table-column prop="repairHours" label="耗时(小时)" width="100" align="center"></el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              @click="handleDetail(row.id)"
            >
              详情
            </el-button>
            <el-button
              v-if="row.status === 'PROCESSING'"
              type="success"
              size="small"
              @click="handleComplete(row.id)"
            >
              完成
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 20px; text-align: right"
        @change="loadRepairOrders"
      ></el-pagination>
    </el-card>

    <!-- 详情对话框 -->
    <el-dialog v-model="showDetailDialog" title="报修单详情" width="600px">
      <div v-if="detailRepair">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="报修编号">{{ detailRepair.repairCode }}</el-descriptions-item>
          <el-descriptions-item label="设施编号">{{ detailRepair.facilityCode }}</el-descriptions-item>
          <el-descriptions-item label="设施类型">{{ detailRepair.facilityType }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(detailRepair.status)">{{ detailRepair.status }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="市民">{{ detailRepair.citizenName }}</el-descriptions-item>
          <el-descriptions-item label="维修工">{{ detailRepair.workerName || '未分配' }}</el-descriptions-item>
          <el-descriptions-item label-align="top" label="问题描述" :span="2">
            {{ detailRepair.problemDescription }}
          </el-descriptions-item>
          <el-descriptions-item label="提交时间" :span="2">{{ detailRepair.submitTime }}</el-descriptions-item>
          <el-descriptions-item label="维修反馈" :span="2">{{ detailRepair.feedback || '暂无反馈' }}</el-descriptions-item>
        </el-descriptions>

        <div v-if="detailRepair.status === 'PROCESSING'" style="margin-top: 20px">
          <el-form label-width="120px">
            <el-form-item label="维修反馈">
              <el-input
                v-model="completeFeedback"
                type="textarea"
                rows="3"
                placeholder="请输入维修完成反馈"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showDetailDialog = false">关闭</el-button>
          <el-button
            v-if="detailRepair?.status === 'PROCESSING'"
            type="success"
            @click="submitCompleteRepair"
          >
            完成维修
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { repairOrderAPI } from '../api/index'
import { ElMessage } from 'element-plus'

const router = useRouter()
const repairOrders = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const searchForm = ref({
  status: ''
})

const showDetailDialog = ref(false)
const detailRepair = ref(null)
const completeFeedback = ref('')

onMounted(() => {
  loadRepairOrders()
})

const loadRepairOrders = async () => {
  try {
    const response = await repairOrderAPI.getList(currentPage.value - 1, pageSize.value)
    if (response.code === 200) {
      repairOrders.value = response.data.content || []
      total.value = response.data.totalElements || 0
    }
  } catch (error) {
    ElMessage.error('加载报修单失败: ' + error.message)
  }
}

const handleDetail = async (id) => {
  try {
    const response = await repairOrderAPI.getById(id)
    if (response.code === 200) {
      detailRepair.value = response.data
      showDetailDialog.value = true
    }
  } catch (error) {
    ElMessage.error('加载详情失败: ' + error.message)
  }
}

const handleComplete = (id) => {
  detailRepair.value = repairOrders.value.find(r => r.id === id)
  showDetailDialog.value = true
}

const submitCompleteRepair = async () => {
  try {
    const response = await repairOrderAPI.process({
      repairOrderId: detailRepair.value.id,
      status: 'completed',
      feedback: completeFeedback.value
    })

    if (response.code === 200) {
      ElMessage.success('报修单已完成')
      showDetailDialog.value = false
      loadRepairOrders()
    }
  } catch (error) {
    ElMessage.error('完成报修失败: ' + error.message)
  }
}

const goToCreate = () => {
  router.push('/repair-order/create')
}

const getStatusType = (status) => {
  const statusMap = {
    'SUBMITTED': 'info',
    'ACCEPTED': 'warning',
    'PROCESSING': 'danger',
    'COMPLETED': 'success'
  }
  return statusMap[status] || 'info'
}
</script>

<style scoped>
.repair-order-list-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
