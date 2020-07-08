package com.hanfu.dichan.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class CatrgoryOrDetail implements Serializable{
	
	@ApiModelProperty(required = true, value = "类型（2是类目3是详情")
	private Integer type;
	
	private String categoryName;
	private Integer categoryId;
	private Integer fileId;
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	
}
