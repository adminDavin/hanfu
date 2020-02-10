package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class RecordScoreRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "被选举者的用户id")
    private Integer electedUserId;
    @ApiModelProperty(required = false, value = "备注(暂用投票分数)")
    private Integer[] remark;
    @ApiModelProperty(required = false, value = "暂用打分类型")
    private Integer type;

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

    public Integer getElectedUserId() {
        return electedUserId;
    }

    public void setElectedUserId(Integer electedUserId) {
        this.electedUserId = electedUserId;
    }

    public Integer[] getRemark() {
        return remark;
    }

    public void setRemark(Integer[] remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
