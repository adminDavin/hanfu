package com.hanfu.shejiao.center.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
 
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public  String app_id = "2016101300674964";
 
    // 商户私钥，您的PKCS8格式RSA2私钥
    public  String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGwXl1mjyT7xoOEF7auag5jguItc4GCIY4nnsPKCtTcwWsJj73AmIXwp8H1wFm79B6qlMxKml8FeWVqSsNcS7+91gNSBd6dpGjzVb456oNdcV8Ot3erc71dLBLnqAEO+SvAVlZ+cEgW1w0m4vn9HmziuXxKQJk9eJWkWTe81MQcpPAcoHwRkHuMj5jqTPCi2OLoUq9omyNu8vG5x3Usy+/APxE+oId42/W9XKe5A48a+/X2vN48Mp4AHh+p2L8qkhLxiBTb/OrFsMdJ8vRbY2x4h/4H35Wy/wVJzvTr2I+qZ41r72IGWAUQnJohqT0NgyYJH8RPx6oh/cg3V+bkPqjAgMBAAECggEAMs/xhK7L+NxH7tWtbrxi+pDdOIeVUCEWu3LG5Eg5ThYpJWimhW2Yn2z7mnEwTihPbHGdPQTJEckP8QcA/OiY1/tccGWmtL7Q2/spuGFidgDanqVz0+umBlghweHmZLJQ4MyZbJwIfrL0acHdV3GQEmtlmTHlfTxaRFo+tvL5d1GvrqdCyVe8p+l4bp+Na/v6P1cP5TK7mll68oIAnzbC4p150xG3cwE+oC9Pl82o6tDKDa4zPQ64n3iPqw+x/KTGvE57yqSZ5OGovmZIo9wyhZfS4ay/IyQvwNUYPIzQ/CWIqtgcdwzFP4jqUwHC+jVrnNbAKJpse6vVve/OzBQ/QQKBgQDTuGRSN8TT+dLNDOePwJA17uufTB94myWhXtlF/6C49eaqL7v0AQdn6+H+aBPJyHvrzGb1G+aPvE0wTv6uLBQvFgHY/srjjlHgSj4Taf6vtW2YkVE9uAYi9ZhB+N8s0nmQyAkaF0QKZc6vm5446SOKkYP6SSNFXSZLGDAPr+o/jwKBgQCi8F075aVq+YfMlT59rtfkUQgOGmILGZFjDSwPWISPjQPQyvOxc4oNCqwxwf0YW82nvPfWd4ohD46pB3irZ64DpsD2bul4b7CbODIpq+ucP3n+ZOdFrT3SHBxELSNwwDzuWKzxruTJqVU+aKuHrqmxp8s68m5W3pBQ2nVSnXgJrQKBgDqUWUWaYp5YEChl+qf+mqghjBYOROLk4nuMt8czXG/wsbrC7Fkt87OgQ25Vs6vwCcGLU61b4+Y/LohAQx1D2u3QRACbNt3RYfwR7q0Gdc59/23oPzc5dJE1rMQMLQvEzJfvkP0kFNJfqtlR0GxkrU3Ix2B33gdyJ4c9kQa2ILUnAoGAOOT9aUDS9fuTs5gZbgFzWUICMgp8Wavf3FppHCKohweSYYG8fiX9+nfTkLxQCAVmVj/hun2bOdgY1GTLee6GELhVgLdRxsAZRtnkijCB4Nk5W98X8nh/sbInHJc2XNSDciZ8nReYw+Hg5h3eZ05R6rrQeBDW/nZQpirh9KsPkzECgYAi/xToQD0q02tgwJVLC3BWxiTgchh/pvtF6c6TZE4dtpzHp8QA+1pSv47ulfEno0ZBzRev4UkbbiA05DNu4zUphuNIg5RUlpvYTyQEAhpdUu2BRqS1MN7WgCVotvuhvsazYKl3e+KfRwhnxsR9WxsWYBKD3uNIYteJG/NtDduRPg==";
 
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoIra0CTOxpCiV/o0CVY95WP3qYfnerdVDvBf8i/aSB548aIMetSA9j/scf1p/gQ0IkWol3XFekGq0WgZJ6HE/gNma2ampTsJp0Otp46g1S98Dwbew1Mx1PhEGE/Knk0xfAT8y+NPGwzwu9fIckhKEMsR8ZsawggYj5P4VLM1ag/0cUU5OnSRKc19TUYXgAm4Y3G4UwwFBrQdRaCnhlEZydzKMsKh1xc3iOJJf9aYtJa/5CK8Br6NTmYim2FYAQe3wrZtxz/WLLJyebpyaPSUfsS3rK4geUlApLIMsuoVX4BjuTkyEZ3LLqoo+NJbhErb+qVuO7dDnqz7uk3DZia6NwIDAQAB";
 
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public  String notify_url = "https://swcloud.tjsichuang.cn:1445/api/shejiao/alipay/alipayNotify";
 
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // public  String return_url = "";
 
    // 签名方式
    public  String signType = "RSA2";
 
    // 字符编码格式
    public  String charset = "utf-8";
 
    // 支付宝请求地址
    public  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
 
    // 支付宝网关
    public  String log_path = "C:\\";
 
    //支付宝仅支持JSON格式
    public  String format="JSON";
 
 
    public String getApp_id() {
        return app_id;
    }
 
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
 
    public String getMerchant_private_key() {
        return merchant_private_key;
    }
 
    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }
 
    public String getAlipay_public_key() {
        return alipay_public_key;
    }
 
    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }
 
    public String getNotify_url() {
        return notify_url;
    }
 
    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
 
//    public String getReturn_url() {
//        return return_url;
//    }
//
//    public void setReturn_url(String return_url) {
//        this.return_url = return_url;
//    }
 
 
    public String getSignType() {
        return signType;
    }
 
    public void setSignType(String signType) {
        this.signType = signType;
    }
 
    public String getCharset() {
        return charset;
    }
 
    public void setCharset(String charset) {
        this.charset = charset;
    }
 
    public String getGatewayUrl() {
        return gatewayUrl;
    }
 
    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }
 
    public String getLog_path() {
        return log_path;
    }
 
    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }
 
    public String getFormat() {
        return format;
    }
 
    public void setFormat(String format) {
        this.format = format;
    }
 
    public  void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
 
}