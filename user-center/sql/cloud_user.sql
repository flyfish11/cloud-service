/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : cloud_user

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-06-30 18:35:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` varchar(50) NOT NULL,
  `userName` varchar(50)  NOT NULL,
  `password` varchar(60) NOT NULL,
  `fullName` varchar(255)  DEFAULT NULL,
  `headImgUrl` varchar(1024) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `idNumber` varchar(20) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `isDelete` tinyint(1) DEFAULT NULL,
  `type` varchar(16) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  `organizeId` varchar(50) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  `createBy` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO `app_user` VALUES ('1', 'admin', '$2a$10$3uOoX1ps14CxuotogUoDreW8zXJOZB9XeGdrC/xDV36hhaE8Rn9HO', '测试1', '', '', null, null, '1', '1', null, 'APP', '2018-01-17 16:56:59', '2018-01-17 16:57:01', null, null, null, null);
INSERT INTO `app_user` VALUES ('2', 'superadmin', '$2a$10$.gLkG0j2kM0stWoOvPBvqu0H9uSD0HUlpErI.PTKyZQkSUZIV2wFq', '超级管理员', null, null, null, null, '1', '1', null, 'BACKEND', '2018-01-19 20:30:09', '2018-01-19 20:30:11', null, null, null, null);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(50) NOT NULL,
  `permission` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'back:permission:save', '保存权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('10', 'back:user:update', '修改用户', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('11', 'back:user:role:set', '给用户分配角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('12', 'back:user:password', '用户重置密码', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('13', 'back:menu:save', '添加菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('14', 'back:menu:update', '修改菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('15', 'back:menu:delete', '删除菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('16', 'back:menu:query', '查询菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('17', 'back:menu:set2role', '给角色分配菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('18', 'back:role:query', '查询角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('19', 'user:role:byuid', '获取用户的角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('2', 'back:permission:update', '修改权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('20', 'role:permission:byroleid', '获取角色的权限', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('21', 'menu:byroleid', '获取角色的菜单', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('22', 'ip:black:query', '查询黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('23', 'ip:black:save', '添加黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('24', 'ip:black:delete', '删除黑名单ip', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('25', 'log:query', '日志查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('26', 'file:query', '文件查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('27', 'file:del', '文件删除', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('28', 'mail:save', '保存邮件', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('29', 'mail:update', '修改邮件', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('3', 'back:permission:delete', '删除权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('30', 'mail:query', '邮件查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('31', 'sms:query', '短信发送记录查询', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('32', 'back:organize:query', '组织信息查询', '2018-06-25 14:02:21', '2018-06-25 14:02:30');
INSERT INTO `sys_permission` VALUES ('4', 'back:permission:query', '查询权限标识', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('5', 'back:role:save', '添加角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('6', 'back:role:update', '修改角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('7', 'back:role:delete', '删除角色', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('8', 'back:role:permission:set', '给角色分配权限', '2018-01-18 17:06:39', '2018-01-18 17:06:42');
INSERT INTO `sys_permission` VALUES ('9', 'back:user:query', '用户查询', '2018-01-18 17:12:00', '2018-01-18 17:12:05');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(50) NOT NULL,
  `code` varchar(32) NOT NULL COMMENT '角色code',
  `name` varchar(50) NOT NULL COMMENT '角色名',
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'SUPER_ADMIN', '超级管理员', '2018-01-19 20:32:16', '2018-01-19 20:32:18');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `roleId` varchar(50) NOT NULL,
  `permissionId` varchar(50) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '10');
INSERT INTO `sys_role_permission` VALUES ('1', '11');
INSERT INTO `sys_role_permission` VALUES ('1', '12');
INSERT INTO `sys_role_permission` VALUES ('1', '13');
INSERT INTO `sys_role_permission` VALUES ('1', '14');
INSERT INTO `sys_role_permission` VALUES ('1', '15');
INSERT INTO `sys_role_permission` VALUES ('1', '16');
INSERT INTO `sys_role_permission` VALUES ('1', '17');
INSERT INTO `sys_role_permission` VALUES ('1', '18');
INSERT INTO `sys_role_permission` VALUES ('1', '19');
INSERT INTO `sys_role_permission` VALUES ('1', '2');
INSERT INTO `sys_role_permission` VALUES ('1', '20');
INSERT INTO `sys_role_permission` VALUES ('1', '21');
INSERT INTO `sys_role_permission` VALUES ('1', '22');
INSERT INTO `sys_role_permission` VALUES ('1', '23');
INSERT INTO `sys_role_permission` VALUES ('1', '24');
INSERT INTO `sys_role_permission` VALUES ('1', '25');
INSERT INTO `sys_role_permission` VALUES ('1', '26');
INSERT INTO `sys_role_permission` VALUES ('1', '27');
INSERT INTO `sys_role_permission` VALUES ('1', '28');
INSERT INTO `sys_role_permission` VALUES ('1', '29');
INSERT INTO `sys_role_permission` VALUES ('1', '3');
INSERT INTO `sys_role_permission` VALUES ('1', '30');
INSERT INTO `sys_role_permission` VALUES ('1', '31');
INSERT INTO `sys_role_permission` VALUES ('1', '32');
INSERT INTO `sys_role_permission` VALUES ('1', '4');
INSERT INTO `sys_role_permission` VALUES ('1', '5');
INSERT INTO `sys_role_permission` VALUES ('1', '6');
INSERT INTO `sys_role_permission` VALUES ('1', '7');
INSERT INTO `sys_role_permission` VALUES ('1', '8');
INSERT INTO `sys_role_permission` VALUES ('1', '9');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `userId` varchar(50) NOT NULL,
  `roleId` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1');
INSERT INTO `sys_role_user` VALUES ('2', '1');

-- ----------------------------
-- Table structure for t_wechat
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat`;
CREATE TABLE `t_wechat` (
  `id` varchar(50) NOT NULL,
  `openid` varchar(128) NOT NULL,
  `unionid` varchar(128) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL COMMENT '绑定用户的id',
  `app` varchar(32) NOT NULL COMMENT '公众号标识',
  `nickname` varchar(64) NOT NULL COMMENT '微信昵称',
  `sex` varchar(16) DEFAULT NULL,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `country` varchar(64) DEFAULT NULL,
  `headimgurl` varchar(1024) DEFAULT NULL,
  `createTime` datetime NOT NULL,
  `updateTime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`openid`),
  KEY `userId` (`userId`),
  KEY `unionid` (`unionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='微信信息';

-- ----------------------------
-- Records of t_wechat
-- ----------------------------

-- ----------------------------
-- Table structure for user_credentials
-- ----------------------------
DROP TABLE IF EXISTS `user_credentials`;
CREATE TABLE `user_credentials` (
  `username` varchar(50) NOT NULL COMMENT '用户名或手机号等',
  `type` varchar(16) NOT NULL COMMENT '账号类型（用户名、手机号）',
  `userId` varchar(50) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户凭证表';

-- ----------------------------
-- Records of user_credentials
-- ----------------------------
INSERT INTO `user_credentials` VALUES ('admin', 'USERNAME', '1');
INSERT INTO `user_credentials` VALUES ('superadmin', 'USERNAME', '2');



insert into app_user
(id,userName,password,fullName,headImgUrl,phone,email,idNumber,sex,enabled,isDelete, type, createTime, updateTime, organizeId, remark, version, createBy)
values
(#{id},#{username},#{password},#{fullName},#{headImgUrl},#{phone},#{email},#{idNumber},#{sex},#{enabled},#{isDelete},#{type},#{createTime},#{updateTime},#{organizeId},#{remark},#{version},#{createBy})