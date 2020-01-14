package com.hanfu.user.center.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.user.center.service.HfUserBalanceService;
import com.hanfu.user.center.utils.QRCodeUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Api
@RequestMapping("/user/balance")
@CrossOrigin
public class HfUserBalanceController {

    @Autowired
    HUserBalanceMapper hUserBalanceMapper;

    @Autowired
    HfUserBalanceService hfUserBalanceService;

	@RequestMapping(value = "/setCode",method = RequestMethod.GET)
	@ApiOperation(value = "生成二维码",notes = "生成二维码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "总额", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> setCode(@RequestParam(required = true,defaultValue = "") Integer userId,
												 @RequestParam(required = true,defaultValue = "") Integer hfBalance,
												 @RequestParam(required = true,defaultValue = "") Integer total,
												 HttpServletResponse response) throws JSONException, IOException {


		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		if(hfBalance>=total){
			// 设置响应流信息
			response.setContentType("image/jpg");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			OutputStream stream = response.getOutputStream();

			//type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
			String content = userId+" "+total+" "+hfBalance;
			//获取一个二维码图片
			BitMatrix bitMatrix = QRCodeUtils.createCode(content);
			//以流的形式输出到前端
			MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);

//			hfUserBalanceService.balanceCutTotal(userId,hfBalance,total);
			return builder.body(ResponseUtils.getResponseBody("支付成功"));
		}else{
			return builder.body(ResponseUtils.getResponseBody("余额不足,请充值"));
		}
	}

	@RequestMapping(value = "/balancePay",method = RequestMethod.GET)
	@ApiOperation(value = "小程序用户余额支付",notes = "小程序用户余额支付")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "总额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "code", value = "判断二维码是否被扫", required = true, type = "Integer"),
	})
	public ResponseEntity<JSONObject> balancePay(@RequestParam(required = true,defaultValue = "") Integer userId,
												 @RequestParam(required = true,defaultValue = "") Integer hfBalance,
												 @RequestParam(required = true,defaultValue = "") Integer total,
												 @RequestParam(required = true,defaultValue = "") Integer code) throws JSONException, IOException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if(code==1){
			System.out.println("二维码已经被扫");
			hfUserBalanceService.balanceCutTotal(userId,hfBalance,total);
			return builder.body(ResponseUtils.getResponseBody("支付成功"));
		}else{
			System.out.println("二维码未被扫描");
			return builder.body(ResponseUtils.getResponseBody("二维码未被扫描"));
		}
	}
}