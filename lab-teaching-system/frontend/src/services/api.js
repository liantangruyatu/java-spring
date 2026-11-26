import axios from 'axios'

export const api = axios.create({
  baseURL: '/',
  timeout: 5000,
})

// Initialize Authorization header from localStorage if present
const auth = localStorage.getItem('auth')
if (auth) {
  api.defaults.headers.common['Authorization'] = 'Basic ' + auth
}

export async function setAuthHeader(username, password) {
  const token = 'Basic ' + btoa(username + ':' + password)
  api.defaults.headers.common['Authorization'] = token
  localStorage.setItem('auth', token)
  // fetch current user
  const res = await api.get('/api/public/me')
  localStorage.setItem('user', JSON.stringify(res.data))
  return res.data
}

export function clearAuth() {
  delete api.defaults.headers.common['Authorization']
  localStorage.removeItem('auth')
  localStorage.removeItem('user')
}

export function currentUser() {
  const s = localStorage.getItem('user')
  return s ? JSON.parse(s) : null
}

// Add response interceptor to handle 401 errors
api.interceptors.response.use(
  response => response,
  error => {
    // Prevent browser from showing default basic auth prompt
    if (error.response?.status === 401) {
      // Clear auth data and redirect to login
      clearAuth()
      // Don't let the error propagate to trigger browser auth
      return Promise.reject(new Error('认证失败，请重新登录'))
    }
    return Promise.reject(error)
  }
)
