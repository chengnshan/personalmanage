create table if not exists userInfo (
	 id serial not null,
	 username varchar(50) not null,
	 PASSWORD VARCHAR(128) not null,
	 salt VARCHAR(50),
	 realName VARCHAR(50),
	 image_url varchar(255),
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time timestamp ,
	 update_user VARCHAR(50),
	 update_time timestamp ,
	constraint primary_key_id primary key (id)
);
SELECT * from userinfo;

--序列授权
grant all on userinfo_id_seq to chengxp;

--表说明
comment on table userInfo is '用户信息表';
--表字段说明
comment on column userInfo.id is '主键Id';
comment on column userInfo.username is '用户名';
comment on column userInfo.PASSWORD is '密码';
comment on column userInfo.salt is '盐';
comment on column userInfo.realName is '真实名字';
comment on column userInfo.image_url is '图片路径';
comment on column userInfo.enable is '是否有效(1有效,0无效)';
comment on column userInfo.create_user is '创建人';
comment on column userInfo.create_time is '创建时间';
comment on column userInfo.update_user is '修改人';
comment on column userInfo.update_time is '修改时间';
--添加索引
create index index_userinfo_username on userinfo(username);
--添加唯一约束
alter table userinfo add CONSTRAINT unique_userinfo_username UNIQUE(username);
--表权限赋予
grant all on userInfo to chengxp;


--==========================================================================================
--==========================================================================================
--mysql的sql脚本

create table if not exists userinfo (
	 id int not null AUTO_INCREMENT,
	 username varchar(50) not null,
	 PASSWORD VARCHAR(128) not null,
	 salt VARCHAR(50),
	 realName VARCHAR(50),
	 image_url varchar(255),
	 enable smallint default 1 ,
	 create_user VARCHAR(50),
	 create_time datetime ,
	 update_user VARCHAR(50),
	 update_time datetime,
	 PRIMARY  KEY  (id)
);
SELECT * from userinfo;

--序列授权
grant all on userinfo to chengxp;

--表说明
ALTER TABLE  userinfo COMMENT = '用户信息表';
--表字段说明
ALTER table userinfo MODIFY column  username varchar(50) COMMENT '用户名';
ALTER table userinfo MODIFY column `PASSWORD` VARCHAR(128) COMMENT '密码';
ALTER table userinfo MODIFY column salt VARCHAR(50), COMMENT '盐';
ALTER table userinfo MODIFY column realName VARCHAR(50) COMMENT '真实名字';
ALTER table userinfo MODIFY column image_url varchar(255), COMMENT '图片路径';
ALTER table userinfo MODIFY column `enable` smallint COMMENT '是否有效(1有效,0无效)';
ALTER table userinfo MODIFY column create_user VARCHAR(50) COMMENT '创建人';
ALTER table userinfo MODIFY column create_time date COMMENT '创建时间';
ALTER table userinfo MODIFY column update_user VARCHAR(50) COMMENT '修改人';
ALTER table userinfo MODIFY column update_time date COMMENT '修改时间';
--添加索引
create index index_userinfo_username on userinfo(username);
--添加唯一约束
alter table userinfo add CONSTRAINT unique_userinfo_username UNIQUE(username);
