package com.hanfu.payment.center.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.hanfu.payment.center.request.AlipayConfig;
import com.hanfu.payment.center.service.AlipayService;
import com.hanfu.payment.center.service.WeChatService;
import com.hanfu.payment.center.util.WxMD5Util;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;
import com.hanfu.utils.response.handler.ResponseUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/payment")
@Api
public class PaymentController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlipayService alipayService;
    @Autowired
    private WeChatService weChatService;

    @ApiOperation(value = "支付宝支付", notes = "支付宝支付")
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "商品实体id", required = true, type = "Integer")})
    public ResponseEntity<JSONObject> payment(@RequestParam Integer bossId, Integer orderId, Integer amount)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String pay = alipayService.getAliPayOrderStr(bossId, orderId, amount);
        return builder.body(ResponseUtils.getResponseBody(pay));
    }

    @ApiOperation(value = "异步回调", notes = "异步回调")
    @RequestMapping(value = "/payNotify", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> payNotify(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        boolean flag = alipayService.rsaCheckV1(request);
        if (flag) {
            String tradeStatus = request.getParameter("trade_status"); // 交易状态
            String outTradeNo = request.getParameter("out_trade_no"); // 商户订单号
            String tradeNo = request.getParameter("trade_no"); // 支付宝订单号
            /**
             * 还可以从request中获取更多有用的参数，自己尝试
             */
            boolean notify = alipayService.notify(tradeStatus, outTradeNo, tradeNo);
            if (notify) {
                return builder.body(ResponseUtils.getResponseBody("成功"));
            }
        }
        return builder.body(ResponseUtils.getResponseBody("失败"));
    }

    @ApiOperation(value = "退款流程", notes = "退款流程")
    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "orderId", value = "订单id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "amount", value = "退款金额", required = true, type = "Double"),
            @ApiImplicitParam(paramType = "query", name = "refundReason", value = "退款原因", required = true, type = "String")
    })
    public ResponseEntity<JSONObject> refund(Integer orderId, Double amount, String refundReason)
            throws JSONException, Exception {
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(alipayService.refund(orderId, amount, refundReason)));
    }

    /**
     * 说明：单笔转账到支付宝账户
     *
     * @param out_biz_no      商户转账唯一订单号
     * @param payee_type      收款方账户类型  (1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。)
     * @param payee_account   收款方账户
     * @param amount          转账金额
     * @param payer_show_name 付款方姓名
     * @param payee_real_name 收款方真实姓名
     * @param remark          转账备注
     */
    @RequestMapping("/transferAccounts")
    public void transferAccounts(String out_biz_no, String payee_type, String payee_account, String amount, String payer_show_name, String payee_real_name, String remark) {
        //填写自己创建的app的对应参数
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, "json", "UTF-8",
                AlipayConfig.ALIPAY_PUBLIC_KEY, "RSA");
        AlipayFundTransToaccountTransferRequest transferAccounts_request = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
        model.setOutBizNo(out_biz_no);
        model.setPayeeType(payee_type);
        model.setPayeeAccount(payee_account);
        model.setAmount(amount);
        model.setPayerShowName(payer_show_name);
        model.setPayeeRealName(payee_real_name);
        model.setRemark(remark);
        transferAccounts_request.setBizModel(model);
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferAccounts_request);
            if (response.isSuccess()) {
                System.out.println(response.getBody());
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({"rawtypes", "unused"})
    @ApiOperation(value = "同步回调流程", notes = "同步回调流程")
    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean verifyResult = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
        if (verifyResult) {
            //验证成功
            //请在这里加上商户的业务逻辑程序代码，如保存支付宝交易号
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            return builder.body(ResponseUtils.getResponseBody("成功"));
        } else {
            return builder.body(ResponseUtils.getResponseBody("失败"));
        }
    }

    @ApiOperation(value = "微信支付", notes = "微信支付")
    @RequestMapping(value = "/wxpay", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "totalFee", value = "商品实体id", required = true, type = "String")})
    public ResponseEntity<JSONObject> wxpay(@RequestParam Integer userId, String totalFee)
            throws Exception {
        String attach = "{\"user_id\":\"" + userId + "\"}";
        Map<String, String> result = weChatService.dounifiedOrder(attach, totalFee);
        WxMD5Util md5Util = new WxMD5Util();
        Map<String, String> map = new HashMap<>();
        //返回APP端的数据
        //参加调起支付的签名字段有且只能是6个，分别为appid、partnerid、prepayid、package、noncestr和timestamp，而且都必须是小写
        map.put("appid", result.get("appid"));
        map.put("partnerid", result.get("mch_id"));
        map.put("prepayid", result.get("prepay_id"));
        map.put("package", "Sign=WXPay");
        map.put("noncestr", result.get("nonce_str"));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//单位为秒
        map.put("sign", md5Util.getSign(map));
        map.put("extdata", attach);
        BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        return builder.body(ResponseUtils.getResponseBody(map));
    }

    @ApiOperation(value = "异步调用", notes = "异步调用")
    @RequestMapping(value = "/wxnotify", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> wxPayNotify(HttpServletRequest request, HttpServletResponse response)
            throws JSONException {
        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = weChatService.payBack(resXml);
            BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
            return builder.body(ResponseUtils.getResponseBody(result));
        } catch (Exception e) {
            System.out.println("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
            return builder.body(ResponseUtils.getResponseBody(result));
        }
    }
}
