package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class VoteTicketRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "活动id")
    private Integer activityId;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "被选举者的用户id")
    private Integer electedUserId;
    @ApiModelProperty(required = true, value = "备注(暂用投票分数)")
    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
