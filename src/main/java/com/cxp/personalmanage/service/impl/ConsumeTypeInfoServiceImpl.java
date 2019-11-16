package com.cxp.personalmanage.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.mapper.postgresql.ConsumeTypeInfoMapper;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import com.cxp.personalmanage.service.ConsumeTypeInfoService;

@Service(value = "consumeTypeInfoService")
public class ConsumeTypeInfoServiceImpl extends ServiceImpl<ConsumeTypeInfoMapper, ConsumeTypeInfo>
		implements ConsumeTypeInfoService {

	@Autowired
	private ConsumeTypeInfoMapper consumeTypeInfoMapper;

	@Override
	public List<ConsumeTypeInfo> findConsumeTypeInfo(ConsumeTypeInfo consumeTypeInfo) {
		return consumeTypeInfoMapper.findConsumeTypeInfo(consumeTypeInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsumeTypeInfo getConsumeTypeByNum(Integer num) {
		List<ConsumeTypeInfo> consumeTypeList = null;
		Map<String, Object> initMap = InitMemoryConfig.initMap;
		if (MapUtils.isNotEmpty(initMap)) {
			consumeTypeList = (List<ConsumeTypeInfo>) initMap.get(Constant.InitKey.CONSUMER_TYPE_LIST);
		} else {
			consumeTypeList = findConsumeTypeInfo(null);
		}

		if (CollectionUtils.isNotEmpty(consumeTypeList)) {
			for (ConsumeTypeInfo consumeTypeInfo : consumeTypeList) {
				if ( null !=consumeTypeInfo.getImportNo() && num.equals(consumeTypeInfo.getImportNo())) {
					return consumeTypeInfo;
				}
			}
		}
		return null;
	}

}
