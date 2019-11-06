package com.cxp.personalmanage.config.websocket;

import com.cxp.personalmanage.pojo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2019-11-06 22:43
 */
public class MyWebSocketInterceptor extends  HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Before Handshake");
        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)request;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
            Session session = SecurityUtils.getSubject().getSession() ;
            UserInfo principal = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (principal != null){
            //    String username = (String) session.getAttribute("username");
                String username = principal.getUserName();
                attributes.put("websocket_username",username);
            }else {
                attributes.put("websocket_username",String.valueOf(System.currentTimeMillis()));
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
        System.out.println("After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
