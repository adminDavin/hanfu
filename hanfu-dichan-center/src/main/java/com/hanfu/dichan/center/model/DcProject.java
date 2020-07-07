package com.hanfu.dichan.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DcProject implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.boss_id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.project_name
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private String projectName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.create_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.modify_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_project.is_deleted
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dc_project
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.id
     *
     * @return the value of dc_project.id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.id
     *
     * @param id the value for dc_project.id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.boss_id
     *
     * @return the value of dc_project.boss_id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.boss_id
     *
     * @param bossId the value for dc_project.boss_id
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.project_name
     *
     * @return the value of dc_project.project_name
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.project_name
     *
     * @param projectName the value for dc_project.project_name
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.create_time
     *
     * @return the value of dc_project.create_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.create_time
     *
     * @param createTime the value for dc_project.create_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.modify_time
     *
     * @return the value of dc_project.modify_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.modify_time
     *
     * @param modifyTime the value for dc_project.modify_time
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_project.is_deleted
     *
     * @return the value of dc_project.is_deleted
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_project.is_deleted
     *
     * @param isDeleted the value for dc_project.is_deleted
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_project
     *
     * @mbg.generated Tue Jul 07 10:53:03 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bossId=").append(bossId);
        sb.append(", projectName=").append(projectName);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}