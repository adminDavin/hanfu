package com.hanfu.activity.center.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ActivitiRuleInstance implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.activity_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer activityId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.rule_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer ruleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.rule_instance_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer ruleInstanceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.is_relate_user
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Boolean isRelateUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.file_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.user_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.user_ticket_count
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer userTicketCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.user_score
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Integer userScore;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.is_elected
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Boolean isElected;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.rule_instance_value
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String ruleInstanceValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.remarks
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private String remarks;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private LocalDateTime createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private LocalDateTime modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column activiti_rule_instance.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private Short isDeleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table activiti_rule_instance
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.id
     *
     * @return the value of activiti_rule_instance.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.id
     *
     * @param id the value for activiti_rule_instance.id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.activity_id
     *
     * @return the value of activiti_rule_instance.activity_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getActivityId() {
        return activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.activity_id
     *
     * @param activityId the value for activiti_rule_instance.activity_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.rule_id
     *
     * @return the value of activiti_rule_instance.rule_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.rule_id
     *
     * @param ruleId the value for activiti_rule_instance.rule_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.rule_instance_id
     *
     * @return the value of activiti_rule_instance.rule_instance_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getRuleInstanceId() {
        return ruleInstanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.rule_instance_id
     *
     * @param ruleInstanceId the value for activiti_rule_instance.rule_instance_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleInstanceId(Integer ruleInstanceId) {
        this.ruleInstanceId = ruleInstanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.is_relate_user
     *
     * @return the value of activiti_rule_instance.is_relate_user
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Boolean getIsRelateUser() {
        return isRelateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.is_relate_user
     *
     * @param isRelateUser the value for activiti_rule_instance.is_relate_user
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setIsRelateUser(Boolean isRelateUser) {
        this.isRelateUser = isRelateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.file_id
     *
     * @return the value of activiti_rule_instance.file_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.file_id
     *
     * @param fileId the value for activiti_rule_instance.file_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.user_id
     *
     * @return the value of activiti_rule_instance.user_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.user_id
     *
     * @param userId the value for activiti_rule_instance.user_id
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.user_ticket_count
     *
     * @return the value of activiti_rule_instance.user_ticket_count
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getUserTicketCount() {
        return userTicketCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.user_ticket_count
     *
     * @param userTicketCount the value for activiti_rule_instance.user_ticket_count
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setUserTicketCount(Integer userTicketCount) {
        this.userTicketCount = userTicketCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.user_score
     *
     * @return the value of activiti_rule_instance.user_score
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Integer getUserScore() {
        return userScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.user_score
     *
     * @param userScore the value for activiti_rule_instance.user_score
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.is_elected
     *
     * @return the value of activiti_rule_instance.is_elected
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Boolean getIsElected() {
        return isElected;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.is_elected
     *
     * @param isElected the value for activiti_rule_instance.is_elected
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setIsElected(Boolean isElected) {
        this.isElected = isElected;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.rule_instance_value
     *
     * @return the value of activiti_rule_instance.rule_instance_value
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRuleInstanceValue() {
        return ruleInstanceValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.rule_instance_value
     *
     * @param ruleInstanceValue the value for activiti_rule_instance.rule_instance_value
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRuleInstanceValue(String ruleInstanceValue) {
        this.ruleInstanceValue = ruleInstanceValue == null ? null : ruleInstanceValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.remarks
     *
     * @return the value of activiti_rule_instance.remarks
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.remarks
     *
     * @param remarks the value for activiti_rule_instance.remarks
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.create_time
     *
     * @return the value of activiti_rule_instance.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.create_time
     *
     * @param createTime the value for activiti_rule_instance.create_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.modify_time
     *
     * @return the value of activiti_rule_instance.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.modify_time
     *
     * @param modifyTime the value for activiti_rule_instance.modify_time
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column activiti_rule_instance.is_deleted
     *
     * @return the value of activiti_rule_instance.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column activiti_rule_instance.is_deleted
     *
     * @param isDeleted the value for activiti_rule_instance.is_deleted
     *
     * @mbg.generated Wed Nov 20 07:47:04 CST 2019
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table activiti_rule_instance
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
        sb.append(", activityId=").append(activityId);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", ruleInstanceId=").append(ruleInstanceId);
        sb.append(", isRelateUser=").append(isRelateUser);
        sb.append(", fileId=").append(fileId);
        sb.append(", userId=").append(userId);
        sb.append(", userTicketCount=").append(userTicketCount);
        sb.append(", userScore=").append(userScore);
        sb.append(", isElected=").append(isElected);
        sb.append(", ruleInstanceValue=").append(ruleInstanceValue);
        sb.append(", remarks=").append(remarks);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}