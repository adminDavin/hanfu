package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserBrowseInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2314724088608457686L;
	private String date;
	private Integer userId;
	private String name;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime browseTime;
	private List<HfProductDisplay> list;
	private List<StoneConcernInfo> stoneInfo;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getBrowseTime() {
		return browseTime;
	}
	public void setBrowseTime(LocalDateTime browseTime) {
		this.browseTime = browseTime;
	}
	public List<HfProductDisplay> getList() {
		return list;
	}
	public void setList(List<HfProductDisplay> list) {
		this.list = list;
	}
	public List<StoneConcernInfo> getStoneInfo() {
		return stoneInfo;
	}
	public void setStoneInfo(List<StoneConcernInfo> stoneInfo) {
		this.stoneInfo = stoneInfo;
	}
}
