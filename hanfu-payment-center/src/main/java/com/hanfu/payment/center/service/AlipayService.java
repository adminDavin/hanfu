package com.hanfu.group.center.service;

import javax.servlet.http.HttpServletRequest;

public interface AlipayService {

	String getAliPayOrderStr(Integer bossId, Integer orderId, Integer amount);

	boolean rsaCheckV1(HttpServletRequest request);

	boolean notify(String tradeStatus, String outTradeNo, String tradeNo);

	Object refund(Integer orderId, Double amount, String refundReason);

}
