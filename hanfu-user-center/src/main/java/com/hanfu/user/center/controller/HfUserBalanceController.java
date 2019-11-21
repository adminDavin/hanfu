package com.hanfu.user.center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayDataBillTransferQueryModel;
import com.alipay.api.request.AlipayDataBillTransferQueryRequest;
import com.alipay.api.response.AlipayDataBillTransferQueryResponse;
import com.hanfu.user.center.dao.HUserBalanceMapper;
import com.hanfu.user.center.model.HUserBalanceExample;
import com.hanfu.user.center.request.AlipayConfig;
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
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ApiOperation(value = "获取用戶余额", notes = "获取用戶余额")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
	})
	public ResponseEntity<JSONObject> query(@RequestParam Integer userId) throws Exception {
		HUserBalanceExample example = new HUserBalanceExample();
		example.createCriteria().andUserIdEqualTo(userId);
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(hUserBalanceMapper.selectByExample(example)));
	}
	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	@ApiOperation(value = "余额充值", notes = "余额充值")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = true, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = true, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "type", value = "转账类型：充值-DEPOSIT，提现-WITHDRAW，转账-TRANSFER。", required = true, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "pageNo", value = "分页号", required = true, type = "String"),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "分页大小", required = true, type = "String"),
	})
	public ResponseEntity<JSONObject> recharge(String startTime,String endTime,String type,String pageNo,String pageSize) throws Exception{
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "UTF-8",
				AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA");
		AlipayDataBillTransferQueryRequest  transferQuery_request = new AlipayDataBillTransferQueryRequest();
		AlipayDataBillTransferQueryModel model = new AlipayDataBillTransferQueryModel();
		model.setStartTime(startTime);
		model.setEndTime(endTime);
		model.setType(type);
		model.setPageNo(pageNo);
		model.setPageSize(pageSize);
		transferQuery_request.setBizModel(model);
		try {
			AlipayDataBillTransferQueryResponse response = alipayClient.execute(transferQuery_request);
			if(response.isSuccess()){
				System.out.println(response.getBody());
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		return builder.body(ResponseUtils.getResponseBody(""));
	}  
}
