package com.hanfu.payment.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class GoodsSpecRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "物品id, 即对应到商铺商品的某一种种类")
    private Integer goodsId;
    @ApiModelProperty(required = true, value = "规格名称")
    private String specName;
    @ApiModelProperty(required = true, value = "规格描述")
    private String specDesc;
    @ApiModelProperty(required = true, value = "规格值")
    private String specValue;
    @ApiModelProperty(required = true, value = "店家名称, 登录修改的用户名称")
    private String username;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSpecDesc() {
        return specDesc;
    }

    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
