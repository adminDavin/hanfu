package com.hanfu.base.chat.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.base.chat.model.HfUser;
import com.hanfu.base.chat.model.Message;
import com.hanfu.base.chat.response.handler.GlobalException;
import com.hanfu.base.chat.service.ChatSessionService;
import com.hanfu.base.chat.utils.CoreUtil;

@Component
@ServerEndpoint(value = "/chat/{id}")
public class WebsocketServerEndpoint {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ChatSessionService chatSessionService;

    @Autowired
    public void setChatSessionService(ChatSessionService chatSessionService) {
        WebsocketServerEndpoint.chatSessionService = chatSessionService;
    }

    private static long online = 0;
    private static CopyOnWriteArraySet<WebsocketServerEndpoint> websocketServerEndpoints = new CopyOnWriteArraySet<>();
    private Session session;
    private String fromId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        logger.info("onOpen >> 链接成功");
        this.session = session;

        //将当前websocket对象存入到Set集合中
        websocketServerEndpoints.add(this);

        //在线人数+1
        addOnlineCount();

        logger.info("有新窗口开始监听：" + id + ", 当前在线人数为：" + getOnlineCount());

        this.fromId = id;
        try {
            HfUser user = chatSessionService.findById(fromId);
            //群发消息
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "用户 " + user.getName() + " 已上线");
            sendMore(JSONObject.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        logger.info("onClose >> 链接关闭");

        //移除当前Websocket对象
        websocketServerEndpoints.remove(this);

        //在内线人数-1
        subOnLineCount();

        logger.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info("接收到窗口：" + fromId + " 的信息：" + message);

        chatSessionService.pushMessage(fromId, null, message);

        //群发消息
        sendMore(getData(null, message));
    }


    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private void subOnLineCount() {
        WebsocketServerEndpoint.online--;

    }

    private void sendMore(String data) {
        for (WebsocketServerEndpoint websocketServerEndpoint : websocketServerEndpoints) {
            try {
                websocketServerEndpoint.sendMessage(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private synchronized long getOnlineCount() {
        return online;
    }

    private void addOnlineCount() {
        WebsocketServerEndpoint.online++;

    }

    private String getData(String toId, String message) {
        Message entity = new Message();
        entity.setMessage(message);
        entity.setTime(CoreUtil.format(new Date()));
        entity.setFrom(chatSessionService.findById(fromId));
        entity.setTo(chatSessionService.findById(toId));
        return JSONObject.toJSONString(entity);
    }

    public void sendTo(String toId, Message entity) throws Exception {
        fromId = entity.getFrom().getId().toString();
        if (websocketServerEndpoints.size() <= 1) {
            throw new GlobalException("用户未上线");
        }
        boolean flag = false;
        for (WebsocketServerEndpoint endpoint : websocketServerEndpoints) {
            try {
                if (endpoint.fromId.equals(toId)) {
                    flag = true;
                    logger.info(entity.getFrom().getId() + " 推送消息到窗口：" + toId + " ，推送内容：" + entity.getMessage());

                    endpoint.sendMessage(getData(toId, entity.getMessage()));
                    chatSessionService.pushMessage(fromId, toId, entity.getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        if (!flag) {
            throw new GlobalException("推送失败，找不到该窗口");
        }
    }
}
