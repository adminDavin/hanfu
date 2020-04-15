package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfStone implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.hf_name
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.user_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.hf_desc
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.boss_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.create_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.expire_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private LocalDateTime expireTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.hf_status
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Integer hfStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.is_deleted
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Short isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.concern_count
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private Integer concernCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_stone.address
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_stone
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.id
     *
     * @return the value of hf_stone.id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.id
     *
     * @param id the value for hf_stone.id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.hf_name
     *
     * @return the value of hf_stone.hf_name
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.hf_name
     *
     * @param hfName the value for hf_stone.hf_name
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.user_id
     *
     * @return the value of hf_stone.user_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.user_id
     *
     * @param userId the value for hf_stone.user_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.hf_desc
     *
     * @return the value of hf_stone.hf_desc
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.hf_desc
     *
     * @param hfDesc the value for hf_stone.hf_desc
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.boss_id
     *
     * @return the value of hf_stone.boss_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.boss_id
     *
     * @param bossId the value for hf_stone.boss_id
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.create_time
     *
     * @return the value of hf_stone.create_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.create_time
     *
     * @param createTime the value for hf_stone.create_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.expire_time
     *
     * @return the value of hf_stone.expire_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.expire_time
     *
     * @param expireTime the value for hf_stone.expire_time
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.hf_status
     *
     * @return the value of hf_stone.hf_status
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Integer getHfStatus() {
        return hfStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.hf_status
     *
     * @param hfStatus the value for hf_stone.hf_status
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setHfStatus(Integer hfStatus) {
        this.hfStatus = hfStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.is_deleted
     *
     * @return the value of hf_stone.is_deleted
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.is_deleted
     *
     * @param isDeleted the value for hf_stone.is_deleted
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.concern_count
     *
     * @return the value of hf_stone.concern_count
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public Integer getConcernCount() {
        return concernCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.concern_count
     *
     * @param concernCount the value for hf_stone.concern_count
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setConcernCount(Integer concernCount) {
        this.concernCount = concernCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_stone.address
     *
     * @return the value of hf_stone.address
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_stone.address
     *
     * @param address the value for hf_stone.address
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_stone
     *
     * @mbg.generated Wed Apr 15 11:41:26 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hfName=").append(hfName);
        sb.append(", userId=").append(userId);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", bossId=").append(bossId);
        sb.append(", createTime=").append(createTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", hfStatus=").append(hfStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", concernCount=").append(concernCount);
        sb.append(", address=").append(address);
        sb.append("]");
        return sb.toString();
    }
}