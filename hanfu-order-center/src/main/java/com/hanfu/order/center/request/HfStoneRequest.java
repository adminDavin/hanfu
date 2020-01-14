package com.hanfu.order.center.request;

import io.swagger.annotations.ApiModelProperty;

public class HfStoneRequest {
    @ApiModelProperty(required = true, name = "userId", value = "商家id")
    private Integer bossId;
    @ApiModelProperty(required = true, name = "stoneManagerId", value = "店铺管理者")
    private Integer userId;
    @ApiModelProperty(required = true, value = "店铺名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "店铺描述")
    private String stoneDesc;
    @ApiModelProperty(required = true, value = "店铺状态")
    private Integer stoneStatus;

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

    public String getStoneDesc() {
        return stoneDesc;
    }

    public void setStoneDesc(String stoneDesc) {
        this.stoneDesc = stoneDesc;
    }

    public Integer getStoneStatus() {
        return stoneStatus;
    }

    public void setStoneStatus(Integer stoneStatus) {
        this.stoneStatus = stoneStatus;
    }


}
