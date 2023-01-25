/*
 Navicat Premium Data Transfer

 Source Server         : mysql5
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : ccms

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 25/01/2023 18:06:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表，存储管理员登陆信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES (1, 'admin', '1');

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '区域表，用于存储区域信息，是商户store的父层' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES (1, '一期', '一期区域');
INSERT INTO `t_area` VALUES (2, '二期', '二期区域');
INSERT INTO `t_area` VALUES (3, '三期', '三期区域');
INSERT INTO `t_area` VALUES (4, '西门商业街', '西门商业街区域');
INSERT INTO `t_area` VALUES (5, '测试A', '测试A');
INSERT INTO `t_area` VALUES (6, '测试B', '测试B');
INSERT INTO `t_area` VALUES (7, '测试C', '测试CC');
INSERT INTO `t_area` VALUES (8, '测试', '测试2');

-- ----------------------------
-- Table structure for t_card
-- ----------------------------
DROP TABLE IF EXISTS `t_card`;
CREATE TABLE `t_card`  (
  `user` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'PK; 学号; FK:t_student.id',
  `balance` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '余额, 单位是分',
  `lock` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '卡状态: 正常/锁定 挂失',
  PRIMARY KEY (`user`) USING BTREE,
  UNIQUE INDEX `FK_student_REF_card`(`user`) USING BTREE,
  CONSTRAINT `t_card_t_student_id_fk` FOREIGN KEY (`user`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '一卡通表，外键关联 user 表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_card
-- ----------------------------
INSERT INTO `t_card` VALUES ('1', 290, 0);
INSERT INTO `t_card` VALUES ('1432101149', 10150199, 0);
INSERT INTO `t_card` VALUES ('1632101188', 0, 0);
INSERT INTO `t_card` VALUES ('1932101111', 9999999, 0);
INSERT INTO `t_card` VALUES ('1932101122', 9999999, 0);
INSERT INTO `t_card` VALUES ('1932101133', 10269999, 0);
INSERT INTO `t_card` VALUES ('1932101141', 9999999, 0);
INSERT INTO `t_card` VALUES ('1932101143', 9999999, 0);
INSERT INTO `t_card` VALUES ('1932101144', 9999999, 0);
INSERT INTO `t_card` VALUES ('1932101149', 9981999, 0);
INSERT INTO `t_card` VALUES ('1932121149', 9999999, 0);

-- ----------------------------
-- Table structure for t_clazz
-- ----------------------------
DROP TABLE IF EXISTS `t_clazz`;
CREATE TABLE `t_clazz`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `specialty` int(10) UNSIGNED NOT NULL COMMENT 'FK:所属专业ID',
  `grade` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '年级(如2022级)',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clazz_REF_specialty`(`specialty`) USING BTREE,
  CONSTRAINT `clazz_REF_specialty` FOREIGN KEY (`specialty`) REFERENCES `t_specialty` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 376 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '班级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_clazz
-- ----------------------------
INSERT INTO `t_clazz` VALUES (1, '20计应1班', 1, '2020', '这是20计应1班哦~~');
INSERT INTO `t_clazz` VALUES (2, '20通信2班', 2, '2020', '这是20通信2班哦~~');
INSERT INTO `t_clazz` VALUES (3, '20计应2班', 1, '2020', '这是20计应2班哦~~');
INSERT INTO `t_clazz` VALUES (4, '20通信1班', 2, '2020', '这是20通信1班哦~~');
INSERT INTO `t_clazz` VALUES (5, '21软工1班', 3, '2021', '这是21软工1班哦~~');
INSERT INTO `t_clazz` VALUES (6, '21软工2班', 3, '2021', '这是21软工2班哦~~');
INSERT INTO `t_clazz` VALUES (7, '21无人驾驶1班', 4, '2021', '这是21无人驾驶1班哦~~');
INSERT INTO `t_clazz` VALUES (8, '21无人驾驶2班', 4, '2021', '这是21无人驾驶2班哦~~');
INSERT INTO `t_clazz` VALUES (135, '计算机应用技术2020 1班', 1, '2020', '这是计算机应用技术2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (136, '通信工程2020 1班', 2, '2020', '这是通信工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (137, '软件工程2020 1班', 3, '2020', '这是软件工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (138, '无人驾驶技术2020 1班', 4, '2020', '这是无人驾驶技术2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (139, '计算机科学与技术2020 1班', 5, '2020', '这是计算机科学与技术2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (140, '网络工程2020 1班', 6, '2020', '这是网络工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (141, '数字媒体技术2020 1班', 8, '2020', '这是数字媒体技术2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (142, '数据科学与大数据技术2020 1班', 9, '2020', '这是数据科学与大数据技术2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (143, '人工智能2020 1班', 10, '2020', '这是人工智能2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (144, '电子通信工程2020 1班', 11, '2020', '这是电子通信工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (145, '通信工程2020 1班', 12, '2020', '这是通信工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (146, '物联网工程2020 1班', 13, '2020', '这是物联网工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (147, '机械设计制造及其自动化2020 1班', 14, '2020', '这是机械设计制造及其自动化2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (148, '自动化2020 1班', 15, '2020', '这是自动化2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (149, '电气工程及其自动化2020 1班', 16, '2020', '这是电气工程及其自动化2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (150, '机器人工程2020 1班', 17, '2020', '这是机器人工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (151, '土木工程2020 1班', 18, '2020', '这是土木工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (152, '安全工程2020 1班', 19, '2020', '这是安全工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (153, '工程管理2020 1班', 20, '2020', '这是工程管理2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (154, '建筑学2020 1班', 21, '2020', '这是建筑学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (155, '风景园林2020 1班', 22, '2020', '这是风景园林2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (156, '测绘工程2020 1班', 23, '2020', '这是测绘工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (157, '药学2020 1班', 24, '2020', '这是药学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (158, '制药工程2020 1班', 25, '2020', '这是制药工程2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (159, '药物制剂2020 1班', 26, '2020', '这是药物制剂2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (160, '健康服务与管理2020 1班', 27, '2020', '这是健康服务与管理2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (161, '财务管理2020 1班', 28, '2020', '这是财务管理2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (162, '会计学2020 1班', 29, '2020', '这是会计学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (163, '经济与金融2020 1班', 30, '2020', '这是经济与金融2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (164, '审计学2020 1班', 31, '2020', '这是审计学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (165, '金融科技2020 1班', 32, '2020', '这是金融科技2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (166, '国际经济与贸易2020 1班', 33, '2020', '这是国际经济与贸易2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (167, '人力资源管理2020 1班', 34, '2020', '这是人力资源管理2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (168, '物流管理2020 1班', 35, '2020', '这是物流管理2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (169, '电子商务2020 1班', 36, '2020', '这是电子商务2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (170, '大数据管理与应用2020 1班', 37, '2020', '这是大数据管理与应用2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (171, '数字经济2020 1班', 38, '2020', '这是数字经济2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (172, '视觉传达设计2020 1班', 39, '2020', '这是视觉传达设计2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (173, '环境设计2020 1班', 40, '2020', '这是环境设计2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (174, '动画2020 1班', 41, '2020', '这是动画2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (175, '美术学2020 1班', 42, '2020', '这是美术学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (176, '汉语言文学2020 1班', 43, '2020', '这是汉语言文学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (177, '新闻学2020 1班', 44, '2020', '这是新闻学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (178, '广告学2020 1班', 45, '2020', '这是广告学2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (179, '广播电视编导2020 1班', 46, '2020', '这是广播电视编导2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (180, '表演2020 1班', 47, '2020', '这是表演2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (181, '学前教育2020 1班', 48, '2020', '这是学前教育2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (182, '小学教育2020 1班', 49, '2020', '这是小学教育2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (183, '英语2020 1班', 50, '2020', '这是英语2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (184, '商务英语2020 1班', 51, '2020', '这是商务英语2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (185, '日语2020 1班', 52, '2020', '这是日语2020 1班哦~~');
INSERT INTO `t_clazz` VALUES (198, '计算机应用技术2020 2班', 1, '2020', '这是计算机应用技术2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (199, '通信工程2020 2班', 2, '2020', '这是通信工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (200, '软件工程2020 2班', 3, '2020', '这是软件工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (201, '无人驾驶技术2020 2班', 4, '2020', '这是无人驾驶技术2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (202, '计算机科学与技术2020 2班', 5, '2020', '这是计算机科学与技术2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (203, '网络工程2020 2班', 6, '2020', '这是网络工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (204, '数字媒体技术2020 2班', 8, '2020', '这是数字媒体技术2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (205, '数据科学与大数据技术2020 2班', 9, '2020', '这是数据科学与大数据技术2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (206, '人工智能2020 2班', 10, '2020', '这是人工智能2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (207, '电子通信工程2020 2班', 11, '2020', '这是电子通信工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (208, '通信工程2020 2班', 12, '2020', '这是通信工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (209, '物联网工程2020 2班', 13, '2020', '这是物联网工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (210, '机械设计制造及其自动化2020 2班', 14, '2020', '这是机械设计制造及其自动化2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (211, '自动化2020 2班', 15, '2020', '这是自动化2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (212, '电气工程及其自动化2020 2班', 16, '2020', '这是电气工程及其自动化2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (213, '机器人工程2020 2班', 17, '2020', '这是机器人工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (214, '土木工程2020 2班', 18, '2020', '这是土木工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (215, '安全工程2020 2班', 19, '2020', '这是安全工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (216, '工程管理2020 2班', 20, '2020', '这是工程管理2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (217, '建筑学2020 2班', 21, '2020', '这是建筑学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (218, '风景园林2020 2班', 22, '2020', '这是风景园林2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (219, '测绘工程2020 2班', 23, '2020', '这是测绘工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (220, '药学2020 2班', 24, '2020', '这是药学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (221, '制药工程2020 2班', 25, '2020', '这是制药工程2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (222, '药物制剂2020 2班', 26, '2020', '这是药物制剂2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (223, '健康服务与管理2020 2班', 27, '2020', '这是健康服务与管理2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (224, '财务管理2020 2班', 28, '2020', '这是财务管理2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (225, '会计学2020 2班', 29, '2020', '这是会计学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (226, '经济与金融2020 2班', 30, '2020', '这是经济与金融2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (227, '审计学2020 2班', 31, '2020', '这是审计学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (228, '金融科技2020 2班', 32, '2020', '这是金融科技2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (229, '国际经济与贸易2020 2班', 33, '2020', '这是国际经济与贸易2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (230, '人力资源管理2020 2班', 34, '2020', '这是人力资源管理2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (231, '物流管理2020 2班', 35, '2020', '这是物流管理2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (232, '电子商务2020 2班', 36, '2020', '这是电子商务2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (233, '大数据管理与应用2020 2班', 37, '2020', '这是大数据管理与应用2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (234, '数字经济2020 2班', 38, '2020', '这是数字经济2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (235, '视觉传达设计2020 2班', 39, '2020', '这是视觉传达设计2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (236, '环境设计2020 2班', 40, '2020', '这是环境设计2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (237, '动画2020 2班', 41, '2020', '这是动画2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (238, '美术学2020 2班', 42, '2020', '这是美术学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (239, '汉语言文学2020 2班', 43, '2020', '这是汉语言文学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (240, '新闻学2020 2班', 44, '2020', '这是新闻学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (241, '广告学2020 2班', 45, '2020', '这是广告学2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (242, '广播电视编导2020 2班', 46, '2020', '这是广播电视编导2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (243, '表演2020 2班', 47, '2020', '这是表演2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (244, '学前教育2020 2班', 48, '2020', '这是学前教育2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (245, '小学教育2020 2班', 49, '2020', '这是小学教育2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (246, '英语2020 2班', 50, '2020', '这是英语2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (247, '商务英语2020 2班', 51, '2020', '这是商务英语2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (248, '日语2020 2班', 52, '2020', '这是日语2020 2班哦~~');
INSERT INTO `t_clazz` VALUES (261, '计算机应用技术2021 1班', 1, '2021', '这是计算机应用技术2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (262, '通信工程2021 1班', 2, '2021', '这是通信工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (263, '软件工程2021 1班', 3, '2021', '这是软件工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (264, '无人驾驶技术2021 1班', 4, '2021', '这是无人驾驶技术2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (265, '计算机科学与技术2021 1班', 5, '2021', '这是计算机科学与技术2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (266, '网络工程2021 1班', 6, '2021', '这是网络工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (267, '数字媒体技术2021 1班', 8, '2021', '这是数字媒体技术2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (268, '数据科学与大数据技术2021 1班', 9, '2021', '这是数据科学与大数据技术2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (269, '人工智能2021 1班', 10, '2021', '这是人工智能2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (270, '电子通信工程2021 1班', 11, '2021', '这是电子通信工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (271, '通信工程2021 1班', 12, '2021', '这是通信工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (272, '物联网工程2021 1班', 13, '2021', '这是物联网工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (273, '机械设计制造及其自动化2021 1班', 14, '2021', '这是机械设计制造及其自动化2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (274, '自动化2021 1班', 15, '2021', '这是自动化2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (275, '电气工程及其自动化2021 1班', 16, '2021', '这是电气工程及其自动化2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (276, '机器人工程2021 1班', 17, '2021', '这是机器人工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (277, '土木工程2021 1班', 18, '2021', '这是土木工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (278, '安全工程2021 1班', 19, '2021', '这是安全工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (279, '工程管理2021 1班', 20, '2021', '这是工程管理2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (280, '建筑学2021 1班', 21, '2021', '这是建筑学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (281, '风景园林2021 1班', 22, '2021', '这是风景园林2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (282, '测绘工程2021 1班', 23, '2021', '这是测绘工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (283, '药学2021 1班', 24, '2021', '这是药学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (284, '制药工程2021 1班', 25, '2021', '这是制药工程2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (285, '药物制剂2021 1班', 26, '2021', '这是药物制剂2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (286, '健康服务与管理2021 1班', 27, '2021', '这是健康服务与管理2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (287, '财务管理2021 1班', 28, '2021', '这是财务管理2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (288, '会计学2021 1班', 29, '2021', '这是会计学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (289, '经济与金融2021 1班', 30, '2021', '这是经济与金融2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (290, '审计学2021 1班', 31, '2021', '这是审计学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (291, '金融科技2021 1班', 32, '2021', '这是金融科技2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (292, '国际经济与贸易2021 1班', 33, '2021', '这是国际经济与贸易2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (293, '人力资源管理2021 1班', 34, '2021', '这是人力资源管理2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (294, '物流管理2021 1班', 35, '2021', '这是物流管理2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (295, '电子商务2021 1班', 36, '2021', '这是电子商务2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (296, '大数据管理与应用2021 1班', 37, '2021', '这是大数据管理与应用2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (297, '数字经济2021 1班', 38, '2021', '这是数字经济2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (298, '视觉传达设计2021 1班', 39, '2021', '这是视觉传达设计2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (299, '环境设计2021 1班', 40, '2021', '这是环境设计2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (300, '动画2021 1班', 41, '2021', '这是动画2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (301, '美术学2021 1班', 42, '2021', '这是美术学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (302, '汉语言文学2021 1班', 43, '2021', '这是汉语言文学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (303, '新闻学2021 1班', 44, '2021', '这是新闻学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (304, '广告学2021 1班', 45, '2021', '这是广告学2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (305, '广播电视编导2021 1班', 46, '2021', '这是广播电视编导2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (306, '表演2021 1班', 47, '2021', '这是表演2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (307, '学前教育2021 1班', 48, '2021', '这是学前教育2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (308, '小学教育2021 1班', 49, '2021', '这是小学教育2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (309, '英语2021 1班', 50, '2021', '这是英语2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (310, '商务英语2021 1班', 51, '2021', '这是商务英语2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (311, '日语2021 1班', 52, '2021', '这是日语2021 1班哦~~');
INSERT INTO `t_clazz` VALUES (324, '计算机应用技术2021 2班', 1, '2021', '这是计算机应用技术2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (325, '通信工程2021 2班', 2, '2021', '这是通信工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (326, '软件工程2021 2班', 3, '2021', '这是软件工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (327, '无人驾驶技术2021 2班', 4, '2021', '这是无人驾驶技术2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (328, '计算机科学与技术2021 2班', 5, '2021', '这是计算机科学与技术2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (329, '网络工程2021 2班', 6, '2021', '这是网络工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (330, '数字媒体技术2021 2班', 8, '2021', '这是数字媒体技术2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (331, '数据科学与大数据技术2021 2班', 9, '2021', '这是数据科学与大数据技术2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (332, '人工智能2021 2班', 10, '2021', '这是人工智能2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (333, '电子通信工程2021 2班', 11, '2021', '这是电子通信工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (334, '通信工程2021 2班', 12, '2021', '这是通信工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (335, '物联网工程2021 2班', 13, '2021', '这是物联网工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (336, '机械设计制造及其自动化2021 2班', 14, '2021', '这是机械设计制造及其自动化2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (337, '自动化2021 2班', 15, '2021', '这是自动化2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (338, '电气工程及其自动化2021 2班', 16, '2021', '这是电气工程及其自动化2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (339, '机器人工程2021 2班', 17, '2021', '这是机器人工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (340, '土木工程2021 2班', 18, '2021', '这是土木工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (341, '安全工程2021 2班', 19, '2021', '这是安全工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (342, '工程管理2021 2班', 20, '2021', '这是工程管理2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (343, '建筑学2021 2班', 21, '2021', '这是建筑学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (344, '风景园林2021 2班', 22, '2021', '这是风景园林2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (345, '测绘工程2021 2班', 23, '2021', '这是测绘工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (346, '药学2021 2班', 24, '2021', '这是药学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (347, '制药工程2021 2班', 25, '2021', '这是制药工程2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (348, '药物制剂2021 2班', 26, '2021', '这是药物制剂2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (349, '健康服务与管理2021 2班', 27, '2021', '这是健康服务与管理2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (350, '财务管理2021 2班', 28, '2021', '这是财务管理2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (351, '会计学2021 2班', 29, '2021', '这是会计学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (352, '经济与金融2021 2班', 30, '2021', '这是经济与金融2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (353, '审计学2021 2班', 31, '2021', '这是审计学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (354, '金融科技2021 2班', 32, '2021', '这是金融科技2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (355, '国际经济与贸易2021 2班', 33, '2021', '这是国际经济与贸易2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (356, '人力资源管理2021 2班', 34, '2021', '这是人力资源管理2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (357, '物流管理2021 2班', 35, '2021', '这是物流管理2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (358, '电子商务2021 2班', 36, '2021', '这是电子商务2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (359, '大数据管理与应用2021 2班', 37, '2021', '这是大数据管理与应用2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (360, '数字经济2021 2班', 38, '2021', '这是数字经济2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (361, '视觉传达设计2021 2班', 39, '2021', '这是视觉传达设计2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (362, '环境设计2021 2班', 40, '2021', '这是环境设计2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (363, '动画2021 2班', 41, '2021', '这是动画2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (364, '美术学2021 2班', 42, '2021', '这是美术学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (365, '汉语言文学2021 2班', 43, '2021', '这是汉语言文学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (366, '新闻学2021 2班', 44, '2021', '这是新闻学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (367, '广告学2021 2班', 45, '2021', '这是广告学2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (368, '广播电视编导2021 2班', 46, '2021', '这是广播电视编导2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (369, '表演2021 2班', 47, '2021', '这是表演2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (370, '学前教育2021 2班', 48, '2021', '这是学前教育2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (371, '小学教育2021 2班', 49, '2021', '这是小学教育2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (372, '英语2021 2班', 50, '2021', '这是英语2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (373, '商务英语2021 2班', 51, '2021', '这是商务英语2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (374, '日语2021 2班', 52, '2021', '这是日语2021 2班哦~~');
INSERT INTO `t_clazz` VALUES (375, '芒果小1洛2', 52, '111', '这是芒果小1洛2哦~~');

-- ----------------------------
-- Table structure for t_consume
-- ----------------------------
DROP TABLE IF EXISTS `t_consume`;
CREATE TABLE `t_consume`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `createTime` datetime NOT NULL COMMENT '日期时间',
  `price` mediumint(8) UNSIGNED NOT NULL COMMENT '消费金额',
  `user` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `type` enum('WATER','ELECTRIC','MEAL_EXPENSE','RECHARGE','REFUND','PAYMENT','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消费类型\n\r\n水费 WATER,\r\n电费 ELECTRIC,\r\n餐费 MEAL_EXPENSE,\r\n住宿费 ACCOMMODATION_FEE,\r\n充值 RECHARGE,\r\n其他 OTHER,\r\n缴费 PAYMENT',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消费信息',
  `pos` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '刷卡机ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消费表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_consume
-- ----------------------------
INSERT INTO `t_consume` VALUES (1, '2022-12-04 18:39:15', 100, '1932101141', 'MEAL_EXPENSE', '测试', 1);
INSERT INTO `t_consume` VALUES (2, '2022-12-04 18:39:15', 1000, '1932101144', 'PAYMENT', '测试', 2);
INSERT INTO `t_consume` VALUES (3, '2022-12-04 18:39:15', 300, '1932101144', 'MEAL_EXPENSE', '韭菜绿豆芽3;', 3);
INSERT INTO `t_consume` VALUES (4, '2022-12-04 18:39:15', 700, '1932101144', 'MEAL_EXPENSE', '韭菜绿豆芽3;西红柿炒鸡蛋4;', 4);
INSERT INTO `t_consume` VALUES (5, '2022-12-04 18:39:15', 11000, '1932101144', 'MEAL_EXPENSE', '米饭1;三杯鸡11;带鱼10;排骨年糕12;米花鸡10;酱鸭腿6;西兰花6;热狗炒蛋7;小炒冬瓜5;南瓜丝6;脆皮豆腐6;千页豆腐7;三合小炒6;芹菜炒腊肠10;酸辣土豆丝3;干锅平包菜4;', 1);
INSERT INTO `t_consume` VALUES (6, '2022-12-04 18:39:15', 4000, '1932101144', 'MEAL_EXPENSE', '三杯鸡11;脆皮豆腐6;千页豆腐7;三合小炒6;芹菜炒腊肠10;', 2);
INSERT INTO `t_consume` VALUES (7, '2022-12-04 18:39:15', 2800, '1932101144', 'MEAL_EXPENSE', '排骨年糕12;米花鸡10;三合小炒6;', 3);
INSERT INTO `t_consume` VALUES (8, '2022-12-04 18:39:15', 1000, '1932101144', 'MEAL_EXPENSE', '芹菜炒腊肠10;', 4);
INSERT INTO `t_consume` VALUES (9, '2022-12-04 18:39:15', 300, '1932101144', 'MEAL_EXPENSE', '酸辣土豆丝3;', 1);
INSERT INTO `t_consume` VALUES (10, '2022-12-04 18:39:15', 400, '1932101144', 'MEAL_EXPENSE', '西红柿炒鸡蛋4;', 2);
INSERT INTO `t_consume` VALUES (11, '2022-12-04 18:39:15', 100, '1932101144', 'MEAL_EXPENSE', '米饭1;', 3);
INSERT INTO `t_consume` VALUES (12, '2022-12-04 18:39:15', 400, '1932101144', 'MEAL_EXPENSE', '西红柿炒鸡蛋4;', 4);
INSERT INTO `t_consume` VALUES (13, '2023-01-04 18:39:15', 400, '1932101149', 'MEAL_EXPENSE', '西红柿炒鸡蛋4;', 1);
INSERT INTO `t_consume` VALUES (14, '2023-01-04 18:39:15', 700, '1932101149', 'MEAL_EXPENSE', '米饭1;三杯鸡11;带鱼10;排骨年糕12;米花鸡10;酱鸭腿6;西兰花6;热狗炒蛋7;小炒冬瓜5;南瓜丝6;脆皮豆腐6;千页豆腐7;三合小炒6;芹菜炒腊肠10;酸辣土豆丝3;干锅平包菜4;', 2);
INSERT INTO `t_consume` VALUES (15, '2023-01-04 18:39:15', 400, '1932101149', 'MEAL_EXPENSE', '西红柿炒鸡蛋4;', 3);
INSERT INTO `t_consume` VALUES (16, '2023-01-04 18:39:15', 400, '1932101149', 'MEAL_EXPENSE', '西红柿炒鸡蛋4;', 4);
INSERT INTO `t_consume` VALUES (17, '2023-01-04 18:39:15', 300, '1932101149', 'MEAL_EXPENSE', '酸辣土豆丝3;', 1);
INSERT INTO `t_consume` VALUES (18, '2023-01-04 18:39:15', 1000, '1932101149', 'MEAL_EXPENSE', '芹菜炒腊肠10;', 2);
INSERT INTO `t_consume` VALUES (19, '2023-01-04 18:39:15', 300, '1932101149', 'MEAL_EXPENSE', '韭菜绿豆芽3;', 3);
INSERT INTO `t_consume` VALUES (20, '2023-01-04 18:39:15', 1000, '1932101149', 'MEAL_EXPENSE', '测试', 4);
INSERT INTO `t_consume` VALUES (22, '2023-01-04 18:39:15', 1700, '1932101149', 'MEAL_EXPENSE', '三杯鸡11;脆皮豆腐6;', 1);
INSERT INTO `t_consume` VALUES (28, '2023-01-04 18:39:15', 2000, '1932101149', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (29, '2023-01-04 18:39:15', 700, '1932101149', 'RECHARGE', '充值', 6);
INSERT INTO `t_consume` VALUES (30, '2023-01-04 18:39:15', 1000, '1932101149', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (31, '2023-01-04 18:39:15', 1000, '1932101149', 'RECHARGE', '充值', 6);
INSERT INTO `t_consume` VALUES (32, '2023-01-04 18:39:15', 1010, '1932101149', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (33, '2023-01-04 18:39:15', 1000, '1932101149', 'RECHARGE', '充值', 6);
INSERT INTO `t_consume` VALUES (34, '2023-01-04 18:39:15', 1020, '1932101149', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (35, '2023-01-04 18:39:15', 1000, '1932101149', 'RECHARGE', '充值', 6);
INSERT INTO `t_consume` VALUES (36, '2023-01-04 18:39:15', 1000, '1932101149', 'WATER', '水费', 2);
INSERT INTO `t_consume` VALUES (37, '2023-01-04 18:39:15', 1000, '1932101149', 'WATER', '水费', 3);
INSERT INTO `t_consume` VALUES (38, '2023-01-04 18:39:15', 1000, '1932101149', 'ELECTRIC', '电费', 4);
INSERT INTO `t_consume` VALUES (39, '2023-01-04 18:39:15', 1000, '1932101149', 'ELECTRIC', '电费', 1);
INSERT INTO `t_consume` VALUES (40, '2023-01-04 18:39:15', 1000, '1932101149', 'PAYMENT', '住宿费', 2);
INSERT INTO `t_consume` VALUES (41, '2023-01-04 18:39:15', 1000, '1932101149', 'PAYMENT', '住宿费', 3);
INSERT INTO `t_consume` VALUES (44, '2023-01-04 18:39:15', 1000, '1932101149', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (45, '2022-12-09 23:28:20', 2300, '1932101141', 'WATER', '千页豆腐7;三合小炒6;芹菜炒腊肠10;', 1);
INSERT INTO `t_consume` VALUES (46, '2023-01-04 18:39:15', 1600, '1932101149', 'WATER', '带鱼10;西兰花6;', 2);
INSERT INTO `t_consume` VALUES (47, '2023-01-04 18:39:15', 1000, '1932101149', 'WATER', '芹菜炒腊肠10;', 3);
INSERT INTO `t_consume` VALUES (48, '2023-01-04 18:39:15', 400, '1932101149', 'WATER', '西红柿炒鸡蛋4;', 4);
INSERT INTO `t_consume` VALUES (49, '2023-01-04 18:39:15', 1300, '1932101149', 'WATER', '芹菜炒腊肠10;韭菜绿豆芽3;', 1);
INSERT INTO `t_consume` VALUES (50, '2022-12-21 14:46:36', 10000, '1932101122', 'RECHARGE', '充值', 5);
INSERT INTO `t_consume` VALUES (51, '2022-12-04 18:39:15', 1000, '1932101141', 'MEAL_EXPENSE', '测试', 1);
INSERT INTO `t_consume` VALUES (52, '2023-01-04 18:39:15', 700, '1932101149', 'WATER', '米饭1;南瓜丝6;', 4);
INSERT INTO `t_consume` VALUES (53, '2023-01-04 18:39:15', 1000, '1932101149', 'WATER', '芹菜炒腊肠10;', 4);
INSERT INTO `t_consume` VALUES (54, '2023-01-08 21:31:49', 100, '1932101149', 'WATER', '米饭1;', 4);
INSERT INTO `t_consume` VALUES (55, '2023-01-19 17:19:45', 290000, '1', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (56, '2023-01-19 17:25:05', 290000, '1432101149', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (57, '2023-01-19 17:29:02', 290000, '1432101149', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (58, '2023-01-19 17:29:29', 290000, '1932101111', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (59, '2023-01-19 17:30:51', 290000, '1932101141', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (60, '2023-01-19 18:42:46', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (61, '2023-01-19 18:47:15', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (62, '2023-01-19 18:47:20', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (63, '2023-01-19 18:48:08', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (64, '2023-01-19 18:48:15', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (65, '2023-01-19 19:38:32', 290000, '1932101122', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (66, '2023-01-19 19:39:58', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (67, '2023-01-19 19:45:31', 290000, '1932101122', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (68, '2023-01-19 19:45:45', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (69, '2023-01-19 19:54:39', 290000, '1932101122', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (70, '2023-01-19 19:54:53', 290000, '1932101122', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (71, '2023-01-22 17:54:16', 290000, '1932101133', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (72, '2023-01-22 17:54:41', 290000, '1932101133', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (73, '2023-01-22 17:54:48', 290000, '1932101133', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (74, '2023-01-22 18:04:12', 290000, '1432101149', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (75, '2023-01-22 18:04:48', 290000, '1432101149', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (76, '2023-01-22 18:51:38', 270000, '1932101133', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (77, '2023-01-24 14:05:44', 270000, '1932101141', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (78, '2023-01-24 14:09:12', 100, '1932101141', 'PAYMENT', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (79, '2023-01-24 18:21:08', 270000, '1932101133', 'REFUND', '2022-2023学年第二学期住宿费', NULL);
INSERT INTO `t_consume` VALUES (80, '2023-01-24 18:33:01', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (81, '2023-01-24 18:33:46', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (82, '2023-01-24 18:34:30', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (83, '2023-01-24 18:36:25', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (86, '2023-01-24 18:38:15', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (87, '2023-01-24 18:38:50', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (88, '2023-01-24 18:40:14', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (89, '2023-01-24 18:40:53', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (90, '2023-01-24 18:41:12', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (91, '2023-01-24 18:42:32', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (92, '2023-01-24 18:42:49', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (93, '2023-01-24 18:44:58', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (95, '2023-01-24 19:31:29', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (97, '2023-01-24 19:36:53', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (98, '2023-01-24 19:39:08', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (99, '2023-01-24 19:40:08', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (100, '2023-01-24 19:41:48', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (101, '2023-01-24 19:42:11', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (102, '2023-01-24 19:43:07', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (103, '2023-01-24 19:43:53', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (104, '2023-01-24 19:45:36', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (105, '2023-01-24 19:46:51', 2000, '1932101149', 'PAYMENT', '测试', NULL);
INSERT INTO `t_consume` VALUES (108, '2023-01-24 19:48:51', 2000, '1932101149', 'REFUND', '测试', NULL);
INSERT INTO `t_consume` VALUES (109, '2023-01-24 20:10:11', 50000, '1432101149', 'RECHARGE', '充值', NULL);
INSERT INTO `t_consume` VALUES (110, '2023-01-24 20:10:32', 100000, '1432101149', 'RECHARGE', '充值', NULL);

-- ----------------------------
-- Table structure for t_faculty
-- ----------------------------
DROP TABLE IF EXISTS `t_faculty`;
CREATE TABLE `t_faculty`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '学院表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_faculty
-- ----------------------------
INSERT INTO `t_faculty` VALUES (1, '大数据与人工智能学院4', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (2, '电子工程学院/智能制造学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (3, '城市建设学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (4, '药学院', '这是药学院哦');
INSERT INTO `t_faculty` VALUES (5, '财会与金融学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (6, '商学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (7, '艺术学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (8, '文化与传媒学院', '哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈');
INSERT INTO `t_faculty` VALUES (9, '外国语学院', '这是外国语学院哦');

-- ----------------------------
-- Table structure for t_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_grade`;
CREATE TABLE `t_grade`  (
  `name` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK:年份年级学年',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '年份、年级、学年表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_grade
-- ----------------------------

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `author` int(10) UNSIGNED NOT NULL COMMENT '发布者',
  `createTime` datetime NOT NULL COMMENT '日期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_message
-- ----------------------------
INSERT INTO `t_message` VALUES (1, '1', 1932101111, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (3, '有道智慧教育平台是基于区域教育教学流程，利用人工智能、大数据等技术，打通课前、课中、课后的教学闭环场景，通过对学校日常教学过程中的学业数据进行全方位采集，构建基于大数据驱动下的教育数据平台。用数据智能促进管理部门的精准教学与科学决策，助力学校进行分层教研、精准教学，为老师和学生减负增效。', 1932101122, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (4, '111111', 1932101141, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (6, '111111', 1932101143, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (7, '111111', 1932101144, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (8, '111111', 1932101147, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (9, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (10, '111111', 1932101111, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (11, '111111', 1932101122, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (13, '111111', 1932101141, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (14, '111111', 1932101143, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (15, '111111', 1932101144, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (16, '111111', 1932101147, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (17, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (18, '111111', 1932101111, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (19, '111111', 1932101122, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (20, '111111', 1932101141, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (21, '111111', 1932101143, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (22, '111111', 1932101144, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (23, '111111', 1932101147, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (24, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (25, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (27, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (36, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (37, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (38, '111111', 1932101149, '2020-12-17 13:17:16');
INSERT INTO `t_message` VALUES (39, '哈哈哈哈哈', 1932101149, '2023-01-08 19:08:31');
INSERT INTO `t_message` VALUES (40, '轻轻巧巧', 1932101149, '2023-01-08 20:27:11');
INSERT INTO `t_message` VALUES (41, '哈哈哈哈哈', 1932101149, '2023-01-08 20:28:31');
INSERT INTO `t_message` VALUES (42, '哈哈哈哈哈', 1932101149, '2023-01-08 20:29:13');
INSERT INTO `t_message` VALUES (43, '哈哈哈哈', 1932101149, '2023-01-08 20:30:08');
INSERT INTO `t_message` VALUES (44, '哈哈哈哈哈', 1932101149, '2023-01-08 20:32:38');
INSERT INTO `t_message` VALUES (45, '你不宝贝宝贝', 1932101149, '2023-01-08 20:36:10');

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `author` int(10) UNSIGNED NOT NULL COMMENT '发布者',
  `createTime` datetime NOT NULL COMMENT '日期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (1, '系统测试公告1', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-07 23:33:01');
INSERT INTO `t_notice` VALUES (2, '系统测试公告2', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-08 00:12:52');
INSERT INTO `t_notice` VALUES (3, '系统测试公告3', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-07 23:33:01');
INSERT INTO `t_notice` VALUES (4, '系统测试公告4', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-08 00:12:52');
INSERT INTO `t_notice` VALUES (7, '系统测试公告7', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-07 23:33:01');
INSERT INTO `t_notice` VALUES (8, '系统测试公告8', '3种提取方法：1、使用SUBSTRING()函数，可从字符串头部开始截取指定长度的子字符串，语法“UBSTRING(字符串,1,子串长度)”。2、使用left()函数，可从字符串开头截取指定长度的子字符串并返回，语法“LEFT(字符串,子串长度)”。3、使用substr()函数，可从字符串开头截取指定长度的子串并返回，语法“SUBSTR(字符串,1,子串长度)”\n\n', 1, '2022-12-08 00:12:52');
INSERT INTO `t_notice` VALUES (9, '测试公告9', '测试哈哈哈哈哈', 1, '2022-12-08 00:49:57');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '缴费信息发布表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_payment
-- ----------------------------
INSERT INTO `t_payment` VALUES (8970391972352000, '2023年度住宿费', 'DORMITORY_FEE', 200000, '2023年度住宿费', 1932101149, '2023-01-25 18:05:08', '2023-01-01 00:00:00', '2023-03-10 00:00:00', 'IN_PROGRESS');

-- ----------------------------
-- Table structure for t_pos
-- ----------------------------
DROP TABLE IF EXISTS `t_pos`;
CREATE TABLE `t_pos`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '刷卡机名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在地址',
  `type` enum('WATER','ELECTRIC','MEAL_EXPENSE','ACCOMMODATION_FEE','RECHARGE','OTHER') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消费类型\n\r\n水费 WATER,\r\n电费 ELECTRIC,\r\n餐费 MEAL_EXPENSE,\r\n住宿费 ACCOMMODATION_FEE,\r\n充值 RECHARGE,\r\n其他 OTHER',
  `store` int(10) UNSIGNED NOT NULL COMMENT '商户ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pos_REF_store`(`store`) USING BTREE,
  CONSTRAINT `pos_REF_store` FOREIGN KEY (`store`) REFERENCES `t_store` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '刷卡机表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_pos
-- ----------------------------
INSERT INTO `t_pos` VALUES (1, '手撕鸡01', '1', '妈妈的味道', 'WATER', 1);
INSERT INTO `t_pos` VALUES (2, '螺蛳粉', '1', '翰林二楼', 'WATER', 1);
INSERT INTO `t_pos` VALUES (3, '食惠客', '1', '小万达', 'WATER', 1);
INSERT INTO `t_pos` VALUES (4, '惠香源', '1', '小万达', 'WATER', 1);
INSERT INTO `t_pos` VALUES (5, '充值机01', '1', '翰林一楼', 'RECHARGE', 1);
INSERT INTO `t_pos` VALUES (6, '充值机02', '1', '小万达二楼', 'RECHARGE', 1);
INSERT INTO `t_pos` VALUES (16, '1', '888888', '12', 'MEAL_EXPENSE', 4);

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '缴费表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_record
-- ----------------------------
INSERT INTO `t_record` VALUES (1, 8970391972352000, 1, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (2, 8970391972352000, 1432101149, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (3, 8970391972352000, 1632101188, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (4, 8970391972352000, 1932101111, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (5, 8970391972352000, 1932101122, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (6, 8970391972352000, 1932101133, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (7, 8970391972352000, 1932101141, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (8, 8970391972352000, 1932101143, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (9, 8970391972352000, 1932101144, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (10, 8970391972352000, 1932101149, 'UNPAID', NULL);
INSERT INTO `t_record` VALUES (11, 8970391972352000, 1932121149, 'UNPAID', NULL);

-- ----------------------------
-- Table structure for t_specialty
-- ----------------------------
DROP TABLE IF EXISTS `t_specialty`;
CREATE TABLE `t_specialty`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `faculty` int(10) UNSIGNED NULL DEFAULT NULL COMMENT 'FK:学院ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `specialty_REF_faculty`(`faculty`) USING BTREE,
  CONSTRAINT `specialty_REF_faculty` FOREIGN KEY (`faculty`) REFERENCES `t_faculty` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专业表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_specialty
-- ----------------------------
INSERT INTO `t_specialty` VALUES (1, '计算机应用技术', '这是计算机应用技术哦', 1);
INSERT INTO `t_specialty` VALUES (2, '通信工程', '这是通信工程哦', 2);
INSERT INTO `t_specialty` VALUES (3, '软件工程', '这是软件工程哦', 1);
INSERT INTO `t_specialty` VALUES (4, '无人驾驶技术', '这是无人驾驶技术哦', 2);
INSERT INTO `t_specialty` VALUES (5, '计算机科学与技术', '这是计算机科学与技术哦', 1);
INSERT INTO `t_specialty` VALUES (6, '网络工程', '这是网络工程哦', 1);
INSERT INTO `t_specialty` VALUES (8, '数字媒体技术', '这是数字媒体技术哦', 1);
INSERT INTO `t_specialty` VALUES (9, '数据科学与大数据技术', '这是数据科学与大数据技术哦', 1);
INSERT INTO `t_specialty` VALUES (10, '人工智能', '这是人工智能哦', 1);
INSERT INTO `t_specialty` VALUES (11, '电子通信工程', '这是电子通信工程哦', 2);
INSERT INTO `t_specialty` VALUES (12, '通信工程', '这是通信工程哦', 2);
INSERT INTO `t_specialty` VALUES (13, '物联网工程', '这是物联网工程哦', 2);
INSERT INTO `t_specialty` VALUES (14, '机械设计制造及其自动化', '这是机械设计制造及其自动化哦', 2);
INSERT INTO `t_specialty` VALUES (15, '自动化', '这是自动化哦', 2);
INSERT INTO `t_specialty` VALUES (16, '电气工程及其自动化', '这是电气工程及其自动化哦', 2);
INSERT INTO `t_specialty` VALUES (17, '机器人工程', '这是机器人工程哦', 2);
INSERT INTO `t_specialty` VALUES (18, '土木工程', '这是土木工程哦', 3);
INSERT INTO `t_specialty` VALUES (19, '安全工程', '这是安全工程哦', 3);
INSERT INTO `t_specialty` VALUES (20, '工程管理', '这是工程管理哦', 3);
INSERT INTO `t_specialty` VALUES (21, '建筑学', '这是建筑学哦', 3);
INSERT INTO `t_specialty` VALUES (22, '风景园林', '这是风景园林哦', 3);
INSERT INTO `t_specialty` VALUES (23, '测绘工程', '这是测绘工程哦', 3);
INSERT INTO `t_specialty` VALUES (24, '药学', '这是药学哦', 4);
INSERT INTO `t_specialty` VALUES (25, '制药工程', '这是制药工程哦', 4);
INSERT INTO `t_specialty` VALUES (26, '药物制剂', '这是药物制剂哦', 4);
INSERT INTO `t_specialty` VALUES (27, '健康服务与管理', '这是健康服务与管理哦', 4);
INSERT INTO `t_specialty` VALUES (28, '财务管理', '这是财务管理哦', 5);
INSERT INTO `t_specialty` VALUES (29, '会计学', '这是会计学哦', 5);
INSERT INTO `t_specialty` VALUES (30, '经济与金融', '这是经济与金融哦', 5);
INSERT INTO `t_specialty` VALUES (31, '审计学', '这是审计学哦', 5);
INSERT INTO `t_specialty` VALUES (32, '金融科技', '这是金融科技哦', 5);
INSERT INTO `t_specialty` VALUES (33, '国际经济与贸易', '这是国际经济与贸易哦', 6);
INSERT INTO `t_specialty` VALUES (34, '人力资源管理', '这是人力资源管理哦', 6);
INSERT INTO `t_specialty` VALUES (35, '物流管理', '这是物流管理哦', 6);
INSERT INTO `t_specialty` VALUES (36, '电子商务', '这是电子商务哦', 6);
INSERT INTO `t_specialty` VALUES (37, '大数据管理与应用', '这是大数据管理与应用哦', 6);
INSERT INTO `t_specialty` VALUES (38, '数字经济', '这是数字经济哦', 6);
INSERT INTO `t_specialty` VALUES (39, '视觉传达设计', '这是视觉传达设计哦', 7);
INSERT INTO `t_specialty` VALUES (40, '环境设计', '这是环境设计哦', 7);
INSERT INTO `t_specialty` VALUES (41, '动画', '这是动画哦', 7);
INSERT INTO `t_specialty` VALUES (42, '美术学', '这是美术学哦', 7);
INSERT INTO `t_specialty` VALUES (43, '汉语言文学', '这是汉语言文学哦', 8);
INSERT INTO `t_specialty` VALUES (44, '新闻学', '这是新闻学哦', 8);
INSERT INTO `t_specialty` VALUES (45, '广告学', '这是广告学哦', 8);
INSERT INTO `t_specialty` VALUES (46, '广播电视编导', '这是广播电视编导哦', 8);
INSERT INTO `t_specialty` VALUES (47, '表演', '这是表演哦', 8);
INSERT INTO `t_specialty` VALUES (48, '学前教育', '这是学前教育哦', 8);
INSERT INTO `t_specialty` VALUES (49, '小学教育', '这是小学教育哦', 8);
INSERT INTO `t_specialty` VALUES (50, '英语', '这是英语哦', 9);
INSERT INTO `t_specialty` VALUES (51, '商务英语', '这是商务英语哦', 9);
INSERT INTO `t_specialty` VALUES (52, '日语', '这是日语哦哈哈哈嘿嘿', 9);

-- ----------------------------
-- Table structure for t_store
-- ----------------------------
DROP TABLE IF EXISTS `t_store`;
CREATE TABLE `t_store`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `area` int(10) UNSIGNED NOT NULL COMMENT 'FK所属区域ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `manager` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理者',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_store_REF_area`(`area`) USING BTREE,
  CONSTRAINT `FK_store_REF_area` FOREIGN KEY (`area`) REFERENCES `t_area` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_store
-- ----------------------------
INSERT INTO `t_store` VALUES (1, '食汇客', '三期一楼', 3, '食汇客餐饮', '大老板', '18888888888');
INSERT INTO `t_store` VALUES (2, '筷筷香', '三期二楼', 3, '筷筷香餐饮', '大老板', '18888888888');
INSERT INTO `t_store` VALUES (3, '中快', '一期一楼', 1, '中快', '大老板', '18888888888');
INSERT INTO `t_store` VALUES (4, '妈妈的味道', '一期右侧二楼', 1, '妈妈的味道', '大老板', '18888888888');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'PK',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班级',
  `specialized` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业',
  `faculty` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `idCard` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '身份证号',
  `unsubscribe` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '注销',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_user_idCard_uindex`(`idCard`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'hhhhhhh', '1', '1', '1', '1', '341227200010102020', 0);
INSERT INTO `t_user` VALUES ('1432101149', '芒果小洛2', '1', '1', '1', '1', '341221200010102020', 0);
INSERT INTO `t_user` VALUES ('1632101188', '芒果小洛88', '1', '1', '1', '102020', '341225201010102020', 0);
INSERT INTO `t_user` VALUES ('1932101111', '芒果小洛11', '1', '1', '1', '1', '341225200010102025', 0);
INSERT INTO `t_user` VALUES ('1932101122', '芒果小洛22', '1', '1', '1', '1', '341225200010102033', 0);
INSERT INTO `t_user` VALUES ('1932101133', '芒果小洛2', '1', '1', '1', '1', '341225200010102220', 0);
INSERT INTO `t_user` VALUES ('1932101141', '芒果小洛41', '1', '1', '1', '1', '341222222222222128', 0);
INSERT INTO `t_user` VALUES ('1932101143', '芒果小洛43', '2', '1', '1', '1', '341225200010111111', 0);
INSERT INTO `t_user` VALUES ('1932101144', 'qkmango', '2', '1', '1', '1', '341225200011222223', 0);
INSERT INTO `t_user` VALUES ('1932101149', '芒果小洛', '2', '1', '1', '1', '341225200011222222', 0);
INSERT INTO `t_user` VALUES ('1932121149', '芒果小洛2', '2', '1', '1', '1', '341225200012102020', 0);

SET FOREIGN_KEY_CHECKS = 1;
