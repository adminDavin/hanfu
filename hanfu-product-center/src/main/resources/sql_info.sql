/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/9/24 18:06:31                           */
/*==============================================================*/


drop table if exists category_spec;

drop table if exists hf_boss;

drop table if exists hf_brand;

drop table if exists hf_category;

drop table if exists hf_goods;

drop table if exists hf_goods_info;

drop table if exists hf_goods_pictrue;

drop table if exists hf_goods_pictrue2;

drop table if exists hf_stone;

drop table if exists product;

drop table if exists product_info;

drop table if exists product_instance;

drop table if exists product_source;

drop table if exists product_spec;

drop table if exists stone_info;

/*==============================================================*/
/* Table: category_spec                                         */
/*==============================================================*/
create table category_spec
(
   id                   int not null,
   hf_name              varchar(127),
   classic_type         int,
   spec_type            varchar(63),
   spec_unit            varchar(15),
   spec_value           varchar(127),
   create_time          timestamp,
   modify_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table category_spec comment '类目规格';

/*==============================================================*/
/* Table: hf_boss                                               */
/*==============================================================*/
create table hf_boss
(
   id                   int,
   name                 varchar(63),
   amount               int,
   create_time          datetime,
   modify_time          datetime,
   expire_time          datetime,
   is_deleted           bool
);

alter table hf_boss comment '商家';

/*==============================================================*/
/* Table: hf_brand                                              */
/*==============================================================*/
create table hf_brand
(
   id                   int not null,
   product_id           char(1),
   primary key (id)
);

alter table hf_brand comment '商品品牌';

/*==============================================================*/
/* Table: hf_category                                           */
/*==============================================================*/
create table hf_category
(
   id                   int not null,
   hf_name              char(1),
   category_id          int,
   level_id             int,
   create_time          timestamp,
   modify_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table hf_category comment '商品类目表';

/*==============================================================*/
/* Table: hf_goods                                              */
/*==============================================================*/
create table hf_goods
(
   id                   int not null,
   instance_id          int,
   quantity             int,
   spec_desc            varchar(127),
   price_id             int,
   create_time          timestamp,
   modify_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table hf_goods comment '商品实体定价单元(SKU)';

/*==============================================================*/
/* Table: hf_goods_info                                         */
/*==============================================================*/
create table hf_goods_info
(
   id                   int not null,
   instance_id          int,
   hf_resp              varchar(63),
   spec_desc            varchar(15),
   price                varchar(127),
   create_time          timestamp,
   modify_time          timestamp,
   last_modifier        varchar(15),
   is_deleted           smallint,
   primary key (id)
);

alter table hf_goods_info comment '商品实体定价单元规格描述';

/*==============================================================*/
/* Table: hf_goods_pictrue                                      */
/*==============================================================*/
create table hf_goods_pictrue
(
   id                   int not null,
   instance_id          int,
   hf_name              varchar(63),
   spec_desc            varchar(127),
   picture_address      char(10),
   create_time          timestamp,
   modify_time          timestamp,
   last_modifier        varchar(15),
   is_deleted           smallint,
   primary key (id)
);

alter table hf_goods_pictrue comment '商品实体定价单图片描述';

/*==============================================================*/
/* Table: hf_goods_pictrue2                                     */
/*==============================================================*/
create table hf_goods_pictrue2
(
   id                   int not null,
   instance_id          int,
   price                varchar(63),
   discount             varchar(127),
   picture_address      char(10),
   create_time          timestamp,
   modify_time          timestamp,
   last_modifier        varchar(15),
   is_deleted           smallint,
   primary key (id)
);

alter table hf_goods_pictrue2 comment '商品实体定价实体';

/*==============================================================*/
/* Table: hf_stone                                              */
/*==============================================================*/
create table hf_stone
(
   id                   int not null,
   hf_name              varchar(127),
   boss_id              int,
   user_id              int,
   hf_desc              varchar(255),
   create_time          datetime,
   expire_time          datetime,
   hf_status            int,
   is_deleted           bool,
   primary key (id)
);

alter table hf_stone comment '商铺';

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   int not null,
   hf_name              varchar(100),
   category_id          int,
   product_desc         varchar(255),
   boss_id              int,
   create_time          timestamp,
   last_modifier        varchar(15),
   modify_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table product comment '商品';

/*==============================================================*/
/* Table: product_info                                          */
/*==============================================================*/
create table product_info
(
   id                   int not null,
   product_id           int,
   hf_name              varchar(100),
   hf_value             varchar(255),
   value_unit           varchar(15),
   create_time          timestamp,
   modify_time          timestamp,
   last_modifier        varchar(15),
   hf_remark            varchar(100),
   is_deleted           smallint,
   primary key (id)
);

alter table product_info comment '商品属性描述';

/*==============================================================*/
/* Table: product_instance                                      */
/*==============================================================*/
create table product_instance
(
   id                   int not null,
   product_id           int,
   stone_id             int,
   create_time          timestamp,
   modify_time          timestamp,
   last_modifier        varchar(15),
   is_deleted           smallint,
   primary key (id)
);

alter table product_instance comment '商品实体';

/*==============================================================*/
/* Table: product_source                                        */
/*==============================================================*/
create table product_source
(
   id                   int not null,
   name                 int,
   factory_id           int,
   create_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table product_source comment '商品来源';

/*==============================================================*/
/* Table: product_spec                                          */
/*==============================================================*/
create table product_spec
(
   id                   int not null,
   hf_name              char(1),
   product_id           int,
   spec_type            varchar(63),
   spec_unit            varchar(31),
   spec_value           varchar(127),
   create_time          timestamp,
   modify_time          timestamp,
   is_deleted           smallint,
   primary key (id)
);

alter table product_spec comment '商品规格';

/*==============================================================*/
/* Table: stone_info                                            */
/*==============================================================*/
create table stone_info
(
   id                   int not null,
   stone_id             int,
   attribute            varchar(63),
   value                varchar(127),
   create_time          datetime,
   modify_time          datetime,
   is_deleted           bool,
   primary key (id)
);

alter table stone_info comment '店铺属性描述';

