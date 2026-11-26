<template>
  <div class="products-container">
    <div class="search-bar">
      <el-input
        v-model="searchText"
        placeholder="搜索物品名称"
        @keyup.enter="handleSearch"
        style="max-width: 300px; margin-right: 10px;"
      />
      <el-button @click="handleSearch" type="primary">搜索</el-button>
      <el-select
        v-model="selectedCategory"
        placeholder="选择分类"
        @change="handleCategoryChange"
        style="max-width: 200px; margin-left: 10px;"
      >
        <el-option label="全部分类" value="0" />
        <el-option v-for="cat in categories" :key="cat.categoryId" :label="cat.categoryName" :value="cat.categoryId" />
      </el-select>
    </div>

    <el-skeleton :loading="loading" :rows="3" animated>
      <el-empty v-if="products.length === 0" description="暂无物品" />
      
      <el-row :gutter="20" v-else>
        <el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="product in products" :key="product.productId">
          <el-card class="product-card" @click="$router.push(`/product/${product.productId}`)">
            <div class="product-image">
              <img :src="product.imageUrl || 'https://via.placeholder.com/200'" :alt="product.productName" />
              <el-tag class="status-tag">{{ product.productStatus }}</el-tag>
            </div>
            <div class="product-info">
              <h3>{{ product.productName }}</h3>
              <p class="price">¥{{ product.price }}</p>
              <p class="category">{{ product.categoryName }}</p>
              <p class="seller">卖家: {{ product.sellerName }}</p>
              <div class="tags">
                <el-tag size="small">{{ product.condition }}</el-tag>
                <el-tag size="small" type="info">👁️ {{ product.viewCount }}</el-tag>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-skeleton>

    <el-pagination
      v-if="products.length > 0"
      @current-change="handlePageChange"
      :current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="prev, pager, next"
      class="pagination"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { productApi, categoryApi } from '../api/request'

const loading = ref(false)
const products = ref([])
const categories = ref([])
const searchText = ref('')
const selectedCategory = ref('0')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

const loadCategories = async () => {
  try {
    const response = await categoryApi.getAllCategories()
    categories.value = response.data || []
  } catch (error) {
    console.error('Failed to load categories:', error)
  }
}

const loadProducts = async (page = 0) => {
  loading.value = true
  try {
    let response
    if (searchText.value) {
      response = await productApi.searchProducts(searchText.value, page - 1, pageSize.value)
    } else if (selectedCategory.value && selectedCategory.value !== '0') {
      response = await productApi.getProductsByCategory(selectedCategory.value, page - 1, pageSize.value)
    } else {
      response = await productApi.getOnSaleProducts(page - 1, pageSize.value)
    }
    
    products.value = response.data.content || []
    total.value = response.data.totalElements || 0
  } catch (error) {
    console.error('Failed to load products:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
  selectedCategory.value = '0'
  loadProducts(1)
}

const handleCategoryChange = () => {
  currentPage.value = 1
  searchText.value = ''
  loadProducts(1)
}

const handlePageChange = (page) => {
  currentPage.value = page
  loadProducts(page)
}

onMounted(() => {
  loadCategories()
  loadProducts()
})
</script>

<style scoped lang="scss">
.products-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.product-card {
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  margin-bottom: 20px;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  }

  .product-image {
    position: relative;
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

    .status-tag {
      position: absolute;
      top: 5px;
      right: 5px;
    }
  }

  .product-info {
    h3 {
      margin: 10px 0;
      font-size: 14px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      height: 20px;
    }

    .price {
      margin: 5px 0;
      font-size: 18px;
      font-weight: 600;
      color: #e74c3c;
    }

    .category {
      margin: 5px 0;
      font-size: 12px;
      color: #666;
    }

    .seller {
      margin: 5px 0;
      font-size: 12px;
      color: #999;
    }

    .tags {
      display: flex;
      gap: 5px;
      margin-top: 8px;
    }
  }
}

.pagination {
  text-align: center;
  margin-top: 30px;
}
</style>
