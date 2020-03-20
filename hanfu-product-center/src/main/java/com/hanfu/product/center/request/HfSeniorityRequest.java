package com.hanfu.product.center.request;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class HfSeniorityRequest extends CommonRequest{
	
	@ApiModelProperty(required = false, value = "序列id")
	private Integer id;
	@ApiModelProperty(required = false, value = "排行名称")
	private String seniorityName;
	@ApiModelProperty(required = false, value = "文件id")
	private Integer fileId;
	@ApiModelProperty(required = false, value = "创建时间")
	private String createTime;
	@ApiModelProperty(required = false, value = "修改时间")
	private String modifityTime;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifityTime() {
		return modifityTime;
	}
	public void setModifityTime(String modifityTime) {
		this.modifityTime = modifityTime;
	}
	
}
