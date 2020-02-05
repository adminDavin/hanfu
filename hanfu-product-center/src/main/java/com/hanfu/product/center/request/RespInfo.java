package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class RespInfo extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id")
    private Integer HfGoodsId;
    @ApiModelProperty(required = true, value = "物品数量")
    private Integer quantity;
    @ApiModelProperty(required = false, value = "仓库id")
    private Integer wareHouseId;
    @ApiModelProperty(required = true, value = "库存描述")
    private String respDesc;
    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }
}
