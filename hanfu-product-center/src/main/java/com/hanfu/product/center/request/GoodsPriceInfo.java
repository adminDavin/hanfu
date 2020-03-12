package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class GoodsPriceInfo extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id")
    private Integer HfGoodsId;
    @ApiModelProperty(required = true, value = "物品价格")
    private Integer sellPrice;
    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")
    private String username;
    @ApiModelProperty(required = true, value = "划线价")
    private Integer linePrice;
    @ApiModelProperty(required = true, value = "物品数量")
    private Integer quantity;
    @ApiModelProperty(required = false, value = "仓库id")
    private Integer wareHouseId;
    @ApiModelProperty(required = true, value = "库存描述")
    private String respDesc;
    
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

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public Integer getLinePrice() {
		return linePrice;
	}

	public void setLinePrice(Integer linePrice) {
		this.linePrice = linePrice;
	}

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
