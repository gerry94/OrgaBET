CREATE DATABASE  IF NOT EXISTS `lsdb` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lsdb`;
-- MySQL dump 10.13  Distrib 8.0.17, for Linux (x86_64)
--
-- Host: localhost    Database: lsdb
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Book` (
  `idBook` varchar(45) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Author` varchar(45) NOT NULL,
  `Available` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`idBook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book`
--

LOCK TABLES `Book` WRITE;
/*!40000 ALTER TABLE `Book` DISABLE KEYS */;
INSERT INTO `Book` VALUES ('111a','The Lord of The Rings','J.R.R. Tolkien',1),('111b','Manuale del Trapano: Guida per Principianti','Mario Dilaria',1),('111c','1984','George Orwell',1),('111d','Moby Dick','Herman Melville',1),('111e','Don Chisciotte della Mancia','Miguel de Cervantes',1),('111f','Tennis per Dilettanti','Roger Orga',1),('111g','Il Barone Rampante','Italo Calvino',1),('111h','Il Decamerone','Giovanni Boccaccio',1),('111i','Odissea','Omero',0),('111j','Come Scrivere la Tesi','Joseph Pimpinella',0),('111k','Amleto','William Shakespeare',0),('111l','Dalle Porte AND, OR, NOT al Sistema Calcolatore','Paolo Corsini',1),('222a','Leone d\'Argento: Biografia di Mimmo Croce','Alessio Bongiovanni',1);
/*!40000 ALTER TABLE `Book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Loan`
--

DROP TABLE IF EXISTS `Loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Loan` (
  `idUser` varchar(45) NOT NULL,
  `idBook` varchar(45) NOT NULL,
  `startDate` date NOT NULL,
  `deliveryDate` date NOT NULL,
  PRIMARY KEY (`idUser`,`idBook`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Loan`
--

LOCK TABLES `Loan` WRITE;
/*!40000 ALTER TABLE `Loan` DISABLE KEYS */;
INSERT INTO `Loan` VALUES ('bbb1','111i','2019-10-05','2019-11-05'),('ggg1','111j','2019-10-06','2019-11-06'),('lll1','111k','2019-10-07','2019-11-07');
/*!40000 ALTER TABLE `Loan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `idUser` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Surname` varchar(45) NOT NULL,
  `Privilege` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('aaa1','Gerardo','Bongiovanni',0),('abcd1','Daniele','Matonti',0),('bbb1','Riccardo','Alvaro',0),('ccc1','Marco','Silvestri',1),('ddd1','Giulio','Polini',0),('eee1','Francesco','Barbarulo',1),('fff1','Daniela','Comola',1),('ggg1','Giulia','Amerighi',0),('hhh1','Mario','Dilaria',0),('jjj1','Giuseppe','Pimpinella',0),('kkk1','Ciccio','Fornaini',0),('lll1','Daniele','Orgallo',0),('xxx1','Giuseppe','Macchi',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-10-10 17:12:19
