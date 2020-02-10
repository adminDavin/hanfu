package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfSeniority implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.seniority_name
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private String seniorityName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.file_id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.create_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.modifity_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private LocalDateTime modifityTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_seniority.is_deleted
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.id
     *
     * @return the value of hf_seniority.id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.id
     *
     * @param id the value for hf_seniority.id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.seniority_name
     *
     * @return the value of hf_seniority.seniority_name
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public String getSeniorityName() {
        return seniorityName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.seniority_name
     *
     * @param seniorityName the value for hf_seniority.seniority_name
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setSeniorityName(String seniorityName) {
        this.seniorityName = seniorityName == null ? null : seniorityName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.file_id
     *
     * @return the value of hf_seniority.file_id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.file_id
     *
     * @param fileId the value for hf_seniority.file_id
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.create_time
     *
     * @return the value of hf_seniority.create_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.create_time
     *
     * @param createTime the value for hf_seniority.create_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.modifity_time
     *
     * @return the value of hf_seniority.modifity_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public LocalDateTime getModifityTime() {
        return modifityTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.modifity_time
     *
     * @param modifityTime the value for hf_seniority.modifity_time
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setModifityTime(LocalDateTime modifityTime) {
        this.modifityTime = modifityTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_seniority.is_deleted
     *
     * @return the value of hf_seniority.is_deleted
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_seniority.is_deleted
     *
     * @param isDeleted the value for hf_seniority.is_deleted
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_seniority
     *
     * @mbg.generated Mon Feb 10 09:00:31 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", seniorityName=").append(seniorityName);
        sb.append(", fileId=").append(fileId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifityTime=").append(modifityTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}