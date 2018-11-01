/*
 Navicat Premium Data Transfer

 Source Server         : pg.test.200
 Source Server Type    : PostgreSQL
 Source Server Version : 90605
 Source Host           : 192.168.24.200:5432
 Source Catalog        : paygw
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90605
 File Encoding         : 65001

 Date: 17/07/2018 17:55:48
*/


-- ----------------------------
-- Table structure for s_admin
-- ----------------------------
DROP TABLE IF EXISTS "public"."s_admin";
CREATE TABLE "public"."s_admin" (
  "id" int4 NOT NULL DEFAULT nextval('s_admin_id_seq'::regclass),
  "role_id" int4 NOT NULL,
  "admin_name" varchar(32) COLLATE "pg_catalog"."default",
  "admin_realname" varchar(16) COLLATE "pg_catalog"."default",
  "admin_password" varchar(32) COLLATE "pg_catalog"."default",
  "admin_phone" varchar(16) COLLATE "pg_catalog"."default",
  "admin_rights" varchar(1000) COLLATE "pg_catalog"."default",
  "admin_create_time" timestamp(6) DEFAULT now(),
  "admin_login_time" timestamp(6),
  "admin_login_ip" varchar(64) COLLATE "pg_catalog"."default",
  "deleted" bool DEFAULT false
)
;
ALTER TABLE "public"."s_admin" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_admin"."id" IS 'id';
COMMENT ON COLUMN "public"."s_admin"."role_id" IS '用户组id';
COMMENT ON COLUMN "public"."s_admin"."admin_name" IS '用户名';
COMMENT ON COLUMN "public"."s_admin"."admin_realname" IS '真实姓名';
COMMENT ON COLUMN "public"."s_admin"."admin_password" IS '登录密码';
COMMENT ON COLUMN "public"."s_admin"."admin_phone" IS '手机号';
COMMENT ON COLUMN "public"."s_admin"."admin_rights" IS '权限';
COMMENT ON COLUMN "public"."s_admin"."admin_create_time" IS '账号创建日期';
COMMENT ON COLUMN "public"."s_admin"."admin_login_time" IS '上次登录时间';
COMMENT ON COLUMN "public"."s_admin"."admin_login_ip" IS '上次登录ip';
COMMENT ON COLUMN "public"."s_admin"."deleted" IS '是否删除';
COMMENT ON TABLE "public"."s_admin" IS '后台管理员';

-- ----------------------------
-- Records of s_admin
-- ----------------------------
BEGIN;
INSERT INTO "public"."s_admin" VALUES (10000, 10000, 'admin', '超管', 'e10adc3949ba59abbe56e057f20f883e', '17730215422', NULL, '2018-07-17 15:56:10.411314', '2018-07-17 17:35:12.486', '127.0.0.1', 'f');
COMMIT;

-- ----------------------------
-- Table structure for s_config
-- ----------------------------

CREATE TABLE "public"."s_config" (
  "key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "val" varchar(255) COLLATE "pg_catalog"."default",
  "descr" varchar(255) COLLATE "pg_catalog"."default",
  CONSTRAINT "s_config_pkey" PRIMARY KEY ("key")
)
;

ALTER TABLE "public"."s_config" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_config"."key" IS '键';
COMMENT ON COLUMN "public"."s_config"."val" IS '值';
COMMENT ON COLUMN "public"."s_config"."descr" IS '描述';
COMMENT ON TABLE "public"."s_config" IS '配置表';

-- ----------------------------
-- Table structure for s_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."s_log";
CREATE TABLE "public"."s_log" (
  "id" int4 NOT NULL DEFAULT nextval('s_log_id_seq'::regclass),
  "log_user" varchar(64) COLLATE "pg_catalog"."default",
  "log_remark" varchar(255) COLLATE "pg_catalog"."default",
  "log_ip" varchar(32) COLLATE "pg_catalog"."default",
  "log_params" varchar(255) COLLATE "pg_catalog"."default",
  "log_create_time" timestamp(6) DEFAULT now()
)
;
ALTER TABLE "public"."s_log" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_log"."log_user" IS '操作人';
COMMENT ON COLUMN "public"."s_log"."log_remark" IS '备注';
COMMENT ON COLUMN "public"."s_log"."log_ip" IS 'ip';
COMMENT ON COLUMN "public"."s_log"."log_params" IS '参数';
COMMENT ON COLUMN "public"."s_log"."log_create_time" IS '创建时间';
COMMENT ON TABLE "public"."s_log" IS '日志表';

