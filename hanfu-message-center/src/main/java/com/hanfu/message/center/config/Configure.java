package com.hanfu.message.center.config;

/**
 * @description: 配置
 * @author: ningcs
 * @create: 2019-06-25 14:54
 **/
public class Configure {
    // 商户支付秘钥
    private static String key = "";
    //小程序ID
    private static String appID = "wxc2684fab7d1b8b76";
    //商户号
    private static String mch_id = "";
    // 小程序的secret
    private static String secret = "a1ee1c6cd34adf1c830664a2805f0c85";

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
