package com.hanfu.payment.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.config.Configure;
import com.hanfu.payment.center.entity.NewWXOrderRequest;
import com.hanfu.payment.center.entity.OrderInfo;
import com.hanfu.payment.center.entity.OrderReturnInfo;
import com.hanfu.payment.center.entity.SignInfo;
import com.hanfu.payment.center.utils.HttpRequest;
import com.hanfu.payment.center.utils.PayUtil;
import com.hanfu.payment.center.utils.RandomStringGenerator;
import com.hanfu.payment.center.utils.Signature;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.thoughtworks.xstream.XStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PayController
 * @Date 2019/12/30 14:56
 * @Author CONSAK
 **/
@Api
@CrossOrigin
@RestController
@RequestMapping("/pay")
public class PayController {

    private static final long serialVersionUID = 1L;

    @Value("${wxapplet.config.weixinpay.notifyurl}")
    private String notify_url;
    //交易类型
    private final String trade_type = "JSAPI";
    //统一下单API接口链接
    private final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "统一下单",notes = "统一下单")
    @RequestMapping(value = "/wxpay",method = RequestMethod.POST)
    public ResponseEntity<JSONObject> payment(@Valid @RequestBody NewWXOrderRequest request, HttpServletRequest httpServletRequest) {
        Map map = new HashMap();
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        try {
            OrderInfo order = new OrderInfo();
            order.setAppid(Configure.getAppID());
            order.setMch_id(Configure.getMch_id());
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setBody(request.getBody());
            order.setOut_trade_no(request.getId());

            order.setTotal_fee(request.getTotal_fee()*100);//注意 ！！！这里传过来的钱是分  一定注意，例如传过来10  代表是10分钱  就是一毛钱 零点一元钱！！！

            order.setSpbill_create_ip(RandomStringGenerator.getIp2(httpServletRequest));
            order.setNotify_url(notify_url);
            order.setTrade_type(trade_type);
            order.setOpenid(request.getOpenId());
            order.setSign_type("MD5");
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);

            //把统一下单的所有参数装好 发送到微信的统一下单API
            String result = HttpRequest.sendPost(url, order);

            System.out.println(result);

            //下单完成返回的XML
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnInfo.class);

            //统一下单完成之后返回的结果 放到实体类里面
            OrderReturnInfo returnInfo = (OrderReturnInfo) xStream.fromXML(result);

            System.out.println(returnInfo);

            // 二次签名
            if ("SUCCESS".equals(returnInfo.getReturn_code()) && returnInfo.getReturn_code().equals(returnInfo.getResult_code())) {

                SignInfo signInfo = new SignInfo();

                signInfo.setAppId(Configure.getAppID());
                long time = System.currentTimeMillis() / 1000;
                signInfo.setTimeStamp(String.valueOf(time));
                signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
                signInfo.setPrepay_id("prepay_id=" + returnInfo.getPrepay_id());
                signInfo.setSignType("MD5");

                //生成签名
                String sign1 = Signature.getSign(signInfo);

                Map payInfo = new HashMap();

                payInfo.put("timeStamp", signInfo.getTimeStamp());
                payInfo.put("nonceStr", signInfo.getNonceStr());
                payInfo.put("package", signInfo.getPrepay_id());
                payInfo.put("signType", signInfo.getSignType());
                payInfo.put("paySign", sign1);

                map.put("status", 200);
                map.put("msg", "统一下单成功!");
                map.put("data", payInfo);
                return builder.body(ResponseUtils.getResponseBody(map));
            }
            map.put("status", 500);
            map.put("msg", "统一下单失败!");
            map.put("data", null);
            return builder.body(ResponseUtils.getResponseBody(map));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 微信小程序支付成功回调函数
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/weixin/callback",method = RequestMethod.POST)
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);
        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, Configure.getKey(), "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            // 因为微信回调会有八次之多,所以当第一次回调成功了,那么我们就不再执行逻辑了
            // 根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if (sign.equals(map.get("sign"))) {
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                return resXml;
            } else {
                return "微信支付回调失败!签名不一致";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return resXml;
    }
}