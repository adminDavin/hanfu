package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityStrategyInstanceRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "活动策略实体id")
    private Integer id;
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "规则id")
    private Integer ruleId;
    @ApiModelProperty(required = true, value = "规则名字")
    private String ruleName;
    @ApiModelProperty(required = true, value = "规则描述")
    private String ruleDesc;
    @ApiModelProperty(required = true, value = "规则值")
    private String ruleValue;
    @ApiModelProperty(required = true, value = "规则值类型")
    private String ruleValueType;
    @ApiModelProperty(required = true, value = "规则状态")
    private String ruleStatus;

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

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getRuleValueType() {
        return ruleValueType;
    }

    public void setRuleValueType(String ruleValueType) {
        this.ruleValueType = ruleValueType;
    }

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

}
