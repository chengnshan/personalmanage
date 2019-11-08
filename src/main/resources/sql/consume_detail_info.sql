CREATE table if NOT EXISTS consume_detail_info(
	id serial not null,
	user_name varchar(30) not null,
	consume_time date not null,
	consume_money numeric(9,4) not null,
	consume_id VARCHAR(30),
	remark varchar(255),
	create_time TIMESTAMP ,
	create_user VARCHAR(30),
	update_time TIMESTAMP ,
	update_user VARCHAR(30),
	channel_code varchar(20),
	constraint consume_detail_info_primary_key_id primary key (id)
);

--表授权
grant all on consume_detail_info to chengxp;

--表说明
comment on table consume_detail_info is '消费类型表';
--表字段说明
comment on column consume_detail_info.id is '主键Id';
comment on column consume_detail_info.user_name is '消费人';
comment on column consume_detail_info.consume_time is '消费时间';
comment on column consume_detail_info.consume_money is '消费金额';
comment on column consume_detail_info.consume_id is '消费类型Id';
comment on column consume_detail_info.remark is '消费备注';
--添加索引
CREATE INDEX index_consume_detail_info_user_name on consume_detail_info(user_name);
--表赋予权限
grant all on consume_detail_info to chengxp;
--添加消费渠道编码
alter table consume_detail_info add column channel_code varchar(30);

--==========================================================================================
--==========================================================================================
-- mysql表创建
CREATE table if NOT EXISTS consume_detail_info(
	id int not null AUTO_INCREMENT,
	user_name varchar(30) not null,
	consume_time date not null,
	consume_money numeric(9,4) not null,
	consume_id VARCHAR(30),
	remark varchar(255),
	create_time datetime ,
	create_user VARCHAR(30),
	update_time datetime ,
	update_user VARCHAR(30),
	channel_code varchar(20),
	constraint consume_detail_info_primary_key_id primary key (id)
);
-- 序列授权
grant all on consume_detail_info to chengxp;

-- 表说明
ALTER table consume_detail_info COMMENT = '消费类型表';
-- 表字段说明
ALTER table consume_detail_info MODIFY column user_name varchar(30) COMMENT '消费人';
ALTER table consume_detail_info MODIFY column consume_time date COMMENT '消费时间';
ALTER table consume_detail_info MODIFY column consume_money numeric(9,4) COMMENT '消费金额';
ALTER table consume_detail_info MODIFY column consume_id VARCHAR(30) COMMENT '消费类型Id';
ALTER table consume_detail_info MODIFY column remark varchar(255) COMMENT '消费备注';
-- 添加索引
CREATE INDEX index_consume_detail_info_user_name on consume_detail_info(user_name);



