package com.hanfu.user.center.manual.model;

import java.io.Serializable;

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
}
