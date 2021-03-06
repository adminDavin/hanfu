package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DiscountCouponOrder implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.order_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.discount_coupon_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private Integer discountCouponId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.use_state
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private Integer useState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.create_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.modify_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column discount_coupon_order.is_deleted
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private Integer isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table discount_coupon_order
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.id
     *
     * @return the value of discount_coupon_order.id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.id
     *
     * @param id the value for discount_coupon_order.id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.order_id
     *
     * @return the value of discount_coupon_order.order_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.order_id
     *
     * @param orderId the value for discount_coupon_order.order_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.discount_coupon_id
     *
     * @return the value of discount_coupon_order.discount_coupon_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public Integer getDiscountCouponId() {
        return discountCouponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.discount_coupon_id
     *
     * @param discountCouponId the value for discount_coupon_order.discount_coupon_id
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setDiscountCouponId(Integer discountCouponId) {
        this.discountCouponId = discountCouponId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.use_state
     *
     * @return the value of discount_coupon_order.use_state
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public Integer getUseState() {
        return useState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.use_state
     *
     * @param useState the value for discount_coupon_order.use_state
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setUseState(Integer useState) {
        this.useState = useState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.create_date
     *
     * @return the value of discount_coupon_order.create_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.create_date
     *
     * @param createDate the value for discount_coupon_order.create_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.modify_date
     *
     * @return the value of discount_coupon_order.modify_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.modify_date
     *
     * @param modifyDate the value for discount_coupon_order.modify_date
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column discount_coupon_order.is_deleted
     *
     * @return the value of discount_coupon_order.is_deleted
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column discount_coupon_order.is_deleted
     *
     * @param isDeleted the value for discount_coupon_order.is_deleted
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table discount_coupon_order
     *
     * @mbg.generated Sat Apr 11 12:10:07 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", discountCouponId=").append(discountCouponId);
        sb.append(", useState=").append(useState);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}