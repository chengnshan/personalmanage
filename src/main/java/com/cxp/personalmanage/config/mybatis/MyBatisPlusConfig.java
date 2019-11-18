package com.cxp.personalmanage.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : cheng
 * @date : 2019-11-17 22:54
 */
@Configuration
public class MyBatisPlusConfig {
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        System.out.println("=============================PaginationInterceptor========================");
        return paginationInterceptor;
    }

}
