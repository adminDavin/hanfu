package com.hanfu.user.center.request;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class EmployeeInfoRequest implements Serializable {

	@ApiParam(required = false, value = "员工的id")
    private Integer id;
	@ApiParam(required = false, value = "员工的图片id")
	private Integer fileId;
	@ApiParam(required = false, value = "员工的真实姓名")
	private String realName;
	@ApiParam(required = false, value = "员工的工号")
	private String employeeCode;
	@ApiParam(required = false, value = "员工的手机号")
	private String phone;
	@ApiParam(required = false, value = "员工的身份证号")
	private String idCard;
	@ApiParam(required = false, value = "员工的邮箱")
	private String employeeEmail;
	@ApiParam(required = false, value = "员工的职位")
	private String position;
	@ApiParam(required = false, value = "员工的性别")
	private Integer employeeSex;
	@ApiParam(required = false, value = "员工的用户名")
	private String employeeName;
	@ApiParam(required = false, value = "员工的用地址")
	private String employeeSite;
	@ApiParam(required = false, value = "员工的部门")
	private String employeeDepartment;
	@ApiParam(required = false, value = "员工的状态")
	private Integer state;
	@ApiParam(required = false, value = "评论")
	private String remark;
	@ApiParam(required = false, value = "是否删除")
	private byte idDeleted;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getEmployeeSex() {
		return employeeSex;
	}
	public void setEmployeeSex(Integer employeeSex) {
		this.employeeSex = employeeSex;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeSite() {
		return employeeSite;
	}
	public void setEmployeeSite(String employeeSite) {
		this.employeeSite = employeeSite;
	}
	public String getEmployeeDepartment() {
		return employeeDepartment;
	}
	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public byte getIdDeleted() {
		return idDeleted;
	}
	public void setIdDeleted(byte idDeleted) {
		this.idDeleted = idDeleted;
	}
}
