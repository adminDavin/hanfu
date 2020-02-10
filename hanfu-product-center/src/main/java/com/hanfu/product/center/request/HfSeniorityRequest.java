package com.hanfu.product.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfSeniorityRequest extends CommonRequest{
	
	@ApiModelProperty(required = false, value = "序列id")
	private Integer id;
	@ApiModelProperty(required = false, value = "排行名称")
	private String seniorityName;
	@ApiModelProperty(required = false, value = "文件id")
	private Integer fileId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSeniorityName() {
		return seniorityName;
	}
	public void setSeniorityName(String seniorityName) {
		this.seniorityName = seniorityName;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
