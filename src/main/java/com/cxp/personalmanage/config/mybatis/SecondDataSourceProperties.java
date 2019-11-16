package com.cxp.personalmanage.config.mybatis;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : cheng
 * @date : 2019-11-16 23:13
 */
@Data
@ToString
@ConfigurationProperties(prefix = "spring.second.datasource")
public class SecondDataSourceProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private int initialize;
    private int maxActive;
    private int minIdle;
    private Long maxWait;
    private String validationQuery;
    private String filters;
    private String connectionProperties;
    private boolean useGlobalDataSourceStat;
}
