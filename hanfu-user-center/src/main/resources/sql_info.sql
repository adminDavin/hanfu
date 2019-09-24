-- hf_user
drop table if exists hf_user;
create table  hf_user
(
       id 				 integer NOT NULL AUTO_INCREMENT,
       username          VARCHAR(50) NOT NULL COMMENT '用户名',
       phone             VARCHAR(15)  COMMENT '电话号码',
       email             VARCHAR(50)  COMMENT '邮箱地址',
       source_type       VARCHAR(20)  COMMENT '用户来源',
       nick_name         VARCHAR(50)  COMMENT '昵称',
       real_name         VARCHAR(100)  COMMENT '真实姓名',
       sex               int1 default 1 COMMENT '性别 0 女 1 男',
       birth_day         datetime NOT NULL COMMENT '出生时间',
       user_status       int1  default 1 COMMENT '用户状态',
       head_pic          VARCHAR(200)  COMMENT '头像地址',
       address           VARCHAR(200) COMMENT '用户地址',
       user_level        int1 default 1 COMMENT '用户等级',
       last_auth_time    datetime NOT NULL COMMENT '最后一次时间',
       region            VARCHAR(30) COMMENT '地区',
       create_date       datetime NOT NULL COMMENT '创建时间',
       modify_date       datetime NOT NULL COMMENT '修改时间',
	   id_deleted   	 int1 default 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`) USING BTREE
);

-- hf_auth
drop table if exists hf_auth;
create table  hf_auth
(
       id 				 integer NOT NULL AUTO_INCREMENT,
       user_id           integer NOT NULL COMMENT '用户名',
       auth_type         varchar(20)  COMMENT '认证类型',
       auth_key          VARCHAR(100) 	/*认证key*/,
       encode_type       VARCHAR(20) 	/*加密方式*/,
	   create_date       datetime NOT NULL COMMENT '创建时间',
       modify_date       datetime NOT NULL COMMENT '修改时间',
	   id_deleted   	 int1 default 0 COMMENT '是否删除',
       PRIMARY KEY (`id`)
);
