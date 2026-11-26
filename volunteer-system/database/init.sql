-- 志愿者管理系统数据库初始化脚本
-- 数据库: volunteer_mgmt
-- 字符集: utf8mb4

CREATE DATABASE IF NOT EXISTS volunteer_mgmt DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE volunteer_mgmt;

-- 角色表
CREATE TABLE IF NOT EXISTS role (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE COMMENT '角色名称',
  description VARCHAR(200) COMMENT '角色描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户表
CREATE TABLE IF NOT EXISTS user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  password VARCHAR(100) NOT NULL COMMENT '密码',
  role_id INT NOT NULL COMMENT '角色ID',
  full_name VARCHAR(50) COMMENT '姓名',
  phone VARCHAR(20) COMMENT '手机号',
  email VARCHAR(100) COMMENT '邮箱',
  status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 志愿者信息表
CREATE TABLE IF NOT EXISTS volunteer (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE COMMENT '用户ID',
  gender CHAR(1) COMMENT '性别: M-男, F-女',
  birthday DATE COMMENT '生日',
  id_card VARCHAR(20) COMMENT '身份证号',
  address VARCHAR(200) COMMENT '地址',
  audit_status TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态: 0-待审核, 1-通过, 2-驳回',
  audit_remark VARCHAR(200) COMMENT '审核备注',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT fk_vol_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者信息表';

-- 志愿者特长表
CREATE TABLE IF NOT EXISTS volunteer_skill (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id BIGINT NOT NULL COMMENT '志愿者ID',
  skill_name VARCHAR(100) NOT NULL COMMENT '特长名称',
  description VARCHAR(200) COMMENT '特长描述',
  CONSTRAINT fk_vs_vol FOREIGN KEY (volunteer_id) REFERENCES volunteer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者特长表';

-- 志愿者服务意向表
CREATE TABLE IF NOT EXISTS volunteer_preference (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id BIGINT NOT NULL COMMENT '志愿者ID',
  service_type VARCHAR(100) COMMENT '服务类型',
  preferred_time VARCHAR(100) COMMENT '偏好时间',
  preferred_location VARCHAR(100) COMMENT '偏好地点',
  CONSTRAINT fk_vp_vol FOREIGN KEY (volunteer_id) REFERENCES volunteer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='志愿者服务意向表';

-- 服务记录表
CREATE TABLE IF NOT EXISTS service_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id BIGINT NOT NULL COMMENT '志愿者ID',
  service_date DATE COMMENT '服务日期',
  hours DECIMAL(5,2) COMMENT '服务时长(小时)',
  content VARCHAR(200) COMMENT '服务内容',
  evaluation VARCHAR(200) COMMENT '评价',
  CONSTRAINT fk_sr_vol FOREIGN KEY (volunteer_id) REFERENCES volunteer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务记录表';

-- 培训记录表
CREATE TABLE IF NOT EXISTS training_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  volunteer_id BIGINT NOT NULL COMMENT '志愿者ID',
  topic VARCHAR(100) COMMENT '培训主题',
  training_date DATE COMMENT '培训日期',
  result VARCHAR(100) COMMENT '培训结果',
  CONSTRAINT fk_tr_vol FOREIGN KEY (volunteer_id) REFERENCES volunteer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='培训记录表';

-- 招募信息表
CREATE TABLE IF NOT EXISTS recruitment (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL COMMENT '招募标题',
  description TEXT COMMENT '招募描述',
  location VARCHAR(100) COMMENT '活动地点',
  start_time DATETIME COMMENT '开始时间',
  end_time DATETIME COMMENT '结束时间',
  requirement VARCHAR(200) COMMENT '招募条件',
  max_volunteers INT COMMENT '最大志愿者人数',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-草稿, 1-发布, 2-结束',
  created_by BIGINT COMMENT '创建人ID',
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  CONSTRAINT fk_rec_user FOREIGN KEY (created_by) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='招募信息表';

-- 报名记录表
CREATE TABLE IF NOT EXISTS recruitment_application (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  recruitment_id BIGINT NOT NULL COMMENT '招募ID',
  volunteer_id BIGINT NOT NULL COMMENT '志愿者ID',
  apply_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-待审核, 1-通过, 2-拒绝, 3-取消',
  remark VARCHAR(200) COMMENT '备注',
  CONSTRAINT fk_ra_rec FOREIGN KEY (recruitment_id) REFERENCES recruitment(id) ON DELETE CASCADE,
  CONSTRAINT fk_ra_vol FOREIGN KEY (volunteer_id) REFERENCES volunteer(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报名记录表';

-- 初始化角色数据
INSERT INTO role (name, description) VALUES
('ADMIN', '系统管理员'),
('VOLUNTEER', '志愿者')
ON DUPLICATE KEY UPDATE description=VALUES(description);

-- 初始化管理员账号（密码: admin123）
INSERT INTO user (username, password, role_id, full_name, phone, email, status) VALUES
('admin', 'admin123', 1, '系统管理员', '13800000000', 'admin@example.com', 1)
ON DUPLICATE KEY UPDATE username=username;

-- 创建索引
CREATE INDEX idx_user_role ON user(role_id);
CREATE INDEX idx_volunteer_user ON volunteer(user_id);
CREATE INDEX idx_volunteer_audit ON volunteer(audit_status);
CREATE INDEX idx_recruitment_status ON recruitment(status);
CREATE INDEX idx_application_recruitment ON recruitment_application(recruitment_id);
CREATE INDEX idx_application_volunteer ON recruitment_application(volunteer_id);
CREATE INDEX idx_application_status ON recruitment_application(status);


