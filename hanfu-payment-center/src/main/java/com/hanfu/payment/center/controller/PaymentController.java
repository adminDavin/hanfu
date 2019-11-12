package com.hanfu.payment.center.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hanfu.payment.center.request.AlipayConfig;
import com.hanfu.payment.center.request.AlipayRefund;
import com.hanfu.payment.center.service.AlipayService;
import com.hanfu.payment.center.service.WeChatService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation; 
@CrossOrigin
@RestController
@RequestMapping("/payment")
@Api
public class PaymentController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AlipayService alipayService;
	@Autowired
	private WeChatService weChatService;

	@ApiOperation(value = "支付流程", notes = "支付流程")
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "bossId", value = "商品实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> payment(@RequestParam Integer bossId,Integer  orderId, Integer amount)
			throws JSONException {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		String pay = alipayService.getAliPayOrderStr(bossId,orderId,amount);
		return builder.body(ResponseUtils.getResponseBody(pay));	
	}

	@ApiOperation(value = "退款流程", notes = "退款流程")
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "bossId", value = "商品实体id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> refund(@RequestParam Integer bossId)
			throws JSONException, Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "UTF-8",
				AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA");
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		AlipayRefund alipayRefund= new AlipayRefund();
		alipayRefund.setOut_trade_no("316594250940");//这个是商户的订单号
		alipayRefund.setTrade_no("2017091221001004040242043928");//这个是支付宝的订单号
		alipayRefund.setRefund_amount("0.5");//退款金额
		alipayRefund.setRefund_reason("正常退款");//退款说明
		alipayRefund.setOut_request_no("HZ01RF001");
		alipayRefund.setOperator_id("OP001");
		alipayRefund.setStore_id("NJ_S_001");
		alipayRefund.setTerminal_id("NJ_T_001");//request.setBizContent(JSONUtil.simpleObjectToJsonStr(alipayRefund));
		request.setBizContent(JSONObject.toJSONString(alipayRefund));//2个都可以，这个参数的顺序 不影响退款
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			System.out.println("调用成功");
		} else {	
			System.out.println("调用失败");
		}
		return builder.body(ResponseUtils.getResponseBody("退款成功"));
	}
	/**
     * 
     * 说明：单笔转账到支付宝账户
     * @param out_biz_no  商户转账唯一订单号
     * @param payee_type 收款方账户类型  (1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。)
     * @param payee_account 收款方账户
     * @param amount 转账金额
     * @param payer_show_name 付款方姓名
     * @param payee_real_name 收款方真实姓名
     * @param remark 转账备注
     */
	@RequestMapping("/transferAccounts")
	public void transferAccounts(String out_biz_no,String payee_type,String payee_account,String amount,String payer_show_name,String payee_real_name,String remark) {
		//填写自己创建的app的对应参数
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
				AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "UTF-8",
				AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA");
		AlipayFundTransToaccountTransferRequest  transferAccounts_request = new AlipayFundTransToaccountTransferRequest();
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		model.setOutBizNo(out_biz_no);
		model.setPayeeType(payee_type);
		model.setPayeeAccount(payee_account);
		model.setAmount(amount);
		model.setPayerShowName(payer_show_name);
		model.setPayeeRealName(payee_real_name);
		model.setRemark(remark);
		transferAccounts_request.setBizModel(model);
		try {
			AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferAccounts_request);
			if(response.isSuccess()){
				System.out.println(response.getBody());

			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

	}        
}
