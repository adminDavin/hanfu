package com.hanfu.cart.center.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class ProductCart implements Serializable{
	private Integer userid;
	private List<ProductMessage> productMessageList;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public List<ProductMessage> getProductMessageList() {
		return productMessageList;
	}
	public void setProductMessageList(List<ProductMessage> productMessageList) {
		this.productMessageList = productMessageList;
	}
	
}
