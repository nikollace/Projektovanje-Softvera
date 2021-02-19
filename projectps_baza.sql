/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 8.0.21 : Database - projectps
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`projectps` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `projectps`;

/*Table structure for table `pacijent` */

DROP TABLE IF EXISTS `pacijent`;

CREATE TABLE `pacijent` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(30) DEFAULT NULL,
  `prezime` varchar(30) DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `adresa` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `datumrodjenja` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pacijent` */

insert  into `pacijent`(`id`,`ime`,`prezime`,`email`,`adresa`,`datumrodjenja`) values 
(15,'Aleksandar','Milosavljevic','aleks@gmail.com','Boska Toskovica 62','1993-06-23'),
(16,'Ivan','Jacovic','ivan@hotmail.com','Bosanska 789','1972-02-10'),
(17,'Mirko','Savic','mirko@yahoo.com','Ustanicka 25','1999-07-03');

/*Table structure for table `pregled` */

DROP TABLE IF EXISTS `pregled`;

CREATE TABLE `pregled` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ukupnaCena` double DEFAULT '0',
  `ukupnoTrajanje` double DEFAULT '0',
  `stomatolog` bigint NOT NULL,
  `pacijent` bigint NOT NULL,
  `terminDatum` date NOT NULL,
  `terminVremeOd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pregled_ibfk_1` (`stomatolog`),
  KEY `pregled_ibfk_2` (`pacijent`),
  KEY `pregled_ibfk_3` (`terminDatum`,`terminVremeOd`),
  CONSTRAINT `pregled_ibfk_1` FOREIGN KEY (`stomatolog`) REFERENCES `stomatolog` (`id`) ON UPDATE RESTRICT,
  CONSTRAINT `pregled_ibfk_2` FOREIGN KEY (`pacijent`) REFERENCES `pacijent` (`id`) ON UPDATE RESTRICT,
  CONSTRAINT `pregled_ibfk_3` FOREIGN KEY (`terminDatum`, `terminVremeOd`) REFERENCES `termin` (`datum`, `vremeOd`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pregled` */

insert  into `pregled`(`id`,`ukupnaCena`,`ukupnoTrajanje`,`stomatolog`,`pacijent`,`terminDatum`,`terminVremeOd`) values 
(29,2200,27,16,15,'2021-02-20','15:15'),
(30,2410,34,16,17,'2021-02-20','13:14');

/*Table structure for table `stavkapregleda` */

DROP TABLE IF EXISTS `stavkapregleda`;

CREATE TABLE `stavkapregleda` (
  `pregledId` bigint NOT NULL AUTO_INCREMENT,
  `rbr` int NOT NULL,
  `nazivpregleda` varchar(100) DEFAULT NULL,
  `cena` double DEFAULT '0',
  `trajanje` double DEFAULT '0',
  `opis` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`rbr`,`pregledId`),
  KEY `pregledId` (`pregledId`),
  CONSTRAINT `stavkapregleda_ibfk_1` FOREIGN KEY (`pregledId`) REFERENCES `pregled` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stavkapregleda` */

insert  into `stavkapregleda`(`pregledId`,`rbr`,`nazivpregleda`,`cena`,`trajanje`,`opis`) values 
(29,1,'Pranje zuba',1000,17,'Oprani zubi'),
(30,1,'Popravka zubica',10,14,'Radio popravku'),
(29,2,'Ciscenje karijesa',1200,10,'Ociscen'),
(30,2,'Ciscenje karijesa',1200,10,'Radi'),
(30,3,'Vadjenje zuba',1200,10,'Radio');

/*Table structure for table `stomatolog` */

DROP TABLE IF EXISTS `stomatolog`;

CREATE TABLE `stomatolog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ime` varchar(30) DEFAULT NULL,
  `prezime` varchar(30) DEFAULT NULL,
  `email` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(120) DEFAULT NULL,
  `datumrodjenja` date DEFAULT NULL,
  `adresa` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `stomatolog` */

insert  into `stomatolog`(`id`,`ime`,`prezime`,`email`,`password`,`datumrodjenja`,`adresa`) values 
(16,'Nikola','Djordjevic','nikola@gmail.com','admin','1998-12-15','Boska Toskovica 52'),
(18,'Mirko','Zecic','mire@gmail.com','admin123','2004-04-18','Uzicka 126');

/*Table structure for table `termin` */

DROP TABLE IF EXISTS `termin`;

CREATE TABLE `termin` (
  `datum` date NOT NULL,
  `vremeOd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `vremeDo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`datum`,`vremeOd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `termin` */

insert  into `termin`(`datum`,`vremeOd`,`vremeDo`) values 
('2021-02-12','15:10','16:10'),
('2021-02-13','18:40','19:40'),
('2021-02-20','13:14','14:14'),
('2021-02-20','15:15','17:15'),
('2021-02-20','17:18','19:18'),
('2021-02-22','13:19','14:15'),
('2021-02-23','19:40','20:40'),
('2021-02-24','14:40','20:40'),
('2021-02-26','12:12','14:12'),
('2021-02-27','17:12','17:18');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
