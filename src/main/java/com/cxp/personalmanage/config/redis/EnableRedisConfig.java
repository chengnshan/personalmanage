package com.cxp.personalmanage.config.redis;

import com.cxp.personalmanage.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author : cheng
 * @date : 2019-11-05 21:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RedisConfig.class)
public @interface EnableRedisConfig {
}
