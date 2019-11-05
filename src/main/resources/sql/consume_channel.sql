create table if not exists consume_channel_info(
  id serial not null,
  channel_code varchar(30) not null,
  channel_name varchar(30) not null,
  channel_remark varchar(255),
  constraint consume_channel_info_primary_key_id primary key (id)
);

--序列授权
grant all on consume_channel_info_id_seq to chengxp;
grant all on consume_channel_info to chengxp;
--表说明
comment on table consume_channel_info is '消费渠道表';
--表字段说明
comment on column consume_channel_info.id is '主键Id';
comment on column consume_channel_info.channel_code is '渠道数字代号';
comment on column consume_channel_info.channel_name is '渠道名称';
comment on column consume_channel_info.channel_remark is '渠道备注';
--创建索引
create index consume_channel_info_channel_code on consume_channel_info(channel_code);
--添加基础数据
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C001','花呗','支付宝花呗支付');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C002','支付宝余额','支付宝余额');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C003','招行信用卡7298','招行信用卡7298');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C004','平安信用卡1273','平安信用卡1273');

--==========================================================================================
--==========================================================================================
--  mysql的sql脚本
create table if not exists consume_channel_info(
  id int not null AUTO_INCREMENT,
  channel_code varchar(30) not null,
  channel_name varchar(30) not null,
  channel_remark varchar(255),
  constraint consume_channel_info_primary_key_id primary key (id)
);

ALTER table consume_channel_info COMMENT  =  '消费渠道表';
-- 表字段说明
ALTER table consume_channel_info  MODIFY column id int COMMENT  '主键Id';
ALTER table consume_channel_info  MODIFY column channel_code varchar(30) COMMENT '渠道数字代号';
ALTER table consume_channel_info  MODIFY column channel_name varchar(30) COMMENT '渠道名称';
ALTER table consume_channel_info  MODIFY column channel_remark varchar(255) COMMENT '渠道备注';
-- 创建索引
create index consume_channel_info_channel_code on consume_channel_info(channel_code);
-- 添加基础数据
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C001','花呗','支付宝花呗支付');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C002','支付宝余额','支付宝余额');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C003','招行信用卡7298','招行信用卡7298');
insert into consume_channel_info (channel_code,channel_name,channel_remark) VALUES ('C004','平安信用卡1273','平安信用卡1273');