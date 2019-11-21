package com.hanfu.activity.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ActivityInfo implements Serializable {
    private Integer id;
    private String activityName;
    private String activityDesc;
    private String activiyType;
    private String activityStatus;
    private String activityResult;
    private Integer strategyId;
    private Integer userId;
    private Short isTimingStart;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;
    private Short isDeleted;
    private String strategyName;
    private static final long serialVersionUID = 1L;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }
    public String getActivityDesc() {
        return activityDesc;
    }
    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc == null ? null : activityDesc.trim();
    }
    public String getActiviyType() {
        return activiyType;
    }

    public void setActiviyType(String activiyType) {
        this.activiyType = activiyType == null ? null : activiyType.trim();
    }
    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus == null ? null : activityStatus.trim();
    }

    public String getActivityResult() {
        return activityResult;
    }

    public void setActivityResult(String activityResult) {
        this.activityResult = activityResult == null ? null : activityResult.trim();
    }
    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Short getIsTimingStart() {
        return isTimingStart;
    }

    public void setIsTimingStart(Short isTimingStart) {
        this.isTimingStart = isTimingStart;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getStrategyName() {
		return strategyName;
	}
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", activityName=").append(activityName);
        sb.append(", activityDesc=").append(activityDesc);
        sb.append(", activiyType=").append(activiyType);
        sb.append(", activityStatus=").append(activityStatus);
        sb.append(", activityResult=").append(activityResult);
        sb.append(", strategyId=").append(strategyId);
        sb.append(", userId=").append(userId);
        sb.append(", isTimingStart=").append(isTimingStart);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append("]");
        return sb.toString();
    }
}