package com.cxp.personalmanage.mapper.postgresql;

import java.util.List;

import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import org.apache.ibatis.annotations.Param;

public interface ConsumeTypeInfoMapper {

	/**
	 * 根据属性查询消费类型
	 * @param consumeTypeInfo
	 * @return
	 */
	 List<ConsumeTypeInfo> findConsumeTypeInfo(ConsumeTypeInfo consumeTypeInfo);
	
	/**
	 * 根据消费Id查询
	 * @param consumeId
	 * @return
	 */
	ConsumeTypeInfo getConsumerTypeById(@Param("consumeId") String consumeId);
}
