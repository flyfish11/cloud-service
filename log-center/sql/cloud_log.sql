/*
Navicat MySQL Data Transfer

Source Server         : zhangw
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : cloud_log

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-02-13 10:02:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `module` varchar(100) NOT NULL COMMENT '模块名',
  `params` text COMMENT '方法参数',
  `remark` text COMMENT '备注',
  `flag` tinyint(1) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`),
  KEY `createTime` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
