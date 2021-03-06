package com.hanfu.user.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Account implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.id
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.user_id
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_code
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String accountCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_type
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String accountType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_role
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String accountRole;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.merchant_id
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer merchantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.username
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.password
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.valid
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")//, timezone = "GMT+8"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime valid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.sum_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer sumMiniProgram;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.already_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer alreadyMiniProgram;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.sum_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer sumWeb;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.already_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer alreadyWeb;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.sum_uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer sumUniApp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.already__uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer alreadyUniApp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.account_attribute
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String accountAttribute;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.remark
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.create_date
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.modify_date
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.is_perpetual
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer isPerpetual;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.is_deleted
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private Integer isDeleted;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.last_modifier
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private String lastModifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table account
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.id
     *
     * @return the value of account.id
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.username
     *
     * @return the value of account.username
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.username
     *
     * @param username the value for account.username
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.password
     *
     * @return the value of account.password
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.password
     *
     * @param password the value for account.password
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.valid
     *
     * @return the value of account.valid
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public LocalDateTime getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.valid
     *
     * @param valid the value for account.valid
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setValid(LocalDateTime valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.sum_mini_program
     *
     * @return the value of account.sum_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getSumMiniProgram() {
        return sumMiniProgram;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.sum_mini_program
     *
     * @param sumMiniProgram the value for account.sum_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setSumMiniProgram(Integer sumMiniProgram) {
        this.sumMiniProgram = sumMiniProgram;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.already_mini_program
     *
     * @return the value of account.already_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getAlreadyMiniProgram() {
        return alreadyMiniProgram;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.already_mini_program
     *
     * @param alreadyMiniProgram the value for account.already_mini_program
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setAlreadyMiniProgram(Integer alreadyMiniProgram) {
        this.alreadyMiniProgram = alreadyMiniProgram;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.sum_web
     *
     * @return the value of account.sum_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getSumWeb() {
        return sumWeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.sum_web
     *
     * @param sumWeb the value for account.sum_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setSumWeb(Integer sumWeb) {
        this.sumWeb = sumWeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.already_web
     *
     * @return the value of account.already_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getAlreadyWeb() {
        return alreadyWeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.already_web
     *
     * @param alreadyWeb the value for account.already_web
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setAlreadyWeb(Integer alreadyWeb) {
        this.alreadyWeb = alreadyWeb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.sum_uni_app
     *
     * @return the value of account.sum_uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getSumUniApp() {
        return sumUniApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.sum_uni_app
     *
     * @param sumUniApp the value for account.sum_uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setSumUniApp(Integer sumUniApp) {
        this.sumUniApp = sumUniApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.already__uni_app
     *
     * @return the value of account.already__uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getAlreadyUniApp() {
        return alreadyUniApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.already__uni_app
     *
     * @param alreadyUniApp the value for account.already__uni_app
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setAlreadyUniApp(Integer alreadyUniApp) {
        this.alreadyUniApp = alreadyUniApp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.account_attribute
     *
     * @return the value of account.account_attribute
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public String getAccountAttribute() {
        return accountAttribute;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.account_attribute
     *
     * @param accountAttribute the value for account.account_attribute
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setAccountAttribute(String accountAttribute) {
        this.accountAttribute = accountAttribute == null ? null : accountAttribute.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.remark
     *
     * @return the value of account.remark
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.remark
     *
     * @param remark the value for account.remark
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.create_date
     *
     * @return the value of account.create_date
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.is_perpetual
     *
     * @return the value of account.is_perpetual
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public Integer getIsPerpetual() {
        return isPerpetual;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.is_perpetual
     *
     * @param isPerpetual the value for account.is_perpetual
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setIsPerpetual(Integer isPerpetual) {
        this.isPerpetual = isPerpetual;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.is_deleted
     *
     * @return the value of account.is_deleted
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
     */
    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier == null ? null : lastModifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table account
     *
     * @mbg.generated Mon Jun 15 14:06:25 CST 2020
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
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", valid=").append(valid);
        sb.append(", sumMiniProgram=").append(sumMiniProgram);
        sb.append(", alreadyMiniProgram=").append(alreadyMiniProgram);
        sb.append(", sumWeb=").append(sumWeb);
        sb.append(", alreadyWeb=").append(alreadyWeb);
        sb.append(", sumUniApp=").append(sumUniApp);
        sb.append(", alreadyUniApp=").append(alreadyUniApp);
        sb.append(", accountAttribute=").append(accountAttribute);
        sb.append(", remark=").append(remark);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", isPerpetual=").append(isPerpetual);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", lastModifier=").append(lastModifier);
        sb.append("]");
        return sb.toString();
    }
}