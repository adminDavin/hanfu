package com.hanfu.payment.center.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hanfu.payment.center.dao.*;
import com.hanfu.payment.center.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
	@Autowired
	private HfOrderDetailMapper hfOrderDetailMapper;
	@Autowired
	private StoneChargeOffMapper stoneChargeOffMapper;
	@Autowired
	private StoneBalanceMapper stoneBalanceMapper;



	@ApiOperation(value = "支付订单", notes = "")
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "outTradeNo", value = "订单id", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer") })
	public ResponseEntity<JSONObject> payment(@RequestParam String outTradeNo, Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(outTradeNo);
		HfUser hfUser = hfOrderDao.selectHfUser(userId);

		Map<String, String> resp = null;
		if (PaymentTypeEnum.getPaymentTypeEnum(hfOrder.getPaymentName()).equals(PaymentTypeEnum.WECHART)) {
			resp = wxPay(hfUser, hfOrder);
		} else {
			resp = balancePay(hfUser, hfOrder);
		}
		return builder.body(ResponseUtils.getResponseBody(resp));
	}

	private Map<String, String> balancePay(HfUser hfUser, HfOrderDisplay hfOrder) throws Exception {
//		MiniProgramConfig config = new MiniProgramConfig();
//		Map<String, String> data = getWxPayData(config, hfUser.getAuthKey(), hfOrder.getOrderCode());
//		WXPay wxpay = new WXPay(config);
//		Map<String, String> resp = wxpay.unifiedOrder(data);


		HfUserBalanceExample hfUserBalanceExample = new HfUserBalanceExample();
		hfUserBalanceExample.createCriteria().andUserIdEqualTo(hfUser.getUserId()).andBalanceTypeEqualTo("rechargeAmount");
		List<HfUserBalance> hfUserBalance= hfUserBalanceMapper.selectByExample(hfUserBalanceExample);
		HfOrderExample hfOrderExample = new HfOrderExample();
		hfOrderExample.createCriteria().andOrderCodeEqualTo(hfOrder.getOrderCode());
		List<HfOrder> hfOrders= hfOrderMapper.selectByExample(hfOrderExample);
		if (hfUserBalance.size()!=0&&hfOrders.get(0).getAmount()<hfUserBalance.get(0).getHfBalance()){
				HfUserBalance hfUserBalance1 = new HfUserBalance();
				hfUserBalance1.setId(hfUserBalance.get(0).getId());
				hfUserBalance1.setModifyTime(LocalDateTime.now());
//				hfUserBalance1.setLastModifier(hfUser.getAuthKey());
				hfUserBalance1.setHfBalance(hfUserBalance.get(0).getHfBalance()-hfOrders.get(0).getAmount());
				hfUserBalanceMapper.updateByPrimaryKeySelective(hfUserBalance1);
				HfBalanceDetail detail = new HfBalanceDetail();
				detail.setUserId(hfUser.getUserId());
				detail.setAmount(String.valueOf(hfOrders.get(0).getAmount()));
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

	private Map<String, String> wxPay(HfUser hfUser, HfOrderDisplay hfOrder) throws Exception {
		MiniProgramConfig config = new MiniProgramConfig();
		Map<String, String> data = getWxPayData(config, hfUser.getAuthKey(), hfOrder.getOrderCode(),hfOrder.getAmount());
		logger.info(JSONObject.toJSONString(data));

		WXPay wxpay = new WXPay(config);
		Map<String, String> resp = wxpay.unifiedOrder(data);
		logger.info(JSONObject.toJSONString(resp));
		if ("SUCCESS".equals(resp.get("return_code"))) {
			Map<String, String> reData = new HashMap<>();
			reData.put("appId", config.getAppID());
			reData.put("nonceStr", resp.get("nonce_str"));
			String newPackage = "prepay_id=" + resp.get("prepay_id");
			reData.put("package", newPackage);
			reData.put("signType", "MD5");
			reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

			String newSign = WXPayUtil.generateSignature(reData, config.getKey());
			resp.put("paySign", newSign);
			resp.put("package", reData.get("package"));
			resp.put("signType", reData.get("signType"));
			resp.put("timeStamp", reData.get("timeStamp"));
			recordTransactionFlow(hfUser, hfOrder, data, reData);
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
	public ResponseEntity<JSONObject> refund(String outTradeNo, Integer userId) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(outTradeNo);
		HfUser hfUser = hfOrderDao.selectHfUser(userId);
		if (hfOrder.getPaymentName().equals("balance") && hfOrder.getPaymentType().equals(0)) {
			HfUserBalanceExample hfUserBalanceExample = new HfUserBalanceExample();
			hfUserBalanceExample.createCriteria().andUserIdEqualTo(userId).andBalanceTypeEqualTo("rechargeAmount");
			List<HfUserBalance> hfUserBalances = hfUserBalanceMapper.selectByExample(hfUserBalanceExample);
                 HfUserBalance hfUserBalance = new HfUserBalance();
                 hfUserBalance.setHfBalance(hfUserBalances.get(0).getHfBalance()+hfOrder.getAmount());
                 hfUserBalance.setModifyTime(LocalDateTime.now());
                 hfUserBalance.setId(hfUserBalances.get(0).getId());
                 hfUserBalanceMapper.updateByPrimaryKeySelective(hfUserBalance);
                 HfBalanceDetail detail = new HfBalanceDetail();
 				detail.setUserId(hfUser.getUserId());
 				detail.setAmount(String.valueOf(hfOrder.getAmount()));
 				detail.setPaymentName("退款");
 				detail.setCreateTime(LocalDateTime.now());
 				detail.setModifyTime(LocalDateTime.now());
 				detail.setIsDeleted((byte) 0);
 				hfBalanceDetailMapper.insert(detail);
			return builder.body(ResponseUtils.getResponseBody(0));
		} else{
			MiniProgramConfig config = new MiniProgramConfig();
		WXPay wxpay = new WXPay(config);
		Map<String, String> data = new HashMap<>();
		data.put("appid", config.getAppID());
		data.put("mch_id", config.getMchID());
		data.put("device_info", req.getRemoteHost());
		data.put("fee_type", "CNY");
		data.put("total_fee", String.valueOf(hfOrder.getAmount()));
		data.put("spbill_create_ip", req.getRemoteAddr());
		data.put("notify_url", "https://www.tjsichuang.cn:1443/api/payment/hf-payment/handleWxpay");

		data.put("out_trade_no", hfOrder.getOrderCode());
		data.put("op_user_id", config.getMchID());
		data.put("refund_fee_type", "CNY");
		data.put("refund_fee", String.valueOf(hfOrder.getAmount()));
		data.put("out_refund_no", UUID.randomUUID().toString().replaceAll("-", ""));
		String sign = WXPayUtil.generateSignature(data, config.getKey());
		data.put("sign", sign);
		logger.info(JSONObject.toJSONString(data));

		Map<String, String> resp = wxpay.refund(data);
		logger.info(JSONObject.toJSONString(resp));
		if ("SUCCESS".equals(resp.get("return_code"))) {
			LocalDateTime current = LocalDateTime.now();

			HfTansactionFlow t = new HfTansactionFlow();
			t.setAppId(config.getAppID());
			t.setCreateDate(current);
			t.setDeviceInfo(req.getRemoteHost());
			t.setFeeType(data.get("refund_fee_type"));
			t.setMchId(config.getMchID());
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

	private void recordTransactionFlow(HfUser hfUser, HfOrderDisplay hfOrder, Map<String, String> data,
			Map<String, String> reData) {
		HfTansactionFlowExample e = new HfTansactionFlowExample();
		e.createCriteria().andTradeTypeEqualTo(hfOrder.getOrderType()).andOutTradeNoEqualTo(data.get("out_trade_no"))
				.andHfStatusEqualTo(TansactionFlowStatusEnum.PROCESS.getStatus());
		List<HfTansactionFlow> hfTansactionFlows = hfTansactionFlowMapper.selectByExample(e);

		if (hfTansactionFlows.isEmpty()) {
			HfTansactionFlow t = completeHfTansactionFlow(new HfTansactionFlow(), hfUser, hfOrder, data, reData);
			hfTansactionFlowMapper.insertSelective(t);
		} else {
			HfTansactionFlow t = completeHfTansactionFlow(hfTansactionFlows.get(0), hfUser, hfOrder, data, reData);
			hfTansactionFlowMapper.updateByPrimaryKey(t);
		}
	}

	private HfTansactionFlow completeHfTansactionFlow(HfTansactionFlow t, HfUser hfUser, HfOrderDisplay hfOrder,
			Map<String, String> data, Map<String, String> reData) {
		LocalDateTime current = LocalDateTime.now();

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
		t.setTransactionType(hfOrder.getOrderType());
		t.setHfStatus(TansactionFlowStatusEnum.PROCESS.getStatus());
		t.setUserId(hfUser.getUserId());
		t.setWechartBody(data.get("body"));
		t.setWechartPackage("package");
		return t;
	}

	@ApiOperation(value = "完成支付", notes = "")
	@RequestMapping(value = "/complete", method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "outTradeNo", value = "订单id", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "transactionType", value = "订单id", required = true, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "level", value = "会员等级", required = true, type = "Integer")})
	public ResponseEntity<JSONObject> completePaymentAfter(@RequestParam("outTradeNo") String outTradeNo,
			@RequestParam("transactionType") String transactionType, @RequestParam("userId") Integer userId,
			@RequestParam(required = false) Integer level)
			throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
		HfOrderDisplay hfOrder = hfOrderDao.selectHfOrderbyCode(outTradeNo);
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
			e.createCriteria().andOutTradeNoEqualTo(outTradeNo);
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
				return builder.body(ResponseUtils.getResponseBody(hfTansactionFlow));
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
				hfOrderDao.updateHfOrderStatus(outTradeNo, OrderStatus.PROCESS.getOrderStatus(), LocalDateTime.now());
				HfOrderDetail hfOrderDetail = new HfOrderDetail();
				hfOrderDetail.setHfStatus(OrderStatus.PROCESS.getOrderStatus());
				System.out.println(OrderStatus.PROCESS.getOrderStatus());
				Example example = new Example(HfOrderDetail.class);
				Example.Criteria criteria = example.createCriteria();
				criteria.andEqualTo("orderId",hfOrder.getId());
				hfOrderDetailMapper.updateByExampleSelective(hfOrderDetail,example);
			}

			return builder.body(ResponseUtils.getResponseBody(hfOrder));
		}
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
		MiniProgramConfig config = new MiniProgramConfig();

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
			String sign = PayUtil.sign(validStr, config.getKey(), "utf-8").toUpperCase();// 拼装生成服务器端验证的签名
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
