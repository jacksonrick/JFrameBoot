-- 请勿照搬本人数据库设计，应按所在公司规范化设计

DROP DATABASE IF EXISTS `jframe`;

CREATE DATABASE `jframe` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `jframe`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(250) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(250) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('client', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', 'client_credentials,refresh_token', NULL, 'ROLE_USER', 1800, 86400, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('pwd', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('sso01', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', NULL, 'http://127.0.0.1:8091/sso/login', NULL, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('sso02', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', NULL, 'http://127.0.0.1:8092/sso/login', NULL, NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('web', NULL, NULL , 'all', 'implicit', 'http://127.0.0.1:8092/sso/login', NULL, NULL, NULL, NULL, 'true');
COMMIT;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for oauth_user
-- ----------------------------
DROP TABLE IF EXISTS `oauth_user`;
CREATE TABLE `oauth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_user
-- ----------------------------
BEGIN;
INSERT INTO `oauth_user` VALUES (1, 'ADMIN,USER,MGM', 'test', '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02');
COMMIT;

-- ----------------------------
-- Table structure for qz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_blob_triggers`;
CREATE TABLE `qz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qz_calendars`;
CREATE TABLE `qz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_cron_triggers`;
CREATE TABLE `qz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qz_cron_triggers` VALUES ('DefaultQuartzScheduler', 'com.jf.system.quartz.jobs.Job2', 'MY_GROUP', '0/5 * * * * ?', 'Asia/Shanghai');
INSERT INTO `qz_cron_triggers` VALUES ('DefaultQuartzScheduler', 'job', 'DEFAULT', '0 */1 * * * ?', 'Asia/Shanghai');
INSERT INTO `qz_cron_triggers` VALUES ('DefaultQuartzScheduler', 'job1', 'DEFAULT', '0 0/1 * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_fired_triggers`;
CREATE TABLE `qz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qz_job_details`;
CREATE TABLE `qz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qz_job_details
-- ----------------------------
BEGIN;
INSERT INTO `qz_job_details` VALUES ('DefaultQuartzScheduler', 'com.jf.system.quartz.jobs.Job2', 'MY_GROUP', NULL, 'com.jf.system.quartz.jobs.Job2', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
INSERT INTO `qz_job_details` VALUES ('DefaultQuartzScheduler', 'job', 'DEFAULT', NULL, 'com.jf.system.quartz.jobs.DayJob', '1', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
INSERT INTO `qz_job_details` VALUES ('DefaultQuartzScheduler', 'job1', 'DEFAULT', NULL, 'com.jf.system.quartz.jobs.Job1', '1', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
COMMIT;

-- ----------------------------
-- Table structure for qz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qz_locks`;
CREATE TABLE `qz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qz_locks` VALUES ('DefaultQuartzScheduler', 'STATE_ACCESS');
INSERT INTO `qz_locks` VALUES ('DefaultQuartzScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qz_paused_trigger_grps`;
CREATE TABLE `qz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qz_scheduler_state`;
CREATE TABLE `qz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO `qz_scheduler_state` VALUES ('DefaultQuartzScheduler', 'JacksonRick.local1552902392962', 1552902473133, 10000);
COMMIT;

-- ----------------------------
-- Table structure for qz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_simple_triggers`;
CREATE TABLE `qz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_simprop_triggers`;
CREATE TABLE `qz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qz_triggers`;
CREATE TABLE `qz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qz_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qz_triggers` VALUES ('DefaultQuartzScheduler', 'com.jf.system.quartz.jobs.Job2', 'MY_GROUP', 'com.jf.system.quartz.jobs.Job2', 'MY_GROUP', NULL, 1552027100000, 1552027095000, 5, 'WAITING', 'CRON', 1552027031000, 0, NULL, 0, '');
INSERT INTO `qz_triggers` VALUES ('DefaultQuartzScheduler', 'job', 'DEFAULT', 'job', 'DEFAULT', NULL, 1552902480000, 1552902420000, 5, 'WAITING', 'CRON', 1552902392000, 0, NULL, 0, '');
INSERT INTO `qz_triggers` VALUES ('DefaultQuartzScheduler', 'job1', 'DEFAULT', 'job1', 'DEFAULT', NULL, 1552027140000, 1552027080000, 5, 'WAITING', 'CRON', 1552026942000, 0, NULL, 0, '');
COMMIT;

-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT '用户组id',
  `login_name` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '登录密码',
  `realname` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `rights` varchar(1024) DEFAULT NULL COMMENT '权限',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '上次登录ip',
  `login_error_count` tinyint(4) DEFAULT '0' COMMENT '登录错误次数',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除 1-是 0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_admin_username` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8 COMMENT='后台管理员';

-- ----------------------------
-- Records of s_admin
-- ----------------------------
BEGIN;
INSERT INTO `s_admin` VALUES (10000, 10000, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超管', '17730215422', NULL, '2016-04-23 15:36:55', NULL, '2019-04-03 16:00:03', '127.0.0.1', 0, b'0');
INSERT INTO `s_admin` VALUES (10003, 10001, 'test', 'e10adc3949ba59abbe56e057f20f883e', 'test', '17726736171', '1,100,101', '2018-05-24 14:40:46', NULL, '2018-05-25 15:10:51', '127.0.0.1', 0, b'0');
COMMIT;

-- ----------------------------
-- Table structure for s_config
-- ----------------------------
DROP TABLE IF EXISTS `s_config`;
CREATE TABLE `s_config` (
  `key` varchar(65) NOT NULL COMMENT '键',
  `val` varchar(255) DEFAULT NULL COMMENT '值',
  `descr` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of s_config
-- ----------------------------
BEGIN;
INSERT INTO `s_config` VALUES ('sys_version', '5.0', '系统版本');
COMMIT;

-- ----------------------------
-- Table structure for s_log
-- ----------------------------
DROP TABLE IF EXISTS `s_log`;
CREATE TABLE `s_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(64) NOT NULL COMMENT '操作人',
  `remark` varchar(128) NOT NULL COMMENT '备注',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip',
  `params` varchar(255) DEFAULT NULL COMMENT '参数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Table structure for s_module
-- ----------------------------
DROP TABLE IF EXISTS `s_module`;
CREATE TABLE `s_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) NOT NULL COMMENT '父模块id 0表示一级分类',
  `name` varchar(64) NOT NULL COMMENT '模块名',
  `action` varchar(128) DEFAULT NULL COMMENT '访问地址',
  `icon_name` varchar(128) DEFAULT NULL COMMENT '模块图标',
  `flag` tinyint(2) DEFAULT NULL COMMENT '1,2表示菜单 | 3表示功能',
  `sort` tinyint(4) DEFAULT NULL COMMENT '菜单排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `module_path` (`action`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='后台模块';

-- ----------------------------
-- Records of s_module
-- ----------------------------
BEGIN;
INSERT INTO `s_module` VALUES (1, 0, '用户管理', NULL, 'fa fa-user', 1, 1, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (2, 0, '系统管理', NULL, 'fa fa-gear', 1, 10, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (100, 1, '会员列表', '/admin/user/userList', 'fa fa-group', 2, 1, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (101, 100, '用户数据', '/admin/user/userListData', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (102, 100, '用户编辑', '/admin/user/userEdit', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (103, 100, '用户禁用/解封', '/admin/user/userEnable', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (104, 100, '用户详情', '/admin/user/userDetail', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (105, 100, '导出用户Excel', '/admin/user/exportUserExcel', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (106, 2, '管理员列表', '/admin/system/adminList', 'fa fa-user', 2, 1, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (107, 106, '管理员编辑', '/admin/system/adminEdit', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (108, 106, '管理员冻结', '/admin/system/adminEnable', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (109, 2, '权限管理', '/admin/system/rights', 'fa fa-delicious', 2, 2, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (110, 109, '编辑组', '/admin/system/roleEdit', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (111, 109, '禁用/启用组', '/admin/system/roleEnable', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (112, 109, '查看权限', '/admin/system/permits', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (113, 109, '授权组', '/admin/system/permit', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (114, 2, '操作日志', '/admin/system/logList', 'fa fa-keyboard-o', 2, 3, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (115, 114, '日志备份', '/admin/system/backupLog', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (116, 2, '文件管理', '/admin/system/file', 'fa fa-archive', 2, 5, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (117, 116, '获取目录', '/admin/system/getDirectory', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (118, 2, '系统工具', '/admin/system/tools', 'fa fa-crop', 2, 6, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (119, 118, 'SQL编辑', '/admin/system/executeUpdate', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (120, 118, 'SQL查询', '/admin/system/executeQuery', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (121, 2, '地址管理', '/admin/system/address', 'fa fa-map', 2, 8, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (122, 121, '编辑地址', '/admin/system/addrEdit', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (123, 121, '删除地址', '/admin/system/addrDel', NULL, 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (124, 2, '模块管理', '/admin/system/module', 'fa fa-desktop', 2, 7, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (125, 124, '模块编辑', '/admin/system/moduleEdit', '', 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (126, 124, '模块删除', '/admin/system/moduleDel', '', 3, NULL, '2018-07-17 17:39:43', b'0');
INSERT INTO `s_module` VALUES (127, 2, '系统日志', '/admin/system/syslogList', 'fa fa-laptop', 2, 4, '2018-08-30 17:23:41', b'0');
COMMIT;

-- ----------------------------
-- Table structure for s_msg
-- ----------------------------
DROP TABLE IF EXISTS `s_msg`;
CREATE TABLE `s_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL COMMENT '管理ID',
  `content` text COMMENT '内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统消息';

-- ----------------------------
-- Records of s_msg
-- ----------------------------
BEGIN;
INSERT INTO `s_msg` VALUES (1, 10000, '系统消息', '2018-08-31 16:39:01');
COMMIT;

-- ----------------------------
-- Table structure for s_operation
-- ----------------------------
DROP TABLE IF EXISTS `s_operation`;
CREATE TABLE `s_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operator` varchar(30) DEFAULT NULL COMMENT '操作人',
  `content` varchar(100) DEFAULT NULL COMMENT '操作内容',
  `tb_name` varchar(30) DEFAULT NULL COMMENT '操作表',
  `tb_id` int(11) DEFAULT NULL COMMENT '操作表id',
  `ip` varchar(32) DEFAULT NULL COMMENT 'ip地址',
  `params` varchar(512) DEFAULT NULL COMMENT '参数，键值对',
  `extra` varchar(255) DEFAULT NULL COMMENT '额外属性',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='操作记录表';

-- ----------------------------
-- Records of s_operation
-- ----------------------------
BEGIN;
INSERT INTO `s_operation` VALUES (62, 'admin', '管理员登录', NULL, NULL, '127.0.0.1', 'username=admin', 'login', '2018-07-24 14:03:09');
INSERT INTO `s_operation` VALUES (63, 'admin', '删除模块', 's_module', 1001, '127.0.0.1', 'id=1001&name=', NULL, '2018-08-30 16:55:17');
COMMIT;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS `s_role`;
CREATE TABLE `s_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(16) NOT NULL COMMENT '用户组名称',
  `rights` varchar(1024) DEFAULT NULL COMMENT '权限',
  `flag` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否可编辑 1-可编辑 0-不可编辑',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除 1-是 0-否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_role_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8 COMMENT='后台用户组';

-- ----------------------------
-- Records of s_role
-- ----------------------------
BEGIN;
INSERT INTO `s_role` VALUES (10000, '超级管理组', NULL, 0, '2018-07-17 17:38:59', NULL, b'0');
INSERT INTO `s_role` VALUES (10001, '财务组', '1,100,105', 1, '2018-07-17 17:38:59', NULL, b'1');
INSERT INTO `s_role` VALUES (10002, '运维组', '2,124,125', 1, '2018-07-17 17:38:59', NULL, b'0');
COMMIT;

-- ----------------------------
-- Table structure for s_token
-- ----------------------------
DROP TABLE IF EXISTS `s_token`;
CREATE TABLE `s_token` (
  `uid` varchar(64) NOT NULL,
  `token` varchar(32) NOT NULL,
  `created` datetime DEFAULT CURRENT_TIMESTAMP,
  `expired` datetime NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Token';

-- ----------------------------
-- Records of s_token
-- ----------------------------
BEGIN;
INSERT INTO `s_token` VALUES ('1124954973340172288', '7AC264B3576BCBF830BC9F221FC85A4D', '2019-05-05 16:32:20', '2019-05-12 16:36:23');
INSERT INTO `s_token` VALUES ('1124956235494326272', '5011BC50E97D2E62421F23C011E69213', '2019-05-05 16:37:21', '2019-05-12 16:37:22');
COMMIT;

-- ----------------------------
-- Table structure for t_sms
-- ----------------------------
DROP TABLE IF EXISTS `t_sms`;
CREATE TABLE `t_sms` (
  `id` bigint(20) NOT NULL,
  `phone` varchar(16) NOT NULL,
  `code` varchar(10) NOT NULL,
  `source` varchar(10) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `valided` bit(1) NOT NULL DEFAULT b'0',
  UNIQUE KEY `t_sms_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信记录';

-- ----------------------------
-- Records of t_sms
-- ----------------------------
BEGIN;
INSERT INTO `t_sms` VALUES (1124954930176589824, '17755451874', '044609', 'reg', '2019-05-05 16:32:10', '2019-05-05 16:32:20', b'1');
INSERT INTO `t_sms` VALUES (1124956090656620544, '17755451875', '377204', 'reg', '2019-05-05 16:36:47', '2019-05-05 16:37:21', b'1');
COMMIT;

-- ----------------------------
-- Table structure for t_test
-- ----------------------------
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `crtime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test
-- ----------------------------
BEGIN;
INSERT INTO `t_test` VALUES (1, 'haha', 20, '2019-07-03 10:35:31');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `money` decimal(15,2) DEFAULT '0.00',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登陆时间',
  `realname` varchar(16) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `gender` bit(1) DEFAULT b'1' COMMENT '性别 1-男 0-女',
  `address` varchar(64) DEFAULT NULL COMMENT '住址',
  `birthday` varchar(32) DEFAULT NULL COMMENT '出生日期',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除 1-是 0-否(默认)',
  `wrong_pwd` int(11) NOT NULL DEFAULT '0' COMMENT '密码错误次数',
  `locked` bit(1) NOT NULL DEFAULT b'0' COMMENT 'APP登陆锁定',
  `extend` json DEFAULT NULL COMMENT '拓展字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (10113, 'user55451873', '17755451873', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10114, 'user98673915', '17798673915', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10115, 'user47013961', '17747013961', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10116, 'user19048063', '17719048063', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10117, 'user34198435', '17734198435', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10118, 'user13847987', '17713847987', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10119, 'user46644635', '17746644635', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10120, 'user91679214', '17791679214', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10121, 'user38462170', '17738462170', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10122, 'user87273210', '17787273210', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10123, 'user40979542', '17740979542', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10124, 'user23078085', '17723078085', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10125, 'user72451801', '17772451801', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10126, 'user13024752', '17713024752', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10127, 'user17768359', '17717768359', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10128, 'user39767541', '17739767541', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10129, 'user45532633', '17745532633', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10130, 'user98360542', '17798360542', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10131, 'user75204815', '17775204815', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10132, 'user70942451', '17770942451', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10133, 'user29097811', '17729097811', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10134, 'user12661708', '17712661708', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10135, 'user56015110', '17756015110', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10136, 'user52090426', '17752090426', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10137, 'user82406805', '17782406805', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10138, 'user65762749', '17765762749', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10139, 'user71593332', '17771593332', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10140, 'user60678414', '17760678414', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10141, 'user78612080', '17778612080', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10142, 'user21025162', '17721025162', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10143, 'user39289570', '17739289570', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10144, 'user33372366', '17733372366', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10145, 'user38993124', '17738993124', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10146, 'user84848526', '17784848526', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10147, 'user27263261', '17727263261', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10148, 'user51770730', '17751770730', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10149, 'user77063868', '17777063868', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10150, 'user40007152', '17740007152', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10151, 'user48844160', '17748844160', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10152, 'user24199348', '17724199348', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10153, 'user54464261', '17754464261', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10154, 'user99723265', '17799723265', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10155, 'user55223543', '17755223543', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10156, 'user56947922', '17756947922', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10157, 'user19068984', '17719068984', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10158, 'user94501157', '17794501157', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10159, 'user45298834', '17745298834', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10160, 'user22990703', '17722990703', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10161, 'user59057017', '17759057017', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10162, 'user36312977', '17736312977', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10163, 'user84393838', '17784393838', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10164, 'user33030260', '17733030260', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10165, 'user81969791', '17781969791', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10166, 'user30758176', '17730758176', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10167, 'user77881511', '17777881511', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10168, 'user17133030', '17717133030', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10169, 'user22020618', '17722020618', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10170, 'user48704005', '17748704005', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10171, 'user77458174', '17777458174', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10172, 'user51178856', '17751178856', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10173, 'user13519761', '17713519761', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10174, 'user84062242', '17784062242', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10175, 'user99751929', '17799751929', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10176, 'user56572921', '17756572921', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10177, 'user63608820', '17763608820', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10178, 'user48325340', '17748325340', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10179, 'user40800244', '17740800244', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10180, 'user49025203', '17749025203', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10181, 'user22725285', '17722725285', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10182, 'user46550818', '17746550818', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10183, 'user64578240', '17764578240', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10184, 'user83238747', '17783238747', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10185, 'user32459018', '17732459018', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10186, 'user82578853', '17782578853', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10187, 'user35517214', '17735517214', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10188, 'user99849512', '17799849512', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10189, 'user22695923', '17722695923', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10190, 'user73931082', '17773931082', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10191, 'user21567642', '17721567642', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10192, 'user56044966', '17756044966', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10193, 'user25521910', '17725521910', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10194, 'user39474652', '17739474652', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10195, 'user20807534', '17720807534', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10196, 'user65613714', '17765613714', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10197, 'user75645974', '17775645974', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10198, 'user81388730', '17781388730', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10199, 'user80005730', '17780005730', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10200, 'user55862464', '17755862464', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10201, 'user29295131', '17729295131', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10202, 'user58888266', '17758888266', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10203, 'user16555939', '17716555939', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10204, 'user76114901', '17776114901', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10205, 'user50906692', '17750906692', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10206, 'user16188759', '17716188759', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10207, 'user98223722', '17798223722', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10208, 'user72552335', '17772552335', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10209, 'user58090514', '17758090514', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10210, 'user62795567', '17762795567', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10211, 'user39706293', '17739706293', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
INSERT INTO `t_user` VALUES (10212, 'user90144770', '17790144770', 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'0', 0, b'0', NULL);
COMMIT;

-- ----------------------------
-- Function structure for func_dist
-- ----------------------------
DROP FUNCTION IF EXISTS `func_dist`;
delimiter ;;
CREATE FUNCTION `jframe`.`func_dist`(origLng DOUBLE(12,6),origLat DOUBLE(12,6), longitude DOUBLE(12,6), latitude DOUBLE(12,6))
 RETURNS double
  READS SQL DATA
  COMMENT '获取经纬度距离'
BEGIN
      RETURN round(6378.138*2*asin(sqrt(pow(sin((origLat*pi()/180-latitude*pi()/180)/2),2)+cos(origLat*pi()/180)*cos(latitude*pi()/180)*pow(sin( (origLng*pi()/180-longitude*pi()/180)/2),2)))*1000);
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for insert_batch
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_batch`;
delimiter ;;
CREATE PROCEDURE `jframe`.`insert_batch`(in l int)
BEGIN
	DECLARE i int DEFAULT 1;
	DECLARE a int DEFAULT 1;

	WHILE i <= l do
		SET a = ROUND(rand() * 90000000 + 10000000);
		INSERT INTO `t_user`(`nickname`, `phone`, `email`, `password`, `avatar`, `money`, `create_time`, `last_login_time`, `realname`, `idcard`, `gender`, `address`, `birthday`, `deleted`, `extend`) VALUES (CONCAT('user', a), CONCAT('177', a), 'test@qq.com', '5d24bbbe2fe58aba66827aa870004c79', '/static/theme/images/avatar.jpg', 1000.00, '2016-09-27 17:53:50', '2016-12-21 13:29:21', '1', '340123199311012774', b'0', '安徽省/合肥市/瑶海区', '2016-12-21', b'1', NULL);
		SET i = i + 1;
	END WHILE;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;