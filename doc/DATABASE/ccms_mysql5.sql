/*
 Navicat Premium Data Transfer

 Source Server         : MySQL5.5
 Source Server Type    : MySQL
 Source Server Version : 50562 (5.5.62)
 Source Host           : localhost:3306
 Source Schema         : ccms

 Target Server Type    : MySQL
 Target Server Version : 50562 (5.5.62)
 File Encoding         : 65001

 Date: 01/08/2023 19:46:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account`  (
                              `id` int(10) UNSIGNED NOT NULL,
                              `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `state` enum('normal','canceled','frozen') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'normal',
                              `role` enum('admin','user','pos') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                              `department` int(10) UNSIGNED NOT NULL,
                              `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `un_email`(`email`) USING BTREE,
                              INDEX `department_REF_department_id`(`department`) USING BTREE,
                              CONSTRAINT `department_REF_department_id` FOREIGN KEY (`department`) REFERENCES `t_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
                            `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                            `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
                            `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
                            `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件地址',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表，存储管理员登陆信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_card
-- ----------------------------
DROP TABLE IF EXISTS `t_card`;
CREATE TABLE `t_card`  (
                           `id` bigint(20) UNSIGNED NOT NULL,
                           `balance` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '余额, 单位是分',
                           `state` enum('normal','loss','canceled') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '卡状态: 正常/挂失',
                           `account` int(10) UNSIGNED NOT NULL,
                           `version` int(10) UNSIGNED NOT NULL,
                           PRIMARY KEY (`id`) USING BTREE,
                           UNIQUE INDEX `uni_account`(`account`) USING BTREE,
                           CONSTRAINT `t_card_t_account_id_fk` FOREIGN KEY (`account`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '一卡通表，外键关联 user 表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_consume
-- ----------------------------
DROP TABLE IF EXISTS `t_consume`;
CREATE TABLE `t_consume`  (
                              `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `createTime` datetime NOT NULL COMMENT '日期时间',
                              `price` mediumint(8) UNSIGNED NOT NULL COMMENT '消费金额',
                              `user` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
                              `type` enum('WATER','ELECTRIC','MEAL_EXPENSE','REFUND','PAYMENT','OTHER','RECHARGE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消费类型\n\r\n水费 WATER,\r\n电费  ELECTRIC,\r\n餐费 MEAL_EXPENSE,\r\n缴费 PAYMENT,\r\n充值 RECHARGE,\r\n退款 REFUND,\r\n其他 OTHER',
                              `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消费信息',
                              `pos` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '刷卡机ID',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消费表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_consume_statistic
-- ----------------------------
DROP TABLE IF EXISTS `t_consume_statistic`;
CREATE TABLE `t_consume_statistic`  (
                                        `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                        `consume_type` enum('WATER','ELECTRIC','MEAL_EXPENSE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消费类型',
                                        `price` int(10) UNSIGNED NOT NULL COMMENT '消费统计金额',
                                        `date` date NOT NULL COMMENT '日期',
                                        `count` int(10) UNSIGNED NOT NULL COMMENT '消费统计次数',
                                        `type` enum('ALL','GROUP') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '统计类型',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消费统计表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
                                 `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `parent` int(10) UNSIGNED NOT NULL,
                                 `addition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                 `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                                 `type` enum('root','middle','leaf') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 PRIMARY KEY (`id`) USING BTREE,
                                 INDEX `t_department_parent_index`(`parent`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 273 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
                              `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                              `author` int(10) UNSIGNED NOT NULL COMMENT '发布者',
                              `create_time` bigint(20) UNSIGNED NOT NULL COMMENT '日期时间',
                              `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
                             `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                             `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
                             `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
                             `author` int(10) UNSIGNED NOT NULL COMMENT '发布者',
                             `create_time` datetime NOT NULL COMMENT '日期时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_open_platform
-- ----------------------------
DROP TABLE IF EXISTS `t_open_platform`;
CREATE TABLE `t_open_platform`  (
                                    `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                                    `account` int(10) UNSIGNED NOT NULL COMMENT '用户id',
                                    `type` enum('gitee','dingtalk','alipay') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台类型',
                                    `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '平台UID',
                                    PRIMARY KEY (`id`) USING BTREE,
                                    UNIQUE INDEX `unique_user_type`(`account`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '开放平台' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_pay`;
CREATE TABLE `t_pay`  (
                          `id` bigint(10) UNSIGNED NOT NULL,
                          `user` int(10) UNSIGNED NOT NULL,
                          `amount` int(10) UNSIGNED NOT NULL,
                          `tradeNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝交易号',
                          `type` enum('system','alipay') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                          `createTime` datetime NOT NULL,
                          `done` tinyint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否完成支付',
                          `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '信息',
                          `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付详细信息',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '充值表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_payment
-- ----------------------------
DROP TABLE IF EXISTS `t_payment`;
CREATE TABLE `t_payment`  (
                              `id` bigint(10) UNSIGNED NOT NULL COMMENT 'PK:雪花算法生成ID',
                              `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
                              `type` enum('DORMITORY_FEE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '缴费类型 \r\nDORMITORY_FEE 住宿费,\r\nOTHER 其他',
                              `price` int(10) UNSIGNED NOT NULL COMMENT '金额,分为单位',
                              `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
                              `author` int(10) UNSIGNED NOT NULL COMMENT '发布者管理员ID',
                              `createTime` datetime NULL DEFAULT NULL COMMENT '缴费时间',
                              `startTime` datetime NOT NULL COMMENT '开始缴费时间',
                              `endTime` datetime NOT NULL COMMENT '结束缴费时间',
                              `state` enum('NOT_START','IN_PROGRESS','END','CANCEL','PAUSE') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '缴费项目状态\r\n未开始 NOT_START \r\n进行中 IN_PROGRESS \r\n已结束 END \r\n已取消 CANCEL \r\n已暂停 PAUSE',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '缴费项目表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_pos
-- ----------------------------
DROP TABLE IF EXISTS `t_pos`;
CREATE TABLE `t_pos`  (
                          `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                          `account` int(10) UNSIGNED NOT NULL COMMENT '账户',
                          `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '刷卡机名',
                          `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在地址',
                          `type` enum('WATER','ELECTRIC','MEAL_EXPENSE','ACCOMMODATION_FEE','RECHARGE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消费类型\n\n水费 WATER,\n电费 ELECTRIC,\n餐费 MEAL_EXPENSE,\n住宿费 ACCOMMODATION_FEE,\n充值 RECHARGE,\n其他 OTHER',
                          `store` int(10) UNSIGNED NOT NULL COMMENT '商户ID',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `fk_store_REF_store_id`(`store`) USING BTREE,
                          INDEX `fk_account_REF_account_id`(`account`) USING BTREE,
                          CONSTRAINT `fk_account_REF_account_id` FOREIGN KEY (`account`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '刷卡机表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record`  (
                             `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
                             `payment` bigint(10) UNSIGNED NOT NULL COMMENT 'UK:缴费项目ID',
                             `user` int(10) UNSIGNED NOT NULL COMMENT 'UK:缴费用户ID',
                             `state` enum('PAID','UNPAID','REFUNDED') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'UNPAID' COMMENT '已支付 PAID,\r\n未支付 UNPAID,\r\n已退款 REFUNDED',
                             `createTime` datetime NULL DEFAULT NULL COMMENT '支付时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `UK_payment_user`(`payment`, `user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '缴费表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_trade
-- ----------------------------
DROP TABLE IF EXISTS `t_trade`;
CREATE TABLE `t_trade`  (
                            `id` bigint(20) UNSIGNED NOT NULL,
                            `account` int(10) UNSIGNED NOT NULL,
                            `level1` enum('in','out') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `level2` enum('card','system','alipay') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `level3` enum('consume','payment','refund','recharge','withdraw','other') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `state` enum('success','fail','processing','refund') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `out_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                            `in_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                            `amount` mediumint(9) NOT NULL,
                            `create_time` datetime NOT NULL,
                            `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
                            `version` int(10) UNSIGNED NOT NULL,
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                           `id` bigint(20) UNSIGNED NOT NULL,
                           `account` int(10) UNSIGNED NOT NULL,
                           `card` bigint(20) UNSIGNED NOT NULL,
                           `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `fk_account_REF_acount_id`(`account`) USING BTREE,
                           INDEX `fk_card_REF_card_id`(`card`) USING BTREE,
                           CONSTRAINT `fk_account_REF_acount_id` FOREIGN KEY (`account`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `fk_card_REF_card_id` FOREIGN KEY (`card`) REFERENCES `t_card` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
