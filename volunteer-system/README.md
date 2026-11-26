# 志愿者管理系统

## 项目简介

这是一个基于 Spring Boot + Thymeleaf + MySQL 开发的志愿者管理系统，实现了用户管理、志愿者管理、志愿者招募等核心功能。

## 技术栈

- **后端框架**: Spring Boot 3.x
- **前端模板**: Thymeleaf + Bootstrap 5
- **数据库**: MySQL 8.0+
- **构建工具**: Maven
- **Java版本**: JDK 17+

## 功能模块

### 1. 用户管理
- 用户登录/注销
- 管理员创建用户账号、分配角色权限
- 重置密码
- 查看、编辑、删除用户信息

### 2. 志愿者管理
- 志愿者注册（包括特长和服务意向）
- 信息审核（管理员审核志愿者信息真实性）
- 信息维护（修改个人信息，更新服务记录、培训情况等）

### 3. 志愿者招募
- 招募信息发布
- 招募条件设置
- 报名管理（志愿者查看招募信息并报名，管理员审核报名）

## 数据库配置

### 数据库信息
- 数据库名: `volunteer_mgmt`
- 用户名: `root`
- 密码: `root`
- 端口: `3306`

### 数据库初始化

系统启动时会自动创建表结构（通过 JPA 的 `ddl-auto=update`），也可以手动执行 `database/init.sql` 脚本初始化数据库。

## 快速开始

### 1. 环境要求
- JDK 17 或更高版本
- Maven 3.6+
- MySQL 8.0+
- IDE（推荐 IntelliJ IDEA 或 Eclipse）

### 2. 数据库准备

确保 MySQL 已启动，并创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS volunteer_mgmt DEFAULT CHARACTER SET utf8mb4;
```

### 3. 配置数据库连接

编辑 `src/main/resources/application.properties`，确认数据库配置正确：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/volunteer_mgmt?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
```

### 4. 运行项目

#### 方式一：使用 Maven 命令
```bash
mvn spring-boot:run
```

#### 方式二：使用 IDE
1. 导入项目到 IDE
2. 找到 `VolunteerManagementApplication.java`
3. 右键运行 `main` 方法

### 5. 访问系统

启动成功后，访问：http://localhost:8080/login

## 默认账号

系统启动时会自动创建默认管理员账号：

- **用户名**: `admin`
- **密码**: `admin123`

## 系统角色

- **ADMIN**: 系统管理员，拥有所有权限
- **VOLUNTEER**: 志愿者，可以注册、查看招募、报名等

## 主要页面说明

### 登录页面
- 路径: `/login`
- 功能: 用户登录

### 注册页面
- 路径: `/register`
- 功能: 志愿者注册

### 仪表盘
- 路径: `/dashboard`
- 功能: 根据角色显示不同的功能入口

### 志愿者功能
- `/volunteer/profile` - 个人资料管理
- `/volunteer/recruitments` - 查看招募信息并报名

### 管理员功能
- `/admin/users` - 用户管理
- `/admin/volunteers` - 志愿者审核
- `/admin/recruitments` - 招募信息管理
- `/admin/recruitments/{id}/applications` - 报名审核

## 数据库表结构

### 核心表
- `role` - 角色表
- `user` - 用户表
- `volunteer` - 志愿者信息表
- `volunteer_skill` - 志愿者特长表
- `volunteer_preference` - 志愿者服务意向表
- `service_record` - 服务记录表
- `training_record` - 培训记录表
- `recruitment` - 招募信息表
- `recruitment_application` - 报名记录表

详细表结构请参考 `database/init.sql` 文件。

## 项目结构

```
src/
├── main/
│   ├── java/com/example/volunteer/
│   │   ├── config/          # 配置类
│   │   ├── controller/      # 控制器
│   │   ├── model/           # 实体类
│   │   ├── repository/      # 数据访问层
│   │   ├── service/         # 业务逻辑层
│   │   └── VolunteerManagementApplication.java
│   └── resources/
│       ├── templates/       # Thymeleaf 模板
│       └── application.properties
└── test/                    # 测试代码
```

## 开发说明

### 添加新功能
1. 在 `model` 包中创建实体类
2. 在 `repository` 包中创建 Repository 接口
3. 在 `service` 包中实现业务逻辑
4. 在 `controller` 包中创建控制器
5. 在 `templates` 中创建对应的 HTML 模板

### 代码规范
- 遵循 Java 命名规范
- 使用 Spring Boot 最佳实践
- 保持代码简洁易读

## 测试建议

### 功能测试
1. **用户登录测试**
   - 使用默认管理员账号登录
   - 注册新志愿者账号并登录

2. **用户管理测试**
   - 创建新用户
   - 编辑用户信息
   - 重置密码
   - 删除用户

3. **志愿者管理测试**
   - 志愿者注册
   - 管理员审核志愿者
   - 志愿者修改个人信息

4. **招募管理测试**
   - 发布招募信息
   - 志愿者报名
   - 管理员审核报名

## 常见问题

### 1. 数据库连接失败
- 检查 MySQL 是否启动
- 确认数据库用户名密码正确
- 确认数据库已创建

### 2. 端口被占用
- 修改 `application.properties` 中的 `server.port` 配置

### 3. 编译错误
- 确认 JDK 版本为 17+
- 执行 `mvn clean install` 重新编译

## 许可证

本项目仅用于课程设计学习使用。

## 作者

志愿者管理系统开发团队

## 更新日志

### v1.0.0 (2026-01-13)
- 初始版本发布
- 实现用户管理、志愿者管理、招募管理等核心功能


