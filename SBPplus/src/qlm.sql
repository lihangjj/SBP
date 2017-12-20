-- 删除arm数据库
DROP DATABASE IF EXISTS qlm ;

-- 创建arm数据库
CREATE DATABASE qlm CHARACTER SET UTF8 ;

-- 使用arm数据库
USE qlm ;

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `clientid` int(11) NOT NULL AUTO_INCREMENT,
  `claim` text,
  `phone` varchar(15) DEFAULT NULL,
  `budget` varchar(10) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `pubdate` datetime DEFAULT NULL,
  PRIMARY KEY (`clientid`)
) ENGINE=InnoDB;
