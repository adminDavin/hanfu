package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class ProductRequest extends CommonRequest {
	@ApiParam(name = "商品名称", required = true, type="String")
	private String produtName;
	@ApiParam(name="商品类目Id", required = true, type="Integer")
	private Integer categoryId;
	@ApiParam(name="商品描述", required = true, type="String")
	private String productDesc;
	@ApiParam(name = "商家Id", required = true, type="Integer")
	private Integer bossId;
	public String getProdutName() {
		return produtName;
	}
	public void setProdutName(String produtName) {
		this.produtName = produtName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public Integer getBossId() {
		return bossId;
	}
	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}
	
	
	
}
