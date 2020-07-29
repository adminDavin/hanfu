package com.hanfu.dichan.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DcCategory implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.boss_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.project_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer projectId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.hf_name
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.parent_category_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer parentCategoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.category_detail_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer categoryDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.level_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer levelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.create_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.modify_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.is_deleted
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Short isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_category.file_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private Integer fileId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dc_category
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.id
     *
     * @return the value of dc_category.id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.id
     *
     * @param id the value for dc_category.id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.boss_id
     *
     * @return the value of dc_category.boss_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.boss_id
     *
     * @param bossId the value for dc_category.boss_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.project_id
     *
     * @return the value of dc_category.project_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.project_id
     *
     * @param projectId the value for dc_category.project_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.hf_name
     *
     * @return the value of dc_category.hf_name
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.hf_name
     *
     * @param hfName the value for dc_category.hf_name
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.parent_category_id
     *
     * @return the value of dc_category.parent_category_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.parent_category_id
     *
     * @param parentCategoryId the value for dc_category.parent_category_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.category_detail_id
     *
     * @return the value of dc_category.category_detail_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getCategoryDetailId() {
        return categoryDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.category_detail_id
     *
     * @param categoryDetailId the value for dc_category.category_detail_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setCategoryDetailId(Integer categoryDetailId) {
        this.categoryDetailId = categoryDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.level_id
     *
     * @return the value of dc_category.level_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.level_id
     *
     * @param levelId the value for dc_category.level_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.create_time
     *
     * @return the value of dc_category.create_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.create_time
     *
     * @param createTime the value for dc_category.create_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.modify_time
     *
     * @return the value of dc_category.modify_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.modify_time
     *
     * @param modifyTime the value for dc_category.modify_time
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.is_deleted
     *
     * @return the value of dc_category.is_deleted
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.is_deleted
     *
     * @param isDeleted the value for dc_category.is_deleted
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_category.file_id
     *
     * @return the value of dc_category.file_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_category.file_id
     *
     * @param fileId the value for dc_category.file_id
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_category
     *
     * @mbg.generated Fri Jul 10 16:43:32 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bossId=").append(bossId);
        sb.append(", projectId=").append(projectId);
        sb.append(", hfName=").append(hfName);
        sb.append(", parentCategoryId=").append(parentCategoryId);
        sb.append(", categoryDetailId=").append(categoryDetailId);
        sb.append(", levelId=").append(levelId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", fileId=").append(fileId);
        sb.append("]");
        return sb.toString();
    }
}