package com.hanfu.payment.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserMemberInfo implements Serializable{

	private Integer level;
	private Integer userId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
}
