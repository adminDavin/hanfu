package com.hanfu.user.center.manual.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;

import java.time.LocalDateTime;
/**
 * 皓月千里
 *
 * @param
 * @return
 */
public class AuthorizationUserAddress {
    @ApiParam(required = false, value = "用户Id")
    private Integer id;
    @ApiParam(required = false, value = "头像id")
    private Integer fileId;
    @ApiParam(required = false, value = "员工工号")
    private String employeeCode;
    @ApiParam(required = false, value = "真实姓名")
    private String realName;
    @ApiParam(required = false, value = "部门")
    private String employeeDepartment;
    @ApiParam(required = false, value = "职位")
    private String position;
    @ApiParam(required = false, value = "电话")
    private String phone;
    @ApiParam(required = false, value = "邮箱")
    private String email;
    @ApiParam(required = false, value = "状态")
    private Short state;
    @ApiParam(required = false, value = "县/区")
    private String conty;
    @ApiParam(required = false, value = "详情地址")
    private String hfAddressDetail;
    @ApiParam(required = false, value = "用户名")
    private String nickName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createDate;

    private String hfProvince;
    private String hfCity;
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getConty() {
        return conty;
    }

    public void setConty(String conty) {
        this.conty = conty;
    }

    public String getHfAddressDetail() {
        return hfAddressDetail;
    }

    public void setHfAddressDetail(String hfAddressDetail) {
        this.hfAddressDetail = hfAddressDetail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getHfProvince() {
        return hfProvince;
    }

    public void setHfProvince(String hfProvince) {
        this.hfProvince = hfProvince;
    }

    public String getHfCity() {
        return hfCity;
    }

    public void setHfCity(String hfCity) {
        this.hfCity = hfCity;
    }
}
