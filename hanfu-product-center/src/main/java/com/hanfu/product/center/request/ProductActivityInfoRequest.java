package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class ProductActivityInfoRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "折扣比例")
    private double discountRatio;
    @ApiModelProperty(required = false, value = "团购人数")
    private Integer groupNum;
    @ApiModelProperty(required = false, value = "优惠价格")
    private double favoravlePrice;
    @ApiModelProperty(required = false, value = "库存上限")
    private Integer inventoryCelling;
    @ApiModelProperty(required = false, value = "分销比例")
    private String distributionRatio;
	public double getDiscountRatio() {
		return discountRatio;
	}
	public void setDiscountRatio(double discountRatio) {
		this.discountRatio = discountRatio;
	}
	public Integer getGroupNum() {
		return groupNum;
	}
	public void setGroupNum(Integer groupNum) {
		this.groupNum = groupNum;
	}
	public double getFavoravlePrice() {
		return favoravlePrice;
	}
	public void setFavoravlePrice(double favoravlePrice) {
		this.favoravlePrice = favoravlePrice;
	}
	public Integer getInventoryCelling() {
		return inventoryCelling;
	}
	public void setInventoryCelling(Integer inventoryCelling) {
		this.inventoryCelling = inventoryCelling;
	}
	public String getDistributionRatio() {
		return distributionRatio;
	}
	public void setDistributionRatio(String distributionRatio) {
		this.distributionRatio = distributionRatio;
	}

}
