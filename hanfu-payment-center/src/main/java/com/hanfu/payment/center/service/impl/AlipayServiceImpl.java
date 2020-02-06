package com.hanfu.payment.center.service.impl;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hanfu.payment.center.request.AlipayConfig;
import com.hanfu.payment.center.request.AlipaymentOrder;
import com.hanfu.payment.center.service.AlipayService;

@Service("alipayService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class AlipayServiceImpl implements AlipayService {

    private AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.APPID,
            AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
            AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS"; //支付成功标识
    public static final String TRADE_CLOSED = "TRADE_CLOSED";//交易关闭

    @Override
    public String getAliPayOrderStr(Integer bossId, Integer orderId, Integer amount) {
        //最终返回加签之后的，app需要传给支付宝app的订单信息字符串
        String orderString = "";
        logger.info("==================支付宝下单,商户订单号为：" + orderId);
        //创建商户支付宝订单(因为需要记录每次支付宝支付的记录信息，单独存一个表跟商户订单表关联，以便以后查证)
        AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
        alipaymentOrder.setClubOrderId("bossId");//商家订单主键序列号
        alipaymentOrder.setOrderId("orderId");//商户订单号
        alipaymentOrder.setStatus((byte) 0);//交易状态
        alipaymentOrder.setAmount(amount);//订单金额
        alipaymentOrder.setRefundFee(amount);    //总退款金额
        try {
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            model.setBody("");                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setSubject("");                 //商品名称
            model.setOutTradeNo("");           //商户订单号(自动生成)
            model.setTimeoutExpress("30m");                  //交易超时时间
            model.setTotalAmount("amount");         //支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");              //销售产品码（固定值）
            ali_request.setBizModel(model);
            //            AlipayTradeAppPayRequest alipayment = null;
            //			logger.info("====================异步通知的地址为："+alipayment.getNotifyUrl());
            ali_request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
            ali_request.setReturnUrl(AlipayConfig.return_url);        //同步回调地址（APP）
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            orderString = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
            logger.info("与支付宝交互出错，未能生成订单，请检查代码！");
        }
        return orderString;
    }

    @Override
    public boolean rsaCheckV1(HttpServletRequest request) {
        try {
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (@SuppressWarnings("rawtypes")
                 Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
            return verifyResult;
        } catch (AlipayApiException e) {
            logger.debug("verify sigin error, exception is:{}", e);
            return false;
        }
    }

    @Override
    public boolean notify(String tradeStatus, String orderNo, String tradeNo) {
        if ("TRADE_FINISHED".equals(tradeStatus)
                || "TRADE_SUCCESS".equals(tradeStatus)) {
            boolean state = true;
            // 支付成功，根据业务逻辑修改相应数据的状态
//            boolean state = orderPaymentService.updatePaymentState(orderNo, tradeNo);
            if (state) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object refund(Integer orderId, Double amount, String refundReason) {
        if (StringUtils.isEmpty(orderId)) {
            return "订单编号不能为空";
        }
        if (amount <= 0) {
            return "退款金额必须大于0";
        }

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        // 商户订单号

        model.setOutTradeNo(orderId.toString());
        // 退款金额
        model.setRefundAmount(String.valueOf(amount));
        // 退款原因
        model.setRefundReason(refundReason);
        // 退款订单号(同一个订单可以分多次部分退款，当分多次时必传)
        // model.setOutRequestNo(UUID.randomUUID().toString());
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        alipayRequest.setBizModel(model);
        AlipayTradeRefundResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.execute(alipayRequest);
        } catch (AlipayApiException e) {
            logger.error("订单退款失败，异常原因:{}", e);
        }
        if (alipayResponse != null) {
            String code = alipayResponse.getCode();
            String subCode = alipayResponse.getSubCode();
            String subMsg = alipayResponse.getSubMsg();
            if ("10000".equals(code)
                    && StringUtils.isEmpty(subCode)
                    && StringUtils.isEmpty(subMsg)) {
                // 表示退款申请接受成功，结果通过退款查询接口查询
                // 修改用户订单状态为退款
                return "订单退款成功";
            }
            return subCode + ":" + subMsg;
        }
        return "订单退款失败";
    }
}
