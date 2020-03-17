package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiParam;

@SuppressWarnings("serial")
public class UserInfo implements Serializable {
	@ApiParam(required = false, value = "用户Id")
	private Integer Id;
	@ApiParam(required = false, value = "用户Id")
	private Integer userId;
	@ApiParam(required = false, value = "用户邮箱")
	private String email;
	@ApiParam(required = false, value = "用户名")
	private String username;
	@ApiParam(required = false, value = "手机号")
	private String phone;
	@ApiParam(required = false, value = "用户来源")
	private String sourceType;
	@ApiParam(required = false, value = "用户昵称")
	private String nickName;
	@ApiParam(required = false, value = "真实姓名")
	private String realName;
	@ApiParam(required = false, value = "性别")
	private Byte sex;
	@ApiParam(required = false, value = "出生日期")
	private LocalDateTime birthDay;
	@ApiParam(required = false, value = "用户状态")
	private Byte userStatus;
	@ApiParam(required = false, value = "用户头像")
	private Integer fileId;
	@ApiParam(required = false, value = "用户地址")
	private String address;
	@ApiParam(required = false, value = "用户等级")
	private Byte userLevel;
	@ApiParam(required = false, value = "最后修改人")
	private LocalDateTime lastAuthTime;
	@ApiParam(required = false, value = "地区")
	private String region;
	@ApiParam(required = false, value = "创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createDate;
	@ApiParam(required = false, value = "修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime modifyDate;
	@ApiParam(required = false, value = "是否失效")
	private Byte idDeleted;
	@ApiParam(required = false, value = "核销员Id")
	private Integer cancelId;
	@ApiParam(required = false, value = "余额类型")
	private String balanceType;
	@ApiParam(required = false, value = "余额")
    private Integer hfBalance;
	@ApiParam(required = false, value = "时间")
    private String time;
	@ApiParam(required = false, value = "自己的邀请码")
	private String ownInvitationCode;
	@ApiParam(required = false, value = "他人的邀请码")
	private String InvitationCode;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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
	public Byte getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Byte userStatus) {
		this.userStatus = userStatus;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Byte getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Byte userLevel) {
		this.userLevel = userLevel;
	}
	public LocalDateTime getLastAuthTime() {
		return lastAuthTime;
	}
	public void setLastAuthTime(LocalDateTime lastAuthTime) {
		this.lastAuthTime = lastAuthTime;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public Byte getIdDeleted() {
		return idDeleted;
	}
	public void setIdDeleted(Byte idDeleted) {
		this.idDeleted = idDeleted;
	}
	public Integer getCancelId() {
		return cancelId;
	}
	public void setCancelId(Integer cancelId) {
		this.cancelId = cancelId;
	}
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public Integer getHfBalance() {
		return hfBalance;
	}
	public void setHfBalance(Integer hfBalance) {
		this.hfBalance = hfBalance;
	}
	public String getOwnInvitationCode() {
		return ownInvitationCode;
	}
	public void setOwnInvitationCode(String ownInvitationCode) {
		this.ownInvitationCode = ownInvitationCode;
	}
	public String getInvitationCode() {
		return InvitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		InvitationCode = invitationCode;
	}

}
