/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.39 : Database - library_system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`library_system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `library_system`;

/*Table structure for table `book_info` */

DROP TABLE IF EXISTS `book_info`;

CREATE TABLE `book_info` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `isbn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `publication_date` datetime NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `stock_quantity` int DEFAULT NULL,
  `description` text,
  `cover_image_url` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `number_of_reviews` varchar(255) DEFAULT NULL,
  `shelf_location` varchar(255) DEFAULT NULL,
  `deleted` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `book_info` */

insert  into `book_info`(`id`,`title`,`author`,`isbn`,`publication_date`,`category`,`price`,`publisher`,`stock_quantity`,`description`,`cover_image_url`,`rating`,`number_of_reviews`,`shelf_location`,`deleted`,`status`,`create_user`,`create_time`,`update_user`,`update_time`) values ('1827002723473993730','admin','鲁迅','GB123654','2024-08-23 19:11:11','小说',36.55,'人民',11,'admin',NULL,NULL,NULL,'admin','0','0',NULL,NULL,NULL,NULL);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`user_name`,`password_hash`,`role`,`phone_number`,`email`,`gender`,`create_user`,`create_time`,`update_user`,`update_time`) values ('143425324','admin','$2a$10$SsFtXonVf1Uelux7bkEkVeQk1ov/S2SyDQ.cGJF5tBBRnXsr/vxe2','admin','1698887965','102587421@qq.com','男','143425324','2024-08-23 22:31:01','143425324','2024-08-23 22:31:09'),('1826999112429961218','user01','$2a$10$SsFtXonVf1Uelux7bkEkVeQk1ov/S2SyDQ.cGJF5tBBRnXsr/vxe2','customer','1698887965','102587421@qq.com','男','1826999112429961218','2024-08-23 23:06:41','1826999112429961218','2024-08-23 23:06:44');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
