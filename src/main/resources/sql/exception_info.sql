create table if not exists exception_info  (
    `id` bigint(20) not null auto_increment comment '主键id',
    `exception_position` varchar(255) comment '异常发生位置',
    `create_time` timestamp comment '创建时间',
    `exception_name` varchar(255) comment '异常名称',
    `exception_detail` text default null comment '异常信息',
	PRIMARY key (`id`)
) engine=InnoDB default charset='utf8' comment='异常信息堆栈记录表' ;

grant all on exception_info to chengxp;