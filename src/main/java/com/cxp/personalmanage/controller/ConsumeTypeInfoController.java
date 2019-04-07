package com.cxp.personalmanage.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.controller.base.BaseController;
import com.cxp.personalmanage.pojo.consumer.ConsumeTypeInfo;
import com.cxp.personalmanage.service.ConsumeTypeInfoService;

@RestController
@RequestMapping(value = "/consumeType")
public class ConsumeTypeInfoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumeTypeInfoController.class);
	
	 @Autowired
	 @Qualifier(value = "consumeTypeInfoService")
	 private ConsumeTypeInfoService consumeTypeInfoService;
	 
	 @SuppressWarnings("unchecked")
	 @RequestMapping(value="/findConsumeTypeInfoList")
	 public String findConsumeTypeInfoList(){
		 List<ConsumeTypeInfo> consumeTypeList = null;
		 Map<String, Object> initMap = InitMemoryConfig.initMap;
		 if(MapUtils.isNotEmpty(initMap)) {
			consumeTypeList = (List<ConsumeTypeInfo>) initMap.get(Constant.InitKey.CONSUMER_TYPE_LIST);
		 }else {
			 consumeTypeList = consumeTypeInfoService.findConsumeTypeInfo(null);
		 }
		 return buildSuccessResultInfo(consumeTypeList);
	 }
}
