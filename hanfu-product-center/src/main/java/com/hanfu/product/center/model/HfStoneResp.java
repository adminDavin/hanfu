package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfStoneResp implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer goodId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone_resp.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_stone_resp
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.id
     *
     * @return the value of hf_stone_resp.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.id
     *
     * @param id the value for hf_stone_resp.id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.stone_id
     *
     * @return the value of hf_stone_resp.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.stone_id
     *
     * @param stoneId the value for hf_stone_resp.stone_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.good_id
     *
     * @return the value of hf_stone_resp.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getGoodId() {
        return goodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.good_id
     *
     * @param goodId the value for hf_stone_resp.good_id
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.quantity
     *
     * @return the value of hf_stone_resp.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.quantity
     *
     * @param quantity the value for hf_stone_resp.quantity
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.create_time
     *
     * @return the value of hf_stone_resp.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.create_time
     *
     * @param createTime the value for hf_stone_resp.create_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.modify_time
     *
     * @return the value of hf_stone_resp.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.modify_time
     *
     * @param modifyTime the value for hf_stone_resp.modify_time
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone_resp.is_deleted
     *
     * @return the value of hf_stone_resp.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone_resp.is_deleted
     *
     * @param isDeleted the value for hf_stone_resp.is_deleted
     *
     * @mbg.generated Wed May 06 15:01:58 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone_resp
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
        sb.append(", goodId=").append(goodId);
        sb.append(", quantity=").append(quantity);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}