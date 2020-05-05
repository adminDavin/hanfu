package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PayOrder implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.user_id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.pay_status
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Integer payStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.actual_price
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Integer actualPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.amount
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Integer amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.payment_name
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private String paymentName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.create_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.modify_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pay_order.is_deleted
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pay_order
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.id
     *
     * @return the value of pay_order.id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.id
     *
     * @param id the value for pay_order.id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.user_id
     *
     * @return the value of pay_order.user_id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.user_id
     *
     * @param userId the value for pay_order.user_id
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.pay_status
     *
     * @return the value of pay_order.pay_status
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.pay_status
     *
     * @param payStatus the value for pay_order.pay_status
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.actual_price
     *
     * @return the value of pay_order.actual_price
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Integer getActualPrice() {
        return actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.actual_price
     *
     * @param actualPrice the value for pay_order.actual_price
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.amount
     *
     * @return the value of pay_order.amount
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.amount
     *
     * @param amount the value for pay_order.amount
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.payment_name
     *
     * @return the value of pay_order.payment_name
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.payment_name
     *
     * @param paymentName the value for pay_order.payment_name
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.create_time
     *
     * @return the value of pay_order.create_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.create_time
     *
     * @param createTime the value for pay_order.create_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.modify_time
     *
     * @return the value of pay_order.modify_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.modify_time
     *
     * @param modifyTime the value for pay_order.modify_time
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pay_order.is_deleted
     *
     * @return the value of pay_order.is_deleted
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pay_order.is_deleted
     *
     * @param isDeleted the value for pay_order.is_deleted
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pay_order
     *
     * @mbg.generated Tue May 05 15:38:19 CST 2020
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
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", amount=").append(amount);
        sb.append(", paymentName=").append(paymentName);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}