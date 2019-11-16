package com.cxp.personalmanage.config.mybatis;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cxp.personalmanage.config.shiro.ShrioConfig_crazycake;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
@MapperScan(basePackages = { "com.cxp.personalmanage.mapper.mysql" }, sqlSessionFactoryRef = "sqlSessionFactory2")
@Import(value= {EntityInterceptor.class,SQLStatsInterceptor.class})
public class SecondSessionFactory {

	@Autowired
	@Qualifier("secondaryDataSource")
	private DataSource ds2;
	
	@Autowired
	private EntityInterceptor entityInterceptor;
	
	@Autowired
	private SQLStatsInterceptor sQLStatsInterceptor;
	
	@Bean(name = "sqlSessionFactory2")
	public SqlSessionFactory sqlSessionFactory2() throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(secondDataSource());
		factoryBean.setTypeAliasesPackage("com.cxp.personalmanage.pojo");
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:static/mybatis/mysql/**/*.xml");
        factoryBean.setMapperLocations(resources);
        //设置mybatis拦截器
        factoryBean.setPlugins(new Interceptor[] {entityInterceptor,sQLStatsInterceptor});
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

	private Environment environment;

	@Bean(name = "secondaryDataSource")
	public DataSource secondDataSource() {
		this.environment = ShrioConfig_crazycake.enviroment;
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


	@Bean(name="txSecondManager")
	public PlatformTransactionManager txSecondManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(secondDataSource());
		dataSourceTransactionManager.setRollbackOnCommitFailure(true);
		dataSourceTransactionManager.setFailEarlyOnGlobalRollbackOnly(true);
		dataSourceTransactionManager.setValidateExistingTransaction(true);
		return dataSourceTransactionManager;
	}
}
