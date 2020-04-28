package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.util.List;

import com.hanfu.product.center.cart.model.Cart;

public class CartList implements Serializable{
	
	
	private String name;
	private Integer ofenBuyCount;
	private Integer all;
	private List<Cart> goodList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Cart> getGoodList() {
		return goodList;
	}
	public void setGoodList(List<Cart> goodList) {
		this.goodList = goodList;
	}
	public Integer getOfenBuyCount() {
		return ofenBuyCount;
	}
	public void setOfenBuyCount(Integer ofenBuyCount) {
		this.ofenBuyCount = ofenBuyCount;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
}
