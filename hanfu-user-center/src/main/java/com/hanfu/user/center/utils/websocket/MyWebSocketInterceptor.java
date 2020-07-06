package com.hanfu.user.center.utils.websocket;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author sh
 * @Date 2020/7 9:41
 * @Version 1.0
 * @name 创建握手 此类用来获取登录用户信息并交由websocket管理
 * @Description HandshakeInterceptor WebSocket握手请求的拦截器. 检查握手请求和响应, 对WebSocketHandler传递属性
 */
public class MyWebSocketInterceptor implements HandshakeInterceptor {

    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false. 通过attributes参数设置WebSocketSession的属性
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("1.用户建立连接。。。");
        if (request instanceof ServletServerHttpRequest) {
            String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
//            String suserId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("suserId");
            attributes.put("userId", userId);
//            attributes.put("suserId", suserId);
            System.out.println("用户唯一标识:" + userId);
            System.out.println(redisTemplate.opsForValue().get(userId+"msg"));
        }

        return true;
    }

    /**
     * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("2.在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头");
    }
}
