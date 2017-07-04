-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sale_management
-- ------------------------------------------------------
-- Server version	5.1.59-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `id` bigint(45) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (1,1);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!50001 DROP VIEW IF EXISTS `brand`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `brand` AS SELECT 
 1 AS `BRAND`,
 1 AS `STATUS`,
 1 AS `USER_ID`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `buying_code`
--

DROP TABLE IF EXISTS `buying_code`;
/*!50001 DROP VIEW IF EXISTS `buying_code`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `buying_code` AS SELECT 
 1 AS `buying_code`,
 1 AS `STATUS`,
 1 AS `USER_ID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `ID` bigint(45) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `full_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `address` varchar(450) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `note` varchar(450) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `transfer_id` varchar(45) DEFAULT NULL,
  `bill_id` bigint(45) DEFAULT NULL,
  `approver` bigint(45) DEFAULT NULL,
  `approved_date` datetime DEFAULT NULL,
  `buyer` bigint(45) DEFAULT NULL,
  `bought_date` datetime DEFAULT NULL,
  `transporter` bigint(45) DEFAULT NULL,
  `transported_date` datetime DEFAULT NULL,
  `transporter_vn` bigint(45) DEFAULT NULL,
  `transported_vn_date` datetime DEFAULT NULL,
  `checker` bigint(45) DEFAULT NULL,
  `checked_date` datetime DEFAULT NULL,
  `informer` bigint(45) DEFAULT NULL,
  `informed_date` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (3,3,0,'c','c','09','0987654321','Ha Noi','',NULL,'2017-06-26 15:00:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,3,0,'c','c','098765431','12354678','','',NULL,'2017-06-26 15:10:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,3,0,'c','c','123','124','','',NULL,'2017-06-27 11:06:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,3,0,'c','c','01685180626','Hà Nội','','',NULL,'2017-06-27 11:10:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,3,0,'Mit mit','c','1234568','123567','','',NULL,'2017-06-27 11:28:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_history`
--

DROP TABLE IF EXISTS `item_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_history` (
  `history_id` bigint(45) NOT NULL AUTO_INCREMENT,
  `id` bigint(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `link` varchar(450) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `update_by` bigint(50) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_history`
--

LOCK TABLES `item_history` WRITE;
/*!40000 ALTER TABLE `item_history` DISABLE KEYS */;
INSERT INTO `item_history` VALUES (1,4,'Giay lining','das','Tui la tui 123     ','dfsfsf',4.3,3,12.9,2,'2017-06-27 23:09:49');
/*!40000 ALTER TABLE `item_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `id` bigint(45) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `link` varchar(450) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `categoryId` bigint(45) NOT NULL,
  `transfer_id` varchar(45) DEFAULT NULL,
  `bill_id` bigint(45) DEFAULT NULL,
  `approver` bigint(45) DEFAULT NULL,
  `approved_date` datetime DEFAULT NULL,
  `buyer` bigint(45) DEFAULT NULL,
  `bought_date` datetime DEFAULT NULL,
  `transporter` bigint(45) DEFAULT NULL,
  `transported_date` datetime DEFAULT NULL,
  `transporter_vn` bigint(45) DEFAULT NULL,
  `transported_vn_date` datetime DEFAULT NULL,
  `checker` bigint(45) DEFAULT NULL,
  `checked_date` datetime DEFAULT NULL,
  `informer` bigint(45) DEFAULT NULL,
  `informed_date` datetime DEFAULT NULL,
  `user_id` bigint(45) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `approval_note` varchar(500) DEFAULT NULL,
  `buy_note` varchar(500) DEFAULT NULL,
  `real_cost` double DEFAULT NULL,
  `real_quantity` int(11) DEFAULT NULL,
  `real_price` double DEFAULT NULL,
  `compute_cost` double DEFAULT NULL,
  `compute_price` double DEFAULT NULL,
  `buying_code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Ten san pham','das3333','Size S         ','dfsfsf 23',2,5,10,'2017-07-03 09:54:08','2017-06-26 15:00:58',3,NULL,NULL,2,'2017-06-27 10:11:37',1,'2017-07-03 09:51:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,'',-1,NULL,'Đơn hàng này không còn size S',NULL,NULL,12,NULL,16,NULL),(2,'Giay nike','das','TEST  la sao  ','dfsfsf',2,2,4,'2017-07-03 23:23:25','2017-06-26 15:10:59',4,'1234567',1,2,'2017-06-27 10:20:07',1,'2017-07-03 09:43:58',1,'2017-07-03 21:32:20',1,'2017-07-03 23:23:14',1,'2017-07-03 23:23:25',NULL,NULL,3,'',5,NULL,'',2,3,6,3,9,'123456'),(3,'Giay adids','das','Hello 1 2  ','dfsfsf',2,2,4,'2017-07-03 23:23:25','2017-06-26 15:10:59',4,'LKJHG',1,1,'2017-06-27 11:21:30',1,'2017-07-03 09:43:58',1,'2017-07-03 21:32:20',1,'2017-07-03 22:23:43',1,'2017-07-03 23:23:25',NULL,NULL,3,'',5,NULL,'',2,3,6,2,6,'123456'),(4,'Giay lining','das','Tui la tui 123      ','dfsfsf',4.3,3,12.9,'2017-07-03 23:23:25','2017-06-26 15:10:59',4,'LKJHG',1,1,'2017-06-27 23:58:21',1,'2017-07-03 21:32:51',1,'2017-07-03 22:15:40',1,'2017-07-03 22:23:43',1,'2017-07-03 23:23:25',NULL,NULL,3,'Đơn hàng có lỗi',5,'','',2,3,6,2,6,'22222'),(6,'24','456','5656','56',5,5,25,'2017-06-28 11:14:02','2017-06-27 11:06:04',5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,-5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'121','2','2  ','2',3,3,9,'2017-07-04 10:55:01','2017-06-27 11:06:04',5,'12ffdf',NULL,1,'2017-06-29 22:22:05',1,'2017-07-04 10:51:40',1,'2017-07-04 10:53:03',1,'2017-07-04 10:55:01',NULL,NULL,NULL,NULL,3,NULL,4,'','',12,1,12,1,1,'TM02_0012'),(8,'Nuoc hoa Parfum Dior','Christian ','Loại 200ml ','https://google.com',20,10,200,'2017-07-03 21:32:20','2017-06-27 11:10:10',6,'VĐ12345',NULL,1,'2017-06-29 22:22:05',1,'2017-07-03 10:04:28',1,'2017-07-03 21:32:20',NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,3,'','',15,9,135,14,126,'654321'),(9,'Son Mac','MAC','Màu hồng nhạt','https://mac.com.vn',20,2,40,'2017-06-29 22:39:49','2017-06-27 11:10:10',6,NULL,NULL,1,'2017-06-29 22:39:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,-1,'134567890-=',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'Giay nike','das','TEST','dfsfsf',2,2,4,'2017-06-28 11:14:02','2017-06-27 11:28:06',7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,-5,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'Giay adids','das','Hello','dfsfsf',2,2,4,'2017-07-04 10:50:55','2017-06-27 11:28:06',7,NULL,NULL,1,'2017-07-04 10:50:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,1,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'Giay puma','das','Dog ','dfsfsf',2,2,4,'2017-07-03 22:14:26','2017-06-27 11:28:06',7,NULL,NULL,1,'2017-06-27 23:58:21',1,'2017-07-03 22:14:33',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,2,'','',2,3,6,1.5,4.5,'ABCDEF');
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_exchange`
--

DROP TABLE IF EXISTS `money_exchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `money_exchange` (
  `id` int(11) NOT NULL,
  `value` double NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_exchange`
--

LOCK TABLES `money_exchange` WRITE;
/*!40000 ALTER TABLE `money_exchange` DISABLE KEYS */;
INSERT INTO `money_exchange` VALUES (1,29225.0979583419,'2017-06-03 10:18:02','2017-05-01 17:19:17');
/*!40000 ALTER TABLE `money_exchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` char(2) CHARACTER SET latin1 NOT NULL,
  `name` varchar(45) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('A','Admin'),('B','mua hang'),('BG','Bao gia'),('C','Duyet hang'),('K','Kiem hang'),('T1','Duyet hang'),('T2','Duyet 2'),('U','User thuong');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `transfer_id`
--

DROP TABLE IF EXISTS `transfer_id`;
/*!50001 DROP VIEW IF EXISTS `transfer_id`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `transfer_id` AS SELECT 
 1 AS `transfer_id`,
 1 AS `STATUS`,
 1 AS `USER_ID`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `password` varchar(45) NOT NULL,
  `full_name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'a','admin','a','m',NULL),(2,'b','Nguoi duyet hang','b','f',NULL),(3,'c','Mit mit','c','m',NULL),(4,'b1','Nguoi mua hang','b1','M',NULL),(5,'a','Nguoi chuyen hang nuoc ngoai','t1','M',NULL),(6,'a','Nguoi nhan hang tai viet nam','t2','F',NULL),(7,'a','Nguoi kiem hang VN','k','M',NULL),(8,'a','Nguoi bao gia','bg','F',NULL),(9,'d','Bon Bon','bonbon@gmail.com','M','0123456789');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(45) NOT NULL,
  `role_id` char(2) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKUser_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'A'),(1,'V'),(2,'C'),(3,'U'),(4,'B'),(5,'T1'),(6,'T2'),(7,'K'),(8,'BG'),(9,'U');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `brand`
--

/*!50001 DROP VIEW IF EXISTS `brand`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `brand` AS select `items`.`brand` AS `BRAND`,`items`.`status` AS `STATUS`,`items`.`user_id` AS `USER_ID` from `items` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `buying_code`
--

/*!50001 DROP VIEW IF EXISTS `buying_code`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `buying_code` AS select `items`.`buying_code` AS `buying_code`,`items`.`status` AS `STATUS`,`items`.`user_id` AS `USER_ID` from `items` where (`items`.`status` > 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `transfer_id`
--

/*!50001 DROP VIEW IF EXISTS `transfer_id`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `transfer_id` AS select `items`.`transfer_id` AS `transfer_id`,`items`.`status` AS `STATUS`,`items`.`user_id` AS `USER_ID` from `items` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-04 10:57:43
