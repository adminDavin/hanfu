package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StoneRespRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.user_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer goodId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.type
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column stone_resp_record.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table stone_resp_record
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.id
     *
     * @return the value of stone_resp_record.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.id
     *
     * @param id the value for stone_resp_record.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.stone_id
     *
     * @return the value of stone_resp_record.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.stone_id
     *
     * @param stoneId the value for stone_resp_record.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.user_id
     *
     * @return the value of stone_resp_record.user_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.user_id
     *
     * @param userId the value for stone_resp_record.user_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.good_id
     *
     * @return the value of stone_resp_record.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getGoodId() {
        return goodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.good_id
     *
     * @param goodId the value for stone_resp_record.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.quantity
     *
     * @return the value of stone_resp_record.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.quantity
     *
     * @param quantity the value for stone_resp_record.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.type
     *
     * @return the value of stone_resp_record.type
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.type
     *
     * @param type the value for stone_resp_record.type
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.create_time
     *
     * @return the value of stone_resp_record.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.create_time
     *
     * @param createTime the value for stone_resp_record.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.modify_time
     *
     * @return the value of stone_resp_record.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.modify_time
     *
     * @param modifyTime the value for stone_resp_record.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column stone_resp_record.is_deleted
     *
     * @return the value of stone_resp_record.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column stone_resp_record.is_deleted
     *
     * @param isDeleted the value for stone_resp_record.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stone_resp_record
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", stoneId=").append(stoneId);
        sb.append(", userId=").append(userId);
        sb.append(", goodId=").append(goodId);
        sb.append(", quantity=").append(quantity);
        sb.append(", type=").append(type);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}