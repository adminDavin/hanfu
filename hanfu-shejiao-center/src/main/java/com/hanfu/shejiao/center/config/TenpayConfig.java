package com.hanfu.shejiao.center.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** 
 * @Description: 微信支付配置类
 * @Param: 
 * @return: 
 * @Author: zengXianKang
 * @Date: 2019/7/28 
 */
@Component
@ConfigurationProperties(prefix = "tenpayconfig")
public class TenpayConfig {
    //appId
    private String appId;
    //商户号
    private String mchId;
    //商户的key(API密匙)
    private String key;
    //API支付请求地址
    private String payUrl;
    //API查询请求地址
    private String queryUrl;
    //Sign=WXPay
    private String packageValue;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getQueryUrl() {
		return queryUrl;
	}
	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}
	public String getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}
    
}    
