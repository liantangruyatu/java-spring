<template>
  <div class="product-detail-container">
    <el-skeleton :loading="loading" :rows="5" animated>
      <el-row :gutter="20" v-if="product">
        <el-col :xs="24" :md="12">
          <div class="image-container">
            <img :src="product.imageUrl || 'https://via.placeholder.com/400'" :alt="product.productName" class="product-image" />
          </div>
        </el-col>
        <el-col :xs="24" :md="12">
          <div class="product-details">
            <h1>{{ product.productName }}</h1>
            
            <div class="price-section">
              <span class="price">¥{{ product.price }}</span>
              <el-tag type="success">{{ product.productStatus }}</el-tag>
              <el-tag>{{ product.condition }}</el-tag>
            </div>

            <div class="info-section">
              <p><strong>卖家:</strong> {{ product.sellerName }}</p>
              <p><strong>分类:</strong> {{ product.categoryName }}</p>
              <p><strong>浏览次数:</strong> {{ product.viewCount }}</p>
              <p><strong>发布时间:</strong> {{ product.publishTime }}</p>
              <p v-if="product.location"><strong>交易地点:</strong> {{ product.location }}</p>
            </div>

            <div class="description-section">
              <h3>商品描述</h3>
              <p>{{ product.description }}</p>
            </div>

            <div class="action-section">
              <el-button type="primary" size="large" @click="handleBuyNow" v-if="product.productStatus === '在售'">购买</el-button>
              <el-button size="large" @click="showContactDialog = true">联系卖家</el-button>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-skeleton>

    <el-dialog v-model="showContactDialog" title="联系卖家">
      <el-form :model="contactForm">
        <el-form-item label="消息类型">
          <el-select v-model="contactForm.messageType" placeholder="请选择消息类型">
            <el-option label="询价" value="询价" />
            <el-option label="讨价还价" value="讨价还价" />
            <el-option label="交易相关" value="交易相关" />
          </el-select>
        </el-form-item>
        <el-form-item label="消息内容">
          <el-input v-model="contactForm.messageContent" type="textarea" rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showContactDialog = false">取消</el-button>
        <el-button type="primary" @click="sendMessage">发送</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { productApi, messageApi, orderApi } from '../api/request'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const product = ref(null)
const showContactDialog = ref(false)

const contactForm = ref({
  messageType: '询价',
  messageContent: '',
})

const loadProduct = async () => {
  loading.value = true
  try {
    const response = await productApi.getProductById(route.params.id)
    product.value = response.data
  } catch (error) {
    ElMessage.error('加载物品信息失败')
    router.push('/products')
  } finally {
    loading.value = false
  }
}

const sendMessage = async () => {
  try {
    if (!userStore.isLoggedIn) {
      ElMessage.warning('请先登录')
      router.push('/login')
      return
    }

    const messageData = {
      senderId: userStore.user.userId,
      receiverId: product.value.sellerId,
      productId: product.value.productId,
      messageType: contactForm.value.messageType,
      messageContent: contactForm.value.messageContent,
    }

    await messageApi.sendMessage(messageData)
    ElMessage.success('消息发送成功，请等待卖家回复')
    showContactDialog.value = false
    contactForm.value = { messageType: '询价', messageContent: '' }
  } catch (error) {
    ElMessage.error('发送消息失败')
  }
}

const handleBuyNow = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  try {
    const orderData = {
      buyerId: userStore.user.userId,
      sellerId: product.value.sellerId,
      productId: product.value.productId,
      quantity: 1,
    }

    const response = await orderApi.createOrder(orderData)
    ElMessage.success('订单创建成功')
    router.push('/my-orders')
  } catch (error) {
    ElMessage.error('创建订单失败')
  }
}

onMounted(() => {
  loadProduct()
})
</script>

<style scoped lang="scss">
.product-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.image-container {
  display: flex;
  justify-content: center;

  .product-image {
    max-width: 100%;
    max-height: 500px;
    border-radius: 8px;
    object-fit: contain;
  }
}

.product-details {
  h1 {
    margin-bottom: 20px;
    font-size: 28px;
  }

  .price-section {
    display: flex;
    align-items: center;
    gap: 15px;
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #ebeef5;

    .price {
      font-size: 32px;
      color: #e74c3c;
      font-weight: 600;
    }
  }

  .info-section {
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #ebeef5;

    p {
      margin: 10px 0;
      line-height: 1.8;
    }
  }

  .description-section {
    margin-bottom: 20px;

    h3 {
      margin-bottom: 10px;
    }

    p {
      line-height: 1.8;
      color: #666;
    }
  }

  .action-section {
    display: flex;
    gap: 10px;
  }
}
</style>
