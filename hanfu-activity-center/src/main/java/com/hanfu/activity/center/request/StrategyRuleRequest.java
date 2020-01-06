package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class StrategyRuleRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "策略规则id")
    private Integer id;
    @ApiModelProperty(required = false, value = "规则名字")
    private String ruleName;
    @ApiModelProperty(required = false, value = "规则描述")
    private String ruleDesc;
    @ApiModelProperty(required = false, value = "规则状态")
    private String ruleStatus;
    @ApiModelProperty(required = true, value = "规则类型")
    private String ruleType;
    @ApiModelProperty(required = false, value = "规则值类型")
    private String ruleValueType;
    @ApiModelProperty(required = false, value = "策略id")
    private Integer strategyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(String ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleValueType() {
        return ruleValueType;
    }

    public void setRuleValueType(String ruleValueType) {
        this.ruleValueType = ruleValueType;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }
}