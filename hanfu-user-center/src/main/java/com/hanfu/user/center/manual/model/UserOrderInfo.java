package com.hanfu.user.center.manual.model;

import java.io.Serializable;

public class UserOrderInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2860833393982676021L;
	private Integer orderCount;
	private String orderStatus;
	
	public UserOrderInfo(Integer orderCount, String orderStatus) {
		super();
		this.orderCount = orderCount;
		this.orderStatus = orderStatus;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	

}
