package com.hanfu.shejiao.center.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.hanfu.shejiao.center.config.AlipayConfig;
import com.hanfu.shejiao.center.service.ALiservice;

@Service
public class ALiserviceImpl implements ALiservice {
    private static final Logger logger = LoggerFactory.getLogger("MainLogger");
   
 
    AlipayConfig alipayConfig = new AlipayConfig();
    /**
     * app支付接口2.0
     *
     * @param total_fee 总价
     * @param oid       订单ID
     * @return
     * @throws Exception
     */
 
    @Override
    public Map pay(String total_fee, String oid) throws Exception {
 
        try {
            System.out.println("-----------------------------支付宝预支付开始------------------------------------");
            // 封装请求客户端  实例化客户端  这个顺序不要动
            AlipayClient client = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getApp_id(), alipayConfig.getMerchant_private_key(),
                    alipayConfig.getFormat(), alipayConfig.getCharset(),alipayConfig.getAlipay_public_key(),alipayConfig.getSignType());
            //	支付请求 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。
            // 以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradePayModel model = new AlipayTradePayModel();
            model.setSubject("测试支付宝");  //订单名称
            model.setBody("测试支付宝支付");       //设置商品描述
//            String out_trade_no = jinOrderMapper.selectOrderNum(oid);  //获取订单号
            String out_trade_no = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println("商户订单号------------" + out_trade_no);
            model.setOutTradeNo(out_trade_no);      //设置商户网站唯一订单号
            model.setTotalAmount(total_fee);        //设置总金额
            model.setTimeoutExpress("90m");         //设置超时关闭该订单时间
            model.setProductCode("QUICK_MSECURITY_PAY");//销售产品码
            payRequest.setBizModel(model);          //封装好的额外参数放到主请求中
            payRequest.setNotifyUrl(alipayConfig.getNotify_url());//设置异步通知地址
            //支付宝请求应答
            AlipayTradeAppPayResponse responses = client.sdkExecute(payRequest);
            System.out.println("-----------------------------支付宝应答处理开始------------------------------------");
            System.out.println(responses.getBody()); // 就是orderString 可以直接给客户端请求，无需再做处理
            Map<String, String> data = new HashMap<>();
            data.put("body",responses.getBody());   //支付表单
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("下单失败");
    }
 
    /**
     *支付回调
     * @param request
     * @param response
     */
    @Override
    public void paymentCallback(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("---------------------------支付宝进入异步通知--------------------------");
        String resultFail = "fail";
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        System.out.println("返回的map----------------" + requestParams);
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            /*try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            params.put(name, valueStr);
        }
 
        logger.info("params={}", params);
 
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipay_public_key(), alipayConfig.getCharset(), alipayConfig.getSignType());
        } catch (AlipayApiException e) {
            logger.error("【支付宝异步通知】支付宝回调通知失败 e={} params={}", e, params);
            responseBody(response, resultFail);
            return;
            //e.printStackTrace();
        }
        if (!signVerified) {
            logger.error("【支付宝异步通知】验证签名错误 params={} ", params);
            responseBody(response, resultFail);
            return;
        }
        BigDecimal trade_price = new BigDecimal(request.getParameter("total_amount"));
        //商户订单号
        String out_trade_no = params.get("out_trade_no");
        //支付宝交易号
        String trade_no = params.get("trade_no");
        //交易状态
        String trade_status = params.get("trade_status");
        // 支付成功修改订单状态
        if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
            //业务处理，主要是更新订单状态
 
            System.out.println("---------------------------支付成功--------------------------");
        }
        responseBody(response, resultFail);
        return;
    }
    private void responseBody(HttpServletResponse response, String contentBody) {
        try {
            response.setContentType("type=text/javascript;charset=UTF-8");
            String s = contentBody;
            response.getWriter().write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}