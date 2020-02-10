package com.hanfu.payment.center.config;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.hanfu.payment.center.manaul.model.config.WxpayTradeAppPayModel;
import com.github.wxpay.sdk.WXPayUtil;

public class WechartConfig {
    private static Logger logger = LoggerFactory.getLogger(WechartConfig.class.getClass());

    // 商户支付秘钥
    public static String key = "laiwangtongchengyoupin8888888888";
    // 小程序ID
    public static String appId = "wxfa188a42d843a0b0";
    // 商户号
    public static String mchId = "1508952911";
    // 小程序的secret
    public static String secret = "0433593dd1887ea5381e6d01308f81ba";

    public static String tradeType = "JSAPI";

    public static String signType = "MD5";
    private Map<String, String> createMiniProgramTradePay(WxpayTradeAppPayModel model,String notifyUrl) throws Exception {
        MiniProgramConfig config = new MiniProgramConfig();

        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<>();
        data.put("appid",config.getAppID());
        data.put("mch_id",config.getMchID());
        data.put("body",model.getBody());
        data.put("out_trade_no", model.getOutTradeNo());
        data.put("device_info", "");
        data.put("fee_type", "CNY");
        data.put("total_fee", model.getTotalFee());
        data.put("spbill_create_ip", "127.0.0.1");
        data.put("notify_url", notifyUrl);
        data.put("trade_type", "JSAPI");
        data.put("openid",model.getOpenId());
//        data.put("nonce_str","1add1a30ac87aa2db72f57a2375d8fec");
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign",sign);

        Map<String, String> resp = wxpay.unifiedOrder(data);
        if ("SUCCESS".equals(resp.get("return_code"))) {
            //再次签名
            /** 重要的事情说三遍  小程序支付 所有的字段必须大写 驼峰模式 严格按照小程序支付文档
             *https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=3#
             * ******* 我当初就因为timeStamp中S没大写弄了3个小时 **********
             * */
            Map<String, String> reData = new HashMap<>();
            reData.put("appId", config.getAppID());
            reData.put("nonceStr", resp.get("nonce_str"));
            String newPackage = "prepay_id=" + resp.get("prepay_id");
            reData.put("package", newPackage);
            reData.put("signType","MD5");
            reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

            String newSign = WXPayUtil.generateSignature(reData, config.getKey());
            resp.put("paySign",newSign);
            resp.put("timeStamp", reData.get("timeStamp"));
            return resp;
        } else {
            throw new Exception(resp.get("return_msg"));
        }

    }
}
