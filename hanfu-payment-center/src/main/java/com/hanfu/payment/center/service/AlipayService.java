package com.hanfu.payment.center.service;

public interface AlipayService {

	String getAliPayOrderStr(Integer bossId, Integer orderId, Integer amount);


}
