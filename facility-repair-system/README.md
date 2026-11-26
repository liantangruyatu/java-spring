# 快速开始指南

## 项目概述

城市公共设施智能报修与状态监控平台是一个完整的Java课设项目，包括后端Spring Boot服务和前端Vue应用。

## 项目结构

```
源码/
├── facility-repair-backend/        # Spring Boot后端项目
│   ├── src/main/java/              # Java源代码
│   ├── src/main/resources/         # 配置文件
│   └── pom.xml                     # Maven配置
│
├── facility-repair-frontend/       # Vue 3前端项目
│   ├── src/                        # Vue源代码
│   ├── public/                     # 静态资源
│   ├── package.json                # NPM配置
│   └── vite.config.js              # Vite配置
│
└── docs/                           # 项目文档
    ├── init.sql                    # 数据库初始化脚本
    ├── 数据库设计文档.md            # ER图、数据字典
    └── 系统设计文档.md             # 完整的需求和设计文档
```

## 快速启动步骤

### 1. 环境准备

**检查环境**
```bash
# 检查JDK
java -version   # 需要 JDK 8 或更高版本

# 检查MySQL
mysql --version # 需要 MySQL 8.0 或更高版本

# 检查Node.js
node --version  # 需要 Node.js 14 或更高版本
npm --version
```

### 2. 数据库初始化

```bash
# 使用MySQL命令行导入初始化脚本
mysql -u root -p < docs/init.sql

# 或者在MySQL客户端中手动执行
# 登录MySQL
mysql -u root -p

# 在MySQL终端中执行
source docs/init.sql;

# 验证数据库是否创建成功
use facility_repair_db;
show tables;
```

### 3. 后端启动

**方法一：使用IDE（推荐）**
1. 在IDE中打开 `facility-repair-backend` 目录作为Maven项目
2. 等待依赖下载完成
3. 打开 `src/main/java/com/city/facility/FacilityRepairApplication.java`
4. 右键运行 `FacilityRepairApplication` 的main方法

**方法二：使用Maven命令**
```bash
cd facility-repair-backend

# 构建项目
mvn clean package

# 运行
java -jar target/facility-repair-system-1.0.0.jar
```

**验证后端启动成功**
- 访问 http://localhost:8080/api/user/test
- 应该返回 `{"code":200,"message":"success","data":"后端服务正常运行"}`

### 4. 前端启动

```bash
cd facility-repair-frontend

# 安装依赖
npm install
# 或使用yarn
yarn install

# 启动开发服务器
npm run dev
# 或
yarn dev

# 浏览器自动打开 http://localhost:5173
```

## 使用说明

### 登录测试

打开前端应用后，使用以下测试账号登录：

#### 1. 管理员账号
- **手机号：** 18600000001
- **密码：** root
- **权限：** 查看系统统计、管理设施、处理预警

#### 2. 维修工账号
- **手机号：** 18600000002
- **密码：** root
- **权限：** 接收报修任务、处理报修单、提交维修反馈

#### 3. 普通市民账号
- **手机号：** 18600000010
- **密码：** root
- **权限：** 提交报修单、查看报修进度

### 主要功能演示

#### 1. 仪表板 (Dashboard)
- 查看总设施数、故障设施数、维修中数量
- 查看各类型设施的分布
- 查看最近的报修单列表

#### 2. 设施管理 (Facilities)
- 浏览所有设施
- 按类型、状态筛选设施
- 创建新的设施记录
- 标记设施状态（正常/损坏等）

#### 3. 报修单 (Repair Orders)
- 查看所有报修单列表
- 按状态筛选报修单
- 查看报修单详情
- 完成报修单（维修工功能）

#### 4. 新建报修 (Create Repair Order)
- 选择要报修的设施
- 详细描述问题
- 上传问题图片
- 提交报修申请

#### 5. 统计分析 (Statistics)
- 查看各类型设施的故障率
- 生成日统计数据
- 查看设施分布图表

## 核心功能说明

