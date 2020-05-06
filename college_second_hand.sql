/*
 Navicat Premium Data Transfer

 Source Server         : myconnection
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : college_second_hand

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/05/2020 00:28:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for table_address
-- ----------------------------
DROP TABLE IF EXISTS `table_address`;
CREATE TABLE `table_address`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `address_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人详细地址',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人手机',
  `default` int(1) NULL DEFAULT 0 COMMENT '0,非默认地址，1-默认地址',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户id',
  `user_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `selected` int(1) NULL DEFAULT NULL COMMENT '1-yes 0-no',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id_in_adress`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_id_in_adress` FOREIGN KEY (`user_id`) REFERENCES `table_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_address
-- ----------------------------
INSERT INTO `table_address` VALUES (1, 'Jack', '南充市嘉陵区', '15182942983', 0, 14, '777777', 1);
INSERT INTO `table_address` VALUES (3, 'Prayer', '江西省南昌市红谷滩新区南昌大学', '13361650890', 0, NULL, '777777', NULL);
INSERT INTO `table_address` VALUES (7, '777', '777', '777', 0, NULL, '777777', NULL);
INSERT INTO `table_address` VALUES (14, '马云', '支付宝', '64676464', 0, NULL, '222222', NULL);
INSERT INTO `table_address` VALUES (16, 'Yol', 'Fjfkjdkdkfkkd', '39939493', 0, NULL, '222222', NULL);
INSERT INTO `table_address` VALUES (17, '。', '你你你', '16464664', 0, NULL, '6130116071', NULL);
INSERT INTO `table_address` VALUES (18, 'Jack', 'L.a', '19292044', 0, NULL, '111111', NULL);
INSERT INTO `table_address` VALUES (24, '任凌云', '江西省南昌市红谷滩新区南昌大学前湖校区', '15182942983', 0, NULL, 'test111', NULL);

-- ----------------------------
-- Table structure for table_cart
-- ----------------------------
DROP TABLE IF EXISTS `table_cart`;
CREATE TABLE `table_cart`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '购物车商品id',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户id',
  `product_id` int(10) NULL DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_product_id_in_cart`(`product_id`) USING BTREE,
  INDEX `fk_user_id_in_cart`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_cart
-- ----------------------------

-- ----------------------------
-- Table structure for table_category
-- ----------------------------
DROP TABLE IF EXISTS `table_category`;
CREATE TABLE `table_category`  (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '大分类id',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大类名字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_category
-- ----------------------------
INSERT INTO `table_category` VALUES (1, '手机');
INSERT INTO `table_category` VALUES (2, '电脑');
INSERT INTO `table_category` VALUES (3, '书籍');
INSERT INTO `table_category` VALUES (4, '出行');
INSERT INTO `table_category` VALUES (5, '服饰');
INSERT INTO `table_category` VALUES (6, '鞋');
INSERT INTO `table_category` VALUES (7, '数码');
INSERT INTO `table_category` VALUES (8, '食品');
INSERT INTO `table_category` VALUES (9, '运动');
INSERT INTO `table_category` VALUES (10, '美妆');
INSERT INTO `table_category` VALUES (11, '分类11');
INSERT INTO `table_category` VALUES (12, '分类12');
INSERT INTO `table_category` VALUES (13, '分类13');
INSERT INTO `table_category` VALUES (14, '分类14');
INSERT INTO `table_category` VALUES (15, '分类15');
INSERT INTO `table_category` VALUES (16, '分类16');
INSERT INTO `table_category` VALUES (17, '分类17');
INSERT INTO `table_category` VALUES (18, '其他');

-- ----------------------------
-- Table structure for table_order
-- ----------------------------
DROP TABLE IF EXISTS `table_order`;
CREATE TABLE `table_order`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `product_id` int(10) NOT NULL COMMENT '商品id',
  `seller_id` int(10) NULL DEFAULT NULL COMMENT '卖家id',
  `address_id` int(10) NULL DEFAULT NULL COMMENT '收货地址id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `carry_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '订单总价格',
  `status` int(2) NULL DEFAULT NULL COMMENT '提交未付款0，付款未收货1，确认收货完成2，产生退货3，退货完成4',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '订单所属用户',
  `order_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号年月日时分秒+四位随机数',
  `created_time` timestamp(0) NULL DEFAULT 'now()' COMMENT '创建时间',
  `payment` int(2) NULL DEFAULT NULL COMMENT '平台积分0，支付宝1，微信2',
  `pay_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id_in_order`(`user_id`) USING BTREE,
  INDEX `fk_seller_id_in_order`(`seller_id`) USING BTREE,
  INDEX `fk_address_id_in_order`(`address_id`) USING BTREE,
  INDEX `fk_product_id_in_order`(`product_id`) USING BTREE,
  CONSTRAINT `fk_address_id_in_order` FOREIGN KEY (`address_id`) REFERENCES `table_address` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_id_in_order` FOREIGN KEY (`product_id`) REFERENCES `table_product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_seller_id_in_order` FOREIGN KEY (`seller_id`) REFERENCES `table_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id_in_order` FOREIGN KEY (`user_id`) REFERENCES `table_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_order
-- ----------------------------
INSERT INTO `table_order` VALUES (39, 4, 14, 24, 400.00, 0.00, 400.00, 3, 29, '202004300809317141', '2020-04-30 20:09:31', 0, NULL, NULL);

-- ----------------------------
-- Table structure for table_product
-- ----------------------------
DROP TABLE IF EXISTS `table_product`;
CREATE TABLE `table_product`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述，不要超过500个字',
  `images` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '九张图片的地址',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '原价格',
  `carry_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费',
  `user_id` int(10) NOT NULL COMMENT '用户id',
  `category_id` int(5) NOT NULL COMMENT '具体小类的分类id',
  `source_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发货地',
  `created_time` timestamp(0) NULL DEFAULT 'now()' COMMENT '发布时间',
  `status` int(1) NULL DEFAULT 1 COMMENT '0-sold,1-remained',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id_in_product`(`user_id`) USING BTREE,
  INDEX `fk_category_id_in_product`(`category_id`) USING BTREE,
  CONSTRAINT `fk_category_id_in_product` FOREIGN KEY (`category_id`) REFERENCES `table_category` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_id_in_product` FOREIGN KEY (`user_id`) REFERENCES `table_user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 276 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_product
-- ----------------------------
INSERT INTO `table_product` VALUES (4, '耐克 跑步鞋 全新', '刚买的跑步鞋 不合脚，还没穿的，需要的同学赶紧下单吧！', 'http://116.62.47.202/images/ab506fcc1ae24dd383ad7912c3cf6f47.jpg;http://116.62.47.202/images/78add594c9ce463fb7d525920450a3a9.jpg;http://116.62.47.202/images/b288e11705534bfca9400e5e0ffa59ca.jpg;http://116.62.47.202/images/de8106736fb241c7a7277b81b4521cc5.jpg;http://116.62.47.202/images/94c93a97e0224816826ffc7a2a12116d.jpg;', 400.00, 500.00, 0.00, 14, 6, '四川省南充市嘉陵区', '2020-04-30 18:57:34', 0);
INSERT INTO `table_product` VALUES (6, '圣诞树 圣诞礼物', '这是一个圣诞树 测试这是一个圣诞树 测试这是一个圣诞树 测试这是一个圣诞树 测试', 'http://116.62.47.202/images/e1e00c6f6e5a44eda25d15de5125431a.jpg;http://116.62.47.202/images/9775a9c57b5d4e9aa8ef7351459a7f4c.jpg;http://116.62.47.202/images/eedb77b383ac413eb90d27cc703e728a.jpg;http://116.62.47.202/images/21d18ea3e613453db9639962e3eade8a.jpg;http://116.62.47.202/images/266d8b7064174305aeebc42822555816.jpg;', 59.99, 100.00, 5.00, 1, 18, '四川省南充市嘉陵区', '2020-04-30 19:04:31', 1);
INSERT INTO `table_product` VALUES (8, '商品名称9', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (10, '商品名称11', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (12, '商品名称13', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (14, '商品名称15', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (16, '小商品', '农夫山泉瓶子', 'http://116.62.47.202/images/94b1fcef4a5e4daa854713b835ee51d5.jpg;', 10.00, 12.00, 0.00, 2, 18, '四川省南充市嘉陵区', '2020-04-29 00:24:43', 0);
INSERT INTO `table_product` VALUES (18, '商品名称19', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (20, '商品名称21', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (22, '商品名称23', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (24, '商品名称25', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (26, '商品名称27', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (28, '商品名称29', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (30, '商品名称31', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (32, '商品名称33', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (34, '商品名称35', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (36, '商品名称37', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (38, '商品名称39', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 0);
INSERT INTO `table_product` VALUES (40, '商品名称41', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (42, '商品名称43', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (44, '商品名称45', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (46, '商品名称47', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (48, '商品名称49', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (50, '商品名称51', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (52, '商品名称53', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (54, '商品名称55', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (56, '商品名称57', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (58, '商品名称59', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (60, '商品名称61', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (62, '商品名称63', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (64, '商品名称65', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (66, '商品名称67', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (68, '商品名称69', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (70, '商品名称71', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (72, '商品名称73', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (74, '商品名称75', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (76, '商品名称77', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (78, '商品名称79', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (80, '商品名称81', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (82, '商品名称83', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (84, '商品名称85', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (86, '商品名称87', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (88, '商品名称89', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (90, '商品名称91', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (92, '商品名称93', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (94, '商品名称95', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (96, '商品名称97', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (98, '商品名称99', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (100, '商品名称101', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (102, '商品名称103', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (104, '商品名称105', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (106, '商品名称107', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (108, '商品名称109', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (110, '商品名称111', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (112, '商品名称113', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (114, '商品名称115', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (116, '商品名称117', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (118, '商品名称119', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (120, '商品名称121', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (122, '商品名称123', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (124, '商品名称125', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (126, '商品名称127', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (128, '商品名称129', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (130, '商品名称131', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (132, '商品名称133', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (134, '商品名称135', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (136, '商品名称137', '描述', 'http://116.62.47.202/images/nikelogo.jpeg;http://116.62.47.202/images/lining.jpeg', 999.99, 1200.00, 5.00, 2, 1, 'nanchong', '2020-04-17 16:37:47', 1);
INSERT INTO `table_product` VALUES (268, '测试ipad', '自己用的ipad', 'https://s1.ax1x.com/2020/04/30/JLV0s0.th.jpg;', 2000.00, 3000.00, 0.00, 14, 7, '四川省南充市嘉陵区', '2020-04-25 18:26:35', 0);
INSERT INTO `table_product` VALUES (269, '小米手机', '价格测试', 'https://s1.ax1x.com/2020/04/30/JLZIcn.jpg;', 50.00, 60.00, 0.00, 1, 18, '四川省南充市嘉陵区', '2020-04-29 00:09:09', 0);
INSERT INTO `table_product` VALUES (270, '华为手机', '测试华为手机', 'https://s1.ax1x.com/2020/04/30/JLePHK.jpg;', 10.00, 12.00, 0.00, 2, 18, '四川省南充市嘉陵区', '2020-04-29 00:24:43', 1);
INSERT INTO `table_product` VALUES (275, '圣诞树 圣诞礼物', '这是一个圣诞树 测试这是一个圣诞树 测试这是一个圣诞树 测试这是一个圣诞树 测试', 'http://116.62.47.202/images/e1e00c6f6e5a44eda25d15de5125431a.jpg;http://116.62.47.202/images/9775a9c57b5d4e9aa8ef7351459a7f4c.jpg;http://116.62.47.202/images/eedb77b383ac413eb90d27cc703e728a.jpg;http://116.62.47.202/images/21d18ea3e613453db9639962e3eade8a.jpg;http://116.62.47.202/images/266d8b7064174305aeebc42822555816.jpg;', 59.99, 100.00, 5.00, 1, 18, '四川省南充市嘉陵区', '2020-04-30 19:04:31', 1);

-- ----------------------------
-- Table structure for table_refund
-- ----------------------------
DROP TABLE IF EXISTS `table_refund`;
CREATE TABLE `table_refund`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NULL DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货理由',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_refund
-- ----------------------------
INSERT INTO `table_refund` VALUES (1, 1, '买错了');
INSERT INTO `table_refund` VALUES (2, 26, '不合适');
INSERT INTO `table_refund` VALUES (5, 23, '。。。');
INSERT INTO `table_refund` VALUES (6, 39, '大小不合适。');

-- ----------------------------
-- Table structure for table_subcategory
-- ----------------------------
DROP TABLE IF EXISTS `table_subcategory`;
CREATE TABLE `table_subcategory`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '子类id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子类名字',
  `image` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '子类图片',
  `category_id` int(5) NULL DEFAULT NULL COMMENT '所属的大类id',
  `type` int(2) NULL DEFAULT NULL COMMENT '品牌类brand-0,特色类feature-1',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_category_id_in_subcategory`(`category_id`) USING BTREE,
  CONSTRAINT `fk_category_id_in_subcategory` FOREIGN KEY (`category_id`) REFERENCES `table_category` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_subcategory
-- ----------------------------
INSERT INTO `table_subcategory` VALUES (1, 'Apple', 'http://116.62.47.202/cateimg/smartphone/appple.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (2, 'Samsung', 'http://116.62.47.202/cateimg/smartphone/samsung.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (3, '小米', 'http://116.62.47.202/cateimg/smartphone/xiaomi.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (4, '华为', 'http://116.62.47.202/cateimg/smartphone/huawei.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (5, 'Vivo', 'http://116.62.47.202/cateimg/smartphone/vivo.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (6, 'OPPO', 'http://116.62.47.202/cateimg/smartphone/oppo.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (7, 'OnePlus', 'http://116.62.47.202/cateimg/smartphone/oneplus.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (8, '魅族', 'http://116.62.47.202/cateimg/smartphone/meizu.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (9, '荣耀', 'http://116.62.47.202/cateimg/smartphone/honnor.jpg', 1, 0);
INSERT INTO `table_subcategory` VALUES (10, '5G', 'http://116.62.47.202/cateimg/smartphone/5g.png', 1, 1);
INSERT INTO `table_subcategory` VALUES (11, '拍照', 'http://116.62.47.202/cateimg/smartphone/camera.png', 1, 1);
INSERT INTO `table_subcategory` VALUES (12, '全面屏', 'http://116.62.47.202/cateimg/smartphone/fullscreen.png', 1, 1);
INSERT INTO `table_subcategory` VALUES (13, '游戏手机', 'http://116.62.47.202/cateimg/smartphone/game.png', 1, 1);
INSERT INTO `table_subcategory` VALUES (14, '外星人', 'http://116.62.47.202/cateimg/computer/alienware.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (15, 'Apple', 'http://116.62.47.202/cateimg/computer/apple.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (16, '华硕', 'http://116.62.47.202/cateimg/computer/asus.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (17, '戴尔', 'http://116.62.47.202/cateimg/computer/dell.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (18, '游戏本', 'http://116.62.47.202/cateimg/computer/game.jpg', 2, 1);
INSERT INTO `table_subcategory` VALUES (19, '神州', 'http://116.62.47.202/cateimg/computer/hasee.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (20, '惠普', 'http://116.62.47.202/cateimg/computer/hp.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (21, '华为', 'http://116.62.47.202/cateimg/computer/huawei.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (22, '联想', 'http://116.62.47.202/cateimg/computer/lenovo.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (23, '微软', 'http://116.62.47.202/cateimg/computer/microsoft.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (24, '微星', 'http://116.62.47.202/cateimg/computer/msi.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (25, '台式机', 'http://116.62.47.202/cateimg/computer/pc.jpg', 2, 1);
INSERT INTO `table_subcategory` VALUES (26, '三星', 'http://116.62.47.202/cateimg/computer/samsung.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (27, '轻薄本', 'http://116.62.47.202/cateimg/computer/thin.jpg', 2, 1);
INSERT INTO `table_subcategory` VALUES (28, 'ThinkPad', 'http://116.62.47.202/cateimg/computer/thinkpad.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (29, '小米', 'http://116.62.47.202/cateimg/computer/xiaomi.jpg', 2, 0);
INSERT INTO `table_subcategory` VALUES (30, '考研英语', 'http://116.62.47.202/cateimg/books/english.png', 3, 1);
INSERT INTO `table_subcategory` VALUES (31, '何凯文', 'http://116.62.47.202/cateimg/books/hekaiwen.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (32, '李林', 'http://116.62.47.202/cateimg/books/lilin.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (33, '李永乐', 'http://116.62.47.202/cateimg/books/liyongle.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (34, '考研数学', 'http://116.62.47.202/cateimg/books/math.png', 3, 1);
INSERT INTO `table_subcategory` VALUES (35, '考研政治', 'http://116.62.47.202/cateimg/books/politic.png', 3, 1);
INSERT INTO `table_subcategory` VALUES (36, '汤家凤', 'http://116.62.47.202/cateimg/books/tangjiafeng.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (37, '王江涛', 'http://116.62.47.202/cateimg/books/wangjiangtao.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (38, '肖秀荣', 'http://116.62.47.202/cateimg/books/xiaoxiurong.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (39, '徐涛', 'http://116.62.47.202/cateimg/books/xutao.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (40, '张宇', 'http://116.62.47.202/cateimg/books/zhangyu.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (41, '朱伟', 'http://116.62.47.202/cateimg/books/zhuwei.jpg', 3, 0);
INSERT INTO `table_subcategory` VALUES (42, '短袖', 'http://116.62.47.202/cateimg/clothandshoes/shorttshirt.jpeg', 5, 1);
INSERT INTO `table_subcategory` VALUES (43, '毛衣', 'http://116.62.47.202/cateimg/clothandshoes/sweater.jpeg', 5, 1);
INSERT INTO `table_subcategory` VALUES (44, '卫衣', 'http://116.62.47.202/cateimg/clothandshoes/weiyi.jpeg', 5, 1);
INSERT INTO `table_subcategory` VALUES (45, '风衣', 'http://116.62.47.202/cateimg/clothandshoes/fengyi.jpeg', 5, 1);
INSERT INTO `table_subcategory` VALUES (46, '羽绒服', 'http://116.62.47.202/cateimg/clothandshoes/yurongfu.jpeg', 5, 1);
INSERT INTO `table_subcategory` VALUES (47, '耐克', 'http://116.62.47.202/cateimg/clothandshoes/nikelogo.jpeg', 5, 0);
INSERT INTO `table_subcategory` VALUES (48, '阿迪达斯', 'http://116.62.47.202/cateimg/clothandshoes/adidas.jpeg', 5, 0);
INSERT INTO `table_subcategory` VALUES (49, '新百伦', 'http://116.62.47.202/cateimg/clothandshoes/newbalance.jpeg', 5, 0);
INSERT INTO `table_subcategory` VALUES (50, '耐克', 'http://116.62.47.202/cateimg/clothandshoes/nikelogo.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (51, '阿迪达斯', 'http://116.62.47.202/cateimg/clothandshoes/adidas.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (52, 'New Balance', 'http://116.62.47.202/cateimg/clothandshoes/newbalance.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (53, 'Air Jordan', 'http://116.62.47.202/cateimg/clothandshoes/airjordan.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (54, '安踏', 'http://116.62.47.202/cateimg/clothandshoes/anta.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (55, '匡威', 'http://116.62.47.202/cateimg/clothandshoes/converse.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (56, '德尔惠', 'http://116.62.47.202/cateimg/clothandshoes/deerway.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (57, '鸿星尔克', 'http://116.62.47.202/cateimg/clothandshoes/erke.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (58, '杰克琼斯', 'http://116.62.47.202/cateimg/clothandshoes/jackjones.jpg', 5, 0);
INSERT INTO `table_subcategory` VALUES (59, '海澜之家', 'http://116.62.47.202/cateimg/clothandshoes/hla.jpg', 5, 0);
INSERT INTO `table_subcategory` VALUES (60, 'Kappa', 'http://116.62.47.202/cateimg/clothandshoes/kappa.jpg', 5, 0);
INSERT INTO `table_subcategory` VALUES (61, '劲霸男装', 'http://116.62.47.202/cateimg/clothandshoes/kboxing.jpg', 5, 0);
INSERT INTO `table_subcategory` VALUES (62, '李宁', 'http://116.62.47.202/cateimg/clothandshoes/lining.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (63, '匹克', 'http://116.62.47.202/cateimg/clothandshoes/peak.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (64, 'Puma', 'http://116.62.47.202/cateimg/clothandshoes/puma.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (65, '乔丹', 'http://116.62.47.202/cateimg/clothandshoes/qiaodan.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (66, '特步', 'http://116.62.47.202/cateimg/clothandshoes/tebu.jpeg', 6, 0);
INSERT INTO `table_subcategory` VALUES (67, 'Vans', 'http://116.62.47.202/cateimg/clothandshoes/vans.jpg', 6, 0);
INSERT INTO `table_subcategory` VALUES (68, '滑板车', 'http://116.62.47.202/cateimg/traffic/Scooter.jpg', 4, 1);
INSERT INTO `table_subcategory` VALUES (69, '爱玛', 'http://116.62.47.202/cateimg/traffic/aima.jpg', 4, 0);
INSERT INTO `table_subcategory` VALUES (70, '自行车', 'http://116.62.47.202/cateimg/traffic/bike.jpg', 4, 1);
INSERT INTO `table_subcategory` VALUES (71, '电动车', 'http://116.62.47.202/cateimg/traffic/ebike.jpg', 4, 1);
INSERT INTO `table_subcategory` VALUES (72, '小牛', 'http://116.62.47.202/cateimg/traffic/xiaoniu.jpg', 4, 0);
INSERT INTO `table_subcategory` VALUES (73, '雅迪', 'http://116.62.47.202/cateimg/traffic/yadi.jpg', 4, 0);
INSERT INTO `table_subcategory` VALUES (74, 'iPad', NULL, NULL, NULL);
INSERT INTO `table_subcategory` VALUES (75, 'iPad', 'http://116.62.47.202/cateimg/digital/ipad.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (76, '手环', 'http://116.62.47.202/cateimg/digital/band.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (77, '单反', 'http://116.62.47.202/cateimg/digital/danfan.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (78, '耳机', 'http://116.62.47.202/cateimg/digital/headphone.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (79, '键盘', 'http://116.62.47.202/cateimg/digital/keyboard.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (80, '鼠标', 'http://116.62.47.202/cateimg/digital/mouse.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (81, '路由器', 'http://116.62.47.202/cateimg/digital/router.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (82, '运动相机', 'http://116.62.47.202/cateimg/digital/sportscamera.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (83, '智能手表', 'http://116.62.47.202/cateimg/digital/watch.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (84, '微单', 'http://116.62.47.202/cateimg/digital/weidan.jpg', 7, 1);
INSERT INTO `table_subcategory` VALUES (85, '饮料', 'http://116.62.47.202/cateimg/food/drink.jpg', 8, 1);
INSERT INTO `table_subcategory` VALUES (86, '水果', 'http://116.62.47.202/cateimg/food/fruit.jpg', 8, 1);
INSERT INTO `table_subcategory` VALUES (87, '牛奶', 'http://116.62.47.202/cateimg/food/milk.jpg', 8, 1);
INSERT INTO `table_subcategory` VALUES (88, '坚果', 'http://116.62.47.202/cateimg/food/nuts.jpg', 8, 1);
INSERT INTO `table_subcategory` VALUES (89, '篮球', 'http://116.62.47.202/cateimg/sport/basketball.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (90, '垫子', 'http://116.62.47.202/cateimg/sport/dianzi.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (91, '足球', 'http://116.62.47.202/cateimg/sport/football.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (92, '乒乓', 'http://116.62.47.202/cateimg/sport/tabletenis.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (93, '排球', 'http://116.62.47.202/cateimg/sport/volleyball.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (94, '哑铃', 'http://116.62.47.202/cateimg/sport/yaling.jpg', 9, 1);
INSERT INTO `table_subcategory` VALUES (95, '迪奥', 'http://116.62.47.202/cateimg/makeup/Dior.jpg', 10, 0);
INSERT INTO `table_subcategory` VALUES (96, '卡姿兰', 'http://116.62.47.202/cateimg/makeup/cazilan.jpg', 10, 0);
INSERT INTO `table_subcategory` VALUES (97, '防晒', 'http://116.62.47.202/cateimg/makeup/fangshai.jpg', 10, 1);
INSERT INTO `table_subcategory` VALUES (98, '口红', 'http://116.62.47.202/cateimg/makeup/kouhong.jpg', 10, 1);
INSERT INTO `table_subcategory` VALUES (99, '玉兰油', 'http://116.62.47.202/cateimg/makeup/olay.jpg', 10, 0);
INSERT INTO `table_subcategory` VALUES (100, '欧莱雅', 'http://116.62.47.202/cateimg/makeup/oulaiya.jpg', 10, 0);
INSERT INTO `table_subcategory` VALUES (101, 'SK-II', 'http://116.62.47.202/cateimg/makeup/skii.jpg', 10, 0);
INSERT INTO `table_subcategory` VALUES (102, '香水', 'http://116.62.47.202/cateimg/makeup/xaingshui.jpg', 10, 1);
INSERT INTO `table_subcategory` VALUES (103, '洗面奶', 'http://116.62.47.202/cateimg/makeup/ximainnai.jpg', 10, 1);
INSERT INTO `table_subcategory` VALUES (104, '眼霜', 'http://116.62.47.202/cateimg/makeup/yanshuang.jpg', 10, 1);

-- ----------------------------
-- Table structure for table_user
-- ----------------------------
DROP TABLE IF EXISTS `table_user`;
CREATE TABLE `table_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称，注册时系统随机生成，之后可修改',
  `gender` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '男，女，保密',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `register_time` timestamp(0) NULL DEFAULT 'now()' COMMENT '注册时间',
  `phone` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `college` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校',
  `credit` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '积分额度，1积分=1元',
  `is_admin` int(1) NULL DEFAULT 0 COMMENT '0-no,1yes',
  `is_member` int(1) NULL DEFAULT 0 COMMENT '0-no,1yes',
  `is_ncu` int(1) NULL DEFAULT 0 COMMENT '0-no,1yes',
  `is_badge` int(1) NULL DEFAULT 0 COMMENT '0-no,1yes',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_user
-- ----------------------------
INSERT INTO `table_user` VALUES (1, '111111', 'E10ADC3949BA59ABBE56E057F20F883E', '1111', '1111', '2020-04-16', '2020-04-16 21:30:40', '1111', 'http://116.62.47.202/images/snoopy.jpg', '1111', 10050.00, 1, 1, 1, 1);
INSERT INTO `table_user` VALUES (2, '222222', 'E10ADC3949BA59ABBE56E057F20F883E', '2222', '2222', '2020-04-16', '2020-04-16 21:35:59', '15182942983', 'http://116.62.47.202/images/snooy.png', '2222', 10954.99, 0, 0, 0, 0);
INSERT INTO `table_user` VALUES (3, 'myaccount', 'mypassword', NULL, NULL, NULL, '2020-04-20 16:52:11', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (4, '1111111', '1111111', NULL, NULL, NULL, '2020-04-20 16:59:44', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (10, '1518294', '0000000', NULL, NULL, NULL, '2020-04-20 17:35:28', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (11, '15182941', '0000000', NULL, NULL, NULL, '2020-04-20 17:45:56', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (12, '1518294111', '00000002', NULL, NULL, NULL, '2020-04-20 17:46:04', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (13, '666666', 'F379EAF3C831B04DE153469D1BEC345E', NULL, NULL, NULL, '2020-04-20 18:01:28', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (14, '777777', 'F63F4FBC9F8C85D409F2F59F2B9E12D5', '如此美好', '女', '1997-09-01', '2020-04-20 18:02:22', '15182942983', 'http://116.62.47.202/avatars/10da3c0f68464a318c6ad9a58c330550.jpg', '南昌大学', -504.99, 1, 1, 1, 1);
INSERT INTO `table_user` VALUES (15, '12345678', '96E79218965EB72C92A549DD5A330112', NULL, NULL, NULL, '2020-04-20 18:02:56', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (16, '838386', '96E79218965EB72C92A549DD5A330112', NULL, NULL, NULL, '2020-04-20 18:03:46', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (17, '15182942983', '025E5E057B5551F7E11D686D9F36032B', NULL, NULL, NULL, '2020-04-20 18:19:57', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (18, '147852', '83B4EF5AE4BB360C96628AECDA974200', '随机', '', NULL, '2020-04-20 18:35:18', NULL, NULL, NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (19, '6130116071', 'E10ADC3949BA59ABBE56E057F20F883E', '用户_er31082', NULL, NULL, '2020-04-21 19:25:10', NULL, 'http://116.62.47.202/avatars/7813d4b616524c5eaf00eab2ffc11dd4.jpg', NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (20, '1aaaaa', 'E10ADC3949BA59ABBE56E057F20F883E', '用户_ershou29906', NULL, NULL, '2020-04-21 21:21:39', NULL, 'http://116.62.47.202/avatars/my/da3.jpg', NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (21, 'ekekdkd', '7FA8282AD93047A4D6FE6111C93B308A', '用户_ershou36295', NULL, NULL, '2020-04-21 21:22:42', NULL, 'http://116.62.47.202/avatars/my/da4.png', NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (22, '123456a', '96E79218965EB72C92A549DD5A330112', '用户_ershou40469', NULL, NULL, '2020-04-21 21:23:24', NULL, 'http://116.62.47.202/avatars/my/da2.jpg', NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (23, 'bsjsjsjjdhj', '96E79218965EB72C92A549DD5A330112', '用户_ershou43064', NULL, NULL, '2020-04-21 21:23:50', NULL, 'http://116.62.47.202/avatars/my/da4.png', NULL, 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (24, 'mymymy', '96E79218965EB72C92A549DD5A330112', '用户_ershou89835', '男', '1997-09-01', '2020-04-21 21:31:38', '15182942983', 'http://116.62.47.202/avatars/my/da1.jpg', '南昌大学', 0.00, 0, NULL, NULL, NULL);
INSERT INTO `table_user` VALUES (25, '1', '1', '1', NULL, NULL, '2020-04-21 21:44:26', NULL, NULL, NULL, 0.00, 0, 0, 0, 0);
INSERT INTO `table_user` VALUES (26, '2aaaaaa', '96E79218965EB72C92A549DD5A330112', 'Prayerling', '保密', '1997-09-01', '2020-04-21 23:21:38', '15182942983', 'http://116.62.47.202/avatars/2b4b112a32dc414e821505bf39f3c1dd.jpg', '南昌大学', 0.00, 0, 0, 0, 0);
INSERT INTO `table_user` VALUES (27, '1122333', 'E50D9B8F32678FDA664D7B9EF3F6702B', '南大用户161', '男', '2019-08-15', '2020-04-25 02:25:35', '15182942983', 'http://116.62.47.202/avatars/3a4b4ca2341949aca068a784465131e8.jpg', '南昌大学', 10233.50, 0, 0, 0, 0);
INSERT INTO `table_user` VALUES (28, 'uvuvuguc', 'E10ADC3949BA59ABBE56E057F20F883E', '用户_ershou78590', NULL, NULL, '2020-04-27 05:33:05', NULL, 'http://116.62.47.202/avatars/my/da4.png', NULL, 0.00, 0, 0, 0, 0);
INSERT INTO `table_user` VALUES (29, 'test111', '96E79218965EB72C92A549DD5A330112', '南大用户111', '男', '2016-09-01', '2020-04-30 19:16:51', '15182942983', 'http://116.62.47.202/avatars/8c2ccfabbcc84b18a4047d917ac69f89.jpg', '南昌大学', -400.00, 0, 0, 0, 0);

SET FOREIGN_KEY_CHECKS = 1;
