package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ProductDispaly implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3834215356820052568L;
	
	private String productName;
	private String productDesc;
	private String productCategoryName;
	private Integer id;
	private Integer categoryId;
	private Integer brandId;
	private String lastModifier;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	
	
	
}
