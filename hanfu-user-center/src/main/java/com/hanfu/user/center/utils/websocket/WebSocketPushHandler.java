package com.hanfu.user.center.utils.websocket;

//import org.junit.jupiter.api.Test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.controller.MsgController;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sh
 * @Date 2020/7/14 9:44
 * @Version 1.0
 * @name 创建处理器
 */
public class WebSocketPushHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> USER_LIST = new ArrayList<>();
    /**
     * 用户进入系统监听
     */
    private static RedisTemplate<String, Object> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        WebSocketPushHandler.redisTemplate = redisTemplate;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("3.用户进入系统。。。");
        System.out.println("用户信息:" + session.getAttributes());
        Map<String, Object> map = session.getAttributes();
        USER_LIST.add(session);
        for (String key : map.keySet()) {
            System.out.println("key:" + key + " and value:" + map.get(key));
//            redisTemplate.delete(String.valueOf(map.get(key))+"msg");
            if (key.equals("userId")){
                if (redisTemplate.opsForValue().get(String.valueOf(map.get(key))+"msg")!=null){
                    System.out.println((List<String>)redisTemplate.opsForValue().get(String.valueOf(map.get(key))+"msg"));
                    for (Map s:((List<Map<String,String>>)redisTemplate.opsForValue().get(String.valueOf(map.get(key))+"msg"))){
                        Map HashMap = s;
                        for (Object key1 : HashMap.keySet()) {
                            System.out.println("key= " + key1 + " and value= " + HashMap.get(key1));
                            HashMap<String,String> hashMap = new HashMap();
                            hashMap.put(String.valueOf(key),String.valueOf(HashMap.get(key1)));
                            TextMessage textMessage = new TextMessage(String.valueOf(key1));
                            WebSocketPushHandler.sendMessageToUser(String.valueOf(map.get(key)),textMessage,"msg",String.valueOf(HashMap.get(key1)),key);
                        }
                    }
                }
                redisTemplate.delete(String.valueOf(map.get(key))+"msg");
            }
        }

//        USER_LIST.add(session);
    }

    /**
     * 处理用户请求
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("系统处理xxx用户的请求信息。。。");
        System.out.println("===>>> 当前系统用户数为:"+USER_LIST.size());
        System.out.println("用户所发送消息为："+message.getPayload() );
        com.alibaba.fastjson.JSONArray jsonArray= com.alibaba.fastjson.JSONArray.parseArray(message.getPayload());
        List<Msg> list = JSONObject.parseArray(jsonArray.toJSONString(), Msg.class);
        System.out.println("消息哟"+list);
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("----------------"+ LocalTime.now() +"----------------");
//        stringBuilder.append("<h3>当前系统在线用户数为:"+USER_LIST.size()+"</h3><br/><br/>");
//        stringBuilder.append("<h4>用户"+session.getAttributes().get("userId")+"：</h4><br/>");
        stringBuilder.append(list.get(0).getMsg());
        TextMessage textMessage = new TextMessage(stringBuilder.toString());
        if (!list.get(0).getSuser().equals("0")){
            System.out.println(1);
            sendMessageToUser(list.get(0).getSuser(),textMessage,list.get(0).getMsg(),list.get(0).getType(),list.get(0).getUser());
        }else {
            System.out.println(2);
            //向所有用户广播消息
            sendMessagesToUsers(textMessage);
        }

    }

    /**
     * 用户退出后的处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("===>>> 退出系统时用户数为:"+USER_LIST.size());
        USER_LIST.remove(session);
        System.out.println(session.getAttributes().get("userId")+"xxx用户退出系统。。。");
    }

    /**
     * 自定义函数
     * 给所有的在线用户发送消息
     */
    public static  void sendMessagesToUsers(TextMessage message) {

        for (WebSocketSession user : USER_LIST) {
            try {
                // isOpen()在线就发送
                if (user.isOpen()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder
                            .append("######################################## <br/>")
                            .append("&nbsp;&nbsp;&nbsp;&nbsp;").append(LocalDateTime.now()).append("<br/>")
                            .append("[用户] <br/>")
                            .append("<img src='"+message.getPayload()+"'/>")
                            .append("<br/>")
                            .append("######################################## <br/>");
                    System.out.println("8777"+message.getPayload());
                    TextMessage textMessage = new TextMessage(stringBuilder);
                    user.sendMessage(textMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
    public static String List2String(List<String> list){
        JSONArray jsonArray = new JSONArray();
        for(int i=0;i<list.size();++i){
            try{
                jsonArray.put(list.get(i));
            }catch (Exception e){
                //这里处理异常
                break;
            }
        }
        return jsonArray.toString();
    }
    /**
     * 自定义函数
     * 发送消息给指定的在线用户
     */
    public static void sendMessageToUser(String userId, TextMessage message,String msg,String type,String user1) {
if (USER_LIST.size()==0){
    List<Map<String,String>> list = new ArrayList();
    System.out.println(888);
    if (redisTemplate.opsForValue().get(userId+"msg")!=null){
        System.out.println(redisTemplate.opsForValue().get(userId+"msg"));
        System.out.println(((List<Map<String,String>>)redisTemplate.opsForValue().get(userId+"msg")).get(0));
//        HashMap HashMap = JSON.parseObject(((List<Map<String,String>>)redisTemplate.opsForValue().get(userId+"msg")).get(0), HashMap.class);
        Map<String,String> HashMap = ((List<Map<String,String>>)redisTemplate.opsForValue().get(userId+"msg")).get(0);
        for (Object key : HashMap.keySet()) {
            System.out.println("key= " + key + " and value= " + HashMap.get(key));
            HashMap<String,String> hashMap = new HashMap();
            hashMap.put(String.valueOf(key),String.valueOf(HashMap.get(key)));
            list.add(hashMap);
        }
    }
    System.out.println(list);
    HashMap<String,String> hashMap = new HashMap();
    hashMap.put(String.valueOf(msg),type);
    list.add(hashMap);
    System.out.println(msg);
//    List2String(list);
//    list.toArray();
    redisTemplate.opsForValue().set(userId+"msg",list);
    System.out.println(888);
    System.out.println(redisTemplate.opsForValue().get(userId+"msg")+"----");
}
        for (WebSocketSession user : USER_LIST) {
            if (user.getAttributes().get("userId").equals(userId)||user.getAttributes().get("userId").equals(user1)) {
                try {
                    // isOpen()在线就发送
                    if (user.isOpen()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder
                                .append("######################################## <br/>")
                                .append("&nbsp;&nbsp;&nbsp;&nbsp;").append(LocalDateTime.now()).append("<br/>")
                                .append("<p align=\"right\">[用户] <br/></p>")
//                                .append("<img src='"+message.getPayload()+"'/>")
//                                .append(message.getPayload()).append("<br/>")
                                .append("<p align=\"right\"> ######################################## <br/> </p>");
                        if(type.equals("img")){
                            stringBuilder.append("<p align=\"right\"> <img src='"+message.getPayload()+"'/> </p>");
                        }
                        if(type.equals("test")){
                            stringBuilder.append("<p align=\"right\">"+message.getPayload()+"</p>");
                        }
                        TextMessage textMessage = new TextMessage(stringBuilder);
                        System.out.println(textMessage);
                        user.sendMessage(textMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }
    }

//    @Test
//    public void Test(List<WebSocketSession> USER_LIST){
//        for (WebSocketSession user : USER_LIST) {
//            System.out.println(user);
//        }
//    }

}
