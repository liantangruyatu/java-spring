# 后端（Spring Boot）启动说明

1. 准备 MySQL：运行 `db/schema.sql` 和 `db/test_data.sql`，或让应用自动建表（application.properties 中 `spring.jpa.hibernate.ddl-auto=update`）。

2. 修改数据库配置（若非本地）：编辑 `src/main/resources/application.properties`（用户名/密码默认为 `root`）。

3. 构建并运行：

```bash
cd backend
mvn spring-boot:run
```

默认服务启动后可访问示例接口：`http://localhost:8080/api/buildings`
