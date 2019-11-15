package com.hanfu.activity.center.model;

public class Total {
	
	private Integer userId;
	private Integer voteCount;
	private Integer socre;
	private Integer activityId;
	private String code;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		Integer i = 1;
		return "统计 :第"+ i++ +"名,用户id=" + userId + ", 用户票数=" + voteCount + ", 用户分数=" + socre + ")";
	}
}
