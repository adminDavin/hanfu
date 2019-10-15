package com.hanfu.product.center.manual.model;

import java.io.Serializable;

public class HfGoodsDisplay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5934450949730812779L;
	
	private Integer id;
	private String productName;
	private String productCategoryName;
	private String goodsDesc;
	private Integer categoryId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
}
