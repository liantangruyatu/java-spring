<template>
  <div style="padding:16px">
    <h2>仪表盘</h2>
    <p>欢迎使用学生宿舍管理系统。本系统提供以下功能：</p>
    <div style="display:grid;grid-template-columns:repeat(auto-fit, minmax(200px, 1fr));gap:16px;margin-top:16px">
      <div style="border:1px solid #ddd;padding:16px;border-radius:4px;text-align:center">
        <div style="font-size:24px;margin-bottom:8px">🏠</div>
        <div style="font-weight:bold">宿舍管理</div>
        <div style="color:#999;font-size:12px;margin-top:8px">管理宿舍信息、楼栋分类</div>
      </div>
      <div style="border:1px solid #ddd;padding:16px;border-radius:4px;text-align:center">
        <div style="font-size:24px;margin-bottom:8px">👤</div>
        <div style="font-weight:bold">学生管理</div>
        <div style="color:#999;font-size:12px;margin-top:8px">维护学生基本信息</div>
      </div>
      <div style="border:1px solid #ddd;padding:16px;border-radius:4px;text-align:center">
        <div style="font-size:24px;margin-bottom:8px">📝</div>
        <div style="font-weight:bold">入住管理</div>
        <div style="color:#999;font-size:12px;margin-top:8px">办理入住、调宿、退宿</div>
      </div>
      <div style="border:1px solid #ddd;padding:16px;border-radius:4px;text-align:center">
        <div style="font-size:24px;margin-bottom:8px">📈</div>
        <div style="font-weight:bold">统计查询</div>
        <div style="color:#999;font-size:12px;margin-top:8px">查看空闲床位、班级分布</div>
      </div>
      <div style="border:1px solid #ddd;padding:16px;border-radius:4px;text-align:center">
        <div style="font-size:24px;margin-bottom:8px">⚠️</div>
        <div style="font-weight:bold">缴费/违纪提示</div>
        <div style="color:#999;font-size:12px;margin-top:8px">催缴未缴费、处理违纪</div>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>
