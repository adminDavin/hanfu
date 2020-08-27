package com.hanfu.payment.center.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.hanfu.payment.center.AppPaymentService.AlipayService;
import com.hanfu.payment.center.config.AlipayConfig;
import com.hanfu.payment.center.dao.*;
import com.hanfu.payment.center.model.*;
import com.hanfu.payment.center.tool.ResultMap;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hanfu.payment.center.config.MiniProgramConfig;
import com.hanfu.payment.center.manual.dao.HfOrderDao;
import com.hanfu.payment.center.manual.dao.UserMemberDao;
import com.hanfu.payment.center.manual.model.HfOrderDisplay;
import com.hanfu.payment.center.manual.model.HfUser;
import com.hanfu.payment.center.manual.model.OrderStatus;
import com.hanfu.payment.center.manual.model.OrderTypeEnum;
import com.hanfu.payment.center.manual.model.PaymentTypeEnum;
import com.hanfu.payment.center.manual.model.TansactionFlowStatusEnum;
import com.hanfu.payment.center.manual.model.UserMemberInfo;
import com.hanfu.payment.center.util.PayUtil;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

@CrossOrigin
@RestController
@RequestMapping("/hf-payment/")
@Api
public class PaymentOrderController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HfOrderDao hfOrderDao;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private HfTansactionFlowMapper hfTansactionFlowMapper;

	@Autowired
	private HfUserBalanceMapper hfUserBalanceMapper;
	
	@Autowired
	private HfIntegralMapper hfIntegralMapper;

	@Autowired
	HttpServletRequest req;

	@Autowired
	private HfOrderMapper hfOrderMapper;
	
	@Autowired
	private UserMemberDao userMemberDao;
	
	@Autowired
	private HfUserMemberMapper hfUserMemberMapper;
	
	@Autowired
	private HfBalanceDetailMapper hfBalanceDetailMapper;
	
	@Autowired
	private HfLevelDescribeMapper hfLevelDescribeMapper;
	
	@Autowired
	private HfUserPrivilegeMapper hfUserPrivilegeMapper;
	@Value("${myspcloud.item3.url3}")
	private String itemUrl3;
	@Value("${myspcloud.item2.url2}")
	private String itemUrl2;
	@Value("${myspcloud.item4.url4}")
	private String itemUrl4;
	@Autowired
	private HfOrderDetailMapper hfOrderDetailMapper;
	@Autowired
	private StoneChargeOffMapper stoneChargeOffMapper;
	@Autowired
	private StoneBalanceMapper stoneBalanceMapper;
	@Autowired
    private PayOrderMapper payOrderMapper;
	@Autowired
	private MiniProgramConfig miniProgramConfig;
	@Autowired
	private AlipayService alipayService;
	@Autowired
	private AlipayConfig alipayConfig;

	@ApiOperation(value = "支付订单", notes = "")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> payment(Integer userId,Integer payOrderId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//		HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(outTradeNo);
//		Integer.valueOf((String) req.getServletContext().getAttribute("bossId"))

		HfUser hfUser = hfOrderDao.selectHfUser(userId);
        PayOrder payOrder= payOrderMapper.selectByPrimaryKey(payOrderId);
        HfOrderExample hfOrderExample = new HfOrderExample();
        hfOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());

		Map<String, String> resp = null;
		if (PaymentTypeEnum.getPaymentTypeEnum(hfOrderMapper.selectByExample(hfOrderExample).get(0).getPaymentName()).equals(PaymentTypeEnum.WECHART)) {
			miniProgramConfig.setBossId(Integer.valueOf((String) req.getServletContext().getAttribute("bossId")));
			resp = wxPay(hfUser, payOrder);
		} else if(PaymentTypeEnum.getPaymentTypeEnum(hfOrderMapper.selectByExample(hfOrderExample).get(0).getPaymentName()).equals(PaymentTypeEnum.BALANCE)){
			resp = balancePay(hfUser, payOrder);
		} else if (PaymentTypeEnum.getPaymentTypeEnum(hfOrderMapper.selectByExample(hfOrderExample).get(0).getPaymentName()).equals(PaymentTypeEnum.APPCHART)){
//			resp = AppCHPay(hfUser, payOrder);
		} else if(PaymentTypeEnum.getPaymentTypeEnum(hfOrderMapper.selectByExample(hfOrderExample).get(0).getPaymentName()).equals(PaymentTypeEnum.APPALIPAY)){
			alipayConfig.setBossId(Integer.valueOf((String) req.getServletContext().getAttribute("bossId")));
			resp = appalipay(hfUser, payOrder);
			System.out.println("app");
		}
		System.out.println(resp);
		return builder.body(ResponseUtils.getResponseBody(resp));
	}
