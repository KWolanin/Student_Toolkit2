-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: student
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `dayofweek` varchar(15) NOT NULL,
  `startHour` varchar(10) NOT NULL,
  `endHour` varchar(10) NOT NULL,
  `room` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES (1,'Programowanie','niedziela','02:30','03:30',11),(2,'Test','poniedziałek','02:30','03:30',1);
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `exams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `type` varchar(25) NOT NULL,
  `date` varchar(15) DEFAULT NULL,
  `hour` varchar(10) NOT NULL,
  `room` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (11,'55','egzamin','18.5.2019','5',5),(12,'te','egzamin','30.5.2019','5',5),(27,'Test','egzamin','18.06.2019','03:30',1),(29,'Systemy wbudowane','egzamin','28.06.2019','16:00',225);
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `ects` int(11) NOT NULL,
  `type` varchar(25) DEFAULT NULL,
  `grade` float(4,1) DEFAULT NULL,
  `examKind` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (1,'BHP',0,'zaliczenie bez oceny',NULL,'wyklad'),(2,'Jezyk angielski',2,'zaliczenie z ocena',3.5,'cwiczenia'),(3,'komunikacja personalna',1,'zaliczenie bez oceny',NULL,'cwiczenia'),(4,'algebra liniowa',4,'egzamin',3.5,'wyklad'),(5,'algebra liniowa',4,'zaliczenie z ocena',3.5,'cwiczenia'),(7,'analiza matematyczna',4,'zaliczenie z ocena',3.0,'cwiczenia'),(9,'algorytmy i struktury danych',7,'zaliczenie z ocena',3.0,'cwiczenia'),(11,'narzedzia informatyki',3,'zaliczenie bez oceny',NULL,'wyklad'),(12,'narzedzia informatyki',3,'zaliczenie z ocena',5.0,'laboratorium'),(13,'podstawy techniki cyfrowej',3,'zaliczenie bez oceny',NULL,'wyklad'),(15,'sieci komputerowe',4,'zaliczenie z ocena',5.0,'laboratorium'),(56,'Test',3,'egzamin',5.0,'wyklad'),(57,'Tess',4,'zaliczenie bez oceny',2.0,'wyklad'),(58,'tet',2,'zaliczenie bez oceny',0.0,'wyklad'),(59,'Test',5,'egzamin',5.0,'wyklad'),(60,'Test',5,'egzamin',5.0,'wyklad'),(63,'Test',5,'egzamin',4.5,'wyklad');
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `teacher` varchar(45) NOT NULL,
  `room` int(11) NOT NULL,
  `start_hour` varchar(10) NOT NULL,
  `end_hour` varchar(10) NOT NULL,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(30) NOT NULL,
  lastName varchar(40) NOT NULL,
  `title` varchar(15) DEFAULT NULL,
  `email` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Jan','Kowal','dr','jankowal@gmail.com'),(2,'Jan','Kowalski','dr','k.kowalski@gmail.com'),(4,'Michał','Wiśniewski','mgr','wisnia@gmail.com'),(5,'Mateusz','X','prof.','mati@wp.pl'),(6,'Anna','Apostolakis','dr','anna@wp.pl'),(10,'Test','Test','dr','test@gmail.com'),(12,'Mariusz','D','prof.','du@xd.pl'),(14,'Jan','Król','prof. dr. hab.','krol@gmail.com');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-09 21:02:39
