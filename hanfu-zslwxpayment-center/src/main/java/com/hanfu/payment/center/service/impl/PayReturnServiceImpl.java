package com.hanfu.payment.center.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.payment.center.returnutil.WXPayConstants;
import com.hanfu.payment.center.returnutil.WXPayUtil;
import com.hanfu.payment.center.service.PayReturnService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.util.Map;

/**
 * @ClassName PayReturnServiceImpl
 * @Date 2019/12/23 10:41
 * @Author CONSAK
 **/

@Service
public class PayReturnServiceImpl implements PayReturnService {

    private static final String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    @Override
    public ResponseEntity<JSONObject> refund(String out_trade_no, String transaction_id, int total_fee) throws Exception {

        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        InputStream instream = null;
        KeyStore keyStore = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        StringBuilder text = new StringBuilder();
        String key = WXPayConstants.MCH_ID;
        try {
            /**
             * 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
             */
            keyStore = KeyStore.getInstance("PKCS12");
            instream = new FileInputStream(new File("D:\\微信商户平台支付证书\\apiclient_cert.p12"));

            /**
             * 此处要改
             */
            keyStore.load(instream, key.toCharArray());// 这里写密码..默认是MCHID

            /**
             * 此处要改
             */// 这里也是写密码的
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            //=======================证书配置完成========================

            HttpPost httpPost = new HttpPost(url);

            String xmlstring = WXPayUtil.wxPayRefund(out_trade_no, transaction_id, String.valueOf((int) (total_fee * 100)));

            httpPost.setEntity(new StringEntity(xmlstring));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            response = httpclient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    text.append(str);
                }
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {

        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Map<String, String> map = WXPayUtil.xmlToMap(text.toString());
        String return_msg = map.get("return_msg");
        if ("OK".equals(return_msg) && "SUCCESS".equals(map.get("return_code"))) {
            return builder.body(ResponseUtils.getResponseBody("退款成功"));
        } else {
            return builder.body(ResponseUtils.getResponseBody("退款失败"));
        }
    }

}