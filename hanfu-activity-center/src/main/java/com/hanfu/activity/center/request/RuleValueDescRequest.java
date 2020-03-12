package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class RuleValueDescRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "活动规则值描述id")
    private Integer id;
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "规则id")
    private Integer ruleId;
    @ApiModelProperty(required = true, value = "规则实体id")
    private Integer ruleInstanceId;
    @ApiModelProperty(required = true, value = "是否关联用户")
    private boolean isRelateUser;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = true, value = "规则实体值")
    private String ruleInstanceValue;
    @ApiModelProperty(required = true, value = "备注")
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isRelateUser() {
        return isRelateUser;
    }

    public void setRelateUser(boolean isRelateUser) {
        this.isRelateUser = isRelateUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRuleInstanceValue() {
        return ruleInstanceValue;
    }

    public void setRuleInstanceValue(String ruleInstanceValue) {
        this.ruleInstanceValue = ruleInstanceValue;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
