-- 志愿者管理系统测试数据
-- 使用前请确保已执行 database/init.sql 创建表结构

USE volunteer_mgmt;

-- 插入测试志愿者用户（密码都是123456）
INSERT INTO user (username, password, role_id, full_name, phone, email, status) VALUES
('zhangsan', '123456', 2, '张三', '13800000001', 'zhangsan@example.com', 1),
('lisi', '123456', 2, '李四', '13800000002', 'lisi@example.com', 1),
('wangwu', '123456', 2, '王五', '13800000003', 'wangwu@example.com', 1),
('zhaoliu', '123456', 2, '赵六', '13800000004', 'zhaoliu@example.com', 1)
ON DUPLICATE KEY UPDATE username=username;

-- 插入志愿者信息
INSERT INTO volunteer (user_id, gender, birthday, id_card, address, audit_status, audit_remark) VALUES
((SELECT id FROM user WHERE username='zhangsan'), 'M', '1995-05-15', '440101199505150011', '广州市天河区', 1, '审核通过'),
((SELECT id FROM user WHERE username='lisi'), 'F', '1998-08-22', '440101199808220022', '广州市越秀区', 1, '审核通过'),
((SELECT id FROM user WHERE username='wangwu'), 'M', '2000-03-10', '440101200003100033', '广州市海珠区', 0, NULL),
((SELECT id FROM user WHERE username='zhaoliu'), 'F', '1996-11-05', '440101199611050044', '广州市白云区', 1, '审核通过')
ON DUPLICATE KEY UPDATE user_id=VALUES(user_id);

-- 插入志愿者特长
INSERT INTO volunteer_skill (volunteer_id, skill_name, description) VALUES
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '急救', '通过红十字急救培训，持有急救证书'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '计算机', '熟悉办公软件，会使用PS、PR等设计软件'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '教育', '师范专业毕业，有教学经验'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '心理咨询', '持有心理咨询师证书'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='wangwu')), '体育', '擅长篮球、足球等运动'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhaoliu')), '医疗护理', '护理专业，有医院实习经验');

-- 插入志愿者服务意向
INSERT INTO volunteer_preference (volunteer_id, service_type, preferred_time, preferred_location) VALUES
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '助老服务', '周末', '市中心'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '环境保护', '节假日', '郊区'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '支教服务', '寒暑假', '山区'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '心理辅导', '工作日晚上', '社区'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='wangwu')), '体育指导', '周末', '学校'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhaoliu')), '医疗协助', '周末', '医院');

-- 插入招募信息
INSERT INTO recruitment (title, description, location, start_time, end_time, requirement, max_volunteers, status, created_by) VALUES
('敬老院周末陪伴活动', '陪同老人聊天、散步，协助简单照料，为老人带去温暖和关怀。', 'XX敬老院', '2026-02-01 09:00:00', '2026-02-01 17:00:00', '身体健康，有耐心，优先有助老经验者', 20, 1, (SELECT id FROM user WHERE username='admin')),
('社区环保宣传活动', '在社区开展环保知识宣传，组织垃圾分类活动，提高居民环保意识。', 'XX社区活动中心', '2026-02-15 14:00:00', '2026-02-15 18:00:00', '热爱环保，有宣传经验优先', 15, 1, (SELECT id FROM user WHERE username='admin')),
('山区支教活动', '前往山区小学进行支教，为孩子们提供教育支持，传递知识的力量。', 'XX山区小学', '2026-03-01 08:00:00', '2026-03-05 18:00:00', '有教学经验，能适应山区环境', 10, 1, (SELECT id FROM user WHERE username='admin')),
('社区义诊活动', '协助医生开展社区义诊，为居民提供健康咨询和基础检查服务。', 'XX社区卫生服务中心', '2026-02-20 09:00:00', '2026-02-20 16:00:00', '有医疗或护理背景优先', 12, 0, (SELECT id FROM user WHERE username='admin'));

-- 插入报名记录
INSERT INTO recruitment_application (recruitment_id, volunteer_id, apply_time, status, remark) VALUES
((SELECT id FROM recruitment WHERE title='敬老院周末陪伴活动'), (SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), NOW(), 1, '已通过'),
((SELECT id FROM recruitment WHERE title='社区环保宣传活动'), (SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), NOW(), 0, '待审核'),
((SELECT id FROM recruitment WHERE title='山区支教活动'), (SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), NOW(), 0, '待审核'),
((SELECT id FROM recruitment WHERE title='社区义诊活动'), (SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhaoliu')), NOW(), 0, '待审核');

-- 插入服务记录
INSERT INTO service_record (volunteer_id, service_date, hours, content, evaluation) VALUES
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '2025-12-10', 4.0, '敬老院陪伴老人', '表现优秀'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '2025-11-20', 3.5, '社区环保宣传', '积极参与'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '2025-12-05', 5.0, '山区支教活动', '认真负责'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhaoliu')), '2025-12-01', 6.0, '社区义诊活动', '专业细致');

-- 插入培训记录
INSERT INTO training_record (volunteer_id, topic, training_date, result) VALUES
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='zhangsan')), '志愿者基础培训', '2025-10-15', '优秀'),
((SELECT id FROM volunteer WHERE user_id=(SELECT id FROM user WHERE username='lisi')), '志愿者心理辅导培训', '2025-09-20', '合格');


