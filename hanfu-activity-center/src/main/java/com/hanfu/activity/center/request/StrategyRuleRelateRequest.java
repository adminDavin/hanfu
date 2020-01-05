package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class StrategyRuleRelateRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "策略规则关系id")
    private Integer id;
    @ApiModelProperty(required = true, value = "策略id")
    private Integer strategyId;
    @ApiModelProperty(required = true, value = "策略规则id")
    private Integer strategyRuleId;
    @ApiModelProperty(required = true, value = "是否启用")
    private boolean isUsed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getStrategyRuleId() {
        return strategyRuleId;
    }

    public void setStrategyRuleId(Integer strategyRuleId) {
        this.strategyRuleId = strategyRuleId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}