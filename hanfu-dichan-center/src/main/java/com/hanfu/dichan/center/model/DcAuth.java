package com.hanfu.dichan.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DcAuth implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.user_id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.auth_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private String authType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.auth_key
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private String authKey;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.encode_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private String encodeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.auth_status
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private Byte authStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.create_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.modify_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dc_auth.id_deleted
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dc_auth
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.id
     *
     * @return the value of dc_auth.id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.id
     *
     * @param id the value for dc_auth.id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.user_id
     *
     * @return the value of dc_auth.user_id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.user_id
     *
     * @param userId the value for dc_auth.user_id
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.auth_type
     *
     * @return the value of dc_auth.auth_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.auth_type
     *
     * @param authType the value for dc_auth.auth_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setAuthType(String authType) {
        this.authType = authType == null ? null : authType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.auth_key
     *
     * @return the value of dc_auth.auth_key
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public String getAuthKey() {
        return authKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.auth_key
     *
     * @param authKey the value for dc_auth.auth_key
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setAuthKey(String authKey) {
        this.authKey = authKey == null ? null : authKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.encode_type
     *
     * @return the value of dc_auth.encode_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public String getEncodeType() {
        return encodeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.encode_type
     *
     * @param encodeType the value for dc_auth.encode_type
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType == null ? null : encodeType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.auth_status
     *
     * @return the value of dc_auth.auth_status
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public Byte getAuthStatus() {
        return authStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.auth_status
     *
     * @param authStatus the value for dc_auth.auth_status
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setAuthStatus(Byte authStatus) {
        this.authStatus = authStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.create_date
     *
     * @return the value of dc_auth.create_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.create_date
     *
     * @param createDate the value for dc_auth.create_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.modify_date
     *
     * @return the value of dc_auth.modify_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.modify_date
     *
     * @param modifyDate the value for dc_auth.modify_date
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dc_auth.id_deleted
     *
     * @return the value of dc_auth.id_deleted
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dc_auth.id_deleted
     *
     * @param idDeleted the value for dc_auth.id_deleted
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dc_auth
     *
     * @mbg.generated Mon Jul 06 11:48:54 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", authType=").append(authType);
        sb.append(", authKey=").append(authKey);
        sb.append(", encodeType=").append(encodeType);
        sb.append(", authStatus=").append(authStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}