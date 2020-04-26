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
	private Integer stoneId;
	private String stoneName;
	private String priceArea;
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
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public String getPriceArea() {
		return priceArea;
	}
	public void setPriceArea(String priceArea) {
		this.priceArea = priceArea;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
