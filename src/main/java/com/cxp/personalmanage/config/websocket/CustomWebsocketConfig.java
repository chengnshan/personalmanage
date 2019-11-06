package com.cxp.personalmanage.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author : cheng
 * @date : 2019-11-06 22:35
 */

public class CustomWebsocketConfig {

    @Bean
    public WebsocketConfig websocketConfig(){
        return new WebsocketConfig();
    }

    @Bean
    public ChatMessageHandler chatMessageHandler(){
        return new ChatMessageHandler();
    }

    private class WebsocketConfig implements WebSocketConfigurer {

        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(chatMessageHandler(), "/websocket2")
                    .addInterceptors(new MyWebSocketInterceptor()).setAllowedOrigins("*");

            registry.addHandler(chatMessageHandler(),"/sockjs/websocket2").addInterceptors(new MyWebSocketInterceptor()).withSockJS();
        }
    }
}
