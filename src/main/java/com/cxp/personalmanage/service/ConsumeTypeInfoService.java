package com.cxp.personalmanage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;

public interface ConsumeTypeInfoService extends IService<ConsumeTypeInfo> {

	/**
	 * 根据属性查询消费类型
	 * @param consumeTypeInfo
	 * @return
	 */
	public List<ConsumeTypeInfo> findConsumeTypeInfo(ConsumeTypeInfo consumeTypeInfo);
	
	/**
	 * 根据表格中的数字判定类型
	 * @param num
	 * @return
	 */
	public ConsumeTypeInfo getConsumeTypeByNum(Integer num);
}
