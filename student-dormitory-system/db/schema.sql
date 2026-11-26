-- 数据库与表结构（MySQL）
CREATE DATABASE IF NOT EXISTS dorm_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE dorm_management;

CREATE TABLE IF NOT EXISTS building (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  `type` VARCHAR(50),
  address VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS dorm (
  id INT AUTO_INCREMENT PRIMARY KEY,
  building_id INT NOT NULL,
  room_no VARCHAR(50) NOT NULL,
  floor INT,
  room_type VARCHAR(50),
  capacity INT DEFAULT 4,
  note VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_dorm_building FOREIGN KEY (building_id) REFERENCES building(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS student (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_no VARCHAR(30) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  `class` VARCHAR(100),
  phone VARCHAR(50),
  gender VARCHAR(10),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS stay (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  dorm_id INT NOT NULL,
  bed_no INT NOT NULL,
  checkin_date DATE NOT NULL,
  checkout_date DATE DEFAULT NULL,
  active TINYINT(1) DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_stay_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
  CONSTRAINT fk_stay_dorm FOREIGN KEY (dorm_id) REFERENCES dorm(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  due_date DATE,
  paid TINYINT(1) DEFAULT 0,
  paid_date DATE DEFAULT NULL,
  note VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_payment_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS violation (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  description TEXT,
  occ_date DATE,
  processed TINYINT(1) DEFAULT 0,
  handler_note VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_violation_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);

-- 索引示例
CREATE INDEX idx_dorm_building ON dorm(building_id);
CREATE INDEX idx_stay_student ON stay(student_id);
CREATE INDEX idx_payment_student ON payment(student_id);
