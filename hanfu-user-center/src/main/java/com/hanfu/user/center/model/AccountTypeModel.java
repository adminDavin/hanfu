package com.hanfu.user.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AccountTypeModel implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.model_id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private Integer modelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.account_type
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private String accountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.create_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.modify_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account_type_model.is_deleted
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private Byte isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account_type_model
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.id
     *
     * @return the value of account_type_model.id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.id
     *
     * @param id the value for account_type_model.id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.model_id
     *
     * @return the value of account_type_model.model_id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public Integer getModelId() {
        return modelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.model_id
     *
     * @param modelId the value for account_type_model.model_id
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.account_type
     *
     * @return the value of account_type_model.account_type
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.account_type
     *
     * @param accountType the value for account_type_model.account_type
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.create_date
     *
     * @return the value of account_type_model.create_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.create_date
     *
     * @param createDate the value for account_type_model.create_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.modify_date
     *
     * @return the value of account_type_model.modify_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.modify_date
     *
     * @param modifyDate the value for account_type_model.modify_date
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account_type_model.is_deleted
     *
     * @return the value of account_type_model.is_deleted
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public Byte getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account_type_model.is_deleted
     *
     * @param isDeleted the value for account_type_model.is_deleted
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account_type_model
     *
     * @mbg.generated Thu May 21 10:51:26 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", modelId=").append(modelId);
        sb.append(", accountType=").append(accountType);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}