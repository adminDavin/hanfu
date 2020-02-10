package com.hanfu.payment.center.returnutil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.util.*;

/**
 * @Author: HONGLINCHEN
 * @Description:微信支付
 * @Date: 2017-9-7 17:14
 */
public class WXPayUtil {
    public static String PostRequest(String url, String data) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        String result = "";
        post.addRequestHeader("Content-Type", "text/html; charset=utf-8");
        post.addRequestHeader("content", "text/html; charset=utf-8");
        post.setRequestBody(data);
        try {
            int status = client.executeMethod(post);
            result = post.getResponseBodyAsString();
            result = new String(result.getBytes(post.getResponseCharSet()), "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createSign(SortedMap<String, String> packageParams, String AppKey) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + AppKey);
        String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        return sign;
    }

    /**
     * @param out_trade_no
     * @param body
     * @param detail
     * @param total_fee
     * @param ip_address
     * @Author: HONGLINCHEN
     * @Description:微信支付 统一下单
     * @Date: 2017-9-11 14:35
     * @return:
     */
    public static String unifiedOrder(String out_trade_no, String body, String detail, int total_fee, String ip_address) {
        StringBuffer xml = new StringBuffer();
        String data = null;
        try {
            xml.append("</xml>");
            if (body.length() > 32) {
                body = body.substring(0, 32);
            }
            SortedMap<String, String> parameters = new TreeMap();
            parameters.put("appid", WXPayConstants.APP_ID);
            parameters.put("body", body);
            parameters.put("detail", detail);
            parameters.put("mch_id", WXPayConstants.MCH_ID);
            parameters.put("nonce_str", genNonceStr());
            parameters.put("notify_url", "http://www.aidongsports.com/wx");
            parameters.put("out_trade_no", out_trade_no);
            parameters.put("fee_type", "CNY");
            parameters.put("spbill_create_ip", ip_address);
            parameters.put("total_fee", String.valueOf(total_fee));
            parameters.put("trade_type", "APP");
            parameters.put("sign", createSign(parameters, WXPayConstants.API_KEY));
            data = PostRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", SortedMaptoXml(parameters));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * @param out_trade_no
     * @param total_fee
     * @Author: HONGLINCHEN
     * @Description:微信退款
     * @Date: 2017-9-11 14:35
     * @return:
     */
    public static String wxPayRefund(String out_trade_no, String transaction_id, String total_fee) {
        StringBuffer xml = new StringBuffer();
        String data = null;
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            SortedMap<String, String> parameters = new TreeMap<String, String>();
            parameters.put("appid", WXPayConstants.APP_ID);
            parameters.put("mch_id", WXPayConstants.MCH_ID);
            parameters.put("nonce_str", nonceStr);
            parameters.put("out_trade_no", out_trade_no);
            parameters.put("transaction_id", transaction_id);
            parameters.put("out_refund_no", nonceStr);
            parameters.put("fee_type", "CNY");
            parameters.put("total_fee", total_fee);
            parameters.put("refund_fee", total_fee);
            parameters.put("op_user_id", WXPayConstants.MCH_ID);
            parameters.put("sign", createSign(parameters, WXPayConstants.API_KEY));
            data = SortedMaptoXml(parameters);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        return data;
    }

    /**
     * 证书使用
     * 微信退款
     */
    public static String wxPayBack(String url, String data) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("D:\\微信商户平台支付证书\\apiclient_cert.p12"));
        String result = "";
        try {
            keyStore.load(instream, WXPayConstants.MCH_ID.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, WXPayConstants.MCH_ID.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            StringEntity entitys = new StringEntity(data);
            httppost.setEntity((HttpEntity) entitys);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    String t = "";
                    while ((text = bufferedReader.readLine()) != null) {
                        t += text;
                    }
                    byte[] temp = t.getBytes("gbk");//这里写原编码方式
                    String newStr = new String(temp, "utf-8");//这里写转换后的编码方式
                    result = newStr;
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return result;
    }

    /**
     * XML格式字符串转换为Map
     * 微信支付 解析xml xml转map  获取prepay_id
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            org.dom4j.Document document = DocumentHelper.parseText(strXML);
            org.dom4j.Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext(); ) {
                org.dom4j.Element elm = (Element) it.next();
                data.put(elm.getName(), elm.getText());
            }
            node = null;
            nodeElement = null;
            document = null;
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
//        try {
//            Map<String, String> data = new HashMap<String, String>();
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
//            org.w3c.dom.Document doc = documentBuilder.parse(stream);
//            doc.getDocumentElement().normalize();
//            NodeList nodeList = doc.getDocumentElement().getChildNodes();
//            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
//                Node node = nodeList.item(idx);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
//                    data.put(element.getNodeName(), element.getTextContent());
//                }
//            }
//            try {
//                stream.close();
//            } catch (Exception ex) {
//                // do nothing
//            }
//            return data;
//        } catch (Exception ex) {
//            WXPayUtil.getLogger().warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
//            throw ex;
//        }

    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     *
     * @param data 待处理数据
     * @param key  密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * @param prepay_id
     * @Author: HONGLINCHEN
     * @Description:通过prepay_id 生成微信支付参数
     * @Date: 2017-9-8 10:17
     */
    public static SortedMap<Object, Object> genPayRequest(String prepay_id) {
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
        parameters.put("appid", WXPayConstants.APP_ID);
        parameters.put("noncestr", genNonceStr());
        parameters.put("package", "Sign=WXPay");
        parameters.put("partnerid", WXPayConstants.MCH_ID);
        parameters.put("prepayid", prepay_id);
        parameters.put("timestamp", getCurrentTimestamp());
        parameters.put("sign", MD5.createSign("utf-8", parameters).toUpperCase());
        return parameters;
    }

    /**
     * @param params
     * @Author: HONGLINCHEN
     * @Description:请求值转换为xml格式 SortedMap转xml
     * @Date: 2017-9-7 17:18
     */
    private static String SortedMaptoXml(SortedMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        Set es = params.entrySet();
        Iterator it = es.iterator();
        sb.append("<xml>\n");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            sb.append("<" + k + ">");
            sb.append(v);
            sb.append("</" + k + ">\n");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 日志
     *
     * @return
     */
    public static Logger getLogger() {
        Logger logger = LoggerFactory.getLogger("wxpay java sdk");
        return logger;
    }

    /**
     * 生成32位随机数字
     */
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取当前时间戳，单位秒
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

}
