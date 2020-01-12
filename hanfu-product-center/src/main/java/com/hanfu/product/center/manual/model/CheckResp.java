package com.hanfu.product.center.manual.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CheckResp implements Serializable{
	private Integer productId;
	private Integer goodsNum;
	private String respList;
	private String name;
	private String value;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
