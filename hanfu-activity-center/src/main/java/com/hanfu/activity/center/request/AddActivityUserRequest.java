package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class AddActivityUserRequest  extends CommonRequest{
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "活动策略里的规则id")
    private Integer ruleId;
    @ApiModelProperty(required = false, value = "活动实例规则id")
    private Integer ruleInstanceId;
    @ApiModelProperty(required = true, value = "用户id列表")
    private Integer[] userIds;
    public Integer getActivityId() {
        return activityId;
    }
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
    public Integer getRuleId() {
        return ruleId;
    }
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
    public Integer getRuleInstanceId() {
        return ruleInstanceId;
    }
    public void setRuleInstanceId(Integer ruleInstanceId) {
        this.ruleInstanceId = ruleInstanceId;
    }
    public Integer[] getUserIds() {
        return userIds;
    }
    public void setUserIds(Integer[] userIds) {
        this.userIds = userIds;
    }
    
    
}
