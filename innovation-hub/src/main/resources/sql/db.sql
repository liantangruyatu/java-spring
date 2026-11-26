-- 创建数据库
CREATE DATABASE IF NOT EXISTS innovation_project_db;
USE innovation_project_db;

-- 教师表
CREATE TABLE teacher (
                         teacher_id INT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(50) NOT NULL UNIQUE,
                         password VARCHAR(100) NOT NULL,
                         name VARCHAR(50) NOT NULL,
                         college VARCHAR(100) NOT NULL,
                         title VARCHAR(50),
                         phone VARCHAR(20),
                         email VARCHAR(100) UNIQUE
);

-- 学生表
CREATE TABLE student (
                         student_id INT AUTO_INCREMENT PRIMARY KEY,
                         username VARCHAR(50) NOT NULL UNIQUE,
                         password VARCHAR(100) NOT NULL,
                         name VARCHAR(50) NOT NULL,
                         major VARCHAR(100) NOT NULL,
                         class_name VARCHAR(50),
                         phone VARCHAR(20),
                         email VARCHAR(100) UNIQUE
);

-- 项目表
CREATE TABLE project (
                         project_id INT AUTO_INCREMENT PRIMARY KEY,
                         project_name VARCHAR(200) NOT NULL,
                         description TEXT,
                         teacher_id INT,
                         student_id INT,
                         status VARCHAR(20) DEFAULT '待审核',
                         created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                         updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
                         FOREIGN KEY (student_id) REFERENCES student(student_id)
);

-- 题目表
CREATE TABLE topic (
                       topic_id INT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(200) NOT NULL,
                       description TEXT,
                       teacher_id INT,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);

-- 选题记录表
CREATE TABLE selection (
                           selection_id INT AUTO_INCREMENT PRIMARY KEY,
                           student_id INT,
                           topic_id INT,
                           selected_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           status VARCHAR(20) DEFAULT '待审核',
                           FOREIGN KEY (student_id) REFERENCES student(student_id),
                           FOREIGN KEY (topic_id) REFERENCES topic(topic_id)
);


-- 插入教师数据
INSERT INTO teacher (username, password, name, college, title, phone, email) VALUES
                                                             ('teacher001', '123456', '张老师', '软件学院', '教授', '13800138001', 'zhang@nuc.edu.cn'),
                                                             ('teacher002', '123456', '李老师', '计算机学院', '副教授', '13800138002', 'li@nuc.edu.cn');

-- 插入学生数据
INSERT INTO student (username, password, name, major, class_name, phone, email) VALUES
                                                                ('student001', '123456', '张三', '软件工程', '软工2101', '13900139001', 'zhangsan@stu.nuc.edu.cn'),
                                                                ('student002', '123456', '李四', '计算机科学', '计科2102', '13900139002', 'lisi@stu.nuc.edu.cn');

-- 插入题目数据
INSERT INTO topic (title, description, teacher_id) VALUES
                                                       ('基于SpringBoot的在线申报系统', '开发一个创新创业项目在线申报平台', 1),
                                                       ('基于人工智能的校园安全监控系统', '利用AI技术提升校园安全管理效率', 2);

-- 插入项目数据
INSERT INTO project (project_name, description, teacher_id, student_id, status) VALUES
                                                                                    ('创新创业项目管理平台', '一个集项目发布、申报、评审于一体的系统', 1, 1, '已提交'),
                                                                                    ('智能校园安全系统', '利用AI进行异常行为识别与预警', 2, 2, '待审核');

-- 插入选题记录
INSERT INTO selection (student_id, topic_id, status) VALUES
                                                         (1, 1, '已通过'),
                                                         (2, 2, '待审核');