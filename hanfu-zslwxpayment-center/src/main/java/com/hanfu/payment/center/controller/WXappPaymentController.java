package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.hanfu.payment.center.config.MiniProgramConfig;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shihao
 * @Title: WXappPaymentController
 * @ProjectName Second-order-center
 * @Description: 微信app支付
 * @date Created in
 * @Version: $
 */
@Api
@CrossOrigin
@RestController
@RequestMapping("/WxAPPpay")
public class WXappPaymentController {
    @Autowired
    HttpServletRequest req;
    @ApiOperation(value = "支付订单", notes = "")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "订单号随便输", required = true, type = "Integer") })
    public ResponseEntity<JSONObject> payment(Integer userId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Map<String, String> resp = null;
            resp = wxPay(userId);
        System.out.println(resp);
        return builder.body(ResponseUtils.getResponseBody(resp));
    }

    private Map<String, String> wxPay(Integer userId) throws Exception {
		MiniProgramConfig miniProgramConfig = new MiniProgramConfig();
        Map<String, String> data = getWxPayData(miniProgramConfig, String.valueOf(userId),1);
//        logger.info(JSONObject.toJSONString(data));
        WXPay wxpay = new WXPay(miniProgramConfig);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        System.out.println(resp);
//        logger.info(JSONObject.toJSONString(resp));
        if ("SUCCESS".equals(resp.get("return_code"))) {
            System.out.println(1);
            resp.put("appid", resp.get("appid"));
            resp.put("noncestr", resp.get("nonce_str"));
            if ("SUCCESS".equals(resp.get("result_code"))) {//resultCode 为SUCCESS，才会返回prepay_id和trade_type
                System.out.println(2);
                resp.put("package","Sign=WXPay");
                resp.put("partnerid", resp.get("mch_id"));
                resp.put("prepayid", resp.get("prepay_id"));
                resp.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//单位为秒

                String sign = WXPayUtil.generateSignature(resp, miniProgramConfig.getKey());// 二次签名
                resp.put("sign",sign); //签名
                resp.put("trade_type", resp.get("trade_type"));//获取预支付交易回话标志
                return resp;
            } else {
                //此时返回没有预付订单的数据
                return resp;
            }
        } else {
            throw new Exception(resp.get("return_msg"));
        }

    }
    private Map<String, String> getWxPayData(MiniProgramConfig config, String orderCode,Integer Amount)
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
        data.put("trade_type", "APP");
//        data.put("openid", openId);
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        System.out.println(sign);
        data.put("sign", sign);
//        logger.info(JSONObject.toJSONString(data));
        return data;
    }
}
