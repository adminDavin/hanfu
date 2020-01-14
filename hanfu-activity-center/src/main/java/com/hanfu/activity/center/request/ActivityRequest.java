package com.hanfu.activity.center.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityRequest extends CommonRequest {
    @ApiModelProperty(required = true, value = "活动id")
    private Integer id;
    @ApiModelProperty(required = true, value = "活动名称")
    private String activityName;
    @ApiModelProperty(required = true, value = "活动描述")
    private String activityDesc;
    @ApiModelProperty(required = true, value = "活动类型")
    private String activiyType;
    @ApiModelProperty(required = true, value = "活动状态")
    private String activityStatus;
    @ApiModelProperty(required = true, value = "策略id")
    private Integer strategyId;
    @ApiModelProperty(required = true, value = "活动发起者id")
    private Integer userId;

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
        this.activityName = activityName;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }

    public String getActiviyType() {
        return activiyType;
    }

    public void setActiviyType(String activiyType) {
        this.activiyType = activiyType;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
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
}
