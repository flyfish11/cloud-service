/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : cloud_file

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-09 17:23:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(32) NOT NULL COMMENT '文件md5',
  `name` varchar(128) NOT NULL,
  `isImg` tinyint(1) NOT NULL,
  `contentType` varchar(128) NOT NULL,
  `size` int(11) NOT NULL,
  `path` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) NOT NULL,
  `source` varchar(32) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `createTime` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
