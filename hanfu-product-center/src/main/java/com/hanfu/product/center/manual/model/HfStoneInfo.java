package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HfStoneInfo implements Serializable{
	
	private String stoneName;
	private String stoneDesc;
	private String address;
	private Integer avatarId;
	private Integer backgroundId;
	private Integer codeId;
	private Integer concernCount;
	private Integer hfStatus;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime expireTime;
	private Integer isConcern;
	public String getStoneName() {
		return stoneName;
	}
	public void setStoneName(String stoneName) {
		this.stoneName = stoneName;
	}
	public String getStoneDesc() {
		return stoneDesc;
	}
	public void setStoneDesc(String stoneDesc) {
		this.stoneDesc = stoneDesc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAvatarId() {
		return avatarId;
	}
	public void setAvatarId(Integer avatarId) {
		this.avatarId = avatarId;
	}
	public Integer getBackgroundId() {
		return backgroundId;
	}
	public void setBackgroundId(Integer backgroundId) {
		this.backgroundId = backgroundId;
	}
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getConcernCount() {
		return concernCount;
	}
	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}
	public Integer getHfStatus() {
		return hfStatus;
	}
	public void setHfStatus(Integer hfStatus) {
		this.hfStatus = hfStatus;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getIsConcern() {
		return isConcern;
	}
	public void setIsConcern(Integer isConcern) {
		this.isConcern = isConcern;
	}
}
