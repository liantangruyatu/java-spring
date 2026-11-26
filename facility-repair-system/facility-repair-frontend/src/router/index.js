import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Dashboard from '../views/Dashboard.vue'
import FacilityList from '../views/FacilityList.vue'
import RepairOrderList from '../views/RepairOrderList.vue'
import RepairOrderCreate from '../views/RepairOrderCreate.vue'
import Statistics from '../views/Statistics.vue'

const routes = [
  {
    path: '/',
    redirect: '/dashboard'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  {
    path: '/facilities',
    name: 'FacilityList',
    component: FacilityList,
    meta: { requiresAuth: true }
  },
  {
    path: '/repair-orders',
    name: 'RepairOrderList',
    component: RepairOrderList,
    meta: { requiresAuth: true }
  },
  {
    path: '/repair-order/create',
    name: 'RepairOrderCreate',
    component: RepairOrderCreate,
    meta: { requiresAuth: true }
  },
  {
    path: '/statistics',
    name: 'Statistics',
    component: Statistics,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('user')
  
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
  } else if ((to.path === '/login' || to.path === '/register') && isAuthenticated) {
    next('/dashboard')
  } else {
    next()
  }
})

export default router
