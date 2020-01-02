package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityVoteRecordRequest extends CommonRequest {
    @ApiModelProperty(required = false, value = "id")
    private Integer id;
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = true, value = "投票分数记录")
    private Integer voteTimes;
    @ApiModelProperty(required = true, value = "候选人id")
    private Integer electedUserId;
    @ApiModelProperty(required = true, value = "备注")
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getVoteTimes() {
        return voteTimes;
    }

    public void setVoteTimes(Integer voteTimes) {
        this.voteTimes = voteTimes;
    }

    public Integer getElectedUserId() {
        return electedUserId;
    }

    public void setElectedUserId(Integer electedUserId) {
        this.electedUserId = electedUserId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
