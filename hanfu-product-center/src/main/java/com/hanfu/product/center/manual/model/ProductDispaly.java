package com.hanfu.product.center.manual.model;

import java.io.Serializable;

public class ProductDispaly implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3834215356820052568L;
	
	private String productName;
	private String productDesc;
	private String productCategoryName;
	private Integer categoryId;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
	
	
}
