package com.hanfu.activity.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StrategyRule implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.rule_name
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.rule_desc
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruleDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.strategy_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer strategyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.rule_status
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruleStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.rule_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruleType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.ruel_value_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruelValueType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column strategy_rule.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table strategy_rule
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.id
     *
     * @return the value of strategy_rule.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.id
     *
     * @param id the value for strategy_rule.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.rule_name
     *
     * @return the value of strategy_rule.rule_name
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.rule_name
     *
     * @param ruleName the value for strategy_rule.rule_name
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.rule_desc
     *
     * @return the value of strategy_rule.rule_desc
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.rule_desc
     *
     * @param ruleDesc the value for strategy_rule.rule_desc
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc == null ? null : ruleDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.strategy_id
     *
     * @return the value of strategy_rule.strategy_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getStrategyId() {
        return strategyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.strategy_id
     *
     * @param strategyId the value for strategy_rule.strategy_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.rule_status
     *
     * @return the value of strategy_rule.rule_status
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuleStatus() {
        return ruleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.rule_status
     *
     * @param ruleStatus the value for strategy_rule.rule_status
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus == null ? null : ruleStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.rule_type
     *
     * @return the value of strategy_rule.rule_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.rule_type
     *
     * @param ruleType the value for strategy_rule.rule_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType == null ? null : ruleType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.ruel_value_type
     *
     * @return the value of strategy_rule.ruel_value_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuelValueType() {
        return ruelValueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.ruel_value_type
     *
     * @param ruelValueType the value for strategy_rule.ruel_value_type
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuelValueType(String ruelValueType) {
        this.ruelValueType = ruelValueType == null ? null : ruelValueType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.create_time
     *
     * @return the value of strategy_rule.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.create_time
     *
     * @param createTime the value for strategy_rule.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.modify_time
     *
     * @return the value of strategy_rule.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.modify_time
     *
     * @param modifyTime the value for strategy_rule.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column strategy_rule.is_deleted
     *
     * @return the value of strategy_rule.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column strategy_rule.is_deleted
     *
     * @param isDeleted the value for strategy_rule.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table strategy_rule
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ruleName=").append(ruleName);
        sb.append(", ruleDesc=").append(ruleDesc);
        sb.append(", strategyId=").append(strategyId);
        sb.append(", ruleStatus=").append(ruleStatus);
        sb.append(", ruleType=").append(ruleType);
        sb.append(", ruelValueType=").append(ruelValueType);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}