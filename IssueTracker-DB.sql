CREATE DATABASE  IF NOT EXISTS `issuetrackersystem` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `issuetrackersystem`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: issuetrackersystem
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `title` varchar(256) DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `date_time_created` datetime DEFAULT NULL,
  `date_time_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Project_User1_idx` (`user_id`),
  CONSTRAINT `fk_Project_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,3,'Core','Core Descrption',128,'2015-06-06 14:34:43','2015-06-06 14:34:45'),(2,3,'Client','Client Description',128,'2015-06-06 14:34:43','2015-06-06 14:34:45'),(3,3,'Server','Server Description',128,'2015-06-06 14:34:44','2015-06-06 14:34:46'),(4,3,'DB','Database Description',128,'2015-06-06 14:34:44','2015-06-06 14:34:44');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_follower`
--

DROP TABLE IF EXISTS `project_follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_follower` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ProjectFollower_Project_idx` (`project_id`),
  KEY `fk_ProjectFollower_User1_idx` (`user_id`),
  CONSTRAINT `fk_ProjectFollower_Project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProjectFollower_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_follower`
--

LOCK TABLES `project_follower` WRITE;
/*!40000 ALTER TABLE `project_follower` DISABLE KEYS */;
INSERT INTO `project_follower` VALUES (1,1,3),(2,2,3),(3,3,3),(4,4,3),(26,1,1),(27,4,1);
/*!40000 ALTER TABLE `project_follower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `ticket_attachment_id` int(10) unsigned DEFAULT NULL,
  `title` varchar(256) DEFAULT NULL,
  `description` text,
  `status` varchar(128) DEFAULT NULL,
  `priority` varchar(128) DEFAULT NULL,
  `categories` varchar(1024) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `date_time_created` datetime DEFAULT NULL,
  `date_time_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Ticket_User1_idx` (`user_id`),
  KEY `fk_Ticket_Project1_idx` (`project_id`),
  KEY `fk_Ticket_TicketAttachment1_idx` (`ticket_attachment_id`),
  CONSTRAINT `fk_Ticket_Project1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ticket_TicketAttachment1` FOREIGN KEY (`ticket_attachment_id`) REFERENCES `ticket_attachment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ticket_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,6,3,NULL,'Can\'t compile with GCC 5.1','<h4>Steps to reproduce the issue</h4>  <p>Create a contact, public &amp; published, save, memorise id (1).<br> Create another contact, public &amp; unpublished, save, memorise id (2).<br> Create another contact, registered &amp; published, save, memorise id (3).<br> Create a menu item \"list contacts in category\", call it \"contacts\".<br> Navigate to <a href=\"http://mysite.com/contacts\">http://mysite.com/contacts</a> to see the list of contacts as public, you\'ll see just one contact (1).</p>  <p>Navigate to this contact (<a href=\"http://mysite.com/contacts/1-somename\">http://mysite.com/contacts/1-somename</a>), all good.</p>  <p>Navigate to unpublished contact (<a href=\"http://mysite.com/contacts/2-somename\">http://mysite.com/contacts/2-somename</a>), see 404 \"Contact not found\" error page. All good.</p>  <p>Navigate to registered contact (<a href=\"http://mysite.com/contacts/3-somename\">http://mysite.com/contacts/3-somename</a>), see long error message with stack trace.</p>  <h4>Expected result</h4>  <p>404 or 403 Error page (error template based) saying \"contact not found\" or \"not authorised\"</p>','Solved','Low','Server',28,'2015-06-06 14:03:51','2015-06-14 14:17:48'),(2,6,1,NULL,'6.x - access_requirement','Hi! ','Open','Low','Core',44,'2015-06-06 14:03:51','2015-06-06 14:03:51'),(3,6,2,NULL,'[Crash] Player::_SaveSpells','Worldserver crashed with the following crashlog:','Closed','Medium','Client, Medium',34,'2015-06-06 14:03:51','2015-06-06 14:03:51'),(4,4,4,NULL,'[DB/Creature] Skorn - Emotes','rev ed2607f','Open','Medium','DB, Server',13,'2015-06-06 14:03:51','2015-06-06 14:03:51'),(5,4,4,NULL,'[DB/SAI] Creature walks Path after Death','<h4>Steps to reproduce the issue</h4>  <p>Create a contact, public &amp; published, save, memorise id (1).<br> Create another contact, public &amp; unpublished, save, memorise id (2).<br> Create another contact, registered &amp; published, save, memorise id (3).<br> Create a menu item \"list contacts in category\", call it \"contacts\".<br> Navigate to <a href=\"http://mysite.com/contacts\">http://mysite.com/contacts</a> to see the list of contacts as public, you\'ll see just one contact (1).</p>  <p>Navigate to this contact (<a href=\"http://mysite.com/contacts/1-somename\">http://mysite.com/contacts/1-somename</a>), all good.</p>  <p>Navigate to unpublished contact (<a href=\"http://mysite.com/contacts/2-somename\">http://mysite.com/contacts/2-somename</a>), see 404 \"Contact not found\" error page. All good.</p>  <p>Navigate to registered contact (<a href=\"http://mysite.com/contacts/3-somename\">http://mysite.com/contacts/3-somename</a>), see long error message with stack trace.</p>  <h4>Expected result</h4>  <p>404 or 403 Error page (error template based) saying \"contact not found\" or \"not authorised\"</p>','Open','Medium','DB, Server',64,'2015-06-06 14:03:51','2015-06-06 14:03:51');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_attachment`
