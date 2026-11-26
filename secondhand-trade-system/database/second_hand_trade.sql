/*
 Navicat Premium Data Transfer

 Source Server         : 123
 Source Server Type    : MySQL
 Source Server Version : 80040
 Source Host           : localhost:3306
 Source Schema         : second_hand_trade

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 02/01/2026 18:07:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` int NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类描述',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类图标URL',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `category_name`(`category_name`) USING BTREE,
  INDEX `idx_name`(`category_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '物品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '图书', '各类书籍', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (2, '电子产品', '手机、平板、电脑等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (3, '服饰箱包', '衣服、鞋、包等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (4, '食品饮料', '食品、饮料等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (5, '家居用品', '床品、家具等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (6, '美妆护肤', '化妆品、护肤品等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (7, '运动户外', '运动器材、户外用品', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (8, '数码配件', '手机壳、充电器等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (9, '娱乐产品', '游戏机、乐器等', NULL, '2026-01-02 16:09:22');
INSERT INTO `category` VALUES (10, '其他', '其他物品', NULL, '2026-01-02 16:09:22');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `message_id` int NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` int NOT NULL COMMENT '发送者ID',
  `receiver_id` int NOT NULL COMMENT '接收者ID',
  `product_id` int NULL DEFAULT NULL COMMENT '物品ID',
  `message_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
  `message_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `send_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  `is_read` tinyint NULL DEFAULT 0 COMMENT '是否已读',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `sender_id`(`sender_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `idx_receiver`(`receiver_id`) USING BTREE,
  INDEX `idx_is_read`(`is_read`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `message_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (8, 4, 1, 1, '询价', '请问这本书还有吗？可以便宜点吗？', '2025-12-01 10:15:00', 1, NULL);
INSERT INTO `message` VALUES (9, 1, 4, 1, '回复', '有的，图片上的就是，价格可以商量', '2025-12-01 10:20:00', 1, NULL);
INSERT INTO `message` VALUES (10, 5, 1, 2, '询价', '能不能发快递？多少运费？', '2025-12-02 11:20:00', 1, NULL);
INSERT INTO `message` VALUES (11, 1, 5, 2, '回复', '可以发快递，顺丰到付，或者你来自取', '2025-12-02 11:25:00', 1, NULL);
INSERT INTO `message` VALUES (12, 6, 2, 3, '询价', '这个衣服能试穿吗？', '2025-12-03 09:45:00', 0, NULL);
INSERT INTO `message` VALUES (13, 4, 3, 5, '询价', '床单是全棉的吗？多大尺寸？', '2025-12-05 13:15:00', 1, NULL);
INSERT INTO `message` VALUES (14, 3, 4, 5, '回复', '是的，100%全棉，1.5米床的四件套', '2025-12-05 13:20:00', 1, NULL);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `buyer_id` int NOT NULL COMMENT '买家ID',
  `seller_id` int NOT NULL COMMENT '卖家ID',
  `product_id` int NOT NULL COMMENT '物品ID',
  `order_amount` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `order_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '待支付' COMMENT '订单状态',
  `quantity` int NULL DEFAULT 1 COMMENT '购买数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receipt_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `completion_time` datetime NULL DEFAULT NULL COMMENT '完成时间',
  `buyer_rate_score` int NULL DEFAULT NULL COMMENT '买家评分',
  `buyer_rate_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '买家评价',
  `seller_rate_score` int NULL DEFAULT NULL COMMENT '卖家评分',
  `seller_rate_comment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '卖家评价',
  `transaction_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '交易地址',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE INDEX `order_number`(`order_number`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `idx_buyer`(`buyer_id`) USING BTREE,
  INDEX `idx_seller`(`seller_id`) USING BTREE,
  INDEX `idx_status`(`order_status`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE,
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_ibfk_3` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (8, 'ORD20251201001', 4, 1, 1, 35.00, '交易完成', 1, '2025-12-01 10:30:00', '2025-12-01 11:00:00', NULL, NULL, '2025-12-02 14:00:00', 5, '卖家很守信用，书籍品相很好，推荐！', 5, '买家很爽快，准时收货，好评！', '宿舍1栋301', NULL);
INSERT INTO `order` VALUES (9, 'ORD20251202002', 5, 1, 2, 6500.00, '交易完成', 1, '2025-12-02 11:30:00', '2025-12-02 12:00:00', NULL, NULL, '2025-12-03 15:00:00', 5, '电脑完全没问题，包装很仔细', 5, '买家人很好，爽快付款', '宿舍1栋301', NULL);
INSERT INTO `order` VALUES (10, 'ORD20251203003', 6, 2, 3, 350.00, '待收货', 1, '2025-12-03 10:00:00', '2025-12-03 10:30:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '宿舍2栋502', '已发货，EMS单号：1234567890');
INSERT INTO `order` VALUES (11, 'ORD20251204004', 4, 2, 4, 85.00, '交易完成', 1, '2025-12-04 14:30:00', '2025-12-04 15:00:00', NULL, NULL, '2025-12-05 10:00:00', 5, '营养品很不错，很新鲜', 4, '买家很守信用', '宿舍2栋502', NULL);
INSERT INTO `order` VALUES (12, 'ORD20251205005', 5, 3, 5, 150.00, '已支付', 1, '2025-12-05 13:30:00', '2025-12-05 14:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '宿舍3栋203', '待发货');
INSERT INTO `order` VALUES (13, 'ORD20251206006', 6, 3, 6, 280.00, '待支付', 1, '2025-12-06 16:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '宿舍3栋203', NULL);
INSERT INTO `order` VALUES (14, 'ORD20251207007', 4, 1, 7, 1200.00, '已支付', 1, '2025-12-07 10:30:00', '2025-12-07 11:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '宿舍1栋301', '待发货，大件商品需要协商交付时间');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `product_id` int NOT NULL AUTO_INCREMENT COMMENT '物品ID',
  `seller_id` int NOT NULL COMMENT '卖家ID',
  `category_id` int NOT NULL COMMENT '分类ID',
  `product_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '物品名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '物品描述',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `condition` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '物品成色（全新/九成新/八成新/使用过）',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物品图片URL',
  `publish_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `sale_start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `sale_end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `product_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '在售' COMMENT '物品状态（在售/已售/已下架）',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览次数',
  `location` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物品位置/交易地点',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `idx_seller`(`seller_id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE,
  INDEX `idx_status`(`product_status`) USING BTREE,
  INDEX `idx_name`(`product_name`) USING BTREE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `product_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '物品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (13, 1, 1, '高等数学上册', '清华大学出版，正版教材，无标记，品相好', 35.00, '九成新', '/images/gaodengshuxueshangce.avif', '2025-12-01 10:00:00', '2025-12-01 10:00:00', '2025-12-31 23:59:59', '在售', 3, '宿舍1栋301');
INSERT INTO `product` VALUES (14, 1, 2, 'MacBook Air M1', '2021年购买，正常使用，无维修，屏幕完好', 6500.00, '八成新', '/images/macbookairm1.avif', '2025-12-02 11:00:00', '2025-12-02 11:00:00', '2025-12-31 23:59:59', '在售', 3, '宿舍1栋301');
INSERT INTO `product` VALUES (15, 2, 3, '北面冲锋衣', '防水防风，户外穿，基本未穿，还有吊牌', 350.00, '全新', '/images/beimianchongfengyi.avif', '2025-12-03 09:30:00', '2025-12-03 09:30:00', '2025-12-31 23:59:59', '在售', 0, '宿舍2栋502');
INSERT INTO `product` VALUES (16, 2, 4, '蛋白粉1kg装', '健身营养品，未开封，生产日期新鲜', 85.00, '全新', '/images/danbaifen.avif', '2025-12-04 14:00:00', '2025-12-04 14:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍2栋502');
INSERT INTO `product` VALUES (17, 3, 5, '床上四件套', '全棉100%，原价398，只用过一次', 150.00, '全新', '/images/chuangshangsijiantao.avif', '2025-12-05 13:00:00', '2025-12-05 13:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍3栋203');
INSERT INTO `product` VALUES (18, 3, 6, 'SK-II神仙水', '正品，用过一半，还有很多', 280.00, '使用过', '/images/SKIIshenxianshui.avif', '2025-12-06 15:30:00', '2025-12-06 15:30:00', '2025-12-31 23:59:59', '在售', 0, '宿舍3栋203');
INSERT INTO `product` VALUES (19, 1, 7, '动感单车', '家用健身车，电磁阻力，基本没用过', 1200.00, '全新', '/images/donggandanche.avif', '2025-12-07 10:00:00', '2025-12-07 10:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍1栋301');
INSERT INTO `product` VALUES (20, 2, 8, '苹果原装充电器', '支持快充，原装正品，有发票', 120.00, '全新', '/images/pingguochongdianqi.avif', '2025-12-08 11:00:00', '2025-12-08 11:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍2栋502');
INSERT INTO `product` VALUES (21, 3, 9, '吉他一把', 'YAMAHA民谣吉他，音质好，需要调音', 600.00, '八成新', '/images/jita.avif', '2025-12-09 09:00:00', '2025-12-09 09:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍3栋203');
INSERT INTO `product` VALUES (22, 1, 10, '自行车锁一把', '钢铁制，防盗效果好，钥匙一把', 45.00, '全新', '/images/zixingchesuo.avif', '2025-12-10 12:00:00', '2025-12-10 12:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍1栋301');
INSERT INTO `product` VALUES (23, 2, 1, '数据结构与算法', '李宏毅编著，有笔记但不影响阅读', 42.00, '八成新', '/images/shujujiegou.avif', '2025-12-11 08:00:00', '2025-12-11 08:00:00', '2025-12-31 23:59:59', '在售', 0, '宿舍2栋502');
INSERT INTO `product` VALUES (24, 3, 2, 'iPad Air 5', '2022年款，屏幕无伤，电池健康98%', 3500.00, '九成新', '/images/ipadair5.avif', '2025-12-12 10:30:00', '2025-12-12 10:30:00', '2025-12-31 23:59:59', '在售', 0, '宿舍3栋203');

-- ----------------------------
-- Table structure for rating
-- ----------------------------
DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating`  (
  `rating_id` int NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` int NOT NULL COMMENT '订单ID',
  `rater_id` int NOT NULL COMMENT '评价者ID',
  `rated_id` int NOT NULL COMMENT '被评价人ID',
  `rating_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评价类型（商品/卖家/买家）',
  `score` int NOT NULL COMMENT '评分',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '评价内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`rating_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `rater_id`(`rater_id`) USING BTREE,
  INDEX `idx_rated`(`rated_id`) USING BTREE,
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`rater_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `rating_ibfk_3` FOREIGN KEY (`rated_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评价表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rating
-- ----------------------------

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction`  (
  `transaction_id` int NOT NULL AUTO_INCREMENT COMMENT '交易ID',
  `order_id` int NOT NULL COMMENT '订单ID',
  `buyer_id` int NOT NULL COMMENT '买家ID',
  `seller_id` int NOT NULL COMMENT '卖家ID',
  `transaction_amount` decimal(10, 2) NOT NULL COMMENT '交易金额',
  `transaction_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易类型（面交/快递/自取）',
  `transaction_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '进行中' COMMENT '交易状态',
  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  `expected_completion_time` datetime NULL DEFAULT NULL COMMENT '预计完成时间',
  `actual_completion_time` datetime NULL DEFAULT NULL COMMENT '实际完成时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`transaction_id`) USING BTREE,
  INDEX `buyer_id`(`buyer_id`) USING BTREE,
  INDEX `seller_id`(`seller_id`) USING BTREE,
  INDEX `idx_order`(`order_id`) USING BTREE,
  INDEX `idx_status`(`transaction_status`) USING BTREE,
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '交易记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transaction
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（加密存储）',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '真实姓名',
  `gender` enum('M','F') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'M' COMMENT '性别',
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所在院系',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '电话号码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `user_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '卖家' COMMENT '用户类型（卖家/买家/都是）',
  `user_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '正常' COMMENT '用户状态（正常/禁用）',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像URL',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `credit_score` int NULL DEFAULT 100 COMMENT '信用评分',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `phone_number`(`phone_number`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE,
  INDEX `idx_department`(`department`) USING BTREE,
  INDEX `idx_phone`(`phone_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, 'seller001', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'M', '计算机学院', '13800138001', 'zhangsan@nuc.edu.cn', '中北大学宿舍1栋', '卖家', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', '2026-01-02 08:38:27', 95);
INSERT INTO `user` VALUES (9, 'seller002', 'e10adc3949ba59abbe56e057f20f883e', '李四', 'M', '机械学院', '13800138002', 'lisi@nuc.edu.cn', '中北大学宿舍2栋', '卖家', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', NULL, 98);
INSERT INTO `user` VALUES (10, 'seller003', 'e10adc3949ba59abbe56e057f20f883e', '王女士', 'F', '化学工程学院', '13800138003', 'wang@nuc.edu.cn', '中北大学宿舍3栋', '卖家', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', NULL, 92);
INSERT INTO `user` VALUES (11, 'buyer001', 'e10adc3949ba59abbe56e057f20f883e', '赵五', 'M', '电气工程学院', '13800138004', 'zhaowu@nuc.edu.cn', '中北大学宿舍4栋', '买家', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', NULL, 100);
INSERT INTO `user` VALUES (12, 'buyer002', 'e10adc3949ba59abbe56e057f20f883e', '孙六', 'M', '土木工程学院', '13800138005', 'sunliu@nuc.edu.cn', '中北大学宿舍5栋', '买家', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', NULL, 97);
INSERT INTO `user` VALUES (13, 'buyer003', 'e10adc3949ba59abbe56e057f20f883e', '周女士', 'F', '艺术学院', '13800138006', 'zhou@nuc.edu.cn', '中北大学宿舍6栋', '都是', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', NULL, 94);
INSERT INTO `user` VALUES (14, 'admin001', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'M', '校园管理', '13800138007', 'admin@nuc.edu.cn', '中北大学行政楼', '都是', '正常', 'https://via.placeholder.com/150', '2026-01-02 16:14:27', '2026-01-02 09:34:45', 100);

SET FOREIGN_KEY_CHECKS = 1;
