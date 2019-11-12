/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/11/11 6:43:49                           */
/*==============================================================*/


drop table if exists activiti_strategy;

drop table if exists activity_strategy_instance;

drop table if exists activiti_rule_instance;

drop table if exists strategy_rule;

drop table if exists activity;

drop table if exists strategy_rule_relate;

/*==============================================================*/
/* Table: activiti_strategy                                     */
/*==============================================================*/
create table activiti_strategy
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   strategy_name        varchar(63) comment '策略名',
   strategy_desc        varchar(1023) comment '策略描述',
   strategy_type        varchar(31) comment '策略类型',
   strategy_status      varchar(7) comment '策略状态',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table activiti_strategy comment '活动策略表';

/*==============================================================*/
/* Table: activity_strategy_instance                            */
/*==============================================================*/
create table activity_strategy_instance
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   activity_id          int comment '活动id',
   rule_id              int comment '规则id',
   rule_name            varchar(31) comment '规则名称',
   rule_desc            varchar(1023) comment '规则描述',
   rule_value           varchar(511) comment '规则值',
   rule_value_type      varchar(31) comment '规则值类型',
   rule_status          varchar(7) comment '规则状态',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table activity_strategy_instance comment '活动策略实体表';

/*==============================================================*/
/* Table: rule_value_desc                                       */
/*==============================================================*/
create table activiti_rule_instance
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   activity_id          int comment '活动id',
   rule_id              int comment '规则id',
   rule_instance_id     int comment '规则实体id',
   is_relate_user       bool comment '是否关联用户',
   user_id              int comment '用户id',
   user_ticket_count          int comment '用户所持有的票数',
   user_score           int comment '用户得分',
   is_elected           boolean comment '是否是被选举者', 
   rule_instance_value  varchar(511) comment '规则实体值',
   remarks              varchar(1023) comment '备注',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table activiti_rule_instance comment '活动规则值描述表';

/*==============================================================*/
/* Table: strategy_rule                                         */
/*==============================================================*/
create table strategy_rule
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   rule_name            varchar(64) comment 'rule_name',
   rule_desc            varchar(1023) comment 'rule_desc',
   rule_status          varchar(7) comment 'rule_status',
   rule_type            varchar(31) comment 'rule_type',
   ruel_value_type      varchar(31) comment 'ruel_value_type',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table strategy_rule comment '策略规则表';

/*==============================================================*/
/* Table: 活动表                                                   */
/*==============================================================*/
create table activity
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   activity_name                 varchar(127) comment 'name',
   activity_desc        varchar(1024) comment 'activity_desc',
   activiy_type         varchar(31) comment 'activiy_type',
   activity_status      varchar(15) comment '活动状态',
   activity_result      varchar(1023) comment '活动结果',
   strategy_id          int comment '策略id',
   user_id          int comment '活动发起者的用户id',
   is_timing_start          smallint default false comment '是否定时开启活动',
   start_time          timestamp default now()  comment '活动开启时间',
   end_time          timestamp default now()  comment '活动结束时间',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table activity comment '活动表';

/*==============================================================*/
/* Table: 策略规则关系表                                               */
/*==============================================================*/
create table strategy_rule_relate
(
   id                   int not null AUTO_INCREMENT comment '序列号',
   strategy_id          int comment '策略id',
   strategy_rule_id     int comment '策略规则id',
   is_used              bool comment '是否启用规则',
   create_time          timestamp default now() comment '添加时间',
   modify_time          timestamp default now() comment '修改时间',
   is_deleted           smallint default false comment '是否失效',
   primary key (id)
);

alter table strategy_rule_relate comment '策略规则关系表';
