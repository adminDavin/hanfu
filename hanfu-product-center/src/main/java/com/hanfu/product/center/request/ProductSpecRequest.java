package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class ProductSpecRequest extends CommonRequest{
	@ApiParam(name = "规格名称", required = true, type = "String")
	private String specsName;
	@ApiParam(name = "商品Id", required = true, type = "Integer")
	private Integer productId;
	@ApiParam(name = "规格类型", required = true, type = "String")
	private String specsType;
	@ApiParam(name = "规格单位", required = true, type = "String")
	private String specsUnit;
	@ApiParam(name = "规格值", required = true, type = "String")
	private String specsValue;
	public String getSpecsName() {
		return specsName;
	}
	public void setSpecsName(String specsName) {
		this.specsName = specsName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getSpecsType() {
		return specsType;
	}
	public void setSpecsType(String specsType) {
		this.specsType = specsType;
	}
	public String getSpecsUnit() {
		return specsUnit;
	}
	public void setSpecsUnit(String specsUnit) {
		this.specsUnit = specsUnit;
	}
	public String getSpecsValue() {
		return specsValue;
	}
	public void setSpecsValue(String specsValue) {
		this.specsValue = specsValue;
	}
	
}
