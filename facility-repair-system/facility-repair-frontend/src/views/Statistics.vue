<template>
  <div class="statistics-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>📊 统计分析</span>
          <el-button @click="generateStatistics" :loading="generating">生成统计</el-button>
        </div>
      </template>

      <el-row :gutter="20">
        <el-col :xs="24" :md="12">
          <div ref="chartRef1" style="width: 100%; height: 300px"></div>
        </el-col>
        <el-col :xs="24" :md="12">
          <div ref="chartRef2" style="width: 100%; height: 300px"></div>
        </el-col>
      </el-row>

      <el-divider></el-divider>

      <h3>设施故障率统计</h3>
      <el-table :data="statisticsData" stripe style="width: 100%; margin-top: 20px">
        <el-table-column prop="facilityType" label="设施类型" width="150"></el-table-column>
        <el-table-column prop="totalCount" label="总数" width="100" align="center"></el-table-column>
        <el-table-column prop="brokenCount" label="故障数" width="100" align="center"></el-table-column>
        <el-table-column prop="repairingCount" label="维修中" width="100" align="center"></el-table-column>
        <el-table-column prop="normalCount" label="正常" width="100" align="center"></el-table-column>
        <el-table-column prop="faultRate" label="故障率">
          <template #default="{ row }">
            <el-progress
              :percentage="parseFloat(row.faultRate)"
              :color="row.faultRate > 50 ? '#f56c6c' : '#85ce61'"
            ></el-progress>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { statisticsAPI, facilityAPI } from '../api/index'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'

const chartRef1 = ref(null)
const chartRef2 = ref(null)
const statisticsData = ref([])
const generating = ref(false)

onMounted(() => {
  loadStatistics()
})

const loadStatistics = async () => {
  try {
    const response = await statisticsAPI.getLatest()
    if (response.code === 200) {
      statisticsData.value = response.data || []
      renderCharts()
    }
  } catch (error) {
    ElMessage.error('加载统计数据失败: ' + error.message)
  }
}

const generateStatistics = async () => {
  generating.value = true
  try {
    const response = await statisticsAPI.generate(new Date().toISOString().split('T')[0])
    if (response.code === 200) {
      ElMessage.success('统计数据已生成')
      loadStatistics()
    }
  } catch (error) {
    ElMessage.error('生成统计失败: ' + error.message)
  } finally {
    generating.value = false
  }
}

const renderCharts = () => {
  if (!statisticsData.value.length) return

  // 图表1: 故障率对比
  if (chartRef1.value) {
    const chart1 = echarts.init(chartRef1.value)
    const types = statisticsData.value.map(s => s.facilityType)
    const faultRates = statisticsData.value.map(s => parseFloat(s.faultRate) || 0)

    chart1.setOption({
      title: { text: '设施类型故障率对比' },
      tooltip: { trigger: 'axis' },
      xAxis: { type: 'category', data: types },
      yAxis: { type: 'value', name: '故障率(%)' },
      series: [
        {
          name: '故障率',
          data: faultRates,
          type: 'bar',
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#83bff6' },
              { offset: 0.5, color: '#188df0' },
              { offset: 1, color: '#188df0' }
            ])
          }
        }
      ]
    })
  }

  // 图表2: 各类型设施数量
  if (chartRef2.value) {
    const chart2 = echarts.init(chartRef2.value)
    const data = statisticsData.value.map(s => ({
      value: s.totalCount,
      name: s.facilityType
    }))

    chart2.setOption({
      title: { text: '设施数量分布' },
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
}
</script>

<style scoped>
.statistics-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h3 {
  margin-top: 20px;
  color: #333;
}
</style>
