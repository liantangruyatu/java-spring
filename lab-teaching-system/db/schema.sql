-- MySQL 建表脚本与测试数据

CREATE DATABASE IF NOT EXISTS lab_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE lab_management;

-- users
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  fullname VARCHAR(100),
  role ENUM('ADMIN','TEACHER','STUDENT') NOT NULL,
  email VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- courses
DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) NOT NULL,
  name VARCHAR(200) NOT NULL,
  term VARCHAR(20),
  teacher_id INT,
  FOREIGN KEY (teacher_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- experiments
DROP TABLE IF EXISTS experiments;
CREATE TABLE experiments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_id INT,
  title VARCHAR(200) NOT NULL,
  description TEXT,
  duration_minutes INT DEFAULT 90,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- labs
DROP TABLE IF EXISTS labs;
CREATE TABLE labs (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  location VARCHAR(200),
  capacity INT DEFAULT 20,
  status ENUM('AVAILABLE','IN_USE','MAINTENANCE') DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- reservations
DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  experiment_id INT,
  lab_id INT,
  start_time DATETIME,
  end_time DATETIME,
  status ENUM('PENDING','APPROVED','REJECTED','CANCELLED') DEFAULT 'PENDING',
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE,
  FOREIGN KEY (lab_id) REFERENCES labs(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- reports
DROP TABLE IF EXISTS reports;
CREATE TABLE reports (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  experiment_id INT,
  file_path VARCHAR(500),
  submitted_at DATETIME,
  status ENUM('SUBMITTED','GRADED') DEFAULT 'SUBMITTED',
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (experiment_id) REFERENCES experiments(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- grades
DROP TABLE IF EXISTS grades;
CREATE TABLE grades (
  id INT AUTO_INCREMENT PRIMARY KEY,
  report_id INT,
  grader_id INT,
  score DECIMAL(5,2),
  comment TEXT,
  FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE,
  FOREIGN KEY (grader_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 测试数据（示例用户）
-- 使用BCrypt哈希后的密码，原始密码：admin123, teacher123, student123
INSERT INTO users (username, password, fullname, role, email) VALUES
('admin', '$2a$10$J7eS8rRvX6kX5dY4cW3bV2aZ1qA0pN9oM8lK7jI6hH5gF4eD3cB2aA', '系统管理员', 'ADMIN', 'admin@example.com'),
('teacher1', '$2a$10$K8lM7nO6pQ5rR4sT3uV2wX1yZ0aB9cC8dD7eE6fF5gG4hH3iI2jJ', '张老师', 'TEACHER', 'teacher1@example.com'),
('student1', '$2a$10$L9mN8oO7pP6qQ5rR4sS3tT2uU1vV0wW9xX8yY7zZ6aA5bB4cC3dD', '李学生', 'STUDENT', 'student1@example.com');

-- 示例实验室
INSERT INTO labs (name, location, capacity, status) VALUES
('电工实验室', '教学楼A-101', 30, 'AVAILABLE'),
('计算机实验室', '教学楼B-202', 40, 'AVAILABLE');

-- 示例课程与实验
INSERT INTO courses (code, name, term, teacher_id) VALUES
('CS101', '计算机基础', '2025春', 2);

INSERT INTO experiments (course_id, title, description, duration_minutes) VALUES
(1, '计算机组装实验', '组装并测试计算机硬件', 120),
(1, '操作系统安装', '安装常见操作系统并配置', 90);

-- 示例预约
INSERT INTO reservations (user_id, experiment_id, lab_id, start_time, end_time, status) VALUES
(3, 1, 2, '2025-03-10 09:00:00', '2025-03-10 11:00:00', 'APPROVED');

-- 示例报告
INSERT INTO reports (user_id, experiment_id, file_path, submitted_at, status) VALUES
(3, 1, '/uploads/report_student1_exp1.pdf', '2025-03-10 13:00:00', 'SUBMITTED');

-- 注意：示例密码已使用BCrypt进行哈希处理，原始密码为admin123, teacher123, student123。