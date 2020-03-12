package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

public class HfStoneRequest {
    @ApiModelProperty(required = true, name = "stoneId", value = "店铺id")
    private Integer stoneId;
    @ApiModelProperty(required = true, name = "bossId", value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = true, name = "userId", value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = true, value = "店铺名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "店铺描述")
    private String hfDesc;
    @ApiModelProperty(required = true, value = "店铺状态")
    private Integer hfStatus;

    public Integer getStoneId() {
        return stoneId;
    }

    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public Integer getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
    }
}
