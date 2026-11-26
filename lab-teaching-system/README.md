实验教学管理系统 — 初始交付物

概览
- 后端：Spring Boot（Java + Spring Data JPA + Spring Security）
- 前端：Vue 3（Vite + Vue Router + Axios）
- 数据库：MySQL（账号密码均为 root）

包含文件
- ER 图（Mermaid）：docs/er_diagram.md
- 数据字典：docs/data_dictionary.md
- 数据库建表与测试数据：db/schema.sql
- 后端骨架：backend/
- 前端骨架：frontend/

快速运行说明（开发模式）
1. 准备 MySQL，创建数据库 `lab_management`，MySQL 账号 root/root。
2. 导入 `db/schema.sql`：

```sql
mysql -u root -p lab_management < db/schema.sql
```

3. 后端：打开 `backend/`，用 IDE 导入 Maven 项目，运行 `com.example.labmanagement.Application`。
   - 数据库配置在 `backend/src/main/resources/application.properties`。

4. 前端：打开 `frontend/`，安装依赖并启动：

```bash
cd frontend
npm install
npm run dev
```

后续
- 我可以继续实现详细的后端业务逻辑（预约冲突检测、报告上传、成绩统计）、完善前端界面并集成认证。想要我现在开始实现哪些具体模块？
