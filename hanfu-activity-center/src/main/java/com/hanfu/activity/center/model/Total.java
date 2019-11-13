package com.hanfu.activity.center.model;

public class Total {
	
	private Integer userId;
	private Integer voteCount;
	private Integer socre;
	private Integer activityId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getVoteCount() {
		return voteCount;
	}
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
	public Integer getSocre() {
		return socre;
	}
	public void setSocre(Integer socre) {
		this.socre = socre;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	@Override
	public String toString() {
		return "Total [userId=" + userId + ", voteCount=" + voteCount + ", socre=" + socre + "]";
	}
}
