-- 图书销售管理系统完整初始化脚本
-- 使用方法：在MySQL中执行此脚本即可完成数据库初始化

-- 1. 建库
CREATE DATABASE IF NOT EXISTS bookshop
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;

USE bookshop;

-- 2. 用户表
CREATE TABLE IF NOT EXISTS t_user (
  user_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_name    VARCHAR(30) NOT NULL,
  password     VARCHAR(64) NOT NULL,
  phone        VARCHAR(20),
  email        VARCHAR(50),
  address      VARCHAR(200),
  role         TINYINT DEFAULT 0 COMMENT '0-用户 1-管理员',
  status       TINYINT DEFAULT 1 COMMENT '0-禁用 1-正常',
  create_time  DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_name (user_name),
  KEY idx_role_status (role, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 3. 类别表
CREATE TABLE IF NOT EXISTS t_category (
  category_id   INT AUTO_INCREMENT PRIMARY KEY,
  category_name VARCHAR(30) NOT NULL,
  parent_id     INT DEFAULT 0,
  path          VARCHAR(255),
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY idx_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书类别';

-- 4. 图书表
CREATE TABLE IF NOT EXISTS t_book (
  book_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  book_name     VARCHAR(100) NOT NULL,
  author        VARCHAR(60),
  press         VARCHAR(60),
  publish_date  DATE,
  isbn          VARCHAR(20) UNIQUE,
  category_id   INT,
  price         DECIMAL(10,2) CHECK (price >= 0),
  stock         INT DEFAULT 0 CHECK (stock >= 0),
  cover_url     VARCHAR(200),
  description   TEXT,
  sales_volume  INT DEFAULT 0,
  create_time   DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY idx_category (category_id),
  KEY idx_price (price),
  FULLTEXT KEY ft_name (book_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书';

-- 5. 订单表
CREATE TABLE IF NOT EXISTS t_order (
  order_id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id        BIGINT NOT NULL,
  order_no       VARCHAR(32) NOT NULL,
  order_date     DATETIME DEFAULT CURRENT_TIMESTAMP,
  total_amount   DECIMAL(10,2) NOT NULL,
  status         TINYINT DEFAULT 0 COMMENT '0待支付 1已支付 2已发货 3已完成 4已取消',
  receiver_name  VARCHAR(30),
  receiver_phone VARCHAR(20),
  receiver_addr  VARCHAR(200),
  pay_time       DATETIME,
  deliver_time   DATETIME,
  finish_time    DATETIME,
  create_time    DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_order_no (order_no),
  KEY idx_user_status (user_id, status),
  KEY idx_create (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单';

-- 6. 订单明细
CREATE TABLE IF NOT EXISTS t_order_item (
  item_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id    BIGINT NOT NULL,
  book_id     BIGINT NOT NULL,
  quantity    INT NOT NULL CHECK (quantity > 0),
  unit_price  DECIMAL(10,2) NOT NULL,
  discount    DECIMAL(3,2) DEFAULT 1.0,
  subtotal    DECIMAL(10,2) NOT NULL,
  KEY idx_oid (order_id),
  KEY idx_bid (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细';

-- 7. 购物车
CREATE TABLE IF NOT EXISTS t_cart (
  cart_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id     BIGINT NOT NULL,
  book_id     BIGINT NOT NULL,
  quantity    INT DEFAULT 1,
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_user_book (user_id, book_id),
  KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

-- 8. 外键约束（可选，开发阶段可关闭，生产建议打开）
ALTER TABLE t_book
  ADD CONSTRAINT fk_book_category
  FOREIGN KEY (category_id) REFERENCES t_category(category_id)
  ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE t_order
  ADD CONSTRAINT fk_order_user
  FOREIGN KEY (user_id) REFERENCES t_user(user_id)
  ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE t_order_item
  ADD CONSTRAINT fk_item_order
  FOREIGN KEY (order_id) REFERENCES t_order(order_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_item_book
  FOREIGN KEY (book_id) REFERENCES t_book(book_id)
  ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE t_cart
  ADD CONSTRAINT fk_cart_user
  FOREIGN KEY (user_id) REFERENCES t_user(user_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_cart_book
  FOREIGN KEY (book_id) REFERENCES t_book(book_id)
  ON DELETE CASCADE ON UPDATE CASCADE;

-- 9. 插入测试数据

-- 用户数据（密码都是123456，使用BCrypt加密）
INSERT INTO t_user(user_name,password,phone,email,address,role,status)
VALUES 
('admin', '$2a$10$ht.p7X20nWCKkrR1OuXIAetB87PYvQWldH7920qxpgaoRCvtjePKG', '13800000000','admin@shop.com','火星路1号',1,1),
('zhangsan', '$2a$10$ht.p7X20nWCKkrR1OuXIAetB87PYvQWldH7920qxpgaoRCvtjePKG', '13911111111','zs@xx.com','地球村',0,1);

-- 类别数据
INSERT INTO t_category(category_name,parent_id,path) VALUES
('计算机',0,'/1/'),
('文学',0,'/2/'),
('儿童',0,'/3/'),
('编程语言',1,'/1/4/'),
('数据库',1,'/1/5/'),
('小说',2,'/2/6/');

-- 图书数据
INSERT INTO t_book(book_name,author,press,isbn,category_id,price,stock,description,sales_volume)
VALUES
('Java编程思想','Bruce Eckel','机械工业','9787111213826',4,89.00,200,'经典Java入门书籍',0),
('MySQL必知必会','Ben Forta','人民邮电','9787115272045',5,45.00,150,'SQL基础教程',0),
('百年孤独','加西亚·马尔克斯','南海出版','9787544253994',6,39.50,100,'魔幻现实主义经典',0),
('小王子','圣埃克苏佩里','译林','9787544758974',3,22.00,300,'经典童话',0),
('Spring Boot实战','汪云飞','电子工业','9787121300001',4,69.00,180,'Spring Boot开发指南',0),
('深入理解MySQL','姜承尧','机械工业','9787111400002',5,79.00,120,'MySQL高级特性',0);

-- 购物车数据
INSERT INTO t_cart(user_id,book_id,quantity) VALUES 
(2,1,1),
(2,3,2);

-- 订单数据（先插主表）
INSERT INTO t_order(user_id,order_no,total_amount,status,receiver_name,receiver_phone,receiver_addr,pay_time)
VALUES 
(2,'T202601091000001',167.50,3,'张三','13911111111','地球村',NOW());

-- 订单明细数据
INSERT INTO t_order_item(order_id,book_id,quantity,unit_price,discount,subtotal)
VALUES
(1,1,1,89.00,1.0,89.00),
(1,3,2,39.50,1.0,79.00);

-- 更新图书销量
UPDATE t_book SET sales_volume=sales_volume+1 WHERE book_id=1;
UPDATE t_book SET sales_volume=sales_volume+2 WHERE book_id=3;

-- 完成
SELECT '数据库初始化完成！' AS message;

