-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_auth
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `smart_auth`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_auth` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_auth`;

--
-- Table structure for table `auth_info_update`
--

DROP TABLE IF EXISTS `auth_info_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_info_update` (
  `id` bigint NOT NULL,
  `attribute_name` varchar(30) NOT NULL COMMENT '属性名',
  `attribute_old_val` varchar(30) NOT NULL DEFAULT '' COMMENT '属性对应旧的值',
  `attribute_new_val` varchar(30) NOT NULL DEFAULT '' COMMENT '属性对应新的值',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户注册日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_info_update`
--

LOCK TABLES `auth_info_update` WRITE;
/*!40000 ALTER TABLE `auth_info_update` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_info_update` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_login_log`
--

DROP TABLE IF EXISTS `auth_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_login_log` (
  `id` bigint NOT NULL,
  `type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '登录方式 第三方/邮箱/手机等',
  `command` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '操作类型 1登陆成功  2登出成功 3登录失败 4登出失败',
  `version` varchar(32) NOT NULL DEFAULT '1.0' COMMENT '客户端版本号',
  `client` varchar(20) NOT NULL DEFAULT '' COMMENT '客户端',
  `device_id` varchar(64) NOT NULL DEFAULT '' COMMENT '登录时设备号',
  `lastip` varchar(32) NOT NULL DEFAULT '' COMMENT '登录ip',
  `os` varchar(16) NOT NULL DEFAULT '' COMMENT '手机系统',
  `osver` varchar(32) NOT NULL DEFAULT '' COMMENT '系统版本',
  `text` varchar(200) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid_type_time` (`id`,`type`,`create_time`) USING BTREE,
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='登陆日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_login_log`
--

LOCK TABLES `auth_login_log` WRITE;
/*!40000 ALTER TABLE `auth_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_register_log`
--

DROP TABLE IF EXISTS `auth_register_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_register_log` (
  `id` bigint NOT NULL,
  `register_method` tinyint unsigned NOT NULL COMMENT '注册方式1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `register_ip` varchar(16) NOT NULL DEFAULT '' COMMENT '注册IP',
  `register_client` varchar(16) NOT NULL DEFAULT '' COMMENT '注册客户端',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户注册日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_register_log`
--

LOCK TABLES `auth_register_log` WRITE;
/*!40000 ALTER TABLE `auth_register_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_register_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user` (
  `id` bigint NOT NULL,
  `identity_type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '1手机号 2邮箱 3用户名 4qq 5微信 6腾讯微博 7新浪微博',
  `identifier` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号 邮箱 用户名或第三方应用的唯一标识',
  `certificate` varchar(200) NOT NULL DEFAULT '' COMMENT '密码凭证(站内的保存密码，站外的不保存或保存token)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新绑定时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户授权表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (1571562360907292673,0,'89991829@qq.com','$2a$10$ew9PLyvSqdrv9C5jjBJhaODM2zPDCD6TiQA6N2MMsd4b/YcxmB1dW','2022-09-18 18:10:39','2022-09-18 18:10:39');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint NOT NULL,
  `permission_name` varchar(50) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `update_time` timestamp NOT NULL,
  `create_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin','管理员','2022-06-09 13:41:53','2022-06-09 13:41:55');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permission` (
  `id` bigint NOT NULL,
  `role_id` bigint NOT NULL COMMENT '角色id',
  `permission_id` bigint NOT NULL COMMENT '权限id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1506910849124057090,1,'2022-06-09 13:42:56','2022-06-09 13:42:57');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_config`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_config`;

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info` DISABLE KEYS */;
INSERT INTO `config_info` VALUES (1,'smart-auth-dev.yml','DEFAULT_GROUP','server:\r\n  port: 17000\r\nspring:\r\n  application:\r\n    name: smart-auth\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','bd17f0326355bd45aa5c8dfd4221c516','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1','',NULL,NULL,'yaml',NULL,''),(2,'smart-gateway-dev.yml','DEFAULT_GROUP','server:\n  port: 8601\nspring:\n  application:\n    name: smart-gateway\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n    gateway:\n      routes:\n        - id: route_member\n          uri: \'lb://smart-member\'\n          predicates:\n            - Path=/api/v1/member/**\n          filters:\n            - \'RewritePath=/api/v1/member,/\'\n        - id: route_auth\n          uri: \'lb://smart-auth\'\n          predicates:\n            - Path=/api/v1/auth/**\n          filters:\n            - \'RewritePath=/api/v1/auth,/\'\n        - id: route_post\n          uri: \'lb://smart-post\'\n          predicates:\n            - Path=/api/v1/post/**\n          filters:\n            - \'RewritePath=/api/v1/post,/\'\n        - id: route_notify\n          uri: \'lb://smart-notify\'\n          predicates:\n            - Path=/api/v1/notification/**\n          filters:\n            - \'RewritePath=/api/v1/notification,/\'\n        - id: route_thirdpart\n          uri: \'lb://smart-thirdpart\'\n          predicates:\n            - Path=/api/v1/thirdpart/**\n          filters:\n            - \'RewritePath=/api/v1/thirdpart,/\'\n        - id: route_search\n          uri: \'lb://smart-search\'\n          predicates:\n            - Path=/api/v1/search/**\n          filters:\n            - \'RewritePath=/api/v1/search,/\'\n        - id: route_task\n          uri: \'lb://smart-task\'\n          predicates:\n            - Path=/api/v1/task/**\n          filters:\n            - \'RewritePath=/api/v1/task,/\'\n  redis:\n    database: 0\n    host: 127.0.0.1\n    port: 6379\n    timeout: 10s\n    lettuce:\n      pool:\n        max-active: 200\n        max-wait: -1ms\n        max-idle: 10\n        min-idle: 0\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\nmybatis-plus:\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\n  global-config:\n    db-config:\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\nsa-token:\n  token-name: access_token\n  timeout: 2592000\n  activity-timeout: -1\n  is-concurrent: false\n  is-share: true\n  token-style: simple-uuid\n  is-log: true\n  isReadBody: true\n  isReadHead: true\n  isReadCookie: true\n  autoRenew: true\n  isPrint: false\n','86513fd10e988859c2eec662608be8ee','2022-08-31 00:02:25','2022-09-03 00:50:31','nacos','0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1','','','','yaml','',''),(3,'smart-member-dev.yml','DEFAULT_GROUP','server:\r\n  port: 12000\r\nspring:\r\n  application:\r\n    name: smart-member\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','8ccb50dea7431694a95dea9086e5c468','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,''),(4,'smart-notification-dev.yml','DEFAULT_GROUP','server:\r\n  port: 16000\r\nspring:\r\n  application:\r\n    name: smart-notification\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_notification?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','754f453905ff84589b8daef4a7e254c2','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,''),(5,'smart-post-dev.yml','DEFAULT_GROUP','server:\r\n  port: 18000\r\nspring:\r\n  application:\r\n    name: smart-post\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    publisherConfirms: true\r\n    publisher-returns: true\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\n          maxAttempts: 3\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_post?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n\r\nxxl:\r\n  job:\r\n    admin:\r\n      #调度中心部署跟地址：如调度中心集群部署存在多个地址则用逗号分隔。\r\n      #执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"。\r\n      addresses: http://localhost:13000/xxl-job-admin\r\n    #分别配置执行器的名称、ip地址、端口号\r\n    #注意：如果配置多个执行器时，防止端口冲突\r\n    executor:\r\n      appname: smart-post\r\n      ip: 127.0.0.1\r\n      port: 9999\r\n      #执行器运行日志文件存储的磁盘位置，需要对该路径拥有读写权限\r\n      logpath: /data/applogs/xxl-job/jobhandler\r\n      #执行器Log文件定期清理功能，指定日志保存天数，日志文件过期自动删除。限制至少保持3天，否则功能不生效；\r\n      #-1表示永不删除\r\n      logretentiondays: 7\r\nribbon:\r\n#指的是建立连接所用的时间，适用于网络状况正常的情况下，两端连接所用的时间\r\n  ReadTimeout: 5000\r\n#指的是建立连接后从服务器读取到可用资源所用的时间\r\n  ConnectTimeout: 5000\r\n','60505e3ac12a0640a00226506924446d','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,''),(6,'smart-search-dev.yml','DEFAULT_GROUP','server:\r\n  port: 15000\r\nspring:\r\n  application:\r\n    name: smart-search\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','a5018c9a98e95b38c4494ea059a8020b','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,''),(7,'smart-task-dev.yml','DEFAULT_GROUP','server:\r\n  port: 13000\r\n  servlet:\r\n    context-path: /xxl-job-admin\r\nspring:\r\n  freemarker:\r\n    charset: UTF-8\r\n    request-context-attribute: request\r\n    settings:\r\n      number_format: 0.##########\r\n    suffix: .ftl\r\n    templateLoaderPath: classpath:/templates/\r\n  mail:\r\n    from: xxx@qq.com\r\n    host: smtp.qq.com\r\n    password: xxx\r\n    port: 25\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          socketFactory:\r\n            class: javax.net.ssl.SSLSocketFactory\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n    username: xxx@qq.com\r\n  mvc:\r\n    servlet:\r\n      load-on-startup: 0\r\n    static-path-pattern: /static/**\r\n  resources:\r\n    static-locations: classpath:/static/\r\n  application:\r\n    name: smart-task\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_task?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\nmanagement:\r\n  health:\r\n    mail:\r\n      enabled: false\r\n  server:\r\n    servlet:\r\n      context-path: /actuator\r\nxxl:\r\n  job:\r\n    accessToken: \'\'\r\n    i18n: zh_CN\r\n    logretentiondays: 30\r\n    triggerpool:\r\n      fast:\r\n        max: 200\r\n      slow:\r\n        max: 100','ef9040688df1ddb356024850b68d131f','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,''),(8,'smart-thirdpart-dev.yml','DEFAULT_GROUP','server:\r\n  port: 9000\r\nspring:\r\n  application:\r\n    name: smart-thirdpart\r\n  mail:\r\n    host: smtp.qq.com \r\n    username: \r\n    password: \r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n\r\noss:\r\n  accessId: test\r\n  accessKey: test\r\n  endpoint: test\r\n  bucket: test\r\n  dir: test','cdce58e00abb6f933a0a34f7730eff87','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','','af0252de-6da5-45a8-905b-53efeb106bc1',NULL,NULL,NULL,'yaml',NULL,'');
/*!40000 ALTER TABLE `config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
  KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
  KEY `idx_did` (`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info` DISABLE KEYS */;
INSERT INTO `his_config_info` VALUES (0,1,'smart-auth-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 17000\r\nspring:\r\n  application:\r\n    name: smart-auth\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','bd17f0326355bd45aa5c8dfd4221c516','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,2,'smart-gateway-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 8601\r\nspring:\r\n  application:\r\n    name: smart-gateway\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n    gateway:\r\n      routes:\r\n        - id: route_member\r\n          uri: \'lb://smart-member\'\r\n          predicates:\r\n            - Path=/api/v1/member/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_auth\r\n          uri: \'lb://smart-auth\'\r\n          predicates:\r\n            - Path=/api/v1/auth/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_post\r\n          uri: \'lb://smart-post\'\r\n          predicates:\r\n            - Path=/api/v1/post/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_notify\r\n          uri: \'lb://smart-notify\'\r\n          predicates:\r\n            - Path=/api/v1/notification/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_thirdpart\r\n          uri: \'lb://smart-thirdpart\'\r\n          predicates:\r\n            - Path=/api/v1/thirdpart/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_search\r\n          uri: \'lb://smart-search\'\r\n          predicates:\r\n            - Path=/api/v1/search/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_task\r\n          uri: \'lb://smart-task\'\r\n          predicates:\r\n            - Path=/api/v1/task/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','62cc5ea86057090efe23ae34beb4e9c4','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,3,'smart-member-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 12000\r\nspring:\r\n  application:\r\n    name: smart-member\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','8ccb50dea7431694a95dea9086e5c468','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,4,'smart-notification-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 16000\r\nspring:\r\n  application:\r\n    name: smart-notification\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_notification?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','754f453905ff84589b8daef4a7e254c2','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,5,'smart-post-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 18000\r\nspring:\r\n  application:\r\n    name: smart-post\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    publisherConfirms: true\r\n    publisher-returns: true\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\n          maxAttempts: 3\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_post?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: classpath:/mapper/**/*.xml\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n\r\nxxl:\r\n  job:\r\n    admin:\r\n      #调度中心部署跟地址：如调度中心集群部署存在多个地址则用逗号分隔。\r\n      #执行器将会使用该地址进行\"执行器心跳注册\"和\"任务结果回调\"。\r\n      addresses: http://localhost:13000/xxl-job-admin\r\n    #分别配置执行器的名称、ip地址、端口号\r\n    #注意：如果配置多个执行器时，防止端口冲突\r\n    executor:\r\n      appname: smart-post\r\n      ip: 127.0.0.1\r\n      port: 9999\r\n      #执行器运行日志文件存储的磁盘位置，需要对该路径拥有读写权限\r\n      logpath: /data/applogs/xxl-job/jobhandler\r\n      #执行器Log文件定期清理功能，指定日志保存天数，日志文件过期自动删除。限制至少保持3天，否则功能不生效；\r\n      #-1表示永不删除\r\n      logretentiondays: 7\r\nribbon:\r\n#指的是建立连接所用的时间，适用于网络状况正常的情况下，两端连接所用的时间\r\n  ReadTimeout: 5000\r\n#指的是建立连接后从服务器读取到可用资源所用的时间\r\n  ConnectTimeout: 5000\r\n','60505e3ac12a0640a00226506924446d','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,6,'smart-search-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 15000\r\nspring:\r\n  application:\r\n    name: smart-search\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','a5018c9a98e95b38c4494ea059a8020b','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,7,'smart-task-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 13000\r\n  servlet:\r\n    context-path: /xxl-job-admin\r\nspring:\r\n  freemarker:\r\n    charset: UTF-8\r\n    request-context-attribute: request\r\n    settings:\r\n      number_format: 0.##########\r\n    suffix: .ftl\r\n    templateLoaderPath: classpath:/templates/\r\n  mail:\r\n    from: xxx@qq.com\r\n    host: smtp.qq.com\r\n    password: xxx\r\n    port: 25\r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          socketFactory:\r\n            class: javax.net.ssl.SSLSocketFactory\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n    username: xxx@qq.com\r\n  mvc:\r\n    servlet:\r\n      load-on-startup: 0\r\n    static-path-pattern: /static/**\r\n  resources:\r\n    static-locations: classpath:/static/\r\n  application:\r\n    name: smart-task\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_task?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\nmanagement:\r\n  health:\r\n    mail:\r\n      enabled: false\r\n  server:\r\n    servlet:\r\n      context-path: /actuator\r\nxxl:\r\n  job:\r\n    accessToken: \'\'\r\n    i18n: zh_CN\r\n    logretentiondays: 30\r\n    triggerpool:\r\n      fast:\r\n        max: 200\r\n      slow:\r\n        max: 100','ef9040688df1ddb356024850b68d131f','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(0,8,'smart-thirdpart-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 9000\r\nspring:\r\n  application:\r\n    name: smart-thirdpart\r\n  mail:\r\n    host: smtp.qq.com \r\n    username: \r\n    password: \r\n    properties:\r\n      mail:\r\n        smtp:\r\n          auth: true\r\n          starttls:\r\n            enable: true\r\n            required: true\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  rabbitmq:\r\n    host: 127.0.0.1\r\n    username: guest\r\n    password: guest\r\n    virtual-host: /\r\n    port: 5672\r\n    listener:\r\n      simple:\r\n        retry:\r\n          enabled: true\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n\r\noss:\r\n  accessId: test\r\n  accessKey: test\r\n  endpoint: test\r\n  bucket: test\r\n  dir: test','cdce58e00abb6f933a0a34f7730eff87','2022-08-31 00:02:25','2022-08-31 00:02:25',NULL,'0:0:0:0:0:0:0:1','I','af0252de-6da5-45a8-905b-53efeb106bc1',''),(2,9,'smart-gateway-dev.yml','DEFAULT_GROUP','','server:\r\n  port: 8601\r\nspring:\r\n  application:\r\n    name: smart-gateway\r\n  cloud:\r\n    nacos:\r\n      discovery:\r\n        server-addr: localhost:8848\r\n    gateway:\r\n      routes:\r\n        - id: route_member\r\n          uri: \'lb://smart-member\'\r\n          predicates:\r\n            - Path=/api/v1/member/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_auth\r\n          uri: \'lb://smart-auth\'\r\n          predicates:\r\n            - Path=/api/v1/auth/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_post\r\n          uri: \'lb://smart-post\'\r\n          predicates:\r\n            - Path=/api/v1/post/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_notify\r\n          uri: \'lb://smart-notify\'\r\n          predicates:\r\n            - Path=/api/v1/notification/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_thirdpart\r\n          uri: \'lb://smart-thirdpart\'\r\n          predicates:\r\n            - Path=/api/v1/thirdpart/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_search\r\n          uri: \'lb://smart-search\'\r\n          predicates:\r\n            - Path=/api/v1/search/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n        - id: route_task\r\n          uri: \'lb://smart-task\'\r\n          predicates:\r\n            - Path=/api/v1/task/**\r\n          filters:\r\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\r\n  redis:\r\n    database: 0\r\n    host: 127.0.0.1\r\n    port: 6379\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        max-active: 200\r\n        max-wait: -1ms\r\n        max-idle: 10\r\n        min-idle: 0\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driver-class-name: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\r\n    username: root\r\n    password: root\r\nmybatis-plus:\r\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\r\n  global-config:\r\n    db-config:\r\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\r\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\r\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\r\nsa-token:\r\n  token-name: access_token\r\n  timeout: 2592000\r\n  activity-timeout: -1\r\n  is-concurrent: false\r\n  is-share: true\r\n  token-style: simple-uuid\r\n  is-log: true\r\n  isReadBody: true\r\n  isReadHead: true\r\n  isReadCookie: true\r\n  autoRenew: true\r\n  isPrint: false\r\n','62cc5ea86057090efe23ae34beb4e9c4','2022-09-03 00:46:30','2022-09-03 00:46:30','nacos','0:0:0:0:0:0:0:1','U','af0252de-6da5-45a8-905b-53efeb106bc1',''),(2,10,'smart-gateway-dev.yml','DEFAULT_GROUP','','server:\n  port: 8601\nspring:\n  application:\n    name: smart-gateway\n  cloud:\n    nacos:\n      discovery:\n        server-addr: localhost:8848\n    gateway:\n      routes:\n        - id: route_member\n          uri: \'lb://smart-member\'\n          predicates:\n            - Path=/api/v1/member/**\n          filters:\n            - \'RewritePath=/api/v1/member,/\'\n        - id: route_auth\n          uri: \'lb://smart-auth\'\n          predicates:\n            - Path=/api/v1/auth/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n        - id: route_post\n          uri: \'lb://smart-post\'\n          predicates:\n            - Path=/api/v1/post/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n        - id: route_notify\n          uri: \'lb://smart-notify\'\n          predicates:\n            - Path=/api/v1/notification/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n        - id: route_thirdpart\n          uri: \'lb://smart-thirdpart\'\n          predicates:\n            - Path=/api/v1/thirdpart/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n        - id: route_search\n          uri: \'lb://smart-search\'\n          predicates:\n            - Path=/api/v1/search/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n        - id: route_task\n          uri: \'lb://smart-task\'\n          predicates:\n            - Path=/api/v1/task/**\n          filters:\n            - \'RewritePath=/api/v1/(?<segment>.*),/$\\{segment}\'\n  redis:\n    database: 0\n    host: 127.0.0.1\n    port: 6379\n    timeout: 10s\n    lettuce:\n      pool:\n        max-active: 200\n        max-wait: -1ms\n        max-idle: 10\n        min-idle: 0\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://localhost:3306/smart_member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai\n    username: root\n    password: root\nmybatis-plus:\n  mapper-locations: \'classpath:/mapper/**/*.xml\'\n  global-config:\n    db-config:\n      logic-delete-field: deleted # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\nsa-token:\n  token-name: access_token\n  timeout: 2592000\n  activity-timeout: -1\n  is-concurrent: false\n  is-share: true\n  token-style: simple-uuid\n  is-log: true\n  isReadBody: true\n  isReadHead: true\n  isReadCookie: true\n  autoRenew: true\n  isPrint: false\n','4a27d5934dfe4ef90e9cc2c81427eca8','2022-09-03 00:50:30','2022-09-03 00:50:31','nacos','0:0:0:0:0:0:0:1','U','af0252de-6da5-45a8-905b-53efeb106bc1','');
/*!40000 ALTER TABLE `his_config_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('nacos','ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity` DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info` DISABLE KEYS */;
INSERT INTO `tenant_info` VALUES (1,'1','af0252de-6da5-45a8-905b-53efeb106bc1','dev','开发环境','nacos',1661875332622,1661875332622);
/*!40000 ALTER TABLE `tenant_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_member`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_member` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_member`;

--
-- Table structure for table `user_collect`
--

DROP TABLE IF EXISTS `user_collect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_collect` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `post_id` bigint NOT NULL COMMENT '文章id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_collect_id_uindex` (`id`),
  KEY `user_collect_user_id_post_id_index` (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collect`
--

LOCK TABLES `user_collect` WRITE;
/*!40000 ALTER TABLE `user_collect` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_collect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_follow`
--

DROP TABLE IF EXISTS `user_follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_follow` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `to_user_id` bigint NOT NULL COMMENT '关注目标的用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_follow_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户关注表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_follow`
--

LOCK TABLES `user_follow` WRITE;
/*!40000 ALTER TABLE `user_follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '呢称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '登录账号',
  `gender` varchar(1) NOT NULL DEFAULT '' COMMENT '性别 0 = 男 1= 女',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像地址',
  `intro` varchar(255) NOT NULL DEFAULT '' COMMENT '个人介绍',
  `fans` int NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `follow` int NOT NULL DEFAULT '0' COMMENT '关注数',
  `score` int NOT NULL DEFAULT '0' COMMENT '积分',
  `gitee` varchar(255) NOT NULL DEFAULT '' COMMENT 'gitee地址',
  `github` varchar(255) NOT NULL DEFAULT '' COMMENT 'github地址',
  `os` varchar(255) NOT NULL DEFAULT '' COMMENT '访问系统',
  `qq_number` varchar(20) NOT NULL DEFAULT '' COMMENT 'QQ号码',
  `comment_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 = 正常 1 = 不可评论',
  `browser` varchar(255) NOT NULL DEFAULT '' COMMENT '访问浏览器',
  `user_tag` tinyint NOT NULL DEFAULT '0' COMMENT '用户的标签 0 = 普通用户',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '用户状态 0 = 正常',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上一次登录的时间',
  `last_login_ip` varchar(50) NOT NULL DEFAULT '' COMMENT '上一次登录的ip',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1571562363390316546,1571562360907292673,'我是传奇','','','','https://img.llwstu.com/img/202208212352490.png','',0,0,0,'','','','',0,'',0,0,'2022-09-24 17:11:21','','2022-09-24 17:11:21','2022-09-18 18:10:40');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_notification`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_notification` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_notification`;

--
-- Table structure for table `t_event_remind`
--

DROP TABLE IF EXISTS `t_event_remind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_event_remind` (
  `id` bigint NOT NULL COMMENT '主键id',
  `action_type` tinyint NOT NULL COMMENT '操作类型',
  `source_id` bigint DEFAULT NULL COMMENT '事件源id',
  `source_type` tinyint DEFAULT NULL COMMENT '事件源类型',
  `source_content` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '事件源内容',
  `source_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '事件源标题',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读 0 = 未读 1= 已读',
  `sender_id` bigint DEFAULT NULL COMMENT '操作者uid',
  `recipient_id` bigint DEFAULT NULL COMMENT '接受通知的用户uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提醒时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '读取的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='事件提醒表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_event_remind`
--

LOCK TABLES `t_event_remind` WRITE;
/*!40000 ALTER TABLE `t_event_remind` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_event_remind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_post`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_post` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_post`;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL COMMENT '板块id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '板块名称',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '板块图标',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '板块链接',
  `intro` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '板块介绍',
  `state` int NOT NULL DEFAULT '0' COMMENT '板块状态 0 = 正常 1 = 关闭',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_section_uid_uindex` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='板块表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'前端',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-04 01:36:40'),(2,'后端',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-04 01:36:41'),(3,'Android',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-04 01:36:41'),(4,'算法',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-04 01:36:42'),(5,'人工智能',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-07 06:34:22'),(6,'开发工具',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-07 06:34:23'),(7,'代码人生',NULL,NULL,NULL,0,'2022-09-25 08:44:48','2022-03-07 06:34:23');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_tag`
--

DROP TABLE IF EXISTS `category_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_tag` (
  `id` bigint NOT NULL,
  `category_id` bigint NOT NULL COMMENT '板块id',
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_section_tag_uid_uindex` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='分类标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_tag`
--

LOCK TABLES `category_tag` WRITE;
/*!40000 ALTER TABLE `category_tag` DISABLE KEYS */;
INSERT INTO `category_tag` VALUES (1,2,1,'2022-03-04 08:21:42','2022-03-04 08:21:44'),(2,2,3,'2022-03-04 08:22:20','2022-03-04 08:22:21'),(3,2,4,'2022-03-08 03:33:56','2022-03-08 03:34:06'),(4,2,5,'2022-03-08 03:33:59','2022-03-08 03:34:06'),(5,2,6,'2022-03-08 03:34:02','2022-03-08 03:34:06'),(6,2,7,'2022-03-08 03:34:02','2022-03-08 03:34:06'),(7,2,8,'2022-03-08 03:34:02','2022-03-08 03:34:06'),(8,2,9,'2022-03-08 03:34:02','2022-03-08 03:34:06'),(9,2,10,'2022-03-08 03:34:02','2022-03-08 03:34:06'),(11,2,12,'2022-03-08 03:34:04','2022-03-08 03:34:06'),(12,2,13,'2022-03-08 03:34:04','2022-03-08 03:34:06'),(13,2,14,'2022-03-08 03:34:04','2022-03-08 03:34:06'),(14,2,15,'2022-03-08 03:34:04','2022-03-08 03:34:06'),(15,1,2,'2022-03-08 08:22:25','2022-03-08 08:22:26');
/*!40000 ALTER TABLE `category_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint DEFAULT NULL COMMENT '发送方id',
  `post_id` bigint DEFAULT NULL COMMENT '文章id',
  `to_user_id` bigint DEFAULT NULL COMMENT '接收方id',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评论内容',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0 = 正常显示 1 = 隐藏',
  `type` tinyint(1) NOT NULL COMMENT '回复类型 0 = 一级评论 1 = 评论中回复',
  `first_comment_id` bigint DEFAULT NULL COMMENT '一级评论uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除标记 0 = 正常 1 = 删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `comment_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章回复表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL,
  `category_id` bigint DEFAULT NULL COMMENT '板块id',
  `author_id` bigint NOT NULL COMMENT '作者id',
  `title` varchar(255) NOT NULL COMMENT '文章题目',
  `content` longtext NOT NULL COMMENT '文章内容',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '文章状态 0 = 正常',
  `collect_count` int NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `thumb_count` int NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数量',
  `is_publish` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0 = 不公布  1 = 公布',
  `summary` varchar(255) DEFAULT NULL COMMENT '文章摘要',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除 0 = 未删除 1 = 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `post_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tag` (
  `id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `post_tag_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tag`
--

LOCK TABLES `post_tag` WRITE;
/*!40000 ALTER TABLE `post_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(1000) NOT NULL COMMENT '标签内容',
  `state` tinyint DEFAULT '0' COMMENT '标签状态 0 = 正常',
  `intro` varchar(2048) DEFAULT NULL COMMENT '标签介绍',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序字段',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `tag_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,'Java',0,NULL,0,'2022-03-04 01:35:41','2022-03-04 01:35:42'),(2,'Vue',0,NULL,0,'2022-03-04 01:36:05','2022-03-04 01:36:06'),(3,'.Net',0,NULL,0,'2022-03-04 01:36:06','2022-03-04 01:36:07'),(4,'C#',0,NULL,0,'2022-03-04 01:36:07','2022-03-04 01:36:08'),(5,'算法',0,NULL,0,'2022-03-07 06:36:52','2022-03-07 06:36:58'),(6,'python',0,NULL,0,'2022-03-07 06:36:53','2022-03-07 06:36:59'),(7,'Go',0,NULL,0,'2022-03-07 06:36:53','2022-03-07 06:36:59'),(8,'MYSQL',0,NULL,0,'2022-03-07 06:36:54','2022-03-07 06:37:00'),(9,'数据库',0,NULL,0,'2022-03-07 06:36:54','2022-03-07 06:37:00'),(10,'Spring Boot',0,NULL,0,'2022-03-07 06:36:55','2022-03-07 06:37:01'),(11,'Redis',0,NULL,0,'2022-03-07 06:36:56','2022-03-07 06:37:01'),(12,'Spring',0,NULL,0,'2022-03-07 06:36:56','2022-03-07 06:37:02'),(13,'架构',0,NULL,0,'2022-03-07 06:36:56','2022-03-07 06:37:02'),(14,'面试',0,NULL,0,'2022-03-07 06:36:57','2022-03-07 06:37:03'),(15,'Linux',0,NULL,0,'2022-03-07 06:36:58','2022-03-07 06:37:04'),(16,'前端',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(17,'JavaScript',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(18,'React.js',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(19,'CSS',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(20,'LeetCode',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(21,'Node.js',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(22,'TypeScript',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(23,'Webpack',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(24,'架构',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(25,'Flutter',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(26,'后端',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(27,'微信小程序',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(28,'Kotlin',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(29,'操作系统',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(30,'架构',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15'),(31,'性能优化',0,NULL,0,'2022-04-07 14:34:15','2022-04-07 14:34:15');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thumb`
--

DROP TABLE IF EXISTS `thumb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thumb` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '点赞用户id',
  `to_user_id` bigint NOT NULL COMMENT '点赞目标的id',
  `post_id` bigint NOT NULL COMMENT '点赞所在的帖子id',
  `to_id` bigint NOT NULL COMMENT '点赞内容的id',
  `type` int NOT NULL COMMENT '点赞类型 0 = 评论 1 = 文章',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `thumb_id_uindex` (`id`),
  KEY `thumb_user_id_type_post_id_index` (`user_id`,`type`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thumb`
--

LOCK TABLES `thumb` WRITE;
/*!40000 ALTER TABLE `thumb` DISABLE KEYS */;
/*!40000 ALTER TABLE `thumb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_task`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_task` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_task`;

--
-- Table structure for table `xxl_job_group`
--

DROP TABLE IF EXISTS `xxl_job_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint NOT NULL DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_group`
--

LOCK TABLES `xxl_job_group` WRITE;
/*!40000 ALTER TABLE `xxl_job_group` DISABLE KEYS */;
INSERT INTO `xxl_job_group` VALUES (1,'smart-post','文章信息执行器',0,'http://192.168.6.198:10001/,http://192.168.6.198:9999/','2022-08-30 02:16:29');
/*!40000 ALTER TABLE `xxl_job_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_info`
--

DROP TABLE IF EXISTS `xxl_job_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int NOT NULL DEFAULT '0' COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint NOT NULL DEFAULT '0' COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint NOT NULL DEFAULT '0' COMMENT '上次调度时间',
  `trigger_next_time` bigint NOT NULL DEFAULT '0' COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_info`
--

LOCK TABLES `xxl_job_info` WRITE;
/*!40000 ALTER TABLE `xxl_job_info` DISABLE KEYS */;
INSERT INTO `xxl_job_info` VALUES (1,1,'持久化点赞信息缓存','2018-11-03 22:21:31','2022-06-17 16:27:48','admin','','CRON','0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?','DO_NOTHING','FIRST','transThumbFromRedis2DHandler','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2018-11-03 22:21:31','',1,1661796900000,1661797200000),(2,1,'持久化文章评论点赞总数','2022-03-16 16:02:05','2022-06-17 16:27:40','Alickx','','CRON','0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?','DO_NOTHING','FIRST','transCountFromRedis2DHandler','','SERIAL_EXECUTION',0,0,'BEAN','','GLUE代码初始化','2022-03-16 16:02:05','',1,1661796900000,1661797200000);
/*!40000 ALTER TABLE `xxl_job_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_lock`
--

DROP TABLE IF EXISTS `xxl_job_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_lock` (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_lock`
--

LOCK TABLES `xxl_job_lock` WRITE;
/*!40000 ALTER TABLE `xxl_job_lock` DISABLE KEYS */;
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');
/*!40000 ALTER TABLE `xxl_job_lock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log`
--

DROP TABLE IF EXISTS `xxl_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `job_group` int NOT NULL COMMENT '执行器主键ID',
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int NOT NULL DEFAULT '0' COMMENT '失败重试次数',
  `trigger_time` datetime DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '调度-日志',
  `handle_time` datetime DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '执行-日志',
  `alarm_status` tinyint NOT NULL DEFAULT '0' COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `I_trigger_time` (`trigger_time`) USING BTREE,
  KEY `I_handle_code` (`handle_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5134 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log`
--

LOCK TABLES `xxl_job_log` WRITE;
/*!40000 ALTER TABLE `xxl_job_log` DISABLE KEYS */;
INSERT INTO `xxl_job_log` VALUES (5114,1,1,'http://192.168.31.153:10000/','transThumbFromRedis2DHandler','',NULL,0,'2022-08-15 23:35:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:35:00',200,'',0),(5115,1,2,'http://192.168.31.153:10000/','transCountFromRedis2DHandler','',NULL,0,'2022-08-15 23:35:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:35:01',200,'',0),(5116,1,2,'http://192.168.31.153:10000/','transCountFromRedis2DHandler','',NULL,0,'2022-08-15 23:40:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:40:00',200,'',0),(5117,1,1,'http://192.168.31.153:10000/','transThumbFromRedis2DHandler','',NULL,0,'2022-08-15 23:40:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:40:00',200,'',0),(5118,1,1,'http://192.168.31.153:10000/','transThumbFromRedis2DHandler','',NULL,0,'2022-08-15 23:45:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:45:00',200,'',0),(5119,1,2,'http://192.168.31.153:10000/','transCountFromRedis2DHandler','',NULL,0,'2022-08-15 23:45:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:45:00',200,'',0),(5120,1,2,'http://192.168.31.153:10000/','transCountFromRedis2DHandler','',NULL,0,'2022-08-15 23:50:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:50:00',200,'',0),(5121,1,1,'http://192.168.31.153:10000/','transThumbFromRedis2DHandler','',NULL,0,'2022-08-15 23:50:00',200,'任务触发类型：Cron触发<br>调度机器：192.168.31.153<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.31.153:10000/, http://192.168.31.153:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.31.153:10000/<br>code：200<br>msg：null','2022-08-15 23:50:00',200,'',0),(5122,1,2,NULL,'transCountFromRedis2DHandler','',NULL,0,'2022-08-30 01:50:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5123,1,1,NULL,'transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 01:50:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5124,1,1,NULL,'transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 01:55:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5125,1,2,NULL,'transCountFromRedis2DHandler','',NULL,0,'2022-08-30 01:55:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5126,1,1,NULL,'transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 02:00:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5127,1,2,NULL,'transCountFromRedis2DHandler','',NULL,0,'2022-08-30 02:00:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5128,1,2,NULL,'transCountFromRedis2DHandler','',NULL,0,'2022-08-30 02:05:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5129,1,1,NULL,'transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 02:05:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5130,1,2,NULL,'transCountFromRedis2DHandler','',NULL,0,'2022-08-30 02:10:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5131,1,1,NULL,'transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 02:10:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>',NULL,0,NULL,2),(5132,1,2,'http://192.168.6.198:9999/','transCountFromRedis2DHandler','',NULL,0,'2022-08-30 02:15:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.6.198:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.6.198:9999/<br>code：500<br>msg：job handler [transCountFromRedis2DHandler] not found.',NULL,0,NULL,2),(5133,1,1,'http://192.168.6.198:9999/','transThumbFromRedis2DHandler','',NULL,0,'2022-08-30 02:15:00',500,'任务触发类型：Cron触发<br>调度机器：192.168.6.198<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.6.198:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.6.198:9999/<br>code：500<br>msg：job handler [transThumbFromRedis2DHandler] not found.',NULL,0,NULL,2);
/*!40000 ALTER TABLE `xxl_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_log_report`
--

DROP TABLE IF EXISTS `xxl_job_log_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_log_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime DEFAULT NULL COMMENT '调度-时间',
  `running_count` int NOT NULL DEFAULT '0' COMMENT '运行中-日志数量',
  `suc_count` int NOT NULL DEFAULT '0' COMMENT '执行成功-日志数量',
  `fail_count` int NOT NULL DEFAULT '0' COMMENT '执行失败-日志数量',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_trigger_day` (`trigger_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_log_report`
--

LOCK TABLES `xxl_job_log_report` WRITE;
/*!40000 ALTER TABLE `xxl_job_log_report` DISABLE KEYS */;
INSERT INTO `xxl_job_log_report` VALUES (1,'2022-03-06 00:00:00',0,2,0,NULL),(2,'2022-03-05 00:00:00',0,0,0,NULL),(3,'2022-03-04 00:00:00',0,0,0,NULL),(4,'2022-03-07 00:00:00',0,6,0,NULL),(5,'2022-03-08 00:00:00',0,18,1,NULL),(6,'2022-03-13 00:00:00',0,18,2,NULL),(7,'2022-03-12 00:00:00',0,0,0,NULL),(8,'2022-03-11 00:00:00',0,0,0,NULL),(9,'2022-03-14 00:00:00',0,19,0,NULL),(10,'2022-03-15 00:00:00',0,7,13,NULL),(11,'2022-03-16 00:00:00',0,18,1,NULL),(12,'2022-03-17 00:00:00',0,32,2,NULL),(13,'2022-03-18 00:00:00',0,10,0,NULL),(14,'2022-03-19 00:00:00',0,8,4,NULL),(15,'2022-03-20 00:00:00',0,0,0,NULL),(16,'2022-03-21 00:00:00',0,7,1,NULL),(17,'2022-03-22 00:00:00',0,21,15,NULL),(18,'2022-03-23 00:00:00',0,14,15,NULL),(19,'2022-03-24 00:00:00',0,11,3,NULL),(20,'2022-03-25 00:00:00',0,11,7,NULL),(21,'2022-03-26 00:00:00',0,5,5,NULL),(22,'2022-03-27 00:00:00',0,8,10,NULL),(23,'2022-03-28 00:00:00',0,13,12,NULL),(24,'2022-03-29 00:00:00',0,2,0,NULL),(25,'2022-03-31 00:00:00',0,0,2,NULL),(26,'2022-03-30 00:00:00',0,0,0,NULL),(27,'2022-04-01 00:00:00',0,0,28,NULL),(28,'2022-04-02 00:00:00',0,0,74,NULL),(29,'2022-04-06 00:00:00',0,0,14,NULL),(30,'2022-04-05 00:00:00',0,0,0,NULL),(31,'2022-04-04 00:00:00',0,0,0,NULL),(32,'2022-04-07 00:00:00',0,0,34,NULL),(33,'2022-04-14 00:00:00',0,0,2,NULL),(34,'2022-04-13 00:00:00',0,0,0,NULL),(35,'2022-04-12 00:00:00',0,0,0,NULL),(36,'2022-04-25 00:00:00',0,2,4,NULL),(37,'2022-04-24 00:00:00',0,0,0,NULL),(38,'2022-04-23 00:00:00',0,0,0,NULL),(39,'2022-05-08 00:00:00',0,3,3,NULL),(40,'2022-05-07 00:00:00',0,0,0,NULL),(41,'2022-05-06 00:00:00',0,0,0,NULL),(42,'2022-05-23 00:00:00',0,2,10,NULL),(43,'2022-05-22 00:00:00',0,0,0,NULL),(44,'2022-05-21 00:00:00',0,0,0,NULL),(45,'2022-06-06 00:00:00',0,0,0,NULL),(46,'2022-06-05 00:00:00',0,0,0,NULL),(47,'2022-06-04 00:00:00',0,0,0,NULL),(48,'2022-06-07 00:00:00',0,0,0,NULL),(49,'2022-06-08 00:00:00',0,41,13,NULL),(50,'2022-06-09 00:00:00',0,1506,6,NULL),(51,'2022-06-10 00:00:00',0,1630,20,NULL),(52,'2022-06-12 00:00:00',0,372,2,NULL),(53,'2022-06-11 00:00:00',0,0,0,NULL),(54,'2022-06-15 00:00:00',0,96,2,NULL),(55,'2022-06-14 00:00:00',0,0,0,NULL),(56,'2022-06-13 00:00:00',0,0,0,NULL),(57,'2022-06-16 00:00:00',0,384,2,NULL),(58,'2022-06-17 00:00:00',0,80,12,NULL),(59,'2022-06-18 00:00:00',0,48,0,NULL),(60,'2022-06-19 00:00:00',0,32,0,NULL),(61,'2022-07-03 00:00:00',0,22,2,NULL),(62,'2022-07-02 00:00:00',0,0,0,NULL),(63,'2022-07-01 00:00:00',0,0,0,NULL),(64,'2022-07-05 00:00:00',0,48,0,NULL),(65,'2022-07-04 00:00:00',0,0,0,NULL),(66,'2022-07-06 00:00:00',0,6,0,NULL),(67,'2022-07-09 00:00:00',0,38,0,NULL),(68,'2022-07-08 00:00:00',0,0,0,NULL),(69,'2022-07-07 00:00:00',0,0,0,NULL),(70,'2022-07-10 00:00:00',0,32,0,NULL),(71,'2022-07-24 00:00:00',0,14,0,NULL),(72,'2022-07-23 00:00:00',0,0,0,NULL),(73,'2022-07-22 00:00:00',0,0,0,NULL),(74,'2022-08-15 00:00:00',0,8,0,NULL),(75,'2022-08-14 00:00:00',0,0,0,NULL),(76,'2022-08-13 00:00:00',0,0,0,NULL),(77,'2022-08-30 00:00:00',0,0,12,NULL),(78,'2022-08-29 00:00:00',0,0,0,NULL),(79,'2022-08-28 00:00:00',0,0,0,NULL);
/*!40000 ALTER TABLE `xxl_job_log_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_logglue`
--

DROP TABLE IF EXISTS `xxl_job_logglue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_logglue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_logglue`
--

LOCK TABLES `xxl_job_logglue` WRITE;
/*!40000 ALTER TABLE `xxl_job_logglue` DISABLE KEYS */;
/*!40000 ALTER TABLE `xxl_job_logglue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_registry`
--

DROP TABLE IF EXISTS `xxl_job_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_registry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `i_g_k_v` (`registry_group`,`registry_key`,`registry_value`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=427 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_registry`
--

LOCK TABLES `xxl_job_registry` WRITE;
/*!40000 ALTER TABLE `xxl_job_registry` DISABLE KEYS */;
INSERT INTO `xxl_job_registry` VALUES (425,'EXECUTOR','smart-post','http://192.168.6.198:9999/','2022-08-30 02:16:10'),(426,'EXECUTOR','smart-post','http://192.168.6.198:10001/','2022-08-30 02:16:13');
/*!40000 ALTER TABLE `xxl_job_registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xxl_job_user`
--

DROP TABLE IF EXISTS `xxl_job_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xxl_job_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `i_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xxl_job_user`
--

LOCK TABLES `xxl_job_user` WRITE;
/*!40000 ALTER TABLE `xxl_job_user` DISABLE KEYS */;
INSERT INTO `xxl_job_user` VALUES (1,'admin','e10adc3949ba59abbe56e057f20f883e',1,NULL);
/*!40000 ALTER TABLE `xxl_job_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-26  0:39:18
