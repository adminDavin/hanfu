package com.hanfu.base.chat.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 测试客服消息发送 Title:TemplateTest Description:
 */

public class WXMsgResponseUtil {


    private static Logger log = Logger.getLogger(WXMsgResponseUtil.class.getSimpleName());


    private static String RES_RESULT =
            "你好，很高兴为你服务。";

    /***
     * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/conversation.html
     * 发送的文本消息
     */
    public static JSONObject sendCustomerMessage(String touser) {
        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");

        JSONObject text = new JSONObject();
        text.put("content", RES_RESULT);

        obj.put("text", text);

//        obj.put("ToUserName",touser);
//        obj.put("FromUserName","S______A");
//        obj.put("CreateTime", (new Date()).getTime());
//        obj.put("MsgType","transfer_customer_service");

        log.info("回复的文本:\n" + obj.toString());
        JSONObject jsonObject = HttpUtil.httpsRequest(obj);

        log.info("回复jsonObject:\n" + jsonObject);
        return jsonObject;
    }


    public static JSONObject sendCustomerMessage(String touser, String oldId, String keyWord) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);// 设置超时
        requestFactory.setReadTimeout(5000);
        RestTemplate template = new RestTemplate(requestFactory);


        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");

        JSONObject text = new JSONObject();

        //RES_RESULT 根据index在数据库中查询
        text.put("content", RES_RESULT);

        obj.put("text", text);

        log.info("回复的文本:\n" + obj.toString());

        return JSONObject.parseObject(RES_RESULT);
    }

    /***
     * 文档地址：https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/conversation.html
     * 发送的图片消息
     */
    public static JSONObject sendCustomerImageMessage(String touser, String mediaId) {
        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "image");

        JSONObject media = new JSONObject();
        media.put("media_id", mediaId);

        obj.put("image", media);

        System.out.println("回复的图片:\n" + obj.toString());
        JSONObject jsonObject = HttpUtil.httpsRequest(obj);
        System.out.println(jsonObject);
        return jsonObject;
    }

    public static String sendFirstMessage(String appId, String index, String touser) {


        JSONObject obj = new JSONObject();

        obj.put("touser", touser);
        obj.put("msgtype", "text");


        JSONObject text = new JSONObject();

        //RES_RESULT 根据index在数据库中查询
        text.put("content", RES_RESULT);

        obj.put("text", text);

        JSONObject jsonObject = HttpUtil.httpsRequest(obj, "accessToken");

        return jsonObject.toString();
    }
}

