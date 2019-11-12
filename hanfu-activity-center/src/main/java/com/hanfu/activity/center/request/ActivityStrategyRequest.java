package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityStrategyRequest extends CommonRequest{
	@ApiModelProperty(required = true, value = "活动策略id")
	private Integer id;
	@ApiModelProperty(required = true, value = "活动策略名称")
	private String strategyName;
	@ApiModelProperty(required = true, value = "活动策略描述")
	private String strategyDesc;
	@ApiModelProperty(required = true, value = "活动策略类型")
	private String strategyType;
	@ApiModelProperty(required = true, value = "活动策略状态")
	private String strategyStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	public String getStrategyDesc() {
		return strategyDesc;
	}
	public void setStrategyDesc(String strategyDesc) {
		this.strategyDesc = strategyDesc;
	}
	public String getStrategyType() {
		return strategyType;
	}
	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
	public String getStrategyStatus() {
		return strategyStatus;
	}
	public void setStrategyStatus(String strategyStatus) {
		this.strategyStatus = strategyStatus;
	}
	
}
