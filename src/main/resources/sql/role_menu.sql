create table if not exists role_menu_info (
	 id serial not null,
	 roleId varchar(50) not null,
	 menuId VARCHAR(128) ,
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time timestamp ,
	 update_user VARCHAR(50),
	 update_time timestamp ,
	constraint role_menu_info_primary_key_id primary key (id)
);

--序列授权
grant all on role_menu_info_id_seq to chengxp;

--表说明
comment on table role_menu_info is '角色菜单对应表';
--表字段说明
comment on column role_menu_info.id is '主键Id';
comment on column role_menu_info.roleId is '角色id';
comment on column role_menu_info.menuId is '菜单id';
comment on column role_menu_info.enable is '是否有效(1有效,0无效)';


--添加索引
create index index_role_menu_info_roleId on role_menu_info(roleId);

insert into role_menu_info (roleId,menuId) values ('user','blog');
insert into role_menu_info (roleId,menuId) values ('user','signup');
insert into role_menu_info (roleId,menuId) values ('user','register');
insert into role_menu_info (roleId,menuId) values ('admin','timeline');
insert into role_menu_info (roleId,menuId) values ('admin','forms');
insert into role_menu_info (roleId,menuId) values ('admin','typography');
insert into role_menu_info (roleId,menuId) values ('admin','bootstrap-elements');
insert into role_menu_info (roleId,menuId) values ('admin','bootstrap-grid');
insert into role_menu_info (roleId,menuId) values ('admin','onlieUserList');
--表赋予权限
grant all on role_menu_info to chengxp;

--==========================================================================================
--==========================================================================================
--mysql创建表
create table if not exists role_menu_info (
	 id int not null AUTO_INCREMENT,
	 roleId varchar(50) not null,
	 menuId VARCHAR(128) ,
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time datetime ,
	 update_user VARCHAR(50),
	 update_time datetime ,
	constraint role_menu_info_primary_key_id primary key (id)
);

grant all on role_menu_info to chengxp;
--表说明
ALTER table role_menu_info COMMENT = '角色菜单对应表';
--表字段说明
ALTER table role_menu_info MODIFY column roleId varchar(50) COMMENT '角色id';
ALTER table role_menu_info MODIFY column menuId VARCHAR(128) COMMENT '菜单id';
ALTER table role_menu_info MODIFY column enable smallint COMMENT '是否有效(1有效,0无效)';

