package com.hanfu.user.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;


@SuppressWarnings("serial")
public class UserInfo implements Serializable {
	private Integer userId;


	private String email;
	private Integer id;
	private String username;
	private String phone;
	private String sourceType;
	private String nickName;
	private String realName;
	private Byte sex;
	private LocalDateTime birthDay;
	private Byte userStatus;
	private Integer fileId;
	private String address;
	private Byte userLevel;
	private LocalDateTime lastAuthTime;
	private String region;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private Byte idDeleted;
	private Integer cancelId;
	private String balanceType;
    private Integer hfBalance;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
    

}
