import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor - attach token
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
}, error => Promise.reject(error))

// Response interceptor - handle errors
api.interceptors.response.use(response => {
  const data = response.data
  if (data.code !== 200) {
    console.warn('API Error:', data.message)
  }
  return data
}, error => {
  if (error.response?.status === 401) {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    if (window.location.pathname.startsWith('/admin') &&
        window.location.pathname !== '/admin/login') {
      window.location.href = '/admin/login'
    }
  }
  return Promise.reject(error)
})

export default api

// ============ Auth API ============
export const authAPI = {
  login: (username, password) => api.post('/auth/login', null, { params: { username, password } }),
  refresh: (token) => api.post('/auth/refresh', null, { params: { token } }),
  current: () => api.get('/auth/current')
}

// ============ User API ============
export const userAPI = {
  list: (params) => api.post('/user/list', params),
  getById: (id) => api.get(`/user/${id}`),
  update: (id, data) => api.put(`/user/${id}`, data),
  delete: (id) => api.delete(`/user/${id}`)
}

// ============ Banner API ============
export const bannerAPI = {
  list: () => api.get('/banner/list'),
  adminList: (params) => api.get('/banner/admin/list', { params }),
  create: (data) => api.post('/banner', data),
  update: (id, data) => api.put(`/banner/${id}`, data),
  delete: (id) => api.delete(`/banner/${id}`)
}

// ============ Article API ============
export const articleAPI = {
  list: (params) => api.get('/article/list', { params }),
  getById: (id) => api.get(`/article/${id}`),
  create: (data) => api.post('/article', data),
  update: (id, data) => api.put(`/article/${id}`, data),
  delete: (id) => api.delete(`/article/${id}`)
}

// ============ Category API ============
export const categoryAPI = {
  list: () => api.get('/category/list'),
  create: (data) => api.post('/category', data),
  update: (id, data) => api.put(`/category/${id}`, data),
  delete: (id) => api.delete(`/category/${id}`)
}

// ============ Zodiac API ============
export const zodiacAPI = {
  list: () => api.get('/zodiac/list'),
  getById: (id) => api.get(`/zodiac/${id}`),
  create: (data) => api.post('/zodiac', data),
  update: (id, data) => api.put(`/zodiac/${id}`, data),
  delete: (id) => api.delete(`/zodiac/${id}`)
}

// ============ Story API ============
export const storyAPI = {
  list: (zodiacId) => api.get('/story/list', { params: { zodiacId } }),
  getById: (id) => api.get(`/story/${id}`),
  create: (data) => api.post('/story', data),
  update: (id, data) => api.put(`/story/${id}`, data),
  delete: (id) => api.delete(`/story/${id}`)
}

// ============ Clay Sculpture API ============
export const sculptureAPI = {
  list: (params) => api.get('/clay-sculpture/list', { params }),
  getById: (id) => api.get(`/clay-sculpture/${id}`),
  create: (data) => api.post('/clay-sculpture', data),
  update: (id, data) => api.put(`/clay-sculpture/${id}`, data),
  delete: (id) => api.delete(`/clay-sculpture/${id}`)
}

// ============ Personality Test API ============
export const testAPI = {
  getQuestions: () => api.get('/test/questions'),
  submit: (answers, testDuration, userId) =>
    api.post('/test/submit', answers, { params: { testDuration, userId } }),
  getResult: (recordId) => api.get(`/test/result/${recordId}`),
  getHistory: (userId) => api.get('/test/history', { params: { userId } })
}

// ============ Product API ============
export const productAPI = {
  list: (params) => api.get('/product/list', { params }),
  hot: () => api.get('/product/hot'),
  recommend: () => api.get('/product/recommend'),
  getById: (id) => api.get(`/product/${id}`),
  create: (data) => api.post('/product', data),
  update: (id, data) => api.put(`/product/${id}`, data),
  delete: (id) => api.delete(`/product/${id}`)
}

// ============ Product Category API ============
export const productCategoryAPI = {
  list: () => api.get('/product/category/list'),
  create: (data) => api.post('/product/category', data),
  update: (id, data) => api.put(`/product/category/${id}`, data),
  delete: (id) => api.delete(`/product/category/${id}`)
}

// ============ Gallery API ============
export const galleryAPI = {
  list: (params) => api.get('/gallery/list', { params }),
  create: (data) => api.post('/gallery', data),
  update: (id, data) => api.put(`/gallery/${id}`, data),
  delete: (id) => api.delete(`/gallery/${id}`)
}

// ============ Partner API ============
export const partnerAPI = {
  list: () => api.get('/partner/list'),
  create: (data) => api.post('/partner', data),
  update: (id, data) => api.put(`/partner/${id}`, data),
  delete: (id) => api.delete(`/partner/${id}`)
}

// ============ Team Member API ============
export const teamAPI = {
  list: () => api.get('/team-member/list'),
  create: (data) => api.post('/team-member', data),
  update: (id, data) => api.put(`/team-member/${id}`, data),
  delete: (id) => api.delete(`/team-member/${id}`)
}

// ============ Feedback API ============
export const feedbackAPI = {
  submit: (data) => api.post('/feedback', data),
  list: (params) => api.get('/feedback/list', { params }),
  handle: (id) => api.put(`/feedback/${id}/handle`)
}

// ============ File API ============
export const fileAPI = {
  upload: (formData) => api.post('/file/upload', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  }),
  delete: (id) => api.delete(`/file/${id}`)
}

// ============ Statistics API ============
export const statsAPI = {
  dashboard: () => api.get('/statistics/dashboard'),
  recordBehavior: (data) => api.post('/statistics/behavior', data),
  daily: (params) => api.get('/statistics/daily', { params })
}

// ============ Admin API ============
export const adminAPI = {
  dashboard: () => api.get('/admin/dashboard'),
  users: (params) => api.get('/admin/users', { params }),
  products: (params) => api.get('/admin/products', { params }),
  articles: (params) => api.get('/admin/articles', { params }),
  questions: () => api.get('/admin/questions'),
  banners: () => api.get('/admin/banners')
}
