package com.hanfu.payment.center.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hanfu.payment.center.config.WXConfigUtil;
import com.hanfu.payment.center.config.WxCfg;
import com.hanfu.payment.center.service.WXAppPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
@Service
public class WXAppPayServiceImpl implements WXAppPayService {
    @Autowired
    private WxCfg wxCfg;
    @Override
    public Map<String, String> dounifiedOrder(String type, String out_trade_no, String money) throws Exception {
        Map<String, String> returnMap = new HashMap<>();

        //支付参数
        WXConfigUtil config = new WXConfigUtil();
        WXPay wxpay = new WXPay(config);
        //请求参数封装
        Map<String, String> data = new HashMap<>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", "订单支付");
        data.put("out_trade_no", out_trade_no);
        data.put("total_fee", "1");
        data.put("spbill_create_ip", wxCfg.getIp()); //自己的服务器IP地址
        data.put("notify_url", wxCfg.getAppNotifyUrl());//异步通知地址（请注意必须是外网）
        data.put("trade_type", wxCfg.getAppType());//交易类型
        data.put("attach", type);//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        String s = WXPayUtil.generateSignature(data, config.getKey());  //签名
        data.put("sign", s);//签名

        try {
            //使用官方API请求预付订单
            Map<String, String> response = wxpay.unifiedOrder(data);
            System.out.println(response);
            String returnCode = response.get("return_code");    //获取返回码
            //若返回码为SUCCESS，则会返回一个result_code,再对该result_code进行判断
            if (returnCode.equals("SUCCESS")) {
                //主要返回以下5个参数(必须按照顺序，否则APP报错：-1)
                String resultCode = response.get("result_code");
                returnMap.put("appid", response.get("appid"));
                returnMap.put("noncestr", response.get("nonce_str"));
                if ("SUCCESS".equals(resultCode)) {//resultCode 为SUCCESS，才会返回prepay_id和trade_type
                    returnMap.put("package","Sign=WXPay");
                    returnMap.put("partnerid", response.get("mch_id"));
                    returnMap.put("prepayid", response.get("prepay_id"));
                    returnMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//单位为秒

                    String sign = WXPayUtil.generateSignature(returnMap, config.getKey());// 二次签名
                    returnMap.put("sign",sign); //签名
                    returnMap.put("trade_type", response.get("trade_type"));//获取预支付交易回话标志
                    return returnMap;
                } else {
                    //此时返回没有预付订单的数据
                    return returnMap;
                }
            } else {
                return returnMap;
            }
        } catch (Exception e) {
            System.out.println(e);
            //系统等其他错误的时候
        }
        return returnMap;
    }

    @Override
    public String payBack(String notifyData) {
        WXConfigUtil config = null;
        try {
            config = new WXConfigUtil();
        } catch (Exception e) {
            e.printStackTrace();
        }
        WXPay wxpay = new WXPay(config);
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(notifyData);         // 调用官方SDK转换成map类型数据
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {//验证签名是否有效，有效则进一步处理

                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//商户订单号
                if (return_code.equals("SUCCESS")) {
                    if (out_trade_no != null) {
                        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                        // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                        //业务数据持久化
                        System.err.println("支付成功"+"\n");
                        String attach = notifyMap.get("attach");//附加数据，用于区分是那张表订单
                        System.out.print("附加数据类型为：{}"+attach+"\n");
                        if(StringUtils.isEmpty(attach)){
//                            logger.info();
                            System.out.println("附加数据类型为：{}"+ attach);
                        }else{
                            //@TODO 预支付下单后回调的逻辑

                        }
//                        logger.info("微信手机支付回调成功订单号:{}", out_trade_no);
                        System.out.println("微信手机支付回调成功订单号:{}"+out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
//                        logger.info("微信手机支付回调失败订单号:{}", out_trade_no);
                        System.out.println("微信手机支付回调失败订单号:{}"+ out_trade_no);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
//                logger.error();
                System.out.println("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
//            logger.error("手机支付回调通知失败", e);
            System.out.println("手机支付回调通知失败"+ e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }
    }