### 自动分配机制
当市民提交报修单时，系统会自动将其分配给当前待办任务最少的维修工。这确保了负载均衡和高效的任务处理。

### 超时预警
系统每小时检查一次所有"维修中"的报修单。如果维修时间超过48小时，系统会自动创建一个高级别预警，通知管理员处理。

### 统计分析
系统自动统计各类型设施的：
- 故障率：故障设施数 / 总设施数
- 平均维修耗时：所有已完成报修的平均用时
- 设施分布：各类型设施的数量对比

## API端点概览

### 用户API
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/{id}` - 获取用户信息

### 设施API
- `GET /api/facility/list` - 查询设施列表
- `POST /api/facility/create` - 创建设施
- `GET /api/facility/stats/summary` - 获取统计摘要

### 报修单API
- `POST /api/repair-order/create` - 创建报修单
- `GET /api/repair-order/list` - 查询报修单列表
- `POST /api/repair-order/process` - 处理报修单

### 统计API
- `GET /api/statistics/latest` - 获取最新统计数据
- `POST /api/statistics/generate` - 生成统计数据

## 常见问题

### Q: 后端无法连接到数据库？
**A:** 检查以下几点：
1. MySQL服务是否运行中
2. `application.yml` 中的数据库连接信息是否正确
3. 数据库名是否为 `facility_repair_db`
4. 用户名/密码是否为 `root/root`

### Q: 前端无法调用后端API？
**A:** 
1. 确保后端已启动在 8080 端口
2. 检查浏览器控制台是否有CORS错误
3. 确保 `vite.config.js` 中的代理配置正确

### Q: 如何修改数据库密码？
**A:**
1. 在MySQL中修改root用户密码
2. 更新 `application.yml` 中的密码

### Q: 如何在Windows环境下运行？
**A:** Windows和Linux命令基本相同，主要差异：
1. 路径分隔符用 `\` 替换 `/`
2. 数据库脚本导入：`mysql -u root -p < docs\init.sql`

## 项目文档

- **系统设计文档** - `docs/系统设计文档.md`
  - 项目背景、目标、功能需求
  - 系统架构、技术栈
  - 数据库设计、API设计
  - 核心业务流程

- **数据库设计文档** - `docs/数据库设计文档.md`
  - ER图说明
  - 表结构设计
  - 数据字典
  - 关键业务规则

- **初始化脚本** - `docs/init.sql`
  - 数据库DDL脚本
  - 测试数据
  - 视图定义

## 扩展开发

### 添加新的设施类型
1. 在 `PublicFacility.java` 中的 `FacilityType` 枚举中添加新类型
2. 更新数据库表的enum定义
3. 在前端选择框中添加新选项

### 添加新的预警类型
1. 在 `AlarmService` 中实现新的预警检查逻辑
2. 在 `AlarmServiceImpl` 中注册新的检查方法
3. 在前端展示相应的预警信息

### 集成短信通知
1. 选择短信服务商（如阿里云、腾讯云）
2. 在 `WorkerService` 中的分配方法中添加短信通知
3. 在 `AlarmService` 中的预警方法中添加短信通知

## 性能优化建议

1. **数据库优化**
   - 为常用查询字段添加索引（已在初始化脚本中完成）
   - 定期清理历史数据

2. **缓存优化**
   - 使用Redis缓存热点数据
   - 缓存设施和维修工列表

3. **分页优化**
   - 所有列表API都支持分页
   - 前端使用分页查询而非全量加载

4. **前端优化**
   - 启用代码分割和懒加载
   - 使用虚拟滚动处理大列表

## 后续维护

- 定期更新依赖库版本
- 监控系统日志和错误
- 备份数据库
- 定期进行性能压测

## 技术支持

遇到问题时，可以查看以下资源：
- Spring Boot官方文档: https://spring.io/projects/spring-boot
- Vue 3官方文档: https://vuejs.org/
- Element Plus文档: https://element-plus.org/
- MySQL文档: https://dev.mysql.com/doc/

---

祝你使用愉快！有任何问题欢迎反馈。
