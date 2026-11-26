-- 毕业设计管理系统 - MySQL数据库脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS graduation_design_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE graduation_design_system;

-- 1. 用户表
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    role INT NOT NULL COMMENT '角色(0:系统管理员,1:教师,2:学生)',
    status INT NOT NULL DEFAULT 1 COMMENT '状态(0:禁用,1:启用)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 教师表
CREATE TABLE teachers (
    teacher_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '教师ID',
    user_id INT NOT NULL UNIQUE COMMENT '用户ID',
    real_name VARCHAR(50) NOT NULL COMMENT '教师姓名',
    phone VARCHAR(20) COMMENT '电话',
    department VARCHAR(100) COMMENT '部门',
    research_area VARCHAR(255) COMMENT '研究方向',
    max_students INT NOT NULL DEFAULT 10 COMMENT '最多指导学生数',
    current_count INT NOT NULL DEFAULT 0 COMMENT '当前指导学生数',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 3. 学生表
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '学生ID',
    user_id INT NOT NULL UNIQUE COMMENT '用户ID',
    real_name VARCHAR(50) NOT NULL COMMENT '学生姓名',
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    major VARCHAR(100) COMMENT '专业',
    class_name VARCHAR(50) COMMENT '班级',
    phone VARCHAR(20) COMMENT '电话',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_student_no (student_no),
    INDEX idx_major (major)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 4. 毕设题目表
CREATE TABLE topics (
    topic_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '题目ID',
    teacher_id INT NOT NULL COMMENT '教师ID',
    title VARCHAR(200) NOT NULL COMMENT '题目标题',
    description LONGTEXT NOT NULL COMMENT '题目描述',
    type INT NOT NULL COMMENT '题目类型(0:工程应用,1:科研创新,2:综合实践)',
    difficulty INT NOT NULL COMMENT '难度等级(1:简单,2:中等,3:困难)',
    required_skills VARCHAR(255) COMMENT '所需技能',
    max_students INT NOT NULL DEFAULT 1 COMMENT '最多学生数',
    selected_count INT NOT NULL DEFAULT 0 COMMENT '已选人数',
    status INT NOT NULL DEFAULT 0 COMMENT '状态(0:草稿,1:发布中,2:已结束)',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
    INDEX idx_teacher_id (teacher_id),
    INDEX idx_status (status),
    INDEX idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='毕设题目表';

-- 5. 学生选题表
CREATE TABLE selections (
    selection_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '选题ID',
    student_id INT NOT NULL COMMENT '学生ID',
    topic_id INT COMMENT '题目ID',
    selection_type INT NOT NULL COMMENT '选题类型(0:选择已有题目,1:自拟题目)',
    custom_title VARCHAR(200) COMMENT '自拟题目标题',
    custom_description LONGTEXT COMMENT '自拟题目描述',
    teacher_id INT COMMENT '分配的教师ID',
    status INT NOT NULL DEFAULT 0 COMMENT '申请状态(0:待审批,1:已同意,2:已拒绝)',
    apply_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    approval_time DATETIME COMMENT '审批时间',
    approval_comment TEXT COMMENT '审批意见',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (topic_id) REFERENCES topics(topic_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers(teacher_id),
    INDEX idx_student_id (student_id),
    INDEX idx_status (status),
    INDEX idx_teacher_id (teacher_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生选题表';

-- 6. 毕设进度表
CREATE TABLE progress (
    progress_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '进度ID',
    selection_id INT NOT NULL COMMENT '选题ID',
    stage INT NOT NULL COMMENT '阶段(1:任务书,2:中期检查,3:论文完成,4:答辩)',
    status INT NOT NULL DEFAULT 0 COMMENT '状态(0:未开始,1:进行中,2:已完成)',
    submit_file VARCHAR(255) COMMENT '提交文件名',
    submit_time DATETIME COMMENT '提交时间',
    teacher_comment TEXT COMMENT '教师评价',
    score DECIMAL(5,2) COMMENT '成绩',
    update_time DATETIME COMMENT '更新时间',
    FOREIGN KEY (selection_id) REFERENCES selections(selection_id),
    INDEX idx_selection_id (selection_id),
    INDEX idx_stage (stage)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='毕设进度表';

-- 7. 系统日志表
CREATE TABLE system_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id INT NOT NULL COMMENT '用户ID',
    operation VARCHAR(100) NOT NULL COMMENT '操作类型',
    table_name VARCHAR(50) COMMENT '操作的表名',
    record_id INT COMMENT '操作的记录ID',
    old_value LONGTEXT COMMENT '修改前的值',
    new_value LONGTEXT COMMENT '修改后的值',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统日志表';

-- 插入测试数据

-- 1. 系统管理员
INSERT INTO users (username, password, email, role, status) VALUES 
('admin', 'admin123', 'admin@example.com', 0, 1);

-- 2. 教师用户
INSERT INTO users (username, password, email, role, status) VALUES 
('teacher1', 'teacher123', 'teacher1@example.com', 1, 1),
('teacher2', 'teacher123', 'teacher2@example.com', 1, 1),
('teacher3', 'teacher123', 'teacher3@example.com', 1, 1);

-- 3. 学生用户
INSERT INTO users (username, password, email, role, status) VALUES 
('student1', 'student123', 'student1@example.com', 2, 1),
('student2', 'student123', 'student2@example.com', 2, 1),
('student3', 'student123', 'student3@example.com', 2, 1),
('student4', 'student123', 'student4@example.com', 2, 1),
('student5', 'student123', 'student5@example.com', 2, 1);

-- 4. 教师信息
INSERT INTO teachers (user_id, real_name, phone, department, research_area, max_students) VALUES
(2, '张三', '13800138001', '计算机学院', '人工智能、深度学习', 10),
(3, '李四', '13800138002', '计算机学院', '数据库、大数据', 10),
(4, '王五', '13800138003', '计算机学院', '分布式系统、云计算', 10);

-- 5. 学生信息
INSERT INTO students (user_id, real_name, student_no, major, class_name, phone) VALUES
(5, '小明', '20210001', '计算机科学与技术', '2021级1班', '13900139001'),
(6, '小红', '20210002', '计算机科学与技术', '2021级1班', '13900139002'),
(7, '小刚', '20210003', '计算机科学与技术', '2021级2班', '13900139003'),
(8, '小丽', '20210004', '计算机科学与技术', '2021级2班', '13900139004'),
(9, '小王', '20210005', '计算机科学与技术', '2021级3班', '13900139005');

-- 6. 毕设题目
INSERT INTO topics (teacher_id, title, description, type, difficulty, required_skills, max_students, status) VALUES
(1, '基于深度学习的人脸识别系统', '利用卷积神经网络实现人脸检测、识别和验证功能，支持多个人脸库的管理', 1, 3, 'Python,TensorFlow,OpenCV', 2, 1),
(1, '智能推荐系统的设计与实现', '基于协同过滤和内容推荐算法的电商产品推荐系统', 1, 2, 'Python,Spark,机器学习', 2, 1),
(2, '医院管理系统开发', '包含挂号、病历管理、处方管理等功能的医院管理系统', 0, 2, 'Java,MySQL,Vue', 2, 1),
(2, '在线教育平台设计', '支持课程发布、学生学习、作业提交、成绩管理的在线教育平台', 2, 2, 'Java,React,MongoDB', 3, 1),
(3, '分布式数据同步系统', '实现数据中心间的实时数据同步，支持高可用和容错', 1, 3, 'Java,Kafka,Zookeeper', 2, 1);

-- 7. 学生选题
INSERT INTO selections (student_id, topic_id, selection_type, teacher_id, status) VALUES
(1, 1, 0, 1, 1),
(2, 2, 0, 1, 1),
(3, 3, 0, 2, 1),
(4, 4, 0, 2, 1);

-- 8. 自拟题目申请
INSERT INTO selections (student_id, selection_type, custom_title, custom_description, status) VALUES
(5, 1, '基于微服务的社交媒体平台', '设计并实现一个具有发布动态、评论、点赞等功能的社交媒体平台，采用微服务架构', 0);

-- 9. 毕设进度
INSERT INTO progress (selection_id, stage, status) VALUES
(1, 1, 0),
(1, 2, 0),
(1, 3, 0),
(1, 4, 0),
(2, 1, 0),
(2, 2, 0),
(2, 3, 0),
(2, 4, 0),
(3, 1, 0),
(3, 2, 0),
(3, 3, 0),
(3, 4, 0),
(4, 1, 0),
(4, 2, 0),
(4, 3, 0),
(4, 4, 0);

COMMIT;