//app支付宝支付
	private Map<String ,String> appalipay(HfUser hfUser,PayOrder payOrder) throws AlipayApiException {
		String orderStr = alipayService.createOrder(String.valueOf(payOrder.getId()), payOrder.getAmount(), "shihao");
		Map<String, String> resp = new HashMap<>();
		resp.put("data", orderStr);
		return resp;
	}
	@ApiOperation(value = "退款", notes = "退款")
	@PostMapping("/refunds")
	public ResultMap refunds(@ApiParam(value = "订单号") @RequestParam String orderNo,
							@ApiParam(value = "退款金额") @RequestParam double amount,
							@ApiParam(value = "退款原因") @RequestParam(required = false) String refundReason) {
		return alipayService.refund(orderNo, amount, refundReason);
	}
	/**
	 * 支付异步通知
	 * 接收到异步通知并验签通过后，一定要检查通知内容，
	 * 包括通知中的app_id、out_trade_no、total_amount是否与请求中的一致，并根据trade_status进行后续业务处理。
	 * https://docs.open.alipay.com/194/103296
	 */
	@RequestMapping("/notify")
	public String notify(HttpServletRequest request) {
		System.out.println("异步回调");
		// 验证签名
		boolean flag = alipayService.rsaCheckV1(request);
		if (flag) {
			String tradeStatus = request.getParameter("trade_status"); // 交易状态
			String outTradeNo = request.getParameter("out_trade_no"); // 商户订单号
			String tradeNo = request.getParameter("trade_no"); // 支付宝订单号
			System.out.println(tradeStatus);
			System.out.println(outTradeNo);
			System.out.println(tradeNo);
			/**
			 * 还可以从request中获取更多有用的参数，自己尝试
			 */
			boolean notify = alipayService.notify(tradeStatus, outTradeNo, tradeNo);
			if(notify){
				return "success";
			}
		}
		return "fail";
	}
	//
	private Map<String, String> balancePay(HfUser hfUser, PayOrder payOrder) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
