package com.hanfu.message.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.message.center.service.WxTemplateService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/message")
@Api
public class MessageController {
    @Autowired
    WxTemplateService wxTemplateService;

    @RequestMapping(path = "/sendMessage", method = RequestMethod.GET)
    @ApiOperation(value = "发送模板消息", notes = "发送消息")
    public ResponseEntity<JSONObject> sendMessage(String message01, String message02) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (StringUtils.isEmpty(message01) || StringUtils.isEmpty(message02)) {
            builder.body(ResponseUtils.getResponseBody("参数确实"));
        }

        wxTemplateService.sendTemplateToUsers(message01, message02);
        return builder.body(ResponseUtils.getResponseBody("消息发送成功"));
    }
}
