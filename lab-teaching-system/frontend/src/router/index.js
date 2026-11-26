import { createRouter, createWebHistory } from 'vue-router'
import Login from '../pages/Login.vue'
import Home from '../pages/Home.vue'
import CourseList from '../pages/CourseList.vue'
import ExperimentList from '../pages/ExperimentList.vue'
import LabList from '../pages/LabList.vue'
import Reservation from '../pages/Reservation.vue'
import ReportUpload from '../pages/ReportUpload.vue'
import ReportList from '../pages/ReportList.vue'
import GradeList from '../pages/GradeList.vue'
import CourseManage from '../pages/admin/CourseManage.vue'
import ExperimentManage from '../pages/admin/ExperimentManage.vue'
import LabManage from '../pages/admin/LabManage.vue'
import Dashboard from '../pages/Dashboard.vue'

const routes = [
  { path: '/login', name: 'Login', component: Login },
  { path: '/', name: 'Home', component: Home },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/courses', name: 'CourseList', component: CourseList },
  { path: '/courses/:courseId/experiments', name: 'Experiments', component: ExperimentList, props: true },
  { path: '/labs', name: 'Labs', component: LabList },
  { path: '/reservation', name: 'Reservation', component: Reservation },
  { path: '/upload', name: 'ReportUpload', component: ReportUpload },
  { path: '/reports', name: 'ReportList', component: ReportList },
  { path: '/grades', name: 'GradeList', component: GradeList },
  { path: '/admin/courses', name: 'CourseManage', component: CourseManage },
  { path: '/admin/experiments', name: 'ExperimentManage', component: ExperimentManage },
  { path: '/admin/labs', name: 'LabManage', component: LabManage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const auth = localStorage.getItem('auth')
  const user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null
  
  // 允许访问登录页
  if (to.path === '/login') {
    next()
    return
  }
  
  // 检查是否认证
  if (!auth) {
    next('/login')
    return
  }
  
  // 检查权限 (可选)
  next()
})

export default router
