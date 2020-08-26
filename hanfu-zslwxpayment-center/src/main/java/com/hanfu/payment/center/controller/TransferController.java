package com.hanfu.payment.center.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class TransferController {
    String URL = "https://openapi.alipay.com/gateway.do";
    String APP_ID = "2017062307553200";

    String APP_PRIVATE_KEY = "";
    String FORMAT = "json";
    String CHARSET = "UTF-8";
    String ALIPAY_PUBLIC_KEY = "";
    String SIGN_TYPE = "RSA2";
    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    String Ordernumber = df.format(new Date()) + "_" + new Random().nextInt(1000);
private Map<String,String> transfer(){
    AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();

    Map<String, String> respMap = new LinkedHashMap<String, String>();
    respMap.put("out_biz_no", "这是编号");
    respMap.put("payee_type", "ALIPAY_LOGONID");//这是支付类型
    respMap.put("payee_account", "89387757@qq.com");//这是对方支付宝账号
    respMap.put("amount", "0.1");//转账金额
    respMap.put("payer_show_name", "");//谁转账的名称
    respMap.put("payee_real_name", "尹娟娟");//对方姓名
    respMap.put("remark", "这是备注");
    JSONObject ja1 = JSONObject.fromObject(respMap);
    request.setBizContent(ja1.toString());

    AlipayFundTransToaccountTransferResponse response;
    try {
        response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            System.out.println(response.getSubMsg());
            System.out.println(response.getOrderId());
        } else {
            System.out.println(response.getSubMsg());
            System.out.println("调用失败");
        }
    } catch (
            AlipayApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}

}
