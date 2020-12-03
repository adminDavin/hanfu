package com.hanfu.user.center.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
//    private static ConcurrentHashMap<String, WebSocketTest> webSocketSet = new ConcurrentHashMap<String, WebSocketTest>();
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=58*1000)
//    private void configureTasks() throws Exception{
//        webSocketSet.get();
//    }
}
