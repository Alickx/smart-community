/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : smart_notification

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/08/2022 02:33:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_event_remind
-- ----------------------------
DROP TABLE IF EXISTS `t_event_remind`;
CREATE TABLE `t_event_remind`  (
  `id` bigint(0) NOT NULL COMMENT '主键id',
  `action_type` tinyint(0) NOT NULL COMMENT '操作类型',
  `source_id` bigint(0) NULL DEFAULT NULL COMMENT '事件源id',
  `source_type` tinyint(0) NULL DEFAULT NULL COMMENT '事件源类型',
  `source_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '事件源内容',
  `source_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '事件源标题',
  `state` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否已读 0 = 未读 1= 已读',
  `sender_id` bigint(0) NULL DEFAULT NULL COMMENT '操作者uid',
  `recipient_id` bigint(0) NULL DEFAULT NULL COMMENT '接受通知的用户uid',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '提醒时间',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '读取的时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0 = 未删除 1 = 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '事件提醒表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_event_remind
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
