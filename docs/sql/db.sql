-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_user
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
-- Current Database: `smart_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_user`;

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
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_collect_id_uindex` (`id`),
  KEY `user_collect_user_id_post_id_index` (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_collect`
--

LOCK TABLES `user_collect` WRITE;
/*!40000 ALTER TABLE `user_collect` DISABLE KEYS */;
INSERT INTO `user_collect` VALUES (1634836474234707970,1631698711704567810,1634756236859457537,0,'2023-03-12 08:39:23','2023-03-12 08:39:23'),(1634903685875920897,1631698711704567810,1631912653852844034,0,'2023-03-12 13:06:28','2023-03-12 13:06:28'),(1637095882134155266,1631698711704567810,1636019004044529665,0,'2023-03-18 14:17:28','2023-03-18 14:17:28');
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
  `follow_time` datetime NOT NULL COMMENT '关注时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_follow_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关注表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_follow`
--

LOCK TABLES `user_follow` WRITE;
/*!40000 ALTER TABLE `user_follow` DISABLE KEYS */;
INSERT INTO `user_follow` VALUES (1639496789094371330,1632406592896679937,1631698711704567810,'2023-03-25 13:17:48',1,'2023-03-25 05:17:49','2023-03-25 05:29:25'),(1639499839926243329,1632406592896679937,1631698711704567810,'2023-03-25 13:29:57',0,'2023-03-25 05:29:56','2023-03-25 05:29:56');
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
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '呢称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '登录账号',
  `gender` varchar(1) NOT NULL DEFAULT '' COMMENT '性别 0 = 男 1= 女',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(500) NOT NULL DEFAULT '' COMMENT '头像地址',
  `intro` varchar(255) NOT NULL DEFAULT '' COMMENT '个人介绍',
  `fan_num` int NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `follow_num` int NOT NULL DEFAULT '0' COMMENT '关注数',
  `article_num` int NOT NULL DEFAULT '0' COMMENT '文章数',
  `os` varchar(255) NOT NULL DEFAULT '' COMMENT '访问系统',
  `browser` varchar(255) NOT NULL DEFAULT '' COMMENT '访问浏览器',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '用户状态 0 = 正常',
  `last_login_time` timestamp NOT NULL COMMENT '上一次登录的时间',
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
INSERT INTO `user_profile` VALUES (1631698711704567810,1631698711704567810,'alickx','alickx1','89991829@qq.com','','','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fb420e1ac-6042-4d62-adbd-490724e2cf3a%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1680787643&t=1c5697d55f4f06421b44bec01f2bc616','我是小小妖怪，如果你是1212我，我12212哈哈哈哈，我是小小妖怪，如果你是我，我就会啊哈哈哈哈，我是小小妖怪，如果你1221我，我就会啊哈哈哈哈',3,-1,0,'','',0,'2023-03-11 03:30:59','','2023-03-29 15:36:48','2023-03-03 16:51:03'),(1632406592896679937,1632406592896679937,'ak87715700','ak87715700','alickx@foxmail.com','','','https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2Fb420e1ac-6042-4d62-adbd-490724e2cf3a%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1680787643&t=1c5697d55f4f06421b44bec01f2bc616','我是小小妖怪，如果你是我，我就会啊哈哈哈哈',0,2,0,'','',0,'2023-03-11 03:28:10','','2023-03-25 05:29:56','2023-03-05 15:43:55');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
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
  `user_id` bigint NOT NULL COMMENT '发送方id',
  `post_id` bigint NOT NULL COMMENT '文章id',
  `to_user_id` bigint NOT NULL COMMENT '接收方id',
  `content` varchar(2048) NOT NULL COMMENT '评论内容',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0 = 正常显示 1 = 隐藏',
  `type` tinyint(1) NOT NULL COMMENT '回复类型 0 = 一级评论 1 = 评论中回复',
  `first_comment_id` bigint DEFAULT NULL COMMENT '一级评论uid',
  `thumb_count` int NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `ip` varchar(20) DEFAULT NULL COMMENT '发布评论时的ip',
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
INSERT INTO `comment` VALUES (1636019703163731970,1631698711704567810,1636019004044529665,1631698711704567810,'123123213',0,0,NULL,1,NULL,'2023-03-15 15:01:07','2023-03-15 15:01:25',0),(1636019837142384642,1631698711704567810,1636019004044529665,1631698711704567810,'123',0,1,1636019703163731970,0,NULL,'2023-03-15 15:01:39','2023-03-15 15:01:39',0);
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
  `category_name` varchar(100) NOT NULL COMMENT '板块名称',
  `tag_name` varchar(100) NOT NULL COMMENT '标签名称',
  `author_id` bigint NOT NULL COMMENT '作者id',
  `title` varchar(255) NOT NULL COMMENT '文章题目',
  `content` longtext NOT NULL COMMENT '文章内容',
  `state` tinyint NOT NULL DEFAULT '0' COMMENT '文章状态 0 = 正常',
  `is_publish` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0 = 不公布  1 = 公布',
  `ip` varchar(20) DEFAULT NULL COMMENT '文章作者发布时的ip',
  `summary` varchar(255) NOT NULL COMMENT '文章摘要',
  `thumb_count` int NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数量',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '查看次数',
  `collect_count` int NOT NULL DEFAULT '0' COMMENT '收藏数量',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 = 未删除 1 = 已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `post_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1636019004044529665,'前端','Vue',1631698711704567810,'哈哈','啊哈哈哈哈哈哈哈哈',0,1,'127.0.0.1','啊哈哈哈哈哈哈哈哈',2,2,0,0,'2023-03-25 08:50:45','2023-03-15 14:58:20',0);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
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
  `to_id` bigint NOT NULL COMMENT '点赞内容的id',
  `type` int NOT NULL COMMENT '点赞类型 0 = 评论 1 = 文章',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 1 = 删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `thumb_id_uindex` (`id`),
  KEY `thumb_user_id_type_post_id_index` (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thumb`
