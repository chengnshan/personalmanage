package com.cxp.personalmanage;

import com.cxp.personalmanage.config.redis.EnableRedisConfig;
import com.cxp.personalmanage.config.websocket.EnableWebsocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {RabbitAutoConfiguration.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableRedisConfig
@EnableWebsocketConfig
@EnableScheduling
@EnableDiscoveryClient
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

