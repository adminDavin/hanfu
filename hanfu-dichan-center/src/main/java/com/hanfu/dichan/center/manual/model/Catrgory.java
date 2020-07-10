package com.hanfu.dichan.center.manual.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class Catrgory implements Serializable{
	
	@ApiModelProperty(required = true, value = "类型（true是类目false是详情")
	private boolean hasChildren;
	private String categoryName;
	private Integer categoryId;
	private Integer fileId;
	private Integer categoryDetailId;
	public boolean isHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
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
	public Integer getCategoryDetailId() {
		return categoryDetailId;
	}
	public void setCategoryDetailId(Integer categoryDetailId) {
		this.categoryDetailId = categoryDetailId;
	}
	
}
