<template>
  <div class="topic-list-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>题目管理</h3>
          <el-button type="primary" @click="openAddTopicDialog">添加题目</el-button>
        </div>
      </template>
      <el-table :data="topics" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="题目名称" width="300" />
        <el-table-column prop="type" label="题目类型" width="120" />
        <el-table-column prop="description" label="题目描述" show-overflow-tooltip />
        <el-table-column prop="teacher.name" label="指导教师" width="120" />
        <el-table-column prop="maxStudents" label="最大学生数" width="120" />
        <el-table-column prop="selectedCount" label="已选学生数" width="120" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditTopicDialog(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteTopic(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加题目对话框 -->
    <el-dialog v-model="dialogVisible" title="添加题目" width="600px">
      <el-form :model="topicForm" :rules="rules" ref="topicFormRef" label-width="100px">
        <el-form-item label="题目名称" prop="title">
          <el-input v-model="topicForm.title" placeholder="请输入题目名称" />
        </el-form-item>
        <el-form-item label="题目类型" prop="type">
          <el-select v-model="topicForm.type" placeholder="请选择题目类型">
            <el-option label="软件设计" value="软件设计" />
            <el-option label="硬件设计" value="硬件设计" />
            <el-option label="综合设计" value="综合设计" />
          </el-select>
        </el-form-item>
        <el-form-item label="题目描述" prop="description">
          <el-input v-model="topicForm.description" type="textarea" rows="4" placeholder="请输入题目描述" />
        </el-form-item>
        <el-form-item label="指导教师" prop="teacherId">
          <el-select v-model="topicForm.teacherId" placeholder="请选择指导教师">
            <el-option v-for="teacher in teachers" :key="teacher.id" :label="teacher.name" :value="teacher.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="最大学生数" prop="maxStudents">
          <el-input-number v-model="topicForm.maxStudents" :min="1" :max="10" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'TopicList',
  data() {
    return {
      topics: [],
      teachers: [],
      dialogVisible: false,
      topicForm: {
        title: '',
        type: '',
        description: '',
        teacherId: null,
        maxStudents: 1
      },
      rules: {
        title: [{ required: true, message: '请输入题目名称', trigger: 'blur' }],
        type: [{ required: true, message: '请选择题目类型', trigger: 'blur' }],
        description: [{ required: true, message: '请输入题目描述', trigger: 'blur' }],
        teacherId: [{ required: true, message: '请选择指导教师', trigger: 'blur' }],
        maxStudents: [{ required: true, message: '请输入最大学生数', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.fetchTopics()
    this.fetchTeachers()
  },
  methods: {
    fetchTopics() {
      axios.get('/topic/list')
        .then(response => {
          if (response.data.code === 1) {
            this.topics = response.data.data
          }
        })
        .catch(error => {
          console.error('获取题目列表失败:', error)
        })
    },
    fetchTeachers() {
      axios.get('/teacher/list')
        .then(response => {
          if (response.data.code === 1) {
            this.teachers = response.data.data
          }
        })
        .catch(error => {
          console.error('获取教师列表失败:', error)
        })
    },
    openAddTopicDialog() {
      this.resetForm()
      this.dialogVisible = true
    },
    openEditTopicDialog(topic) {
      this.topicForm = { ...topic }
      this.topicForm.teacherId = topic.teacher?.id
      this.dialogVisible = true
    },
    deleteTopic(id) {
      this.$confirm('确定要删除这个题目吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(`/topic/delete/${id}`)
          .then(response => {
            if (response.data.code === 1) {
              this.$message.success('删除成功')
              this.fetchTopics()
            } else {
              this.$message.error(response.data.message)
            }
          })
          .catch(error => {
            console.error('删除题目失败:', error)
            this.$message.error('删除失败')
          })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    resetForm() {
      this.topicForm = {
        title: '',
        type: '',
        description: '',
        teacherId: null,
        maxStudents: 1
      }
      this.$refs.topicFormRef?.resetFields()
    },
    submitForm() {
      this.$refs.topicFormRef.validate((valid) => {
        if (valid) {
          if (this.topicForm.id) {
            // 编辑题目
            axios.post('/topic/update', this.topicForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('编辑成功')
                  this.dialogVisible = false
                  this.fetchTopics()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('编辑题目失败:', error)
                this.$message.error('编辑失败')
              })
          } else {
            // 添加题目
            axios.post('/topic/add', this.topicForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('添加成功')
                  this.dialogVisible = false
                  this.fetchTopics()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('添加题目失败:', error)
                this.$message.error('添加失败')
              })
          }
        }
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

.topic-list-container {
  height: 100%;
}
</style>
