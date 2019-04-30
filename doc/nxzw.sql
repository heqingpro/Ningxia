/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.5.57 : Database - nxspider1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nxspider` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nxspider`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` varchar(32) NOT NULL,
  `content` longtext,
  `meetting` varchar(255) DEFAULT NULL,
  `release_time` datetime DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `subtitle` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `img_article` */

DROP TABLE IF EXISTS `img_article`;

CREATE TABLE `img_article` (
  `id` varchar(32) NOT NULL,
  `content` text,
  `img` varchar(255) DEFAULT NULL,
  `release_time` datetime DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `leader` */

DROP TABLE IF EXISTS `leader`;

CREATE TABLE `leader` (
  `id` varchar(32) NOT NULL,
  `belong` varchar(5) DEFAULT NULL,
  `duties` varchar(255) DEFAULT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  `job_detail` text,
  `name` varchar(255) DEFAULT NULL,
  `resume` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `letter` */

DROP TABLE IF EXISTS `letter`;

CREATE TABLE `letter` (
  `id` varchar(32) NOT NULL,
  `appeal` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `letter_content` text,
  `letter_title` varchar(255) DEFAULT NULL,
  `reply_content` text,
  `reply_dept` varchar(255) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `suggest` */

DROP TABLE IF EXISTS `suggest`;

CREATE TABLE `suggest` (
  `id` varchar(32) NOT NULL,
  `ask_category` varchar(255) DEFAULT NULL,
  `co_oganizer` varchar(255) DEFAULT NULL,
  `content` text,
  `deal` text,
  `num` varchar(255) DEFAULT NULL,
  `organizer` varchar(255) DEFAULT NULL,
  `period` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `topic_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
