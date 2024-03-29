package com.hanfu.payment.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Account implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.user_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_code
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private String accountCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_type
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private String accountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_role
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private String accountRole;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.merchant_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private Integer merchantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.create_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.modify_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.is_deleted
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private Integer isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.last_modifier
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private String lastModifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.id
     *
     * @return the value of account.id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.id
     *
     * @param id the value for account.id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.user_id
     *
     * @return the value of account.user_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.user_id
     *
     * @param userId the value for account.user_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.account_code
     *
     * @return the value of account.account_code
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.account_code
     *
     * @param accountCode the value for account.account_code
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode == null ? null : accountCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.account_type
     *
     * @return the value of account.account_type
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.account_type
     *
     * @param accountType the value for account.account_type
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.account_role
     *
     * @return the value of account.account_role
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public String getAccountRole() {
        return accountRole;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.account_role
     *
     * @param accountRole the value for account.account_role
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole == null ? null : accountRole.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.merchant_id
     *
     * @return the value of account.merchant_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public Integer getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.merchant_id
     *
     * @param merchantId the value for account.merchant_id
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.create_date
     *
     * @return the value of account.create_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.create_date
     *
     * @param createDate the value for account.create_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.modify_date
     *
     * @return the value of account.modify_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.modify_date
     *
     * @param modifyDate the value for account.modify_date
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.is_deleted
     *
     * @return the value of account.is_deleted
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.is_deleted
     *
     * @param isDeleted the value for account.is_deleted
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.last_modifier
     *
     * @return the value of account.last_modifier
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public String getLastModifier() {
        return lastModifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.last_modifier
     *
     * @param lastModifier the value for account.last_modifier
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Thu May 28 17:27:30 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", accountCode=").append(accountCode);
        sb.append(", accountType=").append(accountType);
        sb.append(", accountRole=").append(accountRole);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append("]");
        return sb.toString();
    }
}