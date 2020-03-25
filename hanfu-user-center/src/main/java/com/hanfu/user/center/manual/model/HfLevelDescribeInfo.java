package com.hanfu.user.center.manual.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class HfLevelDescribeInfo {
	
	private Integer id;
	private Integer levelId;
	private String levelDescribe;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime modifyTime;
	private byte isDeleted;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public String getLevelDescribe() {
		return levelDescribe;
	}
	public void setLevelDescribe(String levelDescribe) {
		this.levelDescribe = levelDescribe;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public LocalDateTime getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(LocalDateTime modifyTime) {
		this.modifyTime = modifyTime;
	}
	public byte getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
