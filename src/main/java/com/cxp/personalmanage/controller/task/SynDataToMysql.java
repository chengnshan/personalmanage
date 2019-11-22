package com.cxp.personalmanage.controller.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cxp.personalmanage.utils.ExceptionInfoUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.mapper.mysql.ConsumeDetailInfoMysqlMapper;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.pojo.consumer.ConsumeDetailInfo;
import com.cxp.personalmanage.service.ConsumeDetailInfoService;
import com.cxp.personalmanage.service.ScheduledTaskService;
import com.cxp.personalmanage.service.SystemParameterInfoService;

//@RestController
//@RequestMapping(value = "/tasks")
public class SynDataToMysql implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(SynDataToMysql.class);
	
	@Autowired
	private SystemParameterInfoService systemParameterInfoService;
	
	@Autowired
	@Qualifier(value="scheduledTaskService")
	private ScheduledTaskService scheduledTaskService;

	// 每天 0点30分执行执行一次
	private static String expression = "0 0 1 * * ?";

	@RequestMapping(value = "/changeSynMysqlDataExpression")
	public String synMysqlDataTask() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("param_code", Constant.ScheduConsume.MYSQL_DATA_SCHEDU_TIME);
		List<SystemParameterInfo> list = systemParameterInfoService.getParameterInfoByCode(paramMap);
		if (CollectionUtils.isNotEmpty(list)) {
			SystemParameterInfo systemParameterInfo = list.get(0);
			expression = systemParameterInfo.getParam_value();
			logger.info("刷新mysql同步数据时间成功!");
		}
		return expression;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				long start = System.currentTimeMillis();
				logger.info("start :定时同步数据到mysql开始 : " + new Date());
				try {
					// 定时任务方法
					int consumeNum = scheduledTaskService.sysnMysqlData();
					long end = System.currentTimeMillis();
					logger.info("执行时间为: " + (end - start) + " ms;");
					logger.info("end : 定时同步数据到mysql结束 : " + new Date() +", 同步添加数据 :["+consumeNum+"]条");
				} catch (Exception e) {
					logger.error("定时同步数据到mysql任务出错: " + e.getMessage());
					ExceptionInfoUtil.saveExceptionInfo("synMysqlDataTask",e.getMessage(), e);
				}
			}
		};

		Trigger trigger = new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger cronTrigger = new CronTrigger(expression);
				return cronTrigger.nextExecutionTime(triggerContext);
			}
		};
		taskRegistrar.addTriggerTask(task, trigger);
	}
}
