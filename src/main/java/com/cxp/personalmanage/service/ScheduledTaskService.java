package com.cxp.personalmanage.service;

public interface ScheduledTaskService {

	/**
	 * 同步消费信息到Mysql数据库
	 * @return
	 */
	int sysnMysqlData() ;
	
	/**
	 *  同步消费明细信息
	 */
	void saveTransportConsume() ;
}
