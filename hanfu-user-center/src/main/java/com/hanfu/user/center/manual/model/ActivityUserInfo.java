package com.hanfu.user.center.manual.model;

import java.io.Serializable;

public class ActivityUserInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5681401763142319621L;
	
	private String avatar;
	private String wxName;
	private String departmentName;
	private String realName;
	private String Jobposition;
	private String createtime;
	private Integer departmentId;
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getWxName() {
		return wxName;
	}
	public void setWxName(String wxName) {
		this.wxName = wxName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getJobposition() {
		return Jobposition;
	}
	public void setJobposition(String jobposition) {
		Jobposition = jobposition;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
}
