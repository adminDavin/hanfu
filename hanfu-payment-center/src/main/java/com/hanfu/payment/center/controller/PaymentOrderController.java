package com.hanfu.payment.center.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hanfu.payment.center.util.PayUtil;
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
        logger.info(JSONObject.toJSONString(data));
        Map<String, String> resp = wxpay.unifiedOrder(data);
        logger.info(JSONObject.toJSONString(resp));
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
    public void refund(HttpServletRequest request,HttpServletResponse response) throws Exception {
        MiniProgramConfig config = new MiniProgramConfig();
        
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        logger.info("接收到的报文：" + notityXml);
 
        Map map = PayUtil.doXMLParse(notityXml);
 
        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
//        hfOrderDao.updateHfOrderStatus(out_trade_no, OrderStatus.PROCESS.getOrderStatus(), LocalDateTime.now());        
        Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
        String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String sign = PayUtil.sign(validStr, config.getKey(), "utf-8").toUpperCase();//拼装生成服务器端验证的签名
        // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了
       
        //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
        if(sign.equals(map.get("sign"))){
            /**此处添加自己的业务逻辑代码start**/
              // bla bla bla....
            /**此处添加自己的业务逻辑代码end**/
            //通知微信服务器已经支付成功
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        } else {
            System.out.println("微信支付回调失败!签名不一致");
        }
    }else{
        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
    }
    System.out.println(resXml);
    System.out.println("微信支付回调数据结束");

    BufferedOutputStream out = new BufferedOutputStream(
            response.getOutputStream());
    out.write(resXml.getBytes());
    out.flush();
    out.close();
    }
}
