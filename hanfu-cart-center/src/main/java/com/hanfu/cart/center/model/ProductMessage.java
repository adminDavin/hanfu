package com.hanfu.cart.center.model;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ProductMessage implements Serializable{
	private Integer productId;
	private Integer productPrice;
	private String productNum;
	/*是否勾选*/
	private String check;
	private String productName;
	private String productIcon;
	
	/**
	 * 状态, 0正常1下架.
	 */
	private String productStatus;

	/* 商品小图*/
	private String img;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductIcon() {
		return productIcon;
	}

	public void setProductIcon(String productIcon) {
		this.productIcon = productIcon;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}




}
