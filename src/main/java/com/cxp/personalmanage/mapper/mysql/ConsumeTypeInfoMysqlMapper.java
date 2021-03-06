package com.cxp.personalmanage.mapper.mysql;

import java.util.List;

import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;

public interface ConsumeTypeInfoMysqlMapper {

	/**
	 * 根据属性查询消费类型
	 * @param consumeTypeInfo
	 * @return
	 */
	public List<ConsumeTypeInfo> findConsumeTypeInfo(ConsumeTypeInfo consumeTypeInfo);
	
	/**
	 * 根据消费Id查询
	 * @param consumeId
	 * @return
	 */
	public ConsumeTypeInfo getConsumerTypeById(String consumeId);
}
