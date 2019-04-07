package com.cxp.personalmanage.config.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.cxp.personalmanage.interceptor.EntityInterceptor;
import com.cxp.personalmanage.interceptor.SQLStatsInterceptor;

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
	
	@Bean
	public SqlSessionFactory sqlSessionFactory2() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(ds2);
		factoryBean.setTypeAliasesPackage("com.cxp.personalmanage.pojo");
		// 配置mapper的扫描，找到所有的mapper.xml映射文件
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:static/mybatis/mysql/*Mapper.xml");
        factoryBean.setMapperLocations(resources);
        //设置mybatis拦截器
        factoryBean.setPlugins(new Interceptor[] {entityInterceptor,sQLStatsInterceptor});
		return factoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		//使用上面配置的factory
		SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory2());
		return template;
	}
}
