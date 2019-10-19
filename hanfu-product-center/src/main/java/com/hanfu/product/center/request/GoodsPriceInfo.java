package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

public class GoodsPriceInfo {
    @ApiModelProperty(required = true, value = "物品id")
    private Integer HfGoodsId;
    @ApiModelProperty(required = true, value = "物品价格")
    private Integer HfPrice;
    public Integer getHfGoodsId() {
        return HfGoodsId;
    }
    public void setHfGoodsId(Integer hfGoodsId) {
        HfGoodsId = hfGoodsId;
    }
    public Integer getHfPrice() {
        return HfPrice;
    }
    public void setHfPrice(Integer hfPrice) {
        HfPrice = hfPrice;
    }
    
}
