import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import TeacherList from '../views/TeacherList.vue'
import TopicList from '../views/TopicList.vue'
import StudentList from '../views/StudentList.vue'
import SelectionList from '../views/SelectionList.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/teachers',
        name: 'TeacherList',
        component: TeacherList
      },
      {
        path: '/topics',
        name: 'TopicList',
        component: TopicList
      },
      {
        path: '/students',
        name: 'StudentList',
        component: StudentList
      },
      {
        path: '/selections',
        name: 'SelectionList',
        component: SelectionList
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
