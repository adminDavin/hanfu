package com.hanfu.product.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class HfCouponsStrategy implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.id
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.hf_name
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private String hfName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.hf_desc
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private String hfDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.strategy_type
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private String strategyType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.strategy_info
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private String strategyInfo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.hf_remark
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private String hfRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.effective_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private LocalDateTime effectiveDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.expire_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private LocalDateTime expireDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.create_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private LocalDateTime createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.modify_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private LocalDateTime modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hf_coupons_strategy.id_deleted
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private Byte idDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hf_coupons_strategy
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.id
     *
     * @return the value of hf_coupons_strategy.id
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.id
     *
     * @param id the value for hf_coupons_strategy.id
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.hf_name
     *
     * @return the value of hf_coupons_strategy.hf_name
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public String getHfName() {
        return hfName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.hf_name
     *
     * @param hfName the value for hf_coupons_strategy.hf_name
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setHfName(String hfName) {
        this.hfName = hfName == null ? null : hfName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.hf_desc
     *
     * @return the value of hf_coupons_strategy.hf_desc
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public String getHfDesc() {
        return hfDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.hf_desc
     *
     * @param hfDesc the value for hf_coupons_strategy.hf_desc
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setHfDesc(String hfDesc) {
        this.hfDesc = hfDesc == null ? null : hfDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.strategy_type
     *
     * @return the value of hf_coupons_strategy.strategy_type
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public String getStrategyType() {
        return strategyType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.strategy_type
     *
     * @param strategyType the value for hf_coupons_strategy.strategy_type
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType == null ? null : strategyType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.strategy_info
     *
     * @return the value of hf_coupons_strategy.strategy_info
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public String getStrategyInfo() {
        return strategyInfo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.strategy_info
     *
     * @param strategyInfo the value for hf_coupons_strategy.strategy_info
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setStrategyInfo(String strategyInfo) {
        this.strategyInfo = strategyInfo == null ? null : strategyInfo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.hf_remark
     *
     * @return the value of hf_coupons_strategy.hf_remark
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public String getHfRemark() {
        return hfRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.hf_remark
     *
     * @param hfRemark the value for hf_coupons_strategy.hf_remark
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setHfRemark(String hfRemark) {
        this.hfRemark = hfRemark == null ? null : hfRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.effective_date
     *
     * @return the value of hf_coupons_strategy.effective_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.effective_date
     *
     * @param effectiveDate the value for hf_coupons_strategy.effective_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.expire_date
     *
     * @return the value of hf_coupons_strategy.expire_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.expire_date
     *
     * @param expireDate the value for hf_coupons_strategy.expire_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.create_date
     *
     * @return the value of hf_coupons_strategy.create_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.create_date
     *
     * @param createDate the value for hf_coupons_strategy.create_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.modify_date
     *
     * @return the value of hf_coupons_strategy.modify_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.modify_date
     *
     * @param modifyDate the value for hf_coupons_strategy.modify_date
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hf_coupons_strategy.id_deleted
     *
     * @return the value of hf_coupons_strategy.id_deleted
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public Byte getIdDeleted() {
        return idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hf_coupons_strategy.id_deleted
     *
     * @param idDeleted the value for hf_coupons_strategy.id_deleted
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hf_coupons_strategy
     *
     * @mbg.generated Sun Mar 22 17:13:44 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hfName=").append(hfName);
        sb.append(", hfDesc=").append(hfDesc);
        sb.append(", strategyType=").append(strategyType);
        sb.append(", strategyInfo=").append(strategyInfo);
        sb.append(", hfRemark=").append(hfRemark);
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", expireDate=").append(expireDate);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", idDeleted=").append(idDeleted);
        sb.append("]");
        return sb.toString();
    }
}