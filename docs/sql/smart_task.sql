/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : smart_task

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 30/08/2022 02:33:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (1, 'smart-post', '文章信息执行器', 0, 'http://192.168.6.198:10001/,http://192.168.6.198:9999/', '2022-08-30 02:16:29');

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `job_group` int(0) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(0) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(0) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(0) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (1, 1, '持久化点赞信息缓存', '2018-11-03 22:21:31', '2022-06-17 16:27:48', 'admin', '', 'CRON', '0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?', 'DO_NOTHING', 'FIRST', 'transThumbFromRedis2DHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '', 1, 1661796900000, 1661797200000);
INSERT INTO `xxl_job_info` VALUES (2, 1, '持久化文章评论点赞总数', '2022-03-16 16:02:05', '2022-06-17 16:27:40', 'Alickx', '', 'CRON', '0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?', 'DO_NOTHING', 'FIRST', 'transCountFromRedis2DHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2022-03-16 16:02:05', '', 1, 1661796900000, 1661797200000);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `job_group` int(0) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(0) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(0) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(0) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '调度-日志',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(0) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log
-- ----------------------------
INSERT INTO `xxl_job_log` VALUES (5114, 1, 1, 'http://192.168.31.153:10000/', 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:35:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:35:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5115, 1, 2, 'http://192.168.31.153:10000/', 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:35:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:35:01', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5116, 1, 2, 'http://192.168.31.153:10000/', 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:40:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:40:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5117, 1, 1, 'http://192.168.31.153:10000/', 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:40:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:40:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5118, 1, 1, 'http://192.168.31.153:10000/', 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:45:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:45:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5119, 1, 2, 'http://192.168.31.153:10000/', 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:45:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:45:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5120, 1, 2, 'http://192.168.31.153:10000/', 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:50:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:50:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5121, 1, 1, 'http://192.168.31.153:10000/', 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-15 23:50:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null', '2022-08-15 23:50:00', 200, '', 0);
INSERT INTO `xxl_job_log` VALUES (5122, 1, 2, NULL, 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 01:50:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5123, 1, 1, NULL, 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 01:50:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5124, 1, 1, NULL, 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 01:55:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5125, 1, 2, NULL, 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 01:55:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5126, 1, 1, NULL, 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:00:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5127, 1, 2, NULL, 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:00:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5128, 1, 2, NULL, 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:05:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5129, 1, 1, NULL, 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:05:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5130, 1, 2, NULL, 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:10:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5131, 1, 1, NULL, 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:10:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5132, 1, 2, 'http://192.168.6.198:9999/', 'transCountFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:15:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.6.198:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.6.198:9999/<br>code：500<br>msg：job handler [transCountFromRedis2DHandler] not found.', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (5133, 1, 1, 'http://192.168.6.198:9999/', 'transThumbFromRedis2DHandler', '', NULL, 0, '2022-08-30 02:15:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.6.198:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.6.198:9999/<br>code：500<br>msg：job handler [transThumbFromRedis2DHandler] not found.', NULL, 0, NULL, 2);

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(0) NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int(0) NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int(0) NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
INSERT INTO `xxl_job_log_report` VALUES (1, '2022-03-06 00:00:00', 0, 2, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (2, '2022-03-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (3, '2022-03-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (4, '2022-03-07 00:00:00', 0, 6, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (5, '2022-03-08 00:00:00', 0, 18, 1, NULL);
INSERT INTO `xxl_job_log_report` VALUES (6, '2022-03-13 00:00:00', 0, 18, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (7, '2022-03-12 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (8, '2022-03-11 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (9, '2022-03-14 00:00:00', 0, 19, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (10, '2022-03-15 00:00:00', 0, 7, 13, NULL);
INSERT INTO `xxl_job_log_report` VALUES (11, '2022-03-16 00:00:00', 0, 18, 1, NULL);
INSERT INTO `xxl_job_log_report` VALUES (12, '2022-03-17 00:00:00', 0, 32, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (13, '2022-03-18 00:00:00', 0, 10, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (14, '2022-03-19 00:00:00', 0, 8, 4, NULL);
INSERT INTO `xxl_job_log_report` VALUES (15, '2022-03-20 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (16, '2022-03-21 00:00:00', 0, 7, 1, NULL);
INSERT INTO `xxl_job_log_report` VALUES (17, '2022-03-22 00:00:00', 0, 21, 15, NULL);
INSERT INTO `xxl_job_log_report` VALUES (18, '2022-03-23 00:00:00', 0, 14, 15, NULL);
INSERT INTO `xxl_job_log_report` VALUES (19, '2022-03-24 00:00:00', 0, 11, 3, NULL);
INSERT INTO `xxl_job_log_report` VALUES (20, '2022-03-25 00:00:00', 0, 11, 7, NULL);
INSERT INTO `xxl_job_log_report` VALUES (21, '2022-03-26 00:00:00', 0, 5, 5, NULL);
INSERT INTO `xxl_job_log_report` VALUES (22, '2022-03-27 00:00:00', 0, 8, 10, NULL);
INSERT INTO `xxl_job_log_report` VALUES (23, '2022-03-28 00:00:00', 0, 13, 12, NULL);
INSERT INTO `xxl_job_log_report` VALUES (24, '2022-03-29 00:00:00', 0, 2, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (25, '2022-03-31 00:00:00', 0, 0, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (26, '2022-03-30 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (27, '2022-04-01 00:00:00', 0, 0, 28, NULL);
INSERT INTO `xxl_job_log_report` VALUES (28, '2022-04-02 00:00:00', 0, 0, 74, NULL);
INSERT INTO `xxl_job_log_report` VALUES (29, '2022-04-06 00:00:00', 0, 0, 14, NULL);
INSERT INTO `xxl_job_log_report` VALUES (30, '2022-04-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (31, '2022-04-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (32, '2022-04-07 00:00:00', 0, 0, 34, NULL);
INSERT INTO `xxl_job_log_report` VALUES (33, '2022-04-14 00:00:00', 0, 0, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (34, '2022-04-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (35, '2022-04-12 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (36, '2022-04-25 00:00:00', 0, 2, 4, NULL);
INSERT INTO `xxl_job_log_report` VALUES (37, '2022-04-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (38, '2022-04-23 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (39, '2022-05-08 00:00:00', 0, 3, 3, NULL);
INSERT INTO `xxl_job_log_report` VALUES (40, '2022-05-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (41, '2022-05-06 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (42, '2022-05-23 00:00:00', 0, 2, 10, NULL);
INSERT INTO `xxl_job_log_report` VALUES (43, '2022-05-22 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (44, '2022-05-21 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (45, '2022-06-06 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (46, '2022-06-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (47, '2022-06-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (48, '2022-06-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (49, '2022-06-08 00:00:00', 0, 41, 13, NULL);
INSERT INTO `xxl_job_log_report` VALUES (50, '2022-06-09 00:00:00', 0, 1506, 6, NULL);
INSERT INTO `xxl_job_log_report` VALUES (51, '2022-06-10 00:00:00', 0, 1630, 20, NULL);
INSERT INTO `xxl_job_log_report` VALUES (52, '2022-06-12 00:00:00', 0, 372, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (53, '2022-06-11 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (54, '2022-06-15 00:00:00', 0, 96, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (55, '2022-06-14 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (56, '2022-06-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (57, '2022-06-16 00:00:00', 0, 384, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (58, '2022-06-17 00:00:00', 0, 80, 12, NULL);
INSERT INTO `xxl_job_log_report` VALUES (59, '2022-06-18 00:00:00', 0, 48, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (60, '2022-06-19 00:00:00', 0, 32, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (61, '2022-07-03 00:00:00', 0, 22, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (62, '2022-07-02 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (63, '2022-07-01 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (64, '2022-07-05 00:00:00', 0, 48, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (65, '2022-07-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (66, '2022-07-06 00:00:00', 0, 6, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (67, '2022-07-09 00:00:00', 0, 38, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (68, '2022-07-08 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (69, '2022-07-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (70, '2022-07-10 00:00:00', 0, 32, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (71, '2022-07-24 00:00:00', 0, 14, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (72, '2022-07-23 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (73, '2022-07-22 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (74, '2022-08-15 00:00:00', 0, 8, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (75, '2022-08-14 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (76, '2022-08-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (77, '2022-08-30 00:00:00', 0, 0, 12, NULL);
INSERT INTO `xxl_job_log_report` VALUES (78, '2022-08-29 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (79, '2022-08-28 00:00:00', 0, 0, 0, NULL);

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `job_id` int(0) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 422 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
INSERT INTO `xxl_job_registry` VALUES (425, 'EXECUTOR', 'smart-post', 'http://192.168.6.198:9999/', '2022-08-30 02:16:10');
INSERT INTO `xxl_job_registry` VALUES (426, 'EXECUTOR', 'smart-post', 'http://192.168.6.198:10001/', '2022-08-30 02:16:13');

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `role` tinyint(0) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
