package com.hanfu.payment.center.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付的参数配置
 *
 * @author mengday zhang
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "pay.alipay")
public class AlipayConfig {

    /**
     * 支付宝gatewayUrl
     */
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";
    /**
     * 商户应用id
     */
    private String appid = "2021001188658781";
    /**
     * RSA私钥，用于对商户请求报文加签
     */
    private String appPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDpHG7POZ5UUpBYMUfcufQkK+xCL84X4hvhtipkCUurazg7J8WHFDVQtjOjs/1zYfw5aBh/WXfUpNbhewz94ArNGgHmqW+ZzxlrUq1FlKSlJ2zcs4SArPbskXYy6JRH9RgP5reQn9SWp7GG3pv/4q5k+xLDkgloQOfB31fdboabO/saBPLNcE3Yv8PysKn2r5EYqbjftv0eHRlNynWXWIFBkTpdeE3ZKcizmZhvYcSABxA0vkVfx0UG2Ww8PKwWd1Ix1HGk2YZPtSFXw4RJNQDAOIg1faH3cb0ERC0F3hW/1VLvBt+OB6ryZizhkGBL2dHb2hVqwOGMQRAuNfRD1KoTAgMBAAECggEAPx1Dozemdo4kJryxCEIQp/p8bS8kTbMaeXIo6AzcP/03rEyIXppKFSA/uwLwjFzrPV/BwjknCJvY0hPO4K3YPs/b0+fsH2jDKRupbj1glpamEttH9M7fzA1WQ1JVU2Y4rZV5/d5IYMHmfu2mrxFgxlZ0Lh9MDL4GAafG0l0qdLy0QknxW/FQ55enP1Brzjk9b5t2IFwDI8LSqEfgCd1GF7zb3s9hETF6JMlJOfZb/9aMxjIcqFiHk0hQcRtIhIUIDfyRVdP7xIegIjvziWTH/f46wvkZUUSFKVKXp45kDS0MtcruWWy6rCeCSrA5JMD0uMwCWb9TIkwsREtWYpf9gQKBgQD//x07UYNLsfpiDf2ywM49YxoZJBU95a/Aj9Yly+1SPzIwWC8vRLrbviH9m1DLGzb/rXe8/MktxdTpwVKlTPtWf8Kuyn7ad7shZsNIke+HZT7HN6nuyLJfmO20NmEuXlRK9Tuk9NlC8phH+0t48vWc45EcrzsIIeIx6moPfpH0hwKBgQDpHT1OIwOtEwa5+SIRxsYmkWv9+bJRHRom85Z4oMCYMwkdeTwxuy1vStP4ClHA+tIw5IzlubaIMcirM+CndBwA9hmek6QODt4AQsY1oxGD7xWPHO85Jnfxav69OR/wcpH4d/PL9r3tweh2huWDF2scXDpEmGlFMZgjDEkVvPlNFQKBgF1mT9z/nX61ndu+rQxV6ildif0YPCPMNHZ2RDSgFaNHaWFYG0cMtXxM/4vO6ghgko1wVdp/BEuN/WOX5DZpQt5JWNd7DBNbUcwa+S72B8WHfAMlXPvA/8t/OWLh4E0Rn4GFvdAugUI6zrKcjenAAzrNkHk9ubT639NEapPiow/rAoGAV9uJS3Wu4QAWvOjEimacL9UHGoWNSzzoql2MDJKpg7NYcB9NLIEbMqLFGOrqvQzLQ1ogMES6IsKAmuXGedYs57/ZEAcdAZ9g1EBWa8i1xT/KpFGHZ8az1e05bR+mhfzoPvkWjXzFQGKgtmtI/QtKdznbTBcVNdrnN+5dbS/31bECgYEAzuqk6h36sQZMI6sJlErpEmeP6iKRCpagS2z5Qc/7w9NExgEbedSW50HTQ4KM/1UEIQ4Wjq8mfNLh8blFEU7IIvof6dEprkSOLwhJJO5TGHbpk1RHvB3wOADksq0Y/0eIdML+2k4OzpJ/JVw10O3DDWCCs5QMUNhjc0jJP+piZQ0=";
    /**
     * 支付宝RSA公钥，用于验签支付宝应答
     */
    private String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgoWeddoBW3spATYcb1sgXy+FV+igdXtKCD1HGwQS6UudMyMvuWOIcP1fGyt1izN5KpKNcWvhdrt5NGo0xbYi27BqUfG4bUgodUD4xZ0D1wyGgo/0wQg7F5lItm9Oi70ZjFt+pGFVDabDM7y9EMyL4DjONPExxIrO2aGwjFFeOMLbptYJ6mS4859kR9m33v7E5D6Gwl3sLOuwHGTfyKA3jtSAVMOokbB1H05a7nm4gllKjqb5UPLdsJFusZENkSpxULkVDAtP05+O6a7lCYMADkUo+f1Av3fLPSRPhdbBotySkLTXff7DTlbWijm8aFxyCEjHV35LV53oHD5Vzn3UdQIDAQAB";
    /**
     * 签名类型
     */
    private String signType = "RSA2";
    /**
     * 格式
     */
    private String formate = "json";
    /**
     * 编码
     */
    private String charset = "UTF-8";
    /**
     * 同步地址
     */
    private String returnUrl;
    /**
     * 异步地址
     */
    private String notifyUrl;
    /**
     * 最大查询次数
     */
    private static int maxQueryRetry = 5;
    /**
     * 查询间隔（毫秒）
     */
    private static long queryDuration = 5000;
    /**
     * 最大撤销次数
     */
    private static int maxCancelRetry = 3;
    /**
     * 撤销间隔（毫秒）
     */
    private static long cancelDuration = 3000;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public static int getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public static void setMaxQueryRetry(int maxQueryRetry) {
        AlipayConfig.maxQueryRetry = maxQueryRetry;
    }

    public static long getQueryDuration() {
        return queryDuration;
    }

    public static void setQueryDuration(long queryDuration) {
        AlipayConfig.queryDuration = queryDuration;
    }

    public static int getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public static void setMaxCancelRetry(int maxCancelRetry) {
        AlipayConfig.maxCancelRetry = maxCancelRetry;
    }

    public static long getCancelDuration() {
        return cancelDuration;
    }

    public static void setCancelDuration(long cancelDuration) {
        AlipayConfig.cancelDuration = cancelDuration;
    }

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(this.getGatewayUrl(),
                this.getAppid(),
                this.getAppPrivateKey(),
                this.getFormate(),
                this.getCharset(),
                this.getAlipayPublicKey(),
                this.getSignType());
    }
}
