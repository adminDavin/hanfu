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

drop table if exists hf_user_address;

drop table if exists hf_user_balance;



/*==============================================================*/
/* Table: hf_user_address                                       */
/*==============================================================*/
create table hf_user_address
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   user_id              int comment '用户id',
   is_fault_address     int comment '是否为默认地址',
   hf_province          varchar(127) comment '省',
   hf_city              varchar(127) comment '市',
   hf_conty             varchar(127) comment '县/区',
   hf_address_detail    varchar(127) comment '详情地址',
   contact              varchar(127) comment '联系人',
   phone_number         varchar(127) comment '联系电话',
   hf_desc              varchar(127) comment '备注',
   create_time          timestamp default now() comment  '添加时间',
   modify_time          timestamp default now() comment  '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_user_address comment '用户地址管理';

/*==============================================================*/
/* Table: hf_user_balance                                       */
/*==============================================================*/
create table hf_user_balance
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   user_id              int comment '用户id',
   balance_type         varchar(15) comment '余额类型',
   hf_balance           int comment '用户余额(单位分)',
   pay_method_type      varchar(31) comment '最近一次充值方式',
   hf_remark            varchar(127) comment '备注',
   create_time          timestamp default now() comment  '添加时间',
   modify_time          timestamp default now() comment  '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_user_balance comment '用户余额表';


alter table hf_user_address add constraint FK_Reference_30 foreign key (user_id)
      references hf_user (id) on delete restrict on update restrict;

alter table hf_user_balance add constraint FK_Reference_31 foreign key (user_id)
      references hf_user (id) on delete restrict on update restrict;

