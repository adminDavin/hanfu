package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class StrategyRuleRequest extends CommonRequest{
	@ApiModelProperty(required = true, value = "规则名字")
	private String ruleName;
	@ApiModelProperty(required = true, value = "规则描述")
	private String ruleDesc;
	@ApiModelProperty(required = true, value = "规则类型")
	private String ruleType;
    @ApiModelProperty(required = true, value = "规则值类型")
    private String ruelValueType;
    @ApiModelProperty(required = true, value = "活动策略id")
    private Integer StrategyId;
	
	

	public Integer getStrategyId() {
        return StrategyId;
    }
    public void setStrategyId(Integer strategyId) {
        StrategyId = strategyId;
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
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuelValueType() {
		return ruelValueType;
	}
	public void setRuelValueType(String ruelValueType) {
		this.ruelValueType = ruelValueType;
	}
}