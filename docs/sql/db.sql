-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_auth
-- ------------------------------------------------------
-- Server version	8.0.30

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
  `certificate` varchar(20) NOT NULL DEFAULT '' COMMENT '密码凭证(站内的保存密码，站外的不保存或保存token)',
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
  `permission_uid` bigint NOT NULL COMMENT '权限id',
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
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info';
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
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='增加租户字段';
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
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_beta';
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
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_info_tag';
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
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='config_tag_relation';
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
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='集群、各Group容量信息表';
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
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
  KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
  KEY `idx_did` (`data_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='多租户改造';
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
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='租户容量信息表';
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
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`) USING BTREE,
  KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='tenant_info';
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
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '呢称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录账号',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '性别 0 = 男 1= 女',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像地址',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人介绍',
  `fans` int NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `follow` int NOT NULL DEFAULT '0' COMMENT '关注数',
  `score` int NOT NULL DEFAULT '0' COMMENT '积分',
  `gitee` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'gitee地址',
  `github` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'github地址',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '访问系统',
  `qq_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'QQ号码',
  `comment_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 = 正常 1 = 不可评论',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '访问浏览器',
  `user_tag` tinyint DEFAULT '0' COMMENT '用户的标签 0 = 普通用户',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '用户状态 0 = 正常',
  `last_login_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上一次登录的时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '上一次登录的ip',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_member__email_index` (`email`) USING BTREE,
  UNIQUE KEY `user_profile_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1499226795866386433,'我是小号','249095581@qq.com','0',NULL,'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-08/87c6cf2e6ae14f688a94cf487780a15920180905151420_k5Ueh.jpeg','我就是一个小妖怪。',0,0,0,NULL,NULL,'Windows',NULL,0,'Chrome',0,0,'2022-06-16 08:01:38','0:0:0:0:0:0:0:1','2022-06-08 06:37:05','2022-03-03 03:34:57'),(1500659300137959426,'Gardero','799833026@qq.com','2',NULL,'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-03-18/cf2c4d83644941ef8a61175f7d5284ee微信图片_20220318163217.jpg',NULL,0,0,0,NULL,NULL,NULL,NULL,0,NULL,0,0,NULL,NULL,'2022-03-18 08:35:32','2022-03-07 02:27:13'),(1506910849124057090,'我是管理员','89991829@qq.com','0',NULL,'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-07/7fd18ec14e5b413aaacb1522c6023764微信图片_20220414145043.jpg','我就是一个可爱的人捏',0,0,0,NULL,NULL,'Windows',NULL,0,'Chrome',0,0,'2022-08-15 15:42:14','0:0:0:0:0:0:0:1','2022-08-15 15:50:52','2022-03-24 08:28:38'),(1534144417055760386,'新人_oeqw9','1944716886@qq.com','0',NULL,'https://passjava-oss-bucket.oss-cn-guangzhou.aliyuncs.com/img/2022-06-07/503a85ac7dd942f3a610edb6a87ea64220180905151708_fTUsh.jpeg','我是王旭嘉',0,0,0,NULL,NULL,'Windows',NULL,0,'Chrome',0,0,'2022-06-07 12:05:43','0:0:0:0:0:0:0:1','2022-06-07 12:33:18','2022-06-07 12:05:07');
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
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
INSERT INTO `category` VALUES (1,'前端',NULL,NULL,NULL,'2022-03-04 01:36:38','2022-03-04 01:36:40'),(2,'后端',NULL,NULL,NULL,'2022-03-04 01:36:39','2022-03-04 01:36:41'),(3,'Android',NULL,NULL,NULL,'2022-03-04 01:36:39','2022-03-04 01:36:41'),(4,'算法',NULL,NULL,NULL,'2022-03-04 01:36:40','2022-03-04 01:36:42'),(5,'人工智能',NULL,NULL,NULL,'2022-03-07 06:34:21','2022-03-07 06:34:22'),(6,'开发工具',NULL,NULL,NULL,'2022-03-07 06:34:21','2022-03-07 06:34:23'),(7,'代码人生',NULL,NULL,NULL,'2022-03-07 06:34:22','2022-03-07 06:34:23');
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
  `category_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '状态',
  `type` tinyint(1) NOT NULL COMMENT '回复类型 0 = 一级评论 1 = 评论中回复',
  `first_comment_id` bigint DEFAULT NULL COMMENT '一级评论uid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `comment_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章回复表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1512073311142146049,1506910849124057090,1511898013389856769,1506910849124057090,'测试一下',0,0,NULL,'2022-04-07 14:22:25','2022-04-07 14:22:25'),(1512074386230267905,1506910849124057090,1511898013389856769,1506910849124057090,'测试我的页面',1,1,1512073311142146000,'2022-04-07 14:26:41','2022-04-07 14:26:41'),(1512077407244832770,1506910849124057090,1512076635195101186,1506910849124057090,'测试评论',0,0,NULL,'2022-04-07 14:38:42','2022-04-07 14:38:42'),(1514610542369136642,1506910849124057090,1514610398072496130,1506910849124057090,'哈哈',0,0,NULL,'2022-04-14 14:24:28','2022-04-14 14:24:28'),(1514610608454590466,1506910849124057090,1514610398072496130,1506910849124057090,'哒哒哒',0,1,1514610542369136600,'2022-04-14 14:24:44','2022-04-14 14:24:44'),(1514610628943765506,1506910849124057090,1514610398072496130,1506910849124057090,'哒哒哒',0,1,NULL,'2022-04-14 14:24:49','2022-04-14 14:24:49'),(1514610740084432898,1506910849124057090,1514610398072496130,1506910849124057090,'哈哈哈',0,1,NULL,'2022-04-14 14:25:15','2022-04-14 14:25:15'),(1514610925858545665,1506910849124057090,1514610398072496130,1506910849124057090,'sha逼',1,0,NULL,'2022-04-14 14:25:59','2022-04-14 14:25:59'),(1518421774305865730,1506910849124057090,1518416862733938689,1506910849124057090,'这是一条一级评论',0,0,NULL,'2022-04-25 02:48:57','2022-04-25 02:48:57'),(1518421820850057218,1506910849124057090,1518416862733938689,1506910849124057090,'这是一个二级评论',0,1,1518421774305865700,'2022-04-25 02:49:08','2022-04-25 02:49:08'),(1518421849019002882,1506910849124057090,1518416862733938689,1506910849124057090,'我在回复你',1,1,NULL,'2022-04-25 02:49:14','2022-04-25 02:49:14'),(1523245539346972673,1506910849124057090,1518416862733938689,1506910849124057090,'这是二级',0,1,NULL,'2022-05-08 10:16:52','2022-05-08 10:16:52'),(1523246112293093377,1506910849124057090,1518416862733938689,1506910849124057090,'测试',0,1,1518421774305865700,'2022-05-08 10:19:08','2022-05-08 10:19:08'),(1523246858124230657,1506910849124057090,1518416862733938689,1506910849124057090,'123',0,1,1518421774305865700,'2022-05-08 10:22:06','2022-05-08 10:22:06'),(1523255060769374209,1506910849124057090,1518416862733938689,1506910849124057090,'1',0,1,1523245539346972673,'2022-05-08 10:54:42','2022-05-08 10:54:42'),(1523255487405588482,1506910849124057090,1518416862733938689,1506910849124057090,'2',1,1,NULL,'2022-05-08 10:56:24','2022-05-08 10:56:24'),(1523255857536139265,1506910849124057090,1518416862733938689,1506910849124057090,'2',0,1,1523245539346972673,'2022-05-08 10:57:52','2022-05-08 10:57:52'),(1523255866511949825,1506910849124057090,1518416862733938689,1506910849124057090,'3',0,1,1523245539346972673,'2022-05-08 10:57:54','2022-05-08 10:57:54'),(1533655498602090498,1506910849124057090,1533654884224634881,1506910849124057090,'如果你有任何的问题，都可以来私信我，我会查看你们的来信。',0,0,NULL,'2022-06-06 03:42:19','2022-06-06 03:42:19'),(1533977537674317826,1499226795866386433,1533654884224634881,1506910849124057090,'很高兴认识你',0,0,NULL,'2022-06-07 01:02:00','2022-06-07 01:02:00'),(1533977827307786241,1506910849124057090,1533654884224634881,1499226795866386433,'我也很高兴认识你',0,1,1533977537674317826,'2022-06-07 01:03:09','2022-06-07 01:03:09'),(1534142076654989313,1499226795866386433,1533799764712239106,1506910849124057090,'你的文章真好看',0,0,NULL,'2022-06-07 11:55:49','2022-06-07 11:55:49'),(1534142567963176962,1506910849124057090,1533799764712239106,1499226795866386433,'是啊，我也是这么觉得的',0,1,1534142076654989313,'2022-06-07 11:57:46','2022-06-07 11:57:46'),(1534144653324988418,1534144417055760386,1533800812499709953,1506910849124057090,'你在吗',1,0,NULL,'2022-06-07 12:06:03','2022-06-07 12:06:03'),(1534144706231939073,1534144417055760386,1533800812499709953,1506910849124057090,'点点滴滴',0,0,NULL,'2022-06-07 12:06:16','2022-06-07 12:06:16'),(1534145791092862978,1506910849124057090,1533800952211976193,1506910849124057090,'不喜欢这个输入框\n\n得到\n\n\n\n哒哒哒',1,0,NULL,'2022-06-07 12:10:34','2022-06-07 12:10:34'),(1534424109323235330,1506910849124057090,1534424017899991042,1506910849124057090,'测试发帖',0,0,NULL,'2022-06-08 06:36:31','2022-06-08 06:36:31'),(1534424312247857153,1499226795866386433,1534424017899991042,1506910849124057090,'测试回复',0,0,NULL,'2022-06-08 06:37:19','2022-06-08 06:37:19'),(1534424345378664450,1499226795866386433,1534424017899991042,1499226795866386433,'给自己回复',0,1,1534424312247857153,'2022-06-08 06:37:27','2022-06-08 06:37:27'),(1534424373933486081,1499226795866386433,1534424017899991042,1506910849124057090,'给你回复',0,1,1534424109323235330,'2022-06-08 06:37:34','2022-06-08 06:37:34'),(1551117861630545922,1506910849124057090,1533654884224634881,1506910849124057090,'无言独上西楼，月如钩',0,0,NULL,'2022-07-24 08:11:31','2022-07-24 08:11:31'),(1559204559581102081,1506910849124057090,1538082038932869122,1506910849124057090,'我回复了你一下',1,0,NULL,'2022-08-15 15:45:10','2022-08-15 15:45:10');
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
  `category_id` bigint DEFAULT NULL,
  `member_id` bigint NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '文章状态 0 = 正常',
  `collect_count` int NOT NULL DEFAULT '0',
  `thumb_count` int NOT NULL DEFAULT '0',
  `is_publish` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '0 = 不公布  1 = 公布',
  `comment_count` int NOT NULL DEFAULT '0',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `post_id_uindex` (`id`),
  KEY `post_status_is_publish_index` (`status`,`is_publish`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1511898013389856769,2,1506910849124057090,'测试一下vue3','## 测试一下vue3\n测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3\n',0,0,0,'1',0,'测试一下vue3测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3，测试一下vue3','2022-04-07 10:45:51','2022-04-07 10:45:51',1),(1512076635195101186,2,1506910849124057090,'测试文档','## 测试文档\n测试一下文档,测试一下文档,测试一下文档测试一下文档,测试一下文档,测试一下文档,不能低于50字',0,0,0,'1',0,'测试文档测试一下文档,测试一下文档,测试一下文档测试一下文档,测试一下文档,测试一下文档,不能低于50字','2022-04-07 22:35:38','2022-04-07 22:35:38',1),(1514610151573250049,1,1506910849124057090,'傻逼','哈哈哈，那就是一个大傻逼！！ ~~### 㔖 滴答滴答滴答滴答滴答滴答滴答滴答答阿发呜呜呜呜呜呜呜呜呜呜呜呜呜呜呜娃娃',3,0,0,'1',0,'傻逼','2022-04-14 22:22:55','2022-04-14 22:22:55',0),(1514610398072496130,1,1506910849124057090,'的哒哒哒','滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴呆呆呆呆呆呆地多呆呆呆呆呆呆地多多多多多多多多多多多多多多地地道道的',0,0,0,'1',0,'哒哒哒','2022-04-14 22:23:54','2022-04-14 22:23:54',1),(1518416862733938689,2,1506910849124057090,'实践 - 搭建Redis一主两从三哨兵','### 实践 - 搭建Redis一主两从三哨兵\n\n#### 原因：\n\n最近在复习Redis的时候，学习到了为了提高Redis集群的**高可用性**，有一个模式为**哨兵模式**。**哨兵模式**的作用是为了在主节点出现阻塞或者错误，无法接收数据的时候，及时将**从节点切换为主节点**，由此保证Redis集群能够保持正常状态，保持高可用。  \n\n但是尽管引入**哨兵模式**能够提高集群的高可用性，但是随之带来的有**数据丢失**，**数据不一致**问题。这些问题的原因有可能是因为主从异步复制的时候，主节点挂了，导致子节点接收**数据不完整**，出现**数据不一致问题**。也有可能是因为出现了**脑裂问题**，导致数据丢失问题等等。  \n\n但是知道概念后，就需要实践一下才会知道这些问题出现的原因，避免纸上谈兵。\n\n\n\n#### 部署节点：\n\n那么一共需要的是六个节点，也就是要启动六个Redis服务来模拟集群，那这里我使用Docker-Compose来实现集群。  \n\n##### 1. 创建文件夹\n\n一共是六个节点，分别是一个**主机**，两个**从机**，三个**哨兵**。所以我们需要创建**六个文件夹**来对应这六个节点。  \n\n这是最终创建的结构树。  \n\n```txt\n.\n|-- docker-compose.yml\n|-- master\n|   |-- conf\n|   |   `-- redis.conf\n|   `-- data\n|       |-- dump.rdb\n|       `-- nodes.conf\n|-- sentinel1\n|   |-- conf\n|   |   `-- sentinel.conf\n|   `-- data\n|-- sentinel2\n|   |-- conf\n|   |   `-- sentinel.conf\n|   `-- data\n|-- sentinel3\n|   |-- conf\n|   |   `-- sentinel.conf\n|   `-- data\n|-- slave1\n|   |-- conf\n|   |   `-- redis.conf\n|   `-- data\n|       `-- dump.rdb\n`-- slave2\n    |-- conf\n    |   `-- redis.conf\n    `-- data\n        `-- dump.rdb\n```\n\n从Redis官网获取最新的**Redis.conf**，并复制到**master，slave1，slave2**的conf文件夹中。并获取**sentinel.conf**复制到**sentinel1，sentinel2，sentinel3**的conf文件夹中。  \n\n\n\n##### 2.编写配置：\n\n然后修改一下配置。\n\n- 主机\n\n```conf\nbind 0.0.0.0 #设置所有地址访问\nprotected-mode yes #这个是默认开启的，也就是开启安全模式\nrequirepass 123456 #设置密码\n```\n\n以上的配置无论是主机还是从机都要配置，这是一样的。\n\n- 从机\n\n```conf\nreplica-read-only yes   #这个配置是从机只能读，不能写\nreplicaof 172.20.1.2 6379 #配置主机的ip和端口 在redis5.0以前则是salveof配置\nmasterauth 123456   #因为主节点设置了密码，必须设置这个，否则会连不上主节点\n```\n\n这里说一下，在Vi下编辑文档，查找，另起一行的命令如下。\n\n```shell\n/你要查找的词  #按N往上找 按n往下找\no #直接在当前行下另起一行\n```\n\n- 哨兵\n\n```conf\n#这个配置的作用就是设置监听的master节点的信息，mymaster可以换成符合规定的其他名字，后面的2是指当有两个sentinel认为#这个master失效了，才会认为失效，从而进行主从切换\nsentinel monitor mymaster 172.20.1.2 6379 2\n\n#配置主从的的密码，注意mymaster要对应刚才的配置项\nsentinel auth-pass mymaster 123456 \n\n#这个配置项指定了需要多少失效时间，一个master才会被这个sentinel主观地认为是不可用的。 单位是毫秒，默认为30秒\nsentinel down-after-milliseconds mymaster 30000\n\n#这个配置项指定了在发生failover主备切换时最多可以有多少个slave同时对新的master进行 同步，可以通过将这个值设为 1 来保证每次只有一个slave 处于不能处理命令请求的状态。值越大，slave复制的越快，但同时也对主节点的网络和硬盘负载造成压力\nsentinel parallel-syncs mymaster 1\n\n#定义故障切换超时时间。默认180000，单位秒，即3min。\nsentinel failover-timeout mymaster 180000\n\n#设置运行期是不能改变notification-script和 client-reconfig-script ，避免一些安全问题\nsentinel deny-scripts-reconfig yes\n```\n\n\n\n##### 3.编写docker-compose文件\n\n然后就是编写**docker-compose**文件了。\n\n```yaml\nversion: \'3\'\nservices:\n  master:\n    image: redis:latest\n    container_name: redis_master  #master节点\n    volumes:\n      - ./master/conf/redis.conf:/etc/redis/redis.conf\n      - ./master/data:/data\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.2\n    command: /bin/bash -c \"redis-server /etc/redis/redis.conf\"  #这句话就是要加载这个路径下的配置\n    environment:\n      - TZ=Asia/Shanghai\n      - LANG=en_US.UTF-8\n    ports:\n      - \"6379:6379\"\n\n  slave1:\n    image: redis:latest\n    container_name: redis_slave_1   #slave1节点\n    volumes:\n      - ./slave1/conf/redis.conf:/etc/redis/redis.conf\n      - ./slave1/data:/data\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.3\n    command: /bin/bash -c \"redis-server /etc/redis/redis.conf\"\n    environment:\n      - TZ=Asia/Shanghai\n      - LANG=en_US.UTF-8\n    ports:\n      - \"6380:6379\"\n\n  slave2:\n    image: redis:latest\n    container_name: redis_slave_2   #slave2节点\n    volumes:\n      - ./slave2/conf/redis.conf:/etc/redis/redis.conf\n      - ./slave2/data:/data\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.4\n    command: /bin/bash -c \"redis-server /etc/redis/redis.conf\"\n    environment:\n      - TZ=Asia/Shanghai\n      - LANG=en_US.UTF-8\n    ports:\n      - \"6381:6379\"\n  \n  sentinel1:\n    image: redis:latest\n    container_name: redis_sentinel_1  #sentinel1节点\n    ports:\n      - \"26379:26379\"\n    volumes:\n      - ./sentinel1/conf/sentinel.conf:/usr/local/etc/redis/sentinel.conf\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.5\n    command: /bin/bash -c \"redis-sentinel /usr/local/etc/redis/sentinel.conf\"\n\n  sentinel2:\n    image: redis:latest\n    container_name: redis_sentinel_2 #sentinel2节点\n    ports:\n      - \"26380:26379\"\n    volumes:\n      - ./sentinel2/conf/sentinel.conf:/usr/local/etc/redis/sentinel.conf\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.6\n    command: /bin/bash -c \"redis-sentinel /usr/local/etc/redis/sentinel.conf\"\n\n  sentinel3:\n    image: redis:latest\n    container_name: redis_sentinel_3 #sentinel3节点\n    ports:\n      - \"26381:26379\"\n    volumes:\n      - ./sentinel3/conf/sentinel.conf:/usr/local/etc/redis/sentinel.conf\n    networks:\n      redis_network:\n        ipv4_address: 172.20.1.7\n    command: /bin/bash -c \"redis-sentinel /usr/local/etc/redis/sentinel.conf\"\n\nnetworks:\n  redis_network:\n    driver: bridge\n    ipam:\n      config:\n      - subnet: 172.20.1.0/24\n```\n\n\n\n执行`docker-compose up -d`创建容器。\n\n![image-20220420215554698](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202155799.png)\n\n一共出现六个容器，如果某个容器不见了，那就证明配置有误。执行`docker logs 容器id`来查看日志。\n\n新建三个终端来分别进入**master，slave和sentinel**节点。\n\n\n\n##### 4.验证状态\n\n使用命令\n\n```shell\ndocker exec -it redis_master bash\n#进入后使用命令进入redis-cli，-a是指密码，-h是指ip，-p是指端口\nredis-cli -a 123456 -h 172.20.1.2 -p 6379\n#使用命令查看从机信息\ninfo replication\n```\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202111340.png\" alt=\"image-20220420211124204\" style=\"zoom:50%;\" />\n\n\n\n从节点有两个，ip也给出了。\n\n然后我们按照刚才的命令进入从机，试一下创建一个key，发现出现错误。这就是刚才从机配置的`replica-read-only yes`配置在发挥作用了。因为按照我们的设定，一主两从，主节点是负责写，从节点负责读，读写分离，那么从节点当然无法写入数据。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202113198.png\" alt=\"image-20220420211326152\" style=\"zoom:50%;\" />\n\n\n\n我们进入**sentinel**节点，**注意：进入redis-cli的端口不是6379了，而是刚才配置的26379端口**。执行`info`命令，往下划。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202133633.png\" alt=\"image-20220420213336585\" style=\"zoom:80%;\" />\n\n\n\n可以看到**sentinel**节点监控的**master节点**只有一个，而且ip也正是我们的**master**主机ip，**slaves**为2，**sentinels**为3，这说明我们的配置的一主两从三哨兵是正常运行的。\n\n\n\n#### 测试：\n\n我们尝试在主节点写入数据，看看是否会同步到从机中。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202207163.png\" alt=\"image-20220420220729118\" style=\"zoom:50%;\" />\n\n我们在从机查看是否有该key。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202208914.png\" alt=\"image-20220420220811865\" style=\"zoom:50%;\" />\n\n从机的确能获取到主机所设置的key值，说明**主从同步**是正常的。\n\n\n\n同时**读写分离**是Redis自带的，通过配置**slave**，Redis会自动地让从机进行读操作，让主机进行写操作。这是Redis的主从模式所自带的。\n\n而在主从模式的基础上添加**哨兵模式**，从而提高主从模式的高可用。\n\n\n\n### 模拟故障\n\n这里先模拟一个最常见的故障，就是**master主机宕机**，看看是否会进行主从切换。\n\n这里直接stop掉master主机的容器。\n\n然后过个30秒，在Sentinel的容器上执行命令\n\n```shell\ninfo Sentinel\n```\n\n![image-20220420224731273](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204202247338.png)\n\n然后发现**master**主机已经改变了，**slave2**从机节点被切换成为新的**master**节点。\n\n\n\n### 总结：\n\n通过以上步骤，我们完成了一主两从三哨兵的搭建，同时也通过模拟一个最最常见的故障了测试**哨兵模式**的主从切换功能。那就来总结一下哨兵模式的功能吧。\n\n1. 故障转移，能够通过配置及时地将从机切换成主机。\n2. 故障发现，能够通过Ping监控Master状态。\n3. 配置中心，能够统一配置所有节点的主节点信息。\n\n\n\n优点：\n\n- 哨兵模式是基于主从模式的，所有主从的优点，哨兵模式都具有。\n- 主从可以自动切换，系统更健壮，可用性更高。\n- Sentinel 会不断的检查 主服务器 和 从服务器 是否正常运行。当被监控的某个 Redis 服务器出现问题，Sentinel 通过API脚本向管理员或者其他的应用程序发送通知。\n\n​    缺点：\n\n- Redis较难支持在线扩容，对于集群，容量达到上限时在线扩容会变得很复杂。\n\n\n\n主从模式解决了**Redis**的xx，哨兵模式解决了**Redis**的高可用性问题，但是面对**在线扩容**则显得困难，所以才有了**Cluster**集群模式，通过水平拓展**Redis节点**，从而解决了扩容这个问题。后面我们会继续研究**Cluster**集群的搭建与它的一些讨论。  \n\n同时主从模式和哨兵模式中有一些功能值得我们去深究，例如Sentinel是如何通知其他从机切换主机的呢？Sentinel的投票仲裁机制是怎么样的？\n\n',0,0,7,'1',8,'实践-搭建Redis一主两从三哨兵原因：最近在复习Redis的时候，学习到了为了提高Redis集群的高可用性，有一个模式为哨兵模式。哨兵模式的作用是为了在主节点出现阻塞或者错误，无法接收数据的时候，及时将从节点切换为主节点，由此保证Redis集群能够保持正常状态，保持高可用。但是尽管引入哨兵模式能够','2022-08-15 23:50:00','2022-04-25 10:29:26',0),(1518422387823489025,1,1506910849124057090,'测试回复','我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，',0,0,0,'1',0,'我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，','2022-04-25 11:00:00','2022-04-25 10:51:23',1),(1518426013098848257,1,1506910849124057090,'测试发送文章','我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，',0,0,0,'1',0,'我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，我在测试一下，测试一下我的文章，测试一下，测试一下，','2022-04-25 11:05:47','2022-04-25 11:05:47',1),(1518427685451739137,2,1506910849124057090,'python','```python\n\nimport os\nimport time\nimport shutil\n\n\ndef backupFile():\n    # 备份目录下的aaa,bbb,ccc文件\n    # 获取当前时间\n    now = time.strftime(\"%Y%m%d%H%M%S\")\n    # 获取当前目录\n    path = os.getcwd()\n    # 获取当前目录下的文件\n    files = os.listdir(path)\n    # 遍历文件\n    for file in files:\n        # 判断文件是否为aaa,bbb,ccc\n        if file.startswith(\"aaa\") or file.endswith(\"bbb\") or file.endswith(\"ccc\"):\n            # 备份文件\n            shutil.copy(file, file + \".\" + now)\n            print(\"备份文件%s成功\" % file)\n\n\nif __name__ == \'__main__\':\n    backupFile()\n\n\n```',0,0,0,'1',0,'importosimporttimeimportshutildefbackupFile():#备份目录下的aaa,bbb,ccc文件#获取当前时间now=time.strftime(%Y%m%d%H%M%S)#获取当前目录path=os.getcwd()#获取当前目录下的文件files=os.lis','2022-04-25 11:12:26','2022-04-25 11:12:26',1),(1523259194033668098,2,1506910849124057090,'测试是否会错误Url','测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url',0,0,0,'1',0,'测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url','2022-05-08 20:00:00','2022-05-08 19:11:07',1),(1523260177627295745,2,1506910849124057090,'测试是否会错误Url','测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url',0,0,0,'1',0,'测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url','2022-05-08 20:00:00','2022-05-08 19:15:02',1),(1523260563176108034,2,1506910849124057090,'测试是否会错误Url','测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url',0,0,0,'1',0,'测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url测试是否会错误Url','2022-05-08 20:00:00','2022-05-08 19:16:34',1),(1523261037874851842,2,1506910849124057090,'测试文章啊','测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常',0,0,0,'1',0,'测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常','2022-05-08 19:18:27','2022-05-08 19:18:27',1),(1523261324786216961,2,1506910849124057090,'测试是否正常','测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常',0,0,0,'1',0,'测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常','2022-05-08 19:19:35','2022-05-08 19:19:35',1),(1523262297168474114,2,1506910849124057090,'测试是否正常','测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常',0,0,0,'1',0,'测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常测试是否正常','2022-05-08 19:23:27','2022-05-08 19:23:27',1),(1523276412918214657,2,1506910849124057090,'测试测试','/**\n     * Jackson全局转化long类型为String，解决jackson序列化时传入前端Long类型缺失精度问题\n     */\n    @Bean\n    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {\n        Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {\n            @Override\n            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {\n                jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);\n                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);\n//                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);\n            }\n        };\n        return cunstomizer;\n    }/**\n     * Jackson全局转化long类型为String，解决jackson序列化时传入前端Long类型缺失精度问题\n     */\n    @Bean\n    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {\n        Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {\n            @Override\n            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {\n                jacksonObjectMapperBuilder.serializerByType(BigInteger.class, ToStringSerializer.instance);\n                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);\n//                jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);\n            }\n        };\n        return cunstomizer;\n    }',0,0,0,'1',0,'/***Jackson全局转化long类型为String，解决jackson序列化时传入前端Long类型缺失精度问题/@BeanpublicJackson2ObjectMapperBuilderCustomizerjackson2ObjectMapperBuilderCustomizer(){Jac','2022-05-08 20:19:33','2022-05-08 20:19:33',1),(1533654884224634881,1,1506910849124057090,'我的个人介绍','## 大家好，我是新来的黄文杰\n我是一个练习时常两年半的个人练习生，我的兴趣爱好是唱跳Rap篮球，MUSIC!!~~~~！！！！测试',0,0,5,'1',4,'大家好，我是新来的黄文杰我是一个练习时常两年半的个人练习生，我的兴趣爱好是唱跳Rap篮球，MUSIC!!~~~~','2022-08-15 23:50:00','2022-06-06 11:39:53',0),(1533796720905859073,2,1506910849124057090,'操作系统 - 编译指南','### 操作系统 - 编译指南\n\n本指南能给你带来什么？\n\n1. :walking:避免每次编译都要打开PPT一下一下看命令\n2. :car:完美避开所有坑\n3. :lantern:CV爱好者的福利\n\n\n\n本指南由蔡国鹏总结，完美编译2.6.33内核，请一步一步按照我的指示来走，绝对不会错。\n\n\n\n请使用**root**账号登录，以下命令不再带有sudo\n\n\n\n### 前景提要\n\n经测试，甘老师所给的Ubuntu9.1系统，如果编译的Kernel内核版本为2.6.33.3，那么会出现**电脑CPU为AMD的无法正常启动打开**，Intel的电脑可以运行启动。暂无解决方法，欢迎童鞋找出解决方法，互相告知。\n\n\n\n#### 0. 硬盘扩容\n\n经测试，如果正常编译的话，到后面步骤可能会硬盘剩余空间不足，导致错误。\n\n![image-20220525225232302](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205252252431.png)\n\n尽管我们在Vmware上增加了硬盘空间，但我们仍然需要在Linux手动把空间扩容上去。\n\n\n\nVmware扩容虚拟机硬盘为30G\n\n![image-20220526130936621](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261309704.png)\n\n\n\n进入系统\n\n扩容步骤:\n\n1. 输入命令\n\n```\nfdisk /dev/sda\n```\n\n\n\n进去之后输入 **p**\n\n查看分区信息\n\n![image-20220526131103554](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261311702.png)\n\n\n\n我们全部给删了\n\n输入 **d**\n\n再输入 **1**\n\n\n\n再次输入 **d**\n\n再输入 **2**\n\n\n\n然后我们来新建新的主分区\n\n![image-20220526131411192](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261314342.png)\n\n\n\n1. 先输入n\n2. 再输入p\n3. 输入1\n4. 直接回车\n5. 这里输入+20G，回车\n6. 输入 w 保存\n\n\n\n重启虚拟机\n\n重启完成后，输入\n\n```\nresize2fs /dev/sda1\n```\n\n这个时候就已经扩容完毕。\n\n![image-20220526131823524](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261318590.png)\n\n\n\n但是我们把swap交换空间也给删了，这会导致在编译的时候会出现。\n\nvmlinux.o 出错，而且还是最后的时候才出错。:angry:\n\n\n\n所以我们还需要创建交换空间,避免出错。\n\n\n\n输入命令\n\n```\ndd if=/dev/zero of=/swap_file bs=1M count=2048\n```\n\n这里创建交换空间为内存的两倍\n\n![image-20220526131858970](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261318019.png)\n\n\n\n再输入\n\n```\nmkswap /swap_file\n```\n\n将刚才创建的文件做成swap分区\n\n再输入 \n\n```\nswapon /swap_file\n```\n\n启用交换空间。\n\n输入命令\n\n```\ngedit /etc/fstab\n```\n\n设置开机自动挂载\n\n在打开的文档中，最下面加上\n\n```\n/swap_file swap swap defaults 0 0\n```\n\n![image-20220526132116493](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261321593.png)\n\n\n\n大功告成，这时候可以输入命令，查看是否生效\n\n```\nfree\n```\n\n![image-20220526132220430](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205261322497.png)\n\n\n\n#### 1. 切换软件源（重要）\n\n打开附件 - 终端，输入命令\n\n```shell\ngedit /etc/apt/sources.list\n```\n\n\n\n将以下源复制粘贴进去保存。\n\n```\ndeb http://old-releases.ubuntu.com/ubuntu karmic main restricted universe multiverse\ndeb http://old-releases.ubuntu.com/ubuntu karmic-security main restricted universe multiverse\ndeb http://old-releases.ubuntu.com/ubuntu karmic-updates main restricted universe multiverse\ndeb http://old-releases.ubuntu.com/ubuntu karmic-proposed main restricted universe multiverse\ndeb http://old-releases.ubuntu.com/ubuntu karmic-backports main restricted universe multiverse\ndeb-src http://old-releases.ubuntu.com/ubuntu karmic main restricted universe multiverse\ndeb-src http://old-releases.ubuntu.com/ubuntu karmic-security main restricted universe multiverse\ndeb-src http://old-releases.ubuntu.com/ubuntu karmic-updates main restricted universe multiverse\ndeb-src http://old-releases.ubuntu.com/ubuntu karmic-proposed main restricted universe multiverse\ndeb-src http://old-releases.ubuntu.com/ubuntu karmic-backports main restricted universe multiverse\n```\n\n\n\n输入命令\n\n```shell\napt-get update\n```\n\n\n\n等待更新完成。\n\n\n\n#### 2. 安装编译所需组件\n\n分别执行\n\n```shell\napt-get install build-essential kernel-package libncurses5-dev fakeroot -y\n```\n\n\n\n```shell\naptitude install libqt3-headers libqt3-mt-dev libqt3-compat-headers libqt3-mt -y\n```\n\n\n\n切记，一定要等待安装结束才动终端，不要CTRL + C，不然会中断。\n\n\n\n#### 3. 下载内核\n\n输入命令\n\n```shell\ncd /usr/src\n```\n\n```shell\nwget http://mirrors.tuna.tsinghua.edu.cn/kernel/v2.6/linux-2.6.33.3.tar.bz2\n```\n\n\n\n等待下载完成。\n\n\n\n解压,执行命令。\n\n```\ntar xjvf linux-2.6.33.3.tar.bz2\n```\n\n\n\n输入命令，创建软连接\n\n```\nln -s linux-2.6.33.3 linux\n```\n\n\n\n#### 4. 配置内核\n\n输入命令\n\n```\ncd /usr/src/linux\n```\n\n\n\n先删除之前编译所生成的文件和配置文件，备份文件等。\n\n```\nmake mrproper\n```\n\n\n\n按顺序执行以下命令\n\n```\ncd /usr/include/\nrm -r asm linux scsi\nln -s /usr/src/linux/include/asm-generic asm\nln -s /usr/src/linux/include/linux linux\nln -s /usr/src/linux/include/scsi scsi\n```\n\n\n\n再次输出命令\n\n```\ncd /usr/src/linux\n```\n\n\n\n输入\n\n```\nmake xconfig\n```\n\n\n\n成功出现该页面\n\n![image-20220525220234961](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205252202119.png)\n\n\n\nOK，我们只需要更改一个地方即可。\n\n点击File systems,右边就会出现。\n\n![image-20220525183126372](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205251831472.png)\n\n勾上该项。\n\n![image-20220525220512621](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205252205739.png)\n\n\n\n点击保存即可。\n\n关闭后，我们来开始漫长的编。。。。译。\n\n\n\n#### 5. 编译系统\n\n在编译之前首先应该执行make dep命令建立好依赖关系，该命令将会修改linux中每个子目录下的.depend文件，该文件包含了该目录下每个目标文件所需要的头文件（绝对路径的方式列举）。\n\n执行命令\n\n```\nmake dep\nmake clean\n```\n\n\n\n执行第一个漫长的命令. :timer_clock: 15-25min\n\n这里的 -j 为启用多线程来执行编译，**数字取决于你分配虚拟机的核心数 x 2**，例如我现在分配给虚拟机只有一个核心，那么就是2。\n\n```\nmake -j2 bzImage\n```\n\n\n\n执行好了吗？ 好，接下来第二个和第三个漫长的指令。 :timer_clock: 1 hour+\n\n```\nmake -j2 modules && make -j2 modules_install\n```\n\n#### 6. 最后的最后\n\n执行以下一大堆的命令。\n\n下面的命令主要是把编译好后的镜像，模块，复制到系统目录下。\n**要一条一条执行，不要全部复制进去一下子执行，会出问题的。**\n\n```\ncp /usr/src/linux/System.map /boot/System.map-2.6.33.3\ncp /usr/src/linux/arch/i386/boot/bzImage /boot/vmlinuz-2.6.33.3\ncp .config /boot/config-2.6.33.3\nupdate-initramfs -c -k 2.6.33.3\ncd /boot\nrm -f System.map vmlinuz\nln -s vmlinuz-2.6.33.3 vmlinuz\nln -s system.map-2.6.33.3 System.map\n```\n\n\n\n安装这个包\n\n```\napt-get install startupmanager \n```\n\n\n\n然后在桌面 -> 左上角系统 -> 系统管理 -> 启动管理器 -> 2.6.33.3内核的\n\n\n\n选择它，然后我们默认的启动内核选择我们刚刚编译好的系统啦~~。\n\n![image-20220526004035448](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205260040643.png)\n\n\n\n#### 6.5 设置快照，以防万一\n\n\n\n为了以防万一，设置以下当前的快照，如果后面有错误，可以立刻恢复。\n\n\n\n\n\n#### 7. 坑\n\n什么，你一定要有坑？ Look this。\n\n![image-20220525181624580](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202205251816746.png)\n\n\n\n**这个出现的原因就是软件源跟系统版本不对，由于系统是ubuntu9.1，炒鸡旧的，所以要费一些功夫才能找对源**',3,0,0,'1',0,'操作系统-编译指南本指南能给你带来什么？:walking:避免每次编译都要打开PPT一下一下看命令:car:完美避开所有坑:lantern:CV爱好者的福利本指南由蔡国鹏总结，完美编译2.6.33内核，请一步一步按照我的指示来走，绝对不会错。请使用root账号登录，以下命令不再带有sudo前景提要经','2022-06-06 21:03:29','2022-06-06 21:03:29',0),(1533799472079843330,2,1506910849124057090,'测试下一页，','测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，',0,0,1,'1',0,'测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，测试下一页，','2022-08-15 23:50:00','2022-06-06 21:14:25',0),(1533799764712239106,2,1506910849124057090,'学生成绩管理系统 1- 1','### 学生成绩管理系统\n\n\n\n系统使用Jsp + Servlet + Mysql技术栈，可以实现**对学生成绩的修改，添加学生，删除学生，修改学生信息**，以及学生可以**查询自己的成绩，查看个人信息**。\n\n\n\n1. 通过多表查询能够查询教师，学生以及课程之间联系。\n2. 通过JSTL中的ForEach标签能够实现表格展示列表。\n3. 通过JDBC能够熟悉Java sql底层增删查改的实现原理，通过拼接字符串的形式能够做到动态SQL。\n4. 通过对Servlet的使用，能够了解和熟悉如何处理网页的get和post请求。\n5. 前端使用原生css，html，能够了解和熟悉页面布局操作。\n\n\n\n以下为Mysql创表语句。\n\n```mysql\n-- auto-generated definition\ncreate table class_info\n(\n    id                   int auto_increment\n        primary key,\n    class_name           varchar(100) not null,\n    opening_grade        varchar(20)  not null,\n    semester_total_hours int          not null comment \'学时数\',\n    credits              int          not null comment \'学分\',\n    constraint class_info_id_uindex\n        unique (id)\n)\n    auto_increment = 4;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table collage_info\n(\n    id            int auto_increment\n        primary key,\n    name          varchar(50)   not null,\n    manager       varchar(50)   not null,\n    address       varchar(100)  not null,\n    number_people int default 0 not null,\n    constraint collage_info_id_uindex\n        unique (id)\n);\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table report_card\n(\n    id         int auto_increment\n        primary key,\n    student_id int          not null,\n    class_id   int          not null,\n    semester   varchar(150) not null,\n    score      double       not null,\n    info       varchar(500) null,\n    constraint report_card_id_uindex\n        unique (id)\n)\n    auto_increment = 3;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table student\n(\n    id               int auto_increment\n        primary key,\n    password         varchar(100) not null,\n    name             varchar(50)  not null,\n    sex              tinyint      not null,\n    admission_date   datetime     not null,\n    native_place     varchar(15)  not null,\n    id_number        varchar(20)  not null,\n    detailed_address varchar(150) not null,\n    date_birth       datetime     not null,\n    professional     varchar(50)  not null,\n    national         varchar(50)  not null,\n    phone            varchar(20)  not null,\n    pay_cost         varchar(50)  not null,\n    repair_credit    int          not null comment \'已修学分\',\n    note             varchar(150) null comment \'备注\'\n)\n    auto_increment = 1908030101;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table student_class\n(\n    id         int auto_increment\n        primary key,\n    student_id int not null,\n    class_id   int not null,\n    constraint student_class_id_uindex\n        unique (id)\n)\n    auto_increment = 3;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table teacher\n(\n    id           int auto_increment\n        primary key,\n    teacher_name varchar(50)  not null,\n    password     varchar(100) not null,\n    constraint teacher_id_uindex\n        unique (id)\n)\n    auto_increment = 10001;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table teacher_class\n(\n    id         int auto_increment\n        primary key,\n    teacher_id int not null,\n    class_id   int not null,\n    constraint teacher_class_id_uindex\n        unique (id)\n)\n    comment \'老师课程表\' auto_increment = 3;\n```\n\n\n\n```mysql\n-- auto-generated definition\ncreate table teacher_student\n(\n    id         int auto_increment\n        primary key,\n    teacher_id int not null,\n    student_id int not null,\n    constraint teacher_student_id_uindex\n        unique (id)\n);\n```\n\n',0,0,3,'1',2,'学生成绩管理系统系统使用Jsp+Servlet+Mysql技术栈，可以实现对学生成绩的修改，添加学生，删除学生，修改学生信息，以及学生可以查询自己的成绩，查看个人信息。通过多表查询能够查询教师，学生以及课程之间联系。通过JSTL中的ForEach标签能够实现表格展示列表。通过JDBC能够熟悉Java','2022-08-15 23:50:00','2022-06-06 21:15:35',0),(1533800465731432449,2,1506910849124057090,'IO的五种模型','## IO的五种模型\n\n\n\nIO的基本模型一共有五种，分别是\n\n- 阻塞IO模型\n- 非阻塞IO模型\n- IO复用模型\n- 信号驱动IO模型\n- 异步IO模型\n\n\n\n有两大步：\n\n1. 同步\n2. 异步\n\n有两大类：\n\n1. 阻塞\n2. 非阻塞\n\n\n\n### 1. 阻塞IO模型\n\n首先来这么一个图。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327091741577.png\" alt=\"image-20220327091741577\" style=\"zoom:50%;\" />\n\n客户端对服务端请求数据，但是此时缓冲区中仍没有数据，所以客户端此时要干的事，如果是阻塞IO，那么客户端会一直**等待**，啥也不做，直到缓冲区中有数据了，此时客户端才会动起来，读取数据。  \n\n举个例子：A拿着一支鱼竿在河边钓鱼，并且一直在鱼竿前等，在等的时候不做其他的事情，十分专心。只有鱼上钩的时，才结束掉等的动作，把鱼钓上来。\n\n\n\n**这里之所以能够知道什么时候出现数据，是因为当数据准备好后，内核空间会给进程发送一个成功指示，进程获取到成功指示后则会狐开始获取数据。**\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327093548987.png\" alt=\"image-20220327093548987\" style=\"zoom:50%;\" />\n\n流程： \n\n1. 进程对内核空间读取数据\n2. 内核空间未准备好数据\n3. 进程阻塞等待，啥也不干\n4. 内核空间准备完成，对进程发送成功标识\n5. 进程读取数据\n\n\n\n### 2. 非阻塞IO\n\n非阻塞IO的主要流程其实和阻塞IO差不多，但是在一个步骤上会所差别，而这个差别就是阻塞IO和非阻塞IO之间的区别了。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327093438511.png\" alt=\"image-20220327093438511\" style=\"zoom:50%;\" />\n\n阻塞IO和非阻塞IO之间的区别就是当内核中没有数据的时候，阻塞IO是会一直等待，除非异常或是其他干预，否侧会等待数据准备完成。**而非阻塞IO则是会不停的轮询内核空间，查询数据是否准备好了，如果数据未准备好，则会立刻返回一个错误标识给进程，当数据准备好后，则会返回数据。**\n\n非阻塞IO的流程就是\n\n1. 进程向内核发起读取数据请求\n2. 内核未准备好数据，返回错误标识\n3. 进程向内核发起读取数据请求\n4. 内核准备好数据，返回成功标识\n5. 进程读取数据\n\n\n\n举个栗子，B也在河边钓鱼，但是B不想将自己的所有时间都花费在钓鱼上，在等鱼上钩这个时间段中，B也在做其他的事情（一会看看书，一会读读报纸，一会又去看其他人的钓鱼等），但B在做这些事情的时候，每隔一个固定的时间检查鱼是否上钩。一旦检查到有鱼上钩，就停下手中的事情，把鱼钓上来。\n\n\n\n特点：\n\n1. 进程轮询调用，消耗CPU的资源\n\n\n\n### 3. IO多路复用模型\n\n这里拿一下网图。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327102253843.png\" alt=\"image-20220327102253843\" style=\"zoom: 33%;\" />\n\n这里的IO多路复用其实并不是和阻塞IO，非阻塞IO同一级别的，这里的多路复用IO是本质上是属于阻塞IO，但是又比原生的阻塞IO性能要高。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327113910288.png\" alt=\"image-20220327113910288\" style=\"zoom: 50%;\" />\n\n在以前还没有IO多路复用模型的时候，一开始使用的是每一个客户端请求进来，那么服务端就得相对应的fork/thread一个进程/线程来对请求进行处理，那么如果有100W个客户端来进行连接，那么服务端就得创建100W个进程/线程来对请求进行处理。  \n\n这个模型在一开始计算机还没那么普遍的时候的确是好用，毕竟每个请求都有专人（进程/线程）服务，响应效率也不错。  \n\n\n\n但是时代在发展，随着计算机的普遍，越来越多的客户端去连接服务端，那么如果这时候还是1:1服务，那么服务端的资源那不得被请求耗尽？这时候就发明出select，poll,epoll函数。这种函数实际上是系统提供的函数，在上面每个客户端连接到服务端，服务端都会产生一个fd文件，这个fd就是文件描述符，在Linux中一切皆文件，网络连接也是一种文件，通过这个fd我们就可以对网路连接的状态进行读取。\n\n\n\n那么select，poll,epoll函数，就可以对这些fd进行监控，比如监听请求要进行读数据，或者写数据，那么就会另起一个线程来处理，这种也可以称为事件驱动模型。就只有你有事件要触发才会对你进行处理。\n\n> 其实在Linux底层中，select，poll本质上其实是对fd进行单线程的遍历，遍历每个fd的一个数据请求状态，所以其实大部分的轮询fd请求是无效的。\n\n\n\n那么这个和一开始的1:1模型有什么区别啊，这里就是有些有些请求进来之后他啥也不干，就是连接，那如果这个请求啥也不干就分配一个进程/线程给他服务是不是很傻？我们做到**就是一个进程/线程就可以对所有的网络请求进行监听，当某个网络请求有事件发生，我再对该网络请求进行处理。**\n\n\n\n如果还是拿钓鱼的故事就是，有一个C也在钓鱼，但是这个C呢比较富有，他用了很多条鱼竿(fd)在钓鱼，**并且他会一个一个检查这些鱼竿有没有钓上鱼了。** \n\n<img src=\"https://pic3.zhimg.com/v2-2c65fd3534e58d3a54cdeae778a31446_r.jpg\" alt=\"img\" style=\"zoom:80%;\" />\n\n\n\n这里的IO多路复用也有几种，一种是IO多路复用 + 多线程读写，一种是IO多路复用 + 多线程IO多路复用。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327122244963.png\" alt=\"image-20220327122244963\" style=\"zoom: 50%;\" />\n\n这种就是普通的IO多路复用，当其中一个客户端发起网络IO请求，那么该服务端的主线程就会处理该读写操作（此时仍可以处理多个连接，也就是生成fd文件，但是IO操作只能一个来），其他客户端如果想进行读写操作则会被阻塞，直到超时或者第一个客户端读写结束。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327123123170.png\" alt=\"image-20220327123123170\" style=\"zoom:50%;\" />\n\n这种就是IO多路复用 + 线程池读写业务。弥补上面只有单一读写的缺点。\n\n\n\n总结：\n\n1. IO多路复用其实是利用select函数，对多个fd进行监听，当监听到fd数据状态有变化时（读写操作），就会进行IO操作。\n2. select函数底层是通过单线程轮询fd文件来实现监听数据状态。\n3. 通过select函数可以做到只对需要read/write操作的请求创建新线程处理，无需1:1分配。\n\n\n\n### 4. 信号驱动IO模型\n\n这个模型是在IO复用的基础上进行优化的。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327131708553.png\" alt=\"image-20220327131708553\" style=\"zoom:50%;\" />\n\n复用IO模型解决了一个线程可以监控多个fd的问题，但是select是采用轮询的方式来监控多个fd的，通过不断的轮询fd的可读状态来知道是否就可读的数据，而无脑的轮询就显得有点暴力，因为大部分情况下的轮询都是无效的，所以有人就想，能不能不要我总是去问你是否数据准备就绪，能不能我发出请求后等你数据准备好了就通知我，所以就衍生了信号驱动IO模型。\n\n\n\n于是信号驱动IO不是用循环请求询问的方式去监控数据就绪状态，而是在调用sigaction时候建立一个SIGIO的信号联系，当内核数据准备好之后再通过SIGIO信号通知线程数据准备好后的可读状态，当线程收到可读状态的信号后，此时再向内核发起recvfrom读取数据的请求，因为信号驱动IO的模型下应用线程在发出信号监控后即可返回，不会阻塞，所以这样的方式下，一个应用线程也可以同时监控多个fd。\n\n\n\n举个栗子，就类似于刚才IO多路复用中的C，有多条鱼竿，但是这次他在每条鱼竿上都装了一个铃铛。只要铃铛一响，就证明有鱼上钩了。这样C就不用一个一个鱼竿的去看，它只需要等待哪个鱼竿上的铃铛响，他就去哪个鱼竿。\n\n\n\n### 5. 异步IO\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220327132250608.png\" alt=\"image-20220327132250608\" style=\"zoom:50%;\" />\n\n异步IO挺好理解的，就是进程向内核发起读请求，然后立刻返回，等内核处理好后再自动返回，而自动返回来就有相对应的业务逻辑去处理。异步IO模型在Js编程中也有用到，因为Js是单线程执行的，如果一个请求阻塞太久就会导致下面的逻辑无法执行下去。所以就产生了异步调用，先发送请求给服务器，然后等待服务器回调的同时执行下面的逻辑，等服务器回调后，再对回调数据进行处理。\n\n在Js中，如果有大量的异步调用，同时一个异步调用又依赖着上一个异步调用的回调结果，这种就称为**回调地狱**。\n\n\n\n### 总结：\n\n先取知乎一篇文章中的总结，本次学习笔记大部分内容其实是基于该文章得来的。\n\n> ## **再谈IO模型里面的同步异步**\n>\n> 我们通常会说到同步阻塞IO、同步非阻塞IO，异步IO几种术语，通过上面的内容，那么我想你现在肯定已经理解了什么是阻塞什么是非阻塞了，所谓阻塞就是发起读取数据请求的时，当数据还没准备就绪的时候，这时请求是即刻返回，还是在这里等待数据的就绪，如果需要等待的话就是阻塞，反之如果即刻返回就是非阻塞。\n>\n> \n>\n> 我们区分了阻塞和非阻塞后再来分别下同步和异步，在IO模型里面如果请求方从发起请求到数据最后完成的这一段过程中都需要自己参与，那么这种我们称为同步请求；反之，如果应用发送完指令后就不再参与过程了，只需要等待最终完成结果的通知，那么这就属于异步。\n>\n> \n>\n> 我们再看同步阻塞、同步非阻塞，他们不同的只是发起读取请求的时候一个请求阻塞，一个请求不阻塞，但是相同的是，他们都需要应用自己监控整个数据完成的过程。而为什么只有异步非阻塞 而没有异步阻塞呢，因为异步模型下请求指定发送完后就即刻返回了，没有任何后续流程了，所以它注定不会阻塞，所以也就只会有异步非阻塞模型了。\n\n',0,0,3,'1',0,'IO的五种模型IO的基本模型一共有五种，分别是阻塞IO模型非阻塞IO模型IO复用模型信号驱动IO模型异步IO模型有两大步：同步异步有两大类：阻塞非阻塞1.阻塞IO模型首先来这么一个图。客户端对服务端请求数据，但是此时缓冲区中仍没有数据，所以客户端此时要干的事，如果是阻塞IO，那么客户端会一直等待，啥','2022-08-15 23:50:00','2022-06-06 21:18:22',0),(1533800553593712641,2,1506910849124057090,'NIO学习笔记','## NIO学习笔记\n\n### 1. Selector选择器\n\n#### 1. Selector和Channel的关系\n\nSelector称为多路复用器，也叫选择器，用于检查一个或多个Nio Channel（通道）的状态是否可读，可写。可以实现单线程管理多个channel，也就是可以管理多个网络连接。\n\n#### 2. 可选择通道\n\n不是所有的channel都可以被Selector复用，例如FileChannel，能不能被Selector复用的关键在于有没有继承一个抽象类SelectableChannel。而且如果能被Selector复用就意味着该channel的读和写是不阻塞的，但是FileChannel会阻塞。\n\n\n\n#### 3.Channel注册到Selector中\n\nChannel要注册到Selector中去需要指定Selector，然后还需要指定可供选择器查询的通道操作，也就是选择一个让选择器感兴趣的操作类型。\n\n1. 可读\n2. 可写\n3. 连接\n4. 接收\n\n如果Selector对通道的多类型操作感兴趣，那么可以使用`位或`操作符来实现。\n\n```java\nint key = SelectionKey.OP_READ | SelectionKey.OP_Write\n```\n\n选择查询的不是通道的操作，而是查询通道某种操作的状态\n\n\n\n#### 4. 选择键（SelectionKey）\n\n选择键就是选择器对通道感兴趣的操作类型，分别有`可读`，`可写`,`连接`,`接收`。\n\n一个选择键包含了注册在Selector的通道操作类型，也包含了特定的通道与特定的选择器之间的注册类型。\n\nSelector可以不断的查询Channel中发生的操作的状态。一旦通道中操作的就绪状态达成，并且是Selector感兴趣的操作，就会被Selector选中，放入选择键集合中。\n\n\n\n**开发应用程序是，选择键是编程的关键.NIO的编程，就是根据对应的选择键，进行不同的业务处理。**\n\n\n\n### 2. Channel通道\n\nChannel是一个通道，可以通过它对数据进行读取和写入，就像水管一样，网络数据通过Channel进行读取和写入。**通道与流的不同之处在于，流是单向的，只能进行读取或是写入一种操作，不能进行双向操作。而通道可以用于读，写，或同时读写。这是因为Channel是全双工的。**\n\n通道可以异步地进行读写。\n\n通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220326214452943.png\" alt=\"image-20220326214452943\" style=\"zoom:50%;\" />\n\n\n\nChannel的实现一共有四种\n\n1. FileChannel 从文件中读取数据\n2. DatagramChannel 能通过UDP读写网络中的数据\n3. SocketChannel 能通过TCP读写网络中的数据\n4. ServerSocketChannel可以监听新进来的TCP连接，像WEB服务器一样，对于每一个新进来的连接都会创建一个SocketChannel。\n\n\n\n\n\n#### 3. Buffer（缓冲区）\n\n缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。缓冲区实际上是一个容器对象，更直接的说其实就是一个数组，在NIO库中，所有数据都是用缓冲区进行处理的。  \n\n在读取数据时，它是直接读到缓冲区的，在写入数据时，它也是写入到缓冲区的，任何时候访问NIO中的数据，都是将他放到缓冲区的。\n\n\n\nBuffer底层实现其实是一个数组，拥有三个重要的属性\n\n- Capacity\n- Position\n- limit\n\nPosition和limit取决于Buffer处于什么状态（读或者写）\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220326221210104.png\" alt=\"image-20220326221210104\" style=\"zoom:50%;\" />\n\n当Buffer处于写状态时，limit和capacity的下标是一样的，position随着数据的写入而增加，当使用filp()方法后，Buffer将会从写状态转换成读状态，而**position将会重置到下标0，limit则重置到转换前position的位置**。\n\n\n\n#### NIO编程步骤\n\n1. 创建一个Selector选择器\n2. 创建一个ServerSocketChannel通道，绑定监听端口\n3. 设置Channel通道为非阻塞\n4. 把Channel注册到Selector选择器上，并选择监听连接事件。\n5. 循环调用Selector中的select方法， 检测通道的就绪状态。\n6. 调用selectKeys方法获取就绪的Channel集合。\n7. 遍历就绪的Channel集合，判断事件类型，根据事件类型处理不同的业务逻辑。\n8. 根据业务，决定是否需要再次注册监听事件，重复执行第三步步骤。\n\n\n\n##### 服务端代码\n\n```java\n@Test\npublic void ServerDemo() {\n  try {\n    //创建Channel\n    ServerSocketChannel ssc = ServerSocketChannel.open();\n    //绑定地址和端口\n    ssc.socket().bind(new InetSocketAddress(\"127.0.0.1\", 8000));\n    //设置非阻塞模式\n    ssc.configureBlocking(false);\n    Selector selector = Selector.open();\n    // 注册 channel，并且指定感兴趣的事件是 Accept\n    ssc.register(selector, SelectionKey.OP_ACCEPT);\n    ByteBuffer readBuff = ByteBuffer.allocate(1024);\n    ByteBuffer writeBuff = ByteBuffer.allocate(128);\n    writeBuff.put(\"received\".getBytes());\n    writeBuff.flip();\n    while (true) {\n      int nReady = selector.select();\n      Set < SelectionKey > keys = selector.selectedKeys();\n      Iterator < SelectionKey > it = keys.iterator();\n      while (it.hasNext()) {\n        SelectionKey key = it.next();\n        it.remove();\n        if (key.isAcceptable()) {\n          // 创建新的连接，并且把连接注册到 selector 上，而且，\n          // 声明这个 channel 只对读操作感兴趣。\n          SocketChannel socketChannel = ssc.accept();\n          socketChannel.configureBlocking(false);\n          socketChannel.register(selector, SelectionKey.OP_READ);\n        } else if (key.isReadable()) {\n          SocketChannel socketChannel = (SocketChannel) key.channel();\n          readBuff.clear();\n          socketChannel.read(readBuff);\n          readBuff.flip();\n          System.out.println(\"received : \" + new String(readBuff.array()));\n          key.interestOps(SelectionKey.OP_WRITE);\n        } else if (key.isWritable()) {\n          writeBuff.rewind();\n          SocketChannel socketChannel = (SocketChannel) key.channel();\n          socketChannel.write(writeBuff);\n          key.interestOps(SelectionKey.OP_READ);\n        }\n      }\n    }\n  } catch (IOException e) {\n    e.printStackTrace();\n  }\n}\n```\n\n\n\n客户端代码\n\n```java\n@Test\npublic void ClientDemo() {\n  try {\n    SocketChannel socketChannel = SocketChannel.open();\n    socketChannel.connect(new InetSocketAddress(\"127.0.0.1\", 8000));\n    ByteBuffer writeBuffer = ByteBuffer.allocate(32);\n    ByteBuffer readBuffer = ByteBuffer.allocate(32);\n    writeBuffer.put(\"hello\".getBytes());\n    writeBuffer.flip();\n    while (true) {\n      writeBuffer.rewind();\n      socketChannel.write(writeBuffer);\n      readBuffer.clear();\n      socketChannel.read(readBuffer);\n    }\n  } catch (IOException e) {}\n}\n```\n\n',0,0,2,'1',0,'NIO学习笔记1.Selector选择器1.Selector和Channel的关系Selector称为多路复用器，也叫选择器，用于检查一个或多个NioChannel（通道）的状态是否可读，可写。可以实现单线程管理多个channel，也就是可以管理多个网络连接。2.可选择通道不是所有的channel都','2022-08-15 23:50:00','2022-06-06 21:18:43',0),(1533800693297590274,2,1506910849124057090,'Spring的三级缓存是如何解决循环依赖的','### Spring的三级缓存是如何解决循环依赖的\n\n首先先看一下Spring获取Bean是怎么获取。获取方法是在`DefaultSingletonBeanRegistry`类下，也就是默认的单例bean注册类。\n\n```java\nprotected Object getSingleton(String beanName, boolean allowEarlyReference) {\n    //传入的是bean的名称，和是否允许建立早期引用（解决循环依赖）\n    // Quick check for existing instance without full singleton lock\n    Object singletonObject = this.singletonObjects.get(beanName);\n    //首先在一级缓存singletonObjects中寻找是否有已经实例化好的bean\n    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {\n        //如果在一级缓存找不到bean并且判断该bean是否正在在bean工厂进行创建\n        singletonObject = this.earlySingletonObjects.get(beanName);\n       	//如果一级缓存找不到并且bean是正在在bean工厂进行创建的，那就到二级缓存中获取\n        //二级缓存中存储的都是已经实例化完成，但是还没有注入参数的对象\n        if (singletonObject == null && allowEarlyReference) {\n            //如果二级缓存还是没有拿到且允许提前拿到对象引用就执行下面方法，如果在二级缓存中获取到就返回bean\n            synchronized (this.singletonObjects) {\n                //加锁执行\n                // Consistent creation of early reference within full singleton lock\n                singletonObject = this.singletonObjects.get(beanName);\n                //依旧从一级缓存中获取bean\n                if (singletonObject == null) {\n                    //一级缓存中没有获取到bean就到二级缓存中获取\n                    singletonObject = this.earlySingletonObjects.get(beanName);\n                    if (singletonObject == null) {\n                        //如果二级缓存中还是获取不到bean，那就到三级缓存中获取bean\n                        ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);\n                        if (singletonFactory != null) {\n                            singletonObject = singletonFactory.getObject();\n                            //在三级缓存中获取到单例bean\n                            this.earlySingletonObjects.put(beanName, singletonObject);\n                            //在二级缓存中缓存该bean\n                            this.singletonFactories.remove(beanName);\n                            //从三级缓存中移除该bean\n                        }\n                    }\n                }\n            }\n        }\n    }\n    return singletonObject;\n}\n```\n\n先从获取bean的方法上看，获取bean的方法是在`AbstractBeanFactory`抽象方法中的doGetBean。\n\n![image-20220322103011001](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322103011001.png)\n\n`getSingleton`方法的解析在上面有，这里解释一下为什么要在获取三级缓存前加锁，并且重复获取一二级缓存。\n\n![image-20220322103155511](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322103155511.png)\n\n这里的加锁可以理解为为了避免多个线程在获取一级和二级缓存后，大量在三级缓存中生成并获取缓存。可以看作我们在平时请求中，如果缓存没有对象，那么就会到数据库中获取数据并设置到redis中，在获取数据库数据和设置redis中进行加锁，这样子后面的请求不会大量的打到数据库中，而且在第一个请求设置了缓存后，后面的请求可以直接在缓存中获取，也不用到达数据库。  \n\n这里获取bean的思想也是如此，**首先使用互斥锁来阻塞线程，然后当第一个线程在三级缓存中获取到bean，就会把bean缓存到二级缓存中，同时会从三级缓存中移除该bean，这样就解释了为什么要在获取三级缓存前还要重复在一二级缓存中获取bean的原因了，只要第一个线程把bean从三级缓存中缓存到二级缓存，那么后面的线程就没必要去三级缓存中获取bean。**  \n\n\n\n那么三级缓存的作用是什么呢？一个是生成这个bean对象，通过lambda表达式，还有一个作用是提前执行`AOP`。\n\n首先Bean的生命周期是这样子的。简单概括如下：\n\nSpringBean 生命周期简单概括为4个阶段：\n\n1. 实例化，创建一个Bean对象\n2. 填充属性，为属性赋值\n3. 初始化\n   - 如果实现了`xxxAware`接口，通过不同类型的Aware接口拿到Spring容器的资源\n   - 如果实现了BeanPostProcessor接口，则会回调该接口的`postProcessBeforeInitialzation`和`postProcessAfterInitialization`方法\n   - 如果配置了`init-method`方法，则会执行`init-method`配置的方法\n4. 销毁\n   - 容器关闭后，如果Bean实现了`DisposableBean`接口，则会回调该接口的`destroy`方法\n   - 如果配置了`destroy-method`方法，则会执行`destroy-method`配置的方法\n\n然后实现默认bean创建的抽象类是`AbstractAutowireCapableBeanFactory`。在这抽象类中有创建bean的方法。\n\n![image-20220322110435582](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322110435582.png)\n\n其中核心的就是![image-20220322110456826](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322110456826.png)\n\n`doCreateBean()`方法，在这一方法里面，将会对bean进行实例化。\n\n![image-20220322110956890](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322110956890.png)\n\n在下面有一个处理。\n\n![image-20220322111110352](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322111110352.png)\n\n在这里会把以beanName为Key，一个lambda表达式为value存入到三级缓存中，而该表达式的作用是返回一个原始对象。\n\n![image-20220322111237606](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322111237606.png)注意：是缓存的是原始对象，而不是已经赋值的对象，初始化的操作在缓存的操作下面。\n\n而这个`getEarlyBeanReference`方法如下：\n\n![image-20220322111352099](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322111352099.png)\n\n这里会判断生成的bean是否是代理对象，如果是代理对象则会覆盖掉这个对象，如果不是则直接返回原始对象。这是为什么呢？\n\n> 因为三级缓存中放的是生成具体对象的匿名内部类，他可以生成代理对象，也可以是普通的实例对象。\n>\n> 使用三级缓存主要是为了保证不管什么时候使用的都是一个对象。\n>\n> 假设只有二级缓存的情况，往二级缓存中放的显示一个普通的Bean对象，`BeanPostProcessor`去生成代理对象之后，覆盖掉二级缓存中的普通Bean对象，那么多线程环境下可能取到的对象就不一致了。\n\n也就是说如果原始对象中的某个方法被AOP了，那么则需要根据原始对象生成一个代理对象。而这个代理对象就会在这一步中覆盖掉原始对象。\n\n![image-20220322112102605](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322112102605.png)\n\n也就是这里放入三级缓存中如果对象中没有AOP方法，则返回原始对象，如果有AOP方法，则返回AOP生成的代理对象。\n\n\n\n### 总结：\n\n![三级缓存流程图](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/%E4%B8%89%E7%BA%A7%E7%BC%93%E5%AD%98%E6%B5%81%E7%A8%8B%E5%9B%BE.png)\n\n这里用IT老哥画的流程图来总结一下。\n\n这里说一下再填充AService属性中，执行完lambda表达式后会将AService从三级缓存放到二级缓存中。\n\n![image-20220322115414255](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220322115414255.png)\n\n',0,0,3,'1',0,'Spring的三级缓存是如何解决循环依赖的首先先看一下Spring获取Bean是怎么获取。获取方法是在DefaultSingletonBeanRegistry类下，也就是默认的单例bean注册类。protectedObjectgetSingleton(StringbeanName,booleanal','2022-08-15 23:50:00','2022-06-06 21:19:17',0),(1533800812499709953,2,1506910849124057090,'Netty学习笔记','## Netty学习笔记\n\n\n\n#### 1. Netty简介\n\n维基百科简介：\n\n> **Netty**是一个[非阻塞I/O](https://zh.wikipedia.org/wiki/异步IO)客户端-服务器[框架](https://zh.wikipedia.org/wiki/軟體框架)，主要用于开发[Java](https://zh.wikipedia.org/wiki/Java)网络应用程序，如协议服务器和客户端。异步[事件驱动](https://zh.wikipedia.org/wiki/事件驱动的编程)的网络应用程序框架和工具用于简化网络编程，例如[TCP](https://zh.wikipedia.org/wiki/传输控制协议)和[UDP](https://zh.wikipedia.org/wiki/用户数据报协议)套接字服务器。[[2\\]](https://zh.wikipedia.org/wiki/Netty#cite_note-2)Netty包括了[反应器编程模式](https://zh.wikipedia.org/wiki/反应器模式)的实现。Netty最初由[JBoss](https://zh.wikipedia.org/wiki/JBoss)开发，现在由Netty项目社区开发和维护。\n>\n> 除了作为异步网络应用程序框架，Netty还包括了对[HTTP](https://zh.wikipedia.org/wiki/超文本传输协议)、[HTTP2](https://zh.wikipedia.org/wiki/HTTP/2)、[DNS](https://zh.wikipedia.org/wiki/域名系统)及其他协议的支持，涵盖了在[Servlet容器](https://zh.wikipedia.org/wiki/Servlet容器)内运行的能力、对[WebSockets](https://zh.wikipedia.org/wiki/WebSocket)的支持、与[Google](https://zh.wikipedia.org/wiki/Google) [Protocol Buffers](https://zh.wikipedia.org/wiki/Protocol_Buffers)的集成、对[SSL](https://zh.wikipedia.org/wiki/傳輸層安全性協定)/[TLS](https://zh.wikipedia.org/wiki/傳輸層安全性協定)的支持以及对用于[SPDY](https://zh.wikipedia.org/wiki/SPDY)协议和消息[压缩](https://zh.wikipedia.org/wiki/数据压缩)的支持。自2004年以来，Netty一直在被积极开发。[[3\\]](https://zh.wikipedia.org/wiki/Netty#cite_note-3)\n>\n> 从版本4.0.0开始，Netty在支持[NIO](https://zh.wikipedia.org/wiki/Java_NIO)和阻塞Java套接字的同时，还支持使用NIO.2作为后端。\n\n\n\nNetty是一个高性能的非阻塞IO的客户端服务器框架，底层封装了JDK的NIO。\n\n1. Netty的底层IO模型可以随意切换\n\n2. Netty自带拆包解包，异常检测等机制。\n\n3. Netty解决了JDK的很多包括空轮询的bug\n\n4. Netty底层对线程，selector做了很多细小的优化，精心设计reactor线程模型租掉非常高效的并发处理。\n\n   \n\n#### 2.Netty服务端启动方法解析\n\n```java\n/**\n * @Author: Alickx\n * @Date: 2022/04/08/19:31\n * @Description: NettyServer\n */\n@Slf4j\n@Component\npublic class NettyServer implements ApplicationRunner{\n\n    /**\n     * 建立线程组\n     */\n    private final NioEventLoopGroup bossGroup;\n    private final NioEventLoopGroup workerGroup;\n\n    @Autowired\n    MyChannelInitializer myChannelInitializer;\n\n    /**\n     * 初始化\n     */\n    public NettyServer() {\n        bossGroup = new NioEventLoopGroup();\n        workerGroup = new NioEventLoopGroup();\n    }\n\n    public void start() throws InterruptedException {\n        ServerBootstrap bootstrap = new ServerBootstrap();\n        bootstrap.group(bossGroup, workerGroup)\n            .option(ChannelOption.SO_BACKLOG, 1024)\n            .childOption(ChannelOption.TCP_NODELAY, true)\n            .handler(new LoggingHandler(LogLevel.TRACE))\n            .channel(NioServerSocketChannel.class)\n            .childHandler(myChannelInitializer);\n\n        ChannelFuture channelFuture = bootstrap.bind(9898).sync();\n        log.info(\"netty server start success,port=[{}]\", 9898);\n        // 等待服务器监听端口关闭\n        channelFuture.channel().closeFuture().sync();\n\n    }\n\n    /**\n     * 销毁线程组\n     */\n    @PreDestroy\n    public void stop() {\n        bossGroup.shutdownGracefully();\n        workerGroup.shutdownGracefully();\n    }\n\n    @Override\n    public void run(ApplicationArguments args) throws Exception {\n        start();\n    }\n}\n```\n\n\n\n##### 2.1 bossGroup和workerGroup\n\n这两个实例其实就是对应着Netty中IO模型中的两大线程组。Netty的IO线程模型是**基于主从Reactor线程模型**。\n\n> Reactor是反应堆的意思，Reactor模型，是指通过一个或多个输入同时传递给服务处理器的服务请求的**事件驱动处理模式**。 服务端程序处理传入多路请求，并将它们同步分派给请求对应的处理线程，Reactor模式也叫Dispatcher模式，即I/O多了复用统一监听事件，收到事件后分发(Dispatch给某进程)，是编写高性能网络服务器的必备技术之一。\n\n\n\nReactor模型中有2个关键组成：\n\n- Reactor Reactor在一个单独的线程中运行，负责监听和分发事件，分发给适当的处理程序来对IO事件做出反应。 它就像公司的电话接线员，它接听来自客户的电话并将线路转移到适当的联系人\n- Handlers 处理程序执行I/O事件要完成的实际事件，类似于客户想要与之交谈的公司中的实际官员。Reactor通过调度适当的处理程序来响应I/O事件，处理程序执行非阻塞操作\n\n\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204161743556.webp\" alt=\"Reactor模型\" style=\"zoom:50%;\" />\n\n取决于Reactor的数量和Hanndler线程数量的不同，Reactor模型有3个变种\n\n- 单Reactor单线程\n- 单Reactor多线程\n- 主从Reactor多线程\n\n可以这样理解，Reactor就是一个执行while (true) { selector.select(); ...}循环的线程，会源源不断的产生新的事件，称作反应堆很贴切。\n\n\n\nNetty主要**基于主从Reactors多线程模型**（如下图）做了一定的修改，其中主从Reactor多线程模型有多个Reactor：MainReactor和SubReactor：\n\n- MainReactor负责客户端的连接请求，并将请求转交给SubReactor\n- SubReactor负责相应通道的IO读写请求\n- 非IO请求（具体逻辑处理）的任务则会直接写入队列，等待worker threads进行处理\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/202204161749392.webp\" alt=\"主从Rreactor多线程模型\" style=\"zoom:50%;\" />\n\n',0,0,3,'1',1,'Netty学习笔记1.Netty简介维基百科简介：Netty是一个非阻塞I/O客户端-服务器框架，主要用于开发Java网络应用程序，如协议服务器和客户端。异步事件驱动的网络应用程序框架和工具用于简化网络编程，例如TCP和UDP套接字服务器。[2]Netty包括了反应器编程模式的实现。Netty最初由','2022-08-15 23:50:00','2022-06-06 21:19:45',0),(1533800952211976193,2,1506910849124057090,'研究一下微服务下的认证和授权（1）（实战）','### 研究一下微服务下的认证和授权（1）（实战）\n\n#### 介绍\n\n常见的认证和授权方式包括但不限于以下几种\n\n- OAtuh2\n- Session-Cookie\n- Token（jwt）\n\nOAtuh2的常见使用场景包括`给第三方授予用户部分信息以及权限`，`大型系统内各小型系统之间的认证和授权`等，这里可以以微信登录为例，当你登录某一网站下使用微信登录，你扫码后微信会提示你，你的部分信息头像或个人资料会授予该网站等等，这个就是给第三方授权了。OAuth2的认证模式一共有四种。  \n\n1. 授权码模式\n2. 密码模式\n3. 简单模式\n4. 客户端模式\n\n由于OAtuh2概念较多，而且这篇文章并不是以OAtuh2为中心，所以具体内容就不细讲了，下一次会专门研究OAuth2。\n\n那么剩下两种分别是Session-Cookie和Token，这两种我会运用我的项目来进行演示。\n\n\n\n#### Session-Cookie\n\n我在以前学习jsp的时候，就知道session是会话，是客户端和服务端之间的会话，这里就要扯到Jsp中的四个域对象了。\n\n分别是\n\n- pageContext page域\n- request request域\n- session session域\n- application context域\n\n每个域都有自己的范围，page域只要你刷新本页或者跳转到别的页面就会失效，也就是只能在当前页面生效。request域则是只能在同一个请求内使用，也就是请求转发。context域则是全局都可使用（整个web应用），只有系统重启才会失效。\n\n那session域呢，session域的范围是在一次`会话`中有效。\n\n那什么是会话？会话什么时候开始结束？会话的内容是什么？  \n\n##### 1.什么是会话？\n\n会话相关的定义百度百科有，这里我就说一下我的见解吧。我们在平时生活中，公司办公室有会话间，顾名思义会话就是有两个或以上的物体在进行对话，因为一个物体压根都不能说是会话，只能说是自言自语。那么在这里就是服务端和客户端之间的交流，那我们在BS应用上就是服务端和浏览器之间的交流。而且这种交流是持续的，除非两者之一退出会话间，那么会话才会结束。  \n\n##### 2.会话什么时候开始？\n\n按照上面那个例子，会话是两者之间的交流，那么我们客户端访问服务端的第一次，你可以看作在进入会话间后，服务端要给你一个牌子，这个牌子就是你要和我交流的一个标志，这个标志是独一无二的。我们可以把这个标志看作是SessionId，你在第一次访问服务端后，服务端会响应给你一个SessionId，用来认识你的。在之后，只要你继续带着这个SessionId去请求服务端，服务端就知道，啊，原来是你。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312101920313.png\" alt=\"image-20220312101920313\" style=\"zoom: 67%;\" />\n\n\n\n也就是这么个流程。我们可以做个简单的实验来看看。\n\n在SpringBoot上加入spring-boot-starter-web依赖，然后编写下面这样的一个Demo。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312102515013.png\" alt=\"image-20220312102515013\" style=\"zoom:50%;\" />\n\n然后我们在浏览器上访问一下，看看会得到什么。  \n\n![image-20220312102637893](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312102637893.png)\n\n可以看到当我们访问了该地址后，服务端向我们返回了一个Cookie。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312102738383.png\" alt=\"image-20220312102738383\" style=\"zoom: 50%;\" />\n\n\n\ncookie里面的JSessionId就是上面我们所说的SessionId，再来看一下Springboot里面的这个`getSession`方法。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312102907876.png\" alt=\"image-20220312102907876\" style=\"zoom:50%;\" />\n\n> 返回与此**请求关联**的当前会话，或者如果请求没有会话，则**创建一个**\n\n也就是说该方法可以给当前的一个请求创建一个会话，所谓的会话就是我们会给这个请求返回一个id，而**存储这个id的信息则是在服务端中**。根据这个id我们可以在服务端中辨识，存储和读取该用户的信息。如果没有这个SessionId我们便不能识别该请求是谁的。**这个是因为HTTP的请求是无状态的，如果我们不依赖cookie或者其他标识，服务端是完全无法分辨该请求和上一次请求的区别**。这里就是与token的区别之一了，客户端的所有信息都是存储在服务端的，由服务端来控制。\n\n那这里说到Session了，为什么这个认证方案叫session-cookie方案呢，原因就是**SessionId是可变的**。我们如果单纯的给予用户SessionId，然后就直接在服务端中存储用户信息，那么等浏览器关闭或者服务器重启后，SessionId就会失效，无法做到对用户状态的持久追踪。\n\n现在的SessionId是：<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312104130252.png\" alt=\"image-20220312104130252\" style=\"zoom:50%;\" />\n\n如果我们重启服务器后就会变成：<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312104357596.png\" alt=\"image-20220312104357596\" style=\"zoom:50%;\" />\n\nSession的原理就是使用cookie存储服务端响应回来的SessionId，但是该cookie是有过期时间的。\n\n![image-20220312104711360](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312104711360.png)\n\n过期时间就是会话结束，也就是说只要会话结束了，该cookie就会消失。假如我们在登陆服务端后获得了一个SessionId，但是每次关闭浏览器后都需要重新登录，而且服务端也会丢失跟踪该SessionId，导致丢失信息。\n\n\n\n##### Cookie登场\n\n 那我们要使用什么法子来存储SessionId呢？很简单，我们只需要手动把需要返回的SessionId的过期时间以cookie形式返回给客户端。怎么手动法？Servlet中就包含了许多可以对Cookie进行操作的方法。  \n\n我们取出SessionId，然后手动创建一个Cookie，并设置过期时间，返回给客户端。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312111330233.png\" alt=\"image-20220312111330233\" style=\"zoom:50%;\" />\n\n在浏览器上可以看到返回来的SessionId的过期时间是7天以后，\n\n![image-20220312111033331](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312111033331.png)\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312105503082.png\" alt=\"image-20220312105503082\" style=\"zoom:50%;\" />\n\nCookie其中一个特性就是每次请求都会带上该域名所含有的Cookie（就是你访问百度的话不会把谷歌的Cookie带上，只会是百度域名的），这个无需前端还是后端来操作，这是一个浏览器本身的行为。\n\n#### 正题\n\n那我们就使用`Spring Session`来模拟一下登录的过程吧。\n\n引入依赖\n\n```xml\n<dependency>\n    <groupId>org.springframework.session</groupId>\n    <artifactId>spring-session-data-redis</artifactId>\n</dependency>\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-data-redis</artifactId>\n</dependency>\n```\n\n在配置文件中填入redis的配置\n\n```properties\nspring.redis.host=localhost\nspring.redis.port=6379\n```\n\nSpring Session也是Spring的一款开箱即用的工具，相当于一款管理Session的工具\n\nSpring Session有多种方式可以管理Session\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312192559219.png\" alt=\"image-20220312192559219\" style=\"zoom:50%;\" />\n\n那我们这里就使用Redis来实现Session的存储和管理。\n\nSpring Session其核心其实就是实现了一个过滤器链，在这个过滤器链里获取并存储session,并且对request和response进行了一层包装，对session等方法进行重写。\n\n那我们用以下的demo来测试一下。\n\n![image-20220312193852423](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312193852423.png)\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312194250441.png\" alt=\"image-20220312194250441\" style=\"zoom:50%;\" />\n\n访问web路径，然后redis中出现了三条数据，这三条正是我们session的信息，以及session过期的一些标志key。\n\n我们访问index，看看能不能获取到session里面的属性。\n\n![image-20220312194522855](https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220312194522855.png)\n\n可以获取到的。\n\n如果是要重写session的名称，过期时间等设置的话，就要重写`CookieSerializer`这个方法\n\n```java\n@Configuration\n@EnableRedisHttpSession\npublic class RedisSessionConfig {\n\n    @Bean\n    public CookieSerializer cookieSerializer() {\n        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();\n        defaultCookieSerializer.setCookiePath(\"/\");\n        defaultCookieSerializer.setCookieName(\"session_id\");\n        defaultCookieSerializer.setUseBase64Encoding(true);\n        defaultCookieSerializer.setCookieMaxAge(60*60*24*7);\n        return defaultCookieSerializer;\n    }\n\n}\n```\n\n\n\n那在微服务中，怎么使用呢？\n\n\n\n### 微服务中的Spring Session\n\n',0,0,1,'1',0,'研究一下微服务下的认证和授权（1）（实战）介绍常见的认证和授权方式包括但不限于以下几种OAtuh2Session-CookieToken（jwt）OAtuh2的常见使用场景包括给第三方授予用户部分信息以及权限，大型系统内各小型系统之间的认证和授权等，这里可以以微信登录为例，当你登录某一网站下使用微信','2022-08-15 23:50:00','2022-06-06 21:20:18',0),(1533801238963957762,2,1506910849124057090,'Mysql','### InnoDb和MyISAM的区别\n\n1. innodb支持事务，myisam不支持事务\n2. innodb支持外键，myisam不支持\n3. innodb是聚簇索引，myisam是非聚索引。聚簇索引的文件存放在主键索引的叶子节点上，因此innodb必须有主键。辅助索引需要两次查询，先查询到主键，然后再通过主键查询到数据。\n4. innodb不保存表的具体行数，而myisam用一个变量保存了整个表的行数。\n5. innodb最小的粒度锁是行锁，myisam最小的粒度锁是表锁。一个更新语句会锁住整张表。\n\n\n\n### 一道面试题\n\n> 一张表，里面有ID自增主键，当insert了17条记录之后，删除了第15,16,17条记录，再把MySQL重启，再Insert一条记录，这条记录的ID是18还是15?\n\n这道题要分情况，如果在Mysql8.0之前，如果引擎是Innodb的话，且数据库重启的话，那么插入的值是15，如果没有重启则是18。而myisam无论重不重启数据库，插入的值都是18。这是因为在mysql8.0之前，innodb存放主键的值是在内存中存放的，而myisam通过把自增主键的最大id保存在数据文件中，所以myisam不会丢失。但是在mysql8.0后，这个信息写入了共享表空间中，所以服务重启之后，还是可以继续追溯这个自增列的ID变化情况的。\n\n\n\n> 哪个存储引擎执行select count(*)更快\n\n在myisam引擎中，表的总行数是存储在硬盘上的，可以直接返回总数据。\n\n在innodb引擎中，没有把总行数存储到硬盘上，会先把数据读出来，然后累加，返回总数量。\n\n\n\n### 什么是索引\n\n1. 索引的本质是数据结构\n2. 索引的目的是为了提高查询效率，可以类比字典的目录，火车站的车次表。\n3. 索引一般以索引文件的形式存储在硬盘上。\n\n优势：\n\n- 提高数据检索效率，降低数据库IO成本。\n- 降低数据排序的成本，降低CPU的消耗。\n\n劣势：\n\n- 索引也是一张表，保存了主键和索引字段，并指向实体表的记录，所以也需要占用内存。\n- 虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行更新操作，mysql需要对索引进行调整。\n\n\n\nB+Tree相对于B-Tree有几点不同：\n\n1. 非叶子节点只存储键值信息；\n2. 所有叶子节点之间都有一个链指针；\n3. 数据记录都存放在叶子节点中\n\n\n\n### myisam和innodb主键索引和辅助索引的结构\n\n#### myisam\n\nmyisam引擎索引结构的叶子节点的数据域，存放的并不是实际的数据记录，而是数据记录的地址。而且索引文件与数据文件分离，这样的索引成为`非聚簇索引`。myisam的主索引和辅助索引区别并不大，只是主键索引不能有重复的关键字。\n\n先从索引文件中查找到索引节点，从中拿到数据的文件指针，再到数据文件中通过文件指针定位了具体的数据，辅助索引类似。\n\n\n\n#### innodb\n\ninnodb引擎的叶子节点的数据域存放的就是实际的数据记录（对于主索引，此处会存放表中所有的数据记录；对于辅助索引此处会引用主键，检索的时候通过主键索引，找到对应数据行）。innodb的数据文件本身就是主键索引文件，这样的索引就被成为`聚簇索引`，一个表只能有一个聚簇索引。\n\n\n\n### 回表\n\n在辅助索引中，叶子节点只存储数据的对应的主键，如果我们要通过辅助索引来查询，那么必须先在辅助索引上检索字段，到达其叶子节点后获取对应的主键，然后通过主键在主索引上再进行对应的检索操作。\n\n![img](https://images.alsritter.icu/images/2021/07/15/20210716090903.png)\n\n也就是说如果在一棵高度为 3 的辅助索引树中查找数据，那需要对这棵辅助索引树遍历 3 次找到指定主键，如果聚集索引树的高度同样为 3，那么还需要对聚集索引树进行 3 次查找，最终找到一个完整的行数据所在的页，因此一共需要 6 次逻辑 IO 访问以得到最终的一个数据页。\n\n### 一道面试题\n\n> 为什么Mysql索引要用B+树而不是B树\n\n这是因为需要考虑IO对性能的影响，B树的每个节点都存储数据，而B+树只有叶子节点才存储数据，在查找相同数据量的情况下，B树的高度更高，IO更频繁。而且索引是存储在硬盘上的，当数据量大的时候就不能把整个索引全部加载到内存了，只能逐一加载每一个硬盘页。而且Mysql底层对B+树进行了优化，在叶子节点是双向链表，且在链表的头结点和尾节点也是循环指向的。这样子遍历的效率会更高。\n\n\n\n### 覆盖索引\n\n- 就是select的数据列只用从索引中就能够取得，不必读取数据行，MySQL可以利用索引返回select列表中的字段，而不必根据索引再次读取数据文件，换句话说**查询列要被所建的索引覆盖**。\n\n- 索引是高效找到行的一个方法，但是一般数据库也能使用索引找到一个列的数据，因此它不必读取整个行。毕竟索引叶子节点存储了它们索引的数据，当能通过读取索引就可以得到想要的数据，那就不需要读取行了。一个索引包含（覆盖）满足查询结果的数据就叫做覆盖索引。\n\n也就是**索引即数据，数据即索引**。\n\n所以要为了避免回表查询，尽量使用到索引覆盖，我们要避免使用 `select * `\n\n\n\n### Mysql事务\n\n事务的ACID属性\n\n- A （原子性）：整个事务中的所有操作，要么全部完成，要么全部不完成，不可能停滞在中间某个环节，如果事务在执行过程中发生错误，会被回滚到事务开始前的状态，就像这个事务从来没有执行过一样。\n- C（一致性）：在事务开始之前和事务结束以后，数据库的完整性约束没有被破坏。\n- I（隔离性）：一个事务的执行不能被其他事务干扰。即一个事务内部的操作及使用的数据对其他并发事务是隔离的，并发执行的各个事务之间不能互相干扰。\n- D（持久性）：在事务完成以后，该事务所对数据库所做的更改便持久的保存在数据库之中，并不会被回滚。\n\n\n\n并发事务处理带来的问题：\n\n- 更新丢失：事务A和事务B选择同一行，然后基于最初选定的值更新该行时候，由于两个事务都不知道彼此的存在，就会发生丢失更新的问题。\n- 脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据\n- 不可重复读：事务A多次读取同一数据，事务B在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取统一数据时，结果不一致。\n- 幻读：幻读和不可重复读类似。它发生在一个事务A读取了几行数据，接着另一个并发事务B插入了一些数据时。在随后的查询中，事务A就会发生多了一些原本不存在的记录，就想好发生了幻觉一样。\n\n\n\n**幻读和不可重复读的区别：**\n\n- **不可重复读的重点是修改**：在同一事务中，同样的条件，第一次读的数据和第二次读的数据不一样。（因为中间有其他事务提交了修改）\n- **幻读的重点在于新增或者删除**：在同一事务中，同样的条件,，第一次和第二次读出来的记录数不一样。（因为中间有其他事务提交了插入/删除）\n\n\n\n事务的隔离级别：\n\n- 读未提交：最低的隔离级别，允许读取尚未提交的数据变更，可能会造成脏读，幻读或不可重复读。\n- 读已提交：允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发送。\n- 可重复读：对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读或不可重复读，但幻读仍有可能发生。\n- 可串行化：最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰。\n\n\n\n### MVCC多版本并发控制\n\n可以认为MVCC是行级锁的一个变种，但它在很多情况下避免了加锁操作，因此开销更低。虽然实现机制有所不同，但大都实现了非阻塞的读操作，写操作也只是锁定必要的行。\n\nMVCC的实现是通过保存数据在某个时间点的快照来实现的。也就是不管需要执行多久时间每个事务看到的数据都是一致的。\n\n典型的MVCC实现方式，分为**乐观并发控制和悲观并发控制**。InnoDB的MVCC是通过在每行记录后面保存两个隐藏的列来实现。这两个列一个保存了行的创建时间，一个保存行的过期时间（删除时间）。当然存储的并不是真实的时间，而是系统版本号。每开始一个新的事务，系统版本号都会自动递增。事务开始时刻的系统版本号会作为事务的版本号，用来和查询到的每行记录的版本号进行对比。\n\n\n\n### 事务日志\n\n事务日志包括：重做日志redo log和回滚日志undo log\n\n- redo log（重做日志）实现持久化和原子性\n\n​	在innoDB的存储引擎中，事务日志通过redo log和innoDB存储引擎的日志缓冲实现。事务开启时，事务中的操作，都会先写入存储引擎的日志缓冲中，在事务提交前，这些缓冲的日志都需要提前刷新到磁盘上持久化。当事务提交后，在缓冲区映射的数据文件才会慢慢刷新到磁盘。此时如果数据库宕机，那么当系统重启进行恢复时，就可以根据redo log中记录的日志，把数据库恢复到崩溃前的一个状态。未完成的事务也可以继续提交，也可以选择回滚，这基于恢复的策略而定。\n\n在系统启动的时候，就已经为redo log分配了一块连续的存储空间，以顺序追加的方式记录redolog通过顺序IO来改善性能。所有的事务共享redolog的存储空间，他们的redolog按语句的执行顺序，依次交替的记录在一起。\n\n- undo log（回滚日志）实现一致性\n\n  undo log主要为事务的而回滚服务。在事务执行的过程中，除了记录redo log，，还会记录一定量的undo log。undo log记录了数据在每个操作前的状态，如果事务执行过程中需要回滚，就可以根据undo log进行回滚操作。单个事务的回滚指挥回滚当前事务做的操作，并不会影响到其他的事务做的操作。Undo记录的是已部分完成并且写入硬盘的未完成的事务，默认情况下回滚日志是记录表空间中的。\n\n两种日志均可以视为一种恢复操作，redo log是恢复提交事务修改的叶草页操作，而undo log是回滚行记录到特定版本。二者记录的内容也不同，redolog是物理日志，记录也得物理修改操作，而undolog是逻辑日志，根据每行记录进行记录。\n\n> Mysql中有多少种日志？\n\n- 错误日志：记录出错信息，也记录一些警告信息或者正确的信息。\n- 查询日志：记录所有对数据库请求的信息，不论这些请求是否得到了正确的执行。\n- 慢查询日志：设置一个阈值，将运行时间超过该值的所有SQL语句都记录到慢查询的日志文件中。\n- 二进制日志：记录对数据库执行更改的所有操作。\n- 中继日志：中继日志也是二进制日志，用来给slave库恢复\n- 事务日志：也就是redo log和undo log\n\n\n\n### Mysql锁机制\n\n### 锁的分类\n\n**从对数据操作的类型分类**：\n\n- **读锁**（共享锁）：针对同一份数据，多个读操作可以同时进行，不会互相影响\n- **写锁**（排他锁）：当前写操作没有完成前，它会阻断其他写锁和读锁\n\n**从对数据操作的粒度分类**：\n\n为了尽可能提高数据库的并发度，每次锁定的数据范围越小越好，理论上每次只锁定当前操作的数据的方案会得到最大的并发度，但是管理锁是很耗资源的事情（涉及获取，检查，释放锁等动作），因此数据库系统需要在高并发响应和系统性能两方面进行平衡，这样就产生了“锁粒度（Lock granularity）”的概念。\n\n- **表级锁**：开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突的概率最高，并发度最低（MyISAM 和 MEMORY 存储引擎采用的是表级锁）；\n- **行级锁**：开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度也最高（InnoDB 存储引擎既支持行级锁也支持表级锁，但默认情况下是采用行级锁）；\n- **页面锁**：开销和加锁时间界于表锁和行锁之间；会出现死锁；锁定粒度界于表锁和行锁之间，并发度一般。\n\n适用：从锁的角度来说，表级锁更适合于以查询为主，只有少量按索引条件更新数据的应用，如Web应用；而行级锁则更适合于有大量按索引条件并发更新少量不同数据，同时又有并发查询的应用，如一些在线事务处理（OLTP）系统。\n\n\n\n### 锁模式\n\nInnodb有三种行锁的算法\n\n- 记录锁，单个行记录上的锁。对索引项加锁，锁定符合条件的行，其他事务不能修改和删除加锁项。\n\n```mysql\nselect * from table where id = 1 for update\n```\n\n它会在id = 1的记录上加上记录锁，以阻止其他事务插入，更新，删除id = 1这一行。\n\n在通过主键索引与唯一索引对数据进行update操作时也会对该行数据加记录锁。\n\n```mysql\nupdate set age =  where id = 1;\n```\n\n- 间隙锁（gap locks）：当我们使用范围条件而不是相等条件检索数据，并请求共享或排他锁时，innodb会给符合条件的已有数据记录的索引项加锁。对于键值在条件范围内并不存在的记录，叫做间隙。\n\n​	对索引项之间的间隙加锁，锁定记录的范围（对第一条记录前的间隙或最后一条记录后的间隙加锁），不包含索引项本身，其他事务不能在锁范围内插入数据，这样防止了别的事务新增幻影行。\n\n```mysql\nSELECT * FROM table WHERE id BETWEN 1 AND 10 FOR UPDATE;\n```\n\n即所有在`（1，10）`区间内的记录行都会被锁住，所有id 为 2、3、4、5、6、7、8、9 的数据行的插入会被阻塞，但是 1 和 10 两条记录行并不会被锁住。\n\nGAP锁的目的，是为了防止同一事务的两次当前读，出现幻读的情况。\n\n- 临键锁（Next-key locks）\n\n是记录锁与间隙锁的组合，他的封锁范围既包含索引记录，又包含索引区间。\n\n> select for update有什么含义，会锁表还是锁行还是其他\n\nfor update仅适用于InnoDB，且必须在事务块(Begin，Commit)中才能生效。在进行事务操作时，通过\"for update\"语句，MySQL会对查询结果集中每行数据都添加排他锁，其他线程对该记录的更新与删除操作都会阻塞。排他锁包含行锁、表锁。\n\n**InnoDB这种行锁实现特点意味着：只有通过索引条件检索数据，InnoDB才使用行级锁，否则，InnoDB将使用表锁！**\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220310151357683.png\" alt=\"image-20220310151357683\" style=\"zoom:50%;\" />\n\n**for update使用row lock还是table lock取决于判断的字段是否用到了索引，如果字段不存在就无锁，如果字段是走索引，无论是主键索引还是普通索引，只要是走索引的话，都是行锁，否则就是表锁。**\n\n### 死锁\n\n**死锁产生**：\n\n- 死锁是指两个或多个事务在同一资源上相互占用，并请求锁定对方占有的资源，从而导致恶行循环\n- 当事务试图以不同的顺序锁定资源时，就可能产生死锁。多个事务同时锁定一个资源时也有可能会产生死锁。\n\n- 锁的行为和顺序和存储引擎相关。以同样的顺序执行语句，有些存储引擎会产生死锁有些不会——死锁有双重原因：真正的数据冲突；存储引擎的实现方式。\n\n**检测死锁**：数据库系统实现了各种死锁检测和死锁超时的机制。InnoDB存储引擎能检测到死锁的循环依赖并立即返回一个错误。\n\n**死锁恢复**：死锁发生以后，只有部分或完全回滚其中一个事务，才能打破死锁，InnoDB目前处理死锁的方法是，将持有最少行级排他锁的事务进行回滚。所以事务型应用程序在设计时必须考虑如何处理死锁，多数情况下只需要重新执行因死锁回滚的事务即可。\n\n**外部锁的死锁检测**：发生死锁后，InnoDB 一般都能自动检测到，并使一个事务释放锁并回退，另一个事务获得锁，继续完成事务。但在涉及外部锁，或涉及表锁的情况下，InnoDB 并不能完全自动检测到死锁， 这需要通过设置锁等待超时参数 innodb_lock_wait_timeout 来解决\n\n**死锁影响性能**：死锁会影响性能而不是会产生严重错误，因为InnoDB会自动检测死锁状况并回滚其中一个受影响的事务。在高并发系统上，当许多线程等待同一个锁时，死锁检测可能导致速度变慢。 有时当发生死锁时，禁用死锁检测（使用innodb_deadlock_detect配置选项）可能会更有效，这时可以依赖`innodb_lock_wait_timeout`设置进行事务回滚。\n\n\n\n#### innoDB避免死锁\n\n- 可以在事务开始时使用`select ... for update`来获取必要的锁。\n- 在事务中，如果要更新记录，应该直接申请足够级别的锁，即排他锁。\n- 如果事务需要修改或锁定多个表，在应在每个事务中以相同的顺序使用加锁语句。尽量约定以相同的顺序来访问表。\n- 改变事务隔离级别。\n\n如果出现死锁，可以用 `show engine innodb status; `命令来确定最后一个死锁产生的原因。返回结果中包括死锁相关事务的详细信息，如引发死锁的 SQL 语句，事务已经获得的锁，正在等待什么锁，以及被回滚的事务等。据此可以分析死锁产生的原因和改进措施。\n\n\n\n### 百万级别或以上的数据如何删除\n\n关于索引：由于索引需要额外的维护成本，因为索引文件是单独存在的文件,所以当我们对数据的增加,修改,删除,都会产生额外的对索引文件的操作,这些操作需要消耗额外的IO,会降低增/改/删的执行效率。所以，在我们删除数据库百万级别数据的时候，查询MySQL官方手册得知删除数据的速度和创建的索引数量是成正比的。\n\n1. 所以我们想要删除百万数据的时候可以先删除索引（此时大概耗时三分多钟）\n2. 然后删除其中无用数据（此过程需要不到两分钟）\n3. 删除完成后重新创建索引(此时数据较少了)创建索引也非常快，约十分钟左右。\n4. 与之前的直接删除绝对是要快速很多，更别说万一删除中断,一切删除会回滚。那更是坑了。\n5. 总结就是：先删索引再删数据',0,0,1,'1',0,'InnoDb和MyISAM的区别innodb支持事务，myisam不支持事务innodb支持外键，myisam不支持innodb是聚簇索引，myisam是非聚索引。聚簇索引的文件存放在主键索引的叶子节点上，因此innodb必须有主键。辅助索引需要两次查询，先查询到主键，然后再通过主键查询到数据。in','2022-08-15 23:50:00','2022-06-06 21:21:27',0),(1533801362536542210,2,1506910849124057090,'计算机操作系统的操作指南','# 计算机操作系统的操作指南\n\n### 1. 用户态和内核态\n\n用户态：应用程序在执行的过程中，**CPU处在一个可执行的特权级的状态**，**不能直接访问一些特殊的机械指令和直接访问IO**，这个就是用户态  \n\n内核态: 操作系统在运行中，CPU处在的一个状态，在这个状态下，操作系统可以**执行任何一条指令**（包括特权指令和访问IO的指令）\n\n这使得我们系统的安全性得到一个保障，当我们应用程序处于用户态的时候，无法执行一些特定的指令，无法完全的控制操作系统，当CPU处于内核态的时候，可以完全的控制整个操作系统。\n\n但是在执行系统调用的时候，需要执行特权级的转换（从用户态到内核态的转换）和堆栈的一个切换都需要一定的开销，这种开销的代价换来的回报是安全可靠。\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220228092817224.png\" alt=\"image-20220228092817224\" style=\"zoom:50%;\" />\n\n1. 用户态： 用户态运行的进程可以直接读取用户程序的数据。\n2. 系统态： 系统态运行的进程和程序几乎可以访问计算机的任何资源，不受限制。  \n\n\n\n什么是系统调用？我们运行的程序基本都是运行在用户态，如果我们调用操作系统提供的系统态级别的子功能就需要**系统调用**。\n\n也就是说在我们运行的用户程序中，凡是与系统态级别的资源有关的操作（如文件管理、进程控制、内存管理等)，都必须通过系统调用方式向操作系统提出服务请求，并由操作系统代为完成。\n\n这些系统调用按功能大致可分为如下几类：\n\n- 设备管理。完成设备的请求或释放，以及设备启动等功能。\n- 文件管理。完成文件的读、写、创建及删除等功能。\n- 进程控制。完成进程的创建、撤销、阻塞及唤醒等功能。\n- 进程通信。完成进程之间的消息传递或信号传递等功能。\n- 内存管理。完成内存的分配、回收以及获取作业占用内存区大小及地址等功能。\n\n\n\n### 进程和线程的区别\n\n线程是进程划分更小的一个运行单位，一个进程在执行过程中可能会产生多个线程，线程和进程最大的不同在于基本各进程都是保持独立，而各线程则不一定，因为同一进程中的线程容易互相影响。线程执行的开销小，但不利于资源的管理和保护。进程则相反。\n\n\n\n### 产生死锁的条件\n\n如果系统中以下四个条件同时成立，那么就能引起死锁；\n\n- 互斥： 资源必须处于非共享模式，即一次只有一个进程可使用。如果另一进程申请该资源，那么必须等待该资源被释放为止。\n- 占有并等待：一个进程至少应该占有一个资源，并等待另一资源，而该资源被其他进程所占有。\n- 非抢占： 资源不能被抢占，只有在持有资源的进程完成任务后，该资源才会被释放。\n- 循环等待：有一组等待进程，p0等待的资源被p1占有，p1等待的资源被p2占有，pn-1等待的资源被pn占有。\n\n\n\n### 操作系统的内存管理机制和内存管理方式\n\n操作系统的内存管理机制分为连续分配管理方式和非连续分配管理方式。连续分为管理方式指为一个用户程序分配一个连续的内存空间，常见的如 **块式管理**。同样地，非连续分配管理方式则允许一个程序所使用的内存分布在不相邻的内存中，常见如页式管理，段式管理。\n\n1. **块式管理**： 将内存分为几个固定大小的块，每个块中只包含一个进程。如果程序运行需要内存的话，操作系统就分配给它一块内存空间。但是如果程序运行只需要一个很小的空间，那么分配的这块内存很大一部分就几乎被浪费了。这些在每个块中未被利用的空间，我们称之为碎片。\n2. **页式管理**：顾名思义，就是把内存分为大小相等且固定的一页一页的形式，页较小，相对于块式管理的划分力度更大，**提高了内存的利用率**，减少了碎片。页式管理通过页表对应逻辑地址和物理地址。\n3. **段式管理**：页式管理虽然提高了内存的利用率，但是页式管理其中的页实际并无任何实际意义。段式管理把主存分为一段段的，段是有实际意义的，每个段定义了一组逻辑信息，例如有主程序的MAIN，子程序段X等。段式管理通过段表对应逻辑地址和物理地址。\n4. 段页式管理机制：结合了段式管理和页式管理的优点，把主存分成若干段，每个段又分成若干页。\n\n总结：页是物理单位，段是逻辑单位，分页可以有效提高内存利用率，分段可以更好地满足用户需求。\n\n区别：\n\n1. 共同点：\n\n- 分页机制和分段机制都是为了提高内存的利用率，减少碎片。\n- 页和段都是离散存储的，所以两者都是离散分配内存的方式。但是每个页和段中的内存是连续的。\n\n2. 区别：\n\n- 页的大小的是固定的，由操作系统决定；而端的大小不固定，取决于我们运行的程序。\n- 分页仅仅是为了满足操作系统内存管理的需求，而段是逻辑信息的单位，在程序中可以体现为代码段，数据段，能够更好满足用户的需要。\n\n\n\n\n\n',0,0,1,'1',0,'计算机操作系统1.用户态和内核态用户态：应用程序在执行的过程中，CPU处在一个可执行的特权级的状态，不能直接访问一些特殊的机械指令和直接访问IO，这个就是用户态内核态:操作系统在运行中，CPU处在的一个状态，在这个状态下，操作系统可以执行任何一条指令（包括特权指令和访问IO的指令）这使得我们系统的安','2022-08-15 23:50:00','2022-06-06 21:21:56',0),(1534146397555666946,2,1506910849124057090,'哒哒哒','我们蛇蛇第三方第三方第三方第三方第三方所发生的方法水电费第三方佛山市非范德萨第三方第三方第三方第三方的三分大赛范德萨范德萨',0,0,0,'1',0,'是的撒','2022-06-07 20:30:00','2022-06-07 20:12:59',1),(1534146954068504578,2,1506910849124057090,'得到','的滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多多',0,0,0,'0',0,'得到','2022-06-07 20:15:12','2022-06-07 20:15:12',1),(1534424017899991042,2,1506910849124057090,'测试是否可以发帖','## 测试能否发帖\n测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖',0,0,2,'1',4,'测试能否发帖测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖，测试是否可以发帖','2022-08-15 23:50:00','2022-06-08 14:36:09',0),(1534432686079324162,2,1506910849124057090,'这是一个文章','## 这是一个文章\n### InnoDb和MyISAM的区别\n\n1. innodb支持事务，myisam不支持事务\n2. innodb支持外键，myisam不支持\n3. innodb是聚簇索引，myisam是非聚索引。聚簇索引的文件存放在主键索引的叶子节点上，因此innodb必须有主键。辅助索引需要两次查询，先查询到主键，然后再通过主键查询到数据。\n4. innodb不保存表的具体行数，而myisam用一个变量保存了整个表的行数。\n5. innodb最小的粒度锁是行锁，myisam最小的粒度锁是表锁。一个更新语句会锁住整张表。\n\n\n\n### 一道面试题\n\n> 一张表，里面有ID自增主键，当insert了17条记录之后，删除了第15,16,17条记录，再把MySQL重启，再Insert一条记录，这条记录的ID是18还是15?\n\n这道题要分情况，如果在Mysql8.0之前，如果引擎是Innodb的话，且数据库重启的话，那么插入的值是15，如果没有重启则是18。而myisam无论重不重启数据库，插入的值都是18。这是因为在mysql8.0之前，innodb存放主键的值是在内存中存放的，而myisam通过把自增主键的最大id保存在数据文件中，所以myisam不会丢失。但是在mysql8.0后，这个信息写入了共享表空间中，所以服务重启之后，还是可以继续追溯这个自增列的ID变化情况的。\n\n\n\n> 哪个存储引擎执行select count(*)更快\n\n在myisam引擎中，表的总行数是存储在硬盘上的，可以直接返回总数据。\n\n在innodb引擎中，没有把总行数存储到硬盘上，会先把数据读出来，然后累加，返回总数量。\n\n\n\n### 什么是索引\n\n1. 索引的本质是数据结构\n2. 索引的目的是为了提高查询效率，可以类比字典的目录，火车站的车次表。\n3. 索引一般以索引文件的形式存储在硬盘上。\n\n优势：\n\n- 提高数据检索效率，降低数据库IO成本。\n- 降低数据排序的成本，降低CPU的消耗。\n\n劣势：\n\n- 索引也是一张表，保存了主键和索引字段，并指向实体表的记录，所以也需要占用内存。\n- 虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行更新操作，mysql需要对索引进行调整。\n\n\n\nB+Tree相对于B-Tree有几点不同：\n\n1. 非叶子节点只存储键值信息；\n2. 所有叶子节点之间都有一个链指针；\n3. 数据记录都存放在叶子节点中\n\n\n\n### myisam和innodb主键索引和辅助索引的结构\n\n#### myisam\n\nmyisam引擎索引结构的叶子节点的数据域，存放的并不是实际的数据记录，而是数据记录的地址。而且索引文件与数据文件分离，这样的索引成为`非聚簇索引`。myisam的主索引和辅助索引区别并不大，只是主键索引不能有重复的关键字。\n\n先从索引文件中查找到索引节点，从中拿到数据的文件指针，再到数据文件中通过文件指针定位了具体的数据，辅助索引类似。\n\n\n\n#### innodb\n\ninnodb引擎的叶子节点的数据域存放的就是实际的数据记录（对于主索引，此处会存放表中所有的数据记录；对于辅助索引此处会引用主键，检索的时候通过主键索引，找到对应数据行）。innodb的数据文件本身就是主键索引文件，这样的索引就被成为`聚簇索引`，一个表只能有一个聚簇索引。\n\n\n\n### 回表\n\n在辅助索引中，叶子节点只存储数据的对应的主键，如果我们要通过辅助索引来查询，那么必须先在辅助索引上检索字段，到达其叶子节点后获取对应的主键，然后通过主键在主索引上再进行对应的检索操作。\n\n![img](https://images.alsritter.icu/images/2021/07/15/20210716090903.png)\n\n也就是说如果在一棵高度为 3 的辅助索引树中查找数据，那需要对这棵辅助索引树遍历 3 次找到指定主键，如果聚集索引树的高度同样为 3，那么还需要对聚集索引树进行 3 次查找，最终找到一个完整的行数据所在的页，因此一共需要 6 次逻辑 IO 访问以得到最终的一个数据页。\n\n### 一道面试题\n\n> 为什么Mysql索引要用B+树而不是B树\n\n这是因为需要考虑IO对性能的影响，B树的每个节点都存储数据，而B+树只有叶子节点才存储数据，在查找相同数据量的情况下，B树的高度更高，IO更频繁。而且索引是存储在硬盘上的，当数据量大的时候就不能把整个索引全部加载到内存了，只能逐一加载每一个硬盘页。而且Mysql底层对B+树进行了优化，在叶子节点是双向链表，且在链表的头结点和尾节点也是循环指向的。这样子遍历的效率会更高。\n\n\n\n### 覆盖索引\n\n- 就是select的数据列只用从索引中就能够取得，不必读取数据行，MySQL可以利用索引返回select列表中的字段，而不必根据索引再次读取数据文件，换句话说**查询列要被所建的索引覆盖**。\n\n- 索引是高效找到行的一个方法，但是一般数据库也能使用索引找到一个列的数据，因此它不必读取整个行。毕竟索引叶子节点存储了它们索引的数据，当能通过读取索引就可以得到想要的数据，那就不需要读取行了。一个索引包含（覆盖）满足查询结果的数据就叫做覆盖索引。\n\n也就是**索引即数据，数据即索引**。\n\n所以要为了避免回表查询，尽量使用到索引覆盖，我们要避免使用 `select * `\n\n\n\n### Mysql事务\n\n事务的ACID属性\n\n- A （原子性）：整个事务中的所有操作，要么全部完成，要么全部不完成，不可能停滞在中间某个环节，如果事务在执行过程中发生错误，会被回滚到事务开始前的状态，就像这个事务从来没有执行过一样。\n- C（一致性）：在事务开始之前和事务结束以后，数据库的完整性约束没有被破坏。\n- I（隔离性）：一个事务的执行不能被其他事务干扰。即一个事务内部的操作及使用的数据对其他并发事务是隔离的，并发执行的各个事务之间不能互相干扰。\n- D（持久性）：在事务完成以后，该事务所对数据库所做的更改便持久的保存在数据库之中，并不会被回滚。\n\n\n\n并发事务处理带来的问题：\n\n- 更新丢失：事务A和事务B选择同一行，然后基于最初选定的值更新该行时候，由于两个事务都不知道彼此的存在，就会发生丢失更新的问题。\n- 脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据\n- 不可重复读：事务A多次读取同一数据，事务B在事务A多次读取的过程中，对数据作了更新并提交，导致事务A多次读取统一数据时，结果不一致。\n- 幻读：幻读和不可重复读类似。它发生在一个事务A读取了几行数据，接着另一个并发事务B插入了一些数据时。在随后的查询中，事务A就会发生多了一些原本不存在的记录，就想好发生了幻觉一样。\n\n\n\n**幻读和不可重复读的区别：**\n\n- **不可重复读的重点是修改**：在同一事务中，同样的条件，第一次读的数据和第二次读的数据不一样。（因为中间有其他事务提交了修改）\n- **幻读的重点在于新增或者删除**：在同一事务中，同样的条件,，第一次和第二次读出来的记录数不一样。（因为中间有其他事务提交了插入/删除）\n\n\n\n事务的隔离级别：\n\n- 读未提交：最低的隔离级别，允许读取尚未提交的数据变更，可能会造成脏读，幻读或不可重复读。\n- 读已提交：允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发送。\n- 可重复读：对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读或不可重复读，但幻读仍有可能发生。\n- 可串行化：最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰。\n\n\n\n### MVCC多版本并发控制\n\n可以认为MVCC是行级锁的一个变种，但它在很多情况下避免了加锁操作，因此开销更低。虽然实现机制有所不同，但大都实现了非阻塞的读操作，写操作也只是锁定必要的行。\n\nMVCC的实现是通过保存数据在某个时间点的快照来实现的。也就是不管需要执行多久时间每个事务看到的数据都是一致的。\n\n典型的MVCC实现方式，分为**乐观并发控制和悲观并发控制**。InnoDB的MVCC是通过在每行记录后面保存两个隐藏的列来实现。这两个列一个保存了行的创建时间，一个保存行的过期时间（删除时间）。当然存储的并不是真实的时间，而是系统版本号。每开始一个新的事务，系统版本号都会自动递增。事务开始时刻的系统版本号会作为事务的版本号，用来和查询到的每行记录的版本号进行对比。\n\n\n\n### 事务日志\n\n事务日志包括：重做日志redo log和回滚日志undo log\n\n- redo log（重做日志）实现持久化和原子性\n\n​	在innoDB的存储引擎中，事务日志通过redo log和innoDB存储引擎的日志缓冲实现。事务开启时，事务中的操作，都会先写入存储引擎的日志缓冲中，在事务提交前，这些缓冲的日志都需要提前刷新到磁盘上持久化。当事务提交后，在缓冲区映射的数据文件才会慢慢刷新到磁盘。此时如果数据库宕机，那么当系统重启进行恢复时，就可以根据redo log中记录的日志，把数据库恢复到崩溃前的一个状态。未完成的事务也可以继续提交，也可以选择回滚，这基于恢复的策略而定。\n\n在系统启动的时候，就已经为redo log分配了一块连续的存储空间，以顺序追加的方式记录redolog通过顺序IO来改善性能。所有的事务共享redolog的存储空间，他们的redolog按语句的执行顺序，依次交替的记录在一起。\n\n- undo log（回滚日志）实现一致性\n\n  undo log主要为事务的而回滚服务。在事务执行的过程中，除了记录redo log，，还会记录一定量的undo log。undo log记录了数据在每个操作前的状态，如果事务执行过程中需要回滚，就可以根据undo log进行回滚操作。单个事务的回滚指挥回滚当前事务做的操作，并不会影响到其他的事务做的操作。Undo记录的是已部分完成并且写入硬盘的未完成的事务，默认情况下回滚日志是记录表空间中的。\n\n两种日志均可以视为一种恢复操作，redo log是恢复提交事务修改的叶草页操作，而undo log是回滚行记录到特定版本。二者记录的内容也不同，redolog是物理日志，记录也得物理修改操作，而undolog是逻辑日志，根据每行记录进行记录。\n\n> Mysql中有多少种日志？\n\n- 错误日志：记录出错信息，也记录一些警告信息或者正确的信息。\n- 查询日志：记录所有对数据库请求的信息，不论这些请求是否得到了正确的执行。\n- 慢查询日志：设置一个阈值，将运行时间超过该值的所有SQL语句都记录到慢查询的日志文件中。\n- 二进制日志：记录对数据库执行更改的所有操作。\n- 中继日志：中继日志也是二进制日志，用来给slave库恢复\n- 事务日志：也就是redo log和undo log\n\n\n\n### Mysql锁机制\n\n### 锁的分类\n\n**从对数据操作的类型分类**：\n\n- **读锁**（共享锁）：针对同一份数据，多个读操作可以同时进行，不会互相影响\n- **写锁**（排他锁）：当前写操作没有完成前，它会阻断其他写锁和读锁\n\n**从对数据操作的粒度分类**：\n\n为了尽可能提高数据库的并发度，每次锁定的数据范围越小越好，理论上每次只锁定当前操作的数据的方案会得到最大的并发度，但是管理锁是很耗资源的事情（涉及获取，检查，释放锁等动作），因此数据库系统需要在高并发响应和系统性能两方面进行平衡，这样就产生了“锁粒度（Lock granularity）”的概念。\n\n- **表级锁**：开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突的概率最高，并发度最低（MyISAM 和 MEMORY 存储引擎采用的是表级锁）；\n- **行级锁**：开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度也最高（InnoDB 存储引擎既支持行级锁也支持表级锁，但默认情况下是采用行级锁）；\n- **页面锁**：开销和加锁时间界于表锁和行锁之间；会出现死锁；锁定粒度界于表锁和行锁之间，并发度一般。\n\n适用：从锁的角度来说，表级锁更适合于以查询为主，只有少量按索引条件更新数据的应用，如Web应用；而行级锁则更适合于有大量按索引条件并发更新少量不同数据，同时又有并发查询的应用，如一些在线事务处理（OLTP）系统。\n\n\n\n### 锁模式\n\nInnodb有三种行锁的算法\n\n- 记录锁，单个行记录上的锁。对索引项加锁，锁定符合条件的行，其他事务不能修改和删除加锁项。\n\n```mysql\nselect * from table where id = 1 for update\n```\n\n它会在id = 1的记录上加上记录锁，以阻止其他事务插入，更新，删除id = 1这一行。\n\n在通过主键索引与唯一索引对数据进行update操作时也会对该行数据加记录锁。\n\n```mysql\nupdate set age =  where id = 1;\n```\n\n- 间隙锁（gap locks）：当我们使用范围条件而不是相等条件检索数据，并请求共享或排他锁时，innodb会给符合条件的已有数据记录的索引项加锁。对于键值在条件范围内并不存在的记录，叫做间隙。\n\n​	对索引项之间的间隙加锁，锁定记录的范围（对第一条记录前的间隙或最后一条记录后的间隙加锁），不包含索引项本身，其他事务不能在锁范围内插入数据，这样防止了别的事务新增幻影行。\n\n```mysql\nSELECT * FROM table WHERE id BETWEN 1 AND 10 FOR UPDATE;\n```\n\n即所有在`（1，10）`区间内的记录行都会被锁住，所有id 为 2、3、4、5、6、7、8、9 的数据行的插入会被阻塞，但是 1 和 10 两条记录行并不会被锁住。\n\nGAP锁的目的，是为了防止同一事务的两次当前读，出现幻读的情况。\n\n- 临键锁（Next-key locks）\n\n是记录锁与间隙锁的组合，他的封锁范围既包含索引记录，又包含索引区间。\n\n> select for update有什么含义，会锁表还是锁行还是其他\n\nfor update仅适用于InnoDB，且必须在事务块(Begin，Commit)中才能生效。在进行事务操作时，通过\"for update\"语句，MySQL会对查询结果集中每行数据都添加排他锁，其他线程对该记录的更新与删除操作都会阻塞。排他锁包含行锁、表锁。\n\n**InnoDB这种行锁实现特点意味着：只有通过索引条件检索数据，InnoDB才使用行级锁，否则，InnoDB将使用表锁！**\n\n<img src=\"https://songtiancloud-1300061766.cos.ap-guangzhou.myqcloud.com/img/image-20220310151357683.png\" alt=\"image-20220310151357683\" style=\"zoom:50%;\" />\n\n**for update使用row lock还是table lock取决于判断的字段是否用到了索引，如果字段不存在就无锁，如果字段是走索引，无论是主键索引还是普通索引，只要是走索引的话，都是行锁，否则就是表锁。**\n\n### 死锁\n\n**死锁产生**：\n\n- 死锁是指两个或多个事务在同一资源上相互占用，并请求锁定对方占有的资源，从而导致恶行循环\n- 当事务试图以不同的顺序锁定资源时，就可能产生死锁。多个事务同时锁定一个资源时也有可能会产生死锁。\n\n- 锁的行为和顺序和存储引擎相关。以同样的顺序执行语句，有些存储引擎会产生死锁有些不会——死锁有双重原因：真正的数据冲突；存储引擎的实现方式。\n\n**检测死锁**：数据库系统实现了各种死锁检测和死锁超时的机制。InnoDB存储引擎能检测到死锁的循环依赖并立即返回一个错误。\n\n**死锁恢复**：死锁发生以后，只有部分或完全回滚其中一个事务，才能打破死锁，InnoDB目前处理死锁的方法是，将持有最少行级排他锁的事务进行回滚。所以事务型应用程序在设计时必须考虑如何处理死锁，多数情况下只需要重新执行因死锁回滚的事务即可。\n\n**外部锁的死锁检测**：发生死锁后，InnoDB 一般都能自动检测到，并使一个事务释放锁并回退，另一个事务获得锁，继续完成事务。但在涉及外部锁，或涉及表锁的情况下，InnoDB 并不能完全自动检测到死锁， 这需要通过设置锁等待超时参数 innodb_lock_wait_timeout 来解决\n\n**死锁影响性能**：死锁会影响性能而不是会产生严重错误，因为InnoDB会自动检测死锁状况并回滚其中一个受影响的事务。在高并发系统上，当许多线程等待同一个锁时，死锁检测可能导致速度变慢。 有时当发生死锁时，禁用死锁检测（使用innodb_deadlock_detect配置选项）可能会更有效，这时可以依赖`innodb_lock_wait_timeout`设置进行事务回滚。\n\n\n\n#### innoDB避免死锁\n\n- 可以在事务开始时使用`select ... for update`来获取必要的锁。\n- 在事务中，如果要更新记录，应该直接申请足够级别的锁，即排他锁。\n- 如果事务需要修改或锁定多个表，在应在每个事务中以相同的顺序使用加锁语句。尽量约定以相同的顺序来访问表。\n- 改变事务隔离级别。\n\n如果出现死锁，可以用 `show engine innodb status; `命令来确定最后一个死锁产生的原因。返回结果中包括死锁相关事务的详细信息，如引发死锁的 SQL 语句，事务已经获得的锁，正在等待什么锁，以及被回滚的事务等。据此可以分析死锁产生的原因和改进措施。\n\n\n\n### 百万级别或以上的数据如何删除\n\n关于索引：由于索引需要额外的维护成本，因为索引文件是单独存在的文件,所以当我们对数据的增加,修改,删除,都会产生额外的对索引文件的操作,这些操作需要消耗额外的IO,会降低增/改/删的执行效率。所以，在我们删除数据库百万级别数据的时候，查询MySQL官方手册得知删除数据的速度和创建的索引数量是成正比的。\n\n1. 所以我们想要删除百万数据的时候可以先删除索引（此时大概耗时三分多钟）\n2. 然后删除其中无用数据（此过程需要不到两分钟）\n3. 删除完成后重新创建索引(此时数据较少了)创建索引也非常快，约十分钟左右。\n4. 与之前的直接删除绝对是要快速很多，更别说万一删除中断,一切删除会回滚。那更是坑了。\n5. 总结就是：先删索引再删数据',0,0,1,'1',0,'这是一个文章InnoDb和MyISAM的区别innodb支持事务，myisam不支持事务innodb支持外键，myisam不支持innodb是聚簇索引，myisam是非聚索引。聚簇索引的文件存放在主键索引的叶子节点上，因此innodb必须有主键。辅助索引需要两次查询，先查询到主键，然后再通过主键查询','2022-08-15 23:50:00','2022-06-08 15:10:35',0),(1534802518222114818,2,1499226795866386433,'这只是题目而已','## 我发出来只是为了测试一下而已\n这是一个富文本编辑器，使用该编辑器我们可以发布许多好看的文章，通过设置不同的格式，展现出不同的文章风格。',0,0,0,'1',0,'我发出来只是为了测试一下而已这是一个富文本编辑器，使用该编辑器我们可以发布许多好看的文章，通过设置不同的格式，展现出不同的文章风格。','2022-08-15 23:50:00','2022-06-09 15:40:10',0),(1535895444775772161,2,1506910849124057090,'测试发帖','## 测试发帖\n我觉得这次的测试非常的重要，如果重要，那么我将会哈哈哈哈哈哈哈哈，凑够50个字数没有啊啊啊啊啊啊',0,0,0,'1',0,'测试发帖我觉得这次的测试非常的重要，如果重要，那么我将会哈哈哈哈哈哈哈哈，凑够50个字数没有啊啊啊啊啊啊','2022-08-15 23:50:00','2022-06-12 16:03:04',0),(1538075883544252417,2,1506910849124057090,'测试发布文章','## 测试发布文章\n测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章测试发布文章2016年02月18',0,0,0,'1',0,'测试发布文章','2022-08-15 23:50:00','2022-06-18 16:27:21',0),(1538080359919427586,2,1506910849124057090,'这是消息队列持久化','### 这是消息队列持久化\n这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化',0,0,0,'1',0,'这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列持久化这是消息队列','2022-08-15 23:50:00','2022-06-18 16:45:09',0),(1538082038932869122,2,1506910849124057090,'测试消息队列的应答','## 测试消息队列的应答\n测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答测试消息队列的应答v',0,0,0,'1',0,'测试消息队列的应答','2022-08-15 23:50:00','2022-06-18 16:51:49',0),(1538423219839684609,2,1506910849124057090,'修复消息队列bug','## 修复消息队列bug\n修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug',0,0,0,'1',0,'修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列bug修复消息队列','2022-08-15 23:50:00','2022-06-19 15:27:33',0),(1538425817447649282,2,1506910849124057090,'再次测试','### 再次测试\n再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试v',0,0,0,'1',0,'再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次测试再次','2022-08-15 23:50:00','2022-06-19 15:37:52',0);
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
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
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
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` tinyint DEFAULT '0' COMMENT '0 = 正常',
  `intro` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `sort` int NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  `user_id` bigint NOT NULL COMMENT '点赞用户uid',
  `to_user_id` bigint NOT NULL COMMENT '点赞目标的id',
  `post_id` bigint NOT NULL COMMENT '点赞所在的帖子uid',
  `to_id` bigint NOT NULL COMMENT '点赞内容的uid 评论uid或是文章uid',
  `type` int NOT NULL COMMENT '点赞类型 0 = 评论 1 = 文章',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '0 = 正常 1 = 已取消点赞',
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
INSERT INTO `thumb` VALUES (1534535969213018113,1506910849124057090,1506910849124057090,1533800812499709953,1533800812499709953,2,'2022-06-08 14:01:00','2022-06-08 14:01:00',0),(1534535969213018114,1506910849124057090,1506910849124057090,1533800693297590274,1533800693297590274,2,'2022-06-08 14:01:00','2022-06-08 14:01:00',0),(1537338812907245569,1499226795866386433,1506910849124057090,1534424017899991042,1534424017899991042,2,'2022-06-16 07:38:30','2022-06-16 07:38:30',0),(1551117479089049602,1506910849124057090,1499226795866386433,1533654884224634881,1533977537674317826,0,'2022-07-24 08:10:00','2022-07-24 08:10:00',0),(1551121253891424258,1506910849124057090,1506910849124057090,1533654884224634881,1533654884224634881,2,'2022-07-24 08:25:00','2022-07-24 08:25:00',0);
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

-- Dump completed on 2022-09-17 19:34:04
