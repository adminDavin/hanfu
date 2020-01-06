package com.hanfu.activity.center.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("serial")
@ApiModel
public class ActivityUserBaseInfoRequest implements Serializable {
    @ApiModelProperty(required = false, value = "用户姓名")
    private String username;
    @ApiModelProperty(required = false, value = "部门名称")
    private String departmentName;
    @ApiModelProperty(required = false, value = "入职时间")
    private String hiredate;
    @ApiModelProperty(required = true, value = "用户id")
    private Integer userId;
    @ApiModelProperty(required = false, value = "用户手机")
    private String phone;

    public String getUsername() {
        return username;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
