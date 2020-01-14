package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.Map;


@SuppressWarnings("serial")
public class CheckResp implements Serializable{
	private Integer productId;
	private Integer goodsNum;
	private String respList;
	private String productSpecName;
	private String hfValue;

	public String getProductSpecName() {
		return productSpecName;
	}
	public void setProductSpecName(String productSpecName) {
		this.productSpecName = productSpecName;
	}
	public String getHfValue() {
		return hfValue;
	}
	public void setHfValue(String hfValue) {
		this.hfValue = hfValue;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
    public String getRespList() {
        return respList;
    }
    public void setRespList(String respList) {
        this.respList = respList;
    }

}
