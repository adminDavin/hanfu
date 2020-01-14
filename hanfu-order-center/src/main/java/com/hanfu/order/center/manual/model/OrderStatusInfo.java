package com.hanfu.order.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class OrderStatusInfo implements Serializable {
    @ApiModelProperty(required = true, value = "类目名称")
    private Integer id;
    @ApiModelProperty(required = true, value = "状态名称")
    private String hfName;
    @ApiModelProperty(required = true, value = "订单状态")
    private Integer hfStatus;
    @ApiModelProperty(required = true, value = "订单状态描述")
    private String hfDesc;

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

    public Integer getHfStatus() {
        return hfStatus;
    }

    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

}
