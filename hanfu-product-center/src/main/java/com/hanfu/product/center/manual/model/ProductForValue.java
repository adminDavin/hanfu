package com.hanfu.product.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductForValue implements Serializable{
    @ApiModelProperty(required = false, value = "类目名称")
    private String productCategoryName;
    @ApiModelProperty(required = false, value = "价格1")
    private Integer sellPrice1;
    @ApiModelProperty(required = false, value = "价格2")
    private Integer sellPrice2;
    @ApiModelProperty(required = false, value = "商品名称")
    private String goodsName;

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getSellPrice1() {
		return sellPrice1;
	}
	public void setSellPrice1(Integer sellPrice1) {
		this.sellPrice1 = sellPrice1;
	}
	public Integer getSellPrice2() {
		return sellPrice2;
	}
	public void setSellPrice2(Integer sellPrice2) {
		this.sellPrice2 = sellPrice2;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	
}
