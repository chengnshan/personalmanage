package com.cxp.personalmanage;

import com.cxp.personalmanage.config.redis.EnableRedisConfig;
import com.cxp.personalmanage.config.websocket.EnableWebsocketConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisConfig
@EnableWebsocketConfig
public class PersonalmanageApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalmanageApplication.class, args);
	}

}
/*
public class PersonalmanageApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PersonalmanageApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PersonalmanageApplication.class);
	}

}
*/