-- ----------------------------
-- Records of s_log
-- ----------------------------
BEGIN;
INSERT INTO "public"."s_log" VALUES (1, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2018-07-17 17:24:12.323');
INSERT INTO "public"."s_log" VALUES (2, 'admin', '管理员登录', '127.0.0.1', 'username=admin', '2018-07-17 17:35:12.515');
INSERT INTO "public"."s_log" VALUES (3, 'admin', '授权组', '127.0.0.1', 'roleId=10001', '2018-07-17 17:36:30.016');
COMMIT;

-- ----------------------------
-- Table structure for s_module
-- ----------------------------
DROP TABLE IF EXISTS "public"."s_module";
CREATE TABLE "public"."s_module" (
  "id" int4 NOT NULL DEFAULT nextval('s_module_id_seq'::regclass),
  "parent_id" int4 NOT NULL,
  "mod_name" varchar(64) COLLATE "pg_catalog"."default",
  "mod_path" varchar(128) COLLATE "pg_catalog"."default",
  "mod_icon" varchar(128) COLLATE "pg_catalog"."default",
  "mod_flag" int2,
  "mod_sort" int2,
  "mod_create_time" timestamp(6) DEFAULT now(),
  "deleted" bool DEFAULT false
)
;
ALTER TABLE "public"."s_module" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_module"."id" IS 'id';
COMMENT ON COLUMN "public"."s_module"."parent_id" IS '父模块id 0表示一级分类';
COMMENT ON COLUMN "public"."s_module"."mod_name" IS '模块名';
COMMENT ON COLUMN "public"."s_module"."mod_path" IS '访问action';
COMMENT ON COLUMN "public"."s_module"."mod_icon" IS '模块图标';
COMMENT ON COLUMN "public"."s_module"."mod_flag" IS '1,2表示层级 | 3表示功能';
COMMENT ON COLUMN "public"."s_module"."mod_sort" IS '排序';
COMMENT ON COLUMN "public"."s_module"."mod_create_time" IS '创建时间';
COMMENT ON COLUMN "public"."s_module"."deleted" IS '是否删除';
COMMENT ON TABLE "public"."s_module" IS '后台模块';

-- ----------------------------
-- Records of s_module
-- ----------------------------
BEGIN;
INSERT INTO "public"."s_module" VALUES (1, 0, '系统管理', NULL, 'fa fa-gear', 1, 10, '2018-07-17 16:16:31.01073', 'f');
INSERT INTO "public"."s_module" VALUES (107, 106, '管理员编辑', '/admin/system/adminEdit', NULL, 3, NULL, '2018-07-17 16:18:37.608083', 'f');
INSERT INTO "public"."s_module" VALUES (108, 106, '管理员冻结', '/admin/system/adminEnable', NULL, 3, NULL, '2018-07-17 16:18:37.609757', 'f');
INSERT INTO "public"."s_module" VALUES (110, 109, '编辑组', '/admin/system/roleEdit', NULL, 3, NULL, '2018-07-17 16:18:37.613165', 'f');
INSERT INTO "public"."s_module" VALUES (111, 109, '禁用/启用组', '/admin/system/roleEnable', NULL, 3, NULL, '2018-07-17 16:18:37.614802', 'f');
INSERT INTO "public"."s_module" VALUES (112, 109, '查看权限', '/admin/system/permits', NULL, 3, NULL, '2018-07-17 16:18:37.616407', 'f');
INSERT INTO "public"."s_module" VALUES (113, 109, '授权组', '/admin/system/permit', NULL, 3, NULL, '2018-07-17 16:18:37.618078', 'f');
INSERT INTO "public"."s_module" VALUES (115, 114, '日志备份', '/admin/system/backupLog', NULL, 3, NULL, '2018-07-17 16:18:37.621191', 'f');
INSERT INTO "public"."s_module" VALUES (117, 116, '获取目录', '/admin/system/getDirectory', NULL, 3, NULL, '2018-07-17 16:18:37.625449', 'f');
INSERT INTO "public"."s_module" VALUES (119, 118, 'SQL编辑', '/admin/system/executeUpdate', NULL, 3, NULL, '2018-07-17 16:18:37.628952', 'f');
INSERT INTO "public"."s_module" VALUES (120, 118, 'SQL查询', '/admin/system/executeQuery', NULL, 3, NULL, '2018-07-17 16:18:37.630593', 'f');
INSERT INTO "public"."s_module" VALUES (125, 124, '模块编辑', '/admin/system/moduleEdit', '', 3, NULL, '2018-07-17 16:18:37.639243', 'f');
INSERT INTO "public"."s_module" VALUES (126, 124, '模块删除', '/admin/system/moduleDel', '', 3, NULL, '2018-07-17 16:18:37.640978', 'f');
INSERT INTO "public"."s_module" VALUES (106, 1, '管理员列表', '/admin/system/adminList', 'fa fa-user', 2, 1, '2018-07-17 16:18:37.606119', 'f');
INSERT INTO "public"."s_module" VALUES (109, 1, '权限管理', '/admin/system/rights', 'fa fa-delicious', 2, 2, '2018-07-17 16:18:37.611426', 'f');
INSERT INTO "public"."s_module" VALUES (114, 1, '日志管理', '/admin/system/logList', 'fa fa-laptop', 2, 3, '2018-07-17 16:18:37.619586', 'f');
INSERT INTO "public"."s_module" VALUES (118, 1, '系统工具', '/admin/system/tools', 'fa fa-crop', 2, 5, '2018-07-17 16:18:37.627168', 'f');
INSERT INTO "public"."s_module" VALUES (116, 1, '文件管理', '/admin/system/file', 'fa fa-archive', 2, 4, '2018-07-17 16:18:37.623485', 'f');
INSERT INTO "public"."s_module" VALUES (124, 1, '模块管理', '/admin/system/module', 'fa fa-desktop', 2, 6, '2018-07-17 16:18:37.637545', 'f');
INSERT INTO "public"."s_module" VALUES (121, 1, '地址管理', '/admin/system/address', 'fa fa-map', 2, 7, '2018-07-17 16:18:37.632397', 't');
INSERT INTO "public"."s_module" VALUES (122, 121, '编辑地址', '/admin/system/addrEdit', NULL, 3, NULL, '2018-07-17 16:18:37.634204', 't');
INSERT INTO "public"."s_module" VALUES (123, 121, '删除地址', '/admin/system/addrDel', NULL, 3, NULL, '2018-07-17 16:18:37.635905', 't');
COMMIT;

-- ----------------------------
-- Table structure for s_msg
-- ----------------------------
DROP TABLE IF EXISTS "public"."s_msg";
CREATE TABLE "public"."s_msg" (
  "id" int4 NOT NULL DEFAULT nextval('s_msg_id_seq'::regclass),
  "admin_id" int4 NOT NULL,
  "content" text COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) DEFAULT now()
)
;
ALTER TABLE "public"."s_msg" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_msg"."admin_id" IS '管理ID';
COMMENT ON COLUMN "public"."s_msg"."content" IS '内容';
COMMENT ON TABLE "public"."s_msg" IS '系统消息';

