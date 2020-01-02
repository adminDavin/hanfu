package com.hanfu.inner.model.product.center;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrders implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.id
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.user_id
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.pay_status
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Integer payStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.order_type
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private String orderType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.amount
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Integer amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.pay_method_type
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Integer payMethodType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.hf_memo
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private String hfMemo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.hf_remark
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private String hfRemark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.pay_method_name
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private String payMethodName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.create_time
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private LocalDateTime createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.modify_time
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.last_modifier
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private String lastModifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_orders.is_deleted
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_orders
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.id
     *
     * @return the value of hf_orders.id
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.id
     *
     * @param id the value for hf_orders.id
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.user_id
     *
     * @return the value of hf_orders.user_id
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.user_id
     *
     * @param userId the value for hf_orders.user_id
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.pay_status
     *
     * @return the value of hf_orders.pay_status
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.pay_status
     *
     * @param payStatus the value for hf_orders.pay_status
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.order_type
     *
     * @return the value of hf_orders.order_type
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.order_type
     *
     * @param orderType the value for hf_orders.order_type
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.amount
     *
     * @return the value of hf_orders.amount
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.amount
     *
     * @param amount the value for hf_orders.amount
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.pay_method_type
     *
     * @return the value of hf_orders.pay_method_type
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Integer getPayMethodType() {
        return payMethodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.pay_method_type
     *
     * @param payMethodType the value for hf_orders.pay_method_type
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setPayMethodType(Integer payMethodType) {
        this.payMethodType = payMethodType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.hf_memo
     *
     * @return the value of hf_orders.hf_memo
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public String getHfMemo() {
        return hfMemo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.hf_memo
     *
     * @param hfMemo the value for hf_orders.hf_memo
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setHfMemo(String hfMemo) {
        this.hfMemo = hfMemo == null ? null : hfMemo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.hf_remark
     *
     * @return the value of hf_orders.hf_remark
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public String getHfRemark() {
        return hfRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.hf_remark
     *
     * @param hfRemark the value for hf_orders.hf_remark
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark == null ? null : hfRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.pay_method_name
     *
     * @return the value of hf_orders.pay_method_name
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public String getPayMethodName() {
        return payMethodName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.pay_method_name
     *
     * @param payMethodName the value for hf_orders.pay_method_name
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName == null ? null : payMethodName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.create_time
     *
     * @return the value of hf_orders.create_time
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.create_time
     *
     * @param createTime the value for hf_orders.create_time
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.modify_time
     *
     * @return the value of hf_orders.modify_time
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.modify_time
     *
     * @param modifyTime the value for hf_orders.modify_time
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.last_modifier
     *
     * @return the value of hf_orders.last_modifier
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.last_modifier
     *
     * @param lastModifier the value for hf_orders.last_modifier
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_orders.is_deleted
     *
     * @return the value of hf_orders.is_deleted
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_orders.is_deleted
     *
     * @param isDeleted the value for hf_orders.is_deleted
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_orders
     *
     * @mbg.generated Mon Oct 14 06:55:35 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", orderType=").append(orderType);
        sb.append(", amount=").append(amount);
        sb.append(", payMethodType=").append(payMethodType);
        sb.append(", hfMemo=").append(hfMemo);
        sb.append(", hfRemark=").append(hfRemark);
        sb.append(", payMethodName=").append(payMethodName);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}