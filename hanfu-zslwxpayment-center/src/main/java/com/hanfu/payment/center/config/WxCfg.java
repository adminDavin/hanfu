package com.hanfu.payment.center.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信参数配置中心
 */
@Component
public class WxCfg {

//    @Value("${wx.appType}")
    private String appType = "APP";//App支付交易类型
//    @Value("${wx.appNotifyUrl}")
    private String appNotifyUrl = "https://swcloud.tjsichuang.cn:1445/api/cart/hf-payment/complete";//App回调地址
//    @Value("${wx.h5Type}")
    private String h5Type = "MWEB";//H5支付交易类型
//    @Value("${wx.h5NotifyUrl}")
    private String h5NotifyUrl = "https://swcloud.tjsichuang.cn:1445/api/cart/hf-payment/complete";//H5回调地址
//    @Value("${wx.ip}")
    private String ip = "39.100.237.144";//服务器ip
//    @Value("${wx.redirect_url}")
    private String redirect_url = "https://swcloud.tjsichuang.cn:1445/api/cart/hf-payment/complete";//跳转地址


    public String getAppNotifyUrl() {
        return appNotifyUrl;
    }

    public void setAppNotifyUrl(String appNotifyUrl) {
        this.appNotifyUrl = appNotifyUrl;
    }

    public String getH5NotifyUrl() {
        return h5NotifyUrl;
    }

    public void setH5NotifyUrl(String h5NotifyUrl) {
        this.h5NotifyUrl = h5NotifyUrl;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getH5Type() {
        return h5Type;
    }

    public void setH5Type(String h5Type) {
        this.h5Type = h5Type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }
}