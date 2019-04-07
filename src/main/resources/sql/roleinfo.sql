create table if not exists roleinfo (
	 id serial not null,
	 roleId varchar(50) not null,
	 roleName VARCHAR(50),
	 enable smallint default 1 ,
	 description VARCHAR(200),
	 create_user VARCHAR(50),
	 create_time timestamp ,
	 update_user VARCHAR(50),
	 update_time timestamp ,
	constraint roleinfo_primary_key_id primary key (id)
);

--序列授权
grant all on roleinfo_id_seq to chengxp;

SELECT * from roleinfo;
--表说明
comment on table roleinfo is '角色信息表';
--表字段说明
comment on column roleinfo.id is '主键Id';
comment on column roleinfo.roleId is '角色id';
comment on column userInfo.roleName is '角色名';
comment on column roleinfo.description is '角色描述';
comment on column roleinfo.create_user is '创建人';
comment on column roleinfo.create_time is '创建时间';
comment on column roleinfo.update_user is '修改人';
comment on column roleinfo.update_time is '修改时间';
comment on column roleinfo.enable is '是否有效(1有效,0无效)';
--添加索引
create index index_roleinfo_roleId on roleinfo(roleId);
--表赋予权限
grant all on roleinfo to chengxp;


--==========================================================================================
--==========================================================================================
--mysql创建表
create table if not exists roleinfo (
	 id int not null AUTO_INCREMENT,
	 roleId varchar(50) not null,
	 roleName VARCHAR(50),
	 enable smallint default 1 ,
	 description VARCHAR(200),
	 create_user VARCHAR(50),
	 create_time datetime ,
	 update_user VARCHAR(50),
	 update_time datetime ,
	constraint roleinfo_primary_key_id primary key (id)
);

grant all on roleinfo to chengxp;
--表说明
ALTER table roleinfo COMMENT = '角色信息表';
--表字段说明
ALTER table roleinfo  MODIFY column roleId varchar(50) COMMENT '角色id';
ALTER table roleinfo  MODIFY column roleName varchar(50) COMMENT '角色名';
ALTER table roleinfo  MODIFY column description  VARCHAR(200) COMMENT '角色描述';
ALTER table roleinfo  MODIFY column create_user VARCHAR(50) COMMENT '创建人';
ALTER table roleinfo  MODIFY column create_time date COMMENT '创建时间';
ALTER table roleinfo  MODIFY column update_user VARCHAR(50)COMMENT '修改人';
ALTER table roleinfo  MODIFY column update_time date COMMENT '修改时间';
ALTER table roleinfo  MODIFY column enable smallint COMMENT '是否有效(1有效,0无效)';



