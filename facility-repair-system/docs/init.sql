-- =====================================================
-- 城市公共设施智能报修与状态监控平台 - 数据库初始化脚本
-- 数据库: facility_repair_db
-- 字符集: utf8mb4
-- =====================================================

-- 1. 创建数据库
DROP DATABASE IF EXISTS facility_repair_db;
CREATE DATABASE facility_repair_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE facility_repair_db;

-- 2. 创建用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    phone VARCHAR(20) UNIQUE NOT NULL COMMENT '手机号',
    password VARCHAR(100) NOT NULL COMMENT '密码(加密存储)',
    real_name VARCHAR(50) COMMENT '真实姓名',
    user_type ENUM('citizen', 'worker', 'admin') NOT NULL DEFAULT 'citizen' COMMENT '用户类型',
    status ENUM('active', 'inactive', 'banned') NOT NULL DEFAULT 'active' COMMENT '用户状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_phone (phone),
    KEY idx_user_type (user_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 3. 创建公共设施表
CREATE TABLE public_facility (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '设施ID',
    facility_code VARCHAR(50) UNIQUE NOT NULL COMMENT '设施编号',
    facility_type ENUM('street_light', 'park_bench', 'public_toilet', 'fitness_equipment') NOT NULL COMMENT '设施类型',
    location VARCHAR(200) COMMENT '位置描述',
    latitude DECIMAL(10, 8) COMMENT '纬度',
    longitude DECIMAL(11, 8) COMMENT '经度',
    status ENUM('normal', 'broken', 'repairing', 'discarded') NOT NULL DEFAULT 'normal' COMMENT '设施状态',
    install_date DATE COMMENT '安装日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_facility_code (facility_code),
    KEY idx_facility_type (facility_type),
    KEY idx_status (status),
    KEY idx_location (location)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公共设施表';

-- 4. 创建维修工表
CREATE TABLE worker (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '维修工ID',
    user_id BIGINT UNIQUE NOT NULL COMMENT '关联用户ID',
    specialization VARCHAR(100) COMMENT '专业技能',
    phone VARCHAR(20) COMMENT '联系电话',
    status ENUM('available', 'busy', 'offline') NOT NULL DEFAULT 'available' COMMENT '维修工状态',
    pending_count INT DEFAULT 0 COMMENT '待办任务数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    KEY idx_status (status),
    KEY idx_pending_count (pending_count)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='维修工表';

-- 5. 创建报修单表
CREATE TABLE repair_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '报修单ID',
    repair_code VARCHAR(50) UNIQUE NOT NULL COMMENT '报修单编号',
    facility_id BIGINT NOT NULL COMMENT '设施ID',
    user_id BIGINT NOT NULL COMMENT '市民ID',
    worker_id BIGINT COMMENT '维修工ID',
    problem_description TEXT NOT NULL COMMENT '问题描述',
    images VARCHAR(1000) COMMENT '问题图片URL列表(JSON格式)',
    status ENUM('submitted', 'accepted', 'processing', 'completed') NOT NULL DEFAULT 'submitted' COMMENT '报修单状态',
    submit_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    accept_time DATETIME COMMENT '接受时间',
    complete_time DATETIME COMMENT '完成时间',
    feedback TEXT COMMENT '维修反馈',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (facility_id) REFERENCES public_facility(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (worker_id) REFERENCES user(id),
    KEY idx_repair_code (repair_code),
    KEY idx_facility_id (facility_id),
    KEY idx_user_id (user_id),
    KEY idx_worker_id (worker_id),
    KEY idx_status (status),
    KEY idx_submit_time (submit_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报修单表';

-- 6. 创建预警表
CREATE TABLE alarm (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预警ID',
    repair_order_id BIGINT NOT NULL COMMENT '报修单ID',
    alarm_type VARCHAR(50) NOT NULL COMMENT '预警类型(timeout_repair)',
    alarm_content TEXT COMMENT '预警内容',
    alarm_level ENUM('low', 'medium', 'high') NOT NULL DEFAULT 'medium' COMMENT '预警级别',
    is_read BOOLEAN DEFAULT FALSE COMMENT '是否已读',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (repair_order_id) REFERENCES repair_order(id) ON DELETE CASCADE,
    KEY idx_alarm_type (alarm_type),
    KEY idx_is_read (is_read),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='预警表';

-- 7. 创建统计表
CREATE TABLE statistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '统计ID',
    facility_type VARCHAR(30) COMMENT '设施类型',
    total_count INT DEFAULT 0 COMMENT '总数',
    broken_count INT DEFAULT 0 COMMENT '故障数',
    fault_rate DECIMAL(5, 2) DEFAULT 0 COMMENT '故障率(%)',
    avg_repair_time DECIMAL(10, 2) DEFAULT 0 COMMENT '平均维修耗时(小时)',
    stat_date DATE COMMENT '统计日期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_type_date (facility_type, stat_date),
    KEY idx_stat_date (stat_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计表';

-- =====================================================
-- 测试数据插入
-- =====================================================

-- 1. 插入管理员用户
INSERT INTO user (phone, password, real_name, user_type, status) VALUES
('18600000001', '$2a$10$test1234567890', '王管理', 'admin', 'active');

-- 2. 插入维修工用户
INSERT INTO user (phone, password, real_name, user_type, status) VALUES
('18600000002', '$2a$10$test1234567890', '李维修', 'worker', 'active'),
('18600000003', '$2a$10$test1234567890', '张维修', 'worker', 'active'),
('18600000004', '$2a$10$test1234567890', '刘维修', 'worker', 'active');

-- 3. 插入维修工信息
INSERT INTO worker (user_id, specialization, phone, status, pending_count) VALUES
(2, '路灯维修', '18600000002', 'available', 0),
(3, '公用设施维修', '18600000003', 'available', 0),
(4, '综合维修', '18600000004', 'available', 0);

-- 4. 插入市民用户
INSERT INTO user (phone, password, real_name, user_type, status) VALUES
('18600000010', '$2a$10$test1234567890', '市民甲', 'citizen', 'active'),
('18600000011', '$2a$10$test1234567890', '市民乙', 'citizen', 'active'),
('18600000012', '$2a$10$test1234567890', '市民丙', 'citizen', 'active'),
('18600000013', '$2a$10$test1234567890', '市民丁', 'citizen', 'active'),
('18600000014', '$2a$10$test1234567890', '市民戊', 'citizen', 'active');

-- 5. 插入公共设施数据
-- 路灯
INSERT INTO public_facility (facility_code, facility_type, location, latitude, longitude, status, install_date) VALUES
('SL-001', 'street_light', '文化广场北侧', 39.9042, 116.4074, 'normal', '2020-01-15'),
('SL-002', 'street_light', '人民公园西门', 39.8648, 116.4067, 'normal', '2020-02-20'),
('SL-003', 'street_light', '商业街道A段', 39.9276, 116.4119, 'broken', '2020-03-10'),
('SL-004', 'street_light', '学院路口', 39.8956, 116.4011, 'repairing', '2020-01-25'),
('SL-005', 'street_light', '南二环路', 39.8827, 116.3906, 'normal', '2020-04-05');

-- 公园长椅
INSERT INTO public_facility (facility_code, facility_type, location, latitude, longitude, status, install_date) VALUES
('PB-001', 'park_bench', '人民公园中心广场', 39.8648, 116.4067, 'normal', '2021-01-10'),
('PB-002', 'park_bench', '人民公园南侧步道', 39.8620, 116.4080, 'broken', '2021-02-15'),
('PB-003', 'park_bench', '河滨公园休息区', 39.8500, 116.4150, 'normal', '2021-03-20'),
('PB-004', 'park_bench', '中山公园草坪', 39.8580, 116.3950, 'normal', '2021-04-10'),
('PB-005', 'park_bench', '植物园入口', 39.8700, 116.4200, 'discarded', '2021-05-01');

-- 公共厕所
INSERT INTO public_facility (facility_code, facility_type, location, latitude, longitude, status, install_date) VALUES
('PT-001', 'public_toilet', '火车站广场', 39.8986, 116.4312, 'normal', '2019-01-01'),
('PT-002', 'public_toilet', '商业街道B段', 39.9276, 116.4119, 'broken', '2019-02-15'),
('PT-003', 'public_toilet', '地铁2号线C出口', 39.9100, 116.4200, 'normal', '2019-03-20'),
('PT-004', 'public_toilet', '公交总站', 39.8750, 116.3800, 'repairing', '2019-04-10'),
('PT-005', 'public_toilet', '机场大巴站', 39.8900, 116.5000, 'normal', '2019-05-15');

-- 健身器材
INSERT INTO public_facility (facility_code, facility_type, location, latitude, longitude, status, install_date) VALUES
('FE-001', 'fitness_equipment', '中央公园东侧', 39.8660, 116.4150, 'normal', '2022-01-10'),
('FE-002', 'fitness_equipment', '朝阳区体育馆', 39.9000, 116.4500, 'broken', '2022-02-20'),
('FE-003', 'fitness_equipment', '海淀区运动中心', 39.9600, 116.3300, 'normal', '2022-03-15'),
('FE-004', 'fitness_equipment', '丰台区休闲广场', 39.8300, 116.3800, 'normal', '2022-04-01'),
('FE-005', 'fitness_equipment', '昌平区健身公园', 40.2200, 116.2300, 'repairing', '2022-05-10');

-- 6. 插入报修单数据
-- 已完成的报修单
INSERT INTO repair_order (repair_code, facility_id, user_id, worker_id, problem_description, status, submit_time, accept_time, complete_time, feedback) VALUES
('RP-2024-001', 3, 5, 2, '路灯不亮,无法照明', 'completed', '2024-12-01 09:00:00', '2024-12-01 09:30:00', '2024-12-01 16:00:00', '更换灯泡,现已正常亮起'),
('RP-2024-002', 2, 6, 3, '长椅垮塌,存在安全隐患', 'completed', '2024-12-02 10:00:00', '2024-12-02 11:00:00', '2024-12-02 17:30:00', '已修复,长椅可正常使用'),
('RP-2024-003', 6, 7, 2, '厕所冲水不正常', 'completed', '2024-12-03 14:00:00', '2024-12-03 14:30:00', '2024-12-04 10:00:00', '维修冲水装置,恢复正常'),
('RP-2024-004', 11, 8, 3, '健身器材破损', 'completed', '2024-12-05 08:00:00', '2024-12-05 08:30:00', '2024-12-05 14:00:00', '更换损坏部件');

-- 维修中的报修单(用于超时预警测试)
INSERT INTO repair_order (repair_code, facility_id, user_id, worker_id, problem_description, status, submit_time, accept_time) VALUES
('RP-2024-005', 4, 9, 4, '路灯安装位置偏移', 'processing', '2024-12-20 10:00:00', '2024-12-20 10:30:00'),
('RP-2024-006', 8, 5, 4, '公园长椅木板开裂', 'processing', '2024-12-22 11:00:00', '2024-12-22 11:30:00');

-- 已提交的报修单
INSERT INTO repair_order (repair_code, facility_id, user_id, problem_description, status, submit_time) VALUES
('RP-2024-007', 10, 6, '公共厕所卫生纸盒损坏', 'submitted', '2024-12-28 15:00:00'),
('RP-2024-008', 15, 7, '健身器材零件松动', 'submitted', '2024-12-29 09:00:00');

-- 7. 插入统计数据
INSERT INTO statistics (facility_type, total_count, broken_count, fault_rate, avg_repair_time, stat_date) VALUES
('street_light', 5, 1, 20.00, 7.5, CURDATE()),
('park_bench', 5, 1, 20.00, 6.25, CURDATE()),
('public_toilet', 5, 1, 20.00, 20.0, CURDATE()),
('fitness_equipment', 5, 1, 20.00, 5.5, CURDATE());

-- =====================================================
-- 建立索引以优化查询性能
-- =====================================================
CREATE INDEX idx_repair_facility_status ON repair_order(facility_id, status);
CREATE INDEX idx_repair_worker_status ON repair_order(worker_id, status);
CREATE INDEX idx_alarm_create_time ON alarm(create_time DESC);

-- =====================================================
-- 创建视图用于快速查询
-- =====================================================

-- 视图1: 维修单详情视图
CREATE VIEW v_repair_order_detail AS
SELECT 
    ro.id,
    ro.repair_code,
    ro.facility_id,
    pf.facility_code,
    pf.facility_type,
    pf.location,
    u_citizen.real_name AS citizen_name,
    u_worker.real_name AS worker_name,
    ro.problem_description,
    ro.status,
    ro.submit_time,
    ro.accept_time,
    ro.complete_time,
    CASE 
        WHEN ro.complete_time IS NOT NULL THEN 
            TIMESTAMPDIFF(HOUR, ro.submit_time, ro.complete_time)
        WHEN ro.accept_time IS NOT NULL THEN 
            TIMESTAMPDIFF(HOUR, ro.submit_time, NOW())
        ELSE 
            TIMESTAMPDIFF(HOUR, ro.submit_time, NOW())
    END AS repair_hours
FROM repair_order ro
INNER JOIN public_facility pf ON ro.facility_id = pf.id
INNER JOIN user u_citizen ON ro.user_id = u_citizen.id
LEFT JOIN user u_worker ON ro.worker_id = u_worker.id;

-- 视图2: 设施状态统计视图
CREATE VIEW v_facility_status_summary AS
SELECT 
    facility_type,
    COUNT(*) AS total_count,
    SUM(CASE WHEN status = 'broken' THEN 1 ELSE 0 END) AS broken_count,
    SUM(CASE WHEN status = 'repairing' THEN 1 ELSE 0 END) AS repairing_count,
    SUM(CASE WHEN status = 'normal' THEN 1 ELSE 0 END) AS normal_count,
    SUM(CASE WHEN status = 'discarded' THEN 1 ELSE 0 END) AS discarded_count,
    ROUND(SUM(CASE WHEN status = 'broken' THEN 1 ELSE 0 END) / COUNT(*) * 100, 2) AS fault_rate
FROM public_facility
GROUP BY facility_type;

-- 视图3: 维修工负载视图
CREATE VIEW v_worker_workload AS
SELECT 
    w.id,
    u.real_name,
    u.phone,
    w.specialization,
    w.pending_count,
    COUNT(CASE WHEN ro.status IN ('accepted', 'processing') THEN 1 END) AS active_orders,
    COUNT(CASE WHEN ro.status = 'completed' THEN 1 END) AS completed_orders
FROM worker w
INNER JOIN user u ON w.user_id = u.id
LEFT JOIN repair_order ro ON w.user_id = ro.worker_id
GROUP BY w.id;

COMMIT;
