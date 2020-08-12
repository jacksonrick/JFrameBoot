CREATE TABLE `im_chat`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT,
    `chat_no`     int(11)    NOT NULL COMMENT '编号',
    `user_id`     bigint(20) NOT NULL COMMENT '用户1id',
    `other_id`    bigint(20) NOT NULL COMMENT '用户2id',
    `last_msg_id` bigint(20) DEFAULT NULL COMMENT '最后消息id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 37
  DEFAULT CHARSET = utf8mb4 COMMENT ='聊天室';

CREATE TABLE `im_message`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT,
    `chat_no`     int(11)     NOT NULL COMMENT '聊天室编号',
    `from_id`     bigint(20)  NOT NULL COMMENT '发信人id',
    `to_id`       bigint(20)  NOT NULL COMMENT '收信人id',
    `content`     text COMMENT '内容',
    `msg_type`    char(8)     NOT NULL DEFAULT 'txt' COMMENT 'txt,pic,aud,link',
    `readed`      bit(1)      NOT NULL DEFAULT b'0' COMMENT '收信人已读',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '发信时间',
    `extra`       varchar(100)         DEFAULT NULL COMMENT '额外数据',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 217
  DEFAULT CHARSET = utf8mb4 COMMENT ='聊天记录';