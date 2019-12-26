package com.hanfu.user.center.controller;

import com.hanfu.user.center.service.HfUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.model.HUserBalanceExample;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@RequestMapping("/user/balance")
@CrossOrigin
public class HfUserBalanceController {

	@Autowired
	HUserBalanceMapper hUserBalanceMapper;

	@Autowired
	HfUserBalanceService hfUserBalanceService;
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ApiOperation(value = "获取用戶余额", notes = "获取用戶余额")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true,type = "Integer"),
	})
	public ResponseEntity<JSONObject> query(@RequestParam Integer userId) throws Exception {
		HUserBalanceExample example = new HUserBalanceExample();
		example.createCriteria().andUserIdEqualTo(userId);
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(hUserBalanceMapper.selectByExample(example)));
	}

	@RequestMapping(value = "/balancePay",method = RequestMethod.GET)
	@ApiOperation(value = "小程序用户余额支付",notes = "小程序用户余额支付")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "总额", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> balancePay(@RequestParam(required = true,defaultValue = "") Integer userId,
												 @RequestParam(required = true,defaultValue = "") Integer hfBalance,
												 @RequestParam(required = true,defaultValue = "") Integer total)throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if(hfBalance>=total){
			hfUserBalanceService.balanceCutTotal(userId,hfBalance,total);
			return builder.body(ResponseUtils.getResponseBody("支付成功"));
		}else{
			return builder.body(ResponseUtils.getResponseBody("余额不足,请充值"));
		}
	}
}