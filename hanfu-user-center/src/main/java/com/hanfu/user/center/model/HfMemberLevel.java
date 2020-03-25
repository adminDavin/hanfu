package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfMemberLevel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_member_level.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_member_level.level_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private String levelName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_member_level.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_member_level.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_member_level.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_member_level
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_member_level.id
     *
     * @return the value of hf_member_level.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_member_level.id
     *
     * @param id the value for hf_member_level.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_member_level.level_name
     *
     * @return the value of hf_member_level.level_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_member_level.level_name
     *
     * @param levelName the value for hf_member_level.level_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_member_level.create_time
     *
     * @return the value of hf_member_level.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_member_level.create_time
     *
     * @param createTime the value for hf_member_level.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_member_level.modify_time
     *
     * @return the value of hf_member_level.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_member_level.modify_time
     *
     * @param modifyTime the value for hf_member_level.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_member_level.is_deleted
     *
     * @return the value of hf_member_level.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_member_level.is_deleted
     *
     * @param isDeleted the value for hf_member_level.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_member_level
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", levelName=").append(levelName);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}