# Java / Spring 课程设计合集

9 个 Java Web 课程设计项目，均为「Spring Boot 后端 + Vue 前端」的前后端分离结构。

## 项目列表

| 目录 | 项目 | 结构 |
|---|---|---|
| `student-dormitory-system` | 学生宿舍管理系统 | `backend`（Spring Boot）+ `frontend`（Vue + Vite） |
| `facility-repair-system` | 城市公共设施智能报修与状态监控平台 | `facility-repair-backend` + `facility-repair-frontend` |
| `graduation-design-system` | 毕业设计管理系统 | `backend` + `frontend` |
| `lab-teaching-system` | 实验教学管理系统 | `backend` + `frontend` |
| `secondhand-trade-system` | 大学生二手物品交易管理系统 | `backend` + `frontend` |
| `innovation-hub` | 创新创业项目申报管理系统 | Gradle 多模块（`build.gradle`） |
| `agri-trade-system` | 农副产品交易系统 | Gradle + Maven 混合 |
| `book-manager` | 图书销售管理系统 | Maven 单体 |
| `volunteer-system` | 志愿者管理系统 | Maven |

## 运行

```bash
# 后端（Maven）
cd <项目>/backend && mvn spring-boot:run

# 后端（Gradle）
cd <项目> && ./gradlew bootRun

# 前端
cd <项目>/frontend && npm install && npm run dev
```

数据库脚本一般在各项目的 `src/main/resources` 或 `sql/` 目录下；连接信息需自行配置。

> 已剔除 `target/`、`node_modules/`、`.idea/` 等构建与 IDE 产物。
