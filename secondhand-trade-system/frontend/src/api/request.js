import axiosInstance from './index'

export const userApi = {
  register(data) {
    return axiosInstance.post('/users/register', data)
  },

  login(data) {
    return axiosInstance.post('/users/login', data)
  },

  getUserById(userId) {
    return axiosInstance.get(`/users/${userId}`)
  },

  updateUser(userId, data) {
    return axiosInstance.put(`/users/${userId}`, data)
  },

  getAllUsers() {
    return axiosInstance.get('/users')
  },

  deleteUser(userId) {
    return axiosInstance.delete(`/users/${userId}`)
  },
}

export const productApi = {
  createProduct(data) {
    return axiosInstance.post('/products', data)
  },

  getProductById(productId) {
    return axiosInstance.get(`/products/${productId}`)
  },

  searchProducts(productName, page = 0, size = 10) {
    return axiosInstance.get(`/products/search?productName=${productName}&page=${page}&size=${size}`)
  },

  getProductsByCategory(categoryId, page = 0, size = 10) {
    return axiosInstance.get(`/products/category/${categoryId}?page=${page}&size=${size}`)
  },

  getProductsBySeller(sellerId, page = 0, size = 10) {
    return axiosInstance.get(`/products/seller/${sellerId}?page=${page}&size=${size}`)
  },

  getOnSaleProducts(page = 0, size = 10) {
    return axiosInstance.get(`/products/on-sale?page=${page}&size=${size}`)
  },

  updateProduct(productId, data) {
    return axiosInstance.put(`/products/${productId}`, data)
  },

  deleteProduct(productId) {
    return axiosInstance.delete(`/products/${productId}`)
  },

  changeProductStatus(productId, status) {
    return axiosInstance.put(`/products/${productId}/status?status=${status}`)
  },
}

export const orderApi = {
  createOrder(data) {
    return axiosInstance.post('/orders', data)
  },

  getOrderById(orderId) {
    return axiosInstance.get(`/orders/${orderId}`)
  },

  getOrdersByBuyer(buyerId, page = 0, size = 10) {
    return axiosInstance.get(`/orders/buyer/${buyerId}?page=${page}&size=${size}`)
  },

  getOrdersBySeller(sellerId, page = 0, size = 10) {
    return axiosInstance.get(`/orders/seller/${sellerId}?page=${page}&size=${size}`)
  },

  getOrdersByStatus(status, page = 0, size = 10) {
    return axiosInstance.get(`/orders/status/${status}?page=${page}&size=${size}`)
  },

  updateOrderStatus(orderId, status) {
    return axiosInstance.put(`/orders/${orderId}/status?status=${status}`)
  },

  rateOrder(orderId, score, comment, rateType) {
    return axiosInstance.put(`/orders/${orderId}/rate?score=${score}&comment=${comment}&rateType=${rateType}`)
  },

  deleteOrder(orderId) {
    return axiosInstance.delete(`/orders/${orderId}`)
  },
}

export const categoryApi = {
  createCategory(data) {
    return axiosInstance.post('/categories', data)
  },

  getCategoryById(categoryId) {
    return axiosInstance.get(`/categories/${categoryId}`)
  },

  getAllCategories() {
    return axiosInstance.get('/categories')
  },

  updateCategory(categoryId, data) {
    return axiosInstance.put(`/categories/${categoryId}`, data)
  },

  deleteCategory(categoryId) {
    return axiosInstance.delete(`/categories/${categoryId}`)
  },
}

export const messageApi = {
  sendMessage(data) {
    return axiosInstance.post('/messages', data)
  },

  getMessageById(messageId) {
    return axiosInstance.get(`/messages/${messageId}`)
  },

  getReceiverMessages(receiverId, page = 0, size = 10) {
    return axiosInstance.get(`/messages/receiver/${receiverId}?page=${page}&size=${size}`)
  },

  getUnreadMessages(receiverId, page = 0, size = 10) {
    return axiosInstance.get(`/messages/receiver/${receiverId}/unread?page=${page}&size=${size}`)
  },

  getConversation(senderId, receiverId, page = 0, size = 10) {
    return axiosInstance.get(`/messages/conversation/${senderId}/${receiverId}?page=${page}&size=${size}`)
  },

  markAsRead(messageId) {
    return axiosInstance.put(`/messages/${messageId}/read`)
  },

  deleteMessage(messageId) {
    return axiosInstance.delete(`/messages/${messageId}`)
  },
}
