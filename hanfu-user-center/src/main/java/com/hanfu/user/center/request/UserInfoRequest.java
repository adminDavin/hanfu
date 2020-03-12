package com.hanfu.user.center.request;


import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class UserInfoRequest extends CommonRequest {
    @ApiParam(required = false, value = "登录的用户名")
    private String username;
    @ApiParam(required = false, value = "用户邮箱")
    private String email;
    @ApiParam(required = false, value = "用户昵称")
    private String nickName;
    @ApiParam(required = false, value = "用户真实姓名")
    private String realName;
    @ApiParam(required = false, value = "用户性别")
    private Byte sex;
    @ApiParam(required = false, value = "出生时间, 时间格式:20181023T081324Z")
    private LocalDateTime birthDay;
    @ApiModelProperty(required = false, value = "图片文件")
    private MultipartFile fileInfo;
    @ApiParam(required = false, value = "详细地址")
    private String address;
    @ApiParam(required = false, value = "所在地区")
    private String region;
    @ApiParam(required = false, value = "手机号")
    private String phone;
    @ApiParam(required = false, value = "用户状态")
    private Byte userStatus;
    @ApiParam(required = false, value = "核销员Id")
    private Integer cancelId;
    
    public Integer getCancelId() {
		return cancelId;
	}

	public void setCancelId(Integer cancelId) {
		this.cancelId = cancelId;
	}

	public Byte getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public LocalDateTime getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDateTime birthDay) {
        this.birthDay = birthDay;
    }

    public MultipartFile getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(MultipartFile fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
