package com.hanfu.activity.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class UserFormInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3487964551024846418L;
	
	private Integer fileId;
	private String username;
	private String departmentName;
	private LocalDateTime hiredate;
	private String jobContent;
	private String phone;
	private String evaluation;
	private Integer departmentId;
	private String A;
	private String B;
	private String C;
	private String D;
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public LocalDateTime getHiredate() {
		return hiredate;
	}
	public void setHiredate(LocalDateTime hiredate) {
		this.hiredate = hiredate;
	}
	public String getJobContent() {
		return jobContent;
	}
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getA() {
		return A;
	}
	public void setA(String a) {
		A = a;
	}
	public String getB() {
		return B;
	}
	public void setB(String b) {
		B = b;
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}
	
	
}
