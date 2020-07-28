package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfIcon implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.boss_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private Integer bossId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.file_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.icon_name
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private String iconName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.url
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.param
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private String param;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.create_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.modify_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_icon.is_deleted
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_icon
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.id
     *
     * @return the value of hf_icon.id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.id
     *
     * @param id the value for hf_icon.id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.boss_id
     *
     * @return the value of hf_icon.boss_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public Integer getBossId() {
        return bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.boss_id
     *
     * @param bossId the value for hf_icon.boss_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.file_id
     *
     * @return the value of hf_icon.file_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.file_id
     *
     * @param fileId the value for hf_icon.file_id
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.icon_name
     *
     * @return the value of hf_icon.icon_name
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.icon_name
     *
     * @param iconName the value for hf_icon.icon_name
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setIconName(String iconName) {
        this.iconName = iconName == null ? null : iconName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.url
     *
     * @return the value of hf_icon.url
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.url
     *
     * @param url the value for hf_icon.url
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.param
     *
     * @return the value of hf_icon.param
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public String getParam() {
        return param;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.param
     *
     * @param param the value for hf_icon.param
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.create_time
     *
     * @return the value of hf_icon.create_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.create_time
     *
     * @param createTime the value for hf_icon.create_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.modify_time
     *
     * @return the value of hf_icon.modify_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.modify_time
     *
     * @param modifyTime the value for hf_icon.modify_time
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_icon.is_deleted
     *
     * @return the value of hf_icon.is_deleted
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_icon.is_deleted
     *
     * @param isDeleted the value for hf_icon.is_deleted
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_icon
     *
     * @mbg.generated Fri Jul 24 15:53:08 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", bossId=").append(bossId);
        sb.append(", fileId=").append(fileId);
        sb.append(", iconName=").append(iconName);
        sb.append(", url=").append(url);
        sb.append(", param=").append(param);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}