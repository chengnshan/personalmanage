package com.cxp.personalmanage.config.mybatis;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cxp.personalmanage.config.shiro.ShrioConfig_crazycake;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.cxp.personalmanage.interceptor.EntityInterceptor;
import com.cxp.personalmanage.interceptor.SQLStatsInterceptor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

/**
 * @author cheng
 */
@Configuration
@EnableConfigurationProperties(SecondDataSourceProperties.class)
@MapperScan(basePackages = { "com.cxp.personalmanage.mapper.mysql" }, sqlSessionFactoryRef = "sqlSessionFactory2")
@Import(value= {EntityInterceptor.class,SQLStatsInterceptor.class})
public class SecondSessionFactory {

	private static final Logger logger = LoggerFactory.getLogger(SecondSessionFactory.class);
	
	@Autowired
	private EntityInterceptor entityInterceptor;
	
	@Autowired
	private SQLStatsInterceptor sqlStatsInterceptor;

	@Autowired
	private SecondDataSourceProperties secondDataSourceProperties;

	@Autowired
	private Environment environment;

	@PostConstruct
	public void init(){
		if (environment == null){
			this.environment = ShrioConfig_crazycake.enviroment;
		}
	}
	
	@Bean(name = "sqlSessionFactory2")
	public SqlSessionFactory sqlSessionFactory2() throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(secondDataSource());
		factoryBean.setTypeAliasesPackage("com.cxp.personalmanage.pojo");
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:static/mybatis/mysql/**/*.xml");
        factoryBean.setMapperLocations(resources);
        //设置mybatis拦截器
        factoryBean.setPlugins(new Interceptor[] {entityInterceptor,sqlStatsInterceptor});
		return factoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplate2")
	public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
		//使用上面配置的factory
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2());
		return template;
	}

/*	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer2() throws Exception {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("com.cxp.personalmanage.mapper.mysql");
		configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean2");
		configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate2");
		configurer.afterPropertiesSet();
		return configurer;
	}*/

	@Bean(name = "secondaryDataSource")
	public DataSource secondDataSource() {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(secondDataSourceProperties.getUrl());
		druidDataSource.setDriverClassName(secondDataSourceProperties.getDriverClassName());
		druidDataSource.setUsername(secondDataSourceProperties.getUsername());

		druidDataSource.setInitialSize(secondDataSourceProperties.getInitialize());
		druidDataSource.setPassword(secondDataSourceProperties.getPassword());
		druidDataSource.setMaxActive(secondDataSourceProperties.getMaxActive());
		druidDataSource.setMinIdle(secondDataSourceProperties.getMinIdle());
		druidDataSource.setMaxWait(secondDataSourceProperties.getMaxWait());

		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setValidationQuery(secondDataSourceProperties.getValidationQuery());
		druidDataSource.setConnectionProperties(secondDataSourceProperties.getConnectionProperties());
		druidDataSource.setUseGlobalDataSourceStat(secondDataSourceProperties.isUseGlobalDataSourceStat());
		try {
			druidDataSource.setFilters(secondDataSourceProperties.getFilters());
		} catch (SQLException e) {
			logger.error("secondDataSource exception" + e.getMessage(), e);
		}
		return druidDataSource;
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
