/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : smart_member

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/08/2022 02:32:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_check_in
-- ----------------------------
DROP TABLE IF EXISTS `t_check_in`;
CREATE TABLE `t_check_in`  (
  `id` bigint(0) NOT NULL,
  `member_id` bigint(0) NOT NULL,
  `check_in_date` datetime(0) NOT NULL,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_check_in_id_uindex`(`id`) USING BTREE,
  INDEX `t_check_in_member_id_check_in_date_index`(`member_id`, `check_in_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '签到表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_check_in
-- ----------------------------

-- ----------------------------
-- Table structure for t_follow
-- ----------------------------
DROP TABLE IF EXISTS `t_follow`;
CREATE TABLE `t_follow`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `member_id` bigint(0) NOT NULL COMMENT '用户id',
  `to_member_id` bigint(0) NOT NULL COMMENT '关注目标的用户id',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '关注状态',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_follow_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_follow
-- ----------------------------

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '呢称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `pass_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别 0 = 男 1= 女',
  `phone` int(0) NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人介绍',
  `fans` int(0) NOT NULL DEFAULT 0 COMMENT '粉丝数',
  `follow` int(0) NOT NULL DEFAULT 0 COMMENT '关注数',
  `score` int(0) NOT NULL DEFAULT 0 COMMENT '积分',
  `gitee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'gitee地址',
  `github` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'github地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问系统',
  `qq_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'QQ号码',
  `comment_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 = 正常 1 = 不可评论',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问浏览器',
  `user_tag` tinyint(0) NULL DEFAULT 0 COMMENT '用户的标签 0 = 普通用户',
  `status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '用户状态 0 = 正常',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上一次登录的时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上一次登录的ip',
  `updated_time` datetime(0) NOT NULL,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_member__email_index`(`email`) USING BTREE,
  UNIQUE INDEX `t_member_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (1499226795866386433, '我是小号', '249095581@qq.com', '$2a$10$X0ZzXiudT2/XbHFyMdkyJe4j5pQ6CsWebm/edETxIgOz4TglMksa6', '0', NULL, 'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-08/87c6cf2e6ae14f688a94cf487780a15920180905151420_k5Ueh.jpeg', '我就是一个小妖怪。', 0, 0, 0, NULL, NULL, 'Windows', NULL, 0, 'Chrome', 0, 0, '2022-06-16 16:01:38', '0:0:0:0:0:0:0:1', '2022-06-08 14:37:05', '2022-03-03 11:34:57', 0);
INSERT INTO `t_member` VALUES (1500659300137959426, 'Gardero', '799833026@qq.com', '$2a$10$PS7brgX/RkOVfL2G2x6gb.EMmO4U2V7zVopk8G3QKkwXpYEWc7k1G', '2', NULL, 'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-03-18/cf2c4d83644941ef8a61175f7d5284ee微信图片_20220318163217.jpg', NULL, 0, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 0, 0, NULL, NULL, '2022-03-18 16:35:32', '2022-03-07 10:27:13', 0);
INSERT INTO `t_member` VALUES (1506910849124057090, '我是管理员', '89991829@qq.com', '$2a$10$6oXCqLAsCStFL0sbwWgLruTc81rRdgxWIVBUj.Tbc//hJEzjqISw2', '0', NULL, 'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-07/7fd18ec14e5b413aaacb1522c6023764微信图片_20220414145043.jpg', '我就是一个可爱的人捏', 0, 0, 0, NULL, NULL, 'Windows', NULL, 0, 'Chrome', 0, 0, '2022-08-15 23:42:14', '0:0:0:0:0:0:0:1', '2022-08-15 23:50:52', '2022-03-24 16:28:38', 0);
INSERT INTO `t_member` VALUES (1534144417055760386, '新人_oeqw9', '1944716886@qq.com', '$2a$10$mquTgn3FSxf6rV8oCLdsi.XRRSLxnsbJ.26TmraUkMUykrz0Evup2', '0', NULL, 'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-07/503a85ac7dd942f3a610edb6a87ea64220180905151708_fTUsh.jpeg', '我是王旭嘉', 0, 0, 0, NULL, NULL, 'Windows', NULL, 0, 'Chrome', 0, 0, '2022-06-07 20:05:43', '0:0:0:0:0:0:0:1', '2022-06-07 20:33:18', '2022-06-07 20:05:07', 0);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `permission_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '对应url',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_permission_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色的描述',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_role_id_uindex`(`id`) USING BTREE,
  INDEX `t_role_role_name_index`(`role_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, 'admin', '管理员', '2022-06-09 21:41:53', '2022-06-09 21:41:55', 0);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission`  (
  `id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  `permission_uid` bigint(0) NOT NULL COMMENT '权限uid',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_role_permission_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_ban
-- ----------------------------
DROP TABLE IF EXISTS `t_user_ban`;
CREATE TABLE `t_user_ban`  (
  `id` bigint(0) NOT NULL,
  `ban_user_id` bigint(0) NOT NULL COMMENT '封禁用户id',
  `ban_type` tinyint(0) NOT NULL COMMENT '封禁类型',
  `ban_handler_id` bigint(0) NOT NULL COMMENT '封禁操作者Id',
  `ban_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封禁理由',
  `ban_time` datetime(0) NOT NULL COMMENT '封禁开始时间',
  `ban_end_time` datetime(0) NOT NULL COMMENT '封禁结束时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_user_ban_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '封禁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_ban
-- ----------------------------
INSERT INTO `t_user_ban` VALUES (1535165512378523649, 1499226795866386433, 2, 1506910849124057090, '这是一个测试封禁。', '2022-06-10 15:42:35', '2022-06-17 15:42:35', '2022-06-10 15:42:35', '2022-06-10 15:42:35', 1);
INSERT INTO `t_user_ban` VALUES (1535165512399495169, 1499226795866386433, 4, 1506910849124057090, '这是一个测试封禁。', '2022-06-10 15:42:35', '2022-06-17 15:42:35', '2022-06-10 15:42:35', '2022-06-10 15:42:35', 1);
INSERT INTO `t_user_ban` VALUES (1535165512466604033, 1499226795866386433, 5, 1506910849124057090, '这是一个测试封禁。', '2022-06-10 15:42:35', '2022-06-17 15:42:35', '2022-06-10 15:42:35', '2022-06-10 15:42:35', 1);
INSERT INTO `t_user_ban` VALUES (1537338884764069889, 1499226795866386433, 4, 1506910849124057090, '涉嫌非法信息', '2022-06-16 15:38:47', '2022-06-19 15:38:47', '2022-06-16 15:38:47', '2022-06-16 15:38:47', 1);
INSERT INTO `t_user_ban` VALUES (1537344486902984705, 1499226795866386433, 4, 1506910849124057090, '涉嫌非法信息', '2022-06-16 16:01:03', '2022-06-23 16:01:03', '2022-06-16 16:01:03', '2022-06-16 16:01:03', 1);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(0) NOT NULL,
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `role_id` bigint(0) NOT NULL COMMENT '角色的id',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `t_user_role_id_uindex`(`id`) USING BTREE,
  INDEX `t_user_role_user_id_index`(`user_id`) USING BTREE,
  INDEX `t_user_role_role_id_index`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1506910849124057090, 1, '2022-06-09 21:42:56', '2022-06-09 21:42:57', 0);

SET FOREIGN_KEY_CHECKS = 1;