--

LOCK TABLES `thumb` WRITE;
/*!40000 ALTER TABLE `thumb` DISABLE KEYS */;
INSERT INTO `thumb` VALUES (1625757307859329025,1571562360907292674,1625757062769369090,0,0,'2023-02-15 07:22:03','2023-02-15 07:22:03'),(1625758670680973314,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:27:28','2023-02-15 07:27:28'),(1625758727853531138,1571562360907292674,1625758597523922945,1,0,'2023-02-15 07:27:41','2023-02-15 07:27:41'),(1625758728243601409,1571562360907292674,1625758597523922945,1,0,'2023-02-15 07:27:41','2023-02-15 07:27:41'),(1625758810439376898,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:28:01','2023-02-15 07:28:01'),(1625758810980442114,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:28:01','2023-02-15 07:28:01'),(1625758812368756738,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:28:02','2023-02-15 07:28:02'),(1625758813006290945,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:28:02','2023-02-15 07:28:02'),(1625758813035651074,1571562360907292674,1625758643199893505,2,0,'2023-02-15 07:28:02','2023-02-15 07:28:02'),(1633098407715082242,1631698711704567810,1631912653852844034,0,0,'2023-03-07 13:32:56','2023-03-07 13:32:56'),(1633108408659677185,1631698711704567810,1633099158273200130,1,0,'2023-03-07 14:12:40','2023-03-07 14:12:40'),(1633108798834806786,1631698711704567810,1631912653852844034,0,0,'2023-03-07 14:14:13','2023-03-07 14:14:13'),(1633115977155383298,1631698711704567810,1631912653852844034,0,0,'2023-03-07 14:42:45','2023-03-07 14:42:45'),(1633118917597429762,1631698711704567810,1631912653852844034,0,0,'2023-03-07 14:54:26','2023-03-07 14:54:26'),(1634786026396082178,1631698711704567810,1634756236859457537,0,0,'2023-03-12 05:18:56','2023-03-12 05:18:56'),(1634793185880072194,1631698711704567810,1634756236859457537,0,0,'2023-03-12 05:47:23','2023-03-12 05:47:23'),(1634793215055650817,1631698711704567810,1634756236859457537,0,0,'2023-03-12 05:47:30','2023-03-12 05:47:30'),(1634901786984501250,1631698711704567810,1631912653852844034,0,0,'2023-03-12 12:58:55','2023-03-12 12:58:55'),(1634910345625415681,1631698711704567810,1634390849500307458,1,0,'2023-03-12 13:32:56','2023-03-12 13:32:56'),(1634915962209513473,1631698711704567810,1634756236859457537,0,0,'2023-03-12 13:55:15','2023-03-12 13:55:15'),(1634934892454002689,1631698711704567810,1634934835348553729,0,0,'2023-03-12 15:10:28','2023-03-12 15:10:28'),(1635997065515184129,1631698711704567810,1634934927203811329,1,0,'2023-03-15 13:31:10','2023-03-15 13:31:10'),(1636000337865039874,1631698711704567810,1636000287923462146,0,0,'2023-03-15 13:44:10','2023-03-15 13:44:10'),(1636001047335755778,1631698711704567810,1636000501409341442,1,0,'2023-03-15 13:46:59','2023-03-15 13:46:59'),(1636012264846577665,1631698711704567810,1636012209376907265,1,0,'2023-03-15 14:31:34','2023-03-15 14:31:34'),(1636012307074830338,1631698711704567810,1636012247939338242,2,0,'2023-03-15 14:31:44','2023-03-15 14:31:44'),(1636015110736359426,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:42:52','2023-03-15 14:42:52'),(1636015150926180354,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:02','2023-03-15 14:43:02'),(1636015155690909698,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:03','2023-03-15 14:43:03'),(1636015160963149825,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:04','2023-03-15 14:43:04'),(1636015162083028993,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:04','2023-03-15 14:43:04'),(1636015171499241473,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:07','2023-03-15 14:43:07'),(1636015178273042434,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:08','2023-03-15 14:43:08'),(1636015183608197121,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:10','2023-03-15 14:43:10'),(1636015189044015106,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:11','2023-03-15 14:43:11'),(1636015192084885506,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:12','2023-03-15 14:43:12'),(1636015197550063617,1631698711704567810,1636006728117825537,0,0,'2023-03-15 14:43:13','2023-03-15 14:43:13'),(1636019777960755201,1631698711704567810,1636019703163731970,1,0,'2023-03-15 15:01:25','2023-03-15 15:01:25'),(1639496703492808706,1632406592896679937,1636019004044529665,0,0,'2023-03-25 05:17:29','2023-03-25 05:17:29'),(1639550375383867393,1631698711704567810,1636019004044529665,0,0,'2023-03-25 08:50:45','2023-03-25 08:50:45');
/*!40000 ALTER TABLE `thumb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_interact`
--

DROP TABLE IF EXISTS `user_interact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_interact` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `type` tinyint NOT NULL COMMENT '类型 0 = 文章 1 = comment 2 = reply',
  `target_id` bigint NOT NULL COMMENT '目标id',
  `is_thumb` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 = false  1 = true',
  `is_comment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 = false  1 = true',
  `is_collect` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 = false  1 = true',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户交互表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_interact`
--

LOCK TABLES `user_interact` WRITE;
/*!40000 ALTER TABLE `user_interact` DISABLE KEYS */;
INSERT INTO `user_interact` VALUES (1636019703407001601,1631698711704567810,0,1636019004044529665,1,1,1,'2023-03-15 23:01:07','2023-03-15 23:01:07'),(1636019777960755202,1631698711704567810,1,1636019703163731970,1,0,0,'2023-03-15 23:01:25','2023-03-15 23:01:25'),(1636019837343711234,1631698711704567810,1,1636019004044529665,0,1,0,'2023-03-15 23:01:39','2023-03-15 23:01:39'),(1639496703903850497,1632406592896679937,0,1636019004044529665,1,0,0,'2023-03-25 13:17:29','2023-03-25 13:17:29');
/*!40000 ALTER TABLE `user_interact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_notice`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_notice` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_notice`;

--
-- Table structure for table `t_notice`
--

DROP TABLE IF EXISTS `t_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice` (
  `id` bigint NOT NULL COMMENT '主键id',
  `sender_id` bigint NOT NULL COMMENT '发送者的用户id',
  `receiver_id` bigint NOT NULL COMMENT '接受者用户id',
  `msg_info_id` bigint NOT NULL COMMENT '消息详细id',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '查看状态 0 = 未查看 1 = 已查看',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice`
--

LOCK TABLES `t_notice` WRITE;
/*!40000 ALTER TABLE `t_notice` DISABLE KEYS */;
INSERT INTO `t_notice` VALUES (1634391489702039554,1632406592896679937,1631698711704567810,1634391489634930690,1,'2023-03-11 11:11:11','2023-03-11 11:11:35'),(1634391776294637571,1632406592896679937,1631698711704567810,1634391776294637570,1,'2023-03-11 11:12:19','2023-03-11 11:12:31'),(1634392080545255427,1632406592896679937,1631698711704567810,1634392080545255426,1,'2023-03-11 11:13:32','2023-03-11 11:14:17'),(1634910346158133250,1631698711704567810,1632406592896679937,1634910346158133249,1,'2023-03-12 21:32:56','2023-03-25 13:17:59'),(1639496708093968386,1632406592896679937,1631698711704567810,1639496708043636737,1,'2023-03-25 13:17:30','2023-03-25 13:18:06'),(1639496791673864194,1632406592896679937,1631698711704567810,1639496791610949633,1,'2023-03-25 13:17:50','2023-03-25 13:18:11'),(1639499840840597506,1632406592896679937,1631698711704567810,1639499840777682946,1,'2023-03-25 13:29:57','2023-03-25 16:50:12');
/*!40000 ALTER TABLE `t_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_notice_message_info`
--

DROP TABLE IF EXISTS `t_notice_message_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice_message_info` (
  `id` bigint NOT NULL COMMENT '主键id',
  `target_id` bigint NOT NULL COMMENT '对象id 例如文章,评论等',
  `post_id` bigint DEFAULT NULL COMMENT '文章id',
  `post_title` varchar(100) DEFAULT NULL COMMENT '文章标题',
  `content` varchar(200) DEFAULT NULL COMMENT '通知消息内容',
  `msg_type` tinyint NOT NULL COMMENT '知消息类型 0 = 点赞 1 = 评论 2 = 关注 3 = @操作 4 = 系统提醒',
  `source_type` tinyint(1) NOT NULL COMMENT '源类型 0 = 文章 1 = 评论',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice_message_info`
--

LOCK TABLES `t_notice_message_info` WRITE;
/*!40000 ALTER TABLE `t_notice_message_info` DISABLE KEYS */;
INSERT INTO `t_notice_message_info` VALUES (1634391489634930690,1634391489043587074,1631912653852844034,'mysql面试题','真的',1,1,'2023-03-11 11:11:11','2023-03-11 11:11:11'),(1634391776294637570,1634390849500307458,1631912653852844034,'mysql面试题','谢谢你的文章，受益匪浅',1,1,'2023-03-11 11:12:19','2023-03-11 11:12:19'),(1634392080545255426,1634392079958020097,NULL,NULL,NULL,2,2,'2023-03-11 11:13:32','2023-03-11 11:13:32'),(1634910346158133249,1634910345625415681,1631912653852844034,'mysql面试题','谢谢你的文章，受益匪浅',0,1,'2023-03-12 21:32:56','2023-03-12 21:32:56'),(1639496708043636737,1639496703492808706,1636019004044529665,'哈哈','哈哈',0,0,'2023-03-25 13:17:30','2023-03-25 13:17:30'),(1639496791610949633,1639496789094371330,NULL,NULL,NULL,2,2,'2023-03-25 13:17:50','2023-03-25 13:17:50'),(1639499840777682946,1639499839926243329,NULL,NULL,NULL,2,2,'2023-03-25 13:29:57','2023-03-25 13:29:57');
/*!40000 ALTER TABLE `t_notice_message_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `smart_auth`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smart_auth` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `smart_auth`;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user` (
  `id` bigint NOT NULL,
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `user_email` varchar(255) NOT NULL COMMENT '用户邮箱',
  `is_activate` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否激活 0 = 未激活 1 = 已激活',
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
INSERT INTO `auth_user` VALUES (1631698711704567810,'alickx','$2a$10$2wb5Unk56JofTmSK4QD7wenz0NY0FTMeM9WIJEKFZECq/7X5N/zci','89991829@qq.com',1,'2023-03-03 16:51:03','2023-03-03 16:51:03'),(1632406592896679937,'ak87715700','$2a$10$fIef4tl5AIaWiFx6sUprQup9ujbMCbeYHJV2D2BqEn/MKPw7nZxSm','alickx@foxmail.com',1,'2023-03-05 15:43:55','2023-03-05 15:43:55');
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-06 12:14:48
