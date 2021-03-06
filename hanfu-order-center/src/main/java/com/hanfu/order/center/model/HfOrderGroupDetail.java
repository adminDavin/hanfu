package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfOrderGroupDetail implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.order_group_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private Integer orderGroupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.user_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.order_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.order_code
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private String orderCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.create_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.modify_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_order_group_detail.id_deleted
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_order_group_detail
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.id
     *
     * @return the value of hf_order_group_detail.id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.id
     *
     * @param id the value for hf_order_group_detail.id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.order_group_id
     *
     * @return the value of hf_order_group_detail.order_group_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public Integer getOrderGroupId() {
        return orderGroupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.order_group_id
     *
     * @param orderGroupId the value for hf_order_group_detail.order_group_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setOrderGroupId(Integer orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.user_id
     *
     * @return the value of hf_order_group_detail.user_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.user_id
     *
     * @param userId the value for hf_order_group_detail.user_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.order_id
     *
     * @return the value of hf_order_group_detail.order_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.order_id
     *
     * @param orderId the value for hf_order_group_detail.order_id
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.order_code
     *
     * @return the value of hf_order_group_detail.order_code
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.order_code
     *
     * @param orderCode the value for hf_order_group_detail.order_code
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.create_date
     *
     * @return the value of hf_order_group_detail.create_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.create_date
     *
     * @param createDate the value for hf_order_group_detail.create_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.modify_date
     *
     * @return the value of hf_order_group_detail.modify_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.modify_date
     *
     * @param modifyDate the value for hf_order_group_detail.modify_date
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_order_group_detail.id_deleted
     *
     * @return the value of hf_order_group_detail.id_deleted
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_order_group_detail.id_deleted
     *
     * @param idDeleted the value for hf_order_group_detail.id_deleted
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_order_group_detail
     *
     * @mbg.generated Fri Mar 20 15:54:29 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderGroupId=").append(orderGroupId);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}