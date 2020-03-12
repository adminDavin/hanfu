

/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/9/27 10:25:38                           */
/*==============================================================*/


drop table if exists category_spec;

drop table if exists file_desc;

drop table if exists hf_boss;

drop table if exists hf_brand;

drop table if exists hf_category;

drop table if exists hf_goods;

drop table if exists hf_goods_pictrue;

drop table if exists hf_product_pictrue;

drop table if exists hf_warehouse;

drop table if exists hf_goods_spec;

drop table if exists hf_price;

drop table if exists hf_price_mode;

drop table if exists hf_resp;

drop table if exists hf_stone;

drop table if exists product;

drop table if exists product_info;

drop table if exists product_instance;

drop table if exists product_spec;

/*==============================================================*/
/* Table: category_spec                                         */
/*==============================================================*/
create table category_spec
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   category_id          int comment '所属类目',
   hf_name              varchar(127) comment '规格名称',
   spec_unit            varchar(15) comment '规格单位',
   spec_value           varchar(127) comment '规格值',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table category_spec comment '类目规格';

/*==============================================================*/
/* Table: file_desc                                             */
/*==============================================================*/
create table file_desc
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   file_name            varchar(63) comment '文件名',
   user_id              int comment '用户Id',
   group_name           varchar(63) comment '文件组',
   remote_filename      varchar(255) comment '文件路径',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table file_desc comment '文件描述';

/*==============================================================*/
/* Table: hf_boss                                               */
/*==============================================================*/
create table hf_boss
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   name                 varchar(63) comment '商家名称',
   amount               int comment '账户余额',
   create_time          datetime  default now()  comment '创建时间',
   modify_time          datetime  default now()  comment '修改时间',
   expire_time          datetime  default now()  comment '失效时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_boss comment '商家';

/*==============================================================*/
/* Table: hf_brand                                              */
/*==============================================================*/
create table hf_brand
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(127) comment '品牌名称',
   brand_type           varchar(31) comment '品牌类型',
   hf_desc              varchar(127) comment '品牌描述',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_brand comment '商品品牌';

/*==============================================================*/
/* Table: hf_category                                           */
/*==============================================================*/
create table hf_category
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(31) comment '类目名称',
   parent_category_id          int comment '所属类目',
   level_id             int comment '所属级别',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '调整时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_category comment '商品类目表';

/*==============================================================*/
/* Table: hf_goods                                              */
/*==============================================================*/
create table hf_goods
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(63) comment '物品名称',
   instance_id          int comment '商品实体id',
   product_id           int comment '商品id',
   category_id          int comment '所属类目',
   stone_id             int comment '商铺id',
   boss_id              int comment '所属商家',
   brand_id             int comment '品牌id',
   resp_id              int comment '库存id',
   price_id              int comment '价格id',
   goods_desc           varchar(127) comment '物品描述',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_goods comment '商品实体定价单元(SKU)';

/*==============================================================*/
/* Table: hf_goods_pictrue                                      */
/*==============================================================*/
create table hf_goods_pictrue
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   goods_id             int comment '物品id',
   hf_name              varchar(63) comment '图片名称',
   spec_desc            varchar(127) comment '图片说明',
   file_id              int comment '文件id',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_goods_pictrue comment '商品实体定价单图片描述';


create table hf_product_pictrue
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   product_id             int comment '物品id',
   hf_name              varchar(63) comment '图片名称',
   spec_desc            varchar(127) comment '图片说明',
   file_id              int comment '文件id',
   is_default           smallint default false comment '是否默认',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_product_pictrue comment '商品图片描述';


/*==============================================================*/
/* Table: hf_warehouse                                     */
/*==============================================================*/
create table hf_warehouse
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(63)  comment '仓库名称',
   hf_region            varchar(63) comment '所属区域',
   hf_desc              varchar(127) comment '仓库描述',
   bossId               int comment '商家id',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_warehouse comment '仓库描述';

/*==============================================================*/
/* Table: hf_goods_spec                                         */
/*==============================================================*/
create table hf_goods_spec
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   goods_id             int comment '商品实体定价单元ID',
   hf_spec_id           varchar(127) comment '商品规格id',
   category_spec_id     int comment '类目规格id',
   hf_value             varchar(127) comment '规格值',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_goods_spec comment '商品实体定价单元规格描述';

/*==============================================================*/
/* Table: hf_price                                              */
/*==============================================================*/
create table hf_price
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   googs_id             int comment '物品id',
   price_mode_id        int comment '价格模型id',
   sell_price           int comment '售卖价格(单位分)',
   is_use_price_mode    smallint default false comment '是否启用价格模型',
   hf_desc              varchar(127) comment '价格描述',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_price comment '定价描述';

/*==============================================================*/
/* Table: hf_price_mode                                         */
/*==============================================================*/
create table hf_price_mode
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(63) comment '价格模型名称',
   price                varchar(31) comment '价格调整方式',
   discount             varchar(127) comment '调整范围',
   hf_desc              varchar(127) comment '价格模型描述',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_price_mode comment '价格模型描述';

/*==============================================================*/
/* Table: hf_resp                                               */
/*==============================================================*/
create table hf_resp
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   googs_id             int comment '物品id',
   warehouse_id         int comment '仓库id',
   quantity             varchar(127) comment '库存量',
   hf_status            int comment '库存状态',
   hf_desc              varchar(127) comment '库存描述',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_resp comment '库存描述';

/*==============================================================*/
/* Table: hf_stone                                              */
/*==============================================================*/
create table hf_stone
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(127) comment '商铺名称',
   user_id              int comment '用户id',
   hf_desc              varchar(255) comment '商铺描述',
   boss_id              int comment '所属商家',
   create_time          datetime comment '注册时间',
   expire_time                 datetime comment '失效时间',
   hf_status            int comment '商铺状态',
   is_deleted                 smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_stone comment '商铺';

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(100) comment '商品名称',
   category_id          int comment '所属类目',
   brand_id             int comment '品牌id',
   product_desc         varchar(255) comment '商品描述',
   boss_id              int comment '所属商家',
   create_time          timestamp default now() comment '创建时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table product comment '商品';

/*==============================================================*/
/* Table: product_info                                          */
/*==============================================================*/
create table product_info
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   product_id           int comment '商品id',
   category_id          int comment '所属类目',
   hf_name              varchar(100) comment '属性名称',
   hf_value             varchar(255) comment '属性值',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   hf_remark            varchar(100) comment '备注',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table product_info comment '商品属性描述';

/*==============================================================*/
/* Table: product_instance                                      */
/*==============================================================*/
create table product_instance
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   product_id           int comment '商品id',
   category_id          int comment '所属类目',
   stone_id             int comment '商铺id',
   boss_id              int comment '所属商家',
   brand_id             int comment '品牌id',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table product_instance comment '商品实体';

/*==============================================================*/
/* Table: product_spec                                          */
/*==============================================================*/
create table product_spec
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   hf_name              varchar(63) comment '规格名称',
   category_spec_id     int comment '类目规格id',
   product_id           int comment '商品ID',
   spec_type            varchar(63) comment '规格类型',
   spec_unit            varchar(31) comment '规格单位',
   spec_value           varchar(127) comment '规格值',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table product_spec comment '商品规格';


