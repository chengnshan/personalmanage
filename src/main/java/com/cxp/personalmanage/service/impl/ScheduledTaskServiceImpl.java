package com.cxp.personalmanage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.mapper.mysql.ConsumeDetailInfoMysqlMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.ScheduledTaskService;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import com.cxp.personalmanage.utils.JackJsonUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(value="scheduledTaskService")
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskServiceImpl.class);

	@Autowired
	private ConsumeDetailInfoService consumeDetailInfoService;

	@Autowired
	private ConsumeDetailInfoMysqlMapper consumeDetailInfoMysqlMapper;
	
	@Autowired
	private SystemParameterInfoService systemParameterInfoService;

	public static final int BATCHNUM = 5000;

	@Override
	public int sysnMysqlData() {
		try {
			Map<String, Object> map = new HashMap<>();
			int consumeDetailInfoCount = consumeDetailInfoService.findConsumeDetailInfoCountByMap(null);
			int a = consumeDetailInfoCount % BATCHNUM == 0 ? consumeDetailInfoCount % BATCHNUM
					: (consumeDetailInfoCount / BATCHNUM + 1);
			for (int i = 0; i < a; i++) {
				map.put("pageRow", (i + 1 - 1) * BATCHNUM);
				map.put("pageSize", BATCHNUM);
				// 获取5000条数据
				List<ConsumeDetailInfo> findConsumeDetailInfoListByMap = consumeDetailInfoService
						.findConsumeDetailInfoListByMap(map);
				ListIterator<ConsumeDetailInfo> listIterator = findConsumeDetailInfoListByMap.listIterator();
				while (listIterator.hasNext()) {
					ConsumeDetailInfo next = listIterator.next();
					// 数据库查询这条记录是否存在,不存在则添加到Mysql表中
					next.setId(null);
					List<ConsumeDetailInfo> mysqlConsumeDetail = consumeDetailInfoMysqlMapper
							.findConsumeDetailInfoListByObj(next);
					if (CollectionUtils.isNotEmpty(mysqlConsumeDetail)) {
						listIterator.remove();
					}
				}

				if (CollectionUtils.isNotEmpty(findConsumeDetailInfoListByMap)) {
					return consumeDetailInfoMysqlMapper.batchInsert(findConsumeDetailInfoListByMap);
				}
			}
		} catch (Exception e) {
			logger.error("同步consumeDetailInfo表中数据到mysql异常！  " + e.getMessage());
		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	@Transactional(value="txPrimaryManager",rollbackFor= {Exception.class,RuntimeException.class},propagation=Propagation.REQUIRED)
	@Override
	public void saveTransportConsume() {

		Map<String, Object> map = null;
		List<Object> list = null;
		ConsumeDetailInfo cdi = null;
		List<ConsumeDetailInfo> saveList = new ArrayList<>();

		List<SystemParameterInfo> parameterInfoList = null;
		String paramValue = InitMemoryConfig.getParamValue(Constant.ScheduConsume.CONSUME_CODE);
		if (StringUtils.isBlank(StringUtil.conveterStr(paramValue))){
			Map<String, Object> paramMap = new HashMap<>();
			// 获取设置的参数值
			paramMap.put("param_code", Constant.ScheduConsume.CONSUME_CODE);
			parameterInfoList = systemParameterInfoService.getParameterInfoByCode(paramMap);
			if (CollectionUtils.isNotEmpty(parameterInfoList)) {
				SystemParameterInfo param = parameterInfoList.get(0);
				paramValue = param.getParam_value();
			}
		}

		// 把JSON字符串转换成List集合
		ObjectMapper mapper = JackJsonUtil.getInstance();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		try {
			list = JackJsonUtil.json2ListRecursion(paramValue, mapper);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// 如果集合不为空
		if (CollectionUtils.isNotEmpty(list)) {
			Date currentDate = new Date();
			String channelCode = null;
			for (Object obj : list) {
				// 强制转换为Map
				map = (Map<String, Object>) obj;
				if (MapUtils.isNotEmpty(map)) {
					// 获取执行次数
					Integer num = (Integer) map.get("num");
					if (num != null && num > 0) {
						for (int i = 0; i < num; i++) {
							cdi = new ConsumeDetailInfo();
							cdi.setConsume_time(currentDate);

							BigDecimal big=new BigDecimal(map.get("consumeMoney")+"");
							cdi.setConsume_money(big);
							cdi.setConsumeId((String) map.get("consumeType"));
							cdi.setRemark((String) map.get("remark"));
							cdi.setCreate_user(Constant.ScheduConsume.ADMIN);
							cdi.setUpdate_user(Constant.ScheduConsume.ADMIN);
							cdi.setUserName((String) map.get("userName"));
							channelCode = StringUtil.conveterStr(String.valueOf(map.get("channelCode")));
							cdi.setChannel_code(StringUtils.isNotBlank(channelCode) ? channelCode : "");
							saveList.add(cdi);
						}
					} else {
						cdi = new ConsumeDetailInfo();
						cdi.setConsume_time(currentDate);

						BigDecimal big=new BigDecimal(map.get("consumeMoney")+"");
						cdi.setConsume_money(big);
						cdi.setConsumeId((String) map.get("consumeType"));
						cdi.setRemark((String) map.get("remark"));
						cdi.setCreate_user(Constant.ScheduConsume.ADMIN);
						cdi.setUpdate_user(Constant.ScheduConsume.ADMIN);
						cdi.setUserName((String) map.get("userName"));
						saveList.add(cdi);
					}
				}
			}
		}
		// 批量插入
		if (CollectionUtils.isNotEmpty(saveList)) {
			consumeDetailInfoService.batchInsertDetailInfo(saveList);
		}


	}

}
