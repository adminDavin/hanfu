package com.hanfu.user.center.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class Authorization {
    @KeySql(useGeneratedKeys = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private Integer fileId;
    @ApiParam(required = false, value = "真实姓名")
    private String realName;
    @ApiParam(required = false, value = "员工工号")
    private String employeeCode;
    @ApiParam(required = false, value = "电话号码")
    private String phone;
    @ApiParam(required = false, value = "身份证号码")
    private String idCard;
    @ApiParam(required = false, value = "邮箱")
    private String employeeEmail;
    @ApiParam(required = false, value = "职位")
    private String position;
    @ApiParam(required = false, value = "性别")
    private Integer employeeSex;
    @ApiParam(required = false, value = "用户名")
    private String employeeName;
    @ApiParam(required = false, value = "地址")
    private String employeeSite;
    @ApiParam(required = false, value = "部门")
    private String employeeDepartment;
    @ApiParam(required = false, value = "状态")
    private Integer state;
    @ApiParam(required = false, value = "备注")
    private String remark;
    @ApiParam(required = false, value = "是否失效")
    private Byte idDeleted;
    @ApiParam(required = false, value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createDate;
    @ApiParam(required = false, value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime modifyDate;

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
        this.realName = realName == null ? null : realName.trim();
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode == null ? null : employeeCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail == null ? null : employeeEmail.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
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
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getEmployeeSite() {
        return employeeSite;
    }

    public void setEmployeeSite(String employeeSite) {
        this.employeeSite = employeeSite == null ? null : employeeSite.trim();
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment == null ? null : employeeDepartment.trim();
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
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(Byte idDeleted) {
        this.idDeleted = idDeleted;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
    }
}