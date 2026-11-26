<template>
  <div class="student-list-container">
    <el-card shadow="hover">
      <template #header>
        <div class="card-header">
          <h3>学生管理</h3>
          <el-button type="primary" @click="openAddStudentDialog">添加学生</el-button>
        </div>
      </template>
      <el-table :data="students" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="username" label="用户名" width="180" />
        <el-table-column prop="gender" label="性别" width="100" />
        <el-table-column prop="class" label="班级" width="150" />
        <el-table-column prop="major" label="专业" width="150" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="phone" label="电话" width="150" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button type="primary" size="small" @click="openEditStudentDialog(scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="deleteStudent(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加学生对话框 -->
    <el-dialog v-model="dialogVisible" title="添加学生" width="500px">
      <el-form :model="studentForm" :rules="rules" ref="studentFormRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="studentForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="studentForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="studentForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="studentForm.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="class">
          <el-input v-model="studentForm.class" placeholder="请输入班级" />
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="studentForm.major" placeholder="请输入专业" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="studentForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="studentForm.phone" placeholder="请输入电话" />
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
  name: 'StudentList',
  data() {
    return {
      students: [],
      dialogVisible: false,
      studentForm: {
        name: '',
        username: '',
        password: '',
        gender: '',
        class: '',
        major: '',
        email: '',
        phone: ''
      },
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
        class: [{ required: true, message: '请输入班级', trigger: 'blur' }],
        major: [{ required: true, message: '请输入专业', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.fetchStudents()
  },
  methods: {
    fetchStudents() {
      axios.get('/student/list')
        .then(response => {
          if (response.data.code === 1) {
            this.students = response.data.data
          }
        })
        .catch(error => {
          console.error('获取学生列表失败:', error)
        })
    },
    openAddStudentDialog() {
      this.resetForm()
      this.dialogVisible = true
    },
    openEditStudentDialog(student) {
      this.studentForm = { ...student }
      this.dialogVisible = true
    },
    deleteStudent(id) {
      this.$confirm('确定要删除这个学生吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post(`/student/delete/${id}`)
          .then(response => {
            if (response.data.code === 1) {
              this.$message.success('删除成功')
              this.fetchStudents()
            } else {
              this.$message.error(response.data.message)
            }
          })
          .catch(error => {
            console.error('删除学生失败:', error)
            this.$message.error('删除失败')
          })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    resetForm() {
      this.studentForm = {
        name: '',
        username: '',
        password: '',
        gender: '',
        class: '',
        major: '',
        email: '',
        phone: ''
      }
      this.$refs.studentFormRef?.resetFields()
    },
    submitForm() {
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          if (this.studentForm.id) {
            // 编辑学生
            axios.post('/student/update', this.studentForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('编辑成功')
                  this.dialogVisible = false
                  this.fetchStudents()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('编辑学生失败:', error)
                this.$message.error('编辑失败')
              })
          } else {
            // 添加学生
            axios.post('/student/add', this.studentForm)
              .then(response => {
                if (response.data.code === 1) {
                  this.$message.success('添加成功')
                  this.dialogVisible = false
                  this.fetchStudents()
                } else {
                  this.$message.error(response.data.message)
                }
              })
              .catch(error => {
                console.error('添加学生失败:', error)
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

.student-list-container {
  height: 100%;
}
</style>