//		Map<String, String> data = getWxPayData(config, hfUser.getAuthKey(), hfOrder.getOrderCode());
//		WXPay wxpay = new WXPay(config);
//		Map<String, String> resp = wxpay.unifiedOrder(data);


		HfUserBalanceExample hfUserBalanceExample = new HfUserBalanceExample();
		hfUserBalanceExample.createCriteria().andUserIdEqualTo(hfUser.getUserId()).andBalanceTypeEqualTo("rechargeAmount");
		List<HfUserBalance> hfUserBalance= hfUserBalanceMapper.selectByExample(hfUserBalanceExample);

		if (hfUserBalance.size()!=0&&payOrder.getAmount()<hfUserBalance.get(0).getHfBalance()){
				HfUserBalance hfUserBalance1 = new HfUserBalance();
				hfUserBalance1.setId(hfUserBalance.get(0).getId());
				hfUserBalance1.setModifyTime(LocalDateTime.now());
//				hfUserBalance1.setLastModifier(hfUser.getAuthKey());
				hfUserBalance1.setHfBalance(hfUserBalance.get(0).getHfBalance()-payOrder.getAmount());
				hfUserBalanceMapper.updateByPrimaryKeySelective(hfUserBalance1);
				HfBalanceDetail detail = new HfBalanceDetail();
				detail.setUserId(hfUser.getUserId());
				detail.setAmount(String.valueOf(payOrder.getAmount()));
				detail.setPaymentName("消费");
				detail.setCreateTime(LocalDateTime.now());
				detail.setModifyTime(LocalDateTime.now());
				detail.setIsDeleted((byte) 0);
				hfBalanceDetailMapper.insert(detail);
		} else {
			throw new Exception("return_msg");
		}
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, String> wxPay(HfUser hfUser, PayOrder payOrder) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
		Map<String, String> data = getWxPayData(miniProgramConfig, hfUser.getAuthKey(), String.valueOf(payOrder.getId()),payOrder.getAmount());
		logger.info(JSONObject.toJSONString(data));
		WXPay wxpay = new WXPay(miniProgramConfig);
		Map<String, String> resp = wxpay.unifiedOrder(data);
		logger.info(JSONObject.toJSONString(resp));
		if ("SUCCESS".equals(resp.get("return_code"))) {
			Map<String, String> reData = new HashMap<>();
			reData.put("appId", miniProgramConfig.getAppID());
			reData.put("nonceStr", resp.get("nonce_str"));
			String newPackage = "prepay_id=" + resp.get("prepay_id");
			reData.put("package", newPackage);
			reData.put("signType", "MD5");
			reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

			String newSign = WXPayUtil.generateSignature(reData, miniProgramConfig.getKey());
			resp.put("paySign", newSign);
			resp.put("package", reData.get("package"));
			resp.put("signType", reData.get("signType"));
			resp.put("timeStamp", reData.get("timeStamp"));
			recordTransactionFlow(hfUser, payOrder, data, reData);
			return resp;
		} else {
			throw new Exception(resp.get("return_msg"));
		}

	}

	@ApiOperation(value = "退款订单", notes = "")
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "outTradeNo", value = "订单id", required = true, type = "orderCode"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> refund( Integer userId,Integer payOrderId,String orderCode) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		System.out.println(req.getServletContext().getAttribute("bossId"));
		miniProgramConfig.setBossId((Integer) req.getServletContext().getAttribute("bossId"));
