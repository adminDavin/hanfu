package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "商品id")
    private Integer id;
    @ApiModelProperty(required = true, value = "商品名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "商品所属的类目id")
    private Integer categoryId;
    @ApiModelProperty(required = true, value = "品牌id")
    private Integer brandId;
    @ApiModelProperty(required = true, value = "商品描述")
    private String productDesc;
    @ApiModelProperty(required = true, name = "bossId", value = "商家id")
    private Integer bossId;
    @ApiModelProperty(name = "lastModifier", required = true, value = "商家名称")
    private String lastModifier;
    @ApiModelProperty(required = true, value = "规格类型")
    private String[] specType;
    @ApiModelProperty(required = true, value = "规格单元")
    private String specUnit;
    @ApiModelProperty(required = true, value = "规格规格默认值")
    private String specValue;
    @ApiModelProperty(required = true, value = "规格名称")
    private String[] SpecName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHfName() {
		return hfName;
	}
	public void setHfName(String hfName) {
		this.hfName = hfName;
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
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	public String[] getSpecType() {
		return specType;
	}
	public void setSpecType(String[] specType) {
		this.specType = specType;
	}
	public String getSpecUnit() {
		return specUnit;
	}
	public void setSpecUnit(String specUnit) {
		this.specUnit = specUnit;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	public String[] getSpecName() {
		return SpecName;
	}
	public void setSpecName(String[] specName) {
		SpecName = specName;
	}
  
}
