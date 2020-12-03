package com.hanfu.user.center.config;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.model.Message;
import com.hanfu.user.center.manual.model.MessageEnum;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.user.center.model.HfUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 * @ServerEndpoint 可以把当前类变成websocket服务类
 */
@ServerEndpoint("/websocket/{userno}")
@Component
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, WebSocketTest> webSocketSet = new ConcurrentHashMap<String, WebSocketTest>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //当前发消息的人员编号
    private String userno = "";
    //消息
    //redis
    private static RedisTemplate<String, Object> redisTemplate;
    @Resource
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        WebSocketTest.redisTemplate = redisTemplate;
    }
    //用户
    private static HfUserMapper hfUserMapper;
    @Autowired
    public void setSecondUserMapper(HfUserMapper hfUserMapper){
        WebSocketTest.hfUserMapper = hfUserMapper;
    }
    //聊天
//    private static SecondChatMapper secondChatMapper;
//    @Autowired
//    public void setSecondChatMapper(SecondChatMapper secondChatMapper){
//        WebSocketTest.secondChatMapper = secondChatMapper;
//    }
    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userno") String param, Session session, EndpointConfig config) {
        System.out.println("接入连接用户id:"+param);
        userno = param;//接收到发送消息的人员编号
        this.session = session;
        webSocketSet.put(param, this);//加入map中
        addOnlineCount();           //在线数加1
        Object object = redisTemplate.opsForValue().get(param+"sendAllSass");
        if (object!=null){
            JSONArray jsonArray= JSONArray.parseArray(String.valueOf(object));
            List<Message> list = JSONObject.parseArray(jsonArray.toJSONString(), Message.class);
            list.forEach(ls->{
                redisTemplate.delete(param+"sendAllSass");
                ls.setByUserId(Integer.valueOf(param));
                ls.setType(MessageEnum.MessageStatus.SOLO.getMessageStatus());
                List<Message> list1 = new ArrayList<>();
                list1.add(ls);
                Gson gson = new Gson();
                onMessage(gson.toJson(list1));
            });

        }
        Object object1 = redisTemplate.opsForValue().get(param+"sendSoloSass");
        if (object1!=null){
            redisTemplate.delete(param+"sendSoloSass");
            JSONArray jsonArray= JSONArray.parseArray(String.valueOf(object1));
            List<Message> list = JSONObject.parseArray(jsonArray.toJSONString(), Message.class);
            list.forEach(ls->{
                ls.setByUserId(Integer.valueOf(param));
                ls.setType(MessageEnum.MessageStatus.SOLO.getMessageStatus());
                List<Message> list1 = new ArrayList<>();
                list1.add(ls);
                Gson gson = new Gson();
                onMessage(gson.toJson(list1));
            });

        }
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (!userno.equals("")) {
            webSocketSet.remove(userno);  //从set中删除
            subOnlineCount();           //在线数减1
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     *
     */
    @OnMessage
    public void onMessage(String message) {
        System.out.println("来自客户端的消息:" + message);
        JSONArray jsonArray= JSONArray.parseArray(message);
        List<Message> list = JSONObject.parseArray(jsonArray.toJSONString(), Message.class);
//        session.get
        //群发消息
        if (MessageEnum.MessageStatus.Group.getMessageStatus().equals(list.get(0).getType())) {
            sendAll(list.get(0).getMessage(),message,list.get(0).getBossId());
        } else {
            //保存聊天
//            addMessages(message);
            //给指定的人发消息
            sendToUser(list.get(0).getMessage(), String.valueOf(list.get(0).getByUserId())
                    , String.valueOf(list.get(0).getUserId()),message);

        }
    }

    /**
     * 给指定的人发送消息
     * @param message
     * @param byUserId //接收人
     * @param userId  //发送人
     *
     */
    private void sendToUser(String message,String byUserId,String userId,String messages) {

//        String sendUserno = message.split("[|]")[1];
//        String sendMessage = message.split("[|]")[0];
        String sendUserno = byUserId;
        String sendMessage = message;
        String now = getNowTime();
        try {
            if (webSocketSet.get(sendUserno) != null) {
//                webSocketSet.get(sendUserno).sendMessage(now + "用户" + userId + "发来消息：" + " <br/> " + sendMessage);
                webSocketSet.get(sendUserno).sendMessage(sendMessage);
            } else {
                JSONArray jsonArray1= JSONArray.parseArray(String.valueOf(messages));
                List<Message> list1 = JSONObject.parseArray(jsonArray1.toJSONString(), Message.class);

                Object object = redisTemplate.opsForValue().get(String.valueOf(byUserId)+"sendSoloSass");
                List<Message> list = new ArrayList<>();
                if (object!=null){
                    JSONArray jsonArray= JSONArray.parseArray(String.valueOf(object));
                    list = JSONObject.parseArray(jsonArray.toJSONString(), Message.class);
                    list.add(list1.get(0));
                } else {
                    list.add(list1.get(0));
                }
                Gson gson = new Gson();
                System.out.println("传入了"+gson.toJson(list));
                redisTemplate.opsForValue().set(String.valueOf(byUserId)+"sendSoloSass",gson.toJson(list));
                System.out.println("当前用户不在线");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给所有人发消息
     * @param message //发送的消息
     * @param messages //原本的消息
     */
    private void sendAll(String message,String messages,Integer bossId) {
//        SecondMessage secondMessage  = new SecondMessage();
//        secondMessage.setHeadline("测试");
//        int a =  secondMessageMapper.insertSelective(secondMessage);
        String now = getNowTime();
//        String sendMessage = message.split("[|]")[0];
        String sendMessage = message;
        //遍历HashMap
        HfUserExample hfUserExample = new HfUserExample();
        hfUserExample.createCriteria()
                .andBossIdEqualTo(bossId)
                .andIdDeletedEqualTo((byte) 0);
        List<HfUser> secondUsers =
        hfUserMapper.selectByExample(hfUserExample);
        List<String> keys = new ArrayList<>();
        for (String key : webSocketSet.keySet()) {
            try {
                //判断接收用户是否是当前发消息的用户
                if (!userno.equals(key)) {
                    keys.add(key);
//                    webSocketSet.get(key).sendMessage(now + "用户" + userno + "发来消息：" + " <br/> " + sendMessage);
                    webSocketSet.get(key).sendMessage(sendMessage);
                    System.out.println("key = " + key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        secondUsers.forEach(secondUser -> {
            boolean result = keys.contains(String.valueOf(secondUser.getId()));
            if (!result){
                Object object =
                redisTemplate.opsForValue().get(String.valueOf(secondUser.getId())+"sendAllSass");
                List<Message> list = new ArrayList<>();
                JSONArray jsonArray1= JSONArray.parseArray(messages);
                List<Message> list1 = JSONObject.parseArray(jsonArray1.toJSONString(), Message.class);
                if (object!=null){
                    JSONArray jsonArray= JSONArray.parseArray(String.valueOf(object));
                    list = JSONObject.parseArray(jsonArray.toJSONString(), Message.class);
                    list.add(list1.get(0));
                } else {
                    list.add(list1.get(0));
                }
                Gson gson = new Gson();
                System.out.println("传入了"+gson.toJson(list));
                redisTemplate.opsForValue().set(String.valueOf(secondUser.getId())+"sendAllSass",gson.toJson(list));
            }
        });
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    private String getNowTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }
    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    @Scheduled(cron = "0/5 * * * * ?")
//    或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=58*1000)
    private void configureTasks() throws Exception{
        for (String key : webSocketSet.keySet()) {
            try {
                String json = "{" + "\"userId\":" + "\"" + key + "\"," + "\"byUserId\":" + "\"" + key + "\"," + "\"message\":" + "\"" +"心跳" + "\" ," + "\"type\":" + "\"" + "heartbeat" + "\"}";
                System.out.println("连接的用户"+webSocketSet);
                System.out.println("在线人数"+getOnlineCount());
                webSocketSet.get(key).sendMessage(json);
                    System.out.println("key = " + key+"心跳");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }


}
