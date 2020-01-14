package com.hanfu.order.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfRespRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id")
    private Integer goodsId;
    @ApiModelProperty(required = true, value = "仓库id")
    private Integer wareHouseId;
    @ApiModelProperty(required = true, value = "物品数量")
    private String quatity;
    @ApiModelProperty(required = true, value = "库存状态")
    private String hfStatus;
    @ApiModelProperty(required = true, value = "库存描述")
    private String hfDesc;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(Integer wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getQuatity() {
        return quatity;
    }

    public void setQuatity(String quatity) {
        this.quatity = quatity;
    }

    public String getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(String hfStatus) {
        this.hfStatus = hfStatus;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }


}
