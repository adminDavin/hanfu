package com.hanfu.payment.center.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.hanfu.payment.center.config.MiniProgramConfig;
import com.hanfu.payment.center.manual.dao.HfOrderDao;
import com.hanfu.payment.center.manual.model.HfOrderDisplay;
import com.hanfu.payment.center.manual.model.HfUser;
import com.hanfu.payment.center.manual.model.OrderStatus;
import com.hanfu.payment.center.util.WXConfigUtil;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/hf-payment/")
@Api
public class PaymentOrderController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HfOrderDao hfOrderDao;
    
    @Autowired
    HttpServletRequest req;
    
    @ApiOperation(value = "支付订单", notes = "")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer"),
        @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer")}
    )
    public ResponseEntity<JSONObject> payment(@RequestParam Integer orderId, Integer userId)
            throws Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        List<HfOrderDisplay> hfOrders = hfOrderDao.selectHfOrder(params);
        HfOrderDisplay hfOrder = hfOrders.get(0);
        HfUser hfUser = hfOrderDao.selectHfUser(userId);
//        HfOrderDisplay newHfOrder = hfOrderDao.selectHfOrderbyCode(hfOrder.getOrderCode());
//
//        hfOrderDao.updateHfOrderStatus(newHfOrder.getOrderCode(), OrderStatus.PAYMENT.getOrderStatus(), LocalDateTime.now());  
        MiniProgramConfig config = new MiniProgramConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<>();
        data.put("appid",config.getAppID());
        data.put("mch_id",config.getMchID());
        data.put("body","訂單支付");
        data.put("out_trade_no", hfOrder.getOrderCode());
        data.put("device_info", req.getRemoteHost());
        data.put("fee_type", "CNY");
        data.put("total_fee", String.valueOf(1));
        data.put("spbill_create_ip", req.getRemoteAddr());
        data.put("notify_url", "https://www.tjsichuang.cn:1443/api/payment/hf-payment/handleWxpay");
        data.put("trade_type", "JSAPI");
        data.put("openid",hfUser.getAuthKey());
        String sign = WXPayUtil.generateSignature(data, config.getKey());
        data.put("sign",sign);
        Map<String, String> resp = wxpay.unifiedOrder(data);
        if ("SUCCESS".equals(resp.get("return_code"))) {
            Map<String, String> reData = new HashMap<>();
            reData.put("appId", config.getAppID());
            reData.put("nonceStr", resp.get("nonce_str"));
            String newPackage = "prepay_id=" + resp.get("prepay_id");
            reData.put("package", newPackage);
            reData.put("signType","MD5");
            reData.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));

            String newSign = WXPayUtil.generateSignature(reData, config.getKey());
            resp.put("paySign",newSign);
            resp.put("package",reData.get("package"));
            resp.put("signType", reData.get("signType"));
            resp.put("timeStamp", reData.get("timeStamp"));
            return builder.body(ResponseUtils.getResponseBody(resp));
        } else {
            throw new Exception(resp.get("return_msg"));
        }
        
    }
    
    @ApiOperation(value = "訂單支付后處理", notes = "訂單支付后處理")
    @RequestMapping(value = "/handleWxpay", method = RequestMethod.GET)
    @ApiImplicitParams({                                                   //商户单号和微信单号  选择其一
            @ApiImplicitParam(paramType = "query", name = "out_trade_no", value = "商户订单号", required = false),
            @ApiImplicitParam(paramType = "query", name = "transaction_id", value = "微信订单号", required = false),
            @ApiImplicitParam(paramType = "query", name = "total_fee", value = "总额", required = true)
    })
    public ResponseEntity<JSONObject> refund(String out_trade_no, String transaction_id, int total_fee) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        hfOrderDao.updateHfOrderStatus(out_trade_no, OrderStatus.PROCESS.getOrderStatus(), LocalDateTime.now());        
        return builder.body(ResponseUtils.getResponseBody(total_fee));
    }
}
