package com.hanfu.product.center.manual.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class CheckResp implements Serializable{
	private Integer productId;
	private Integer goodsNum;
	private String respList;
	private String name1;
	private String value1;
	private String name;
	private String value;
	
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
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
