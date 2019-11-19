package com.hanfu.activity.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ActivityStrategyInstance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.activity_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private Integer activityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private Integer ruleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_name
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private String ruleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_desc
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private String ruleDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_value
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private String ruleValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_value_type
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private String ruleValueType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.rule_status
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private String ruleStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.create_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.modify_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activity_strategy_instance.is_deleted
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activity_strategy_instance
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.id
     *
     * @return the value of activity_strategy_instance.id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.id
     *
     * @param id the value for activity_strategy_instance.id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.activity_id
     *
     * @return the value of activity_strategy_instance.activity_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.activity_id
     *
     * @param activityId the value for activity_strategy_instance.activity_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_id
     *
     * @return the value of activity_strategy_instance.rule_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_id
     *
     * @param ruleId the value for activity_strategy_instance.rule_id
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_name
     *
     * @return the value of activity_strategy_instance.rule_name
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_name
     *
     * @param ruleName the value for activity_strategy_instance.rule_name
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName == null ? null : ruleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_desc
     *
     * @return the value of activity_strategy_instance.rule_desc
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getRuleDesc() {
        return ruleDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_desc
     *
     * @param ruleDesc the value for activity_strategy_instance.rule_desc
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc == null ? null : ruleDesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_value
     *
     * @return the value of activity_strategy_instance.rule_value
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getRuleValue() {
        return ruleValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_value
     *
     * @param ruleValue the value for activity_strategy_instance.rule_value
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue == null ? null : ruleValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_value_type
     *
     * @return the value of activity_strategy_instance.rule_value_type
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getRuleValueType() {
        return ruleValueType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_value_type
     *
     * @param ruleValueType the value for activity_strategy_instance.rule_value_type
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleValueType(String ruleValueType) {
        this.ruleValueType = ruleValueType == null ? null : ruleValueType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.rule_status
     *
     * @return the value of activity_strategy_instance.rule_status
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public String getRuleStatus() {
        return ruleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.rule_status
     *
     * @param ruleStatus the value for activity_strategy_instance.rule_status
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus == null ? null : ruleStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.create_time
     *
     * @return the value of activity_strategy_instance.create_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.create_time
     *
     * @param createTime the value for activity_strategy_instance.create_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.modify_time
     *
     * @return the value of activity_strategy_instance.modify_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.modify_time
     *
     * @param modifyTime the value for activity_strategy_instance.modify_time
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activity_strategy_instance.is_deleted
     *
     * @return the value of activity_strategy_instance.is_deleted
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activity_strategy_instance.is_deleted
     *
     * @param isDeleted the value for activity_strategy_instance.is_deleted
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activity_strategy_instance
     *
     * @mbg.generated Fri Nov 15 06:59:35 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", activityId=").append(activityId);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", ruleName=").append(ruleName);
        sb.append(", ruleDesc=").append(ruleDesc);
        sb.append(", ruleValue=").append(ruleValue);
        sb.append(", ruleValueType=").append(ruleValueType);
        sb.append(", ruleStatus=").append(ruleStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}