--

DROP TABLE IF EXISTS `ticket_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_attachment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `file_path` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_TicketAttachment_Ticket1_idx` (`ticket_id`),
  KEY `fk_TicketAttachment_User1_idx` (`user_id`),
  CONSTRAINT `fk_TicketAttachment_Ticket1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TicketAttachment_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_attachment`
--

LOCK TABLES `ticket_attachment` WRITE;
/*!40000 ALTER TABLE `ticket_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_comment`
--

DROP TABLE IF EXISTS `ticket_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `text` varchar(2048) DEFAULT NULL,
  `date_time_created` datetime DEFAULT NULL,
  `date_time_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_TicketComment_Ticket1_idx` (`ticket_id`),
  KEY `fk_TicketComment_User1_idx` (`user_id`),
  CONSTRAINT `fk_TicketComment_Ticket1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TicketComment_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_comment`
--

LOCK TABLES `ticket_comment` WRITE;
/*!40000 ALTER TABLE `ticket_comment` DISABLE KEYS */;
INSERT INTO `ticket_comment` VALUES (1,1,4,'try D_GLIBCXX_USE_CXX11_ABI=1 to set','2015-06-06 14:03:51','2015-06-06 14:03:51'),(2,1,6,'ty VM but I still have the same error...','2015-06-06 14:03:51','2015-06-06 14:03:51'),(3,2,4,'wrong difficulty?','2015-06-06 14:03:51','2015-06-06 14:03:51'),(4,2,6,'For example Bloodmaul Slag Mines HC:','2015-06-06 14:03:51','2015-06-06 14:03:51'),(5,3,4,'Its CFBG issue. Custom patch crash.','2015-06-06 14:03:51','2015-06-06 14:03:51'),(7,5,4,'try D_GLIBCXX_USE_CXX11_ABI=1 to set','2015-06-14 04:25:45','2015-06-14 04:25:45'),(8,5,6,'that works','2015-06-14 04:25:45','2015-06-14 04:25:45');
/*!40000 ALTER TABLE `ticket_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_follower`
--

DROP TABLE IF EXISTS `ticket_follower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket_follower` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticket_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_TicketFollower_Ticket1_idx` (`ticket_id`),
  KEY `fk_TicketFollower_User1_idx` (`user_id`),
  CONSTRAINT `fk_TicketFollower_Ticket1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TicketFollower_User1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_follower`
--

LOCK TABLES `ticket_follower` WRITE;
/*!40000 ALTER TABLE `ticket_follower` DISABLE KEYS */;
INSERT INTO `ticket_follower` VALUES (1,1,6),(2,2,6),(3,3,6),(4,4,4),(5,5,4),(10,1,1);
/*!40000 ALTER TABLE `ticket_follower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `first_name` varchar(32) DEFAULT NULL,
  `last_name` varchar(32) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `role` varchar(64) DEFAULT NULL,
  `avatar_path` varchar(512) DEFAULT NULL,
  `date_time_registered` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','123','Admin','Adminic','admin@issuetracker.com','Admin','user-avatar.png','2015-06-06 14:06:22'),(2,'moderator','123','Moderator','Moderatoric','moderator@issuetracker.com','Moderator','user-avatar.png','2015-06-06 14:08:38'),(3,'softwarearchitect','123','Softver','Arhitektic','softwarearchitect@issuetracker.com','Architect','user-avatar.png','2015-06-06 14:11:18'),(4,'softwaredeveloper','123','Softver','Developeric','softwaredeveloper@issuetracker.com','Developer','user-avatar.png','2015-06-06 14:11:19'),(5,'softwaretester','123','Softver','Testeric','softwaretester@issuetracker.com','Tester','user-avatar.png','2015-06-06 14:11:20'),(6,'member','123','Member','Memberic','member@issuetracker.com','Member','user-avatar.png','2015-06-06 14:11:20'),(9,'rile','123','Stefan','Ristic','stefan.ristic.1604@metropolitan.ac.rs','Member','user-avatar.png','2015-06-13 00:16:59');
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

-- Dump completed on 2015-06-14 23:02:01
