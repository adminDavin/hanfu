package com.hanfu.order.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RoleJurisdiction implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.role_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.jurisdiction_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private Integer jurisdictionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.create_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.modify_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_jurisdiction.is_deleted
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table role_jurisdiction
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.id
     *
     * @return the value of role_jurisdiction.id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.id
     *
     * @param id the value for role_jurisdiction.id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.role_id
     *
     * @return the value of role_jurisdiction.role_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.role_id
     *
     * @param roleId the value for role_jurisdiction.role_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.jurisdiction_id
     *
     * @return the value of role_jurisdiction.jurisdiction_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public Integer getJurisdictionId() {
        return jurisdictionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.jurisdiction_id
     *
     * @param jurisdictionId the value for role_jurisdiction.jurisdiction_id
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setJurisdictionId(Integer jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.create_time
     *
     * @return the value of role_jurisdiction.create_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.create_time
     *
     * @param createTime the value for role_jurisdiction.create_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.modify_time
     *
     * @return the value of role_jurisdiction.modify_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.modify_time
     *
     * @param modifyTime the value for role_jurisdiction.modify_time
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_jurisdiction.is_deleted
     *
     * @return the value of role_jurisdiction.is_deleted
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_jurisdiction.is_deleted
     *
     * @param isDeleted the value for role_jurisdiction.is_deleted
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role_jurisdiction
     *
     * @mbg.generated Mon Jun 08 16:47:03 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", jurisdictionId=").append(jurisdictionId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}