-- ----------------------------
-- Records of s_msg
-- ----------------------------
BEGIN;
INSERT INTO "public"."s_msg" VALUES (1, 10000, '1111', '2018-07-17 17:30:32.362607');
COMMIT;

-- ----------------------------
-- Table structure for s_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."s_role";
CREATE TABLE "public"."s_role" (
  "id" int4 NOT NULL DEFAULT nextval('s_role_id_seq'::regclass),
  "role_name" varchar(16) COLLATE "pg_catalog"."default",
  "role_flag" int2 DEFAULT 1,
  "role_rights" varchar(1000) COLLATE "pg_catalog"."default",
  "role_create_time" timestamp(6) DEFAULT now(),
  "deleted" bool DEFAULT false
)
;
ALTER TABLE "public"."s_role" OWNER TO "postgres";
COMMENT ON COLUMN "public"."s_role"."id" IS 'id';
COMMENT ON COLUMN "public"."s_role"."role_name" IS '用户组名称';
COMMENT ON COLUMN "public"."s_role"."role_flag" IS '是否可编辑 1-可编辑 0-不可编辑';
COMMENT ON COLUMN "public"."s_role"."role_rights" IS '权限';
COMMENT ON COLUMN "public"."s_role"."role_create_time" IS '创建时间';
COMMENT ON COLUMN "public"."s_role"."deleted" IS '是否删除';
COMMENT ON TABLE "public"."s_role" IS '后台用户组';

-- ----------------------------
-- Records of s_role
-- ----------------------------
BEGIN;
INSERT INTO "public"."s_role" VALUES (10000, '超级管理组', 0, NULL, '2018-07-17 17:55:11.928909', 'f');
INSERT INTO "public"."s_role" VALUES (10001, '运维组', 1, '1,106,108,107', '2018-07-17 17:55:11.928909', 'f');
COMMIT;

-- ----------------------------
-- Indexes structure for table s_admin
-- ----------------------------
CREATE INDEX "s_admin_name" ON "public"."s_admin" USING btree (
  "admin_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table s_admin
-- ----------------------------
ALTER TABLE "public"."s_admin" ADD CONSTRAINT "s_admin_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table s_log
-- ----------------------------
ALTER TABLE "public"."s_log" ADD CONSTRAINT "s_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table s_module
-- ----------------------------
CREATE INDEX "s_module_path" ON "public"."s_module" USING btree (
  "mod_path" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table s_module
-- ----------------------------
ALTER TABLE "public"."s_module" ADD CONSTRAINT "s_module_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table s_msg
-- ----------------------------
ALTER TABLE "public"."s_msg" ADD CONSTRAINT "s_msg_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table s_role
-- ----------------------------
ALTER TABLE "public"."s_role" ADD CONSTRAINT "s_role_pkey" PRIMARY KEY ("id");
