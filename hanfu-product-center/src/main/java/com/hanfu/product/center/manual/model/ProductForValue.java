package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductForValue implements Serializable{
    @ApiModelProperty(required = false, value = "类目名称")
    private String productCategoryName;
    @ApiModelProperty(required = false, value = "品牌名称")
    private String brandName;
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}
