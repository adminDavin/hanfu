package com.hanfu.payment.center.request;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APPID = "2016092700611035";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvcv2NgoUEiPLu3FW/k1EX55md8ol/9L4cnP0L0Vpd96w8xqDRoywynOKmgFHASRmQ6kX7nNfmJ0eV4T8zmX73VcFJFfpzAxaELignYCr2X0hAiQ9ftrYlnZeghHwtbmA6FFHICfloAP26oDbg+HReT2g0XOvxtalbdDdK/o82KDOPVdFUQS8VHA64PXRn1rBeF7JaowUZQ4+NEKaba1RJqB3pJmNERGRIBZ519W/babn75bsZE8TtVqYpoUuXnt1QB5kUWrGCbs9qnBo6eqF7TeNNpPA5o9NDAj2qX3aE6WW7x98Ahdc0sNSWp05W23Fk1GGry+AFxBMS7dzslViNAgMBAAECggEAaauoawKhewnAx3yNaMUhonQrYaYiip6toi4remKtOsthQXmggqL0i8dR+1Zt5pIgg9eWLuVYY5Zk1zDDECDzB6TutUY3ge3qMdJa/Nw/BAR/T989EzjIm/8+w78Hy5Ue/9wdJY5cWPe/2heE9hque973tBIDSaFQT3S/mLuZJdQxb0Cp87hlMm1IEaj+OIzqnT6PT6CU4srIobAeksGwId1GfUg7f8bxzP8J8mPUM5PriUXhKxWTjUTJk5KhzLtBdJdA9PCtdg6TqVF7TC7nUz28YKi/GZg4KxD+zAPGYJmZpFXEUU6CYHmoquiLGpNPAyOL5osLIG+TuVbo9oAIAQKBgQDUMOjV9xJKAp6lOIncgHRbdBLhhOjyGQPDBWV949bLAVhP/JzUKnVIsFQ0JrHuz1LFpoe05/gIz+GuZl0u9tvt8tFthJQUFg+pgN/rCFOEhpwPXKXiXtPsa6Qwnln+DThh8xhZLm0NlBsSpT74Jn6VDsv4SqZzUyVEgLlJGB5Q7QKBgQDTrCK6EHtrR7UsEvGmAatddgxpzaIti02Gtz3912+TUzlwkQVMot9+QIVfMm4cV69l4DO44IoNCXqhedJYZ0rICU8kh6vTprdKE9UPJW09HE1HCE+vTigYNuFBeFcfMnoobZRh+WhAWLLA+r0ubMRcWJxKLuBoKUN2aaqMeqVSIQKBgFl1XVSH29AUFqp1kbnC9ktwPl2R2VvoK+vWpJlUq0PfB0qBp+TTGatRm8542d9izPTDZsdgXPPqIK2UPokETmidTKotkPh+7I974+uFESD4V2cqmk07yYENSKWuvSYckL7K7GL87cEhxeAwhsG8CPXyzQ+a+V35meatlnA4mN9pAoGBALUR5UsBWnJ1Dvg65Fg7yVnT596CN596MMFy8ya4Kgv+/33ZsIzULxcTHoRG4YaBQ9uEJPqbqbxpRWvoiBVzlrbzr63yGPye+t/1gybHv2CtWzXOWEwAMbA1EhAtX2mihnka8/mznoic15zZYQfbp0XPzzJuUy0HT1x2G5y8NbvBAoGBAMkIYWAvwvB65BqcpsYbBeTbGO1N+nYNj1AXj9gFF4P6SRIyF9uOlxQu7d5+Ic5/grf4VucuO2kvYvA2VCIVzHjZtU5IQG4mZyS4XhXB8+QervyCaNBeswmDVHIrzKRZCGTAi9gRaIC4LiER0Viok6wvV+YGzEUMME+wP2CrqbXU";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr3L9jYKFBIjy7txVv5NRF+eZnfKJf/S+HJz9C9FaXfesPMag0aMsMpzipoBRwEkZkOpF+5zX5idHleE/M5l+91XBSRX6cwMWhC4oJ2Aq9l9IQIkPX7a2JZ2XoIR8LW5gOhRRyAn5aAD9uqA24Ph0Xk9oNFzr8bWpW3Q3Sv6PNigzj1XRVEEvFRwOuD10Z9awXheyWqMFGUOPjRCmm2tUSagd6SZjRERkSAWedfVv22m5++W7GRPE7VamKaFLl57dUAeZFFqxgm7PapwaOnqhe03jTaTwOaPTQwI9ql92hOllu8ffAIXXNLDUlqdOVttxZNRhq8vgBcQTEu3c7JVYjQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:9099/payment/pay/";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，
    //标红的域名为内网穿透工具生成的域名，没启动一次穿透工具，生成的域名都不一样，所有启动一次都需要进行修改
    public static String return_url = "http://localhost:9099/payment/pay/";
    // 签名方式
    public static String SIGNTYPE = "RSA2";
    // 字符编码格式
    public static String CHARSET = "utf-8";
    //返回格式
    public static String FORMAT = "json";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    // 支付宝网关
    public static String log_path = "/log";

}
