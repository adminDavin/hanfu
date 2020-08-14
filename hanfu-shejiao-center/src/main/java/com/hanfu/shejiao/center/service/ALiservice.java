package com.hanfu.shejiao.center.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ALiservice {
	 
    /**
     *  统一下单
     * @param total_fee 总价
     * @param oid 订单ID
     * @return
     * @throws Exception
     */
    Map pay(String total_fee, String oid) throws Exception;
 
    /**
     * 支付回调
     * @param request
     * @param response
     */
    void paymentCallback(HttpServletRequest request, HttpServletResponse response);
}

