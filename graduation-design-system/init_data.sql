-- 初始化数据库数据脚本

-- 1. 插入用户数据
INSERT INTO users (username, password, email, role, status, create_time, update_time) VALUES
('admin', '123456', 'admin@example.com', 0, 1, NOW(), NOW()),
('teacher1', '123456', 'teacher1@example.com', 1, 1, NOW(), NOW()),
('teacher2', '123456', 'teacher2@example.com', 1, 1, NOW(), NOW()),
('student1', '123456', 'student1@example.com', 2, 1, NOW(), NOW()),
('student2', '123456', 'student2@example.com', 2, 1, NOW(), NOW()),
('student3', '123456', 'student3@example.com', 2, 1, NOW(), NOW()),
('student4', '123456', 'student4@example.com', 2, 1, NOW(), NOW()),
('student5', '123456', 'student5@example.com', 2, 1, NOW(), NOW());

-- 2. 插入教师数据
INSERT INTO teachers (user_id, real_name, phone, department, research_area, max_students, current_count, create_time, update_time) VALUES
(2, '张教授', '13800138001', '计算机科学与技术', '人工智能', 5, 0, NOW(), NOW()),
(3, '李副教授', '13800138002', '软件工程', '软件开发', 5, 0, NOW(), NOW());

-- 3. 插入学生数据
INSERT INTO students (user_id, real_name, student_no, major, class_name, phone, create_time, update_time) VALUES
(4, '学生1', '2022001', '计算机科学与技术', '计科1班', '13900139001', NOW(), NOW()),
(5, '学生2', '2022002', '计算机科学与技术', '计科1班', '13900139002', NOW(), NOW()),
(6, '学生3', '2022003', '软件工程', '软工1班', '13900139003', NOW(), NOW()),
(7, '学生4', '2022004', '软件工程', '软工1班', '13900139004', NOW(), NOW()),
(8, '学生5', '2022005', '软件工程', '软工1班', '13900139005', NOW(), NOW());

-- 4. 插入课题数据
INSERT INTO topics (teacher_id, title, description, type, difficulty, required_skills, max_students, selected_count, status, create_time, update_time) VALUES
(1, '基于深度学习的图像识别系统', '使用卷积神经网络实现图像分类和识别功能', 1, 3, 'Python, TensorFlow, OpenCV', 2, 0, 1, NOW(), NOW()),
(1, '智能推荐系统设计与实现', '基于协同过滤算法开发个性化推荐系统', 1, 2, 'Java, Spring Boot, MySQL', 2, 0, 1, NOW(), NOW()),
(1, '自然语言处理在文本分类中的应用', '研究文本分类算法并实现新闻分类系统', 1, 3, 'Python, NLTK, scikit-learn', 1, 0, 1, NOW(), NOW()),
(2, 'Web前端框架性能比较研究', '对比Vue、React、Angular等前端框架的性能差异', 2, 2, 'JavaScript, Vue, React, Angular', 3, 0, 1, NOW(), NOW()),
(2, '移动应用开发实践', '开发一款基于Flutter的移动应用', 1, 2, 'Dart, Flutter, Firebase', 2, 0, 1, NOW(), NOW()),
(2, '数据库优化技术研究', '研究数据库索引、查询优化等技术并进行实践', 2, 3, 'MySQL, SQL, 数据库原理', 2, 0, 1, NOW(), NOW());

-- 5. 插入选题数据
INSERT INTO selections (student_id, topic_id, status, create_time, update_time) VALUES
(1, 1, 1, NOW(), NOW()),
(2, 2, 1, NOW(), NOW()),
(3, 4, 1, NOW(), NOW());

-- 6. 插入进度数据
INSERT INTO progress (selection_id, stage, content, file_path, status, create_time, update_time) VALUES
(1, '开题报告', '完成开题报告撰写', '/upload/progress/1/开题报告.docx', 2, NOW(), NOW()),
(1, '中期检查', '完成中期检查PPT', '/upload/progress/1/中期检查.ppt', 1, NOW(), NOW()),
(2, '开题报告', '完成开题报告撰写', '/upload/progress/2/开题报告.docx', 2, NOW(), NOW());
