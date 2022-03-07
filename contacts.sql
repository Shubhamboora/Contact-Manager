/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 8.0.27 : Database - contacts
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`contacts` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `contacts`;

/*Table structure for table `contacts` */

DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `c_id` int NOT NULL,
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `second_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `work` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`c_id`),
  KEY `FKf9pc3faerry2wp3xnahv2b0rg` (`user_id`),
  FULLTEXT KEY `UK_728mksvqr0n907kujew6p3jc0` (`email`),
  CONSTRAINT `FKf9pc3faerry2wp3xnahv2b0rg` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_ci;

/*Data for the table `contacts` */

insert  into `contacts`(`c_id`,`description`,`email`,`image`,`name`,`phone`,`second_name`,`work`,`user_id`) values 
(10,'<p>another discription</p>','Karanchauhan@gmail.com','download.jpg','Karan','1211','chauhan','do something',7),
(13,'<p>ase</p>','vikramK@gmail.com','K.jpg','vikram','24546666','aditya','Accenture',7),
(14,'<p>Vandy do not work</p>','vandanaTiwari@gmail.com','images.jpg','vandana ','2343252','Tiwari','none',7),
(15,'Developer','RahulSinha@hotmail.com','contact.png','Rahul Sinha','70701234432','Tina','TCS',7),
(24,'<p>ADA</p>','Shubhamboora10@gmail.com','contact.png','Shubham','09416190860','Boora','Accenture',21),
(25,'<p>He is a good boxer</p>','VijendarSingh@gmail.com','Vijender_Singh.jpg','Vijendar','9285536122','Singh','Boxing',7),
(26,'<p>ADA</p>','Shubhamboora10@gmail.com','contact.png','Shubham','09416190860','Boora','Accenture',7);

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values 
(27);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL,
  `about` varchar(500) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`about`,`email`,`enabled`,`image_url`,`name`,`password`,`role`) values 
(7,'wwww','Vijayteja@gmail.com','','User_default.png','Vijay','$2a$10$70nkCWTxlZe9ImayOiC.yuMgkWjY90.tHBDynNSJv/zQVgCC/9.Uy','ROLE_USER'),
(21,'tina','RahulSinha@hotmail.com','','User_default.png','Rahul Sinha','$2a$10$4iFAPhjKnbapriOvmUAOBeDlJ96oz9JrLahFxo8igRs8oq8K4D.FK','ROLE_USER');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