//		alipayConfig.setBossId(2);
		HfOrderDisplay hfOrder = new HfOrderDisplay();
		hfOrder = hfOrderDao.selectHfOrderbyCode(orderCode);
		if (orderCode!=null){
			MultiValueMap<String, Object> paramMap2 = new LinkedMultiValueMap<>();
			paramMap2.add("stoneId",hfOrder.getStoneId());
			paramMap2.add("balanceType","rechargeAmount");
			paramMap2.add("money",hfOrder.getAmount());
			paramMap2.add("type", "-1");
			restTemplate.postForObject(itemUrl2,paramMap2,JSONObject.class);
		}
		List<HfOrder> hfOrderList = new ArrayList<>();
		if (payOrderId!=null){
			HfOrderExample hfOrderExample = new HfOrderExample();
			hfOrderExample.createCriteria().andPayOrderIdEqualTo(payOrderId);
			hfOrderList= hfOrderMapper.selectByExample(hfOrderExample);
		}
		PayOrder payOrder = payOrderMapper.selectByPrimaryKey(payOrderId);
		HfUser hfUser = hfOrderDao.selectHfUser(userId);
		System.out.println(hfOrderList.get(0).getPaymentName());
		if (hfOrderList.get(0).getPaymentName().equals("balance") && hfOrderList.get(0).getPaymentType().equals(0)) {
			HfUserBalanceExample hfUserBalanceExample = new HfUserBalanceExample();
			hfUserBalanceExample.createCriteria().andUserIdEqualTo(userId).andBalanceTypeEqualTo("rechargeAmount");
			List<HfUserBalance> hfUserBalances = hfUserBalanceMapper.selectByExample(hfUserBalanceExample);
                 HfUserBalance hfUserBalance = new HfUserBalance();
                 if (hfOrder!=null){
					 hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance()+hfOrder.getAmount());
				 }else {
					 hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance()+payOrder.getAmount());
				 }
                 hfUserBalance.setModifyTime(LocalDateTime.now());
                 hfUserBalance.setId(hfUserBalances.get(0).getId());
                 hfUserBalanceMapper.updateByPrimaryKeySelective(hfUserBalance);
                 HfBalanceDetail detail = new HfBalanceDetail();
 				detail.setUserId(hfUser.getUserId());
 				if (hfOrder!=null){
					detail.setAmount(String.valueOf(hfOrder.getAmount()));
				}else {
					detail.setAmount(String.valueOf(payOrder.getAmount()));
				}
 				detail.setPaymentName("退款");
 				detail.setCreateTime(LocalDateTime.now());
 				detail.setModifyTime(LocalDateTime.now());
 				detail.setIsDeleted((byte) 0);
 				hfBalanceDetailMapper.insert(detail);
			return builder.body(ResponseUtils.getResponseBody(0));
		}
		if (hfOrderList.get(0).getPaymentName().equals("wechart") && hfOrderList.get(0).getPaymentType().equals(0)){
//			MiniProgramConfig config = new MiniProgramConfig();
		WXPay wxpay = new WXPay(miniProgramConfig);
		Map<String, String> data = new HashMap<>();
		data.put("appid", miniProgramConfig.getAppID());
		data.put("mch_id", miniProgramConfig.getMchID());
		data.put("device_info", req.getRemoteHost());
		data.put("fee_type", "CNY");
		data.put("total_fee", String.valueOf(payOrder.getAmount()));

		data.put("spbill_create_ip", req.getRemoteAddr());
		data.put("notify_url", "https://www.tjsichuang.cn:1443/api/payment/hf-payment/handleWxpay");

		data.put("out_trade_no", String.valueOf(payOrder.getId()));
		data.put("op_user_id", miniProgramConfig.getMchID());
		data.put("refund_fee_type", "CNY");
		if (hfOrder!=null){
			System.out.println(hfOrder.getAmount());
			data.put("refund_fee", String.valueOf(hfOrder.getAmount()));
		}else {
			System.out.println(payOrder.getAmount());
			data.put("refund_fee", String.valueOf(payOrder.getAmount()));
		}

		data.put("out_refund_no", UUID.randomUUID().toString().replaceAll("-", ""));
		String sign = WXPayUtil.generateSignature(data, miniProgramConfig.getKey());
		data.put("sign", sign);
		logger.info(JSONObject.toJSONString(data));

		Map<String, String> resp = wxpay.refund(data);
		logger.info(JSONObject.toJSONString(resp));
		if ("SUCCESS".equals(resp.get("return_code"))) {
			LocalDateTime current = LocalDateTime.now();

			HfTansactionFlow t = new HfTansactionFlow();
			t.setAppId(miniProgramConfig.getAppID());
			t.setCreateDate(current);
			t.setDeviceInfo(req.getRemoteHost());
			t.setFeeType(data.get("refund_fee_type"));
			t.setMchId(miniProgramConfig.getMchID());
			t.setModifyDate(current);
			t.setOpenId(hfUser.getAuthKey());
			t.setOutTradeNo(data.get("out_trade_no"));
			t.setSpbillCreateIp(req.getRemoteAddr());
			t.setTotalFee(data.get("refund_fee"));
			t.setOutRefundNo(data.get("out_refund_no"));
			t.setTransactionType("rerundOrder");
			t.setHfStatus(TansactionFlowStatusEnum.COMPLETE.getStatus());
			t.setUserId(hfUser.getUserId());
		}

		return builder.body(ResponseUtils.getResponseBody(resp));
	}
	if(hfOrderList.get(0).getPaymentName().equals("appalipay") && hfOrderList.get(0).getPaymentType().equals(0)) {
		System.out.println("退款"+(hfOrder.getAmount().doubleValue())/100);
		System.out.println(hfOrder.getAmount());
			refunds(String.valueOf(payOrderId),((hfOrder.getAmount().doubleValue())/100),"原因");
			return builder.body(ResponseUtils.getResponseBody(2));
		}
		return builder.body(ResponseUtils.getResponseBody(1));
	}

	private Map<String, String> getWxPayData(MiniProgramConfig config, String openId, String orderCode,Integer Amount)
			throws Exception {
		Map<String, String> data = new HashMap<>();
		data.put("appid", config.getAppID());
		data.put("mch_id", config.getMchID());
		data.put("body", "订单支付");
		data.put("out_trade_no", orderCode);
		data.put("device_info", req.getRemoteHost());
		data.put("fee_type", "CNY");
		data.put("total_fee", String.valueOf(Amount));
		data.put("spbill_create_ip", req.getRemoteAddr());
		data.put("notify_url", "https://www.tjsichuang.cn:1443/api/payment/hf-payment/handleWxpay");
		data.put("trade_type", "JSAPI");
		data.put("openid", openId);
		String sign = WXPayUtil.generateSignature(data, config.getKey());
		data.put("sign", sign);
		logger.info(JSONObject.toJSONString(data));
		return data;
	}

	private void recordTransactionFlow(HfUser hfUser, PayOrder payOrder, Map<String, String> data,
			Map<String, String> reData) {
	    HfOrderExample hfOrderExample = new HfOrderExample();
	    hfOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());
	    List<HfOrder> hfOrders= hfOrderMapper.selectByExample(hfOrderExample);
		HfTansactionFlowExample e = new HfTansactionFlowExample();
		e.createCriteria().andTradeTypeEqualTo(hfOrders.get(0).getOrderType()).andOutTradeNoEqualTo(data.get("out_trade_no"))
				.andHfStatusEqualTo(TansactionFlowStatusEnum.PROCESS.getStatus());
		List<HfTansactionFlow> hfTansactionFlows = hfTansactionFlowMapper.selectByExample(e);

		if (hfTansactionFlows.isEmpty()) {
			HfTansactionFlow t = completeHfTansactionFlow(new HfTansactionFlow(), hfUser, payOrder, data, reData);
			hfTansactionFlowMapper.insertSelective(t);
		} else {
			HfTansactionFlow t = completeHfTansactionFlow(hfTansactionFlows.get(0), hfUser, payOrder, data, reData);
			hfTansactionFlowMapper.updateByPrimaryKey(t);
		}
	}

	private HfTansactionFlow completeHfTansactionFlow(HfTansactionFlow t, HfUser hfUser, PayOrder payOrder,
			Map<String, String> data, Map<String, String> reData) {
		LocalDateTime current = LocalDateTime.now();
		HfOrderExample hfOrderExample = new HfOrderExample();
		hfOrderExample.createCriteria().andPayOrderIdEqualTo(payOrder.getId());
		List<HfOrder> HfOrders= hfOrderMapper.selectByExample(hfOrderExample);
		t.setAppId(data.get("appid"));
		t.setCreateDate(current);
		t.setDeviceInfo(data.get("device_info"));
		t.setFeeType(data.get("fee_type"));
		t.setMchId(data.get("mch_id"));
		t.setModifyDate(current);
		t.setNotifyUrl(data.get("notify_url"));
		t.setOpenId(hfUser.getAuthKey());
		t.setOutTradeNo(data.get("out_trade_no"));
		t.setSigntype(reData.get("signType"));
		t.setSpbillCreateIp(data.get("spbill_create_ip"));
		t.setTotalFee(data.get("total_fee"));
		t.setTradeType(data.get("trade_type"));
		t.setTransactionType(HfOrders.get(0).getOrderType());
		t.setHfStatus(TansactionFlowStatusEnum.PROCESS.getStatus());
		t.setUserId(hfUser.getUserId());
		t.setWechartBody(data.get("body"));
		t.setWechartPackage("package");
		return t;
	}

	@ApiOperation(value = "完成支付", notes = "")
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "outTradeNo", value = "订单id", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "transactionType", value = "订单id", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "会员等级", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> completePaymentAfter(
			@RequestParam("transactionType") String transactionType, @RequestParam("userId") Integer userId,
			@RequestParam(required = false) Integer level,Integer payOrderId)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		PayOrder payOrder = payOrderMapper.selectByPrimaryKey(payOrderId);
		HfOrderExample hfOrderExample = new HfOrderExample();
		hfOrderExample.createCriteria().andPayOrderIdEqualTo(payOrderId);
		List<HfOrder> hfOrders= hfOrderMapper.selectByExample(hfOrderExample);
		for (HfOrder hfOrder1:hfOrders){
			HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(hfOrder1.getOrderCode());
			//流水状态
			StoneChargeOff stoneChargeOff = new StoneChargeOff();
			stoneChargeOff.setChargeOffState(1);
			StoneChargeOffExample stoneChargeOffExample = new StoneChargeOffExample();
			stoneChargeOffExample.createCriteria().andOrderIdEqualTo(hfOrder.getId());
			stoneChargeOffMapper.updateByExampleSelective(stoneChargeOff,stoneChargeOffExample);
			//
			MultiValueMap<String, Object> paramMap2 = new LinkedMultiValueMap<>();
			paramMap2.add("orderId",hfOrder.getId());
			restTemplate.postForObject(itemUrl3,paramMap2,JSONObject.class);
			if (PaymentTypeEnum.getPaymentTypeEnum(hfOrder.getPaymentName()).equals(PaymentTypeEnum.WECHART)) {
				HfTansactionFlowExample e = new HfTansactionFlowExample();
				e.createCriteria().andOutTradeNoEqualTo(String.valueOf(payOrder.getId()));
				List<HfTansactionFlow> hfTansactionFlows = hfTansactionFlowMapper.selectByExample(e);
				if (!hfTansactionFlows.isEmpty()) {
					HfTansactionFlow hfTansactionFlow = hfTansactionFlows.get(0);
					hfTansactionFlow.setModifyDate(LocalDateTime.now());
					hfTansactionFlow.setHfStatus(TansactionFlowStatusEnum.COMPLETE.getStatus());
					hfTansactionFlowMapper.updateByPrimaryKeySelective(hfTansactionFlow);

					if (OrderTypeEnum.RECHAEGE_ORDER.getOrderType().equals(hfOrder.getOrderType())) {
//					rechangeBalance(userId, Integer.valueOf(hfTansactionFlow.getTotalFee()),level);
						rechangeBalance(userId, Integer.valueOf(hfOrder.getAmount()),level);
						hfOrderDao.updateHfOrderStatus(hfOrder.getOrderCode(), OrderStatus.COMPLETE.getOrderStatus(),
								LocalDateTime.now());
					} else if (OrderTypeEnum.SHOPPING_ORDER.getOrderType().equals(hfOrder.getOrderType())) {
						hfOrderDao.updateHfOrderStatus(hfOrder.getOrderCode(), OrderStatus.COMPLETE.getOrderStatus(),
								LocalDateTime.now());
					} else {
						hfOrderDao.updateHfOrderStatus(hfOrder.getOrderCode(), OrderStatus.PROCESS.getOrderStatus(),
								LocalDateTime.now());
						HfOrderDetail hfOrderDetail = new HfOrderDetail();
						hfOrderDetail.setHfStatus(OrderStatus.PROCESS.getOrderStatus());
						System.out.println(OrderStatus.PROCESS.getOrderStatus());
						Example example = new Example(HfOrderDetail.class);
						Example.Criteria criteria = example.createCriteria();
						criteria.andEqualTo("orderId",hfOrder.getId());
						hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,example);
					}
//					return builder.body(ResponseUtils.getResponseBody(hfTansactionFlow));
				} else {
					throw new Exception("交易柳树不存在, 或者已完成支付");
				}
			} else {
//			Integer result = paymentBalance(userId, hfOrder.getAmount());
//			if (result == -1) {
//				return builder.body(ResponseUtils.getResponseBody(-1));
//			}
				if (OrderTypeEnum.SHOPPING_ORDER.getOrderType().equals(hfOrder.getOrderType())) {
					hfOrderDao.updateHfOrderStatus(hfOrder.getOrderCode(), OrderStatus.COMPLETE.getOrderStatus(),
							LocalDateTime.now());
				} else if (OrderTypeEnum.RECHAEGE_ORDER.getOrderType().equals(hfOrder.getOrderType())) {
//					rechangeBalance(userId, Integer.valueOf(hfTansactionFlow.getTotalFee()),level);
					rechangeBalance(userId, Integer.valueOf(hfOrder.getAmount()),level);
					hfOrderDao.updateHfOrderStatus(hfOrder.getOrderCode(), OrderStatus.COMPLETE.getOrderStatus(),
							LocalDateTime.now());
				}else {
					hfOrderDao.updateHfOrderStatus(hfOrder1.getOrderCode(), OrderStatus.PROCESS.getOrderStatus(), LocalDateTime.now());
					PayOrder order = payOrderMapper.selectByPrimaryKey(hfOrder1.getPayOrderId());
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
					map.add("orderId", hfOrder1.getOrderCode().substring(0, 7));
					map.add("total", String.valueOf(hfOrder1.getAmount()));
					map.add("userId", String.valueOf(hfOrder1.getUserId()));
					map.add("type", "orderCreate");
					map.add("bossId", order.getBossId());
					restTemplate.postForObject(itemUrl4,map,JSONObject.class);
					HfOrderDetail hfOrderDetail = new HfOrderDetail();
					hfOrderDetail.setHfStatus(OrderStatus.PROCESS.getOrderStatus());
					System.out.println(OrderStatus.PROCESS.getOrderStatus());
					Example example = new Example(HfOrderDetail.class);
					Example.Criteria criteria = example.createCriteria();
					criteria.andEqualTo("orderId",hfOrder.getId());
					hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,example);
				}

