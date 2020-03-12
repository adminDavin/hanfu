package com.hanfu.cart.center.model;



import java.io.Serializable;



@SuppressWarnings("serial")

public class Product implements Serializable{

	private String productId;

    private Integer productPrice;

    private Integer productNum;

    /*是否勾选*/

    private String check;

    private String productName;

    /** 状态, 0正常1下架. */	

    private Integer productStatus;



    /* 商品小图*/

    private Integer productIcon;



	public String getProductId() {

		return productId;

	}



	public void setProductId(String productId) {

		this.productId = productId;

	}



	public Integer getProductPrice() {

		return productPrice;

	}



	public void setProductPrice(Integer productPrice) {

		this.productPrice = productPrice;

	}



	public Integer getProductNum() {

		return productNum;

	}



	public void setProductNum(Integer productNum) {

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



	public Integer getProductStatus() {

		return productStatus;

	}



	public void setProductStatus(Integer productStatus) {

		this.productStatus = productStatus;

	}



	public Integer getProductIcon() {

		return productIcon;

	}



	public void setProductIcon(Integer productIcon) {

		this.productIcon = productIcon;

	}

    

}
