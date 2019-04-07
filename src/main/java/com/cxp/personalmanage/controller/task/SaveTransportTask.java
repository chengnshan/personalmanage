package com.cxp.personalmanage.controller.task;

import java.math.BigDecimal;
import java.util.*;

import com.cxp.personalmanage.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.service.ScheduledTaskService;
import com.cxp.personalmanage.service.SystemParameterInfoService;

/**
 * 动态改变Cron定时任务执行时间
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/tasks")
@EnableScheduling
public class SaveTransportTask implements SchedulingConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(SaveTransportTask.class);

	@Autowired
	private SystemParameterInfoService systemParameterInfoService;
	
	@Autowired
	@Qualifier(value="scheduledTaskService")
	private ScheduledTaskService scheduledTaskService;

	// 每周一到周五 0点30分执行执行一次
	private static String expression = "0 30 0 * * MON-FRI";

	@RequestMapping(value = "/changeExpression")
	public String saveTransport() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("param_code", Constant.ScheduConsume.COSUME_SCHEDU_TIME);
		List<SystemParameterInfo> list = systemParameterInfoService.getParameterInfoByCode(paramMap);
		if (CollectionUtils.isNotEmpty(list)) {
			SystemParameterInfo systemParameterInfo = list.get(0);
			expression = systemParameterInfo.getParam_value();
			logger.info("刷新定时插入消费明细时间成功!");
		}
		return expression;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		Runnable task = new Runnable() {

			@Override
			public void run() {
				//设置分布式锁,如何获取到锁,则可以执行定时任务
				String uuid = UUID.randomUUID().toString();
				boolean setLock = RedisUtils.setLock(Constant.DistributedCon.CONSUME_DETAIL_LOCK,uuid ,20000L);
				if (setLock){
					long start = System.currentTimeMillis();
					logger.info("start :保存上下班固定费用开始 : " + new Date());
					try {
						// 定时任务方法
						scheduledTaskService.saveTransportConsume();

						long end = System.currentTimeMillis();
						logger.info("执行时间为: " + (end - start) +" ms;");
						logger.info("end : 保存上下班固定费用结束 : " + new Date());
					} catch (Exception e) {
						logger.error("保存上下班固定费用开始SaveTransportTask任务出错: " + e.getMessage(),e);
					}
				}
				RedisUtils.releaseLock(Constant.DistributedCon.CONSUME_DETAIL_LOCK,uuid);
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
