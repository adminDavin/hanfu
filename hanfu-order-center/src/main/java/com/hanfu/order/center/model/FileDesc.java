package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FileDesc implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.file_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.user_id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.group_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private String groupName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.remote_filename
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private String remoteFilename;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.create_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.modify_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_desc.is_deleted
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table file_desc
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.id
     *
     * @return the value of file_desc.id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.id
     *
     * @param id the value for file_desc.id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.file_name
     *
     * @return the value of file_desc.file_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.file_name
     *
     * @param fileName the value for file_desc.file_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.user_id
     *
     * @return the value of file_desc.user_id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.user_id
     *
     * @param userId the value for file_desc.user_id
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.group_name
     *
     * @return the value of file_desc.group_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.group_name
     *
     * @param groupName the value for file_desc.group_name
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.remote_filename
     *
     * @return the value of file_desc.remote_filename
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public String getRemoteFilename() {
        return remoteFilename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.remote_filename
     *
     * @param remoteFilename the value for file_desc.remote_filename
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setRemoteFilename(String remoteFilename) {
        this.remoteFilename = remoteFilename == null ? null : remoteFilename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.create_time
     *
     * @return the value of file_desc.create_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.create_time
     *
     * @param createTime the value for file_desc.create_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.modify_time
     *
     * @return the value of file_desc.modify_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.modify_time
     *
     * @param modifyTime the value for file_desc.modify_time
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_desc.is_deleted
     *
     * @return the value of file_desc.is_deleted
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_desc.is_deleted
     *
     * @param isDeleted the value for file_desc.is_deleted
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table file_desc
     *
     * @mbg.generated Tue Feb 04 14:49:11 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", userId=").append(userId);
        sb.append(", groupName=").append(groupName);
        sb.append(", remoteFilename=").append(remoteFilename);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}