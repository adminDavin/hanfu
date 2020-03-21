package com.hanfu.product.center.request;



import java.util.Arrays;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@SuppressWarnings("serial")
@ApiModel
public class ProductActivityRequest extends CommonRequest {
	
	public static enum ActivityTypeEnum {
        SECKILL_ACTIVITY("seckillActivity"),
        GROUP_ACTIVITY("groupActivity"),
        SENIORITY_ACTIVITY("seniorityActivity"),
		DISTRIBUTION_ACTIVITY("distributionActivity");
        private String activityType;
        ActivityTypeEnum(String activityType) {
            this.activityType = activityType;
        }
        
		public String getActivityType() {
            return this.activityType;
        }
        
        public static ActivityTypeEnum getActivityTypeEnum(String activityType) {
            for(ActivityTypeEnum item : ActivityTypeEnum.values()) {
                if (item.activityType.equals(activityType)) {
                    return item;
                }
            }
            return DISTRIBUTION_ACTIVITY;
        }
    }
	@ApiModelProperty(required = true, value = "活动名称")
    private String activityName;
	@ApiModelProperty(required = true, value = "活动类型")
	private String activityType;
	@ApiModelProperty(required = false, value = "开始时间")
	private Date startTime;
	@ApiModelProperty(required = false, value = "结束时间")
	private Date endTime;
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}