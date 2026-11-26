<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">🏗️</div>
            <div class="stat-info">
              <p class="stat-label">总设施数</p>
              <p class="stat-value">{{ stats.total || 0 }}</p>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">⚠️</div>
            <div class="stat-info">
              <p class="stat-label">故障设施</p>
              <p class="stat-value">{{ stats.broken || 0 }}</p>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">🔧</div>
            <div class="stat-info">
              <p class="stat-label">维修中</p>
              <p class="stat-value">{{ stats.repairing || 0 }}</p>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">✅</div>
            <div class="stat-info">
              <p class="stat-label">完成率</p>
              <p class="stat-value">{{ stats.rate || '0%' }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>📊 设施类型分布</span>
            </div>
          </template>
          <div ref="chartRef" style="width: 100%; height: 300px"></div>
        </el-card>
      </el-col>

      <el-col :xs="24" :md="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>📈 故障率统计</span>
            </div>
          </template>
          <el-table :data="facilityStats" size="small">
            <el-table-column prop="type" label="设施类型" width="100"></el-table-column>
            <el-table-column prop="total" label="总数" width="80" align="center"></el-table-column>
            <el-table-column prop="broken" label="故障数" width="80" align="center"></el-table-column>
            <el-table-column prop="rate" label="故障率" align="center"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :xs="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>🔔 最近的报修单</span>
            </div>
          </template>
          <el-table :data="recentRepairs" size="small">
            <el-table-column prop="id" label="编号" width="80"></el-table-column>
            <el-table-column prop="facility" label="设施"></el-table-column>
            <el-table-column prop="status" label="状态">
              <template #default="{ row }">
                <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="submitTime" label="提交时间"></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { facilityAPI, repairOrderAPI } from '../api/index'
import * as echarts from 'echarts'

const chartRef = ref(null)
const stats = ref({
  total: 0,
  broken: 0,
  repairing: 0,
  rate: '0%'
})

const facilityStats = ref([])
const recentRepairs = ref([])

onMounted(() => {
  loadStats()
  loadChart()
  loadRecentRepairs()
})

const loadStats = async () => {
  try {
    const response = await facilityAPI.getStats()
    if (response.code === 200) {
      const allStats = response.data
      let totalCount = 0
      let totalBroken = 0
      
      Object.values(allStats).forEach(stat => {
        totalCount += stat.total || 0
        totalBroken += stat.broken || 0
      })
      
      stats.value = {
        total: totalCount,
        broken: totalBroken,
        repairing: 0,
        rate: totalCount > 0 ? ((totalCount - totalBroken) / totalCount * 100).toFixed(1) + '%' : '0%'
      }

      // 准备表格数据
      facilityStats.value = [
        { type: '路灯', ...allStats.street_light },
        { type: '公园长椅', ...allStats.park_bench },
        { type: '公共厕所', ...allStats.public_toilet },
        { type: '健身器材', ...allStats.fitness_equipment }
      ]
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const loadChart = async () => {
  try {
    const response = await facilityAPI.getStats()
    if (response.code === 200 && chartRef.value) {
      const chart = echarts.init(chartRef.value)
      
      const data = [
        { value: response.data.street_light?.total || 0, name: '路灯' },
        { value: response.data.park_bench?.total || 0, name: '公园长椅' },
        { value: response.data.public_toilet?.total || 0, name: '公共厕所' },
        { value: response.data.fitness_equipment?.total || 0, name: '健身器材' }
      ]
      
      chart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [
          {
            name: '设施数量',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      })
    }
  } catch (error) {
    console.error('加载图表失败:', error)
  }
}

const loadRecentRepairs = async () => {
  try {
    const response = await repairOrderAPI.getList(0, 5)
    if (response.code === 200) {
      recentRepairs.value = response.data.content || []
    }
  } catch (error) {
    console.error('加载报修单失败:', error)
  }
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
.dashboard-container {
  padding: 20px;
}

.stat-card {
  border: none;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.stat-content {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  font-size: 32px;
  margin-right: 20px;
}

.stat-info {
  flex: 1;
}

.stat-label {
  margin: 0;
  font-size: 12px;
  opacity: 0.8;
}

.stat-value {
  margin: 8px 0 0 0;
  font-size: 24px;
  font-weight: bold;
}

:deep(.el-card__header) {
  padding: 15px 20px;
  border-bottom: 1px solid #e6e6e6;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
