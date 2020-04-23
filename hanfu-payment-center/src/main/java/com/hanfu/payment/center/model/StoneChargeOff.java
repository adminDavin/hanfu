package com.hanfu.payment.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StoneChargeOff implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.charge_off_type
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private String chargeOffType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.charge_off_state
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Integer chargeOffState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.stone_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.order_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Integer orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.format
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private String format;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.stone_desc
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private String stoneDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.actual_price
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Integer actualPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.create_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.modify_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.last_modifier
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_charge_off.is_deleted
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stone_charge_off
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.id
     *
     * @return the value of stone_charge_off.id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.id
     *
     * @param id the value for stone_charge_off.id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.charge_off_type
     *
     * @return the value of stone_charge_off.charge_off_type
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public String getChargeOffType() {
        return chargeOffType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.charge_off_type
     *
     * @param chargeOffType the value for stone_charge_off.charge_off_type
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setChargeOffType(String chargeOffType) {
        this.chargeOffType = chargeOffType == null ? null : chargeOffType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.charge_off_state
     *
     * @return the value of stone_charge_off.charge_off_state
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Integer getChargeOffState() {
        return chargeOffState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.charge_off_state
     *
     * @param chargeOffState the value for stone_charge_off.charge_off_state
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setChargeOffState(Integer chargeOffState) {
        this.chargeOffState = chargeOffState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.stone_id
     *
     * @return the value of stone_charge_off.stone_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.stone_id
     *
     * @param stoneId the value for stone_charge_off.stone_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.order_id
     *
     * @return the value of stone_charge_off.order_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.order_id
     *
     * @param orderId the value for stone_charge_off.order_id
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.format
     *
     * @return the value of stone_charge_off.format
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public String getFormat() {
        return format;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.format
     *
     * @param format the value for stone_charge_off.format
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.stone_desc
     *
     * @return the value of stone_charge_off.stone_desc
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public String getStoneDesc() {
        return stoneDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.stone_desc
     *
     * @param stoneDesc the value for stone_charge_off.stone_desc
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setStoneDesc(String stoneDesc) {
        this.stoneDesc = stoneDesc == null ? null : stoneDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.actual_price
     *
     * @return the value of stone_charge_off.actual_price
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Integer getActualPrice() {
        return actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.actual_price
     *
     * @param actualPrice the value for stone_charge_off.actual_price
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.create_time
     *
     * @return the value of stone_charge_off.create_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.create_time
     *
     * @param createTime the value for stone_charge_off.create_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.modify_time
     *
     * @return the value of stone_charge_off.modify_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.modify_time
     *
     * @param modifyTime the value for stone_charge_off.modify_time
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.last_modifier
     *
     * @return the value of stone_charge_off.last_modifier
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.last_modifier
     *
     * @param lastModifier the value for stone_charge_off.last_modifier
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_charge_off.is_deleted
     *
     * @return the value of stone_charge_off.is_deleted
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_charge_off.is_deleted
     *
     * @param isDeleted the value for stone_charge_off.is_deleted
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_charge_off
     *
     * @mbg.generated Tue Apr 21 11:24:04 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", chargeOffType=").append(chargeOffType);
        sb.append(", chargeOffState=").append(chargeOffState);
        sb.append(", stoneId=").append(stoneId);
        sb.append(", orderId=").append(orderId);
        sb.append(", format=").append(format);
        sb.append(", stoneDesc=").append(stoneDesc);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}