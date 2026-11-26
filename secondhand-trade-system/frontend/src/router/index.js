import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/products',
    name: 'products',
    component: () => import('../views/Products.vue')
  },
  {
    path: '/product/:id',
    name: 'productDetail',
    component: () => import('../views/ProductDetail.vue')
  },
  {
    path: '/my-products',
    name: 'myProducts',
    component: () => import('../views/MyProducts.vue')
  },
  {
    path: '/my-orders',
    name: 'myOrders',
    component: () => import('../views/MyOrders.vue')
  },
  {
    path: '/publish-product',
    name: 'publishProduct',
    component: () => import('../views/PublishProduct.vue')
  },
  {
    path: '/messages',
    name: 'messages',
    component: () => import('../views/Messages.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    component: () => import('../views/UserProfile.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router