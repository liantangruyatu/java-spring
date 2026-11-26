# 数据字典（摘要）

- `users` 表
  - `id` INT PK 自增
  - `username` VARCHAR(50) 唯一，登录名
  - `password` VARCHAR(255) 哈希密码
  - `fullname` VARCHAR(100)
  - `role` ENUM('ADMIN','TEACHER','STUDENT')
  - `email` VARCHAR(100)

- `roles` 表（可选）
  - `id` INT PK
  - `name` VARCHAR(50)
  - `description` TEXT

- `courses` 表
  - `id` INT PK
  - `code` VARCHAR(20)
  - `name` VARCHAR(200)
  - `term` VARCHAR(20)
  - `teacher_id` INT FK -> users(id)

- `experiments` 表
  - `id` INT PK
  - `course_id` INT FK -> courses(id)
  - `title` VARCHAR(200)
  - `description` TEXT
  - `duration_minutes` INT

- `labs` 表
  - `id` INT PK
  - `name` VARCHAR(100)
  - `location` VARCHAR(200)
  - `capacity` INT
  - `status` ENUM('AVAILABLE','IN_USE','MAINTENANCE')

- `reservations` 表
  - `id` INT PK
  - `user_id` INT FK -> users(id)
  - `experiment_id` INT FK -> experiments(id)
  - `lab_id` INT FK -> labs(id)
  - `start_time` DATETIME
  - `end_time` DATETIME
  - `status` ENUM('PENDING','APPROVED','REJECTED','CANCELLED')

- `reports` 表
  - `id` INT PK
  - `user_id` INT FK -> users(id)
  - `experiment_id` INT FK -> experiments(id)
  - `file_path` VARCHAR(500)
  - `submitted_at` DATETIME
  - `status` ENUM('SUBMITTED','GRADED')

- `grades` 表
  - `id` INT PK
  - `report_id` INT FK -> reports(id)
  - `grader_id` INT FK -> users(id)
  - `score` DECIMAL(5,2)
  - `comment` TEXT

备注：字段类型与长度可根据需要调整。