//				return builder.body(ResponseUtils.getResponseBody(hfOrder));
			}
		}

		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	
	@ApiOperation(value = "测试", notes = "测试")
	@RequestMapping(value = "/qqqqqqc", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> qqqqqqc(Integer userId, Integer totalFee, Integer level)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		rechangeBalance(userId,totalFee,level);
		return builder.body(ResponseUtils.getResponseBody(null));
	}
	
	
	private void rechangeBalance(Integer userId, Integer totalFee ,Integer level) {
		if(level == null) {
			HfBalanceDetail detail = new HfBalanceDetail();
			detail.setUserId(userId);
			detail.setAmount(String.valueOf(totalFee));
			detail.setPaymentName("充值");
			detail.setCreateTime(LocalDateTime.now());
			detail.setModifyTime(LocalDateTime.now());
			detail.setIsDeleted((byte) 0);
			hfBalanceDetailMapper.insert(detail);
			
			HfUserBalanceExample example = new HfUserBalanceExample();
			example.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo((short) 0)
					.andBalanceTypeEqualTo("rechargeAmount");
			List<HfUserBalance> hfUserBalance = hfUserBalanceMapper.selectByExample(example);
			if (hfUserBalance.isEmpty()) {
				HfUserBalance userBalance = new HfUserBalance();
				userBalance.setBalanceType("rechargeAmount");
				userBalance.setCreateTime(LocalDateTime.now());
				userBalance.setHfBalance(Integer.valueOf(totalFee));
				userBalance.setLastModifier(String.valueOf(userId));
				userBalance.setModifyTime(LocalDateTime.now());
				userBalance.setUserId(userId);
				hfUserBalanceMapper.insertSelective(userBalance);
			} else {
				HfUserBalance userBalance = hfUserBalance.get(0);
				userBalance.setHfBalance(Integer.valueOf(totalFee) + userBalance.getHfBalance());
				userBalance.setModifyTime(LocalDateTime.now());
				userBalance.setLastModifier(String.valueOf(userId));
				hfUserBalanceMapper.updateByPrimaryKey(userBalance);
			}
			example.clear();
			example.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo((short) 0)
					.andBalanceTypeEqualTo("integral");
			hfUserBalance = hfUserBalanceMapper.selectByExample(example);
			if (hfUserBalance.isEmpty()) {
				HfUserBalance userBalance = new HfUserBalance();
				userBalance.setBalanceType("integral");
				userBalance.setCreateTime(LocalDateTime.now());
				userBalance.setHfBalance(Integer.valueOf(totalFee));
				userBalance.setUserId(userId);
				userBalance.setLastModifier(String.valueOf(userId));
				userBalance.setModifyTime(LocalDateTime.now());
				hfUserBalanceMapper.insertSelective(userBalance);
			} else {
				HfUserBalance userBalance = hfUserBalance.get(0);
				userBalance.setHfBalance(Integer.valueOf(totalFee) + userBalance.getHfBalance());
				userBalance.setModifyTime(LocalDateTime.now());
				userBalance.setLastModifier(String.valueOf(userId));
				hfUserBalanceMapper.updateByPrimaryKey(userBalance);
			}
			HfIntegral hfIntegral = new HfIntegral();
			hfIntegral.setUserId(userId);
			hfIntegral.setAmount(totalFee);
			hfIntegral.setPaymentName("充值");
			hfIntegral.setCreateTime(LocalDateTime.now());
			hfIntegral.setModifyTime(LocalDateTime.now());
			hfIntegral.setIsDeleted((byte) 0);
			hfIntegralMapper.insert(hfIntegral);
		}
		if(level != null) {
			HfUserMember member = new HfUserMember();
			member.setLevelId(level);
			member.setUserId(userId);
			member.setCreateTime(LocalDateTime.now());
			member.setModifyTime(LocalDateTime.now());
			member.setIsDeleted((byte) 0);
			hfUserMemberMapper.insert(member);
			HfLevelDescribeExample describeExample = new HfLevelDescribeExample();
			describeExample.createCriteria().andLevelIdEqualTo(level);
			List<HfLevelDescribe> describes = hfLevelDescribeMapper.selectByExample(describeExample);
			for (int j = 0; j < describes.size(); j++) {
				HfUserPrivilege privilege = new HfUserPrivilege();
				privilege.setUserId(userId);
				privilege.setPrivilegeId(describes.get(j).getId());
				hfUserPrivilegeMapper.insert(privilege);
			}
		}
		
	}

