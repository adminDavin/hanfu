package com.hanfu.user.center.request;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APPID = "2016092700611035";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDP1hzPIGDA7XHmPpSIwvZVBGU1wsVYKactul5v4bJSV3Le0zGhV82GIP0ByX9tNzU+//kaxFgRsdZyPvR28iDbf9IaVZ+n+cZHA2rvevmntzoi3wMSEhfi8gHmY3+FuUALmICcK8a8rNV5qjpzRAyOb0IRLh1OxtCb64caKQivIt6OTEmLvyJ9BFdYbOSxHWpaOd6UMoXzG9gUzoRkMQiv9bGPkbOjxSXvYZXIXMF9OH+aHQ0oB94Od/Y/uc1xav23hPEq7HfaEMdNc3ZeRxC2JRGZm0HEn360hQ5fduE5QHnC0havpecvPNmmJv18KmYPICvK0WEONXQzZkd7ieWZAgMBAAECggEAPCPk6jqgRLNwxgNNz9U6mpdPAiN+H2jOK3lGYnGYvCL55miOb/WMT0e0bpPz/5J+IeDIIGq4YUZom16ENvcRcvKlxWEJdf3c1iezBeE75MAsXnLR3KTHrLACxLv95qAYXEKg1JAGTgplK3OKvlfumjivkXEaofT94GBjE7rT/HlBDUQd7fofu337fkVlzaluL9n6KwPyFoOhEoXz/ESANlcAzFxEdrHJ+41ywfivpCmPYipJ5RlPcvrxYPQVVcID9yxJmYl+yT+5zq+RENQw9UErKwufO27wMmlk8h9qnJ353Q5qzlI1XuziW27e+WcuCKA5KjPQDTW0FQX46+auAQKBgQDsM2A34G4bxF4FrKQMXYK+9AeN7jnOiObVigJWDQJdDmE6ajFJyjywSMJdJHFkt093jq1Q7GbFQratvXnsKw808gBqlUDHruHfQyAjB7Euwo/uj+Rf64wFSYeNyPz5Y4ZzTW/egp0+fNlwdQueF/ZxTix3Kveopju05OdP5LsDKQKBgQDhQhFWfLdhrzsmCbo1+FwI9XLQcijrwkDJcDCwjSw77Utdc7ZB9b47Qw8gPdGHsHUNST2SO0MUolxpcslhK0LCp2JH+jmyLvutCwJHge2ajEHr9UQNwqNT0VjfvLOCsqy1XjcT4UMkVbLuZfQVkuGeQMlZAz7vMwjnLHM5OWYM8QKBgQCx7+YtUfJBEsZt6efPtOZZKgdC4dNEhxe2iHhIERbVuY5/bh5hY2witT1O47RU1k+UVrS0QNMPCboK2rGtu6aCEUlFAdlCVLmt8qiUGyhcfI/nfRjjNdRO61yl8GK7EUHpPM3ox2bj+zPnJxWwbIboRqbywzi9ITXbiZHEOyCzOQKBgERWMMBWMaP27zDI/q7k27b0D5LhLzzmv5Tc7kiBa4v6n0SImCBGlyGPZIVZe7l4dOpE7+LvC5i08OgkpKdP4n7/HLfNV7eQvVVv448iAZ1DM7SLwcuS4s3I0vd9XdAmuHKFVJw5WI8rWI8uavAokT3bVbolW/BhQTAwsfwIR8dhAoGAOnxoALzuKX647N6k8AQEdNGr6CsDfwVR050vJHDbYVAxocGifiNE64/LJyzG0lO3mj4eiVwpikwPMx1oDhPuDURXrLK0TNzBV8LsGQvf+mTDgabAo8Jf/fYY3Ea8NOnE4Zrg6mEC93C0rUHEfsO8g+B2Skvw+8p6M6SHsmNsthw=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz9YczyBgwO1x5j6UiML2VQRlNcLFWCmnLbpeb+GyUldy3tMxoVfNhiD9Acl/bTc1Pv/5GsRYEbHWcj70dvIg23/SGlWfp/nGRwNq73r5p7c6It8DEhIX4vIB5mN/hblAC5iAnCvGvKzVeao6c0QMjm9CES4dTsbQm+uHGikIryLejkxJi78ifQRXWGzksR1qWjnelDKF8xvYFM6EZDEIr/Wxj5Gzo8Ul72GVyFzBfTh/mh0NKAfeDnf2P7nNcWr9t4TxKux32hDHTXN2XkcQtiURmZtBxJ9+tIUOX3bhOUB5wtIWr6XnLzzZpib9fCpmDyArytFhDjV0M2ZHe4nlmQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:9097/payment/pay/";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，
    //标红的域名为内网穿透工具生成的域名，没启动一次穿透工具，生成的域名都不一样，所有启动一次都需要进行修改
    public static String return_url = "http://localhost:9097/payment/pay/";
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
