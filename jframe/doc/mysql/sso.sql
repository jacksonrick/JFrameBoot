DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`
(
    `token_id`          varchar(256) DEFAULT NULL,
    `token`             blob,
    `authentication_id` varchar(250) NOT NULL,
    `user_name`         varchar(256) DEFAULT NULL,
    `client_id`         varchar(256) DEFAULT NULL,
    `authentication`    blob,
    `refresh_token`     varchar(256) DEFAULT NULL,
    PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(250) NOT NULL,
    `resource_ids`            varchar(256)  DEFAULT NULL,
    `client_secret`           varchar(256)  DEFAULT NULL,
    `scope`                   varchar(256)  DEFAULT NULL,
    `authorized_grant_types`  varchar(256)  DEFAULT NULL,
    `web_server_redirect_uri` varchar(256)  DEFAULT NULL,
    `authorities`             varchar(256)  DEFAULT NULL,
    `access_token_validity`   int(11)       DEFAULT NULL,
    `refresh_token_validity`  int(11)       DEFAULT NULL,
    `additional_information`  varchar(4096) DEFAULT NULL,
    `autoapprove`             varchar(256)  DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;

BEGIN;
INSERT INTO `oauth_client_details`
VALUES ('client', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', 'client_credentials,refresh_token', NULL,
        'ROLE_USER', 1800, 86400, NULL, 'true');
INSERT INTO `oauth_client_details`
VALUES ('pwd', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', 'password,refresh_token', NULL, NULL, NULL,
        NULL, NULL, 'true');
INSERT INTO `oauth_client_details`
VALUES ('sso01', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', NULL, 'http://127.0.0.1:8091/sso/login', NULL,
        NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details`
VALUES ('sso02', NULL, '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02', 'all', NULL, 'http://127.0.0.1:8092/sso/login', NULL,
        NULL, NULL, NULL, 'true');
INSERT INTO `oauth_client_details`
VALUES ('web', NULL, NULL, 'all', 'implicit', 'http://127.0.0.1:8092/sso/login', NULL, NULL, NULL, NULL, 'true');
COMMIT;


DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`
(
    `token_id`       varchar(256) DEFAULT NULL,
    `token`          blob,
    `authentication` blob
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`
(
    `token_id`          varchar(256) DEFAULT NULL,
    `token`             blob,
    `authentication_id` varchar(256) NOT NULL,
    `user_name`         varchar(256) DEFAULT NULL,
    `client_id`         varchar(256) DEFAULT NULL,
    PRIMARY KEY (`authentication_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
    `code`           varchar(256) DEFAULT NULL,
    `authentication` blob
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`
(
    `userId`         varchar(256)   DEFAULT NULL,
    `clientId`       varchar(256)   DEFAULT NULL,
    `scope`          varchar(256)   DEFAULT NULL,
    `status`         varchar(10)    DEFAULT NULL,
    `expiresAt`      timestamp NULL DEFAULT NULL,
    `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS `oauth_user`;
CREATE TABLE `oauth_user`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `role` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `pwd`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

BEGIN;
INSERT INTO `oauth_user`
VALUES (1, 'ADMIN,USER,MGM', 'test', '$2a$10$2QaFSy4T84/06c2uREOqxeTSNRsA1z6YYsGM/NJl..ZbjrOP9lL02');
COMMIT;


