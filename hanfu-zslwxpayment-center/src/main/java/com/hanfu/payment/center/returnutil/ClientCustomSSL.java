package com.hanfu.payment.center.returnutil;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * @Author: HONGLINCHEN
 * @Description: 微信退款
 * @Date: 2017-9-12 13:15
 */
public class ClientCustomSSL {
    /**
     * @param merchantNumber      商户这边的订单号
     * @param wxTransactionNumber 微信那边的交易单号
     * @param totalFee            订单的金额
     * @Author: HONGLINCHEN
     * @Description:微信退款方法封装 注意：：微信金额的单位是分 所以这里要X100 转成int是因为 退款的时候不能有小数点
     * @Date: 2017-9-12 11:18
     */
    public static Object setUrl(String merchantNumber, String wxTransactionNumber, double totalFee) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File("D:\\微信商户平台支付证书\\apiclient_cert.p12"));
            try {
                keyStore.load(instream, WXPayConstants.MCH_ID.toCharArray());
            } finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WXPayConstants.MCH_ID.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            String xml = com.hanfu.payment.center.returnutil.WXPayUtil.wxPayRefund(merchantNumber, wxTransactionNumber, String.valueOf((int) (totalFee * 100)));
            try {
                StringEntity se = new StringEntity(xml);
                httppost.setEntity(se);
                System.out.println("executing request" + httppost.getRequestLine());
                CloseableHttpResponse responseEntry = httpclient.execute(httppost);
                try {
                    HttpEntity entity = responseEntry.getEntity();
                    System.out.println(responseEntry.getStatusLine());
                    if (entity != null) {
                        System.out.println("Response content length: "
                                + entity.getContentLength());
                        SAXReader saxReader = new SAXReader();
                        Document document = saxReader.read(entity.getContent());
                        Element rootElt = document.getRootElement();
                        System.out.println("根节点：" + rootElt.getName());
                        System.out.println("===" + rootElt.elementText("result_code"));
                        System.out.println("===" + rootElt.elementText("return_msg"));
                        String resultCode = rootElt.elementText("result_code");
                        JSONObject result = new JSONObject();

                        Document documentXml = DocumentHelper.parseText(xml);
                        Element rootEltXml = documentXml.getRootElement();
                        if (resultCode.equals("SUCCESS")) {
                            System.out.println("=================prepay_id====================" + rootElt.elementText("prepay_id"));
                            System.out.println("=================sign====================" + rootEltXml.elementText("sign"));
                            result.put("weixinPayUrl", rootElt.elementText("code_url"));
                            result.put("prepayId", rootElt.elementText("prepay_id"));
                            result.put("status", "success");
                            result.put("msg", "success");
                        } else {
                            result.put("status", "false");
                            result.put("msg", rootElt.elementText("err_code_des"));
                        }
                        return result;
                    }
                    EntityUtils.consume(entity);
                } finally {
                    responseEntry.close();
                }
            } finally {
                httpclient.close();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("status", "error");
            result.put("msg", e.getMessage());
            return result;
        }
    }

}