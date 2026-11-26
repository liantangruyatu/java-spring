<template>
  <div class="messages-container">
    <h2>消息列表</h2>

    <el-skeleton :loading="loading" :rows="3" animated>
      <el-empty v-if="messages.length === 0" description="暂无消息" />

      <el-row :gutter="20" v-else>
        <el-col :xs="24" :md="6">
          <div class="message-list">
            <div
              v-for="msg in messages"
              :key="msg.messageId"
              class="message-item"
              :class="{ active: selectedMessage?.messageId === msg.messageId }"
              @click="selectedMessage = msg"
            >
              <div class="sender-info">
                <strong>{{ msg.senderName }}</strong>
                <span class="unread" v-if="msg.isRead === 0">未读</span>
              </div>
              <p class="preview">{{ msg.messageContent.substring(0, 30) }}...</p>
              <p class="time">{{ msg.sendTime }}</p>
            </div>
          </div>
        </el-col>
        <el-col :xs="24" :md="18">
          <div v-if="selectedMessage" class="message-detail">
            <div class="message-header">
              <h3>{{ selectedMessage.senderName }} - {{ selectedMessage.productName }}</h3>
              <el-button link type="danger" @click="deleteMessage">删除</el-button>
            </div>
            <div class="message-body">
              <div class="message-type">
                <el-tag>{{ selectedMessage.messageType }}</el-tag>
              </div>
              <p>{{ selectedMessage.messageContent }}</p>
              <p class="send-time">{{ selectedMessage.sendTime }}</p>
            </div>
            <div class="reply-section">
              <el-input v-model="replyContent" type="textarea" rows="4" placeholder="请输入回复内容" />
              <el-button type="primary" @click="sendReply" :loading="replying">发送回复</el-button>
            </div>
          </div>
          <div v-else class="empty-placeholder">
            <p>选择一条消息查看详情</p>
          </div>
        </el-col>
      </el-row>
    </el-skeleton>

    <el-pagination
      v-if="messages.length > 0"
      @current-change="loadMessages"
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
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'
import { messageApi } from '../api/request'

const userStore = useUserStore()
const loading = ref(false)
const replying = ref(false)
const messages = ref([])
const selectedMessage = ref(null)
const replyContent = ref('')
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadMessages = async (page = 1) => {
  loading.value = true
  try {
    const response = await messageApi.getReceiverMessages(userStore.user.userId, page - 1, pageSize.value)
    messages.value = response.data.content || []
    total.value = response.data.totalElements || 0
  } catch (error) {
    ElMessage.error('加载消息失败')
  } finally {
    loading.value = false
  }
}

const sendReply = async () => {
  if (!replyContent.value.trim()) {
    ElMessage.warning('请输入回复内容')
    return
  }

  replying.value = true
  try {
    await messageApi.sendMessage({
      senderId: userStore.user.userId,
      receiverId: selectedMessage.value.senderId,
      productId: selectedMessage.value.productId,
      messageType: '回复',
      messageContent: replyContent.value,
    })
    ElMessage.success('回复已发送')
    replyContent.value = ''
    loadMessages(currentPage.value)
  } catch (error) {
    ElMessage.error('发送失败')
  } finally {
    replying.value = false
  }
}

const deleteMessage = async () => {
  try {
    await messageApi.deleteMessage(selectedMessage.value.messageId)
    ElMessage.success('消息已删除')
    selectedMessage.value = null
    loadMessages(currentPage.value)
  } catch (error) {
    ElMessage.error('删除失败')
  }
}

onMounted(() => {
  loadMessages()
})
</script>

<style scoped lang="scss">
.messages-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;

  h2 {
    margin-bottom: 20px;
  }
}

.message-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  max-height: 600px;
  overflow-y: auto;

  .message-item {
    padding: 12px;
    border-bottom: 1px solid #ebeef5;
    cursor: pointer;
    transition: background-color 0.3s;

    &:hover {
      background-color: #f5f7fa;
    }

    &.active {
      background-color: #e6f7ff;
    }

    .sender-info {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 5px;

      strong {
        font-size: 14px;
      }

      .unread {
        background-color: #f56c6c;
        color: white;
        padding: 2px 6px;
        border-radius: 3px;
        font-size: 12px;
      }
    }

    .preview {
      margin: 5px 0;
      font-size: 12px;
      color: #666;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .time {
      margin: 0;
      font-size: 11px;
      color: #999;
    }
  }
}

.message-detail {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 20px;

  .message-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 15px;
    border-bottom: 1px solid #ebeef5;

    h3 {
      margin: 0;
    }
  }

  .message-body {
    padding: 20px 0;

    .message-type {
      margin-bottom: 10px;
    }

    p {
      line-height: 1.8;
      color: #666;

      &.send-time {
        font-size: 12px;
        color: #999;
        margin-top: 10px;
      }
    }
  }

  .reply-section {
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #ebeef5;

    :deep(.el-textarea) {
      margin-bottom: 10px;
    }
  }
}

.empty-placeholder {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  border: 1px solid #ebeef5;
  border-radius: 4px;

  p {
    color: #999;
  }
}

.pagination {
  text-align: center;
  margin-top: 20px;
}
</style>
