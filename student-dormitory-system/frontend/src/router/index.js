import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Dorms from '../views/Dorms.vue'
import Students from '../views/Students.vue'
import Stays from '../views/Stays.vue'
import Statistics from '../views/Statistics.vue'
import Alerts from '../views/Alerts.vue'

const routes = [
  { path: '/', component: Dashboard },
  { path: '/dorms', component: Dorms },
  { path: '/students', component: Students },
  { path: '/stays', component: Stays },
  { path: '/statistics', component: Statistics },
  { path: '/alerts', component: Alerts }
]

const router = createRouter({ history: createWebHistory(), routes })
export default router
