CREATE table if NOT EXISTS consume_type_info(
	id serial not null,
	consume_id VARCHAR(30) not null,
	consume_name  VARCHAR(100) ,
	remark varchar(255),
	import_no int,
	constraint consume_type_primary_key_id primary key (id)
);
--序列授权
grant all on consume_type_info_id_seq to chengxp;

--表说明
comment on table consume_type_info is '消费类型表';
--表字段说明
comment on column consume_type_info.id is '主键Id';
comment on column consume_type_info.consume_id is '消费类型id';
comment on column consume_type_info.consume_name is '消费类型名称';
comment on column consume_type_info.remark is '消费备注';
comment on column consume_type_info.import_no is '导入类型对应的数字代号';
--添加索引
create index index_consume_type_info_consumeId on consume_type_info(consume_id);
--表赋予权限
grant all on consume_type_info to chengxp;
--初始化数据
insert into consume_type_info (consume_id,consume_name,remark) values ('transPort','交通费用','上下班或者乘坐交通工具');
insert into consume_type_info (consume_id,consume_name,remark) values ('lifeCost','生活费用','日常吃饭喝水');
insert into consume_type_info (consume_id,consume_name,remark) values ('otherCost','其它费用','其它费用');

--==========================================================================================
--==========================================================================================
--mysql的sql脚本
CREATE table if NOT EXISTS consume_type_info(
	id int not null AUTO_INCREMENT,
	consume_id VARCHAR(30) not null,
	consume_name  VARCHAR(100) ,
	remark varchar(255),
	import_no int,
	constraint consume_type_primary_key_id primary key (id)
);

--表授权
grant all on consume_type_info to chengxp;

--表说明
ALTER TABLE consume_type_info COMMENT = '消费类型表';
--表字段说明
ALTER table consume_type_info MODIFY column consume_id varchar(30) COMMENT '消费类型id';
ALTER table consume_type_info MODIFY column consume_name varchar(100) COMMENT  '消费类型名称';
ALTER table consume_type_info MODIFY column remark varchar(255) COMMENT  '消费备注';
ALTER table consume_type_info MODIFY column import_no int COMMENT  '导入类型对应的数字代号';
--添加索引
create index index_consume_type_info_consumeId on consume_type_info(consume_id);

