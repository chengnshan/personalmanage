create table if not exists system_parameter_info(
	id serial not null,
	param_code varchar(50) not null,
	param_value varchar(512) not null,
	param_name varchar(128),
	enable smallint default 1,
	create_user VARCHAR(50),
	create_time timestamp ,
	update_user VARCHAR(50),
	update_time timestamp,
	constraint system_parameter_info_primary_key_id primary key (id)
);

--序列授权
grant all on system_parameter_info_id_seq to chengxp;

SELECT * from system_parameter_info;
--表说明
comment on table system_parameter_info is '系统参数信息表';
--表字段说明
comment on column system_parameter_info.id is '主键Id';
comment on column system_parameter_info.param_code is '参数代码';
comment on column system_parameter_info.param_value is '参数值';
comment on column system_parameter_info.param_name is '参数名称';
comment on column system_parameter_info.enable is '是否有效(1有效,0无效)';
comment on column system_parameter_info.create_user is '创建人';
comment on column system_parameter_info.create_time is '创建时间';
comment on column system_parameter_info.update_user is '修改人';
comment on column system_parameter_info.update_time is '修改时间';
--添加索引
create index index_system_parameter_info_param_code on system_parameter_info(param_code);
--表权限赋予
grant all on system_parameter_info to chengxp;

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('COSUME_SCHEDU_TIME','0 0 23 * * MON-FRI','消费定时任务频率',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('COSUME_SCHEDU_SETTING','[{"userName":"80003382","consumeType":"transport","consumeMoney":"5.85","remark":"上下班地铁","num":2}]',
'定时任务消费配置',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('SYNC_COSUME_DETAIL_SWITCH','Y','消费明细接口同步开关',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('SYNC_COSUME_DETAIL_URL','http://100.64.124.126/synch/synchConsumeDetail','消费明细同步接口url地址',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('MYSQL_DATA_SCHEDU_TIME','0 30 0 * * ?','定时同步mysql数据Cron表达式',1,now(),'system',now(),'system');


--==========================================================================================
--==========================================================================================
-- mysql创建表
create table if not exists system_parameter_info(
	id int not null AUTO_INCREMENT,
	param_code varchar(50) not null,
	param_value varchar(512) not null,
	param_name varchar(128),
	enable smallint default 1,
	create_user VARCHAR(50),
	create_time datetime ,
	update_user VARCHAR(50),
	update_time datetime,
	constraint system_parameter_info_primary_key_id primary key (id)
);

SELECT * from system_parameter_info;
-- 表说明
ALTER TABLE system_parameter_info COMMENT = '系统参数信息表';
-- 表字段说明
ALTER TABLE system_parameter_info MODIFY column param_code varchar(50) COMMENT '参数代码';
ALTER TABLE system_parameter_info MODIFY column param_value varchar(512) COMMENT '参数值';
ALTER TABLE system_parameter_info MODIFY column param_name varchar(128) COMMENT '参数名称';
ALTER TABLE system_parameter_info MODIFY column enable smallint COMMENT '是否有效(1有效,0无效)';
ALTER TABLE system_parameter_info MODIFY column create_user VARCHAR(50) COMMENT '创建人';
ALTER TABLE system_parameter_info MODIFY column create_time date COMMENT '创建时间';
ALTER TABLE system_parameter_info MODIFY column update_user VARCHAR(50) COMMENT '修改人';
ALTER TABLE system_parameter_info MODIFY column update_time date COMMENT '修改时间';
-- 添加索引
create index index_system_parameter_info_param_code on system_parameter_info(param_code);
-- 表权限赋予
grant all on system_parameter_info to chengxp;

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('COSUME_SCHEDU_TIME','0 0 23 1-5 * ?','消费定时任务频率',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('COSUME_SCHEDU_SETTING','[{"userName":"80003382","consumeType":"transport","consumeMoney":"5.85","remark":"上下班地铁","num":2}]',
'定时任务消费配置',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('SYNC_COSUME_DETAIL_SWITCH','Y','消费明细接口同步开关',1,now(),'system',now(),'system');

insert into system_parameter_info (param_code,param_value,param_name,enable,create_time,create_user,update_time,update_user ) values 
('SYNC_COSUME_DETAIL_URL','http://100.64.124.126/synch/synchConsumeDetail','消费明细同步接口url地址',1,now(),'system',now(),'system');
