package com.cxp.personalmanage.config.mybatis;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.cxp.personalmanage.config.DataSourceConfig;
import com.cxp.personalmanage.config.shiro.ShrioConfig_crazycake;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.cxp.personalmanage.interceptor.EntityInterceptor;
import com.cxp.personalmanage.interceptor.SQLStatsInterceptor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = { "com.cxp.personalmanage.mapper.postgresql" }, sqlSessionFactoryRef = "sqlSessionFactory1")
@Import(value= {EntityInterceptor.class,SQLStatsInterceptor.class})
public class PrimarySessionFactory {

	@Autowired
	@Qualifier("primaryDataSource")
	private DataSource dataSource;
	
	@Autowired
	private EntityInterceptor entityInterceptor;
	
	@Autowired
	private SQLStatsInterceptor sQLStatsInterceptor;

	private Environment environment;
	
	@Bean(name = "sqlSessionFactory1")
	public SqlSessionFactory sqlSessionFactory1() throws Exception {
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		factoryBean.setDataSource(primaryDataSource());
		factoryBean.setTypeAliasesPackage("com.cxp.personalmanage.pojo");
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:static/mybatis/postgresql/**/*.xml");
        factoryBean.setMapperLocations(resources);
        //设置mybatis拦截器
        factoryBean.setPlugins(new Interceptor[] {entityInterceptor,sQLStatsInterceptor});

		SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
		return sqlSessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
		//使用上面配置的factory
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1());
		return template;
	}

	/*@Bean
	public MapperScannerConfigurer mapperScannerConfigurer1() throws Exception {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("com.cxp.personalmanage.mapper");
		configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean1");
		configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate1");
		configurer.afterPropertiesSet();
		return configurer;
	}*/

	@Bean(name = "primaryDataSource")
	@Primary
	public DataSource primaryDataSource() {
		this.environment = ShrioConfig_crazycake.enviroment;
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
		druidDataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
		druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
		druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
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
}
