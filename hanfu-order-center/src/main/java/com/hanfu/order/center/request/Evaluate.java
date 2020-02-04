package com.hanfu.order.center.request;

import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
public class Evaluate extends CommonRequest{
	 @ApiModelProperty(required = false, value = "id")
	    private Integer id;
	 @ApiModelProperty(required = false, value = "用户Id")
	    private Integer userId;
	    @ApiModelProperty(required = false, value = "订单id")
	    private Integer orderId;
	    @ApiModelProperty(required = false, value = "评价")
	    private String evaluate;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getEvaluate() {
			return evaluate;
		}
		public void setEvaluate(String evaluate) {
			this.evaluate = evaluate;
		}
		public Integer getOrderId() {
			return orderId;
		}
		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
	    
}
