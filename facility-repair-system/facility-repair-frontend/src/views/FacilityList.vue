<template>
  <div class="facility-list-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>🏗️ 公共设施管理</span>
          <el-button type="primary" @click="showCreateDialog = true">+ 新增设施</el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item>
          <el-input
            v-model="searchForm.location"
            placeholder="搜索位置..."
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="searchForm.type"
            placeholder="选择类型"
            clearable
          >
            <el-option label="路灯" value="street_light"></el-option>
            <el-option label="公园长椅" value="park_bench"></el-option>
            <el-option label="公共厕所" value="public_toilet"></el-option>
            <el-option label="健身器材" value="fitness_equipment"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="searchForm.status"
            placeholder="选择状态"
            clearable
          >
            <el-option label="正常" value="normal"></el-option>
            <el-option label="损坏" value="broken"></el-option>
            <el-option label="维修中" value="repairing"></el-option>
            <el-option label="已报废" value="discarded"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadFacilities">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="facilities" stripe style="width: 100%; margin-top: 20px">
        <el-table-column prop="id" label="ID" width="60"></el-table-column>
        <el-table-column prop="facilityCode" label="编号" width="120"></el-table-column>
        <el-table-column prop="facilityType" label="类型">
          <template #default="{ row }">
            <el-tag>{{ row.facilityType }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="location" label="位置" min-width="150"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              @click="handleRepair(row.id)"
            >
              报修
            </el-button>
            <el-button
              v-if="row.status === 'normal'"
              type="warning"
              size="small"
              @click="handleStatusChange(row.id, 'broken')"
            >
              标记故障
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增设施对话框 -->
    <el-dialog v-model="showCreateDialog" title="新增设施" width="500px">
      <el-form :model="newFacility" label-width="120px">
        <el-form-item label="设施编号">
          <el-input v-model="newFacility.facilityCode" placeholder="自动生成"></el-input>
        </el-form-item>
        <el-form-item label="设施类型">
          <el-select v-model="newFacility.facilityType" placeholder="请选择">
            <el-option label="路灯" value="street_light"></el-option>
            <el-option label="公园长椅" value="park_bench"></el-option>
            <el-option label="公共厕所" value="public_toilet"></el-option>
            <el-option label="健身器材" value="fitness_equipment"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="位置">
          <el-input v-model="newFacility.location" placeholder="请输入位置"></el-input>
        </el-form-item>
        <el-form-item label="纬度">
          <el-input v-model.number="newFacility.latitude" placeholder="39.9042"></el-input>
        </el-form-item>
        <el-form-item label="经度">
          <el-input v-model.number="newFacility.longitude" placeholder="116.4074"></el-input>
        </el-form-item>
        <el-form-item label="安装日期">
          <el-date-picker v-model="newFacility.installDate" type="date"></el-date-picker>
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showCreateDialog = false">取消</el-button>
          <el-button type="primary" @click="handleCreate">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { facilityAPI } from '../api/index'
import { ElMessage } from 'element-plus'

const router = useRouter()
const facilities = ref([])
const showCreateDialog = ref(false)

const searchForm = ref({
  location: '',
  type: '',
  status: ''
})

const newFacility = ref({
  facilityCode: '',
  facilityType: '',
  location: '',
  latitude: null,
  longitude: null,
  installDate: null
})

onMounted(() => {
  loadFacilities()
})

const loadFacilities = async () => {
  try {
    const response = await facilityAPI.getList(0, 100)
    if (response.code === 200) {
      facilities.value = response.data.content || []
    }
  } catch (error) {
    ElMessage.error('加载设施列表失败: ' + error.message)
  }
}

const handleCreate = async () => {
  try {
    const response = await facilityAPI.create(newFacility.value)
    if (response.code === 200) {
      ElMessage.success('设施创建成功')
      showCreateDialog.value = false
      newFacility.value = {}
      loadFacilities()
    }
  } catch (error) {
    ElMessage.error('创建设施失败: ' + error.message)
  }
}

const handleRepair = (facilityId) => {
  router.push(`/repair-order/create?facilityId=${facilityId}`)
}

const handleStatusChange = async (facilityId, status) => {
  try {
    const response = await facilityAPI.updateStatus(facilityId, status)
    if (response.code === 200) {
      ElMessage.success('状态更新成功')
      loadFacilities()
    }
  } catch (error) {
    ElMessage.error('更新状态失败: ' + error.message)
  }
}

const getStatusType = (status) => {
  const statusMap = {
    'normal': 'success',
    'broken': 'danger',
    'repairing': 'warning',
    'discarded': 'info'
  }
  return statusMap[status] || 'info'
}
</script>

<style scoped>
.facility-list-container {
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
