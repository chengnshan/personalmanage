create table if not exists menu_info (
	 id serial not null,
	 menuid VARCHAR(60) not null,
	 menuName varchar(50) ,
	 menuUrl varchar(255) not NULL ,
	 sort int ,
	 classStyle varchar(50),
	 remark varchar(255) ,
	 parent_menuid varchar(100) ,
	 enable smallint default 1 ,
	 menu_level int ,
	constraint menu_info_primary_key_id primary key (id)
);

--序列授权
grant all on menu_info_id_seq to chengxp;

--表说明
comment on table menu_info is '菜单信息表';
--表字段说明
comment on column menu_info.id is '主键Id';
comment on column menu_info.menuid is '菜单id';
comment on column menu_info.menuName is '菜单名';
comment on column menu_info.menuUrl is '菜单路径';
comment on column menu_info.sort is '菜单序号';
comment on column menu_info.remark is '菜单备注';
comment on column menu_info.parent_menuid is '父菜单id';
comment on column menu_info.enable is '是否有效(1有效,0无效)';
comment on column menu_info.menu_level is '菜单级别';

--添加索引
create index index_menu_info_menuId on menu_info(menuId);
--添加唯一约束
alter table menu_info add CONSTRAINT unique_menu_info_menuId UNIQUE(menuId);
--表赋予权限
grant all on menu_info to chengxp;

--==========================================================================================
--==========================================================================================

create table if not exists menu_info (
	 id int not null AUTO_INCREMENT,
	 menuid VARCHAR(60) not null,
	 menuName varchar(50) ,
	 menuUrl varchar(255) not NULL ,
	 sort int ,
	 classStyle varchar(50),
	 remark varchar(255) ,
	 parent_menuid varchar(100) ,
	 enable smallint default 1 ,
	 menu_level int ,
	constraint menu_info_primary_key_id primary key (id)
);

grant all on menu_info to chengxp;
-- 表说明
ALTER table menu_info COMMENT = '菜单信息表';
-- 表字段说明
ALTER table menu_info MODIFY column menuid VARCHAR(60) COMMENT '菜单id';
ALTER table menu_info MODIFY column menuName varchar(50) COMMENT '菜单名';
ALTER table menu_info MODIFY column menuUrl varchar(255) COMMENT '菜单路径';
ALTER table menu_info MODIFY column sort int COMMENT '菜单序号';
ALTER table menu_info MODIFY column classStyle varchar(50) COMMENT '菜单序号';
ALTER table menu_info MODIFY column remark varchar(255) COMMENT '菜单备注';
ALTER table menu_info MODIFY column parent_menuid varchar(100) COMMENT '父菜单id';
ALTER table menu_info MODIFY column enable smallint COMMENT '是否有效(1有效,0无效)';
ALTER table menu_info MODIFY column menu_level int COMMENT '菜单级别';

-- 添加索引
create index index_menu_info_menuId on menu_info(menuId);
-- 添加唯一约束
alter table menu_info add CONSTRAINT unique_menu_info_menuId UNIQUE(menuId);


