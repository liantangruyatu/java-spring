import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 添加请求拦截器
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

// 添加响应拦截器
apiClient.interceptors.response.use((response) => {
  return response.data
}, (error) => {
  if (error.response?.status === 401) {
    localStorage.removeItem('token')
    window.location.href = '/login'
  }
  return Promise.reject(error)
})

// 用户API
export const userAPI = {
  register: (data) => apiClient.post('/user/register', data),
  login: (data) => apiClient.post('/user/login', data),
  getUserInfo: (id) => apiClient.get(`/user/${id}`),
  updateUser: (id, data) => apiClient.put(`/user/${id}`, data),
  getWorkers: () => apiClient.get('/user/workers')
}

// 报修单API
export const repairOrderAPI = {
  create: (data) => apiClient.post('/repair-order/create', data),
  getList: (page = 0, size = 10) => apiClient.get('/repair-order/list', { params: { page, size } }),
  getUserOrders: (userId, page = 0, size = 10) => apiClient.get(`/repair-order/user/${userId}`, { params: { page, size } }),
  getWorkerOrders: (workerId, page = 0, size = 10) => apiClient.get(`/repair-order/worker/${workerId}`, { params: { page, size } }),
  getById: (id) => apiClient.get(`/repair-order/${id}`),
  process: (data) => apiClient.post('/repair-order/process', data)
}

// 设施API
export const facilityAPI = {
  create: (data) => apiClient.post('/facility/create', data),
  getList: (page = 0, size = 10) => apiClient.get('/facility/list', { params: { page, size } }),
  getByType: (facilityType) => apiClient.get(`/facility/type/${facilityType}`),
  getByStatus: (status) => apiClient.get(`/facility/status/${status}`),
  getById: (id) => apiClient.get(`/facility/${id}`),
  updateStatus: (id, status) => apiClient.put(`/facility/${id}/status`, null, { params: { status } }),
  getStats: () => apiClient.get('/facility/stats/summary')
}

// 统计API
export const statisticsAPI = {
  generate: (date) => apiClient.post('/statistics/generate', null, { params: { date } }),
  getByDate: (date) => apiClient.get(`/statistics/date/${date}`),
  getLatest: () => apiClient.get('/statistics/latest')
}
