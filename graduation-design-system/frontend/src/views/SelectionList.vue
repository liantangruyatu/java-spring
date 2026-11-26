<template>
  <div class="selection-list-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>选题管理</h3>
        </div>
      </template>
      <el-table :data="selections" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="student.name" label="学生姓名" width="120" />
        <el-table-column prop="topic.title" label="题目名称" width="300" />
        <el-table-column prop="topic.type" label="题目类型" width="120" />
        <el-table-column prop="topic.teacher.name" label="指导教师" width="120" />
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已分配' ? 'success' : 'info'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="选择时间" width="200" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'SelectionList',
  data() {
    return {
      selections: []
    }
  },
  mounted() {
    this.fetchSelections()
  },
  methods: {
    fetchSelections() {
      axios.get('/selection/list')
        .then(response => {
          if (response.data.code === 1) {
            this.selections = response.data.data
          }
        })
        .catch(error => {
          console.error('获取选题列表失败:', error)
        })
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.selection-list-container {
  height: 100%;
}
</style>
