package com.hanfu.product.center.manual.model;

import java.io.Serializable;

public class RatationInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8698981859111742278L;
	private Integer activityId;
	private Integer fileId;
	private Integer productId;
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	

}
