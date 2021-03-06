package com.hanfu.payment.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfVipUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.vip_day
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Integer vipDay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.boss_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.user_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.start_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private LocalDateTime startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.end_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private LocalDateTime endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.vip_state
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private String vipState;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.vip_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Integer vipId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.create_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.modify_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_vip_user.is_deleted
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_vip_user
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.id
     *
     * @return the value of hf_vip_user.id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.id
     *
     * @param id the value for hf_vip_user.id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.vip_day
     *
     * @return the value of hf_vip_user.vip_day
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Integer getVipDay() {
        return vipDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.vip_day
     *
     * @param vipDay the value for hf_vip_user.vip_day
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setVipDay(Integer vipDay) {
        this.vipDay = vipDay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.boss_id
     *
     * @return the value of hf_vip_user.boss_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.boss_id
     *
     * @param bossId the value for hf_vip_user.boss_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.user_id
     *
     * @return the value of hf_vip_user.user_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.user_id
     *
     * @param userId the value for hf_vip_user.user_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.start_time
     *
     * @return the value of hf_vip_user.start_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.start_time
     *
     * @param startTime the value for hf_vip_user.start_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.end_time
     *
     * @return the value of hf_vip_user.end_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.end_time
     *
     * @param endTime the value for hf_vip_user.end_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.vip_state
     *
     * @return the value of hf_vip_user.vip_state
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public String getVipState() {
        return vipState;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.vip_state
     *
     * @param vipState the value for hf_vip_user.vip_state
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setVipState(String vipState) {
        this.vipState = vipState == null ? null : vipState.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.vip_id
     *
     * @return the value of hf_vip_user.vip_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Integer getVipId() {
        return vipId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.vip_id
     *
     * @param vipId the value for hf_vip_user.vip_id
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.create_time
     *
     * @return the value of hf_vip_user.create_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.create_time
     *
     * @param createTime the value for hf_vip_user.create_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.modify_time
     *
     * @return the value of hf_vip_user.modify_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.modify_time
     *
     * @param modifyTime the value for hf_vip_user.modify_time
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_vip_user.is_deleted
     *
     * @return the value of hf_vip_user.is_deleted
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_vip_user.is_deleted
     *
     * @param isDeleted the value for hf_vip_user.is_deleted
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_vip_user
     *
     * @mbg.generated Mon Dec 07 14:35:32 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vipDay=").append(vipDay);
        sb.append(", bossId=").append(bossId);
        sb.append(", userId=").append(userId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", vipState=").append(vipState);
        sb.append(", vipId=").append(vipId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}