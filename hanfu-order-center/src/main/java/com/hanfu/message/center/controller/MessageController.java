package com.hanfu.message.center.controller;


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

import com.alibaba.fastjson.JSONObject;
import com.hanfu.message.center.manual.dao.MessageDao;
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
	@Resource
    private RedisTemplate<String, String> redisTemplate;
	@ApiOperation(value = "查询全部消息", notes = "查询全部消息")
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> query()
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(messageDao.selectMeaasgeList()));
	}
	@ApiOperation(value = "评价回复", notes = "评价回复")
	@RequestMapping(value = "/creat", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "evaluate", value = "评价", required = true, type = "String") })
	public ResponseEntity<JSONObject> creatOrder(@RequestParam String evaluate)
			throws JSONException {
		String key = null;
		redisTemplate.opsForValue().set(key, evaluate);
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		return builder.body(ResponseUtils.getResponseBody(""));
	}
}
