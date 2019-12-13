package com.hanfu.activity.center.manual.model;

import java.io.Serializable;

public class VoteRecordsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4746650606978736939L;
	
	private Integer activityId;
	private Integer userId;
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
