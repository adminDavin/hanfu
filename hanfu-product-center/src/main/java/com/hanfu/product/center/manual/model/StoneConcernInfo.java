package com.hanfu.product.center.manual.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StoneConcernInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5597559531759476351L;
	
	private String name;
	private Integer stoneId;
	private String stoneDesc;
	private Integer avatarId;
	private List<Integer> fileId;
	private Integer backgroundId;
	private Integer concernCount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStoneId() {
		return stoneId;
	}
	public void setStoneId(Integer stoneId) {
		this.stoneId = stoneId;
	}
	public String getStoneDesc() {
		return stoneDesc;
	}
	public void setStoneDesc(String stoneDesc) {
		this.stoneDesc = stoneDesc;
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
	public Integer getConcernCount() {
		return concernCount;
	}
	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}
	public List<Integer> getFileId() {
		return fileId;
	}
	public void setFileId(List<Integer> fileId) {
		this.fileId = fileId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
}
