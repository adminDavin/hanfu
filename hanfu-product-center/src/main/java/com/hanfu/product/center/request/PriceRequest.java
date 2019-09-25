package com.hanfu.product.center.request;

import io.swagger.annotations.ApiParam;

public class PriceRequest extends CommonRequest{
	@ApiParam(name = "商品定价实体id", required = true, type = "Integer")
	private Integer instanceId;
	@ApiParam(name = "商品定价规格类型", required = true, type = "String")
	private String hfSpec;
	@ApiParam(name = "商品定价实体规格单位", required = true, type = "String")
	private String specDesc;
	@ApiParam(name = "商品定价实体规格值", required = true, type = "String")
	private String specValue;
	public Integer getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Integer priceId) {
		this.instanceId = priceId;
	}
	public String getHfSpec() {
		return hfSpec;
	}
	public void setHfSpec(String priceSpec) {
		this.hfSpec = priceSpec;
	}
	public String getSpecDesc() {
		return specDesc;
	}
	public void setSpecDesc(String specDesc) {
		this.specDesc = specDesc;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	
}
