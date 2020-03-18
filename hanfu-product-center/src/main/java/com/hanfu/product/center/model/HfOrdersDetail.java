package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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


    private LocalDateTime createTime;


    private LocalDateTime modifyTime;

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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ordersId=").append(ordersId);
        sb.append(", respId=").append(respId);
        sb.append(", orderDetailStatus=").append(orderDetailStatus);
        sb.append(", googsId=").append(googsId);
        sb.append(", hfTax=").append(hfTax);
        sb.append(", purchasePrice=").append(purchasePrice);
        sb.append(", purchaseQuantity=").append(purchaseQuantity);
        sb.append(", distribution=").append(distribution);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}
