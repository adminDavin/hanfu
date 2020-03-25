package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfStoreRele implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.role_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.stone_id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private Integer stoneId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.last_modifier
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private String lastModifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_store_rele.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_store_rele
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.id
     *
     * @return the value of hf_store_rele.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.id
     *
     * @param id the value for hf_store_rele.id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.role_name
     *
     * @return the value of hf_store_rele.role_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.role_name
     *
     * @param roleName the value for hf_store_rele.role_name
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.stone_id
     *
     * @return the value of hf_store_rele.stone_id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public Integer getStoneId() {
        return stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.stone_id
     *
     * @param stoneId the value for hf_store_rele.stone_id
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setStoneId(Integer stoneId) {
        this.stoneId = stoneId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.create_time
     *
     * @return the value of hf_store_rele.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.create_time
     *
     * @param createTime the value for hf_store_rele.create_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.modify_time
     *
     * @return the value of hf_store_rele.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.modify_time
     *
     * @param modifyTime the value for hf_store_rele.modify_time
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.last_modifier
     *
     * @return the value of hf_store_rele.last_modifier
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.last_modifier
     *
     * @param lastModifier the value for hf_store_rele.last_modifier
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_store_rele.is_deleted
     *
     * @return the value of hf_store_rele.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_store_rele.is_deleted
     *
     * @param isDeleted the value for hf_store_rele.is_deleted
     *
     * @mbg.generated Wed Mar 25 14:05:22 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_store_rele
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
        sb.append(", roleName=").append(roleName);
        sb.append(", stoneId=").append(stoneId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}