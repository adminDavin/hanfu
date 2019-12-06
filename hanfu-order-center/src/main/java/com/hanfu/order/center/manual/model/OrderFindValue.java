package com.hanfu.order.center.manual.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderFindValue implements Serializable{
	private Integer orderId;
	private String hfName;
	private String payMethodType;
	private String orderDetailStatus;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getHfName() {
		return hfName;
	}
	public void setHfName(String hfName) {
		this.hfName = hfName;
	}
	public String getPayMethodType() {
		return payMethodType;
	}
	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	
}
