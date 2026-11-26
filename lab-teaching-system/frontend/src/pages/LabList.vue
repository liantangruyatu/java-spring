<template>
  <div class="lab-list-page">
    <h1>实验室列表</h1>

    <div class="filter-bar">
      <select v-model="filterStatus">
        <option value="">所有状态</option>
        <option value="AVAILABLE">可用</option>
        <option value="IN_USE">使用中</option>
        <option value="MAINTENANCE">维护中</option>
      </select>
      <input v-model="searchKeyword" type="text" placeholder="搜索实验室名称或位置...">
    </div>

    <div v-if="filteredLabs.length > 0" class="labs-grid">
      <div v-for="lab in filteredLabs" :key="lab.id" class="lab-card">
        <div class="lab-status" :class="'status-' + lab.status">
          {{ getStatusLabel(lab.status) }}
        </div>

        <h3>{{ lab.name }}</h3>

        <div class="lab-info">
          <div class="info-item">
            <span class="label">位置:</span>
            <span class="value">{{ lab.location }}</span>
          </div>
          <div class="info-item">
            <span class="label">容量:</span>
            <span class="value">{{ lab.capacity }}人</span>
          </div>
        </div>

        <div class="lab-footer">
          <button 
            v-if="lab.status === 'AVAILABLE'" 
            @click="goToReservation" 
            class="btn btn-primary"
          >
            预约实验
          </button>
          <button v-else class="btn" disabled>暂不可预约</button>
        </div>
      </div>
    </div>
    <p v-else class="no-data">暂无实验室</p>
  </div>
</template>

<script>
import { api } from '../services/api'

export default {
  name: 'LabList',
  data() {
    return {
      labs: [],
      filterStatus: '',
      searchKeyword: ''
    }
  },
  computed: {
    filteredLabs() {
      return this.labs.filter(lab => {
        const statusMatch = !this.filterStatus || lab.status === this.filterStatus
        const searchMatch = lab.name.includes(this.searchKeyword) || lab.location.includes(this.searchKeyword)
        return statusMatch && searchMatch
      })
    }
  },
  mounted() {
    this.loadLabs()
  },
  methods: {
    async loadLabs() {
      try {
        const response = await api.get('/api/admin/labs')
        this.labs = response.data
      } catch (error) {
        console.error('Failed to load labs:', error)
        alert('加载实验室失败')
      }
    },
    getStatusLabel(status) {
      const labels = {
        'AVAILABLE': '✓ 可用',
        'IN_USE': '⏱ 使用中',
        'MAINTENANCE': '🔧 维护中'
      }
      return labels[status] || status
    },
    goToReservation() {
      this.$router.push('/reservation')
    }
  }
}
</script>

<style scoped>
.lab-list-page {
  padding: 20px;
}

.lab-list-page h1 {
  color: #333;
  margin-bottom: 20px;
}

.filter-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 30px;
}

.filter-bar select,
.filter-bar input {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.filter-bar input {
  flex: 1;
}

.labs-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.lab-card {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  position: relative;
}

.lab-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  transform: translateY(-2px);
}

.lab-status {
  position: absolute;
  top: 15px;
  right: 15px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.status-AVAILABLE {
  background: #e6ffe6;
  color: #22543d;
}

.status-IN_USE {
  background: #fff5e6;
  color: #7c2d12;
}

.status-MAINTENANCE {
  background: #ffe6e6;
  color: #742a2a;
}

.lab-card h3 {
  margin: 0 0 20px 0;
  color: #333;
  font-size: 20px;
  padding-right: 100px;
}

.lab-info {
  flex: 1;
  margin-bottom: 15px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
  font-size: 14px;
}

.info-item:last-child {
  border-bottom: none;
}

.info-item .label {
  color: #666;
  font-weight: 500;
}

.info-item .value {
  color: #333;
  font-weight: 600;
}

.lab-footer {
  display: flex;
  gap: 10px;
}

.btn {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  background: white;
  color: #333;
  transition: all 0.3s ease;
  flex: 1;
  text-align: center;
  font-size: 14px;
}

.btn:hover:not(:disabled) {
  background: #f5f5f5;
}

.btn-primary {
  background: #667eea;
  color: white;
  border-color: #667eea;
}

.btn-primary:hover {
  background: #5568d3;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.no-data {
  text-align: center;
  color: #999;
  padding: 40px 20px;
}
</style>

