package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

public class GoodsPriceInfo extends CommonRequest{
    @ApiModelProperty(required = true, value = "物品id")
    private Integer HfGoodsId;
    @ApiModelProperty(required = true, value = "物品价格")
    private Integer sellPrice;
    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")
	private String username;
    public Integer getHfGoodsId() {
        return HfGoodsId;
    }
    public void setHfGoodsId(Integer hfGoodsId) {
        HfGoodsId = hfGoodsId;
    }
	public Integer getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
}
