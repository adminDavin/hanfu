package com.hanfu.payment.center.manual.dao;

public class OrderInfo {

    private String appid;// 小程序ID
    private String mchId;// 商户号
    private String nonceStr;// 随机字符串
    private String signType;//签名类型
    private String sign;// 签名
    private String body;// 商品描述
    private String outTradeNo;// 商户订单号
    private int totalFee;// 标价金额 ,单位为分
    private String spbillCreateIp;// 终端IP
    private String notifyUrl;// 通知地址
    private String tradeType;// 交易类型
    private String openid;//用户标识
    public String getAppid() {
        return appid;
    }
    public void setAppid(String appid) {
        this.appid = appid;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getNonceStr() {
        return nonceStr;
    }
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getOutTradeNo() {
        return outTradeNo;
    }
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    public int getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }
    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }
    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    
}
