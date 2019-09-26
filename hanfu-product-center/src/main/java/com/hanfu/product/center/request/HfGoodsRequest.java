package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsRequest extends CommonRequest {
	@ApiModelProperty(required = true, value = "商品实体id, 即具体到到店铺的商品")
	private Integer instanceId;
	@ApiModelProperty(required = true, value = "物品数量")
	private Integer quantity;
	@ApiModelProperty(required = true, value = "物品描述")
	private String goodDesc;
	@ApiModelProperty(required = true, value = "物品价格,单位: 分", example = "0")
	private Integer priceId;
	public Integer getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getGoodDesc() {
		return goodDesc;
	}
	public void setGoodDesc(String goodDesc) {
		this.goodDesc = goodDesc;
	}
	public Integer getPriceId() {
		return priceId;
	}
	public void setPriceId(Integer priceId) {
		this.priceId = priceId;
	}
	
	
	
}
