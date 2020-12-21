//package com.hanfu.product.center.config;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.websocket.*;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.URI;
//
//@Component
//@ClientEndpoint
//public class SocketText {
//
//    // 服务端的IP和端口号
//    private static final String URL = "192.168.1.15:1200";
//
//    private Session session;
//
//    @PostConstruct
//    void init() {
//        try {
//            // 本机地址
//            String hostAddress = InetAddress.getLocalHost().getHostAddress();
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            String wsUrl = "ws://" + URL + "/" + hostAddress;
//            URI uri = URI.create(wsUrl);
//            session = container.connectToServer(SocketText.class, uri);
//        } catch (DeploymentException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 打开连接
//     * @param session
//     */
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println("打开连接");
//        this.session = session;
//    }
//
//    /**
//     * 接收消息
//     * @param text
//     */
//    @OnMessage
//    public void onMessage(String text) {
//        System.out.println(text);
//    }
//
//    /**
//     * 异常处理
//     * @param throwable
//     */
//    @OnError
//    public void onError(Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    /**
//     * 关闭连接
//     */
//    @OnClose
//    public void onClosing() throws IOException {
//        session.close();
//    }
//
//    /**
//     * 主动发送消息
//     */
//    public void send(JSONObject json) {
//        session.getAsyncRemote().sendText(json.toJSONString());
//    }
//
//
//}
