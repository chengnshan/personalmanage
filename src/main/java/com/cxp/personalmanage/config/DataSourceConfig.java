package com.cxp.personalmanage.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private Environment environment;

    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource primaryDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceProperties.getUrl());
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        druidDataSource.setUsername(dataSourceProperties.getUsername());
        druidDataSource.setPassword(dataSourceProperties.getPassword());
        druidDataSource.setMaxActive(Integer.valueOf(environment.getProperty("datasource.maxActive")));
        druidDataSource.setMinIdle(Integer.valueOf(environment.getProperty("datasource.minIdle")));
        druidDataSource.setMaxWait(Long.valueOf(environment.getProperty("datasource.maxWait")));
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setValidationQuery(environment.getProperty("datasource.validationQuery"));
        druidDataSource.setInitialSize(Integer.valueOf(environment.getProperty("datasource.initialize")));
        druidDataSource.setConnectionProperties(environment.getProperty("datasource.connectionProperties"));
        druidDataSource.setUseGlobalDataSourceStat(Boolean.valueOf(environment.getProperty("useGlobalDataSourceStat")));
        try {
			druidDataSource.setFilters(environment.getProperty("datasource.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return druidDataSource;
    }
    
    @Bean(name = "secondaryDataSource")
    public DataSource secondDataSource() {
    	DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(environment.getProperty("spring.second.datasource.url"));
        druidDataSource.setDriverClassName(environment.getProperty("spring.second.datasource.driver-class-name"));
        druidDataSource.setUsername(environment.getProperty("spring.second.datasource.username"));
        druidDataSource.setPassword(environment.getProperty("spring.second.datasource.password"));
        druidDataSource.setMaxActive(Integer.valueOf(environment.getProperty("datasource.maxActive")));
        druidDataSource.setMinIdle(Integer.valueOf(environment.getProperty("datasource.minIdle")));
        druidDataSource.setMaxWait(Long.valueOf(environment.getProperty("datasource.maxWait")));
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setValidationQuery(environment.getProperty("datasource.validationQuery"));
        druidDataSource.setInitialSize(Integer.valueOf(environment.getProperty("datasource.initialize")));
        druidDataSource.setConnectionProperties(environment.getProperty("datasource.connectionProperties"));
        druidDataSource.setUseGlobalDataSourceStat(Boolean.valueOf(environment.getProperty("useGlobalDataSourceStat")));
        try {
			druidDataSource.setFilters(environment.getProperty("datasource.filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return druidDataSource;
    }

    // 其中 dataSource 框架会自动为我们注入
    @Bean(name="txPrimaryManager")
    public PlatformTransactionManager txPrimaryManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(primaryDataSource());
        dataSourceTransactionManager.setRollbackOnCommitFailure(true);
        dataSourceTransactionManager.setFailEarlyOnGlobalRollbackOnly(true);
        dataSourceTransactionManager.setValidateExistingTransaction(true);
        return dataSourceTransactionManager;
    }
    
    @Bean(name="txSecondManager")
    public PlatformTransactionManager txSecondManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(secondDataSource());
        dataSourceTransactionManager.setRollbackOnCommitFailure(true);
        dataSourceTransactionManager.setFailEarlyOnGlobalRollbackOnly(true);
        dataSourceTransactionManager.setValidateExistingTransaction(true);
        return dataSourceTransactionManager;
    }
}
