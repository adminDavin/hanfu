package com.hanfu.payment.center.AppPaymentService;

import com.alipay.api.AlipayApiException;
import com.hanfu.payment.center.tool.ResultMap;


import javax.servlet.http.HttpServletRequest;

/**
 * 支付宝服务接口
 *
 * Author:
 * Date:2019/8/1
 * Description:
 */
public interface AlipayService {

    /**
     * @Description: 创建支付宝订单
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param body: 订单描述
     * @Author:
     * @Date: 2019/8/1
     * @return
     */
    String createOrder(String orderNo, double amount, String body) throws AlipayApiException;

    /**
     * @Description:
     * @param tradeStatus: 支付宝交易状态
     * @param orderNo: 订单编号
     * @param tradeNo: 支付宝订单号
     * @Author:
     * @Date: 2019/8/1
     * @return
     */
    boolean notify(String tradeStatus, String orderNo, String tradeNo);

    /**
     * @Description: 校验签名
     * @param request
     * @Author:
     * @Date: 2019/8/1
     * @return
     */
    boolean rsaCheckV1(HttpServletRequest request);

    /**
     * @Description: 退款
     * @param orderNo : 订单编号
     * @param amount : 实际支付金额
     * @param refundReason : 退款原因
     * @Author: XCK
     * @Date: 2019/8/6
     * @return
     */
    ResultMap refund(String orderNo, double amount, String refundReason);
}
