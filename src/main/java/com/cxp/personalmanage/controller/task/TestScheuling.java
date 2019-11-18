package com.cxp.personalmanage.controller.task;

import com.cxp.personalmanage.common.Constant;
import com.cxp.personalmanage.config.context.InitMemoryConfig;
import com.cxp.personalmanage.pojo.SystemParameterInfo;
import com.cxp.personalmanage.service.ScheduledTaskService;
import com.cxp.personalmanage.service.SystemParameterInfoService;
import com.cxp.personalmanage.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : cheng
 * @date : 2019-11-17 17:54
 */
@RestController
@RequestMapping(value = "/tasks")
public class TestScheuling implements SchedulingConfigurer {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //时间表达式  每天凌晨2点执行一次
    private String expression = "0 0 2 * * ? ";

    @Autowired
    private SystemParameterInfoService systemParameterInfoService;

    @Autowired
    @Qualifier(value="scheduledTaskService")
    private ScheduledTaskService scheduledTaskService;

    @RequestMapping(value = "/changeExpression1")
    public String saveTransport() {
        expression = InitMemoryConfig.getParamValue(Constant.ScheduConsume.COSUME_SCHEDU_TIME);
        if (StringUtils.isBlank(StringUtil.conveterStr(expression))){
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("param_code", Constant.ScheduConsume.COSUME_SCHEDU_TIME);
            List<SystemParameterInfo> list = systemParameterInfoService.getParameterInfoByCode(paramMap);
            if (CollectionUtils.isNotEmpty(list)) {
                SystemParameterInfo systemParameterInfo = list.get(0);
                expression = systemParameterInfo.getParam_value();
            }
        }
        return expression;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                //任务逻辑
                System.out.println("---------------start-------------------");
                System.out.println("动态修改定时任务参数，时间表达式cron为：" + expression);
                System.out.println("当前时间为：" + sdf.format(new Date()));
                System.out.println("----------------end--------------------");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                CronTrigger cronTrigger = new CronTrigger(expression);
                Date nextExecDate = cronTrigger.nextExecutionTime(triggerContext);
                return nextExecDate;
            }
        });
    }
}
