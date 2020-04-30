package com.hanfu.user.center.manual.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class StoreUser {
    private Integer userId;
    private Integer storeId;
    private Integer storeRole;
    private Integer isCancel;
    private String phone;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @JSONField(format= "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime createtime;
    private String lastModifier;
    private String userName;
    private String realName;
    private String bossName;
    private Integer bossId;
    private String storeName;
    private String storeAddress;
    private String storeDesc;
    private String userPhone;
    private Integer cancelId;
    private String storeRoleName;
    private String ownInvitationCode;
    private List<Integer> id;
    
    
    public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStoreRole() {
        return storeRole;
    }

    public void setStoreRole(Integer storeRole) {
        this.storeRole = storeRole;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getCancelId() {
        return cancelId;
    }

    public void setCancelId(Integer cancelId) {
        this.cancelId = cancelId;
    }

    public String getStoreRoleName() {
        return storeRoleName;
    }

    public void setStoreRoleName(String storeRoleName) {
        this.storeRoleName = storeRoleName;
    }

	public String getOwnInvitationCode() {
		return ownInvitationCode;
	}

	public void setOwnInvitationCode(String ownInvitationCode) {
		this.ownInvitationCode = ownInvitationCode;
	}
}

