-- 创建数据库
CREATE DATABASE IF NOT EXISTS agri_trade_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE agri_trade_db;

-- ============================================
-- 1. 用户表 - 所有角色共用
-- ============================================
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       phone VARCHAR(20),
                       real_name VARCHAR(50),
                       role ENUM('admin', 'merchant', 'customer') NOT NULL,
                       avatar VARCHAR(255),
                       address TEXT,
                       status TINYINT DEFAULT 1 COMMENT '1正常 0禁用',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 2. 产品分类表
-- ============================================
CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(50) NOT NULL,
                            parent_id INT DEFAULT 0,
                            sort INT DEFAULT 0,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- 3. 产品表
-- ============================================
CREATE TABLE products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          merchant_id INT NOT NULL,
                          category_id INT NOT NULL,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          stock INT DEFAULT 0,
                          unit VARCHAR(20) DEFAULT '件',
                          image_url VARCHAR(255),
                          status TINYINT DEFAULT 1 COMMENT '1上架 0下架',
                          sales_count INT DEFAULT 0,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (merchant_id) REFERENCES users(id),
                          FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ============================================
-- 4. 订单表
-- ============================================
CREATE TABLE orders (
                        id VARCHAR(20) PRIMARY KEY COMMENT '订单号',
                        customer_id INT NOT NULL,
                        merchant_id INT NOT NULL,
                        total_amount DECIMAL(10,2) NOT NULL,
                        status TINYINT DEFAULT 0 COMMENT '0待付款 1已付款 2已发货 3已完成 4已取消',
                        address TEXT NOT NULL,
                        phone VARCHAR(20) NOT NULL,
                        receiver VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (customer_id) REFERENCES users(id),
                        FOREIGN KEY (merchant_id) REFERENCES users(id)
);

-- ============================================
-- 5. 订单详情表
-- ============================================
CREATE TABLE order_items (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             order_id VARCHAR(20) NOT NULL,
                             product_id INT NOT NULL,
                             quantity INT NOT NULL,
                             price DECIMAL(10,2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id),
                             FOREIGN KEY (product_id) REFERENCES products(id)
);

-- ============================================
-- 6. 购物车表
-- ============================================
CREATE TABLE cart (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      user_id INT NOT NULL,
                      product_id INT NOT NULL,
                      quantity INT DEFAULT 1,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES users(id),
                      FOREIGN KEY (product_id) REFERENCES products(id),
                      UNIQUE KEY (user_id, product_id)
);

-- ============================================
-- 插入基本数据
-- ============================================

-- 1. 插入用户数据
INSERT INTO users (username, password, email, real_name, role) VALUES
                                                                   ('admin', 'admin123', 'admin@example.com', '管理员', 'admin'),
                                                                   ('merchant1', 'merchant123', 'merchant1@example.com', '张三商家', 'merchant'),
                                                                   ('merchant2', 'merchant123', 'merchant2@example.com', '李四农场', 'merchant'),
                                                                   ('user1', 'user123', 'user1@example.com', '王五', 'customer'),
                                                                   ('user2', 'user123', 'user2@example.com', '赵六', 'customer');

-- 2. 插入分类数据
INSERT INTO categories (name, parent_id, sort) VALUES
                                                   ('蔬菜', 0, 1),
                                                   ('水果', 0, 2),
                                                   ('谷物', 0, 3),
                                                   ('叶菜类', 1, 11),
                                                   ('根茎类', 1, 12),
                                                   ('浆果类', 2, 21),
                                                   ('柑橘类', 2, 22);

-- 3. 插入产品数据
INSERT INTO products (merchant_id, category_id, name, description, price, stock, unit, image_url) VALUES
                                                                                                      (2, 4, '有机菠菜', '新鲜有机菠菜', 12.80, 100, '斤', '/images/spinach.jpg'),
                                                                                                      (2, 5, '胡萝卜', '农家胡萝卜', 8.50, 200, '斤', '/images/carrot.jpg'),
                                                                                                      (3, 6, '蓝莓', '高山蓝莓', 45.00, 50, '盒', '/images/blueberry.jpg'),
                                                                                                      (3, 7, '橙子', '有机橙子', 18.50, 150, '斤', '/images/orange.jpg');

-- 4. 插入订单数据
INSERT INTO orders (id, customer_id, merchant_id, total_amount, status, address, phone, receiver) VALUES
                                                                                                      ('ORD001', 4, 2, 45.60, 3, '北京市朝阳区', '13800138000', '王五'),
                                                                                                      ('ORD002', 5, 3, 63.50, 2, '上海市浦东新区', '13900139000', '赵六');

-- 5. 插入订单详情
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
                                                                    ('ORD001', 1, 2, 12.80),
                                                                    ('ORD001', 2, 2, 8.50),
                                                                    ('ORD002', 3, 1, 45.00),
                                                                    ('ORD002', 4, 1, 18.50);

-- 6. 插入购物车数据
INSERT INTO cart (user_id, product_id, quantity) VALUES
                                                     (4, 3, 1),
                                                     (4, 4, 2),
                                                     (5, 1, 3);
