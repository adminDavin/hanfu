package com.hanfu.payment.center.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

/**
 * @Description: 微信支付配置信息
 * @Author: junqiang.lu
 * @Date: 2018/7/10
 */
@Component
@Configuration
@PropertySource(value = "classpath:wxPay.properties")
public class WxPayConfig {

    /**
     * APP 应用 id
     */
    @Value("${wxPay.pay.appId}")
    private String appId;

    /**
     * 公众号应用 id
     */
    @Value("${wxPay.pay.publicAppId}")
    private String publicAppId;

    /**
     * 小程序应用 id
     */
    @Value("${wxPay.pay.miniAppId}")
    private String miniAppId;

    /**
     * 商户号 mch id
     */
    @Value("${wxPay.pay.mchId}")
    private String mchId;

    /**
     * 商户号支付密钥(key)
     */
    @Value("${wxPay.pay.key}")
    private String key;

    /**
     * 商品描述
     */
    @Value("${wxPay.pay.body}")
    private String body;

    /**
     * 通知地址
     */
    @Value("${wxPay.pay.notifyUrl}")
    private String notifyUrl;

    /**
     * H5 支付所需网址
     */
    @Value("${wxPay.pay.wapUrl}")
    private String wapUrl;

    /**
     * H5 交易类型
     */
    @Value("${wxPay.pay.tradeType.h5}")
    private String tradeTypeH5;

    /**
     * NATIVE 交易类型
     */
    @Value("${wxPay.pay.tradeType.native}")
    private String tradeTypeNative;

    /**
     * JSAPI 交易类型
     */
    @Value("${wxPay.pay.tradeType.jsAPI}")
    private String TradeTypeJsApi;

    /**
     * APP 交易类型
     */
    @Value("${wxPay.pay.tradeType.app}")
    private String tradeTypeApp;

    /**
     * 微信接口返回结果成功状态值(responseSuccess)
     */
    @Value("${wxPay.pay.response.success}")
    private String responseSuccess;

    /**
     * 微信接口返回结果失败状态值(responseFail)
     */
    @Value("${wxPay.pay.response.fail}")
    private String responseFail;

    /**
     * 微信签名字段名(sign)
     */
    @Value("${wxPay.pay.field.sign}")
    private String fieldSign;

    /**
     * 微信支付签名方式
     */
    @Value("${wxPay.pay.signType}")
    private String signType;

    /**
     * 微信 APP 支付扩展字段( APP 支付 package: Sign=WXPay)
     */
    @Value("${wxPay.pay.packageApp}")
    private String packageApp;

    /**
     * 微信「统一下单」接口地址
     */
    @Value("${wxPay.pay.api.unifiedOrderUrl}")
    private String unifiedOrderUrl;

    /**
     * 微信查询订单信息接口地址
     */
    @Value("${wxPay.pay.api.orderQueryUrl}")
    private String orderQueryUrl;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    public String getAppId() {
        return appId;
    }


    public String getPublicAppId() {
        return publicAppId;
    }


    public String getMiniAppId() {
        return miniAppId;
    }


    public String getMchId() {
        return mchId;
    }


    public String getKey() {
        return key;
    }


    public String getBody() {
        return body;
    }


    public String getNotifyUrl() {
        return notifyUrl;
    }


    public String getWapUrl() {
        return wapUrl;
    }


    public String getTradeTypeH5() {
        return tradeTypeH5;
    }


    public String getTradeTypeNative() {
        return tradeTypeNative;
    }


    public String getTradeTypeJsApi() {
        return TradeTypeJsApi;
    }


    public String getTradeTypeApp() {
        return tradeTypeApp;
    }


    public String getResponseSuccess() {
        return responseSuccess;
    }


    public String getResponseFail() {
        return responseFail;
    }


    public String getFieldSign() {
        return fieldSign;
    }


    public String getSignType() {
        return signType;
    }


    public String getPackageApp() {
        return packageApp;
    }


    public String getUnifiedOrderUrl() {
        return unifiedOrderUrl;
    }


    public String getOrderQueryUrl() {
        return orderQueryUrl;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }


    public void setPublicAppId(String publicAppId) {
        this.publicAppId = publicAppId;
    }


    public void setMiniAppId(String miniAppId) {
        this.miniAppId = miniAppId;
    }


    public void setMchId(String mchId) {
        this.mchId = mchId;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public void setBody(String body) {
        this.body = body;
    }


    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }


    public void setWapUrl(String wapUrl) {
        this.wapUrl = wapUrl;
    }


    public void setTradeTypeH5(String tradeTypeH5) {
        this.tradeTypeH5 = tradeTypeH5;
    }


    public void setTradeTypeNative(String tradeTypeNative) {
        this.tradeTypeNative = tradeTypeNative;
    }


    public void setTradeTypeJsApi(String tradeTypeJsApi) {
        TradeTypeJsApi = tradeTypeJsApi;
    }


    public void setTradeTypeApp(String tradeTypeApp) {
        this.tradeTypeApp = tradeTypeApp;
    }


    public void setResponseSuccess(String responseSuccess) {
        this.responseSuccess = responseSuccess;
    }


    public void setResponseFail(String responseFail) {
        this.responseFail = responseFail;
    }


    public void setFieldSign(String fieldSign) {
        this.fieldSign = fieldSign;
    }


    public void setSignType(String signType) {
        this.signType = signType;
    }


    public void setPackageApp(String packageApp) {
        this.packageApp = packageApp;
    }


    public void setUnifiedOrderUrl(String unifiedOrderUrl) {
        this.unifiedOrderUrl = unifiedOrderUrl;
    }


    public void setOrderQueryUrl(String orderQueryUrl) {
        this.orderQueryUrl = orderQueryUrl;
    }
    
    
}
