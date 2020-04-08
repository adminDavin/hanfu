package com.hanfu.order.center.manual.model;

import com.hanfu.order.center.request.CommonRequest;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class CreateOrderRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "优惠券Id")
    private Integer[] disconuntId;
    @ApiModelProperty(required = false, value = "活动Id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "物品列表")
    private String goodsList;
    @ApiModelProperty(required = true, value = "订单备注")
    private String hfRemark;
    @ApiModelProperty(required = true, value = "订单类型")
    private String orderType;
    @ApiModelProperty(required = true, value = "支付类型")
    private String paymentName;
    @ApiModelProperty(required = false, value = "实际价格")
    private Integer actualPrice;
    @ApiModelProperty(required = false, value = "物品id")
    private Integer goodsId;
    @ApiModelProperty(required = false, value = "运费")
    private Integer freight;
    @ApiModelProperty(required = false, value = "描述")
    private String hfDesc;
    @ApiModelProperty(required = false, value = "购买数量")
    private Integer quantity;
    @ApiModelProperty(required = false, value = "售卖价格")
    private Integer sellPrice;
    @ApiModelProperty(required = true, value = "邮寄地址")
    private Integer userAddressId;

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer[] getDisconuntId() {
        return disconuntId;
    }

    public void setDisconuntId(Integer[] disconuntId) {
        this.disconuntId = disconuntId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public String getHfRemark() {
        return hfRemark;
    }

    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }
}
