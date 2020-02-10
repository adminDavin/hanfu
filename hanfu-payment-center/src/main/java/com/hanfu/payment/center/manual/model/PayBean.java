package com.hanfu.payment.center.manual.model;


import java.io.Serializable;

public class PayBean implements Serializable {
    private static final long serialVersionUID = -7551908500227408235L;

    /**
     * 用户订单号
     */
    private String orderNo;
    /**
     * 订单总金额
     */
    private String amount;
    /**
     * 用户实际 ip 地址
     */
    private String ip;

    /**
     * 支付方式
     * 11: 支付宝电脑网站支付
     * 12: 支付宝手机网站支付
     * 13: 支付宝 APP 支付
     *
     * 21: 微信 NATIVE 支付(二维码支付)
     * 22: 微信 JSAPI 支付
     * 23: 微信 H5 支付
     * 24: 微信 APP 支付
     * 25: 微信 小程序 支付
     */
    private int payType;

    /**
     * 微信 JSAPI/小程序 支付必传
     */
    private String openId;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getAmount() {
        return amount;
    }

    public String getIp() {
        return ip;
    }

    public int getPayType() {
        return payType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    
}
