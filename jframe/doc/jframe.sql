/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : jframe

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 19/12/2017 14:29:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(20) NOT NULL COMMENT '用户组id',
  `admin_name` varchar(32) NOT NULL COMMENT '用户名',
  `admin_realname` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `admin_password` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `admin_phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `admin_create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建日期',
  `admin_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `admin_login_ip` varchar(64) DEFAULT NULL COMMENT '上次登录ip',
  `admin_flag` tinyint(2) DEFAULT '1' COMMENT '是否可编辑 1-可编辑 0-不可编辑',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除 1-是 0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_admin_username` (`admin_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8 COMMENT='后台管理员';

-- ----------------------------
-- Records of s_admin
-- ----------------------------
BEGIN;
INSERT INTO `s_admin` VALUES (10000, 10000, 'admin', '超管', 'c4ca4238a0b923820dcc509a6f75849b', '17730215422', '2016-04-23 15:36:55', '2017-12-19 14:26:13', '127.0.0.1', 0, b'0');
INSERT INTO `s_admin` VALUES (10001, 10001, 'huang', '老黄', 'c4ca4238a0b923820dcc509a6f75849b', '17732125421', '2016-05-13 10:58:17', '2016-05-21 09:50:49', NULL, 1, b'0');
INSERT INTO `s_admin` VALUES (10002, 10002, 'zhang', '老张', 'c4ca4238a0b923820dcc509a6f75849b', '17730215512', '2016-05-13 11:37:13', NULL, NULL, 1, b'0');
COMMIT;

-- ----------------------------
-- Table structure for s_log
-- ----------------------------
DROP TABLE IF EXISTS `s_log`;
CREATE TABLE `s_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_user` varchar(64) DEFAULT NULL COMMENT '操作人',
  `log_remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `log_ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `log_params` varchar(255) DEFAULT NULL COMMENT '参数',
  `log_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=531 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of s_log
-- ----------------------------
BEGIN;
INSERT INTO `s_log` VALUES (485, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 14:10:43');
INSERT INTO `s_log` VALUES (486, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 14:40:44');
INSERT INTO `s_log` VALUES (487, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 15:19:13');
INSERT INTO `s_log` VALUES (488, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 15:38:31');
INSERT INTO `s_log` VALUES (489, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 16:31:59');
INSERT INTO `s_log` VALUES (490, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 16:45:05');
INSERT INTO `s_log` VALUES (491, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 16:48:35');
INSERT INTO `s_log` VALUES (492, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-09 17:24:33');
INSERT INTO `s_log` VALUES (493, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-10 09:11:13');
INSERT INTO `s_log` VALUES (494, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-10 09:15:34');
INSERT INTO `s_log` VALUES (495, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-11 09:24:54');
INSERT INTO `s_log` VALUES (496, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-11 15:50:08');
INSERT INTO `s_log` VALUES (497, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-23 09:30:50');
INSERT INTO `s_log` VALUES (498, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-24 10:25:57');
INSERT INTO `s_log` VALUES (499, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-05-25 13:54:16');
INSERT INTO `s_log` VALUES (500, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-06-07 16:12:35');
INSERT INTO `s_log` VALUES (501, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-06-24 17:09:59');
INSERT INTO `s_log` VALUES (502, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-07-28 18:08:57');
INSERT INTO `s_log` VALUES (503, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-08-20 15:53:14');
INSERT INTO `s_log` VALUES (504, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-08-20 16:04:02');
INSERT INTO `s_log` VALUES (505, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10012', '2017-08-20 16:36:02');
INSERT INTO `s_log` VALUES (506, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10010', '2017-08-20 16:39:08');
INSERT INTO `s_log` VALUES (507, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10008', '2017-08-20 16:39:23');
INSERT INTO `s_log` VALUES (508, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10000', '2017-08-20 16:39:35');
INSERT INTO `s_log` VALUES (509, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-08-20 16:53:33');
INSERT INTO `s_log` VALUES (510, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10011', '2017-08-20 16:54:45');
INSERT INTO `s_log` VALUES (511, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10009', '2017-08-20 16:54:58');
INSERT INTO `s_log` VALUES (512, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10007', '2017-08-20 16:55:41');
INSERT INTO `s_log` VALUES (513, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10012', '2017-08-20 16:55:57');
INSERT INTO `s_log` VALUES (514, 'admin', '禁用/启用用户', '127.0.0.1', 'id=10011', '2017-08-20 16:56:05');
INSERT INTO `s_log` VALUES (515, 'admin', '禁用/启用管理员', '127.0.0.1', 'id=10001', '2017-08-20 16:58:37');
INSERT INTO `s_log` VALUES (516, 'admin', '禁用/启用管理员', '127.0.0.1', 'id=10001', '2017-08-20 16:59:58');
INSERT INTO `s_log` VALUES (517, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-09-11 11:21:45');
INSERT INTO `s_log` VALUES (518, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-09-11 11:37:38');
INSERT INTO `s_log` VALUES (519, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-09-21 09:54:35');
INSERT INTO `s_log` VALUES (520, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-11 14:38:03');
INSERT INTO `s_log` VALUES (521, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-11 14:55:03');
INSERT INTO `s_log` VALUES (522, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-11 14:57:13');
INSERT INTO `s_log` VALUES (523, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-18 14:26:43');
INSERT INTO `s_log` VALUES (524, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-18 14:45:15');
INSERT INTO `s_log` VALUES (525, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-18 14:53:58');
INSERT INTO `s_log` VALUES (526, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-18 15:12:28');
INSERT INTO `s_log` VALUES (527, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-19 19:51:31');
INSERT INTO `s_log` VALUES (528, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-19 14:20:56');
INSERT INTO `s_log` VALUES (529, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2017-12-19 14:26:13');
INSERT INTO `s_log` VALUES (530, 'admin', '执行查询SQL', '127.0.0.1', 'sql=select * from t_user;', '2017-12-19 14:26:25');
COMMIT;

-- ----------------------------
-- Table structure for s_module
-- ----------------------------
DROP TABLE IF EXISTS `s_module`;
CREATE TABLE `s_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) DEFAULT NULL COMMENT '父模块id 0表示一级分类',
  `mod_name` varchar(64) DEFAULT NULL COMMENT '模块名',
  `mod_path` varchar(128) DEFAULT NULL COMMENT '访问action',
  `mod_icon` varchar(128) DEFAULT NULL COMMENT '模块图标',
  `mod_flag` tinyint(2) DEFAULT NULL COMMENT '1,2表示层级 | 3表示功能',
  `mod_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='后台模块';

-- ----------------------------
-- Records of s_module
-- ----------------------------
BEGIN;
INSERT INTO `s_module` VALUES (1, 0, '用户管理', NULL, 'fa fa-user', 1, 1);
INSERT INTO `s_module` VALUES (2, 0, '系统管理', NULL, 'fa fa-gear', 1, 2);
INSERT INTO `s_module` VALUES (100, 1, '会员列表', '/admin/user/userList', 'fa fa-group', 2, NULL);
INSERT INTO `s_module` VALUES (101, 100, '用户数据', '/admin/user/userListData', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (102, 100, '用户编辑', '/admin/user/userEdit', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (103, 100, '用户禁用/解封', '/admin/user/userEnable', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (104, 100, '用户详情', '/admin/user/userDetail', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (105, 100, '导出用户Excel', '/admin/user/exportUserExcel', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (106, 2, '管理员列表', '/admin/system/adminList', 'fa fa-user', 2, NULL);
INSERT INTO `s_module` VALUES (107, 106, '管理员编辑', '/admin/system/adminEdit', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (108, 106, '管理员冻结', '/admin/system/adminEnable', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (109, 2, '权限管理', '/admin/system/rights', 'fa fa-delicious', 2, NULL);
INSERT INTO `s_module` VALUES (110, 109, '编辑组', '/admin/system/roleEdit', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (111, 109, '禁用/启用组', '/admin/system/roleEnable', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (112, 109, '查看权限', '/admin/system/rolePerm', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (113, 109, '授权组', '/admin/system/permit', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (114, 2, '日志管理', '/admin/system/logList', 'fa fa-laptop', 2, NULL);
INSERT INTO `s_module` VALUES (115, 114, '日志备份', '/admin/system/backupLog', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (116, 2, '文件管理', '/admin/system/file', 'fa fa-archive', 2, NULL);
INSERT INTO `s_module` VALUES (117, 116, '获取目录', '/admin/system/getDirectory', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (118, 2, '系统工具', '/admin/system/tools', 'fa fa-crop', 2, NULL);
INSERT INTO `s_module` VALUES (119, 118, 'SQL编辑', '/admin/system/executeUpdate', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (120, 118, 'SQL查询', '/admin/system/executeQuery', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (121, 2, '地址管理', '/admin/system/address', 'fa fa-map', 2, NULL);
INSERT INTO `s_module` VALUES (122, 121, '编辑地址', '/admin/system/addrEdit', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (123, 121, '删除地址', '/admin/system/addrDel', NULL, 3, NULL);
INSERT INTO `s_module` VALUES (124, 2, '模块管理', '/admin/system/module', 'fa fa-desktop', 2, NULL);
INSERT INTO `s_module` VALUES (125, 124, '模块编辑', '/admin/system/moduleEdit', '', 3, NULL);
COMMIT;

-- ----------------------------
-- Table structure for s_msg
-- ----------------------------
DROP TABLE IF EXISTS `s_msg`;
CREATE TABLE `s_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL COMMENT '管理ID,10000',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统消息';

-- ----------------------------
-- Records of s_msg
-- ----------------------------
BEGIN;
INSERT INTO `s_msg` VALUES (1, 10000, '欢迎进入后台管理系统', '2017-09-11 11:16:48');
INSERT INTO `s_msg` VALUES (2, 10000, '任务计划【***】已完成', '2017-09-11 11:35:07');
COMMIT;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(16) DEFAULT NULL COMMENT '用户组名称',
  `role_rights` varchar(1024) DEFAULT NULL COMMENT '权限',
  `role_flag` tinyint(2) DEFAULT '1' COMMENT '是否可编辑 1-可编辑 0-不可编辑',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除 1-是 0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8 COMMENT='后台用户组';

-- ----------------------------
-- Records of s_role
-- ----------------------------
BEGIN;
INSERT INTO `s_role` VALUES (10000, '超级管理组', '1,2,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125', 0, b'0');
INSERT INTO `s_role` VALUES (10001, '财务组', NULL, 1, b'1');
INSERT INTO `s_role` VALUES (10002, '运维组', NULL, 1, b'0');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登陆时间',
  `realname` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `gender` bit(1) DEFAULT b'1' COMMENT '性别 1-男 0-女',
  `address` varchar(64) DEFAULT NULL COMMENT '住址',
  `birthday` varchar(32) DEFAULT NULL COMMENT '出生日期',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除 1-是 0-否(默认)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10013 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (10000, 'fei', '15515556993', '11@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2016-09-27 17:53:50', '2016-12-21 13:29:21', '11', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'1');
INSERT INTO `t_user` VALUES (10001, 'feifei', '17730215423', '22@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2016-09-27 11:04:17', NULL, '22', '340123199311012774', b'1', NULL, NULL, b'0');
INSERT INTO `t_user` VALUES (10002, 'feifeifei', '17734901234', '33@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2016-10-14 15:04:06', NULL, '22', '340123199311012774', b'1', '安徽省/合肥市/瑶海区', NULL, b'1');
INSERT INTO `t_user` VALUES (10003, 'test1', '17734901231', '44@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-04-20 16:35:11', NULL, '44', '340123199311012774', b'1', NULL, '2016-12-21', b'0');
INSERT INTO `t_user` VALUES (10004, 'test2', '17734901232', '55@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:24', NULL, '55', '340123199311012774', b'1', '安徽省/合肥市/瑶海区', NULL, b'0');
INSERT INTO `t_user` VALUES (10005, 'test3', '17734901233', '66@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:27', NULL, '22', '340123199311012774', b'1', NULL, NULL, b'0');
INSERT INTO `t_user` VALUES (10006, 'test4', '17734901235', '77@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:29', NULL, '66', '340123199311012774', b'1', NULL, '2016-12-21', b'0');
INSERT INTO `t_user` VALUES (10007, 'test5', '17734901236', '88@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:32', NULL, '33', '340123199311012774', b'1', '安徽省/合肥市/瑶海区', NULL, b'1');
INSERT INTO `t_user` VALUES (10008, 'test6', '17734901237', '99@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:34', NULL, '33', '340123199311012774', b'1', NULL, '2016-12-21', b'1');
INSERT INTO `t_user` VALUES (10009, 'test7', '17734901238', '00@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:37', NULL, '77', '340123199311012774', b'1', '安徽省/合肥市/瑶海区', NULL, b'1');
INSERT INTO `t_user` VALUES (10010, 'test8', '17734901239', '111@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:39', NULL, '44', '340123199311012774', b'1', NULL, '2016-12-21', b'1');
INSERT INTO `t_user` VALUES (10011, 'test9', '17734901200', '222@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:42', NULL, '88', '340123199311012774', b'1', '安徽省/合肥市/瑶海区', NULL, b'0');
INSERT INTO `t_user` VALUES (10012, 'test10', '17734901321', '333@qq.com', 'c4ca4238a0b923820dcc509a6f75849b', '/static/theme/images/avatar.jpg', '2017-06-07 16:13:44', NULL, '55', '340123199311012774', b'1', NULL, '2016-12-21', b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
