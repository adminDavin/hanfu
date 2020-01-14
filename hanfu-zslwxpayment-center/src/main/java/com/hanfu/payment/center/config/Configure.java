package com.hanfu.payment.center.config;

/**
 * @description: 配置
 * @author: ningcs
 * @create: 2019-06-25 14:54
 **/
public class Configure {
    // 商户支付秘钥
    private static String key = "laiwangtongchengyoupin8888888888";
    //小程序ID
    private static String appID = "wxfa188a42d843a0b0";
    //商户号
    private static String mch_id = "1508952911";
    // 小程序的secret
    private static String secret = "0433593dd1887ea5381e6d01308f81ba";

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        Configure.secret = secret;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        Configure.key = key;
    }

    public static String getAppID() {
        return appID;
    }

    public static void setAppID(String appID) {
        Configure.appID = appID;
    }

    public static String getMch_id() {
        return mch_id;
    }

    public static void setMch_id(String mch_id) {
        Configure.mch_id = mch_id;
    }
}
