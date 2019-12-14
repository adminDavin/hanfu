package com.hanfu.activity.center.model;

public class Total {
	
	private Integer position;
	private Integer userId;
	private Integer voteCount;
	private Integer socre;
	private Double totalScore;
	private Integer activityId;
	private String code;
	private String username;
	private Integer fileId;
	private String departmentName;
	private boolean isRecord;
	private String activityName;
	private String activityType;
	
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
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
	public Double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public boolean isRecord() {
		return isRecord;
	}
	public void setRecord(boolean isRecord) {
		this.isRecord = isRecord;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	@Override
	public String toString() {
		return "统计 :第"+ position +"名,用户id=" + userId + ", 用户票数=" + voteCount + ", 用户分数=" + socre + ")";
	}
}
