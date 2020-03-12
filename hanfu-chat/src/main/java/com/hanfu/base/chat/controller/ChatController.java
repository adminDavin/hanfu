package com.hanfu.base.chat.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.base.chat.model.Message;
import com.hanfu.base.chat.response.handler.GlobalException;
import com.hanfu.base.chat.service.ChatSessionService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/chat")
@Api
@CrossOrigin
public class ChatController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ChatSessionService chatSessionService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ApiOperation(value = "当前用户信息", notes = "当前用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> query(String userId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(chatSessionService.findById(userId)));
    }

    @RequestMapping(value = "/toId", method = RequestMethod.GET)
    @ApiOperation(value = "接收方信息", notes = "接收方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> toid(String userId, Message message) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        try {
            WebsocketServerEndpoint endpoint = new WebsocketServerEndpoint();
            endpoint.sendTo(userId, message);
        } catch (GlobalException e) {
            e.printStackTrace();
            return builder.body(ResponseUtils.getResponseBody(e.getMsg()));
        }
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @RequestMapping(value = "/online/list", method = RequestMethod.GET)
    @ApiOperation(value = "获取在线用户列表", notes = "获取在线用户列表")
    public ResponseEntity<JSONObject> onlineList() throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(chatSessionService.onlineList()));
    }

    @RequestMapping(value = "/common", method = RequestMethod.GET)
    @ApiOperation(value = "获取公共聊天消息内容", notes = "获取公共聊天消息内容")
    public ResponseEntity<JSONObject> commonList() throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(chatSessionService.commonList()));
    }

    @RequestMapping(value = "/self", method = RequestMethod.GET)
    @ApiOperation(value = "获取公共聊天消息内容", notes = "获取公共聊天消息内容")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fromId", value = "发送方id", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "toId", value = "接收方id", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> selfList(String fromId, String toId) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        List<Message> list = chatSessionService.selfList(fromId, toId);
        return builder.body(ResponseUtils.getResponseBody(list));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "发送方id", required = true, type = "String"),
    })
    public ResponseEntity<JSONObject> logout(String id) throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder();
        chatSessionService.delete(id);
        return builder.body(ResponseUtils.getResponseBody(""));
    }
}
