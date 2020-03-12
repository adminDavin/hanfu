package com.hanfu.order.center.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfOrdersRequest implements Serializable {
    @ApiModelProperty(required = false, value = "订单id")
    private Integer Id;
    @ApiModelProperty(required = false, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "支付状态")
    private Integer payStatus;
    @ApiModelProperty(required = false, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = false, value = "支付金额")
    private Integer amount;
    @ApiModelProperty(required = false, value = "支付方式类型")
    private Integer payMethodType;
    @ApiModelProperty(required = false, value = "支付附言")
    private String hfMemo;
    @ApiModelProperty(required = false, value = "备注")
    private String hfRemark;
    @ApiModelProperty(required = false, value = "支付方式名称")
    private String payMethodName;
    @ApiModelProperty(required = false, value = "创建时间")
    private LocalDateTime createTime;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethodType() {
        return payMethodType;
    }

    public void setPayMethodType(Integer payMethodType) {
        this.payMethodType = payMethodType;
    }

    public String getHfMemo() {
        return hfMemo;
    }

    public void setHfMemo(String hfMemo) {
        this.hfMemo = hfMemo;
    }

    public String getHfRemark() {
        return hfRemark;
    }

    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


}
