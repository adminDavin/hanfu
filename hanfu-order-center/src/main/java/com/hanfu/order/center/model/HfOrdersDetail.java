package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrdersDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.orders_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer ordersId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer respId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.order_detail_status
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private String orderDetailStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.googs_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer googsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer hfTax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.purchase_price
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer purchasePrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.purchase_quantity
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Integer purchaseQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.Distribution
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private String distribution;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.create_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders_detail.is_deleted
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.id
     *
     * @return the value of hf_orders_detail.id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.id
     *
     * @param id the value for hf_orders_detail.id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.orders_id
     *
     * @return the value of hf_orders_detail.orders_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getOrdersId() {
        return ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.orders_id
     *
     * @param ordersId the value for hf_orders_detail.orders_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.resp_id
     *
     * @return the value of hf_orders_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getRespId() {
        return respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.resp_id
     *
     * @param respId the value for hf_orders_detail.resp_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setRespId(Integer respId) {
        this.respId = respId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.order_detail_status
     *
     * @return the value of hf_orders_detail.order_detail_status
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public String getOrderDetailStatus() {
        return orderDetailStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.order_detail_status
     *
     * @param orderDetailStatus the value for hf_orders_detail.order_detail_status
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setOrderDetailStatus(String orderDetailStatus) {
        this.orderDetailStatus = orderDetailStatus == null ? null : orderDetailStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.googs_id
     *
     * @return the value of hf_orders_detail.googs_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getGoogsId() {
        return googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.googs_id
     *
     * @param googsId the value for hf_orders_detail.googs_id
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setGoogsId(Integer googsId) {
        this.googsId = googsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.hf_tax
     *
     * @return the value of hf_orders_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getHfTax() {
        return hfTax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.hf_tax
     *
     * @param hfTax the value for hf_orders_detail.hf_tax
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setHfTax(Integer hfTax) {
        this.hfTax = hfTax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.purchase_price
     *
     * @return the value of hf_orders_detail.purchase_price
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.purchase_price
     *
     * @param purchasePrice the value for hf_orders_detail.purchase_price
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.purchase_quantity
     *
     * @return the value of hf_orders_detail.purchase_quantity
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Integer getPurchaseQuantity() {
        return purchaseQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.purchase_quantity
     *
     * @param purchaseQuantity the value for hf_orders_detail.purchase_quantity
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setPurchaseQuantity(Integer purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.Distribution
     *
     * @return the value of hf_orders_detail.Distribution
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public String getDistribution() {
        return distribution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.Distribution
     *
     * @param distribution the value for hf_orders_detail.Distribution
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setDistribution(String distribution) {
        this.distribution = distribution == null ? null : distribution.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.hf_desc
     *
     * @return the value of hf_orders_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.hf_desc
     *
     * @param hfDesc the value for hf_orders_detail.hf_desc
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.create_time
     *
     * @return the value of hf_orders_detail.create_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.create_time
     *
     * @param createTime the value for hf_orders_detail.create_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.modify_time
     *
     * @return the value of hf_orders_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.modify_time
     *
     * @param modifyTime the value for hf_orders_detail.modify_time
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.last_modifier
     *
     * @return the value of hf_orders_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.last_modifier
     *
     * @param lastModifier the value for hf_orders_detail.last_modifier
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders_detail.is_deleted
     *
     * @return the value of hf_orders_detail.is_deleted
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders_detail.is_deleted
     *
     * @param isDeleted the value for hf_orders_detail.is_deleted
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders_detail
     *
     * @mbg.generated Tue Apr 07 11:13:03 CST 2020
     */
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