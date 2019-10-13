package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class WareHouseRequest extends CommonRequest {
	@ApiModelProperty(required = true, value = "仓库名称")
	private String hfName;
	@ApiModelProperty(required = true, value = "所属区域")
	private String hfRegion;
	@ApiModelProperty(required = true, value = "仓库描述")
	private String hfDesc;
	@ApiModelProperty(required = true, value = "商家id")
	private Integer bossId;
	public String getHfName() {
		return hfName;
	}
	public void setHfName(String hfName) {
		this.hfName = hfName;
	}
	
	public String getHfRegion() {
		return hfRegion;
	}
	public void setHfRegion(String hfRegion) {
		this.hfRegion = hfRegion;
	}
	public String getHfDesc() {
		return hfDesc;
	}
	public void setHfDesc(String hfDesc) {
		this.hfDesc = hfDesc;
	}
	public Integer getBossId() {
		return bossId;
	}
	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	} 
	
	
	
}
