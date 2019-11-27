package com.hanfu.activity.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ActivityCompony implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.company_name
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private String companyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.company_info
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private String companyInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.remarks
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private String remarks;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.create_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.modify_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.is_deleted
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private Short isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_compony.file_id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private Integer fileId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activity_compony
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.id
     *
     * @return the value of activity_compony.id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.id
     *
     * @param id the value for activity_compony.id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.company_name
     *
     * @return the value of activity_compony.company_name
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.company_name
     *
     * @param companyName the value for activity_compony.company_name
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.company_info
     *
     * @return the value of activity_compony.company_info
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public String getCompanyInfo() {
        return companyInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.company_info
     *
     * @param companyInfo the value for activity_compony.company_info
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo == null ? null : companyInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.remarks
     *
     * @return the value of activity_compony.remarks
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.remarks
     *
     * @param remarks the value for activity_compony.remarks
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.create_time
     *
     * @return the value of activity_compony.create_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.create_time
     *
     * @param createTime the value for activity_compony.create_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.modify_time
     *
     * @return the value of activity_compony.modify_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.modify_time
     *
     * @param modifyTime the value for activity_compony.modify_time
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.is_deleted
     *
     * @return the value of activity_compony.is_deleted
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.is_deleted
     *
     * @param isDeleted the value for activity_compony.is_deleted
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_compony.file_id
     *
     * @return the value of activity_compony.file_id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_compony.file_id
     *
     * @param fileId the value for activity_compony.file_id
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_compony
     *
     * @mbg.generated Thu Nov 28 07:46:15 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", companyName=").append(companyName);
        sb.append(", companyInfo=").append(companyInfo);
        sb.append(", remarks=").append(remarks);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", fileId=").append(fileId);
        sb.append("]");
        return sb.toString();
    }
}