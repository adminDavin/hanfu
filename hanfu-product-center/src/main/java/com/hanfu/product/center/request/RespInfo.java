package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class RespInfo extends CommonRequest {
	@ApiModelProperty(required = true, value = "物品id")
    private Integer HfGoodsId;
    @ApiModelProperty(required = true, value = "物品数量")
    private Integer quantity;
    @ApiModelProperty(required = true, value = "仓库id")
    private Integer wareHouseId;
	public Integer getHfGoodsId() {
		return HfGoodsId;
	}
	public void setHfGoodsId(Integer hfGoodsId) {
		HfGoodsId = hfGoodsId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getWareHouseId() {
		return wareHouseId;
	}
	public void setWareHouseId(Integer wareHouseId) {
		this.wareHouseId = wareHouseId;
	}
    
    
}
