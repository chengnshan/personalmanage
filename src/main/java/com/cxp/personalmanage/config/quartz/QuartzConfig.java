package com.cxp.personalmanage.config.quartz;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author : cheng
 * @date : 2020-07-10 23:46
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setQuartzProperties(quartzProperties());
        /**可选，QuartzScheduler启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了*/
        factoryBean.setOverwriteExistingJobs(true);
        /**延迟两秒启动*/
        factoryBean.setStartupDelay(2);
        factoryBean.setAutoStartup(true);
        return factoryBean;
    }

    private Properties quartzProperties() throws IOException {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("quartz/quartz.properties"));
        factoryBean.afterPropertiesSet();
        Properties properties = factoryBean.getObject();
        return properties;
    }
}
