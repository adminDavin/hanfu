/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/10/13 20:15:20                          */
/*==============================================================*/


drop table if exists hf_order_logistics;

drop table if exists hf_orders;

drop table if exists hf_orders_detail;
drop table if exists hf_order_status;


/*==============================================================*/
/* Table: hf_order_logistics                                    */
/*==============================================================*/
create table hf_order_logistics
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   orders_id            int comment '订单id',
   order_detail_id      int comment '订单详情id',
   user_id             int comment '用户id',
   user_address_id      int comment '用户地址id',
   googs_id             int comment '物品id',
   logistics_order_name varchar(63) comment '物流名称',
   resp_id              int comment '仓库id',
   logistics_orders_id  varchar(63) comment '物流订单号',
   logistics_company    varchar(127) comment '物流公司',
   hf_desc              varchar(127) comment '物流描述',
   create_time          timestamp default now() comment  '添加时间',
   modify_time          timestamp default now() comment  '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_order_logistics comment '物流详情';

/*==============================================================*/
/* Table: hf_orders                                             */
/*==============================================================*/
create table hf_orders
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   user_id              int comment '用户id',
   pay_status           int comment '支付状态',
   order_type           varchar(63) comment '订单类型',
   amount               int comment '支付金额',
   pay_method_type      int comment '支付方式类型',
   hf_memo              varchar(127) comment '支付附言',
   hf_remark            varchar(127) comment '备注',
   pay_method_name      varchar(127) comment '支付方式名称',
   create_time          timestamp default now() comment  '添加时间',
   modify_time          timestamp default now() comment  '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_orders comment '订单描述';

/*==============================================================*/
/* Table: hf_orders_detail                                      */
/*==============================================================*/
create table hf_orders_detail
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   orders_id            int comment '订单id',
   resp_id              int comment '仓库id',
   order_detail_status  varchar(63) comment '订单详情状态',
   googs_id             int comment '物品id',
   hf_tax               int comment '税金(单位分)',
   purchase_price       int comment '购买价格(单位分)',
   purchase_quantity    int comment '售卖数量',
   Distribution         varchar(127) comment '配送方式',
   hf_desc              varchar(127) comment '订单描述',
   create_time          timestamp default now() comment  '添加时间',
   modify_time          timestamp default now() comment  '修改时间',
   last_modifier        varchar(15) comment '最后一次修改人',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table hf_orders_detail comment '订单详情';


CREATE TABLE `hf_order_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `hf_name` varchar(100) DEFAULT NULL COMMENT '订单状态名称',
  `hf_status` int(11) DEFAULT NULL COMMENT '订单状态',
  `hf_desc` varchar(255) DEFAULT NULL COMMENT '订单状态描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` smallint(6) DEFAULT '0' COMMENT '是否失效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='订单状态';