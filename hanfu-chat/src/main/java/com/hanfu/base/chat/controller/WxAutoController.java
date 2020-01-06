package com.hanfu.base.chat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanfu.base.chat.utils.SignUtil;
import com.hanfu.base.chat.utils.WXMsgResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by dingd on 2018-07-22.
 */

@RestController
public class WxAutoController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/WxAutoReply")
    public @ResponseBody
    String weixinProcessGetMethod(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("get" + echostr);
            return echostr;
        }
        logger.info("get   NULL");
        return null;
    }

    @PostMapping("/WxAutoReply")
    @ResponseBody
    public String weixinProcessPostMethod(HttpServletRequest request,
                                          HttpServletResponse response) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        response.setCharacterEncoding("UTF-8");
        String success = "{\"success\":\"success\"}";
        JSONObject object = JSONObject.parseObject(success);
        try {
            ServletInputStream stream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = new String("");
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            logger.info("JSONObject   JSONObject" + jsonObject);


            if (jsonObject.getString("MsgType").equals("text")) { //收到的是文本消息
                //回复转人工服务
                if ("人工服务".equals(jsonObject.getString("Content"))) {
                    HashMap<String, Object> resultMap = new HashMap<>();
                    resultMap.put("ToUserName", jsonObject.getString("FromUserName"));
                    resultMap.put("FromUserName", jsonObject.getString("ToUserName"));
                    resultMap.put("CreateTime", Long.parseLong(jsonObject.getString("CreateTime")));
                    resultMap.put("MsgType", "transfer_customer_service");
                    String json = JSON.toJSONString(resultMap);
                    JSONObject result = JSONObject.parseObject(json);
                    logger.info("POST   result" + result);
                    return result.toString();
                }
                //也回复一个文本消息
                logger.info("POST" + jsonObject);
                WXMsgResponseUtil.sendCustomerMessage(jsonObject.getString("FromUserName"), jsonObject.getString("ToUserName"), jsonObject.getString("Content"));
                return "success";
            } else if (jsonObject.getString("MsgType").equals("event")) {
                String sessionFrom = (String) jsonObject.get("SessionFrom");
                logger.info("SessionFrom   SessionFrom" + sessionFrom);
                int i = sessionFrom.indexOf("+");
                String sessionFromFirst = "1";
                String appId = "*****";
                if (i > 0) {
                    sessionFromFirst = sessionFrom.substring(0, i); //标志位 1 2 3 4 5 6
                    logger.info("SessionFrom   sessionFromFirst    " + sessionFromFirst);
                    String sessionFromLast = sessionFrom.substring(i + 1);  //{"appId":"","data":"test"}
                    logger.info("SessionFrom   sessionFromLast     " + sessionFromLast);
                    if (JSONObject.parseObject(sessionFromLast).get("appId") != null) {
                        appId = (String) JSONObject.parseObject(sessionFromLast).get("appId");
                    }
                }
                WXMsgResponseUtil.sendFirstMessage(appId, sessionFromFirst, jsonObject.getString("FromUserName"));
                return "success";
            } else { //那就是图片的消息了

                //也回复一个图片消息
                WXMsgResponseUtil.sendCustomerImageMessage(jsonObject.getString("FromUserName"), jsonObject.getString("MediaId"));
                return "success";
            }


        } catch (Exception e) {
            e.printStackTrace();
            logger.info("回复异常：" + e);
        }
        return null;

    }

}
