package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class VoteTicketRequest extends CommonRequest{
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "被选举者的用户id")
    private Integer electedUserId;
    @ApiModelProperty(required = false, value = "活动规则id")
    private Integer ruleInstanceId;
    @ApiModelProperty(required = true, value = "备注")
    private String remark;
    public Integer getActivityId() {
        return activityId;
    }
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getElectedUserId() {
        return electedUserId;
    }
    public void setElectedUserId(Integer electedUserId) {
        this.electedUserId = electedUserId;
    }
    public Integer getRuleInstanceId() {
        return ruleInstanceId;
    }
    public void setRuleInstanceId(Integer ruleInstanceId) {
        this.ruleInstanceId = ruleInstanceId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}