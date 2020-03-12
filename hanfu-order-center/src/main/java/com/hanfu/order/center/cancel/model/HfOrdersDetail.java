package com.hanfu.order.center.cancel.model;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class HfOrdersDetail {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @ApiModelProperty(value = "订单id")
    private Integer ordersId;
    @ApiModelProperty(value = "仓库id")
    private Integer respId;
    @ApiModelProperty(value = "订单详情状态")
    private String orderDetailStatus;
    @ApiModelProperty(value = "物品id")
    private Integer googsId;
    @ApiModelProperty(value = "税金")
    private Integer hfTax;
    @ApiModelProperty(value = "购买价格")
    private Integer purchasePrice;
    @ApiModelProperty(value = "购买数量")
    private Integer purchaseQuantity;
    @ApiModelProperty(value = "配送方式")
    private String distribution;
    @ApiModelProperty(value = "订单描述")
    private String hfDesc;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;
    @ApiModelProperty(value = "最后一次修改人")
    private String lastModifier;

    private Short isDeleted;

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
        this.orderDetailStatus = orderDetailStatus == null ? null : orderDetailStatus.trim();
    }

    public Integer getGoogsId() {
        return googsId;
    }

    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    public Integer getHfTax() {
        return hfTax;
    }

    public void setHfTax(Integer hfTax) {
        this.hfTax = hfTax;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution == null ? null : distribution.trim();
    }

    public String getHfDesc() {
        return hfDesc;
    }

    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}