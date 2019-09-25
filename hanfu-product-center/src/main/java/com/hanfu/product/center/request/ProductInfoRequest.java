package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class ProductInfoRequest extends CommonRequest{
	@ApiParam(name = "商品id", required = true, type = "Integer")
	private Integer productId;
	@ApiParam(name = "属性名称", required = true, type = "String")
	private String propertyName;
	@ApiParam(name = "属性值", required = true, type = "String")
	private String propertyValue;
	@ApiParam(name = "属性单位",required = true, type = "String")
	private String propertyUnit;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	public String getPropertyUnit() {
		return propertyUnit;
	}
	public void setPropertyUnit(String propertyUnit) {
		this.propertyUnit = propertyUnit;
	}
	
}
