package com.hanfu.order.center.controller;


import java.io.IOException;
import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.order.center.dao.FileDescMapper;
import com.hanfu.order.center.manual.dao.MessageDao;
import com.hanfu.order.center.manual.model.Evaluate;
import com.hanfu.order.center.model.FileDesc;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/message")
@Api
public class MessageController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MessageDao messageDao;
    @Autowired
    FileDescMapper fileDescMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "查询全部消息", notes = "查询全部消息")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> query()
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(messageDao.selectMeaasgeList()));
    }

    @ApiOperation(value = "添加消息", notes = "添加消息")
    @RequestMapping(value = "/addMessage", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "message", value = "消息", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> addMessage(String message, Integer userId)
            throws JSONException {
        String key = userId.toString();
        redisTemplate.opsForValue().set(key, message);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @ApiOperation(value = "删除消息", notes = "删除消息")
    @RequestMapping(value = "/deleteMessage", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")
    })
    public ResponseEntity<JSONObject> deleteMessage(Integer userId)
            throws JSONException {
        String userid = userId.toString();
        redisTemplate.delete(userid);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @ApiOperation(value = "修改消息", notes = "修改消息")
    @RequestMapping(value = "/updateMessage", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "message", value = "消息", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> updateMessage(Integer userId, String message)
            throws JSONException {
        redisTemplate.opsForValue().get(userId);
        String key = userId.toString();
        redisTemplate.opsForValue().append(key, message);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @ApiOperation(value = "添加评价", notes = "添加评价")
    @RequestMapping(value = "/insertReply", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "evaluate", value = "评价", required = false, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单Id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = false, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品Id", required = false, type = "Integer"),
    })
    public ResponseEntity<JSONObject> insertReply(MultipartFile fileInfo, Evaluate evaluate, Integer orderId, Integer userId,Integer goodsId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (orderId == null) {
            return builder.body(ResponseUtils.getResponseBody("没有任何评价"));
        }
        String key = orderId.toString() + userId.toString()+goodsId.toString();
		FileMangeService fileMangeService = new FileMangeService();
		String arr[];
		try {
			arr = fileMangeService.uploadFile(fileInfo.getBytes(), String.valueOf(userId));
			FileDesc fileDesc = new FileDesc();
			fileDesc.setFileName(fileInfo.getName());
			fileDesc.setGroupName(arr[0]);
			fileDesc.setRemoteFilename(arr[1]);
			fileDesc.setUserId(userId);
			fileDesc.setCreateTime(LocalDateTime.now());
			fileDesc.setModifyTime(LocalDateTime.now());
			fileDesc.setIsDeleted((short) 0);
			fileDescMapper.insert(fileDesc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        redisTemplate.opsForValue().set(key, evaluate);
        return builder.body(ResponseUtils.getResponseBody(""));
    }

    @ApiOperation(value = "查看评价", notes = "查看评价")
    @RequestMapping(value = "/SeekReply", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> SeekReply(Integer orderId, Integer userId,Integer goodsId)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        if (orderId == null) {
            return builder.body(ResponseUtils.getResponseBody("没有评价"));
        }
        String key = orderId.toString() + userId.toString()+goodsId.toString();
        return builder.body(ResponseUtils.getResponseBody(redisTemplate.opsForValue().get(key)));
    }

    @ApiOperation(value = "评价回复", notes = "评价回复")
    @RequestMapping(value = "/queryReply", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "evaluate", value = "评价", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "goodsId", value = "物品Id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> queryReply(@RequestParam String evaluate, Integer orderId, Integer userId,Integer goodsId)
            throws JSONException {
        String key = orderId.toString()+orderId.toString()+goodsId.toString();
        redisTemplate.opsForValue().set(key, evaluate);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(""));
    }
}