//	private Integer paymentBalance(Integer userId, Integer totalFee) {
//		HfUserBalanceExample example = new HfUserBalanceExample();
//		example.createCriteria().andUserIdEqualTo(userId).andIsDeletedEqualTo((short) 0)
//				.andBalanceTypeEqualTo("rechargeAmount");
//		List<HfUserBalance> hfUserBalance = hfUserBalanceMapper.selectByExample(example);
//		if (hfUserBalance.isEmpty()) {
//            HfUserBalance userBalance = new HfUserBalance();
//            userBalance.setBalanceType("rechargeAmount");
//            userBalance.setCreateTime(LocalDateTime.now());
//            userBalance.setHfBalance(Integer.valueOf(totalFee));
//            userBalance.setLastModifier(String.valueOf(userId));
//            userBalance.setModifyTime(LocalDateTime.now());
//            userBalance.setUserId(userId);
//            hfUserBalanceMapper.insertSelective(userBalance);
//			return -1;
//		} else {
//			HfUserBalance userBalance = hfUserBalance.get(0);
//			userBalance.setHfBalance(userBalance.getHfBalance() - totalFee);
//			userBalance.setModifyTime(LocalDateTime.now());
//			userBalance.setLastModifier(String.valueOf(userId));
//			hfUserBalanceMapper.updateByPrimaryKey(userBalance);
//			return 1;
//		}
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "訂單支付后處理", notes = "訂單支付后處理")
	@RequestMapping(value = "/handleWxpay", method = RequestMethod.GET)
	public void refund(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
		miniProgramConfig.setBossId((Integer) req.getServletContext().getAttribute("bossId"));
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		// sb为微信返回的xml
		String notityXml = sb.toString();
		String resXml = "";
		logger.info("接收到的报文：" + notityXml);

		Map map = PayUtil.doXMLParse(notityXml);

		String returnCode = (String) map.get("return_code");
		if ("SUCCESS".equals(returnCode)) {
			// hfOrderDao.updateHfOrderStatus(out_trade_no,
			// OrderStatus.PROCESS.getOrderStatus(), LocalDateTime.now());
			Map<String, String> validParams = PayUtil.paraFilter(map); // 回调验签时需要去除sign和空值参数
			String validStr = PayUtil.createLinkString(validParams);// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			String sign = PayUtil.sign(validStr, miniProgramConfig.getKey(), "utf-8").toUpperCase();// 拼装生成服务器端验证的签名
			// 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了

			// 根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
			if (sign.equals(map.get("sign"))) {
				/** 此处添加自己的业务逻辑代码start **/
				// bla bla bla....
				/** 此处添加自己的业务逻辑代码end **/
				// 通知微信服务器已经支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			} else {
				System.out.println("微信支付回调失败!签名不一致");
			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		System.out.println(resXml);
		logger.info("微信支付回调数据结束");

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}
}
