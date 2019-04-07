create table if not exists user_role_info (
	 id serial not null,
	 roleId varchar(50) not null,
	 username VARCHAR(50) not null,
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time timestamp ,
	 update_user VARCHAR(50),
	 update_time timestamp ,
	constraint user_role_info_primary_key_id primary key (id)
);

--序列授权
grant all on user_role_info_id_seq to chengxp;

--表说明
comment on table user_role_info is '用户角色对应关系表';
--表字段说明
comment on column user_role_info.id is '主键Id';
comment on column user_role_info.roleId is '角色id';
comment on column user_role_info.username is '角色名';
comment on column user_role_info.enable is '是否有效(1有效,0无效)';
--表赋予权限
grant all on user_role_info to chengxp;

--==========================================================================================
--==========================================================================================
--mysql
create table if not exists user_role_info (
	  id int not null AUTO_INCREMENT,
	 roleid varchar(50) not null,
	 username VARCHAR(50) not null,
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time datetime ,
	 update_user VARCHAR(50),
	 update_time datetime ,
	constraint user_role_info_primary_key_id primary key (id)
);

--序列授权
grant all on user_role_info to chengxp;

--表说明
ALTER TABLE user_role_info COMMENT = '用户角色对应关系表';
--表字段说明
ALTER table user_role_info MODIFY column roleid varchar(50) COMMENT '角色id';
ALTER table user_role_info MODIFY column username VARCHAR(50) COMMENT '角色名';
ALTER table user_role_info MODIFY column enable smallint COMMENT '是否有效(1有效,0无效)';


