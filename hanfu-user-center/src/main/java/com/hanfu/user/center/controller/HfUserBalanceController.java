package com.hanfu.user.center.controller;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hanfu.user.center.service.HfUserBalanceService;
import com.hanfu.user.center.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import java.util.concurrent.TimeUnit;

@RestController
@Api
@RequestMapping("/user/balance")
@CrossOrigin
public class HfUserBalanceController {

	@Autowired
	HUserBalanceMapper hUserBalanceMapper;

	@Autowired
	HfUserBalanceService hfUserBalanceService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

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

	@RequestMapping(value = "/setCode",method = RequestMethod.GET)
	@ApiOperation(value = "生成二维码",notes = "生成二维码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "商品价格", required = true, type = "Integer")
	})
	public ResponseEntity<JSONObject> setCode(@RequestParam(required = true,defaultValue = "") Integer userId,
											  @RequestParam(required = true,defaultValue = "") Integer hfBalance,
											  @RequestParam(required = true,defaultValue = "") Integer total,
											  HttpServletResponse response) throws JSONException, IOException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		int random = (int)((Math.random()*9+1)*100000);//六个随机数字

		//System.out.println(random);

		stringRedisTemplate.opsForValue().set(userId+"",random+"",60, TimeUnit.SECONDS);

		// 设置响应流信息
		response.setContentType("image/jpg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream stream = response.getOutputStream();

		//type是1，生成活动详情、报名的二维码，type是2，生成活动签到的二维码
		String content = "用户的ID是:"+userId+",用户的余额是:"+hfBalance+",商品价格:"+total;

		//加密String encode = MD5.encode(content);

		//获取一个二维码图片
		BitMatrix bitMatrix = QRCodeUtils.createCode(content);

		//以流的形式输出到前端
		MatrixToImageWriter.writeToStream(bitMatrix , "jpg" , stream);

		return builder.body(ResponseUtils.getResponseBody("二维码生成成功,六十秒钟后失效"));
	}


	@RequestMapping(value = "/deduction",method = RequestMethod.GET)
	@ApiOperation(value = "扣款请求",notes = "扣款请求")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "商品价格", required = true, type = "Integer"),
	})
	public ResponseEntity<JSONObject> deduction(@RequestParam(required = true,defaultValue = "") Integer userId,
												 @RequestParam(required = true,defaultValue = "") Integer hfBalance,
												 @RequestParam(required = true,defaultValue = "") Integer total) throws JSONException, IOException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		String newUserId = userId+"";

		String value = stringRedisTemplate.opsForValue().get(newUserId);

		if(null!=value){

			if(hfBalance>=total){
				hfUserBalanceService.balanceCutTotal(userId,hfBalance,total);
				stringRedisTemplate.delete(newUserId);
				return builder.body(ResponseUtils.getResponseBody("付款成功，二维码已失效"));
			}else{
				return builder.body(ResponseUtils.getResponseBody("余额不足，请充值"));
			}

		}else{
			return builder.body(ResponseUtils.getResponseBody("该二维码已经失效"));
		}
	}


	@RequestMapping(value = "/balancePay",method = RequestMethod.GET)
	@ApiOperation(value = "余额支付",notes = "余额支付")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用戶id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "hfBalance", value = "用户余额", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "total", value = "商品价格", required = true, type = "Integer"),
	})
	public ResponseEntity<JSONObject> balancePay(@RequestParam(required = true,defaultValue = "") Integer userId,
												 @RequestParam(required = true,defaultValue = "") Integer hfBalance,
												 @RequestParam(required = true,defaultValue = "") Integer total) throws JSONException, IOException {

		BodyBuilder builder = ResponseUtils.getBodyBuilder();

		if(hfBalance>=total){
			hfUserBalanceService.balanceCutTotal(userId,hfBalance,total);
			return builder.body(ResponseUtils.getResponseBody("付款成功"));
		}else{
			return builder.body(ResponseUtils.getResponseBody("余额不足，请充值"));
		}
	}
}