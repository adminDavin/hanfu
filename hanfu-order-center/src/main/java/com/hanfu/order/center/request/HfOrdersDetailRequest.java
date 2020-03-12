package com.hanfu.order.center.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfOrdersDetailRequest implements Serializable {
    @ApiModelProperty(required = false, value = "订单详情id")
    private Integer id;
    @ApiModelProperty(required = false, value = "订单id")
    private Integer ordersId;
    @ApiModelProperty(required = false, value = "仓库id")
    private Integer respId;
    @ApiModelProperty(required = false, value = "订单详情状况")
    private String orderDetailStatus;
    @ApiModelProperty(required = false, value = "物品id")
    private Integer[] googsId;
    @ApiModelProperty(required = false, value = "税金")
    private Integer hfTax;
    @ApiModelProperty(required = false, value = "购买价格")
    private Integer[] purchasePrice;
    @ApiModelProperty(required = false, value = "购买数量")
    private Integer[] purchaseQuantity;
    @ApiModelProperty(required = false, value = "配送方式")
    private String distribution;
    @ApiModelProperty(required = false, value = "订单描述")
    private String hfDesc;
    @ApiModelProperty(required = false, value = "创建时间")
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getRespId() {
        return respId;
    }

    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus;
    }

    public Integer[] getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer[] googsId) {
        this.googsId = googsId;
    }

    public Integer getHfTax() {
        return hfTax;
    }

    public void setHfTax(Integer hfTax) {
        this.hfTax = hfTax;
    }

    public Integer[] getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer[] purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer[] getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer[] purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}	
