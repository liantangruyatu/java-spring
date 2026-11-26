<template>
  <div class="teacher-list-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>教师管理</h3>
          <el-button type="primary" @click="openAddTeacherDialog">添加教师</el-button>
        </div>
      </template>
      <el-table :data="teachers" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="username" label="用户名" width="180" />
        <el-table-column prop="gender" label="性别" width="100" />
        <el-table-column prop="title" label="职称" width="150" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="phone" label="电话" width="150" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditTeacherDialog(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteTeacher(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加教师对话框 -->
    <el-dialog v-model="dialogVisible" title="添加教师" width="500px">
      <el-form :model="teacherForm" :rules="rules" ref="teacherFormRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="teacherForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="teacherForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="teacherForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="teacherForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="title">
          <el-input v-model="teacherForm.title" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="teacherForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="teacherForm.phone" placeholder="请输入电话" />
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
  name: 'TeacherList',
  data() {
    return {
      teachers: [],
      dialogVisible: false,
      teacherForm: {
        name: '',
        username: '',
        password: '',
        gender: '',
        title: '',
        email: '',
        phone: ''
      },
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        title: [{ required: true, message: '请输入职称', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.fetchTeachers()
  },
  methods: {
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
    openAddTeacherDialog() {
      this.resetForm()
      this.dialogVisible = true
    },
    openEditTeacherDialog(teacher) {
      this.teacherForm = { ...teacher }
      this.dialogVisible = true
    },
    deleteTeacher(id) {
      this.$confirm('确定要删除这个教师吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(`/teacher/delete/${id}`)
          .then(response => {
            if (response.data.code === 1) {
              this.$message.success('删除成功')
              this.fetchTeachers()
            } else {
              this.$message.error(response.data.message)
            }
          })
          .catch(error => {
            console.error('删除教师失败:', error)
            this.$message.error('删除失败')
          })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    resetForm() {
      this.teacherForm = {
        name: '',
        username: '',
        password: '',
        gender: '',
        title: '',
        email: '',
        phone: ''
      }
      this.$refs.teacherFormRef?.resetFields()
    },
    submitForm() {
      this.$refs.teacherFormRef.validate((valid) => {
        if (valid) {
          if (this.teacherForm.id) {
            // 编辑教师
            axios.post('/teacher/update', this.teacherForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('编辑成功')
                  this.dialogVisible = false
                  this.fetchTeachers()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('编辑教师失败:', error)
                this.$message.error('编辑失败')
              })
          } else {
            // 添加教师
            axios.post('/teacher/add', this.teacherForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('添加成功')
                  this.dialogVisible = false
                  this.fetchTeachers()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('添加教师失败:', error)
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

.teacher-list-container {
  height: 100%;
}
</style>
