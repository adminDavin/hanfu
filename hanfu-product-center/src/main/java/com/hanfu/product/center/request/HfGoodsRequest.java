package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfGoodsRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "商品实体id, 即具体到到店铺的商品")
    private Integer instanceId;
    @ApiModelProperty(required = true, value = "仓库地址")
    private Integer respId;
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

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
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
