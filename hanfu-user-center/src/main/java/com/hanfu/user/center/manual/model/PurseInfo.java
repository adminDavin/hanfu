package com.hanfu.user.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurseInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7084355919020674473L;

	
	private String prerogative;
	private Integer surplus;
	private Integer integral;
	private Integer couponCount;
	private Integer member;
	private Integer collectCount;
	private Integer concernCount;
	private Integer browseCount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endTime;
	private String label;
	private String userName;
	private List<UserOrderInfo> order;
	
	public String getPrerogative() {
		return prerogative;
	}
	public void setPrerogative(String prerogative) {
		this.prerogative = prerogative;
	}
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getMember() {
		return member;
	}
	public void setMember(Integer member) {
		this.member = member;
	}
	public Integer getCollectCount() {
		return collectCount;
	}
	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}
	public Integer getConcernCount() {
		return concernCount;
	}
	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}
	public Integer getBrowseCount() {
		return browseCount;
	}
	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	public List<UserOrderInfo> getOrder() {
		return order;
	}
	public void setOrder(List<UserOrderInfo> order) {
		this.order = order;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
