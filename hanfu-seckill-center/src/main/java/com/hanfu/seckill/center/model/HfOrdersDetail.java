package com.hanfu.seckill.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class HfOrdersDetail implements Serializable {

    private Integer id;


    private Integer ordersId;

    private Integer respId;


    private String orderDetailStatus;


    private Integer googsId;


    private Integer hfTax;

    private Integer purchasePrice;


    private Integer purchaseQuantity;


    private String distribution;

    private String hfDesc;


    private Date createTime;


    private Date modifyTime;

    private String lastModifier;


    private Short isDeleted;


    private static final long serialVersionUID = 1L;


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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
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