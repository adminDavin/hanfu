package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

public class HfStoneRequest {
	@ApiModelProperty(required = true, name = "stoneId", value = "店铺id")
	private Integer stoneId;
	@ApiModelProperty(required = true, name = "stoneManagerId", value = "商家id")
	private Integer stoneManagerId;
	@ApiModelProperty(required = true, name = "userId", value = "用户id")
	private Integer userId;
	@ApiModelProperty(required = true, value = "店铺名称")
	private String hfName;
	@ApiModelProperty(required = true, value = "店铺描述")
	private String stoneDesc;
	@ApiModelProperty(required = true, value = "店铺状态")
	private Integer stoneStatus;
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public Integer getStoneManagerId() {
		return stoneManagerId;
	}
	public void setStoneManagerId(Integer stoneManagerId) {
		this.stoneManagerId = stoneManagerId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getHfName() {
		return hfName;
	}
	public void setHfName(String hfName) {
		this.hfName = hfName;
	}
	public String getStoneDesc() {
		return stoneDesc;
	}
	public void setStoneDesc(String stoneDesc) {
		this.stoneDesc = stoneDesc;
	}
	public Integer getStoneStatus() {
		return stoneStatus;
	}
	public void setStoneStatus(Integer stoneStatus) {
		this.stoneStatus = stoneStatus;
	}
}
