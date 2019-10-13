CREATE DATABASE  IF NOT EXISTS `lsdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `lsdb`;
-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: lsdb
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.10.1

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `idBook` varchar(45) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Author` varchar(45) NOT NULL,
  `numCopies` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idBook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('111a','The Lord of The Rings','J.R.R. Tolkien',3),('111b','Manuale del Trapano: Guida per Principianti','Mario Dilaria',5),('111c','1984','George Orwell',1),('111d','Moby Dick','Herman Melville',3),('111e','Don Chisciotte della Mancia','Miguel de Cervantes',2),('111f','Tennis per Dilettanti','Roger Orga',1),('111g','Il Barone Rampante','Italo Calvino',2),('111h','Il Decamerone','Giovanni Boccaccio',1),('111i','Odissea','Omero',1),('111j','Come Scrivere la Tesi','Joseph Pimpinella',1),('111k','Amleto','William Shakespeare',2),('111l','Dalle Porte AND, OR, NOT al Sistema Calcolatore','Paolo Corsini',1),('111z','Manuale di DataBase NoSQL','Pietro Ducange',2),('222a','Leone d\'Argento: Biografia di Mimmo Croce','Alessio Bongiovanni',2);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `loan` (
  `idUser` varchar(45) NOT NULL,
  `idBook` varchar(45) NOT NULL,
  `startDate` date NOT NULL,
  `deliveryDate` date NOT NULL,
  PRIMARY KEY (`idUser`,`idBook`),
  KEY `fk_loan_2_idx` (`idBook`),
  CONSTRAINT `fk_loan_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION,
  CONSTRAINT `fk_loan_2` FOREIGN KEY (`idBook`) REFERENCES `book` (`idBook`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan`
--

LOCK TABLES `loan` WRITE;
/*!40000 ALTER TABLE `loan` DISABLE KEYS */;
INSERT INTO `loan` VALUES ('bbb1','111i','2019-10-05','2019-11-05'),('ggg1','111j','2019-10-06','2019-11-06'),('lll1','111k','2019-10-07','2019-11-07');
/*!40000 ALTER TABLE `loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Surname` varchar(45) NOT NULL,
  `Privilege` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('aaa1','Gerardo','Bongiovanni',0),('abcd1','Daniele','Matonti',0),('bbb1','Riccardo','Alvaro',0),('ccc1','Marco','Silvestri',1),('ddd1','Giulio','Polini',0),('eee1','Francesco','Barbarulo',1),('fff1','Daniela','Comola',1),('ggg1','Giulia','Amerighi',0),('hhh1','Mario','Dilaria',0),('jjj1','Giuseppe','Pimpinella',0),('kkk1','Ciccio','Fornaini',0),('lll1','Daniele','Orgallo',0),('xxx1','Giuseppe','Macchi',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-13 16:28:56
