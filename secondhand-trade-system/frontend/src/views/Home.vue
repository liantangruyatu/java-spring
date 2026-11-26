<template>
  <div class="home-container">
    <el-row :gutter="20" class="welcome-banner">
      <el-col :span="24">
        <div class="banner">
          <h2>欢迎来到中北大学生二手物品交易平台</h2>
          <p>一个安全、便捷的校园二手物品交易社区</p>
          <div class="banner-actions" v-if="!userStore.isLoggedIn">
            <el-button type="primary" size="large" @click="$router.push('/register')">立即注册</el-button>
            <el-button size="large" @click="$router.push('/login')">登录账户</el-button>
          </div>
          <div class="banner-actions" v-else>
            <el-button type="primary" size="large" @click="$router.push('/products')">浏览物品</el-button>
            <el-button size="large" @click="$router.push('/publish-product')">发布物品</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="features">
      <el-col :xs="24" :sm="12" :md="6" v-for="feature in features" :key="feature.id" class="feature-item">
        <el-card>
          <div class="feature-content">
            <div class="feature-icon">{{ feature.icon }}</div>
            <h3>{{ feature.title }}</h3>
            <p>{{ feature.description }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="latest-products">
      <el-col :span="24">
        <h2>最新物品</h2>
      </el-col>
      <el-col :span="24">
        <el-skeleton :loading="loading" :rows="2" animated>
          <el-row :gutter="20">
            <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="product in latestProducts" :key="product.productId">
              <el-card class="product-card" @click="$router.push(`/product/${product.productId}`)">
                <div class="product-image">
                  <img :src="product.imageUrl || 'https://via.placeholder.com/200'" :alt="product.productName" />
                </div>
                <div class="product-info">
                  <h3>{{ product.productName }}</h3>
                  <p class="price">¥{{ product.price }}</p>
                  <p class="seller">{{ product.sellerName }}</p>
                  <el-tag>{{ product.condition }}</el-tag>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-skeleton>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../store/user'
import { productApi } from '../api/request'

const userStore = useUserStore()
const loading = ref(false)
const latestProducts = ref([])

const features = [
  {
    id: 1,
    icon: '🛡️',
    title: '安全交易',
    description: '实名认证，保障交易安全',
  },
  {
    id: 2,
    icon: '⚡',
    title: '快速发布',
    description: '一键发布，轻松出售闲置物品',
  },
  {
    id: 3,
    icon: '💬',
    title: '在线沟通',
    description: '实时消息，轻松讨价还价',
  },
  {
    id: 4,
    icon: '⭐',
    title: '信用评分',
    description: '透明评价体系，诚信交易',
  },
]

const loadLatestProducts = async () => {
  loading.value = true
  try {
    const response = await productApi.getOnSaleProducts(0, 8)
    latestProducts.value = response.data.content || []
  } catch (error) {
    console.error('Failed to load products:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadLatestProducts()
})
</script>

<style scoped lang="scss">
.home-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.welcome-banner {
  margin-bottom: 40px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 60px 40px;
  border-radius: 8px;
  text-align: center;

  h2 {
    margin-bottom: 10px;
    font-size: 32px;
    font-weight: 600;
  }

  p {
    margin-bottom: 30px;
    font-size: 16px;
    opacity: 0.9;
  }
}

.banner-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.features {
  margin-bottom: 40px;

  .feature-item {
    margin-bottom: 20px;

    :deep(.el-card) {
      cursor: pointer;
      transition: transform 0.3s, box-shadow 0.3s;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }
    }

    .feature-content {
      text-align: center;

      .feature-icon {
        font-size: 40px;
        margin-bottom: 15px;
      }

      h3 {
        margin: 15px 0;
        font-size: 16px;
      }

      p {
        margin: 0;
        font-size: 14px;
        color: #666;
      }
    }
  }
}

.latest-products {
  h2 {
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: 600;
  }

  .product-card {
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    }

    .product-image {
      width: 100%;
      height: 150px;
      overflow: hidden;
      border-radius: 4px;
      margin-bottom: 10px;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }

    .product-info {
      h3 {
        margin: 10px 0;
        font-size: 14px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .price {
        margin: 5px 0;
        font-size: 18px;
        font-weight: 600;
        color: #e74c3c;
      }

      .seller {
        margin: 5px 0;
        font-size: 12px;
        color: #999;
      }
    }
  }
}
</style>
