package com.cxp.personalmanage.config.websocket;

import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.lang.annotation.*;

/**
 * @author : cheng
 * @date : 2019-11-06 22:34
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CustomWebsocketConfig.class)
@EnableWebSocket
public @interface EnableWebsocketConfig {
}
