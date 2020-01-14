package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class WareHouseRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "仓库id")
    private Integer id;
    @ApiModelProperty(required = true, value = "仓库名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "所属区域")
    private String hfRegion;
    @ApiModelProperty(required = true, value = "仓库描述")
    private String hfDesc;
    @ApiModelProperty(required = true, value = "商家id")
    private Integer bossId;
    @ApiModelProperty(name = "username", required = true, value = "商家名称")
    private String username;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public String getHfRegion() {
        return hfRegion;
    }

    public void setHfRegion(String hfRegion) {
        this.hfRegion = hfRegion;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
